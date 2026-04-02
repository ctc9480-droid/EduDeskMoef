package com.educare.edu.comn.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringEscapeUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.educare.component.NosProgressVO;
import com.educare.component.QrCodeComponent;
import com.educare.component.VarComponent;
import com.educare.edu.comn.mapper.FeedbackEduMapper;
import com.educare.edu.comn.mapper.FeedbackEduResultMapper;
import com.educare.edu.comn.mapper.LectureMovMapper;
import com.educare.edu.comn.mapper.LectureMovTimeMapper;
import com.educare.edu.comn.mapper.LectureTimeMapper;
import com.educare.edu.comn.mapper.MovMapper;
import com.educare.edu.comn.mapper.QrCodeMapper;
import com.educare.edu.comn.mapper.SmartCheckMapper;
import com.educare.edu.comn.mapper.TableMapper;
import com.educare.edu.comn.model.FeedbackEdu;
import com.educare.edu.comn.model.FeedbackEduResult;
import com.educare.edu.comn.model.LectureMov;
import com.educare.edu.comn.model.LectureMovTime;
import com.educare.edu.comn.model.LectureTime;
import com.educare.edu.comn.model.QrCode;
import com.educare.edu.comn.service.AjaxService;
import com.educare.edu.comn.service.CheckService;
import com.educare.edu.comn.service.EmailService;
import com.educare.edu.comn.service.LectureRoomService;
import com.educare.edu.comn.service.MovService;
import com.educare.edu.comn.service.ResultService;
import com.educare.edu.comn.service.SmartcheckService;
import com.educare.edu.comn.service.SmsService;
import com.educare.edu.comn.service.StatService;
import com.educare.edu.comn.service.SyncDataService;
import com.educare.edu.comn.vo.CheckVO;
import com.educare.edu.comn.vo.FeedbackVO;
import com.educare.edu.comn.vo.MovVO;
import com.educare.edu.comn.vo.PassCertVO;
import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.comn.vo.SmartCheckVO;
import com.educare.edu.comn.vo.TimeTableVO;
import com.educare.edu.education.service.EduService;
import com.educare.edu.education.service.EduVO;
import com.educare.edu.education.service.LctreService;
import com.educare.edu.education.service.impl.EduMapper;
import com.educare.edu.education.service.model.Lecture;
import com.educare.edu.education.service.model.LectureRcept;
import com.educare.edu.education.service.model.LectureStdnt;
import com.educare.edu.feedback.service.FeedbackService;
import com.educare.edu.member.service.SessionUserInfoHelper;
import com.educare.edu.member.service.impl.MemberMapper;
import com.educare.edu.member.service.model.UserInfo;
import com.educare.util.CryptoSeedUtil;
import com.educare.util.DateUtil;
import com.educare.util.FileUtil;
import com.educare.util.LncUtil;
import com.educare.util.XmlBean;

@Controller
@RequestMapping(value="/admin/ajax/")
public class AdminAjaxController {
	
	/** org.slf4j.Logger */
	private static final Logger LOG = LoggerFactory.getLogger(AdminAjaxController.class.getName());
	
	@Resource(name="EduService")
	private EduService eduService;
	
	@Resource(name="EduMapper")
	private EduMapper eduMapper;
	
	@Resource(name="SmartcheckService")
	private SmartcheckService smartcheckService;
	@Resource(name="SmartCheckMapper")
	private SmartCheckMapper smartCheckMapper;
	
	@Resource(name="FeedbackService")
	private FeedbackService feedbackService;
	
	@Resource(name="LectureMovMapper")
	private LectureMovMapper lectureMovMapper;
	@Resource(name="LectureTimeMapper")
	private LectureTimeMapper lectureTimeMapper;
	@Resource(name="CheckService")
	private CheckService checkService;
	@Resource(name="MovMapper")
	private MovMapper movMapper;
	@Resource(name="MovService")
	private MovService movService;
	@Resource(name="StatService")
	private StatService statService;
	@Resource(name="MemberMapper")
	private MemberMapper memberMapper;
	@Resource(name="TableMapper")
	private TableMapper tableMapper;
	@Resource(name="LectureMovTimeMapper")
	private LectureMovTimeMapper lectureMovTimeMapper;
	@Resource(name="AjaxService")
	private AjaxService ajaxService;
	
	@Resource(name = "ResultService")
	private ResultService resultService;
	@Resource(name = "LctreService")
	private LctreService lctreService;
	@Resource(name = "SmsService")
	private SmsService smsService;
	@Resource(name = "FeedbackEduMapper")
	private FeedbackEduMapper feedbackEduMapper;
	@Resource(name = "FeedbackEduResultMapper")
	private FeedbackEduResultMapper feedbackEduResultMapper;
	@Resource(name = "LectureRoomService")
	private LectureRoomService lectureRoomService;
	@Resource(name = "QrCodeMapper")
	private QrCodeMapper qrCodeMapper;
	@Resource(name = "SyncDataService")
	private SyncDataService syncDataService;
	
