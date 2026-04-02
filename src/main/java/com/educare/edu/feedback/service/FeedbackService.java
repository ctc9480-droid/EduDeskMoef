package com.educare.edu.feedback.service;

import java.util.List;
import java.util.Map;

import org.stringtemplate.v4.compiler.STParser.mapExpr_return;

import com.educare.edu.comn.vo.FeedbackVO;
import com.educare.edu.comn.vo.ResultVO;

/**
 * 설문지 관리 서비스
 */
public interface FeedbackService {

	/**
	 * 설문지 리스트
	 * @param orgCd
	 * @return
	 */
	List<FeedbackVO> getfeedbackList(String orgCd,String userId,String srchWrd); 

	/**
	 * 설문지 정보
	 * @param idx
	 * @return
	 */
	Map<String, Object> getFeedbackInfo(int idx);

	/**
	 * 설문지 저장
	 * @param orgCd
	 * @param param
	 * @return
	 */
	int saveFeedback(String orgCd, Map<String, Object> param);

	/**
	 * 설문지 삭제 
	 * @param orgCd
	 * @param idx
	 * @return
	 */
	int deleteFeedback(String orgCd, int idx);

	/**
	 * 설문지 결과 저장
	 * @param feedbackInfo
	 */
	void setResult(Map<String, Object> feedbackInfo,int eduSeq,int feSeq);
	
	/**
	 * 설문지 내 답안 세팅
	 * @param feedbackInfo
	 */
	void setAnswer(Map<String, Object> feedbackInfo,String userId,int eduSeq,int feSeq);

	/**
	 * 설문지 내답안 저장
	 * @param orgCd
	 * @param param
	 * @return
	 */
	int saveFeedbackAnswer(Map<String, Object> param);

	void setResultAll(Map<String, Object> feedbackInfo, List<Integer> eduSeqArr);

	/**
	 * 동일한 설문지 번호 가져오기
	 * @param eduSeqChk
	 * @return
	 */
	int getFbIdxByEduSeqArr(List<Integer> eduSeqChk);

	/**
	 * 설문오픈가능 체크
	 * @param feSeq
	 * @return
	 */
	ResultVO checkOpenFeedback(int feSeq,String userId);

	/***
	 * 피드백 관리자 페이징 목록
	 * @param pageNo
	 * @param i
	 * @param srchWrd
	 * @return
	 */
	ResultVO getFeedbackPageList(int pageNo, int rowCnt, String srchWrd);

	
}
