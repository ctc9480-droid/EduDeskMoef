<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<%@page import="com.educare.edu.education.service.EduVO"%>
<%@page import="com.educare.util.DateUtil"%>
<%@page import="java.util.Calendar"%>
<%@page import="com.educare.edu.member.service.MemberService"%>
<%@page import="com.educare.edu.member.service.model.UserInfo"%>
<%@page import="com.educare.edu.member.service.SessionUserInfoHelper"%>
<%@page import="com.educare.edu.education.service.model.Lecture"%>
<%@page import="com.educare.edu.education.service.EduService"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<!DOCTYPE html>
<html lang="ko">
  <head>
    <meta charset="utf-8" />
    <link
      rel="icon"
      href="https://static.toss.im/icons/png/4x/icon-toss-logo.png"
    />
    <link rel="stylesheet" type="text/css" href="style.css" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>토스페이먼츠 샘플 프로젝트</title>
  </head>

  <body>
    <div class="result wrapper">
      <div class="box_section">
        <h2 style="padding: 20px 0px 10px 0px">
          <img
            width="25px"
            src="https://static.toss.im/3d-emojis/u1F6A8-apng.png"
          />
          결제 실패
        </h2>
        <p id="code"></p>
        <p id="message"></p>
      </div>
    </div>
  </body>
</html>

<script>
  const urlParams = new URLSearchParams(window.location.search);

  const codeElement = document.getElementById("code");
  const messageElement = document.getElementById("message");

  codeElement.textContent = "에러코드: " + urlParams.get("code");
  messageElement.textContent = "실패 사유: " + urlParams.get("message");
  alert(urlParams.get("message"));
	location.href='${utcp.ctxPath}/edu/pay/toss/order.do?';
  </script>
