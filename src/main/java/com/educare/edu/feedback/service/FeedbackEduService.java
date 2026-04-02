package com.educare.edu.feedback.service;

import java.util.List;
import java.util.Map;

import org.stringtemplate.v4.compiler.STParser.mapExpr_return;

import com.educare.edu.comn.vo.FeedbackVO;
import com.educare.edu.comn.vo.ResultVO;

/**
 * 설문지 관리 서비스
 */
public interface FeedbackEduService {

	/**
	 * 교육연결 설문지 
	 * @param feSeq
	 * @param eduSeq
	 * @return
	 */
	ResultVO getFeedbackEduMap(int feSeq, int eduSeq,String userId);

	/**
	 * 교육연결설문지 저장하기
	 * @param feSeq
	 * @param eduSeq
	 * @param fbIdx
	 * @param startDeStr
	 * @param endDeStr
	 * @param state
	 * @return
	 */
	ResultVO saveFeedbackEduMap(int feSeq, int eduSeq, int fbIdx,String fbNm, String startDeStr, String endDeStr, int state);

	/**
	 * 교육연결설문지 목록
	 * @param userId
	 * @param eduSeq
	 * @param i
	 * @param pageNo
	 * @return
	 */
	ResultVO getFeedbackEduList(String userId, int eduSeq, int i, int pageNo);

}
