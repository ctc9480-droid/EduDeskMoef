<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp" %>
<script type="text/javascript">
	function fn_updPw(){
		var passOrg = $("#oldPw").val();
		var passNew = $("#newPw").val();
		
		if(passOrg == ""){
			$("#messageStr").html("기존 비밀번호를 입력하세요.");
			location.href = "#message";
		    return false;
		}
		
		if(passNew == ""){
			$("#messageStr").html("새 비밀번호를 입력하세요.");
			location.href = "#message";
	        return false;
	    }else{
			/* var pwCheck1 = /^(?=.*[a-zA-Z])(?=.*[0-9]).{8,15}$/.test(passNew);			//영문,숫자
			var pwCheck2 = /^(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).{8,15}$/.test(passNew);	//영문,특수문자
			var pwCheck3 = /^(?=.*[^a-zA-Z0-9])(?=.*[0-9]).{8,15}$/.test(passNew);		//특수문자, 숫자
			if( pwCheck1 == false && pwCheck2 == false && pwCheck3 == false) {
				$("#messageStr").html("비밀번호는 영문 , 숫자 , 특수문자 2종류 이상 조합하여 최소 8자리 이상 15자리 이하 입니다.");
				location.href="#message";
				return false;
			} */
			if(passNew.length<8){
				$("#messageStr").html("비밀번호는 8자리 이상 이어야 합니다.");
				location.href="#message";
				return false;
			}
	    }
		
		$.ajax({
			url: "/member/newPw.json",
			type: "post",
			data: {
				"passOrg" : passOrg,
				"passNew" : passNew
			},
			cache: false,
			async: true,
			success: function(r) {
				if(r.isLogined){
					if(r.isSuccess){
						location.href="#success";
					}else{
						$("#messageStr").html(r.message);
						location.href = "#message";
					}	
				}else{
					location.href = "#login";	
				}
			}
		});
	}
</script>
<div class="listWrap" style="padding-top:0">
	<div class="idpw_box" id="passwd">
		<span class="idpw_title">비밀번호 변경</span>
		<ul>
			<li class="idpw_text">기존 비밀번호</li>
			<li><input type="password" id="oldPw"/></li>
		</ul>
		<ul>
			<li class="idpw_text">새 비밀번호</li>
			<li><input type="password" id="newPw"/></li>
			<li class="idpw_text">※ 암호는 최소 8글자로 영대문자, 영소문자, 숫자, 특수문자 중 3가지 이상의 조합이 필요합니다.<br/>※ 비밀번호는 사용자 ID의 전부 또는 일부를 포함하지 않아야 합니다.</li>
			
		</ul>
		<div class="btn_find" style="cursor:pointer;" onclick="fn_updPw(); return false;">확인</div>
	</div>
</div>

<div class="remodal messagePop messagePop1" data-remodal-id="success" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc">
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC">알림</p>
		</div>
		<div class="modal-body">
			<p class="messageTxt">
				변경되었습니다.
			</p>
		</div>
		<div class="modal-footer">
			<div class="tc">
				<button onclick="location.href='/'" class="remodal-confirm btn02 btn_green">확인</button>
			</div>
		</div>
	</div>
</div>

<div class="remodal messagePop messagePop2" data-remodal-id="message" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc">
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC">알림</p>
		</div>
		<div class="modal-body">
			<p class="messageTxt" id="messageStr">
				
			</p>
		</div>
		<div class="modal-footer">
			<div class="tc">
				<button data-remodal-action="cancel" class="remodal-confirm btn02 btn_orange">확인</button>
			</div>
		</div>
	</div>
</div>

<div class="remodal messagePop messagePop2" data-remodal-id="login" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc">
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC">알림</p>
		</div>
		<div class="modal-body">
			<p class="messageTxt">
				로그인 후 이용하세요
			</p>
		</div>
		<div class="modal-footer">
			<div class="tc">
				<button onclick="location.href='${utcp.ctxPath}/user/login.do'" class="remodal-confirm btn02 btn_orange">확인</button>
			</div>
		</div>
	</div>
</div>