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
								<th scope="col" class="vm" width="70px">년도</th>
								<th scope="col" class="vm" width="">교육명</th>
								<th scope="col" class="vm">차수</th>
								<th scope="col" class="vm" style="min-width: 99px;">교육시작일</th>
								<th scope="col" class="vm" style="min-width: 78px;">교육종료일</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="o" items="${data.eduOldList}" varStatus="s">
								<tr>
									<td class="fs_12 ls-05">${data.pageNavi.totalRecordCount - (((data.pageNavi.currentPageNo-1)*data.pageNavi.recordCountPerPage) + s.index)}</td>
									<td class="fs_12 ls-05">${o.eduYear }</td>
									<td class="tl" >
									<!-- a href="javascript:md_openLcrcpInfo(${o.eduNm },${o.eduNm });" -->
									<strong class="fw_500 fs_13">${o.eduNm }</strong>
									</a>
									<br/>
									</td>
									<td>${o.chasu }</td>
									<td class="fs_12 ls-05">${utcp.convDateToStr(o.dtEduStart,'yyyy-MM-dd') }</td>
									<td class="fs_12 ls-05">${utcp.convDateToStr(o.dtEduEnd,'yyyy-MM-dd') }</td>
									
								</tr>

							</c:forEach>
							<c:if test="${empty data.eduOldList }">
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


	function fnc_paging(page){
		$("#page").val(page);
		$("form[id='searchFrm']").submit();
	}
	function receipt(pgPayNo, payType) {
		var controlNo = pgPayNo;
		var payment = '01';
		if (payType == '2') {
			payment = '02';
		} else if (payType == '3') {
			payment = '03';
		}

		window
				.open(
						'<spring:eval expression="@prop['pay.easypay.receiptUrl']"/>?controlNo='
								+ controlNo + '&payment=' + payment,
						'MEMB_POP_RECEIPT',
						'toolbar=0,scroll=1,menubar=0,status=0,resizable=0,width=380,height=700');
	}
</script>


<div class="remodal remodals" data-remodal-id="md-vcInfo" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc" id="vm-vtInfo">
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC"><i class="fas fa-chevron-circle-right fs_22 pdr5"></i>가상계좌정보</p>
		</div>
		<div class="modal-body">
			<div class="tbBox">
				<table class="w100 tc">
					<tbody>
						<tr>
							<th bgcolor="#f7f8fa">계좌번호</th>
							<td>{{vtNo}}</td>
						</tr>
						<tr>
							<th bgcolor="#f7f8fa">은행명</th>
							<td>{{vtBank}}</td>
						</tr>
						<tr>
							<th bgcolor="#f7f8fa">예금주</th>
							<td>{{vtNm}}</td>
						</tr>
						<tr>
							<th bgcolor="#f7f8fa">입금마감기한</th>
							<td>{{vtCloseDtime}}</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<div class="modal-footer">
			<div class="tc">
				<button type="button" class="remodal-confirm btn02 btn_green" data-remodal-action="close">닫기</button>
			</div>
		</div>
	</div>
</div>
<script>
	var vm_vtInfo = new Vue({
		el : '#vm-vtInfo',
		data : {
			vtBank : '',
			vtNm : '',
			vtNo : '',
			vtCloseDtime : '',
		},
	});
	function open_md_vtInfo(vtBank, vtNm, vtNo, vtCloseDtime) {
		vm_vtInfo.vtBank = vtBank;
		vm_vtInfo.vtNm = vtNm;
		vm_vtInfo.vtNo = vtNo;
		vm_vtInfo.vtCloseDtime = vtCloseDtime;
		location.href = '#md-vcInfo';
	}
</script>

<!-- 신청서 모달 -->
