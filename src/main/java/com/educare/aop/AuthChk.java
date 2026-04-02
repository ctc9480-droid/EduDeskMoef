package com.educare.aop;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.educare.component.UtilComponent;
import com.educare.edu.member.service.SessionConfig;
import com.educare.edu.member.service.SessionUserInfoHelper;
import com.educare.edu.menu.service.MenuUtil;

/**
 * @Class Name : AuthChk.java
 * @author SI개발팀 박용주
 * @since 2020. 6. 2.
 * @version 1.0
 * @see
 * @Description 권한체크
 * 
 * <pre>
 *
 *
 * 수정일		    	수정자		    	수정내용
 * --------	  		---------	  		------------------------
 * 2020. 6. 2.	  	SI개발팀 박용주     		최초생성 
 * </pre>
 */
@Aspect
@Component
@Order(0)
public class AuthChk {
	
	/** javax.servlet.http.HttpServletRequest */
	@Autowired
	private HttpServletRequest request;
	
	private static final String SUPER_ADMIN_ACCESS_MSG = "접근 권한이 없습니다.";
	private static final String ADMIN_ACCESS_MSG = "관리자만 접근 가능합니다.";
	private static final String MENU_ACCESS_MSG = "메뉴접근 권한이 없습니다.";
	
	@Resource(name = "utcp")
	private UtilComponent utcp;
	
	/**
	 * 관리자 권한을 체크한다. 관리자레벨 + 메뉴 접근권한
	 * @param joinPoint
	 * @return
	 * @throws Throwable
	 */
	@Around(	" execution(* com.educare.*..web.*AdminController.*(..))")
	public Object admAuthCheck(ProceedingJoinPoint joinPoint) throws Throwable{

		if(!SessionUserInfoHelper.isAdmin() && !"8".equals(SessionUserInfoHelper.getUserMemLvl())){
			
			//중복로그아웃인지 체크하여 메시지 처리
			/*안정화까지 임시 주석처리*/
			int dupLogoutResult = SessionConfig.checkDupLogout(request.getSession().getId());
			if(dupLogoutResult == 1){
				request.setAttribute("message", "다른 기기에서 로그인되어 로그아웃 처리되었습니다.");
				return "message";
			}
			
			request.setAttribute("message", ADMIN_ACCESS_MSG);
			return "message";
		}else{
			if(!SessionUserInfoHelper.isSuperAdmin()){
				if(!MenuUtil.isAdmMenuAuth(request)){
					request.setAttribute("message", MENU_ACCESS_MSG);
					return "message";
				}
			}
		}
		
		return joinPoint.proceed();
	}
	
	/**
	 * 슈퍼관리자 접근권한을 체크한다.
	 * @param joinPoint
	 * @return
	 * @throws Throwable
	 */
	@Around("@annotation(SuperAdmCheck)")
	public String superAdmCheck(ProceedingJoinPoint joinPoint) throws Throwable {
		if(!SessionUserInfoHelper.isLogined()){
			return "redirect:/user/index.do";
		}else{
			if(!SessionUserInfoHelper.isSuperAdmin()){
				request.setAttribute("message", SUPER_ADMIN_ACCESS_MSG);
				return "message";
			}
		}
		
		return (String) joinPoint.proceed();
	}
	
}
