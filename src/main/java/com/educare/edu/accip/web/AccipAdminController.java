package com.educare.edu.accip.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.educare.component.UtilComponent;
import com.educare.edu.accip.service.AccipService;
import com.educare.edu.comn.model.AdminIp;
import com.educare.edu.comn.vo.ResultVO;
import com.educare.util.LncUtil;

/**
 * @Class Name : LoginController.java
 */
@Controller
@RequestMapping(value="/admin/accip/")
public class AccipAdminController {
	
	@Resource(name = "utcp")
	private UtilComponent utcp;
	@Resource(name = "AccipService")
	private AccipService accipService;
	
	//접근제한 아이피 관리
	@RequestMapping("accipList.do")
	public String accipList(ModelMap model,HttpServletRequest request
			,@RequestParam(defaultValue="") String srchWrd
			,@RequestParam(defaultValue="1") Integer page
			,@RequestParam(defaultValue="0") Integer idx
			) {
		if(idx>0){
			AdminIp ip = accipService.getAccip(idx);
			model.addAttribute("ip",ip);
		}
		
		ResultVO result = accipService.getAccipPageList(srchWrd,page);
		model.addAttribute("data",result.getData());
		return "/admin/accip/accipList"+LncUtil.TILES;
	}
	
	@ResponseBody
	@RequestMapping("delAccIpProc.ajax")
	public ResultVO delAccIpProc(ModelMap model,HttpServletRequest request
			,@RequestParam(defaultValue="0") Integer idx
//			,@RequestParam(defaultValue="") String ip4
//			,@RequestParam(defaultValue="") String memo
			) {
		
		ResultVO result = accipService.delAccipProc(idx);
		return result;
	}
	
	@ResponseBody
	@RequestMapping("saveAccIpProc.ajax")
	public ResultVO saveAccIpProc(ModelMap model,HttpServletRequest request
			,@RequestParam(defaultValue="0") Integer idx
			,@RequestParam(defaultValue="") String ip4
			,@RequestParam(defaultValue="") String memo
			) {
		
		ResultVO result = accipService.saveAccipProc(idx,ip4,memo);
		return result;
	}
	
}