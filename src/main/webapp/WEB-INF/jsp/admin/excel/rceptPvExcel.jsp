<%@page import="com.educare.edu.education.service.EduVO"%>
<%@page import="com.educare.edu.comn.vo.ResultVO"%>
<%@page import="com.educare.edu.education.service.EduService"%>
<%@page import="org.springframework.beans.BeanUtils"%>
<%@page import="com.educare.util.LncUtil"%>
<%@page import="com.fasterxml.jackson.core.JsonProcessingException"%>
<%@page import="com.fasterxml.jackson.databind.ObjectMapper"%>
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
EduService eduService = (EduService)wac.getBean("EduService");
EduMapper eduMapper = (EduMapper)wac.getBean("EduMapper");

EduVO vo = (EduVO)request.getAttribute("vo");
int eduSeq = Integer.parseInt(request.getParameter("eduSeq"));

ResultVO dataVO = eduService.getLcrcpPageList(vo,0);
pageContext.setAttribute("data", dataVO.getData());

Lecture lctre = eduMapper.selectLctreByPk(eduSeq);

//기타항목 조회
Map<String,Object> rstData = (Map<String,Object>)dataVO.getData();
List<Lecture> lcrcpList = (List<Lecture>)rstData.get("lcrcpList");
eduService.setEtcIemDataList(lcrcpList);

String fileNm1 = "[신청현황] "+lctre.getEduNm()+" ";
fileNm1 = fileNm1.replaceAll(" ", "_");
fileNm1 = fileNm1.replaceAll(",", "_");
fileNm1 = LncUtil.filterFileNm(fileNm1);
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
	<table class="w100 tb01">
		<caption class="sound_only">개인교육 신청내역 테이블</caption>
		<thead>
			<tr>
				<th scope="col" class="vm">순번</th>
				<th scope="col" class="vm">연도</th>
				<th scope="col" class="vm">학기</th>
				<th scope="col" class="vm">교육명</th>
				<th scope="col" class="vm">아이디</th>
				<th scope="col" class="vm">이름</th>
				<th scope="col" class="vm">생년월일</th>
				<th scope="col" class="vm">핸드폰번호</th>
				<th scope="col" class="vm">이메일</th>
				<th scope="col" class="vm">기타신청내용</th>
				<th scope="col" class="vm">신청상태</th>
				<th scope="col" class="vm">결제수단</th>
				<th scope="col" class="vm">결제(취소)일시</th>
				<th scope="col" class="vm">결제상태</th>
				<th scope="col" class="vm">결제금액</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="o" items="${data.lcrcpList}" varStatus="s">
				<tr>
					<td>${data.pageNavi.totalRecordCount - (((data.pageNavi.currentPageNo-1)*data.pageNavi.recordCountPerPage) + s.index)}</td>
					<td>${o.eduYear }</td>
					<td>${vcp.getEduTermNm(o.eduTerm)} </td>
					<td>${o.eduNm }</td>
					<td>${o.loginId2 }</td>
					<td>${o.userNm }</td>
					<td>${o.birth }</td>
					<td>${o.decMobile }</td>
					<td>${o.decEmail }</td>
					<td>${o.etcData01 }</td>
					<td>${o.addRceptStateNm }</td>
					<td>${o.addPayTypeNm }</td>
					<td>${utcp.convDateToStr(o.updDt2,'yyyy-MM-dd HH:mm') }</td>
					<td>${o.addPayStateNm }</td>
					<td><fmt:formatNumber value="${o.amount }" type="currency" currencySymbol="￦" minFractionDigits="0" maxFractionDigits="0" /></td>
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