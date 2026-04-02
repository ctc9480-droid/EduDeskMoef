<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<section class="pd025 pd200 po_re">
	<div class="po_re br5 bs_box">
		<span class="tb_text"> 총 <strong class="fc_red">${totalCnt}</strong>명
		</span>

		<form name="search_form" method="get" action="${utcp.ctxPath}/admin/member/instrctrList.do">
			<input type="hidden" id="page" name="page" value="${vo.page}" />
			<div class="tr mgb15">
				
				<label for="srchWrd" class="sound_only">검색어입력</label>
				<input type="text" name="srchWrd" id="srchWrd" placeholder="성명,연락처 검색" class="btn04 btn_blackl tl mgr5" value="${vo.srchWrd}" />

				<button class="btn04 btn_black fr">검색</button>
			</div>
		</form>

		<table width="100%" class="tb01 tc">
			<caption class="sound_only">강사정보테이블</caption>
			<thead bgcolor="#f7f8fa">
				<tr>
					<th><input type="checkbox" id="all-check" /></th>
<!-- 					<th>번호</th> -->
					<th>성명</th>
					<th>연락처</th>
					<th>상태</th>
					<th>등록일</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test='${dataList != null && fn:length(dataList) > 0}'>
						<c:forEach var="user" items="${dataList}" varStatus="stat">
							<tr>
								<td><input type="checkbox" name="checkUserId" data-mobile="${user.decMobile}"/></td>
<%-- 								<td>${totalCnt - (vo.page - 1) * vo.row - stat.index}</td> --%>
								<td><a href="${utcp.ctxPath}/admin/member/instrctrView.do?userId=${user.userId}">${user.userNm}</a></td>
								<td>${user.decMobile}</td>
								<td>${user.addStateNm}</td>
								<td><fmt:formatDate value="${user.rgsde}" pattern="yyyy-MM-dd" /></td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr>
							<td colspan="5" class="h200">데이터가 없습니다.</td>
						</tr>
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>
		<!-- <div class="fl tc">
			<button type="button" class="btn01 btn_orange" onclick="fn_checkApply()">승인하기</button>
		</div> -->
		<div class="fr tc">
			<button class="btn01 btn_greenl" onclick="vm_sms1.callLayer()">문자발송</button>
			<!-- <button class="btn01 btn_green" onclick="fn_excel(); return false;">액셀 다운로드</button> -->
			<button class="btn01 btn_greenl" onclick="fn_rgsFrm(); return false;">강사 등록</button>
		</div>

		<!--// paging //-->
		<jsp:include page="/WEB-INF/jsp/admin/common/paging.jsp" />
		<!--// paging //-->
	</div>
</section>

<script>
	$(document).ready(function(){
		//전체선택 이벤트
		$('#all-check').click(function(){
			if(this.checked){
				$('input[name=checkUserId]').prop('checked',true);
			}else{
				$('input[name=checkUserId]').prop('checked',false);
			}
		});
		$('#srchPosition1').change(function(){
			$("#page").val(1);
			$("form[name='search_form']").submit();
		});
	});

	function fn_paging(page) {
		$("#page").val(page);
		$("form[name='search_form']").submit();
	}

	function fn_rgsFrm() {
		location.href = "${utcp.ctxPath}/admin/member/instrctrRgs.do";
	}

	function fn_excel() {
		var srchColumn = $("#srchColumn").val();
		var srchWrd = $("#srchWrd").val();
		location.href = "${utcp.ctxPath}/admin/member/instrctrListExcel.do?srchColumn="
				+ srchColumn + "&srchWrd=" + srchWrd;
	}
	function fn_checkApply(){
		var formData = $('input[name=checkUserId]:checked').serialize();
		$.ajax({
			type:'post',
			url:'${utcp.ctxPath}/admin/ajax/applyCheckedInstrctr.json',
			data:formData,
			success:function(r){
				if(r.result.result==1){
					location.reload();
				}
			},
		});
	}
</script>

<!-- 문자발송 레이어 -->
<div class="remodal remodal-is-initialized remodal-is-opened" data-remodal-id="md-sms1" id="vm-sms1" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc" style="width: 480px;"  tabindex="-1" data-remodal-options="closeOnOutsideClick: false">
	<button data-remodal-action="close" class="remodal-close" aria-label="Close"></button>
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC"><i class="fas fa-chevron-circle-right fs_22 pdr5"></i>문자발송</p>
		</div>
		<div class="modal-body">
			<div class="tbBox1">
				<textarea class="w100 h400" v-model="msg"></textarea>
			</div>
		</div>
		<div class="modal-footer">
			<div class="tc">
				<button type="button" @click="sendSms" class="remodal-confirm btn01 btn_green">발송</button>
			</div>
		</div>
	</div>
</div>
<script>
var vm_sms_msg = '';
vm_sms_msg += '0000년 0월 안전교육시스템에서 신청하신 보육교직원 안전교육이 현재 미수료 상태로, 0/0(0)까지 수강 완료하셔야 합니다. \n';
vm_sms_msg += '(마이페이지-진행중 교육 클릭-진도율 100% 여부 확인)\n';
vm_sms_msg += '* 0월 신청하신 안전교육과정 중 1개라도 미이수하신 경우 문자 발송됨\n\n';
vm_sms_msg += '※ 보육교직원 안전교육은 기본, 심화, 영아담당교사Ⅰ,Ⅱ, 유아담당교사Ⅰ,Ⅱ 과정 중 매년 1개만 이수하시면 됩니다. (문의: 1600-0611) \n';

var vm_sms1 = new Vue({
	el: '#vm-sms1',
	data: {
		msg: vm_sms_msg,
		noPassList: [],
	},
	methods: {
		sendSms: function(){
			var _this = this;
			
			//발송목록체크
			if(_this.noPassList.length == 0){
				alert('발송대상이 없습니다.');return;
			}
			
			if(!confirm('문자발송 하시겠습니까?')){
				return;
			}
			
			$.ajax({
				type: 'post',
				data: {msg: _this.msg},
				url: '/admin/sms/sendSmsNoPassList.ajax',
				success: function(r){
					if(r.result == 1){
						alert('문자발송 완료하였습니다.');
						$('[data-remodal-id=md-sms1]').remodal().close();
						location.reload();
					}else{
						alert(r.msg);
					}
					
				}
			});
			
		},
		getCheckedMobiles: function(){
			const checkedBoxes = document.querySelectorAll('input[name="checkUserId"]:checked');
			const mobileList = [];
			checkedBoxes.forEach(cb => {
			    const mobile = cb.getAttribute('data-mobile');
			    mobileList.push(mobile);
			});

			const result = mobileList.join(',');
			return result;
		},
		callLayer: function(){
 			var toNum = this.getCheckedMobiles();
 			console.log(toNum);
			fn_md_smsSend('', toNum, '', fn_stdnt_closeSmeSend, true);
			//$('[data-remodal-id=md-sms1]').remodal().open();
			//location.href='#none';
		},
	},
});
function fn_stdnt_closeSmeSend(){
	alert('문자발송 완료하였습니다.');
}
</script>
