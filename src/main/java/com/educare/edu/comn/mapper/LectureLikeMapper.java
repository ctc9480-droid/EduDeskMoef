package com.educare.edu.comn.mapper;

import com.educare.edu.comn.model.LectureLike;
import com.educare.edu.comn.model.LectureSubject;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

/**
 * 
 */
@Mapper("LectureLikeMapper")
public interface LectureLikeMapper {

	LectureLike selectByPk(LectureLike vo);

	void deleteByPk(LectureLike vo);
	
	int insertByPk(LectureLike vo);

	int updateByPk(LectureLike vo);
}
