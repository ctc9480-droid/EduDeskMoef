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

String fileNm1 = "개인교육 통계";
fileNm1=new String(fileNm1.getBytes("KSC5601"), "8859_1");
fileNm1 = fileNm1.replaceAll(" ", "_");
fileNm1 = fileNm1.replaceAll(",", "_");
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
		<caption class="sound_only">개인교육 통계 테이블</caption>
		<thead>
			<tr>
				<th scope="col" class="vm">순번</th>
				<th scope="col" class="vm">연도</th>
				<th scope="col" class="vm">학기</th>
				<th scope="col" class="vm">과정</th>
				<th scope="col" class="vm">교육명</th>
				<th scope="col" class="vm">교육일</th>
				<th scope="col" class="vm">정원</th>
				<th scope="col" class="vm">승인인원</th>
				<th scope="col" class="vm">잔여인원</th>
				<th scope="col" class="vm">예약인원</th>
				<th scope="col" class="vm">결제금액</th>
			</tr>
		</thead>
		<tbody>
			<c:set var="personnelSum" value="0"/>
			<c:set var="stdntCntSum" value="0"/>
			<c:set var="waitCntSum" value="0"/>
			<c:set var="totalAmountSum" value="0"/>
			<c:forEach var="o" items="${data.pageList}" varStatus="s">
				<c:set var="personnelSum" value="${personnelSum+o.personnel }"/>
				<c:set var="stdntCntSum" value="${stdntCntSum+o.stdntCnt }"/>
				<c:set var="waitCntSum" value="${waitCntSum+o.waitCnt }"/>
				<c:set var="totalAmountSum" value="${totalAmountSum+o.totalAmount }"/>
				<tr>
					<td>${data.pageNavi.totalRecordCount - (((data.pageNavi.currentPageNo-1)*data.pageNavi.recordCountPerPage) + s.index)}</td>
					<td>${o.eduYear }</td>
					<td>${vcp.getEduTermNm(o.eduTerm)} </td>
					<td>${o.detailCtgryNm }</td>
					<td>${o.eduNm }</td>
					<td>${o.eduPeriodBegin} ~ ${o.eduPeriodEnd }</td>
					<td>${o.personnel }</td>
					<td>${o.stdntCnt }</td>
					<td>
					<%-- ${utcp.calculatePercentage(o.stdntCnt,o.personnel,2) } --%>
					${o.personnel - o.stdntCnt }
					</td>
					<td>${o.waitCnt }</td>
					<td>
					<fmt:formatNumber value="${o.totalAmount }" type="currency" currencySymbol="￦" minFractionDigits="0" maxFractionDigits="0" />
					</td>
				</tr>
			</c:forEach>
			<tr>
				<td>합계</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td>${personnelSum }</td>
				<td>${stdntCntSum }</td>
				<td>
				<%-- 
				${utcp.calculatePercentage(stdntCntSum,personnelSum,2) }
				 --%>
				 ${personnelSum - stdntCntSum }
				</td>
				<td>${waitCntSum }</td>
				<td>
				<fmt:formatNumber value="${totalAmountSum }" type="currency" currencySymbol="￦" minFractionDigits="0" maxFractionDigits="0" />
				</td>
			</tr>
			<c:if test="${empty data.pageList }">
				<tr>
					<td colspan="11">내역이 없습니다.</td>
				</tr>
			</c:if>
		</tbody>
	</table>
</body>
</html>