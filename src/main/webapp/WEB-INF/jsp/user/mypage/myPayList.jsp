<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<div class="listWrap" style="padding-top: 0">
	<div id="listDiv">

		<div class="board_tab_onoff">
			<!--// board_tab_con //-->
			<div class="board_tab_con">

				<!--// tab_con1 //-->
				<div class="cont tableRespon">
					<table class="tc w100 timetb">
						<caption class="sound_only">안전컨설팅 신청내역 테이블</caption>
						<thead>
							<tr>
								<th scope="col" class="vm">주문번호</th>
								<th scope="col" class="vm">주문금액</th>
								<th scope="col" class="vm">결제유형</th>
								<th scope="col" class="vm">결제상태</th>
								<th scope="col" class="vm">결제일</th>
								<th scope="col" class="vm">취소일</th>
								<th scope="col" class="vm">교육명</th>
								<th scope="col" class="vm">영수증</th>
								<th scope="col" class="vm"></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="o" items="${payList}" varStatus="s">
								<tr>
									<td>${o.payNo }</td>
									<td><fmt:formatNumber value="${o.amount }" type="currency"/> </td>
									<td>${o.addPayTypeNm }</td>
									<td>${o.addStateNm }</td>
									<td>${utcp.convDateToStr(o.regDt,'yyyy-MM-dd HH:mm') }</td>
									<td>${utcp.convDateToStr(o.updDt,'yyyy-MM-dd HH:mm') }</td>
									<td>${o.goodNm }</td>
									<td><button class="btn03 btn_blue" onclick="receipt('${o.pgPayNo }','${o.payType }');" >보기</button></td>
									<td>
									<c:if test="${o.payType == 3 and (o.state != 3 and o.state != 1)}">
									<a class="btn04 btn_orange" href="javascript:open_md_vtInfo('${o.vtBank }','${o.vtNm }','${o.vtNo }','${utcp.convDateToStr(utcp.convStrToDate(o.vtCloseDtime,'yyyyMMddHHmmss'),'yyyy.MM.dd(E) HH:mm') }');">가상계좌</a>
									</c:if>
									</td>
								</tr>
							</c:forEach>
							<c:if test="${empty payList }">
								<tr>
									<td colspan="8">내역이 없습니다.</td>
								</tr>
							</c:if>
						</tbody>
					</table>

				</div>
				<!--// tab_con1 //-->

				<!--// tab_con2 //-->
				<div class="cont" style="display: block;"></div>
				<!--// tab_con2 //-->

				<!--// tab_con3 //-->
				<div class="cont" style="display: block;"></div>
				<!--// tab_con3 //-->


			</div>
			<!--// board_tab_con //-->
		</div>
		<!--// board_tab_onoff //-->
<!-- 
		<form name="form_search" method="get">
		<div class="box_sort flex listWrapBox">
			<div class="box_search mgauto">
				<select class="srchColumn" title="제목+내용 검색">
					<option value="titCont">제목+내용</option>
				</select>
				<input type="text" id="srchWrd" />
				<button type="submit" onclick="fn_srch(); return false;">검색</button>
			</div>
		</div>
		</form>
		 -->
	</div>
</div>
<script>
function receipt(pgPayNo,payType){
	var controlNo = pgPayNo;
	var payment = '01';
	if(payType == '2'){
		payment = '02';
	}else if(payType == '3'){
		payment = '03';
	}
	
	window.open('<spring:eval expression="@prop['pay.easypay.receiptUrl']"/>?controlNo='+controlNo+'&payment='+payment,'MEMB_POP_RECEIPT', 'toolbar=0,scroll=1,menubar=0,status=0,resizable=0,width=380,height=700');
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
				<button type="button"  class="remodal-confirm btn02 btn_green" data-remodal-action="close">닫기</button>
			</div>
		</div>
	</div>
</div> 
<script>
var vm_vtInfo = new Vue({
	el:'#vm-vtInfo',
	data:{
		vtBank:'',
		vtNm:'',
		vtNo:'',
		vtCloseDtime:'',
		},
});
function open_md_vtInfo(vtBank,vtNm,vtNo,vtCloseDtime){
	vm_vtInfo.vtBank=vtBank;
	vm_vtInfo.vtNm=vtNm;
	vm_vtInfo.vtNo=vtNo;
	vm_vtInfo.vtCloseDtime=vtCloseDtime;
	location.href='#md-vcInfo';
}
</script>