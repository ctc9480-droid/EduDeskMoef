/**
 * 새로 추가된 메뉴,전자출결
 * 메뉴관리는 독립적으로 구축함
 * aop미적용
 * 원래는 MenuUtil.java에서 관리했었음
 */
package com.educare.edu.smartCheck.web;

import java.util.List;

import javax.annotation.Resource;
import javax.enterprise.inject.Model;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hsqldb.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.educare.edu.bbs.BbsConstant;
import com.educare.edu.comn.mapper.SmartCheckMapper;
import com.educare.edu.comn.service.SmartcheckService;
import com.educare.edu.comn.vo.SmartCheckVO;
import com.educare.edu.education.service.EduService;
import com.educare.edu.education.service.model.Lecture;
import com.educare.edu.member.service.MemberService;
import com.educare.edu.member.service.SessionUserInfoHelper;
import com.educare.edu.member.service.model.UserInfo;
import com.educare.edu.member.service.model.UserInfoInstrctr;
import com.educare.edu.member.service.model.UserInfoStdnt;
import com.educare.edu.menu.service.MenuUtil;
import com.educare.edu.smartCheck.SmartCheckConstants;
import com.educare.util.LncUtil;

import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

@Controller
@RequestMapping(value="/smartCheck/stud/stat/")
public class SmartCheckStudStatController {
	
	/** org.slf4j.Logger */
	private static final Logger LOG = LoggerFactory.getLogger(SmartCheckStudStatController.class);
	
	@Resource(name="SmartcheckService")
	private SmartcheckService smartCheckService;
	@Resource(name="MemberService")
	private MemberService memberService;
	@Resource(name="EduService")
	private EduService eduService;
	
	@RequestMapping("statList.do")
	public String attendanceList(HttpServletRequest request, ModelMap model
			,SmartCheckVO params
			) {
		//로그인 체크
		if(!SessionUserInfoHelper.isLogined()){
			model.addAttribute("message",BbsConstant.MSG_ERR_LOGIN);
			return "alert_back";
		}
		
		params.setReqStudId(SessionUserInfoHelper.getUserId());
		
		//교수정보
		UserInfo studInfo = memberService.getStdntInfo(SessionUserInfoHelper.getUserId());
		model.addAttribute("studInfo",studInfo);
		//수업리스트 가져오기
		List<SmartCheckVO> eduList = smartCheckService.getEduListByStud(params);
		model.addAttribute("eduList",eduList);

		model.addAttribute("params",params);
		model.addAttribute("tab","smartCheck/stud/stat/statList_inc_tab");
		model.addAttribute("jsp","smartCheck/stud/stat/statList");
		return SmartCheckConstants.LAYOUT_STUD;
	}	
	//학생별 출결정보,사이드바
    @RequestMapping(value = "studentAttInfo.json", method = {RequestMethod.GET})
    public String studentAttInfo(ModelMap model
    		,HttpServletRequest request, HttpServletResponse response
    		,SmartCheckVO params
    		,int eduSeq
    		) {
    	String reqStudId = SessionUserInfoHelper.getUserId();
    	
    	//수업정보
    	Lecture eduInfo = eduService.getLctreDetail(eduSeq);
    	model.addAttribute("eduInfo", eduInfo);
    	
    	//교수정보
    	String reqProfId = eduInfo.getInstrctrId();
    	UserInfoInstrctr profInfo = memberService.getInstrctrInfo(reqProfId);
    	model.addAttribute("profInfo", profInfo);
    	
    	//출결 합계 정보
    	SmartCheckVO attInfo = smartCheckService.getStdntAttInfo(eduSeq, reqStudId);
    	model.addAttribute("attInfo", attInfo);
    	
    	//출결정보
    	List<SmartCheckVO> attList = smartCheckService.getStdntAttList(eduSeq,reqStudId);
    	model.addAttribute("attList", attList);
    	
    	return "jsonView";
    }
}
