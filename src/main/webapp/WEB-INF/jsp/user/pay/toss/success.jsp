<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<%@page import="com.educare.edu.comn.mapper.TableMapper"%>
<%@page import="ch.qos.logback.core.Context"%>
<%@page import="com.educare.edu.comn.vo.PayVO"%>
<%@page import="com.educare.edu.member.service.SessionUserInfoHelper"%>
<%@page import="com.educare.edu.comn.service.PayService"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@ page language="java" contentType="text/html;charset=utf-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<title>가맹점 결제 샘플페이지</title>


<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=yes, target-densitydpi=medium-dpi">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="-1">
<link href="${utcp.ctxPath}/pay/easypay/static/css/style.css" rel="stylesheet" type="text/css" id="cssLink" />
<script type="text/javascript">
	
</script>
</head>

<body>
	<div class="wrap">
		
	</div>
	<script>
	alert('결제가 완료되었습니다.');
	location.href='${utcp.ctxPath}/user/mypage/myLcRceptList.do';
	</script>
	
</body>
</html>
