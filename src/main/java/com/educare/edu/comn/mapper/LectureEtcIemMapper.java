package com.educare.edu.comn.mapper;

import java.util.List;
import java.util.Map;

import com.educare.edu.comn.model.Code;
import com.educare.edu.comn.model.LectureTime;
import com.educare.edu.comn.model.Org;
import com.educare.edu.comn.vo.LectureEtcIemVO;
import com.educare.edu.comn.vo.TimeTableVO;
import com.educare.edu.education.service.model.Lecture;
import com.educare.edu.education.service.model.LectureAttach;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

/**
 *
 */
@Mapper("LectureEtcIemMapper")
public interface LectureEtcIemMapper {

	int deleteByEduSeq(LectureEtcIemVO leiVO);

	int insertByPk(LectureEtcIemVO leiVO);

	List<LectureEtcIemVO> selectByEduSeq(LectureEtcIemVO leiVO);

}
