<%@page import="com.educare.edu.comn.mapper.LectureTimeStdntMapper"%>
<%@page import="com.educare.edu.comn.model.LectureTimeStdnt"%>
<%@page import="com.educare.edu.education.service.model.LectureStdnt"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="com.educare.edu.education.service.model.LectureRcept"%>
<%@page import="java.util.List"%>
<%@page import="com.educare.edu.education.service.impl.EduMapper"%>
<%@page import="com.educare.edu.education.service.model.Lecture"%>
<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%

String fileNm1 = "회원정보";
fileNm1=new String(fileNm1.getBytes("KSC5601"), "8859_1");
/*
*/
response.setHeader("Content-Disposition", "attachment; filename="+fileNm1+".xls");
response.setHeader("Content-Description", "JSP Generated Data");
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
<table width="100%" class="tb01 tc">
	<caption class="sound_only">회원정보테이블</caption>
	<thead bgcolor="#f7f8fa">
		<tr>
			<th>번호</th>
			<th>성명</th>
			<th>아이디</th>
			<th>생년월일</th>
			<th>성별</th>
			<th>휴대폰</th>
			<th>회원구분</th>
			<th>가입일</th>
			<th>탈퇴여부</th>
		</tr>
	</thead>
	<tbody>
		<c:choose>
			<c:when test='${dataList != null && fn:length(dataList) > 0}'>
				<c:forEach var="user" items="${dataList}" varStatus="stat">
					<tr>
						<td>${stat.count}</td>
						<td>${user.userNm}</td>
						<td>${user.loginId}</td>
						<td>${user.birth}</td>
						<td>${user.mfType eq '01'?'남':'여'}</td>
						<td>${user.mobile}</td>
						<td>${user.userMemLvl eq 7?'준회원':'정회원' }</td>
						<td>${utcp.convDateToStr(user.rgsde,'yyyy-MM-dd') }</td>
						<td>${user.state eq 'S'?'탈퇴':'' }</td>
					</tr>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<tr>
					<td colspan="8" class="h200"">데이터가 없습니다.</td>
				</tr>
			</c:otherwise>
		</c:choose>
	</tbody>
</table>
</body></html>