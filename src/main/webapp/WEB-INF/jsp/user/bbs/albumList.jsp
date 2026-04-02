<%@page import="java.util.Iterator"%>
<%@page import="java.util.Enumeration"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>


<div class="listWrap pdt0">
	<form name="search_form" method="get" class="w100">
		<input type="hidden" name="fileTypeGb" />
		<input type="hidden" id="pageNo" name="pageNo" value="1" />
		<c:if test="${not empty boardMst.addCateArr }">
			<div class="selectBox cf">
				<label for="cate" class="sound_only">영유아 자료실 분류 선택</label>
				<select name="cate" id="cate" class="dataSlct">
					<option value="">전체</option>
					<c:forEach items="${boardMst.addCateArr}" var="o" varStatus="s">
						<option value="${o}">${o }</option>
					</c:forEach>
				</select>
			</div>
		</c:if>
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

	<!--//glistWrap(s)//-->
	<div class="glistWrap">
		<div class="fileSort">
			<ul id="myDIV" class="fileSortUl cf">
				<li><a href="#none" onclick="fnc_searchFileType('')" class="fileType fileType1 icoAll active" title="전체 탭">전체</a></li>
				<li><a href="#none" onclick="fnc_searchFileType('doc')" fileTypeGb="doc" class="fileType fileType2 icoDoc" title="문서 탭">문서</a></li>
				<li><a href="#none" onclick="fnc_searchFileType('img')" fileTypeGb="img" class="fileType fileType3 icoImg" title="이미지 탭">이미지</a></li>
				<li><a href="#none" onclick="fnc_searchFileType('pdf')" fileTypeGb="pdf" class="fileType fileType4 icoPdf" title="PDF 탭">PDF</a></li>
				<li><a href="#none" onclick="fnc_searchFileType('ppt')" fileTypeGb="ppt" class="fileType fileType5 icoPpt" title="PPT 탭">PPT</a></li>
				<li><a href="#none" onclick="fnc_searchFileType('vid')" fileTypeGb="vid" class="fileType fileType6 icoVideo" title="동영상 탭">동영상</a></li>
			</ul>
		</div>

		<!--//glist(s)//-->
		<ul class="glist cf">
			<c:forEach items="${boardList }" var="map">
			<li>
				<a href="${vo.boardType }View.do?idx=${map.idx }" title="${map.title }">
					<c:if test="${not empty map.fileTypeGb }">
					<span class="fileType ${map.addFileTypeIco }"><span class="altTxt">${map.fileTypeGb }</span></span>
					</c:if>
					<div class="thumb">
						<span class="img">
							<c:choose>
							<c:when test="${not empty map.thumbFile }">
							<img src="${utcp.getCdnUrl('upload/board/') }${map.thumbFile}" />
							</c:when>
							<c:otherwise>
							<img src="${utcp.ctxPath}/resources/user/image/img/gthumbNo.jpg" />
							</c:otherwise>
							</c:choose>
						</span>
					</div>
					<div class="desc">
						<p class="cate">${map.cate }</p>
						<p class="subj el">${map.title }</p>
						<p class="writ">${map.regNm }</p>
					</div>
				</a>
			</li>
			</c:forEach>
		</ul>
		<!--//glist(e)//-->
	</div>
	<!--//glistWrap(e)//-->

	<c:if test="${vo.boardType eq 'free' and not empty sessionScope.sessionUserInfo.userId}">
		<div class="tb_btn">
			<ul>
				<a href="${vo.boardType }Write.do"><li class="right">글쓰기</li></a>
			</ul>
		</div>
	</c:if>
	<jsp:include page="/WEB-INF/jsp/user/bbs/pageNavi.jsp" />
</div>
<script>
	$('#cate').val('${vo.cate}');
	$('#cate').change(function(){
		document.search_form.submit();
	});
	
	if('${param.fileTypeGb}'!=''){
		$('#myDIV a').removeClass('active');
		$('a[fileTypeGb=${param.fileTypeGb}]').addClass('active');
	}
	
	function fnc_paging(pageNo) {
		document.search_form.pageNo.value = pageNo;
		document.search_form.submit();
	}
	function fnc_searchFileType(fileTypeGb){
		document.search_form.fileTypeGb.value = fileTypeGb;
		document.search_form.submit();
	}
	
</script>