	@Resource(name = "EmailService")
	private EmailService emailService;
	
	
	@RequestMapping(value="saveTimeData.json",produces="application/json")
	public String saveTimeData(ModelMap model
			,@RequestBody Map<String, Object> param
			) {
		String result="";
		
		int eduSeq = Integer.parseInt(param.get("eduSeq").toString());
		String checkYn = param.get("checkYn").toString();
		List<Map<String, String>> timeList = (List<Map<String, String>>) param.get("timeList");
		List<LectureTime> timeList2 = new ArrayList<LectureTime>();
		
		result = convObject(timeList,timeList2);
		if(result.equals("")){
			result = eduService.saveLectureTime(eduSeq,checkYn,timeList2); 
		}
		model.addAttribute("result", result);
		return "jsonView";
	}
	@RequestMapping(value="lectureTimeList.json")
	public String lectureTimeList(ModelMap model
			,@RequestParam int eduSeq
			) {
		
		//전자출결 사용여부
		Lecture lctre = eduService.getLctreDetail(eduSeq);
		
		//시간표
//		List<LectureTime> list = eduService.getLectureTimeList(eduSeq);
		List<TimeTableVO> list = eduService.getTimeTableList(lctre, null);
		
		model.addAttribute("list", list);
		return "jsonView";
	}
	/**
	 * 객체 변환 하면서 유효성 체크
	 * @param result
	 * @param source
	 * @param target
	 */
	public String convObject(List<Map<String, String>> source,List<LectureTime> target){
		try {
			for(Map<String, String> obj:source){
				String eduDt = obj.get("eduDt2").replaceAll("\\.", "");
				Pattern p = Pattern.compile(VarComponent.REGEX_YYYYMMDD); 
				Matcher m = p.matcher(eduDt); 
				if(!m.matches()) {
					return "수업일 형식이 잘못되었습니다.";
				}
				p = Pattern.compile(VarComponent.REGEX_HHMI); 
				m = p.matcher(obj.get("startTm")); 
				if(!m.matches()) {
					return "시작시간 형식이 잘못되었습니다.";
				}
				m = p.matcher(obj.get("endTm")); 
				if(!m.matches()) {
					return "종료시간 형식이 잘못되었습니다.";
				}
				
				if(Integer.parseInt(String.valueOf(obj.get("classHow")))==1){
					if(obj.get("url")==null){
						return "외부 온라인 강의 URL을 입력하셔야 합니다.";
					}
					p = Pattern.compile(VarComponent.REGEX_URL); 
					m = p.matcher(obj.get("url")); 
					if(!m.matches()) {
						return "URL 형식이 잘못되었습니다.";
					}
				}
				
				LectureTime ltData = new LectureTime();
				ltData.setEduDt(eduDt);
				ltData.setStartTm(obj.get("startTm"));
				ltData.setEndTm(obj.get("endTm"));
				ltData.setDescription(obj.get("description"));
				ltData.setInstrNm(obj.get("instrNm"));
				ltData.setCheckId(obj.get("checkId"));
				ltData.setCheckNm(obj.get("checkNm"));
				ltData.setClassHow(Integer.parseInt(String.valueOf(obj.get("classHow"))));
				ltData.setUrl(obj.get("url"));
				ltData.setUrlPw(obj.get("urlPw"));
				ltData.setMvIdx(Integer.parseInt(String.valueOf(obj.get("mvIdx"))));
				ltData.setFreeView(Integer.parseInt(String.valueOf(obj.get("freeView"))));
				target.add(ltData);
			}
			return "";
		} catch (NullPointerException e) {
			return "데이터 형식 오류";
		}
	}

