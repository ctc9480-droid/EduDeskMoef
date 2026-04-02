<%@page import="com.educare.util.LncUtil"%>
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
ServletContext conext = session.getServletContext();
WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(conext);
EduMapper eduMapper = (EduMapper)wac.getBean("EduMapper");
LectureTimeStdntMapper lectureTimeStdntMapper = (LectureTimeStdntMapper)wac.getBean("LectureTimeStdntMapper");
int eduSeq = Integer.parseInt(request.getParameter("eduSeq"));
Lecture lctre = eduMapper.selectLctreByPk(eduSeq);
List<LectureStdnt> dataList = eduMapper.selectStdntExcelList(eduSeq);

for(LectureStdnt o:dataList){
	LectureTimeStdnt p = new LectureTimeStdnt();
	p.setEduSeq(o.getEduSeq());
	p.setUserId(o.getUserId());
	List<LectureTimeStdnt> list2 = lectureTimeStdntMapper.selectByEduSeqUserId(p);
	o.setLtsInfo(list2);
}

pageContext.setAttribute("lctre", lctre);
pageContext.setAttribute("dataList", dataList);

String fileNm1 = "[수강현황] "+lctre.getEduNm()+" ";
fileNm1 = fileNm1.replaceAll(" ", "_");
fileNm1 = fileNm1.replaceAll(",", "_");
System.out.println(fileNm1);
fileNm1 = LncUtil.filterFileNm(fileNm1);
System.out.println(fileNm1);
fileNm1=new String(fileNm1.getBytes("KSC5601"), "8859_1");
/*
response.setHeader("Content-Disposition", "attachment; filename="+fileNm1+".xls");
response.setHeader("Content-Description", "JSP Generated Data");
response.setContentType("application/vnd.ms-excel"); 
*/





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
<c:set var="colcnt" value="3"/>
	<table class="tc w100">
		<caption>${eduNm }</caption>
		<thead bgcolor="#f7f8fa">
			<tr>
				<th rowspan="">연번</th>
				<th>이름</th>
				<th>아이디</th>
				<th>생년월일</th>
				<th>이메일</th>
				<th>휴대폰</th>
				<th>과정명</th>
				<th>교육신청일</th>
				<!-- 
				<th>진도율</th>
				 -->
				<th>출석점수</th>
				<th>과제점수</th>
				<th>시험점수</th>
				<th>설문</th>
				<th>수료여부</th>
				<th>수료증번호</th>
				<th>수료일</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${dataList}" var="o" varStatus="s">
			<tr>
				<td>${s.count }</td>
				<td>${o.userNm }</td>
				<td>${o.loginId }</td>
				<td>${o.birth }</td>
				<td>${o.email }</td>
				<td>${o.mobile }</td>
				<td>${lctre.eduNm }</td>
				<td>${utcp.convDateToStr(o.regDe,'yyyy.MM.dd') }</td>
				<%-- 
				<td>${o.addMovRatio }</td>
				 --%>
				<td>${o.attendScore }</td>
				<td>${o.workshopScore }</td>
				<td>${o.examScore }</td>
				<td>${o.surveyYn }</td>
				<td>${o.passYn eq 'Y'?'수료':'미수료' }</td>
				<td>${o.passCertNum }</td>
				<td>${utcp.convDateToStr(o.certDe,'yyyy.MM.dd') }</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>