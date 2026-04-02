package com.educare.edu.comn.mapper;

import java.util.List;

import com.educare.edu.comn.model.LectureMov;
import com.educare.edu.comn.model.LectureMovTime;
import com.educare.edu.comn.model.LectureTime;
import com.educare.edu.comn.model.Org;
import com.educare.edu.comn.vo.MovVO;
import com.educare.edu.education.service.model.Lecture;
import com.educare.edu.education.service.model.LectureAttach;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

/**
 *동영상 챕터,퀴즈 시간 정보
 */
@Mapper("LectureMovTimeMapper")
public interface LectureMovTimeMapper {
	void insertByPk(LectureMovTime param);
	List<LectureMovTime> selectByMv(LectureMovTime param);
}
