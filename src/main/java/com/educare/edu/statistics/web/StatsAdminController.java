package com.educare.edu.statistics.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.educare.edu.comn.mapper.LectureMapper;
import com.educare.edu.comn.model.Org;
import com.educare.edu.comn.vo.CategoryVO;
import com.educare.edu.comn.vo.FeedbackVO;
import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.education.service.CategoryService;
import com.educare.edu.education.service.EduService;
import com.educare.edu.education.service.EduVO;
import com.educare.edu.education.service.impl.EduMapper;
import com.educare.edu.education.service.model.Lecture;
import com.educare.edu.feedback.mapper.FeedbackMapper;
import com.educare.edu.feedback.service.FeedbackService;
import com.educare.edu.member.service.SessionUserInfoHelper;
import com.educare.edu.statistics.service.StatsService;
import com.educare.edu.statistics.service.StatsVO;
import com.educare.edu.statistics.service.model.EduStats;
import com.educare.edu.statistics.service.model.Tuition;
import com.educare.util.DateUtil;
import com.educare.util.FileUtil;
import com.educare.util.LncUtil;

@Controller
@RequestMapping(value="/admin/stats/")
public class StatsAdminController {
	
	/** 카테고리 서비스 */
	@Resource(name = "CategoryService")
	private CategoryService categoryService;
	
	/** 통계 서비스 */
	@Resource(name = "StatsService")
	private StatsService statsService;
	  
	/** 교육관리 서비스 */ 
	@Resource(name = "EduService")
	private EduService eduService;
	
	@Resource(name="FeedbackService")
	private FeedbackService feedbackService;
	
	@Resource(name="EduMapper")
	private EduMapper eduMapper;
	
	@Resource(name="LectureMapper")
	private LectureMapper lectureMapper;
	
	/**
	 * 카테고리별 통계
	 * @param model
	 * @return
	 */
	@RequestMapping("statCtgryList.do")
	public String lctreList(ModelMap model
			,@ModelAttribute("vo") StatsVO vo
			,@RequestParam(defaultValue="N") String excelYn
			) {
		//카테고리 
		List<CategoryVO> cateList = categoryService.getCategoryEduList();
		List<CategoryVO> cateList2 = categoryService.getCategoryChildList(vo.getSrchCtgry());
		List<CategoryVO> cateList3 = categoryService.getCategoryChildList(vo.getSrchCtgry2());
		model.addAttribute("cateList", cateList);
		model.addAttribute("cateList2", cateList2);
		model.addAttribute("cateList3", cateList3);
		
		ResultVO result = statsService.getStatCtgryList(vo);
		model.addAttribute("data", result.getData());
		
		if(excelYn.equals("Y")){
			return "/admin/stats/statCtgryList_excel";
		}
		
        return "/admin/stats/statCtgryList" + LncUtil.TILES;
    }
	@RequestMapping("statInstrctrList.do")
	public String statInstrctrList(ModelMap model
			,@ModelAttribute("vo") StatsVO vo
			,@RequestParam(defaultValue="N") String excelYn
			) {
		//카테고리 
		List<CategoryVO> cateList = categoryService.getCategoryEduList();
		List<CategoryVO> cateList2 = categoryService.getCategoryChildList(vo.getSrchCtgry());
		List<CategoryVO> cateList3 = categoryService.getCategoryChildList(vo.getSrchCtgry2());
		model.addAttribute("cateList", cateList);
		model.addAttribute("cateList2", cateList2);
		model.addAttribute("cateList3", cateList3);
		
		ResultVO result = statsService.getStatInstrctrList(vo);
		model.addAttribute("data", result.getData());
		
		if(excelYn.equals("Y")){
			return "/admin/stats/statInstrctrList_excel";
		}
		
		return "/admin/stats/statInstrctrList" + LncUtil.TILES;
	}
	@RequestMapping("statRoomList.do")
	public String statRoomList(ModelMap model
			,@ModelAttribute("vo") StatsVO vo
			,@RequestParam(defaultValue="N") String excelYn
			) {
		//카테고리 
		List<CategoryVO> cateList = categoryService.getCategoryEduList();
		List<CategoryVO> cateList2 = categoryService.getCategoryChildList(vo.getSrchCtgry());
		List<CategoryVO> cateList3 = categoryService.getCategoryChildList(vo.getSrchCtgry2());
		model.addAttribute("cateList", cateList);
		model.addAttribute("cateList2", cateList2);
		model.addAttribute("cateList3", cateList3);
		
		ResultVO result = statsService.getStatRoomList(vo);
		model.addAttribute("data", result.getData());
		
		if(excelYn.equals("Y")){
			return "/admin/stats/statRoomList_excel";
		}
		
		return "/admin/stats/statRoomList" + LncUtil.TILES;
	}
	@RequestMapping("statCertList.do")
	public String statCertList(ModelMap model
			,@ModelAttribute("vo") StatsVO vo
			,@RequestParam(defaultValue="N") String excelYn
			) {
		//카테고리 
		List<CategoryVO> cateList = categoryService.getCategoryEduList();
		List<CategoryVO> cateList2 = categoryService.getCategoryChildList(vo.getSrchCtgry());
		List<CategoryVO> cateList3 = categoryService.getCategoryChildList(vo.getSrchCtgry2());
		model.addAttribute("cateList", cateList);
		model.addAttribute("cateList2", cateList2);
		model.addAttribute("cateList3", cateList3);
		
		//ResultVO result = statsService.getStatInstrctrList(vo);
		//model.addAttribute("data", result.getData());
		
		if(excelYn.equals("Y")){
			return "/admin/stats/statCertList_excel";
		}
		
		return "/admin/stats/statCertList" + LncUtil.TILES;
	}
	
