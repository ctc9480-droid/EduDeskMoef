package com.educare.edu.comn.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
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
import com.educare.edu.comn.mapper.LectureEtcIemMapper;
import com.educare.edu.comn.mapper.LectureStdntMapper;
import com.educare.edu.comn.model.Code;
import com.educare.edu.comn.model.LectureTime;
import com.educare.edu.comn.vo.CategoryVO;
import com.educare.edu.comn.vo.LectureEtcIemVO;
import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.education.service.CategoryService;
import com.educare.edu.education.service.EduService;
import com.educare.edu.education.service.impl.CategoryMapper;
import com.educare.edu.education.service.model.Category;
import com.educare.edu.education.service.model.Lecture;
import com.educare.edu.member.service.SessionUserInfoHelper;
import com.educare.edu.member.service.model.UserInfo;
import com.educare.util.XmlBean;
import com.google.gson.JsonObject;

@Controller
@RequestMapping(value="/comm/jspInc/")
public class JspIncController {
	
	/** org.slf4j.Logger */
	private static final Logger LOG = LoggerFactory.getLogger(JspIncController.class.getName());
	
	@Resource(name="CategoryService")
	private CategoryService categoryService;
	
	@Resource(name="CodeMapper") 
	private CodeMapper codeMapper;
	@Resource(name="LectureEtcIemMapper") 
	private LectureEtcIemMapper lectureEtcIemMapper;
	@Resource(name="EduService") 
	private EduService eduService;
	
	
	 
	@RequestMapping(value="lectureEtcIemForm.do")
	public String eduMap(ModelMap model
			,@ModelAttribute(value="vo") LectureEtcIemVO vo
			) {
		
		return "/comm/jspInc/lectureEtcIemForm";
	}
	@RequestMapping(value="mypageInfo.do")
	public String mypageInfo(ModelMap model
			,@ModelAttribute(value="vo") LectureEtcIemVO vo
			) {
		UserInfo user = SessionUserInfoHelper.getUserInfo();
		if(user == null){
			return null;
		}
		
		String userNm = user.getUserNm();
		Date lstLoginDe = user.getLstLoginDe();
		
		String userId = user.getUserId();
		
		ResultVO result = eduService.getStdntEduStat(userId);
		model.addAttribute("myStat",result.getData());
		
		
		model.addAttribute("userNm", userNm);
		model.addAttribute("lstLoginDe", lstLoginDe);
		return "/comm/jspInc/mypageInfo";
	}
	
}
