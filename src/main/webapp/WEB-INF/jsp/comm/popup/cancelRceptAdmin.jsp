<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<div id="wrap">
	<div id="content" class="pdt0 w100">
		<form id="form-refundRcept">
		<input type="hidden" name="rceptSeq" value="${param.rceptSeq }"/>
		<table class="tb04 w100">
			<tbody>
				<tr>
					<th>작성자</th>
					<td><input type="text" value="${data.rcept.userNm }" readonly /></td>
				</tr>
			</tbody>
		</table>
		<table class="tb04 w100">
			<tbody>
				<tr>
					<th>환불요청금액</th>
					<td><input type="text" name="rfndReqFee" value="${data.rcept.rfndReqFee }" /></td>
					<th>환불 은행명</th>
					<td><input type="text" name="rfndBankNm" value="${data.rcept.rfndBankNm }" /></td>
				</tr>
				<tr>
					<th>예금주</th>
					<td><input type="text" name="rfndAccountNm" value="${data.rcept.rfndAccountNm }" /></td>
					<th>계좌번호</th>
					<td><input type="text" name="rfndAccountNo" value="${data.rcept.rfndAccountNo }" /></td>
				</tr>
			</tbody>
		</table>
		</form>
		<div class="w100 tc mgt20">
			<button class="btn04 btn_blue" onclick="fn_refundRcept()">신청</button>
		</div>
	</div>
</div>

<script>
function fn_refundRcept(){
	return;
	var formData = $('#form-refundRcept').serialize();
	$.ajax({
		type: 'post',
		url: '${utcp.ctxPath}/admin/ajax/refundRceptProc.ajax',
		data: formData,
		success: function(r){
			
		}
	});
}
</script>