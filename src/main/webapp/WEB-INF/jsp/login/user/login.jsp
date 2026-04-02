<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp" %>

<%-- <%@ include file="/gpkisecureweb/jsp/gpkisecureweb.jsp" %> --%>

<%
	String challenge = "";//gpkiresponse.getChallenge();
	String sessionid = "";//gpkirequest.getSession().getId();
	String url = javax.servlet.http.HttpUtils.getRequestURL(request).toString();
	session.setAttribute("currentpage",url);
	
	System.out.println("challenge:"+challenge);
	System.out.println("sessionid:"+sessionid);
	System.out.println("url:"+url);
%>

<!DOCTYPE HTML>
<html lang="ko" xml:lang=“ko” xmlns=“http://www.w3.org/1999/xhtml”>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no">
    <meta http-equiv="imagetoolbar" content="no">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="keywords" content="<spring:eval expression="@prop['service.name']"/>">
    <meta name="description" content="<spring:eval expression="@prop['service.name']"/>">
    <meta name="author" content="<spring:eval expression="@prop['service.name']"/>">
    <meta property="og:type" content="website">
    <meta property="og:title" content="<spring:eval expression="@prop['service.name']"/>">
    <meta property="og:description" content="<spring:eval expression="@prop['service.name']"/>">
    <meta name="format-detection" content="telephone=no">

    <title><spring:eval expression="@prop['service.name']"/></title>

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
	
	<script language="javascript" src="${utcp.ctxPath}/resources/common/js/util.js" type="text/javascript"></script>
	<script language="javascript" src="${utcp.ctxPath}/resources/common/js/enc/aes.js" type="text/javascript"></script>
	<script language="javascript" src="${utcp.ctxPath}/resources/common/js/enc/sha256.js" type="text/javascript"></script>
	
	<link rel="stylesheet" type="text/css" href="${utcp.ctxPath}/resources/plugins/jquery/jquery.loading-indicator.css" /> 
	<script type="text/javascript" src="${utcp.ctxPath}/resources/plugins/jquery/jquery.loading-indicator.min.js"></script>
	<script>
	var homeLoader;
	$( document ).ready(function() {
		homeLoader = $('body').loadingIndicator({
			useImage: false
		}).data("loadingIndicator");
		homeLoader.hide();
		$('.loading-indicator-wrapper').css('position','fixed');
	});
	$( document ).ajaxSend(function(evt, request, settings) {
		if(typeof homeLoader !== 'undefined'){
			homeLoader.show();
		}
			
	});
	$( document ).ajaxStop(function() {
		if(typeof homeLoader !== 'undefined'){
			homeLoader.hide();
		}
	});
	</script>
	
	<script type="text/javascript">
		$(document).ready(function(){
			
			$('#userPw').on('keypress', function(e){
				if(e.keyCode == '13'){
					fn_login();
				}
			});
			 
		    // 저장된 쿠키값을 가져와서 ID 칸에 넣어준다. 없으면 공백으로 들어감.
		    var key = getCookie("key");
		    $("#userId").val(key); 
		     
		    if($("#userId").val() != ""){ // 그 전에 ID를 저장해서 처음 페이지 로딩 시, 입력 칸에 저장된 ID가 표시된 상태라면,
		        $("#idSaveCheck").attr("checked", true); // ID 저장하기를 체크 상태로 두기.
		    }
		     
		    $("#idSaveCheck").change(function(){ // 체크박스에 변화가 있다면,
		        if($("#idSaveCheck").is(":checked")){ // ID 저장하기 체크했을 때,
		            setCookie("key", $("#userId").val(), 7); // 7일 동안 쿠키 보관
		        }else{ // ID 저장하기 체크 해제 시,
		            deleteCookie("key");
		        }
		    });
		     
		    // ID 저장하기를 체크한 상태에서 ID를 입력하는 경우, 이럴 때도 쿠키 저장.
		    $("#userId").keyup(function(){ // ID 입력 칸에 ID를 입력할 때,
		        if($("#idSaveCheck").is(":checked")){ // ID 저장하기를 체크한 상태라면,
		            setCookie("key", $("#userId").val(), 7); // 7일 동안 쿠키 보관
		        }
		    });
		});
		 
		function setCookie(cookieName, value, exdays){
		    var exdate = new Date();
		    exdate.setDate(exdate.getDate() + exdays);
		    var cookieValue = escape(value) + ((exdays==null) ? "" : "; expires=" + exdate.toGMTString());
		    document.cookie = cookieName + "=" + cookieValue;
		}
		 
		function deleteCookie(cookieName){
		    var expireDate = new Date();
		    expireDate.setDate(expireDate.getDate() - 1);
		    document.cookie = cookieName + "= " + "; expires=" + expireDate.toGMTString();
		}
		 
		function getCookie(cookieName) {
		    cookieName = cookieName + '=';
		    var cookieData = document.cookie;
		    var start = cookieData.indexOf(cookieName);
		    var cookieValue = '';
		    if(start != -1){
		        start += cookieName.length;
		        var end = cookieData.indexOf(';', start);
		        if(end == -1)end = cookieData.length;
		        cookieValue = cookieData.substring(start, end);
		    }
		    return unescape(cookieValue);
		}
		
		function fn_login(){
			
			var userId = $("#userId").val();
			var userPw = $("#userPw").val();
			var userMemLvl = $('#userMemLvl').val();

			if(userId == ""){
				$("#messageStr").html("아이디를 입력하세요.");
				location.href="#message";
			    return;
			}
			     
			if(userPw == ""){
				$("#messageStr").html("비밀번호를 입력하세요.");
				location.href="#message";
		        return;
		    }
		    
			
			//enc
			var iv = UTIL.random(16);
			var key = UTIL.random(32);
			var cryptoArr = {
				iv : CryptoJS.enc.Utf8.parse(iv),
				padding : CryptoJS.pad.Pkcs7,
				mode: CryptoJS.mode.CBC
			}
			userId = CryptoJS.AES.encrypt(userId, CryptoJS.enc.Utf8.parse(key), cryptoArr).toString();
			userPw = CryptoJS.AES.encrypt(userPw, CryptoJS.enc.Utf8.parse(key), cryptoArr).toString();
			
		 	$.ajax({
				url: "${utcp.ctxPath}/user/login.json",
				type: "post",
				data: {
					"loginId" : userId,
					"loginPw" : userPw,
					"userMemLvl" : userMemLvl,
					"encIv": iv,
					"encKey": key,
				},
				cache: false,
				async: true,
				success: function(r) {
					if(r.result > 0 ){
						if(r.msg != ''){
							//alert(r.msg);
						}
						if('${param.rtnUri}'!=''){
							location.href='${utcp.ctxPath}${param.rtnUri}&data=${param.qrData}';
						}else{
							location.href='${utcp.ctxPath}/';
						}
					}else if(r.result == -9){
						alert('통신오류로 인해 로그인이 실패하였습니다. 다시 시도하시기 바랍니다.');
					}else{
						//$("#messageStr").html(r.msg);
						//location.href="#message";
						alert(r.msg);
					}
				}
			});
		}
	</script>
	
