<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>

<script src="${utcp.ctxPath}/resources/plugins/node_modules/ckeditor4/ckeditor.js"></script>
<script src="${utcp.ctxPath}/resources/plugins/node_modules/ckeditor4/plugins/colorbutton/plugin.js"></script>
<script src="${utcp.ctxPath}/resources/plugins/node_modules/ckeditor4/plugins/font/plugin.js"></script>
<script src="${utcp.ctxPath}/resources/plugins/node_modules/ckeditor4-vue/dist/ckeditor.js"></script>

<input type="hidden" id="idx" value="${param.idx }" />
<input type="hidden" id="copyIdx" value="${param.copyIdx }" />
<section class="pd025 pd200 po_re" id="vm-feedback">
	<div class="po_re br5 bs_box cf">
		<div class="fbWrap">
			<comp-table2 :fb="fb" :list="qtList" :ckcfg="ckcfg"></comp-table2>
			<div class="fl tc">
				<button class="btn02 btn_grayl mgb20" onclick="location.href='feedbackList.do';">목록</button>
			</div>
			<div class="tc">
				<button type="button" onclick="fn_save()" class="btn01 btn_greenl">등록완료</button>
			</div>
		</div>
	</div>
</section>



<script type="text/x-template" id="tpl-table2">
			<table width="100%" class="tb05">
				<thead>
					<tr>
						<td><input v-model="fb.title" type="text" class="ip2 mgl10" placeholder="제목을 입력하세요." /></td>
					</tr>
					<tr>
						<td>
							<ckeditor  v-model="fb.content" :config="ckcfg" ></ckeditor>
						</td>
					</tr>
				</thead>
				<tbody>
					<template v-for="(o,i) in list"> <template v-if="o.qt.qtType==1 || o.qt.qtType==2  || o.qt.qtType==3">
					<tr>
						<td class="mcqtd">
							<div class="mcqWrap">
								<div class="mgb5">
									<span class="dp_ib pdr5">{{i+1}}.</span>
									<select name="survey_cate" id="survey_cate" v-model="o.qt.qtDiv">
										<option value="">구분명</option>
										<option value="전반적 만족도">전반적 만족도</option>
										<option value="교육내용">교육내용</option>
										<option value="강사">강사</option>
										<option value="교육장">교육장</option>
										<option value="식사">식사</option>
										<option value="숙소">숙소</option>
									</select> 
									<!--<input v-model="o.qt.qtDiv" type="text" class="mgl10 ip6 vm mgb10" placeholder="구분명을 입력하세요"/>-->
									<input v-model="o.qt.question" type="text" name="" class="mgl10 ip7 vm mgb10" placeholder="질문을 입력하세요." /> 
									
									
									<input type="radio" v-model="o.qt.qtType" :id="'qtType-1-'+i" :name="'qtType'+i" value="1"/><label :for="'qtType-1-'+i">일반</label>
									<input type="radio" v-model="o.qt.qtType" :id="'qtType-2-'+i" :name="'qtType'+i" value="2"/><label :for="'qtType-2-'+i">다중</label>
									<input type="radio" v-model="o.qt.qtType" :id="'qtType-3-'+i" :name="'qtType'+i" value="3"/><label :for="'qtType-3-'+i">점수</label>

									<input type="checkbox" v-model="o.qt.essntlYn2" :id="'essntlYn'+i" value="Y"><label :for="'essntlYn'+i">필수</label> 

									<button :onclick="'fn_moveQuestion(1,'+i+')'" class="upBtn btn04 btn_grayl vt">
										<i class="fas fa-arrow-up fc_gray"></i>
									</button>
									<button :onclick="'fn_moveQuestion(0,'+i+')'" class="downBtn btn04 btn_grayl vt">
										<i class="fas fa-arrow-down fc_gray"></i>
									</button>
									<button :onclick="'fn_delQuestion('+i+')'" name="down" class="delBtn btn04 btn_grayl vt">
										<i class="fas fa-trash-alt fc_red"></i>
									</button>
								</div>

								<div class="mgb5" v-for="(o2,i2) in o.chList">
									<span class="pdl30 fs_14">({{i2+1}})</span> <input v-model="o2.choice" type="text" class="ip7 vt mgb10 mgl5" placeholder="보기를 입력하세요." />
									<input v-if="o.qt.qtType==3" v-model="o2.point" type="number" class="ip9 vt mgb10 mgl5" min="0" max="5" style="width:50px;"/>
									<button :onclick="'fn_moveChoice(1,'+i+','+i2+')'" class="upBtn btn04 btn_grayl vt">
										<i class="fas fa-arrow-up fc_gray"></i>
									</button>
									<button :onclick="'fn_moveChoice(0,'+i+','+i2+')'" class="downBtn btn04 btn_grayl vt">
										<i class="fas fa-arrow-down fc_gray"></i>
									</button>
									<button :onclick="'fn_delChoice('+i+','+i2+')'" class="delBtn btn04 btn_grayl vt">
										<i class="fas fa-trash-alt fc_red"></i>
									</button>
								</div>
							</div>
						</td>
					</tr>
					<tr>
						<td class="">
							<button :onclick="'fn_addChoice('+i+')'" class="btn04 btn_gray w50">
								<i class="fas fa-plus fl vm pdt10"></i> 보기 추가
							</button>
						</td>
					</tr>
					</template> <template v-else>
					<tr>
						<td>
							<div class="saqWrap">
								<div class="mgb5">
									<span class="dp_ib pdr5">{{i+1}}.</span>
									<input v-model="o.qt.qtDiv" type="text" class="mgl10 ip6 vm mgb10" placeholder="구분명을 입력하세요"/>
									<input v-model="o.qt.question" type="text" name="" class="mgl10 ip7 vm mgb10" placeholder="질문을 입력하세요." />
									<input type="checkbox" v-model="o.qt.essntlYn2" :id="'essntlYn'+i" value="1"><label :for="'essntlYn'+i">필수</label> 
									<button :onclick="'fn_moveQuestion(1,'+i+')'" class="upBtn btn04 btn_grayl vt">
										<i class="fas fa-arrow-up fc_gray"></i>
									</button>
									<button :onclick="'fn_moveQuestion(0,'+i+')'" class="downBtn btn04 btn_grayl vt">
										<i class="fas fa-arrow-down fc_gray"></i>
									</button>
									<button :onclick="'fn_delQuestion('+i+')'" class="delBtn btn04 btn_grayl vt">
										<i class="fas fa-trash-alt fc_red"></i>
									</button>
								</div>
							</div>
						</td>
					</tr>
					</template> </template>
				</tbody>
				<tfoot>
					<tr>
						<td class="tc">
							<button onclick="fn_addQuestion(1);" type="button" class="btn01 btn_bluel">
								<i class="fas fa-tasks"></i> 객관식 문항추가
							</button>
							<button onclick="fn_addQuestion(0);" type="button" class="btn01 btn_bluel">
								<i class="far fa-edit"></i> 주관식 문항추가
							</button>
						</td>
					</tr>
				</tfoot>
			</table>
