<%@page import="org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo"%>
<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<div class="listWrap" style="padding-top: 0">
	<div id="listDiv">
		<form id="searchFrm">
			<input type="hidden" name="page" id="page"/>
		</form>
		<div class="board_tab_onoff">
            <ul class="board_tab">
				<li id="tab1" class="active">
					<p><a href="?tabNm=" >신청내역</a></p>
				</li>
				<li id="tab2" class="">
					<p><a href="?tabNm=resv" >선점내역</a></p>
				</li>
			</ul>
		
			<!--// board_tab_con //-->
			<div class="board_tab_con">

				<!--// tab_con1 //-->
				<div class="cont tableRespon">
					<table class="tc w100 timetb tb07">
						<caption class="sound_only">단체교육 신청내역 테이블</caption>
						<thead>
							<tr>
								<th scope="col" class="vm">순번</th>
								<th scope="col" class="vm">교육명</th>
								<th scope="col" class="vm" style="min-width: 78px;">금액</th>
								<th scope="col" class="vm">신청인원</th>
								<th scope="col" class="vm" style="min-width: 99px;">신청일</th>
								<th scope="col" class="vm">신청상태</th>
								<th scope="col" class="vm" style="min-width: 99px;">취소일</th>
								<th scope="col" class="vm">회차</th>
								<th scope="col" class="vm" style="min-width: 89px;">교육일</th>
								<th scope="col" class="vm" style="min-width: 79px;">교육시간</th>
								<th scope="col" class="vm">강의실</th>
								<th scope="col" class="vm">취소</th>
								<th scope="col" class="vm">수정</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="o" items="${data.tmRceptList}" varStatus="s">
								<c:set var="rowspanCnt" value="1"/>
								<c:set var="isMulti" value="false"/>
								<c:if test="${o.tmPlanCnt>1 }">
									<c:set var="rowspanCnt" value="${o.tmPlanCnt }"/>
									<c:set var="isMulti" value="true"/>
									<c:set var="lctreListKeyNm" value="lctreList_${o.tmRceptSeq }"/>
									<c:set var="ml" value="${data[lctreListKeyNm] }"/>
								</c:if>
								
								<tr>
									<td class="fs_12 ls-05" rowspan="${rowspanCnt }">${fn:length(data.tmRceptList)-s.index }</td>
									<td class="fs_12 ls-05" rowspan="${rowspanCnt }" style="min-width:200px; max-width: 250px;">
									<%-- 
									<a href="javascript:md_openTmrcpInfo(${o.tmSeq },${o.tmRceptSeq });">
									</a>
									 --%>
									${o.eduNm }
									</td>
									<td rowspan="${rowspanCnt }">
									<fmt:formatNumber value="${o.totalFee }" type="currency" currencySymbol="￦" minFractionDigits="0" maxFractionDigits="0" />
									</td>
									<td rowspan="${rowspanCnt }">${o.rceptCnt}</td>
									<td class="fs_12 ls-05" rowspan="${rowspanCnt }">${utcp.convDateToStr(o.regDt,'yyyy-MM-dd HH:mm') }</td>
									<td rowspan="${rowspanCnt }">
									<span class="${o.rceptState == 1?'eduAppl':o.rceptState == 2?'eduCanc':'eduAppr' }">
									${o.addRceptStateNm }
									</span>
									</td>
									<td class="fs_12 ls-05" rowspan="${rowspanCnt }">
										<c:if test="${o.rceptState == 2 }">
										${utcp.convDateToStr(o.modDt,'yyyy.MM.dd HH:mm') }
										</c:if>
									</td>
									<c:choose>
									<c:when test="${isMulti }">
									<td class="fs_12 ls-05">${ml[0].tmPlanSeq }</td>
									<td class="fs_12 ls-05">${utcp.convDateToStr(utcp.convStrToDate(ml[0].tmEduDt,'yyyyMMdd'),'yyyy-MM-dd (E)') }</td>
									<td class="fs_12 ls-05">
									${utcp.convDateToStr(utcp.convStrToDate(ml[0].startTm,'HHmm'),'HH:mm') } ~ 
									${utcp.convDateToStr(utcp.convStrToDate(ml[0].endTm,'HHmm'),'HH:mm') }
									</td>
									<td class="fs_12 ls-05">${ml[0].roomNm }</td>
									</c:when>
									<c:otherwise>
									<td class="fs_12 ls-05">${o.tmPlanSeq }</td>
									<td class="fs_12 ls-05">${utcp.convDateToStr(utcp.convStrToDate(o.tmEduDt,'yyyyMMdd'),'yyyy-MM-dd (E)') }</td>
									<td class="fs_12 ls-05">
									${utcp.convDateToStr(utcp.convStrToDate(o.startTm,'HHmm'),'HH:mm') } ~ 
									${utcp.convDateToStr(utcp.convStrToDate(o.endTm,'HHmm'),'HH:mm') }
									</td>
									<td class="fs_12 ls-05">${o.roomNm }</td>
									</c:otherwise>
									</c:choose>
									<td rowspan="${rowspanCnt }">
									<c:if test="${o.addPossibleCancel == 1 and o.rceptState == 1}">
									<a href="javascript:fn_cancelTmRcept('${o.tmRceptSeq }');" class="btn btn04 btn_blue fs_12 ls-05">취소</a> 
									</c:if>
									</td>
									<td rowspan="${rowspanCnt }">
									<c:if test="${o.rceptState == 1}">
									<a href="javascript:md_openTmrcpInfo(${o.tmSeq },${o.tmRceptSeq });" class="btn btn04 btn_green fs_12 ls-05">수정</a> 
									</c:if>
									</td>
								</tr>
								<c:if test="${isMulti }">
								<c:forEach begin="1" end="${fn:length(data[lctreListKeyNm])-1 }" var="i2">
								<tr>
									<td class="fs_12 ls-05" style="border-left: 1px solid #ebedf2;">${ml[i2].tmPlanSeq }</td>
									<td class="fs_12 ls-05">${utcp.convDateToStr(utcp.convStrToDate(ml[i2].tmEduDt,'yyyyMMdd'),'yyyy-MM-dd (E)') }</td>
									<td class="fs_12 ls-05">
									${utcp.convDateToStr(utcp.convStrToDate(ml[i2].startTm,'HHmm'),'HH:mm') } ~ 
									${utcp.convDateToStr(utcp.convStrToDate(ml[i2].endTm,'HHmm'),'HH:mm') }
									</td>
									<td class="fs_12 ls-05" style="border-right: 1px solid #ebedf2;">${ml[i2].roomNm }</td>
								</tr>
								</c:forEach>
								</c:if>
							</c:forEach>
							<c:if test="${empty data.tmRceptList }">
								<tr>
									<td colspan="12">내역이 없습니다.</td>
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
		<!--// board_tab_onoff //-->

	</div>
</div>

<script>
function fn_cancelTmRcept(tmRceptSeq){
	if(!confirm('취소하시겠습니까?')){
		return;
	}
	$.ajax({
		type : 'post',
		url : 'myTmRceptCancel.ajax',
		data : {tmRceptSeq : tmRceptSeq},
		success : function (r){
			if(r.result!=1){
				alert(r.msg);
				return;
			}
			location.reload();
			
		}
	});
}
function fnc_paging(page){
	$("#page").val(page);
	$("form[id='searchFrm']").submit();
}
</script>