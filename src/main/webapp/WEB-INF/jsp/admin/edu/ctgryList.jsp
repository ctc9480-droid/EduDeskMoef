<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<style>
  .truncate {
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
    max-width: 24ch;
  }
</style>
<script>
$(function(){
	$("#checkAll01").click(function() {
		if($("#checkAll01").is(":checked") == true){
			$("input[name=ctgrySeqs1]:checkbox").each(function() {
				$(this).prop("checked", true);
			});	
		}else{
			$("input[name=ctgrySeqs1]:checkbox").each(function() {
				$(this).prop("checked", false);
			});
		}
	});
	
	$("#checkAll02").click(function() {
		if($("#checkAll02").is(":checked") == true){
			$("input[name=ctgrySeqs2]:checkbox").each(function() {
				$(this).prop("checked", true);
			});	
		}else{
			$("input[name=ctgrySeqs2]:checkbox").each(function() {
				$(this).prop("checked", false);
			});
		}
	});
	
	$('#area-tr-passIdx').hide();
	$('#area-tr-openClass').hide();
	$('#area-tr-fbIdx').hide();
});

function fn_add(depth){
	//
	$('#parentSeq').val(0);
	$('#passCd').val("");
	if(depth==3){
		$('#parentSeq').val($('#parentSeq2').val());
		$('#area-tr-passCd').show();
	}else if(depth==2){
		$('#parentSeq').val($('#parentSeq1').val());
		$('#area-tr-passCd').hide();
	}else{
		$('#area-tr-passCd').hide();
	}
	
	$("#ctgryDepth").val(depth);
	$("#command").val("add");
	$("#addFrmTitle").text("카테고리 추가");
	$("input:radio[id='useY']").prop("checked", true);
	
	$('#ctgryNm').val('');
	$("input:radio[name=openClass]:input[value=1]").prop("checked", true);
	$("input:radio[name=passIdx]:input[value=1]").prop("checked", true);
	
	if($("#ctgryDepth").val()==2 && $("#parentSeq").val()==0){
		alert('입력할 수 없습니다.');return;
	}
	
	
	
	location.href = "#ctgryAdd";
}

function fn_upd(ctgrySeq, depth){
	if(depth==1){
		$('#area-tr-passCd').show();
	}else{
		$('#area-tr-passCd').hide();
	}
	
	$('#ctgryNm').val('');
	$("input:radio[name=openClass]:input[value=1]").prop("checked", true);
	$("input:radio[name=passIdx]:input[value=1]").prop("checked", true);
	
	$.ajax({
        url: "${utcp.ctxPath}/admin/edu/ctgryView.json",
        type: "POST",
        data: {
        	"ctgrySeq" : ctgrySeq
        },
        cache: false,
		async: true,
        success: function(r) {
			if(r.result==1){
				$("#ctgrySeq").val(r.data.ctgrySeq);
				$("#ctgryDepth").val(r.data.ctgryDepth);
				$("#ctgryNm").val(r.data.ctgryNm);
				$("#passCd").val(r.data.passCd);
				$("#command").val("upd");
				$("#addFrmTitle").text("카테고리 수정");
				$("#parentSeq").val(r.data.parentSeq);
				$("#fbIdx").val(r.data.fbIdx);
				$("#fbTitle").val(r.data.fbTitle);
				if(r.data.useYn == "Y"){
					$("input:radio[id='useY']").prop("checked", true);	
				}else{
					$("input:radio[id='useN']").prop("checked", true);
				}
				$('input:radio[name=openClass]:input[value='+r.data.openClass+']').prop('checked',true);
				$('input:radio[name=passIdx]:input[value='+r.data.passIdx+']').prop('checked',true);
				//1뎁스인경우 수료증,만족도선택 숨김
				//$('#area-tr-passIdx').show();
				//$('#area-tr-fbIdx').show();
				if(r.data.ctgryDepth==1){
					$('#area-tr-passIdx').hide();
					$('#area-tr-fbIdx').hide();
				}
				
				location.href = "#ctgryAdd";
			}else{
				vm_alert.message = '에러';
				location.href = '#md-alert';
			}
		}
    });
}

