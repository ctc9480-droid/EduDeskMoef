package com.educare.edu.education.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.educare.edu.education.service.TuitionFeeService;
import com.educare.edu.education.service.model.LectureTutfee;

/**
 * @Class Name : TuitionFeeServiceImpl.java
 * @author SI개발팀 박용주
 * @since 2020. 8. 7.
 * @version 1.0
 * @see
 * @Description 수업료 서비스 구현 클래스
 * 
 * <pre>
 *
 *
 * 수정일		    	수정자		    	수정내용
 * --------	  		---------	  		------------------------
 * 2020. 8. 7.	  	SI개발팀 박용주     		최초생성 
 * </pre>
 */
@Service("TuitionFeeService")
public class TuitionFeeServiceImpl implements TuitionFeeService {
	
	/** 수업료 Mapper */
	@Resource(name = "TuitionFeeMapper")
	private TuitionFeeMapper tuitionFeeMapper;
	
	/**
	 * 수업료를 저장한다.
	 * @param lectureTutfee
	 */
	@Override
	public void insertTutfee(LectureTutfee lectureTutfee) {
		LectureTutfee tutfee = tuitionFeeMapper.selectTutfeeByEdu(lectureTutfee.getEduSeq());
		if(tutfee != null){	
			tuitionFeeMapper.deleteTutfee(tutfee.getEduSeq());
		}	
		tuitionFeeMapper.insertTutfee(lectureTutfee);
	}

	/**
	 * 수업료를 삭제한다.
	 * @param eduSeq
	 */
	@Override
	public void deleteTutfee(Integer eduSeq) {
		tuitionFeeMapper.deleteTutfee(eduSeq);
	}

}
