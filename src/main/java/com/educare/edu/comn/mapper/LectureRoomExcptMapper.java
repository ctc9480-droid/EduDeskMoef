package com.educare.edu.comn.mapper;

import java.util.List;

import com.educare.edu.comn.model.LectureTime;
import com.educare.edu.comn.model.Org;
import com.educare.edu.comn.vo.LectureRoomExcptVO;
import com.educare.edu.comn.vo.LectureRoomVO;
import com.educare.edu.education.service.model.Lecture;
import com.educare.edu.education.service.model.LectureAttach;
import com.educare.edu.education.service.model.LectureStdnt;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

/**
 * 
 */
@Mapper("LectureRoomExcptMapper")
public interface LectureRoomExcptMapper {

	void insertByPk(LectureRoomExcptVO vo);
	void deleteByParam(LectureRoomExcptVO vo);
	List<LectureRoomExcptVO> selectByParam(LectureRoomExcptVO vo);
	int getExcptCnt(LectureRoomExcptVO excptVO);
	List<LectureRoomExcptVO> selectByDt(LectureRoomExcptVO roomExcptVO);

}
