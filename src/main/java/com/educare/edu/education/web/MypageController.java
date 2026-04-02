package com.educare.edu.education.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.educare.edu.comn.mapper.TableMapper;
import com.educare.edu.comn.service.CheckService;
import com.educare.edu.comn.service.PayBackService;
import com.educare.edu.comn.vo.CategoryVO;
import com.educare.edu.comn.vo.PayVO;
import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.comn.vo.TimeTableVO;
import com.educare.edu.education.service.CategoryService;
import com.educare.edu.education.service.EduService;
import com.educare.edu.education.service.EduVO;
import com.educare.edu.education.service.SurveyService;
import com.educare.edu.education.service.model.Lecture;
import com.educare.edu.education.service.model.LectureRcept;
import com.educare.edu.education.service.model.LectureStdnt;
import com.educare.edu.education.service.model.LectureSurvey;
import com.educare.edu.lclike.service.LclikeService;
import com.educare.edu.member.service.MemberService;
import com.educare.edu.member.service.SessionUserInfoHelper;
import com.educare.edu.member.service.model.UserInfo;
import com.educare.util.CryptoUtil;
import com.educare.util.DateUtil;
import com.educare.util.LncUtil;
import com.google.gson.JsonObject;

/**
 * @Class Name : MypageController.java
 * @author SI개발팀 박용주
 * @since 2020. 8. 5.
 * @version 1.0
 * @see
 * @Description 마이페이지용 교육관련 Controller
 * 
 * <pre>
 *
 *
 * 수정일		    	수정자		    	수정내용
 * --------	  		---------	  		------------------------
 * 2020. 8. 5.	  	SI개발팀 박용주     		최초생성 
 * </pre>
 */
@Controller
@RequestMapping(value="/user/mypage/")
public class MypageController {
	
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
	/** 체크 서비스 */
	@Resource(name = "CheckService")
	private CheckService checkService;
	
	@Resource(name = "TableMapper")
	private TableMapper tableMapper;
	
	@Resource(name = "PayBackService")
	private PayBackService  payBackService;
	@Resource(name = "LclikeService")
	private LclikeService  lclikeService;
	
	/**
	 * 교육신청을 취소한다.
	 * @param model
	 * @param vo
	 * @return
	 */
	@RequestMapping("surveyRgs.json")
	public String surveyRgs(
			ModelMap model,
			LectureSurvey lectureSurvey 
			) {
		
		JsonObject jsonObj = new JsonObject();
		
		boolean isLogined = SessionUserInfoHelper.isLogined();
		boolean isSuccess = false;
		String message = "";
		
		if(isLogined){
			surveyService.saveServey(lectureSurvey);
		}
		
		jsonObj.addProperty("isLogined", isLogined);
		jsonObj.addProperty("isSuccess", isSuccess);
		jsonObj.addProperty("message", message);
		model.addAttribute("obj", jsonObj);
		
        return "obj";
    } 
	
