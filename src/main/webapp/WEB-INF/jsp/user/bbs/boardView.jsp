<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<script>
	function fn_del() {
		if(!confirm('삭제하시겠습니까?')){return;}
		$('#form_view').attr('method', 'post');
		$('#form_view').attr('action', 'boardDeleteProc.do');
		$('#form_view').submit();
	}
</script>
<form id="form_view">
	<input type="hidden" name="idx" value="${boardMap.idx }" /> 
	<input type="hidden" name="boardType" value="${boardMap.boardType }" />
</form>
<div class="listWrap" style="padding-top: 0px">
	<div class="tstyle_view">
		<div class="title">${boardMap.title }</div>
		<ul class="head info">
			<li><span>작성일</span> <b>${boardMap.replaceRegDtime('yyyy-MM-dd') }</b></li>
			<li>
				<p></p> <span>조회수</span> <b>${boardMap.hit }</b>
			</li>
			<li>
				<p></p> <span>작성자</span> <b>${boardMap.regNm2 }</b>
			</li>
			
		</ul>
		<div class="tb_contents">${boardMap.content }</div>
		<div class="title data_wrap">
		<c:if test="${boardAttachList ne null and boardAttachList ne '[]' and boardAttachList ne ''}">
			<div class="data_tit"><b>첨부파일</b></div>
			<div>
				<ul class="head info">
					<c:forEach items="${boardAttachList }" var="map">
						<li><a href="${utcp.ctxPath}/bbs/comm/download/${map.fileSeq }.do"> <img src="${utcp.ctxPath}/resources/user/image/icon/down_icon.gif" alt="파일 아이콘" /> <b>${map.fileOrg }(${map.replaceFileSize})</b></a></li><br/>
					</c:forEach>
				</ul>
			</div>
		</c:if>
		</div>
		
		<!--이전글,다음글 영역 230830 hy -->
		<c:if test="${vo.boardType eq 'notice' }">
		<div class="bbsViewList">
			<c:if test="${not empty boardMapNext  }">
			<div class="tableRow">
	            <div class="tableCell tableHead">
	                <p class="prev">다음글</p>
	            </div>
	            <div class="tableCell tit">
	                <a href="${vo.boardType }View.do?idx=${boardMapNext.idx }">${boardMapNext.title }</a>
	            </div>
            </div>
            </c:if>
            <c:if test="${not empty boardMapPrev  }">
			<div class="tableRow">
	            <div class="tableCell tableHead">
	                <p class="next">이전글</p>
	            </div>
	            <div class="tableCell tit">
	                <a href="${vo.boardType }View.do?idx=${boardMapPrev.idx }">${boardMapPrev.title }</a>
	            </div>
            </div>
            </c:if>
		</div>
		</c:if>
		<!-- 230830 hy -->
		
		<div class="tb_btn" style="float:left;">
			<ul>
				<li class="left"><a href="${vo.boardType }List.do">목록</a></li>
				<c:if test="${boardMap.gLvl eq '0' and vo.boardType eq 'free' and not empty sessionScope.sessionUserInfo.userId}">
					<li class="right" style="margin-left: 10px;"><a href="${vo.boardType }Write.do?pIdx=${boardMap.idx }">댓글쓰기</a></li>
				</c:if>
				<c:if test="${boardMst.permTp == 9 and boardMap.regId eq sessionScope.sessionUserInfo.userId }">
					<li class="right" style="margin-left: 10px;"><a href="${vo.boardType }Write.do?idx=${boardMap.idx }">수정</a></li>
					<li class="right"><a href="#none" onclick="fn_del()">삭제</a></li>
				</c:if>
			</ul>
		</div>
		
	</div>
</div>
