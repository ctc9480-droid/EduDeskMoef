<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<section class="pd025 pd200 po_re">
	<div class="po_re br5 bs_box cf">
		<form id="form-reg">
			<input type="hidden" name="qstnSeq" id="i-qstnSeq" value="${param.qstnSeq }" />
			<input type="hidden" name="cn" id="i-qstnDesc" value="" />
			<table class="tb02 tc" style="width: 100%;">
				<tbody>
					<tr>
						<th class="tdbg3 tc">문제명</th>
						<td class="tl"><input type="text" name="qstnNm" class="ip2 mgr10" value="${data.qstn.qstnNm }" required title="문제명" /> <input type="radio" name="useYn" value="Y" id="i-useY" ${data.qstn.useYn ne 'N'?'checked':'' }> <label for="i-useY" class="pdl5 mgr10 cp">사용</label> <input type="radio" name="useYn" value="N" id="i-useN" ${data.qstn.useYn eq 'N'?'checked':'' }> <label for="i-useN" class="pdl5 mgr10 cp">중지</label></td>
					</tr>
					<tr>
						<th>카테고리</th>
						<td class="tl">
							<select name="ctg1Seq" id="ctg1Seq" class="vb">
								<option value=""  >1차카테고리</option>
								<c:forEach var="o" items="${cateList}" varStatus="stat">
									<option value="${o.qstnCtgSeq}" <c:if test='${data.qstn.ctg1Seq == o.qstnCtgSeq}'>selected</c:if>>${o.ctgNm}</option>
								</c:forEach>
							</select>
							<select name="ctg2Seq" id="ctg2Seq" class="vb">
								<option value="" >2차카테고리</option>
								<c:forEach var="o" items="${cateList2}" varStatus="stat">
									<option value="${o.qstnCtgSeq}" <c:if test='${data.qstn.ctg2Seq == o.qstnCtgSeq}'>selected</c:if>>${o.ctgNm}</option>
								</c:forEach>
							</select>
							<select name="ctg3Seq" id="ctg3Seq" class="vb">
								<option value="0" selected>3차카테고리</option>
								<c:forEach var="o" items="${cateList3}" varStatus="stat">
									<option value="${o.qstnCtgSeq}" <c:if test='${data.qstn.ctg3Seq == o.qstnCtgSeq}'>selected</c:if>>${o.ctgNm}</option>
								</c:forEach>
							</select>	
						</td>
					</tr>
					<tr>
						<th>난이도</th>
						<td class="tl"><select name="diffType" required>
								<c:forEach items="${qbcp.getDiffTypeArr() }" var="o" varStatus="s">
									<option value="${s.index}" ${data.qstn.diffType == s.index?'selected':'' }>${s.index==0?'선택':o }</option>
								</c:forEach>
						</select></td>
					</tr>
					<tr>
						<th>문제유형</th>
						<td class="tl"><select name="qstnTp">
								<c:forEach items="${qbcp.getQstnTpArr() }" var="o" varStatus="s">
									<option value="${s.index}" ${data.qstn.qstnTp == s.index?'selected':'' }>${s.index==0?'선택':o }</option>
								</c:forEach>
						</select></td>
					</tr>
					<tr>
						<th>문항</th>
						<td class="tl"><textarea name="qstnStr" style="width: 100%; height: 50px;">${data.qstn.qstnStr }</textarea></td>
					</tr>
					<tr style="display:none;">
						<th>문제설명</th>
						<td class="tl"><textarea id="ckeditCn">${data.qstn.qstnDesc }</textarea></td>
					</tr>
					<tr>
						<th>문제파일</th>
						<td class="tl"><c:if test="${not empty data.qstn.qstnFnRnm}">
								<img width="100px" src="${utcp.ctxPath }/DATA/upload/web/quizBank/${param.qstnSeq}/${data.qstn.qstnFnRnm}" />
								<a href="javascript: fn_delQstnFile(-1,'${data.qstn.qstnFnRnm}')">[삭제]</a>
							</c:if>
							<div class="write_div">
								<span id="photo_div"> <label for="imgFile">
										<span class="sound_only">파일</span>
									</label> <input type="file" name="qstnFn" class="frm_file ip2">
									
								</span>
							</div></td>
					</tr>
					<tr id="area-chList">
						<th>객관식 설정</th>
						<td class="tl mcqtd"><jsp:include page="/WEB-INF/jsp/admin/quizBank/quizBankReg_inc_chList.jsp"></jsp:include></td>
					</tr>
					<tr id="area-fillBlank">
						<th>단답형 정답</th>
						<td class="tl"><input name="fillBlank" class="ip2" value="${data.qstn.fillBlank }"/></td>
					</tr>
					<tr>
						<th>정답설명</th>
						<td class="tl"><textarea style="width: 100%; height: 100px;" name="ansDesc">${data.qstn.ansDesc }</textarea></td>
					</tr>
				</tbody>
			</table>
		</form>
		<div class="fl tc">
			<button class="btn02 btn_grayl mgb20" onclick="location.href='quizBankList.do';">목록</button>
		</div>
		<div class="tc">
			<button type="button" class="btn01 btn_greenl" onclick="fn_save()">등록완료</button>
		</div>
	</div>
