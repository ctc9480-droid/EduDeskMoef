package com.educare.edu.education.service.impl;


import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.ObjectUtils;

import com.educare.component.VarComponent;
import com.educare.edu.comn.mapper.LectureRceptMapper;
import com.educare.edu.comn.mapper.LectureStdntMapper;
import com.educare.edu.comn.mapper.LectureTimeMapper;
import com.educare.edu.comn.model.LectureTime;
import com.educare.edu.comn.service.SmsService;
import com.educare.edu.comn.vo.CheckVO;
import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.education.service.EduService;
import com.educare.edu.education.service.EduVO;
import com.educare.edu.education.service.LctreService;
import com.educare.edu.education.service.model.Lecture;
import com.educare.edu.education.service.model.LectureRcept;
import com.educare.edu.education.service.model.LectureStdnt;
import com.educare.edu.member.service.SessionUserInfoHelper;
import com.educare.edu.member.service.impl.MemberMapper;
import com.educare.util.DateUtil;
import com.educare.util.LncUtil;

@Service("LctreService")
public class LctreServiceImpl implements LctreService {
	
	/** org.slf4j.Logger */
	private static final Logger LOG = LoggerFactory.getLogger(LctreServiceImpl.class);
	
	/** 교육관리 Mapper */
	@Resource(name = "EduMapper")
	private EduMapper eduMapper;
	
	/** 회원 Mapper */
	@Resource(name = "MemberMapper")
	private MemberMapper memberMapper;
	
	/** 회원 Mapper */
	@Resource(name = "EduService")
	private EduService eduService;
	
	@Resource(name = "LectureStdntMapper")
	private LectureStdntMapper lectureStdntMapper;
	@Resource(name = "LectureRceptMapper")
	private LectureRceptMapper lectureRceptMapper;
	@Resource(name = "SmsService")
	private SmsService smsService;
	@Resource(name = "LectureTimeMapper")
	private LectureTimeMapper lectureTimeMapper;
	
	@Override
	public ResultVO setRceptState(int rceptSeq, int eduSeq, String userId, int state) {
		ResultVO result = new ResultVO();
		try {
			String sessId = SessionUserInfoHelper.getUserId();
			//개설정보
			Lecture lc = eduMapper.selectLctreByPk(eduSeq);
			
			LectureRcept rcept = lectureRceptMapper.selectByPk(rceptSeq);
			int state0 = rcept.getState();
			
			int extPersonnel = Integer.valueOf(lc.getExtPersonnel());
			int personnel =	Integer.valueOf(lc.getPersonnel());	
			int totalPersonnel = (personnel+extPersonnel);
			int confirmCnt = eduMapper.selectConfirmRceptCnt(eduSeq);
			if(state == 2){
				//승인인원 체크
				if(confirmCnt >= personnel){
					result.setResult(0);
					result.setMsg("승인 초과 입니다.");
					return result;
				}
			}
			if(state == 1){
				//접수인원 체크
				int rceptCnt = eduMapper.selectLctreRceptCnt(eduSeq);
				//int waitCnt = eduMapper.selectWaitRceptCnt(eduSeq);
				if(lc.getCloseTp() == 1){//선착순 마감인 경우
					//신청인원 체크
					if(rceptCnt>=totalPersonnel){
						result.setResult(0);
						result.setMsg("신청 초과 입니다.");
						return result;
					}
				}
				
			}
			
			if(state > 0 && state <= 4){//1~4
				LectureStdnt stdntVO = new LectureStdnt();
				stdntVO.setEduSeq(eduSeq);
				stdntVO.setUserId(userId);
				stdntVO.setState(state);
				stdntVO.setUpdDe(new Date());
				stdntVO.setUpdId(sessId);
				lectureStdntMapper.updateByPk(stdntVO);
			}
			
			LectureRcept rceptVO = new LectureRcept();
			rceptVO.setEduSeq(eduSeq);
			rceptVO.setUserId(userId);
			rceptVO.setState(state);
			rceptVO.setRceptSeq(rceptSeq);
			lectureRceptMapper.updateByRceptSeq(rceptVO);
			
			//승인 문자발송
			if(state == 2){
				LectureTime ltParam = new LectureTime();
				ltParam.setEduSeq(eduSeq);
				//List<LectureTime> ltList = lectureTimeMapper.selectByEduSeq(ltParam);
				String tplCd = "ktl_edu_002";
				String eduPeriodStr = VarComponent.getEduPeriod2Msg(lc.getEduPeriodBegin(),lc.getEduPeriodEnd());
				String msg = VarComponent.getAlimTalkMsg(tplCd, lc.getEduNm(),eduPeriodStr);
				smsService.setSendSms(null,rcept.getMobile(),msg,null,eduSeq,userId,3,tplCd);
			}else if(state == 3){//취소문자발송
				
			}
			
			//마감 여부 설정
			setRcept(eduSeq);
			
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setResult(0);
			return result;
		}
		
		
	}
	