	/**
	 * 설문조사 통계 시작
	 * @param req
	 * @param vo
	 * @param listRow
	 * @param model
	 * @return
	 */
	@RequestMapping("statFbLctreList.do")
	public String statFbLctreList(HttpServletRequest req
			,@ModelAttribute(value="vo") StatsVO vo
			,@RequestParam(defaultValue="10") Integer listRow
			,ModelMap model) {
		
		//카테고리 
		List<CategoryVO> cateList = categoryService.getCategoryEduList();
		List<CategoryVO> cateList2 = categoryService.getCategoryChildList(vo.getSrchCtgry());
		List<CategoryVO> cateList3 = categoryService.getCategoryChildList(vo.getSrchCtgry2());
		model.addAttribute("cateList", cateList);
		model.addAttribute("cateList2", cateList2);
		model.addAttribute("cateList3", cateList3);
		//if(!ObjectUtils.isEmpty(vo.getEduYear())){
			ResultVO result = statsService.getStatLctrePageList(vo,listRow);
			model.addAttribute("data", result.getData());
		//}
		
		return "/admin/stats/statFbLctreList" + LncUtil.TILES;
    }
	
	@RequestMapping("statFbLctreDetail.do")
	public String statFbDetail(HttpServletRequest req,HttpServletResponse res,ModelMap model
			,@RequestParam List<Integer> eduSeqChk
			,@RequestParam(defaultValue="N") String excelYn
			) throws Exception {
		
		//체크한 수업 설문지 동일한지 검사
		int fbIdx = feedbackService.getFbIdxByEduSeqArr(eduSeqChk);
		if(fbIdx==0){
			LncUtil.alertBack(res, "설문지가 존재하지 않거나, 동일한 설문지가 아닙니다.");
			return null;
		}
		
		//설문지 가져오기
		Map<String, Object> feedbackInfo = feedbackService.getFeedbackInfo(fbIdx );
		
		//설문지 답 세팅하기
		feedbackService.setResultAll(feedbackInfo,eduSeqChk);
		
		model.addAttribute("feedbackInfo",feedbackInfo);
		model.addAttribute("eduSeqChk",eduSeqChk);
		
		
		if(excelYn.equals("Y")){
			return "/admin/stats/statFbDetail_excel";
		}
		
		return "/admin/stats/statFbDetail" + LncUtil.TILES;
    }
	
}
