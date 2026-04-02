package com.educare.edu.lccns.service;

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
public interface LccnsService {

	/**
	 * 과목 페이징용 목록
	 * @param pageNo
	 * @param row
	 * @param srchWrd
	 * @return
	 */
	ResultVO getLccnsPageList(String userId,int eduSeq,int pageNo, int row, String srchWrd);

	/**
	 * 과목정보
	 * @param subSeq
	 * @return
	 */
	ResultVO getLccnsInfo(String userId, int eduSeq,int cnsSeq);

	/**
	 * 저장
	 * @param userId
	 * @param subSeq
	 * @param subNm
	 * @param passIdx
	 * @param state
	 * @return
	 */
	ResultVO saveLccns(String userId, int eduSeq, int cnsSeq, String title, String content, int state);

	ResultVO getLccnsList(String userId,int eduSeq,String srchWrd);


}
