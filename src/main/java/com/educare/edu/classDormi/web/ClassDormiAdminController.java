package com.educare.edu.classDormi.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.educare.component.UtilComponent;
import com.educare.edu.comn.model.LectureDormiPrice;
import com.educare.edu.comn.service.LectureDormiService;
import com.educare.edu.comn.vo.LectureDormiVO;
import com.educare.edu.comn.vo.LectureRoomVO;
import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.education.service.EduService;
import com.educare.edu.education.service.EduVO;
import com.educare.util.DateUtil;
import com.educare.util.LncUtil;

@Controller
@RequestMapping(value="/admin/classDormi/")
public class ClassDormiAdminController {
	
	/** org.slf4j.Logger */
	private static final Logger LOG = LoggerFactory.getLogger(ClassDormiAdminController.class.getName());
	
	@Resource(name="LectureDormiService")
	private LectureDormiService lectureDormiService;
	@Resource(name = "utcp")
	private UtilComponent utcp;
	@Resource(name = "EduService")
	private EduService eduService;
	
	@RequestMapping(value="classDormiCalInfo.do")
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
		
		return "/admin/classDormi/classDormiCalInfo"+ LncUtil.TILES;
	}
	@RequestMapping(value="classDormiCalInfo2.do")
	public String classDormiCalInfoAjax(ModelMap model
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
		
		List<LectureDormiVO> lectureList = lectureDormiService.getAssignLectureDormi(0,vo.getSrchYear(),vo.getSrchMonth());
		Map<String, Object> lecMap = this.getLectureCalMap(startDay, endDay, lectureList);
		
		
		model.addAttribute("calMap", calMap);
		model.addAttribute("lecMap", lecMap);
		model.addAttribute("days", calMap.size());
		model.addAttribute("month", month);
		return "/admin/classDormi/classDormiCalInfo2";
	}
	
	private Map<String, Object> getLectureCalMap(String startDay, String endDay, List<LectureDormiVO> list){
		
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
				List<LectureDormiVO> rceptList = new ArrayList<LectureDormiVO>();
				for(LectureDormiVO o : list){
					if(dateKey.compareTo(o.getStartDt().replaceAll("-", "")) >= 0 && dateKey.compareTo(o.getEndDt().replaceAll("-", "")) <= 0){
						rceptList.add(o);
					}
				}
				dateMap.put("rceptList", rceptList);
			}
			result.put(dateKey, dateMap);
		}
		System.out.println(result);
		return result;
		
	}
	@RequestMapping(value="classDormiMng.do")
	public String classDormiMng(ModelMap model
			,@ModelAttribute(value="vo2") EduVO vo2
			,@ModelAttribute LectureDormiVO vo
			) {
		
		Date curDate = new Date();
		if(ObjectUtils.isEmpty(vo2.getSrchYear())){
			vo2.setSrchYear(DateUtil.getDate2Str(curDate, "yyyy"));
		}
		if(ObjectUtils.isEmpty(vo2.getSrchMonth())){
			vo2.setSrchMonth(DateUtil.getDate2Str(curDate, "MM"));
		}
		
		//강의실 목록 가져오기
		ResultVO result = lectureDormiService.getLectureDormiPageList(vo);
		model.addAttribute("result",result);
		
		//가겫정보가져오기
		List<LectureDormiPrice> price = lectureDormiService.getLectureDormiPrice();
		model.addAttribute("price",price);
		
		return "/admin/classDormi/classDormiMng"+ LncUtil.TILES;
	}
	@RequestMapping(value="classDormiMngReg.do")
	public String classRoomMngReg(ModelMap model
			,@ModelAttribute LectureRoomVO vo
			,int dormiSeq
			) {
		
		if(dormiSeq > 0){
			LectureDormiVO room = lectureDormiService.getLectureDormi(dormiSeq);
			model.addAttribute("dormi",room);
		}
		
		
		return "/admin/classDormi/classDormiMngReg"+ LncUtil.TILES;
	}
	@ResponseBody
	@RequestMapping(value="classDormiMngRegProc.ajax")
	public ResultVO classRoomMngRegProc(ModelMap model
			,@ModelAttribute LectureDormiVO vo
			) {
		
		ResultVO result = lectureDormiService.saveLectureDormi(vo);
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="classDormiMngDelProc.ajax")
	public ResultVO classRoomMngDelProc(ModelMap model
			,Integer dormiSeq
			) {
		
		ResultVO result = lectureDormiService.deleteLectureDormi(dormiSeq);
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="getClassDormiByEduDt.ajax")
	public ResultVO getClassRoomByEduDt(ModelMap model
			,String eduDt
			) {
		eduDt = DateUtil.getDate2Str(DateUtil.getStr2Date(eduDt, "yyyyMMdd"),"yyyy-MM-dd");
		ResultVO result = lectureDormiService.getClassDormiInfoByEduDt(eduDt);
		
		return result;
	}
	@RequestMapping(value="classDormiMngCalInfo.do")
	public String classRoomMngCalInfo(ModelMap model
			,@ModelAttribute("vo") EduVO vo
			,int dormiSeq
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
		
		List<LectureDormiVO> lectureList = lectureDormiService.getAssignLectureDormi(dormiSeq,vo.getSrchYear(),vo.getSrchMonth());
		Map<String, Object> lecMap = this.getLectureCalMap(startDay, endDay, lectureList);
		
		model.addAttribute("calMap", calMap);
		model.addAttribute("lecMap", lecMap);
		model.addAttribute("days", calMap.size());
		model.addAttribute("month", month);
		return "/admin/classDormi/classDormiMngCalInfo";
	}
	
	
	@ResponseBody
	@RequestMapping(value="savePriceProc.ajax")
	public ResultVO savePriceProc(ModelMap model
			,int feeOff2
			,int feeOff4
			,int feePeak2
			,int feePeak4
			,String startDt
			,String endDt
			) {
		
		
		ResultVO result = lectureDormiService.savePriceProc(feeOff2,feeOff4,feePeak2,feePeak4,startDt,endDt);
		
		return result;
	}
}
