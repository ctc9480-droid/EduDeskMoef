<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<div class="apply_c mt80">
	<img src="${utcp.ctxPath}/resources/user/image/icon/join_check.png" class="join_complete" alt="" />
	<span class="ap03">
		<b>회원가입이</b> 완료되었습니다!
	</span>
	<span class="ap04">로그인 하시면 다양한 서비스를 받아보실 수 있습니다.</span>
	<img src="${utcp.ctxPath}/resources/user/image/logo_on.png" class="join_logo" alt="재정경제부 융복합경제재정교육플랫폼">
</div>
<div class="btn_step2"> 
	<a href="${utcp.ctxPath}/user/login.do" class="btn_step01">로그인하기</a> <a href="/" class="btn_step02 btn_go_main">메인화면 이동</a>
</div>