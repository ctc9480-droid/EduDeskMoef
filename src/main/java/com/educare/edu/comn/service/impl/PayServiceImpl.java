package com.educare.edu.comn.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.educare.component.UtilComponent;
import com.educare.edu.comn.mapper.LectureRceptMapper;
import com.educare.edu.comn.mapper.LectureTimeMapper;
import com.educare.edu.comn.mapper.PayMapper;
import com.educare.edu.comn.mapper.TableMapper;
import com.educare.edu.comn.model.Agency;
import com.educare.edu.comn.model.LectureTime;
import com.educare.edu.comn.model.Org;
import com.educare.edu.comn.model.Pay;
import com.educare.edu.comn.service.PayService;
import com.educare.edu.comn.service.SmsService;
import com.educare.edu.comn.vo.PayVO;
import com.educare.edu.education.service.EduVO;
import com.educare.edu.education.service.impl.EduMapper;
import com.educare.edu.education.service.model.Lecture;
import com.educare.edu.education.service.model.LectureRcept;
import com.educare.edu.education.service.model.LectureStdnt;
import com.educare.edu.member.service.SessionUserInfoHelper;
import com.educare.edu.member.service.impl.MemberMapper;
import com.educare.edu.member.service.model.UserInfo;
import com.educare.util.DateUtil;
import com.educare.util.LncUtil;


/**
 *
 *
 */
@Service("PayService")
public class PayServiceImpl implements PayService {
	/** 교육관리 Mapper */
	@Resource(name = "EduMapper")
	private EduMapper eduMapper;
	@Resource(name = "TableMapper")
	private TableMapper tableMapper;
	@Resource(name = "LectureTimeMapper")
	private LectureTimeMapper lectureTimeMapper;
	@Resource(name = "SmsService")
	private SmsService smsService;
	@Resource(name = "MemberMapper")
	private MemberMapper memberMapper;
	@Resource(name = "utcp")
	private UtilComponent utcp;
	@Resource(name = "PayMapper")
	private PayMapper payMapper;
	@Resource(name = "LectureRceptMapper")
	private LectureRceptMapper lectureRceptMapper;
	
	
	/** org.slf4j.Logger */
	private static final Logger LOG = LoggerFactory.getLogger(PayServiceImpl.class.getName());
	@Override
	public int setSuccessPay(String payNo,String userId,int amount,int payType,String pgPayNo,String pgNm,String pgResultData
			) {
		try {
			
			//강의정보
			//Lecture lctre = eduMapper.selectLctreByPk(eduSeq);
			
			LectureRcept lrVO = new LectureRcept();
			lrVO.setPayNo(payNo);
			LectureRcept rcept = lectureRceptMapper.selectByPayNo(lrVO);
			if(rcept == null){
				return  0;
			}
			
			int rceptSeq =  rcept.getRceptSeq();
			
			
			//금액 맞는지 체크
			//if(lctre.getFee()!=amount){
			//	return 0;
			//}
			
			int state = 2;//결제완료
			
			//가상계좌인경우 주문번호만 세팅함, 결제정보는 입금대기중으로
			if(payType==3){
				state = 1;//입금대기
			}
			
			Date updDt = new Date();
			Date regDt = new Date();
			String userNm = null;
			String email = null;
			String tel = null;
			String mobile = null;
			String goodNm = null;
			String vtBank = null;
			String vtNm = null;
			String vtNo = null;
			String vtCloseDtime = null;
			String transactionId = null;
			//결제정보 처리
			PayVO payVo = new PayVO(payNo, rceptSeq, userId, userNm, email, state, amount, payType, tel, mobile, pgPayNo, pgNm,goodNm,regDt
					,vtBank,vtNm,vtNo,vtCloseDtime,transactionId,updDt,pgResultData);
			
			//결제정보 확인
			Pay pay = payMapper.selectByPk(payNo);
			if(pay != null){
				if(pay.getState() != 2){
					Pay param2 = new Pay();
					param2.setPayNo(pgPayNo);
					param2.setState(2);
					payMapper.updateAnyByPk(param2);
				}
			}else{
				payMapper.insertByPk(payVo);
			}
			
			if(state==1){
				return 2;//입금 대기 상태 코드
			}
			
			
			return 1;
		} catch (NullPointerException e) {
			LOG.info("error : {}",e.getMessage());
			return 0;
		}
	}

	@Override
	@Transactional
	public int setCancelPay(int eduSeq,String payNo,String userId) {
		try {
			LOG.debug("in");
			
			//강의정보
			Lecture lInfo = eduMapper.selectLctreByPk(eduSeq);
			
			//결제정보 수정
			int state = 3;//취소
			PayVO payVo = new PayVO();
			payVo.setPayNo(payNo);
			payVo.setState(state);
			payVo.setUpdDt(DateUtil.getCalendar().getTime());
			tableMapper.updatePayByPk(payVo);
			
			EduVO vo = new EduVO();
			vo.setEduSeq(eduSeq);
			vo.setUserId(userId);
			
			LectureRcept lrInfo = eduMapper.selectLctreRceptByPk(vo);
			
			//동영상인경우 수강생 삭제
			eduMapper.deleteLectureStdnt(vo);
			
			eduMapper.updateLectureRceptCancelPay(lrInfo);
			
			//smsService.removeSms(eduSeq, userId);
			
			return 1;
		} catch (NullPointerException e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return 0;
		}
	}

	@Override
	public PayVO getPayInfo(int eduSeq, String userId) {
		try {
			EduVO vo = new EduVO();
			vo.setEduSeq(eduSeq);
			vo.setUserId(userId);
			LectureRcept rcept = eduMapper.selectLctreRceptByPk(vo);
			PayVO pay = tableMapper.selectPayByPk(rcept.getPayNo());
			return pay;
		} catch (NullPointerException e) {
			return null;
		}
	}
}
