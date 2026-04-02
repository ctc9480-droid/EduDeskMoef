<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>

<%
/**/
String fileNm = request.getParameter("excelFileNm");
//String fileNm1 = "[통계] 카테고리별_"+fileNm;
String fileNm1 = "통계/현황관리_교육과정별 현황";
fileNm1=new String(fileNm1.getBytes("KSC5601"), "8859_1");
/*
*/
response.setHeader("Content-Disposition", "attachment; filename="+fileNm1+".xls");
response.setHeader("Content-Description", "JSP Generated Data");
response.setContentType("application/vnd.ms-excel"); 
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
td, th {
	border: 1px solid #d9d9d9;
		mso-number-format:'@';
}

tr {
	mso-height-source: auto;
	mso-ruby-visibility: none;
}

col {
	mso-width-source: auto;
	mso-ruby-visibility: none;
}

br {
	mso-data-placement: same-cell;
}

ruby {
	ruby-align: left;
}

.vm {
	vertical-align: middle !important;
}

.tc {
	text-align: center !important;
}
.tr {
	text-align: right !important;
}
</style>
</head>

<body>
	
	<table class="w100 tb01">
		<caption class="sound_only">통계/현황관리_교육과정별 현황</caption>
		<thead>
			<tr>
				<th rowspan="2" scope="col" class="vm">구분</th>
				<th rowspan="2" scope="col" class="vm">교육분류</th>
				<th rowspan="2" scope="col" class="vm">월</th>
				<th rowspan="2" scope="col" class="vm">주간</th>
				<th rowspan="2" scope="col" class="vm">시작</th>
				<th rowspan="2" scope="col" class="vm">종료</th>
				<th rowspan="2" scope="col" class="vm">기간</th>
				<th rowspan="2" scope="col" class="vm">기수</th>
				<th rowspan="2" scope="col" class="vm">과정명</th>
				<th rowspan="2" scope="col" class="vm">계획인원</th>
				<th rowspan="" colspan="5" scope="col" class="vm">교육수료인원</th>

				<th rowspan="2" scope="col" class="vm">만족도 조사 참여인원</th>
				<th rowspan="2" scope="col" class="vm">만족도 조사 참여율(%)</th>
				<th rowspan="" colspan="6" scope="col" class="vm">만족도 조사(점수)</th>
				<th rowspan="2" scope="col" class="vm">비고(특이사항)</th>
			</tr>
			<tr>
				<th colspan="" scope="col" class="vm">합계</th>
				<th colspan="" scope="col" class="vm">재정경제부</th>
				<th rowspan="" scope="col" class="vm">기획예산처</th>
				<th rowspan="" scope="col" class="vm">지자체</th>
				<th rowspan="" scope="col" class="vm">공공기관</th>
				<th rowspan="" scope="col" class="vm">전반적 만족도</th>
				<th rowspan="" scope="col" class="vm">교육내용</th>
				<th rowspan="" scope="col" class="vm">강사</th>
				<th rowspan="" scope="col" class="vm">교육장</th>
				<th rowspan="" scope="col" class="vm">식사</th>
				<th rowspan="" scope="col" class="vm">숙소</th>

			</tr>
		</thead>
		<tbody>
			<c:set var="personnelSum" value="0"/>
			<c:set var="eduAttendSum" value="0"/>
			<c:set var="eduAttendSum1" value="0"/>
			<c:set var="eduAttendSum2" value="0"/>
			<c:set var="eduAttendSum3" value="0"/>
			<c:set var="eduAttendSum4" value="0"/>
			<c:set var="fbSum" value="0"/>
			<c:set var="choice1Sum" value="0"/>
			<c:set var="choice2Sum" value="0"/>
			<c:set var="choice3Sum" value="0"/>
			<c:set var="choice4Sum" value="0"/>
			<c:set var="choice5Sum" value="0"/>
			<c:set var="choice6Sum" value="0"/>
			<c:forEach var="o" items="${data.pageList}" varStatus="s">
				<c:set var="personnelSum" value="${personnelSum + o.personnel }"/>
				<c:set var="eduAttendSum" value="${eduAttendSum + o.eduAttendCnt }"/>
				<c:set var="eduAttendSum1" value="${eduAttendSum1 + o.eduAttendCnt1 }"/>
				<c:set var="eduAttendSum2" value="${eduAttendSum2 + o.eduAttendCnt2 }"/>
				<c:set var="eduAttendSum3" value="${eduAttendSum3 + o.eduAttendCnt3 }"/>
				<c:set var="eduAttendSum4" value="${eduAttendSum4 + o.eduAttendCnt4 }"/>
				<c:set var="fbSum" value="${fbSum + o.fbCnt }"/>
				<c:set var="choice1Sum" value="${choice1Sum + o.fbCnt * o.choice1}"/>
				<c:set var="choice2Sum" value="${choice2Sum + o.fbCnt * o.choice2}"/>
				<c:set var="choice3Sum" value="${choice3Sum + o.fbCnt * o.choice3}"/>
				<c:set var="choice4Sum" value="${choice4Sum + o.fbCnt * o.choice4}"/>
				<c:set var="choice5Sum" value="${choice5Sum + o.fbCnt * o.choice5}"/>
				<c:set var="choice6Sum" value="${choice6Sum + o.fbCnt * o.choice6}"/>
				
			</c:forEach>
			<tr>
				<td colspan="9" class="tc">전체</td>
				<td class="tr">${personnelSum }</td>
				<td class="tr">${eduAttendSum }</td>
				<td class="tr">${eduAttendSum1 }</td>
				<td class="tr">${eduAttendSum2 }</td>
				<td class="tr">${eduAttendSum3 }</td>
				<td class="tr">${eduAttendSum4 }</td>

				<td class="tr">${fbSum }</td>
				<td class="tr">
				    <c:choose>
				        <c:when test="${eduAttendSum == 0}">0.0</c:when>
				        <c:otherwise><fmt:formatNumber value="${(fbSum / eduAttendSum) * 100}" type="number" minFractionDigits="1" maxFractionDigits="1" /></c:otherwise>
				    </c:choose>
				</td>
				<td class="tr">
				    <c:choose>
				        <c:when test="${fbSum == 0}">0.0</c:when>
				        <c:otherwise><fmt:formatNumber value="${choice1Sum / fbSum}" type="number" minFractionDigits="1" maxFractionDigits="1" /></c:otherwise>
				    </c:choose>
				</td>
				<td class="tr">
				    <c:choose>
				        <c:when test="${fbSum == 0}">0.0</c:when>
				        <c:otherwise><fmt:formatNumber value="${choice2Sum / fbSum}" type="number" minFractionDigits="1" maxFractionDigits="1" /></c:otherwise>
				    </c:choose>
				</td>
				<td class="tr">
				    <c:choose>
				        <c:when test="${fbSum == 0}">0.0</c:when>
				        <c:otherwise><fmt:formatNumber value="${choice3Sum / fbSum}" type="number" minFractionDigits="1" maxFractionDigits="1" /></c:otherwise>
				    </c:choose>
				</td>
				<td class="tr">
				    <c:choose>
				        <c:when test="${fbSum == 0}">0.0</c:when>
				        <c:otherwise><fmt:formatNumber value="${choice4Sum / fbSum}" type="number" minFractionDigits="1" maxFractionDigits="1" /></c:otherwise>
				    </c:choose>
				</td>
				<td class="tr">
				    <c:choose>
				        <c:when test="${fbSum == 0}">0.0</c:when>
				        <c:otherwise><fmt:formatNumber value="${choice5Sum / fbSum}" type="number" minFractionDigits="1" maxFractionDigits="1" /></c:otherwise>
				    </c:choose>
				</td>
				<td class="tr">
				    <c:choose>
				        <c:when test="${fbSum == 0}">0.0</c:when>
				        <c:otherwise><fmt:formatNumber value="${choice6Sum / fbSum}" type="number" minFractionDigits="1" maxFractionDigits="1" /></c:otherwise>
				    </c:choose>
				</td>

				<td></td>
			</tr>
			
			<c:forEach var="o" items="${data.pageList}" varStatus="s">
				
				<tr>
					<td class="tc">${s.count }</td>
					<td class="tc">${o.ctg1Nm }</td>
					<td class="tc">${o.monthKr }월</td>
					<td class="tc">${o.weekKr }주</td>
					<td class="tc">${o.eduPeriodBegin}</td>
					<td class="tc">${o.eduPeriodEnd }</td>
					<td class="tc"><c:if test="${o.periodKr > 0 }">${o.periodKr }박</c:if>${o.periodKr + 1 }일</td>
					<td class="tc">${o.eduTerm }기</td>
					<td class="tc">${o.eduNm }</td>
					<td class="tr">${o.personnel }</td>
					<td class="tr">${o.eduAttendCnt }</td>
					<td class="tr">${o.eduAttendCnt1 }</td>
					<td class="tr">${o.eduAttendCnt2 }</td>
					<td class="tr">${o.eduAttendCnt3 }</td>
					<td class="tr">${o.eduAttendCnt4 }</td>

					<td class="tr">${o.fbCnt }</td>
					<td class="tr">${o.fbRate }</td>
					<td class="tr">${o.choice1 == '6.0' ? '-' : o.choice1 }</td>
					<td class="tr">${o.choice2 == '6.0' ? '-' : o.choice2 }</td>
					<td class="tr">${o.choice3 == '6.0' ? '-' : o.choice3 }</td>
					<td class="tr">${o.choice4 == '6.0' ? '-' : o.choice4 }</td>
					<td class="tr">${o.choice5 == '6.0' ? '-' : o.choice5 }</td>
					<td class="tr">${o.choice6 == '6.0' ? '-' : o.choice6 }</td>

					<td></td>
				</tr>
			</c:forEach>
			<c:if test="${empty data.pageList }">
				<tr>
					<td colspan="24">내역이 없습니다.</td>
				</tr>
			</c:if>
		</tbody>
	</table>

</body>
</html>
