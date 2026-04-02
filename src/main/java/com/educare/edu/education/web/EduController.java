package com.educare.edu.education.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.educare.component.UtilComponent;
import com.educare.component.VarComponent;
import com.educare.edu.comn.mapper.LectureSubjectMapper;
import com.educare.edu.comn.mapper.TableMapper;
import com.educare.edu.comn.model.LectureSubject;
import com.educare.edu.comn.model.LectureTime;
import com.educare.edu.comn.model.Org;
import com.educare.edu.comn.service.CheckService;
import com.educare.edu.comn.service.SmartcheckService;
import com.educare.edu.comn.service.SyncDataService;
import com.educare.edu.comn.service.TableService;
import com.educare.edu.comn.vo.CategoryVO;
import com.educare.edu.comn.vo.CheckVO;
import com.educare.edu.comn.vo.LectureDormiVO;
import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.education.service.AttachService;
import com.educare.edu.education.service.CategoryService;
import com.educare.edu.education.service.EduService;
import com.educare.edu.education.service.EduVO;
import com.educare.edu.education.service.LctreRceptService;
import com.educare.edu.education.service.LctreService;
import com.educare.edu.education.service.SurveyService;
import com.educare.edu.education.service.model.Lecture;
import com.educare.edu.education.service.model.LectureAttach;
import com.educare.edu.education.service.model.LectureRcept;
import com.educare.edu.education.service.model.LectureStdnt;
import com.educare.edu.education.service.model.LectureSurvey;
import com.educare.edu.lclike.service.LclikeService;
import com.educare.edu.member.service.MemberService;
import com.educare.edu.member.service.SessionUserInfoHelper;
import com.educare.edu.member.service.model.UserInfo;
import com.educare.edu.member.service.model.UserInfoStdnt;
import com.educare.edu.tmpFile.service.TempFileService;
import com.educare.util.CryptoUtil;
import com.educare.util.DateUtil;
import com.educare.util.FileUtil;
import com.educare.util.LncUtil;
import com.educare.util.XmlBean;
import com.google.gson.JsonArray;

/**
 * @Class Name : EduController.java
 * @author SI개발팀 박용주
 * @since 2020. 7. 22.
 * @version 1.0
 * @see
 * @Description 교육 사용자 컨트롤러
 * 
 * <pre>
 *
 *
 * 수정일		    	수정자		    	수정내용
 * --------	  		---------	  		------------------------
 * 2020. 7. 22.	  	SI개발팀 박용주     		최초생성 
 * </pre>
 */
@Controller
@RequestMapping(value="/user/edu/")
public class EduController {
	
	/** org.slf4j.Logger */
	private static final Logger LOG = LoggerFactory.getLogger(EduController.class);
	
	/** 카테고리 서비스 */
	@Resource(name = "CategoryService")
	private CategoryService categoryService;
	
	/** 교육관리 서비스 */
	@Resource(name = "EduService")
	private EduService eduService;
	
	/** 회원 서비스 */
	@Resource(name = "MemberService")
	private MemberService memberService;
	
	/** 설문조사 서비스 */
	@Resource(name = "SurveyService")
	private SurveyService surveyService;
	
	/** 첨부파일 서비스 */
	@Resource(name = "AttachService")
	private AttachService attachService;
	
	/** 공통 테이블 서비스 */
	@Resource(name = "TableService")
	private TableService tableService;
	
	/** 전자출결 서비스 */
	@Resource(name = "SmartcheckService")
	private SmartcheckService smartcheckService;
	
	/** 검사 서비스 */
	@Resource(name = "CheckService")
	private CheckService checkService;
	@Resource(name = "TableMapper")
	private TableMapper tableMapper;
	@Resource(name = "LctreRceptService")
	private LctreRceptService lctreRceptService;
	@Resource(name = "LctreService")
	private LctreService lctreService;
	@Resource(name = "utcp")
	private UtilComponent utcp;
	@Resource(name = "LclikeService")
	private LclikeService lclikeService;
	@Resource(name = "SyncDataService")
	private SyncDataService syncDataService;
	@Resource(name = "LectureSubjectMapper")
	private LectureSubjectMapper lectureSubjectMapper;
	
	
	/**
	 * JsonArray를 response로 write
	 * @param jsonArr com.google.gson.JsonArray
	 * @param response javax.servlet.http.HttpServletResponse
	 */
	private void setJsonArrayToResponse(JsonArray jsonArr, HttpServletResponse response) {
		if(jsonArr != null && jsonArr.size() > 0) {
			response.setContentType( "application/json" );
			response.setHeader( "Content-Type","text/json" );
			response.setCharacterEncoding( "UTF-8" );
			PrintWriter writer = null;
			
			try {
				writer = response.getWriter();
				writer.write( jsonArr.toString() );
			} catch (IOException e) {
				LOG.error( e.getClass().getName() + " ::: " + e.getMessage() );
			} 
		}
	}
	
