package com.educare.edu.comn.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.educare.edu.comn.mapper.LectureTimeMapper;
import com.educare.edu.comn.model.LectureTime;
import com.educare.edu.comn.service.AjaxService;
import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.education.service.EduService;
import com.educare.edu.education.service.model.Lecture;
import com.educare.edu.member.service.SessionUserInfoHelper;
import com.educare.util.DateUtil;
import com.educare.util.LncUtil;

@Controller
@RequestMapping(value="/comm/popup/")
public class PopupController {
	
	/** org.slf4j.Logger */
	private static final Logger LOG = LoggerFactory.getLogger(PopupController.class.getName());
	
	@Resource(name="EduService")
	private EduService eduService;
	@Resource(name="AjaxService")
	private AjaxService ajaxService;
	
	@Resource(name="LectureTimeMapper")
	private LectureTimeMapper lectureTimeMapper;
	
	
	@RequestMapping(value="eduMap.do")
	public String eduMap(ModelMap model
			,Integer eduSeq
			,Integer tmSeq
			) {
		if(LncUtil.nvlInt(eduSeq)>0){
			Lecture lctre = eduService.getLctreDetail(eduSeq);
			model.addAttribute("lctre",lctre);
		}
		
		return "comm/popup/eduMap";
	}
	
	@RequestMapping(value="{jspNm}.do")
	public String jspNm(ModelMap model
			,@PathVariable String jspNm
			) {
		
		model.addAttribute("jsp",jspNm);
		return "comm/popup/layout";
	}
	
	@RequestMapping(value="myLcRceptPop.do")
	public String cancelRcept(ModelMap model,HttpServletResponse res
			,int eduSeq
			,int rceptSeq
			,String userId
			) throws Exception {
		if(!SessionUserInfoHelper.isLogined()){
			LncUtil.alertClose(res, "잘못된 접근입니다.");
			return null;
		}
		
		String userId2 = userId;
		if(!SessionUserInfoHelper.isAdmin()){
			userId2 = SessionUserInfoHelper.getUserId();
		}
		ResultVO result = eduService.getLcrcpInfo(eduSeq, userId2, rceptSeq);
		model.addAttribute("data",result.getData());
		
		//교육시간 계산하기
		String classTime = "";
		LectureTime ltParam = new LectureTime();
		ltParam.setEduSeq(eduSeq);
		List<LectureTime> ltList = lectureTimeMapper.selectByEduSeq(ltParam);
		try {
			long allTimeMillis = 0;
			for(LectureTime o : ltList){
				SimpleDateFormat format = new SimpleDateFormat("HHmm",Locale.KOREA);
				Date date1 = format.parse(o.getStartTm());
			    Date date2 = format.parse(o.getEndTm());
			    long timeDifferenceMillis = date2.getTime() - date1.getTime();
			    allTimeMillis+=timeDifferenceMillis;
			}
			classTime = DateUtil.calculateStrTm(allTimeMillis);
		} catch (ParseException e) {
			classTime = "ERROR";
		}
		model.addAttribute("classTime",classTime);
		
		model.addAttribute("jspNm","myLcRceptPop");
		return "comm/popup/layout";
	}
	
	@RequestMapping(value="cancelRcept.do")
	public String cancelRcept(ModelMap model,HttpServletResponse res
			,String limsSrNo
			) throws Exception {
		
		model.addAttribute("jspNm","cancelRcept");
		
		return "comm/popup/layout";
	}
	@RequestMapping(value="cancelRceptAdmin.do")
	public String cancelRceptAdmin(ModelMap model,HttpServletResponse res
			,int rceptSeq
			) throws Exception {
		if(!SessionUserInfoHelper.isAdmin()){
			LncUtil.alertClose(res, "관리자만 접근 가능합니다.");
			return null;
		}
		model.addAttribute("jspNm","cancelRceptAdmin");
		
		//환불정보 조회
		ResultVO result = ajaxService.getRefundRcept(rceptSeq);
		if(result.getResult() != 1){
			LncUtil.alertClose(res, "오류가 발생하였습니다.");
			return null;
		}
		model.addAttribute("data",result.getData());
		
		return "comm/popup/layout";
	}
	
	@RequestMapping(value="nameCard.do")
	public String nameCard(ModelMap model,HttpServletResponse res
			,String userId
			,@RequestParam(defaultValue="0") Integer eduSeq
			,String srchWrd
			) throws Exception {
		if(ObjectUtils.isEmpty(userId)){
			LncUtil.alertClose(res, "잘못된 경로로 진입하였습니다.");
			return null;
		}
		
		ResultVO result = ajaxService.getNameCardInfo(eduSeq,userId);
		model.addAttribute("data",result.getData());
				
		
		model.addAttribute("jspNm","nameCard");
		
		return "comm/popup/layout";
	}
}
