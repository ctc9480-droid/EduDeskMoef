package com.educare.edu.smartCheck.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.enterprise.inject.Model;
import javax.servlet.http.HttpServletRequest;

import org.hsqldb.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.educare.edu.comn.mapper.SmartCheckMapper;
import com.educare.edu.comn.model.Org;
import com.educare.edu.comn.service.SmartcheckService;
import com.educare.edu.comn.service.TableService;
import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.comn.vo.SmartCheckVO;
import com.educare.edu.education.service.EduService;
import com.educare.edu.education.service.model.Lecture;
import com.educare.edu.member.service.SessionUserInfoHelper;
import com.educare.edu.member.service.model.UserInfo;
import com.educare.util.DateUtil;
import com.educare.util.LncUtil;

import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

@Controller
@RequestMapping(value = "/admin/smartCheck/")
public class SmartCheckAdminController {

	/** org.slf4j.Logger */
	private static final Logger LOG = LoggerFactory.getLogger(SmartCheckAdminController.class);

	@Resource(name = "SmartCheckMapper")
	private SmartCheckMapper smartCheckMapper;
	@Resource(name = "TableService")
	private TableService tableService;
	@Resource(name = "EduService")
	private EduService eduService;
	@Resource(name = "SmartcheckService")
	private SmartcheckService smartcheckService;

	// 출결현황
	@RequestMapping("attendanceList.do")
	public String attendanceList(HttpServletRequest request, ModelMap model, SmartCheckVO param) {
		
		if("2".equals(SessionUserInfoHelper.getUserMemLvl())){
			UserInfo user = SessionUserInfoHelper.getUserInfo();
			String orgCd = "";
			if(user!=null){
				orgCd = user.getOrgCd();
			}
			param.setReqOrgCd(orgCd);
		}

		// 기관리스트 가져오기
		List<Org> orgList = tableService.getOrgList();
		
		int totalCnt = smartCheckMapper.getLectureStatCnt(param);
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setTotalRecordCount(totalCnt);
		paginationInfo.setCurrentPageNo(param.getPageNo());
		paginationInfo.setRecordCountPerPage(10);
		paginationInfo.setPageSize(10);

		param.setFirstRecordIndex(paginationInfo.getFirstRecordIndex());
		param.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		List<SmartCheckVO> list = smartCheckMapper.getLectureStatList(param);

		model.addAttribute("totalCnt", totalCnt);
		model.addAttribute("list", list);
		model.addAttribute("pageNavi", paginationInfo);
		model.addAttribute("orgList", orgList);
		return "/admin/edu/attendanceList" + LncUtil.TILES;
	}

	// 출결현황
	@RequestMapping("attendanceView.do")
	public String attendanceView(HttpServletRequest request, ModelMap model
			,@RequestParam(defaultValue="0") int eduSeq
			) {
		Lecture lctre = eduService.getLctreDetail(eduSeq);
		
		model.addAttribute("lctre",lctre);
		return "/admin/edu/attendanceView" + LncUtil.TILES;
	}
	
	@RequestMapping(value="popup/rollbook.do")
	public String popup_quizTestList(ModelMap model
			,int eduSeq
			,String userId
			) {
		
		Lecture lctre = eduService.getLctreDetail(eduSeq);
		model.addAttribute("lctre",lctre);
		
		if("1,2,3,4".contains(SessionUserInfoHelper.getUserMemLvl())){
			model.addAttribute("isCheck",true);
		}
		
		model.addAttribute("jsp","admin/smartcheck/popup/rollbook");
		return "/layout/popup/baseLayout_notiles";
	}
	@ResponseBody
	@RequestMapping(value="rollBookDay.ajax",method=RequestMethod.GET)
	public ResultVO checkList(ModelMap model
			,@RequestParam int eduSeq
			,@RequestParam(required=false) String userId
			) {
		ResultVO result = smartcheckService.getRollBookDay(eduSeq,userId);
		
		return result;
	}
	@ResponseBody
	@RequestMapping(value="attDayProc.ajax",method=RequestMethod.POST)
	public ResultVO attDayProc(ModelMap model
			,int eduSeq
			,String userId
			,String eduDt
			,String beginHhMm
			,String endHhMm
			,int attTp
			) {
		
		Date beginDe = null;
		Date endDe = null;
		
		if(!ObjectUtils.isEmpty(beginHhMm)){
			beginDe = DateUtil.getStr2Date(eduDt+""+beginHhMm.replaceAll(":", ""), "yyyyMMddHHmm");
		}
		if(!ObjectUtils.isEmpty(endHhMm)){
			endDe = DateUtil.getStr2Date(eduDt+""+endHhMm.replaceAll(":", ""), "yyyyMMddHHmm");
		}
		
		ResultVO result = smartcheckService.saveAttDay(eduSeq,userId,eduDt,beginDe,endDe,attTp);
		
		return result;
	}
	@ResponseBody
	@RequestMapping(value="attDayProc2.ajax",method=RequestMethod.POST)
	public ResultVO attDayProc2(ModelMap model
			,int eduSeq
			,String userId
			,String eduDt
			,String hhMm
			,int attTp
			,String qrType
			) {
		
		
		//오전,점심,오후 qr추가로직-251023
		Date at1De = null;
		Date at2De = null;
		Date at3De = null;
		if("1".equals(qrType)){
			at1De = DateUtil.getStr2Date(eduDt+""+hhMm.replaceAll(":", ""), "yyyyMMddHHmm");
		}else if ("2".equals(qrType)){
			at2De = DateUtil.getStr2Date(eduDt+""+hhMm.replaceAll(":", ""), "yyyyMMddHHmm");
		}else if ("3".equals(qrType)){
			at3De = DateUtil.getStr2Date(eduDt+""+hhMm.replaceAll(":", ""), "yyyyMMddHHmm");
		}
		
		ResultVO result = smartcheckService.saveAttDay_251023(eduSeq, userId, eduDt, attTp, at1De, at2De, at3De);
		
		return result;
	}
}
