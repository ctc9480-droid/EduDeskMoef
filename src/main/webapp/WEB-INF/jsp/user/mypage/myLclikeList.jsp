<%@page import="org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo"%>
<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<div class="listWrap" style="padding-top: 0">
	<div id="listDiv">
		<form id="searchFrm">
			<input type="hidden" name="page" id="page"/>
		</form>
		<div class="board_tab_onoff">
			<!--// board_tab_con //-->
			<div class="board_tab_con">

				<!--// tab_con1 //-->
				<div class="cont tableRespon">
					<table class="tc w100 timetb tb07">
						<caption class="sound_only">안전컨설팅 신청내역 테이블</caption>
						<thead>
							<tr>
								<th scope="col" class="vm">순번</th>
								<th scope="col" class="vm" width="">구분</th>
								<th scope="col" class="vm" width="">교육명</th>
								<th scope="col" class="vm">교육기간</th>
								<th scope="col" class="vm">신청기간</th>
								<th scope="col" class="vm"></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="o" items="${data.list}" varStatus="s">
								<tr>
									<td class=" ">${data.pageNavi.totalRecordCount - (((data.pageNavi.currentPageNo-1)*data.pageNavi.recordCountPerPage) + s.index)}</td>
									<td class=" ">
									${o.addTargetTpNm }
									</td>
									<td class="tl" >
									<a href="${utcp.ctxPath }/user/edu/eduView.do?eduSeq=${o.eduSeq}&srchCtgry=${o.ctg1Seq}">
									${o.eduNm }
									</a>
									</td>
									<td>${o.eduPeriodBegin}~ ${o.eduPeriodEnd }</td>
									<td>
										<c:choose>
										<c:when test="${o.rceptPeriodYn  eq 'Y'}">
										${utcp.convDateToStr(utcp.convStrToDate(o.rceptPeriodBegin,'yyyyMMddHHmm'),'yyyy-MM-dd HH:mm')} ~
										${utcp.convDateToStr(utcp.convStrToDate(o.rceptPeriodEnd,'yyyyMMddHHmm'),'yyyy-MM-dd HH:mm')}
										</c:when>
										<c:otherwise>미설정</c:otherwise>
										</c:choose>
									</td>
									<td>
										<button type="button" class="btn04 btn_red" onclick="fn_saveLikeEdu(${o.eduSeq},2)">삭제</button>
									</td>
								</tr>

							</c:forEach>
							<c:if test="${empty data.list }">
								<tr>
									<td colspan="11">내역이 없습니다.</td>
								</tr>
							</c:if>
						</tbody>
					</table>
					<!--// paging //-->
	<%-- 
		--%>
	<c:set var="pageNavi" value="${data.pageNavi }" />
	<%
		PaginationInfo pageNavi = (PaginationInfo)pageContext.getAttribute("pageNavi");
		request.setAttribute("pageNavi", pageNavi);
		%>
	<jsp:include page="/WEB-INF/jsp/user/bbs/pageNavi.jsp" />
	<!--// paging //-->
				</div>
				<!--// tab_con1 //-->
			</div>
			<!--// board_tab_con //-->
		</div>
	</div>
</div>
<script>
function fn_saveLikeEdu(eduSeq,state){
	$.ajax({
		type: 'post',
		url: '${utcp.ctxPath}/user/ajax/setLikeEdu.ajax',
		data: {eduSeq: eduSeq, state: state},
		success: function(r){
			if(r.result == 1){
				location.reload();
				return;
			}
			alert(r.msg);
		},
	});
}
	function fnc_paging(page){
		$("#page").val(page);
		$("form[id='searchFrm']").submit();
	}
	
</script>

