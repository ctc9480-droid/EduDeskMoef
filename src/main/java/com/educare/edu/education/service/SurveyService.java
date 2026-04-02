package com.educare.edu.education.service;

import com.educare.edu.education.service.model.LectureSurvey;

/**
 * @Class Name : SurveyService.java
 * @author SI개발팀 박용주
 * @since 2020. 8. 10.
 * @version 1.0
 * @see
 * @Description 설문조사 서비스 인터페이스
 * 
 * <pre>
 *
 *
 * 수정일		    	수정자		    	수정내용
 * --------	  		---------	  		------------------------
 * 2020. 8. 10.	  	SI개발팀 박용주     		최초생성 
 * </pre>
 */
public interface SurveyService {
	
	/**
	 * 설문조사를 저장한다.
	 * @param lectureSurvey
	 */
	public void saveServey(LectureSurvey lectureSurvey);
	
	/**
	 * 설문조사를 조회한다.
	 * @param vo
	 * @return
	 */
	public LectureSurvey getSurvey(EduVO vo);
}
