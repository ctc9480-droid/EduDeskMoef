package com.educare.edu.site.web;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.educare.component.UtilComponent;
import com.educare.edu.bbs.service.InqryService;
import com.educare.edu.bbs.service.model.Inqry;
import com.educare.edu.education.service.EduService;
import com.educare.edu.education.service.model.LectureRcept;
import com.educare.edu.member.service.MemberService;
import com.educare.edu.member.service.SessionUserInfoHelper;
import com.educare.edu.member.service.model.UserInfo;
import com.educare.edu.member.service.model.UserInfoStdnt;
import com.educare.edu.statistics.service.StatsService;
import com.educare.edu.statistics.service.model.Tuition;
import com.educare.util.LncUtil;

/**
 * @Class Name : SiteAdminController.java
 * @author SI개발팀 박용주
 * @since 2020. 6. 2.
 * @version 1.0
 * @see
 * @Description 관리자 사이트 컨트롤러
 * 
 * <pre>
 *
 *
 * 수정일		    	수정자		    	수정내용
 * --------	  		---------	  		------------------------
 * 2020. 6. 2.	  	SI개발팀 박용주     		최초생성 
 * </pre>
 */
@Controller
public class SiteAdminController {
	
	/** 온라인 문의 서비스 */
	@Resource(name = "InqryService")
	private InqryService inqryService;
	
	/** 회원 서비스 */
	@Resource(name = "MemberService")
	private MemberService memberService;
	
	/** 통계 서비스 */
	@Resource(name = "StatsService")
	private StatsService statsService;
	
	/** 교육관리 서비스 */
	@Resource(name = "EduService")
	private EduService eduService;
	@Resource(name = "utcp")
	private UtilComponent utcp;
	
	/**
	 * 관리자 메인
	 * @param model
	 * @return
	 */
	@RequestMapping("/admin/main.do")
	public String adminMain(ModelMap model) {
		return "redirect:/admin/main/index.do";
    }
	
	/**
	 * 관리자 대시보드
	 * @param param
	 * @param request
	 * @return
	 */
	@RequestMapping("/admin/main/index.do")
	public String adminIndex(ModelMap model) {
		
		UserInfo user = SessionUserInfoHelper.getUserInfo();
		if(user == null){
			return null;
		}
		
		List<Inqry> inqryList = inqryService.getInqryMiniList(10);
		List<UserInfoStdnt> drmncyList = memberService.getDrmncyMiniList(10);
		List<LectureRcept> rceptList = eduService.getRceptMiniList(SessionUserInfoHelper.getUserInfo().getOrgCd(),10);
		
		model.addAttribute("inqryList", inqryList);
		model.addAttribute("drmncyList", drmncyList);
		model.addAttribute("rceptList", rceptList);
		
        return "/admin/main/index" + LncUtil.TILES;
    }
}
