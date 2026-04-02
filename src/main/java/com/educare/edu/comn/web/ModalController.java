package com.educare.edu.comn.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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

import com.educare.component.VarComponent;
import com.educare.edu.bbs.service.model.BoardComment;
import com.educare.edu.comn.mapper.OnlineHistoryMapper;
import com.educare.edu.comn.mapper.TableMapper;
import com.educare.edu.comn.model.LectureTime;
import com.educare.edu.comn.model.OnlineHistory;
import com.educare.edu.comn.vo.TimeTableVO;
import com.educare.edu.education.service.EduService;
import com.educare.edu.education.service.model.Lecture;
import com.educare.edu.member.service.SessionUserInfoHelper;
import com.educare.util.XmlBean;
import com.google.gson.JsonObject;

@Controller
@RequestMapping(value="/comm/modal/")
public class ModalController {
	
	/** org.slf4j.Logger */
	private static final Logger LOG = LoggerFactory.getLogger(ModalController.class.getName());
	
	@Resource(name="TableMapper")
	private TableMapper tableMapper;
	@Resource(name="OnlineHistoryMapper")
	private OnlineHistoryMapper onlineHistoryMapper;
	
	@RequestMapping(value="timeTable.json")
	public String timeTable(ModelMap model
			,int eduSeq
			) { 
		TimeTableVO param = new TimeTableVO();
		param.setEduSeq(eduSeq);
		param.setUserId(SessionUserInfoHelper.getUserId());
		List<TimeTableVO> timeList = tableMapper.selectTimeTableList(param);
		
		model.addAttribute("timeList",timeList);
		return "jsonView";
	}
}
