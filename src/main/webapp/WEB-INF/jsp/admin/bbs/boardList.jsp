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
		<span class="tb_text"> 총 <strong class="fc_red">${pageNavi.totalRecordCount + fn:length(noticeList) }</strong> 건
		</span>
		<div class="tr mgb15">
			<form name="search_form" method="get">
				<input type="hidden" id="pageNo" name="pageNo" value="1" /> <label for="sch_select" class="sound_only">검색대상선택</label> <select name="searchSelect" class="btn04 btn_blackl">
					<option value="title" ${vo.searchSelect eq 'title'?'selected':'' }>제목</option>
					<option value="content" ${vo.searchSelect eq 'content'?'selected':'' }>내용</option>
				</select> <label for="searchWord" class="sound_only">검색어입력</label> <input id="searchWord" name="searchWord" value="${vo.searchWord }" type="text" class="btn04 btn_blackl tl mgr5" placeholder="검색" />
				<button class="btn04 btn_black fr" type="submit">검색</button>
			</form>
		</div>
		<table width="100%" class="tb01 tc tb_fix">
			<caption class="sound_only">게시판테이블</caption>
			<colgroup>
				<col width="">
				<c:if test="${boardMst.useThumb == 1 }">
				<col width="">
				</c:if>
				<col width="50%">
				<col width="">
				<col width="">
				<col width="">
				<col width="">
				<col width="">
			</colgroup>
			<thead bgcolor="#f7f8fa">
				<tr>
					<th>NO</th>
					<c:if test="${boardMst.useThumb == 1 }">
					<th>썸네일</th>
					</c:if>
					<th>제목</th>
					<th>상단고정</th>
					<th>게시여부</th>
					<th>작성자</th>
					<th>작성일</th>
					<th>조회수</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${noticeList }" var="map">
					<tr>
						<td>공지</td>
						<c:if test="${boardMst.useThumb == 1 }">
						<td class="tl" style="${map.status == 2?'text-decoration: line-through;':''}">
						<c:if test="${not empty map.thumbFile }">
						<img src="${utcp.getCdnUrl('upload/board/') }${map.thumbFile}" height="50"/>
						</c:if>
						</td>
						</c:if>
						<td class="tl"  style="${map.status == 2?'text-decoration: line-through;':''}"><a href="${vo.boardType }View.do?idx=${map.idx }" class="dp_b el ${map.childFlag }">
						
						<c:if test="${not empty map.cate }">[${map.cate}]</c:if>
						${map.title } <c:if test="${map.commentCnt>0 }">
						[${map.commentCnt}]
						</c:if></a>
						</td>
						<td>
						<c:if test="${map.noticeYn eq 'Y' }">○</c:if>
						</td>
						<td>
							<c:if test="${map.status == 1 }">게시</c:if>
							<c:if test="${map.status == 2 }">미게시</c:if>
						</td>
						<td>${map.regNm2 }</td>
						<td>${map.replaceRegDtime('yyyy-MM-dd') }</td>
						<td>${map.hit }</td>
					</tr>
				</c:forEach>
				<c:forEach items="${boardList }" var="map" varStatus="s">
					<tr>
						<td>
						${pageNavi.totalRecordCount-(pageNavi.firstRecordIndex+s.index) }
						</td>
						<c:if test="${boardMst.useThumb == 1 }">
						<td class="tl">
						<c:if test="${not empty map.thumbFile }">
						<img src="${utcp.getCdnUrl('upload/board/') }${map.thumbFile}" height="50"/>
						</c:if>
						</td>
						</c:if>
						<td class="tl" style="${map.status == 2?'text-decoration: line-through;':''}"><a href="${vo.boardType }View.do?idx=${map.idx }" class="dp_b el ${map.childFlag }">
						<c:if test="${not empty map.cate }">[${map.cate}]</c:if>
						${map.title } <c:if test="${map.commentCnt>0 }">
						[${map.commentCnt}]
						</c:if></a>
						</td>
						<td>
						<c:if test="${map.noticeYn eq 'Y' }">○</c:if>
						</td>
						<td>
							<c:if test="${map.status == 1 }">게시</c:if>
							<c:if test="${map.status == 2 }">미게시</c:if>
						</td>
						<td>
						<c:choose>
						<c:when test="${boardMst.permTp == 9 }">
						${map.regNm }
						</c:when>
						<c:otherwise>
						${map.regNm2 }
						</c:otherwise>
						</c:choose>
						</td>
						<td>${map.replaceRegDtime('yyyy-MM-dd') }</td>
						<td>${map.hit }</td>
					</tr>
				</c:forEach>
				<c:if test="${empty boardList and empty noticeList}">
				<tr><td colspan="7" class="h200">데이터가 없습니다</td></tr>
				</c:if>
			</tbody>
		</table>
		<div class="fr tc">
			<c:if test="${boardMst.boardType ne 'hope' }">
			<button onclick="location.href='${vo.boardType }Write.do'" class="btn01 btn_greenl">
				등록
			</button>
			</c:if>
		</div>
		<jsp:include page="/WEB-INF/jsp/admin/bbs/pageNavi.jsp" />
	</div>
</section>