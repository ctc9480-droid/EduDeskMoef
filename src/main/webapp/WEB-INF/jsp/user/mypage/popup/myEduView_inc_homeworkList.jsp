<%@page import="com.educare.edu.member.service.SessionUserInfoHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>     
<table class="tb02 mt30">
     <a id="lectureBoardListTop"></a>
	<tr>
		<th width="50px">NO</th>
		<th>제목</th>
		<th width="100px">작성자</th>
		<th width="89px">작성일</th>
		<th width="50px" style="border-right: 0px;">조회수</th>
	</tr>
	<c:forEach items="${lectureHomeworkList }" var="list">
		<tr>
			<td>${(pageNavi.totalRecordCount+1)-list.rNum }</td>
			<td class="tl"><a href="javascript:fn_homeworkView('${list.idx }');">
			<c:choose>
			<c:when test="${ list.scrtyYn eq 'Y' }">
			<img src="${utcp.ctxPath}/resources/user/image/icon/icon_online.png" alt="자물쇠 아이콘" />
			<c:if test="${!list.myReg }">
			비밀글 입니다. 
			</c:if>
			<c:if test="${list.myReg }">
			${list.title }
			</c:if>
			</c:when>
			<c:otherwise>
			${list.title }
			</c:otherwise>
			</c:choose>
			<c:if test="${list.commentCnt>0 }">
			<span>(<strong class="fc_red">${list.commentCnt }</strong>)</span>
			</c:if>
			</a></td>
			<td>${list.regNm }</td>
			<td>${utcp.convDateToStr(utcp.convStrToDate(list.regDtime,'yyyyMMddHHmmssSSS'),'yyyy.MM.dd') }</td>
			<td style="border-right: 0px;">${list.hit }</td>
		</tr>
	</c:forEach>
	<c:if test="${empty lectureHomeworkList }">
		<tr><td colspan="5">게시물이 없습니다.</td></tr>
	</c:if>
</table>
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
		<div class="w100 tr">
                        <a href="javascript:fn_homeworkReg();" class="btn02 btn_blue">작성</a>
        </div>
	</div>
<script>
	function fn_homeworkView(idx){
		//alert("fn_homeworkView called");
		$('#tabAction').val('homeworkView');
		$('#boardIdx').val(idx);
		$('#myEduForm').submit();
	}
	function fn_homeworkReg(){
		  document.myEduForm.boardIdx.value = 0;
		$('#tabAction').val('homeworkReg');
		$('#myEduForm').submit();
	}
	function fnc_paging(pageNo) {
		  document.myEduForm.pageNo.value = pageNo;
		  document.myEduForm.submit();
	}
</script>

