<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp" %>
<!DOCTYPE HTML>
<html lang="ko" xml:lang=“ko” xmlns=“http://www.w3.org/1999/xhtml”>
<head>
	<meta charset="UTF-8">
	<meta name="title" content="<spring:eval expression="@prop['service.name']"/>">
	<meta name="description" content="<spring:eval expression="@prop['service.name']"/>">
	<meta name="keywords" content="<spring:eval expression="@prop['service.name']"/>">
	<meta name="author" content="<spring:eval expression="@prop['service.name']"/>">
	<meta http-equiv="refresh">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no">
	
	<title>${popupMap.title }</title>
	
	<!--// css //-->
	<link rel="stylesheet" type="text/css" href="${utcp.ctxPath}/resources/admin/css/common.css">
	<link rel="stylesheet" type="text/css" href="${utcp.ctxPath}/resources/admin/css/style.css">
	
	<!--// font_css //-->
	<link rel="stylesheet" type="text/css" href="${utcp.ctxPath}/resources/admin/css/font.css">
	
	<!--// js //-->
	<script language="javascript" src="${utcp.ctxPath}/resources/admin/js/jquery-3.5.1.min.js" type="text/javascript"></script>
	<script language="javascript" src="${utcp.ctxPath}/resources/admin/js/common.js" type="text/javascript"></script>
	<script language="javascript" src="${utcp.ctxPath}/resources/admin/js/prefixfree.min.js" crossorigin="anonymous"  type="text/javascript"></script>

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

	<script language="JavaScript">
		function closeWin() {
			self.close();
		}
	</script>
</head>
<body> 
<!-- POPUP start -->
<div id="divpop">
	<table class="mg0" cellpadding=0 cellspacing=0>
		<tr>
			<td class="pd0"> 
				${popupMap.content}
			</td>
		</tr>
		   
		<tr>
			<form name="notice_form">
				<td class="bg_black">
				   <p class="fl">
						<input type="checkbox" name="chkbox" value="checkbox"> 오늘 하루 이 창을 열지 않음
					</p>
				   <p class="fr">
						<a href="javascript:closeWin();">닫기</a>
					</p>
				</td> 
			</form>
		</tr>
	</table>
</div>

</body>
</html>