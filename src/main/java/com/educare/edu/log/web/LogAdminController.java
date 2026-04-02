package com.educare.edu.log.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.educare.component.UtilComponent;
import com.educare.edu.comn.vo.CategoryVO;
import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.log.service.LogService;
import com.educare.edu.log.service.model.LoginLog;
import com.educare.util.LncUtil;

/**
 * @Class Name : LoginController.java
 */
@Controller
@RequestMapping(value="/admin/log/")
public class LogAdminController {
	
	/** 로그인 서비스 */
	@Resource(name = "LogService")
	private LogService logService;
	@Resource(name = "utcp")
	private UtilComponent utcp;
	/** 
	 * 관리자 로그인
	 * @param userId
	 * @param userPw
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("userLoginLogList.do")
	public String adminLogin(
			ModelMap model
			,@ModelAttribute("vo") LoginLog vo
			,HttpServletRequest request
			,String srchBeginDt//필요한 검색파라미터는 여기게 추가하여 사용하였으면 좋겠음
			,String srchEndDt
			) {
		
		if(!ObjectUtils.isEmpty(srchBeginDt)){
			vo.setSrchBeginDt(srchBeginDt.replaceAll("-", ""));
		}
		if(!ObjectUtils.isEmpty(srchEndDt)){
			vo.setSrchEndDt(srchEndDt.replaceAll("-", ""));
		}
		
		ResultVO dataVO = logService.getLctreLoginLogPageListByMngr(vo,10);
		//서비스 호출 구간
		//List list = getLoginLog(srchBeginDt,srchEndDt);//이런형태로 서비스를 구성하기를 원함
		model.addAttribute("data", dataVO.getData());
		
		return "/admin/log/userLoginLogList"+LncUtil.TILES;
	}
	
}