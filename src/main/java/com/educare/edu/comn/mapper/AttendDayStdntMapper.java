package com.educare.edu.comn.mapper;

import java.util.List;

import com.educare.edu.comn.model.AttendDayStdnt;
import com.educare.edu.comn.model.AttendStdnt;
import com.educare.edu.comn.model.LectureMov;
import com.educare.edu.comn.model.LectureTime;
import com.educare.edu.comn.model.Org;
import com.educare.edu.comn.vo.MovVO;
import com.educare.edu.comn.vo.SmartCheckVO;
import com.educare.edu.education.service.model.Lecture;
import com.educare.edu.education.service.model.LectureAttach;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

/**
 *
 */
@Mapper("AttendDayStdntMapper")
public interface AttendDayStdntMapper {

	void insertByPk(AttendDayStdnt param);

	AttendDayStdnt selectByPk(AttendDayStdnt param3);

	void updateByPk(AttendDayStdnt param);

	void deleteByPk(AttendDayStdnt param);

}
