<%@page import="com.educare.edu.login.service.impl.LoginServiceImpl"%>
<%@page import="com.educare.edu.member.service.SessionUserInfoHelper"%>
<%@page import="com.educare.edu.login.service.LoginService"%>
<%@page import="com.educare.edu.member.service.model.UserInfo"%>
<%@page import="com.educare.edu.member.service.impl.MemberMapper"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="com.educare.util.LncUtil"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%
String ip = LncUtil.getIp(request);
out.println(ip);
if(!"106.244.74.221, 152.99.239.18, 10.11.250.14,127.0.0.1,0:0:0:0:0:0:0:1,192.168.0.164".contains(ip)){
	return;
}

//로그인처리
String userId = request.getParameter("userId");

ServletContext conext = session.getServletContext();
WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(conext);
MemberMapper memberMapper = (MemberMapper)wac.getBean("MemberMapper");

UserInfo user = memberMapper.selectUserInfoByPk(userId);
if(user!=null){
	session.setAttribute( "sessionUserInfo", user);
	response.sendRedirect("/edu");
}
%>

<form>
<input type="text" name="userId" />
</form>