<%@page import="com.educare.edu.log.service.model.LoginLog"%>
<%@page import="com.educare.edu.comn.vo.ResultVO"%>
<%@page import="com.educare.edu.log.service.LogService"%>
<%@page import="com.educare.edu.log.service.impl.LogMapper"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" pageEncoding="utf-8"%>
<%@ page import="java.net.URLEncoder"%>
<%
ServletContext conext = session.getServletContext();
WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(conext);

LogService logService = (LogService)wac.getBean("LogService");
LogMapper logMapper = (LogMapper)wac.getBean("LogMapper");
LoginLog vo = (LoginLog)request.getAttribute("vo");

List<LoginLog> LoginLogList = logMapper.selectLoginLogPageListExcel(vo);
pageContext.setAttribute("data", LoginLogList);

String fileNm1 = "사용자 접속 로그내역";
//String fileNm1 = "[사용자 접속 로그내역]";

fileNm1=new String(fileNm1.getBytes("KSC5601"), "8859_1");
/*
*/
response.setHeader("Content-Disposition", "attachment; filename="+fileNm1+".xls");
response.setHeader("Content-Description", "JSP Generated Data");
//response.setContentType("application/vnd.ms-excel charset=MS949"); 
response.setContentType("application/vnd.ms-excel"); 
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
td, th {
	border: 1px solid #d9d9d9;
		mso-number-format:'@';
}

tr {
	mso-height-source: auto;
	mso-ruby-visibility: none;
}

col {
	mso-width-source: auto;
	mso-ruby-visibility: none;
}

br {
	mso-data-placement: same-cell;
}

ruby {
	ruby-align: left;
}
</style>

</head>
<body>
	<table class="w100 tb01">
		<caption class="sound_only">사용자 접속 로그 테이블</caption>
		<thead>
			<tr>
				<th>아이디</th>
				<th>이름</th>
				<th>구분</th>
				<th>IP</th>
				<th>디바이스</th>
				<th>브라우저</th>
				<th>접속일시</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="o" items="${data}" varStatus="s">
				<c:set var="rowspanCnt" value="1" />
					<td rowspan="${rowspanCnt }">${o.loginId }</td>
					<td rowspan="${rowspanCnt }">${o.userNm }</td>
					<td rowspan="${rowspanCnt }">${o.role eq "A"?"관리자": o.role eq "U"?"일반":""}</td>
					<td rowspan="${rowspanCnt }">${o.ip }</td>
					<td rowspan="${rowspanCnt }">${o.device eq "P"?"PC":""}${o.device eq "M"?"MOBILE":""}${o.device eq "T"?"TABLET":""}</td>
					<td rowspan="${rowspanCnt }">${o.browser}</td>
					<td rowspan="${rowspanCnt }"> 
					<fmt:formatDate value="${o.loginDe}" pattern="yyyy-MM-dd HH:mm" var="formattedDate" />
            ${formattedDate}</td>
				</tr>
			</c:forEach>
			<c:if test="${empty data }">
				<tr>
					<td colspan="11">내역이 없습니다.</td>
				</tr>
			</c:if>
		</tbody>
	</table>
</body>
</html>