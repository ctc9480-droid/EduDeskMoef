<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>

<section class="pd025 pd200 po_re">
	<div class="po_re br5 bs_box">
		<table width="100%" class="tb01 tc tb_fix">
			<caption class="sound_only">게시판테이블</caption>
			<colgroup>
				<col width="">
				<col width="">
				<col width="">
			</colgroup>
			<thead bgcolor="#f7f8fa">
				<tr>
					<th>제목</th>
					<th>작성자</th>
					<th>기간</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${feedbackOpenList }" var="map">
					<tr>
						<td class="tl"><a href="fbOpenResult.do?idx=${map.idx }" class="dp_b el">${map.title } </a>
						</td>
						<td>${map.regNm }</td>
						<td>
						${utcp.convDateToStr(utcp.convStrToDate(map.startDtime,'yyyyMMddHHmmssSSS'),'yyyy.MM.dd HH:mm') } ~
						${utcp.convDateToStr(utcp.convStrToDate(map.endDtime,'yyyyMMddHHmmssSSS'),'yyyy.MM.dd HH:mm') }
						</td>
					</tr>
				</c:forEach>
				<c:if test="${empty feedbackOpenList}">
				<tr><td colspan="3" class="h200">데이터가 없습니다</td></tr>
				</c:if>
			</tbody>
		</table>
	</div>
</section>