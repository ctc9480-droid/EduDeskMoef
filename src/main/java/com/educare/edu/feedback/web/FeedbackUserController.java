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
import com.educare.edu.comn.mapper.FeedbackEduMapper;
import com.educare.edu.comn.model.FeedbackEdu;
import com.educare.edu.comn.model.LectureTime;
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
@RequestMapping(value="/user/feedback/")
public class FeedbackUserController {
	
	/** org.slf4j.Logger */
	private static final Logger LOG = LoggerFactory.getLogger(FeedbackUserController.class.getName());
	
	
	@Resource(name="EduService")
	private EduService eduService;
	@Resource(name="FeedbackService")
	private FeedbackService feedbackService;
	@Resource(name="FeedbackMapper")
	private FeedbackMapper feedbackMapper;
	@Resource(name="FeedbackEduMapper")
	private FeedbackEduMapper feedbackEduMapper;

	
	@RequestMapping(value="feedbackInfo.json")
	public String feedbackInfo(ModelMap model
			,@RequestParam(defaultValue="0") int eduSeq 
			,@RequestParam(defaultValue="0") int feSeq 
			) {
		Lecture lecture = eduService.getLctreDetail(eduSeq);
		int fbIdx=lecture.getFbIdx();
		if(feSeq == 0){
			if(fbIdx==0){
				model.addAttribute("RESULT",0);
				model.addAttribute("MESSAGE","설문지가 준비되지 않았습니다.");
				return "jsonView";
			}
		}else{
			FeedbackEdu fe = feedbackEduMapper.selectByPk(feSeq);
			if(fe==null){
				model.addAttribute("RESULT",0);
				model.addAttribute("MESSAGE","설문지가 준비되지 않았습니다.");
				return "jsonView";
			}
			fbIdx = fe.getFbIdx();
		}
		
		//문항가져오기
		Map<String, Object> feedbackInfo = feedbackService.getFeedbackInfo(fbIdx);
		
		//답변가져오기
		String userId = SessionUserInfoHelper.getUserId();
		
		feedbackService.setAnswer(feedbackInfo,userId,eduSeq,feSeq);
		
		model.addAttribute("RESULT",1);
		model.addAttribute("feedbackInfo",feedbackInfo);
		return "jsonView";
	}
	@RequestMapping(value="saveFeedbackAnswer.json",produces="application/json")
	public String feedbackRegProc(ModelMap model
			,@RequestBody Map<String, Object> param
			) {
		int result = feedbackService.saveFeedbackAnswer(param);
		
		model.addAttribute("result",result);
		return "jsonView";
	}
	
	@RequestMapping(value="feedbackPopup.do")
	public String feedbackPopup(ModelMap model
			,@RequestParam(defaultValue="0") int eduSeq 
			,@RequestParam(defaultValue="0") int feSeq
			) {
		Lecture lecture = eduService.getLctreDetail(eduSeq);
		if(feSeq==0){
			int fbIdx=lecture.getFbIdx();
			if(fbIdx==0){
				model.addAttribute("message","설문지가 존재하지 않습니다.");
				return "winClose";
			}
		}else{
			String userId = SessionUserInfoHelper.getUserId();
			//설문기간 체크
			ResultVO result = feedbackService.checkOpenFeedback(feSeq,userId);
			if(result.getResult() != 1){
				model.addAttribute("message",result.getMsg());
				return "winClose";
			}
		}
		
		
		
		model.addAttribute("lecture",lecture);
		return "user/feedback/feedbackPopup";
	}
}
