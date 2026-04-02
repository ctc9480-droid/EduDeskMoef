<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp" %>
<!DOCTYPE HTML>
<html lang="ko" xml:lang=“ko” xmlns=“http://www.w3.org/1999/xhtml”>
<head>
	<meta charset="UTF-8">
	<meta name="title" content="<spring:eval expression="@prop['service.name']"/>">
	<meta name="description" content="<spring:eval expression="@prop['service.name']"/> 관리자">
	<meta name="keywords" content="<spring:eval expression="@prop['service.name']"/> 관리자 교육 관리 교육">
	<meta name="author" content="<spring:eval expression="@prop['service.name']"/>">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no">
	
	<title>관리자 | <spring:eval expression="@prop['service.name']"/></title>
	
	<!--// css //
	<link rel="stylesheet" type="text/css" href="${utcp.ctxPath}/resources/admin/css/common.css">
	<link rel="stylesheet" type="text/css" href="${utcp.ctxPath}/resources/admin/css/style.css">
	-->
	<link rel="stylesheet" type="text/css" href="${utcp.ctxPath}/resources/admin/css/common.css?1">
	<link rel="stylesheet" type="text/css" href="${utcp.ctxPath}/resources/admin/css/style.css">
	
	<!--// font_css //-->
	<link rel="stylesheet" type="text/css" href="${utcp.ctxPath}/resources/admin/css/font.css">
	
	<!--// calendar css -->
	<link rel="stylesheet" type="text/css" href="${utcp.ctxPath}/resources/calendar/dist/css/pignose.calendar.min.css">
	
	<!--// js //
	-->
	<script language="javascript" src="${utcp.ctxPath}/resources/admin/js/jquery-3.5.1.min.js" type="text/javascript"></script>
	<script language="javascript" src="${utcp.ctxPath}/resources/admin/js/jquery.validate.min.js" type="text/javascript"></script>
	<script language="javascript" src="${utcp.ctxPath}/resources/admin/js/common.js" type="text/javascript"></script>
	<script type="text/javascript" src="${utcp.ctxPath}/resources/common/js/util.js"></script>
	
	<!-- css디버깅 하기위해 주석처리 운영에는 주석풀자
	<script language="javascript" src="${utcp.ctxPath}/resources/admin/js/prefixfree.min.js" crossorigin="anonymous"  type="text/javascript"></script>
	 -->
	
	<!--// calendar js -->
	<script src="${utcp.ctxPath}/resources/calendar/dist/js/pignose.calendar.full.min.js"></script>
	
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
	
	<!-- pickadate.js -->
    <link rel="stylesheet" href="${utcp.ctxPath}/resources/plugins/pickadate/default.css">
    <link rel="stylesheet" href="${utcp.ctxPath}/resources/plugins/pickadate/default.date.css">
    <script src="${utcp.ctxPath}/resources/plugins/pickadate/picker.js"></script>
    <script src="${utcp.ctxPath}/resources/plugins/pickadate/picker.date.js"></script>
    <script src="${utcp.ctxPath}/resources/plugins/pickadate/legacy.js"></script>    
	
	<!-- vue -->
	<script type="text/javascript" src="${utcp.ctxPath}/resources/plugins/vue/vue.js" ></script>
	<script type="text/javascript" src="${utcp.ctxPath}/resources/plugins/moment/moment.min.js"></script>
	<script type="text/javascript" src="${utcp.ctxPath}/resources/plugins/moment/moment-with-locales.min.js"></script>
	<script type="text/javascript" src="${utcp.ctxPath}/resources/plugins/vue/vue-custom.js"></script>
	<script>
	moment.locale('ko');
	//lang.hl='ko';
	</script>
	
	<!-- 동영상 재생 hls 
	<script src="https://cdn.jsdelivr.net/npm/hls.js@latest"></script>
	-->
	
	<!-- dropzone -->
	<script src="${utcp.ctxPath}/resources/plugins/dropzone/dropzone.js"></script>
	<link rel="stylesheet" href="${utcp.ctxPath}/resources/plugins/dropzone/dropzone.css">
	
	<script>
		function fn_adminLoginPage(){
			location.href = "/admin/login.do";
		}
	</script>
	
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
	
	<!-- jquery ui -->
	<link rel="stylesheet" href="${utcp.ctxPath }/resources/plugins/jquery/jquery-ui.css">
	<script src="${utcp.ctxPath }/resources/plugins/jquery/jquery-ui.js"></script>
	<script>
	// Datepicker 한글화 설정
    $.datepicker.setDefaults({
        closeText: '닫기',
        prevText: '이전',
        nextText: '다음',
        currentText: '오늘',
        monthNames: ['1월','2월','3월','4월','5월','6월',
                     '7월','8월','9월','10월','11월','12월'],
        monthNamesShort: ['1월','2월','3월','4월','5월','6월',
                          '7월','8월','9월','10월','11월','12월'],
        dayNames: ['일요일','월요일','화요일','수요일','목요일','금요일','토요일'],
        dayNamesShort: ['일','월','화','수','목','금','토'],
        dayNamesMin: ['일','월','화','수','목','금','토'],
        weekHeader: '주',
        dateFormat: 'yy-mm-dd',
        firstDay: 0,
        isRTL: false,
        showMonthAfterYear: true,
        yearSuffix: '년',
        changeYear: true,
        changeMonth: true,
    });
	</script>
	<style>
	.ui-datepicker { z-index: 99999 !important; }
	</style>
	
</head>
<body>
	<!--// wrapper s //-->
	<div id="wrapper" class="cf">
		<tiles:insertAttribute name="menu"/>
		<!--// cont_wrap //-->
		<div class="cont_wrap">
			<tiles:insertAttribute name="header"/>
			<tiles:insertAttribute name="subBox"/>
			<tiles:insertAttribute name="body" />
			<%-- tiles:insertAttribute name="footer" /--%>
		</div>
		<!--// cont_wrap //-->
		
		<!--// popup_message //-->
		<div class="remodal messagePop1" data-remodal-id="messagePop" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc">
			<div class="modal-content">
				<div class="modal-header">
					<p class="tit alignC">알림</p>
				</div>
				<div class="modal-body">
					<p class="messageTxt">
						알립니다.
					</p>
				</div>
				<div class="modal-footer">
					<div class="tc">
						<button data-remodal-action="cancel" class="remodal-confirm btn02 btn_green">확인</button>
					</div>
				</div>
			</div>
		</div>
		<!--// popup_message //-->
		
		<!--// auth_message //-->
		<div class="remodal messagePop2" data-remodal-id="authMessage" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc">
			<div class="modal-content">
				<div class="modal-header">
					<p class="tit alignC">알림</p>
				</div>
				<div class="modal-body">
					<p class="messageTxt">
						로그인 후 이용하세요.
					</p>
				</div>
				<div class="modal-footer">
					<div class="tc">
						<button onclick="fn_adminLoginPage(); return false;" class="remodal-confirm btn02 btn_orange">확인</button>
					</div>
				</div>
			</div>
		</div>
		<!--// auth_message //-->
			
	</div>
	<!--// wrapper e //-->
<tiles:insertAttribute name="modal"/>
<jsp:include page="/WEB-INF/jsp/layout/comm_modal.jsp" flush="false" />
</body>
</html>
