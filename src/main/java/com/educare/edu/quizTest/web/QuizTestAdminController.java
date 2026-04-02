package com.educare.edu.quizTest.web;

import java.util.List;
import java.util.Map;

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
import com.educare.edu.comn.model.Question;
import com.educare.edu.comn.model.TestTmpl;
import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.member.service.SessionUserInfoHelper;
import com.educare.edu.quizBank.service.QuizBankService;
import com.educare.edu.quizCate.service.QuizCateService;
import com.educare.edu.quizCate.vo.QstnCategoryVO;
import com.educare.edu.quizTest.mapper.QuizTestMapper;
import com.educare.edu.quizTest.service.QuizTestService;
import com.educare.edu.quizTest.vo.QuizTestVO;
import com.educare.util.LncUtil;

@Controller
@RequestMapping(value="/admin/quizTest/")
public class QuizTestAdminController {
	
	/** org.slf4j.Logger */
	private static final Logger LOG = LoggerFactory.getLogger(QuizTestAdminController.class.getName());
	
	@Resource(name="QuizTestService")
	private QuizTestService quizTestService;
	@Resource(name = "utcp")
	private UtilComponent utcp;
	/** 카테고리 서비스 */
	@Resource(name = "QuizCateService")
	private QuizCateService quizCateService; 
	@Resource(name = "QuizTestMapper")
	private QuizTestMapper quizTestMapper; 
	
	@RequestMapping(value="quizTestList.do")
	public String quizTestList(ModelMap model
			,@RequestParam(defaultValue="1") int pageNo		
			,@RequestParam(defaultValue="0") int srchCtg1Seq		
			,@RequestParam(defaultValue="0") int srchCtg2Seq		
			,@RequestParam(defaultValue="0") int srchCtg3Seq		
			,String srchWrd
			) {
		List<QstnCategoryVO> cateList = quizCateService.getCategoryListAdmin(QstnCategoryVO.DEPTH_EDU);
		model.addAttribute("cateList",cateList);
		if(srchCtg1Seq > 0){
			List<QstnCategoryVO> cateList2 = quizCateService.getCategoryChildList(srchCtg1Seq);
			model.addAttribute("cateList2", cateList2);
		}
		if(srchCtg2Seq > 0){
			List<QstnCategoryVO> cateList3 = quizCateService.getCategoryChildList(srchCtg2Seq);
			model.addAttribute("cateList3", cateList3);
		}
		
		ResultVO result = quizTestService.getQuizTestPageList(pageNo,10,srchWrd,srchCtg1Seq,srchCtg2Seq,srchCtg3Seq);
		model.addAttribute("data",result.getData());
		
		return "/admin/quizTest/quizTestList"+ LncUtil.TILES;
	}
	
	@ResponseBody
	@RequestMapping(value="quizTestInfo.ajax")
	public ResultVO quizTestInfo(ModelMap model
			,@RequestParam(defaultValue="0") int testTmplSeq
			) {
		
		ResultVO result= quizTestService.getQuizTestInfo(testTmplSeq);
		
		return result;
	}
	
	@RequestMapping(value="quizTestReg.do")
	public String quizTestReg(ModelMap model
			,@RequestParam(defaultValue="0") Integer testTmplSeq
			) {
		ResultVO result= quizTestService.getQuizTestInfo(testTmplSeq);
		model.addAttribute("data",result.getData());
		
		//카테고리 조회
		List<QstnCategoryVO> cateList = quizCateService.getCategoryListAdmin(QstnCategoryVO.DEPTH_EDU);
		model.addAttribute("cateList",cateList);
		Map<String, Object> rstData = (Map<String, Object>) result.getData();
		TestTmpl tmpl = (TestTmpl) rstData.get("tmpl");
		if(tmpl!=null){
			List<QstnCategoryVO> cateList2 = quizCateService.getCategoryChildList(tmpl.getCtg1Seq());
			model.addAttribute("cateList2", cateList2);
			List<QstnCategoryVO> cateList3 = quizCateService.getCategoryChildList(tmpl.getCtg2Seq());
			model.addAttribute("cateList3", cateList3);
		}
		
		//나중에 삭제가능한 시험지인지 유효성 체크해야함
		QuizTestVO vo = new QuizTestVO();
		vo.setTestTmplSeq(testTmplSeq);
		int usedCnt = quizTestMapper.selectTestByUsed(vo);
		if(usedCnt > 0){
			model.addAttribute("isUsed",true);
		}
		
		return "/admin/quizTest/quizTestReg"+ LncUtil.TILES;
	}
	
