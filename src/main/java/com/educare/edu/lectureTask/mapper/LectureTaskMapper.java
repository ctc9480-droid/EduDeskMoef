package com.educare.edu.lectureTask.mapper;
 
import java.util.Map;

import com.educare.edu.education.service.model.LectureStdnt;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

@Mapper("LectureTaskMapper")
public interface LectureTaskMapper {
	void updateTaskAttach(LectureStdnt lectureStdnt);
	
	Map<String, String> selectLectureTaskFileMap(Map<String, String> param);

	void updateLectureTaskFile(Map<String, String> param);
} 
 