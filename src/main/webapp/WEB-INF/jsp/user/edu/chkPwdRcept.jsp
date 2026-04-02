<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp" %>

<form id="chkPwdRceptForm" method="post" action="eduView.do">
<input type="hidden" name="eduSeq" value="${eduSeq }"/>
<div class="listWrap" style="padding-top:0">
	<div class="idpw_box" id="passwd">
		<span class="idpw_title">교육신청 비밀번호를 입력하세요</span>
		문의 : ${lctre.tel } (${lctre.inqNm })
		<ul>
			<!-- <li class="idpw_text">비밀번호</li> -->
			<li><input type="password" name="scrtyPw"/></li>
		</ul>
		<button type="submit" class="btn_find" style="cursor:pointer;" >확인</button>
	</div>
</div>
</form>