	/**
	 * 교육일정
	 * @param model
	 * @return
	 */
	@RequestMapping("eduList.do")
	public String eduList(
			ModelMap model,
			@ModelAttribute("vo") EduVO vo) {
		
		Date curDate = new Date();
		if(vo.getSrchYear() == null || "".equals(vo.getSrchYear())){
			vo.setSrchYear(DateUtil.getDate2Str(curDate, "yyyy"));
		}
		
		if(vo.getSrchMonth() == null || "".equals(vo.getSrchMonth())){
			vo.setSrchMonth(DateUtil.getDate2Str(curDate, "MM"));
		}
		
		//List<Lecture> bannerList = eduService.getLctreBannerList(vo);
		List<CategoryVO> cateList = categoryService.getCategoryEduList();
		//List<Lecture> lctreList = eduService.getLctreUserList(vo);
		//List<Org> orgList = tableService.getOrgList();
		
		
		model.addAttribute("cateList", cateList);
		//model.addAttribute("lctreList", lctreList);
		//model.addAttribute("bannerList", bannerList);
		model.addAttribute("vo", vo);
		//model.addAttribute("orgList",orgList);
		
        return "/user/edu/eduList" + LncUtil.TILES;
    }
	
	/**
	 * 교육일정 조회
	 * @param model
	 * @param response
	 * @param vo
	 */
	@RequestMapping("eduList.json")
	public String eduListAjax(
			ModelMap model,
			HttpServletResponse response,
			@ModelAttribute("vo") EduVO vo) {
		
		List<Lecture> lctreList = eduService.getLctreUserList(vo);
		
		model.addAttribute("lctreList",lctreList);
		return "jsonView";
    }
	
	/**
	 * 교육일정 캘린더
	 * @param model
	 * @return
	 */
	@RequestMapping("edCal.do")
	public String eduCal(
			ModelMap model,
			@ModelAttribute("vo") EduVO vo) {
		
		Date curDate = new Date();
		if(vo.getSrchYear() == null || "".equals(vo.getSrchYear())){
			vo.setSrchYear(DateUtil.getDate2Str(curDate, "yyyy"));
		}
		
		if(vo.getSrchMonth() == null || "".equals(vo.getSrchMonth())){
			vo.setSrchMonth(DateUtil.getDate2Str(curDate, "MM"));
		}
		
		List<CategoryVO> cateList = categoryService.getCategoryEduList();
		List<Org> orgList = tableService.getOrgList();
		
		
		
		model.addAttribute("vo", vo);
		model.addAttribute("cateList", cateList);
		model.addAttribute("orgList",orgList);
		
        return "/user/edu/eduCal" + LncUtil.TILES;
    }
	@RequestMapping("eduCal2.do")
	public String eduCalJson(
			ModelMap model,
			@ModelAttribute("vo") EduVO vo) {
		
		Date curDate = new Date();
		if(vo.getSrchYear() == null || "".equals(vo.getSrchYear())){
			vo.setSrchYear(DateUtil.getDate2Str(curDate, "yyyy"));
		}
		
		if(vo.getSrchMonth() == null || "".equals(vo.getSrchMonth())){
			vo.setSrchMonth(DateUtil.getDate2Str(curDate, "MM"));
		}
		
		int year = Integer.parseInt(vo.getSrchYear());
		int month = Integer.parseInt(vo.getSrchMonth());
		
		String startDay = DateUtil.getFirstDayOfWeek(year, month, 1, "yyyyMMdd");
		String lastDay = DateUtil.getLastDayOfMonth(year, month, 1, "yyyyMMdd");
		String endDay = DateUtil.getLastSaturdayOfCalendar(year, month, Integer.parseInt(lastDay.substring(6, 8)), "yyyyMMdd");
		

		//List<Lecture> lctreList = eduService.getLctreCalList(vo);
		List<Lecture> lctreList = eduService.getLctreUserList(vo);
		
		Map<Integer, String> calMap = LncUtil.getCalMap(startDay, endDay);
		Map<String, Object> lecMap = this.getLectureCalMap(vo.getSrchMonth(), startDay, endDay, lctreList);

		model.addAttribute("calMap", calMap);
		model.addAttribute("lecMap", lecMap);
		model.addAttribute("days", calMap.size());
		model.addAttribute("month", month);
		model.addAttribute("vo", vo);
		
		return "/user/edu/eduCal2";
    }
	@ResponseBody
	@RequestMapping("eduCal2.ajax")
	public ResultVO eduCalJson(ModelMap model
			,String srchYear
			,@RequestParam(defaultValue="0") Integer srchCtgry
			,@RequestParam(defaultValue="0") Integer srchCtgry2
			,@RequestParam(defaultValue="0") Integer srchCtgry3
			,String srchWrd
			,String srchColumn
			) {
		ResultVO result = eduService.getLctreYearInfo(srchYear, srchCtgry,srchCtgry2,srchCtgry3,srchWrd,srchColumn);
		return result;
    }
	
