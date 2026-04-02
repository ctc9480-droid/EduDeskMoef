package com.educare.edu.comn.mapper;

import com.educare.edu.comn.model.LectureSubject;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

/**
 * 
 */
@Mapper("LectureSubjectMapper")
public interface LectureSubjectMapper {


	LectureSubject selectByPk(LectureSubject vo);

	void deleteByPk(LectureSubject vo);
	
	int insertByPk(LectureSubject vo);

	int updateByPk(LectureSubject vo);

	List<LectureSubject> selectByList(LectureSubject vo);
	List<LectureSubject> selectLctreTimeSubList(int eduSeq);
}
