<%@page import="com.educare.edu.member.service.SessionUserInfoHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<table class="tb03 edutb" style="border-top: 3px solid #555;" id="tabDate1">
	<tr>
		<th>교육 기간</th>
		<td>${lctre.eduPeriodBegin }~${lctre.eduPeriodEnd }</td>
	</tr>
	<tr>
	<th>교육 금액</th>
	<td>
		<c:choose>
			<c:when test="${lctre.fee != 0 }">
				유료
			</c:when>
			<c:otherwise>
				무료
			</c:otherwise>
		</c:choose>
	</td>
	</tr>
	<tr>
		<th>상세 내용</th>
		<td>${lctre.cn}</td>
	</tr>
	<c:if test="${not empty lctre.addr}">
		<tr>
			<th>교육 장소</th>
			<td>(${lctre.postNo}) ${lctre.addr } ${lctre.addrDetail }<br /> ${lctre.addrMemo }
			</td>
		</tr>
	</c:if>
	<!--
					<tr>
						<th>문의</th>
						<td></td>
					</tr>
					-->
</table>