	/*
	 * 학생 점수 저장
	 */
	@RequestMapping(value="saveStdntScore.json",produces="application/json")
	public String saveStdntScore(ModelMap model
			,@RequestBody Map<String, Object> param
			) {
		String result="";
		try {
			Lecture lctre = eduService.getLctreDetail(Integer.parseInt(param.get("eduSeq").toString()));
			
			
			
			List<Map<String, Object>> dataList = (List)param.get("dataList");
			for(Map<String, Object> dataMap:dataList){
				LectureStdnt lsparam = new LectureStdnt();
				lsparam.setUserId(dataMap.get("userId").toString());
				lsparam.setEduSeq(Integer.valueOf(dataMap.get("eduSeq").toString()));
				if(dataMap.get("attCnt")!=null){
					lsparam.setAttCnt(Integer.valueOf(dataMap.get("attCnt").toString()));
				}
				lsparam.setWorkshopScore(Integer.valueOf(dataMap.get("workshopScore").toString()));
				lsparam.setExamScore(Integer.valueOf(dataMap.get("examScore").toString()));
				lsparam.setAttendScore(Integer.valueOf(dataMap.get("attendScore").toString()));
				lsparam.setPassYn(dataMap.get("passYn").toString());
				
				//자동수료이면 수료점수 계산하여 수료처리한다,동영상전용이 아닌경우만
				if("Y".equals(lctre.getAutoPassYn()) && lctre.getLctreType() != 3){
					int passPoint = lctre.getPassPoint();
					int passAttPoint = lsparam.getAttendScore();
					int passAsgPoint = lsparam.getWorkshopScore();
					int passTestPoint = lsparam.getExamScore();
					if((passAttPoint+passAsgPoint+passTestPoint) >= passPoint){
						lsparam.setPassYn("Y");
					}else{
						lsparam.setPassYn("N");
					}
				}
				
				eduMapper.updateLectureStdntScore(lsparam);
				
				eduService.setPass(lsparam);
			}
		} catch (NullPointerException e) {
			result= VarComponent.MSG_ERROR;
		}
		model.addAttribute("result", result);
		return "jsonView";
	}
	@RequestMapping(value="rollBook.json",produces="application/json")
	public String checkList(ModelMap model
			,@RequestParam int eduSeq
			,@RequestParam(required=false) String userId
			) {
		int result = 0;
		try {
			//날짜
			List<SmartCheckVO> dateList = smartcheckService.getDateList(eduSeq);
			// 시간
			List<SmartCheckVO> timeList = smartcheckService.getTimeList(eduSeq);

			//시간표
			List<SmartCheckVO> stdntList = smartcheckService.getStdntList(eduSeq,userId);
			
			model.addAttribute("dateList", dateList);
			model.addAttribute("timeList", timeList);
			model.addAttribute("stdntList", stdntList);
			result=1;
		} catch (NullPointerException e) {
			result=0;
		}
		model.addAttribute("result", result);
		return "jsonView";
	}
	@RequestMapping(value="loadFeedback.json")
	public String loadFeedback(ModelMap model
			,@RequestParam(defaultValue="0") int fbIdx
			,@RequestParam(defaultValue="0") int eduSeq
			) {
		int result = eduService.loadFeedback(eduSeq,fbIdx);
		model.addAttribute("result", result);
		return "jsonView";
	}
	@RequestMapping(value="deleteEduFeedback.json")
	public String deleteEduFeedback(ModelMap model
			,@RequestParam(defaultValue="0") int eduSeq
			) {
		int result = eduService.deleteEduFeedback(eduSeq);
		model.addAttribute("result", result);
		return "jsonView";
	}
	/**
	 * 강의에 설정된 피드백 정보
	 * @param model
	 * @param eduSeq
	 * @return
	 */
	@RequestMapping(value="eduFeedbackInfo.json")
	public String eduFeedbackInfo(ModelMap model
			,@RequestParam(defaultValue="0") int eduSeq
			,@RequestParam(defaultValue="0") Integer feSeq
			) {
		int result = 0;
		try {
			Lecture lecture = eduService.getLctreDetail(eduSeq);
			int fbIdx = lecture.getFbIdx();
			
			//총 참여자수
			int stdntFeedbackCnt = eduMapper.selectlctreStdntCntBySurveyY(eduSeq);
			
			//feSeq가 존재한다면
			if(feSeq > 0){
				FeedbackEdu fe = feedbackEduMapper.selectByPk(feSeq);
				if(fe==null){
					model.addAttribute("result",0);
					model.addAttribute("message","잘못된 설문지입니다.");
					return "jsonView";
				}
				model.addAttribute("fe",fe);
				fbIdx = fe.getFbIdx();
				
				FeedbackEduResult vo = new FeedbackEduResult();
				vo.setEduSeq(eduSeq);
				vo.setFeSeq(feSeq);
				vo.setState(2);
				stdntFeedbackCnt = feedbackEduResultMapper.selectCnt(vo);
			}
			
			Map<String, Object> feedbackInfo = feedbackService.getFeedbackInfo(fbIdx );
			
			//
			feedbackService.setResult(feedbackInfo,eduSeq,feSeq);
			
			//총 학생수 
			int stdntCnt = eduMapper.selectConfirmRceptCnt(eduSeq);
			
			
			model.addAttribute("stdntCnt",stdntCnt);
			model.addAttribute("stdntFeedbackCnt",stdntFeedbackCnt);
			model.addAttribute("feedbackInfo",feedbackInfo);
			result = 1;
		} catch (NullPointerException e) {
			result = 0;
		}
		
		model.addAttribute("result", result);
		return "jsonView";
	}
	@ResponseBody
	@RequestMapping(value="uploadMov.ajax" )
	public ResultVO uploadMov(ModelMap model,HttpServletRequest req
			,MultipartHttpServletRequest mhsr
			,@RequestParam(defaultValue="0") int idx
			,String title
			,String content
			,String instrctrNm
			,String duration
			) {
		String title2 = title;
		String instrctrNm2 = instrctrNm;
		String content2 = content;
		ResultVO result= new ResultVO();
		try {
			UserInfo user = SessionUserInfoHelper.getUserInfo();
			if(user == null){
				result.setResult(0);
				result.setMsg("알수없는 오류가 발생하였습니다.");
				return result;
			}
			
			title2 = new String(title2.getBytes("8859_1"),"utf-8");
			content2 = new String(content2.getBytes("8859_1"),"utf-8");
			instrctrNm2 = new String(instrctrNm2.getBytes("8859_1"),"utf-8");
			String orgCd = user.getOrgCd();
			
			if(ObjectUtils.isEmpty(title2)||ObjectUtils.isEmpty(content2)||ObjectUtils.isEmpty(instrctrNm2)){
				result.setResult(0);
				result.setMsg("항목을 모두 입력하세요");
				return result;
			}
			
			//수업정보 가져오기
			LectureMov param3 = new LectureMov();
			param3.setIdx(idx);
			LectureMov movInfo = lectureMovMapper.selectByPk(param3);
			
			MultipartFile mf = mhsr.getFile("file_1");
			String fileOrg="";
			String fileRename="";
			
			
			if(movInfo!=null){
				
				LectureMov param = new LectureMov(idx,user.getOrgCd(), title2, content2, fileOrg, fileRename, instrctrNm2
						, Calendar.getInstance().getTime()
						, user.getUserId()
						, Calendar.getInstance().getTime()
						,duration);
				lectureMovMapper.updateByPk(param);
			}else{
				if(mf==null){
					result.setResult(0);
					result.setMsg("파일을 등록하세요");
					return result;
				}
				
				fileOrg = new String(mf.getOriginalFilename().getBytes("8859_1"),"utf-8");
				fileRename = fileOrg;
				
				//이미업로드된 파일명인지 체크하여 막기
				MovVO param2 = new MovVO();
				param2.setOrgCd(orgCd);
				param2.setFileRename(fileRename);
				int checkMov = movMapper.getCntByfileRenameOrgCd(param2);
				if(checkMov>0){
					result.setResult(0);
					result.setMsg("같은 파일명은 업로드 할 수 없습니다. 파일명을 변경 해 주세요");
					return result;
				}
				String folderName = "mov/"+orgCd+"/";
				LectureMov param = new LectureMov(idx,orgCd, title2, content2, fileOrg, fileRename, instrctrNm2
						, Calendar.getInstance().getTime()
						, SessionUserInfoHelper.getUserId()
						, Calendar.getInstance().getTime()
						,duration);
				//result = NaverObjectStorage.multiPartupload(mf,folderName,fileOrg,fileRename);
				result = FileUtil.multiPartupload(mf, folderName, fileOrg, fileRename);
				if(result.getResult()==1){
					lectureMovMapper.insertByPk(param);
				}
			}
			result.setResult(1);
			return result;
			
		} catch (NullPointerException | UnsupportedEncodingException e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
			result.setResult(0);
			return result;
		}
	}
	@RequestMapping(value="deleteMov.json" )
	public String deleteMov(ModelMap model
			,int idx
			) {
		
		int result = movService.deleteMov(idx);
		model.addAttribute("result", result);
		return "jsonView";
	}
	
