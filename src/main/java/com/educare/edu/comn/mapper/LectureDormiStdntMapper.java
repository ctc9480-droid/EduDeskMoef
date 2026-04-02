package com.educare.edu.comn.mapper;

import java.util.List;

import com.educare.edu.comn.model.LectureDormiStdnt;
import com.educare.edu.comn.model.LectureTime;
import com.educare.edu.comn.model.Org;
import com.educare.edu.comn.vo.LectureDormiVO;
import com.educare.edu.comn.vo.LectureRoomVO;
import com.educare.edu.education.service.model.Lecture;
import com.educare.edu.education.service.model.LectureAttach;
import com.educare.edu.education.service.model.LectureStdnt;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

/**
 * 
 */
@Mapper("LectureDormiStdntMapper")
public interface LectureDormiStdntMapper {

	List<LectureDormiVO> selectByParam(LectureDormiStdnt vo);

	void insertByPk(LectureDormiStdnt vo);
	
	int deleteByPk(LectureDormiStdnt vo);

	void deleteByParam(LectureDormiStdnt assign);

}
