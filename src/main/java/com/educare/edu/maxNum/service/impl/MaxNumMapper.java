package com.educare.edu.maxNum.service.impl;

import com.educare.edu.maxNum.service.model.MaxNumModel;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

/**
 * @Class Name : MaxNumMapper.java
 * @author SI개발팀 박용주
 * @since 2020. 6. 1.
 * @version 1.0
 * @see
 * @Description 맥스넘 매퍼
 * 
 * <pre>
 *
 *
 * 수정일		    	수정자		    	수정내용
 * --------	  		---------	  		------------------------
 * 2020. 6. 1.	  	SI개발팀 박용주     		최초생성 
 * </pre>
 */
@Mapper("MaxNumMapper")
public interface MaxNumMapper {
	
	public Integer getMax(String func);
	
	public void save(MaxNumModel max);
	
	public void update(MaxNumModel max);
	
}
