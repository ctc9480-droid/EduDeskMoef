package com.educare.edu.log.service.impl;

import java.util.List;

import com.educare.edu.log.service.model.ConnLog;
import com.educare.edu.log.service.model.ConnLogAdm;
import com.educare.edu.log.service.model.LoginLog;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;
 
/**
 * @Class Name : LogMapper.java
 * @author SI개발팀 박용주 
 * @since 2020. 6. 3.
 * @version 1.0
 * @see 
 * @Description 로그 매퍼
 * 
 * <pre> 
 *
 *
 * 수정일		    	수정자		    	수정내용
 * --------	  		---------	  		------------------------
 * 2020. 6. 3.	  	SI개발팀 박용주     		최초생성 
 * </pre>
 */
@Mapper("LogMapper")
public interface LogMapper {
	
	void insertConnLog(ConnLog log);
	
	void insertLoginLog(LoginLog loginLog);
	
	List<ConnLogAdm> selectAdminConnList(String userId);
	
	int selectLoginLogPageCnt(LoginLog vo);
	List<LoginLog> selectLoginLogPageList(LoginLog vo);
	List<LoginLog> selectLoginLogPageListExcel(LoginLog vo); 
	
}
