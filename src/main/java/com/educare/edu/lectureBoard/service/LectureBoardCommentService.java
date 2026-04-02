package com.educare.edu.lectureBoard.service;

import java.util.List;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.educare.edu.bbs.service.model.BoardAttach;
import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.lectureBoard.vo.LectureBoardAttach;
import com.educare.edu.lectureBoard.vo.LectureBoardCommentVO;
import com.educare.edu.lectureBoard.vo.LectureBoardVO;

/**
 */
public interface LectureBoardCommentService {

	/**
	 * 코멘트 저장
	 * @param comment
	 * @param bIdx
	 * @return
	 */
	ResultVO lectureBoardCommentWriteProc(String comment, int bIdx ,int idx);

	/**
	 * 코멘트 가져오기
	 * @param bIdx
	 * @param status
	 * @return
	 */
	List<LectureBoardCommentVO> getLectureBoardCommentList(int bIdx, int status);

	/**
	 * 삭제
	 * @param idx
	 * @return
	 */
	ResultVO lectureBoardCommentDeleteProc(int idx);
	
}
