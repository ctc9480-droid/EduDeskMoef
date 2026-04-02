package com.educare.edu.quizBank.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.educare.component.UtilComponent;
import com.educare.edu.comn.model.Question;
import com.educare.edu.comn.vo.CategoryVO;
import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.education.service.model.Lecture;
import com.educare.edu.member.service.SessionUserInfoHelper;
import com.educare.edu.quizBank.service.QuizBankService;
import com.educare.edu.quizCate.service.QuizCateService;
import com.educare.edu.quizCate.vo.QstnCategoryVO;
import com.educare.edu.quizCate.web.QuizCateAdminController;
import com.educare.util.LncUtil;

@Controller
@RequestMapping(value="/admin/quizBank/")
public class QuizBankAdminController {
	
	/** org.slf4j.Logger */
	private static final Logger LOG = LoggerFactory.getLogger(QuizBankAdminController.class.getName());
	
	@Resource(name="QuizBankService")
	private QuizBankService quizBankService;
	@Resource(name = "utcp")
	private UtilComponent utcp;
	
	/** 카테고리 서비스 */
	@Resource(name = "QuizCateService")
	private QuizCateService quizCateService; 
	
	@RequestMapping(value="quizBankList.do")
	public String feedbackList(ModelMap model
			,@RequestParam(defaultValue="1") int pageNo
			,String srchWrd
			,@RequestParam(defaultValue="0") Integer srchCtg1Seq
			,@RequestParam(defaultValue="0") Integer srchQstnTp
			) {
		List<QstnCategoryVO> cateList = quizCateService.getCategoryListAdmin(QstnCategoryVO.DEPTH_EDU);
		model.addAttribute("cateList",cateList);
		
		int row = 10;
		ResultVO result = quizBankService.getQuizBankPageList(pageNo,row,srchWrd,srchQstnTp,srchCtg1Seq);
		model.addAttribute("data",result.getData());
		
		return "/admin/quizBank/quizBankList"+ LncUtil.TILES;
	}
	
	@ResponseBody
	@RequestMapping(value="quizBankInfo.ajax")
	public ResultVO quizBankInfo(ModelMap model
			,@RequestParam(defaultValue="0") int qstnSeq
			) {
		
		ResultVO result= quizBankService.getQuizBankInfo(qstnSeq);
		
		return result;
	}
	
	@RequestMapping(value="quizBankReg.do")
	public String feedbackReg(ModelMap model
			,@RequestParam(defaultValue="0") Integer qstnSeq
			) {
		
		ResultVO result= quizBankService.getQuizBankInfo(qstnSeq);
		model.addAttribute("data",result.getData());
		
		//카테고리 조회
		List<QstnCategoryVO> cateList = quizCateService.getCategoryListAdmin(QstnCategoryVO.DEPTH_EDU);
		model.addAttribute("cateList",cateList);
		Map<String, Object> rstData = (Map<String, Object>) result.getData();
		Question qstn = (Question) rstData.get("qstn");
		if(qstn!=null){
			List<QstnCategoryVO> cateList2 = quizCateService.getCategoryChildList(qstn.getCtg1Seq());
			model.addAttribute("cateList2", cateList2);
			List<QstnCategoryVO> cateList3 = quizCateService.getCategoryChildList(qstn.getCtg2Seq());
			model.addAttribute("cateList3", cateList3);
		}
		
		return "/admin/quizBank/quizBankReg"+ LncUtil.TILES;
	}
	
	@ResponseBody
	@RequestMapping(value="saveQuizBank.ajax",method=RequestMethod.POST)
	public ResultVO saveQuizBank(ModelMap model
			,@RequestParam(defaultValue="0") Integer qstnSeq
			,@RequestParam(required=true) String qstnNm
			,@RequestParam(defaultValue="0") Integer ctg1Seq
			,@RequestParam(defaultValue="0") Integer ctg2Seq
			,@RequestParam(defaultValue="0") Integer ctg3Seq
			,@RequestParam(defaultValue="0")  Integer diffType
			,@RequestParam(defaultValue="0")  Integer qstnTp
			,String qstnStr
			,String cn
			,String ansDesc
			,String fillBlank
			,@RequestParam(defaultValue="N") String useYn
			,String chListJson
			) {
		
		String qstnDesc = cn;//lucy필터때문에 cn사용
		
		String userId = SessionUserInfoHelper.getUserId();
		ResultVO result = quizBankService.saveQuizBank(userId,qstnSeq,qstnNm,ctg1Seq,ctg2Seq,ctg3Seq,diffType,qstnTp,qstnStr,qstnDesc,ansDesc,useYn,chListJson,fillBlank);
		return result;
	}
	@ResponseBody
	@RequestMapping(value="saveQuizBankFile.ajax")
	public ResultVO saveQuizBankFile(ModelMap model
			,MultipartHttpServletRequest mhsr
			,int qstnSeq
			) {
		
		String userId = SessionUserInfoHelper.getUserId();
		ResultVO result = quizBankService.saveQuizBankFile(mhsr,qstnSeq,userId);
		return result;
	}
	
	/**
	 * qstnSeq는 0이상이여야하고
	 * chSeq가 -1이면 문제파일삭제
	 * chSeq가 0보다크면 객관식파일 삭제
	 * @param model
	 * @param qstnSeq
	 * @param chSeq:
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="delQuizBankFile.ajax")
	public ResultVO delQuizBankFile(ModelMap model
			,int qstnSeq
			,int chSeq
			,String fnRnm
			) {
		
		String userId = SessionUserInfoHelper.getUserId();
		ResultVO result = quizBankService.delQuizBankFile(qstnSeq,chSeq,fnRnm);
		return result;
	}

	@ResponseBody
	@RequestMapping(value="getQuizBankList.ajax")
	public ResultVO getQuizTestAdd(ModelMap model
			,int testTmplSeq
			,@RequestParam(defaultValue="") String srchWrd
			,@RequestParam(defaultValue="0") Integer srchCtgry
			,@RequestParam(defaultValue="0") Integer srchCtgry2
			,@RequestParam(defaultValue="0") Integer srchCtgry3
			) {
		ResultVO result= quizBankService.getQuizBankList(testTmplSeq,srchWrd,srchCtgry,srchCtgry2,srchCtgry3);
		
		return result;
	}
	@ResponseBody
	@RequestMapping(value="uploadQuizBankExcel.ajax")
	public ResultVO uploadQuizBankExcel(ModelMap model
			,MultipartHttpServletRequest mhsr
			,int ctg1Seq
			,int ctg2Seq
			,int ctg3Seq
			,@RequestParam(defaultValue="0") Integer forceReg
			) {
		
		ResultVO result = quizBankService.uploadQuizBankExcel(mhsr,ctg1Seq,ctg2Seq,ctg3Seq,forceReg);
		return result;
	}
	
	/*
	 * 문제은행 > 삭제
	 */
	@ResponseBody
	@RequestMapping(value="delQuizBank.ajax",method=RequestMethod.POST)
	public ResultVO quizBankDel(ModelMap model
			,int qstnSeq
			) {
		String userId = SessionUserInfoHelper.getUserId();
		ResultVO result = quizBankService.quizBankDel(userId,qstnSeq);
		return result;
		    
	}
	
	
	
	
}
