<%@page import="java.util.Iterator"%>
<%@page import="java.util.Enumeration"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>


<div class="center_top_wrap">
	<div class="center_top_box">
		<ul class="list_type1">
			<li>KTL Academy와 관련된 고객 여러분의 희망을 듣겠습니다.</li>
			<li>익명성은 철저히 보장되며 센터장 이외에는 확인이 불가합니다.</li>
			<li>답변을 받고자 하는 분은 내용에 답변 받을 메일주소를 적어 주시기 바랍니다.</li>
		</ul>
	</div>
</div>
<div class="tc">
	<a href="${vo.boardType }Write.do" class="btn05 btn_blue">글쓰기 <i class="fa-regular fa-pen-to-square"></i></a>
</div>

<%-- 	
<div class="listWrap pdt">
	<c:choose>
	<c:when test="${boardMst.permTp == 9 and not empty sessionScope.sessionUserInfo.userId}">
		<div class="fr">
			<a href="${vo.boardType }Write.do" class="btn02 btn_black">글쓰기</a>
		</div>
	</c:when>
	<c:otherwise>
		로그인이 필요한 서비스입니다.
	</c:otherwise>
	</c:choose>
</div>
--%>
<script>
	
</script>