	@ResponseBody
	@RequestMapping(value="lectureMov.json")
	public Object checkList(ModelMap model
			,@RequestParam int mvIdx
			) {
		Map<String, Object> result = new HashMap<>();
		try {
			LectureMov param1 = new LectureMov();
			param1.setIdx(mvIdx);
			LectureMov mov = lectureMovMapper.selectByPk(param1);
			
			LectureMovTime param2 = new LectureMovTime();
			param2.setMvIdx(mvIdx);
			List<LectureMovTime> movTime = lectureMovTimeMapper.selectByMv(param2);
			
			result.put("mov", mov);
			result.put("movTime", movTime);
			result.put("result", 1);
		} catch (NullPointerException e) {
			result.put("result", 0);
		}
		return result;
	}
	@RequestMapping("movList.json")
	public String movList(ModelMap model
			,MovVO param
			) {
		
		UserInfo user = SessionUserInfoHelper.getUserInfo();
		if(user == null){
			return null;
		}
		
		param.setOrgCd(user.getOrgCd());
		int totalCnt = movMapper.getMovTotalCnt(param);
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setTotalRecordCount(totalCnt);
		paginationInfo.setCurrentPageNo(param.getPageNo());
		paginationInfo.setRecordCountPerPage(5);
		paginationInfo.setPageSize(10);
		model.addAttribute("pageNavi",paginationInfo);
		param.setFirstRecordIndex(paginationInfo.getFirstRecordIndex());
		//param.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		param.setRecordCountPerPage(0);
		
		List<MovVO> list = movMapper.getMovList(param);
		
		model.addAttribute("list",list);
		return "jsonView";
	}
	
	@RequestMapping("statMonth.json")
	public String statMonth(ModelMap model
			) {
		Map<String, Object> datas = new HashMap<String, Object>();
		int result = statService.getMonthMain(datas);
		
		model.addAttribute("result",result);
		model.addAttribute("datas",datas);
		return "jsonView";
	}
	@RequestMapping("statTotal.json")
	public String statTotal(ModelMap model
			) {
		Map<String, Object> datas = new HashMap<String, Object>();
		int result = statService.getTotalMain(datas);
		
		model.addAttribute("result",result);
		model.addAttribute("datas",datas);
		return "jsonView";
	}
	
