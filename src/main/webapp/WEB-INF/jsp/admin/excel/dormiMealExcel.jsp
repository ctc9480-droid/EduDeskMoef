<%@page import="com.educare.edu.comn.service.LectureDormiService"%>
<%@page import="org.codehaus.jackson.type.TypeReference"%>
<%@page import="org.codehaus.jackson.map.ObjectMapper"%>
<%@page import="com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException"%>
<%@page import="com.educare.edu.comn.model.UserLang"%>
<%@page import="com.educare.edu.comn.model.UserCareer"%>
<%@page import="com.educare.edu.comn.model.UserCertif"%>
<%@page import="com.educare.edu.comn.model.UserComp"%>
<%@page import="com.educare.edu.comn.model.UserSchool"%>
<%@page import="com.educare.edu.comn.vo.ResultVO"%>
<%@page import="com.educare.edu.education.service.EduService"%>
<%@page import="org.springframework.beans.BeanUtils"%>
<%@page import="com.educare.util.LncUtil"%>
<%@page import="com.fasterxml.jackson.core.JsonProcessingException"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.educare.edu.comn.mapper.LectureEtcIemDataMapper"%>
<%@page import="com.educare.edu.comn.vo.LectureEtcIemDataVO"%>
<%@page import="com.educare.edu.comn.mapper.LectureEtcIemMapper"%>
<%@page import="com.educare.edu.comn.vo.LectureEtcIemVO"%>
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
ServletContext conext = session.getServletContext();
WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(conext);

EduMapper eduMapper = (EduMapper)wac.getBean("EduMapper");
LectureDormiService lectureDormiService = (LectureDormiService)wac.getBean("LectureDormiService");

int eduSeq = Integer.parseInt(request.getParameter("eduSeq"));

Lecture lctre = eduMapper.selectLctreByPk(eduSeq);
ResultVO result = lectureDormiService.getClassDormiStdntByEdu(eduSeq,0);

pageContext.setAttribute("data", result.getData());
pageContext.setAttribute("lctre", lctre);

String fileNm1 = ""+lctre.getEduNm()+" ";
fileNm1 = fileNm1.replaceAll(" ", "_");
fileNm1 = fileNm1.replaceAll(",", "_");
fileNm1 = "식비_숙소정보_"+LncUtil.filterFileNm(fileNm1);
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
 table {
            width: 100%; /* 테이블 전체 너비를 100%로 설정 */
            table-layout: auto; /* 셀 너비를 콘텐츠 길이에 맞추기 위해 자동 레이아웃 설정 */
        }
        td {
            white-space: pre-wrap; /* 줄바꿈을 유지하고, 긴 텍스트는 한 줄로 표시 */
            max-width: 1px; /* 너비를 최소로 설정하여 콘텐츠에 맞게 늘어남 */
        }

td, th {
	border: 1px solid #d9d9d9;
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
	<table>
					<thead>
						<tr>
							<th>1</th>
							<th>성명</th>
							<th>ID</th>
							<th>연락처</th>
							<th>이메일</th>
							<th>소속</th>
							<th>성별</th>
							<th>버스이용</th>
							<th>식비</th>
							<th>요청숙소</th>
							<th>숙박요금</th>
							<th>배정숙소</th>
							<th>숙소배정</th>
							<th>납부여부</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${data.stdnt }" var="o" varStatus="s">
						
						<tr>
							<td>${s.count }</td>
							<td>${o.userNm}</td>
							<td>${o.loginId}</td>
							<td>${o.mobile}</td>
							<td>${o.email}</td>
							<td>${o.userOrgNm}</td>
							<td>${o.mfTypeNm}</td>
							<td>
							<template v-if="o.useTrans == 1">
							세종 탑승
							</template>
							<template v-else-if="o.useTrans == 2">
							오송 탑승
							</template>
							<template v-else>
							미이용
							</template>
							
							
							</td>
							<td>${o.mealFee }</td>
							<td>
							<c:if test="${o.rceptCapaCnt > 0}">
							${o.rceptCapaCnt}인실
							</c:if>
							<c:if test="${o.rceptAccessYn eq 'Y'}">
							(장애인)
							</c:if>
							</td>
							<td>${o.dormiFee}</td>
							<td>
							<c:if test="${o.dormiSeq > 0}">
							${o.capaCnt}인실
							<c:if test="${o.accessYn eq 'Y'}">
								(장애인)
							</c:if>
							</c:if>
							<td>
							${o.dormiNm }
							</td>
							<td>
							${o.depositYn }
							</td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
</body>
</html>