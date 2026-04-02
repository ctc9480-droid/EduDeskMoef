package com.educare.edu.lclike.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.stringtemplate.v4.compiler.STParser.mapExpr_return;

import com.educare.edu.comn.vo.FeedbackVO;
import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.exam.vo.ExamVO;

/**
 * 설문지 관리 서비스
 */
public interface LclikeService {

	/**
	 * 과목 페이징용 목록
	 * @param pageNo
	 * @param row
	 * @param srchWrd
	 * @return
	 */
	ResultVO getLclikePageList(int pageNo, int row, String srchWrd);

	

	/**
	 * 저장
	 * @param userId
	 * @param subSeq
	 * @param subNm
	 * @param passIdx
	 * @param state
	 * @return
	 */
	ResultVO saveLclike(String userId, int eduSeq, int state);


	/**
	 * 찜여부
	 * 1:찜
	 * @param eduSeq
	 * @param userId
	 * @return
	 */
	int checkLike(int eduSeq, String userId);

	//ResultVO getLclikeList(String srchWrd);

}
