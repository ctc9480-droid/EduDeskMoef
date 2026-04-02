<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<!DOCTYPE HTML>
<html lang="ko" xml:lang=“ko” xmlns=“http://www.w3.org/1999/xhtml”>
<head>
<meta charset="UTF-8">
<title>${popupMap.title }</title>
<meta http-equiv="Content-Type" content="text/html; utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no">
<meta http-equiv="imagetoolbar" content="no">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="keywords" content="<spring:eval expression="@prop['service.name']"/>, 관리자, 교육관리, 교육, 교육관리, 사이버교육센터, LMS, 교육, 온라인교육, 온라인강의">
<meta name="description" content="<spring:eval expression="@prop['service.name']"/> 관리자">
<meta name="author" content="<spring:eval expression="@prop['service.name']"/>">
<meta property="og:type" content="website">
<meta property="og:title" content="<spring:eval expression="@prop['service.name']"/>">
<meta property="og:description" content="<spring:eval expression="@prop['service.name']"/>">
<meta name="format-detection" content="telephone=no">
<meta http-equiv="refresh">

<!--// css //-->
<link rel="stylesheet" type="text/css" href="${utcp.ctxPath}/resources/admin/css/common.css">
<link rel="stylesheet" type="text/css" href="${utcp.ctxPath}/resources/admin/css/style.css">

<!--// font_css //-->
<link rel="stylesheet" type="text/css" href="${utcp.ctxPath}/resources/admin/css/font.css">

<!--// js //-->
<script language="javascript" src="${utcp.ctxPath}/resources/admin/js/jquery-3.5.1.min.js" type="text/javascript"></script>
<script language="javascript" src="${utcp.ctxPath}/resources/admin/js/common.js" type="text/javascript"></script>
<script language="javascript" src="${utcp.ctxPath}/resources/admin/js/prefixfree.min.js" crossorigin="anonymous" type="text/javascript"></script>

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
<link rel="icon" type="image/png" sizes="192x192" href="${utcp.ctxPath}/resources/favicon/android-icon-192x192.png">
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
<body>
	<!-- POPUP start -->
	<div id="divpop">
		<table class="mg0" cellpadding=0 cellspacing=0 style="width:100%;">
			<tr>
				<td class="pd0" nowrap>${popupMap.content}</td>
			</tr>

			<tr>
					<td class="bg_black">
						<p class="fl">
							<input type="checkbox"  id="chkbox" value="Y"> <label for="chkbox">오늘 하루 이 창을 열지 않음</label>
						</p>
						<p class="fr">
							<a href="javascript:closePopup(${popupMap.idx });">닫기</a>
						</p>
					</td>
			</tr>
		</table>
	</div>

<script language="JavaScript">
	function setCookie(name, value, expiredays) {
		var date = new Date();
		date.setDate(date.getDate() + expiredays);
		document.cookie = escape(name) + "=" + escape(value) + ";path=/; expires="
				+ date.toUTCString()+";";
	}

	function closePopup(idx) {
		if (document.getElementById("chkbox").checked) {
			setCookie("popup_"+idx, "N", 1);
		}
		self.close();
	}
	document.all['divpop'].style.visibility = "visible";//기본설젇이 hidden이네...,231221
	
	var pHeight='${popupMap.height}'*1;
	var pWidth='${popupMap.width}'*1;
	var pTop='${popupMap.posY}';
	var pLeft='${popupMap.posX}'*1;
	//window.resizeTo(pWidth,pHeight);
	
	if(screen.width>window.screenX){
		window.moveTo(pLeft,pTop);
	}else{
		window.moveTo(screen.width+pLeft,pTop);
	}
	
	<%-- // 팝업 창 크기를 HTML 크기에 맞추어 자동으로 크기를 조정하는 함수. --%>
		$(window).load(function() {
	if(pHeight == 0 && pWidth == 0){

		    var strWidth;
		    var strHeight;

		    if ( window.innerWidth && window.innerHeight && window.outerWidth && window.outerHeight ) {
		        strWidth = $('#divpop').outerWidth() + (window.outerWidth - window.innerWidth);
		        strHeight = $('#divpop').outerHeight() + (window.outerHeight - window.innerHeight);
		    }

		    else {
		        var strDocumentWidth = $(document).outerWidth();
		        var strDocumentHeight = $(document).outerHeight();
		        window.resizeTo ( strDocumentWidth, strDocumentHeight );

		        var strMenuWidth = strDocumentWidth - $(window).width();
		        var strMenuHeight = strDocumentHeight - $(window).height();

		        strWidth = $('#divpop').outerWidth() + strMenuWidth;
		        strHeight = $('#divpop').outerHeight() + strMenuHeight;
		    }

			//resize
			window.resizeTo( strWidth, strHeight );

	}
		});
	



</script>
</body>
</html>