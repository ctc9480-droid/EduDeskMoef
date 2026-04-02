<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="java.util.List"%>
<%@page import="com.educare.util.LncUtil"%>
<%@page import="ch.qos.logback.core.Context"%>
<%@page import="com.educare.edu.bbs.service.BoardAttachService"%>
<%@page import="com.educare.edu.bbs.service.model.BoardAttach"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Enumeration"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<% 
ServletContext conext = session.getServletContext();
WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(conext);
BoardAttachService boardAttachService = (BoardAttachService)wac.getBean("BoardAttachService");
%>
<style>
.active a.no-bg{
	background: none;
}
</style>
<div class="listWrap" style="padding-top: 0px">
	<form name="search_form" method="get">
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
	<div class="faqWrap">
		<ul class="faqAccordion">
			<c:if test="${vo.pageNo == 1 }">
			<c:forEach items="${noticeList }" var="o">
				<li>
					<a href="javascript:"> <i class="q-mark">Q.</i> <span>${o.title }</span>
					</a>
					<div class="answerArea" style="display: none;">
						<i class="a-mark">A.</i>
						<p class="answer">${o.content }</p>
						
						<!--// 240118 hy 첨부파일 추가  //-->
						<c:set var="baIdx" value="${o.idx }"/>
						<%
						//첨부파일리스트
						int idx = LncUtil.nvlInt(pageContext.getAttribute("baIdx"));
						System.out.println(idx);
						BoardAttach attachVo = new BoardAttach();
						attachVo.setBoardIdx(idx);
						List<BoardAttach> boardAttachList = boardAttachService.getBoardAttachList(attachVo);
						pageContext.setAttribute("boardAttachList", boardAttachList);
						%>
						<c:if test="${boardAttachList ne null and boardAttachList ne '[]' and boardAttachList ne ''}">
						<div class="faqFile cf">
							<div class="fl fs_20 mgr20"><b>첨부파일</b></div>
							<div class="fl">
								<ul class="head info">
									<c:forEach items="${boardAttachList }" var="map">
										<li><a href="${utcp.ctxPath}/bbs/comm/download/${map.fileSeq }.do" class="no-bg"> <img src="${utcp.ctxPath}/resources/user/image/icon/down_icon.gif" alt="파일 아이콘" /> <b>${map.fileOrg }(${map.replaceFileSize})</b></a></li><br/>
									</c:forEach>
								</ul>
							</div>
						</div>
						</c:if>
						<!--// 240118 hy 첨부파일 추가  //-->
					</div>
				</li>
			</c:forEach>
			</c:if>
			<c:forEach items="${boardList }" var="o">
				<li>
					<a href="javascript:"> <i class="q-mark">Q.</i> <span>${o.title }</span>
					</a>
					<div class="answerArea" style="display: none;">
						<i class="a-mark">A.</i>
						<p class="answer">${o.content }</p>
						
						<!--// 240118 hy 첨부파일 추가  //-->
						<c:set var="baIdx" value="${o.idx }"/>
						<%
						//첨부파일리스트
						int idx = LncUtil.nvlInt(pageContext.getAttribute("baIdx"));
						System.out.println(idx);
						BoardAttach attachVo = new BoardAttach();
						attachVo.setBoardIdx(idx);
						List<BoardAttach> boardAttachList = boardAttachService.getBoardAttachList(attachVo);
						pageContext.setAttribute("boardAttachList", boardAttachList);
						%>
						<c:if test="${boardAttachList ne null and boardAttachList ne '[]' and boardAttachList ne ''}">
						<div class="faqFile cf">
							<div class="fl fs_20 mgr20"><b>첨부파일</b></div>
							<div class="fl">
								<ul class="head info">
									<c:forEach items="${boardAttachList }" var="map">
										<li><a href="${utcp.ctxPath}/bbs/comm/download/${map.fileSeq }.do" class="no-bg"> <img src="${utcp.ctxPath}/resources/user/image/icon/down_icon.gif" alt="파일 아이콘" /> <b>${map.fileOrg }(${map.replaceFileSize})</b></a></li><br/>
									</c:forEach>
								</ul>
							</div>
						</div>
						</c:if>
						<!--// 240118 hy 첨부파일 추가  //-->
						
					</div>
				</li>
			</c:forEach>
			<c:if test="${empty boardList && empty noticeList }">
				<li>
				<span>데이터가 없습니다.</span>
				</li>
			</c:if>
		</ul>
	</div>
	
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
$(document).ready(function(){
    // faq accordion
    var faq_num = -1;
    var $faq_acc = $(".faqAccordion > li");
    $faq_acc.each(function(q){
        jQuery(this).find("> a").click(function(){
            if(faq_num != q){
                $faq_acc.eq(faq_num).removeClass("active");
                $faq_acc.eq(faq_num).find(".answerArea").stop().slideUp(300);
                faq_num = q;
                $faq_acc.eq(faq_num).addClass("active");
                $faq_acc.eq(faq_num).find(".answerArea").stop().slideDown(300);
            }else if(faq_num == q){
                $faq_acc.eq(faq_num).find(".answerArea").stop().slideUp(300, function(){
                    $faq_acc.eq(faq_num).removeClass("active");
                    faq_num = -1;
                });
            }
        })
    });
})

	function fnc_paging(pageNo) {
		document.search_form.pageNo.value = pageNo;
		document.search_form.submit();
	}
	$('#cate').val('${vo.cate}');
	$('#cate').change(function() {
		document.search_form.submit();
	});
</script>