package com.educare.edu.comn.mapper;

import com.educare.edu.comn.model.LectureSubject;
import com.educare.edu.comn.model.LectureSubjectStdnt;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

/**
 * 
 */
@Mapper("LectureSubjectStdntMapper")
public interface LectureSubjectStdntMapper {


	LectureSubjectStdnt selectByPk(LectureSubjectStdnt vo);
	
	int insertByPk(LectureSubjectStdnt vo);

	int updateByPk(LectureSubjectStdnt vo);

}
