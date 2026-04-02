<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="com.educare.edu.menu.service.model.Menu"%>
<%@ page import="com.educare.edu.menu.service.MenuUtil"%>
<%@ page import="com.educare.edu.member.service.SessionUserInfoHelper"%>
<%@ page import="com.educare.edu.member.service.model.UserInfo"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<%
int visualNo = MenuUtil.getMenuOrderNo();
if(visualNo == 0){
	visualNo = 5;
}
%> 

<div class="subVisualArea visualArea<%=visualNo%>">
	<div class="textWrap">
		<p><span>교육과정 소개</span>정보통신인증센터 정보보호교육원은 2020년 최우수훈련기관에 선정되었습니다.</p>
	</div>
</div>
<div class="navArea">
	<div class="navWrap">
		<img src="${utcp.ctxPath}/resources/user/image/icon/icon_home.png" alt="홈 아이콘" />
		<ul>
			<%
				pageContext.setAttribute("naviList", MenuUtil.getNaviList());
			%>
			<c:forEach items="${naviList }" var="o">
			<li>
				<a href="${o.url }" class="text_title">${o.nm }</a>
			</li>
			</c:forEach>
		</ul>
	</div>
</div>

