<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<%@ page import="com.educare.edu.member.service.SessionUserInfoHelper"%>
<%@ page import="com.educare.edu.member.service.model.UserInfo"%>
<%@ page import="java.security.MessageDigest"%>

<div id="successDiv">
	<div class="listWrap" style="padding-top: 0px;">
		<div class="apply_c">
			<span class="ap01">${lctre.eduNm}</span> <span class="ap02">${lctre.ctg1Nm} / ${lctre.ctg2Nm }</span> <span class="ap03"><b>교육신청이 완료</b> 되었습니다.</span>
		</div>
	</div>
	<div class="btn_comp">
		<a href="/">메인으로 이동</a>
		<a href="${utcp.ctxPath}/user/mypage/myLcRceptList.do" class="btn_mp">신청내역 이동</a>
	</div>
</div>


