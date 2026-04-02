package com.educare.edu.sms.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.educare.edu.comn.service.SmsService;
import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.education.service.impl.EduMapper;
import com.educare.util.LncUtil;



@Controller
@RequestMapping(value="/admin/sms/")
public class SmsAdminController {
	@Resource(name="SmsService")
	private SmsService smsService;
	@Resource(name="EduMapper")
	private EduMapper eduMapper;
	
	/** 배너관리
	 * @param model
	 * @param vo
	 * @return
	 */
	@RequestMapping("smsList.do")
	public String smsList(ModelMap model
			,String srchWrd
			,String srchBeginDt
			,@RequestParam(defaultValue="1") Integer pageNo
			) {
		
		ResultVO result = smsService.getSmsLogPageList( 10, pageNo,srchBeginDt,srchWrd);
		
		model.addAttribute("data",result.getData());
        return "/admin/sms/smsList" + LncUtil.TILES;
    }
	
}
