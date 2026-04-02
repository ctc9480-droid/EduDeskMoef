<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<script>
	$(document).ready(function() {
	});
	
	function fnc_paging(pageNo) {
		document.search_form.pageNo.value = pageNo;
		document.search_form.submit();
	}
	
</script>
<section class="pd025 pd200 po_re">
	<div class="po_re br5 bs_box">
		<span class="tb_text"> 총 <strong class="fc_red">${pageNavi.totalRecordCount }</strong> 건
		</span>
		<form name="search_form" method="get">
			<input type="hidden" id="pageNo" name="pageNo" value="1" />
			<div class="tr mgb15">
				<label for="searchWord" class="sound_only">검색어입력</label> <input id="searchWord" name="searchWord" value="${vo.searchWord }" type="text" class="btn04 btn_blackl tl mgr5" />
				<button class="btn04 btn_black fr" type="submit">검색</button>
			</div>
		</form>
		<table width="100%" class="tb01 tc tb_fix">
			<caption class="sound_only">팝업테이블</caption>
			<colgroup>
				<col width="50px" />
				<col width="" />
				<col width="" />
				<col width="" />
				<col width="70px" />
				<col width="70px" />
				<col width="100px" />
			</colgroup>
			<thead bgcolor="#f7f8fa">
				<tr>
					<th>NO</th>
					<th>미리보기</th>
					<th>배너 제목</th>
					<th>게시기간</th>
					<th>순서</th>
					<th>게시여부</th>
					<th>작성일</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${bannerList }" var="o" varStatus="s">
					<tr>
						<td>
						${pageNavi.totalRecordCount-(pageNavi.firstRecordIndex+s.index) }
						</td>
						<td><img src="${utcp.getCdnUrl('upload/web/banner/') }${o.fileImgRenm}" height="50"/></td>
						<td class="tl el"><a href="bannerWrite.do?idx=${o.idx }">${o.title }</a></td>
						<td>${ utcp.convDateToStr(utcp.convStrToDate(o.startDtime,'yyyyMMddHHmmss'),'yyyy.MM.dd HH:mm')} ~
						${utcp.convDateToStr(utcp.convStrToDate(o.endDtime,'yyyyMMddHHmmss'),'yyyy.MM.dd HH:mm') }</td>
						<td>${o.sort }</td>
						<td>${o.addStatusNm}</td>
						<td>${utcp.convDateToStr(o.regDt,'yyyy.MM.dd')}</td>
					</tr>
				</c:forEach>
				<c:if test="${empty bannerList}">
					<tr>
						<td colspan="6">데이터가 없습니다</td>
					</tr>
				</c:if>
			</tbody>
		</table>
		<div class="fl tc">
		</div>
		<div class="fr tc">
			<button type="button" onclick="location.href='${utcp.ctxPath}/admin/bbs/bannerWrite.do'" class="btn01 btn_greenl">작성</button>
		</div>
		<jsp:include page="/WEB-INF/jsp/admin/bbs/pageNavi.jsp" />
	</div>
</section>



