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
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
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
import com.educare.edu.comn.service.CheckService;
import com.educare.edu.comn.vo.FeedbackVO;
import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.education.service.EduService;
import com.educare.edu.education.service.model.Lecture;
import com.educare.edu.feedback.mapper.FeedbackMapper;
import com.educare.edu.feedback.service.FeedbackService;
import com.educare.edu.member.service.SessionUserInfoHelper;
import com.educare.edu.member.service.model.UserInfo;
import com.educare.util.LncUtil;
import com.educare.util.XmlBean;
import com.google.gson.JsonObject;

@Controller
@RequestMapping(value="/admin/feedback/")
public class FeedbackAdminController {
	
	/** org.slf4j.Logger */
	private static final Logger LOG = LoggerFactory.getLogger(FeedbackAdminController.class.getName());
	
	@Resource(name="FeedbackService")
	private FeedbackService feedbackService;
	@Resource(name="FeedbackMapper")
	private FeedbackMapper feedbackMapper;
	
	@Resource(name="EduService")
	private EduService eduService;
	@Resource(name="CheckService")
	private CheckService checkService;
	@Resource(name="FeedbackEduMapper")
	private FeedbackEduMapper feedbackEduMapper;
	
	
	@RequestMapping(value="feedbackList.do")
	public String feedbackList(ModelMap model
			,@RequestParam(defaultValue="1") Integer pageNo
			,String srchWrd
			) {
		UserInfo user = SessionUserInfoHelper.getUserInfo();
		if(user == null){
			return null;
		}
		
		ResultVO result = feedbackService.getFeedbackPageList(pageNo,10,srchWrd);
		model.addAttribute("data",result.getData());
		
		return "/admin/feedback/feedbackList"+ LncUtil.TILES;
	}
	@RequestMapping(value="feedbackReg.do")
	public String feedbackReg(ModelMap model,
			@RequestParam(defaultValue="0") int idx
			) {
		return "/admin/feedback/feedbackReg"+ LncUtil.TILES;
	}
	@RequestMapping(value="feedbackCopy.do")
	public String feedbackCopy(ModelMap model,
			@RequestParam(defaultValue="0") int copyIdx
			) {
		return "/admin/feedback/feedbackReg"+ LncUtil.TILES;
	}
	
	@RequestMapping(value="feedbackInfo.json")
	public String feedbackInfo(ModelMap model
			,@RequestParam(defaultValue="0") int idx
			,@RequestParam(defaultValue="0") Integer eduSeq
			,@RequestParam(defaultValue="") String userId
			,@RequestParam(defaultValue="0") Integer feSeq
			) {
		int result = 0;
		
		//int check = checkService.checkUpdFeedback(idx, orgCd);
		//if(check!=0&&check!=2){
			//Map<String, Object> feedbackInfo = feedbackService.getFeedbackInfo(idx,orgCd);
			//model.addAttribute("feedbackInfo",feedbackInfo);
		//}
		
		//eduSeq = LncUtil.nvlInt(eduSeq);//소스코드점검으로 주석처리, 오류나면 주석풀고 원인 찾아야함
		if(eduSeq>0 && !StringUtils.isEmpty(userId)){
			Lecture lecture = eduService.getLctreDetail(eduSeq);
			int fbIdx=lecture.getFbIdx();
			
			Map<String, Object> feedbackInfo = feedbackService.getFeedbackInfo(fbIdx);
			model.addAttribute("feedbackInfo",feedbackInfo);
			
			feedbackService.setAnswer(feedbackInfo,userId,eduSeq,feSeq);	
		}else{
			Map<String, Object> feedbackInfo = feedbackService.getFeedbackInfo(idx);
			model.addAttribute("feedbackInfo",feedbackInfo);
		}
		
		model.addAttribute("result",result);
		return "jsonView";
	}
	@RequestMapping(value="feedbackRegProc.json",produces="application/json")
	public String feedbackRegProc(ModelMap model
			,@RequestBody Map<String, Object> param
			) {
		UserInfo user = SessionUserInfoHelper.getUserInfo();
		String orgCd = "";
		if(user!=null){
			orgCd = user.getOrgCd();
		}
		
		int result = feedbackService.saveFeedback(orgCd,param);
		
		model.addAttribute("result",result);
		return "jsonView";
	}
	@RequestMapping(value="feedbackDelProc.json",produces="application/json")
	public String feedbackDelProc(ModelMap model
			,@RequestParam(defaultValue="0") int idx
			) {
		UserInfo user = SessionUserInfoHelper.getUserInfo();
		String orgCd = "";
		if(user!=null){
			orgCd = user.getOrgCd();
		}
		
		int result = feedbackService.deleteFeedback(orgCd,idx);
		
		model.addAttribute("result",result);
		return "jsonView";
	}
	@RequestMapping(value="feedbackList.json")
	public String feedbackListJson(ModelMap model
			,String srchWrd
			) {
		int result = 0;
		List<FeedbackVO> feedbackList = new ArrayList<FeedbackVO>();
		try {
			UserInfo user = SessionUserInfoHelper.getUserInfo();
			String orgCd = "";
			String userId = "";
			if(user != null){
				orgCd = user.getOrgCd();
				userId = user.getUserId();
			}
			feedbackList = feedbackService.getfeedbackList(orgCd,userId,srchWrd);
			result=1;
		} catch (NullPointerException e) {
			result=0;
		}
		model.addAttribute("feedbackList", feedbackList); 
		model.addAttribute("result", result);
		return "jsonView";
	}
	@RequestMapping(value="stdntFeedbackInfo.json")
	public String stdntFeedbackInfo(ModelMap model
			,@RequestParam int eduSeq 
			,@RequestParam String userId 
			,@RequestParam Integer feSeq 
			) {
		Lecture lecture = eduService.getLctreDetail(eduSeq);
		int fbIdx=lecture.getFbIdx();
		
		//feSeq가 존재한다면
		//설문지 조회
		if(feSeq > 0){
			FeedbackEdu fe = feedbackEduMapper.selectByPk(feSeq);
			if(fe==null){
				model.addAttribute("result",0);
				model.addAttribute("message","잘못된 설문지입니다.");
				return "jsonView";
			}
			fbIdx = fe.getFbIdx();
		}
		
		if(fbIdx==0){
			model.addAttribute("result",0);
			model.addAttribute("message","설문지가 준비되지 않았습니다.");
			return "jsonView";
		}
		
		String orgCd = lecture.getOrgCd();

		Map<String, Object> feedbackInfo = feedbackService.getFeedbackInfo(fbIdx);
		
		feedbackService.setAnswer(feedbackInfo,userId,eduSeq,feSeq);
		
		model.addAttribute("result",1);
		model.addAttribute("feedbackInfo",feedbackInfo);
		return "jsonView";
	}
	
	@RequestMapping(value="feedbackPopup.do")
	public String feedbackPopup(ModelMap model
			,@RequestParam(defaultValue="0") int fbIdx 
			) {
		
		return "admin/feedback/feedbackPopup";
	}
	
	@RequestMapping(value="popup/feedbackResult.do")
	public String popup_feedbackResult(ModelMap model
			,@RequestParam(defaultValue="0") int feSeq 
			) {
		
		model.addAttribute("jsp","admin/feedback/popup/feedbackResult");
		return "/layout/popup/baseLayout_notiles";
	}
}
