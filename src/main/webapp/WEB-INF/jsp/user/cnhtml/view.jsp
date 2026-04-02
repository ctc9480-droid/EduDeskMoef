<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<c:choose>
<c:when test="${result.result == 1 }">
${result.data.cnht.content}
</c:when>
<c:otherwise>
준비중
</c:otherwise>
</c:choose>



