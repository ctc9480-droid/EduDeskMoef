<%@ page language="java" pageEncoding="euc-kr"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
String eduNm = request.getAttribute("eduNm").toString();
eduNm=eduNm.replaceAll(" ", "_");
eduNm=new String(eduNm.getBytes("KSC5601"), "8859_1");
String timeNm = request.getAttribute("timeSeq").toString()+" 차시 ";
timeNm=new String(timeNm.getBytes("KSC5601"), "8859_1");

response.setHeader("Content-Disposition", "attachment; filename="+eduNm+"("+timeNm+").xls");
response.setHeader("Content-Description", "JSP Generated Data");
response.setContentType("application/vnd.ms-excel");
%>

<html>
<head>
<meta http-equiv="Content-Type" content="application/vnd.ms-excel;charset=euc-kr">

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
				<th rowspan="">아이디</th>
				<th rowspan="">이름</th>
				<th rowspan="">시간</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${ list}" var="o">
			<tr>
				<td> ${o.email} </td>
				<td> ${o.userNm} </td>
				<td> ${utcp.convDateToStr(o.regTime,'yyyy.MM.dd HH:mm:ss')} </td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>