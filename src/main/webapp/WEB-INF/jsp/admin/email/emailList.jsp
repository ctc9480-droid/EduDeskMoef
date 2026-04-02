<%@page import="org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp" %>
<section class="pd025 pd200 po_re">
	<div class="po_re br5 bs_box">
		<span class="tb_text"> 총 <strong class="fc_red">${data.pageNavi.totalRecordCount }</strong> 건
		</span>
		<form name="search_form" method="get" id="search-form">
			<input type="hidden" id="pageNo" name="pageNo" value="1" />
			<div class="tr mgb15">
				<input id="srchWrd" name="srchBeginDt" value="${param.srchBeginDt }" type="text" class="btn04 btn_blackl tl mgr5 datepicker"  style="width:110px;" placeholder="발송일시" />
				<label for="srchWrd" class="sound_only">검색어입력</label> 
				<input id="srchWrd" name="srchWrd" value="${param.srchWrd }" type="text" class="btn04 btn_blackl tl mgr5" placeholder="수신주소,내용 검색"/>
				<button class="btn04 btn_black fr" type="submit">검색</button>
			</div>
		</form>
		<table width="100%" class="tb01">
			<caption class="sound_only">팝업테이블</caption>
			<colgroup>
				<col width="7.5%" />
				<col width="12.5%" />
<!-- 				<col width="" /> -->
				<col width="7.5%" />
				<col width="7.5%" />
				<col width="15%" />
				<col width="15%" />
				<col width="35%" />
			</colgroup>
				<thead bgcolor="#f7f8fa">
				<tr>
                      <th width="">이메일키</th>
                      <th width="">발송일시</th>
<!--                       <th width="">메시지</th> -->
                      <th width="">상태</th>
                      <th width="">결과코드</th>
                      <th width="">수신주소</th>
                      <th width="">발신주소</th>
                      <th width="">내용</th>
                     </tr>
			</thead>
			<tbody>
				<c:forEach items="${data.emailList }" var="o" varStatus="s"> 
				<tr>
					<td>${o.EMAIL_KEY }</td>
					<td>${utcp.convDateToStr(o.SENT_DATE,'yyyy-MM-dd HH:mm') }</td>
<%-- 					<td>${o.RSLT_TYPE }</td> --%>
					<td>${o.EMAIL_STATE }</td>
					<td>${o.RSLT_CODE_NM }</td>
					<td>${o.EMAIL }</td>
					<td>${o.CALLBACK }</td>
					<td>${o.EMAIL_TEXT }</td>
				</tr>
				</c:forEach>
				<c:if test="${empty data.emailList }">
					<tr>
						<td colspan="8">데이터가 없습니다.</td>
					</tr>
				</c:if>
			</tbody>
			</table>
			
			<!--// paging //-->
			<c:if test="${not empty data.pageNavi }">
			<c:set var="pageNavi" value="${data.pageNavi }" />
			<%
				PaginationInfo pageNavi = (PaginationInfo)pageContext.getAttribute("pageNavi");
				request.setAttribute("pageNavi", pageNavi);
				%>
			<jsp:include page="/WEB-INF/jsp/admin/common/paging.jsp" />
			</c:if>
			<!--// paging //-->
			
			<div class="fr tc">
			<button type="button" onclick="vm_email.callLayer()" class="btn01 btn_greenl">이메일발송</button>
		</div>
			</div>
			</section>
<script>
$('.datepicker').datepicker();
//ESC 키를 누르면 날짜 초기화
$(".datepicker").on('keydown', function(event) {
    if (event.key === "Backspace") { // ESC 키 코드
        $(this).val(''); // 입력 필드의 값을 빈 문자열로 설정하여 초기화
        $(this).datepicker('setDate', null); // Datepicker API를 사용하여 날짜 초기화
    }
});
function fn_paging(page) {
	$("#pageNo").val(page);
	$('#search-form').submit();
}
</script>

<!-- 이메일발송 레이어 -->
<div class="remodal remodal-is-initialized remodal-is-opened" data-remodal-id="md-email" id="vm-email" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc" style="width: 480px;"  tabindex="-1" data-remodal-options="closeOnOutsideClick: false">
	<button data-remodal-action="close" class="remodal-close" aria-label="Close"></button>
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC"><i class="fas fa-chevron-circle-right fs_22 pdr5"></i>이메일발송</p>
		</div>
		<div class="modal-body">
			<div class="tbBox1">
				<textarea class="w100 h400" v-model="msg"></textarea>
			</div>
		</div>
		<div class="modal-footer">
			<div class="tc">
				<button type="button" @click="sendEmail" class="remodal-confirm btn01 btn_green">발송</button>
			</div>
		</div>
	</div>
</div>
<script>
// var vm_sms_msg = '';
// vm_sms_msg += '0000년 0월 안전교육시스템에서 신청하신 보육교직원 안전교육이 현재 미수료 상태로, 0/0(0)까지 수강 완료하셔야 합니다. \n';
// vm_sms_msg += '(마이페이지-진행중 교육 클릭-진도율 100% 여부 확인)\n';
// vm_sms_msg += '* 0월 신청하신 안전교육과정 중 1개라도 미이수하신 경우 문자 발송됨\n\n';
// vm_sms_msg += '※ 보육교직원 안전교육은 기본, 심화, 영아담당교사Ⅰ,Ⅱ, 유아담당교사Ⅰ,Ⅱ 과정 중 매년 1개만 이수하시면 됩니다. (문의: 1600-0611) \n';

var vm_email = new Vue({
	el: '#vm-email',
	data: {
// 		msg: vm_sms_msg,
		noPassList: [],
	},
	methods: {
		sendEmail: function(){
			var _this = this;
			
			//발송목록체크
			if(_this.noPassList.length == 0){
				alert('발송대상이 없습니다.');return;
			}
			
			if(!confirm('이메일발송 하시겠습니까?')){
				return;
			}
			
			$.ajax({
				type: 'post',
				data: {msg: _this.msg},
				url: '/admin/sms/sendEmailNoPassList.ajax',
				success: function(r){
					if(r.result == 1){
						alert('이메일발송 완료하였습니다.');
						$('[data-remodal-id=md-email]').remodal().close();
						location.reload();
					}else{
						alert(r.msg);
					}
					
				}
			});
			
		},
		callLayer: function(){
			
			fn_md_emailSend('','','',fn_stdnt_closeEmailSend);
			//$('[data-remodal-id=md-sms1]').remodal().open();
			//location.href='#none';
		},
	},
});
function fn_stdnt_closeEmailSend(){
	location.reload();
}
</script>