function fn_del(depth){
	var ctgrySeqs = new Array();
	var i = 0;
	if(depth == 1){
		if($("input[name='ctgrySeqs1']:checked").length == 0){
			$("#messageStr").html("삭제할 카테고리를 선택하세요.");
			location.href = "#message";
			return;
		}else{
			$("input[name=ctgrySeqs1]:checked").each(function() {
				ctgrySeqs[i] = Number($(this).val());
				i++;
			}); 
		}
	}else if(depth == 2){
		ctgrySeqs = $('input[name=ctgrySeqs2]:checked').val();
		if($("input[name='ctgrySeqs2']:checked").length == 0){
			$("#messageStr").html("삭제할 카테고리를 선택하세요.");
			location.href = "#message";
			return;
		}else{
			$("input[name=ctgrySeqs2]:checked").each(function() {
				ctgrySeqs[i] = Number($(this).val());
				i++;
			});
		}
	}else{
		ctgrySeqs = $('input[name=ctgrySeqs3]:checked').val();
		if($("input[name='ctgrySeqs3']:checked").length == 0){
			$("#messageStr").html("삭제할 카테고리를 선택하세요.");
			location.href = "#message";
			return;
		}else{
			$("input[name='ctgrySeqs3']:checked").each(function() {
				ctgrySeqs[i] = Number($(this).val());
				i++;
			});
		}
		
		
	}
	
	$.ajax({
        url: "${utcp.ctxPath}/admin/edu/ctgryDelete.json",
        type: "POST",
        traditional : true,
        data: {
        	"ctgrySeqs" : ctgrySeqs
        },
        cache: false,
		async: true,
        success: function(r) {
			if(r.isAdmin){
				location.href = "#messagePop";
			}else{
				location.href = "#authMessage";
			}
		}
    });
}

