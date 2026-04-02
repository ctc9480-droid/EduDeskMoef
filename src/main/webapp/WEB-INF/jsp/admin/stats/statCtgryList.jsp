<%@page import="com.educare.component.VarComponent"%>
<%@page import="java.util.Map"%>
<%@page import="org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<jsp:useBean id="curDate" class="java.util.Date" />

<<style>
<!--
.table-container { max-width: 100%; /* 본문 너비 최대 */ max-height: 550px; /* 본문 높이 최대 (원하는 값으로 조절) */ overflow: auto; /* 넘치면 가로/세로 스크롤 */  }
table { width: auto; /* 내용 크기만큼 확장 */ border-collapse: collapse; }
-->
</style>

<section class="pd025 po_re">
	<div class="listWrap listWrapTop listWrapBox">
		<form name="searchFrm" method="get">
		<input type="hidden" name="excelYn" id="excelYn" />
			<div class="box_sort flex">
				<input type="hidden" name="page" id="page" value="${vo.page}">
				<div class="box_cate">
					<%-- 
					<select name="eduYear" class="srchCtgry wauto mgl10">
						<fmt:formatDate value="<%=new java.util.Date()%>" var="curYear" pattern="yyyy" />
						<fmt:parseNumber value="${curYear}" var="year" />
						<option value="">연도선택</option>
						<c:forEach begin="2023" end="${year+1 }" varStatus="stat">
							<c:set value="${year+2-stat.count  }" var="choiceYear" />
							<option value="${choiceYear}" <c:if test="${choiceYear == vo.eduYear}">selected</c:if>>${choiceYear}년</option>
						</c:forEach>
					</select>
					<select name="eduTerm" class="srchCtgry wauto">
						<c:forEach items="<%=VarComponent.EDU_TERM %>" var="o" varStatus="s">
							<c:if test="${s.index==0 }">
							<c:set var="eduTermNm" value="학기선택"/>
							</c:if>
							<c:if test="${s.index!=0 }">
							<c:set var="eduTermNm" value="${o }"/>
							</c:if>
							<option value="${s.index }" ${vo.eduTerm == s.index?'selected':'' }>${eduTermNm }</option>
						</c:forEach>
					</select>
					--%>
					<label for="CtgryType" class="sound_only">1교육과정 선택</label>
					<select id="srchCtgry" name="srchCtgry" class="srchCtgry wauto" title="교육과정을 선택 하실 수 있습니다.">
						<option value="0">1차과정 전체</option>
						<c:forEach var="data" items="${cateList}" varStatus="stat">
							<option value="${data.ctgrySeq}" <c:if test='${vo.srchCtgry == data.ctgrySeq}'>selected</c:if>>${data.ctgryNm}</option>
						</c:forEach>
					</select> 
					<label for="CtgryType" class="sound_only">2교육과정 선택</label>
					<select id="srchCtgry2" name="srchCtgry2" class="srchCtgry wauto" title="교육과정을 선택 하실 수 있습니다.">
						<option value="0">2차과정 전체</option>
						<c:forEach var="data" items="${cateList2}" varStatus="stat">
							<option value="${data.ctgrySeq}" <c:if test='${vo.srchCtgry2 == data.ctgrySeq}'>selected</c:if>>${data.ctgryNm}</option>
						</c:forEach>
					</select>
					<label for="CtgryType" class="sound_only">3교육과정 선택</label>
					<select id="srchCtgry3" name="srchCtgry3" class="srchCtgry wauto" title="교육과정을 선택 하실 수 있습니다.">
						<option value="0">3차과정 전체</option>
						<c:forEach var="data" items="${cateList3}" varStatus="stat">
							<option value="${data.ctgrySeq}" <c:if test='${vo.srchCtgry3 == data.ctgrySeq}'>selected</c:if>>${data.ctgryNm}</option>
						</c:forEach>
					</select> 
					
				</div>
				<div class="box_search">
				
					<%-- 
					<label for="srchDtGb" class="sound_only">교육일,신청일 선택</label>
					<select id="srchDtGb" name="srchDtGb" class="srchColumn" title="">
						<option value="R" <c:if test='${vo.srchDtGb == "R"}'>selected</c:if>>신청일</option>
						<option value="E" <c:if test='${vo.srchDtGb == "E"}'>selected</c:if>>교육일</option>
					</select> 
					--%>
					<input type="hidden" name="srchDtGb" value="R"/>
					<input type="text" id="srchWrd" name="srchWrd" class="" value="${param.srchWrd }" placeholder="과정명" />
					<input readonly type="text" id="srchStartDay" name="srchStartDay" value="${param.srchStartDay }" class="btn04  tl mgr5 datepicker" placeholder="시작일" style="width: 120px;" maxlength="10" />
					~
					<input readonly type="text" id="srchEndDay" name="srchEndDay" value="${param.srchEndDay }" class="btn04  tl mgr5 datepicker" placeholder="종료일" style="width: 120px;" maxlength="10" />
					
					<button>검색</button>
				</div>
			</div>
		</form>
	</div>
	<div class="scl_wrap table-container">
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
				<tr>
					<td colspan="9" class="tc">전체</td>
					<td class="tr" id="personnelSum"></td>
					<td class="tr" id="eduAttendSum"></td>
					<td class="tr" id="eduAttendSum1"></td>
					<td class="tr" id="eduAttendSum2"></td>
					<td class="tr" id="eduAttendSum3"></td>
					<td class="tr" id="eduAttendSum4"></td>

					<td class="tr" id="fbSum"></td>
					<td class="tr" id="fbAvg"></td>
					<td class="tr" id="choice1Avg"></td>
					<td class="tr" id="choice2Avg"></td>
					<td class="tr" id="choice3Avg"></td>
					<td class="tr" id="choice4Avg"></td>
					<td class="tr" id="choice5Avg"></td>
					<td class="tr" id="choice6Avg"></td>

					<td></td>
				</tr>
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
	</div>
	<div class="fr tc">
		<button class="btn01 btn_greenl" onclick="fn_downStatLctreExcel()" type="button">엑셀</button>
	</div>
	
	
	<!--// paging //-->
	<%-- 
	<c:if test="${not empty data.pageNavi && vo.row > 0 }">
	<c:set var="pageNavi" value="${data.pageNavi }" />
	<%
		PaginationInfo pageNavi = (PaginationInfo)pageContext.getAttribute("pageNavi");
		request.setAttribute("pageNavi", pageNavi);
		%>
	<jsp:include page="/WEB-INF/jsp/admin/common/paging.jsp" />
	</c:if>
	<!--// paging //-->
	--%>
	</div>

