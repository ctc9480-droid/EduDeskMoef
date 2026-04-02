package com.educare.edu.education.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.educare.edu.comn.mapper.QrCodeMapper;
import com.educare.edu.education.service.QrCodeService;
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
@Service("QrCodeService")
public class QrCodeServiceImpl implements QrCodeService {
	
	@Resource(name = "QrCodeMapper")
	private QrCodeMapper qrCodeMapper;
}
