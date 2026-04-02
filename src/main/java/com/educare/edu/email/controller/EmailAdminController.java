package com.educare.edu.email.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.educare.edu.comn.service.EmailService;
import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.education.service.impl.EduMapper;
import com.educare.util.LncUtil;



@Controller
@RequestMapping(value="/admin/email/")
public class EmailAdminController {
	@Resource(name="EmailService")
	private EmailService emailService;
	@Resource(name="EduMapper")
	private EduMapper eduMapper;
	
	/** 배너관리
	 * @param model
	 * @param vo
	 * @return
	 */
	@RequestMapping("emailList.do")
	public String emailList(ModelMap model
			,String srchWrd
			,String srchBeginDt
			,@RequestParam(defaultValue="1") Integer pageNo
			) {
		
		ResultVO result = emailService.getEmailLogPageList( 10, pageNo,srchBeginDt,srchWrd);
		
		model.addAttribute("data",result.getData());
        return "/admin/email/emailList" + LncUtil.TILES;
    }
	
}
