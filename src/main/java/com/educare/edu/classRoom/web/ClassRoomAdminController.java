package com.educare.edu.classRoom.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.stringtemplate.v4.compiler.STParser.mapExpr_return;

import com.educare.component.UtilComponent;
import com.educare.component.VarComponent;
import com.educare.edu.bbs.service.model.BoardComment;
import com.educare.edu.comn.model.LectureTime;
import com.educare.edu.comn.service.CheckService;
import com.educare.edu.comn.service.LectureRoomService;
import com.educare.edu.comn.vo.FeedbackVO;
import com.educare.edu.comn.vo.LectureRoomVO;
import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.education.service.EduService;
import com.educare.edu.education.service.EduVO;
import com.educare.edu.education.service.model.Lecture;
import com.educare.edu.exam.service.ExamService;
import com.educare.edu.exam.vo.ExamVO;
import com.educare.edu.feedback.mapper.FeedbackMapper;
import com.educare.edu.feedback.service.FeedbackService;
import com.educare.edu.member.service.SessionUserInfoHelper;
import com.educare.edu.sample.service.SampleService;
import com.educare.edu.sample.vo.SampleVO;
import com.educare.util.DateUtil;
import com.educare.util.LncUtil;
import com.educare.util.XmlBean;
import com.google.gson.JsonObject;

@Controller
@RequestMapping(value="/admin/classRoom/")
public class ClassRoomAdminController {
	
	/** org.slf4j.Logger */
	private static final Logger LOG = LoggerFactory.getLogger(ClassRoomAdminController.class.getName());
	
	@Resource(name="LectureRoomService")
	private LectureRoomService lectureRoomService;
	@Resource(name = "utcp")
	private UtilComponent utcp;
	@Resource(name = "EduService")
	private EduService eduService;
	
	@RequestMapping(value="classRoomCalInfo.do")
	public String classRoomCalInfo(ModelMap model
			,@ModelAttribute("vo") EduVO vo
			) {
		Date curDate = new Date();
		if(ObjectUtils.isEmpty(vo.getSrchYear())){
			vo.setSrchYear(DateUtil.getDate2Str(curDate, "yyyy"));
		}
		if(ObjectUtils.isEmpty(vo.getSrchMonth())){
			vo.setSrchMonth(DateUtil.getDate2Str(curDate, "MM"));
		}
		
		return "/admin/classRoom/classRoomCalInfo"+ LncUtil.TILES;
	}
	@RequestMapping(value="classRoomCalInfo2.do")
	public String classRoomCalInfoAjax(ModelMap model
			,@ModelAttribute("vo") EduVO vo
			) {
		
		Date curDate = new Date();
		if(ObjectUtils.isEmpty(vo.getSrchYear())){
			vo.setSrchYear(DateUtil.getDate2Str(curDate, "yyyy"));
		}
		if(ObjectUtils.isEmpty(vo.getSrchMonth())){
			vo.setSrchMonth(DateUtil.getDate2Str(curDate, "MM"));
		}
		int year = Integer.parseInt(vo.getSrchYear());
		int month = Integer.parseInt(vo.getSrchMonth());
		
		String startDay = DateUtil.getFirstDayOfWeek(year, month, 1, "yyyyMMdd");
		String lastDay = DateUtil.getLastDayOfMonth(year, month, 1, "yyyyMMdd");
		String endDay = DateUtil.getLastSaturdayOfCalendar(year, month, Integer.parseInt(lastDay.substring(6, 8)), "yyyyMMdd");
		
		Map<Integer, String> calMap = LncUtil.getCalMap(startDay, endDay);
		
		List<Lecture> lectureList = eduService.getResvLectureRoom(0,vo.getSrchYear(),vo.getSrchMonth());
		Map<String, Object> lecMap = this.getLectureCalMap(startDay, endDay, lectureList);
		
		model.addAttribute("calMap", calMap);
		model.addAttribute("lecMap", lecMap);
		model.addAttribute("days", calMap.size());
		model.addAttribute("month", month);
		return "/admin/classRoom/classRoomCalInfo2";
	}
	
