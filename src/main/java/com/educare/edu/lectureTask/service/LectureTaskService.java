package com.educare.edu.lectureTask.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.educare.edu.lectureBoard.vo.LectureBoardVO;

/**
 * 샘플 서비스
 */
public interface LectureTaskService {

	int taskAttachWriteProc(MultipartHttpServletRequest mhsr);

	List<LectureBoardVO> getLectureBoardList(LectureBoardVO vo);

	/**
	 * 파일삭제
	 * @param param
	 * @return
	 */
	int lectureTaskDeleteProc(Map<String, String> param);
	
	
}