function fn_excute(){	
	if(document.getElementById('ctgryDepth').value=="3"){
		 var passCd = document.getElementById('passCd').value;
		// 글자 길이 체크
		if (passCd.length > 10) {
		  alert('10자 이하로 입력해주세요.');
		  document.getElementById('passCd').value = passCd.substring(0, 10);
		  return;
		}
		
		// 숫자로 시작하는지 체크
		if (/^[0-9]/.test(passCd)) {
		  alert('숫자로 시작할 수 없습니다.');
		  document.getElementById('passCd').value = '';
		  return;
		}
		
		// 한글 입력 체크
		if (/[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/.test(passCd)) {
		  alert('한글 입력은 불가능합니다.');
		  document.getElementById('passCd').value = '';
		  return;
		}
		
		// 특수 문자 입력 체크
		if (/[~!@#$%^&*()_+|<>?:{}]/.test(passCd)) {
		  alert('특수 문자는 입력할 수 없습니다.');
		  document.getElementById('passCd').value = '';
		  return;
		}
		

	}
	if($("#ctgryNm").val() == ""){
		alert("명칭을 입력하세요.");
		return;
	}
	if($("#command").val() == "add"){
		$.ajax({
	        url: "${utcp.ctxPath}/admin/edu/ctgryAdd.json",
	        type: "POST",
	        data: {
	        	"ctgryDepth" : $("#ctgryDepth").val(),
	        	"ctgryNm" : $("#ctgryNm").val(),
	        	"passCd" : $("#passCd").val(),
	        	"useYn" : $(':radio[name="useYn"]:checked').val(),
	        	"parentSeq" : $("#parentSeq").val(),
	        	'fbIdx':$('#fbIdx').val(),
	        	'passIdx':$(':radio[name="passIdx"]:checked').val(),
	        	'openClass':$(':radio[name="openClass"]:checked').val(),
	        },
	        cache: false,
			async: true,
	        success: function(r) {
				if(r.isAdmin){
					fn_reload();
				}else{
					location.href = "#authMessage";
				}
			}
	    });
	}else if($("#command").val() == "upd"){
		$.ajax({
	        url: "${utcp.ctxPath}/admin/edu/ctgryUpd.json",
	        type: "POST",
	        data: {
	        	"ctgrySeq" : $("#ctgrySeq").val(),
	        	"ctgryDepth" : $("#ctgryDepth").val(),
	        	"ctgryNm" : $("#ctgryNm").val(),
	        	"passCd" : $("#passCd").val(),
	        	"useYn" : $(':radio[name="useYn"]:checked').val(),
	        	'fbIdx':$('#fbIdx').val(),
	        	'passIdx':$(':radio[name="passIdx"]:checked').val(),
	        	'openClass':$(':radio[name="openClass"]:checked').val(),
	        },
	        cache: false,
			async: true,
	        success: function(r) {
				if(r.result==1){
					fn_reload();
				}else{
					vm_alert.message = '에러';
					location.href = '#md-alert';
				}
			}
	    });
	}
}

function fn_reload(){
	console.log('test');
	$('#form-view').submit();
}

function fn_orderUp(idx, depth){
	if(idx != 1){
		var selectIdx = Number(idx);
		var targetIdx = Number(idx) - 1;
		var selectSeq;
		var targetSeq;
		
		if(depth == "1"){
			selectSeq = $("#idx1" + selectIdx).val();
			targetSeq = $("#idx1" + targetIdx).val();	
		}else if(depth == 2){
			selectSeq = $("#idx2" + selectIdx).val();
			targetSeq = $("#idx2" + targetIdx).val();	
		}else{
			selectSeq = $("#idx3" + selectIdx).val();
			targetSeq = $("#idx3" + targetIdx).val();	
		}
		
		$.ajax({
	        url: "${utcp.ctxPath}/admin/edu/ctgryOrder.json",
	        type: "POST",
	        data: {
	        	"selectSeq" : selectSeq,
	        	"targetSeq" : targetSeq
	        },
	        cache: false,
			async: true,
	        success: function(r) {
				if(r.isAdmin){
					fn_reload();
				}else{
					location.href = "#authMessage";
				}
			}
	    });
	}
}

function fn_orderDown(idx, depth){
	
	var chk = false;
	
	if(depth == "1"){
		if(idx != '${fn:length(eduList)}'){
			chk = true;
		}
	}else{
		if(idx != '${fn:length(detailList)}'){
			chk = true;
		}
	}
	
	if(chk){
		var selectIdx = Number(idx);
		var targetIdx = Number(idx) + 1;
		var selectSeq;
		var targetSeq;
		
		if(depth == "1"){
			selectSeq = $("#idx1" + selectIdx).val();
			targetSeq = $("#idx1" + targetIdx).val();	
		}else if(depth == 2){
			selectSeq = $("#idx2" + selectIdx).val();
			targetSeq = $("#idx2" + targetIdx).val();	
		}else{
			selectSeq = $("#idx3" + selectIdx).val();
			targetSeq = $("#idx3" + targetIdx).val();	
		}
		
		$.ajax({
	        url: "${utcp.ctxPath}/admin/edu/ctgryOrder.json",
	        type: "POST",
	        data: {
	        	"selectSeq" : selectSeq,
	        	"targetSeq" : targetSeq
	        },
	        cache: false,
			async: true,
	        success: function(r) {
				if(r.isAdmin){
					fn_reload();
				}else{
					location.href = "#authMessage";
				}
			}
	    });	
	}
}
</script>
<form id="form-view" action="ctgryList.do">
<input type="hidden" name="parentSeq1" value="${parentSeq1 }"/>
<input type="hidden" name="parentSeq2" value="${parentSeq2 }"/>
</form>
<section class="pd025 pd200 po_re">
	<div class="po_re br5 bs_box cf testCtgryWrap">
		<!--// 교육과정관리 //-->
		<div class="ctgryWrap">
			<h3 class="font_22 fw_500 pdb10">
				<i class="fas fa-chevron-circle-right font_22 pdr5"></i> 1차과정
			</h3>
			<div class="ctgryTb_line line1">&nbsp;</div>
			<div class="ctgryTb_line line2">&nbsp;</div>
			<div class="ctgryTb_line line3">&nbsp;</div>

			<div class="ctgryTb">
				<div class="ctgryTit cf">
					<ul>
						<li>
							<input type="checkbox" id="checkAll01">
						</li>
						<li>명칭</li>
						<li>사용</li>
						<li>정렬순서</li>
					</ul>
				</div>
				<div class="ctgryCon cf">
					<ul>
						<c:if test='${cateList1 != null && fn:length(cateList1) > 0}'>
							<c:set var="idx1" value="0" />
							<c:forEach var="data" items="${cateList1}" varStatus="stat">
								<c:set var="idx1" value="${idx1 + 1}" />
								<input type="hidden" id="idx1${idx1}" value="${data.ctgrySeq}" />
								<li>
									<span><input type="checkbox" name="ctgrySeqs1" value="${data.ctgrySeq}"></span> 
									<span class="tl el " style="${data.ctgrySeq eq parentSeq1?'background-color:lightskyblue':''}"> 
									<div class="truncate" style="float:left;" ><a href="javascript:;" title="${data.ctgryNm}" onclick="fn_upd('${data.ctgrySeq}','1'); return false;">${data.ctgryNm}</a></div>
									<a href="ctgryList.do?parentSeq1=${data.ctgrySeq}">[하위보기]</a>
									</span> <span> <c:choose>
											<c:when test='${data.useYn == "Y"}'>
												Yes
											</c:when>
											<c:otherwise>
												No
											</c:otherwise>
										</c:choose>
									</span> <span>
										<button type="button" class="btn04" onclick="fn_orderUp('${idx1}', '1'); return false;">
											<i class="fas fa-chevron-circle-up"></i>
										</button>
										<button type="button" class="btn04" onclick="fn_orderDown('${idx1}', '1'); return false;">
											<i class="fas fa-chevron-circle-down"></i>
										</button>
									</span>
								</li>
							</c:forEach>
						</c:if>
					</ul>
				</div>

			</div>
			<!--// ctgryTb //-->
			<div class="ctgrytbBth cf">
				<div class="fr tc">
					<button type="button" class="btn04 btn_green" onclick="fn_add('1'); return false;">추가</button>
					<button type="button" class="btn04 btn_gray" onclick="fn_del('1');">숨김</button>
				</div>
			</div>
		</div>
		<!--// 교육과정관리 //-->

			<!--// 상세과정관리 //-->
			<div class="ctgryWrap">
				<h3 class="font_22 fw_500 pdb10">
					<i class="fas fa-chevron-circle-right font_22 pdr5"></i> 2차과정
				</h3>
				<div class="ctgryTb_line line1">&nbsp;</div>
				<div class="ctgryTb_line line2">&nbsp;</div>
				<div class="ctgryTb_line line3">&nbsp;</div>
				<div class="ctgryTb">
					<div class="ctgryTit cf">
						<ul>
							<li>
								<input type="checkbox" id="checkAll02" />
							</li>
							<li>명칭</li>
							<li>사용</li>
							<li>정렬순서</li>
						</ul>
					</div>
					<div class="ctgryCon cf">
						<ul>
							<c:if test='${cateList2 != null && fn:length(cateList2) > 0}'>
								<c:set var="idx2" value="0" />
								<c:forEach var="data" items="${cateList2}" varStatus="stat">
									<c:set var="idx2" value="${idx2 + 1}" />
									<input type="hidden" id="idx2${idx2}" value="${data.ctgrySeq}" />
									<li>
										<span><input type="checkbox" name="ctgrySeqs2" value="${data.ctgrySeq}"></span> 
										<span class="tl el" style="${data.ctgrySeq eq parentSeq2?'background-color:lightskyblue':''}"> 
										<div class="truncate" style="float:left;" ><a href="javascript:;" title="${data.ctgryNm}" onclick="fn_upd('${data.ctgrySeq}','2'); return false;">${data.ctgryNm}</a></div>
										<a href="ctgryList.do?parentSeq2=${data.ctgrySeq}">[하위보기]</a>
										</span> <span> <c:choose>
												<c:when test='${data.useYn == "Y"}'>
												Yes
											</c:when>
												<c:otherwise>
												No
											</c:otherwise>
											</c:choose>
										</span> <span>
											<button type="button" class="btn04" onclick="fn_orderUp('${idx2}', '2'); return false;">
												<i class="fas fa-chevron-circle-up"></i>
											</button>
											<button type="button" class="btn04" onclick="fn_orderDown('${idx2}', '2'); return false;">
												<i class="fas fa-chevron-circle-down"></i>
											</button>
										</span>
									</li>
								</c:forEach>
							</c:if>
						</ul>
					</div>
				</div>
				<!--// ctgryTb //-->

				<div class="ctgrytbBth cf">
					<div class="fr tc">
						<c:if test="${parentSeq1 > 0}">
						<button type="button" class="btn04 btn_green" onclick="fn_add('2'); return false;">추가</button>
						<button type="button" class="btn04 btn_gray" onclick="fn_del('2');">숨김</button>
						</c:if>
					</div>
				</div>
			</div>
			<!--// 상세과정관리 //-->
			<!--// 상세과정관리 //-->
			<div class="ctgryWrap">
				<h3 class="font_22 fw_500 pdb10">
					<i class="fas fa-chevron-circle-right font_22 pdr5"></i> 3차과정
				</h3>
				<div class="ctgryTb_line line1">&nbsp;</div>
				<div class="ctgryTb_line line2">&nbsp;</div>
				<div class="ctgryTb_line line3">&nbsp;</div>
				<div class="ctgryTb">
					<div class="ctgryTit cf">
						<ul>
							<li>
								<input type="checkbox" id="checkAll02" />
							</li>
							<li>명칭</li>
							<li>사용</li>
							<li>정렬순서</li>
						</ul>
					</div>
					<div class="ctgryCon cf">
						<ul>
							<c:if test='${cateList3 != null && fn:length(cateList3) > 0}'>
								<c:set var="idx3" value="0" />
								<c:forEach var="data" items="${cateList3}" varStatus="stat">
									<c:set var="idx3" value="${idx3 + 1}" />
									<input type="hidden" id="idx3${idx3}" value="${data.ctgrySeq}" />
									<li>
										<span><input type="checkbox" name="ctgrySeqs3" value="${data.ctgrySeq}"></span> 
										<span class="tl el"> 
											<a href="javascript:;" title="${data.ctgryNm}" onclick="fn_upd('${data.ctgrySeq}','3'); return false;">${data.ctgryNm}</a>
										</span> 
										<span> 
											<c:choose>
												<c:when test='${data.useYn == "Y"}'>
												Yes
											</c:when>
												<c:otherwise>
												No
											</c:otherwise>
											</c:choose>
										</span> <span>
											<button type="button" class="btn04" onclick="fn_orderUp('${idx3}', '3'); return false;">
												<i class="fas fa-chevron-circle-up"></i>
											</button>
											<button type="button" class="btn04" onclick="fn_orderDown('${idx3}', '3'); return false;">
												<i class="fas fa-chevron-circle-down"></i>
											</button>
										</span>
									</li>
								</c:forEach>
							</c:if>
						</ul>
					</div>
				</div>
				<!--// ctgryTb //-->
				
				<div class="ctgrytbBth cf">
					<div class="fr tc">
						<c:if test="${parentSeq2 > 0}">
						<button type="button" class="btn04 btn_green" onclick="fn_add('3'); return false;">추가</button>
						<button type="button" class="btn04 btn_gray" onclick="fn_del('3');">숨김</button>
						</c:if>
					</div>
				</div>
			</div>
			<!--// 상세과정관리 //-->
	</div>
</section>

<div class="remodal" data-remodal-id="ctgryAdd" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc" data-remodal-options="closeOnOutsideClick: false">
	<button data-remodal-action="close" class="remodal-close" aria-label="Close"></button>
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC"><i class="fas fa-chevron-circle-right font_22 pdr5"></i> <span id="addFrmTitle">카테고리 추가 및 수정</span></p>
		</div>
		<div class="modal-body">
			<div class="ctgryBox">
				<form id="addFrm" method="post">
					<input type="hidden" id="ctgrySeq" />
					<input type="hidden" id="ctgryDepth" />
					<input type="hidden" id="command" />
					<input type="hidden" id="parentSeq" value="${parentSeq }" />
					<input type="hidden" id="parentSeq1" value="${parentSeq1 }" />
					<input type="hidden" id="parentSeq2" value="${parentSeq2 }" />
					<table class="tc tb">
						<colgroup>
							<col width="20%" />
							<col width="*" />
						</colgroup>

						<tbody>
							<tr>
								<th>명칭</th>
								<td class="tl"><input type="text" name="ctgryNm" id="ctgryNm" value="" class="ip4"></td>
							</tr>
							<tr>
								<th>사용</th>
								<td class="tl"><input type="radio" name="useYn" value="Y" id="useY" /> <label for="useY" class="mgr10">Yes</label> <input type="radio" name="useYn" value="N" id="useN" /> <label for="useN">No</label></td>
							</tr>
							<tr id="area-tr-passCd">
								<th>코드</th>
								<td class="tl"><input type="text" name="passCd" id="passCd" value="" class="ip5" placeholder="예)EDU">
								※수료증에 출력될 코드명입니다. 예)2024-EDU-00012</td>
							</tr>
							<tr id="area-tr-openClass">
								<th><label for="eduOpenYes">기관 교육개설권한</label>
								</td>
								<td class="tl">
								<input type="radio" name="openClass" value="1" id="eduOpenYes" class="" > 
								<label for="eduOpenYes" class="mgr10">Yes</label> 
								<input type="radio" name="openClass" value="0" id="eduOpenNo" class=""> 
								<label for="eduOpenNo">No</label></td>
							</tr>
							<tr id="area-tr-passIdx"> 
								<th><label for="ctForm1">수료증 선택</label></th>
								<td class="tl">
									<input type="radio" name="passIdx" id="ctForm1" class="mgr5" value="1" /><label for="ctForm1" class="mgr10">기본</label> 
									<input type="radio" name="passIdx" id="ctForm2" class="mgr5" value="2"/><label for="ctForm2" class="mgr10">심화</label> 
									<input type="radio" name="passIdx" id="ctForm3" class="mgr5" value="3"/><label for="ctForm3" class="mgr10">응급</label>
									<input type="radio" name="passIdx" id="ctForm4" class="mgr5" value="4"/><label for="ctForm4" class="mgr10">필수</label>
									<input type="radio" name="passIdx" id="ctForm5" class="mgr5" value="5"/><label for="ctForm5" class="mgr10">선택</label>
									</td>
							</tr>
							<tr id="area-tr-fbIdx">
								<th><label for="ctgryFb">만족도조사 선택</label></th>
								<td class="tl">
								<input type="hidden" name="fbIdx" id="fbIdx" value="0"/>
								<input type="text" name="fbTitle" id="fbTitle" readonly class="ip1" />
									<button type="button" class="btn04 btn_black" id="ctgryFb" onclick="fn_openFeedbackList()";>찾기</button></td>
							</tr>
						</tbody>
					</table>
				</form>
			</div>
		</div>
		<div class="modal-footer">
			<div class="tc">
				<button onclick="fn_excute(); return false;" class="remodal-confirm btn01 btn_green">저장하기</button>
			</div>
		</div>
	</div>
</div>
<!--// ctgryAdd //-->

<!--// popup_message //-->
<div class="remodal messagePop1" data-remodal-id="messagePop" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc">
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC">알림</p>
		</div>
		<div class="modal-body">
			<p class="messageTxt">카테고리가 숨김 되었습니다</p>
		</div>
		<div class="modal-footer">
			<div class="tc">
				<button onclick="fn_reload(); return false;" class="remodal-confirm btn02 btn_green">확인</button>
			</div>
		</div>
	</div>
</div>
<!--// popup_message //-->

<div class="remodal messagePop messagePop2" data-remodal-id="message" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc">
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC">알림</p>
		</div>
		<div class="modal-body">
			<p class="messageTxt" id="messageStr"></p>
		</div>
		<div class="modal-footer">
			<div class="tc">
				<button data-remodal-action="cancel" class="remodal-confirm btn02 btn_orange">확인</button>
			</div>
		</div>
	</div>
</div>

<!--// Feedback List //-->
<div class="remodal" data-remodal-id="md-feedbackList" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc" id="vm-feedbackList">
	<button data-remodal-action="close" class="remodal-close" aria-label="Close"></button>
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC">
				<i class="fas fa-chevron-circle-right fs_22 pdr5"></i>피드백 리스트
			</p>
			<p class="fs_14 pd5">적용할 피드백을 선택하세요.</p>
		</div>
		<div class="modal-body">
			<div class="tbBox fbCon">
				<table width="100%" class="tb03 tc">
					<tr v-for="(o,i) in feedbackList">
						<td class="tl"><a href="#none"><span class="dp_ib fl font_22 fw_500 pdl15 pdr15">{{o.title}}</span></a> <span class="fl dp_ib font_20">(객관식 <strong class="fw_400">{{o.qtType1Cnt}}</strong>개 / 주관식 <strong class="fw_400">{{o.qtType0Cnt}}</strong>개)
						</span> <span class="fl cb dp_ib font_14 pdl15 mgr20">등록자 : {{o.regNm}}</span> <span class="fl dp_ib font_14">등록일시 : {{o.regTime|fltDate2Str('YYYY.MM.DD')}}</span></td>
						<td class="tr">
							<button class="fr btn02 btn_orange mgr15" :onclick="'fn_loadFeedback('+o.idx+');'">등록하기</button>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
</div>
<script>
	var vm_feedbackList = new Vue({
		el : '#vm-feedbackList',
		data : {
			feedbackList : []
		},
	});
	function fn_openFeedbackList() {
		$.ajax({
			url : '${utcp.ctxPath}/admin/feedback/feedbackList.json',
			success : function(r) {
				console.log(r);
				vm_feedbackList.feedbackList = r.feedbackList;
				location.href = '#md-feedbackList';
			}
		});
	}
	function fn_loadFeedback(fbIdx) {
		var list = vm_feedbackList.feedbackList;
		for(var i in list){
			if(list[i].idx==fbIdx){
				$('#fbIdx').val(fbIdx);
				$('#fbTitle').val(list[i].title);
				console.log(1,$('#fbTitle').val());
				$('[data-remodal-id=md-feedbackList]').remodal().close();
				$('[data-remodal-id=ctgryAdd]').remodal().open();
				return false;
			}
		}
				console.log(2,$('#fbTitle').val());
	}
</script>
<!--// Feedback List //-->