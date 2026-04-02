<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>

<div class="po_re br5 bs_box cf">
	<div id="htmlCal">

		<div class="calTit tc">
			<a href="javascript:;" onclick="fn_moveMonth('P'); return false;"
				class="prev"> <img src="/resources/user/image/btn/btn_left.png"
				alt="이전달"></a> ${vo.srchYear}년<span> ${month}월</span> <a
				href="javascript:;" onclick="fn_moveMonth('N'); return false;"
				class="next"> <img src="/resources/user/image/btn/btn_right.png"
				alt="다음달"></a>
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
						<!-- 
						 -->
						<c:forEach items="${lecMap[key].rceptList}" var="o">
						<a href="${utcp.ctxPath }/user/edu/eduView.do?eduSeq=${o.eduSeq}" class="tmType"><span class="classtype">${o.lctreTypeNm }</span>${o.eduNm } ${o.eduTerm }기</a>
						</c:forEach>
						</td>
						</c:if>
						<c:if test="${stat.index % 7 == 6}">
							</tr>
						</c:if>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<!-- /. calendar -->
	</div>

</div>
</div>
<script>
	$("#i_srchYear").val('${vo.srchYear}');
	$("#i_srchMonth").val('${vo.srchMonth}');
	$("#i_srchCtgry").val('${vo.srchCtgry}');
	$("#i_srchAgency").val('${vo.srchAgency}');
	$("#i_srchColumn").val('${vo.srchColumn}');
	$("#i_srchWrd").val('${vo.srchWrd}');
</script>