	@RequestMapping("instrctrEdu{gubun}List.do")
	public String instrctrEduList(
			ModelMap model, 
			@ModelAttribute("vo") EduVO vo
			,@PathVariable String gubun
			) {
		
		if(!SessionUserInfoHelper.isLogined()){
			model.addAttribute("message", "접근 권한이 없습니다.");
			model.addAttribute("moveUrl", "/user/index.do");	
			return "alert";
		}else{
			if(!UserInfo.MEM_LVL_INSTRCTR.equals(SessionUserInfoHelper.getUserMemLvl())){
				model.addAttribute("message", "접근 권한이 없습니다.");
				model.addAttribute("moveUrl", "/user/index.do");	
				return "alert";
			}
		}
		
		List<CategoryVO> eduList = categoryService.getCategoryEduList();
		
		model.addAttribute("eduList", eduList);
		model.addAttribute("vo", vo);
		model.addAttribute("gubun",gubun);
		
        return "/user/mypage/instrctrEduList" + LncUtil.TILES;
    }
	/**
	 * 강사 교육 내역
	 * @param model
	 * @param vo
	 * @return
	 */
	@RequestMapping("instrctrEduList.json")
	public ModelAndView instrctrEduListJson(
			ModelMap model,
			@ModelAttribute("vo") EduVO vo) {
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("jsonView");
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		vo.setUserId(SessionUserInfoHelper.getUserId());
		vo.setSrchDate(DateUtil.getDate2Str(new Date(), "yyyy-MM-dd"));
		resultMap = eduService.getLectureInstrctrList(vo);

		mv.addObject("pageNavi", resultMap.get("paginationInfo"));
		mv.addObject("totalCnt", resultMap.get("totalCnt"));
		mv.addObject("dataList", resultMap.get("dataList"));
		mv.addObject("vo", vo);
		
		return mv;
    }
	@RequestMapping("instrctrEdu{gubun}View.do")
	public String instrctrEduDetail(
			ModelMap model,
			@PathVariable String gubun,
			@ModelAttribute("vo") EduVO vo) {
		
		String jspPath = "/user/mypage/popup/instrctrEduView" + LncUtil.TILES;
		Lecture lctre = eduService.getLctreDetail(vo.getEduSeq());
		
		//시간표
		List<TimeTableVO> timeList = eduService.getTimeTableList(lctre,null);
		
		if(!lctre.getInstrctrId().equals(SessionUserInfoHelper.getUserId())){
			model.addAttribute("rtnMsg","에러");
			return jspPath;
		}
		model.addAttribute("rtnMsg","");
		model.addAttribute("lctre", lctre);
		model.addAttribute("timeList", timeList);
		model.addAttribute("vo", vo);
		
		return jspPath;
    }
	
	//나의 강의 내역 통합
	@RequestMapping("myEdu{gubun}List.do")
	public String myEduList(
			ModelMap model, 
			@ModelAttribute("vo") EduVO vo,
			@PathVariable String gubun
			) {
		
		if(!SessionUserInfoHelper.isLogined()){
			model.addAttribute("message", "로그인이 필요한 서비스입니다.");
			model.addAttribute("moveUrl", "/user/login.do?rtnUri=/user/mypage/myEduOpenList.do?1=1");	
			return "alert";
		}else{
			if(!(UserInfo.MEM_LVL_STDNT+","+UserInfo.MEM_LVL_STDNT2).contains(SessionUserInfoHelper.getUserMemLvl())){
				model.addAttribute("message", "접근 권한이 없습니다.");
				model.addAttribute("moveUrl", "/user/index.do");	
				return "alert";
			}
		}
		
		List<CategoryVO> eduList = categoryService.getCategoryEduList();
		
		model.addAttribute("eduList", eduList);
		model.addAttribute("vo", vo);
		model.addAttribute("gubun",gubun);
		
        return "/user/mypage/myEduList" + LncUtil.TILES;
    }
	@RequestMapping("myEduList.json")
	public String myEduListJson(
			ModelMap model,
			String gubun,
			@ModelAttribute("vo") EduVO vo) {
		//구분에따라
		String userId = SessionUserInfoHelper.getUserId();
		vo.setUserId(userId);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap = eduService.getMyEduList(vo);
		
		model.addAttribute("pageNavi", resultMap.get("paginationInfo"));
		model.addAttribute("totalCnt", resultMap.get("totalCnt"));
		model.addAttribute("dataList", resultMap.get("dataList"));
		model.addAttribute("vo", vo);
		
		return "jsonView";
    }
	@RequestMapping("myEdu{gubun}View.do")
	public String myEduView(
			ModelMap model, 
			@PathVariable String gubun,
			@ModelAttribute("vo") EduVO vo
			) {
		int eduSeq = vo.getEduSeq();
		String userId = SessionUserInfoHelper.getUserId();
		
		Lecture lctre = eduService.getLctreDetail(vo.getEduSeq());
		vo.setUserId(userId);
		LectureStdnt stdnt = eduService.getMyAtnlcDetail(eduSeq,userId);
		int rceptSeq = stdnt.getRceptSeq();
		LectureRcept rcept = eduService.getMyRceptDetail(rceptSeq,eduSeq,userId);
		List<TimeTableVO> timeList = eduService.getTimeTableList(lctre,userId);
		
		model.addAttribute("lctre", lctre);
		model.addAttribute("rcept", rcept);
		model.addAttribute("stdnt", stdnt);
		model.addAttribute("timeList", timeList);
		
		model.addAttribute("vo", vo);
		
        return "/user/mypage/myEduView" + LncUtil.TILES;
    }
	
	
	//나의 결제내역
	@RequestMapping("myLcRceptList.do")
	public String myLcRceptList(ModelMap model
			,@ModelAttribute EduVO vo
			) {
		
		if(!SessionUserInfoHelper.isLogined()){
			model.addAttribute("message", "로그인이 필요한 서비스입니다.");
			model.addAttribute("moveUrl", "/user/login.do");
			return "alert";
		}else{
			if(!(UserInfo.MEM_LVL_STDNT+","+UserInfo.MEM_LVL_STDNT2).contains(SessionUserInfoHelper.getUserMemLvl())){
				model.addAttribute("message", "접근 권한이 없습니다.");
				model.addAttribute("moveUrl", "/user/index.do");	
				return "alert";
			}
		}
		String userId = SessionUserInfoHelper.getUserId();
		vo.setUserId(userId);
		vo.setSrchSort("REGDESC");
		ResultVO dataVO = eduService.getLcrcpPageList(vo,10);
		model.addAttribute("data", dataVO.getData());
		
		return "/user/mypage/myLcRceptList" + LncUtil.TILES;
	}
	//나의 결제내역
	@RequestMapping("myPayList.do")
	public String myEduList(
			ModelMap model
			) {
		
		if(!SessionUserInfoHelper.isLogined()){
			model.addAttribute("message", "로그인이 필요한 서비스입니다.");
			model.addAttribute("moveUrl", "/user/index.do");	
			return "alert";
		}else{
			if(!(UserInfo.MEM_LVL_STDNT+","+UserInfo.MEM_LVL_STDNT2).contains(SessionUserInfoHelper.getUserMemLvl())){
				model.addAttribute("message", "접근 권한이 없습니다.");
				model.addAttribute("moveUrl", "/user/index.do");	
				return "alert";
			}
		}
		
		PayVO param = new PayVO();
		param.setUserId(SessionUserInfoHelper.getUserId());
		List<PayVO> payList = tableMapper.selectPayByUserId(param);
		model.addAttribute("payList",payList);
		
        return "/user/mypage/myPayList" + LncUtil.TILES;
    }
	
