package com.educare.edu.comn.mapper;

import java.util.List;

import com.educare.edu.comn.model.LectureMov;
import com.educare.edu.comn.model.LectureTime;
import com.educare.edu.comn.model.Org;
import com.educare.edu.comn.vo.MovVO;
import com.educare.edu.education.service.model.Lecture;
import com.educare.edu.education.service.model.LectureAttach;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

/**
 *
 */
@Mapper("LectureMovMapper")
public interface LectureMovMapper {

	void insertByPk(LectureMov param);

	LectureMov selectByPk(LectureMov param3);

	void updateByPk(LectureMov param);

	void deleteByPk(LectureMov param);


}