	/**
	 * 교육일정 상세보기
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("eduView.do")
	public String eduView(ModelMap model,HttpServletResponse res,HttpSession session,HttpServletRequest req
			,Integer eduSeq
			,@RequestParam(defaultValue="") String scrtyPw
			,@RequestParam(defaultValue="0") Integer srchCtgry
			) throws Exception {
		int eduSeqp = eduSeq;
		Lecture lctre = eduService.getLctreDetail(eduSeq);
		
		//미노출이면 접근 자체 차단
		//if("N".equals(lctre.getOpenYn())){
			//String clientIp = LncUtil.getIp(req);
			//LncUtil.alertBack(res, "접근할 수 없습니다.");
			//return null;
		//}
		
		//시간표
		List<LectureTime> timeList = eduService.getLectureTimeList(eduSeq);
		model.addAttribute("timeList",timeList);
		
		//시간계산,나중에 함수화 필요
		String eduTmStr = "";
		try {
			int totalEduMin = 0;
			for(LectureTime o: timeList){
				Date startDe = DateUtil.getStr2Date(o.getEduDt()+""+o.getStartTm(),"yyyyMMddHHmm");
				Date endDe = DateUtil.getStr2Date(o.getEduDt()+""+o.getEndTm(),"yyyyMMddHHmm");
				int eduMin = DateUtil.calMil(startDe, endDe);
				totalEduMin += eduMin;
			}
			LOG.info("totalEduMin : "+totalEduMin);
			eduTmStr = DateUtil.calculateStrTm(totalEduMin);
		} catch (NullPointerException e) {
			eduTmStr = "";
		}
		model.addAttribute("eduTmStr",eduTmStr);
		
		//비번 과정인지 체크
		if("Y".equals(lctre.getScrtyYn())){
			if(ObjectUtils.isEmpty(scrtyPw)){
				return "redirect:/user/edu/chkPwdRcept.do?eduSeq="+eduSeqp;
			}
			if(!lctre.getScrtyPw().equals(CryptoUtil.encodeSHA256CryptoNotDecode(scrtyPw))){
				LncUtil.alertBack(res, "비밀번호가 일치하지 않습니다.");
				return null;
			}
			session.setAttribute("SCRTY_PASS_"+eduSeqp, true);
		}
		
		String userId = SessionUserInfoHelper.getUserId();
		LectureStdnt stdnt = eduService.getMyAtnlcDetail(eduSeq, userId);
		if(stdnt != null){
			int rceptSeq = stdnt.getRceptSeq();
			LectureRcept rcept = eduService.getMyRceptDetail(rceptSeq,eduSeq,userId);
			model.addAttribute("rcept",rcept);
		}
		
		//교육자료 존재하면 불러온다.
		if( attachService.isAttach(eduSeqp,"01") ){
			model.addAttribute("isAttach",true);
		}
		
		model.addAttribute("lctre", lctre);
		model.addAttribute("attachList01", attachService.getAttachList(eduSeqp,"01"));
		
		//찜여부조회
		int checkLclike = lclikeService.checkLike(eduSeq,userId);
		model.addAttribute("checkLclike",checkLclike);
		
		model.addAttribute("srchCtgry",srchCtgry);
		
        return "/user/edu/eduView" + LncUtil.TILES;
    }
	
	/**
	 * 교육신청 페이지
	 * @param model
	 * @param vo
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("eduRcept.do")
	public String eduRcept(HttpServletResponse res,
			ModelMap model,
			Integer eduSeq,
			@RequestParam(defaultValue="") String scrtyPw,
			HttpServletRequest request) throws Exception {
		int eduSeqp = eduSeq;
		
		if(!SessionUserInfoHelper.isLogined()) {
			model.addAttribute("message", "로그인 후 이용하세요.");
			
			model.addAttribute("moveUrl", "/user/login.do?rtnUri=/user/edu/eduView.do?eduSeq="+eduSeqp);	
			return "alert";
		}else{
			if(SessionUserInfoHelper.isAdmin()){
				model.addAttribute("message", "관리자 계정으로 신청 할 수 없습니다.");
				model.addAttribute("moveUrl", "/user/login.do");	
				return "alert";
			}
			
			String userMemLvl = SessionUserInfoHelper.getUserMemLvl();
			
			if(userMemLvl != null && !userMemLvl.equals("9")){
				model.addAttribute("message", "정회원만 신청 가능합니다.");
				return "alert";
			}
		}
		
		String page = "/user/edu/eduRcept" + LncUtil.TILES;
		Lecture lctre = eduService.getLctreDetail(eduSeqp);
		UserInfo user = memberService.getStdntInfo(SessionUserInfoHelper.getUserId()); 
		
		//비번 과정인지 체크
		if("Y".equals(lctre.getScrtyYn())){
			if(checkService.checkEduScrtyPass(eduSeqp).getResult()!=1){
				LncUtil.alertBack(res, "비밀번호가 일치하지 않습니다.");
				return null;
			}
		}
		
		CheckVO checkRcept = lctreService.checkRcept(lctre.getEduSeq());
		if(checkRcept.getResult() != 2){
			LncUtil.alertBack(res, checkRcept.getMsg());
			return null;
		}
		
		ResultVO checkResult = checkService.checkEduRcept(lctre,user);
		if(checkResult.getResult()<1){
			LncUtil.alertBack(res, checkResult.getMsg());
			return null;
		}
		
		model.addAttribute("lctre", lctre);
		model.addAttribute("user", user);
		
        return page;
    }
	
	//개인정보 수정전 비밀번호 확인
	@RequestMapping("chkPwdRcept.do")
	public String chkPwdUser(HttpServletResponse res,ModelMap model,HttpSession session
			,int eduSeq
			,@RequestParam(defaultValue="") String scrtyPw
			) throws Exception{
		
		if(!SessionUserInfoHelper.isLogined()){
			model.addAttribute("message", "로그인이 필요합니다.");
			model.addAttribute("moveUrl", "/user/index.do");	
			return "alert";
		}
		
		if(!"7,8,9".contains(SessionUserInfoHelper.getUserMemLvl())){
			model.addAttribute("message", "접근 권한이 없습니다.");
			model.addAttribute("moveUrl", "/user/index.do");	
			return "alert";
		}
		
		Lecture lctre = eduService.getLctreDetail(eduSeq);
		model.addAttribute("lctre",lctre);
		
		model.addAttribute("eduSeq",eduSeq);
		
		return "/user/edu/chkPwdRcept" + LncUtil.TILES;
	}
	
	/**
	 * 추후결제 교육신청 처리
	 * @param model
	 * @param userId
	 * @param mobile
	 * @param payType
	 * @param eduSeq
	 * @param etc
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@ResponseBody
	@RequestMapping("rceptProc.ajax")
	public ResultVO rceptProc(ModelMap model,MultipartHttpServletRequest request
			,String mobile
			,String email
			,Integer eduSeq
			,String etc
			,@RequestParam(defaultValue="1") int feeTp
			,String etcIemDataJson
			,Integer dormiCapaCnt
			,String dormiAccessYn
			,Integer useTrans
			,@RequestParam(value="bsnLcnsFile", required=false) MultipartFile bsnLcnsFile
			) throws UnsupportedEncodingException {
		ResultVO result =  new ResultVO();
		
		String etcp = LncUtil.getEncode(etc);
		String etcIemDataJsonp = LncUtil.getEncode(etcIemDataJson);
		
		String userId=SessionUserInfoHelper.getUserId();
		
		int rceptSeq = 0;//신규는 0이다
		result = eduService.saveLctreRceptAfterPay(request,userId, mobile,email, "01", eduSeq, etcp, etcIemDataJsonp,rceptSeq,feeTp,dormiCapaCnt,dormiAccessYn,useTrans);
		
        return result;
    }
	/**
	 * -1:결제취소 먼저
	 * 1:취소 성공
	 * 0:취소안됨
	 */
	@ResponseBody
	@RequestMapping("cancelRcept.ajax")
	public ResultVO eduCancel(
			ModelMap model,
			@ModelAttribute("vo") EduVO vo, 
			HttpServletRequest request) {
		ResultVO result = new ResultVO();
		boolean isLogined = SessionUserInfoHelper.isLogined();
		
		//로그인여부
		if(!isLogined){
			result.setResult(0);
			result.setMsg("로그아웃 되었습니다.");
			return result;
		}
		
		int eduSeq = vo.getEduSeq();
		String userId = SessionUserInfoHelper.getUserId();
		int rceptSeq = vo.getRceptSeq();
		
		//관리자면 파라미터로 받아온 아이디로 세팅
		if(SessionUserInfoHelper.isAdmin()){
			userId = vo.getUserId();
		}
		ResultVO checkCancel = checkService.checkCancel(rceptSeq,eduSeq,userId);
		
		//유료수업이고 결제를 했다면 결제취소창으로
		if(checkCancel.getResult()==6){
			result.setResult(-1);
			return result;
		}
		if(checkCancel.getResult()!=1){
			return checkCancel;
		}
		
		result = eduService.cancelRcept(rceptSeq,eduSeq,userId);
		return result;
		
    }
	
