package com.educare.edu.comn.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.educare.edu.comn.mapper.CheckMapper;
import com.educare.edu.comn.mapper.LectureMapper;
import com.educare.edu.comn.mapper.LectureRceptMapper;
import com.educare.edu.comn.mapper.LectureStdntMapper;
import com.educare.edu.comn.mapper.LectureTimeMapper;
import com.educare.edu.comn.mapper.LectureTimeStdntMapper;
import com.educare.edu.comn.mapper.PayBackMapper;
import com.educare.edu.comn.mapper.PayMapper;
import com.educare.edu.comn.mapper.TableMapper;
import com.educare.edu.comn.mapper.UserMemoMapper;
import com.educare.edu.comn.model.LectureTime;
import com.educare.edu.comn.model.LectureTimeStdnt;
import com.educare.edu.comn.model.OnlineHistory;
import com.educare.edu.comn.model.Org;
import com.educare.edu.comn.model.Pay;
import com.educare.edu.comn.model.PayBack;
import com.educare.edu.comn.model.UserMemo;
import com.educare.edu.comn.service.CheckService;
import com.educare.edu.comn.service.ResultService;
import com.educare.edu.comn.service.TableService;
import com.educare.edu.comn.vo.CertNumVO;
import com.educare.edu.comn.vo.CheckVO;
import com.educare.edu.comn.vo.FeedbackVO;
import com.educare.edu.comn.vo.PayVO;
import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.education.service.EduService;
import com.educare.edu.education.service.EduVO;
import com.educare.edu.education.service.impl.EduMapper;
import com.educare.edu.education.service.model.Lecture;
import com.educare.edu.education.service.model.LectureAttach;
import com.educare.edu.education.service.model.LectureRcept;
import com.educare.edu.education.service.model.LectureStdnt;
import com.educare.edu.feedback.mapper.FeedbackMapper;
import com.educare.edu.member.service.SessionUserInfoHelper;
import com.educare.edu.member.service.impl.MemberMapper;
import com.educare.edu.member.service.model.UserInfo;
import com.educare.edu.member.service.model.UserInfoStdnt;
import com.educare.util.DateUtil;
import com.educare.util.LncUtil;

@Service("ResultService")
public class ResultServiceImpl implements ResultService {
	
	/** org.slf4j.Logger */
	private static final Logger LOG = LoggerFactory.getLogger(ResultServiceImpl.class.getName());

	@Resource(name="CheckMapper")
	private CheckMapper checkMapper;
	@Resource(name="TableMapper")
	private TableMapper tableMapper;
	@Resource(name="LectureMapper")
	private LectureMapper lectureMapper;
	@Resource(name="LectureTimeMapper")
	private LectureTimeMapper lectureTimeMapper;
	@Resource(name="FeedbackMapper")
	private FeedbackMapper feedbackMapper;
	@Resource(name="EduMapper")
	private EduMapper eduMapper;
	@Resource(name="LectureStdntMapper")
	private LectureStdntMapper lectureStdntMapper;
	@Resource(name="LectureRceptMapper")
	private LectureRceptMapper lectureRceptMapper;
	@Resource(name="PayMapper")
	private PayMapper payMapper;
	@Resource(name="EduService")
	private EduService eduService;
	@Resource(name="UserMemoMapper")
	private UserMemoMapper userMemoMapper;
	@Resource(name="PayBackMapper")
	private PayBackMapper payBackMapper;
	@Resource(name="MemberMapper")
	private MemberMapper memberMapper;
	@Override
	public ResultVO getRceptInfo(int eduSeq, String userId,int rceptSeq) {
		ResultVO result = new ResultVO();
		try {
			EduVO eduVO = new EduVO();
			eduVO.setRceptSeq(rceptSeq);
			Lecture rcept = eduMapper.selectLcrcpInfo(eduVO);
			UserMemo memo = userMemoMapper.selectByPk(new UserMemo(userId, eduSeq));
			PayBack param3 = new PayBack();
			param3.setUserId(userId);
			PayBack payBack = payBackMapper.selectByUk(param3);
			if(memo==null){
				memo = new UserMemo();
			}
			if(payBack==null){
				payBack = new PayBack();
			}
			Map<String, Object> data = new HashMap<>();
			data.put("rcept", rcept);
			data.put("memo", memo);
			data.put("payBack",payBack);
			result.setData(data);
			
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setResult(0);
			return result;
		}
	}
	@Transactional
	@Override
	public ResultVO savePayState(int rceptSeq,int eduSeq,String userId, String memoPay, int pgState) {
		ResultVO result = new ResultVO();
		try {
			
			//신청서
			EduVO vo = new EduVO();
			vo.setRceptSeq(rceptSeq);
			vo.setUserId(userId);
			vo.setEduSeq(eduSeq);
			LectureRcept rcept = eduMapper.selectLctreRceptByPk(vo);
			
			//사용자 정보
			UserInfo user = memberMapper.selectStdntInfoByPk(userId);
			
			//현장결제라면 현장결제내역 저장
			if(pgState > 0){
				//개설정보
				Lecture lc = eduMapper.selectLctreByPk(eduSeq);
				String goodNm = lc.getEduNm();
				String userNm = user.getUserNm();
				String email = user.getEncEmail();
				String tel = "";
				String mobile = user.getEncMobile();
				int payType = 9;//오프라인결제
				Date updDt = new Date();
				Date regDt = new Date();
				int amount = lc.getFee();
				
				String transactionId = null;
				String pgPayNo = null;
				String pgNm = null;
				Calendar cal = Calendar.getInstance();
				String payNo = "E_"+eduSeq+"_"+userId+"_"+DateUtil.getDate2Str(cal.getTime(), "yyyyMMddHHmmss");
				PayVO payVo = new PayVO(payNo, eduSeq, userId, userNm, email, pgState, amount, payType, tel, mobile, pgPayNo, pgNm,goodNm,regDt
						,null,null,null,null,transactionId,updDt,null);
				
				Pay pay = payMapper.selectByPk(rcept.getPayNo());
				if(pay!=null){
					Pay paramPay = new Pay();
					paramPay.setPayNo(pay.getPayNo());
					paramPay.setState(pgState);
					paramPay.setUpdDt(new Date());
					if(!ObjectUtils.isEmpty(pay.getPgPayNo())){
						TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
						result.setResult(0);
						result.setMsg("온라인 결제 상태입니다. 저장하실 수 없습니다.");
						return result;
					}
					payMapper.updateAnyByPk(paramPay);
				}else{
					payMapper.insertByPk(payVo);
				}
				
				
			}
			
			//메모 남기기
			UserMemo param3 = new UserMemo();
			param3.setUserId(userId);
			param3.setEduSeq(eduSeq);
			UserMemo memo = userMemoMapper.selectByPk(param3);
			param3.setMemoPay(memoPay);
			if(memo!=null){
				userMemoMapper.updateAnyByPk(param3);
			}else{
				userMemoMapper.insertByPk(param3);
			}
			
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setResult(0);
			return result;
		}
	}
	
	
	
}
