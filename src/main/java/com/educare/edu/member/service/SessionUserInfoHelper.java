package com.educare.edu.member.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.educare.edu.member.service.model.UserInfo;
import com.educare.util.LncUtil;

/**
 * @Class Name : SessionUserInfoHelper.java
 * @author SI개발팀 박용주
 * @since 2020. 5. 27.
 * @version 1.0
 * @see
 * @Description HttpSession Attribute에(서) UserInfo의 정보들을 추출/저장/삭제하는 클래스
 * 
 * <pre>
 *
 *
 * 수정일		    	수정자		    	수정내용
 * --------	  		---------	  		------------------------
 * 2020. 5. 27.	  	SI개발팀 박용주     		최초생성 
 * </pre>
 */
public class SessionUserInfoHelper {
	
	/** org.slf4j.Logger */
	private static final Logger LOG = LoggerFactory.getLogger(SessionUserInfoHelper.class);
	
	/** SESSION ATTRIBUTE의 USER_INFO KEY */
	private static final String SESSION_KEY_USERINFO = "sessionUserInfo";
	
	
	/**
	 * 로그인 여부 체크한다.
	 * @return 로그인 O : true, 로그인 X : false 
	 */
	public static boolean isLogined() {
		HttpSession session = getSession();
		if( session == null ) {
			LOG.info( "Failed to save UserInfo at session cause HttpSession is null" );
			return false;
		}
		
		UserInfo userInfo = (UserInfo)session.getAttribute( SESSION_KEY_USERINFO );
		
		return userInfo!=null && userInfo.getUserId()!=null && !"".equals( userInfo.getUserId() );
	}
	
	/**
	 * 관리자 여부 체크한다.
	 * @return 로그인 O : true, 로그인 X : false 
	 */
	public static boolean isAdmin() {
		if(!isLogined()){
			return false;
		}
		String userMemLvl = getUserMemLvl();
		return UserInfo.MEM_LVL_MID_ADM.equals(LncUtil.replaceNull(userMemLvl)) 
				|| UserInfo.MEM_LVL_MID2_ADM.equals(LncUtil.replaceNull(userMemLvl))
				|| UserInfo.MEM_LVL_MID3_ADM.equals(LncUtil.replaceNull(userMemLvl))//운영자니까 함부로 빼지 말자
				|| UserInfo.MEM_LVL_SUPER_ADM.equals(LncUtil.replaceNull(userMemLvl));
	}
	
	/**
	 * 슈퍼관리자 여부 체크한다.
	 * @return 로그인 O : true, 로그인 X : false 
	 */
	public static boolean isSuperAdmin() {
		if(!isLogined()){
			return false;
		}
		String userMemLvl = getUserMemLvl();
		return UserInfo.MEM_LVL_SUPER_ADM.equals(LncUtil.replaceNull(userMemLvl));
	}
	
	/**
	 * 세션에 UserInfo를 저장한다.
	 * @param userInfo 회원정보(com.educare.edu.member.service.model.UserInfo)
	 * @return 저장 성공 : true, 실패 : false
	 */
	public static boolean saveUserInfo(UserInfo userInfo) {
		HttpServletRequest request = getRequest();
		if( request == null ) {
			LOG.info( "Failed to save UserInfo at session cause HttpServletRequest is null" );
			return false;
		}
		
		/* [보안취약점] 로그인시마다 새 세션에 저장하도록 */
		HttpSession beforeSession = request.getSession( false );
		HttpSession session = null;
		if( beforeSession == null ) {
			session = request.getSession();
		} else {
			beforeSession.invalidate();
			session = request.getSession( true );
		}
		
		if( session == null ) {
			LOG.info( "Failed to save UserInfo at session cause HttpSession is null" );
			return false;
		}
		
		//관리자 로그인시 세션시간 30분으로
		if("1,2,3,4".contains(userInfo.getUserMemLvl())){
			session.setMaxInactiveInterval(60*120);
			
			//중복로그인
			//String userId = SessionConfig.getSessionidCheck(SESSION_KEY_USERINFO, userInfo);
		}
		
		session.setAttribute( SESSION_KEY_USERINFO, userInfo);
		
		
		return session.getAttribute( SESSION_KEY_USERINFO ) != null;
	}
	