	@RequestMapping("eduRceptResult.do")
	public String eduRceptResult(ModelMap model
			,@RequestParam(defaultValue="0") int eduSeq
			){
		EduVO vo = new EduVO();
		vo.setEduSeq(eduSeq);
		String userId = SessionUserInfoHelper.getUserId();
		
		LectureStdnt stdnt = eduService.getMyAtnlcDetail(eduSeq, userId);
		if(stdnt==null){
			model.addAttribute("message","교육신청이 완료되지 않았씁니다.");
			//return "alert";
		}
		Lecture lctre = eduService.getLctreDetail(eduSeq);
		model.addAttribute("lctre",lctre);
		
		return "/user/edu/eduRceptResult" + LncUtil.TILES;
	}
	
	
	
	/**
	 * 수강생목록 조회
	 * @param model
	 * @param vo
	 */
	@RequestMapping("lectureStdntList.json")
	public ModelAndView lectureStdntList(
			ModelMap model,
			@ModelAttribute("vo") EduVO vo) {
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("jsonView");
		
		String userMemLvl = SessionUserInfoHelper.getUserMemLvl();
		if("9".equals(userMemLvl)){
			mv.addObject("isAdmin", false);	
			return mv;
		}
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap = eduService.getLectureStdntInfo(vo);

		mv.addObject("pageNavi", resultMap.get("paginationInfo"));
		mv.addObject("totalCnt", resultMap.get("totalCnt"));
		mv.addObject("dataList", resultMap.get("dataList"));
		mv.addObject("lectTimeCnt", resultMap.get("lectTimeCnt"));
		mv.addObject("passCnt", resultMap.get("passCnt"));
		mv.addObject("testCnt", resultMap.get("testCnt"));
		mv.addObject("test2Cnt", resultMap.get("test2Cnt"));
		mv.addObject("isAdmin", true);	
		mv.addObject("vo", vo);
		
		return mv;
    }
	
