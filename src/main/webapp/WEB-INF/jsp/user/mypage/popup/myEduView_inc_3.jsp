<%@page import="com.educare.edu.member.service.SessionUserInfoHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<div class="certiListWrap">
	<ul class="bbno">
		<li>
			<c:choose>
			<c:when test="${(lctre.fbIdx > 0 and stdnt.surveyYn ne 'Y') and (lctre.checkRcept==4  or lctre.lctreType==3) }">
					<button class="btn04 btn_blue"  onclick="fn_feedbackReg()">만족도조사</button>
			</c:when>
		
			<c:when test="${lctre.passIdx > 0 and stdnt.passYn=='Y'}">
					<span>과정 : ${lctre.ctg1Nm }</span> 
					<span>종류 : <strong class="fc_red">수료증</strong></span> 
					<span>발급날짜 : ${utcp.convDateToStr(stdnt.certDe,'yyyy.MM.dd') }</span> <span>고유번호 : ${stdnt.passCertNum }호</span> 
					<a href="javascript:fn_certView(${param.eduSeq },0,1);" class="btn01 btn_darkblue fr">출력하기</a>
			</c:when>
			
			<c:when test="${stdnt.passYn == 'Y' and lctre.passIdx == 0 }">
				<span>수료</span>
			</c:when>
			<c:otherwise>
				<span>미수료</span>
			</c:otherwise>
		</c:choose>
		</li>
	</ul>
</div>
