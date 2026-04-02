<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<table class="w100 tb02 tc stdntTb">
	<caption class="sound_only">프로필상세보기테이블</caption>
	<colgroup>
		<col style="width: 15%;">
	</colgroup>
	<tbody>
		<!-- 
		<tr>
			<th class="tdbg3 tc">이&nbsp;&nbsp;메&nbsp;&nbsp;일</th>
			<td class="tl" colspan="">mail@mail.com</td>
		</tr>
		 -->
		<tr>
			<th class="tdbg3 tc">성&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;명</th>
			<td class="tl">${user.userNm }</td>
		</tr>
		<tr>
			<th class="tdbg3 tc">소개</th>
			<td class="add tl" colspan="">
			${user.area }
			</td>
		</tr>
	</tbody>
</table>

<div class="fl tc">
			<button class="btn02 btn_grayl" onclick="location.href='instrctrList.do';">목록</button>
			<button class="btn02 btn_grayl" onclick="location.href='instrctrRgs.do?userId=${param.userId}'">수정</button>
		</div>