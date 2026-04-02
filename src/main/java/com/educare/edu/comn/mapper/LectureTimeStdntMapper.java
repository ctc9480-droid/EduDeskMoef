package com.educare.edu.comn.mapper;

import java.util.List;

import com.educare.edu.comn.model.LectureTimeStdnt;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

/**
 * 
 */
@Mapper("LectureTimeStdntMapper")
public interface LectureTimeStdntMapper {

	LectureTimeStdnt selectByPk(LectureTimeStdnt param);

	void insertByPk(LectureTimeStdnt param);

	void updateByPk(LectureTimeStdnt param);

	void updateByEduSeqTimeSeq(LectureTimeStdnt lectureTimeStdnt);

	List<LectureTimeStdnt> selectByEduSeqTimeSeq(LectureTimeStdnt param);

	List<LectureTimeStdnt> selectByEduSeqUserId(LectureTimeStdnt p);

}