	@ResponseBody
	@RequestMapping(value="saveQuizTest.ajax",method=RequestMethod.POST)
	public ResultVO saveQuizTest(ModelMap model
			,@RequestParam(defaultValue="0") Integer testTmplSeq
			,@RequestParam(required=true) String testTmplNm
			,@RequestParam(defaultValue="0") Integer testTp 	//시험형태
			,@RequestParam(defaultValue="0") Integer ctg1Seq
			,@RequestParam(defaultValue="0") Integer ctg2Seq
			,@RequestParam(defaultValue="0") Integer ctg3Seq
			,@RequestParam(defaultValue="0") Integer timeLimit	//제한시간
			,@RequestParam(defaultValue="0")  Integer markTp	//채점방식
			,@RequestParam(defaultValue="0")  Integer lookTp	//응시방식
			,@RequestParam(defaultValue="0")  Integer ordTp		//출제방식
			,@RequestParam(defaultValue="0")  Integer selectTp	//시험방식
			,@RequestParam(defaultValue="0")  Integer choiceTp	//항목보기방식
			,@RequestParam(defaultValue="0")  Integer lookCnt	//동시 출력 개수
			,String totQstn										//문제개수
			,String selectQstn									//출제개수
			,String descr										//시험지설명
			,@RequestParam(defaultValue="0")  Double totMarks	//총점
			,@RequestParam(defaultValue="0")  Integer status	//상태
			,Integer[] tqSeqArr
			,Double[] marksArr
			) {
		String userId = SessionUserInfoHelper.getUserId();
		ResultVO result = quizTestService.saveQuizTest(userId,testTmplSeq,testTmplNm,testTp,ctg1Seq,ctg2Seq,ctg3Seq,timeLimit,markTp,lookTp,ordTp,selectTp,choiceTp,lookCnt,totQstn,selectQstn,descr,totMarks,status);
		
		
		if(result.getResult() == 1){
			ResultVO result2 = quizTestService.saveQuizTestQstnMark(tqSeqArr,marksArr);
		}
		
		return result;
	}
	@ResponseBody
	@RequestMapping(value="saveQuizTestAddQstn.ajax",method=RequestMethod.POST)
	public ResultVO saveQuizTestAddQstn(ModelMap model
			,int testTmplSeq
			,int[] qstnSeqArr
			) {
		String userId = SessionUserInfoHelper.getUserId();
		ResultVO result = quizTestService.saveQuizTestAddQstn(userId,testTmplSeq,qstnSeqArr);
		return result;
	}
	@ResponseBody
	@RequestMapping(value="delQuizTestAddQstn.ajax",method=RequestMethod.POST)
	public ResultVO delQuizTestAddQstn(ModelMap model
			,int tqSeq
			) {
		String userId = SessionUserInfoHelper.getUserId();
		ResultVO result = quizTestService.delQuizTestAddQstn(userId,tqSeq);
		return result;
	}
	
	/*
	 * 시험지관리 > 삭제
	 */
	@ResponseBody
	@RequestMapping(value="delQuizTestQstn.ajax",method=RequestMethod.POST)
	public ResultVO quizTestDel(ModelMap model
			,int testTmplSeq
			) {
		String userId = SessionUserInfoHelper.getUserId();
		ResultVO result = quizTestService.delQuizTestQstn(userId,testTmplSeq);
		return result;
		    
	}
}
