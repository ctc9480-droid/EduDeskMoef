package com.educare.edu.education.service.impl;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.educare.component.VarComponent;
import com.educare.edu.comn.mapper.LectureRceptMapper;
import com.educare.edu.comn.mapper.LectureStdntMapper;
import com.educare.edu.comn.mapper.PayMapper;
import com.educare.edu.comn.mapper.TableMapper;
import com.educare.edu.comn.model.Pay;
import com.educare.edu.comn.service.SmsService;
import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.education.service.EduService;
import com.educare.edu.education.service.EduVO;
import com.educare.edu.education.service.LctreRceptService;
import com.educare.edu.education.service.LctreService;
import com.educare.edu.education.service.TuitionFeeService;
import com.educare.edu.education.service.model.Lecture;
import com.educare.edu.education.service.model.LectureRcept;
import com.educare.edu.education.service.model.LectureStdnt;
import com.educare.edu.education.service.model.LectureTutfee;
import com.educare.edu.member.service.SessionUserInfoHelper;
import com.educare.edu.member.service.impl.MemberMapper;
import com.educare.edu.member.service.model.UserInfoStdnt;
import com.educare.util.DateUtil;
import com.educare.util.LncUtil;
import com.educare.util.MaxNumUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

@Service("LctreRceptService")
public class LctreRceptServiceImpl implements LctreRceptService {
	
	/** org.slf4j.Logger */
	private static final Logger LOG = LoggerFactory.getLogger(LctreRceptServiceImpl.class);
	
	/** 교육관리 Mapper */
	@Resource(name = "EduMapper")
	private EduMapper eduMapper;
	
	/** 회원 Mapper */
	@Resource(name = "MemberMapper")
	private MemberMapper memberMapper;
	
	/** 교육개설 서비스 */
	@Resource(name = "LctreService")
	private LctreService lctreService;
	
	@Resource(name = "TableMapper")
	private TableMapper tableMapper;
	
	/** 교육개설 서비스 */
	@Resource(name = "EduService")
	private EduService eduService;
	@Resource(name = "SmsService")
	private SmsService smsService;
	@Resource(name = "PayMapper")
	private PayMapper payMapper;
	@Resource(name = "LectureStdntMapper")
	private LectureStdntMapper lectureStdntMapper;
	
	
	
}
