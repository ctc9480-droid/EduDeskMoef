<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<div class="page">
	<div class="inner cf">
		<c:if test="${pageNavi.currentPageNo != 1}">
			<div class="page_prev0"><a href="#" onClick="fnc_paging('${pageNavi.currentPageNo - 1}')">&lt; 이전</a></div>
		</c:if>
		<c:forEach var="pageNo" begin="${pageNavi.firstPageNoOnPageList}" end="${pageNavi.lastPageNoOnPageList}">
			<c:choose>
				<c:when test="${pageNo == pageNavi.currentPageNo}">
					<div class="page_now"><a href="#">${pageNo}</a></div>
				</c:when>
				<c:otherwise>
					<div class="page_nomal"><a href="#" onClick="fnc_paging('${pageNo}')">${pageNo}</a></div>
				</c:otherwise>
			</c:choose>
		</c:forEach>
		<c:if test="${pageNavi.currentPageNo != pageNavi.totalPageCount && pageNavi.totalPageCount > 0}">
			<div class="page_next0"><a href="#" onClick="fnc_paging('${pageNavi.currentPageNo + 1}')">다음 &gt;</a></div>
		</c:if>
	</div>
</div>