	@RequestMapping("nosProgress.json")
	public String nosProgress(ModelMap model,HttpServletRequest req
			) {
		try {
			NosProgressVO np = (NosProgressVO) req.getSession().getAttribute(VarComponent.SESSION_KEY_PROGRESS+"NOS");
			model.addAttribute("result",1);
			model.addAttribute("data",np);
		} catch (NullPointerException e) {
			model.addAttribute("result",0);
		}
		return "jsonView";
	}
	@ResponseBody
	@RequestMapping("myEduList.json")
	public Object myEduList(ModelMap model,HttpServletRequest req
			,EduVO vo
			) {
		
	 	Map<String, Object> resultMap = eduService.getMyEduList(vo);
		
		return resultMap;
	}
	@RequestMapping("myEduInfo.json")
	public Object myEduInfo(ModelMap model,HttpServletRequest req
			,int eduSeq
			,String userId
			) {
		
		Lecture lctre = eduService.getLctreDetail(eduSeq);
		LectureStdnt stdnt = eduService.getMyAtnlcDetail(eduSeq,userId);
		int rceptSeq = stdnt.getRceptSeq();
		LectureRcept rcept = eduService.getMyRceptDetail(rceptSeq,eduSeq,userId);
		List<TimeTableVO> timeList = eduService.getTimeTableList(lctre,userId);
		
		model.addAttribute("lctre", lctre);
		model.addAttribute("rcept", rcept);
		model.addAttribute("stdnt", stdnt);
		model.addAttribute("timeList", timeList);
		
		//과목수료증
		ResultVO result = eduService.getLctreSubResult(eduSeq,userId);
		model.addAttribute("subData",result.getData());
		
		return "jsonView";
	}
	@RequestMapping("applyCheckedInstrctr.json")
	public Object applyCheckedInstrctr(ModelMap model,HttpServletRequest req
			,String[] checkUserId
			) {
		Map<String, Object> result = new HashMap<>();
		UserInfo param = new UserInfo();
		param.setState("A");
		try {
			for(String userId:checkUserId){
				param.setUserId(userId);
				memberMapper.updateUserInfoForState(param);
			} 
			
			result.put("result", 1);
		} catch (NullPointerException e) {
			result.put("result", 0);
		}
				
		model.addAttribute("result",result);
		return "jsonView";
	}
	@ResponseBody
	@RequestMapping("instrctrEduList.json")
	public Object instrctrEduList(ModelMap model,HttpServletRequest req
			,EduVO vo
			) {
		
	 	Map<String, Object> resultMap = eduService.getLectureInstrctrList(vo);
		
		return resultMap;
	}
	@RequestMapping("instrctrEduInfo.json")
	public Object instrctrEduInfo(ModelMap model,HttpServletRequest req
			,EduVO vo
			) {
		
		Lecture lctre = eduService.getLctreDetail(vo.getEduSeq());
		List<TimeTableVO> timeList = eduService.getTimeTableList(lctre,vo.getUserId());
		
		model.addAttribute("lctre", lctre);
		model.addAttribute("timeList", timeList);
		
		return "jsonView"; 
	}
	@ResponseBody
	@RequestMapping("esLectureList.ajax")
	public ResultVO esLectureList(EduVO vo) {
		ResultVO result = new ResultVO();
		Map<String, Object> dataList2 = new HashMap<String, Object>();
		
		//관리자가 아닐시 접속 isAdmin에 퍼즈 담아서 반송  
		if(!SessionUserInfoHelper.isAdmin()) {
			result.setResult(0);
			result.setMsg("관리자만 접근 가능합니다.");
			return result; 
		} 
		dataList2 = ajaxService.getLctreList(vo);
		result.setData(dataList2);
		result.setResult(1);
		return result;
	}
	@ResponseBody
	@RequestMapping("fbList.ajax")
	public ResultVO fbList(EduVO eduVo) {
		ResultVO result = new ResultVO();
		List<FeedbackVO> dataList = new ArrayList<FeedbackVO>();
		
		//관리자 필터
		if(!SessionUserInfoHelper.isAdmin()) {
			result.setResult(0);
			result.setMsg("관리자만 접근 가능합니다.");
			return result;
		}
		try {
			UserInfo user = SessionUserInfoHelper.getUserInfo();
			String orgcd = "";
			if(user!=null){
				orgcd = user.getOrgCd();
			}
			dataList = ajaxService.getFeedbackList(orgcd, eduVo);
			result.setData(dataList);
			result.setResult(1);			
		}catch(NullPointerException e) {
			result.setMsg("설문 호출에 실패하였습니다.");
			result.setResult(0);
		}
		return result;
	}
	@ResponseBody
	@RequestMapping("passCertList.ajax")
	public ResultVO passCertList(EduVO eduVo) {
		ResultVO result = new ResultVO();
		List<PassCertVO> datalist = new ArrayList<PassCertVO>();
	
		try {
			datalist = ajaxService.getPassCertList(eduVo);
			result.setData(datalist);
			result.setResult(1);
		}catch(NullPointerException e) {
			result.setMsg("수료증 호출에 실패하였습니다.");
			result.setResult(0);
		}
		
		return result;
	}	
	
	@ResponseBody
	@RequestMapping(value="saveAttendInfo.ajax")
	public ResultVO saveCheckInfo(HttpSession session
			,int eduSeq
			,int timeSeq
			,String userId
			,String attCd
			) {
		//권한체크,관리자나,강사체크필요
		
		ResultVO result = smartcheckService.setAttendByStudent(eduSeq, timeSeq, userId, attCd, "로그내용");
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="saveExcelLog.ajax")
	public ResultVO saveExcelLog(HttpSession session
			,String excelTy
			,String content
			,int excelEduSeq
			) {
		//권한체크,관리자나,강사체크필요
		ResultVO result = ajaxService.saveExcelLog(excelTy, content, excelEduSeq);
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="getRceptInfo.json")
	public ResultVO getRceptInfo(ModelMap model
			,@RequestParam String userId
			,@RequestParam int eduSeq
			,@RequestParam int rceptSeq
			) {
		
		ResultVO result = resultService.getRceptInfo(eduSeq,userId,rceptSeq);
		
		return result;
	}
	@ResponseBody
	@RequestMapping(value="savePayState.json")
	public ResultVO savePayState(ModelMap model
			,@RequestParam int rceptSeq
			,@RequestParam int eduSeq
			,@RequestParam String userId
			,@RequestParam(required=false) String memoPay
			,@RequestParam(defaultValue="0") int pgState
			) {
		
		ResultVO result = resultService.savePayState(rceptSeq,eduSeq,userId,memoPay,pgState);
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="callLcrcpInfo.ajax")
	public ResultVO callLcrcpInfoAjax(ModelMap model
			,int eduSeq
			,String userId
			,int rceptSeq
			) {
		
		String userId2 = userId;
		if(!SessionUserInfoHelper.isAdmin()){
			userId2 = SessionUserInfoHelper.getUserId();
		}
		
		ResultVO result = eduService.getLcrcpInfo(eduSeq,userId2,rceptSeq);
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="changeInstrctr.ajax")
	public ResultVO changeInstrctrAjax(ModelMap model
			,@RequestParam String instrctrId
			,@RequestParam int eduSeq
			) {
		ResultVO result = eduService.setInstrctr(eduSeq,instrctrId);
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="getStdntLcRceptList.ajax")
	public ResultVO getStdntLcRceptList(ModelMap model
			,@RequestParam String userId
			,@RequestParam Integer page
			) {
		
		EduVO vo = new EduVO();
		vo.setPage(page);
		vo.setUserId(userId);
		ResultVO result =  eduService.getLcrcpPageList(vo,5);
		
		return result;
	}
	@ResponseBody
	@RequestMapping(value="saveOpenYnAll.ajax")
	public ResultVO saveOpenYnAll(ModelMap model
			,@RequestParam String openYn
			,@RequestParam String jsonData
			) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper om = new ObjectMapper();
		List<String> eduSeqArr = om.readValue(LncUtil.unescapeJson(jsonData), List.class);
		
