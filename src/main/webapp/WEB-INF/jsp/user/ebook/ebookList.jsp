<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<section class="pd025 pd200 po_re">
	<div class="listWrap">
		<span class="tb_text"> 총 <strong class="fc_red">${pageNavi.totalRecordCount }</strong> 건
		</span>
		<form name="search_form" method="get">
			<div class="searchBox cf">
				<div class="box_sort cf">
					<div class="box_search">
						<input type="text" id="searchWord" name="searchWord" value="${vo.searchWord }">
						<label for="searchWord" class="sound_only">검색어 입력</label>
						<button type="submit">검색</button>
					</div>
				</div>
			</div>
		</form>
		
		<!--// ebookList(s) //-->
		<div class="ebookList">
			<ul class="ebookul cf">
				<c:forEach items="${ebookList }" var="o" varStatus="s">
				<li class="ebookli">
					<a href="${utcp.ctxPath}/resources/plugins/flip_book/3d-flip-book/index.html?idx=${o.idx }" class="ebooka" target="_blank">
						<c:choose>
						<c:when test="${not empty o.thumRenm }">
						<img alt="" src="${utcp.getCdnUrl('upload/ebook/') }${o.thumRenm}" />
						</c:when>
						<c:otherwise>
						<img alt="이북 기본 섬네일" src="${utcp.ctxPath}/resources/user/image/img/thumb01.jpg" />
						</c:otherwise>
						</c:choose>
						<span class="ebookspan">
							${o.title }
						</span>
					</a>
					<button type="button" class="ebookBtn" onclick="location.href=encodeURI('${utcp.ctxPath}/admin/cloud/download.do?cloudPath=upload/ebook/&cloudFile=${o.fileRenm}&downNm=${o.fileNm }');"><i class="fas fa-download"></i></button>
				</li>
				</c:forEach>
				<c:if test="${empty ebookList}">
					<div class="w100">
						<p class="pdt30 pdb30">데이터가 없습니다.</p>
					</div>
				</c:if>
			</ul>
		</div>
		<!--// ebookList(e) //-->

		<div class="fl tc">
		</div>
		<div class="fr tc">
			<!-- <button type="button" onclick="location.href='${utcp.ctxPath}/admin/ebook/ebookWrite.do'" class="btn01 btn_greenl">작성</button> -->
		</div>
		<jsp:include page="/WEB-INF/jsp/admin/ebook/pageNavi.jsp" />
	</div>
</section>
<script>
	$(document).ready(function() {
	});
	
	function fnc_paging(pageNo) {
		document.search_form.pageNo.value = pageNo;
		document.search_form.submit();
	}
	
</script>


