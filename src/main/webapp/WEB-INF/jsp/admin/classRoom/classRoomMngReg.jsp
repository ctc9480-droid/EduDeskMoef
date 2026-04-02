<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp" %>

<section class="pd025 pd200 po_re">
	<div class="po_re br5 bs_box cf">
		<form method="post" id="regForm" name="regForm" >
			<input type="hidden" name="roomSeq" id="roomSeq" value="${param.roomSeq}">
			<table width="100%" class="tb02 tc">
				<tbody>
					<tr>
						<th class="tdbg3 tc"><label for="eduNm">강의실 명</label></th>
						<td class="tl" colspan="">
							<input type="text" name="roomNm" id="roomNm" class="ip2 mgr10" maxlength="400" value="${room.roomNm}"/>										
						</td>
					</tr>
					<tr>
						<th class="tdbg3 tc"><label for="eduNm">위치</label></th>
						<td class="tl" colspan="">
							<input type="text" name="place" id="place" class="ip2 mgr10" maxlength="400" value="${room.place}"/>										
						</td>
					</tr>
					<tr >
						<th class="tdbg3 tc">메모</th>
						<td class="tl" colspan="3">
							<textarea id="memo" name="memo" placeholder="메모" class="w100 h500">${room.memo}</textarea>
						</td>
					</tr>		
					<tr>
						<th class="tdbg3 tc">사용여부</th>
						<td class="tl" colspan="3">
							<input type="radio" name="useYn" value="Y" id="useY" ${room.useYn eq 'Y' || empty room.useYn?'checked':'' } />
							<label for="useY" class="pdl5 mgr15 cp">예</label>
							<input type="radio" name="useYn" value="N" id="useN" ${room.useYn eq 'N'?'checked':'' } />
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
var $roomNm = $('input[name=roomNm]');
function fn_del(){
	if(!confirm('삭제하시겠습니까?')){
		return;
	}
	var formData = $('#regForm').serialize();
	$.ajax({
		type : 'post',
		url : 'classRoomMngDelProc.ajax',
		data : formData,
		success : function(r){
			if(r.result != 1){
				alert(r.msg);
				return;
			}
			alert('삭제되었습니다.');
			location.href='classRoomMng.do';
		}
	});
}
function fn_save(){
	if($roomNm.val()==''){
		$roomNm.focus();		alert('강의실명은 필수 입력입니다.');		return;
	}
	
	
	var formData = $('#regForm').serialize();
	$.ajax({
		type : 'post',
		url : 'classRoomMngRegProc.ajax',
		data : formData,
		success : function(r){
			if(r.result != 1){
				alert(r.msg);
				return;
			}
			alert('저장되었습니다.');
			location.href='classRoomMng.do';
		}
	});
}
</script>