	/**
	 * 단체수강등록 교육생 목록 조회
	 * @param model
	 * @param EduVO
	 */
	@RequestMapping("bulkStdntList.json")
	public ModelAndView bulkStdntList(
			ModelMap model,
			@ModelAttribute("vo") EduVO vo) {
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("jsonView");
		
		if(!SessionUserInfoHelper.isAdmin()){
			mv.addObject("isAdmin", false);
			return mv;
		}
		
		List<UserInfo> dataList = memberService.getStdntBulkList(vo);
		
		mv.addObject("dataList", dataList);
		mv.addObject("isAdmin", true);
		
		return mv;
    }
	
	/**
	 * 강사 교육내역 조회
	 * @param model
	 * @param vo
	 */
	@RequestMapping("instrctrEduList.json")
	public ModelAndView instrctrEduList(
			ModelMap model,
			@ModelAttribute("vo") EduVO vo) {
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("jsonView");
		
		if(!SessionUserInfoHelper.isAdmin()){
			mv.addObject("isAdmin", false);	
			return mv;
		}
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap = eduService.getInstrctrEduListList(vo);

		mv.addObject("pageNavi", resultMap.get("paginationInfo"));
		mv.addObject("totalCnt", resultMap.get("totalCnt"));
		mv.addObject("dataList", resultMap.get("dataList"));
		mv.addObject("isAdmin", true);	
		mv.addObject("vo", vo);
		
		return mv;
    }
	
	
	/**
	 * 회원 교육 이수내역 조회
	 * @param model
	 * @param vo
	 */
	@RequestMapping("eduMemberList.json")
	public ModelAndView eduMemberList(
			ModelMap model,
			@ModelAttribute("vo") EduVO vo) {
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("jsonView");
		
		if(!SessionUserInfoHelper.isAdmin()){
			mv.addObject("isAdmin", false);	
			return mv;
		}
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap = eduService.getEduMemberList(vo);

		mv.addObject("pageNavi", resultMap.get("paginationInfo"));
		mv.addObject("totalCnt", resultMap.get("totalCnt"));
		mv.addObject("dataList", resultMap.get("dataList"));
		mv.addObject("isAdmin", true);	
		mv.addObject("vo", vo);
		
		return mv;
    }
	
