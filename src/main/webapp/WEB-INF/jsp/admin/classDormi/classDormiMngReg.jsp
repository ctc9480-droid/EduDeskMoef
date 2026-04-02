<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp" %>

<section class="pd025 pd200 po_re">
	<div class="po_re br5 bs_box cf">
		<form method="post" id="regForm" name="regForm" >
			<input type="hidden" name="dormiSeq" id="dormiSeq" value="${param.dormiSeq}"/>
			<input type="hidden" name="accessYn" value="${dormi.accessYn }"/>
			<table width="100%" class="tb02 tc">
				<tbody>
					<tr>
						<th class="tdbg3 tc"><label for="eduNm">숙소 명</label></th>
						<td class="tl" colspan="">
							<input type="text" name="dormiNm" id="dormiNm" class="ip2 mgr10" maxlength="400" value="${dormi.dormiNm}"/>										
						</td>
					</tr>
					<tr>
						<th class="tdbg3 tc"><label for="eduNm">위치</label></th>
						<td class="tl" colspan="">
							<input type="text" name="place" id="place" class="ip2 mgr10" maxlength="400" value="${dormi.place}"/>										
						</td>
					</tr>
					<tr>
						<th class="tdbg3 tc"><label for="eduNm">형태</label></th>
						<td class="tl" colspan="">
							<select name="capaCnt">
								<option value="2" ${dormi.capaCnt == 2?'selected':'' }>2인실</option>
								<option value="4" ${dormi.capaCnt == 4?'selected':'' }>4인실</option>
							</select>
							<input type="checkbox" id="access" ${dormi.accessYn eq 'Y'?'checked':'' }/>			
							<label for="dormiAccess">장애인용</label>			
						</td>
					</tr>
					<tr >
						<th class="tdbg3 tc">메모</th>
						<td class="tl" colspan="3">
							<textarea id="memo" name="memo" placeholder="메모" class="w100 h500">${dormi.memo}</textarea>
						</td>
					</tr>		
					<tr>
						<th class="tdbg3 tc">사용여부</th>
						<td class="tl" colspan="3">
							<input type="radio" name="useYn" value="Y" id="useY" ${dormi.useYn eq 'Y' || empty room.useYn?'checked':'' } />
							<label for="useY" class="pdl5 mgr15 cp">예</label>
							<input type="radio" name="useYn" value="N" id="useN" ${dormi.useYn eq 'N'?'checked':'' } />
							<label for="useN" class="pdl5 mgr15 cp">아니오</label>
						</td>
					</tr>				
				</tbody>
			</table>
			<div class="fl tc">
				<button type="button" class="btn01 btn_greenl mgb20" onclick="fn_del();">삭제</button>
		</div>
			
			<div class="tc">
				<button type="button" class="btn01 btn_greenl mgb20" onclick="fn_save();">등록</button>
				<button type="button" class="btn01 btn_grayl mgb20" onclick="history.back();">취소</button>
			</div>						
		</form>							
	</div>
</section> 
<script>
var $dormiNm = $('input[name=dormiNm]');

function fn_del(){
	if(!confirm('삭제하시겠습니까?')){
		return;
	}
	
	
	var formData = $('#regForm').serialize();
	$.ajax({
		type : 'post',
		url : 'classDormiMngDelProc.ajax',
		data : formData,
		success : function(r){
			if(r.result != 1){
				alert(r.msg);
				return;
			}
			alert('삭제되었습니다.');
			location.href='classDormiMng.do';
		}
	});
}
function fn_save(){
	if($dormiNm.val()==''){
		$dormiNm.focus();		alert('숙소명은 필수 입력입니다.');		return;
	}
	 var accessYn = $("#access").is(":checked") ? "Y" : "N";
	 $('input[name=accessYn]').val(accessYn);
	
	var formData = $('#regForm').serialize();
	$.ajax({
		type : 'post',
		url : 'classDormiMngRegProc.ajax',
		data : formData,
		success : function(r){
			if(r.result != 1){
				alert(r.msg);
				return;
			}
			alert('저장되었습니다.');
			location.href='classDormiMng.do';
		}
	});
}
</script>
