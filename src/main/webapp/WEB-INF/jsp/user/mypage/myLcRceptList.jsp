<%@page
	import="org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo"%>
<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<div class="listWrap" style="padding-top: 0">
	<div id="listDiv">
		<form id="searchFrm">
			<input type="hidden" name="page" id="page" />
		</form>
		<div class="board_tab_onoff">
			<!--// board_tab_con //-->
			<div class="board_tab_con">

				<!--// tab_con1 //-->
				<div class="cont tableRespon">
					<div class="timetb_wrap">
						<table class="tc w100 timetb tb07">
							<caption class="sound_only">교육신청내역 테이블</caption>
							<thead>
								<tr>
									<th scope="col" class="vm" width="60px">순번</th>
									<th scope="col" class="vm mo_tit" width="100px">과정</th>
									<th scope="col" class="vm" width="">교육명</th>
									<th scope="col" class="vm">신청상태</th>
									<th scope="col" class="vm" style="min-width: 99px;">신청일</th>
									<th>입교증</th>
									<!-- 
								
								<th scope="col" class="vm" style="min-width: 99px;">결제방식</th>
								<th scope="col" class="vm" style="min-width: 78px;">결제금액</th>
								<th scope="col" class="vm" style="min-width: 99px;">결제일시</th>
								<th scope="col" class="vm" style="min-width: 99px;">결제상태</th>
								 -->
									<th scope="col" class="vm"></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="o" items="${data.lcrcpList}" varStatus="s">
									<tr>
										<td class="fs_14 ls-05">${data.pageNavi.totalRecordCount - (((data.pageNavi.currentPageNo-1)*data.pageNavi.recordCountPerPage) + s.index)}</td>
										<td class="fs_14 ls-05">${o.ctg1Nm }</td>
										<td class="tl"><a
											href="javascript:md_openLcrcpInfo(${o.eduSeq },${o.rceptSeq });">
										</a> <strong class="fw_500 fs_13">${o.eduNm }</strong> <br /> <span
											class="dp_b fs_13 fc_darkgray pdt5">${o.eduPeriodBegin}~
												${o.eduPeriodEnd }</span></td>
										<td><span
											class="${o.rceptState == 1?'eduAppl':o.rceptState == 2?'eduAppr':'eduCanc' }">
												${o.addRceptStateNm } </span></td>
										<td class="fs_12 ls-05">${utcp.convDateToStr(o.regDe,'yyyy-MM-dd HH:mm') }</td>
										<td><c:if test="${o.rceptState == 2}">
												<a
													href="javascript:open_myRceptPop(${o.eduSeq},${o.rceptSeq });">
													<img
													src="${utcp.ctxPath }/resources/user/image/icon/manage_print.png" />
												</a>
											</c:if></td>
										<!-- 
									 
									<td>${o.addFeeTpNm }</td>
									<td>
									<fmt:formatNumber value="${o.limsFee }" type="currency" currencySymbol="￦" minFractionDigits="0" maxFractionDigits="0" />
									</td>
									<td class="fs_12 ls-05">
									<c:choose>
									<c:when test="${o.fee>0 and empty o.payNo and o.rceptState != 3 and o.limsState == 1 and o.feeTp == 1}">
									<button class="btn04 btn_green" onclick="location.href='${utcp.ctxPath}/user/pay/toss/order.do?eduSeq=${o.eduSeq}&rceptSeq=${o.rceptSeq }';" >걸제하기</button>
									</c:when>
									<c:otherwise>
									${utcp.convDateToStr(o.payDe,'yyyy-MM-dd HH:mm') }
									</c:otherwise>
									</c:choose>
									</td>
									<td>
									${o.addLimsStateNm }
									</td>
									-->
										<td><c:if
												test="${(o.limsState == 1 or o.limsState == 0) and o.rceptState == 1 and empty o.payNo}">
												<!-- 취소조건 정리해서 다시 if문에 넣어야함 -->
												<button class="btn04 btn_blue"
													onclick="fn_cancelProc(${o.rceptSeq},${o.eduSeq})">
													신청취소</button>
											</c:if></td>
									</tr>

								</c:forEach>
								<c:if test="${empty data.lcrcpList }">
									<tr>
										<td colspan="11">내역이 없습니다.</td>
									</tr>
								</c:if>
							</tbody>
						</table>
					</div>
					<!--// paging //-->
					<%-- 
		--%>
					<c:set var="pageNavi" value="${data.pageNavi }" />
					<%
						PaginationInfo pageNavi = (PaginationInfo) pageContext.getAttribute("pageNavi");
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

function fn_cancelProc(rceptSeq,eduSeq){
	if(confirm("교육을 취소하시겠습니까?")){
		$.ajax({
			url: "${utcp.ctxPath}/user/edu/cancelRcept.ajax",
			type: "post",
			data: {
				"rceptSeq" : rceptSeq,
				"eduSeq" : eduSeq
			},
			cache: false,
			async: true,
			success: function(r) {
				if(r.result == 1){
					alert('취소되었습니다.');
					location.reload();
				}else if(r.result == -1){
					alert('교육상태가 변경되었습니다. ');location.reload();
					//window.open('${utcp.ctxPath}/comm/popup/cancelRcept.do?rceptSeq='+rceptSeq,'cancelRceptPop','width=900,height=800');
				}else{
					alert(r.msg);
				}
			}
		});	
	}
}
	function fnc_paging(page){
		$("#page").val(page);
		$("form[id='searchFrm']").submit();
	}
	function fn_openRefundReg(limsSrNo){
		//window.open('${utcp.ctxPath}/comm/popup/cancelRcept.do?limsSrNo='+limsSrNo,'cancelRceptPop','width=900,height=800');
		window.open('https://customer.ktl.re.kr/web/contents/K404000000.do?schM=input&page=1&schOpt=&schFld=&schStr=','cancelRceptPop','');
	}
</script>


<div class="remodal remodals" data-remodal-id="md-vcInfo" role="dialog"
	aria-labelledby="modal1Title" aria-describedby="modal1Desc"
	id="vm-vtInfo">
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC">
				<i class="fas fa-chevron-circle-right fs_22 pdr5"></i>가상계좌정보
			</p>
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
				<button type="button" class="remodal-confirm btn02 btn_green"
					data-remodal-action="close">닫기</button>
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


