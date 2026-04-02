package com.educare.edu.education.service.impl;

import java.util.List;

import com.educare.edu.education.service.model.LectureAttach;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

/**
 * @Class Name : AttachMapper.java
 * @author SI개발팀 박용주
 * @since 2020. 8. 11.
 * @version 1.0
 * @see
 * @Description 교육 결과자료 Mapper
 * 
 * <pre>
 *
 *
 * 수정일		    	수정자		    	수정내용
 * --------	  		---------	  		------------------------
 * 2020. 8. 11.	  	SI개발팀 박용주     		최초생성 
 * </pre>
 */
@Mapper("AttachMapper")
public interface AttachMapper {
	
	void inertAttach(LectureAttach attach);
	
	void deleteAttach(Integer fileSeq);
	
	List<LectureAttach> selectAttachList(LectureAttach attach);
	
	LectureAttach getAttach(Integer fileSeq);

	int selectAttachCnt(LectureAttach la);
}
