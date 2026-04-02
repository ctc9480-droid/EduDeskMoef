package com.educare.edu.comn.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.educare.component.UtilComponent;
import com.educare.edu.comn.mapper.AjaxMapper;
import com.educare.edu.comn.mapper.AttendStdntMapper;
import com.educare.edu.comn.mapper.EtcGradeCodeMapper;
import com.educare.edu.comn.mapper.EtcOrgCodeMapper;
import com.educare.edu.comn.mapper.ExcelLogMapper;
import com.educare.edu.comn.mapper.LectureLikeMapper;
import com.educare.edu.comn.mapper.LectureMapper;
import com.educare.edu.comn.mapper.LectureRceptMapper;
import com.educare.edu.comn.mapper.LectureRoomMapper;
import com.educare.edu.comn.mapper.LectureSubjectStdntMapper;
import com.educare.edu.comn.mapper.LectureTimeMapper;
import com.educare.edu.comn.model.LectureTime;
import com.educare.edu.comn.service.AjaxService;
import com.educare.edu.comn.vo.EtcGradeCodeVO;
import com.educare.edu.comn.vo.EtcOrgCodeVO;
import com.educare.edu.comn.vo.ExcelLogVO;
import com.educare.edu.comn.vo.FeedbackVO;
import com.educare.edu.comn.vo.LectureRoomVO;
import com.educare.edu.comn.vo.PassCertVO;
import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.education.service.EduService;
import com.educare.edu.education.service.EduVO;
import com.educare.edu.education.service.impl.EduMapper;
import com.educare.edu.education.service.model.Lecture;
import com.educare.edu.education.service.model.LectureRcept;
import com.educare.edu.education.service.model.LectureStdnt;
import com.educare.edu.member.service.MemberVO;
import com.educare.edu.member.service.SessionUserInfoHelper;
import com.educare.edu.member.service.impl.MemberMapper;
import com.educare.edu.member.service.model.UserInfo;
import com.educare.util.LncUtil;


/**
 * <pre>
 * 	2023. 2. 23 		배현우	최초생성
 * </pre>
 */ 
@Service("AjaxService")
public class AjaxServiceImpl implements AjaxService{
	
	/** org.slf4j.Logger */
	private static final Logger LOG = LoggerFactory.getLogger(AjaxServiceImpl.class.getName());

	@Resource(name="AjaxMapper")
	private AjaxMapper ajaxMapper;
	@Resource(name="LectureRoomMapper")
	private LectureRoomMapper lectureRoomMapper;
	@Resource(name="AttendStdntMapper")
	private AttendStdntMapper attendStdntMapper;
	@Resource(name="ExcelLogMapper")
	private ExcelLogMapper excelLogMapper;
	@Resource(name="MemberMapper")
	private MemberMapper memberMapper;
	@Resource(name="utcp")
	private UtilComponent utcp;
	
	@Resource(name="EduService")
	private EduService eduService;
	@Resource(name="LectureMapper")
	private LectureMapper lectureMapper;
	@Resource(name="LectureLikeMapper")
	private LectureLikeMapper lectureLikeMapper;
	@Resource(name="LectureRceptMapper")
	private LectureRceptMapper lectureRceptMapper;
	@Resource(name="LectureSubjectStdntMapper")
	private LectureSubjectStdntMapper lectureSubjectStdntMapper;
	@Resource(name="EtcOrgCodeMapper")
	private EtcOrgCodeMapper etcOrgCodeMapper;
	@Resource(name="EtcGradeCodeMapper")
	private EtcGradeCodeMapper etcGradeCodeMapper;
	@Resource(name="EduMapper")
	private EduMapper eduMapper;
	@Resource(name="LectureTimeMapper")
	private LectureTimeMapper lectureTimeMapper;
	
	/**
	 * 수업관리 리스트를 조회한다.
	 * @param vo
	 * @return list
	 */ 
	public Map<String, Object> getLctreList(EduVO vo){
		//변수부
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Lecture> list = new ArrayList<Lecture>();
		
		//로직부
		int totalCnt = 0;
		Integer totalCntInteger = ajaxMapper.selectLctreListCnt(vo);
		if(totalCntInteger!=null) {			
			totalCnt = totalCntInteger.intValue();
		}  
		
		UserInfo user = SessionUserInfoHelper.getUserInfo();
		if(user != null){
			if("3".equals(user.getUserMemLvl())) {
				vo.setUserNm(user.getUserNm());
			}
		}
		
		list = ajaxMapper.selectLctreList(vo);
		for(Lecture dataMap:list) { 
			dataMap.setCheckRcept(utcp.checkRcept(dataMap.getEduSeq()).getResult());
		}
		
		//리턴부  
		resultMap.put("cnt", totalCnt);
		resultMap.put("list", list);
		return resultMap;
	}
	
