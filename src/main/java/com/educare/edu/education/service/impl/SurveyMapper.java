package com.educare.edu.education.service.impl;

import com.educare.edu.education.service.EduVO;
import com.educare.edu.education.service.model.LectureSurvey;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

/**
 * @Class Name : SurveyMapper.java
 * @author SI개발팀 박용주
 * @since 2020. 8. 10.
 * @version 1.0
 * @see
 * @Description 설문조사 Mapper
 * 
 * <pre>
 *
 *
 * 수정일		    	수정자		    	수정내용
 * --------	  		---------	  		------------------------
 * 2020. 8. 10.	  	SI개발팀 박용주     		최초생성 
 * </pre>
 */
@Mapper("SurveyMapper")
public interface SurveyMapper {
	
	void insertSurvey(LectureSurvey lectureSurvey);
	
	LectureSurvey selectSurvey(EduVO vo);
}