		ResultVO result =  ajaxService.saveOpenYnAll(openYn,eduSeqArr);
		
		return result;
	}
	
	//신청 상태 변경
	@ResponseBody
	@RequestMapping("updRceptState.ajax")
	public ResultVO updState(ModelMap model
			, int eduSeq
			, int rceptSeq
			, int state
			, String userId
			) {
		ResultVO result = new ResultVO();
		result.setResult(0);
		//if(state==3){//취소면 결제유무 파악
		//	ResultVO checkCancel = checkService.checkCancel(rceptSeq,eduSeq,userId);
		//}
		
		//상태변경 처리
		result = lctreService.setRceptState(rceptSeq,eduSeq,userId,state);
		return result;
	}
	/*
	@ResponseBody
	@RequestMapping(value="getStdntSmsList.ajax")
	public ResultVO getStdntSmsList(ModelMap model
			,@RequestParam String userId
			,@RequestParam Integer page
			) {
		
		int listRow = 10;
		ResultVO result =  smsService.getSmsLogPageList(userId,listRow,page);
		
		return result;
	}
	*/
	@ResponseBody
	@RequestMapping(value="setSendSms.ajax")
	public CheckVO setSendSms(ModelMap model
			,@RequestParam String userId
			,@RequestParam String fromNum
			,@RequestParam String toNumList
			,@RequestParam String toMsg
			) {
		
		CheckVO result = new CheckVO();
		
		if(ObjectUtils.isEmpty(toNumList)){
			result.setMsg("수신번호 오류");
			return result;
		}
		if(ObjectUtils.isEmpty(toMsg)){
			result.setMsg("문자내용 오류");
			return result;
		}
		
		String[] toNums = toNumList.split(",");
		int failCnt = 0;
		for (String toNum : toNums) {
			result =  smsService.setSendSms(fromNum, toNum, toMsg, new Date(), 0, userId, 1, "");
			if (result.getResult() == 0) {
				failCnt++;
			}
		}
		if (failCnt > 0) {
			result.setResult(0);
			result.setMsg(toNums.length + "건 중 " + failCnt + " 건 오류");
		}
		
		return result;
	}
	@ResponseBody
	@RequestMapping(value="addLectureTime.ajax", method=RequestMethod.POST)
	public ResultVO saveLectureTime(ModelMap model
			,int eduSeq
			,int timeSeq
			,int dateTp		//1:단건,2:반복
			,String startDtStr
			,String endDtStr
			,String eduDtStr
			,@RequestParam(name="weeks[]",defaultValue="0") String[] weeks
			,@RequestParam("times[]") String[] times
			) {
		
		ResultVO result = eduService.addLectureTime(eduSeq,timeSeq,dateTp,startDtStr,endDtStr,eduDtStr,weeks,times); 
		return result;
	}
	@ResponseBody
	@RequestMapping(value="callLectureRoomChoice.ajax")
	public ResultVO saveLectureTime(ModelMap model
			) {
		
		String[] eduDeStrArr = {};
		
		ResultVO result = lectureRoomService.getLectureRoomChoice(eduDeStrArr);
		return result;
	}
	@ResponseBody
	@RequestMapping(value="delLectureTime.ajax", method=RequestMethod.POST)
	public ResultVO delLectureTime(ModelMap model
			,int eduSeq
			,int timeSeq
			) {
		
		ResultVO result = eduService.delLectureTime(eduSeq,timeSeq);
		return result;
	}
	@ResponseBody
	@RequestMapping(value="delLectureTimeSelected.ajax", method=RequestMethod.POST)
	public ResultVO delLectureTimeSelected(ModelMap model
			,int eduSeq
			,@RequestParam(value="timeSeqArr[]")int[] timeSeqArr
			) {
		ResultVO result = new ResultVO();
		for(int timeSeq: timeSeqArr){
			result = eduService.delLectureTime(eduSeq,timeSeq);
		}
		return result;
	}
	@ResponseBody
	@RequestMapping(value="callLectureTime.ajax", method=RequestMethod.GET)
	public ResultVO callLectureTime(ModelMap model
			,int eduSeq
			,int timeSeq
			) {
		
		ResultVO result = eduService.getLectureTime(eduSeq,timeSeq);
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="createQrAttend.ajax")
	public ResultVO createQrAttend(ModelMap model
			,@RequestParam int eduSeq
			,@RequestParam(required=true) String qrType 
			,String qrDt
			,String qrBeginTm
			,String qrEndTm
			) {
		
		if(!"1,2,3".contains(qrType)){
			ResultVO result0 = new ResultVO();
			result0.setResult(0);
			result0.setMsg("잘못된 요청입니다.");
			return result0;
		}
		
		Lecture lctre = eduMapper.selectLctreByPk(eduSeq);
		
		String qrKey = String.valueOf(Calendar.getInstance().getTimeInMillis());
		String encData = eduSeq+","+qrKey+","+qrType+","+qrDt;
		String data = "";
		try {
			encData = CryptoSeedUtil.encrypt(encData);
			data = URLEncoder.encode(encData,"utf-8");
			LOG.debug("data : "+data);
		} catch (NullPointerException | UnsupportedEncodingException e) {
			encData = "ERROR";
		}
		
		Date genDe = new Date();
		
		//qrBeginDe,qrEndDe세팅
		Date qrBeginDe = DateUtil.getStr2Date(qrDt+"00:00", "yyyyMMddHH:mm");
		Date qrEndDe = DateUtil.getStr2Date(qrDt+"23:59","yyyyMMddHH:mm");
		if(!ObjectUtils.isEmpty(qrBeginTm)){
			qrBeginDe = DateUtil.getStr2Date(qrDt+qrBeginTm,"yyyyMMddHH:mm");
		}
		if(!ObjectUtils.isEmpty(qrEndTm)){
			qrEndDe = DateUtil.getStr2Date(qrDt+qrEndTm,"yyyyMMddHH:mm");
		}
		
		String addText1 = "";
		if("1".equals(qrType)){
			addText1 = "아침";
		}else if("2".equals(qrType)){
			addText1 = "점심";
		}else {
			addText1 = "저녁";
		}
		String addText2 = lctre.getEduNm();
		addText2 = StringEscapeUtils.unescapeHtml4(addText2);
		String addText3 = "생성일시 : "+DateUtil.getDate2Str(genDe, "yyyy.MM.dd HH:mm:ss");
		ResultVO resultQr = QrCodeComponent.createQRCode(XmlBean.getConfigValue("domain.name")+"/qrCode/attend/set.do?data="+data,"upload/web/qr/","attend_"+eduSeq+"_"+qrDt+"_"+qrType+".png",0,0,addText1,addText2,addText3);
		
		if(resultQr.getResult()==1){
			
			QrCode param = new QrCode(0, qrKey, qrType, eduSeq,qrDt, genDe,qrBeginDe,qrEndDe);
			QrCode qr = qrCodeMapper.selectByAttend(param);
			param.setQrKey(qrKey);
			if(qr!=null){
				param.setQrSeq(qr.getQrSeq());
				qrCodeMapper.updateByPk(param);
			}else{
				qrCodeMapper.insertByPk(param);
			}
		}
		
		return resultQr;
	}
	
	@ResponseBody
	@RequestMapping(value="createQrFeedback.ajax")
	public ResultVO createQrFeedback(ModelMap model
			,@RequestParam int eduSeq
			,@RequestParam int feSeq
			,@RequestParam(required=true) String qrType
			,String qrStartDt
			,String qrEndDt
			,String qrNm
			) {
		
		if(!"4".equals(qrType)){
			ResultVO result0 = new ResultVO();
			result0.setResult(0);
			result0.setMsg("잘못된 요청입니다.");
			return result0;
		}
		
		Lecture lctre = eduMapper.selectLctreByPk(eduSeq);
		
		String qrKey = String.valueOf(Calendar.getInstance().getTimeInMillis());
		String orgData = eduSeq+","+feSeq+","+qrKey+","+qrType+","+qrStartDt+","+qrEndDt;
		LOG.debug("orgData : "+orgData);
		String data = "";
		try {
			orgData = CryptoSeedUtil.encrypt(orgData);
			LOG.debug("orgData : "+orgData);
			data = URLEncoder.encode(orgData,"utf-8");
			LOG.debug("data : "+data);
		} catch (NullPointerException  | UnsupportedEncodingException e) {
			orgData = "ERROR";
		}
		
		Date genDe = new Date();
		
		//qrBeginDe,qrEndDe세팅
		Date qrBeginDe = DateUtil.getStr2Date(qrStartDt, "yyyy-MM-dd HH:mm");
		Date qrEndDe = DateUtil.getStr2Date(qrEndDt, "yyyy-MM-dd HH:mm");
//		if(!ObjectUtils.isEmpty(qrBeginTm)){
//			qrBeginDe = DateUtil.getStr2Date(qrDt+qrBeginTm,"yyyyMMddHH:mm");
//		}
//		if(!ObjectUtils.isEmpty(qrEndTm)){
//			qrEndDe = DateUtil.getStr2Date(qrDt+qrEndTm,"yyyyMMddHH:mm");
//		}
		
		String addText1 = qrNm;
//		if("1".equals(qrType)){
//			addText1 += "오전";
//		}else if("2".equals(qrType)){
//			addText1 += "점심";
//		}else {
//			addText1 += "오후";
//		}
		String addText2 = lctre.getEduNm();
		addText2 = StringEscapeUtils.unescapeHtml4(addText2);
		String addText3 = "생성일시 : "+DateUtil.getDate2Str(genDe, "yyyy.MM.dd HH:mm:ss");
		ResultVO resultQr = QrCodeComponent.createQRCode(XmlBean.getConfigValue("domain.name")+"/qrCode/feedback/set.do?data="+data,"upload/web/qr/","feedback_"+eduSeq+"_"+feSeq+"_"+qrType+".png",0,0,addText1,addText2,addText3);
		
		if(resultQr.getResult()==1){
			
			QrCode param = new QrCode(0, qrKey, qrType, eduSeq,"", genDe,qrBeginDe,qrEndDe);
			QrCode qr = qrCodeMapper.selectByFeedback(param);
			param.setQrKey(qrKey);
			if(qr!=null){
				param.setQrSeq(qr.getQrSeq());
				qrCodeMapper.updateByPk(param);
			}else{
				qrCodeMapper.insertByPk(param);
			}
		}
		
		return resultQr;
	}
	
	@ResponseBody
	@RequestMapping(value="refundRceptProc.ajax", method=RequestMethod.POST)
	public ResultVO refundRceptProc(ModelMap model
			,int rceptSeq
			) {
		
		//ResultVO result = syncDataService.syncRefundRcept(rceptSeq);
		return null;
	}
	
	@ResponseBody
	@RequestMapping("getRemainingTime.ajax")
    public ResultVO getRemainingTime(HttpServletRequest request) {
        
		HttpSession session = request.getSession(false);
        
        ResultVO result = new ResultVO();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        result.setData(dataMap);
        try {
			
        	if (session != null) {
        		int maxInactiveInterval = session.getMaxInactiveInterval(); // 세션의 최대 비활성 시간(초)
        		int secondsSinceLastAccess = (int) ((System.currentTimeMillis() - session.getLastAccessedTime()) / 1000); // 마지막 액세스 이후 경과 시간(초)
        		int remainingSeconds = maxInactiveInterval - secondsSinceLastAccess; // 남은 시간(초)
        		int remainingMinutes = remainingSeconds / 60; // 남은 시간(분)
        		int remainingSecondsMod = remainingSeconds % 60;
        		
        		dataMap.put("remainingSeconds", remainingSeconds);
        		dataMap.put("remainingMinutes", remainingMinutes);
        		dataMap.put("remainingSecondsMod", remainingSecondsMod);
        		result.setResult(1); 
        	} else {
        		result.setResult(0);
        		result.setMsg("Session not found");
        	}
        	return result;
		} catch (Exception e) {
			result.setResult(0);
			return result; 
		}
    }
	@ResponseBody
	@RequestMapping("setCertSub.ajax")
    public ResultVO setCertSub(HttpServletRequest request
    		,int eduSeq
    		,String userId
    		,int subSeq
    		,int certMode
    		,int state
    		) {
        
        ResultVO result = eduService.setCertSub(eduSeq,userId,subSeq,certMode,state);
        return result;
    }
	
	@ResponseBody
	@RequestMapping("setMealFee.ajax")
	public ResultVO setBaseMealFee(HttpServletRequest request
			,int eduSeq
			,int mealFee
			,String userId
			) {
		
		ResultVO result = ajaxService.setMealFee(eduSeq,mealFee,userId);
		return result;
	}
	
	@ResponseBody
	@RequestMapping("setDormiFee.ajax")
	public ResultVO setBaseDormiFee(HttpServletRequest request
			,int eduSeq
			,int dormiFee
			,String userId
			) {
		
		ResultVO result = ajaxService.setDormiFee(eduSeq,dormiFee,userId);
		return result;
	}
	
	@ResponseBody
	@RequestMapping("setDepositYn.ajax")
	public ResultVO setBaseDepositYn(HttpServletRequest request
			,int eduSeq
			,String depositYn
			,String userId
			) {
		
		ResultVO result = ajaxService.setDepositYn(eduSeq,depositYn,userId);
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="setSendEmail.ajax")
	public CheckVO setSendEmail(ModelMap model
			,@RequestParam String userId
			,@RequestParam String fromAddr
			,@RequestParam String toAddrList
			,@RequestParam String toMsg
			) {
		
		CheckVO result = new CheckVO();
		
		if(ObjectUtils.isEmpty(toAddrList)){
			result.setMsg("수신주소 오류");
			return result;
		}
		if(ObjectUtils.isEmpty(toMsg)){
			result.setMsg("이메일내용 오류");
			return result;
		}
		
		String[] toAddrs = toAddrList.split(",");
		int failCnt = 0;
		for (String toAddr : toAddrs) {
			result =  emailService.setSendEmail(fromAddr, toAddr, toMsg, new Date(), 0, userId, 1,"");
			if (result.getResult() == 0) {
				failCnt++;
			}
		}
		if (failCnt > 0) {
			result.setResult(0);
			result.setMsg(toAddrs.length + "건 중 " + failCnt + " 건 오류");
		}
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="setSendSms2.ajax")
	public CheckVO setSendSms2(ModelMap model
			,@RequestParam String fromNum
			,@RequestParam String toNumList
//			,@RequestParam Object toMsg
			,@RequestParam String userId
			
			,@RequestParam String eduNm
			,@RequestParam String eduPeriodBegin
			,@RequestParam String eduPeriodEnd
			,@RequestParam String mealAccount
			,@RequestParam String dormiAccount
			,@RequestParam String depositLimitDt
			,@RequestParam String mealFeeList
			,@RequestParam String dormiFeeList
			) {
		
		CheckVO result = new CheckVO();
		
		if(ObjectUtils.isEmpty(toNumList)){
			result.setMsg("수신번호 오류");
			return result;
		}
//		if(ObjectUtils.isEmpty(toMsg)){
//			result.setMsg("문자내용 오류");
//			return result;
//		}
		
		String[] toNums = toNumList.split(",");
		int failCnt = 0;

		String tplCd = "ktl_edu_002";
		String[] mealFees = mealFeeList.split(",");
		String[] dormiFees = dormiFeeList.split(",");
		for (int i = 0; i < toNums.length; i++) {
			String mealFee = String.format("%,d", Integer.parseInt(mealFees[i]));
			String dormiFee = String.format("%,d", Integer.parseInt(dormiFees[i]));
			String msg = VarComponent.getAlimTalkMsg(tplCd, eduNm, eduPeriodBegin, eduPeriodEnd, mealFee, dormiFee, mealAccount, dormiAccount, depositLimitDt);
			
			result =  smsService.setSendSms(fromNum, toNums[i], msg, new Date(), 0, userId, 1, "");
			if (result.getResult() == 0) {
				failCnt++;
			}
		}
		if (failCnt > 0) {
			result.setResult(0);
			result.setMsg(toNums.length + "건 중 " + failCnt + " 건 오류");
		}
		
		return result;
	}
}
