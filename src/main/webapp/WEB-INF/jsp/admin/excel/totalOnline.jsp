<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
String eduNm = request.getAttribute("eduNm").toString();
eduNm=eduNm.replaceAll(" ", "_");
eduNm=new String(eduNm.getBytes("KSC5601"), "8859_1");
String fileNm1 = "[온라인진행현황] ";
fileNm1=new String(fileNm1.getBytes("KSC5601"), "8859_1");
/*

*/
response.setHeader("Content-Disposition", "attachment; filename="+fileNm1+eduNm+".xls");
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
	<table class="tc w100">
		<caption>${eduNm }</caption>
		<thead bgcolor="#f7f8fa">
			<tr>
				<th rowspan="">번호</th>
				<th rowspan="">아이디</th>
				<th rowspan="">이름</th>
				<c:forEach items="${checkUserList[0].timeList }" var="o">
				<th rowspan="">${o.timeSeq }:${o.classHowNm }</th>
				
				<c:set var="colcnt" value="${colcnt+1}"/>
				</c:forEach>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${checkUserList}" var="o" varStatus="status">
			<tr>
				<td>${status.count }</td>
				<td> ${o.email} </td>
				<td> ${o.userNm} </td>
				<c:forEach items="${o.timeList }" var="o2">
				<td> 
				<c:if test="${o2.classHow==3 }">
				<fmt:formatNumber var="playRatio" value="${o2.movAllTime!=0?o2.movTime/o2.movAllTime*100:0 }" type="percent" pattern="0.0"/> 
				${playRatio }
				</c:if>
				<c:if test="${o2.classHow!=3 }">
				${utcp.convDateToStr(o2.accessDate,'yyyy-MM-dd HH:mm') }
				</c:if>
				</td>
				</c:forEach>
			</tr>
			</c:forEach>
			<c:if test="${empty checkUserList }">
			<tr><td colspan="${colcnt }">데이터가 없습니다.</td></tr>
			</c:if>
		</tbody>
	</table>
</body>
</html>