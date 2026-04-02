<%@page import="java.util.Iterator"%>
<%@page import="java.util.Enumeration"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>


<div class="sub_txt">
	<span class=""><img src="${utcp.ctxPath}/resources/user/image/icon/icon_subtitle.png" alt="">식단안내</span>
</div>

<div class="s6-wrap">
	<div class="s6-cont">
		<div class="s6-cont01 notice-box">
			<p>구내식당은연수생의 집합 연수를 보장하기 위한 목적으로 <span>예약된 인원에 대해서만 운영</span>하고 있으며, <br /><span>외부인의 식사를 제공할 수 없음</span>을 알려드립니다. 감사합니다.</p>
		</div>
		<div class="s6-cont02">
			<div class="listWrap pdt">
				<form name="search_form" method="get">
					<input type="hidden" id="pageNo" name="pageNo" value="1" />
					<c:if test="${not empty boardMst.addCateArr }">
						<div class="selectBox cf">
							<label for="cate" class="sound_only"> 분류 선택</label>
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
			
				<table class="tb02 mgt30 w100">
					<tr>
						<th width="50px">NO</th>
						<th>제목</th>
						<th width="100px">작성자</th>
						<th width="100px">작성일</th>
						<th width="50px" style="border-right: 0px;">조회수</th>
					</tr>
					<c:forEach items="${noticeList }" var="map">
						<tr>
							<td>공지</td>
							<td class="text"><a href="${vo.boardType }View.do?idx=${map.idx }" class="${map.childFlag }"> ${map.gLvl>0?'└>':'' } ${map.title } <c:if test="${map.commentCnt>0 }">
									[${map.commentCnt}]
									</c:if></a></td>
							<td>${map.regNm2 }</td>
							<td>${map.replaceRegDtime('yyyy-MM-dd') }</td>
							<td style="border-right: 0px;">${map.hit }</td>
						</tr>
					</c:forEach>
					<c:forEach items="${boardList }" var="map" varStatus="s">
						<tr>
							<td>
							${pageNavi.totalRecordCount-(pageNavi.firstRecordIndex+s.index) }
							</td>
							<td class="text"><a href="${vo.boardType }View.do?idx=${map.idx }" class="${map.childFlag }"> ${map.gLvl>0?'└>':'' } ${map.title } <c:if test="${map.commentCnt>0 }">
									[${map.commentCnt}]
									</c:if></a></td>
							<td>${map.regNm2 }</td>
							<td>${map.replaceRegDtime('yyyy-MM-dd') }</td>
							<td style="border-right: 0px;">${map.hit }</td>
						</tr>
					</c:forEach>
					<c:if test="${empty boardList and empty noticeList}">
						<tr>
							<td colspan="5" class="" style="border-right: 0px;">자료가 없습니다</td>
						</tr>
					</c:if>
				</table>
				<c:if test="${boardMst.permTp == 9 and not empty sessionScope.sessionUserInfo.userId}">
					<div class="fr">
						<a href="${vo.boardType }Write.do" class="btn02 btn_black">글쓰기</a>
					</div>
				</c:if>
				<jsp:include page="/WEB-INF/jsp/user/bbs/pageNavi.jsp" />
			</div>
		</div>
	</div>
	
</div>

<script>
	function fnc_paging(pageNo) {
		document.search_form.pageNo.value = pageNo;
		document.search_form.submit();
	}
	$('#cate').val('${vo.cate}');
	$('#cate').change(function(){
		document.search_form.submit();
	});
</script>
