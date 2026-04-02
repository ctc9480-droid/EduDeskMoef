<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="false" %>
<html>
<head>
	<title>Sign In</title>
	
	<link rel="stylesheet" type="text/css" href="/resources/css/jquery-ui.css" />
	<link rel="stylesheet" type="text/css" href="/resources/css/style.css" />
	<link rel="stylesheet" type="text/css" href="/resources/css/ui.css" />
	<script type="text/javascript" src="/resources/js/plugin/jquery-3.2.1.min.js"></script>
	<script type="text/javascript" src="/resources/js/plugin/jquery-ui.min.js"></script>
	<script type="text/javascript" src="/resources/js/ui.js"></script>
	<script type="text/javascript" src="/resources/js/common.js"></script>
</head>
<body>
	<hidden id="contextPathHolder" th:data-contextPath="${httpServletRequest.getContextPath()}" />
	<h1>
		Service Provider(SP) 로그인 화면  
	</h1>
	
	<P>  <button onclick="javascript:doLogin();">QR Code 표출</button> </P>
	
	<div id="mask"></div>
	<div id="default-mask"></div>

	<div class="qr-down window">
		<div class="pop-title">
			<span>데모 검증서버(SP) QR Code 로그인</span>
			<div class="closepop" onclick="javascript:trStatusInfoStop();">
				<a href="#" class="close">[Close]</a>
			</div>
		</div>
		<div class="pop-cont">
			<div class="" style="margin-bottom: 20px;">모바일 공무원증 APP을 실행하시고, QR 촬영하여 로그인을 진행하세요.</div>
			<div class="pop-qr" style="float: none; margin-right: 0; padding-bottom: 0;">
				<img id="authQrImg" alt="QR Code" />
			</div>
			<div class="time" style="color: red; margin-bottom: 20px;" id="elapsedTime">1:59</div>
			<div class="">검증이 완료된 후 [OK]버튼을 눌러 로그인을 진행해주세요.</div>
			<div class="pop-bottom">
				<button class="default-pop-ok">OK</button>
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
	                        location.href = URL_ROOT + "/myPage.do";
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
