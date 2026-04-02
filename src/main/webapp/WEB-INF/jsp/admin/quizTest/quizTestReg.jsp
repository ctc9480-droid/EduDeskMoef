<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<section class="pd025 pd200 po_re">
	<div class="po_re br5 bs_box cf">
		<form id="form-reg">
			<input type="hidden" name="testTmplSeq" id="i-testTmplSeq" value="${param.testTmplSeq }" />
			<table class="tb02 tc" style="width: 100%;">
				<tbody>
					<tr>
						<th class="tdbg3 tc">시험지명</th>
						<td class="tl"><input type="text" name="testTmplNm" class="ip2" value="${data.tmpl.testTmplNm }" /> <input type="radio" name="status" value="1" id="i-useY" ${data.tmpl.status == 1?'checked':'' }> <label for="i-useY" class="pdl5 mgr10 cp">사용</label> <input type="radio" name="status" value="2" id="i-useN" ${data.tmpl.status == 2?'checked':'' }> <label for="i-useN" class="pdl5 mgr10 cp">중지</label></td>
					</tr>
					<tr>
						<th>형태</th>
						<td class="tl"><input type="radio" name="testTp" value="1" id="i-testTp1" ${data.tmpl.testTp == 1?'checked':'' }> <label for="i-testTp1">오프라인</label> <input type="radio" name="testTp" value="2" id="i-testTp2" ${data.tmpl.testTp == 2?'checked':'' }> <label for="i-testTp2">온라인</label></td>
					</tr>
					<tr>
						<th>카테고리</th>
						<td class="tl"><select name="ctg1Seq" id="ctg1Seq" class="vb">
								<option value="">1차카테고리</option>
								<c:forEach var="o" items="${cateList}" varStatus="stat">
									<option value="${o.qstnCtgSeq}" <c:if test='${data.tmpl.ctg1Seq == o.qstnCtgSeq}'>selected</c:if>>${o.ctgNm}</option>
								</c:forEach>
						</select> <select name="ctg2Seq" id="ctg2Seq" class="vb">
								<option value="">2차카테고리</option>
								<c:forEach var="o" items="${cateList2}" varStatus="stat">
									<option value="${o.qstnCtgSeq}" <c:if test='${data.tmpl.ctg2Seq == o.qstnCtgSeq}'>selected</c:if>>${o.ctgNm}</option>
								</c:forEach>
						</select> <select name="ctg3Seq" id="ctg3Seq" class="vb">
								<option value="0" selected>3차카테고리</option>
								<c:forEach var="o" items="${cateList3}" varStatus="stat">
									<option value="${o.qstnCtgSeq}" <c:if test='${data.tmpl.ctg3Seq == o.qstnCtgSeq}'>selected</c:if>>${o.ctgNm}</option>
								</c:forEach>
						</select></td>
					</tr>
					<tr>
						<th>제한시간</th>
						<td class="tl"><input type="text" name="timeLimit" class="ip9" value="${data.tmpl.timeLimit }" maxlength="3" /> 분 <span>(0:제한없음)</span></td>
					</tr>
					
					<tr style="display:none;">
						<th>체점방식</th>
						<td class="tl"><input type="radio" name="markTp" value="1" id="i-markTp1" ${data.tmpl.markTp == 1?'checked':'' }> <label for="i-markTp1" class="pdl5 mgr10 cp">자동채점</label> <input type="radio" name="markTp" value="2" id="i-markTp2" ${data.tmpl.markTp == 2?'checked':'' }> <label for="i-markTp2" class="pdl5 mgr10 cp">수동채점</label></td>
					</tr>
					
					<tr style="display:none;">
						<th>응시방식</th>
						<td class="tl"><input type="radio" name="lookTp" value="1" id="i-lookTp1" ${data.tmpl.lookTp == 1?'checked':'' }> <label for="i-lookTp1" class="pdl5 mgr10 cp">순차응시</label> <input type="radio" name="lookTp" value="2" id="i-lookTp2" ${data.tmpl.lookTp == 2?'checked':'' }> <label for="i-lookTp2" class="pdl5 mgr10 cp">랜덤응시</label></td>
					</tr>
					<tr>
						<th>출제(순서)방식</th>
						<td class="tl"><input type="radio" name="ordTp" value="1" id="i-ordTp1" ${data.tmpl.ordTp == 1?'checked':'' }> <label for="i-ordTp1" class="pdl5 mgr10 cp">순차출제</label> <input type="radio" name="ordTp" value="2" id="i-ordTp2" ${data.tmpl.ordTp == 2?'checked':'' }> <label for="i-ordTp2" class="pdl5 mgr10 cp">랜덤출제</label></td>
					</tr>
					<tr>
						<th>항목보기방식</th>
						<td class="tl"><input type="radio" name="choiceTp" value="1" id="i-choiceTp1" ${data.tmpl.choiceTp == 1?'checked':'' }> <label for="i-choiceTp1" class="pdl5 mgr10 cp">항목고정</label> <input type="radio" name="choiceTp" value="2" id="i-choiceTp2" ${data.tmpl.choiceTp == 2?'checked':'' }> <label for="i-choiceTp2" class="pdl5 mgr10 cp">랜덤항목</label></td>
					</tr>
					<tr>
						<th>문제설명</th>
						<td class="tl"><textarea id="i-descr" name="descr" style="width: 100%; height: 100px;">${data.tmpl.descr }</textarea></td>
					</tr>
				</tbody>
			</table>
			<c:if test="${data.tmpl.testTmplSeq > 0 }">
			<jsp:include page="quizTestReg_inc_qstnList.jsp"></jsp:include>
			</c:if>

		</form>
		<div class="fl tc">
			<button class="btn02 btn_grayl mgb20" onclick="location.href='quizTestList.do';">목록</button>
		</div>
		<div class="tc">
			<button type="button" onclick="fn_save()" class="btn01 btn_greenl">등록완료</button>
		</div>
	</div>
