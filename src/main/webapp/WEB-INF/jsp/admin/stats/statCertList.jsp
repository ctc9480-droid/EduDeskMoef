<%@page import="com.educare.component.VarComponent"%>
<%@page import="java.util.Map"%>
<%@page import="org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<jsp:useBean id="curDate" class="java.util.Date" />

<section class="pd025 pd200 po_re">
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
					
					<input readonly type="text" id="srchStartDay" name="srchStartDay" value="${param.srchStartDay }" class="btn04  tl mgr5 datepicker" placeholder="시작일" style="width: 120px;" maxlength="10" />
					~
					<input readonly type="text" id="srchEndDay" name="srchEndDay" value="${param.srchEndDay }" class="btn04  tl mgr5 datepicker" placeholder="종료일" style="width: 120px;" maxlength="10" />
					<%-- 
				
					<input type="text" id="srchWrd" name="srchWrd" class="" value="${param.srchWrd }"/>
					--%>
					<button>검색</button>
				</div>
			</div>
		</form>
	</div>

	<table class="w100 tb01">
		<caption class="sound_only">카테고리별 신청내역 테이블</caption>
		<thead>
			<tr>
				<th  scope="col" class="vm">연번</th>
				<th  scope="col" class="vm">과정명</th>
				<th  scope="col" class="vm">교육시작일</th>
				<th  scope="col" class="vm">교육종료일</th>
				<th  scope="col" class="vm">증서번호(일련번호)</th>
				<th  scope="col" class="vm">성명</th>
				<th  scope="col" class="vm">발급일</th>
				<th  scope="col" class="vm">증명서</th>
				<th  scope="col" class="vm">발급</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="o" items="${data.pageList}" varStatus="s">
				<tr>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
				</tr>
			</c:forEach>
			<c:if test="${empty data.pageList }">
				<tr>
					<td colspan="9">내역이 없습니다.</td>
				</tr>
			</c:if>
		</tbody>
	</table>
	

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
