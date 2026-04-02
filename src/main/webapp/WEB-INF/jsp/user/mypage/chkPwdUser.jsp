<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp" %>

<form id="chkPwdUserForm" action="updUser.do" method="post">
<div class="listWrap" style="padding-top:0">
	<div class="idpw_box" id="passwd">
		<span class="idpw_title">비밀번호 확인</span>
		<ul>
			<li class="idpw_text">비밀번호</li>
			<li><input type="password" name="userPw"/></li>
		</ul>
		<button type="submit" class="btn_find" style="cursor:pointer;" >확인</button>
	</div>
</div>
</form>
