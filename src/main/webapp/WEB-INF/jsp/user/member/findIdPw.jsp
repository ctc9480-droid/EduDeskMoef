<%@page import="com.educare.util.XmlBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp" %>
<!DOCTYPE HTML>
<html lang="ko" xml:lang=“ko” xmlns=“http://www.w3.org/1999/xhtml”>
<head>
<title><spring:eval expression="@prop['service.name']"/></title>
    <meta http-equiv="Content-Type" content="text/html; utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no">
    <meta http-equiv="imagetoolbar" content="no">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="keywords" content="<spring:eval expression="@prop['service.name']"/>, LMS, 교육, 온라인교육, 온라인강의">
    <meta name="description" content="<spring:eval expression="@prop['service.name']"/>">
    <meta name="author" content="<spring:eval expression="@prop['service.name']"/>">
    <meta property="og:type" content="website">
    <meta property="og:title" content="<spring:eval expression="@prop['service.name']"/>">
    <meta property="og:description" content="<spring:eval expression="@prop['service.name']"/>">
    <meta name="format-detection" content="telephone=no">



    <!-- style.css -->
    <link rel="stylesheet" type="text/css" href="${utcp.ctxPath}/resources/user/css/sub.css">
    <link rel="stylesheet" type="text/css" href="${utcp.ctxPath}/resources/user/css/slick.css"/>
    <link rel="stylesheet" type="text/css" href="${utcp.ctxPath}/resources/user/css/common.css">
    <link rel="stylesheet" type="text/css" href="${utcp.ctxPath}/resources/common/css/style.css">
    
    <!--// favicon -->
	<link rel="apple-touch-icon" sizes="57x57" href="${utcp.ctxPath}/resources/favicon/apple-icon-57x57.png">
	<link rel="apple-touch-icon" sizes="60x60" href="${utcp.ctxPath}/resources/favicon/apple-icon-60x60.png">
	<link rel="apple-touch-icon" sizes="72x72" href="${utcp.ctxPath}/resources/favicon/apple-icon-72x72.png">
	<link rel="apple-touch-icon" sizes="76x76" href="${utcp.ctxPath}/resources/favicon/apple-icon-76x76.png">
	<link rel="apple-touch-icon" sizes="114x114" href="${utcp.ctxPath}/resources/favicon/apple-icon-114x114.png">
	<link rel="apple-touch-icon" sizes="120x120" href="${utcp.ctxPath}/resources/favicon/apple-icon-120x120.png">
	<link rel="apple-touch-icon" sizes="144x144" href="${utcp.ctxPath}/resources/favicon/apple-icon-144x144.png">
	<link rel="apple-touch-icon" sizes="152x152" href="${utcp.ctxPath}/resources/favicon/apple-icon-152x152.png">
	<link rel="apple-touch-icon" sizes="180x180" href="${utcp.ctxPath}/resources/favicon/apple-icon-180x180.png">
	<link rel="icon" type="image/png" sizes="192x192"  href="${utcp.ctxPath}/resources/favicon/android-icon-192x192.png">
	<link rel="icon" type="image/png" sizes="32x32" href="${utcp.ctxPath}/resources/favicon/favicon-32x32.png">
	<link rel="icon" type="image/png" sizes="96x96" href="${utcp.ctxPath}/resources/favicon/favicon-96x96.png">
	<link rel="icon" type="image/png" sizes="16x16" href="${utcp.ctxPath}/resources/favicon/favicon-16x16.png">
	<meta name="msapplication-TileColor" content="#ffffff">
	<meta name="msapplication-TileImage" content="/resources/favicon/ms-icon-144x144.png">
	<meta name="theme-color" content="#ffffff">

    <!-- font -->
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@300;400;500&display=swap" rel="stylesheet">
	
	<!-- script.js -->
	<script src="${utcp.ctxPath}/resources/user/js/jquery-3.5.1.min.js"></script>
	<script type="text/javascript" src="${utcp.ctxPath}/resources/user/js/slick.min.js"></script>
	<script type="text/javascript" src="${utcp.ctxPath}/resources/user/js/common.js"></script>
	
	<!--// modal //-->
	<link rel="stylesheet" href="${utcp.ctxPath}/resources/admin/css/remodal.css">
	<link rel="stylesheet" href="${utcp.ctxPath}/resources/admin/css/remodal-default-theme.css">	
	<script src="${utcp.ctxPath}/resources/admin/js/remodal.js" type="text/javascript"></script>
	
	<script type="text/javascript" src="${utcp.ctxPath}/resources/plugins/vue/vue.js"></script>
	
	<script type="text/javascript">
	$(function(){
		$(".onlyNumber").keyup(function(event){      
	        var str;                     
	        if(event.keyCode != 8){
	            if (!(event.keyCode >=37 && event.keyCode<=40)) {
	                var inputVal = $(this).val();       
	                str = inputVal.replace(/[^0-9]/gi,'');            
	                $(this).val(str);             
	            }                   
	        }
	    });
		
		$('#p3').on('keypress', function(e){
			if(e.keyCode == '13'){
				fn_findId();
			}
		});
		
		$('#userNm2').on('keypress', function(e){
			if(e.keyCode == '13'){
				fn_findPw();
			}
		});
		$('#email3').change(function(){
			$('#email2').val(this.value);
		});
	});
	
	function fn_findId(){
		var isJoin = true;
		$('#area-input1').find('input').each(function() {
			if (this.required && (this.value == '' || this.value == 0)) {
				vm_alert_join.msg = this.title;
				vm_alert_join.focusId = this.id;
				location.href = '#md-alert-join';
				//onsole.log(this.required+','+this.name+','+this.value);
				isJoin = false;
				return false;
			}
		});
		if(!isJoin){
			return;
		}
		var userNm = $('#userNm1').val();
		var mobile = $('#mobile1').val()+'-'+$('#mobile2').val()+'-'+$('#mobile3').val();
		$.ajax({
			url: "/member/findId.json",
			type: "post",
			data: {userNm:userNm,mobile:mobile},
			cache: false,
			async: true,
			success: function(r) {
				if(r.isSuccess){
					$("#id_str1").html(r.message);
					location.href = "#id_message1";
				}else{
					$("#id_str2").html(r.message);
					location.href = "#id_message2";	
				}
			}
		});
	}
	
	function fn_findPw(){
		var isJoin = true;
		$('#area-input2').find('input').each(function() {
			if (this.required && (this.value == '' || this.value == 0)) {
				vm_alert_join.msg = this.title;
				vm_alert_join.focusId = this.id;
				location.href = '#md-alert-join';
				//onsole.log(this.required+','+this.name+','+this.value);
				isJoin = false;
				return false;
			}
		});
		if(!isJoin){
			return;
		}
		
		$.ajax({
			url: "/member/findPw.json",
			type: "post",
			data: {
				"userNm" : $("#userNm2").val(),
				"loginId" : $("#loginId").val()			
			},
			cache: false,
			async: true,
			success: function(r) {
				if(r.isSuccess){
					$(".idpw_box").hide();
					$(".cert").show();
					$("#token").val(r.token);
				}else{
					$("#id_str2").html(r.message);
					location.href = "#id_message2";	
				}
			}
		});
	}
	
	function fn_newPw(){
		
		var isJoin = true;
		$('#newPw').find('input').each(function() {
			if (this.required && (this.value == '' || this.value == 0)) {
				vm_alert_join.msg = this.title;
				vm_alert_join.focusId = this.id;
				location.href = '#md-alert-join';
				//onsole.log(this.required+','+this.name+','+this.value);
				isJoin = false;
				return false;
			}
		});
		if(!isJoin){
			return;
		}
		
		$.ajax({
			url: "/member/updPw.json",
			type: "post",
			data: {
				"userPw" : $("#pw1").val(),
				"token" : $("#token").val()			
			},
			cache: false,
			async: true,
			success: function(r) {
				
				if(r.isSuccess){
					$("#success").show();
					$("#newPw").hide();
				}else{
					vm_alert_join.msg = r.message;
					location.href = '#md-alert-join';
				}
				
			}
			, error: function (xhr, status, error) { 
				vm_alert_join.msg = "에러 발생: "+error;
				location.href = '#md-alert-join';
				console.error("xhr:", xhr); 
				console.error("status:", status); 
				console.error("error:", error); 
			}
			
		});
	}
	
	//이메일 인증번호 보내기
	function fn_sendAuthNoEmail(){
		$.ajax({
			url:'/member/sendAuthNoEmail.json',
			data:{loginId:$('#loginId').val()},
			success:function(r){
				if(r.result == 1){
					$('#btn-sendAuthNoEmail').hide();
					$('#btn-confirmAuthNoEmail').show();
				}
			}
		});
	}
	function fn_confirmAuthNoEmail(){
		$.ajax({
			url:'/member/confirmAuthNoEmail.json',data:{authNo:$('#authNo').val()},
			success:function(r){
				if(r.result == 1){
					$(".cert").hide();
					$("#newPw").show();
				}else{
					vm_alert_join.msg = "인증번호가 잘못되었습니다.";
					location.href = '#md-alert-join';
				}
			}
		});
	}
	</script>
	<style type="text/css">
		.apply {width:100%; border-bottom:1px solid #e5e5e5; padding:65px; text-align:center;}
		.apply span.ap03 {font-size:40px; width:100%; display:block; color:#334655; margin-bottom:27px;}
		.apply span.ap03 b {font-weight:bold; font-size:40px;}
	</style>
</head>
<body>
<div id="wrap">
    <jsp:include page="/WEB-INF/jsp/layout/user/header.jsp"/>
    <div id="container">
        <div id="content">
            <div class="listWrap" style="padding-top:0px;">
                <div class="login_tiitle">아이디/비밀번호 찾기</div>
                <!--<p class="fs_16 pd15">아래에 <b>정보</b>를 <b>입력</b>하시면 <b>가입 시 등록하신 E-mail로 정보</b>를 알려드립니다.</p> -->
				<div class="idpw_box mgr5" id="area-input1">
					<span class="idpw_title">아이디 찾기</span>
					<ul>
						<li class="idpw_text">성명</li>
						<li><input type="text" id="userNm1" required="required" title="성명은 필수 입니다."/></li>
					</ul>
					<ul>
						<li class="idpw_text">휴대폰번호</li>
						<li>
                                <input type="text" class="email" id="mobile1" name=""><label for="mobile1" class="sound_only">휴대폰번호 입력1</label> -
                                <input type="text" class="email" id="mobile2" name=""><label for="mobile2" class="sound_only">휴대폰번호 입력2</label> -
                                <input type="text" class="email" id="mobile3" name=""><label for="mobile3" class="sound_only">휴대폰번호 입력3</label>  
                                
                            </li>
					</ul>
					<button type="button" class="btn_find" onclick="fn_findId(); return false;">확인</button>
				</div>
				<div class="idpw_box mgl5" id="area-input2">
					<span class="idpw_title">비밀번호 찾기</span>
					<ul>
						<li class="idpw_text">아이디</li>
						<li><input type="text" id="loginId" required="required" title="아이디는 필수 입니다." placeholder="아이디"/></li>
					</ul>
					<ul>
						<li class="idpw_text">성명</li>
						<li><input type="text" id="userNm2" required="required" title="성명은 필수 입니다."/></li>
					</ul>
					<button type="button" class="btn_find" onclick="fn_findPw(); return false;">확인</button>
				</div>
				
				<div class="cert" style="display:none;">
					<%-- 
					<ul>
						<li><img src="${utcp.ctxPath}/resources/user/image/icon/icon_cert01.png" alt="이메일인증 아이콘" /></li>
						<li><span class="title">이메일인증</span><br>
						<span><input type="text" id="authNo" placeholder="인증번호 입력" class="authNoip"/>
						<label for="authNo" class="dp_b fc_orange tl pdt10">인증번호 보내기 클릭 후 이메일로 받으신 인증번호를 입력해 주세요.</label></span>
						</li>
					</ul>
					<a href="javascript:;" onclick="fn_sendAuthNoEmail();" id="btn-sendAuthNoEmail">
					<div class="btn_find">인증번호 보내기</div>
					</a>
					<a href="javascript:;" onclick="fn_confirmAuthNoEmail();" id="btn-confirmAuthNoEmail" style="display: none;">
					<div class="btn_find">인증하기</div>
					</a>
					 --%>
					 <ul>
						<li><img src="${utcp.ctxPath}/resources/user/image/icon/icon_cert01.png" alt="이메일인증 아이콘" /></li>
						<li><span class="title">휴대폰 인증</span><br>
						</li>
					</ul>
					<a href="javascript:;" onclick="fnMobileCheckPopup();" id="btn-sendAuthNoEmail">
					<div class="btn_find">인증하기</div>
					</a>
					<form name="authform" method="post">
					<input type="hidden" name="authEncData" id="authEncData" value="${param.authEncData }"/>
					</form>
					<%
						String domainNm = XmlBean.getConfigValue("domain.name");
						String sCheckplusUrl = domainNm + "/user/checkplus_main.do";      // 휴태폰본인인증 URL
					%>
					<script>
					function fn_checkplus_callback(authTypeNm,authUserNm,authBirth,sGender,authMobile,authEncData){
						var loginId = $('#loginId').val();
						$.ajax({
							type: 'post',
							data: {authEncData: authEncData, loginId: loginId},
							url: '${utcp.ctxPath}/user/findIdPwAuth.ajax',
							success: function(r){
								if(r.result == 1){
									$(".cert").hide();
									$("#newPw").show();
								}else{
									alert(r.msg);
								}
							}
						});
					}
					function fnMobileCheckPopup(onlyMobilep){
						var onlyMobile = '0';
						if(onlyMobilep){
							onlyMobile = onlyMobilep;
						}
						window.open('<%= sCheckplusUrl %>?onlyMobile='+onlyMobile, 'popupChk', 'width=500, height=550, top=100, left=100, fullscreen=no, menubar=no, status=no, toolbar=no, titlebar=yes, location=no, scrollbar=no');
					}
					</script>
				</div>
				
				<div class="idpw_box" id="newPw" style="display:none;">
					<input type="hidden" id="token" required="required" title="토큰은 필수 입니다." value=""/>
					<span class="idpw_title">비밀번호 재설정</span>
					<ul>
						<li class="idpw_text">비밀번호</li>
						<li><input type="password" id="pw1" required="required" title="비밀번호는 필수 입니다."/></li>
					</ul>
					<ul>
						<li class="idpw_text">비밀번호 확인</li>
						<li><input type="password" id="pw2" required="required" title="비밀번호확인은 필수 입니다."/></li>
					</ul>
					<div class="btn_find" style="cursor:pointer;" onclick="fn_newPw(); return false;">확인</div>
				</div>
				<div id="success" style="display:none;">
					<div class="apply mt80">
						<span class="ap03"><b>비밀번호가</b> 변경되었습니다.</span>
					</div>
					<div class="btn_step2">
						<a href="${utcp.ctxPath}/user/login.do" class="btn_step01">로그인하기</a>
						<a href="/" class="btn_step02">메인화면 이동</a>
					</div>
				</div>
            </div>
        </div> <!--// content -->
    </div> <!--// #container -->
    <jsp:include page="/WEB-INF/jsp/layout/user/footer.jsp"/>
</div> <!--// #wrap -->

<div class="remodal messagePop messagePop1" data-remodal-id="id_message1" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc">
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC">알림</p>
		</div>
		<div class="modal-body">
			<p class="messageTxt" id="id_str1">
				
			</p>
		</div>
		<div class="modal-footer">
			<div class="tc">
				<button data-remodal-action="cancel" class="remodal-confirm btn02 btn_green">확인</button>
			</div>
		</div>
	</div>
</div>

<div class="remodal messagePop messagePop2" data-remodal-id="id_message2" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc">
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC">알림</p>
		</div>
		<div class="modal-body">
			<p class="messageTxt" id="id_str2">
				
			</p>
		</div>
		<div class="modal-footer">
			<div class="tc">
				<button data-remodal-action="cancel" class="remodal-confirm btn02 btn_orange">확인</button>
			</div>
		</div>
	</div>
</div>



<!-- 회원가입 알림통합 -->
<div class="remodal messagePop1" id="vm-alert-join" data-remodal-id="md-alert-join" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc">
	<input type="hidden" id="md-name" value="#" />

	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC">알림</p>
		</div>
		<div class="modal-body">
			<p class="messageTxt" v-html="msg"></p>
		</div>
		<div class="modal-footer">
			<div class="tc">
				<button onclick="alert_join_confirm();" class="remodal-confirm btn02 btn_green" data-remodal-action="confirm">확인</button>
			</div>
		</div>
	</div>
</div>
<script>
	var vm_alert_join = new Vue({
		el : '#vm-alert-join',
		data : {
			msg : '',
			mdNm : '#',
			focusId : ''
		},
	});
	function alert_join_confirm() {
		if (vm_alert_join.mdNm != '#') {
			location.href = vm_alert_join.mdNm;
		}
		
			//onsole.log('a',vm_alert_join.focusId);
		if (vm_alert_join.focusId != '') {
			//onsole.log(vm_alert_join.focusId);
			setTimeout(function(){
				$('#'+vm_alert_join.focusId).focus();
			}, 1);
		}
	}
</script>
</body>
</html>