package com.educare.edu.quizTest.web;

import javax.annotation.Resource;

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
@RequestMapping(value="/admin/quizTestEdu/")
public class QuizTestEduAdminController {
	
	/** org.slf4j.Logger */
	private static final Logger LOG = LoggerFactory.getLogger(QuizTestEduAdminController.class.getName());
	
	@Resource(name="QuizTestService")
	private QuizTestService quizTestService;
	@Resource(name = "utcp")
	private UtilComponent utcp;
	
	
	@ResponseBody
	@RequestMapping(value="getTestEduMap.ajax")
	public ResultVO getTestEduMap(ModelMap model
			,int testSeq
			,int eduSeq
			) {
		
		ResultVO result= quizTestService.getTestEduMap(testSeq,eduSeq);
		
		return result;
	}
	@ResponseBody
	@RequestMapping(value="getTestTmplList.ajax")
	public ResultVO getTestTmplList(ModelMap model
			,int eduSeq
			,String srchWrd
			,@RequestParam(defaultValue="0") Integer srchCtgry
			,@RequestParam(defaultValue="0") Integer srchCtgry2
			,@RequestParam(defaultValue="0") Integer srchCtgry3
			) {
		
		ResultVO result= quizTestService.getTestTmplList(eduSeq,srchWrd,srchCtgry,srchCtgry2,srchCtgry3);
		
		return result;
	}
	@ResponseBody
	@RequestMapping(value="saveTestEduMap.ajax")
	public ResultVO saveTestEduMap(ModelMap model
			,int testSeq
			,int eduSeq
			,int testTmplSeq
			,String testNm
			,String startDeStr
			,String endDeStr
			,int status
			,int timer
			,Double passMarks
			,int markTp
			,int tryNo
			,int prntTestSeq
			,@RequestParam(defaultValue="0") Integer subSeq
			) {
		int subSeqp = subSeq;
		ResultVO result= quizTestService.saveTestEduMap(testSeq,eduSeq,testTmplSeq,testNm,startDeStr,endDeStr,status,timer,passMarks,markTp,tryNo,prntTestSeq,subSeqp);
		
		return result;
	}
	@ResponseBody
	@RequestMapping(value="getTestEduList.ajax")
	public ResultVO getTestEduList(ModelMap model
			,int eduSeq
			,int pageNo
			) {
		ResultVO result = new ResultVO();
		
		int status = 0;
		result= quizTestService.getTestEduList(null,eduSeq,0,pageNo,status);
		
		return result;
	}
	@ResponseBody
	@RequestMapping(value="getTestResult.ajax")
	public ResultVO getTestResult(ModelMap model
			,int eduSeq
			,int testSeq
			) {
		
		ResultVO result= quizTestService.getTestResult(eduSeq,testSeq);
		
		return result;
	}
	@ResponseBody
	@RequestMapping(value="openResultTest.ajax", method=RequestMethod.POST)
	public ResultVO openResultTest(ModelMap model
			,int eduSeq
			,int testSeq
			,String openYn
			) {
		ResultVO result = quizTestService.openResultTest(eduSeq,testSeq,openYn);
		
		return result;
	}
	
	
}
