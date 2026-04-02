package com.educare.edu.passTpl.service;

import java.util.List;
import java.util.Map;

import org.stringtemplate.v4.compiler.STParser.mapExpr_return;

import com.educare.edu.comn.vo.FeedbackVO;
import com.educare.edu.comn.vo.ResultVO;

/**
 * 설문지 관리 서비스
 */
public interface PassTplService {

	

	/***
	 * 수료증 관리자 페이징 목록
	 * @param pageNo
	 * @param i
	 * @param srchWrd
	 * @return
	 */
	ResultVO getPassTplPageList(int pageNo, int rowCnt, String srchWrd);

	
}
