<%@page import="com.educare.util.LncUtil"%>
<%@page import="java.util.Calendar"%>
<%@page import="com.educare.component.UtilComponent"%>
<%@page import="org.apache.commons.lang3.StringEscapeUtils"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" pageEncoding="UTF-8"%>

<%
String eduNm = request.getAttribute("eduNm").toString();
eduNm = StringEscapeUtils.unescapeHtml4(eduNm);
System.out.println(eduNm);
eduNm=eduNm.replaceAll(" ", "_");
eduNm=eduNm.replaceAll(",", "_");
eduNm = LncUtil.filterFileNm(eduNm);
eduNm=new String(eduNm.getBytes("KSC5601"), "8859_1");
System.out.println(eduNm);
/* 
 */
response.setHeader("Content-Disposition", "attachment; filename="+eduNm+"_"+UtilComponent.convDateToStr(Calendar.getInstance().getTime(), "yyyy.MM.dd")+".xls");
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
	<table class="tc w100">
		<thead bgcolor="#f7f8fa">
			<tr>
				<th rowspan="2">이름</th>
				<th rowspan="2">아이디</th>
				<c:forEach items="${ dateList}" var="o">
				<th colspan="${o.eduDtCnt}">${utcp.convDateToStr(utcp.convStrToDate(o.eduDt,'yyyyMMdd'),'MM.dd(E)')}</th>
				</c:forEach>
				<th rowspan="2">합계</th>
			</tr>
			<tr>
				<c:forEach items="${ timeList}" var="o">
				<th>${o.timeSeq}</th>
				</c:forEach>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${ stdntList}" var="o">
			<tr>
				<td>${o.userNm}</td>
				<td>${o.loginId}</td>
				<c:forEach items="${ o.stdntAttList}" var="o2">
				<td style="text-align:center;">${o2.addAttStr}</td>
				</c:forEach>
				<td style="mso-number-format:'@';">${o.attendCnt} / ${o.timeCnt}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>