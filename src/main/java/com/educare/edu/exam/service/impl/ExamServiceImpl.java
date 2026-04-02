package com.educare.edu.exam.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
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
import org.stringtemplate.v4.compiler.STParser.mapExpr_return;

import com.educare.edu.comn.mapper.CheckMapper;
import com.educare.edu.comn.mapper.ExamBankChoiceMapper;
import com.educare.edu.comn.mapper.ExamBankQuestionMapper;
import com.educare.edu.comn.mapper.FeedbackAnswerMapper;
import com.educare.edu.comn.mapper.LectureMapper;
import com.educare.edu.comn.mapper.LectureStdntMapper;
import com.educare.edu.comn.mapper.SmartCheckMapper;
import com.educare.edu.comn.mapper.TableMapper;
import com.educare.edu.comn.model.ExamBankChoice;
import com.educare.edu.comn.model.ExamBankQuestion;
import com.educare.edu.comn.model.Feedback;
import com.educare.edu.comn.model.FeedbackAnswer;
import com.educare.edu.comn.model.FeedbackChoice;
import com.educare.edu.comn.model.FeedbackQuestion;
import com.educare.edu.comn.model.Org;
import com.educare.edu.comn.service.CheckService;
import com.educare.edu.comn.service.SmartcheckService;
import com.educare.edu.comn.service.TableService;
import com.educare.edu.comn.vo.FeedbackVO;
import com.educare.edu.education.service.impl.EduMapper;
import com.educare.edu.education.service.model.Lecture;
import com.educare.edu.education.service.model.LectureStdnt;
import com.educare.edu.exam.mapper.ExamMapper;
import com.educare.edu.exam.service.ExamService;
import com.educare.edu.exam.vo.ExamVO;
import com.educare.edu.feedback.mapper.FeedbackMapper;
import com.educare.edu.feedback.service.FeedbackService;
import com.educare.edu.member.service.SessionUserInfoHelper;
import com.educare.util.DateUtil;
import com.educare.util.LncUtil;

import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

@Service("ExamService")
public class ExamServiceImpl implements ExamService {
	
	/** org.slf4j.Logger */
	private static final Logger LOG = LoggerFactory.getLogger(ExamServiceImpl.class.getName());

	@Resource(name = "ExamBankQuestionMapper")
	private ExamBankQuestionMapper examBankQuestionMapper;
	@Resource(name = "ExamBankChoiceMapper")
	private ExamBankChoiceMapper examBankChoiceMapper;
	@Resource(name = "ExamMapper")
	private ExamMapper examMapper;
	
	@Override
	public Map<String, Object> getExamBankBoardData(String orgCd,int pageNo) {
		Map<String, Object> data = new HashMap<String, Object>();
		
		ExamVO exParam = new ExamVO();
		int cnt = examMapper.getBoardBankCnt(exParam);
		
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setTotalRecordCount(cnt);
		paginationInfo.setCurrentPageNo(pageNo);
		paginationInfo.setRecordCountPerPage(10);
		paginationInfo.setPageSize(10);
		
		List<ExamVO> list = examMapper.getBoardBankList(exParam);
		
		data.put("boardList", list);
		data.put("boardCnt", cnt);
		data.put("pageNavi",paginationInfo);
		return data;
	}

	@Override
	public int saveExamBank(Map<String, Object> param, String userId, String orgCd) {
		try {
			int ebqIdx = LncUtil.nvlInt(param.get("ebqIdx"));
			
			Map<String, Object> qtMap = (Map<String, Object>) param.get("qtMap");
			String question=qtMap.get("question").toString();
			String answerText= ObjectUtils.nullSafeToString(qtMap.get("answerText"));
			int questionType=(int) qtMap.get("questionType");
			ExamBankQuestion ebqParam = new ExamBankQuestion(question, answerText, questionType
					,DateUtil.getCalendar().getTime()
					,DateUtil.getCalendar().getTime());
			
			if(ebqIdx==0){
				//문제저장
				examBankQuestionMapper.insertByPk(ebqParam);
				ebqIdx = ebqParam.getEbqIdx();
			}else{
				//문제수정
				ebqParam.setEbqIdx(ebqIdx);
				examBankQuestionMapper.updateByPk(ebqParam);
			}
					
			//객관식이라면 객관문항 저장
			if(questionType==1){
				List<Map<String, Object>> chList = (List<Map<String, Object>>) param.get("chList");
				int ebcIdx = 0;
				String choice="";
				int correctType=0;
				int status=0;
				ExamBankChoice ebcParam = new ExamBankChoice();
				ebcParam.setEbqIdx(ebqIdx);
				//객관삭제 ,초기화
				examBankChoiceMapper.deleteByEbqIdx(ebcParam);
				
				for(Map<String, Object> chMap:chList){
					ebcIdx++;
					choice = (String) chMap.get("choice");
					correctType = LncUtil.nvlInt(chMap.get("correctType"));
					status = LncUtil.nvlInt(chMap.get("status"));
					
					ebcParam.setEbcIdx(ebcIdx);
					ebcParam.setChoice(choice);
					ebcParam.setCorrectType(correctType);
					ebcParam.setStatus(status);
					//객관저장
					examBankChoiceMapper.insertByPk(ebcParam);
				}
			}
			return 1;
		} catch (NullPointerException e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return 0;
		}
	}

	@Override
	public int getExamBankInfo(Map<String, Object> returnData,int ebqIdx) {
		try {
			ExamVO exParam = new ExamVO();
			exParam.setEbqIdx(ebqIdx);
			ExamVO qtMap = examMapper.getExamBankQuestionInfo(exParam);
			List<ExamVO> chList = examMapper.getExamBankChoiceList(exParam);
			returnData.put("qtMap", qtMap);
			returnData.put("chList", chList);
			return 1;
		} catch (NullPointerException e) {
			return 0;
		}
	}

}
