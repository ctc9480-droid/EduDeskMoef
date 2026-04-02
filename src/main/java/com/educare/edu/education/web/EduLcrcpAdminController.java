package com.educare.edu.education.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.educare.edu.comn.vo.CategoryVO;
import com.educare.edu.comn.vo.MovVO;
import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.education.service.AttachService;
import com.educare.edu.education.service.CategoryService;
import com.educare.edu.education.service.EduService;
import com.educare.edu.education.service.EduVO;
import com.educare.util.DateUtil;
import com.educare.util.LncUtil;


/**
 */
@Controller
@RequestMapping(value="/admin/edu/")
public class EduLcrcpAdminController {
	
	/** org.slf4j.Logger */
	private static final Logger LOG = LoggerFactory.getLogger(EduLcrcpAdminController.class);
	
	/** 카테고리 서비스 */
	@Resource(name = "CategoryService")
	private CategoryService categoryService;
	
	/** 단체교육관리 서비스 */
	@Resource(name = "EduService")
	private EduService eduService;
	/**
	 * 신청내역 리스트
	 * @param model
	 * @return
	 */
	@RequestMapping("lcrcpList.do")
	public String lctreList(ModelMap model
			,@ModelAttribute("vo") EduVO vo
			,String srchBeginDt
			,String srchEndDt
			,@RequestParam(defaultValue="10") Integer listRow
			) {
		
		if(!ObjectUtils.isEmpty(srchBeginDt)){
			vo.setSrchDateBegin(srchBeginDt.replaceAll("-", ""));
		}
		if(!ObjectUtils.isEmpty(srchEndDt)){
			vo.setSrchDateEnd(srchEndDt.replaceAll("-", ""));
		}
		
		//카테고리 
		List<CategoryVO> cateList = categoryService.getCategoryEduList();
		List<CategoryVO> cateList2 = categoryService.getCategoryChildList(vo.getSrchCtgry());
		List<CategoryVO> cateList3 = categoryService.getCategoryChildList(vo.getSrchCtgry2());
		model.addAttribute("cateList", cateList);
		model.addAttribute("cateList2", cateList2);
		model.addAttribute("cateList3", cateList3);
		
		if(ObjectUtils.isEmpty(vo.getEduYear())){
			//vo.setEduYear(DateUtil.getDate2Str(new Date(), "yyyy"));
		}
		ResultVO dataVO = eduService.getLcrcpPageList(vo,listRow);
		model.addAttribute("data", dataVO.getData());
		
        return "/admin/edu/lcrcpList" + LncUtil.TILES;
    }
	
}
