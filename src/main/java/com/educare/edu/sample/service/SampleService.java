package com.educare.edu.sample.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.stringtemplate.v4.compiler.STParser.mapExpr_return;

import com.educare.edu.comn.vo.FeedbackVO;
import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.exam.vo.ExamVO;
import com.educare.edu.sample.vo.SampleVO;

/**
 * 샘플 서비스
 */
public interface SampleService {

	List<SampleVO> getSampleList(SampleVO vo);
	ResultVO setEtcOrgList(MultipartHttpServletRequest mr);
	ResultVO setEtcGradeList(MultipartHttpServletRequest request);
	ResultVO setEtcGradeList2(MultipartHttpServletRequest request);
	
}
