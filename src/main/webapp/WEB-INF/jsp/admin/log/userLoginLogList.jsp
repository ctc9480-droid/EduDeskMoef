<%@page import="java.util.Map"%>
<%@page import="org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<jsp:useBean id="curDate" class="java.util.Date" />

<section class="pd025 pd200 po_re">
	<div class="listWrap listWrapTop listWrapBox">
		<form name="searchFrm" method="get" action="${utcp.ctxPath}/admin/log/userLoginLogList.do">
			<div class="box_sort flex">
				<input type="hidden" name="page" id="page" value="${vo.page}">
				<div class="box_search">
					<input readonly type="text" id="srchBeginDt" name="srchBeginDt" value="${param.srchBeginDt }" class="btn04  tl mgr5 datepicker" placeholder="시작일" style="width: 110px;" maxlength="10" />
					~
					<input readonly type="text" id="srchEndDt" name="srchEndDt" value="${param.srchEndDt }" class="btn04  tl mgr5 datepicker" placeholder="종료일" style="width: 110px;" maxlength="10" />
					
					<input type="text" id="loginId" name="loginId" value="${param.loginId }" class="btn04  tl mgr5" placeholder="아이디" style="width: 110px;" maxlength="10" />
					<input type="text" id="userNm" name="userNm" value="${param.userNm }" class="btn04  tl mgr5" placeholder="이름" style="width: 110px;" maxlength="10" />
					<%-- <input type="text" id="srchWrd" name="srchWrd" class="" value="${param.srchWrd }"/> --%>
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
				<th scope="col" class="vm">아이디</th>
				<th scope="col" class="vm">이름</th>
				<th scope="col" class="vm">구분</th>
				<th scope="col" class="vm">IP</th>
				<th scope="col" class="vm">디바이스</th>
				<th scope="col" class="vm">브라우저</th>
				<th scope="col" class="vm">접속일시</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="o" items="${data.LoginLogList}" varStatus="s">
				<c:set var="rowspanCnt" value="1" />
				<tr>
					<td rowspan="${rowspanCnt }">${data.pageNavi.totalRecordCount - (((data.pageNavi.currentPageNo-1)*data.pageNavi.recordCountPerPage) + s.index)}</td>
					<td rowspan="${rowspanCnt }">${o.loginId }</td>
					<td rowspan="${rowspanCnt }">${o.userNm }</td>
					<td rowspan="${rowspanCnt }">${o.role eq "A"?"관리자": o.role eq "U"?"일반":""}</td>
					<td rowspan="${rowspanCnt }">${o.ip }</td>
					<td rowspan="${rowspanCnt }">${o.device eq "P"?"PC":""}${o.device eq "M"?"MOBILE":""}${o.device eq "T"?"TABLET":""}</td>
					<td rowspan="${rowspanCnt }">${o.browser}</td>
					<td rowspan="${rowspanCnt }">${utcp.convDateToStr(o.loginDe,'yyyy-MM-dd HH:mm') }</td>
				</tr>
			</c:forEach>
			<c:if test="${empty data.LoginLogList }">
				<tr>
					<td colspan="11">내역이 없습니다.</td>
				</tr>
			</c:if>
		</tbody>
	</table>

	<div class="fr tc">
		<button class="btn01 btn_greenl" onclick="fn_downTmrcpExcel()" type="button">엑셀</button>
	</div>
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
	
	<%-- dykim, 24/06/20 
	<div class="fr tc">
		<input type="file" name="ccExcel" id="ccExcel"/>
		<button type="button" class="btn01 btn_green" onclick="fn_importTimeExcel();">시간표 액셀 업로드</button>
		<br><br>
		<input type="file" name="stdntExcel" id="stdntExcel"/>
		<button type="button" class="btn01 btn_green" onclick="fn_importStdntExcel();">수강생 액셀 업로드</button>
	</div>
	--%>

</section>

<script>

// dykim, 24/06/20
function fn_importTimeExcel(){
	if($("#ccExcel").val() == ""){
		//vm_alert.msg='파일을 선택하세요.';
		//location.href = "#md-alert";
		alert("파일을 선택하세요.");
		return false;
	}
	
    var formData = new FormData();
    formData.append("fileObj", $("#ccExcel")[0].files[0]);

    $.ajax({
        url: "/admin/excel/importLectureTimeExcel.ajax",
        processData: false,
        contentType: false,
        data: formData,
        type: 'POST',
        success: function(r) {
        	if(r.result == 1){
				//성공
				alert("Success");
				location.reload();
			}
        	else {
        		alert(r.msg);
        	}
    	}
    });
}

//dykim, 24/06/20
function fn_importStdntExcel(){
	if($("#stdntExcel").val() == ""){
		//vm_alert.msg='파일을 선택하세요.';
		//location.href = "#md-alert";
		alert("파일을 선택하세요.");
		return false;
	}
	
    var formData = new FormData();
    formData.append("fileObj", $("#stdntExcel")[0].files[0]);

    $.ajax({
        url: "/admin/excel/importLectureStdntExcel.ajax",
        processData: false,
        contentType: false,
        data: formData,
        type: 'POST',
        success: function(r) {
        	if(r.result == 1){
				//성공
				alert("Success");
				location.reload();
			}
        	else {
        		alert(r.msg);
        	}
    	}
    });
}

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
function fn_downTmrcpExcel(){
	$('form[name=searchFrm]').attr('action','${utcp.ctxPath}/admin/excel/loginLogExcel.do');
	$('form[name=searchFrm]').submit();
	$('form[name=searchFrm]').attr('action','loginLogExcel.do');
}
</script>