<script>
function fn_loginTab(key) {
	console.log(key);
	$('ul.login_tab li').removeClass('active');
	if (key == 1) {
		$('ul.login_tab li').eq(0).addClass('active');
		$('#userMemLvl').val('9');
	} else {
		$('ul.login_tab li').eq(1).addClass('active');
		$('#userMemLvl').val('8');
	}
}
</script>

	<script type="text/javascript" src="${utcp.ctxPath}/gpkisecureweb/client/GPKIWeb/js/ext/jquery.blockUI.js"></script>
	
	<script type="text/javascript" src="${utcp.ctxPath}/gpkisecureweb/client/GPKIWeb/js/GPKIWeb_Config.js"></script>
	<script type="text/javascript" src="${utcp.ctxPath}/gpkisecureweb/client/GPKIWeb/js/ext/GPKI_Config.js"></script>
	
	<script type="text/javascript" src="${utcp.ctxPath}/gpkisecureweb/client/gpkijs_1.2.1.5.min.js" id="DSgpkijs"></script>
	<script type="text/javascript" src="${utcp.ctxPath}/gpkisecureweb/client/GenerateContent.js" id="DSGenInterface"></script>
	<script type="text/javascript" src="${utcp.ctxPath}/gpkisecureweb/client/GPKISecureWebJS.js"></script>
	<script type="text/javascript" src="${utcp.ctxPath}/gpkisecureweb/client/GPKIJS_Crypto.js" id="DSGPKIJS_Crypto"></script>
	
	<script type="text/javascript" src="${utcp.ctxPath}/gpkisecureweb/client/var.js"></script>
	<script type="text/javascript" src="${utcp.ctxPath}/gpkisecureweb/client/GPKISecureWebNP2.js"></script>
	