</section>

<script>
$(function() {
	$("#srchCtgry,#srchCtgry2,#srchCtgry3").change(function() {
		$("form[name='searchFrm']").submit();
	});
	
	$('.datepicker').datepicker();
	
	$("#personnelSum").text("${personnelSum }");
	$("#eduAttendSum").text("${eduAttendSum }");
	$("#eduAttendSum1").text("${eduAttendSum1 }");
	$("#eduAttendSum2").text("${eduAttendSum2 }");
	$("#eduAttendSum3").text("${eduAttendSum3 }");
	$("#eduAttendSum4").text("${eduAttendSum4 }");
	
	$("#fbSum").text("${fbSum }");
	var fbAvg = 0.0;
	if (${eduAttendSum } != 0) {
		fbAvg = (${fbSum } / ${eduAttendSum }) * 100;
	}
	$("#fbAvg").text(fbAvg.toFixed(1));
	var choice1Avg = 0.0;
	if (${fbSum } != 0) {
		choice1Avg = (${choice1Sum } / ${fbSum });
	}
	$("#choice1Avg").text(choice1Avg.toFixed(1));
	var choice2Avg = 0.0;
	if (${fbSum } != 0) {
		choice2Avg = (${choice2Sum } / ${fbSum });
	}
	$("#choice2Avg").text(choice2Avg.toFixed(1));
	var choice3Avg = 0.0;
	if (${fbSum } != 0) {
		choice3Avg = (${choice3Sum } / ${fbSum });
	}
	$("#choice3Avg").text(choice3Avg.toFixed(1));
	var choice4Avg = 0.0;
	if (${fbSum } != 0) {
		choice4Avg = (${choice4Sum } / ${fbSum });
	}
	$("#choice4Avg").text(choice4Avg.toFixed(1));
	var choice5Avg = 0.0;
	if (${fbSum } != 0) {
		choice5Avg = (${choice5Sum } / ${fbSum });
	}
	$("#choice5Avg").text(choice5Avg.toFixed(1));
	var choice6Avg = 0.0;
	if (${fbSum } != 0) {
		choice6Avg = (${choice6Sum } / ${fbSum });
	}
	$("#choice6Avg").text(choice6Avg.toFixed(1));
});

function fn_paging(page) {
	$("#page").val(page);
	$("form[name='searchFrm']").submit();
}
function fn_downStatLctreExcel(){
	$('#excelYn').val('Y');
	$("form[name='searchFrm']").submit();
	$('#excelYn').val('');
}

</script>

<script>
	function receipt(pgPayNo, payType) {
		var controlNo = pgPayNo;
		var payment = '01';
		if (payType == '2') {
			payment = '02';
		} else if (payType == '3') {
			payment = '03';
		}

		window
				.open(
						'<spring:eval expression="@prop['pay.easypay.receiptUrl']"/>?controlNo='
								+ controlNo + '&payment=' + payment,
						'MEMB_POP_RECEIPT',
						'toolbar=0,scroll=1,menubar=0,status=0,resizable=0,width=380,height=700');
	}
</script>
