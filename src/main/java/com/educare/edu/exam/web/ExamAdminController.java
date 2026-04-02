package com.educare.edu.exam.web;

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
import com.educare.edu.education.service.EduService;
import com.educare.edu.education.service.model.Lecture;
import com.educare.edu.exam.service.ExamService;
import com.educare.edu.exam.vo.ExamVO;
import com.educare.edu.feedback.mapper.FeedbackMapper;
import com.educare.edu.feedback.service.FeedbackService;
import com.educare.edu.member.service.SessionUserInfoHelper;
import com.educare.edu.member.service.model.UserInfo;
import com.educare.util.LncUtil;
import com.educare.util.XmlBean;
import com.google.gson.JsonObject;

@Controller
@RequestMapping(value="/admin/exam/")
public class ExamAdminController {
	
	/** org.slf4j.Logger */
	private static final Logger LOG = LoggerFactory.getLogger(ExamAdminController.class.getName());
	
	@Resource(name="ExamService")
	private ExamService examService;
	@Resource(name = "utcp")
	private UtilComponent utcp;
	
	@RequestMapping(value="examBankList.do")
	public String feedbackList(ModelMap model
			,@RequestParam(defaultValue="1") int pageNo
			) {
		UserInfo user = SessionUserInfoHelper.getUserInfo();
		String orgCd = "";
		if(user!=null){
			orgCd = user.getOrgCd();
		}
		
		Map<String, Object> data = examService.getExamBankBoardData(orgCd,pageNo);
		
		model.addAttribute("data",data);
		model.addAttribute("pageNavi",data.get("pageNavi"));
		return "/admin/exam/examBankList"+ LncUtil.TILES;
	}
	
	@RequestMapping(value="examBankInfo.json")
	public String examBankInfo(ModelMap model
			,@RequestParam(defaultValue="0") int ebqIdx
			) {
		
		Map<String, Object> returnData = new HashMap<String, Object>();
		int result = examService.getExamBankInfo(returnData,ebqIdx);
		
		model.addAttribute("data",returnData);
		return "jsonView";
	}
	
	@RequestMapping(value="examBankReg.do")
	public String feedbackReg(ModelMap model
			) {
		
		return "/admin/exam/examBankReg"+ LncUtil.TILES;
	}
	
	@RequestMapping(value="examBankRegProc.json",produces="application/json")
	public String examBankRegProc(ModelMap model,Locale locale
			,@RequestBody Map<String, Object> param
			) {
		UserInfo user = SessionUserInfoHelper.getUserInfo();
		String orgCd = "";
		if(user!=null){
			orgCd = user.getOrgCd();
		}
		
		String userId = SessionUserInfoHelper.getUserId();
		
		int result = examService.saveExamBank(param,userId,orgCd);
		
		model.addAttribute("result",result);
		model.addAttribute("msg",utcp.getLang("msg.exambank.submit"+result));
		return "jsonView";
	}
	
}
