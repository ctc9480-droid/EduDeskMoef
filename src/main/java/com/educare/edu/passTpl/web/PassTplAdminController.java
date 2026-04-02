package com.educare.edu.passTpl.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import com.educare.edu.comn.model.LectureTime;
import com.educare.edu.comn.service.CheckService;
import com.educare.edu.comn.vo.FeedbackVO;
import com.educare.edu.comn.vo.PassCertTplVO;
import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.education.service.EduService;
import com.educare.edu.education.service.model.Lecture;
import com.educare.edu.feedback.mapper.FeedbackMapper;
import com.educare.edu.feedback.service.FeedbackService;
import com.educare.edu.member.service.SessionUserInfoHelper;
import com.educare.edu.passTpl.mapper.PassCertTplMapper;
import com.educare.edu.passTpl.service.PassTplService;
import com.educare.util.LncUtil;
import com.educare.util.XmlBean;
import com.google.gson.JsonObject;

@Controller
@RequestMapping(value="/admin/passTpl/")
public class PassTplAdminController {
	
	/** org.slf4j.Logger */
	private static final Logger LOG = LoggerFactory.getLogger(PassTplAdminController.class.getName());
	

	@Resource(name="PassCertTplMapper")
	private PassCertTplMapper passCertTplMapper;
	@Resource(name="PassTplService")
	private PassTplService passTplService;
	
	
	@RequestMapping(value="passTplList.do")
	public String feedbackList(ModelMap model
			,@RequestParam(defaultValue="1") Integer pageNo
			,String srchWrd
			) {
		
		ResultVO result = passTplService.getPassTplPageList(pageNo,10,srchWrd);
		model.addAttribute("data",result.getData());
		
		return "/admin/passTpl/passTplList"+ LncUtil.TILES;
	}
	@RequestMapping(value="passTplReg.do")
	public String passTplReg(ModelMap model
			,PassCertTplVO param
			) {
		PassCertTplVO passTpl = passCertTplMapper.selectPassCertTplMap(param);
		
		model.addAttribute("passTpl",passTpl);
		return "/admin/passTpl/passTplReg"+ LncUtil.TILES;
	}
	
	@RequestMapping(value="passTplPopup.do")
	public String feedbackPopup(ModelMap model
			,@RequestParam(defaultValue="0") int passIdx 
			) {
		Map<String, Object> lctre = new HashMap<String,Object>();
		lctre.put("eduNm", "OOO 기본 과정");
		lctre.put("ctg3Nm", "OOO 기본 과정");
		lctre.put("eduPeriodBegin", "2023-01-01");
		lctre.put("eduPeriodEnd", "2023-01-31");
		lctre.put("tmEduDt", "20230131");
		
		Map<String, Object> user = new HashMap<String,Object>();
		user.put("userNm", "홍길동");
		user.put("userEnNm", "Hong Gil-Dong");
		user.put("birth", "19840323");
		user.put("userOrgNm","재정경제부");
		
		Map<String, Object> stdnt = new HashMap<String,Object>();
		stdnt.put("passCertNum", "MO-25-00001");
		stdnt.put("certDe", new Date());
		stdnt.put("belong", "OO기관");
		
		Map<String, Object> sub = new HashMap<String,Object>();
		
		//수료증정보
		PassCertTplVO param = new PassCertTplVO();
		param.setPassIdx(passIdx);
		PassCertTplVO tpl = passCertTplMapper.selectPassCertTplMap(param);
		
		sub.put("passCertNum", tpl.getPrefix()+"24-00001");
		sub.put("complCertNum", tpl.getPrefix()+"24-00001");
		sub.put("passCertDe", new Date());
		sub.put("complCertDe", new Date());
		
		Map<String, Object> certMap = new HashMap<String,Object>();
		certMap.put("user", user);
		certMap.put("stdnt", stdnt);
		certMap.put("lctre", lctre);
		certMap.put("sub", sub);
		certMap.put("classTime","2시간");
		certMap.put("examDate",new Date());
		certMap.put("subStartDt", "20240101");
		certMap.put("subEndDt", "20240131");
		certMap.put("subClassTime", "72시간");
		
		
		model.addAttribute("certMap", certMap);
		model.addAttribute("compNm", "한국산업기술시험원");
		
		
		
		
      	return "/cert/certView"+passIdx;
		
	}
}
