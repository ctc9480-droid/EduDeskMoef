package com.educare.edu.member.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.hsqldb.persist.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartRequest;

import com.educare.component.UtilComponent;
import com.educare.component.VarComponent;
import com.educare.edu.comn.mapper.TableMapper;
import com.educare.edu.comn.model.Org;
import com.educare.edu.comn.service.SmsService;
import com.educare.edu.comn.vo.CheckVO;
import com.educare.edu.comn.vo.OrgVO;
import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.education.service.EduVO;
import com.educare.edu.member.service.MemberService;
import com.educare.edu.member.service.MemberVO;
import com.educare.edu.member.service.OprtAdminService;
import com.educare.edu.member.service.SessionUserInfoHelper;
import com.educare.edu.member.service.model.InstrctrAttach;
import com.educare.edu.member.service.model.InstrctrRealm;
import com.educare.edu.member.service.model.UserInfo;
import com.educare.edu.member.service.model.UserInfoInstrctr;
import com.educare.edu.member.service.model.UserInfoStdnt;
import com.educare.edu.menu.service.MenuService;
import com.educare.edu.tmpFile.service.TempFileService;
import com.educare.edu.tmpFile.service.model.TempFile;
import com.educare.util.CryptoUtil;
import com.educare.util.DateUtil;
import com.educare.util.FileUtil;
import com.educare.util.LncUtil;
import com.educare.util.MaxNumUtil;
import com.educare.util.SelfAuthUtil;

import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
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

@Service("OprtAdminService")
public class OprtAdminServiceImpl implements OprtAdminService {
	
	/** org.slf4j.Logger */
	private static final Logger LOG = LoggerFactory.getLogger(OprtAdminServiceImpl.class.getName());
	
	/** 회원 Mapper */
	@Resource(name = "MemberMapper")
	private MemberMapper memberMapper;
	
	/** 메뉴 서비스 */
	@Resource(name = "MenuService")
	private MenuService menuService;
	
	/** 임시파일 서비스 */
	@Resource(name = "TempFileService")
	private TempFileService tempFileService;
	
	/** table Mapper */
	@Resource(name = "TableMapper")
	private TableMapper tableMapper;
	
	@Resource(name = "SmsService")
	private SmsService smsService;

	@Override
	public ResultVO getOprtBbsList(MemberVO vo) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
 		
		vo.setSrchMemLvl("3");
		
		int totalCnt = memberMapper.selectMngrListCnt(vo);
		PaginationInfo page = new PaginationInfo();
		
		page.setTotalRecordCount(totalCnt);
		page.setCurrentPageNo(vo.getPage());
		page.setRecordCountPerPage(vo.getRow());
		page.setPageSize(PAGE_SIZE);
		
		vo.setFirstIndex(page.getFirstRecordIndex());
		
		List<UserInfo> userList = memberMapper.selectMngrList(vo);
		
		rstData.put("totalCnt", totalCnt);
		rstData.put("page", page);
		rstData.put("dataList", userList);
		
		return result;
			
	}

	@Override
	public UserInfo getOprt(String userId) {
		UserInfo userInfo =  memberMapper.selectUserInfoByPk(userId);
		return userInfo;
	}
	
}
