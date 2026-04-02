package com.educare.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


/**
 * @Class Name : SelfAuthUtil.java
 * @author SI개발팀 박용주
 * @since 2020. 08. 03.
 * @version 1.0
 * @see
 * @Description 보인인증
 * 
 * <pre>
 *
 *
 * 수정일		    	수정자		    	수정내용
 * --------	  		---------	  		------------------------
 * 2020. 1. 10.	  	SI개발팀 박용주     		최초생성 
 * </pre>
 */
public class SelfAuthUtil {
	
	private static final String SELF_AUTH_KEY_PREFIX = "selfAuth_";
	public static final String SELF_AUTH_KEY_USER_NM = SELF_AUTH_KEY_PREFIX + "userNm", //이름
								SELF_AUTH_KEY_USER_DI = SELF_AUTH_KEY_PREFIX + "userDi", //중복가입 확인 값
								SELF_AUTH_KEY_BIRTHDAY = SELF_AUTH_KEY_PREFIX + "birthday", //생년월일(yyyyMMdd)
								SELF_AUTH_KEY_MF = SELF_AUTH_KEY_PREFIX + "mf"; //성별(남자 : 01, 여자 : 02)
	
	
	/**
	 * 본인인증정보를 초기화한다.
	 */
	public static void reset() {
		HttpSession session = getSession();
		if(session != null) {
			List<String> selfAuthKeys = getSelfAuthKeys();
			if(selfAuthKeys!=null && !selfAuthKeys.isEmpty()) {
				for(String selfAuthKey : selfAuthKeys) {
					session.removeAttribute(selfAuthKey);
				}
			}
		}
	}
	
	/**
	 * 본인인증 값 존재여부
	 * @return 본인인증 완료  : true, 미완료 : false
	 */
	public static boolean isCertified() {
		boolean isExist = false;
		HttpSession session = getSession();
		if(session != null) {
			List<String> selfAuthKeys = getSelfAuthKeys();
			if(selfAuthKeys!=null && !selfAuthKeys.isEmpty()) {
				for(String selfAuthKey : selfAuthKeys) {
					isExist = session.getAttribute(selfAuthKey) != null;
					if(!isExist) break;
				}
			}
		}
		
		return isExist;
	}
	
	public static boolean setUserNm(Object userNm) {
		return setSelfAuthValue(SELF_AUTH_KEY_USER_NM, userNm);
	}
	public static boolean setUserDi(Object userDi) {
		return setSelfAuthValue(SELF_AUTH_KEY_USER_DI, userDi);
	}
	public static boolean setBirthday(Object birthday) {
		return setSelfAuthValue(SELF_AUTH_KEY_BIRTHDAY, birthday);
	}
	public static boolean setMf(Object mf) {
		return setSelfAuthValue(SELF_AUTH_KEY_MF, mf);
	}
	
	public static String getUserNm() {
		return (String)getSelfAuthValue(SELF_AUTH_KEY_USER_NM);
	}
	public static String getUserDi() {
		return (String)getSelfAuthValue(SELF_AUTH_KEY_USER_DI);
	}
	public static String getBirthday() {
		return (String)getSelfAuthValue(SELF_AUTH_KEY_BIRTHDAY);
	}
	public static String getMf() {
		return (String)getSelfAuthValue(SELF_AUTH_KEY_MF);
	}
	
	/**
	 * 세션에서 본인인증 항목별 값을 가져온다.
	 * @param key 본인인증 항목별 키
	 * @return 본인인증 항목별 값
	 */
	private static Object getSelfAuthValue(String key) {
		Object value = null;
		HttpSession session = getSession();
		if(session != null) {
			value = String.valueOf(session.getAttribute(key));
		}
		
		return value;
	}
	
	/**
	 * 본인인증값 세션에 저장
	 * @param key 본인인증 항목별 키
	 * @param value 본인인증 항목별 값
	 * @return 저장성공 : true, 실패 : false
	 */
	private static boolean setSelfAuthValue(String key, Object value) {
		boolean isSaved = false;
		HttpSession session = getSession();
		if(session != null) {
			session.setAttribute(key, value);
			isSaved = session.getAttribute(key) != null;
		}
		
		return isSaved;
	}
	
	private static List<String> getSelfAuthKeys() {
		List<String> selfAuthKeys = null;
		
		Field[] fields = SelfAuthUtil.class.getFields();
		if(fields!=null && fields.length>0) {
			selfAuthKeys = new ArrayList<String>();
			String fieldName = null;
			int fieldModifier = 0;
			
			for(Field field : fields) {
				fieldName = field.getName();
				fieldModifier = field.getModifiers();
				if(fieldName.startsWith("SELF_AUTH_KEY_") 
						&& Modifier.isStatic(fieldModifier)
						&& Modifier.isFinal(fieldModifier)) {
					try {
						selfAuthKeys.add(LncUtil.replaceNull(field.get(null)));
					} catch (IllegalArgumentException e) {
						continue;
					} catch (IllegalAccessException e) {
						continue;
					}
				}
			}
		}
		
		return selfAuthKeys;
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