<!-- 	<link rel="stylesheet" type="text/css" href="/resources/css/jquery-ui.css" /> -->
<!-- 	<link rel="stylesheet" type="text/css" href="/resources/css/style.css" /> -->
	<link rel="stylesheet" type="text/css" href="/resources/css/ui.css" />
<!-- 	<script type="text/javascript" src="/resources/js/plugin/jquery-3.2.1.min.js"></script> -->
<!-- 	<script type="text/javascript" src="/resources/js/plugin/jquery-ui.min.js"></script> -->
	<script type="text/javascript" src="/resources/js/ui.js"></script>
<!-- 	<script type="text/javascript" src="/resources/js/common.js"></script> -->
</head>
<body>
<form action="/user/loginBySubDn.do" method="post" name="popForm">
    <input disabled type="hidden" name="challenge" value="<%=challenge%>" />
	<input type="hidden" name="sessionid" id="sessionid" value="<%=sessionid%>" />
</form>								

<hidden id="contextPathHolder" th:data-contextPath="${httpServletRequest.getContextPath()}" />

<div id="mask"></div>
<div id="default-mask"></div>

<div class="qr-down window log-pop-box">
	<div class="pop-title">
		<span>모바일 공무원증 로그인</span>
<!-- 		<div class="closepop" onclick="javascript:trStatusInfoStop();"> -->
<!-- 			<a href="#" class="close">[Close]</a> -->
<!-- 		</div> -->
	</div>
	<div class="pop-cont">
		<div class="pop-cont-tit" style="margin-bottom: 10px;">모바일 공무원증 앱(App) 실행 후</div>
		<div class="pop-cont-tit">[QR코드 스캔] 버튼을 눌러 아래의 QR코드를 스캔하세요.</div>
		<div class="pop-qr" style="float: none; margin-right: 0; padding-bottom: 0;">
			<img id="authQrImg" alt="QR Code" />
		</div>
		<div class="time-wrap" style="margin-bottom: 10px;">
			<span class="time-tit">유효 시간</span>
			<span class="time" style="color:#3b8fc3;" id="elapsedTime">1:59</span>
		</div>
<!-- 		<div class="time-wrap-txt">검증이 완료된 후 [OK]버튼을 눌러 로그인을 진행해주세요.</div> -->
		<div class="pop-bottom" onclick="javascript:trStatusInfoStop();">
			<button class="default-pop-ok close">취소</button>
		</div>
		<div class="pop-qr-txt">모바일 공무원증 콜센터 1811-8800</div>
	</div>
</div>

