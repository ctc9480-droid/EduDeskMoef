package com.educare.edu.lcsub.service.impl;

import java.util.ArrayList;
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
import org.springframework.util.NumberUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.stringtemplate.v4.compiler.STParser.mapExpr_return;

import com.educare.edu.comn.mapper.CheckMapper;
import com.educare.edu.comn.mapper.ExamBankChoiceMapper;
import com.educare.edu.comn.mapper.ExamBankQuestionMapper;
import com.educare.edu.comn.mapper.FeedbackAnswerMapper;
import com.educare.edu.comn.mapper.LectureMapper;
import com.educare.edu.comn.mapper.LectureStdntMapper;
import com.educare.edu.comn.mapper.LectureSubjectMapper;
import com.educare.edu.comn.mapper.QuestionChoiceMapper;
import com.educare.edu.comn.mapper.QuestionMapper;
import com.educare.edu.comn.mapper.SmartCheckMapper;
import com.educare.edu.comn.mapper.TableMapper;
import com.educare.edu.comn.model.ExamBankChoice;
import com.educare.edu.comn.model.ExamBankQuestion;
import com.educare.edu.comn.model.Feedback;
import com.educare.edu.comn.model.FeedbackAnswer;
import com.educare.edu.comn.model.FeedbackChoice;
import com.educare.edu.comn.model.FeedbackQuestion;
import com.educare.edu.comn.model.LectureSubject;
import com.educare.edu.comn.model.Org;
import com.educare.edu.comn.model.PassCertTpl;
import com.educare.edu.comn.model.Question;
import com.educare.edu.comn.model.QuestionChoice;
import com.educare.edu.comn.service.CheckService;
import com.educare.edu.comn.service.SmartcheckService;
import com.educare.edu.comn.service.TableService;
import com.educare.edu.comn.vo.FeedbackVO;
import com.educare.edu.comn.vo.LectureRoomVO;
import com.educare.edu.comn.vo.PassCertTplVO;
import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.education.service.impl.EduMapper;
import com.educare.edu.education.service.model.Lecture;
import com.educare.edu.education.service.model.LectureStdnt;
import com.educare.edu.exam.mapper.ExamMapper;
import com.educare.edu.exam.service.ExamService;
import com.educare.edu.exam.vo.ExamVO;
import com.educare.edu.feedback.mapper.FeedbackMapper;
import com.educare.edu.feedback.service.FeedbackService;
import com.educare.edu.lcsub.mapper.LcsubMapper;
import com.educare.edu.lcsub.service.LcsubService;
import com.educare.edu.lcsub.vo.LcsubVO;
import com.educare.edu.member.service.SessionUserInfoHelper;
import com.educare.edu.passTpl.mapper.PassCertTplMapper;
import com.educare.edu.quizBank.mapper.QuizBankMapper;
import com.educare.edu.quizBank.service.QuizBankService;
import com.educare.edu.quizBank.vo.QuizBankVO;
import com.educare.edu.quizBank.web.QuizBankComponent;
import com.educare.util.DateUtil;
import com.educare.util.FileUtil;
import com.educare.util.LncUtil;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

@Service("LcsubService")
public class LcsubServiceImpl implements LcsubService {
	
	/** org.slf4j.Logger */
	private static final Logger LOG = LoggerFactory.getLogger(LcsubServiceImpl.class.getName());

	@Resource(name = "LcsubMapper")
	private LcsubMapper lcsubMapper;
	@Resource(name = "LectureSubjectMapper")
	private LectureSubjectMapper lectureSubjectMapper;
	@Resource(name = "PassCertTplMapper")
	private PassCertTplMapper passCertTplMapper;
	
	

	@Override
	public ResultVO getLcsubInfo(int subSeq) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			LectureSubject vo = new LectureSubject();
			vo.setSubSeq(subSeq);
			
			//과목가져오기
			LectureSubject sub = lectureSubjectMapper.selectByPk(vo);
			rstData.put("sub", sub);
			
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setResult(0);
			return result;
		}
	}


	@Override
	public ResultVO saveLcsub(String userId, int subSeq, String subNm, int passIdx,int complIdx, int state,String passCertNm,String complCertNm) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			//나중에 삭제가능한 과목인지 유효성 체크해야함
			
			String regId = userId;
			String updId = userId;
			Date regDe = DateUtil.getCalendar().getTime();
			Date updDe = DateUtil.getCalendar().getTime();
			
			//문제저장
			LectureSubject vo = new LectureSubject(subSeq, subNm, passIdx, complIdx, state, passCertNm, complCertNm, regId, updId, regDe, updDe);
			if(subSeq>0){
				//수정
				vo.setSubSeq(subSeq);
				lectureSubjectMapper.updateByPk(vo);
			}else{
				//저장
				lectureSubjectMapper.insertByPk(vo);
			}
			
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setResult(0);
			return result;
		}
	}


	@Override
	public ResultVO getLcsubPageList(int pageNo, int rowCnt, String srchWrd) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			LcsubVO vo = new LcsubVO();
			vo.setSrchWrd(srchWrd);
			
			//전체 수
			int totalCnt = lcsubMapper.selectLcsubPageCnt(vo);
			
			//페이징설정
			PaginationInfo page = new PaginationInfo();
			page.setTotalRecordCount(totalCnt);
			page.setCurrentPageNo(pageNo);
			page.setRecordCountPerPage(rowCnt);
			page.setPageSize(10);
	        vo.setRowCnt(rowCnt);
	        vo.setFirstIndex(page.getFirstRecordIndex());
			
			//목록
			List<LcsubVO> list = lcsubMapper.selectLcsubPageList(vo);
			
			rstData.put("list", list);
			rstData.put("pageNavi", page);
			
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setResult(0);
			return result;
		}
	}


	@Override
	public ResultVO getLcsubList(String srchWrd) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try{
			LcsubVO vo = new LcsubVO();
			vo.setState(1);
			vo.setSrchWrd(srchWrd);
			
			List<LcsubVO> list = lcsubMapper.selectLcsubPageList(vo);
			rstData.put("list", list);
			
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setResult(0);
			return result;
		}
	}
	
}
