<%@page import="com.educare.edu.quizBank.web.QuizBankComponent"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<section class="pd025 pd200 po_re">
	<div class="po_re br5 bs_box">
		<form id="form-srch">
			<input type="hidden" name="pageNo" />
			<div class="fl tr mgb15">
				<select name="srchCtg1Seq" class="">
					<option value="0">카테고리</option>
					<c:forEach items="${cateList }" var="o">
						<option value="${o.qstnCtgSeq }" ${param.srchCtg1Seq == o.qstnCtgSeq?'selected':'' }>${o.ctgNm }</option>
					</c:forEach>
				</select>
				<select name="srchQstnTp" class="">
					<c:forEach items="${qbcp.getQstnTpArr() }" var="o" varStatus="s">
						<option value="${s.index}" ${data.qstn.qstnTp == s.index?'selected':'' }>${s.index==0?'문제유형':o }</option>
					</c:forEach>
				</select>
				<label for="srchWrd" class="sound_only">검색어입력</label>
				<input type="text" id="srchWrd" name="srchWrd" value="${param.srchWrd }" placeholder="검색" class="btn04  tl mgr5">
				<button type="button" onclick="fn_srch();" class="btn04 btn_black fr">검색</button>
			</div>
			<div class="fr tc mgb15">
				<a href="javascript:vm_qb_eu.uploadForm();" class="btn01 btn_green">엑셀등록</a>
				<!-- 
                        <a href="quizBankReg.do" class="btn01 btn_blue" >문제등록</a>
                        <a href="#none" class="btn01 btn_greenl"><img src="${utcp.ctxPath }/resources/admin/images/excel.png" alt="excel" style="width: 15px; vertical-align: middle;"> 다운로드</a>
                         -->
			</div>
		</form>
		<table class="w100 tb01 tc">
			<thead>
				<tr>
					<th>번호</th>
					<th>문제명</th>
					<th>카테고리</th>
					<th>작성자</th>
					<th>등록일</th>
					<th>난이도</th>
					<th>유형</th>
					<th>상태</th>
					<th></th>
				</tr>
			</thead>
			<c:forEach items="${data.list}" var="o" varStatus="s">
				<tr>
					<td>${data.pageNavi.totalRecordCount-(data.pageNavi.firstRecordIndex+s.index) }</td>
					<td class="tl"><a href="quizBankReg.do?qstnSeq=${o.qstnSeq }"> <span class="dp_ib fl font_22 fw_500 pdl15 pdr15">${o.qstnNm }</span></a></td>
					<td>${o.ctg1Nm }</td>
					<td>${o.regNm }</td>
					<td>${utcp.convDateToStr(o.regDe,'yyyy-MM-dd HH:mm') }</td>
					<td>${qbcp.getDiffTypeNm(o.diffType) }</td>
					<td>${qbcp.getQstnTpNm(o.qstnTp) }</td>
					<td>${o.useYn eq 'Y'?'사용':'<span class="fc_red">중지</span>' }</td>
					<td>
					<button type="button" onclick="vm_quizsample.callQuiz(${o.qstnSeq});" class="btn04 btn_blue">미리보기</button>
					<button type="button" onclick="fn_delQuizBank(${o.qstnSeq })" class="btn04 btn_orange">삭제</button>
					</td>
				</tr>
			</c:forEach>
			<c:if test="${empty data.list }">
				<tr>
					<td colspan="8">데이터가 없습니다.</td>
				</tr>
			</c:if>
		</table>

		<c:set var="pageNavi" value="${data.pageNavi }" />
		<%
			request.setAttribute("pageNavi",
					pageContext.getAttribute("pageNavi"));
		%>
		<jsp:include page="/WEB-INF/jsp/admin/bbs/pageNavi.jsp" />
		<div class="tr fbTxt">
			<button class="btn01 btn_green fbBtn" onclick="location.href='quizBankReg.do';">
				<i class="fas fa-file-signature"></i> 문제 만들기
			</button>
		</div>
	</div>
</section>

<script>
	function fnc_paging(pageNo) {
		$('input[name=pageNo]').val(pageNo);
		$('#form-srch').submit();
	}
	function fn_srch() {
		fnc_paging(1);
	}
</script>


