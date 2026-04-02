<%@page import="org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo"%>
<%@page import="java.util.Map"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<jsp:useBean id="curDate" class="java.util.Date" />



<section class="pd025 pd200 po_re">
	<div class="listWrap listWrapTop listWrapBox">
		<form name="searchFrm" method="get">
			<input type="hidden" id="i-idx" name="idx" value="${ip.idx }"/>
			<div class="box_sort flex">
				<input type="hidden" name="page" id="page" value="${param.page}">
				<div class="box_search">
					<input type="text" id="i-ip4" value="${ip.ip4 }" class="btn04  tl mgr5 " placeholder="000.000.000.000" style="width: 210px;" maxlength="20" />
					<input type="text" id="i-memo" value="${ip.memo }" class="btn04  tl mgr5 " placeholder="메모" style="width: 310px;" maxlength="30" />
					<button type="button" onclick="fn_accip_saveIp()">등록</button>
					
				</div>
				<div class="box_search">
					<input type="text" id="srchWrd" name="srchWrd" value="${param.srchWrd }"  placeholder="검색어" maxlength="20" />
					<button>검색</button>
				</div>
			</div>
		</form>
	</div>

	<table class="w100 tb01">
		<caption class="sound_only">단체교육 신청내역 테이블</caption>
		<thead>
			<tr>
				<th scope="col" class="vm">순번</th>
				<th scope="col" class="vm">아이피</th>
				<th scope="col" class="vm">메모</th>
				<th scope="col" class="vm">등록일시</th>
				<th scope="col" class="vm"></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="o" items="${data.list}" varStatus="s">
				<c:set var="rowspanCnt" value="1" />
				<tr>
					<td>${data.pageNavi.totalRecordCount - (((data.pageNavi.currentPageNo-1)*data.pageNavi.recordCountPerPage) + s.index)}</td>
					<td>${o.ip4 }</td>
					<td>${o.memo }</td>
					<td>${utcp.convDateToStr(o.regDt,'yyyy-MM-dd HH:mm') }</td>
					<td>
						<a href="javascript:fn_accip_updIp(${o.idx })" class="btn04 btn_green">수정</a>
						<a href="javascript:fn_accip_delIp(${o.idx })" class="btn04 btn_green">삭제</a>
					</td>
				</tr>
			</c:forEach>
			<c:if test="${empty data.list }">
				<tr>
					<td colspan="11">내역이 없습니다.</td>
				</tr>
			</c:if>
		</tbody>
	</table>

	<!--// paging //-->
	<%-- 
		--%>
	<c:if test="${not empty data.pageNavi }">
	<c:set var="pageNavi" value="${data.pageNavi }" />
	<%
	PaginationInfo pageNavi = (PaginationInfo)pageContext.getAttribute("pageNavi");
	request.setAttribute("pageNavi", pageNavi);
	%>
	<jsp:include page="/WEB-INF/jsp/admin/common/paging.jsp" />
	<!--// paging //-->
	</c:if>

</section>

<script>
$(function() {
	
});
function fn_paging(page) {
	$("#page").val(page);
	$("form[name='searchFrm']").submit();
}
function fn_accip_updIp(idx){
	$("#i-idx").val(idx);
	$("form[name='searchFrm']").submit();
}
function fn_accip_delIp(idx){
// 	var ip4 = $('#i-ip4').val();
// 	var ipCheck = isValidIP(ip4);
// 	if(!ipCheck){
// 		alert('유효한 아피주소 형식이 아닙니다.');
// 		return;
// 	}
// 	var idx = $('#i-idx').val();
// 	var memo = $('#i-memo').val();
	if(confirm("정말 삭제하시겠습니까?")) {
		$.ajax({
			type : 'post',
			url : 'delAccIpProc.ajax',
			data : {idx: idx},
			success : function (r){
				if(r.result == 1){
					alert('삭제되었습니다.');
					$("#i-idx").val(0);
					$("form[name='searchFrm']").submit();
				}else{
					alert(r.msg);
				}
			}
		});
	}else{
		return false;
	}
}

function fn_accip_saveIp(){
	var ip4 = $('#i-ip4').val();
	var ipCheck = isValidIP(ip4);
	if(!ipCheck){
		alert('유효한 아피주소 형식이 아닙니다.');
		return;
	}
	var idx = $('#i-idx').val();
	var memo = $('#i-memo').val();
	
	$.ajax({
		type : 'post',
		url : 'saveAccIpProc.ajax',
		data : {idx: idx,ip4: ip4, memo: memo},
		success : function (r){
			if(r.result == 1){
				alert('저장되었습니다.');
				$("#i-idx").val(0);
				$("form[name='searchFrm']").submit();
			}else{
				alert(r.msg);
			}
		}
	});
}
function isValidIP(ip) {
    // IP 주소 정규식
    var ipRegex = /^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/;
    return ipRegex.test(ip);
}
</script>