	/**
	 * 세션에서 UserInfo를 삭제한다.
	 * @return 삭제 성공 : true, 실패 : false
	 */
	public static boolean removeUserInfo() {
		HttpSession session = getSession();
		if( session == null ) {
			LOG.info( "Failed to remove UserInfo at session cause HttpSession is null" );
			return false;
		}
		
		session.removeAttribute( SESSION_KEY_USERINFO );
		
		return session.getAttribute( SESSION_KEY_USERINFO ) == null;
	}
	
	/**
	 * 세션에 저장된 UserInfo MODEL를 가져온다.
	 * @return  회원정보(com.educare.edu.member.service.model.UserInfo)
	 */
	public static UserInfo getUserInfo() {
		HttpSession session = getSession();
		if( session == null ) {
			LOG.info( "Failed to get UserInfo at session cause HttpSession is null" );
			return null;
		}
		
		return (UserInfo)session.getAttribute( SESSION_KEY_USERINFO );
	}
	
	/**
	 * 세션에 저장된 UserInfo MODEL의 userId를 가져온다.
	 * @return 세션에 저장된 UserInfo MODEL의 userId
	 */
	public static String getUserId() {
		return (String)getUserInfoProp( "userId" );
	}
	
	/**
	 * 세션에 저장된 UserInfo MODEL의 userNm를 가져온다.
	 * @return 세션에 저장된 UserInfo MODEL의 userNm
	 */
	public static String getUserNm() {
		return (String)getUserInfoProp( "userNm" );
	}
	
	/**
	 * 세션에 저장된 UserInfo MODEL의 userMemLvl를 가져온다.
	 * @return 세션에 저장된 UserInfo MODEL의 userMemLvl
	 */
	public static String getUserMemLvl() {
		return (String)getUserInfoProp( "userMemLvl" );
	}
	
	/**
	 * 세션에 저장된 UserInfo MODEL의 propertyName에 해당하는 값을 가져온다.
	 * @param propertyName UserInfo MODEL의 변수명
	 * @return 세션에 저장된 UserInfo MODEL의 propertyName에 해당하는 값
	 */
	private static Object getUserInfoProp(String propertyName) {
		Object propertyValue = null;
		UserInfo userInfo = getUserInfo();
		if( userInfo == null ) {
			//LOG.info( "Failed to get fieldValue(" + propertyName + ") of UserInfo at session cause UserInfo is null" );
			return null;
		}
		
		String methodName = "get" + propertyName.substring( 0, 1 ).toUpperCase() + propertyName.substring( 1, propertyName.length() );
		Method getter = null; 
		try {
			getter = UserInfo.class.getDeclaredMethod( methodName );
			getter.setAccessible( true );
			propertyValue = getter.invoke( userInfo );
		} catch (NoSuchMethodException e) {
			LOG.info( "Failed to get fieldValue of UserInfo at session cause Method(" + methodName + ") is null" );
			
		} catch (SecurityException e) {
			LOG.info( "Failed to get fieldValue of UserInfo at session cause Method(" + methodName + ") can't access" );
			
		} catch (IllegalAccessException e) {
			LOG.info( "Failed to get fieldValue of UserInfo at session cause Method(" + methodName + ") can't access" );
			
		} catch (IllegalArgumentException e) {
			LOG.info( "Failed to get fieldValue of UserInfo at session cause Method(" + methodName + ")'s argument incorrect" );
			
		} catch (InvocationTargetException e) {
			LOG.info( "Failed to get fieldValue of UserInfo at session cause Method(" + methodName + ") can't invoke" );
			
		}
		
		return propertyValue;
	}
	
	/**
	 * 현재 사용중인 HttpSession을 가져온다.
	 * @return HttpSession javax.servlet.http.HttpSession
	 */
	private static HttpSession getSession() {
		HttpServletRequest request = getRequest();
		return request != null? request.getSession() : null;
	}
	
	/**
	 * 현재 사용중인 HttpServletRequest를 가져온다.
	 * @return HttpServletRequest javax.servlet.http.HttpServletRequest
	 */
	private static HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		return request;
	}
}