</section>

<script>
function fn_save(){
	var _o1 = $('input[name=testTmplNm]');
	if(_o1.val() == ''){
		alert('시험지명을 입력하세요');_o1.focus();return;
	}
	
	_o1 = $('#ctg1Seq');
	if(_o1.val() == ''){ alert('1차카테고리를 입력하세요');_o1.focus();return; }
	_o1 = $('#ctg2Seq');
	if(_o1.val() == ''){ alert('2차카테고리를 입력하세요');_o1.focus();return; }
	_o1 = $('#i-descr');
	if(_o1.val() == ''){ alert('문제설명을 입력하세요');_o1.focus();return; }
	
	var formData = $('#form-reg').serializeArray();
	//formData.push({name:'chListJson',value:JSON.stringify(vm_qstn_ch.chList)});
	console.log(formData);
	$.ajax({
		type : 'post',
		data : formData,
		url : 'saveQuizTest.ajax',
		success : function(r){
			console.log('success',r);
			//return;
			if(r.result == 1){
				location.href='?testTmplSeq='+r.data.testTmplSeq;
			}else{
				alert(r.msg);
			}
			
		}
	});
}
</script>

<script>
var ctg1SeqSelector = document.querySelector("#ctg1Seq");
var ctg2SeqSelector = document.querySelector("#ctg2Seq");
ctg1SeqSelector.addEventListener("change",function(){
	fn_getChildCtgrySeqList('ctg2Seq',this.value);
});
ctg2SeqSelector.addEventListener("change",function(){
	fn_getChildCtgrySeqList('ctg3Seq',this.value);
});
function fn_getChildCtgrySeqList(_id,qstnCtgSeq){
	//자식카테고리 가져옴
	$.ajax({
        url: "${utcp.ctxPath}/admin/testMng/getQstnCtgChildList.ajax",
        type: "POST",
        data: { "parentSeq" : qstnCtgSeq },
        cache: false,
		async: true,
        success: function(r) {
        	$("#"+_id).empty();
        	if(_id == 'ctg2Seq'){
	        	$("#"+_id).append("<option value=\"\">2차 카테고리</option>"	);
	        	$("#ctg3Seq").empty();
	        	$("#ctg3Seq").append("<option value=\"\">3차 카테고리</option>");
        	}else{
        		$("#"+_id).append("<option value=\"0\">3차 카테고리</option>"	);
        	}
       		for(var i in r.data.cateList) {
       			var o = r.data.cateList[i];
       			$("#"+_id).append(
       				"<option value=\"" + o.qstnCtgSeq + "\">" + o.ctgNm + "</option>"
       			);
       		}
		}
    });
}
</script>

<%-- 
<script src="${utcp.ctxPath}/resources/ckeditor4_full/ckeditor.js"></script>
<script>
CKEDITOR.config.allowedContent = true;
CKEDITOR.replace('ckeditCn', {
	//filebrowserUploadUrl : '${utcp.ctxPath}/editot/popupUpload.json?gubun=quizBank&prefixStr=1',
	removePlugins: 'image,image2', // 이미지 삽입 도구 모음 제거
	height : 400,
});
</script> 
--%>


