<%@ page import="com.gpki.gpkiapi.exception.GpkiApiException"%>
<%@ page import="java.util.Enumeration"%>
<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ page import="com.gpki.gpkiapi.cert.*" %>
<%@ page import="com.gpki.gpkiapi.cms.*" %>
<%@ page import="com.gpki.gpkiapi.util.*" %>
<%@ page import="com.dsjdf.jdf.Logger" %>
<%@ page import="java.net.URLDecoder" %>
<%@ include file="./gpkisecureweb.jsp"%>
<%
	X509Certificate cert      = null; 
	byte[]  signData          = null;
	byte[]  privatekey_random = null;
	String  signType          = null;
	String  subDN             = null;
	String  queryString       = "";
	boolean checkPrivateNum   = false;
	
	java.math.BigInteger b = new java.math.BigInteger("-1".getBytes()); 

	int message_type =  gpkirequest.getRequestMessageType();

	if( message_type == gpkirequest.ENCRYPTED_SIGNDATA || message_type == gpkirequest.LOGIN_ENVELOP_SIGN_DATA ||
		message_type == gpkirequest.ENVELOP_SIGNDATA || message_type == gpkirequest.SIGNED_DATA){

		cert              = gpkirequest.getSignerCert(); 
		subDN             = cert.getSubjectDN();
		b                 = cert.getSerialNumber();
		signData          = gpkirequest.getSignedData();
		privatekey_random = gpkirequest.getSignerRValue();
		signType          = gpkirequest.getSignType();
	}

	queryString = gpkirequest.getQueryString();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>GPKI 사용자용 표준보안API</title>
	<script type="text/javascript" src="../client/jquery-1.7.1.min.js"></script>
	<link rel="stylesheet" type="text/css" href="../css/style.css" />
	
	<script language="javascript" src="${utcp.ctxPath}/resources/common/js/util.js" type="text/javascript"></script>
	<script language="javascript" src="${utcp.ctxPath}/resources/common/js/enc/aes.js" type="text/javascript"></script>
</head>
<body>
	<form id="redirectForm" action="/user/mypage/updateGpkiInfo.do" method="post">
	    <input type="hidden" name="subDn" id="subDn" value="<%=subDN%>" />
<!-- 	    <input type="hidden" name="encIv" id="encIv" value="" /> -->
<!-- 	    <input type="hidden" name="encKey" id="encKey" value="" /> -->
	</form>
	
	<script type="text/javascript">
		
		// 페이지가 로드되면 자동으로 form submit
	    document.getElementById("redirectForm").submit();
	</script>
</body>
</html>
