<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>

<table class="w100 tb02 tc stdntTb">
	<caption class="sound_only">프로필상세보기테이블</caption>
	<tbody>
		<tr>
			<th class="tdbg3 tc">아이디</th>
			<td class="tl" colspan="3">${user.loginId }</td>
		</tr>
		<tr>
			<th class="tdbg3 tc">성&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;명</th>
			<td class="tl">${user.userNm }
			<c:if test="${not empty user.userEnNm }">(${user.userEnNm })</c:if>
			</td>
			<th class="tdbg3 tc">생년월일</th>
			<td class="tl">${utcp.convDateToStr(utcp.convStrToDate(user.birth,'yyyyMMdd'),'yyyy-MM-dd') }</td>
		</tr>
		<tr>
			<th class="tdbg3 tc">휴&nbsp;&nbsp;대&nbsp;&nbsp;폰</th>
			<td class="tl" >${user.decMobile }</td>
			<th class="tdbg3 tc">이&nbsp;&nbsp;메&nbsp;&nbsp;일</th>
			<td class="tl" colspan="">${user.decEmail }</td>
		</tr>
		<tr>
			<th class="tdbg3 tc">sms동의</th>
			<td class="tl" >${user.smsYn eq 'Y'?'예':'아니오'}</td>
		</tr>
	</tbody>
</table>