<!-- 문제은행 엑셀업로드 폼 -->
<div class="remodal" data-remodal-id="md-quizBank-uploadExcel" id="vm-quizBank-uploadExcel" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc" data-remodal-options="closeOnOutsideClick: false">
	<button data-remodal-action="close" class="remodal-close" aria-label="Close"></button>
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC">문제은행 엑셀 업로드</p>
		</div>
		<div class="modal-body pdl15 pdr15">
			<!-- <p>(테스트_실제 등록되지 않음)</p> -->
			<div class="dp_ib tc pd15">

				<span> 
				<select id="srchCtgry" class="srchCtgry">
						<option value="-1">1차카테고리</option>
						<c:forEach var="data" items="${cateList}" varStatus="stat">
							<option value="${data.qstnCtgSeq}" >${data.ctgNm}</option>
						</c:forEach>
				</select>
				</span>
				<span id="vm-detailCtgry"> 
				<select id="srchCtgry2" class="srchCtgry">
						<option value="-1">2차 카테고리</option>
				</select>
				</span> <span id="vm-detailCtgry2" style=""> 
				<select id="srchCtgry3" class="srchCtgry">
						<option value="-1">3차 카테고리</option>
				</select>
				</span>
				<script>
					$(function() {
						var select1 = -1;
						var selectList1 = [];
						$('#srchCtgry').change(function() {
							fn_callCategory(this.value);
						});
						function fn_callCategory(ctgrySeq) {
							if (ctgrySeq == 0)
								return;
							select1 = -1;
							$
									.ajax({
										data : {
											ctgrySeq : ctgrySeq
										},
										url : '${utcp.ctxPath}/comm/api/callQuizCategory.json',
										async : false,
										success : function(r) {
											selectList1 = r.ctgryList;
											select1 = -1;
										}
									});
							$('#srchCtgry2').empty();
							$('#srchCtgry2').append($('<option>', {
								value : -1,
								text : '2차 카테고리'
							}));

							$.each(selectList1, function(i, o) {
								$('#srchCtgry2').append($('<option>', {
									value : o.qstnCtgSeq,
									text : o.ctgNm
								}));
							});

						}

						fn_callCategory('${vo.srchCtgry}');
						select1 = '${vo.srchCtgry2 > 0?vo.srchCtgry2:-1}';
						console.log(select1);
						$('#srchCtgry2').val(select1);
					});

					$(function() {
						var select2 = -1;
						var selectList2 = [];
						$('#srchCtgry2').change(function() {
							fn_callCategory2(this.value);
						});
						function fn_callCategory2(ctgrySeq) {
							if (ctgrySeq == 0)
								return;
							select2 = -1;
							$
									.ajax({
										data : {
											ctgrySeq : ctgrySeq
										},
										url : '${utcp.ctxPath}/comm/api/callQuizCategory.json',
										async : false,
										success : function(r) {
											selectList2 = r.ctgryList;
											select2 = -1;

											$('#vm-detailCtgry2').hide();
											if (ctgrySeq > 0) {
												$('#vm-detailCtgry2').show();
											}
										}
									});
							$('#srchCtgry3').empty();
							$('#srchCtgry3').append($('<option>', {
								value : -1,
								text : '3차 카테고리'
							}));

							$.each(selectList2, function(i, o) {
								$('#srchCtgry3').append($('<option>', {
									value : o.qstnCtgSeq,
									text : o.ctgNm
								}));
							});

						}

					});
				</script>
				<input type="file" @change="handleFileUpload" id/>
			</div>
		</div>
		<div class="modal-footer">

			<button onclick="location.href='${utcp.ctxPath }/resources/files/quiz_bulk.xlsx'" style="width:140px !important;" class="remodal-confirm btn02 btn_blue">엑셀 샘플 다운로드</button>
			<button @click="uploadProc" class="remodal-confirm btn02 btn_green">업로드</button>
		</div>
	</div>
</div>
<script>
	var vm_qb_eu = new Vue(
			{
				el : '#vm-quizBank-uploadExcel',
				data : {
					selectedFile: null,
				},
				methods : {
					uploadForm : function() {
						$('[data-remodal-id=md-quizBank-uploadExcel]')
								.remodal().open();
					},
					handleFileUpload : function(event) {
						var file = event.target.files[0];
						if (file) {
							this.selectedFile = file;
							
							 // input의 값을 초기화
					        //event.target.value = '';
						}
					},
					uploadProc : function() {
						console.log('click');
						if (!this.selectedFile) {
							alert("파일을 선택해주세요!");
							return;
						}

						// FormData를 사용하여 파일을 서버로 업로드
						var formData = new FormData();
						formData.append("uploadExcel", this.selectedFile);
						
						//카테고리저장
						var ctg1Seq = $('#srchCtgry').val();
						var ctg2Seq = $('#srchCtgry2').val();
						var ctg3Seq = $('#srchCtgry3').val();
						formData.append("ctg1Seq", ctg1Seq);
						formData.append("ctg2Seq", ctg2Seq);
						formData.append("ctg3Seq", ctg3Seq);
						
						$.ajax({
							type: 'post',
							contentType: false,
				            processData: false,
				            data: formData,
				            url: 'uploadQuizBankExcel.ajax',
				            success: function(r){
				            	if(r.result != 1){
				            		alert(r.msg);
				            		return;
				            	}
				            	if(r.result == 1){
				            		//if(!confirm(r.msg+'(테스트중 실제 등록되지 않음)')){
				            		if(!confirm(r.msg+'')){
				            			return;
				            		}
									formData.append("forceReg", 1);
									$.ajax({
										type: 'post',
										contentType: false,
							            processData: false,
							            data: formData,
							            url: 'uploadQuizBankExcel.ajax',
							            success: function(r){
							            	
							            	if(r.result == 1){
							            		alert('일괄등록 성공');
							            		return;
							            	}
							            	alert(r.msg);
						            		return;
							            	
							            },
									});
				            	}
				            	
				            },
						});
					},
				},
			});
