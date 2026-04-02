<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<span class="tb_text fc_gray">
                            <strong class="fc_black">문제 목록</strong> (※ 총점은 100점이어야 함)
                        </span>
<div class="fr mgb15">
	<c:choose>
	<c:when test="${isUsed }">
	<button type="button" class="btn04 btn_gray" onclick="alert('이미 사용중인 시험지는 문제를 수정하실 수 없습니다.');">문제추가</button>
	</c:when>
	<c:otherwise>
	<button type="button" class="btn04 btn_blue" onclick="fn_openQuizTestAdd(${param.testTmplSeq})">문제추가</button>
	</c:otherwise>
	</c:choose>
</div>
<table class="tb02 tc" style="width: 100%;">
	<thead>
		<tr>
			<th>순번</th>
			<th>문제명</th>
			<th>카테고리</th>
			<th>작성자</th>
			<th>등록일</th>
			<th>난이도</th>
			<th>유형</th>
			<th>점수</th>
			<th>삭제</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${data.tqList }" var="o" varStatus="s">
			<tr>
				<td><input type="hidden" name="tqSeqArr" value="${o.tqSeq }" /> ${s.count}</td>
				<td class="tl">${o.qstnNm }</td>
				<td>${o.ctg1Nm }</td>
				<td>${o.regNm }</td>
				<td>${utcp.convDateToStr(o.regDe,'yyyy.MM.dd') }</td>
				<td>${qbcp.getDiffTypeNm(o.diffType) }</td>
				<td>${qbcp.getQstnTpNm(o.qstnTp) }</td>
				<td><input type="text" class="ip9" maxlength="4" name="marksArr" value="${o.marks }" ${isUsed?'readonly':'' }/></td>
				<td>
					<button class="delBtn btn04 vt" onclick="fn_delQuizTestAdd(${o.tqSeq })">
						<i class="fas fa-trash-alt fc_red"></i>
					</button>
				</td>
			</tr>
		</c:forEach>
		<c:if test="${empty data.tqList }">
			<tr>
				<td colspan="9">문제가 없습니다.</td>
			</tr>
		</c:if>
	</tbody>
	<tfoot>
		<tr>
			<th colspan="7"></th>
			<th><input type="text"  class="ip9" id="sumMarks" readonly></th>
			<th></th>
		</tr>
	</tfoot>
