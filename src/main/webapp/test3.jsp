<%@page import="com.educare.edu.comn.vo.ResultVO"%>
<%@page import="com.educare.edu.comn.service.SyncDataService"%>
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
	if (!"106.244.74.221,127.0.0.1,0:0:0:0:0:0:0:1,192.168.0.164,192.168.0.226".contains(ip)) {
		return;
	}

	ServletContext conext = session.getServletContext();
	WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(conext);
	SyncDataService s = (SyncDataService) wac.getBean("SyncDataService");

	try {
		
		//ResultVO result = s.syncSuccessPay("E_24_98782_20241016192152");
		//out.println(result.getMsg());
	} catch (Exception e) {
		out.println(e.getMessage());
	}
%>
test