package com.educare.edu.education.service;

import com.educare.edu.education.service.model.LectureTutfee;

/**
 * @Class Name : TuitionFeeService.java
 * @author SI개발팀 박용주
 * @since 2020. 8. 6.
 * @version 1.0
 * @see
 * @Description 수업료 서비스 인터페이스
 * 
 * <pre>
 *
 *
 * 수정일		    	수정자		    	수정내용
 * --------	  		---------	  		------------------------
 * 2020. 8. 6.	  	SI개발팀 박용주     		최초생성 
 * </pre>
 */
public interface TuitionFeeService {
	
	/**
	 * 수업료를 저장한다.
	 * @param lectureTutfee
	 */
	public void insertTutfee(LectureTutfee lectureTutfee);
	
	/**
	 * 수업료를 삭제한다.
	 * @param eduSeq
	 */
	public void deleteTutfee(Integer eduSeq);
}