	@RequestMapping("updPayBack.do")
	public String updPayBack(
			ModelMap model
			) {
		
		if(!SessionUserInfoHelper.isLogined()){
			model.addAttribute("message", "로그인이 필요한 서비스입니다.");
			model.addAttribute("moveUrl", "/user/index.do");	
			return "alert";
		}else{
			if(!(UserInfo.MEM_LVL_STDNT+",").contains(SessionUserInfoHelper.getUserMemLvl())){
				model.addAttribute("message", "접근 권한이 없습니다.");
				model.addAttribute("moveUrl", "/user/index.do");	
				return "alert";
			}
		}
		
		String userId = SessionUserInfoHelper.getUserId();
		ResultVO result = payBackService.getPayBackByUserId(userId);
		model.addAttribute("result",result);
		
        return "/user/mypage/updPayBack" + LncUtil.TILES;
    }
	
	//나의 결제내역
	@RequestMapping("myLclikeList.do")
	public String myLclikeList(ModelMap model
			,@RequestParam(defaultValue="1") Integer pageNo
			,@RequestParam(defaultValue="") String srchWrd
			) {
		
		if(!SessionUserInfoHelper.isLogined()){
			model.addAttribute("message", "로그인이 필요한 서비스입니다.");
			model.addAttribute("moveUrl", "/user/index.do");
			return "alert";
		}else{
			if(!(UserInfo.MEM_LVL_STDNT+","+UserInfo.MEM_LVL_STDNT2).contains(SessionUserInfoHelper.getUserMemLvl())){
				model.addAttribute("message", "접근 권한이 없습니다.");
				model.addAttribute("moveUrl", "/user/index.do");	
				return "alert";
			}
		}
		String sessId = SessionUserInfoHelper.getUserId();
		
		ResultVO dataVO = lclikeService.getLclikePageList(pageNo, 10, srchWrd);
		model.addAttribute("data", dataVO.getData());
		
		return "/user/mypage/myLclikeList" + LncUtil.TILES;
	}
}
