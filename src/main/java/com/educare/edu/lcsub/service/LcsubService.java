package com.educare.edu.lcsub.service;

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
public interface LcsubService {

	/**
	 * 과목 페이징용 목록
	 * @param pageNo
	 * @param row
	 * @param srchWrd
	 * @return
	 */
	ResultVO getLcsubPageList(int pageNo, int row, String srchWrd);

	/**
	 * 과목정보
	 * @param subSeq
	 * @return
	 */
	ResultVO getLcsubInfo(int subSeq);

	/**
	 * 저장
	 * @param userId
	 * @param subSeq
	 * @param subNm
	 * @param passIdx
	 * @param state
	 * @return
	 */
	ResultVO saveLcsub(String userId, int subSeq, String subNm, int passIdx, int complIdx, int state,String passCertNm,String complCertNm);

	ResultVO getLcsubList(String srchWrd);

}
