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
import com.educare.edu.comn.mapper.SmartCheckMapper;
import com.educare.edu.comn.model.LectureTime;
import com.educare.edu.comn.service.CheckService;
import com.educare.edu.comn.service.SmartcheckService;
import com.educare.edu.comn.vo.SmartCheckVO;
import com.educare.edu.education.service.EduService;
import com.educare.edu.education.service.impl.EduMapper;
import com.educare.edu.education.service.model.Lecture;
import com.educare.edu.education.service.model.LectureStdnt;
import com.educare.util.XmlBean;
import com.google.gson.JsonObject;

@Controller
@RequestMapping(value="/user/excel/")
public class UserExcelController {
	
	/** org.slf4j.Logger */
	private static final Logger LOG = LoggerFactory.getLogger(UserExcelController.class.getName());
	
	@Resource(name="EduService")
	private EduService eduService;
	
	@Resource(name="EduMapper")
	private EduMapper eduMapper;
	
	@Resource(name="SmartcheckService")
	private SmartcheckService smartcheckService;
	@Resource(name="SmartCheckMapper")
	private SmartCheckMapper smartCheckMapper;
	
	@RequestMapping(value="rollBook.do")
	public String rollBook(ModelMap model
			,@RequestParam int eduSeq
			,@RequestParam(required=false) String userId
			) {
		
		//
		Lecture lecture = eduService.getLctreDetail(eduSeq);
		
		//날짜
		List<SmartCheckVO> dateList = smartcheckService.getDateList(eduSeq);
		// 시간
		List<SmartCheckVO> timeList = smartcheckService.getTimeList(eduSeq);
		//시간표
		List<SmartCheckVO> stdntList = smartcheckService.getStdntList(eduSeq,userId);
		
		model.addAttribute("eduNm", lecture.getEduNm());
		model.addAttribute("dateList", dateList);
		model.addAttribute("timeList", timeList);
		model.addAttribute("stdntList", stdntList);
		
		return "user/excel/rollBook";
	}
}