	/**
	 * 설문조사 결과를 조회한다.
	 * @param model
	 * @param vo
	 * @return
	 */
	@RequestMapping("surveyView.json")
	public ModelAndView surveyView(
			ModelMap model,
			@ModelAttribute("vo") EduVO vo
			) {
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("jsonView");
		
		if(!SessionUserInfoHelper.isAdmin()){
			mv.addObject("isAdmin", false);	
			return mv;
		}
		
		LectureSurvey survey = surveyService.getSurvey(vo);
			
		mv.addObject("survey", survey);
		mv.addObject("isAdmin", true);	
		mv.addObject("vo", vo);
		
		return mv;
	}
	
	/**
	 * 증빙서류 출력
	 * @param model
	 * @return
	 */
	@RequestMapping("certView.do")
	public String certView(ModelMap model
			,int eduSeq
			,int subSeq
			,@RequestParam(defaultValue="1") Integer mode
			,String userId
			) {
		
		if(!SessionUserInfoHelper.isLogined()) {
			model.addAttribute("message", "로그인 후 이용하세요.");
			return "winClose";
		}
		//else{
			//if(!UserInfo.MEM_LVL_STDNT.equals(SessionUserInfoHelper.getUserMemLvl())){
			//	model.addAttribute("message", "접근권한이 없습니다.");
			//	return "winClose";
			//}
		//}

		if(!SessionUserInfoHelper.isAdmin()){
			userId = SessionUserInfoHelper.getUserId();
		}
		
		Map<String, Object> certMap = eduService.getCertInfo(eduSeq,subSeq,userId);
		model.addAttribute("certMap", certMap);
		
		int passIdx = 0;
		if(subSeq > 0){
			LectureSubject vo2 = new LectureSubject();
			vo2.setSubSeq(subSeq);
			LectureSubject sub = lectureSubjectMapper.selectByPk(vo2);
			if(mode == 1){
				passIdx = sub.getComplIdx();
			}else{
				passIdx = sub.getPassIdx();
			}
		}else{
			//수료증 템플릿 가져오기
			Lecture lecture = (Lecture) certMap.get("lctre");
			passIdx=lecture.getPassIdx();
			
			//수료면 발급여부 저장
			LectureStdnt stdnt = (LectureStdnt) certMap.get("stdnt");
			checkService.checkPassCertIssue(stdnt);
			
			
		}
		
		
        return "/cert/certView"+passIdx;
    }
	
	
	/**
	 * 교육 결과자료 첨부파일 다운로드
	 * @param fileSeq
	 * @param request
	 * @param response
	 */
	@RequestMapping("attach/{fileSeq}/download.do")
	public void download(
			@PathVariable( "fileSeq" ) Integer fileSeq,
			HttpServletRequest request,
			HttpServletResponse response
			) {
		
		LectureAttach attach = attachService.getAttach(fileSeq);
		
		String subPath = AttachService.EDU_ATTACH_PATH+attach.getEduSeq()+"/";
		String fileSave = attach.getFileRename();
		String fileOrg = attach.getFileOrg();
		//String filePath = TempFileService.TEMP_DOWNLOAD_PATH+subPath+fileSave;
		//NaverObjectStorage.download(subPath, fileSave);
		FileUtil.download(subPath+fileSave, fileOrg, request, response);
		//FileUtil.delete(filePath);
	}
	
