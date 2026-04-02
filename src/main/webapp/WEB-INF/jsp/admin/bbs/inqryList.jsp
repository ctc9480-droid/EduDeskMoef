<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<script>
	function fnc_paging(pageNo) {
		document.search_form.pageNo.value = pageNo;
		document.search_form.submit();
	}
</script>
<section class="pd025 pd200 po_re">
	<div class="po_re br5 bs_box">
		<span class="tb_text"> 총 <strong class="fc_red">${pageNavi.totalRecordCount }</strong> 건
		</span>
		<div class="tr mgb15">
			<form name="search_form" method="get">
				<input type="hidden" id="pageNo" name="pageNo" value="1" /> <label for="sch_select" class="sound_only">검색대상선택</label> <select name="searchSelect" class="btn04 btn_blackl">
					<option value="title" ${vo.searchSelect eq 'title'?'selected':'' }>제목</option>
					<option value="content" ${vo.searchSelect eq 'content'?'selected':'' }>내용</option>
				</select> <label for="searchWord" class="sound_only">검색어입력</label> <input id="searchWord" name="searchWord" value="${vo.searchWord }" type="text" class="btn04 btn_blackl tl mgr5" />
				<button class="btn04 btn_black fr" type="submit">검색</button>
			</form>
		</div>
		<table width="100%" class="tb01 tc tb_fix">
			<caption class="sound_only">온라인문의테이블</caption>
			<colgroup>
				<col width="7%" />
				<col width="*" />
				<col width="10%" />
				<col width="15%" />
				<col width="12%" />
				<col width="12%" />
			</colgroup>
			<thead bgcolor="#f7f8fa">
				<tr>
					<th>NO</th>
					<th>제목</th>
					<th>작성자</th>
					<th>비밀번호</th>
					<th>작성일</th>
					<th>상태</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${inqryList }" var="map" varStatus="s">
					<tr>
						<td>
						${pageNavi.totalRecordCount-(pageNavi.firstRecordIndex+s.index) }
						</td>
						<td class="tl"><a href="inqryView.do?idx=${map.idx }">${map.title }</a></td>
						<td>${map.regNm }</td>
						<td>${not empty map.regId?'회원': map.password }</td>
						<td>${map.replaceRegDtime('yyyy.MM.dd HH:mm') }</td>
						<td>${map.statusNm }</td>
					</tr>
				</c:forEach>
				<c:if test="${empty inqryList}">
				<tr><td colspan="6">데이터가 없습니다</td></tr>
				</c:if>
			</tbody>
		</table>
		<div class="fl tc">
		</div>
		<div class="fr tc">
			<!-- <a href="${utcp.ctxPath}/admin/bbs/inqryWrite.do" class="btn01 btn_greenl">등록</a> -->
		</div>
		<jsp:include page="/WEB-INF/jsp/admin/bbs/pageNavi.jsp" />
	</div>
</section>