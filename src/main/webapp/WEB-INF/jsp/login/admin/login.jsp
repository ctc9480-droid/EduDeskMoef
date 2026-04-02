<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp" %>
<!DOCTYPE HTML>
<html lang="ko" xml:lang=“ko” xmlns=“http://www.w3.org/1999/xhtml”>
<head>
	<meta charset="UTF-8">
	<meta name="title" content="edudesk">
	<meta name="description" content="<spring:eval expression="@prop['service.name']"/> 관리자"/>
	<meta name="keywords" content="<spring:eval expression="@prop['service.name']"/> 관리자 교육 관리 교육"/>
	<meta name="author" content="edudesk">
	<meta http-equiv="refresh">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no">
	
	<title>관리자 로그인 | <spring:eval expression="@prop['service.name']"/></title>
	
	<!--// css //-->
	<link rel="stylesheet" type="text/css" href="${utcp.ctxPath}/resources/admin/css/common.css">
	<link rel="stylesheet" type="text/css" href="${utcp.ctxPath}/resources/admin/css/style.css">
	
	<!--// font_css //-->
	<link rel="stylesheet" type="text/css" href="${utcp.ctxPath}/resources/admin/css/font.css">

	<!--// js //-->
	<script language="javascript" src="${utcp.ctxPath}/resources/admin/js/jquery-3.5.1.min.js" type="text/javascript"></script>
	<script language="javascript" src="${utcp.ctxPath}/resources/admin/js/common.js" type="text/javascript"></script>
	<script language="javascript" src="${utcp.ctxPath}/resources/admin/js/prefixfree.min.js" crossorigin="anonymous"  type="text/javascript"></script>

	<!--// modal //-->
	<link rel="stylesheet" href="${utcp.ctxPath}/resources/admin/css/remodal.css">
	<link rel="stylesheet" href="${utcp.ctxPath}/resources/admin/css/remodal-default-theme.css">	
	<script language="javascript" src="${utcp.ctxPath}/resources/admin/js/remodal.js" type="text/javascript"></script>
	
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
	
	<!--// fontawesome -->	
	<link href="${utcp.ctxPath}/resources/fontawesome/css/all.css" rel="stylesheet">
	
	<script language="javascript" src="${utcp.ctxPath}/resources/common/js/util.js" type="text/javascript"></script>
	<script language="javascript" src="${utcp.ctxPath}/resources/common/js/enc/aes.js" type="text/javascript"></script>
	<script language="javascript" src="${utcp.ctxPath}/resources/common/js/enc/sha256.js" type="text/javascript"></script>
	
	
	<script type="text/javascript">
		jQuery(document).ready(function(){
			$('#userPw').on('keypress', function(e){
				if(e.keyCode == '13'){
					$("#loginBtn").click();
				}
			});
		});
		function login(){
			//enc
			var iv = UTIL.random(16);
			var key = UTIL.random(32);
			var cryptoArr = {
				iv : CryptoJS.enc.Utf8.parse(iv),
				padding : CryptoJS.pad.Pkcs7,
				mode: CryptoJS.mode.CBC
			}
			var userId = CryptoJS.AES.encrypt($("#i-userId").val(), CryptoJS.enc.Utf8.parse(key), cryptoArr).toString();
			var userPw = CryptoJS.AES.encrypt($("#i-userPw").val(), CryptoJS.enc.Utf8.parse(key), cryptoArr).toString();
			document.getElementById('encIv').value = iv;
			document.getElementById('encKey').value = key;
			document.getElementById('userId').value = userId;
			document.getElementById('userPw').value = userPw;
			document.getElementById('formlogin').submit();
		}
	</script>
</head>

<body class="bg_lightgray">

	<!--// wrapper //-->
	<div id="wrapper" class="cf">

		<div class="login">
			<div class="login_box">
				<div class="login_logo"><img src="${utcp.ctxPath}/resources/admin/images/logo_adm.png"></div>
				<div class="login_tx">관리자 로그인</div>
				<div class="login_form">
					<form id="formlogin" name="formlogin" method="post" action="${utcp.ctxPath}/admin/login.do">
						<input type="hidden" name="encIv" id="encIv" value=""/>
						<input type="hidden" name="encKey" id="encKey" value=""/>
						<input type="hidden" name="userId" id="userId" value=""/>
						<input type="hidden" name="userPw" id="userPw" value=""/>
						
						<input type="text" id="i-userId" placeholder="아이디">
						<input type="password" id="i-userPw" placeholder="패스워드">
						<a href="javascript:;" onclick="login()" id="loginBtn" class="btn01 btn_black"><span>로그인</span></a>
					</form>
				</div>
			</div>
		</div>
	</div>
	<!--// wrapper //-->
</body>
</html>