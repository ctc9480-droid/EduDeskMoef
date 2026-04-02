/**
 * 새로 추가된 메뉴,전자출결
 * 메뉴관리는 독립적으로 구축함
 * aop미적용
 * 원래는 MenuUtil.java에서 관리했었음
 */
package com.educare.edu.smartCheck.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.enterprise.inject.Model;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hsqldb.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.educare.edu.bbs.BbsConstant;
import com.educare.edu.comn.mapper.LectureTimeMapper;
import com.educare.edu.comn.mapper.SmartCheckMapper;
import com.educare.edu.comn.model.LectureTime;
import com.educare.edu.comn.service.SmartcheckService;
import com.educare.edu.comn.vo.SmartCheckVO;
import com.educare.edu.education.service.EduService;
import com.educare.edu.education.service.model.Lecture;
import com.educare.edu.member.service.MemberService;
import com.educare.edu.member.service.SessionUserInfoHelper;
import com.educare.edu.member.service.model.UserInfo;
import com.educare.edu.member.service.model.UserInfoStdnt;
import com.educare.edu.menu.service.MenuUtil;
import com.educare.edu.smartCheck.SmartCheckConstants;
import com.educare.util.LncUtil;

import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

@Controller
@RequestMapping(value="/smartCheck/prof/check/")
public class SmartCheckProfCheckController {
	
	/** org.slf4j.Logger */
	private static final Logger LOG = LoggerFactory.getLogger(SmartCheckProfCheckController.class);
	
	@Resource(name="SmartcheckService")
	private SmartcheckService smartcheckService;
	@Resource(name="MemberService")
	private MemberService memberService;
	@Resource(name="EduService")
	private EduService eduService;
	@Resource(name="LectureTimeMapper")
	private LectureTimeMapper lectureTimeMapper;
	
	@RequestMapping("stdntList.do")
	public String attendanceList(HttpServletRequest request, ModelMap model
			,SmartCheckVO params
			) {
		//로그인 체크
		if(!SessionUserInfoHelper.isLogined()){
			model.addAttribute("message",BbsConstant.MSG_ERR_LOGIN);
			return "alert_back";
		}
    	
		//강의 정보
		Lecture eduInfo = eduService.getLctreDetail(params.getEduSeq());
		model.addAttribute("eduInfo",eduInfo);
		
		//시간정보
		LectureTime ltParam = new LectureTime(params.getEduSeq(), params.getTimeSeq());
		LectureTime timeInfo = lectureTimeMapper.selectByPk(ltParam);
		model.addAttribute("timeInfo",timeInfo);
		
		//종합출석률
		SmartCheckVO eduAttInfo = smartcheckService.getEduAttInfo(params);
		model.addAttribute("eduAttInfo",eduAttInfo);
		
    	model.addAttribute("params",params);
    	model.addAttribute("tab","smartCheck/prof/check/stdntList_inc_tab");
		model.addAttribute("jsp","smartCheck/prof/check/stdntList");
		return SmartCheckConstants.LAYOUT_PROF;
	}	
	
	@RequestMapping(value = "studentList.json", method = {RequestMethod.GET, RequestMethod.POST})
    public String checkAjax(HttpServletRequest request, ModelMap model
    		,SmartCheckVO params
            ) {
		
		List<SmartCheckVO> studentList = smartcheckService.getStudentList(params);
		model.addAttribute("studentList",studentList);
    	return "jsonView";
    }
	
	
	//학생별 출결정보,사이드바
    @RequestMapping(value = "studentAttInfo.json", method = {RequestMethod.GET})
    public String studentAttInfo(ModelMap model
    		,HttpServletRequest request, HttpServletResponse response
    		,SmartCheckVO params
    		,String reqStudId,int eduSeq
    		) {
    	
    	//수업정보
    	Lecture eduInfo = eduService.getLctreDetail(eduSeq);
    	model.addAttribute("eduInfo", eduInfo);
    	
    	//학생정보
    	UserInfo stdntInfo = memberService.getStdntInfo(reqStudId);
    	model.addAttribute("stdntInfo", stdntInfo);
    	
    	//출결 합계 정보
    	SmartCheckVO attInfo = smartcheckService.getStdntAttInfo(eduSeq, reqStudId);
    	model.addAttribute("attInfo", attInfo);
    	
    	//출결정보
    	List<SmartCheckVO> attList = smartcheckService.getStdntAttList(eduSeq,reqStudId);
    	model.addAttribute("attList", attList);
    	
    	return "jsonView";
    }
    
}
