<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<c:set var="user" value="${vo.userInfo}"/>
	이름 : ${user.userNm}<br/>
	아이디 : ${user.userId}<br/>
	<c:forEach var="log" items="${vo.connLogList}" varStatus="stat">
		<fmt:formatDate value="${log.connDe}" pattern="yyyy-MM-dd / HH:mm" />&nbsp;&nbsp;&nbsp;${log.ip}&nbsp;&nbsp;&nbsp;${log.menuNm}<br/>
	</c:forEach>
</body>
</html>