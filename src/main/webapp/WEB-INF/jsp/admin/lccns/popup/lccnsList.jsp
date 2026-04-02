<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<section class="pd30">
	<div class="po_re br5 bs_box">
		<form id="form-srch">
			<input type="hidden" id="i-eduSeq" name="eduSeq" value="${param.eduSeq }"/>
			<input type="hidden" id="i-userId" name="userId" value="${param.userId }"/>
			<input type="hidden" name="pageNo" />
			
			<div class="fl tr mgb15">
				<label for="srchWrd" class="sound_only">검색어입력</label>
				<input type="text" id="srchWrd" name="srchWrd" value="${param.srchWrd }" placeholder="검색" class="btn04  tl mgr5">
				<button type="button" onclick="fn_srch();" class="btn04 btn_black fr">검색</button>
			</div>
			<div class="fr tc mgb15">
				<a href="javascript:fn_regForm(0);" class="btn01 btn_blue">상담등록</a>
			</div>
		</form>
		<table class="w100 tb01 tc">
			<thead>
				<tr>
					<th>번호</th>
					<th>상담제목</th>
					<th>작성자</th>
					<th>등록일</th>
					<th></th>
				</tr>
			</thead>
			<c:forEach items="${data.list}" var="o" varStatus="s">
				<tr>
					<td>${data.pageNavi.totalRecordCount-(data.pageNavi.firstRecordIndex+s.index) }</td>
					<td class="tl">${o.title }</td>
					<td>${o.regNm }</td>
					<td>${utcp.convDateToStr(o.regDe,'yyyy-MM-dd HH:mm') }</td>
					<td><button type="button" onclick="fn_regForm(${o.cnsSeq});" class="btn04 btn_orange">수정</button></td>
				</tr>
			</c:forEach>
			<c:if test="${empty data.list }">
				<tr>
					<td colspan="7">상담내역이 없습니다.</td>
				</tr>
			</c:if>
		</table>

		<c:set var="pageNavi" value="${data.pageNavi }" />
		<%request.setAttribute("pageNavi", pageContext.getAttribute("pageNavi"));%>
		<jsp:include page="/WEB-INF/jsp/admin/bbs/pageNavi.jsp" />
	</div>
</section>
<script>
function fn_regForm(cnsSeq){
	var userId = $('#i-userId').val();
	var eduSeq = $('#i-eduSeq').val();
	location.href = 'lccnsReg.do?cnsSeq='+cnsSeq+'&userId='+userId+'&eduSeq='+eduSeq;
}
function fnc_paging(pageNo){
	$('input[name=pageNo]').val(pageNo);
	$('#form-srch').submit();
}
function fn_srch(){
	fnc_paging(1);
}
</script>
