package com.educare.edu.comn.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.educare.edu.comn.mapper.LectureTimeMapper;
import com.educare.edu.comn.mapper.OnlineHistoryMapper;
import com.educare.edu.comn.mapper.SmartCheckMapper;
import com.educare.edu.comn.mapper.StatMapper;
import com.educare.edu.comn.model.LectureTime;
import com.educare.edu.comn.model.OnlineHistory;
import com.educare.edu.comn.service.ExcelService;
import com.educare.edu.comn.service.MovService;
import com.educare.edu.comn.service.SmartcheckService;
import com.educare.edu.comn.vo.MovVO;
import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.comn.vo.SmartCheckVO;
import com.educare.edu.education.service.EduService;
import com.educare.edu.education.service.EduVO;
import com.educare.edu.education.service.impl.EduMapper;
import com.educare.edu.education.service.model.Lecture;
import com.educare.edu.log.service.model.LoginLog;
import com.educare.edu.member.service.SessionUserInfoHelper;
import com.educare.edu.member.service.model.UserInfo;

@Controller
@RequestMapping(value="/admin/excel/")
public class AdminExcelController {
	
	/** org.slf4j.Logger */
	private static final Logger LOG = LoggerFactory.getLogger(AdminExcelController.class.getName());
	
	@Resource(name="EduService")
	private EduService eduService;
	
	@Resource(name="MovService")
	private MovService movService;
	
	@Resource(name="ExcelService")
	private ExcelService excelService;
	
	@Resource(name="EduMapper")
	private EduMapper eduMapper;
	
	@Resource(name="SmartcheckService")
	private SmartcheckService smartcheckService;
	@Resource(name="SmartCheckMapper")
	private SmartCheckMapper smartCheckMapper;
	@Resource(name="OnlineHistoryMapper")
	private OnlineHistoryMapper onlineHistoryMapper;
	@Resource(name="LectureTimeMapper")
	private LectureTimeMapper lectureTimeMapper;
	@Resource(name="StatMapper")
	private StatMapper statMapper;
	
	@RequestMapping(value="rollBook.do")
	public String rollBook(ModelMap model
			,@RequestParam int eduSeq
			,@RequestParam(required=false) String userId
			) {
		
		//
		Lecture lecture = eduService.getLctreDetail(eduSeq);
		
		//날짜
		List<SmartCheckVO> dateList = smartcheckService.getDateList(eduSeq);
		// 시간
		List<SmartCheckVO> timeList = smartcheckService.getTimeList(eduSeq);
		//시간표
		List<SmartCheckVO> stdntList = smartcheckService.getStdntList(eduSeq,userId);
		
		model.addAttribute("eduNm", lecture.getEduNm());
		model.addAttribute("dateList", dateList);
		model.addAttribute("timeList", timeList);
		model.addAttribute("stdntList", stdntList);
		
		return "admin/excel/rollBook";
	}
	
	@RequestMapping(value="rollBook2.do")
	public String rollBook2(ModelMap model
			,@RequestParam int eduSeq
			,@RequestParam(required=false) String userId
			) {
		Lecture lecture = eduService.getLctreDetail(eduSeq);
		
		ResultVO result = smartcheckService.getRollBookDay(eduSeq,userId);
		Map<String, Object> rstData = (Map<String, Object>) result.getData();
		
		model.addAttribute("eduNm", lecture.getEduNm());
		model.addAttribute("dateList", rstData.get("dateList"));
//		model.addAttribute("timeList", timeList);
		model.addAttribute("stdntList", rstData.get("stdntList"));
		
		return "admin/excel/rollBook2";
	}
	
