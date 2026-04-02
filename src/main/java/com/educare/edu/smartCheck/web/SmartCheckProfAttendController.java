/**
 * 새로 추가된 메뉴,전자출결
 * 메뉴관리는 독립적으로 구축함
 * aop미적용
 * 원래는 MenuUtil.java에서 관리했었음
 */
package com.educare.edu.smartCheck.web;

import java.util.List;

import javax.annotation.Resource;
import javax.enterprise.inject.Model;
import javax.servlet.http.HttpServletRequest;

import org.hsqldb.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.educare.edu.bbs.BbsConstant;
import com.educare.edu.comn.mapper.SmartCheckMapper;
import com.educare.edu.comn.service.SmartcheckService;
import com.educare.edu.comn.vo.SmartCheckVO;
import com.educare.edu.education.service.EduService;
import com.educare.edu.member.service.SessionUserInfoHelper;
import com.educare.edu.menu.service.MenuUtil;
import com.educare.edu.smartCheck.SmartCheckConstants;
import com.educare.util.LncUtil;

import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

@Controller
@RequestMapping(value="/smartCheck/prof/attend/")
public class SmartCheckProfAttendController {
	
	/** org.slf4j.Logger */
	private static final Logger LOG = LoggerFactory.getLogger(SmartCheckProfAttendController.class);
	
	@Resource(name="SmartcheckService")
	private SmartcheckService smartCheckService;
	
	
}
