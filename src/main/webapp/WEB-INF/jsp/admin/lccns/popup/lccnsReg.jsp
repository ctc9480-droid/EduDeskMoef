<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<section class="pd30">
	<div class="po_re br5 bs_box cf">
		<form id="form-reg">
			<input type="hidden" name="eduSeq" id="i-eduSeq" value="${param.eduSeq }" />
			<input type="hidden" name="cnsSeq" id="i-cnsSeq" value="${param.cnsSeq }" />
			<input type="hidden" name="userId" id="i-userId" value="${param.userId }" />
			<table class="tb02 tc" style="width: 100%;">
				<tbody>
					<tr>
						<th class="tdbg3 tc">상담제목</th>
						<td class="tl">
						<input type="text" name="title" class="ip4 mgr10" value="${data.cns.title }" required title="상담제목" /> 
					
						<%-- 
						<input type="radio" name="state" value="1" id="i-state1" ${data.cns.state == 1?'checked':'' }> <label for="i-state1" class="pdl5 mgr10 cp">노출</label> 
						<input type="radio" name="state" value="2" id="i-state2" ${data.cns.state != 1?'checked':'' }> <label for="i-state2" class="pdl5 mgr10 cp">미노출</label>
						 --%>
						 <input type="hidden" name="state" value="1"/>
						</td>
					</tr>
					<tr>
						<th>상담내용</th>
						<td class="tl">
							<textarea style="width:100%;height:300px;" name="content">${data.cns.content }</textarea>
						</td>
					</tr>
					
				</tbody>
			</table>
		</form>
		<div class="fl tc">
			<button class="btn02 btn_grayl mgb20" onclick="fn_list()">목록</button>
		</div>
		<div class="tc">
			<c:if test="${sessionScope.sessionUserInfo.userId eq data.cns.regId or param.cnsSeq == 0 }">
			<button type="button" class="btn01 btn_greenl" onclick="fn_save()">저장</button>
			</c:if>
		</div>
	</div>
</section>

<script>
var $subNm = $('input[name=title]');
var userId = $('#i-userId').val();
var eduSeq = $('#i-eduSeq').val();
$(document).ready(function(){
	
});
function fn_list(){
	location.href='lccnsList.do?userId='+userId+'&eduSeq='+eduSeq;
}
function fn_save(){
	if($subNm.val()==''){
		$subNm.focus();		alert('상담제목은 필수 입력입니다.');		return;
	}
	
	var formData = $('#form-reg').serializeArray();
	$.ajax({
		type : 'post',
		data : formData,
		url : '${utcp.ctxPath}/admin/lccns/saveLccns.ajax',
		beforeSubmit: function(){ 
			return;
		},
		success : function(r){
			//return;
			if(r.result == 1){
				location.href='lccnsList.do?userId='+userId+'&eduSeq='+eduSeq;
			}else{
				alert(r.msg);
			}
			
		}
	});
}

</script>

