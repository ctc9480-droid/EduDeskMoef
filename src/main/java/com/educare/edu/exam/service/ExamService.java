package com.educare.edu.exam.service;

import java.util.List;
import java.util.Map;

import org.stringtemplate.v4.compiler.STParser.mapExpr_return;

import com.educare.edu.comn.vo.FeedbackVO;
import com.educare.edu.exam.vo.ExamVO;

/**
 * 설문지 관리 서비스
 */
public interface ExamService {

	/**
	 * 문제등록
	 * @param param
	 * @param userId
	 * @param orgCd
	 * @return
	 */
	int saveExamBank(Map<String, Object> param, String userId, String orgCd);

	/**
	 * 문제가져오기
	 * @param returnData
	 * @param ebqIdx 
	 * @return
	 */
	int getExamBankInfo(Map<String, Object> returnData, int ebqIdx);

	Map<String, Object> getExamBankBoardData(String orgCd,int pageNo);

	
}