<div id="wrap">
    <jsp:include page="/WEB-INF/jsp/layout/user/header.jsp"/>
    <div id="container">
        <div id="content">
            <div class="listWrap pdt0">
                <div class="login_tiitle">로그인</div>
                
					<div class="login_tab_onoff">
						<!-- <ul class="login_tab">
							<li class="active">
								<p><a href="javascript:fn_loginTab(1);">일반로그인</a></p>
							</li>
							<li>
								<p><a href="javascript:fn_loginTab(2);">강사로그인</a></p>
							</li>
						</ul> -->

						<!--// bd_tab_con //-->
						<div class="login_tab_con">
							<!--// tab_con1 //-->
							<div class="cont cf">
								<div class="login_box login_box01">
									<input type="hidden" id="userMemLvl" value=""/>
									<span>회원 로그인</span>
									<input type="text" id="userId" name="userId" placeholder="아이디" class="log_input"/>
									<label for="userId" class="sound_only">아이디(이메일) 입력</label>
									<input type="password" id="userPw" name="userPw" placeholder="비밀번호" class="log_input"/>
									<label for="userPw" class="sound_only">비밀번호 입력</label>
									<div class="login_ck login_id_ck">
										<input type="checkbox" id="idSaveCheck"> 
										<label for="idSaveCheck">아이디 저장</label> 
									</div>
									
									<button class="btn_login btn_login01" onclick="fn_login(); return false;" >로그인</button>
									<div class="login_ck login_info_ck">
										<!-- 
										-->
										<a href="${utcp.ctxPath }/user/findIdPw.do" class="find_info">아이디/비밀번호 찾기</a> 
										<a href="${utcp.ctxPath}/user/join.do" class="join_go">회원가입</a>  
										
										<!-- 
										-->
										<%-- 
										--%>
									</div>
								</div>
								<div class="login_box login_box02">
									<input type="hidden" id="userMemLvl" value=""/>
									<span>인증 로그인</span>
									<button class="btn_login btn_login02" onclick="Login(this, popForm, false);" >GPKI 인증서 로그인</button>
									<button class="btn_login btn_login03" onclick="doLogin();" >모바일공무원증</button>
								</div>
							</div>
							<!--// tab_con1 //-->

						</div>
						<!--// bd_tab_con //-->
					</div>

            </div>
        </div> <!--// content -->
    </div> <!--// #container -->
    <jsp:include page="/WEB-INF/jsp/layout/user/footer.jsp"/>
</div> <!--// #wrap -->

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

</body>

<script type="text/javascript">
	/* javascript 파일에서 root 경로를 사용하기 위한 전역변수 */
	var URL_ROOT = $('#contextPathHolder').attr('data-contextPath') ? $('#contextPathHolder').attr('data-contextPath') : '';

	function doLogin(formId) {
	    popOn("qr-down");
	    $.ajax({
	        url: (URL_ROOT + "/spnoneprofile/qrVerifyReq.do"),
	        type: "POST",
	        dataType: "json",
	        async: true,
	        success: function(data) {
	            if (data["result"]) {
	                $("#authQrImg").attr("src", data["qrData"]);
	                trStatusInfo(119, data["csrfToken"]);

	            } else {
	                popOff();
	                defaultPopOn(decodeURIComponent(data["resultMsg"]));
	            }
	        }
	    });
	}

	var $$timerResultConfirm;

	function trStatusInfo(expireTime, csrfToken) {
	    $('#mask').one("click", function() {
	        trStatusInfoStop();
	    });

	    var obj = new Object();
	    obj["csrfToken"] = csrfToken;

	    $$timerResultConfirm = setInterval(function() {
	        if (expireTime != 0) {
	            expireTime -= 1;
	        }
	        var elapsedMin = Math.floor(expireTime / 60);
	        var elapsedSec = Math.floor(expireTime % 60);
	        $("#elapsedTime").html(elapsedMin + ":" + (elapsedSec > 9 ? elapsedSec : ("0" + elapsedSec)));

	        $.ajax({
	            url: (URL_ROOT + "/spnoneprofile/txCheck.do"),
	            type: "POST",
	            data: obj,
	            dataType: "json",
	            async: true,

	            success: function(data) {
	                if (data["result"]) {

	                    if (data["txCompleteCode"] == "COMPLETE") {
	                        popOff();
	                        trStatusInfoStop();
	                        location.href = URL_ROOT + "/user/loginBySubDn2.do";
	                    }

	                } else {
	                    if (data.txCompleteCode == "TIMEOUT") {
	                        defaultPopOn("Authentication timeout.");
	                    } else {
	                        defaultPopOn(data.txCompleteCode);
	                    }
	                    popOff();
	                    trStatusInfoStop();
	                }
	            }
	        });

	    }, 1000);
	}

	function trStatusInfoStop() {
	    clearInterval($$timerResultConfirm);
	}

	$(document).ready(function() {
	    if ($('#default-pop').length != 0) {
	        popOn("default-pop");
	    }
	});
</script>

</html>