</script>


<div class="remodal testView" data-remodal-id="md-quizsample" id="vm-quizsample" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc" data-remodal-options="closeOnOutsideClick: false">
	<button data-remodal-action="close" class="remodal-close" aria-label="Close">나가기</button>
	<div class="modal-content">
		<div class="modal-body">
			<!--// 문제영역 //-->
			<div class="testCont cf">
				<div class="testQ tl cf">
					<p>시험지 영역</p>
					<div class="testQlist">
						<dl>
							<dt>
								1. <span v-html="text2Html(qt.qstnStr)"></span> <span>(배점 0점)</span>
								<div>
								<img v-if="qt.qstnFnRnm" width="300px" :src="'${utcp.ctxPath }/DATA/<%=QuizBankComponent.UPLOAD_PATH%>'+qt.qstnSeq+'/'+qt.qstnFnRnm"/>
								</div>
							</dt>
							<template v-if="qt.qstnTp == 1">
							<dd v-for="(o2,i2) in chList" >
								<input type="radio" name="md_mt7t_ch" :id="'md_mt7t_ch_'+i2" v-model="qt.optnStr" :value="o2.chSeq" />
								<label :for="'md_mt7t_ch_'+i2"> {{noArr[i2]}} 
								{{o2.chStr}} 
								</label>
								<br/><img v-if="o2.chFnRnm" height="200px" :src="'${utcp.ctxPath }/DATA/<%=QuizBankComponent.UPLOAD_PATH%>'+o2.qstnSeq+'/'+o2.chFnRnm"/>
							</dd>
							</template>
							<template v-else-if="qt.qstnTp == 2">
							<dd v-for="(o2,i2) in chList">
								<input type="checkbox" name="md_mt7t_ch" :id="'md_mt7t_ch_'+i2" v-model="qt.optnArr" :value="o2.chSeq" />
								<label :for="'md_mt7t_ch_'+i2"> {{noArr[i2]}} {{o2.chStr}} </label>
							</dd>
							</template>
							<template v-else-if="qt.qstnTp == 3"> 
							<dd>
							<input v-model="qt.fillBlank" class="w100" type="text" /></dd> </template>
							<template v-else-if="qt.qstnTp == 4"> <dd>
							<textarea v-model="qt.ansDesc" class="ta_size" ></textarea> </dd></template>
						</dl>
					</div>
					<div class="tc">
						
					</div>
				</div>
				
			</div>
			<!--// 문제영역 //-->
		</div>
		<div class="modal-footer"></div>
	</div>
</div>
<script>
var vm_quizsample = new Vue({
	el: '#vm-quizsample',
	data: {
		qt: {},
		chList: [],
		noArr: ['①','②','③','④','⑤','⑥'],
	},
	methods: {
		callQuiz: function(qstnSeq){
			var _this = this;
			$.ajax({
				data: {qstnSeq: qstnSeq},				
				url: 'quizBankInfo.ajax',	
				success: function(r){
					if(r.result == 1){
						_this.qt = r.data.qstn;	
						_this.chList = r.data.chList;	
						$('[data-remodal-id=md-quizsample]').remodal().open();
						location.href='#none';
					}
				}
			});			
		},
		text2Html : function(v){
			return fltCharToHtml(v);
		},
	},
});

function fn_delQuizBank(qstnSeq){
	if(!confirm('문제를 삭제하시겠습니까?')){
		return;
	}
	$.ajax({
		type: 'post',
		data: {qstnSeq: qstnSeq},
		url: '${utcp.ctxPath}/admin/quizBank/delQuizBank.ajax',
		success: function(r){
			if(r.result == 1){
				location.reload();
			}else{
				alert(r.msg);
			}
			
		}
	});
}
</script>