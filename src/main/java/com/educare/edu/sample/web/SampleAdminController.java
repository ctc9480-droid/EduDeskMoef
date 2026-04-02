package com.educare.edu.sample.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
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
import org.springframework.context.MessageSource;
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

import com.educare.component.UtilComponent;
import com.educare.component.VarComponent;
import com.educare.edu.bbs.service.model.BoardComment;
import com.educare.edu.comn.model.LectureTime;
import com.educare.edu.comn.service.CheckService;
import com.educare.edu.comn.vo.FeedbackVO;
import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.education.service.EduService;
import com.educare.edu.education.service.model.Lecture;
import com.educare.edu.exam.service.ExamService;
import com.educare.edu.exam.vo.ExamVO;
import com.educare.edu.feedback.mapper.FeedbackMapper;
import com.educare.edu.feedback.service.FeedbackService;
import com.educare.edu.member.service.SessionUserInfoHelper;
import com.educare.edu.sample.service.SampleService;
import com.educare.edu.sample.vo.SampleVO;
import com.educare.util.FileUtil;
import com.educare.util.LncUtil;
import com.educare.util.XmlBean;
import com.google.gson.JsonObject;

@Controller
@RequestMapping(value="/admin/sample/")
public class SampleAdminController {
	
	/** org.slf4j.Logger */
	private static final Logger LOG = LoggerFactory.getLogger(SampleAdminController.class.getName());
	
	@Resource(name="SampleService")
	private SampleService sampleService;
	@Resource(name = "utcp")
	private UtilComponent utcp;
	
	@RequestMapping(value="sampleList.do")
	public String feedbackList(ModelMap model
			,@ModelAttribute SampleVO vo
			) {
		
		List<SampleVO> list = sampleService.getSampleList(vo);
		model.addAttribute("list",list);
		
		return "/admin/sample/sampleList"+ LncUtil.TILES;
	}
	@RequestMapping(value="sample_{jspNm}.do")
	public String feedbackList(ModelMap model
			,@ModelAttribute SampleVO vo
			,@PathVariable String jspNm
			) {
		
		List<SampleVO> list = sampleService.getSampleList(vo);
		model.addAttribute("list",list);
		
		return "/admin/sample/sample_"+jspNm+""+ LncUtil.TILES;
	}
	
	@ResponseBody
	@RequestMapping("testUpload.ajax") 
	public ResultVO lctreRgsFileProc(
			ModelMap model,MultipartHttpServletRequest request
			) {
		
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			MultipartFile mf = request.getFile("filetest");
			if(mf==null){
				result.setResult(1);
				return result;
			}
			String orgNm = LncUtil.getEncode(mf.getOriginalFilename());
			if(orgNm != null && !"".equals(orgNm)){
				String fileType = orgNm.substring(orgNm.lastIndexOf(".") + 1, orgNm.length());
				String fileRename = "crepastest." + fileType;
				
				ResultVO result2 = FileUtil.multiPartupload(mf, "crepastest/", orgNm, fileRename);
				
			}
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setResult(0);
			return result;
		}
	}
	
	@ResponseBody
	@RequestMapping("orgUpload.ajax")
	public ResultVO orgUpload(
			ModelMap model,MultipartHttpServletRequest request
			) {
		ResultVO result = sampleService.setEtcOrgList(request);
		return result;
	}
	@ResponseBody
	@RequestMapping("etcGradeUpload.ajax")
	public ResultVO etcGradeUpload(
			ModelMap model,MultipartHttpServletRequest request
			) {
		ResultVO result = sampleService.setEtcGradeList(request);
		return result;
	}
	@ResponseBody
	@RequestMapping("etcGradeUpload2.ajax")
	public ResultVO etcGradeUpload2(
			ModelMap model,MultipartHttpServletRequest request
			) {
		ResultVO result = sampleService.setEtcGradeList2(request);
		return result;
	}
}