	public List<FeedbackVO> getFeedbackList(String orgCd, EduVO eduVo){
		List<FeedbackVO> list = new ArrayList<FeedbackVO>();
		FeedbackVO vo = new FeedbackVO();
		vo.setOrgCd(orgCd);
		list = ajaxMapper.selectFeedbackList(vo, eduVo);	 	
		return list;
	}
	
	public List<PassCertVO> getPassCertList(EduVO eduVo){
		List<PassCertVO> list = new ArrayList<PassCertVO>();
		list = ajaxMapper.selectPassCertList(eduVo);
		return list;
	}
	
	@Override
	public List<LectureRoomVO> getLectureRoomList(LectureRoomVO vo) {
		List<LectureRoomVO> list = lectureRoomMapper.selectByParam(vo);
		return list;
	}

	@Override
	public ResultVO saveExcelLog(String excelTy, String content, int excelEduSeq) {
		ResultVO result = new ResultVO();
		try {
			
			if(!SessionUserInfoHelper.isAdmin()){
				result.setResult(0);
				return result;
			}
			
			String userId = SessionUserInfoHelper.getUserId();
			String userNm = SessionUserInfoHelper.getUserNm();
			ExcelLogVO vo = new ExcelLogVO(excelTy, content, userId, userNm, excelEduSeq, userId, new Date(), userId, new Date());
			int insertResult = excelLogMapper.insertByPk(vo);
			
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setResult(0);
			return result;
		}
	}

