package com.educare.edu.passTpl.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.stringtemplate.v4.compiler.STParser.mapExpr_return;

import com.educare.edu.comn.mapper.CheckMapper;
import com.educare.edu.comn.mapper.FeedbackAnswerMapper;
import com.educare.edu.comn.mapper.FeedbackEduMapper;
import com.educare.edu.comn.mapper.FeedbackEduResultMapper;
import com.educare.edu.comn.mapper.LectureMapper;
import com.educare.edu.comn.mapper.LectureStdntMapper;
import com.educare.edu.comn.mapper.SmartCheckMapper;
import com.educare.edu.comn.mapper.TableMapper;
import com.educare.edu.comn.model.Feedback;
import com.educare.edu.comn.model.FeedbackAnswer;
import com.educare.edu.comn.model.FeedbackChoice;
import com.educare.edu.comn.model.FeedbackEdu;
import com.educare.edu.comn.model.FeedbackEduResult;
import com.educare.edu.comn.model.FeedbackQuestion;
import com.educare.edu.comn.model.Org;
import com.educare.edu.comn.model.Test;
import com.educare.edu.comn.service.CheckService;
import com.educare.edu.comn.service.SmartcheckService;
import com.educare.edu.comn.service.TableService;
import com.educare.edu.comn.vo.FeedbackVO;
import com.educare.edu.comn.vo.PassCertTplVO;
import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.education.service.impl.EduMapper;
import com.educare.edu.education.service.model.Lecture;
import com.educare.edu.education.service.model.LectureStdnt;
import com.educare.edu.feedback.mapper.FeedbackMapper;
import com.educare.edu.feedback.service.FeedbackService;
import com.educare.edu.member.service.SessionUserInfoHelper;
import com.educare.edu.member.service.model.UserInfo;
import com.educare.edu.passTpl.mapper.PassCertTplMapper;
import com.educare.edu.passTpl.service.PassTplService;
import com.educare.edu.quizTest.vo.QuizTestVO;
import com.educare.util.LncUtil;

@Service("PassTplService")
public class PassTplServiceImpl implements PassTplService {
	
	/** org.slf4j.Logger */
	private static final Logger LOG = LoggerFactory.getLogger(PassTplServiceImpl.class.getName());

	@Resource(name="PassCertTplMapper") 
	private PassCertTplMapper passCertTplMapper;

	@Override
	public ResultVO getPassTplPageList(int pageNo, int rowCnt, String srchWrd) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			PassCertTplVO vo = new PassCertTplVO();
			vo.setSrchWrd(srchWrd);
			
			//전체 수
			int totalCnt = passCertTplMapper.selectPassTplPageCnt(vo);
			
			//페이징설정
			PaginationInfo page = new PaginationInfo();
			page.setTotalRecordCount(totalCnt);
			page.setCurrentPageNo(pageNo);
			page.setRecordCountPerPage(rowCnt);
			page.setPageSize(10);
	        vo.setRowCnt(rowCnt);
	        vo.setFirstIndex(page.getFirstRecordIndex());
			
			//목록
			List<QuizTestVO> list = passCertTplMapper.selectPassTplPageList(vo);
			
			rstData.put("list", list);
			rstData.put("pageNavi", page);
			
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setResult(0);
			return result;
		}
	}
}
