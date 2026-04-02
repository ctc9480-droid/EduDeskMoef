<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>

<%
/**/
String fileNm = request.getParameter("excelFileNm");
String fileNm1 = "[통계] 만족도조사_"+fileNm;
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
		<c:set var="stat2RowNo" value="0"/>
<c:set var="stat3RowNo" value="0"/>
<c:forEach items="${feedbackInfo.qtList}" var="o" varStatus="s">
	<c:if test="${fn:length(o.chList2)==2}">
		<c:set var="stat2RowNo" value="${s.count }"/>
	</c:if>
	<c:if test="${o.qt.qtType == 0}">
		<c:set var="stat3RowNo" value="${s.count }"/>
	</c:if>
</c:forEach>
		
		<table class="w100 tb01">
			<thead>
				<tr>
					<th rowspan="">구분</th>
					<th rowspan="">항목</th>
					<th rowspan="">답변</th>
					<th rowspan="">카운트</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${feedbackInfo.qtList}" var="o" varStatus="s">
					<c:set var="isFirstDiv" value="false" />
					<c:set var="rowCnt" value="0" />
					<c:if test="${o.qt.qtDiv ne feedbackInfo.qtList[s.index-1].qt.qtDiv }">
						<c:set var="isFirstDiv" value="true" />
					</c:if>
					<c:if test="${isFirstDiv }">
						<c:forEach begin="${s.index }" end="${fn:length(feedbackInfo.qtList)-1 }" var="i2">
							<c:if test="${o.qt.qtDiv eq feedbackInfo.qtList[i2].qt.qtDiv }">
								<c:set var="rowCnt" value="${rowCnt+fn:length(feedbackInfo.qtList[i2].chList) }" />
								<c:if test="${feedbackInfo.qtList[i2].qt.qtType == 0 }">
									<c:set var="rowCnt" value="${rowCnt+1 }" />
								</c:if>
							</c:if>
						</c:forEach>
					</c:if>

					<c:if test="${o.qt.qtType != 0 }">
						<c:forEach items="${o.chList }" var="o2" varStatus="s2">
							<c:set var="rowCnt2" value="0" />
							<c:if test="${o2.chIdx == 1 }">
								<c:set var="rowCnt2" value="${fn:length(o.chList) }" />
							</c:if>
							<tr>
								<%-- 
							 --%>
								<c:if test="${rowCnt > 0 }">
									<td rowspan="${rowCnt }">${o.qt.qtDiv}</td>
								</c:if>
								<c:if test="${rowCnt2 > 0 }">
									<td rowspan="${rowCnt2 }">${o.qt.question}</td>
								</c:if>
								<td>${o2.choice }</td>
								<td>${o2.asCnt }</td>
							</tr>
							<c:if test="${rowCnt>1 }">
								<c:set var="rowCnt" value="0" />
							</c:if>
						</c:forEach>
					</c:if>
					<c:if test="${o.qt.qtType == 0 }">
						<tr>
							<%-- 
							 --%>
							<c:if test="${rowCnt > 0 }">
								<td rowspan="${rowCnt }">${o.qt.qtDiv}</td>
							</c:if>
							<td>${o.qt.question}</td>
							<td><c:forEach items="${o.asList }" var="as">
							${as.answer }<br />
								</c:forEach></td>
							<td></td>
						</tr>
					</c:if>
				</c:forEach>
			</tbody>
		</table>
		
		</body>
		</html>

