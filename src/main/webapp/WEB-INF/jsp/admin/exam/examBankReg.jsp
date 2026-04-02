<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<section class="pd025 pd200 po_re" id="vm-exam-bank">
	<div class="po_re br5 bs_box cf">
		<div class="fbWrap">
			<comp-table2 :qt="qtMap" :ch="chList"></comp-table2>
			<div class="fl tc">
				<button class="btn02 btn_grayl mgb20" onclick="location.href='examBankList.do';">목록</button>
			</div>
			<div class="tc">
				<button type="button" id="btn-save-bank" onclick="fn_save()" class="btn01 btn_greenl">문제 생성</button>
			</div>
		</div>
	</div>
</section>
<input type="hidden" id="ebq-idx" value="${param.ebqIdx }"/>

<script type="text/x-template" id="tpl-table2">
<table width="100%" class="tb05">
	<thead>
		<tr>
			<td></td>	
		</tr>
	</thead>
	<tbody>
		<tr>
			<td>
			<comp-ckeditor v-model="qt.question"></comp-ckeditor>
			</td>
		</tr>
		<template v-if="qt.questionType==1">
		<tr>	
			<td class="mcqtd">
				<div class="mcqWrap">
					<div class="mgb5" v-for="(o2,i2) in ch" :key="o2">
						<span class="pdl30 fs_14">({{i2+1}})</span>
<%--						
<input v-model="o2.choice" type="text" class="ip7 vt mgb10 mgl5" placeholder="보기를 입력하세요." />
--%>
<comp-ckeditor v-model="o2.choice" :value2="'choice_'+i2"></comp-ckeditor>

<input type="checkbox" v-model="o2.correctType" true-value="1" false-value="0">정답
<select v-model="o2.status">
<option value="1">사용함</option>
<option value="0">사용안함</option>
</select>
						<button :onclick="'fn_moveChoice(1,'+i2+');'" class="upBtn btn04 btn_grayl vt">
							<i class="fas fa-arrow-up fc_gray"></i>
						</button>
						<button :onclick="'fn_moveChoice(0,'+i2+')'" class="downBtn btn04 btn_grayl vt">
							<i class="fas fa-arrow-down fc_gray"></i>
						</button>
						<button :onclick="'fn_delChoice('+i2+')'" class="delBtn btn04 btn_grayl vt">
							<i class="fas fa-trash-alt fc_red"></i>
						</button>
					</div>
				</div>
			</td>
		</tr>
		<tr>
			<td class="">
				<button :onclick="'fn_addChoice()'" class="btn04 btn_gray w50">
					<i class="fas fa-plus fl vm pdt10"></i> 보기 추가
				</button>
			</td>
		</tr>
		</template>
		<template v-else>
		<tr>
		<td>
			<textarea v-model="qt.answerText" placeholder="정답을 작성하세요"></textarea>
		</td>		
	</tr>
		</template>
	</tbody>
	<tfoot>
		<tr>
			<td class="tc">
				<button v-if="qt.questionType==0" onclick="vm_exam_bank.qtMap.questionType=1;fn_addChoice(0);" type="button" class="btn01 btn_bluel">
					<i class="fas fa-tasks"></i> 객관식으로 변경
				</button>
				<button v-if="qt.questionType==1" onclick="vm_exam_bank.qtMap.questionType=0;" type="button" class="btn01 btn_bluel">
					<i class="far fa-edit"></i> 주관식으로 변경
				</button>
			</td>
		</tr>
	</tfoot>
</table>
</script>

<script src="${utcp.ctxPath}/resources/ckeditor4/ckeditor.js"></script>
<script>
	Vue.component('comp-ckeditor', {	
		template: '<textarea v-ckeditor :value="value" :id="value2"></textarea>',
		props:['value','value2'],
		directives: {
	        ckeditor: {
	            inserted:function (el, binding, vNode) {
	            	var vue_bank_ck = CKEDITOR.replace( el ,{
	            		filebrowserUploadUrl: '${utcp.ctxPath}/editot/popupUpload.do',
	            		image_previewText : ' ',
	            	});
	            	vue_bank_ck.on('change', function() { 
	    		   		vNode.context.$emit('input',vue_bank_ck.getData());
	    		  	});
	            },
				update:function(el, binding, vNode){
					//console.log(el.value);
					//console.log(el.id);
					//if(el.id!=''){
					//	CKEDITOR.instances[el.id].setData(el.value);
					//}
				},
	        }
	    },
	});
	
	Vue.component('comp-table2', {
		template : '#tpl-table2',
		props : [ 'qt', 'ch' ],
	});
	var vm_exam_bank = new Vue({
		el : '#vm-exam-bank',
		data : {
			ebqIdx:0,
			qtMap : {
				question : '',
				answerText : '',
				questionType : 0,
			},
			chList : [],
		},
		updated : function(r) {
		},
	});
	$(document).ready(function() {
		//데이터불러오기
		if ($('#ebq-idx').val() != '') {
			$.ajax({
				url : 'examBankInfo.json',
				data : {
					ebqIdx : $('#ebq-idx').val()
				},
				success : function(r) {
					vm_exam_bank.qtMap = r.data.qtMap;
					vm_exam_bank.chList = r.data.chList;
				},
			})
		}
		
	});

	
	//보기추가
	function fn_addChoice(key) {
		console.log(key);
		if( (key==0 && vm_exam_bank.chList.length==0) || key!=0 ){
			vm_exam_bank.chList.push(
					{status:1}
			);
		}
		
	}
	//보기삭제
	function fn_delChoice(i2) {
		vm_exam_bank.chList.splice(i2, 1);
	}
	//보기이동
	function fn_moveChoice(m, i2) {
		if (m) {
			vm_exam_bank.chList = changeArrayOrder(
					vm_exam_bank.chList, i2, -1);
		} else {
			vm_exam_bank.chList = changeArrayOrder(
					vm_exam_bank.chList, i2, 1);
		}
		vm_exam_bank.$forceUpdate();
		//vm_exam_bank.qtMap.questionType=0;
		//vm_exam_bank.qtMap.questionType=1;
		
		//fn_addChoice();
	}

	//배열 순서변경 함수
	function changeArrayOrder(list, targetIdx, moveValue) {
		// 배열값이 없는 경우 나가기
		if (list.length < 0)
			return list;

		// 이동할 index 값을 변수에 선언
		var newPosition = targetIdx + moveValue;

		// 이동할 값이 0보다 작거나 최대값을 벗어나는 경우 종료
		if (newPosition >= list.length || newPosition < 0)
			return list;

		// 임의의 변수를 하나 만들고 배열 값 저장
		var tempList = JSON.parse(JSON.stringify(list));

		// 옮길 대상을 target 변수에 저장하기
		var target = tempList.splice(targetIdx, 1)[0];

		// 새로운 위치에 옮길 대상을 추가하기
		tempList.splice(newPosition, 0, target);
		return tempList;
	};
	//저장
	function fn_save() {
		var formData = {};
		formData.ebqIdx = $('#ebq-idx').val();
		formData.qtMap = vm_exam_bank.qtMap;
		formData.chList = vm_exam_bank.chList;
		//console.log(vm_exam_bank.qtMap.question);return;
		$.ajax({
			type : 'post',
			contentType : 'application/json',
			url : 'examBankRegProc.json',
			data : JSON.stringify(formData),
			success : function(r) {
				if (r.result == 1) {
					location.href = 'examBankList.do';
				} else {
					vm_alert.msg = r.msg;
					location.href = '#md-alert';
				}
			}
		});
	}
</script>
