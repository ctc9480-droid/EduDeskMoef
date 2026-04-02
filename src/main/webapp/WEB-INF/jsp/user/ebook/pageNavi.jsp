<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<div class="board_pager" style="margin-bottom:40px;">
	<div class="pagination">
		<c:if test="${pageNavi.currentPageNo != 1}">
			<a href="#" onClick="fnc_paging('${pageNavi.currentPageNo - 1}')">&laquo;</a>
		</c:if>
		<c:forEach var="pageNo" begin="${pageNavi.firstPageNoOnPageList}" end="${pageNavi.lastPageNoOnPageList}">
			<c:choose>
				<c:when test="${pageNo == pageNavi.currentPageNo}">
					<a href="#" class="active">${pageNo}</a>
				</c:when>
				<c:otherwise>
					<a href="#" onClick="fnc_paging('${pageNo}')">${pageNo}</a>
				</c:otherwise>
			</c:choose>
		</c:forEach>
		<c:if test="${pageNavi.currentPageNo != pageNavi.totalPageCount && pageNavi.totalPageCount > 0}">
			<a href="#" onClick="fnc_paging('${pageNavi.currentPageNo + 1}')">&raquo;</a>
		</c:if>
	</div>
</div>