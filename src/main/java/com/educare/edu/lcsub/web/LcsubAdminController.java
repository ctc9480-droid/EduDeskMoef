package com.educare.edu.lcsub.web;

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
import com.educare.edu.comn.vo.PassCertTplVO;
import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.education.service.model.Lecture;
import com.educare.edu.lcsub.service.LcsubService;
import com.educare.edu.member.service.SessionUserInfoHelper;
import com.educare.edu.passTpl.mapper.PassCertTplMapper;
import com.educare.edu.quizBank.service.QuizBankService;
import com.educare.edu.quizCate.service.QuizCateService;
import com.educare.edu.quizCate.vo.QstnCategoryVO;
import com.educare.edu.quizCate.web.QuizCateAdminController;
import com.educare.util.LncUtil;

@Controller
@RequestMapping(value="/admin/lcsub/")
public class LcsubAdminController {
	
	/** org.slf4j.Logger */
	private static final Logger LOG = LoggerFactory.getLogger(LcsubAdminController.class.getName());
	
	@Resource(name="LcsubService")
	private LcsubService lcsubService;
	@Resource(name = "utcp")
	private UtilComponent utcp;
	@Resource(name = "PassCertTplMapper")
	private PassCertTplMapper passCertTplMapper;
	
	@RequestMapping(value="lcsubList.do")
	public String lcsubList(ModelMap model
			,@RequestParam(defaultValue="1") int pageNo
			,String srchWrd
			) {
		int row = 10;
		ResultVO result = lcsubService.getLcsubPageList(pageNo,row,srchWrd);
		model.addAttribute("data",result.getData());
		
		return "/admin/lcsub/lcsubList"+ LncUtil.TILES;
	}
	
	@RequestMapping(value="lcsubReg.do")
	public String lcsubReg(ModelMap model
			,@RequestParam(defaultValue="0") Integer subSeq
			) {
		
		ResultVO result= lcsubService.getLcsubInfo(subSeq);
		model.addAttribute("data",result.getData());
		
		//이수증목록조회
		PassCertTplVO passVO = new PassCertTplVO();
		passVO.setPassTp(2);
		List<PassCertTplVO> complList = passCertTplMapper.selectPassCertTplList(passVO);
		model.addAttribute("complList",complList);
		passVO.setPassTp(3);
		List<PassCertTplVO> passList = passCertTplMapper.selectPassCertTplList(passVO);
		model.addAttribute("passList",passList);
		
		return "/admin/lcsub/lcsubReg"+ LncUtil.TILES;
	}
	
	@ResponseBody
	@RequestMapping(value="saveLcsub.ajax",method=RequestMethod.POST)
	public ResultVO saveLcsub(ModelMap model
			,@RequestParam(defaultValue="0") Integer subSeq
			,@RequestParam(required=true) String subNm
			,@RequestParam(defaultValue="0") Integer passIdx
			,@RequestParam(defaultValue="0") Integer complIdx
			,@RequestParam(defaultValue="0") Integer state
			,String passCertNm
			,String complCertNm
			
			) {
		
		String userId = SessionUserInfoHelper.getUserId();
		ResultVO result = lcsubService.saveLcsub(userId,subSeq,subNm,passIdx,complIdx,state,passCertNm,complCertNm);
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="lcsubList.ajax", method=RequestMethod.GET)
	public ResultVO lectureSubList(ModelMap model
			,String srchWrd
			) {
		
		ResultVO result = lcsubService.getLcsubList(srchWrd);
		return result;
	}
}