</section>

<script>
var $fillBlank = $('input[name=fillBlank]');
var $qstnTp = $('select[name=qstnTp]');
$(document).ready(function(){
	$('select[name=qstnTp]').change(function(){
		console.log(this.value);
		fn_setAreaByQstnTp(this.value)
	});
	
	//문제유혀엥따른 화면 세팅
	var dataQstnTp = '${data.qstn.qstnTp}'*1;
	fn_setAreaByQstnTp(dataQstnTp);
});
function fn_setAreaByQstnTp(qstnTp){
	if(qstnTp == 1 || qstnTp == 2){
		$('#area-chList').show();
		$('#area-fillBlank').hide();
	}else if(qstnTp == 3){
		$('#area-chList').hide();
		$('#area-fillBlank').show();
	}else{
		$('#area-chList').hide();
		$('#area-fillBlank').hide();
	}
}
function fn_save(){
	if($('[name=qstnNm]').val()==''){
		$('[name=qstnNm]').focus();		alert('문제명은 필수 입력입니다.');		return;
	}
	if($('select[name=diffType]').val()=='0'){
		$('[name=diffType]').focus();	alert('난이도는 필수 입력입니다.');		return;
	}
	if($('select[name=qstnTp]').val()=='0'){
		$('[name=qstnTp]').focus();		alert('문제유형은 필수 입력입니다.');		return;
	}else{
		var value = $('select[name=qstnTp]').val();
		if(value == 1 || value == 2){
			//객관식 2개이상 체크
			if(vm_qstn_ch.chList.length < 2){
				alert('객관식보기가 2개이상 설정되야 합니다.');
				return;
			}else{
				var emptyOrNullObjects = vm_qstn_ch.chList.filter(item => item.chStr === null || item.chStr.trim() === '');
				console.log('emptyOrNullObjects',emptyOrNullObjects);
				if(emptyOrNullObjects.length){
					alert('객관식명을 입력하셔야 합니다.');return;
				}
				
				var ansCnt = 0;
				for(var i in vm_qstn_ch.chList){
					var o = vm_qstn_ch.chList[i];
					if(o.chAns){
						ansCnt++;
					}
				}	
				if(ansCnt == 0){
					alert('객관식의 답을 설정하셔야 합니다.');return;
				}
				if(ansCnt > 1 && value == 1){
					alert('객관식유형입니다. 답이 2개이상 설정되어 있습니다.');return;
				}
				
			}
		}else if(value == 3){
			//단답형 정답 기재했는지 체크
			if($fillBlank.val() == ''){
				$fillBlank.focus();
				alert('단답형 정답을 입력하셔야 합니다');return;
			}
		}
	}
	$('#i-qstnDesc').val(CKEDITOR.instances.ckeditCn.getData());
	
	var formData = $('#form-reg').serializeArray();
	formData.push({name:'chListJson',value:JSON.stringify(vm_qstn_ch.chList)});
	$.ajax({
		type : 'post',
		data : formData,
		url : 'saveQuizBank.ajax',
		beforeSubmit: function(){ 
			return;
		},
		success : function(r){
			//return;
			if(r.result == 1){
				$('#i-qstnSeq').val(r.data.qstnSeq);
				var formData2 = new FormData($('#form-reg')[0]);
				$.ajax({
					type : 'post',
					contentType: false,
			        processData: false,
					data : formData2,
					url : 'saveQuizBankFile.ajax',
					success : function(r){
						console.log('success2',r);
						if(r.result == 1){
							location.href='quizBankList.do';
						}
					}
				});
			}
		}
	});
}
function fn_delQstnFile(chSeq,fnRnm){
	if(!confirm('문제파일을 삭제하시겠습니까?')){
		return;
	}
	var qstnSeq = $('#i-qstnSeq').val();
	$.ajax({
		type: 'post',
		data: {qstnSeq: qstnSeq, chSeq: chSeq, fnRnm: fnRnm},
		url: 'delQuizBankFile.ajax',
		success: function(r){
			if(r.result == 1){
				location.reload();
			}
		}
	});
}

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
	        	$("#"+_id).append("<option value=\"0\">2차 카테고리</option>"	);
	        	$("#ctg3Seq").empty();
	        	$("#ctg3Seq").append("<option value=\"0\">3차 카테고리</option>");
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


<script src="${utcp.ctxPath}/resources/ckeditor4_full/ckeditor.js"></script>
<script>
CKEDITOR.config.allowedContent = true;
CKEDITOR.replace('ckeditCn', {
	//filebrowserUploadUrl : '${utcp.ctxPath}/editot/popupUpload.json?gubun=quizBank&prefixStr=1',
	removePlugins: 'image,image2', // 이미지 삽입 도구 모음 제거
	height : 400,
});
</script>