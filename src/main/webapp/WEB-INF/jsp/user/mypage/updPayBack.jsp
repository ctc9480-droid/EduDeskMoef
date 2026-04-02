<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp" %>
<div class="listWrap" style="padding-top:0">
	<table class="tb03 mt50" style="border-top:3px solid #555; ">
		<form id="form-payBack">
			<tr>
				<th>은행명</th>
				<td><input type="text" id="bankNm" name="bankNm" class="nm_size" value="${result.data.payBack.bankNm }" maxlength="50"></td>
			</tr>
			<tr>
				<th>예금주명</th>
				<td><input type="text" id="accountNm" name="accountNm" class="nm_size" value="${result.data.payBack.accountNm }" maxlength="50"></td>
			</tr>
			<tr>
				<th>계좌번호</th>
				<td><input type="text" id="accountNo" name="accountNo" class="nm_size" value="${result.data.payBack.accountNo }" maxlength="50"></td>
			</tr>
		</form>
	</table>
	<div class="btn_step2" >
		<a href="javascript:;" onclick="fn_savePayBack(); return false;" class="btn_step01">수정하기</a>
		<a href="javascript:history.back();" class="btn_step02">취소</a>
	</div>
</div>
<script type="text/javascript">
function fn_savePayBack(){
	$.ajax({
		type:'post',
		data:$('#form-payBack').serialize(),
		url:'${utcp.ctxPath}/user/ajax/savePayBack.json',
		success:function(r){
			if(r.result==1){
				alert('저장되었습니다.');
				location.reload();
			}else{
				alert('에러');
			}
		}
	});
}
</script>