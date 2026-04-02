package com.educare.edu.comn.mapper;

import java.util.List;

import com.educare.edu.comn.model.LectureTime;
import com.educare.edu.comn.model.Org;
import com.educare.edu.education.service.model.Lecture;
import com.educare.edu.education.service.model.LectureAttach;
import com.educare.edu.education.service.model.LectureStdnt;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

/**
 * 
 */
@Mapper("LectureStdntMapper")
public interface LectureStdntMapper {

	int selectPassYCnt(Integer eduSeq);

	LectureStdnt selectByPk(LectureStdnt ltsParam);

	/**
	 * 수료여부 저장
	 * @param param2
	 */
	void updatePassYn(LectureStdnt param2);

	void deleteByPk(LectureStdnt paramStdnt);

	void deleteByEduSeq(int eduSeq);

	List<LectureStdnt> selectByEduSeq(int eduSeq);

	int updateByPk(LectureStdnt stdntParam);

	int selectPassUserIdCnt(String userId);

	List<LectureStdnt> selectByParam(LectureStdnt lsVO);

}
