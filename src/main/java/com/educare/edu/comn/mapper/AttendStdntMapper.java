package com.educare.edu.comn.mapper;

import java.util.List;

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
@Mapper("AttendStdntMapper")
public interface AttendStdntMapper {

	void insertByPk(AttendStdnt param);

	AttendStdnt selectByPk(AttendStdnt param3);

	void updateByPk(AttendStdnt param);

	void deleteByPk(AttendStdnt param);

	int updateAttCdByPk(AttendStdnt asParam);

}
