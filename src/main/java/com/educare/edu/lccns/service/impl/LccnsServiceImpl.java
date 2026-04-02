package com.educare.edu.lccns.service.impl;

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
import com.educare.edu.comn.mapper.LectureCounsMapper;
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
import com.educare.edu.comn.model.LectureCouns;
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
import com.educare.edu.lccns.mapper.LccnsMapper;
import com.educare.edu.lccns.service.LccnsService;
import com.educare.edu.lccns.vo.LccnsVO;
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

@Service("LccnsService")
public class LccnsServiceImpl implements LccnsService {
	
	/** org.slf4j.Logger */
	private static final Logger LOG = LoggerFactory.getLogger(LccnsServiceImpl.class.getName());

	@Resource(name = "LccnsMapper")
	private LccnsMapper lccnsMapper;
	@Resource(name = "LectureCounsMapper")
	private LectureCounsMapper lectureCounsMapper;
	
	

	@Override
	public ResultVO getLccnsInfo(String userId, int eduSeq,int cnsSeq) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			LectureCouns vo = new LectureCouns();
			vo.setCnsSeq(cnsSeq);
			
			//과목가져오기
			LectureCouns cns = lectureCounsMapper.selectByPk(vo);
			rstData.put("cns", cns);
			
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setResult(0);
			return result;
		}
	}


	@Override
	public ResultVO saveLccns(String userId, int eduSeq, int cnsSeq, String title, String content, int state) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			
			
			String sessId = SessionUserInfoHelper.getUserId();
			
			String regId = sessId;
			String updId = sessId;
			Date regDe = DateUtil.getCalendar().getTime();
			Date updDe = DateUtil.getCalendar().getTime();
			
			//문제저장
			LectureCouns vo = new LectureCouns(cnsSeq, userId, eduSeq, title, content, state, regId, regDe, updId, updDe);
			if(cnsSeq>0){
				vo.setCnsSeq(cnsSeq);
				
				//수정가능한 상태인지 유효성체크 필요
				LectureCouns cns = lectureCounsMapper.selectByPk(vo);
				if(!cns.getRegId().equals(sessId)){
					result.setMsg("본인이 작성한 상담이 아닙니다.");
					result.setResult(0);
					return result;
				}
				
				//수정
				lectureCounsMapper.updateByPk(vo);
			}else{
				//저장
				lectureCounsMapper.insertByPk(vo);
			}
			
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setResult(0);
			return result;
		}
	}


	@Override
	public ResultVO getLccnsPageList(String userId,int eduSeq,int pageNo, int rowCnt, String srchWrd) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try {
			LccnsVO vo = new LccnsVO();
			vo.setSrchWrd(srchWrd);
			vo.setUserId(userId);
			vo.setEduSeq(eduSeq);
			
			//전체 수
			int totalCnt = lccnsMapper.selectLccnsPageCnt(vo);
			
			//페이징설정
			PaginationInfo page = new PaginationInfo();
			page.setTotalRecordCount(totalCnt);
			page.setCurrentPageNo(pageNo);
			page.setRecordCountPerPage(rowCnt);
			page.setPageSize(10);
	        vo.setRowCnt(rowCnt);
	        vo.setFirstIndex(page.getFirstRecordIndex());
			
			//목록
			List<LccnsVO> list = lccnsMapper.selectLccnsPageList(vo);
			
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
	public ResultVO getLccnsList(String userId,int eduSeq,String srchWrd) {
		ResultVO result = new ResultVO();
		Map<String, Object> rstData = new HashMap<String,Object>();
		result.setData(rstData);
		try{
			LccnsVO vo = new LccnsVO();
			vo.setState(1);
			vo.setSrchWrd(srchWrd);
			
			List<LccnsVO> list = lccnsMapper.selectLccnsPageList(vo);
			rstData.put("list", list);
			
			result.setResult(1);
			return result;
		} catch (NullPointerException e) {
			result.setResult(0);
			return result;
		}
	}
	
}
