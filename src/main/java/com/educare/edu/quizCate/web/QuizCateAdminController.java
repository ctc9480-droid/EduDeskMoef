package com.educare.edu.quizCate.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.educare.edu.comn.vo.CategoryVO;
import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.education.service.EduVO;
import com.educare.edu.education.service.model.Category;
import com.educare.edu.member.service.SessionUserInfoHelper;
import com.educare.edu.quizCate.service.QuizCateService;
import com.educare.edu.quizCate.vo.QstnCategoryVO;
import com.educare.util.LncUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@Controller
@RequestMapping(value="/admin/testMng/")
public class QuizCateAdminController {
	
	/** 카테고리 서비스 */
	@Resource(name = "QuizCateService")
	private QuizCateService quizCateService; 
	
	/**
	 * 카테고리관리 리스트
	 * @param model
	 * @return 
	 */
	@RequestMapping("quizCateMng.do")
	public String ctgryList(ModelMap model
			,@RequestParam(defaultValue="0") Integer parentSeq1
			,@RequestParam(defaultValue="0") Integer parentSeq2
			) {
		int parentSeq1p = parentSeq1;
		int parentSeq2p = parentSeq2;
		if(parentSeq2p>0){
			//List<Category> cateList3 = quizCateService.getCategoryChildAllList(parentSeq2);
			List<QstnCategoryVO> cateList3 = quizCateService.getCategoryChildAllList(parentSeq2p);
			model.addAttribute("cateList3", cateList3);
			
			QstnCategoryVO vo1 = quizCateService.getCategory(parentSeq2p);
		 	if(vo1!= null){
		 		parentSeq1p = vo1.getParentSeq();
		 	}
		}
		if(parentSeq1p>0){
			//List<Category> cateList2 = quizCateService.getCategoryChildAllListAdmin(parentSeq1);
			List<QstnCategoryVO> cateList2 = quizCateService.getCategoryChildAllListAdmin(parentSeq1p);
			model.addAttribute("cateList2", cateList2);
		}
		
		//List<CategoryVO> cateList1 = quizCateService.getCategoryListAdmin(Category.DEPTH_EDU);
		List<QstnCategoryVO> cateList1 = quizCateService.getCategoryListAdmin(QstnCategoryVO.DEPTH_EDU);
		model.addAttribute("cateList1", cateList1);
		
		
		model.addAttribute("parentSeq1", parentSeq1p);
		model.addAttribute("parentSeq2", parentSeq2p);
		
        //return "/admin/edu/ctgryList" + LncUtil.TILES;
		return "/admin/quizCate/quizCtgryList" + LncUtil.TILES;
    }
	
	/**
	 * 카테고리 조회
	 * @param model
	 * @param vo
	 * @return
	 */
	@RequestMapping("ctgryView.json")
	public String ctgryView(
			ModelMap model, 
			@ModelAttribute("vo") EduVO vo) {
		
		boolean isAdmin = SessionUserInfoHelper.isAdmin();
		QstnCategoryVO category = null;
		
		model.addAttribute("result",0);
		if(isAdmin){
			//category = categoryService.getCategory(vo.getCtgrySeq());
			category = quizCateService.getCategory(vo.getCtgrySeq());
			model.addAttribute("result",1);
		}
		
		model.addAttribute("data", category);

        return "jsonView";
    }
	
	/**
	 * 카테고리 선택삭제
	 * @param model
	 * @param vo
	 * @return
	 */
	@RequestMapping("ctgryDelete.json")
	public String ctgryDelete(
			ModelMap model, 
			@ModelAttribute("vo") EduVO vo) {
		
		JsonObject jsonObj = new JsonObject();
		boolean isAdmin = SessionUserInfoHelper.isAdmin();
		
		if(isAdmin){
			quizCateService.deleteCategorys(vo);
		}
		
		jsonObj.addProperty("isAdmin", isAdmin);
		model.addAttribute("obj", jsonObj);

        return "obj";
    }
	
	/**
	 * 카테고리 추가
	 * @param model
	 * @param vo
	 * @return
	 */
	@RequestMapping("ctgryAdd.json")
	public String ctgryAdd(
			ModelMap model, 
			QstnCategoryVO categoryVO) {
		
		JsonObject jsonObj = new JsonObject();
		boolean isAdmin = SessionUserInfoHelper.isAdmin();
		
		
		if(categoryVO.getCtgDepth()==1){
			categoryVO.setParentSeq(0);
		}
		if(isAdmin){
			quizCateService.setCategory(categoryVO);
		}
		
		jsonObj.addProperty("isAdmin", isAdmin);
		model.addAttribute("obj", jsonObj);
		
        return "obj";
    }
	
	/**
	 * 카테고리 수정
	 * @param model
	 * @param vo
	 * @return
	 */
	@RequestMapping("ctgryUpd.json")
	public String ctgryUpd(
			ModelMap model, 
			QstnCategoryVO qstnCategoryVO) {
		model.addAttribute("result", 1);
		
		boolean isAdmin = SessionUserInfoHelper.isAdmin();
		
		if(isAdmin){
			quizCateService.updateCategory(qstnCategoryVO);
		}else{
			model.addAttribute("result", 0);
		}
		
		
		
        return "jsonView";
    }
	
	/**
	 * 카테고리 순서변경
	 * @param model
	 * @param vo
	 * @return
	 */
	@RequestMapping("ctgryOrder.json")
	public String ctgryOrder(
			ModelMap model, 
			@ModelAttribute("vo") EduVO vo) {
		
		JsonObject jsonObj = new JsonObject();
		boolean isAdmin = SessionUserInfoHelper.isAdmin();
		
		if(isAdmin){
			quizCateService.updateCategoryOrder(vo);
		}
		
		jsonObj.addProperty("isAdmin", isAdmin);
		model.addAttribute("obj", jsonObj);
		
        return "obj";
    }
	
	@ResponseBody
	@RequestMapping("getQstnCtgChildList.ajax")
	public ResultVO getCategoryChildList(
			ModelMap model,
			HttpServletResponse response,
			@RequestParam(defaultValue="0") int parentSeq) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String, Object>();
		result.setData(rstData);
		List<QstnCategoryVO> cateList = quizCateService.getCategoryChildList(parentSeq);
		rstData.put("cateList", cateList);
		return result;
    }
}
