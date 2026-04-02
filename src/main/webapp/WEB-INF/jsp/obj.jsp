<%@ page language="java" contentType="text/json; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.google.gson.JsonObject"%>
<%
JsonObject obj  = (JsonObject)request.getAttribute("obj");
	if(obj != null) {
		out.print(obj);
	}
%>