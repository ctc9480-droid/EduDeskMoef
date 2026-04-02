package com.educare.edu.comn.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.stringtemplate.v4.compiler.STParser.mapExpr_return;

import com.educare.component.VarComponent;
import com.educare.edu.bbs.service.model.BoardComment;
import com.educare.edu.comn.mapper.CodeMapper;
import com.educare.edu.comn.model.Code;
import com.educare.edu.comn.model.LectureTime;
import com.educare.edu.comn.vo.CategoryVO;
import com.educare.edu.education.service.CategoryService;
import com.educare.edu.education.service.EduService;
import com.educare.edu.education.service.impl.CategoryMapper;
import com.educare.edu.education.service.model.Category;
import com.educare.edu.education.service.model.Lecture;
import com.educare.edu.member.service.SessionUserInfoHelper;
import com.educare.edu.quizCate.service.QuizCateService;
import com.educare.edu.quizCate.vo.QstnCategoryVO;
import com.educare.util.XmlBean;
import com.google.gson.JsonObject;

@Controller
@RequestMapping(value="/comm/api/")
public class ApiController {
	
	/** org.slf4j.Logger */
	private static final Logger LOG = LoggerFactory.getLogger(ApiController.class.getName());
	
	@Resource(name="CategoryService")
	private CategoryService categoryService;
	@Resource(name="QuizCateService")
	private QuizCateService quizCateService;
	
	@Resource(name="CodeMapper") 
	private CodeMapper codeMapper;
	 
	@RequestMapping(value="isLogin.json")
	public String eduMap(ModelMap model
			) {
		int code=0;
		if(SessionUserInfoHelper.isLogined()){
			if("8,9".contains(SessionUserInfoHelper.getUserMemLvl())){
				code=1;
				model.addAttribute("userId",SessionUserInfoHelper.getUserId());
			}
		}
		
		model.addAttribute("code",code);
		return "jsonView";
	}
	
	//카테고리 가져오기
	@RequestMapping(value="callCategory.json")
	public String callCategory(ModelMap model
			,@RequestParam(defaultValue="0") int ctgrySeq
			) {
		int result = 0;
		
		List<CategoryVO> ctgryList = categoryService.getCategoryList2(ctgrySeq);
		
		model.addAttribute("result",result);
		model.addAttribute("ctgryList",ctgryList);
		return "jsonView";
	}
	@RequestMapping(value="callAllCategory.json")
	public String callCategory(ModelMap model
			) {
		
		List<CategoryVO> ctgryList1 = categoryService.getCategoryList(1);
		List<CategoryVO> ctgryList2 = categoryService.getCategoryList(2);
		
		model.addAttribute("ctgryList1",ctgryList1);
		model.addAttribute("ctgryList2",ctgryList2);
		return "jsonView";
	}
	
	//공통코드 가져오기
	@RequestMapping(value="callCodeMap.json")
	public String callCodeMap(ModelMap model
			,@RequestParam(defaultValue="-1") int prntCd
			) {
		
		List<Code> codeList = codeMapper.getCodeByParent(prntCd);
		makeTree(codeList);
		model.addAttribute("data",codeList);
		return "jsonView";
	}
	
	/**
	 * 자식코드를 자식맵으로 만들기
	 * @param codeList
	 * @return
	 */
	public void makeTree(List<Code> codeList){
		for(Code codeMap:codeList){
			List<Code> subList = codeMapper.getCodeByParent(codeMap.getCodeCd());
			codeMap.setSubList(subList);
			this.makeTree(subList);
		}
	}
	
	//공통코드 가져오기
	@RequestMapping(value="callCode.json")
	public String callCode(ModelMap model
			,@RequestParam(defaultValue="-1") int prntCd
			) {
		
		List<Code> codeList = codeMapper.getCodeByParent(prntCd);
		model.addAttribute("data",codeList);
		return "jsonView";
	}
	
	//카테고리 가져오기
		@RequestMapping(value="callQuizCategory.json")
		public String callQuizCategory(ModelMap model
				,@RequestParam(defaultValue="0") int ctgrySeq
				) {
			int result = 0;
			
			List<QstnCategoryVO> ctgryList = quizCateService.getCategoryList2(ctgrySeq);
			model.addAttribute("result",result);
			model.addAttribute("ctgryList",ctgryList);
			return "jsonView";
		}
}
