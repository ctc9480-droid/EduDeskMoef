<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp" %>
샘플리스트<br/>
<c:forEach items="${list }" var="o">
${o.idx },${o.title }<br/>
</c:forEach>