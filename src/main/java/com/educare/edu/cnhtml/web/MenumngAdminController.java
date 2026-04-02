package com.educare.edu.cnhtml.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.educare.component.UtilComponent;
import com.educare.edu.accip.service.AccipService;
import com.educare.edu.cnhtml.service.CnhtmlService;
import com.educare.edu.comn.mapper.AdminIpMapper;
import com.educare.edu.comn.model.AdminIp;
import com.educare.edu.comn.vo.CategoryVO;
import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.log.service.LogService;
import com.educare.edu.log.service.model.LoginLog;
import com.educare.util.LncUtil;

/**
 * @Class Name : LoginController.java
 */
@Controller
@RequestMapping(value="/admin/menumng/")
public class MenumngAdminController {
	
	@Resource(name = "utcp")
	private UtilComponent utcp;
	@Resource(name = "CnhtmlService")
	private CnhtmlService cnhtmlService;
	
	@RequestMapping("menumngList.do")
	public String accipList(ModelMap model,HttpServletRequest request
			) {
		
		return "/admin/menumng/menumngList"+LncUtil.TILES;
	}
	
}