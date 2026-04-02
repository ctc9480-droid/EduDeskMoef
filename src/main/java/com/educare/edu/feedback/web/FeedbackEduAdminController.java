package com.educare.edu.feedback.web;

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
import com.educare.edu.comn.model.LectureTime;
import com.educare.edu.comn.service.CheckService;
import com.educare.edu.comn.vo.FeedbackVO;
import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.education.service.EduService;
import com.educare.edu.education.service.model.Lecture;
import com.educare.edu.feedback.mapper.FeedbackMapper;
import com.educare.edu.feedback.service.FeedbackEduService;
import com.educare.edu.feedback.service.FeedbackService;
import com.educare.edu.member.service.SessionUserInfoHelper;
import com.educare.util.LncUtil;
import com.educare.util.XmlBean;
import com.google.gson.JsonObject;

@Controller
@RequestMapping(value="/admin/feedbackEdu/")
public class FeedbackEduAdminController {
	
	/** org.slf4j.Logger */
	private static final Logger LOG = LoggerFactory.getLogger(FeedbackEduAdminController.class.getName());
	
	@Resource(name="FeedbackEduService")
	private FeedbackEduService feedbackEduService;
	
	@ResponseBody
	@RequestMapping(value="getFeedbackEduMap.ajax")
	public ResultVO getFeedbackEduMap(ModelMap model
			,int feSeq
			,int eduSeq
			,String userId
			) {
		
		ResultVO result= feedbackEduService.getFeedbackEduMap(feSeq,eduSeq,userId);
		
		return result;
	}

	@ResponseBody
	@RequestMapping(value="saveFeedbackEduMap.ajax")
	public ResultVO saveFeedbackEduMap(ModelMap model
			,int feSeq
			,int eduSeq
			,int fbIdx
			,String fbNm
			,String startDeStr
			,String endDeStr
			,int state
			) {
		
		ResultVO result= feedbackEduService.saveFeedbackEduMap(feSeq,eduSeq,fbIdx,fbNm,startDeStr,endDeStr,state);
		
		return result;
	}
	@ResponseBody
	@RequestMapping(value="getFeedbackEduList.ajax")
	public ResultVO getFeebackEduList(ModelMap model
			,int eduSeq
			,int pageNo
			) {
		
		ResultVO result= feedbackEduService.getFeedbackEduList(null,eduSeq,0,pageNo);
		
		return result;
	}
}
