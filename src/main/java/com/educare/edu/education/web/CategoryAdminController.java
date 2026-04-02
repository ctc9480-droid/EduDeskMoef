package com.educare.edu.education.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.educare.edu.comn.vo.CategoryVO;
import com.educare.edu.education.service.CategoryService;
import com.educare.edu.education.service.EduVO;
import com.educare.edu.education.service.model.Category;
import com.educare.edu.member.service.SessionUserInfoHelper;
import com.educare.util.LncUtil;
import com.google.gson.JsonObject;

/**
 * @Class Name : CategoryAdminController.java
 * @author SI개발팀 박용주
 * @since 2020. 7. 1.
 * @version 1.0
 * @see
 * @Description 카테고리 관리자 컨트롤러
 * 
 * <pre>
 *
 *
 * 수정일		    	수정자		    	수정내용
 * --------	  		---------	  		------------------------
 * 2020. 7. 1.	  	SI개발팀 박용주     		최초생성 
 * </pre>
 */
@Controller
@RequestMapping(value="/admin/edu/")
public class CategoryAdminController {
	
	/** 카테고리 서비스 */
	@Resource(name = "CategoryService")
	private CategoryService categoryService;
	
	/**
	 * 카테고리관리 리스트
	 * @param model
	 * @return 
	 */
	@RequestMapping("ctgryList.do")
	public String ctgryList(ModelMap model
			,@RequestParam(defaultValue="0") Integer parentSeq1
			,@RequestParam(defaultValue="0") Integer parentSeq2
			) {
		int parentSeq1p = parentSeq1;
		int parentSeq2p = parentSeq2;
		
		if(parentSeq2p>0){
			List<Category> cateList3 = categoryService.getCategoryChildAllListAdmin(parentSeq2p);
			model.addAttribute("cateList3", cateList3);
			
		 	CategoryVO vo1 = categoryService.getCategory(parentSeq2p);
		 	if(vo1!= null){
		 		parentSeq1p = vo1.getParentSeq();
		 	}
		}
		if(parentSeq1p>0){
			List<Category> cateList2 = categoryService.getCategoryChildAllListAdmin(parentSeq1p);
			model.addAttribute("cateList2", cateList2);
		}
		List<CategoryVO> cateList1 = categoryService.getCategoryListAdmin(Category.DEPTH_EDU);
		model.addAttribute("cateList1", cateList1);
		
		
		model.addAttribute("parentSeq1", parentSeq1p);
		model.addAttribute("parentSeq2", parentSeq2p);
		
        return "/admin/edu/ctgryList" + LncUtil.TILES;
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
		CategoryVO category = null;
		
		model.addAttribute("result",0);
		if(isAdmin){
			category = categoryService.getCategory(vo.getCtgrySeq());
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
			categoryService.deleteCategorys(vo);
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
			Category category) {
		
		JsonObject jsonObj = new JsonObject();
		boolean isAdmin = SessionUserInfoHelper.isAdmin();
		
		
		if(category.getCtgryDepth()==1){
			category.setParentSeq(0);
		}
		if(isAdmin){
			categoryService.setCategory(category);
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
			Category category) {
		model.addAttribute("result", 1);
		
		boolean isAdmin = SessionUserInfoHelper.isAdmin();
		
		if(isAdmin){
			categoryService.updateCategory(category);
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
			categoryService.updateCategoryOrder(vo);
		}
		
		jsonObj.addProperty("isAdmin", isAdmin);
		model.addAttribute("obj", jsonObj);
		
        return "obj";
    }
}
