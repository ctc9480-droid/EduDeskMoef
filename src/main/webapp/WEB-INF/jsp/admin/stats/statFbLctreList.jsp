<%@page import="com.educare.component.VarComponent"%>
<%@page import="java.util.Map"%>
<%@page import="org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<jsp:useBean id="curDate" class="java.util.Date" />

<section class="pd025 pd200 po_re">
	<div class="listWrap listWrapTop listWrapBox mgb0">
		<form name="searchFrm" method="get">
		<input type="hidden" name="excelYn" id="excelYn" />
			<div class="box_sort flex">
				<input type="hidden" name="page" id="page" value="${vo.page}">
				<input type="hidden" name="listRow" value="${not empty param.listRow?param.listRow:10}">
				<div class="box_cate">
					<select name="eduYear" class="srchCtgry wauto mgl10">
						<fmt:formatDate value="<%=new java.util.Date()%>" var="curYear" pattern="yyyy" />
						<fmt:parseNumber value="${curYear}" var="year" />
						<option value="">연도선택</option>
						<c:forEach begin="2023" end="${year+1 }" varStatus="stat">
							<c:set value="${year+2-stat.count  }" var="choiceYear" />
							<option value="${choiceYear}" <c:if test="${choiceYear == vo.eduYear}">selected</c:if>>${choiceYear}년</option>
						</c:forEach>
					</select>
					<%-- 
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
					
					<input readonly type="text" id="srchStartDay" name="srchStartDay" value="${param.srchStartDay }" class="btn04  tl mgr5 datepicker" placeholder="시작일" style="width: 110px;" maxlength="10" />
					~
					<input readonly type="text" id="srchEndDay" name="srchEndDay" value="${param.srchEndDay }" class="btn04  tl mgr5 datepicker" placeholder="종료일" style="width: 110px;" maxlength="10" />
				
					<input type="text" id="srchWrd" name="srchWrd" class="" value="${param.srchWrd }"/>
					<button type="button" onclick="fn_srch();">검색</button>
				</div>
			</div>
		</form>
	</div>
<form id="form-chk" action="statFbDetail.do">

	<div class="tl mgt15 mgb15">
		<label for="listRow" class="sound_only">갯수 선택</label>
		<select id="listRow" class="btn_blackl">
			<option value="10">10</option>
			<option value="20">20</option>
			<option value="30">30</option>
			<option value="40">40</option>
			<option value="50">50</option>
		</select>
	</div>
		
	<table class="w100 tb01">
		<caption class="sound_only">개인교육  테이블</caption>
		<thead>
				<tr>
				<th><input type="checkbox" id="allChk"/></th>
				<th scope="col" class="vm">순번</th>
				<th scope="col" class="vm">만족도조사명</th>
				<%-- th scope="col" class="vm">연도</th --%>
				<%-- th scope="col" class="vm">학기</th --%>
				<%--th scope="col" class="vm">과정</th--%>
				<th scope="col" class="vm">교육명</th>
				<th scope="col" class="vm">교육일</th>
				<th scope="col" class="vm">정원</th>
				<th scope="col" class="vm">승인인원</th>
				<th scope="col" class="vm">설문인원</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="o" items="${data.pageList}" varStatus="s">
				<tr>
					<td><input type="checkbox" name="eduSeqChk" value="${o.eduSeq }"/></td>
					<td>${data.pageNavi.totalRecordCount - (((data.pageNavi.currentPageNo-1)*data.pageNavi.recordCountPerPage) + s.index)}</td>
					<td>${o.fbTitle }</td>
					<%-- td>${o.eduYear }</td--%>
					<%-- td>${vcp.getEduTermNm(o.eduTerm)} </td--%>
					<%-- >td>${o.detailCtgryNm }</td --%>
					<td>${o.eduNm }</td>
					<td>${o.eduPeriodBegin} ~ ${o.eduPeriodEnd }</td>
					<td>${o.personnel }</td>
					<td>${o.stdntCnt }</td>
					<td>${o.surveyCnt }</td>
				</tr>
				
			</c:forEach>
			<c:if test="${empty data.pageList }">
				<tr>
					<td colspan="11">내역이 없습니다.</td>
				</tr>
			</c:if>
		</tbody>
	</table>
</form>
	<div class="fr tc">
		<button class="btn01 btn_greenl" onclick="fn_statFbDetail()" type="button">만족도 조사 통계</button>
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
	$("#srchCtgry,#srchCtgry2,#srchCtgry3").change(function() {
		$("form[name='searchFrm']").submit();
	});
	$("#listRow").change(function() {
		$('[name=listRow]').val($('#listRow').val());
		$("form[name='searchFrm']").submit();
	});
	
	$('.datepicker').datepicker();
	$('#allChk').click(function(o){
		if(this.checked){
			$('input[name=eduSeqChk]').prop('checked',true);
		}else{
			$('input[name=eduSeqChk]').prop('checked',false);
		}
	});
});


function fn_paging(page) {
	$("#page").val(page);
	$("form[name='searchFrm']").submit();
}
function fn_srch() {
	$("#page").val(1);
	$("form[name='searchFrm']").submit();
}

function fn_statFbDetail(){
	if(!$('input[name=eduSeqChk]:checked').length>0){
		alert('교육을 선택하세요');
		return;
	}
	
	var eduSeqArr = [];
	$('input[name=eduSeqChk]').each(function(){
		if(this.checked){
			eduSeqArr.push(this.value);
		}
	});
	console.log(eduSeqArr);
	
	
	$('#form-chk').attr('action','statFbLctreDetail.do');
	$('#form-chk').submit();
}
</script>