</table>
<script>
$(document).ready(function(){
	fn_setSumMarks();
	$('input[name="marksArr"]').on('input change', function(event) {
		fn_setSumMarks();
   	});
	
});
function fn_setSumMarks(){
	var sumMarks = 0;
	$('input[name=marksArr]').each(function(){
		sumMarks += (this.value*1);
	});
	$('#sumMarks').val(sumMarks);
}
function fn_delQuizTestAdd(tqSeq){
	if(!confirm('문제를 삭제하시겠습니까?')){
		return;
	}
	$.ajax({
		type: 'post',
		data: {tqSeq: tqSeq},
		url: '${utcp.ctxPath}/admin/quizTest/delQuizTestAddQstn.ajax',
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

<!-- 문제선택 -->
<div class="remodal" data-remodal-id="md-quizTestAdd" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc" id="vm-quizTestAdd" data-remodal-options="closeOnOutsideClick: false">
	<button data-remodal-action="close" class="remodal-close" aria-label="Close"></button>
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC"><i class="fas fa-chevron-circle-right fs_22 pdr5"></i>문제선택</p>
		</div>
		<div class="modal-body">
			<div class="dp_ib tc pd15">
				
			    <span>
			        <label for="srchCtgry" class="sound_only">교육카테고리 선택</label>
			        <select id="srchCtgry" class="srchCtgry" @change="srchData">
			            <option value="-1">1차카테고리</option>
			            <c:forEach var="data" items="${cateList}" varStatus="stat">
			                <option value="${data.qstnCtgSeq}" <c:if test='${vo.srchCtgry == data.qstnCtgSeq}'>selected</c:if>>${data.ctgNm}</option>
			            </c:forEach>
			        </select>
			    </span> 
			    <input type="hidden" id="srchCtgry" value="${vo.srchCtgry }"/>
			    <span id="vm-detailCtgry">
			        <label for="srchCtgry2" class="sound_only">2차카테고리</label>
			        <select id="srchCtgry2" class="srchCtgry" @change="srchData">
			            <option value="-1">2차과정 전체</option>
			        </select>
			    </span>
			    <span id="vm-detailCtgry2" style="">
			        <label for="srchCtgry3" class="sound_only">3차카테고리</label>
			        <select id="srchCtgry3" class="srchCtgry" @change="srchData">
			            <option value="-1">3차과정 전체</option>
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
			        if (ctgrySeq == 0) return;
			        select1 = -1;
			        $.ajax({
			            data: {
			                ctgrySeq: ctgrySeq
			            },
			            url: '${utcp.ctxPath}/comm/api/callQuizCategory.json',
			            async: false,
			            success: function(r) {
			                selectList1 = r.ctgryList;
			                select1 = -1;
			            }
			        });
			        $('#srchCtgry2').empty();
			        $('#srchCtgry2').append($('<option>', {
			            value: -1,
			            text: '2차과정 전체'
			        }));
			
			        $.each(selectList1, function(i, o) {
			            $('#srchCtgry2').append($('<option>', {
			                value: o.qstnCtgSeq,
			                text: o.ctgNm
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
			        if (ctgrySeq == 0) return;
			        select2 = -1;
			        $.ajax({
			            data: {
			                ctgrySeq: ctgrySeq
			            },
			            url: '${utcp.ctxPath}/comm/api/callQuizCategory.json',
			            async: false,
			            success: function(r) {
			                selectList2 = r.ctgryList;
			                select2 = -1;
			                
			                $('#vm-detailCtgry2').hide();
			                if(ctgrySeq>0){
			                	$('#vm-detailCtgry2').show();
			                }
			            }
			        });
			        $('#srchCtgry3').empty();
			        $('#srchCtgry3').append($('<option>', {
			            value: -1,
			            text: '3차과정 전체'
			        }));
			
			        $.each(selectList2, function(i, o) {
			            $('#srchCtgry3').append($('<option>', {
			            	 value: o.qstnCtgSeq,
				             text: o.ctgNm
			            }));
			        });
			
			        select2 = '${vo.srchCtgry3 > 0?vo.srchCtgry3:-1}';
			        $('#srchCtgry3').val(select2);
			    }
			
			    fn_callCategory2('${vo.srchCtgry2}');
			});
			</script>
				
				<label for="md-*-srchWrd" class="sound_only">검색어입력</label>
				<input v-model="srchWrd" type="text" placeholder="문제명 검색" class="btn04 btn_blackl tl mgr5" id="md-quizTestAdd-srchWrd" />
				<button type="button" class="btn04 btn_black fr" @click="srchData">검색</button>
			</div>
			<div class="tbBox">
				<table class="tc w100 tb">
					<thead bgcolor="#f7f8fa">
						<tr>
							<th>선택</th>
							<th>순번</th>
							<th>문제명</th>
							<th>카테고리</th>
							<th>작성자</th>
							<th>등록일</th>
							<th>난이도</th>
							<th>유형</th>
						</tr>
					</thead>
					<tbody>
						<tr v-for="(o,i) in chList">
							<td><input type="checkbox" v-model="o.chkQstnSeq" /></td>
							<td>{{chList.length - i}}</td>
							<td>{{o.qstnNm}}</td>
							<td>{{o.ctg1Nm}}</td>
							<td></td>
							<td>{{o.regDe|fltDate2Str('YYYY.MM.DD HH:mm')}}</td>
							<td>{{o.addDiffTypeNm}}</td>
							<td>{{o.addQstnTpNm}}</td>
						</tr>
						<tr v-if="!chList.length">
							<td colspan="7">데이터가 없습니다.</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<div class="modal-footer pdt20">
			<div class="tc">
				<button @click="saveData()" type="button" class="remodal-confirm btn01 btn_green" onclick="">저장</button>
				<button type="button" class="remodal-cancel btn01 btn_gray" data-remodal-action="close">취소</button>
			</div>
		</div>
	</div>
</div>
<script>
var vm_quizTestAdd = new Vue({
	el: '#vm-quizTestAdd',
	data: {
		testTmplSeq: 0,
		srchWrd: '',
		chList: [],
	},
	methods: {
		srchData: function(){
			this.loadData(this.testTmplSeq);
		},
		loadData: function(testTmplSeq){
			var srchCtgry = $('#srchCtgry').val();
			var srchCtgry2 = $('#srchCtgry2').val();
			var srchCtgry3 = $('#srchCtgry3').val();
			$.ajax({
				data: {testTmplSeq: testTmplSeq, srchWrd: this.srchWrd, srchCtgry: srchCtgry, srchCtgry2: srchCtgry2, srchCtgry3: srchCtgry3},
				url: '${utcp.ctxPath}/admin/quizBank/getQuizBankList.ajax',
				success: function(r){
					if(r.result == 1){
						vm_quizTestAdd.testTmplSeq = testTmplSeq;
						vm_quizTestAdd.chList = r.data.list;
					}
				}
			});
		},
		saveData: function(){
			var data = [];
			data.push({'name':'testTmplSeq','value':this.testTmplSeq});
			for(var i in this.chList){
				var o = this.chList[i];
				if(o.chkQstnSeq){
					data.push({'name':'qstnSeqArr','value':o.qstnSeq});
				}
			}
			$.ajax({
				type: 'post',
				data: data,
				url: '${utcp.ctxPath}/admin/quizTest/saveQuizTestAddQstn.ajax',
				success: function(r){
					if(r.result == 1){
						fn_closeQuizTestAdd();
					}else{
						alert(r.msg);
					}
				}
			}); 
		}
	},
});
function fn_openQuizTestAdd(testTmplSeq){
	$('[data-remodal-id=md-quizTestAdd]').remodal().open();
	vm_quizTestAdd.loadData(testTmplSeq);
}
function fn_closeQuizTestAdd(){
	$('[data-remodal-id=md-quizTestAdd]').remodal().close();
	location.reload();
	
}

</script>
