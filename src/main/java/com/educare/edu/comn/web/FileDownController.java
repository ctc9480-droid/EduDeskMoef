package com.educare.edu.comn.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
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

import com.educare.aop.SuperAdmCheck;
import com.educare.component.VarComponent;
import com.educare.edu.bbs.service.model.BoardComment;
import com.educare.edu.comn.mapper.SmartCheckMapper;
import com.educare.edu.comn.model.LectureTime;
import com.educare.edu.comn.service.CheckService;
import com.educare.edu.comn.service.SmartcheckService;
import com.educare.edu.comn.vo.SmartCheckVO;
import com.educare.edu.education.service.EduService;
import com.educare.edu.education.service.impl.EduMapper;
import com.educare.edu.education.service.model.Lecture;
import com.educare.edu.education.service.model.LectureStdnt;
import com.educare.edu.member.service.SessionUserInfoHelper;
import com.educare.edu.tmpFile.service.TempFileService;
import com.educare.util.FileUtil;
import com.educare.util.LncUtil;
import com.educare.util.XmlBean;
import com.google.gson.JsonObject;

@Controller
public class FileDownController {
	
	private static final Logger LOG = LoggerFactory.getLogger(FileDownController.class.getName());
	
	@RequestMapping("/admin/cloud/download.do")
	public void download(HttpServletRequest request, HttpServletResponse response
			,String cloudPath
			,String cloudFile
			,String downNm
			) throws Exception {
		if(!SessionUserInfoHelper.isAdmin()){
			LncUtil.alertBack(response, "권한이 없습니다.");
			return ;
		}
		//NaverObjectStorage.download(cloudPath, cloudFile);
		//String filePath = TempFileService.TEMP_DOWNLOAD_PATH+cloudPath+cloudFile;
		//FileUtil.download(filePath, downNm, request, response);
		String filePath = cloudPath+cloudFile;
		FileUtil.download(filePath, downNm, request, response);
	}
	
	
	public String getToday() {
    	LocalDate now = LocalDate.now();
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    	String formatedNow = now.format(formatter);
    	
    	return formatedNow;
    }

    public String getToday2() {
    	LocalDate now = LocalDate.now();
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    	String formatedNow = now.format(formatter);
    	
    	return formatedNow;
    }
    
    public String getNow() {
    	LocalTime now = LocalTime.now();
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    	String formatedNow = now.format(formatter);
    	
    	return formatedNow;
    }

    public String getNow2() {
    	LocalTime now = LocalTime.now();
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmmss");
    	String formatedNow = now.format(formatter);
    	
    	return formatedNow;
    }
	
}
