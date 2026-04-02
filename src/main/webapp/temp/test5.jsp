<%@page import="com.educare.util.CryptoSeedUtil"%>
<%@page import="org.springframework.util.ObjectUtils"%>
<%@page import="com.educare.util.LncUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*, java.util.*" %>
<%
response.setHeader("Set-Cookie", "JSESSIONID=" + request.getRequestedSessionId() + "; path=/edu; Secure; SameSite=None");

String ip = LncUtil.getIp(request);
out.println(ip);
if(!"106.244.74.221,127.0.0.1,0:0:0:0:0:0:0:1,192.168.0.164,192.168.0.226,192.168.245.100".contains(ip)){
	return;
}

out.println(CryptoSeedUtil.encrypt("010-4784-1100"));
%>
</body>
</html>
