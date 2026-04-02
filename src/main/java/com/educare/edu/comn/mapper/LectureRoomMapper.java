package com.educare.edu.comn.mapper;

import java.util.List;

import com.educare.edu.comn.model.LectureTime;
import com.educare.edu.comn.model.Org;
import com.educare.edu.comn.vo.LectureRoomVO;
import com.educare.edu.education.service.model.Lecture;
import com.educare.edu.education.service.model.LectureAttach;
import com.educare.edu.education.service.model.LectureStdnt;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

/**
 * 
 */
@Mapper("LectureRoomMapper")
public interface LectureRoomMapper {

	List<LectureRoomVO> selectByParam(LectureRoomVO vo);

	List<LectureRoomVO> selectPageList(LectureRoomVO vo);

	int selectPageTotalCnt(LectureRoomVO vo);

	LectureRoomVO selectByPk(LectureRoomVO vo);

	void updateByPk(LectureRoomVO vo);

	void insertByPk(LectureRoomVO vo);

}
