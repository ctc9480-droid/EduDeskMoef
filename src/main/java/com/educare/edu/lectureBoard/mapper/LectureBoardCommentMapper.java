package com.educare.edu.lectureBoard.mapper;
 
import java.util.List;

import com.educare.edu.bbs.service.model.BoardComment;
import com.educare.edu.lectureBoard.vo.LectureBoardCommentVO;
import com.educare.edu.lectureBoard.vo.LectureBoardVO;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

@Mapper("LectureBoardCommentMapper")
public interface LectureBoardCommentMapper {
	LectureBoardCommentVO selectLectureBoardCommentMap(LectureBoardCommentVO vo);

	void updateLectureBoardCommentMap(LectureBoardCommentVO vo);

	void insertLectureBoardCommentMap(LectureBoardCommentVO vo);

	void updateLectureBoardCommentMapForGroup(LectureBoardCommentVO vo);

	List<LectureBoardCommentVO> selectLectureBoardCommentList(LectureBoardCommentVO vo);

	void deleteLectureBoardCommentMap(LectureBoardCommentVO commentVo);

	void updateLectureBoardCommentMapByStatus(LectureBoardCommentVO vo);
} 
