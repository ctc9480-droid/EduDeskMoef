<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<%@ page import="com.educare.edu.menu.service.model.Menu"%>
<%@ page import="com.educare.edu.menu.service.MenuUtil"%>
<%
	String uri = MenuUtil.getReqUri(request);
	String menuIds = MenuUtil.accessMenuIds();

	pageContext.setAttribute("baseMenuList", MenuUtil.getAdminBaseMenuList());
	pageContext.setAttribute("menuIds", menuIds);
%>
<!--// side_bar //-->
<div class="side_bar">
	<h1>
		<a href="${utcp.ctxPath}/admin/main/index.do" class="logo"> <img src="${utcp.ctxPath}/resources/admin/images/logo_adm_wt.png" alt="로고">
		</a>
	</h1>
	<ul class="side_nav">
		<c:forEach items="${baseMenuList }" var="o">
			<c:if test="${fn:indexOf(menuIds,o.cd)>=0 or empty menuIds}">
			<c:if test="${o.st == 1 }">
			<li class="">
				<a href="#none" class="menu"> <i class="${o.iconNm }"></i> <span>${o.nm }</span></a>
				<c:if test="${not empty o.sub }">
				<ul>
					<c:forEach items="${o.sub }" var="o2">
					<c:if test="${fn:indexOf(menuIds,o2.cd)>=0 or empty menuIds}">
					<c:if test="${o2.st == 1 }">
					<li class="area-menu">
						<a href="${utcp.ctxPath}${o2.url }" menuCd="${o2.cd }">${o2.nm }</a>
						<c:if test="${not empty o2.sub }">
						<!--// 221228hy추가 //-->
						<ul class="depth3">
							<c:forEach items="${o2.sub }" var="o3">
							<c:if test="${fn:indexOf(menuIds,o3.cd)>=0 or empty menuIds}">
							<c:if test="${o3.st == 1 }">
							<li><a href="${utcp.ctxPath}${o3.url }" menuCd="${o3.cd }">${o3.nm }</a></li>
							</c:if>
							</c:if>
							</c:forEach>
						</ul>
						<!--// 221228hy추가 //-->
						</c:if>
					</li>
					</c:if>
					</c:if>
					</c:forEach>
				</ul>
				</c:if>
				<img src="${utcp.ctxPath}/resources/admin/images/plus.png">
			</li>
			</c:if>
			</c:if>
		</c:forEach>
	</ul>
	<div class="menu_footer">
		<div class="menu_copy">
			<a href="#" class=""> <span class="side_bar_open"> <i class="fas fa-angle-double-right"></i>
			</span> <span class="side_bar_close"> <i class="fas fa-angle-double-left"></i> Close
			</span>
			</a>
		</div>
		<!--// menu_copy //-->
	</div>
	<!--// menu_footer //-->
</div>
<!--// side_bar //-->

<script>
$('.side_nav .area-menu').each(function(){
	var menuCd = $(this).find('a').attr('menuCd');
	if('<%=uri%>'.indexOf(menuCd)>=0){
		$(this).parent().css('display','block');
	}
});
</script>