	@Override
	public String setRcept(int eduSeq) {
		try {
			
			//접수가능 여부 체크
			int checkRecpt = checkRcept(eduSeq).getResult();
			
			Lecture lc = new Lecture();
			lc.setEduSeq(eduSeq);
			if(checkRecpt==3){
				lc.setRceptYn("N");
			}else{
				lc.setRceptYn("Y");
			}
			eduMapper.updateLctreRceptClose(lc);
			
			return "";
		} catch (NullPointerException e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return "접수 마감 확인 중 에러";
		}
		
	}
	
	
	@Override
	public CheckVO checkRcept(int eduSeq){
		CheckVO ret = new CheckVO();
		try {
			String userId =  SessionUserInfoHelper.getUserId();
			EduVO param = new EduVO(); 
			param.setEduSeq(eduSeq);
			param.setUserId(userId);
			
			//개설정보
			Lecture lc = eduMapper.selectLctreByPk(eduSeq);
			
			//교육종료 여부
			if("Y".equals(lc.getCloseYn()) || lc.getEduPeriodEnd().compareTo(DateUtil.getDate2Str(new Date(), "yyyy-MM-dd"))<0){
				ret.setResult(4);
				ret.setMsg("교육이 종료되었습니다.");
				LOG.debug(ret.getMsg());
				return ret;
			}
			
			
			//접수기간미설정인경우 접수마감처리
			if(!SessionUserInfoHelper.isAdmin()){//사용자일경우만 체크,240802
				if(ObjectUtils.isEmpty(lc.getRceptPeriodBegin())){
					ret.setResult(3);
					ret.setMsg("온라인으로 접수 하실 수 없습니다.");
					LOG.debug(ret.getMsg());
					return ret;
				}
			}
			
			
			//if(!SessionUserInfoHelper.isAdmin()){//사용자일경우만 체크,240802//관리자체크안함,250204
				//접수기간 체크
				String nowStr = DateUtil.getDate2Str(Calendar.getInstance().getTime(),"yyyyMMddHHmm");
				String rceptPeriodBegin = LncUtil.replaceNull(lc.getRceptPeriodBegin());
				String rceptPeriodEnd= LncUtil.replaceNull(lc.getRceptPeriodEnd());
				if(rceptPeriodBegin.compareTo(nowStr)>0){//접수기간전이면
					ret.setResult(1);
					ret.setMsg("아직 접수기간이 아닙니다.");
					LOG.debug(ret.getMsg());
					return ret;
				}else if(rceptPeriodEnd.compareTo(nowStr)<0 ){
					ret.setResult(3);
					ret.setMsg("접수기간이 지났습니다.");
					LOG.debug(ret.getMsg());
					return ret;
				}
				//else{
				//	if(rceptPeriodBegin.compareTo(nowStr)>0 || rceptPeriodEnd.compareTo(nowStr)<0){
				//	}
				//}
			//}
			int extPersonnel = Integer.valueOf(lc.getExtPersonnel());
			int personnel =	Integer.valueOf(lc.getPersonnel());	
			int totalPersonnel = (personnel+extPersonnel);
			if(personnel == 0){//0이면 무제한 신청 가능
				ret.setResult(2);
				LOG.debug("limitCnt==0");
				return ret;
			}
		
			//접수인원 체크
			int rceptCnt = eduMapper.selectLctreRceptCnt(eduSeq);
			int confirmCnt = eduMapper.selectConfirmRceptCnt(eduSeq);
			//int waitCnt = eduMapper.selectWaitRceptCnt(eduSeq);
			
			//if(lc.getCloseTp() == 1){//선착순 마감인 경우
				//승인인원 체크
				if(confirmCnt >= personnel){
//					ret.setResult(3);
//					ret.setMsg("정원 초과 입니다.");
//					return ret;
				}
				
				//신청인원 체크
				if(rceptCnt>=totalPersonnel){
//					ret.setResult(3);
//					ret.setMsg("정원 초과 입니다.");
//					return ret;
				}
			//}
			
			ret.setResult(2);
			LOG.debug("end");
			return ret;
		} catch (NullPointerException e) {
			ret.setResult(0);
			LOG.debug("error");
			return ret;
		}
	}

}
