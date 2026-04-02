<%@page import="org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp" %>
<%
PaginationInfo o = (PaginationInfo)request.getAttribute("pageNavi");
int prev1=o.getCurrentPageNo()-o.getPageSize();
int next1=o.getCurrentPageNo()+o.getPageSize();
if(prev1<0){
	prev1=1;
}
if(next1>o.getLastPageNo()){
	next1=o.getLastPageNo();
}
request.setAttribute("prev1", prev1);
request.setAttribute("next1", next1);
request.setAttribute("o", o);
%>

<div class="page">
	<div class="inner cf">
		<c:if test="${pageNavi.currentPageNo != 1}">
			<div class="page_prev0">
				<a href="javascript:;" onclick="fn_paging('1'); return false;">&lt; 앞</a>
			</div>
			<div class="page_prev0">
				<a href="javascript:;" onclick="fn_paging('${o.currentPageNo-1}'); return false;">&lt; 이전</a>
			</div>
		</c:if>
		<c:forEach var="pageNo" begin="${pageNavi.firstPageNoOnPageList}" end="${pageNavi.lastPageNoOnPageList}">
			<c:choose>
				<c:when test="${pageNo == pageNavi.currentPageNo}">
					<div class="page_now"><a href="javascript:;">${pageNo}</a></div> 
				</c:when>
				<c:otherwise>
					<div class="page_nomal">
						<a href="javascript:;" onclick="fn_paging('${pageNo}'); return false;")">${pageNo}</a>
					</div> 
				</c:otherwise>
			</c:choose>
		</c:forEach>
		<c:if test="${pageNavi.currentPageNo != pageNavi.totalPageCount && pageNavi.totalPageCount > 0}">
			<div class="page_next0">
				<a href="javascript:;" onclick="fn_paging('${o.currentPageNo+1}'); return false;">다음 &gt;</a>
			</div>
			<div class="page_next0">
				<a href="javascript:;" onclick="fn_paging('${o.lastPageNo}'); return false;">끝 &gt;</a>
			</div>
		</c:if>
	</div>
</div>