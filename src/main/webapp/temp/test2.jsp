<%@page import="com.educare.util.SendEmailVo"%>
<%@page import="com.educare.util.SendEmail"%>
<%@page import="com.educare.edu.comn.service.SyncDataService"%>
<%@page import="com.educare.edu.comn.vo.ResultVO"%>
<%@page import="com.educare.edu.pay.web.PayTossUtil"%>
<%@page import="com.educare.edu.pay.web.PayTossUserController"%>
<%@page import="org.springframework.web.multipart.MultipartFile"%>
<%@page import="org.springframework.web.multipart.MultipartHttpServletRequest"%>
<%@page import="com.educare.util.FileUtil"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="com.educare.util.LncUtil"%>
<%@page import="com.educare.util.CryptoUtil"%>
<%@page import="com.educare.edu.comn.vo.CheckVO"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
String ip = LncUtil.getIp(request);
out.println(ip);
if(!"106.244.74.221,127.0.0.1,0:0:0:0:0:0:0:1,192.168.0.164,192.168.0.226".contains(ip)){
	return;
}

try{
	ServletContext conext = session.getServletContext();
	WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(conext);
	
	SendEmailVo vo = new SendEmailVo();
		vo.setMessage("test");
	vo.setSubject("subjecttest");
	vo.setToEmail("shox9852@naver.com");
	String result = SendEmail.sendEmail_bak(vo);
	
	out.println(result);
}catch(Exception e){
	out.println(e.getMessage());
}
%>
