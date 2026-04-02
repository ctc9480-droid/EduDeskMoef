<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<section class="pd025 pd200 po_re">
	<div class="po_re br5 bs_box cf">
		<form id="form-reg">
			<input type="hidden" name="subSeq" id="i-subSeq" value="${param.subSeq }" />
			<table class="tb02 tc" style="width: 100%;">
				<tbody>
					<tr>
						<th class="tdbg3 tc">과목명</th>
						<td class="tl"><input type="text" name="subNm" class="ip2 mgr10" value="${data.sub.subNm }" required title="문제명" /> 
						<input type="radio" name="state" value="1" id="i-state1" ${data.sub.state != 2?'checked':'' }> <label for="i-state1" class="pdl5 mgr10 cp">노출</label> 
						<input type="radio" name="state" value="2" id="i-state2" ${data.sub.state == 2?'checked':'' }> <label for="i-state2" class="pdl5 mgr10 cp">미노출</label></td>
					</tr>
					<tr>
						<th>수료증</th>
						<td class="tl">
							<select name="complIdx" id="complIdx" class="vb">
								<option value="">수료증 선택</option>
								<c:forEach var="o" items="${complList}" varStatus="stat">
									<option value="${o.passIdx}" <c:if test='${data.sub.complIdx == o.passIdx}'>selected</c:if>>${o.title}</option>
								</c:forEach>
							</select>
							<input type="text" name="complCertNm" value="${data.sub.complCertNm }" class="ip5" maxlength="5" placeholder="다섯글자 내"/>
						</td>
					</tr>
					<tr>
						<th>합격증</th>
						<td class="tl">
							<select name="passIdx" id="passIdx" class="vb">
								<option value="">합격증 선택</option>
								<c:forEach var="o" items="${passList}" varStatus="stat">
									<option value="${o.passIdx}" <c:if test='${data.sub.passIdx == o.passIdx}'>selected</c:if>>${o.title}</option>
								</c:forEach>
							</select>
							<input type="text" name="passCertNm" value="${data.sub.passCertNm }" class="ip5" maxlength="5" placeholder="다섯글자 내"/>
						</td>
					</tr>
					
				</tbody>
			</table>
		</form>
		<div class="fl tc">
			<button class="btn02 btn_grayl mgb20" onclick="location.href='lcsubList.do';">목록</button>
		</div>
		<div class="tc">
			<button type="button" class="btn01 btn_greenl" onclick="fn_save()">등록완료</button>
		</div>
	</div>
</section>

<script>
var $subNm = $('input[name=subNm]');
$(document).ready(function(){
	
});
function fn_save(){
	if($subNm.val()==''){
		$subNm.focus();		alert('과목명은 필수 입력입니다.');		return;
	}
	
	var formData = $('#form-reg').serializeArray();
	$.ajax({
		type : 'post',
		data : formData,
		url : 'saveLcsub.ajax',
		beforeSubmit: function(){ 
			return;
		},
		success : function(r){
			//return;
			if(r.result == 1){
				location.href='lcsubList.do';
			}
		}
	});
}

</script>

