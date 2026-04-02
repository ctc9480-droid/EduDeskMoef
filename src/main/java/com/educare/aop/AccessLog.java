package com.educare.aop;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.educare.edu.log.service.LogService;
import com.educare.edu.menu.service.model.Menu;
import com.educare.util.LncUtil;

/**
 * @Class Name : AccessLog.java
 * @author SI개발팀 박용주
 * @since 2020. 5. 26.
 * @version 1.0
 * @see
 * @Description 접속로그
 * 
 * <pre>
 *
 *
 * 수정일		    	수정자		    	수정내용
 * --------	  		---------	  		------------------------
 * 2020. 5. 26.	  	SI개발팀 박용주     		최초생성 
 * </pre>
 */
@Aspect
@Component
@Order(1)
public class AccessLog {
	
	private static final String USER_PACKAGE = "user";
	
	/** javax.servlet.http.HttpServletRequest */
	@Autowired
	private HttpServletRequest request;
	
	/** 접속로그 서비스 */
	@Resource(name = "LogService")
	private LogService logService;
	
	/**
	 * 접속로그 저장
	 * @param joinPoint
	 * @throws Throwable
	 */
	@After(" execution(* com.educare.*..web.*Controller.*(..))")
	public void accessLogSave(JoinPoint joinPoint) throws Throwable {
		
		String packageNm = (String)request.getRequestURI().split("/")[1];		
		String menuDiv = USER_PACKAGE.equals(LncUtil.replaceNull(packageNm))? Menu.MENU_DIV_USER : Menu.MENU_DIV_ADM;
		
		if(!LncUtil.isAjaxSubmit(request) && !"localhost,127.0.0.1,www".contains(request.getServerName())){
			logService.insertConnLog(request, menuDiv);
		}
	}
}
