<%@page import="com.educare.component.VarComponent"%>
<%@page import="java.util.Map"%>
<%@page import="org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<jsp:useBean id="curDate" class="java.util.Date" />

<!-- 개발팀에서 임시추가함,240221 -->
<style>
#tmp-table td{
	font-size: 13px;
}
</style>

<section class="pd025 pd200 po_re">
	<div class="listWrap listWrapTop listWrapBox">
		<form name="searchFrm" method="get" action="${utcp.ctxPath}/admin/edu/lcrcpList.do">
			<div class="box_sort flex">
				<input type="hidden" name="page" id="page" value="${vo.page}">
				<input type="hidden" name="listRow" value="${not empty param.listRow?param.listRow:10}">
				<div class="box_cate">
					<select id="eduYear" name="eduYear" class="srchCtgry wauto mgl10">
						<fmt:formatDate value="<%=new java.util.Date()%>" var="curYear" pattern="yyyy" />
						<fmt:parseNumber value="${curYear}" var="year" />
						<option value="">연도선택</option>
						<c:forEach begin="2023" end="${year+1 }" varStatus="stat">
							<c:set value="${year+2-stat.count  }" var="choiceYear" />
							<option value="${choiceYear}" <c:if test="${choiceYear == vo.eduYear}">selected</c:if>>${choiceYear}년</option>
						</c:forEach>
					</select>
					<%-- 
					 --%>
					<select id="eduTerm" name="eduTerm" class="srchCtgry wauto">
						<c:forEach items="<%=VarComponent.EDU_TERM %>" var="o" varStatus="s">
							<c:if test="${s.index==0 }">
							<c:set var="eduTermNm" value="기수선택"/>
							</c:if>
							<c:if test="${s.index!=0 }">
							<c:set var="eduTermNm" value="${o }"/>
							</c:if>
							<option value="${s.index }" ${vo.eduTerm == s.index?'selected':'' }>${eduTermNm }</option>
						</c:forEach>
					</select>
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
					<label for="CtgryType" class="sound_only ">3교육과정 선택</label>
					<select id="srchCtgry3" name="srchCtgry3" class="srchCtgry wauto" title="교육과정을 선택 하실 수 있습니다.">
						<option value="0">3차과정 전체</option>
						<c:forEach var="data" items="${cateList3}" varStatus="stat">
							<option value="${data.ctgrySeq}" <c:if test='${vo.srchCtgry3 == data.ctgrySeq}'>selected</c:if>>${data.ctgryNm}</option>
						</c:forEach>
					</select> 
					<select id="srchState" name="srchState" class="srchCtgry wauto">
						<option value="0">신청상태선택</option>
						<%-- <option value="2" ${vo.srchState eq '2'?'selected':'' }>승인</option> --%>
						<option value="2" ${vo.srchState eq '2'?'selected':'' }>접수</option>
						<option value="1" ${vo.srchState eq '1'?'selected':'' }>신청</option>
						<option value="3" ${vo.srchState eq '3'?'selected':'' }>취소</option>
					</select>
					<%-- 
					<select id="srchFeeTp" name="srchFeeTp" class="srchCtgry wauto">
						<option value="0">결제방식선택</option>
						<option value="1" ${vo.srchFeeTp eq '1'?'selected':'' }>온라인결제</option>
						<option value="2" ${vo.srchFeeTp eq '2'?'selected':'' }>현장결제</option>
						<option value="3" ${vo.srchFeeTp eq '3'?'selected':'' }>세금계산서(영수)</option>
						<option value="4" ${vo.srchFeeTp eq '4'?'selected':'' }>세금계산서(청구)</option>
					</select>
					 --%>
					
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
					
					<input type="text" id="srchBeginDt" name="srchBeginDt" value="${param.srchBeginDt }" class="btn04  tl mgr5 datepicker" placeholder="시작일" style="width: 110px;" maxlength="10" readonly/>
					~
					<input type="text" id="srchEndDt" name="srchEndDt" value="${param.srchEndDt }" class="btn04  tl mgr5 datepicker" placeholder="종료일" style="width: 110px;" maxlength="10" readonly/>
				
					<input type="text" id="srchWrd" name="srchWrd" class="" value="${param.srchWrd }"/>
					<button type="button" onclick="fn_srch()">검색</button>
				</div>
			</div>
		</form>
	</div>

	<div class="tl mgt15 mgb15">
		<label for="listRow" class="sound_only">갯수 선택</label>
		<select id="listRow" class="btn_blackl">
			<option value="10">10</option>
			<option value="20">20</option>
			<option value="30">30</option>
			<option value="40">40</option>
			<option value="50">50</option>
			<option value="60">60</option>
			<option value="70">70</option>
			<option value="80">80</option>
			<option value="90">90</option>
			<option value="100">100</option>
		</select>
	</div>

	<table class="w100 tb01" id="tmp-table">
		<caption class="sound_only">단체교육 신청내역 테이블</caption>
		<thead>
			<tr>
				<th scope="col" class="vm">순번</th>
				<th scope="col" class="vm">과정</th>
				<th scope="col" class="vm">교육명</th>
				<th scope="col" class="vm">교육일</th>
				<th scope="col" class="vm">신청자</th>
				<th scope="col" class="vm">생년월일</th>
				<th scope="col" class="vm">아이디</th>
				<th scope="col" class="vm">휴대폰번호</th>
				<th scope="col" class="vm">신청상태</th>
				<th scope="col" class="vm">신청일</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="o" items="${data.lcrcpList}" varStatus="s">
				<tr>
					<td>${data.pageNavi.totalRecordCount - (((data.pageNavi.currentPageNo-1)*data.pageNavi.recordCountPerPage) + s.index)}</td>
					<td>${o.ctg1Nm }</td>
					<td>${o.eduNm } 
					<c:if test="${not empty o.eduTerm }">${o.eduTerm }기</c:if>
					</td>
					<td>${o.eduPeriodBegin} ~ ${o.eduPeriodEnd }</td>
					<td><a href="javascript:md_openLcrcpInfo(${o.eduSeq },${o.rceptSeq},'${o.userId}');">${o.userNm }</a></td>
					<td>${o.birth }</td>
					<td>${o.loginId }</td>
					<td>${o.mobile }</td>
					<td>${o.addRceptStateNm }</td>
					<td>${utcp.convDateToStr(o.regDe,'yyyy-MM-dd HH:mm') }</td>
					</td>
					<%-- 
					<td>
						<c:if test="${o.rfndState == 1 }">
						<button class="btn04 btn_blue" onclick="fn_openRefundRcept(${o.rceptSeq});" >보기</button>
						</c:if>
					</td>
					 --%>
				</tr>
				
			</c:forEach>
			<c:if test="${empty data.lcrcpList }">
				<tr>
					<td colspan="16">내역이 없습니다.</td>
				</tr>
			</c:if>
		</tbody>
	</table>

	<div class="fr tc">
		<button class="btn01 btn_greenl" onclick="fn_downLcrcpExcel()" type="button">엑셀</button>
	</div>
	<!--// paging //-->
	<c:if test="${not empty data.pageNavi }">
	<c:set var="pageNavi" value="${data.pageNavi }" />
	<%
		PaginationInfo pageNavi = (PaginationInfo)pageContext.getAttribute("pageNavi");
		request.setAttribute("pageNavi", pageNavi);
		%>
	<jsp:include page="/WEB-INF/jsp/admin/common/paging.jsp" />
	</c:if>
	<!--// paging //-->


	</div>

