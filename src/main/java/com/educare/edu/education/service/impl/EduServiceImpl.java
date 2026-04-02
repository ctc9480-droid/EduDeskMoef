package com.educare.edu.education.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;

import org.codehaus.jackson.type.TypeReference;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.educare.component.UtilComponent;
import com.educare.component.VarComponent;
import com.educare.edu.comn.mapper.AjaxMapper;
import com.educare.edu.comn.mapper.LectureEtcIemDataMapper;
import com.educare.edu.comn.mapper.LectureEtcIemMapper;
import com.educare.edu.comn.mapper.LectureMovMapper;
import com.educare.edu.comn.mapper.LectureRceptMapper;
import com.educare.edu.comn.mapper.LectureStdntMapper;
import com.educare.edu.comn.mapper.LectureSubjectMapper;
import com.educare.edu.comn.mapper.LectureSubjectStdntMapper;
import com.educare.edu.comn.mapper.LectureTimeMapper;
import com.educare.edu.comn.mapper.LectureTimeStdntMapper;
import com.educare.edu.comn.mapper.PayMapper;
import com.educare.edu.comn.mapper.SmartCheckMapper;
import com.educare.edu.comn.mapper.TableMapper;
import com.educare.edu.comn.mapper.TestMapper;
import com.educare.edu.comn.model.LectureSubject;
import com.educare.edu.comn.model.LectureSubjectStdnt;
import com.educare.edu.comn.model.LectureTime;
import com.educare.edu.comn.model.LectureTimeStdnt;
import com.educare.edu.comn.model.Test;
import com.educare.edu.comn.model.UserComp;
import com.educare.edu.comn.service.CheckService;
import com.educare.edu.comn.service.LectureEtcIemService;
import com.educare.edu.comn.service.LectureRoomService;
import com.educare.edu.comn.service.SmartcheckService;
import com.educare.edu.comn.service.SmsService;
import com.educare.edu.comn.service.SyncDataService;
import com.educare.edu.comn.vo.CategoryVO;
import com.educare.edu.comn.vo.CheckVO;
import com.educare.edu.comn.vo.FeedbackVO;
import com.educare.edu.comn.vo.LectureEtcIemDataVO;
import com.educare.edu.comn.vo.LectureEtcIemVO;
import com.educare.edu.comn.vo.PassCertTplVO;
import com.educare.edu.comn.vo.PassCertVO;
import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.comn.vo.SmartCheckVO;
import com.educare.edu.comn.vo.SubjectResultVO;
import com.educare.edu.comn.vo.TimeTableVO;
import com.educare.edu.education.service.EduService;
import com.educare.edu.education.service.EduVO;
import com.educare.edu.education.service.LctreRceptService;
import com.educare.edu.education.service.LctreService;
import com.educare.edu.education.service.TuitionFeeService;
import com.educare.edu.education.service.model.Lecture;
import com.educare.edu.education.service.model.LectureRcept;
import com.educare.edu.education.service.model.LectureStdnt;
import com.educare.edu.feedback.mapper.FeedbackMapper;
import com.educare.edu.lcsub.vo.LcsubVO;
import com.educare.edu.member.service.MemberService;
import com.educare.edu.member.service.SessionUserInfoHelper;
import com.educare.edu.member.service.impl.MemberMapper;
import com.educare.edu.member.service.model.UserInfo;
import com.educare.edu.member.service.model.UserInfoInstrctr;
import com.educare.edu.passTpl.mapper.PassCertTplMapper;
import com.educare.edu.quizTest.mapper.QuizTestMapper;
import com.educare.edu.quizTest.vo.QuizTestVO;
import com.educare.util.CryptoUtil;
import com.educare.util.DateUtil;
import com.educare.util.FileUtil;
import com.educare.util.LncUtil;
import com.educare.util.XmlBean;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 */
@Service("EduService")
public class EduServiceImpl implements EduService {
	
	//public static final String EDU_RCEPT_PATH = XmlBean.getServerContextRoot() + "/upload/edu/rcept/";
	public static final String EDU_RCEPT_PATH = XmlBean.getServerDataRoot() + "upload/lctreRcept/";
	
	/** org.slf4j.Logger */
	private static final Logger LOG = LoggerFactory.getLogger(EduServiceImpl.class);
	
	/** 교육관리 Mapper */
	@Resource(name = "EduMapper")
	private EduMapper eduMapper;
	
	/** 회원 Mapper */
	@Resource(name = "MemberMapper")
	private MemberMapper memberMapper;
	
	/** 수강료 서비스 */
	@Resource(name = "TuitionFeeService")
	private TuitionFeeService tuitionFeeService;
	
	/** 수업개설 서비스 */
	@Resource(name = "LctreService")
	private LctreService lctreService;
	
	@Resource(name="AjaxMapper")
	private AjaxMapper ajaxMapper;
	
	/** 수업개설 서비스 */
	@Resource(name = "TableMapper")
	private TableMapper tableMapper;
	
	@Resource(name = "LectureTimeMapper")
	private LectureTimeMapper lectureTimeMapper;
	
	@Resource(name = "LectureTimeStdntMapper")
	private LectureTimeStdntMapper lectureTimeStdntMapper;
	
	@Resource(name = "LectureStdntMapper")
	private LectureStdntMapper lectureStdntMapper;
	
	/** 전자출결 정보 */
	@Resource(name = "SmartCheckMapper")
	private SmartCheckMapper smartCheckMapper;
	
	/** 전자출결 정보 */
	@Resource(name = "CategoryMapper")
	private CategoryMapper categoryMapper;
	
	@Resource(name = "CheckService")
	private CheckService checkService;
	
	@Resource(name = "SmsService")
	private SmsService smsService;
	
	/** 수업개설 서비스 */
	@Resource(name = "EduService")
	private EduService eduService;
	@Resource(name = "LctreRceptService")
	private LctreRceptService lctreRceptService;
	
	@Resource(name="FeedbackMapper")
	private FeedbackMapper feedbackMapper;
	@Resource(name="LectureEtcIemMapper")
	private LectureEtcIemMapper lectureEtcIemMapper;
	@Resource(name="LectureEtcIemDataMapper")
	private LectureEtcIemDataMapper lectureEtcIemDataMapper;
	@Resource(name="LectureEtcIemService")
	private LectureEtcIemService lectureEtcIemService;
	@Resource(name="LectureRceptMapper")
	private LectureRceptMapper lectureRceptMapper;
	@Resource(name="PayMapper")
	private PayMapper payMapper;
	@Resource(name="LectureMovMapper")
	private LectureMovMapper lectureMovMapper;
	@Resource(name="LectureSubjectMapper")
	private LectureSubjectMapper lectureSubjectMapper;
	@Resource(name="MemberService")
	private MemberService memberService;
	@Resource(name="LectureRoomService")
	private LectureRoomService lectureRoomService;
	@Resource(name="TestMapper")
	private TestMapper testMapper;
	@Resource(name="QuizTestMapper")
	private QuizTestMapper quizTestMapper;
	@Resource(name="LectureSubjectStdntMapper")
	private LectureSubjectStdntMapper lectureSubjectStdntMapper;
	@Resource(name="SmartcheckService")
	private SmartcheckService smartcheckService;
	@Resource(name="PassCertTplMapper")
	private PassCertTplMapper passCertTplMapper;
	@Resource(name="SyncDataService")
	private SyncDataService syncDataService;
	
	
	/** utilcomponent */
	@Resource(name = "utcp")
	private UtilComponent utcp;
	/**
	 * 설정한 데이터 리스트를 Json 형태로 변환한다.
	 * @param dataList 설정한 데이터 리스트 
	 * @return Json 형태로 변환된 설정한 데이터 리스트
	 */
	@Override
	public JsonArray convertDataListToJsonArray(List<?> dataList) {
		
		if(dataList==null || dataList.isEmpty()) {
			return null;
		}
		
		Class<?> elementClazz = dataList.get(0).getClass();
		Field[] fields = elementClazz.getDeclaredFields();
		JsonArray jsonArr = new JsonArray();
		JsonObject jsonObj = null;
		String fieldName = null;
		Object fieldValue = null;
		
		for(Object data : dataList) {
			jsonObj = new JsonObject();
			
			for(Field field : fields) {	
				field.setAccessible(true);
				fieldName = field.getName();
				try {
					fieldValue = field.get(data);
				} catch (IllegalArgumentException e) {
					LOG.error( e.getClass().getName() + " ::: " + e.getMessage() );
					fieldValue = null;
					continue;
				} catch (IllegalAccessException e) {
					LOG.error( e.getClass().getName() + " ::: " + e.getMessage() );
					fieldValue = null;
					continue;
				}

				if(fieldValue != null) {
					jsonObj.addProperty(fieldName, String.valueOf(fieldValue));
				}
			}		
			jsonArr.add(jsonObj);
		}
		
		return jsonArr;
	}
	
	/**
	 * 증명서류 번호를 발급한다.
	 * @param mode -> 1 : 수료증, 2 : 합격증
	 * @return
	 */
	private String getCertNum(int mode,String year, int ctgrySeq, int subSeq){
		
		//누적번호
		int cumNum = checkService.getCumNumByCtgry(ctgrySeq,subSeq,year,mode);
		
		//수료증용 코드를 사용한다면
		String passCd = "";
		if(subSeq > 0){
			LectureSubject lsVO = new LectureSubject();
			lsVO.setSubSeq(subSeq);
			LectureSubject sub = lectureSubjectMapper.selectByPk(lsVO);
			/*
			if(mode == 1){
				passCd = sub.getComplCertNm();
			}else if(mode == 2){
				passCd = sub.getPassCertNm();
			}
			*/
			int passIdx = 0;
			if(mode == 1){
				passIdx = sub.getComplIdx();
			}else if(mode == 2){
				passIdx = sub.getPassIdx();
			}
			PassCertTplVO param = new PassCertTplVO();
			param.setPassIdx(passIdx);
			PassCertTplVO passTpl = passCertTplMapper.selectPassCertTplMap(param);
			passCd = passTpl.getPrefix();
		}else{
			CategoryVO cateVO2 = categoryMapper.selectCategoryByPk(ctgrySeq);
			passCd = cateVO2.getPassCd();
		}
		
		// 누적번호를 00001형태로 포맷
		String format = String.format("%%0%dd", 5);
		String cumNumStr = String.format(format, cumNum);
		String str1 = ""+year;
		String str2 = "-"+passCd;
		String str3 = "-"+cumNumStr;
		if(subSeq == 0){
			return str1+""+str2 + str3;//
		}else{
			return passCd + str1.substring(2,4) + str3;//
		}
	}
	

	/**
	 * 수업을 등록한다.
	 * @param lecture
	 * @param request
	 * @return
	 */
	@Override
	public ResultVO saveLctre(Lecture lecture) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			String userId = SessionUserInfoHelper.getUserId();
			if(lecture.getPersonnel() == 0){
				lecture.setPersonnel(0);
			}
			
			if(lecture.getMinPersonnel() == 0){
				lecture.setMinPersonnel(0);
			}
			
			if(lecture.getExtPersonnel() == 0){
				lecture.setExtPersonnel(0);
			}
			
			if(lecture.getFee() == 0){
				lecture.setFee(0);
				lecture.setFeeLimit(0);
			}
			
			if(lecture.getFeeLimit() == 0){
				lecture.setFeeLimit(0);
			}
			
			if(lecture.getGroupFee() == 0){
				lecture.setGroupFee(0);
				lecture.setGroupComment("");
			}
			
			if(lecture.getGroupComment() == null){
				lecture.setGroupComment("");
			} 
			
			if(!ObjectUtils.isEmpty(lecture.getTargetArr())){
				String targets = "";
				for(int i2=0; i2<lecture.getTargetArr().size(); i2++){
					if(i2>0){
						targets += ",";
					}
					targets += lecture.getTargetArr().get(i2);
				}
				lecture.setTargets(targets);
			}
			
			lecture.setRceptYn("Y");
			lecture.setCloseYn("N");
			lecture.setRegDe(new Date());
			lecture.setRegId(SessionUserInfoHelper.getUserId());
			lecture.setUpdDe(new Date());
			lecture.setUpdId(SessionUserInfoHelper.getUserId());
			
			//비밀번호 암호화해서 넣기
			if("Y".equals(lecture.getScrtyYn())){
				lecture.setScrtyPw(CryptoUtil.encodeSHA256CryptoNotDecode(lecture.getScrtyPw()));
			}
			
			// EduSeq 글번호가 있다면 수정
			if(lecture.getEduSeq() > 0){
				//강의 업데이트
				eduMapper.updateLctre(lecture);
				//마감 조건 봐서 '접수중' 표현하려고 함
				lctreService.setRcept(lecture.getEduSeq());
			}else{
				//lecture.setEduSeq(MaxNumUtil.getSequence(MaxNumUtil.FUNC_LCTRE));
				eduMapper.insertLctre(lecture);
			}
			rstData.put("eduSeq", lecture.getEduSeq());
			
