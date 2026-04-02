package com.educare.edu.member.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.catalina.SessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.educare.edu.member.service.model.UserInfo;
import com.educare.util.LncUtil;

/**

 */
@WebListener
public class SessionConfig implements HttpSessionListener{
	private static Map<String, HttpSession> sessions = new ConcurrentHashMap<>();
	
	//중복로그인 지우기
	public static String getSessionidCheck(String type, UserInfo user){
		String result = "";
		
		
	    // sessions에 접근하는 부분만 동기화 처리
		synchronized (sessions) {
	        for (String key : sessions.keySet()) {
	            HttpSession hs = sessions.get(key);
	            if (hs != null && hs.getAttribute(type) != null) {
	                UserInfo sess = (UserInfo) hs.getAttribute(type);
	                if (sess.getUserId().equals(user.getUserId())) {
	                    result = key;
	                    removeSessionForDoubleLogin(result);
						break;
	                }
	            }
	        }
	    }

	    // 동기화 필요 없는 로직은 동기화 블록 외부에서 처리
	    removeSessionForDoubleLogin(result);
	    return result;
	}
	
	//중복로그인 지우기
	public synchronized static int checkDupLogout(String sessionId){
		try {
			System.out.println("=dup check list=");
			System.out.println("sessionId : "+sessionId);
			if(ObjectUtils.isEmpty(sessions.get(sessionId).getAttribute("sessionUserInfo"))){
				return 1;
			}
			return 0;
		} catch (Exception e) {
			return 0;
		}
	}
	
	private static void removeSessionForDoubleLogin(String userId){
		if(userId != null && userId.length() > 0){
			//sessions.get(userId).invalidate();
			//sessions.remove(userId);    	
			sessions.get(userId).removeAttribute("sessionUserInfo");
		}
	}
	
	@Override
	public void sessionCreated(HttpSessionEvent se) {
	    sessions.put(se.getSession().getId(), se.getSession());
	}
	
	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		if(sessions.get(se.getSession().getId()) != null){
			sessions.get(se.getSession().getId()).invalidate();
			sessions.remove(se.getSession().getId());	
		}
	}
}