	@Override
	public ResultVO saveOpenYnAll(String openYn,List<String> eduSeqArr) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			
			Lecture param = new Lecture();
			param.setOpenYn(openYn);
			param.setUpdDe(new Date());
			for(String o : eduSeqArr){
				int eduSeq = LncUtil.nvlInt(o);
				param.setEduSeq(eduSeq);
				lectureMapper.updateByPk(param);
			}
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setResult(0);
			return result;
		}
		
	}

	@Override
	public ResultVO getStdntList(String srchWrds) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			
			MemberVO vo = new MemberVO();
			vo.setSrchMemLvl("9");
			
			if(ObjectUtils.isEmpty(srchWrds)){
				result.setResult(1);
				return result;
			}
			// 문자열을 띄어쓰기로 분리하여 배열에 저장
			String[] srchWrdArr = srchWrds.split("\\s+");
	        vo.setSrchWrdArr(srchWrdArr);
	        
			List<UserInfo> stdntList = memberMapper.selectStdntList(vo);
			rstData.put("stdntList", stdntList);
			
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setResult(0);
			return result;
		}
	}

	@Override
	public ResultVO refundRceptProc(String userId, int rceptSeq, int rfndReqFee, String rfndBankNm, String rfndAccountNm, String rfndAccountNo) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			
			LectureRcept rcept = lectureRceptMapper.selectByPk(rceptSeq);
			if(rcept == null){
				result.setMsg("잘못된 경로로 접근하였습니다.");
				result.setResult(0);
				return result;
			}
			if(!rcept.getRegId().equals(userId)){
				result.setMsg("잘못된 경로로 접근하였습니다.");
				result.setResult(0);
				return result;
			}
			
			LectureRcept vo = new LectureRcept();
			vo.setRceptSeq(rceptSeq);
			vo.setRfndReqFee(rfndReqFee);
			vo.setRfndBankNm(rfndBankNm);
			vo.setRfndAccountNm(rfndAccountNm);
			vo.setRfndAccountNo(rfndAccountNo);
			vo.setRfndState(1);//신청대기단계
			lectureRceptMapper.updateByRceptSeq(vo);
			
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setResult(0);
			return result;
		}
	}

	@Override
	public ResultVO getRefundRcept(int rceptSeq) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			
			LectureRcept rcept = lectureRceptMapper.selectByPk(rceptSeq);
			if(rcept == null){
				result.setMsg("잘못된 경로로 접근하였습니다.");
				result.setResult(0);
				return result;
			}
			
			rstData.put("rcept", rcept);
			
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setResult(0);
			return result;
		}
	}

	@Override
	public ResultVO getEtcOrgCdPage(String srchWrd,int pageNo) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			EtcOrgCodeVO param = new EtcOrgCodeVO();
			param.setSrchWrd(srchWrd);
			int totalCnt = etcOrgCodeMapper.selectByPageCnt(param);
			
			PaginationInfo paginationInfo = new PaginationInfo();
			paginationInfo.setTotalRecordCount(totalCnt);
	        paginationInfo.setCurrentPageNo(pageNo);
	        paginationInfo.setRecordCountPerPage(10);
	        paginationInfo.setPageSize(10);
	        rstData.put("pageNavi", paginationInfo);
			
	        param.setFirstRecordIndex(paginationInfo.getFirstRecordIndex());
	        param.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
			
			List<EtcOrgCodeVO> list = etcOrgCodeMapper.selectByPage(param);
			rstData.put("list", list);
			
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setResult(0);
			return result;
		}
	}
	@Override
	public ResultVO getEtcGradeCdPage(String srchWrd,int pageNo) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			EtcGradeCodeVO param = new EtcGradeCodeVO();
			param.setSrchWrd(srchWrd);
			int totalCnt = etcGradeCodeMapper.selectByPageCnt(param);
			
			PaginationInfo paginationInfo = new PaginationInfo();
			paginationInfo.setTotalRecordCount(totalCnt);
			paginationInfo.setCurrentPageNo(pageNo);
			paginationInfo.setRecordCountPerPage(10);
			paginationInfo.setPageSize(10);
			rstData.put("pageNavi", paginationInfo);
			
			param.setFirstRecordIndex(paginationInfo.getFirstRecordIndex());
			param.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
			
			List<EtcGradeCodeVO> list = etcGradeCodeMapper.selectByPage(param);
			rstData.put("list", list);
			
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setResult(0);
			return result;
		}
	}

	@Override
	public ResultVO getNameCardInfo(Integer eduSeq, String userId) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			
			//교육정보
			Lecture lctre = eduMapper.selectLctreByPk(eduSeq);
			rstData.put("lctre", lctre);
			
			//신청서 정보
			//LectureRcept lcrcp = lectureRceptMapper.selectByPk(rceptSeq);
			//rstData.put("lcrcp", lcrcp);
			
			//사용자정보
			UserInfo user = memberMapper.selectStdntInfoByPk(userId);
			rstData.put("user", user);
			
			//
			LectureTime vo = new LectureTime();
			vo.setEduSeq(eduSeq);
			List<LectureTime> list = lectureTimeMapper.selectByEduSeq(vo);
			rstData.put("time", list);
			
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setResult(0);
			return result;
		}
	}

	@Override
	public ResultVO setMealFee(int eduSeq, int mealFee, String userId) {
		ResultVO result = new ResultVO();
		try {
			
			LectureStdnt vo = new LectureStdnt();
			vo.setEduSeq(eduSeq);
			vo.setMealFee(mealFee);
			if(!ObjectUtils.isEmpty(userId)) {
				//개별 저장
				vo.setUserId(userId);
			}
			ajaxMapper.updateLectureStdntMeal(vo);
			
			result.setResult(1);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg("오류");
			result.setResult(0);
			return result;
		}
	}

	@Override
	public ResultVO setDormiFee(int eduSeq, int dormiFee, String userId) {
		ResultVO result = new ResultVO();
		try {
			
			LectureStdnt vo = new LectureStdnt();
			vo.setEduSeq(eduSeq);
			vo.setDormiFee(dormiFee);
			if(!ObjectUtils.isEmpty(userId)) {
				//개별 저장
				vo.setUserId(userId);
			}
			ajaxMapper.updateLectureStdntDormi(vo);
			
			result.setResult(1);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg("오류");
			result.setResult(0);
			return result;
		}
	}
	
	@Override
	public ResultVO setDepositYn(int eduSeq, String depositYn, String userId) {
		ResultVO result = new ResultVO();
		try {
			
			LectureStdnt vo = new LectureStdnt();
			vo.setEduSeq(eduSeq);
			vo.setDepositYn(depositYn);
			if(!ObjectUtils.isEmpty(userId)) {
				//개별 저장
				vo.setUserId(userId);
			}
			ajaxMapper.updateLectureStdntDeposit(vo);
			
			result.setResult(1);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg("오류");
			result.setResult(0);
			return result;
		}
	}

}