	/**
	 * 압축해서 다운로드
	 * @param fileSeq
	 * @param request
	 * @param response
	 */
	@RequestMapping("attach/{eduSeq}/zipDownload.do")
	public void zipDownload(
			@PathVariable( "eduSeq" ) Integer eduSeq,
			HttpServletRequest request,
			HttpServletResponse response
			) {
		String subPath = AttachService.EDU_ATTACH_PATH+eduSeq+"/";
		String tmpPath = "tmp/";
		
		List<Map<String, String>> zipFileList = new ArrayList<Map<String,String>>();
		List<LectureAttach> attachList01 = attachService.getAttachList(eduSeq, "01");
		for(int i=0;i<attachList01.size();i++){
			Map<String, String> zipMap = new HashMap<String,String>();
			LectureAttach laMap = attachList01.get(i);
			zipMap.put("saveName", laMap.getFileRename());
			zipMap.put("orgName", laMap.getFileOrg());
			zipFileList.add(zipMap);
			
			//스토리지에서 서버로 다운로드
			//NaverObjectStorage.download(subPath, laMap.getFileRename());
			
			//새로 작업해야함,230313
			boolean isCopy = FileUtil.copy (XmlBean.getServerDataRoot()+subPath+laMap.getFileRename(), XmlBean.getServerDataRoot()+tmpPath+subPath+laMap.getFileRename(),true);
			if(!isCopy){
				try {
					response.setContentType( "text/html; charset=UTF-8" ); //리눅스 환경에서 스크립트가 동작 하지 않아 추가함
					response.getWriter().println( "<html  lang='ko'>" );
					response.getWriter().println( "<script  type='text/javascript'>" );
					response.getWriter().println( "alert('ERROR!');" );
					response.getWriter().println( "history.back(-1);" );
					response.getWriter().println( "</script><noscript></noscript>" );
					response.getWriter().println( "</html>" );
					return;
				} catch (IOException e) {
					LOG.error("ERROR:",e);
				}
			}
		}
		
		String zipNm = "edu_01.zip";
		String zipPath = XmlBean.getServerDataRoot()+tmpPath+subPath;
		int result = FileUtil.createZip(zipFileList,zipPath,zipNm);
		if(result==1){
			Lecture lecture = eduService.getLctreDetail(eduSeq);
			String eduNm = "";
			if(lecture!=null){
				eduNm=lecture.getEduNm();
			}
			FileUtil.download(tmpPath+subPath+zipNm, eduNm+" 교육자료.zip", request, response);
			//FileUtil.delete(zipPath+zipNm);
			
		}else{
			try {
				response.setContentType( "text/html; charset=UTF-8" ); //리눅스 환경에서 스크립트가 동작 하지 않아 추가함
				response.getWriter().println( "<html  lang='ko'>" );
				response.getWriter().println( "<script  type='text/javascript'>" );
				response.getWriter().println( "alert('File not found!');" );
				response.getWriter().println( "history.back(-1);" );
				response.getWriter().println( "</script><noscript></noscript>" );
				response.getWriter().println( "</html>" );
			} catch (IOException e) {
				LOG.error("ERROR:",e);
			}
		}
	}
	
	/**
	 * 수업신청서 다운로드
	 */
	@RequestMapping("attach/{eduSeq}/{userId}/rceptDownload.do")
	public void rceptDownload(
			@PathVariable( "eduSeq" ) Integer eduSeq,
			@PathVariable( "userId" ) String userId,
			HttpServletRequest request,
			HttpServletResponse response
			) {
		
		if(!SessionUserInfoHelper.isAdmin()){
			if(!SessionUserInfoHelper.getUserId().equals(userId)  ){
				try {
					response.setContentType( "text/html; charset=UTF-8" ); //리눅스 환경에서 스크립트가 동작 하지 않아 추가함
					response.getWriter().println( "<html  lang='ko'>" );
					response.getWriter().println( "<script  type='text/javascript'>" );
					response.getWriter().println( "alert('File not found!');" );
					response.getWriter().println( "history.back(-1);" );
					response.getWriter().println( "</script><noscript></noscript>" );
					response.getWriter().println( "</html>" );
				} catch (IOException e) {
					LOG.error("ERROR:",e);
				}
			}
		}
		
		LectureStdnt stdnt = eduService.getMyAtnlcDetail(eduSeq, userId);
		int rceptSeq = stdnt.getRceptSeq();
		LectureRcept lectureRcept = eduService.getMyRceptDetail(rceptSeq,eduSeq,userId);
		
		if(lectureRcept!=null){
			
			//String subPath = "upload/eduReq/"+eduSeq+"/";
			//String fileSave = lectureRcept.getFileRe();
			//String fileOrg = lectureRcept.getFileOrg();
			//String filePath = TempFileService.TEMP_DOWNLOAD_PATH + subPath + fileSave;
			//NaverObjectStorage.download(subPath, fileSave);
			//FileUtil.download(filePath, fileOrg, request, response);
			//FileUtil.delete(filePath);
			
			String fileRe = lectureRcept.getFileRe();
			String filePath = "upload/lctreRcept/"+eduSeq+"/";
			FileUtil.download(filePath+fileRe,lectureRcept.getFileOrg() , request, response);
		}else{
			try {
				response.setContentType( "text/html; charset=UTF-8" ); //리눅스 환경에서 스크립트가 동작 하지 않아 추가함
				response.getWriter().println( "<html  lang='ko'>" );
				response.getWriter().println( "<script  type='text/javascript'>" );
				response.getWriter().println( "alert('File not found!');" );
				response.getWriter().println( "history.back(-1);" );
				response.getWriter().println( "</script><noscript></noscript>" );
				response.getWriter().println( "</html>" );
			} catch (IOException e) {
				LOG.error("ERROR:",e);
			}
		}
	}
	