			//기타항목 추가 저장
			if("Y".equals(lecture.getEtcIemYn())){
				LectureEtcIemVO leiVO = new LectureEtcIemVO();
				leiVO.setRegDt(new Date());
				leiVO.setRegId(userId);
				leiVO.setEduSeq(lecture.getEduSeq());
				int deleteResult = lectureEtcIemMapper.deleteByEduSeq(leiVO);
				ObjectMapper om = new ObjectMapper();
				Map<String, Object> etcIemMap = om.readValue(LncUtil.unescapeJson(lecture.getEtcIemJson()), Map.class);
				List<Map<String, Object>> etcIemList = (List<Map<String, Object>>) etcIemMap.get("etcIemList");
				for(Map<String, Object> etcIem : etcIemList){
					leiVO.setEtcIemNm(etcIem.get("etcIemNm").toString());
					leiVO.setEtcIemSeq(LncUtil.nvlInt(etcIem.get("etcIemSeq")));
					leiVO.setEtcIemEx(LncUtil.replaceNull(etcIem.get("etcIemEx")));
					leiVO.setDataInputTy(etcIem.get("dataInputTy").toString());
					leiVO.setEssntlInputYn(LncUtil.replaceNull(etcIem.get("essntlInputYn")));
					leiVO.setUseYn(LncUtil.replaceNull(etcIem.get("useYn")));
					lectureEtcIemMapper.insertByPk(leiVO);
				}
			}
			
			
			result.setResult(1);
			return result;
		} catch (NullPointerException | JsonProcessingException e) {
			result.setResult(0);
			return result;
		}
	}

	/**
	 * 수업관리 리스트를 조회한다.
	 * @param vo
	 * @return
	 */
	@Override
	public Map<String, Object> getLctreListMngr(EduVO vo) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		//연도선택해야 조회가능
		//if(ObjectUtils.isEmpty(vo.getEduYear())){
			//vo.setEduYear(DateUtil.getDate2Str(new Date(), "yyyy"));
		//}
		
		 //운영자인경우 본인의강의만 출력
        if("3".equals(SessionUserInfoHelper.getUserMemLvl()) || "4".equals(SessionUserInfoHelper.getUserMemLvl())){
        	//vo.setSrchOprtUserId(SessionUserInfoHelper.getUserId());
        	vo.setSrchRegId(SessionUserInfoHelper.getUserId());
        	//내부운영자인경우 본인 카테고리 수업도 조회
        	if("3".equals(SessionUserInfoHelper.getUserMemLvl())){
        		//vo.setSrchCtgryAuthYn("Y");//기재부는 운영권한별 필터링 사용안함,251031
        	}
        }
		
		int totalCnt = eduMapper.selectLctreListMngrCnt(vo);
		
		PaginationInfo paginationInfo = new PaginationInfo();
		
		if(vo.getRow() == null){
			vo.setRow(DETAIL_LIST_ROW);
		}
		vo.setRow(5);
		
		paginationInfo.setTotalRecordCount(totalCnt);
        paginationInfo.setCurrentPageNo(vo.getPage());
        paginationInfo.setRecordCountPerPage(vo.getRow());
        paginationInfo.setPageSize(DEFAULT_PAGE_SIZE);
        
        vo.setFirstIndex(paginationInfo.getFirstRecordIndex());
        
        List<Lecture> dataList = eduMapper.selectLctreListMngr(vo);
        //접수가능여부 세팅
        for(Lecture dataMap:dataList){
        	dataMap.setCheckRcept(utcp.checkRcept(dataMap.getEduSeq()).getResult());
        }
        resultMap.put("totalCnt", totalCnt);
        resultMap.put("paginationInfo", paginationInfo);
        resultMap.put("dataList", dataList);
        
        return resultMap;
	}

	/**
	 * 수업내용 상세조회
	 * @param eduSeq
	 * @return
	 */
	@Override
	public Lecture getLctreDetail(int eduSeq) {
		Lecture lecture = eduMapper.selectLctreByPk(eduSeq);
		
		if(lecture!=null){
			//필수 수업 이름 가져오기
			if(lecture.getPreEduSeq()!=0) {
				Lecture lectureTemp = eduMapper.selectLctreByPk(lecture.getPreEduSeq());
				lecture.setPreEduNm(lectureTemp.getEduNm());
			}
			//설문사용 유무 설문지 이름 가져오기 
			if(lecture.getFbIdx()!=0) {
				FeedbackVO feedTemp = feedbackMapper.selectOneFeedbackByFbIdx(lecture.getFbIdx());
				lecture.setFbTitle(feedTemp.getTitle());
			}
			if(lecture.getPassIdx()!=0) {
				PassCertVO passCertTemp = ajaxMapper.selectOneByPassIdx(lecture.getPassIdx());
				lecture.setPassCertNm(passCertTemp.getTitle());
			}
			
			lecture.setCheckRcept(utcp.checkRcept(lecture.getEduSeq()).getResult());
			
			//수료여부
        	LectureStdnt lsParam = new LectureStdnt();
        	lsParam.setEduSeq(lecture.getEduSeq());
        	lsParam.setUserId(SessionUserInfoHelper.getUserId());
        	LectureStdnt lsInfo = lectureStdntMapper.selectByPk(lsParam);
        	if(lsInfo!=null){
        		int passResult = utcp.checkPass(lecture.getCloseYn(),lsInfo.getPassYn(),lecture.getLctreType(),lecture.getEduPeriodEnd(),lsInfo.getState());
        		lecture.setCheckPass(passResult);
        	}
        	
        	//setTemp(lecture);
        	
        	//기타항목 가져오기
    		LectureEtcIemVO leiVO = new LectureEtcIemVO();
    		leiVO.setEduSeq(eduSeq);
    		List<LectureEtcIemVO> etcIemList = lectureEtcIemMapper.selectByEduSeq(leiVO);
    		Map<String, Object> etcIemMap = new HashMap<String, Object>();
    		etcIemMap.put("etcIemList", etcIemList);
    		ObjectMapper om = new ObjectMapper();
    		String etcIemJson = "";
    		try {
    			etcIemJson = om.writeValueAsString(etcIemMap);
    			lecture.setEtcIemJson(etcIemJson);
    		} catch (JsonProcessingException e) {
    			lecture.setEtcIemJson("");
    		}
		}
		return lecture;
	}

	/**
	 * 메인화면용 교육일정
	 * @return
	 */
	@Override
	public List<Lecture> getLctreMiniList(int srchCtgry) {
		
		EduVO vo = new EduVO();
		Date curDate = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.KOREA);
		String dateString = dateFormat.format(curDate);
		String[] dateArr = dateString.split("-");
		String day = dateArr[2];
		vo.setViewCnt(10);
		vo.setSrchDate(DateUtil.getDate2Str(curDate, "yyyyMMddHHmm"));
		vo.setSrchDay(day);
		vo.setSrchCtgry(srchCtgry);
		List<Lecture> list = eduMapper.selectLctreMiniList(vo);
		
		for(Lecture info:list){ 
			info.setCheckRcept(utcp.checkRcept(info.getEduSeq()).getResult());
		}
		
		return list;
	}

	/** 
	 * 사용자용 교육일정
	 * @param vo
	 * @return
	 */
	@Override
	public List<Lecture> getLctreUserList(EduVO vo) {
		List<Lecture> list = eduMapper.selectLctreUserList(vo);
		
		for(Lecture info:list){
			info.setCheckRcept(utcp.checkRcept(info.getEduSeq()).getResult());
		}
		
		//재정렬
		//list.sort(Comparator.comparing(Lecture::getAddSort));
		
		return list;
	}

	/**
	 * 교육일정 배너 리스트
	 * @param vo
	 * @return
	 */
	@Override
	public List<Lecture> getLctreBannerList(EduVO vo) {
		return eduMapper.selectLctreBannerList(vo);
	}

	@Override
	public ResultVO saveLctreRceptAfterPay(MultipartHttpServletRequest request,String userId, String mobilep,String emailp, String payType, Integer eduSeq, String etc, String etcIemDataJson
			,int rceptSeq,int feeTp
			, int dormiCapaCnt, String dormiAccessYn, int useTrans
			) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData =  new HashMap<String,Object>();
		result.setData(rstData);
			try {
				boolean isAdmin = SessionUserInfoHelper.isAdmin();	
				String sessId = SessionUserInfoHelper.getUserId();
				
				UserInfo user = memberMapper.selectStdntInfoByPk(userId); 
				
				if(user == null ){
					result.setMsg("잘못된 접근입니다.") ;
					result.setResult(0);
					return result;
				}
				
				if(!user.getUserMemLvl().equals("9")){
					result.setMsg("정회원만 신청 가능합니다.") ;
					result.setResult(0);
					return result;
				}
				
				Lecture lctre = eduMapper.selectLctreByPk(eduSeq);
				if(lctre == null){
					result.setMsg("잘못된 접근입니다.") ;
					result.setResult(0);
					return result;
				}
				
				CheckVO check = utcp.checkRcept(eduSeq);
				if(check.getResult()!=2){
					result.setMsg(check.getMsg()) ;
					result.setResult(0);
					return result;
				}
				
				//관리자 임의 마감
			
				
				String fileOrg = "";
				String fileRe = "";
				//신청서 업로드 
				if(request!=null){
					Map<String, String> uploadResult= this.saveRceptFile(eduSeq, userId, request);
					if( uploadResult.get("result").equals("F")){
						result.setMsg("파일 업로드중 에러가 발생하였습니다.") ;
						result.setResult(0);
						return result;
					}
					fileOrg = uploadResult.get("fileOrg");
					fileRe = uploadResult.get("fileRe");
				}
				
				//학생이 신청 자격 여부 조회
				ResultVO result2 = checkService.checkEduRcept(lctre, user);
				if(result2.getResult() != 1){
					return result2;
				}
				
				//신청서 세팅
				String belong="";
				String position="";
				String userNm = user.getUserNm();
				Date regDe = new Date();
				Date updDe = new Date();
				String regId = sessId;
				String updId = userId;
				int state = 1;//신청상태
				
				//모바일,이메일은 저장안함 240909
				String mobile=mobilep;
				String email=emailp;
				mobile=null;
				email=null;
				
				LectureRcept rceptVO = new LectureRcept(rceptSeq, eduSeq, userId, userNm, mobile, email, belong, position, etc, state,fileOrg,fileRe,feeTp,regId,regDe,updId,updDe);
				rceptVO.setDormiCapaCnt(dormiCapaCnt);
				rceptVO.setDormiAccessYn(dormiAccessYn);
				rceptVO.setUseTrans(useTrans);
				
				
				//LectureRcept rcept =  lectureRceptMapper.selectByPk(rceptSeq);
				if(rceptSeq == 0){
					lectureRceptMapper.insertByPk(rceptVO);
				}
				
				//수강 등록
				this.setLectureStdnt(rceptVO);
				
				rstData.put("rceptSeq", rceptVO.getRceptSeq());
				
				//기타항목 등록
				ResultVO etcIemResult = lectureEtcIemService.saveLectureEtcIemData(eduSeq,userId,etcIemDataJson);
				
				//신청,수강 관련 알림톡날림
				LectureTime ltParam = new LectureTime();
				ltParam.setEduSeq(eduSeq);
				List<LectureTime> ltList = lectureTimeMapper.selectByEduSeq(ltParam);
				if(lctre.getTargetTp() == 1){
					String tplCd = "ktl_edu_002";
					String eduPeriodStr = VarComponent.getEduPeriod1Msg(ltList);
					String msg = VarComponent.getAlimTalkMsg(tplCd, lctre.getEduNm(),eduPeriodStr,lctre.getTel());
					//smsService.setSendSms(null,user.getMobile(),msg,null,eduSeq,userId,3,tplCd);
				}else{
					String tplCd = "ktl_edu_008";
					String eduPeriodStr = VarComponent.getEduPeriod1Msg(ltList);
					String msg = VarComponent.getAlimTalkMsg(tplCd, lctre.getEduNm(),eduPeriodStr,lctre.getTel());
					//smsService.setSendSms(null,user.getMobile(),msg,null,eduSeq,userId,3,tplCd);
				}
				
				result.setResult(1);
				return result;
			} catch (NullPointerException e) {
				result.setMsg("시스템오류");
				result.setResult(0);
				return result;
			}	
	}

	/**
	 * 관리자용 교육 신청자 목록을 조회한다.
	 * @param vo
	 * @return
	 */
	@Override
	public ResultVO getLectureRceptList(int rowCnt,int pageNo,int eduSeq,int srchRceptState,String srchWrd) {
		ResultVO result =  new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			EduVO vo = new EduVO();
			vo.setEduSeq(eduSeq);
			vo.setSrchRceptState(srchRceptState);
			vo.setSrchWrd(srchWrd);
			int totalCnt = eduMapper.selectLectureRceptListCnt(vo);
			
			if(rowCnt > 0){
				PaginationInfo paginationInfo = new PaginationInfo();
				paginationInfo.setTotalRecordCount(totalCnt);
				paginationInfo.setCurrentPageNo(pageNo);
				paginationInfo.setRecordCountPerPage(rowCnt);
				paginationInfo.setPageSize(DEFAULT_PAGE_SIZE);
				rstData.put("page", paginationInfo);
				vo.setFirstIndex(paginationInfo.getFirstRecordIndex());
			}
	        
			vo.setRow(rowCnt);
	        List<LectureRcept> dataList = eduMapper.selectLectureRceptList(vo);
	        
	        //기타 신청서 전부 조회하여 설정
	        LectureEtcIemVO lectureEtcIemVO = new LectureEtcIemVO();
	        lectureEtcIemVO.setEduSeq(eduSeq);
	        List<LectureEtcIemVO> etcIemList = lectureEtcIemMapper.selectByEduSeq(lectureEtcIemVO);
	        List<LectureEtcIemDataVO> etcIemDataList = lectureEtcIemDataMapper.selectByEdu(lectureEtcIemVO);
	        for(LectureRcept o : dataList){
	        	List<LectureEtcIemVO> etcIemListNew = new ArrayList<LectureEtcIemVO>();
	        	for(LectureEtcIemVO o2 : etcIemList){
	        		LectureEtcIemVO o2New = new LectureEtcIemVO();
	        		BeanUtils.copyProperties(o2, o2New);
	        		etcIemListNew.add(o2New);
	        		for(LectureEtcIemDataVO o3 : etcIemDataList){
	        			if(o.getUserId().equals(o3.getUserId()) && o2.getEduSeq()==o3.getEduSeq() && o2.getEtcIemSeq()==o3.getEtcIemSeq()){
	        				o2New.setEtcIemData(o3.getEtcIemData());
	        				break;
	        			}
	        		}
	        	}
	        	Map<String, Object> etcIemMap = new HashMap<String, Object>();
	        	etcIemMap.put("etcIemList", etcIemListNew);
	        	ObjectMapper om = new ObjectMapper();
	        	String etcIemJson;
				try {
					etcIemJson = om.writeValueAsString(etcIemMap);
					o.setEtcIemJson(etcIemJson);
				} catch (JsonProcessingException e) {
					o.setEtcIemJson("");
				}
	        }
	        
	        
	        rstData.put("totalCnt", totalCnt);
	        rstData.put("dataList", dataList);
	        rstData.put("etcIemList", etcIemList);
	        
	        //승인건수
	        int stdntCnt = eduMapper.selectConfirmRceptCnt(eduSeq);
	        rstData.put("stdntCnt", stdntCnt);
	        
	        result.setResult(1);
	        return result;
		} catch (NullPointerException e) {
			LOG.info(e.getMessage());
			return result;
		}
	}
	
	/**
	 * 관리자용 교육 수강생 목록을 조회한다.
	 * @param vo
	 * @return
	 */
	@Override
	public Map<String, Object> getLectureStdntInfo(EduVO vo) {
		int eduSeq = vo.getEduSeq();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		int totalCnt = eduMapper.selectLectureStdntListCnt(vo);
		
		
		if(vo.getRow() == null){
			vo.setRow(DEFAULT_ROW);
		}
		
		PaginationInfo paginationInfo = new PaginationInfo();
		
		paginationInfo.setTotalRecordCount(totalCnt);
        paginationInfo.setCurrentPageNo(vo.getPage());
        paginationInfo.setRecordCountPerPage(vo.getRow());
        paginationInfo.setPageSize(DEFAULT_PAGE_SIZE);
        vo.setFirstIndex(paginationInfo.getFirstRecordIndex());
        
        //강사계정인 경우 전체 목록으로
        if("8".equals(SessionUserInfoHelper.getUserMemLvl())){
        	vo.setRow(0);
        }
        
        List<LectureStdnt> dataList = eduService.getLectureStdntList(vo);
        
        Lecture lctre = eduMapper.selectLctreByPk(eduSeq);
        //출석부 조회
        if(lctre.getLctreType() == 0 || lctre.getLctreType() == 2){
        	ResultVO result = smartcheckService.getRollBookDay(eduSeq, null);
        	if(result.getResult()== 1){
        		Map<String, Object> rstData = (Map<String, Object>) result.getData();
        		
        		List<SmartCheckVO> attList = (List<SmartCheckVO>) rstData.get("stdntList");
        		for(LectureStdnt o: dataList){
        			for(SmartCheckVO o2: attList){
        				if(o.getUserId().equals(o2.getUserId())){
        					o.setAbsentCnt(o2.getAbsentFinalCnt());
        					o.setDateCnt(o2.getDateCnt());
        					o.setQrCnt(o2.getQrCnt());
        					o.setAttRatio(o2.getAttRatio());
        					o.setAttendCnt(o2.getAttendCnt());//기재부 3qr방식 재합산 결과 세팅,251028
        					break;
        				}
        			}
        		}
        	}
        }
        
        //시간수 가져오기
        int lectTimeCnt = lectureTimeMapper.selectLectTimeCnt(vo.getEduSeq());
        
        //합격자수
        int passCnt = lectureStdntMapper.selectPassYCnt(vo.getEduSeq());
        
        //시험수
        Test testVO = new Test();
		testVO.setEduSeq(vo.getEduSeq());
		testVO.setTryNo(1);
		int testCnt = testMapper.selectCntByEduSeq(testVO);
		resultMap.put("testCnt", testCnt);
		testVO.setTryNo(2);
		int test2Cnt = testMapper.selectCntByEduSeq(testVO);
		resultMap.put("test2Cnt", test2Cnt);
        
        resultMap.put("totalCnt", totalCnt);
        resultMap.put("paginationInfo", paginationInfo);
        resultMap.put("dataList", dataList);
        resultMap.put("lectTimeCnt", lectTimeCnt);
        resultMap.put("passCnt", passCnt);
        
        return resultMap; 
	}
	@Override
	public List<LectureStdnt> getLectureStdntList(EduVO vo) {
		try{
			List<LectureStdnt> list = eduMapper.selectLectureStdntList(vo);
			for(LectureStdnt o:list){
				LectureTimeStdnt p = new LectureTimeStdnt();
				p.setEduSeq(o.getEduSeq());
				p.setUserId(o.getUserId());
				List<LectureTimeStdnt> list2 = lectureTimeStdntMapper.selectByEduSeqUserId(p);
				o.setLtsInfo(list2);
			}
			
			//시험지조회
			/*
			Test testVO = new Test();
			testVO.setEduSeq(vo.getEduSeq());
			List<Test> testList = testMapper.selectByEduSeq(testVO);
			for(Test o: testList){
				QuizTestVO qtVO = new QuizTestVO();
				List<QuizTestVO> trList = quizTestMapper.selectTestResultStdntList(qtVO);
			}
			 */
			
			return list;
		}catch (NullPointerException e) {
			return null;
		}
	}

	/**
	 * 단체 수강등록 처리
	 * @param vo
	 * @return
	 */
	@Override
	public String bulkUserRgs(EduVO vo) {
		String[] userIds = vo.getUserIds(); 
		if(userIds == null){
			return "데이터에 오류가 발생하였습니다.";
		}
		
		int eduSeq = vo.getEduSeq();
		if(eduSeq > 0 && userIds.length > 0){
			//개설정보
			Lecture lc = eduMapper.selectLctreByPk(vo.getEduSeq());
			
			//등록하기전에 정원 체크
			int rceptCnt = eduMapper.selectLctreRceptCnt(vo.getEduSeq());
			int limitCnt = Integer.valueOf(lc.getPersonnel());	
			int extLimitCnt = Integer.valueOf(lc.getExtPersonnel());	
			int bulkCnt = userIds.length;
			if(lc.getCloseTp() == 1){
				if((rceptCnt+bulkCnt)>(limitCnt+extLimitCnt)){
					return "최대모집인원(승인+신청)이 초과 되었습니다.";
				}
			}
			
			//승인 건수
			int confirmCnt = eduMapper.selectConfirmRceptCnt(vo.getEduSeq());
			//대기 건수
			int waitCnt = eduMapper.selectWaitRceptCnt(vo.getEduSeq());
			//남은 정원
			int remainLimitCnt = limitCnt-confirmCnt;
			//남은 대기
			int remainExtLimitCnt = extLimitCnt-waitCnt; 
			
			UserInfo user = null;
			for(String userId : vo.getUserIds()){
				int state = 1;
				if(remainLimitCnt>0){//승인상태로
					//state = LectureRcept.STATE_APPROVAL;//정원 상관없이 신청중 상태로,220321
					remainLimitCnt--;
				}
				user = memberMapper.selectStdntInfoByPk(userId);
				
				//교육신청 가능여부
				ResultVO rceptResult = checkService.checkEduRcept(lc, user);
				if(rceptResult.getResult()<=0){
					return user.getUserNm()+"는 "+rceptResult.getMsg();
				}
				String payType = null;
				String etc = null;
				String etcIemDataJson = null;
				int feeTp = 0;//단체수강등록시 미선택
				int rceptSeq = 0;
				ResultVO result = saveLctreRceptAfterPay(null, userId, user.getMobile(), user.getEmail(), payType, eduSeq, etc, etcIemDataJson, rceptSeq, feeTp,0,"N",0);
				if(result.getResult() == 0){
					return result.getMsg();
				}
				
				//기타정보 추가저장
				Map<String, Object> rstData = (Map<String, Object>) result.getData();
				rceptSeq = LncUtil.nvlInt(rstData.get("rceptSeq"));
				ResultVO result2 = memberService.getMyUserInfoAdd(userId);
				Map<String, Object> rstData2 = (Map<String, Object>) result2.getData();
				ObjectMapper om = new ObjectMapper();
				try {
					
					String compJson = null;
					if(!ObjectUtils.isEmpty(rstData2.get("comp"))){
						compJson = om.writeValueAsString(rstData2.get("comp"));
					}
					LOG.info(compJson);
					String schoolListJson = om.writeValueAsString(rstData2.get("school"));
					String certifListJson = om.writeValueAsString(rstData2.get("certif"));
					String languaListJson = om.writeValueAsString(rstData2.get("langua"));
					String careerListJson = om.writeValueAsString(rstData2.get("career"));
					
					MultipartFile bsnLcnsFile = null;
					int empInsrTp = user.getEmpInsrTp();
					int learnCardTp = user.getLearnCardTp();
					int empSuccTp = user.getEmpSuccTp();
					int bizRgsTp = user.getBizRgsTp();
					if(result.getResult() != 0){
						ResultVO result3 = eduService.saveLctreRceptAdd(rceptSeq,compJson,schoolListJson,certifListJson,languaListJson,careerListJson,bsnLcnsFile,empInsrTp,learnCardTp,empSuccTp,bizRgsTp);
					}
				} catch (JsonProcessingException e) {
					return "추가 정보 입력중 에러가 발생하였습니다.";
				}
				
				//예약 문자발송
				//smsService.setSendSms(null,CryptoSeedUtil.encrypt(user.getMobile()),smsService.getMsg(0, new String[]{user.getUserNm(),lc.getEduNm()}),null,lc.getEduSeq(),user.getUserId(),1);
				
			}
			
			//마감체크 후 처리
			lctreService.setRcept(vo.getEduSeq());
		}
		return EXCUTE_SUCCESS;
	}

	/**
	 * 승인카운트를 조회한다.
	 * @param eduSeq
	 * @return
	 */
	@Override
	public int getConfirmRceptCnt(Integer eduSeq) {
		return eduMapper.selectConfirmRceptCnt(eduSeq);
	}
	
	/**
	 * 강사 교육내역을 조회한다.
	 * @param vo
	 * @return
	 */
	@Override
	public Map<String, Object> getInstrctrEduListList(EduVO vo) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		int totalCnt = eduMapper.selectInstrctrEduListCnt(vo);
		
		PaginationInfo paginationInfo = new PaginationInfo();
		
		if(vo.getRow() == null){
			vo.setRow(MYPAGE_LIST_ROW);
		}
		paginationInfo.setTotalRecordCount(totalCnt);
        paginationInfo.setCurrentPageNo(vo.getPage());
        paginationInfo.setRecordCountPerPage(vo.getRow());
        paginationInfo.setPageSize(DEFAULT_PAGE_SIZE);
        
        vo.setFirstIndex(paginationInfo.getFirstRecordIndex());
        
        List<Lecture> dataList = eduMapper.selectInstrctrEduList(vo);
        
        resultMap.put("totalCnt", totalCnt);
        resultMap.put("paginationInfo", paginationInfo);
        resultMap.put("dataList", dataList);
        
        return resultMap;
	}

	/**
	 * 마이페이지 교육신청내용을 조회한다.
	 * @param vo
	 * @return
	 */
	@Override
	public LectureRcept getMyRceptDetail(int rceptSeq,int eduSeq, String userId) {
		EduVO vo = new EduVO(eduSeq, userId);
		vo.setRceptSeq(rceptSeq);
		return eduMapper.selectLctreRceptByPk(vo);
	}

	/**
	 * 교육신청을 취소한다.
	 * @param eduSeq
	 * @return
	 */
	@Override
	public ResultVO cancelRcept(int rceptSeq, int eduSeq,String userId) {
		ResultVO result = new ResultVO();
		Lecture lctre = eduMapper.selectLctreByPk(eduSeq);
		
		LectureRcept rcept = lectureRceptMapper.selectByPk(rceptSeq);
		LectureStdnt stdntVO = new LectureStdnt();
		stdntVO.setEduSeq(eduSeq);
		stdntVO.setUserId(userId);
		LectureStdnt stdnt = lectureStdntMapper.selectByPk(stdntVO);
		
		if(lctre == null || rcept == null) {
			result.setResult(0);
			result.setMsg("잘못된 접근입니다.");
			return result;
		}
		
		if(rceptSeq != stdnt.getRceptSeq()){
			result.setResult(0);
			result.setMsg("잘못된 접근입니다.");
			return result;
		}
		
		//수료면 불가
		if("Y".equals(stdnt.getPassYn())){
			result.setResult(0);
			result.setMsg("이미 수료 하셨습니다.");
			return result;
		}
		
		try {
			if(lctre.getFee()>0 && rcept.getPayState()==1){
				String begin = lctre.getEduPeriodBegin();
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DAY_OF_MONTH, 1);//1일추가
				String now = DateUtil.getDate2Str(cal.getTime(), "yyyy-MM-dd");
				if(begin.compareTo(now)<0){//추가한 날보다  적으면 
					result.setResult(0);
					result.setMsg("교육이 이미 시작되었습니다.");
					return result;
				}
			}
			//else{
				//String end = lctre.getRceptPeriodEnd();
				//String now = DateUtil.getDate2Str(new Date(), "yyyyMMddHHmm");
				//if(end.compareTo(now)<0){//접수마감이면 취소 안됨
				//	result.setResult(0);
				//	result.setMsg("이미 접수마감 되었습니다.");
				//	return result;
				//}
			//}
		} catch (NullPointerException e) {
			result.setResult(0);
			result.setMsg("알 수 없는 오류가 발생하였습니다. 관리자에게 문의하세요");
			return result;
		}
		
		//현장결제라면 결제취소처리

		
		//예약취소  문자
		//smsService.setSendSms(null,rcept.getMobile(),smsService.getMsg(2, new String[]{rcept.getUserNm(),lctre.getEduNm()}),null,eduSeq,userId,1);
		
		//220218,kbj,신청,수강 모두 삭제
		eduMapper.deleteLectureStdnt(new EduVO(eduSeq, userId));
		
		LectureRcept rceptVO = new LectureRcept();
		rceptVO.setRceptSeq(rceptSeq);
		rceptVO.setState(3);
		rceptVO.setUpdDe(new Date());
		lectureRceptMapper.updateByRceptSeq(rceptVO);
		
		//취소알림톡발송
		LectureTime ltParam = new LectureTime();
		ltParam.setEduSeq(eduSeq);
		List<LectureTime> ltList = lectureTimeMapper.selectByEduSeq(ltParam);
		if(lctre.getTargetTp() == 1){
			String tplCd = "ktl_edu_003";
			String eduPeriodStr = VarComponent.getEduPeriod1Msg(ltList);
			String msg = VarComponent.getAlimTalkMsg(tplCd, lctre.getEduNm(),eduPeriodStr,lctre.getTel());
			smsService.setSendSms(null,rcept.getMobile(),msg,null,eduSeq,userId,3,tplCd);
		}else{
			String tplCd = "ktl_edu_009";
			String eduPeriodStr = VarComponent.getEduPeriod1Msg(ltList);
			String msg = VarComponent.getAlimTalkMsg(tplCd, lctre.getEduNm(),eduPeriodStr,lctre.getTel());
			smsService.setSendSms(null,rcept.getMobile(),msg,null,eduSeq,userId,3,tplCd);
		}
		
		//sms대기 건이 있다면 삭제처리
		//smsService.removeSms(eduSeq,userId);
		
		
		result.setResult(1);
		return result;
	}
	

	
	/**
	 * 교육 신청자 승인마감 처리.
	 * @param vo
	 */
	@Override
	public String rceptClose(EduVO vo) {
		
		try {
			Integer eduSeq = vo.getEduSeq();
			//String userIds[] = vo.getUserIds();
			
			Lecture lctre = eduMapper.selectLctreByPk(eduSeq);
			LectureStdnt stdnt = null;
			
			int limitCnt = lctre.getPersonnel();//정원
			int confirmCnt = eduMapper.selectConfirmRceptCnt(eduSeq);//승인건수
			if(limitCnt<confirmCnt){
				return "정원 초과 입니다. 승인건수를 확인하세요";
			}
			
			List<LectureRcept> lrList = eduMapper.selectLctreRceptByEduSeq(eduSeq);
			//승인건 선발
			for(LectureRcept rcept : lrList){
				if(rcept.getState() == 2){
					vo.setUserId(rcept.getUserId());
					if(eduMapper.selectLctreStdntByPk(vo)==null){
						//rcept.setSelectnYn(LectureRcept.STR_Y);
						//eduMapper.updateLectureRceptSelectn(rcept);
						setLectureStdnt(rcept);
					}
				}
			}
			
			
			int stdntCnt = eduMapper.selectConfirmRceptCnt(eduSeq);//지금까지 선발인원
			int remainCnt = limitCnt-stdntCnt;//남은 정원
			
			//대기건 선발
			for(LectureRcept rcept : lrList){
				if(rcept.getState() == 1){
					vo.setUserId(rcept.getUserId());
					if(eduMapper.selectLctreStdntByPk(vo)==null){
						//rcept.setSelectnYn(LectureRcept.STR_Y);
						//eduMapper.updateLectureRceptSelectn(rcept);
						setLectureStdnt(rcept);
						remainCnt--;
					}
				}
				if(remainCnt==0){
					break;
				}
			}
			
			//강제마감처리
			lctre.setRceptYn("N");
			eduMapper.updateLctreRceptClose(lctre);
			
			return "";
			
		} catch (NullPointerException e) {
			return "오류";
		}
	}
	
	@Override
	public ResultVO setLectureStdnt(LectureRcept rcept){
		ResultVO result = new ResultVO();
		try {
			int state = 1;
			if(rcept.getState()==2){
				state = 2;
			}
			int rceptSeq = rcept.getRceptSeq();
			LectureStdnt stdnt = new LectureStdnt(
					rcept.getEduSeq(), rcept.getUserId(), rcept.getUserNm(), rcept.getMobile(), rcept.getEmail(), 
					rcept.getBelong(), rcept.getPosition(), LectureStdnt.STR_N, 0, 0, LectureStdnt.STR_N, 
					LectureStdnt.STR_N, "", LectureStdnt.STR_N, "", LectureStdnt.STR_N,state,rceptSeq);
			
			/*//내부직원여부 추출하여 설정
			String employYn = "N";
			org.codehaus.jackson.map.ObjectMapper om = new org.codehaus.jackson.map.ObjectMapper();
			try {
				UserComp compVO = om.readValue(LncUtil.unescapeJson(rcept.getCompJson()), new TypeReference<UserComp>() {});
				if("Y".equals(compVO.getEmployYn())){
					employYn = "Y";
				}
			} catch (nullException e1) {
			}
			stdnt.setEmployYn(employYn);*/
			
			LectureStdnt stdnt2 = lectureStdntMapper.selectByPk(stdnt);
			if(stdnt2 == null){
				eduMapper.insertLectureStdnt(stdnt);
			}else{
				lectureStdntMapper.updateByPk(stdnt);
			}
			
			
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			LOG.error(e.getMessage());
			result.setResult(0);
			return result;
		}
	}
	
	/**
	 * <pre>
	 * 관리자에서 일괄 수강 취소 기능
	 * </pre>
	 * @param vo
	 */
	@Override
	public void eduStdntCancel(EduVO vo) {
		
		Integer eduSeq = vo.getEduSeq();
		String userIds[] = vo.getUserIds();
		
		Lecture lctre = eduMapper.selectLctreByPk(eduSeq);
		LectureRcept rcept = null;
		
		if(lctre != null && userIds != null && userIds.length > 0) {
			for(String userId : userIds){
				rcept = eduMapper.selectLctreRceptByPk(new EduVO(eduSeq, userId));
				if(rcept != null) {
					
					//문자발송 취소
					//smsService.removeSms(eduSeq, userId);
					
					eduMapper.deleteLectureStdnt(new EduVO(eduSeq, userId));
					rcept.setState(3);
					eduMapper.updateLectureRceptState(rcept);
					//lctreRceptService.setState(rceptSeq, eduSeq, userId, 3);
				}
			}
		}
	}

	/**
	 * 수강생 성적 수정
	 * @param stdnt
	 */
	@Override
	public void updScore(LectureStdnt stdnt) {
		if(stdnt.getAtendYn() == null) {
			stdnt.setAtendYn(LectureStdnt.STR_N);
		}
		
		if(stdnt.getWorkshopScore() == 0){
			stdnt.setWorkshopScore(0);
		}else{
			if(stdnt.getWorkshopScore() > 100){
				stdnt.setWorkshopScore(100);
			}
		}
		
		if(stdnt.getExamScore() == 0){
			stdnt.setExamScore(0);
		}else{
			if(stdnt.getExamScore() > 100){
				stdnt.setExamScore(100);
			}
		}
		eduMapper.updateLectureStdntScore(stdnt);
	}

	/**
	 * 교육종료
	 * @param vo
	 * @return
	 */
	@Override
	public String eduClose(EduVO vo) {
		
		String result = "오류가 발생하였습니다.";
		Integer eduSeq = vo.getEduSeq();
		
		String passYn = "";
		
		int passYCnt = 0;
		int passNCnt = 0;
		int tutfee = 0;
		
		String passCertNum = "";
		String complCertNum = "";
		if(eduSeq != null){
			Lecture lctre = eduMapper.selectLctreByPk(eduSeq);
			
			//카테고리 정보
			CategoryVO cateVO2 = categoryMapper.selectCategoryByPk(lctre.getCtg1Seq());
			String passCd = cateVO2.getPassCd();
			
			if(lctre != null){
				List<LectureStdnt> stdntList = eduMapper.selectLectureStdntListByEdu(eduSeq);
				if(stdntList != null && !stdntList.isEmpty()){
					for(LectureStdnt stdnt : stdntList){
						passYn = stdnt.getPassYn();
						
						if(LectureStdnt.STR_Y.equals(passYn)){
							passYCnt++;
						}else{
							passNCnt++;
						}
						if(LectureStdnt.STR_Y.equals(passYn)){
							if(ObjectUtils.isEmpty(stdnt.getPassCertNum())){
								setPass(stdnt);
							}
							
							//수료관련 sms날림
							//if(lctre.getSmsAuto()==1&&stdnt.getPassYn().equals("Y")){
							//	UserInfo user = memberMapper.selectUserInfoByPk(stdnt.getUserId());
							//	smsService.setSendSms(null,user.getMobile(),smsService.getMsg(1, new String[]{lctre.getEduNm()}),null,eduSeq,stdnt.getUserId(),1);
							//}
						}
						//complCertNum = this.getCertNum(lctre, passYCnt + passNCnt, 2);
						
					}
				
				}
				
				lctre.setCloseYn("Y");
				eduMapper.updateLctreEduClose(lctre);
				result = "교육 종료 처리되었습니다.<br/>합격 : <span class=\"fc_blue\">" + passYCnt + "</span>명 / 불합격 : " + passNCnt + "명";
			}
		}
		
		return result;
	}

	/**
	 * 교육종료 취소
	 * @param vo
	 * @return
	 */
	@Override
	public String eduCloseCancel(EduVO vo) {
		
		String result = "오류가 발생하였습니다.";
		Integer eduSeq = vo.getEduSeq();
		
		if(eduSeq != null){
			Lecture lctre = eduMapper.selectLctreByPk(eduSeq);
			if(lctre != null){
				if(lctre.getLctreType()==3){
					return "동영상 전용은 취소 할수 없습니다.";
				}
				List<LectureStdnt> stdntList = eduMapper.selectLectureStdntListByEdu(eduSeq);
				if(stdntList != null && !stdntList.isEmpty()){
					for(LectureStdnt stdnt : stdntList){
						
						stdnt.setPassYn(LectureStdnt.STR_N);
						stdnt.setPassCertNum("");
						stdnt.setComplCertNum("");
						
						eduMapper.updateLectureStdntCertNum(stdnt);	
					}
				}
				
				lctre.setCloseYn("N");
				eduMapper.updateLctreEduClose(lctre);
				//tuitionFeeService.deleteTutfee(eduSeq);
			}
		}
		
		return result;
	}

	/**
	 * 회원 이수내역을 조회한다.
	 * @param vo
	 * @return
	 */
	@Override
	public Map<String, Object> getEduMemberList(EduVO vo) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		int totalCnt = eduMapper.selectEduMemberListCnt(vo);
		
		PaginationInfo paginationInfo = new PaginationInfo();
		
		if(vo.getRow() == null){
			vo.setRow(DEFAULT_ROW);
		}
		paginationInfo.setTotalRecordCount(totalCnt);
        paginationInfo.setCurrentPageNo(vo.getPage());
        paginationInfo.setRecordCountPerPage(vo.getRow());
        paginationInfo.setPageSize(DEFAULT_PAGE_SIZE);
        
        vo.setFirstIndex(paginationInfo.getFirstRecordIndex());
        
        List<LectureStdnt> dataList = eduMapper.selectEduMemberList(vo);
        
        resultMap.put("totalCnt", totalCnt);
        resultMap.put("paginationInfo", paginationInfo);
        resultMap.put("dataList", dataList);
        
        return resultMap;
	}

	/**
	 * 마이페이지 교육수강 상세내용을 조회한다.
	 * @param vo
	 * @return
	 */
	@Override
	public LectureStdnt getMyAtnlcDetail(int eduSeq,String userId) {
		EduVO vo = new EduVO(eduSeq, userId);
		return eduMapper.selectLctreStdntByPk(vo);
	}

	/**
	 * 교육신청 미니리스트를 조회한다.
	 * @param row
	 * @return
	 */
	@Override
	public List<LectureRcept> getRceptMiniList(String orgCd,int row) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("row", row);
		param.put("orgCd", orgCd);
		return eduMapper.selectRceptMiniList(param);
	}

	/**
	 * 증빙서류에 표시될 정보를 조회한다.
	 * @param vo
	 * @return
	 */
	@Override
	public Map<String, Object> getCertInfo(int eduSeq, int subSeq, String userId) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		if(eduSeq > 0){
			EduVO vo = new EduVO();
			vo.setEduSeq(eduSeq);
			vo.setUserId(userId);
			
			Lecture lctre = eduMapper.selectLctreByPk(eduSeq);
			LectureStdnt stdnt = eduMapper.selectLctreStdntByPk(vo);
			UserInfo user = memberMapper.selectStdntInfoByPk(userId);
			
			int rceptSeq = stdnt.getRceptSeq();
			LectureRcept rcept = lectureRceptMapper.selectByPk(rceptSeq);
			
			//교육시간 계산하기
			String classTime = "";
			LectureTime ltParam = new LectureTime();
			ltParam.setEduSeq(eduSeq);
			List<LectureTime> ltList = lectureTimeMapper.selectByEduSeq(ltParam);
			try {
				long allTimeMillis = 0;
				for(LectureTime o : ltList){
					SimpleDateFormat format = new SimpleDateFormat("HHmm",Locale.KOREA);
					Date date1 = format.parse(o.getStartTm());
				    Date date2 = format.parse(o.getEndTm());
				    long timeDifferenceMillis = date2.getTime() - date1.getTime();
				    if(subSeq > 0){
				    	if(subSeq == o.getSubSeq()){
				    		allTimeMillis+=timeDifferenceMillis;
				    	}
				    }else{
				    	allTimeMillis+=timeDifferenceMillis;
				    }
				}
				classTime = DateUtil.calculateStrTm(allTimeMillis);
			} catch (ParseException e) {
				classTime = "ERROR";
			}
			
			resultMap.put("lctre", lctre);
			resultMap.put("user", user);
			resultMap.put("stdnt", stdnt);
			resultMap.put("classTime", classTime);
			
			//과목수료증인경우
			if(subSeq > 0){
				LectureSubjectStdnt vo2 = new LectureSubjectStdnt();
				vo2.setEduSeq(eduSeq);
				vo2.setSubSeq(subSeq);
				vo2.setUserId(userId);
				LectureSubjectStdnt sub = lectureSubjectStdntMapper.selectByPk(vo2);
				resultMap.put("sub", sub);
				
				//시험일조회
				QuizTestVO qtVO = new QuizTestVO();
				qtVO.setEduSeq(eduSeq);
				qtVO.setUserId(userId);
				List<QuizTestVO> testResult = quizTestMapper.selectTestResultStdnt(qtVO);
				for(QuizTestVO o: testResult){
					if(o.getSubSeq() == subSeq){
						if("Y".equals(o.getPassYn())){
							resultMap.put("examDate", o.getStartDe());
						}
					}
				}
				
				//해당과목 시작일 종료일 계산
				try {
					long allTimeMillis = 0;
					String subStartDt = "";
					String subEndDt = "";
					Collections.sort(ltList, (o1, o2) -> o1.getEduDt().compareTo(o2.getEduDt()));
					for(LectureTime o : ltList){
						if(o.getSubSeq() == subSeq){
							if(ObjectUtils.isEmpty(subStartDt)){
								subStartDt = o.getEduDt();
							}
							subEndDt = o.getEduDt();
							
							//총시간 계산
							SimpleDateFormat format = new SimpleDateFormat("HHmm",Locale.KOREA);
							Date date1 = format.parse(o.getStartTm());
						    Date date2 = format.parse(o.getEndTm());
						    long timeDifferenceMillis = date2.getTime() - date1.getTime();
						    if(subSeq > 0){
						    	if(subSeq == o.getSubSeq()){
						    		allTimeMillis+=timeDifferenceMillis;
						    	}
						    }else{
						    	allTimeMillis+=timeDifferenceMillis;
						    }
						}
					}
					String subClassTime = DateUtil.calculateStrTm(allTimeMillis);
					resultMap.put("subStartDt", subStartDt);
					resultMap.put("subEndDt", subEndDt);
					resultMap.put("subClassTime", subClassTime);
					
				} catch (NullPointerException | ParseException e) {
					resultMap.put("subClassTime", "에러");
				}
				
			}else{
				//미취업자 과목목록 조회
				List<LectureSubject> subList = lectureSubjectMapper.selectLctreTimeSubList(eduSeq);
				
				try {
					List<LcsubVO> subList2 = new ArrayList<LcsubVO>();
					for(LectureSubject o2 : subList){
						String allTimeStr = "";
						LcsubVO lcsub2 = new LcsubVO();
						lcsub2.setSubNm(o2.getSubNm());
						long allTimeMillis = 0;
						for(LectureTime o : ltList){
							if(o2.getSubSeq() != o.getSubSeq()){
								continue;
							}
							SimpleDateFormat format = new SimpleDateFormat("HHmm",Locale.KOREA);
							Date date1 = format.parse(o.getStartTm());
							Date date2 = format.parse(o.getEndTm());
							long timeDifferenceMillis = date2.getTime() - date1.getTime();
							if(subSeq > 0){
								if(subSeq == o.getSubSeq()){
									allTimeMillis+=timeDifferenceMillis;
								}
							}else{
								allTimeMillis+=timeDifferenceMillis;
							}
						}
						allTimeStr = DateUtil.calculateStrTm(allTimeMillis);
						lcsub2.setClassTmNm(allTimeStr);
						subList2.add(lcsub2);
					}
					resultMap.put("subList", subList2);
				} catch (ParseException e) {
					classTime = "ERROR";
				}
			}
			
			
		}
		
		return resultMap;
	}


	/**
	 * 수강생목록 엑셀파일을 생성한다.
	 * @param vo
	 * @return
	 */
	@Override
	public String createStdntExcel(EduVO vo) {
		List<LectureStdnt> dataList = eduMapper.selectStdntExcelList(vo.getEduSeq()); 
		for(LectureStdnt o:dataList){
			LectureTimeStdnt p = new LectureTimeStdnt();
			p.setEduSeq(o.getEduSeq());
			p.setUserId(o.getUserId());
			List<LectureTimeStdnt> list2 = lectureTimeStdntMapper.selectByEduSeqUserId(p);
			o.setLtsInfo(list2);
		}
		
		String filePath = EXCEL_PATH + "stdnt_" + System.currentTimeMillis() + ".xls";
		
		File dir = new File(EXCEL_PATH);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		
		File file = new File(filePath);
		WritableWorkbook workbook;
		
		try {
			workbook = Workbook.createWorkbook(file);
			WritableSheet sheet = workbook.createSheet("수강생 목록", 0);
			final WritableCellFormat FORMAT_TITLE = new WritableCellFormat();
			final WritableCellFormat FORMAT_DATA = new WritableCellFormat();
			
			FORMAT_TITLE.setFont( new WritableFont( WritableFont.ARIAL, 11, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE) );
			FORMAT_TITLE.setAlignment( Alignment.CENTRE );
			FORMAT_TITLE.setVerticalAlignment( VerticalAlignment.CENTRE );
			FORMAT_TITLE.setBackground(Colour.GREY_25_PERCENT);
			
			FORMAT_DATA.setFont( new WritableFont( WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE) );
			FORMAT_DATA.setAlignment( Alignment.CENTRE );
			FORMAT_DATA.setVerticalAlignment( VerticalAlignment.CENTRE );
			
			Label label;
			
			String[] header = {"성명", "연락처", "소속", "진도율", "워크샵", "성적", "합격여부"};
			
			for(int i = 0 ; i < header.length; i++){
				label = new Label(i, 0, header[i], FORMAT_TITLE);
				sheet.addCell(label);
			}
			
			sheet.setColumnView(0, 14);
			sheet.setColumnView(1, 16);
			sheet.setColumnView(2, 32);
			sheet.setColumnView(3, 26);
			sheet.setColumnView(4, 16);
			sheet.setColumnView(5, 16);
			sheet.setColumnView(6, 12);
			
			String atend = "";
			String passYn = "";
			
			for(int i = 0; i < dataList.size(); i++){
				LectureStdnt stdnt = dataList.get(i);
				
				passYn = LectureStdnt.STR_Y.equals(stdnt.getPassYn())? "O" : "X";
				//atend = LectureStdnt.STR_Y.equals(stdnt.getAtendYn())? "출석" : "결석"; 
				atend = stdnt.getAddMovRatio();
				
				label = new Label(0, (i+1), stdnt.getUserNm(), FORMAT_DATA);
				sheet.addCell(label);
				label = new Label(1, (i+1), stdnt.getMobile(), FORMAT_DATA);
				sheet.addCell(label);
				label = new Label(2, (i+1), stdnt.getBelong(), FORMAT_DATA);
				sheet.addCell(label);
				label = new Label(3, (i+1), atend, FORMAT_DATA);
				sheet.addCell(label);
				label = new Label(4, (i+1), String.valueOf(stdnt.getWorkshopScore()), FORMAT_DATA);
				sheet.addCell(label);
				label = new Label(5, (i+1), String.valueOf(stdnt.getExamScore()), FORMAT_DATA);
				sheet.addCell(label);
				label = new Label(6, (i+1), passYn, FORMAT_DATA);
				sheet.addCell(label);
			}
	
			workbook.write();
			workbook.close();
			
		} catch (IOException e) {
			LOG.error( e.getClass().getName() + " ::: " + e.getMessage() );
		} catch (WriteException e) {
			LOG.error( e.getClass().getName() + " ::: " + e.getMessage() );
		}
		
		return filePath;
	}

	@Override
	public Map<String, Object> getLectureInstrctrList(EduVO vo) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		int totalCnt = eduMapper.selectLectureInstrctrListCnt(vo);
		
		PaginationInfo paginationInfo = new PaginationInfo();
		
		if(vo.getRow() == null){
			vo.setRow(MYPAGE_LIST_ROW);
		}
		paginationInfo.setTotalRecordCount(totalCnt);
        paginationInfo.setCurrentPageNo(vo.getPage());
        paginationInfo.setRecordCountPerPage(vo.getRow());
        paginationInfo.setPageSize(DEFAULT_PAGE_SIZE);
        
        vo.setFirstIndex(paginationInfo.getFirstRecordIndex());
        
        List<Lecture> dataList = eduMapper.selectLectureInstrctrList(vo);
        for(Lecture dataMap:dataList){
        	//상태여부
        	dataMap.setCheckRcept(utcp.checkRcept(dataMap.getEduSeq()).getResult());
        }
        
        resultMap.put("totalCnt", totalCnt);
        resultMap.put("paginationInfo", paginationInfo);
        resultMap.put("dataList", dataList);
        
        return resultMap;
	}

	
	/**
	 * 학생 교육신청 > 신청서 첨부
	 * @param request
	 * @return
	 */
	private Map<String, String> saveRceptFile(int eduSeq,String userId,MultipartHttpServletRequest request) {
		Map<String, String> resultMap = new HashMap<String, String>();
		String isSuccess = "E";
		if(eduSeq<1 || StringUtils.isEmpty(userId)){
			resultMap.put("result", "F");
			return resultMap;
		}
		try {
			Iterator<?> itr =  request.getFileNames();
			File file = null;
			String fileRename = "";
			String orgNm = "";
			String fileType = "";
			//String filePath = EDU_RCEPT_PATH+eduSeq+"/";
			//File dir = new File(filePath);
			//if (!dir.exists()) {
			//	dir.mkdirs();
			//}
			
			if(itr.hasNext()) {
				List<MultipartFile> list = request.getFiles((String) itr.next());
				for(MultipartFile multipartFile : list) {
					orgNm = multipartFile.getOriginalFilename();
					
					orgNm = new String(orgNm.getBytes("8859_1"),"utf-8");
					
					fileType = orgNm.substring(orgNm.lastIndexOf(".") + 1, orgNm.length());
					fileRename = userId + "." + fileType;
					
					//클라우드 사용할때
					//int fileResult = NaverObjectStorage.multiPartupload(multipartFile, "upload/eduReq/"+eduSeq+"/", orgNm, fileRename);
					ResultVO result = FileUtil.multiPartupload(multipartFile, "upload/lctreRcept/"+eduSeq+"/", orgNm, fileRename);
					if(result.getResult()==0){
						resultMap.put("result", "F");
						return resultMap;
					}
					/*클라우드 사용안할 때,211201
					file = new File(filePath + fileRename);
					try {
						multipartFile.transferTo(file);
					} catch (IllegalStateException e) {
						LOG.error( e.getClass().getName() + " ::: " + e.getMessage() );
						isSuccess = "F";
					} catch (IOException e) {
						LOG.error( e.getClass().getName() + " ::: " + e.getMessage() );
						isSuccess = "F";
					}
					*/	
				}
				isSuccess = "S";
			}
			
			if("S".equals(isSuccess)){
				resultMap.put("fileOrg", orgNm);
				resultMap.put("fileRe", fileRename);
			}
		} catch (NullPointerException | UnsupportedEncodingException e) {
			isSuccess = "F";
		}
		
		resultMap.put("result", isSuccess);
		return resultMap;
	}

	@Override
	public String saveLectureTime(int eduSeq,String checkYn, List<LectureTime> timeList) {
		try {
			/*권한체크 주석처리,2`2
			Org orgInfo = tableMapper.selectOrgByPk(SessionUserInfoHelper.getUserInfo().getOrgCd());
			if(checkYn.equals("Y")){
				if(orgInfo.getUseCheck()==0){
					//권한체크
					//return "전자출결을 사용하시려면 관리자에게 문의하세요";
					checkYn="N";//자동으로 미사용으로 설정하여 저장한다.
				}
			}
			for(LectureTime ltInfo:timeList){
				if(orgInfo.getUseGooroomee()==0){
					if(ltInfo.getClassHow()==2){
						return "화상수업을 사용하시려면 관리자에게 문의하세요";
					}
				}
				if(orgInfo.getUseMov()==0){
					if(ltInfo.getClassHow()==3){
						return "동영상수업을 사용하시려면 관리자에게 문의하세요";
					}
				}
			}
			*/
			//전자출결 사용여부 저장
			Lecture lectureParam = new Lecture();
			lectureParam.setEduSeq(eduSeq);
			lectureParam.setCheckYn(checkYn);
			tableMapper.updateLectureForCheckYn(lectureParam);
			
			int timeSeq=0;
			for(LectureTime ltInfo:timeList){
				timeSeq++;
				
				//동영상이 교체되었는지 체크하여 진도율테이블에 저장
				/* 동영상 교체되도 진도율에 영향 안가도록 주석처리함,220518
				if(ltInfo.getClassHow()==3){
					LectureTime ltInfo2 = lectureTimeMapper.selectByPk(new LectureTime(eduSeq, timeSeq));
					if(ltInfo2!=null){
						if(!ltInfo.getUrl().equals(ltInfo2.getUrl())){
							lectureTimeStdntMapper.updateByEduSeqTimeSeq(new LectureTimeStdnt(eduSeq, timeSeq,null, "0", "0",1));
						}
					}
				}
				*/
				
			}
			
			//시간 삭제
			tableMapper.deleteLectureTimeByEduSeq(eduSeq);
			timeSeq=0;
			for(LectureTime ltInfo:timeList){
				timeSeq++;
		
				ltInfo.setEduSeq(eduSeq);
				ltInfo.setSubSeq(0);
				ltInfo.setTimeSeq(timeSeq);
				tableMapper.insertLectureTime(ltInfo);
			}
			
			return "";
		} catch (NullPointerException e) {
			return "error";
		}
	}

	@Override
	public List<LectureTime> getLectureTimeList(int eduSeq) {
		LectureTime vo = new LectureTime();
		vo.setEduSeq(eduSeq);
		List<LectureTime> list = lectureTimeMapper.selectByEduSeq(vo);
		return list;
	}

	@Override
	public int loadFeedback(int eduSeq, int fbIdx) {
		try {
			Lecture param = new Lecture();
			param.setEduSeq(eduSeq);
			param.setFbIdx(fbIdx);
			eduMapper.updateLectureFbIdx(param);
			return 1;
			
		} catch (NullPointerException e) {
			return 0;
		}
	}

	@Override
	public int deleteEduFeedback(int eduSeq) {
		try {
			Lecture param = new Lecture();
			param.setEduSeq(eduSeq);
			param.setFbIdx(0);
			eduMapper.updateLectureFbIdx(param);
			return 1;
		} catch (NullPointerException e) {
			return 0;
		}
	}

	@Override
	public List<TimeTableVO> getTimeTableList(Lecture lctre,String userId) {
		try {
			TimeTableVO param = new TimeTableVO();
			param.setEduSeq(lctre.getEduSeq());
			param.setUserId(userId);
			List<TimeTableVO> timeList = tableMapper.selectTimeTableList(param);
			
			//동영상전용이 아닌경우 정렬 세팅하기
			if(lctre.getLctreType() != 3){//동영상전용이 아닌경우
				Comparator<TimeTableVO> sort = Comparator
			            .comparing(TimeTableVO::getEduDt)
			            .thenComparing(TimeTableVO::getStartTm);
				timeList.sort(sort);
				
				//교시 세팅하기,나중에 함수화 하자
				String currentEduDt = "";
				int gyosi = 1;
				for (TimeTableVO timeTable : timeList) {
					if (!timeTable.getEduDt().equals(currentEduDt)) {
						currentEduDt = timeTable.getEduDt();
						gyosi = 1; // 새로운 날짜면 교시 번호를 초기화
					}
					timeTable.setGyosi(gyosi++);
				}
				
				if(lctre.getLctreType() == 0){
					for (TimeTableVO o : timeList) {
						if(o.getGyosi() == 1){
							for (TimeTableVO o2 : timeList) {
								if(o.getEduDt().equals(o2.getEduDt())){
									o.setRowCnt(o2.getGyosi());
								}
							}
						}
					}
				}
			}
			
			return timeList;
		} catch (NullPointerException e) {
			return null;
		}
	}

	@Override
	public Map<String, Object> getMyEduList(EduVO vo) {
		vo.setSrchDate(DateUtil.getDate2Str(new Date(), "yyyy-MM-dd"));
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		int totalCnt = eduMapper.selectMyEduCnt(vo);
		
		PaginationInfo paginationInfo = new PaginationInfo();
		
		if(vo.getRow() == null){
			vo.setRow(MYPAGE_LIST_ROW);
		}
		paginationInfo.setTotalRecordCount(totalCnt);
        paginationInfo.setCurrentPageNo(vo.getPage());
        paginationInfo.setRecordCountPerPage(vo.getRow());
        paginationInfo.setPageSize(DEFAULT_PAGE_SIZE);
        
        vo.setFirstIndex(paginationInfo.getFirstRecordIndex());
        
        List<Lecture> dataList = eduMapper.selectMyEduList(vo);
        for(Lecture dataMap:dataList){
        	//상태여부
        	dataMap.setCheckRcept(utcp.checkRcept(dataMap.getEduSeq()).getResult());
        	
        	//수료여부
        	LectureStdnt lsParam = new LectureStdnt();
        	lsParam.setEduSeq(dataMap.getEduSeq());
        	lsParam.setUserId(SessionUserInfoHelper.getUserId());
        	LectureStdnt lsInfo = lectureStdntMapper.selectByPk(lsParam);
        	if(lsInfo!=null){
        		
        		int passResult = utcp.checkPass(dataMap.getCloseYn(),lsInfo.getPassYn(),dataMap.getLctreType(),dataMap.getEduPeriodEnd(),lsInfo.getState());
        		dataMap.setCheckPass(passResult);
        		
        	}
        	
        }
        resultMap.put("totalCnt", totalCnt);
        resultMap.put("paginationInfo", paginationInfo);
        resultMap.put("dataList", dataList);
        
        return resultMap;
	}

	@Override
	public int setCheckPassByMov(int eduSeq, String userId) {
		try {
			SmartCheckVO param = new SmartCheckVO();
			param.setEduSeq(eduSeq);
			param.setReqStudId(userId);
			List<SmartCheckVO> attList = smartCheckMapper.getStdntAttList(param);
			for(SmartCheckVO o:attList){
				if(!o.getAttCd().equals("O")){
					return 2;//미수료
				}
			}
			
			//수료저장
			Lecture lctre = eduMapper.selectLctreByPk(eduSeq);

			EduVO eduparam = new EduVO();
			eduparam.setEduSeq(eduSeq);
			eduparam.setUserId(userId);
			LectureStdnt stdnt = eduMapper.selectLctreStdntByPk(eduparam);
			if(stdnt!=null){
				if(!ObjectUtils.isEmpty(stdnt.getPassCertNum()) && stdnt.getPassYn().equals("Y")){
					return 0;
				}
			}
			LectureStdnt param2 = new LectureStdnt();
			param2.setEduSeq(eduSeq);
			param2.setUserId(userId);
			param2.setPassYn("Y");
			param2.setCertDe(new Date());
			eduMapper.updateLectureStdntScore(param2);//수료날짜저장
			
			
			//내누적번호 가져오기
			String year = lctre.getEduYear();
			int ctgrySeq = lctre.getCtg1Seq();
			String passCertNum = this.getCertNum(1, year,ctgrySeq,0);
			param2.setPassCertNum(passCertNum);
			param2.setComplCertNum("");
			param2.setCertDe(new Date());
			eduMapper.updateLectureStdntCertNum(param2);
			
			//sms발송
			//수료관련 sms날림
			//if(lctre.getSmsAuto()==1&&stdnt.getPassYn().equals("Y")){
				//UserInfo user = memberMapper.selectUserInfoByPk(userId);
				//smsService.setSendSms(null,user.getMobile(),smsService.getMsg(1, new String[]{lctre.getEduNm()}),null,eduSeq,userId,1);
			//}
			
			return 1;
		} catch (NullPointerException e) {
			return 0;
		}
	}

	@Override
	public int deleteRcept(Integer eduSeq) {
		try {
			EduVO vo = new EduVO();
			vo.setEduSeq(eduSeq);
			vo.setUserId(SessionUserInfoHelper.getUserId());
			eduMapper.deleteLectureStdnt(vo);
			eduMapper.deleteLectureRcept(vo);
			return 1;
		} catch (NullPointerException e) {
			return 0;
		}
	}
	//과정관리 -> 수료여부 저장시
	@Override
	public void setPass(LectureStdnt lsparam) {
		try {
			Lecture lctre = eduMapper.selectLctreByPk(lsparam.getEduSeq());
			
			LectureStdnt stdnt = eduMapper.selectLctreStdntByPk(new EduVO(lsparam.getEduSeq(),lsparam.getUserId()));
			
			if(!ObjectUtils.isEmpty(stdnt.getPassCertNum())){
				return;
			}
			
			if(lctre.getPassIdx() == 0){
				return;
			}
			
			if(lsparam.getPassYn().equals("Y")){
				//내누적번호 가져오기
				int ctgrySeq = lctre.getCtg1Seq();
				int subSeq = 0;
				String year = lctre.getEduYear();
				if(ObjectUtils.isEmpty(year)){
					year = lctre.getEduPeriodBegin().substring(0, 4);
				}
				
				String passCertNum = this.getCertNum(1,year,ctgrySeq,subSeq);
				lsparam.setPassCertNum(passCertNum);
				lsparam.setCertDe(new Date());
				eduMapper.updateLectureStdntCertNum(lsparam);
			}
		} catch (NullPointerException e) {
			LOG.error("합격 처리중 에러");
		}
		
	}

	@Override
	public ResultVO saveLctreTime(int eduSeq,int copyEduSeq) { 
		ResultVO result = new ResultVO();
		try {
			
			//copyEduSeq에 시간표 목록 가져오기
			List<LectureTime> timeList = tableMapper.selectLectureTimeList(copyEduSeq);
			
			//eduSeq시간표 테이블에 저장
			int timeSeq=0;
			for(LectureTime ltInfo:timeList){
				timeSeq++;
				ltInfo.setEduSeq(eduSeq);
				ltInfo.setTimeSeq(timeSeq);
				tableMapper.insertLectureTime(ltInfo);
			}
			result.setResult(1);
		} catch (NullPointerException e) {
			result.setResult(0);
		}
		return result;
	}

	@Override
	public ResultVO rceptCloseOnly(EduVO vo) {
		ResultVO result = new ResultVO();
		try {
			//강제마감처리
			Integer eduSeq = vo.getEduSeq();
			Lecture lctre = eduMapper.selectLctreByPk(eduSeq);
			lctre.setRceptYn("N");
			eduMapper.updateLctreRceptClose(lctre);
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setResult(0);
			return result;
		}
	}
	@Override
	public ResultVO rceptOpenOnly(EduVO vo) {
		ResultVO result = new ResultVO();
		try {
			//강제마감처리
			Integer eduSeq = vo.getEduSeq();
			Lecture lctre = eduMapper.selectLctreByPk(eduSeq);
			lctre.setRceptYn("Y");
			eduMapper.updateLctreRceptClose(lctre);
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setResult(0);
			return result;
		}
	}

	@Override
	public List<Lecture> getResvLectureRoom(int roomSeq,String srchYear,String srchMonth) {
		EduVO vo = new EduVO();
		vo.setRoomSeq(roomSeq);
		vo.setSrchYear(srchYear);
		vo.setSrchMonth(srchMonth);
		List<Lecture> list = eduMapper.getResvLectureTimeByRoom(vo);
		return list;
	}

	@Override
	public ResultVO getClassRoomInfoByEduDt(String eduDt) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			//
			EduVO vo = new EduVO();
			vo.setEduDt(eduDt);
			List<Lecture> list = eduMapper.getResvLectureTimeByRoom(vo);
			rstData.put("list", list);
			
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setResult(0);
			return result;
		}
	}

	@Override
	public ResultVO delRceptUser(int eduSeq, String userId,int rceptSeq) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			//검증
			LectureStdnt ltsParam = new LectureStdnt();
			ltsParam.setEduSeq(eduSeq);
			ltsParam.setUserId(userId);
			LectureStdnt stdnt = lectureStdntMapper.selectByPk(ltsParam);
			if(stdnt!=null){
				result.setMsg("이미 수강신청된 학생입니다. 수강취소 후 삭제 가능합니다.");
				result.setResult(0);
				return result;
			}
			
			EduVO vo = new EduVO();
			vo.setEduSeq(eduSeq);
			vo.setUserId(userId);
			vo.setRceptSeq(rceptSeq);
			LectureRcept rcept = eduMapper.selectLctreRceptByPk(vo);
			
			//온라인결제는 삭제 안됨
			if(rcept!=null){
				if(rcept.getPayState() == 1){
					result.setMsg("온라인결제완료 상태입니다. 삭제할 수 없습니다.");
					result.setResult(0);
					return result;
				}
			}
			
			//삭제처리
			EduVO eduVO = new EduVO();
			eduVO.setEduSeq(eduSeq);
			eduVO.setUserId(userId);
			eduMapper.deleteLectureRcept(eduVO);			
			
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setResult(0);
			result.setMsg("ERROR");
			return result;
		}
	}

	@Override
	public ResultVO setInstrctr(int eduSeq, String instrctrId) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			if(eduSeq<1 || StringUtils.isEmpty(instrctrId)){
				result.setResult(0);
				result.setMsg("ERROR");
				return result;
			}
			
			UserInfoInstrctr instrctr = memberMapper.selectInstrctrByPk(instrctrId);
			if(instrctr==null){
				result.setResult(0);
				result.setMsg("ERROR");
				return result;
			}
			
			Lecture ltParam = new Lecture();
			ltParam.setEduSeq(eduSeq);
			ltParam.setInstrctrId(instrctrId);
			ltParam.setInstrctrNm(instrctr.getUserNm());
			eduMapper.updateLectureInstrctr(ltParam);
			
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setResult(0);
			result.setMsg("ERROR");
			return result;
		}
	}

	@Override
	public ResultVO getLcrcpPageList(EduVO vo, int listRow) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			int eduSeq = vo.getEduSeq();
			vo.setRow(listRow);
			
			//총 갯수
			int totalCnt = eduMapper.selectLcrcpPageCnt(vo);
			
			//페이징처리
			PaginationInfo paginationInfo = new PaginationInfo();
			paginationInfo.setTotalRecordCount(totalCnt);
	        paginationInfo.setCurrentPageNo(vo.getPage());
	        paginationInfo.setRecordCountPerPage(vo.getRow());
	        paginationInfo.setPageSize(DEFAULT_PAGE_SIZE);
	        vo.setFirstIndex(paginationInfo.getFirstRecordIndex());
			
			//신청명단
			List<Lecture> lcrcpList = eduMapper.selectLcrcpPageList(vo);
			rstData.put("lcrcpList", lcrcpList);
			
			//기타항목 조회
			setEtcIemDataList(lcrcpList);
			
			if(eduSeq > 0){
				int confirmCnt = eduMapper.selectConfirmRceptCnt(vo.getEduSeq());
				int waitCnt = eduMapper.selectWaitRceptCnt(vo.getEduSeq());
				rstData.put("confirmCnt", confirmCnt);
				rstData.put("waitCnt", waitCnt);
			}
			rstData.put("pageNavi",paginationInfo);
			
			
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setMsg("error");
			result.setResult(0);
			return result;
		}
	}

	
	@Override
	public ResultVO setEtcIemDataList(List<Lecture> lcrcpList) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			
			//기타항목 조회
			List<Map<String, Object>> srchArr = new ArrayList<Map<String, Object>>();
			for(Lecture o : lcrcpList){
				Map<String, Object> cnd = new HashMap<String,Object>();
				cnd.put("eduSeq", o.getEduSeq());
				cnd.put("userId", o.getUserId());
				srchArr.add(cnd);
			}
			EduVO vo = new EduVO();
			vo.setSrchArr(srchArr);
			
			List<Lecture> etcList = eduMapper.selectEtcIemDataList(vo);
			for(Lecture o : lcrcpList){
				for(Lecture o2 : etcList){
					if(o.getEduSeq()==o2.getEduSeq() && o.getUserId().equals(o2.getUserId())){
						o.setEtcData01(o2.getEtcData01());
						break;
					}
				}
			}
			
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setMsg("error");
			result.setResult(0);
			return result;
		}
	}

	@Override
	public ResultVO getLcrcpInfo(int eduSeq, String userId, int rceptSeq) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			
			if(userId == null){
				result.setResult(0);
				return result;
			}
			
			//교육정보
			Lecture lctre = eduMapper.selectLctreByPk(eduSeq);
			rstData.put("lctre", lctre);
			
			//신청서 정보
			LectureRcept lcrcp = lectureRceptMapper.selectByPk(rceptSeq);
			rstData.put("lcrcp", lcrcp);
			
			if(lcrcp == null || "C".equals(lcrcp.getState())){
				result.setMsg("취소된 신청서는 확인하실 수 없습니다.");
				result.setResult(0);
				return result;
			}
			
			//신청기타정보
			LectureEtcIemVO leiVO = new LectureEtcIemVO();
			leiVO.setEduSeq(eduSeq);
		    List<LectureEtcIemVO> etcIemList = lectureEtcIemMapper.selectByEduSeq(leiVO);
		    LectureEtcIemDataVO leidVO = new LectureEtcIemDataVO();
		    leidVO.setEduSeq(eduSeq);
		    leidVO.setUserId(userId);
			List<LectureEtcIemDataVO> etcIemDataList = lectureEtcIemDataMapper.selectByRcept(leidVO);
			List<LectureEtcIemVO> etcIemListNew = new ArrayList<LectureEtcIemVO>();
	    	for(LectureEtcIemVO o2 : etcIemList){
	    		LectureEtcIemVO o2New = new LectureEtcIemVO();
	    		BeanUtils.copyProperties(o2, o2New);
	    		etcIemListNew.add(o2New);
	    		for(LectureEtcIemDataVO o3 : etcIemDataList){
	    			if(userId.equals(o3.getUserId()) && o2.getEduSeq()==o3.getEduSeq() && o2.getEtcIemSeq()==o3.getEtcIemSeq()){
	    				o2New.setEtcIemData(o3.getEtcIemData());
	    				break;
	    			}
	    		}
	    	}
	    	Map<String, Object> etcIemMap = new HashMap<String, Object>();
	    	etcIemMap.put("etcIemList", etcIemListNew);
	    	ObjectMapper om = new ObjectMapper();
	    	String etcIemJson = null;
			try {
				etcIemJson = om.writeValueAsString(etcIemMap);
				//tmlct.setEtcIemJson(etcIemJson);
			} catch (JsonProcessingException e) {
				result.setMsg("기타항목 설정 중 에러가 발생하였습니다.");
				result.setResult(0);
				return result;
			}
			rstData.put("etcIemJson", etcIemJson);
			
			//사용자정보
			UserInfo user = memberMapper.selectStdntInfoByPk(userId);
			rstData.put("user", user);
			
			
			//취소기한 체크
			//int isPlay = 1;
			//rstData.put("isPlay",isPlay);
			
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setMsg("error");
			result.setResult(0);
			return result;
		}
	}

	@Transactional
	@Override
	public ResultVO updLctreRceptAfterPay(int rceptSeq, int feeTp, String etc) {
		ResultVO result = new ResultVO();
		
		try {
			String sessId = SessionUserInfoHelper.getUserId();
			LectureRcept rcept = lectureRceptMapper.selectByPk(rceptSeq);
			if(!SessionUserInfoHelper.isAdmin() && !rcept.getUserId().equals(sessId)){
				result.setMsg("오류");result.setResult(0); return result;
			}
			
			//접수 수정
			LectureRcept param = new LectureRcept();
			param.setRceptSeq(rceptSeq);
			param.setFeeTp(feeTp);
			param.setEtc(etc);
			lectureRceptMapper.updateAnyByPk(param);
			
			/*	
			//기타항목 등록
			if("Y".equals(lctre.getEtcIemYn())){
				ResultVO etcIemResult = lectureEtcIemService.saveLectureEtcIemData(eduSeq,userId,etcIemDataJson);
				if(etcIemResult.getResult()!=1){
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					result.setMsg("기타항목 수정중 에러가 발생하였습니다.");
					result.setResult(0);
					return result;
				}
			}
			*/
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			result.setResult(0);
			return result;
		}
	}

	@Override
	public ResultVO updRceptNew(int rceptSeq) {
		ResultVO result = new ResultVO();
		
		try {
			
			LectureRcept paramRcept = new LectureRcept();
			paramRcept.setRceptSeq(rceptSeq);
			paramRcept.setRegDe(new Date());
			lectureRceptMapper.updateByRceptSeq(paramRcept);
			
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setResult(0);
			return result;
		}
	}

	@Override
	public ResultVO addLectureTime(int eduSeq, int timeSeq, int dateTp, String startDtStr, String endDtStr,String eduDtStr, String[] weeks, String[] times) {
		ResultVO result = new ResultVO();
		try {
			if(eduSeq == 0){
				result.setMsg("수업정보가 없습니다.");
				result.setResult(0);
				return result;
			}
			
			String startDtStr2 = startDtStr.replaceAll("-", "");
			String endDtStr2 = endDtStr.replaceAll("-", "");
			String eduDtStr2 = eduDtStr.replaceAll("-", "");
			String eduDt = "";
			
			//최종등록용 시간정보 리스트
			List<LectureTime> finalTimeList = new ArrayList<LectureTime>();
			
			//반복일정인경우
			if(timeSeq == 0 && dateTp == 2){

				if(ObjectUtils.isEmpty(startDtStr2) || ObjectUtils.isEmpty(endDtStr2)){
					result.setMsg("훈련기간이 지정되어 있지 않습니다.");
					result.setResult(0);
					return result;
				}
				
				// 교육일 저장하기,기존 데이터 삭제 필요
				int calDays = DateUtil.calDays(startDtStr2, endDtStr2);
				for(int j = 0; j <= calDays; j++){
					eduDt = DateUtil.dayCalForStr(
							Integer.parseInt(startDtStr2.substring(0, 4)), 
							Integer.parseInt(startDtStr2.substring(4, 6)), 
							Integer.parseInt(startDtStr2.substring(6, 8)), 
							j, 
							"", 
							"yyyyMMdd");
					
					//요일이 존재하면 해당 요일만
					int weekNo = DateUtil.getWeekNoByDate(DateUtil.getStr2Date(eduDt, "yyyyMMdd"));
					String weekNo2 = weekNo+"";
					for(String week : weeks){
						String week2 = week+"";
						if(week2.equals(weekNo2)){
							LOG.debug("eduDt : "+eduDt);
							
							//시간저장
							for(String timeJson: times){
								timeJson = LncUtil.unescapeJson(timeJson);
								ObjectMapper om2 = new ObjectMapper();
								Map<String, String> time = om2.readValue(timeJson, Map.class);
								LOG.debug("time : "+time);
								
								String startTm = time.get("startHour")+time.get("startMin");
								String endTm = time.get("endHour")+time.get("endMin");
								String description = time.get("description");
								int roomSeq = LncUtil.nvlInt(time.get("roomSeq"));
								String ncsNm = time.get("ncsNm");
								String checkId = time.get("checkId");
								String checkNm = time.get("checkNm");
								String instrNm = time.get("instrNm");
								int classHow = LncUtil.nvlInt(time.get("classHow"));
								String url = time.get("url");
								String urlPw = time.get("urlPw");
								int mvIdx = LncUtil.nvlInt(time.get("mvIdx"));
								int subSeq = LncUtil.nvlInt(time.get("subSeq"));
								int status = LncUtil.nvlInt(time.get("status"));
								if(status == 0) {
									status = 1;
								}
								LectureTime ltVO = new LectureTime();
								ltVO.setEduSeq(eduSeq);
								ltVO.setSubSeq(subSeq);
								ltVO.setEduDt(eduDt);
								ltVO.setStartTm(startTm);
								ltVO.setEndTm(endTm);
								ltVO.setDescription(description);
								ltVO.setRoomSeq(roomSeq);
								ltVO.setCheckId(checkId);
								ltVO.setCheckNm(checkNm);
								ltVO.setInstrNm(instrNm);
								ltVO.setNcsNm(ncsNm);
								ltVO.setClassHow(classHow);
								ltVO.setUrl(url);
								ltVO.setUrlPw(urlPw);
								ltVO.setMvIdx(mvIdx);
								ltVO.setStatus(status);
								
								//저장할 시간정보를 리스트에 순차 등록
								finalTimeList.add(ltVO);
							}
						}
					}
				}
			}else if(dateTp == 1){
				eduDt = eduDtStr2;
				LOG.debug("eduDt : "+eduDt);
				
				if(ObjectUtils.isEmpty(eduDt)){
					result.setMsg("훈련일이 지정되어 있지 않습니다.");
					result.setResult(0);
					return result;
				}
				
				//시간저장
				for(String timeJson: times){
					timeJson = LncUtil.unescapeJson(timeJson);
					ObjectMapper om2 = new ObjectMapper();
					Map<String, String> time = om2.readValue(timeJson, Map.class);
					LOG.debug("time : "+time);
					
					String startTm = time.get("startHour")+time.get("startMin");
					String endTm = time.get("endHour")+time.get("endMin");
					String description = time.get("description");
					int roomSeq = LncUtil.nvlInt(time.get("roomSeq"));
					String ncsNm = time.get("ncsNm");
					String checkId = time.get("checkId");
					String instrNm = time.get("instrNm");
					String checkNm = time.get("checkNm");
					int classHow = LncUtil.nvlInt(time.get("classHow"));
					String url = time.get("url");
					String urlPw = time.get("urlPw");
					int mvIdx = LncUtil.nvlInt(time.get("mvIdx"));
					int subSeq = LncUtil.nvlInt(time.get("subSeq"));
					int status = LncUtil.nvlInt(time.get("status"));
					if(status == 0) {
						status = 1;
					}
					LectureTime ltVO = new LectureTime();
					ltVO.setEduSeq(eduSeq);
					ltVO.setSubSeq(subSeq);
					ltVO.setEduDt(eduDt);
					ltVO.setStartTm(startTm);
					ltVO.setEndTm(endTm);
					ltVO.setDescription(description);
					ltVO.setRoomSeq(roomSeq);
					ltVO.setCheckId(checkId);
					ltVO.setCheckNm(checkNm);
					ltVO.setInstrNm(instrNm);
					ltVO.setNcsNm(ncsNm);
					ltVO.setClassHow(classHow);
					ltVO.setUrl(url);
					ltVO.setUrlPw(urlPw);
					ltVO.setMvIdx(mvIdx);
					ltVO.setStatus(status);
					
					if(timeSeq > 0){
						ltVO.setTimeSeq(timeSeq);
					}
					//단건등록도 리스트에 순차등록, 하나만 등록되겠지...
					finalTimeList.add(ltVO);
				}
			}
			
			//최종 등록전 강의실중복체크를 해야함
			ResultVO result2 =  lectureRoomService.checkPossibleRoomByTimeList(finalTimeList);
			if(result2.getResult() != 1){
				return result2;
			}
			
			//최종 DB에 저장
			for(LectureTime o : finalTimeList){
				if(o.getTimeSeq() > 0){
					int result1 = lectureTimeMapper.updateByPk(o);
				}else{
					int result1 = lectureTimeMapper.insertByPk(o);
				}
			}
			
			result.setResult(1);
			return result;
		} catch (NullPointerException | JsonProcessingException e) {
			result.setResult(0);
			return result;
		}
	}

	@Override
	public ResultVO delLectureTime(int eduSeq, int timeSeq) {
		ResultVO result = new ResultVO();
		try{
			LectureTime ltVO = new LectureTime(eduSeq, timeSeq);
			lectureTimeMapper.deleteByPk(ltVO);
			
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setResult(0);
			return result;
		}
	}

	@Override
	public ResultVO getLectureTime(int eduSeq, int timeSeq) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try{
			LectureTime ltVO = new LectureTime(eduSeq, timeSeq);
			LectureTime time = lectureTimeMapper.selectByPk(ltVO);
			rstData.put("time", time);
			
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setResult(0);
			return result;
		}
	}

	@Override
	public ResultVO saveLctreRceptAdd(int rceptSeq,String compJsonp, String schoolListJsonp, String certifListJsonp, String languaListJsonp, String careerListJsonp, MultipartFile bsnLcnsFile
			, int empInsrTp, int learnCardTp, int empSuccTp, int bizRgsTp) {
		ResultVO result = new ResultVO();
		try {
			ObjectMapper om = new ObjectMapper();
			String updId = SessionUserInfoHelper.getUserId();
			Date updDe = new Date();
			
			//재직자 정보 저장
			String compJson = LncUtil.unescapeJson(compJsonp);
			LOG.info("compJson"+compJson);
			
			//학력정보
			String schoolListJson = LncUtil.unescapeJson(schoolListJsonp);
			LOG.info("schoolListJson: "+schoolListJson);
			
			//자격정보
			String certifListJson = LncUtil.unescapeJson(certifListJsonp);
			LOG.info("certifListJson: "+certifListJson);
			
			//어학정보
			String languaListJson = LncUtil.unescapeJson(languaListJsonp);
			LOG.info("languaListJson: "+languaListJson);
			
			//경력정보
			String careerListJson = LncUtil.unescapeJson(careerListJsonp);
			LOG.info("careerListJson: "+careerListJson);
			
			LectureRcept vo = new LectureRcept();
			vo.setRceptSeq(rceptSeq);
			
			vo.setUpdDe(new Date());
			
			//내부직원여부 추출하여 설정
			String employYn = "N";
			org.codehaus.jackson.map.ObjectMapper om2 = new org.codehaus.jackson.map.ObjectMapper();
			try {
				UserComp compVO = om2.readValue(LncUtil.unescapeJson(compJson), new TypeReference<UserComp>() {});
				if("Y".equals(compVO.getEmployYn())){
					employYn = "Y";
				}
			} catch (NullPointerException | IOException e1) {
				employYn = "N";
			}
			vo.setEmployYn(employYn);
			
			lectureRceptMapper.updateByRceptSeq(vo);
			
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setResult(0);
			result.setMsg("알 수 없는 오류가 발생하였습니다. 관리자에게 문의하세요");
			return result;
		}
	}

	@Override
	public ResultVO getStdntEduStat(String userId) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			
			int passCnt = lectureStdntMapper.selectPassUserIdCnt(userId);
			rstData.put("passCnt", passCnt);
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setResult(0);
			result.setMsg("알 수 없는 오류가 발생하였습니다. 관리자에게 문의하세요");
			return result;
		}
	}

	@Override
	public ResultVO getLctreYearInfo(String srchYear, int srchCtgry, int srchCtgry2, int srchCtgry3, String srchWrd, String srchColumn) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			EduVO vo = new EduVO();
			vo.setSrchCtgry(srchCtgry);
			vo.setSrchYear(srchYear);
			vo.setSrchCtgry2(srchCtgry2);
			vo.setSrchCtgry3(srchCtgry3);
			vo.setSrchWrd(srchWrd);
			vo.setSrchColumn(srchColumn);
			List<Lecture> eduList = eduMapper.selectLctreUserList(vo);
			
			//전체일정용 리스트
			List<Map<String, Object>> calList = new ArrayList<Map<String,Object>>();
			
			Date nDate = new Date();
			
			/* 카테고리로 그룹화 하기*/
			CategoryVO cateVO = new CategoryVO();
			cateVO.setSrchCtg1Seq(srchCtgry);
			cateVO.setSrchCtg2Seq(srchCtgry2);
			cateVO.setSrchCtg3Seq(srchCtgry3);
			List<CategoryVO> cateList = categoryMapper.selectCategoryLastList(cateVO);
			for(CategoryVO o: cateList){
				Map<String, Object> calMap = new HashMap<String,Object>();
				List<Lecture> eduList2 = new ArrayList<Lecture>();
				for(Lecture o2: eduList){
					if(o.getCtgrySeq() == o2.getCtg3Seq()){//3차카테고리로 그룹화
						Date begin = DateUtil.getStr2Date(o2.getEduPeriodBegin(), "yyyy-MM-dd");
						if(begin.compareTo(nDate) > 0){
							o2.setCheckRcept(2);
						}
						eduList2.add(o2);	
					}
				}
				calMap.put("ctgInfo", o);
				calMap.put("eduList", eduList2);
				calList.add(calMap);
			}
			
			/*
			//교육명으로 그룹화하기
			List<Lecture> grpList = new ArrayList<Lecture>();
			for(Lecture o: eduList){
				boolean isExist = false;
				for(Lecture o2: grpList){
					if(o.getCtg1Seq()== o2.getCtg1Seq() && o.getCtg2Seq()== o2.getCtg2Seq() && o.getCtg3Seq()== o2.getCtg3Seq() && o.getEduNm().equals(o2.getEduNm())){
						isExist = true;
						break;
					}
				}
				if(!isExist){
					grpList.add(o);
				}
			}
			for(Lecture o: grpList){
				Map<String, Object> calMap = new HashMap<String,Object>();
				List<Lecture> eduList2 = new ArrayList<Lecture>();
				for(Lecture o2: eduList){
					if(o.getCtg1Seq()== o2.getCtg1Seq() && o.getCtg2Seq()== o2.getCtg2Seq() && o.getCtg3Seq()== o2.getCtg3Seq() && o.getEduNm().equals(o2.getEduNm())){
						Date begin = DateUtil.getStr2Date(o2.getEduPeriodBegin(), "yyyy-MM-dd");
						if(begin.compareTo(nDate) > 0){
							o2.setCheckRcept(2);
						}
						eduList2.add(o2);	
					}
				}
				calMap.put("ctgInfo", o);
				calMap.put("eduList", eduList2);
				calList.add(calMap);
			}
			*/
			rstData.put("calList", calList);
			
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setResult(0);
			result.setMsg("알 수 없는 오류가 발생하였습니다. 관리자에게 문의하세요");
			return result;
		}
	}

	@Override
	public ResultVO getLctreSubResult(int eduSeq, String userId) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			//과목목록
			SubjectResultVO srVO = new SubjectResultVO();
			srVO.setEduSeq(eduSeq);
			srVO.setUserId(userId);
			List<SubjectResultVO> subList = eduMapper.getLctreSubList(srVO);
			
			//시험목록
			QuizTestVO qtVO = new QuizTestVO();
			qtVO.setEduSeq(eduSeq);
			qtVO.setUserId(userId);
			List<QuizTestVO> testResultList = quizTestMapper.selectTestResultStdnt(qtVO);

			/*
			//수업정보
			Lecture lctre = eduMapper.selectLctreByPk(eduSeq);
			
			for(SubjectResultVO o: subList){
				int subSeq = o.getSubSeq();
				if(o.getComplIdx() > 0){
					//과목교육일 체크
					Date eduEndDe = DateUtil.getStr2Date(o.getEndDeStr(), "yyyyMMddHHmm");
					if(eduEndDe.compareTo(new Date()) < 0){
						//이수처리
						o.setComplState(1);
						
						//이수증처리
						if(ObjectUtils.isEmpty(o.getComplCertNum())){
							//이수처리
							this.setSubjectCert(lctre,subSeq,userId,1,eduEndDe,1);
						}
					}
				}
				if(o.getPassIdx() > 0){
					if(ObjectUtils.isEmpty(o.getPassCertNum())){
						//합격여부
						for(QuizTestVO o2: testResultList){
							if(o.getSubSeq() == o2.getSubSeq() && "Y".equals(o2.getPassYn()) && o2.getTestResultState() == 3){
								//합격처리
								this.setSubjectCert(lctre,subSeq,userId,2,null,1);
								break;
							}
						}
					}
				}
			}
			*/
			//수료정보갱신
			subList = eduMapper.getLctreSubList(srVO);
			rstData.put("subList", subList);
			
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			e.printStackTrace();
			result.setResult(0);
			result.setMsg("알 수 없는 오류가 발생하였습니다. 관리자에게 문의하세요");
			return result;
		}
	}
	
	/**
	 * 
	 * @param lctre
	 * @param subSeq
	 * @param userId
	 * @param mode (1:수료, 2:합격)
	 */
	private void setSubjectCert(Lecture lctre, int subSeq,String userId,int mode,Date complDe,int state) {
		int eduSeq = lctre.getEduSeq();
		String year = lctre.getEduYear();
		int ctgrySeq = lctre.getCtg1Seq();
		
		LectureSubjectStdnt vo = new LectureSubjectStdnt();
		vo.setEduSeq(eduSeq);
		vo.setSubSeq(subSeq);
		vo.setUserId(userId);
		vo.setUpdDe(new Date());
		LectureSubjectStdnt sbj = lectureSubjectStdntMapper.selectByPk(vo);
		boolean isGenNum = false;
		if(sbj != null){
			ctgrySeq = 0;
			if(!ObjectUtils.isEmpty(sbj.getComplCertNum()) && mode == 1){
				isGenNum = true;
			}
			if(!ObjectUtils.isEmpty(sbj.getPassCertNum()) && mode == 2){
				isGenNum = true;
			}
		}
		String certNum = null;
		if(!isGenNum) {
			ctgrySeq = 0;
			certNum = this.getCertNum(mode, year, ctgrySeq, subSeq);
		}
		
		if(mode == 1){
			vo.setComplState(state);
			vo.setComplCertNum(certNum);
			vo.setComplCertDe(complDe);
		}else{
			vo.setPassState(state);
			vo.setPassCertNum(certNum);
			vo.setPassCertDe(complDe);
		}
		
		if(sbj != null){
			lectureSubjectStdntMapper.updateByPk(vo);
		}else{
			lectureSubjectStdntMapper.insertByPk(vo);
		}
		
	}

	@Override
	public ResultVO getLectureStdntCertList(EduVO vo) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			int eduSeq = vo.getEduSeq();
			int rowCnt = vo.getRow();
			int pageNo = vo.getPage();
			
			int totalCnt = eduMapper.selectLectureStdntListCnt(vo);
			
			if(rowCnt > 0){
				PaginationInfo paginationInfo = new PaginationInfo();
				paginationInfo.setTotalRecordCount(totalCnt);
				paginationInfo.setCurrentPageNo(pageNo);
				paginationInfo.setRecordCountPerPage(rowCnt);
				paginationInfo.setPageSize(DEFAULT_PAGE_SIZE);
				rstData.put("page", paginationInfo);
				vo.setFirstIndex(paginationInfo.getFirstRecordIndex());
			}
	        
			vo.setRow(rowCnt);
			
			//서브수료증 수
			
			//수강생목록 조회
			List<LectureStdnt> stdntList = eduMapper.selectLectureStdntList(vo);
			
			
			//객체 재설정용 리스트
			List<Map<String, Object>> stdntList2 = new ArrayList<Map<String, Object>>();
			
			//서브수료증 설정 및 객체 재설정
			SubjectResultVO srVO = new SubjectResultVO();
			srVO.setEduSeq(eduSeq);
			List<SubjectResultVO> subList = eduMapper.getLctreSubList(srVO);
			for(int i=0; i<stdntList.size(); i++){
				//객체 재설정용 맵
				Map<String, Object> stdntMap = new HashMap<String,Object>();
				LectureStdnt o = stdntList.get(i);
				stdntMap.put("stdnt", o);
				//객체 재설정용 과목리스트
				List<SubjectResultVO> subList2 = new ArrayList<SubjectResultVO>();
				for(int j=0; j<subList.size(); j++){
					SubjectResultVO o2 = subList.get(j);
					if(o.getEduSeq() == o2.getEduSeq() && o.getUserId().equals(o2.getUserId())){
						subList2.add(o2);
					}
				}
				stdntMap.put("subList", subList2);
				stdntMap.put("subCnt", subList2.size());
				stdntList2.add(stdntMap);
			}
			
			rstData.put("list", stdntList2);
			
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setResult(0);
			return result;
		}
	}
	
	@Override
	public ResultVO setCertSub(int eduSeq, String userId, int subSeq, int certMode,int state) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			if(subSeq == 0){
				result.setMsg("과목번호가 없습니다."); result.setResult(0); return result;
			}
			
			Lecture lctre = eduMapper.selectLctreByPk(eduSeq);
			Date complDe = new Date();
			
			//if(certMode == 1){
				SubjectResultVO srVO = new SubjectResultVO();
				srVO.setEduSeq(eduSeq);
				srVO.setSubSeq(subSeq);
				srVO.setUserId(userId);
				List<SubjectResultVO> list = eduMapper.getLctreSubList(srVO);
				complDe = DateUtil.getStr2Date(list.get(0).getEndDeStr(), "yyyyMMddHHmm") ;
			//}
			
			this.setSubjectCert(lctre, subSeq, userId, certMode, complDe, state);
			
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setResult(0);
			return result;
		}
	}

	/**
	 * 달력용 교육일정 조회
	 * @param vo
	 * @return
	 */
	@Override
	public List<Lecture> getLctreCalList(EduVO vo) {
		return eduMapper.selectLctreCalList(vo);
	}
}
