<%@page import="com.educare.edu.quizTest.vo.QuizTestVO"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="com.educare.edu.comn.vo.ResultVO"%>
<%@page import="com.educare.edu.quizTest.service.QuizTestService"%>
<%@page import="com.educare.util.LncUtil"%>
<%@page
	import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="java.util.List"%>
<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
ServletContext conext = session.getServletContext();
WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(conext);

QuizTestService quizTestService = (QuizTestService)wac.getBean("QuizTestService");

int eduSeq = Integer.parseInt(request.getParameter("eduSeq"));
int testSeq = Integer.parseInt(request.getParameter("testSeq"));

ResultVO result= quizTestService.getTestResult(eduSeq,testSeq);
Map data = (HashMap)result.getData();
QuizTestVO test = (QuizTestVO)data.get("test");

String testNm = test.getTestNm();

pageContext.setAttribute("data", data);

String fileNm1 = "[시험현황] "+testNm+" ";
fileNm1 = fileNm1.replaceAll(" ", "_");
fileNm1 = fileNm1.replaceAll(",", "_");
System.out.println(fileNm1);
fileNm1 = LncUtil.filterFileNm(fileNm1);
System.out.println(fileNm1);
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
	mso-number-format: '@';
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
	<table class="tb07 w100">
		<caption class="sound_only">시험 응시자 목록 - No, 이름, ID, 연락처,
			제출시간, 채점여부, 평가점수, 성취수준 순으로 정보를 제공합니다.</caption>
		<thead>
			<tr>
				<th scope="col">No</th>
				<th scope="col">이름</th>
				<th scope="col">ID</th>
				<th scope="col">연락처</th>
				<th scope="col">제출시간</th>
				<th scope="col">상태</th>
				<th scope="col">평가점수</th>
				<th scope="col">총점</th>
				<th scope="col">성취수준</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${data.stdntList }" var="o" varStatus="s">
			<tr >
				<td>${ fn:length(data.stdntList) - s.index }</td>
				<td>${o.userNm}</td>
				<td>${o.loginId}</td>
				<td>${o.mobile}</td>
				<td>
					<c:if test="${o.testResultState > 0}">
					${utcp.convDateToStr(o.endDe,'yyyy-MM-dd HH:mm') }
					</c:if>
				</td>
				<td>${o.addTestResultStateNm}</td>
				<td>${o.marks}</td>
				<td>100</td>
				<td>${o.addGradeNm}</td>
				
			</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>