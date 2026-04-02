package com.educare.edu.comn.service;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.educare.edu.comn.vo.ResultVO;

public interface ExcelService {

	// ResultVO 1:성공.  0:실패
	ResultVO getLectureScheduleByExcel(MultipartHttpServletRequest mr);
	
	ResultVO getLectureStdntByExcel(MultipartHttpServletRequest mr);

}
