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
@RequestMapping(value="/admin/cnhtml/")
public class CnhtmlAdminController {
	
	@Resource(name = "utcp")
	private UtilComponent utcp;
	@Resource(name = "CnhtmlService")
	private CnhtmlService cnhtmlService;
	
	@RequestMapping("cnhtmlList.do")
	public String accipList(ModelMap model,HttpServletRequest request
			,@RequestParam(defaultValue="") String srchWrd
			,@RequestParam(defaultValue="1") Integer page
			,@RequestParam(defaultValue="0") Integer cnhtSeq
			) {
		
		ResultVO result = cnhtmlService.getCnhtmlPageList(srchWrd,page);
		model.addAttribute("data",result.getData());
		return "/admin/cnhtml/cnhtmlList"+LncUtil.TILES;
	}
	@RequestMapping("cnhtmlWrite.do")
	public String accipList(ModelMap model,HttpServletRequest request
			,@RequestParam(defaultValue="0") Integer cnhtSeq
			) {
		
		ResultVO result = cnhtmlService.getCnhtmlData(cnhtSeq);
		model.addAttribute("data",result.getData());
		return "/admin/cnhtml/cnhtmlWrite"+LncUtil.TILES;
	}
	@ResponseBody
	@RequestMapping("saveCnhtmlProc.ajax")
	public ResultVO saveCnhtmlProc(ModelMap model,HttpServletRequest request
			,@RequestParam(defaultValue="0") Integer cnhtSeq
			,@RequestParam(defaultValue="") String cnhtType
			,@RequestParam(defaultValue="") String title
			,@RequestParam(defaultValue="") String content
			,@RequestParam(defaultValue="") Integer state
			) {
		content = LncUtil.unescapeHtml(content);
		ResultVO result = cnhtmlService.saveCnhtmlProc(cnhtSeq,cnhtType,title,content,state);
		return result;
	}
	
}