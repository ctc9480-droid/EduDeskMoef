package com.educare.edu.log.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.log.service.model.ConnLogAdm;
import com.educare.edu.log.service.model.LoginLog;

/**
 * @Class Name : LogService.java
 * @author SI개발팀 박용주
 * @since 2020. 6. 3.
 * @version 1.0
 * @see
 * @Description 접속로그 서비스 인터페이스
 * 
 * <pre>
 *
 *
 * 수정일		    	수정자		    	수정내용
 * --------	  		---------	  		------------------------
 * 2020. 6. 3.	  	SI개발팀 박용주     		최초생성 
 * </pre>
 */
public interface LogService {
	public static final String EXCUTE_SUCCESS = "SUCCESS";
	public static final Integer DEFAULT_PAGE_SIZE = 10;
	public static final Integer DEFAULT_ROW = 10;
	public static final Integer DETAIL_LIST_ROW = 5;
	/**
	 * 접속로그 저장
	 * @param request
	 * @param menuDiv 메뉴구분 A:관리자, U:사용자
	 */
	public void insertConnLog(HttpServletRequest request, String menuDiv);
	
	/**
	 * 로그인로그 저장
	 * @param userId
	 */
	public void insertLoginLog(HttpServletRequest request, String userId, String loginId, String role); 
	
	/**
	 * 관리자 접속이력
	 * @param userId
	 * @return
	 */
	public List<ConnLogAdm> getAdminConnList(String userId);
	
	/**
	 * 관리자 접속이력 리스트 조회
	 * @param vo
	 * @return
	 */
	public ResultVO getLctreLoginLogPageListByMngr(LoginLog vo,int listRow);
}