</script>

<script>
	Vue.component('ckeditor', CKEditor.component);
	Vue.component('comp-table2', {
		template : '#tpl-table2',
		props : [ 'fb', 'list','ckcfg'],
	});
	var vm_feedback = new Vue({
		el : '#vm-feedback',
		data : {
			fb : {
				title : '',content:'',
			},
			qtList : [],
			ckcfg: {
				extraPlugins:['colorbutton','font'],
				toolbar:[
			             { name: 'basicstyles', items: [ 'Font','FontSize','Bold','Italic','Underline','Strike','Subscript','Superscript'] },
						{ name: 'colors', items: [ 'TextColor', 'BGColor' ] },
						{ name: 'basicstyles', groups: [ 'basicstyles', 'cleanup' ], items: [ 'Bold', 'Italic', 'Underline', 'Strike', 'Subscript', 'Superscript', '-', 'CopyFormatting', 'RemoveFormat' ] },
						/* '/',
						{ name: 'document', groups: [ 'mode', 'document', 'doctools' ], items: [ 'Source', '-', 'Save', 'NewPage', 'ExportPdf', 'Preview', 'Print', '-', 'Templates' ] },
						{ name: 'clipboard', groups: [ 'clipboard', 'undo' ], items: [ 'Cut', 'Copy', 'Paste', 'PasteText', 'PasteFromWord', '-', 'Undo', 'Redo' ] },
						{ name: 'editing', groups: [ 'find', 'selection', 'spellchecker' ], items: [ 'Find', 'Replace', '-', 'SelectAll', '-', 'Scayt' ] },
						{ name: 'forms', items: [ 'Form', 'Checkbox', 'Radio', 'TextField', 'Textarea', 'Select', 'Button', 'ImageButton', 'HiddenField' ] },
						{ name: 'paragraph', groups: [ 'list', 'indent', 'blocks', 'align', 'bidi' ], items: [ 'NumberedList', 'BulletedList', '-', 'Outdent', 'Indent', '-', 'Blockquote', 'CreateDiv', '-', 'JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock', '-', 'BidiLtr', 'BidiRtl', 'Language' ] },
						{ name: 'links', items: [ 'Link', 'Unlink', 'Anchor' ] },
						{ name: 'insert', items: [ 'Image', 'Table', 'HorizontalRule', 'Smiley', 'SpecialChar', 'PageBreak', 'Iframe' ] },
						'/',
						{ name: 'tools', items: [ 'Maximize', 'ShowBlocks' ] },
						{ name: 'others', items: [ '-' ] },
						{ name: 'about', items: [ 'About' ] } ,
						*/
				]
			}
		},
		updated : function(r) {
		},
	});
	$(document).ready(function() {
		//복사인경우
		if('${param.copyIdx}'!=''){
			$('#idx').val('${param.copyIdx}');
		}
		
		//데이터불러오기
		if ($('#idx').val() != '') {
			$.ajax({
				url : 'feedbackInfo.json',
				data : {
					idx : $('#idx').val()
				},
				success : function(r) {
					vm_feedback.fb = r.feedbackInfo.fb;
					vm_feedback.qtList = r.feedbackInfo.qtList;
					for(i in vm_feedback.qtList){
						if(vm_feedback.qtList[i].qt.essntlYn == 'Y'){
							vm_feedback.qtList[i].qt.essntlYn2 = true;
						}else{
							vm_feedback.qtList[i].qt.essntlYn2 = false;
						}
					}
				},
			})
		}
	});
	
	//질문추가
	function fn_addQuestion(qtType) {
		var qtObj2 = {
			qt : {
				question : '질문',
				qtType : qtType,
			},
			chList : []
		};
		var qtList = vm_feedback.qtList;
		var idx = -1;
		//마지막질문 찾기
		if (qtList.length) {
			for ( var i in qtList) {
				if (qtList[i].qt.qtType == qtType) {
					idx = i;
				}
				if (qtList[i].qt.qtType>0 && qtType==1) {
					idx = i;
				}
			}
			if (idx >= 0) {
				qtObj2 = $.extend(true, {}, qtList[idx]);//true를빼면 얕은복사
			}
		}
		vm_feedback.qtList.push(qtObj2);

	}
	//질문삭제
	function fn_delQuestion(i) {
		vm_feedback.qtList.splice(i, 1);
	}
	//질문이동
	function fn_moveQuestion(m, i) {
		if (m) {
			vm_feedback.qtList = changeArrayOrder(vm_feedback.qtList, i, -1);
		} else {
			vm_feedback.qtList = changeArrayOrder(vm_feedback.qtList, i, 1);
		}
	}
	//보기추가
	function fn_addChoice(i) {
		vm_feedback.qtList[i].chList.push({
			choice : ''
		});
	}
	//보기삭제
	function fn_delChoice(i, i2) {
		vm_feedback.qtList[i].chList.splice(i2, 1);
	}
	//보기이동
	function fn_moveChoice(m, i, i2) {
		if (m) {
			vm_feedback.qtList[i].chList = changeArrayOrder(
					vm_feedback.qtList[i].chList, i2, -1);
		} else {
			vm_feedback.qtList[i].chList = changeArrayOrder(
					vm_feedback.qtList[i].chList, i2, 1);
		}
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
		for (var i in vm_feedback.qtList) {
	        var qt = vm_feedback.qtList[i].qt;

	        if ((qt.qtType == 1 || qt.qtType == 2 || qt.qtType == 3) &&
	            (!qt.qtDiv || qt.qtDiv == "")) {
	            alert((Number(i) + 1) + "번 문항의 구분명을 선택하세요.");
	            return;
	        }
	    }
		
		var formData = {};
		formData.fb = vm_feedback.fb;
		formData.qtList = vm_feedback.qtList;
		for(i in formData.qtList){
			if(formData.qtList[i].qt.essntlYn2){
				formData.qtList[i].qt.essntlYn = 'Y';
			}else{
				formData.qtList[i].qt.essntlYn = 'N';
			}
		}
		
		//복사인경우 fb.idx를 0으로 세팅
		if($('#copyIdx').val()!=''){
			console.log('copy!!');
			formData.fb.idx = 0;
		}
		
		$.ajax({
			type : 'post',
			contentType : 'application/json',
			url : 'feedbackRegProc.json',
			data : JSON.stringify(formData),
			success : function(r) {
				if (r.result == 1) {
					location.href = 'feedbackList.do';
				} else {
					if (r.result == 2) {
						vm_alert.msg = '기관에서 등록한 만족도조사가 아닙니다.';
					} else if (r.result == 3) {
						vm_alert.msg = '이미 사용중인 만족도조사 입니다.';
					} else {
						vm_alert.msg = '에러가 발생 하였습니다. 관리자에게 문의하세요';
					}
					location.href = '#md-alert';
				}
			}
		});
	}
	

</script>
