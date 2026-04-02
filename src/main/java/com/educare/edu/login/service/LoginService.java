package com.educare.edu.login.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.educare.edu.comn.vo.ResultVO;
import com.educare.edu.member.service.model.UserInfo;

/**
 * @Class Name : LoginService.java
 * @author SI개발팀 박용주
 * @since 2020. 6. 2.
 * @version 1.0
 * @see
 * @Description 로그인 서비스 인터페이스
 * 
 * <pre>
 *
 *
 * 수정일		    	수정자		    	수정내용
 * --------	  		---------	  		------------------------
 * 2020. 6. 2.	  	SI개발팀 박용주     		최초생성 
 * </pre>
 */
public interface LoginService {
	
	/**
	 * 관리자 로그인
	 * @param userId
	 * @param userPw
	 * @return
	 */
	public String adminLogin(HttpServletRequest request, String userId, String userPw);
	
	/**
	 * 사용자 로그인
	 * @param userInfo
	 * @return
	 */
	public ResultVO userLogin(HttpServletRequest request, UserInfo userInfo);
	
	public ResultVO userLoginBySubDn(HttpServletRequest request, UserInfo userInfo);
	
	public ResultVO userLoginBySubDn2(HttpServletRequest request, UserInfo userInfo);
}
