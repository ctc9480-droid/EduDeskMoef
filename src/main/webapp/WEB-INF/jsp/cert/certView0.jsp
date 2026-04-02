<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp" %>
<!DOCTYPE HTML>
<html lang="ko-KR">
<head>

<meta charset="UTF-8">
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

<!--// css //-->
<link rel="stylesheet" type="text/css" href="${utcp.ctxPath}/resources/admin/css/common.css">
<link rel="stylesheet" type="text/css" href="${utcp.ctxPath}/resources/admin/css/style.css">

<!--// font_css //-->
<link rel="stylesheet" type="text/css" href="${utcp.ctxPath}/resources/admin/css/font.css">
<link href="https://fonts.googleapis.com/css2?family=Nanum+Myeongjo:wght@400;700;800&display=swap" rel="stylesheet">
<link href="https://fonts.googleapis.com/css2?family=Playball&display=swap" rel="stylesheet">
    
    
    <!-- script.js -->
	<script src="${utcp.ctxPath}/resources/user/js/jquery-3.5.1.min.js"></script>
	<script src="${utcp.ctxPath}/resources/user/js/common.js"></script>
	
	<script src="${utcp.ctxPath}/resources/plugins/html2canvas/html2canvas.min.js"></script>
	<script src="${utcp.ctxPath}/resources/user/js/jspdf.min.js"></script>
	
<script>
alert('수료증이 없는 교육입니다.');
window.close();
</script>
</head>
<body>
</body>
</html>