	@RequestMapping("bulkInstrctrList.json")
	public ModelAndView bulkInstrctrList(
			ModelMap model,
			@ModelAttribute("vo") EduVO vo) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("jsonView");
		
		if(!SessionUserInfoHelper.isAdmin()){
			mv.addObject("isAdmin", false);	
			return mv;
		}
		//vo.setOrgCd(SessionUserInfoHelper.getUserInfo().getOrgCd());//기관관리자 코드 검색 안함
		List<UserInfo> dataList = memberService.getInstrctrBulkList(vo);
		
		mv.addObject("dataList", dataList);
		mv.addObject("isAdmin", true);
		
		return mv;
    }
	
	
	@RequestMapping("getCategoryChildList.json")
	public void getCategoryChildList(
			ModelMap model,
			HttpServletResponse response,
			@RequestParam(defaultValue="0") int parentSeq) {
		
		List<CategoryVO> detailList = categoryService.getCategoryChildList(parentSeq);
		JsonArray jsonArr = eduService.convertDataListToJsonArray(detailList);
		
		setJsonArrayToResponse(jsonArr, response);
    }
	
	@ResponseBody
	@RequestMapping("lcrcpList.ajax")
	public ResultVO lcrcpList(
			ModelMap model,
			@ModelAttribute("vo") EduVO vo) {
		
		ResultVO result = eduService.getLcrcpPageList(vo, vo.getRow());
		return result;
    }
	/**
	 * 수강생목록 조회
	 * @param model
	 * @param vo
	 */
	@ResponseBody
	@RequestMapping("lectureStdntCertList.ajax")
	public ResultVO lectureStdntCertList(ModelMap model
			,@ModelAttribute("vo") EduVO vo) {
		
		ResultVO result = eduService.getLectureStdntCertList(vo);
		
		return result;
    }
	
	/**
	 * 해당월 교육일정을 반환한다.
	 * @param month
	 * @return
	 */
	private Map<String, Object> getLectureCalMap(String month, String startDay, String endDay, List<Lecture> list){
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		int calDays = 0;
		String dateKey = "";
		
		int sy = Integer.parseInt(startDay.substring(0, 4));
		int sm = Integer.parseInt(startDay.substring(4, 6));
		int sd = Integer.parseInt(startDay.substring(6, 8));
		
		calDays = LncUtil.calDays(startDay, endDay);
		
		for(int i = 0; i <= calDays; i++){
			Map<String, Object> dateMap = new HashMap<String,Object>();
			dateKey = DateUtil.dayCalForStr(sy, sm, sd, i, "", "yyyyMMdd");
			dateMap.put("calDt", dateKey);
			if(list != null && !list.isEmpty() && dateKey != null){
				List<Lecture> rceptList = new ArrayList<Lecture>();
				for(Lecture o : list){
					//if(dateKey.compareTo(o.getEduPeriodBegin().replaceAll("-", "")) >= 0 && dateKey.compareTo(o.getEduPeriodEnd().replaceAll("-", "")) <= 0){
					if(dateKey.equals(o.getEduPeriodBegin().replaceAll("-", "")) ){
						rceptList.add(o);
					}
				}
				dateMap.put("rceptList", rceptList);
			}
			result.put(dateKey, dateMap);
		}
		
		return result;
	}
}
