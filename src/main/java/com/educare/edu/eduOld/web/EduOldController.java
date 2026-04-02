package com.educare.edu.eduOld.web;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.eduOld.service.EduOldService;
import com.educare.edu.education.service.EduVO;
import com.educare.edu.member.service.SessionUserInfoHelper;
import com.educare.edu.member.service.model.UserInfo;
import com.educare.util.LncUtil;

@Controller
@RequestMapping(value="/user/mypage/")
public class EduOldController {
	
	/** org.slf4j.Logger */
	private static final Logger LOG = LoggerFactory.getLogger(EduOldController.class.getName());
	
	/** 구버전 교육관리시스템 서비스 */
	@Resource(name = "EduOldService")
	private EduOldService eduOldService; 
	
	//나의 결제내역
	@RequestMapping("myEduOldList.do")
	public String myLcRceptList(ModelMap model
			,@ModelAttribute EduVO vo
			) {
		
		if(!SessionUserInfoHelper.isLogined()){
			model.addAttribute("message", "로그인이 필요한 서비스입니다.");
			model.addAttribute("moveUrl", "/user/index.do");
			return "alert";
		}else{
			if(!(UserInfo.MEM_LVL_STDNT+","+UserInfo.MEM_LVL_STDNT2).contains(SessionUserInfoHelper.getUserMemLvl())){
				model.addAttribute("message", "접근 권한이 없습니다.");
				model.addAttribute("moveUrl", "/user/index.do");	
				return "alert";
			}
		}
		String userId = SessionUserInfoHelper.getUserId();
		userId = "jdc7355"; //"jaro1";	// test code
		vo.setUserId(userId);
		vo.setSrchSort("REGDESC");
		ResultVO dataVO = eduOldService.selectEduOldPageList(vo,10);
		model.addAttribute("data", dataVO.getData());
		
		return "/user/eduOld/myEduOldList" + LncUtil.TILES;
	}
}
