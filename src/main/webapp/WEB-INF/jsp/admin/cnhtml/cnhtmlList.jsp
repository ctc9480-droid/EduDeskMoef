<%@page import="com.educare.component.VarComponent"%>
<%@page import="org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<!-- 현재url 세팅,작업중 -->
<c:set var="SERVLET_PATH" value="${requestScope['javax.servlet.forward.servlet_path']}" /> 
<c:set var="QUERY_STRING" value="${requestScope['javax.servlet.forward.query_string']}" /> 
<c:set var="SPQR" value="${SERVLET_PATH }?${QUERY_STRING }"/>

<script>
	function fnc_paging(pageNo) {
		document.search_form.pageNo.value = pageNo;
		document.search_form.submit();
	}
</script>
<section class="pd025 pd200 po_re">
	<div class="po_re br5 bs_box">
		<span class="tb_text"> 총 <strong class="fc_red">${data.pageNavi.totalRecordCount }</strong> 건
		</span>
		<div class="tr mgb15">
			<form name="search_form" method="get">
				<input type="hidden" id="pageNo" name="pageNo" value="1" /> 

				<select name="srchCnhtType">
					<option value="">컨텐츠전체</option>
					<c:forEach var="entry" items="${vcp.getCnhtTypeList()}">
						<option value="${entry.key}" ${param.srchCnhtType eq entry.key?'selected':'' }>${entry.value}</option>
					</c:forEach>
				</select>

				<input id="srchWrd" name="srchWrd" value="${param.srchWrd }" type="text" class="btn04 btn_blackl tl mgr5" placeholder="검색" />
				<button class="btn04 btn_black fr" type="submit">검색</button>
			</form>
		</div>
		<table width="100%" class="tb01 tc tb_fix">
			<caption class="sound_only">게시판테이블</caption>
			<colgroup>
				<col width="">
				<col width="">
				<col width="">
				<col width="">
				<col width="">
			</colgroup>
			<thead bgcolor="#f7f8fa">
				<tr>
					<th>컨텐츠명</th>
					<th>제목</th>
					<th>게시여부</th>
					<th>작성자</th>
					<th>작성일</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${data.list }" var="o">
					<tr>
						
						<td class="tl">
						${vcp.getCnhtTypeNm(o.cnhtType) }
						
						</td>
						<td><a href="cnhtmlWrite.do?cnhtSeq=${o.cnhtSeq }" >${o.title } </a></td>
						<td>
							<c:if test="${o.state == 1 }">게시</c:if>
							<c:if test="${o.state == 2 }">미게시</c:if>
						</td>
						<td>${o.regNm }</td>
						<td>${utcp.convDateToStr(o.regDe,'yyyy.MM.dd') }</td>
					</tr>
				</c:forEach>
				
				<c:if test="${empty data.list}">
				<tr><td colspan="4" class="h200">데이터가 없습니다</td></tr>
				</c:if>
			</tbody>
		</table>
		<div class="fr tc">
			<button onclick="location.href='cnhtmlWrite.do'" class="btn01 btn_greenl">
				등록
			</button>
		</div>
		
		<c:if test="${not empty data.pageNavi }">
		<c:set var="pageNavi" value="${data.pageNavi }" />
		<%
		PaginationInfo pageNavi = (PaginationInfo)pageContext.getAttribute("pageNavi");
		request.setAttribute("pageNavi", pageNavi);
		%>
		<jsp:include page="/WEB-INF/jsp/admin/common/paging.jsp" />
		<!--// paging //-->
		</c:if>
		
	</div>
</section>