	@RequestMapping(value="onlineHistory.do")
	public String onlineHistory(ModelMap model
			,@RequestParam int eduSeq
			,@RequestParam int timeSeq
			) {
		
		try {
			//
			Lecture lecture = eduService.getLctreDetail(eduSeq);
			LectureTime ltParam = new LectureTime(eduSeq, timeSeq);
			model.addAttribute("eduNm",lecture.getEduNm());
			model.addAttribute("timeSeq",timeSeq);
			
			LectureTime ltInfo = lectureTimeMapper.selectByPk(ltParam);
			if(ltInfo.getClassHow()==3){
				//진도율
				List<MovVO> stdntList = movService.getStdntProgressList(eduSeq,timeSeq);
				model.addAttribute("list",stdntList);
				return "admin/excel/movProgress";
			}else{
				//로그 누적 
				OnlineHistory param = new OnlineHistory();
				param.setEduSeq(eduSeq);
				param.setTimeSeq(timeSeq);
				List<OnlineHistory> list = onlineHistoryMapper.selectByEduSeqTimeSeq(param);
				model.addAttribute("list",list);
				return "admin/excel/onlineHistory";
			}
		} catch (NullPointerException e) {
			return null;
		}
		
	}
	@RequestMapping(value="totalOnline.do")
	public String totalOnline(ModelMap model
			,@RequestParam int eduSeq
			) {
		try {
			Lecture info = eduService.getLctreDetail(eduSeq);
			model.addAttribute("eduNm",info.getEduNm());
			
			Map<String, Object> param = new HashMap<>();
			List<Map<String, Object>> checkUserList = new ArrayList<>();
			
			param.put("eduSeq", eduSeq);
			UserInfo user = SessionUserInfoHelper.getUserInfo();
			if(user != null){
				param.put("orgCd", user.getOrgCd());
			}
			List<Map<String, Object>> list = statMapper.getTotalOnline(param);
			
			loop1:
			for(Map<String, Object> o:list){
				String newUserId = o.get("user_id").toString();
				String newTimeSeq = o.get("time_seq").toString();
				float newMovTime =  StringUtils.isEmpty(o.get("mov_time"))?0: Float.valueOf(o.get("mov_time").toString());
				float newMovAllTime =  StringUtils.isEmpty(o.get("mov_all_time"))?0:Float.valueOf(o.get("mov_all_time").toString());
				
				String newEduDt = o.get("edu_dt").toString();
				int newClassHow = (int) o.get("class_how");
				Date newAccessDate = (Date) o.get("access_date");
				String newClassHowNm = "";
				switch (newClassHow) {
				case 0:newClassHowNm="오프라인";break;
				case 1:newClassHowNm="온라인";break;
				case 2:newClassHowNm="화상수업";break;
				case 3:newClassHowNm="동영상";break;
				default:break;
				}
				for(Map<String, Object> o2:checkUserList){
					if(o2.get("userId").equals(newUserId)){
						List<Map<String, Object>> timeList = (List<Map<String, Object>>) o2.get("timeList");
						Map<String, Object> timeMap = new HashMap<String, Object>();
						timeMap.put("timeSeq", newTimeSeq);
						timeMap.put("eduDt", newEduDt);
						timeMap.put("movTime", newMovTime);
						timeMap.put("movAllTime", newMovAllTime);
						timeMap.put("accessDate", newAccessDate);
						timeMap.put("classHow", newClassHow);
						timeMap.put("classHowNm", newClassHowNm);
						timeList.add(timeMap);
						continue loop1;
					}
				}
				Map<String, Object> userMap = new HashMap<String, Object>();
				userMap.put("userId", newUserId);
				userMap.put("email", o.get("email"));
				userMap.put("userNm", o.get("user_nm"));
				
				List<Map<String, Object>> timeList = new ArrayList<>();
				Map<String, Object> timeMap = new HashMap<String, Object>();
				timeMap.put("timeSeq", newTimeSeq);
				timeMap.put("eduDt", newEduDt);
				timeMap.put("movTime", newMovTime);
				timeMap.put("movAllTime", newMovAllTime);
				timeMap.put("accessDate", newAccessDate);
				timeMap.put("classHow", newClassHow);
				timeMap.put("classHowNm", newClassHowNm);
				timeList.add(timeMap);
				userMap.put("timeList", timeList);
				checkUserList.add(userMap);
			}
			
			
			model.addAttribute("checkUserList",checkUserList);
			return "admin/excel/totalOnline";
		} catch (NullPointerException e) {
			return null;
		}
		
	}
	
	@RequestMapping("rceptExcel.do")
	public String rceptExcel2(
			ModelMap model,
			@ModelAttribute("vo") EduVO vo,
			HttpServletRequest request, 
			HttpServletResponse response
			,int eduSeq
			) {
		
		Lecture lctre = eduService.getLctreDetail(eduSeq);
		if(lctre.getTargetTp() == 1){
			
			return "/admin/excel/rceptExcel_1";
		}
		
		return "/admin/excel/rceptExcel";
    }
	@RequestMapping("rceptPvExcel.do")
	public String rceptPvExcel2(
			ModelMap model,
			@ModelAttribute("vo") EduVO vo,
			HttpServletRequest request, 
			HttpServletResponse response) {
		return "/admin/excel/rceptPvExcel";
	}
	@RequestMapping("stdntExcel.do")
	public String stdntExcel(
			ModelMap model,
			@ModelAttribute("vo") EduVO vo,
			HttpServletRequest request, 
			HttpServletResponse response) {
		return "/admin/excel/stdntExcel";
    }
	
	@RequestMapping("feedbackExcel.do")
	public String feedbackExcel(
			ModelMap model,
			@ModelAttribute("vo") EduVO vo,
			HttpServletRequest request, 
			HttpServletResponse response) {
		return "/admin/excel/feedbackExcel";
    }
	
	@RequestMapping("loginLogExcel.do")
	public String loginLogExcel(
			ModelMap model,
			@ModelAttribute("vo") LoginLog vo,
			HttpServletRequest request, 
			HttpServletResponse response) {
		return "/admin/excel/loginLogExcel";
    }
	@RequestMapping("lcrcpExcel.do")
	public String lcrcpExcel(
			ModelMap model,
			@ModelAttribute("vo") EduVO vo,
			HttpServletRequest request, 
			HttpServletResponse response) {
		return "/admin/excel/lcrcpExcel";
    }
	
	// dykim, 2024.06.20
	@ResponseBody
	@RequestMapping("importLectureTimeExcel.ajax")
	public Object importLectureTimeExcel(
			ModelMap model
			,MultipartHttpServletRequest mr
			) {
		
		ResultVO result = excelService.getLectureScheduleByExcel(mr);
			
        return result;
    }
	
	// dykim, 2024.06.20
	@ResponseBody
	@RequestMapping("importLectureStdntExcel.ajax")
	public Object importLectureStdntExcel(
			ModelMap model
			,MultipartHttpServletRequest mr
			) {
		
		ResultVO result = excelService.getLectureStdntByExcel(mr);
			
        return result;
    }
	
	@RequestMapping("quizTestEduStdntListExcel.do")
	public String quizTestEduStdntListExcel(ModelMap model,	HttpServletRequest request, HttpServletResponse response
			) {
		
		return "/admin/excel/quizTestEduStdntListExcel";
    }
	
	@RequestMapping("dormiMealExcel.do")
	public String dormiMealExcel(ModelMap model,	HttpServletRequest request, HttpServletResponse response
			) {
		
		return "/admin/excel/dormiMealExcel";
    }
}
