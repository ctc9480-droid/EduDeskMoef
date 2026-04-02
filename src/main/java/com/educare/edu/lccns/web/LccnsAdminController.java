package com.educare.edu.lccns.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.educare.component.UtilComponent;
import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.lccns.service.LccnsService;
import com.educare.edu.member.service.SessionUserInfoHelper;
import com.educare.edu.quizBank.service.QuizBankService;
import com.educare.edu.quizTest.service.QuizTestService;
import com.educare.util.LncUtil;

/**
 *시험지를 수업에 연결하는 컨트롤러
 *
 */
@Controller
@RequestMapping(value="/admin/lccns/")
public class LccnsAdminController {
	
	/** org.slf4j.Logger */
	private static final Logger LOG = LoggerFactory.getLogger(LccnsAdminController.class.getName());
	
	@Resource(name="LccnsService")
	private LccnsService lccnsService;
	@Resource(name = "utcp")
	private UtilComponent utcp;
	
	
	@RequestMapping(value="popup/lccnsList.do")
	public String lccnsList(ModelMap model,HttpServletResponse res
			,String userId
			,@RequestParam(defaultValue="0") Integer eduSeq
			,@RequestParam(defaultValue="1") Integer pageNo
			,String srchWrd
			) throws Exception {
		if(ObjectUtils.isEmpty(userId)){
			LncUtil.alertClose(res, "잘못된 경로로 진입하였습니다.");
			return null;
		}
		
		int row = 10;
		ResultVO result= lccnsService.getLccnsPageList(userId,eduSeq,pageNo, row, srchWrd);
		model.addAttribute("data",result.getData());
		
		model.addAttribute("jsp","admin/lccns/popup/lccnsList");
		return "layout/popup/baseLayout_notiles";
	}
	@RequestMapping(value="popup/lccnsReg.do")
	public String lccnsReg(ModelMap model
			,String userId
			,@RequestParam(defaultValue="0") Integer eduSeq
			,@RequestParam(defaultValue="0") Integer cnsSeq
			) {
		int row = 10;
		ResultVO result= lccnsService.getLccnsInfo(userId,eduSeq,cnsSeq);
		model.addAttribute("data",result.getData());
		
		model.addAttribute("jsp","admin/lccns/popup/lccnsReg");
		return "layout/popup/baseLayout_notiles";
	}
	@ResponseBody
	@RequestMapping(value="saveLccns.ajax",method=RequestMethod.POST)
	public ResultVO saveLcsub(ModelMap model
			,@RequestParam(defaultValue="0") Integer cnsSeq
			,@RequestParam(defaultValue="0") Integer eduSeq
			,String userId
			,String title
			,String content
			,@RequestParam(defaultValue="0") Integer state
			
			) {
		
		ResultVO result = lccnsService.saveLccns(userId,eduSeq,cnsSeq,title,content,state);
		return result;
	}
}
