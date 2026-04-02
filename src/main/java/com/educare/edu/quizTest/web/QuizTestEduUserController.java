package com.educare.edu.quizTest.web;

import javax.annotation.Resource;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.educare.component.UtilComponent;
import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.member.service.SessionUserInfoHelper;
import com.educare.edu.quizBank.service.QuizBankService;
import com.educare.edu.quizTest.service.QuizTestService;
import com.educare.util.LncUtil;

/**
 *시험지를 수업에 연결하는 컨트롤러
 *
 */
@Controller
@RequestMapping(value="/user/quizTestEdu/")
public class QuizTestEduUserController {
	
	/** org.slf4j.Logger */
	private static final Logger LOG = LoggerFactory.getLogger(QuizTestEduUserController.class.getName());
	
	@Resource(name="QuizTestService")
	private QuizTestService quizTestService;
	@Resource(name = "utcp")
	private UtilComponent utcp;
	
	@ResponseBody
	@RequestMapping(value="getTestEduList.ajax")
	public ResultVO getTestEduList(ModelMap model
			,int eduSeq
			,int pageNo
			) {
		String userId = SessionUserInfoHelper.getUserId();
		int status = 1;
		ResultVO result= quizTestService.getTestEduList(userId,eduSeq,0,pageNo,status);
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="getTestEduQstnList.ajax")
	public ResultVO getTestEduQstnList(ModelMap model
			,int eduSeq
			,int testSeq
			,String userId
			) {
		String userIdp = userId;
		if("9".equals(SessionUserInfoHelper.getUserMemLvl())){
			userIdp = SessionUserInfoHelper.getUserId();
		}
		
		//시험지오픈여부 체크
		if(!SessionUserInfoHelper.isAdmin()) {
			ResultVO result2 = quizTestService.checkOpenTest(eduSeq,testSeq,userIdp);
			if(result2.getResult()!=1){
				return result2;
			}
		}
		
		ResultVO result= quizTestService.getTestEduQstnList(eduSeq,testSeq,userIdp);
		
		return result;
	}
	@ResponseBody
	@RequestMapping(value="saveTestQstnAnswer.ajax",method=RequestMethod.POST)
	public ResultVO saveTestQstnAnswer(ModelMap model
			,int eduSeq
			,int testSeq
			,int tqSeq
			,int qstnTp
			,String optn
			,String answer
			,String fillBlank
			) {
		String userId = SessionUserInfoHelper.getUserId();
		
		ResultVO result= quizTestService.saveTestQstnAnswer(eduSeq,testSeq,tqSeq,userId,qstnTp,optn,answer,fillBlank);
		return result;
	}
	@ResponseBody
	@RequestMapping(value="doneTestQstnAnswer.ajax")
	public ResultVO doneTestQstnAnswer(ModelMap model
			,int eduSeq
			,int testSeq
			) {
		String checkId = null;
		String userId = SessionUserInfoHelper.getUserId();
		ResultVO result= quizTestService.doneTestQstnAnswer(eduSeq,testSeq,userId,0,checkId);
		return result;
	}
	
	//관리자  채점 기능
	@ResponseBody
	@RequestMapping(value="saveTestQstnCorrect.ajax")
	public ResultVO saveTestQstnCorrect(ModelMap model
			,int eduSeq
			,int testSeq
			,int tqSeq
			,String userId
			,String correctYn
			,@RequestParam(defaultValue="0") Integer marksGet
			) {
		String checkId = null;
		ResultVO result= quizTestService.saveTestQstnCorrect(eduSeq,testSeq,tqSeq,userId,correctYn,marksGet);
		result = quizTestService.doneTestQstnAnswer(eduSeq, testSeq, userId,0,checkId);
		return result;
	}
	
	//관리자  채점완료 기능
	@ResponseBody
	@RequestMapping(value="saveTestQstnComplete.ajax")
	public ResultVO saveTestQstnComplete(ModelMap model
			,int eduSeq
			,int testSeq
			,String userId
			,String completeYn
			) throws JsonMappingException, JsonParseException {
		String checkId = SessionUserInfoHelper.getUserId();
		ResultVO result = quizTestService.saveTestQstnComplete(eduSeq, testSeq, userId,checkId,completeYn);
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="startTestQstnAnswer.ajax")
	public ResultVO startTestQstnAnswer(ModelMap model
			,int eduSeq
			,int testSeq
			) {
		String userId = SessionUserInfoHelper.getUserId();
		ResultVO result= quizTestService.startTestQstnAnswer(eduSeq,testSeq,userId);
		return result;
	}
	
	@RequestMapping(value="popup/resultStdnt.do")
	public String popup_quizTestList(ModelMap model
			,int eduSeq
			,Integer testSeq
			,String userId
			) {
		String userIdp = userId;
		//ResultVO result= quizTestService.getTestResultStdnt(eduSeq,testSeq,userId);
		//model.addAttribute("resultStdnt",result.getData());
		
		if(!SessionUserInfoHelper.isAdmin()){
			userIdp = SessionUserInfoHelper.getUserId();
		}
		ResultVO result3 = quizTestService.startTestQstnAnswer(eduSeq,testSeq,userIdp);//시작
		model.addAttribute("data",result3.getData());
		
		
		//ResultVO result = quizTestService.getTestResult(eduSeq, testSeq);
		//model.addAttribute("data",result.getData());
		
		model.addAttribute("jsp","user/quizTestEdu/popup/resultStdnt");
		return "/layout/popup/baseLayout_notiles";
	}
}