	private Map<String, Object> getLectureCalMap(String startDay, String endDay, List<Lecture> list){
		
		if(startDay == null || endDay == null || list == null){
			return null;
		}
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		int calDays = 0;
		String dateKey = "";
		
		int sy = Integer.parseInt(startDay.substring(0, 4));
		int sm = Integer.parseInt(startDay.substring(4, 6));
		int sd = Integer.parseInt(startDay.substring(6, 8));
		
		calDays = LncUtil.calDays(startDay, endDay);
		
		for(int i = 0; i <= calDays; i++){
			Map<String, Object> dateMap = new HashMap<String,Object>();
			dateKey = DateUtil.dayCalForStr(sy, sm, sd, i, "", "yyyyMMdd");
			dateMap.put("calDt", dateKey);
			if(list != null && !list.isEmpty() && dateKey != null){
				List<Lecture> rceptList = new ArrayList<Lecture>();
				for(Lecture o : list){
					if(dateKey.equals(o.getEduDt())){
						rceptList.add(o);
					}
				}
				dateMap.put("rceptList", rceptList);
			}
			result.put(dateKey, dateMap);
		}
		return result;
		
	}
	@RequestMapping(value="classRoomMng.do")
	public String classRoomMng(ModelMap model
			,@ModelAttribute(value="vo2") EduVO vo2
			,@ModelAttribute LectureRoomVO vo
			) {
		
		Date curDate = new Date();
		if(ObjectUtils.isEmpty(vo2.getSrchYear())){
			vo2.setSrchYear(DateUtil.getDate2Str(curDate, "yyyy"));
		}
		if(ObjectUtils.isEmpty(vo2.getSrchMonth())){
			vo2.setSrchMonth(DateUtil.getDate2Str(curDate, "MM"));
		}
		
		//강의실 목록 가져오기
		ResultVO result = lectureRoomService.getLectureRoomPageList(vo);
		model.addAttribute("result",result);
		
		return "/admin/classRoom/classRoomMng"+ LncUtil.TILES;
	}
	@RequestMapping(value="classRoomMngReg.do")
	public String classRoomMngReg(ModelMap model
			,@ModelAttribute LectureRoomVO vo
			,int roomSeq
			) {
		
		if(roomSeq > 0){
			LectureRoomVO room = lectureRoomService.getLectureRoom(roomSeq);
			model.addAttribute("room",room);
		}
		
		
		return "/admin/classRoom/classRoomMngReg"+ LncUtil.TILES;
	}
	@ResponseBody
	@RequestMapping(value="classRoomMngRegProc.ajax")
	public ResultVO classRoomMngRegProc(ModelMap model
			,@ModelAttribute LectureRoomVO vo
			) {
		
		ResultVO result = lectureRoomService.saveLectureRoom(vo);
		
		return result;
	}
	@ResponseBody
	@RequestMapping(value="classRoomMngDelProc.ajax")
	public ResultVO classRoomMngDelProc(ModelMap model
			,Integer roomSeq
			) {
		
		ResultVO result = lectureRoomService.deleteLectureRoom(roomSeq);
		
		return result;
	}
	@ResponseBody
	@RequestMapping(value="classRoomExcptProc.ajax")
	public ResultVO classRoomExcptProc(ModelMap model
			,int roomSeq
			,String dataJson
			) {
		
		ResultVO result = lectureRoomService.saveLectureRoomExcpt(roomSeq,dataJson);
		
		return result;
	}
	@ResponseBody
	@RequestMapping(value="classRoomExcptInfo.ajax")
	public ResultVO classRoomExcptInfo(ModelMap model
			,int roomSeq
			) {
		
		ResultVO result = lectureRoomService.getLectureRoomExcpt(roomSeq);
		
		return result;
	}
	@ResponseBody
	@RequestMapping(value="getClassRoomByEduDt.ajax")
	public ResultVO getClassRoomByEduDt(ModelMap model
			,String eduDt
			) {
		
		ResultVO result = eduService.getClassRoomInfoByEduDt(eduDt);
		
		return result;
	}
	@RequestMapping(value="classRoomMngCalInfo.do")
	public String classRoomMngCalInfo(ModelMap model
			,@ModelAttribute("vo") EduVO vo
			,int roomSeq
			) {
		
		Date curDate = new Date();
		if(ObjectUtils.isEmpty(vo.getSrchYear())){
			vo.setSrchYear(DateUtil.getDate2Str(curDate, "yyyy"));
		}
		if(ObjectUtils.isEmpty(vo.getSrchMonth())){
			vo.setSrchMonth(DateUtil.getDate2Str(curDate, "MM"));
		}
		int year = Integer.parseInt(vo.getSrchYear());
		int month = Integer.parseInt(vo.getSrchMonth());
		
		String startDay = DateUtil.getFirstDayOfWeek(year, month, 1, "yyyyMMdd");
		String lastDay = DateUtil.getLastDayOfMonth(year, month, 1, "yyyyMMdd");
		String endDay = DateUtil.getLastSaturdayOfCalendar(year, month, Integer.parseInt(lastDay.substring(6, 8)), "yyyyMMdd");
		
		Map<Integer, String> calMap = LncUtil.getCalMap(startDay, endDay);
		
		List<Lecture> lectureList = eduService.getResvLectureRoom(roomSeq,vo.getSrchYear(),vo.getSrchMonth());
		Map<String, Object> lecMap = this.getLectureCalMap(startDay, endDay, lectureList);
		
		model.addAttribute("calMap", calMap);
		model.addAttribute("lecMap", lecMap);
		model.addAttribute("days", calMap.size());
		model.addAttribute("month", month);
		return "/admin/classRoom/classRoomMngCalInfo";
	}
}
