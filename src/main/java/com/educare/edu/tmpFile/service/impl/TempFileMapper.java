package com.educare.edu.tmpFile.service.impl;

import com.educare.edu.tmpFile.service.model.TempFile;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

/**
 * @Class Name : TempFileMapper.java
 * @author SI개발팀 박용주
 * @since 2020. 6. 10.
 * @version 1.0
 * @see
 * @Description 임시파일 Mapper
 * 
 * <pre>
 *
 *
 * 수정일		    	수정자		    	수정내용
 * --------	  		---------	  		------------------------
 * 2020. 6. 10.	  	SI개발팀 박용주     		최초생성 
 * </pre>
 */
@Mapper("TempFileMapper")
public interface TempFileMapper {
	
	void insertTempFile(TempFile tempFile);
	
	void deleteTempFile(Integer fileSeq);
	
	TempFile getTempFile(Integer fileSeq);
}
