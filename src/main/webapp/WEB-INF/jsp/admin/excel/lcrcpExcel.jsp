<%@page import="com.fasterxml.jackson.databind.ObjectMapper"%>
<%@page import="com.educare.util.LncUtil"%>
<%@page import="com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException"%>
<%@page import="com.fasterxml.jackson.core.type.TypeReference"%>
<%@page import="com.educare.edu.comn.model.UserComp"%>
<%@page import="org.springframework.util.ObjectUtils"%>
<%@page import="java.util.Map"%>
<%@page import="com.educare.edu.comn.vo.LectureEtcIemVO"%>
<%@page import="com.educare.edu.education.service.EduService"%>
<%@page import="com.educare.edu.education.service.EduVO"%>
<%@page import="com.educare.edu.comn.vo.ResultVO"%>
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
String srchBeginDt = request.getParameter("srchBeginDt");
String srchEndDt = request.getParameter("srchEndDt");

ServletContext conext = session.getServletContext();
WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(conext);
EduService eduService = (EduService)wac.getBean("EduService");

EduVO vo = (EduVO)request.getAttribute("vo");

if(!ObjectUtils.isEmpty(srchBeginDt)){
	vo.setSrchDateBegin(srchBeginDt.replaceAll("-", ""));
}
if(!ObjectUtils.isEmpty(srchEndDt)){
	vo.setSrchDateEnd(srchEndDt.replaceAll("-", ""));
}

ResultVO dataVO = eduService.getLcrcpPageList(vo,0);
pageContext.setAttribute("data", dataVO.getData());

//기타항목 조회
Map<String,Object> rstData = (Map<String,Object>)dataVO.getData();
List<Lecture> lcrcpList = (List<Lecture>)rstData.get("lcrcpList");
eduService.setEtcIemDataList(lcrcpList);

String fileNm1 = "[개인신청내역]";
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
	<table class="w100 tb01">
		<caption class="sound_only">개인교육 신청내역 테이블</caption>
		<thead>
			<tr>
				<th scope="col" class="vm">순번</th>
				<th scope="col" class="vm">과정</th>
				<th scope="col" class="vm">교육명</th>
				<th scope="col" class="vm">교육일</th>
				<th scope="col" class="vm">신청자</th>
				<th scope="col" class="vm">영문</th>
				<th scope="col" class="vm">성별</th>
				<th scope="col" class="vm">생년월일</th>
				<th scope="col" class="vm">아이디</th>
				<th scope="col" class="vm">휴대폰번호</th>
				<th scope="col" class="vm">이메일</th>
				<th scope="col" class="vm">소속</th>
				<th scope="col" class="vm">신청상태</th>
				<th scope="col" class="vm">신청일</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="o" items="${data.lcrcpList}" varStatus="s">
				
				<tr>
				
					
					<td>${data.pageNavi.totalRecordCount - (((data.pageNavi.currentPageNo-1)*data.pageNavi.recordCountPerPage) + s.index)}</td>
					<td>${o.ctg1Nm }</td>
					<td>${o.eduNm }</td>
					<td>${o.eduPeriodBegin} ~ ${o.eduPeriodEnd }</td>
					<td>${o.userNm }</td>
					<td>${o.userEnNm }</td>
					<td>${o.addMfTypeNm }</td>
					<td>${o.birth }</td>
					<td>${o.loginId }</td>
					<td>${o.mobile }</td>
					<td>${o.email }</td>
					<td>${o.userOrgNm }</td>
					<td>${o.addRceptStateNm }</td>
					<td>${utcp.convDateToStr(o.regDe,'yyyy-MM-dd HH:mm') }</td>
					
				</tr>
				
			</c:forEach>
			<c:if test="${empty data.lcrcpList }">
				<tr>
					<td colspan="11">내역이 없습니다.</td>
				</tr>
			</c:if>
		</tbody>
	</table>
</body>
</html>