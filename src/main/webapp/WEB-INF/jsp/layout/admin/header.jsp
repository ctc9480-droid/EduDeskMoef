<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp" %>
<%@ page import="com.educare.edu.member.service.SessionUserInfoHelper"%>
<script>
function fn_confirm(){
	location.href = "#confirmPop";
}

function fn_logout(){
	location.href = "${utcp.ctxPath}/admin/logout.do";
}
</script>
<div class="top_gnb">
	<div class="top_select">
		<ul>
			<li>
				<p>
					<% if(SessionUserInfoHelper.isLogined()) { %>
						<span id="remaining-time"></span>
						<%=SessionUserInfoHelper.getUserNm() %>님
					<% } else { %>
						<a href="${utcp.ctxPath}/admin/login.do">로그인</a>
					<% } %>
				</p>
				<% if(SessionUserInfoHelper.isLogined()) { %>
					<a href="javascript:;" onclick="fn_confirm(); return false;"><img src="${utcp.ctxPath}/resources/admin/images/logout.png"></a>
				<% } %>
			</li>
		</ul>
	</div>
</div>

<!--// popup_message //-->
<div class="remodal messagePop messagePop1" data-remodal-id="confirmPop" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc">
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC">로그아웃</p>
		</div>
		<div class="modal-body">
			<p class="messageTxt">
				로그아웃 하시겠습니까?
			</p>
		</div>
		<div class="modal-footer">
			<div class="tc">
				<button onclick="fn_logout(); return false;" class="remodal-confirm btn02 btn_green">확인</button>
				<button data-remodal-action="cancel" class="remodal-cancel btn02 btn_gray">취소</button>
			</div>
		</div>
	</div>
</div>
<!--// popup_message //-->


<script>
function countdown(minutes, seconds) {
	var remainingMinutes = minutes;
	var remainingSeconds = seconds;
	
	var countdownInterval = setInterval(function() {
	  // 1초 감소
	  remainingSeconds--;
	
	  // 분과 초가 모두 0이 되었을 때
	  if (remainingMinutes === 0 && remainingSeconds === 0) {
	    clearInterval(countdownInterval);
	    console.log("Countdown finished!");
	    location.href = "/admin/logout.do";
	  }
	
	  // 초가 0보다 작아지면 분 감소, 초를 59로 설정
	  if (remainingSeconds < 0) {
	    remainingMinutes--;
	    remainingSeconds = 59;
	  }
	
	  // 분과 초를 2자리 숫자로 표시하기 위해 10보다 작을 경우 앞에 0 추가
	  var formattedMinutes = ("0" + remainingMinutes).slice(-2);
	  var formattedSeconds = ("0" + remainingSeconds).slice(-2);
	
	  // 결과 출력
	  document.querySelector("#remaining-time").innerText="[남은 시간 : "+formattedMinutes+"분 "+formattedSeconds+"초]";
	}, 1000);
}
	
function updateSessionTime() {
	  $.ajax({
		url: "/admin/ajax/getRemainingTime.ajax",
		type: "GET",
        success: function(r) {
        	countdown(r.data.remainingMinutes, r.data.remainingSecondsMod);
        }
    }); 
}


$(document).ready(function() {
    updateSessionTime(); // 페이지 로드 시 한 번 호출
});

</script>