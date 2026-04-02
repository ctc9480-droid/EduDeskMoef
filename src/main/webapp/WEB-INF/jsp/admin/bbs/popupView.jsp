<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp" %>
<section class="pd025 pd200" style="padding-bottom:270px;padding-top:25px;">
	<input type="hidden" name="boardType" value="notice"/>
	<table>
		<tr>
			<th>제목</th><td>${popupMap.title }</td>
		</tr>
		<tr>
			<th>이미지</th><td><img src="/bbs/comm/image/${popupMap.idx }.do" width=""/></td>
		</tr>
		<tr>
			<th>내용</th><td>${popupMap.content2 }</td>
		</tr>
	</table>
	<a href="popupList.do">목록</a>
	<a href="popupWrite.do?idx=${popupMap.idx }">수정</a>
</section>


<script>
</script>