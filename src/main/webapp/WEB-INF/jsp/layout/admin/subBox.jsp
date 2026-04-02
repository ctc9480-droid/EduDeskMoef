<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.educare.edu.menu.service.model.Menu"%>
<%@ page import="com.educare.edu.menu.service.MenuUtil"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<%
	String uri = MenuUtil.getReqUri(request);
	Menu menu = MenuUtil.getAdmMenuByUri(uri);
%>
<!--// title_name //-->		
<div class="title_name">
	<h1><%=menu.getMenuNm() %>
		<ul>
			<li>
				<a href="${utcp.ctxPath}/admin/main.do">
					<img src="${utcp.ctxPath}/resources/admin/images/sub_title_icon.png">
				</a>
			</li>
			<%
			pageContext.setAttribute("naviList", MenuUtil.getAdminNaviList());
			%>
			<c:forEach items="${naviList }" var="o">
			<li class="mgl10 mgr10">
				<img src="${utcp.ctxPath}/resources/admin/images/sub_title_icon1.png">
			</li>
			<li>${o.nm }</li>
			</c:forEach>
		</ul>
	</h1>
</div>
<!--// title_name //-->