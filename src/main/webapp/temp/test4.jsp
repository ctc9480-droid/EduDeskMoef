
<%@page import="com.educare.edu.comn.vo.ResultVO"%>
<%@page import="org.springframework.util.ObjectUtils"%>
<%@page import="com.educare.component.DbTableSms"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="com.educare.util.LncUtil"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
String ip = LncUtil.getIp(request);
out.println(ip);
if(!"106.244.74.221,127.0.0.1,0:0:0:0:0:0:0:1,192.168.0.164,192.168.0.226".contains(ip)){
	return;
}
%>



<!DOCTYPE html>
<html>
<head>
    <title>DB 데이터 출력</title>
     <meta charset="UTF-8">
    <style>
        table {
        	
            width: 100%;
            border-collapse: collapse;
            margin: 20px 0 20px;
            font-size: 18px;
            text-align: left;
        }
        th, td {
            padding: 12px;
            border-bottom: 1px solid #ddd;
        }
        th {
            background-color: #f2f2f2;
        }
        tr:hover {background-color: #f5f5f5;}
        td input[type=text] {
        	width: 100%;
        }
    </style>
    

</head>
<body>
<form id="form-db" method="post">
<table>

<tr>
	<th>id</th>
	<td><input type="text" name="tplCd" value="${param.tplCd }"/></td>
</tr>
<tr>
	<th>msg</th>
	<td colspan=""><textarea name="msg" style="width:100%;height:50px;">${param.msg }</textarea></td>
</tr>
</table>
<button type="button" onclick="fn_save()">전송</button>
</form>

<script language="javascript" src="${utcp.ctxPath}/resources/admin/js/jquery-3.5.1.min.js" type="text/javascript"></script>
<script>
function fn_save(){
	$('#form-db').submit();
}
</script>
<%

String tplCd = request.getParameter("tplCd");
String msg = request.getParameter("msg");
System.out.println(msg);
if(ObjectUtils.isEmpty(tplCd) || ObjectUtils.isEmpty(msg)){
	out.println("공백");
	return;
}

ServletContext conext = session.getServletContext();
WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(conext);
DbTableSms dbTableSms = (DbTableSms)wac.getBean("dbTableSms");

ResultVO result = dbTableSms.sendAlimTalk("01047841100", msg, tplCd);
out.println(result.getMsg());
%>
</body>
</html>
