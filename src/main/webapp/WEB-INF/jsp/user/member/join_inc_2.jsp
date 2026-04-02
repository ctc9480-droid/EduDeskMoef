<%@page import="com.educare.component.VarComponent"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<%@ page import="com.educare.util.XmlBean"%>



<div class="apply_c mt80">
	<div class="certi_box cf">
		<!-- <div class="each_box ipin">
			<h3 class="tit str_level2">공공아이핀</h3>
			<p class="txt str_level3">홈페이지에서 회원가입, 글쓰기 시 주민등록번호를 사용하지 않고도 본인임을 확인할 수 있는 개인정보보호 서비스 입니다.</p>
			<div class="btn_wrap">
				<a href="javascript:fnIpinCheckPopup()" class="btn05 btn_blue" title="공공아이핀">공공아이핀(I-IPN) 인증</a>
			</div>
		</div> -->
		<div class="each_box phone">
			<h3 class="tit str_level2">휴대폰 본인확인서비스</h3>
			<p class="txt str_level3">본인확인 서비스 SCI 서울신용평가정보(주)에서 제공되며, 생년월일과 본인 명의의 휴대폰 정보를 이용하여 본인 인증을 받으실 수 있습니다.</p>
			<div class="btn_wrap">
				<!-- 
				<a href="javascript:fn_join_next(3,'S')" class="btn05 btn_blue" title="휴대폰 인증">휴대폰 본인확인 서비스</a>
				 -->
				
				<a href="javascript:fnMobileCheckPopup()" class="btn05 btn_blue" title="휴대폰 인증">휴대폰 본인확인 서비스</a>
			</div>
		</div>
	</div>
</div>

