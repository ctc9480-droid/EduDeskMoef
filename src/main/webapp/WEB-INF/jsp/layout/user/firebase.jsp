<%@page import="com.educare.util.LncUtil"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.educare.edu.member.service.SessionUserInfoHelper"%>
<%
if(!"0:0:0:0:0:0:0:1,192.168.0.114,127.0.0.1".contains(LncUtil.getIp(request))){
%>
<!-- 
<script src="https://www.gstatic.com/firebasejs/8.2.10/firebase-app.js"></script>
<script src="https://www.gstatic.com/firebasejs/8.2.10/firebase-analytics.js"></script>
<script>
  // Your web app's Firebase configuration
  // For Firebase JS SDK v7.20.0 and later, measurementId is optional
  var firebaseConfig = {
    apiKey: "AIzaSyBYolonvUFw-lW-CIdMb-PEwZUxuwsPamo",
    authDomain: "edudesk-dea35.firebaseapp.com",
    projectId: "edudesk-dea35",
    storageBucket: "edudesk-dea35.appspot.com",
    messagingSenderId: "644237269326",
    appId: "1:644237269326:web:73183e769d03b51b95661f",
    measurementId: "G-0XH9GVKHDJ"
  };
  // Initialize Firebase
  firebase.initializeApp(firebaseConfig); 
  firebase.analytics();
</script>
 -->
<%
}
%>