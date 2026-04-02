<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp" %>
<!DOCTYPE HTML>
<html lang="ko" xml:lang=“ko” xmlns=“http://www.w3.org/1999/xhtml”>
<head>
	<meta charset="UTF-8">
	<meta name="title" content="<spring:eval expression="@prop['service.name']"/>">
	<meta name="description" content="<spring:eval expression="@prop['service.name']"/>">
	<meta name="keywords" content="<spring:eval expression="@prop['service.name']"/> 교육 관리">
	<meta name="author" content="<spring:eval expression="@prop['service.name']"/>">
	<meta http-equiv="refresh">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no">
	
	<title><spring:eval expression="@prop['service.name']"/></title>
	
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
	<link rel="manifest" href="${utcp.ctxPath}/resources/favicon/manifest.json">
	<meta name="msapplication-TileColor" content="#ffffff">
	<meta name="msapplication-TileImage" content="/resources/favicon/ms-icon-144x144.png">
	<meta name="theme-color" content="#ffffff">
	
	<!--// fontawesome -->	
	<link href="${utcp.ctxPath}/resources/fontawesome/css/all.css" rel="stylesheet">
	
</head>

<body class="bg_lightgray">
	<!--// wrapper //-->
	<div id="wrapper" class="cf">
		<div class="error">
			<div class="error_box cf">
				
				<div class="error_tx fl">
					<h1 class="fc_black pdb20 font_30">
						알립니다.
					</h1>
					<br/>
					${message}
					<br/>
				</div>
				<div class="error_btn cb fl">
					<a href="${utcp.ctxPath}/user/index.do" class="btn05 btn_black"><span><img src="${utcp.ctxPath}/resources/admin/images/icon_home.png"> 메인으로</span></a>
					<a href="${utcp.ctxPath}/admin/login.do" class="btn05 btn_blackl">
						<span><img src="${utcp.ctxPath}/resources/admin/images/icon_login1.png" class="back_ico1"><img src="${utcp.ctxPath}/resources/admin/images/icon_login2.png" class="back_ico2"> 관리자로그인</span>
					</a>
				</div>			
				<div class="error_img"><img src="${utcp.ctxPath}/resources/admin/images/error_img.png"></div>
			</div>
		</div>
	</div>
	<!--// wrapper //-->
</body>
</html>