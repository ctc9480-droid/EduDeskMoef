package com.educare.edu.education.service.impl;

import com.educare.edu.education.service.model.LectureTutfee;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

/**
 * @Class Name : TuitionFeeMapper.java
 * @author SI개발팀 박용주
 * @since 2020. 8. 7.
 * @version 1.0
 * @see
 * @Description 수업료 Mapper
 * 
 * <pre>
 *
 *
 * 수정일		    	수정자		    	수정내용
 * --------	  		---------	  		------------------------
 * 2020. 8. 7.	  	SI개발팀 박용주     		최초생성 
 * </pre>
 */
@Mapper("TuitionFeeMapper")
public interface TuitionFeeMapper {
	
	void insertTutfee(LectureTutfee lectureTutfee);
	
	LectureTutfee selectTutfeeByEdu(Integer eduSeq);
	
	void deleteTutfee(Integer eduSeq);
}
