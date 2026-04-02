<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<div class="calTit tc">
	<a href="javascript:;" onclick="fn_moveMonth2('P'); return false;" class="prev">
	<img src="${utcp.ctxPath}/resources/user/image/btn/btn_left.png" alt="이전달"></a> 
	${vo.srchYear}년<span> ${vo.srchMonth}월</span> 
	<a href="javascript:;" onclick="fn_moveMonth2('N'); return false;" class="next">
	<img src="${utcp.ctxPath}/resources/user/image/btn/btn_right.png" alt="다음달"></a>
</div>

<div id="calendar" class="rsvCal">
	<table id="calendar" class="w100 tb">
		<tbody>
			<tr class="weekdays">
				<th>일</th>
				<th>월</th>
				<th>화</th>
				<th>수</th>
				<th>목</th>
				<th>금</th>
				<th>토</th>
			</tr>
			<c:forEach begin="0" end="${days}" varStatus="stat">
				<c:set var="key" value="${calMap[stat.index]}" />
				<c:if test="${stat.index % 7 == 0 && stat.index != days}">
					<tr class="days">
				</c:if>
				<c:if test="${not empty key }">
					<c:set var="otherMonth" value="" />
					<c:if test="${fn:substring(key,4,6) ne month }">
						<c:set var="otherMonth" value="other-month" />
					</c:if>
	
					<td class="day ${otherMonth }">
						<c:set var="calDay" value="${utcp.convDateToStr(utcp.convStrToDate(key,'yyyyMMdd'),'d')}" />
						<span class="day">${calDay }</span>
						
						
						<c:set var="prvtCnt" value="0" />
						<c:forEach items="${lecMap[key].rceptList}" var="o">
							<c:set var="prvtCnt" value="${prvtCnt+1 }" />
						</c:forEach>
						
						<c:if test="${prvtCnt>0 }">
							<a href="javascript:fn_openClassDormiInfo('${key}')" class="tmType"> <span class="fc_red pdl5">${prvtCnt }</span>개
						</c:if>
						
						
					</td>
				</c:if>
				<c:if test="${stat.index % 7 == 6}">
					</tr>
				</c:if>
			</c:forEach>
		</tbody>
	</table>
</div>
