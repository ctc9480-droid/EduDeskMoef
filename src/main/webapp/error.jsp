<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp" %>
<%
response.setStatus(200);
Exception exception = (Exception)request.getAttribute("javax.servlet.error.exception"); 
if(exception != null){
	exception.printStackTrace();
}
%>
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
	<META NAME = "GOOGLEBOT" CONTENT= "NOINDEX, NOFOLLOW">
	<title><spring:eval expression="@prop['service.name']"/></title>
	
	<!--// css //-->
	<link rel="stylesheet" type="text/css" href="<spring:eval expression="@prop['lnc.app.contextpath']"/>/resources/admin/css/common.css">
	<link rel="stylesheet" type="text/css" href="<spring:eval expression="@prop['lnc.app.contextpath']"/>/resources/admin/css/style.css">
	
	<!--// font_css //-->
	<link rel="stylesheet" type="text/css" href="<spring:eval expression="@prop['lnc.app.contextpath']"/>/resources/admin/css/font.css">

	
	<!--// js //-->
	<script language="javascript" src="<spring:eval expression="@prop['lnc.app.contextpath']"/>/resources/admin/js/jquery-3.5.1.min.js" type="text/javascript"></script>
	<script language="javascript" src="<spring:eval expression="@prop['lnc.app.contextpath']"/>/resources/admin/js/common.js" type="text/javascript"></script>
	<script language="javascript" src="<spring:eval expression="@prop['lnc.app.contextpath']"/>/resources/admin/js/prefixfree.min.js" crossorigin="anonymous"  type="text/javascript"></script>

	<!--// modal //-->
	<link rel="stylesheet" href="<spring:eval expression="@prop['lnc.app.contextpath']"/>/resources/admin/css/remodal.css">
	<link rel="stylesheet" href="<spring:eval expression="@prop['lnc.app.contextpath']"/>/resources/admin/css/remodal-default-theme.css">	
	<script language="javascript" src="<spring:eval expression="@prop['lnc.app.contextpath']"/>/resources/admin/js/remodal.js" type="text/javascript"></script>
	
	<!--// favicon -->
	<link rel="apple-touch-icon" sizes="57x57" href="<spring:eval expression="@prop['lnc.app.contextpath']"/>/resources/favicon/apple-icon-57x57.png">
	<link rel="apple-touch-icon" sizes="60x60" href="<spring:eval expression="@prop['lnc.app.contextpath']"/>/resources/favicon/apple-icon-60x60.png">
	<link rel="apple-touch-icon" sizes="72x72" href="<spring:eval expression="@prop['lnc.app.contextpath']"/>/resources/favicon/apple-icon-72x72.png">
	<link rel="apple-touch-icon" sizes="76x76" href="<spring:eval expression="@prop['lnc.app.contextpath']"/>/resources/favicon/apple-icon-76x76.png">
	<link rel="apple-touch-icon" sizes="114x114" href="<spring:eval expression="@prop['lnc.app.contextpath']"/>/resources/favicon/apple-icon-114x114.png">
	<link rel="apple-touch-icon" sizes="120x120" href="<spring:eval expression="@prop['lnc.app.contextpath']"/>/resources/favicon/apple-icon-120x120.png">
	<link rel="apple-touch-icon" sizes="144x144" href="<spring:eval expression="@prop['lnc.app.contextpath']"/>/resources/favicon/apple-icon-144x144.png">
	<link rel="apple-touch-icon" sizes="152x152" href="<spring:eval expression="@prop['lnc.app.contextpath']"/>/resources/favicon/apple-icon-152x152.png">
	<link rel="apple-touch-icon" sizes="180x180" href="<spring:eval expression="@prop['lnc.app.contextpath']"/>/resources/favicon/apple-icon-180x180.png">
	<link rel="icon" type="image/png" sizes="192x192"  href="<spring:eval expression="@prop['lnc.app.contextpath']"/>/resources/favicon/android-icon-192x192.png">
	<link rel="icon" type="image/png" sizes="32x32" href="<spring:eval expression="@prop['lnc.app.contextpath']"/>/resources/favicon/favicon-32x32.png">
	<link rel="icon" type="image/png" sizes="96x96" href="<spring:eval expression="@prop['lnc.app.contextpath']"/>/resources/favicon/favicon-96x96.png">
	<link rel="icon" type="image/png" sizes="16x16" href="<spring:eval expression="@prop['lnc.app.contextpath']"/>/resources/favicon/favicon-16x16.png">
	<link rel="manifest" href="<spring:eval expression="@prop['lnc.app.contextpath']"/>/resources/favicon/manifest.json">
	<meta name="msapplication-TileColor" content="#ffffff">
	<meta name="msapplication-TileImage" content="/resources/favicon/ms-icon-144x144.png">
	<meta name="theme-color" content="#ffffff">
	
	<!--// fontawesome -->	
	<link href="<spring:eval expression="@prop['lnc.app.contextPath']"/>/resources/fontawesome/css/all.css" rel="stylesheet">
	
</head>
<body class="bg_lightgray">
	<!--// wrapper //-->
	<div id="wrapper" class="cf">

		<div class="error">
			<div class="error_box cf">
				
				<div class="error_tx fl">
					<h1 class="fc_black pdb20 font_30">
						ERROR1
					</h1>
					요청하신 페이지를 처리 중에 오류가 발생했습니다.<br>
					서비스 이용에 불편을 드려 죄송합니다.<br>
					입력하신 주소가 정확한지 확인 후 다시 시도해 주시기 바랍니다.
				</div>
				<div class="error_btn cb fl">
					<a href="<spring:eval expression="@prop['lnc.app.contextpath']"/>/index.do" class="btn05 btn_black"><span>
					<img src="<spring:eval expression="@prop['lnc.app.contextpath']"/>/resources/admin/images/icon_home.png" alt="메인으로 집모양 아이콘" /> 메인으로</span></a>
					<a href="javascript:history.back(-1);" class="btn05 btn_blackl">
						<span>
						<img src="<spring:eval expression="@prop['lnc.app.contextpath']"/>/resources/admin/images/icon_back1.png" class="back_ico1" alt="이전으로 검정 좌측 화살표 아이콘" />
						<img src="<spring:eval expression="@prop['lnc.app.contextpath']"/>/resources/admin/images/icon_back2.png" class="back_ico2" alt="이전으로 하얀 좌측 화살표 아이콘" /> 이전으로</span>
					</a>
				</div>
				
				<div class="error_img"><img src="<spring:eval expression="@prop['lnc.app.contextpath']"/>/resources/admin/images/error_img.png" alt="에러 아이콘" /></div>
			</div>
		</div>

	</div>
	<!--// wrapper //-->
</body>
</html>