</section>

<script>
$(function() {
	$('#listRow').val('${not empty param.listRow?param.listRow:10}');
	$("#srchCtgry,#srchCtgry2,#srchCtgry3,#eduYear,#eduTerm").change(function() {
		$("form[name='searchFrm']").submit();
	});
	$("#listRow").change(function() {
		$('[name=listRow]').val($('#listRow').val());
		$("form[name='searchFrm']").submit();
	});

	$("#srchBeginDt").datepicker({
        onSelect: function (selectedDate) {
        	console.log(selectedDate);
            var startDate = $(this).datepicker("getDate");
        	console.log(startDate);
            if (startDate) {
                $("#srchEndDt").datepicker("option", "minDate", startDate);
            }
        }
    });
    $("#srchEndDt").datepicker({
        onSelect: function (selectedDate) {
            var endDate = $(this).datepicker("getDate");
            if (endDate) {
                $("#srchBeginDt").datepicker("option", "maxDate", endDate);
            }
        }
    });
	
});
function fn_srch(){
	$("#page").val(1);
	$("form[name='searchFrm']").submit();
}
function fn_paging(page) {
	$("#page").val(page);
	$("form[name='searchFrm']").submit();
}
function fn_downLcrcpExcel(){
	$('form[name=searchFrm]').attr('action','${utcp.ctxPath}/admin/excel/lcrcpExcel.do');
	$('form[name=searchFrm]').submit();
	$('form[name=searchFrm]').attr('action','lcrcpList.do');
}

function fn_openRefundRcept(rceptSeq){
	window.open('${utcp.ctxPath}/comm/popup/cancelRceptAdmin.do?rceptSeq='+rceptSeq,'refundRcept','width=800,height=700');
}
</script>
