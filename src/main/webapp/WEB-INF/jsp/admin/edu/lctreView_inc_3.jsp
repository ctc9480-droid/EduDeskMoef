<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<div id="vm-tab3">
	<span class="tb_text"> 수강생 <strong class="fc_red" id="totalTab3">{{totalCnt}}</strong> 명 / 합격생 <strong class="fc_red" id="totalTab3">{{passCnt}}</strong> 명
					</span>
					<input type="hidden" id="tab3Page" value="1" />
					<div class="dp_ib fr tr mgb15">
						<label for="tab3Row" class="sound_only">리스트줄선택</label>
						<select id="tab3Row" class="btn04 btn_blackl">
							<option value="10">10줄</option>
							<option value="50">50줄</option>
							<option value="100">100줄</option>
							<option value="150">150줄</option>
						</select>

						<label for="tab3SrchColumn" class="sound_only">검색대상선택</label>
						<select id="tab3SrchColumn" class="btn04 btn_blackl">
							<option value="userNm">성명</option>
							<option value="belong">소속</option>
						</select>

						<label for="tab3SrchWrd" class="sound_only">검색어입력</label>
						<input type="text" id="tab3SrchWrd" placeholder="검색" class="btn04 btn_blackl tl mgr5" />

						<button class="btn04 btn_black fr" onclick="fn_htmlWrite('3');">검색</button>
					</div>
					<table width="100%" class="tb02 inc-tb02 tc">
						<thead bgcolor="#f7f8fa">
							<tr>
								<!-- <th><input type="checkbox" id="chkStdntAll" /></th> -->
								<th>
									<input type="checkbox" v-model="isAllChecked" @change="handleChangeAllCheck" />
								</th>
								<th>성명</th>
								<th>아이디</th>
								<th>생년월일</th>
								<th>이메일</th>
								<th>휴대폰번호 </th>
								<c:if test="${lctre.lctreType == 3 }">
								<th>진도율</th>
								</c:if>
								<c:if test="${lctre.lctreType != 3 }">
								<th>출석
								<%-- (${lctre.passAttPoint }) --%>
								</th>
								<c:if test="${lctre.passAsgPoint > 0 }">
								</c:if>
								<th>과제
								<%-- (${lctre.passAsgPoint }) --%>
								</th>
								<c:if test="${lctre.passTestPoint > 0 }">
								</c:if>
								<th>시험
								<%-- (${lctre.passTestPoint }) --%>
								</th>
								</c:if>
								<th>설문</th>
								<th>수료여부
								<c:if test="${lctre.lctreType != 3 }">
								<select v-model="btnPassYn" @change="setBtnPassYn" :disabled="closeYn == 'Y'?true:false">
									<option value="Y">O</option>
									<option value="N">X</option>
								</select>
								</c:if>
								</th>
							</tr>
						</thead>
						<tbody id="tab3Data">
							<tr v-for="(o,i) in stdntList">
								<!-- <td><input type="checkbox" name="delUserIds" :value="o.userId" /> <input type="hidden" name="userId" v-model="o.userId" /></td> -->
								<td><input type="checkbox" v-model="o.checked" /></td>
								<td>{{o.userNm }}</td>
								<td>{{o.loginId}}</td>
								<td class="">{{o.birth}}</td>
								<td>{{o.email}}</td>
								<td>{{o.mobile}}</td>
								<c:choose>
								<c:when test="${lctre.lctreType eq 3 }">
								<td>
								{{o.addMovRatio}}
								</td>
								</c:when>
								<c:otherwise>
								<td>
								{{o.attendCnt}} / {{o.qrCnt}}
								<!-- {{o.attendCnt}} / {{lectTimeCnt}} --> <!-- ({{(o.attendCnt/lectTimeCnt*100).toFixed(1)}}%)  -->
								<a :href="'javascript:fn_openCheckList2('+o.eduSeq+',\''+o.userId+'\')'">[보기]</a>
								<%-- 
								<input v-on:input="checkAttScore(i,${lctre.passAttPoint })" numberonly type="text" name="attendScore" class="ip10 tc" maxlength="3" v-model="o.attendScore" :disabled="closeYn == 'Y'?true:false" style="width: 50px;"/>
								 --%>
								</td>
								<c:if test="${lctre.passAsgPoint > 0 }">
								</c:if>
								<td>
								<a v-if="o.taskFileRename" :href="'${utcp.ctxPath}/user/mypage/popup/taskDownload.do?eduSeq=${param.eduSeq }&userId='+o.userId"><img src="${utcp.ctxPath}/resources/user/image/icon/down_icon.gif" alt="파일 아이콘"></a>
								<%-- 
								<input v-on:input="checkAsgScore(i,${lctre.passAsgPoint })" numberonly type="text" name="workshopScore" class="ip10 tc" maxlength="3" v-model="o.workshopScore" :disabled="closeYn == 'Y'?true:false" style="width: 50px;"/>
								 --%>
								</td>
								<c:if test="${lctre.passTestPoint > 0 }">
								</c:if>
								<td>
								<%-- 
								<input v-on:input="checkTestScore(i,${lctre.passTestPoint })" numberonly type="text" name="examScore" class="ip10 tc" maxlength="3" v-model="o.examScore" :disabled="closeYn == 'Y'?true:false" style="width: 50px;"/>
								 --%>
								 본평가:{{o.testPassCnt}}/{{testCnt}} 재평가:{{o.test2PassCnt}}/{{test2Cnt}}
								 
								</td>
								</c:otherwise>
								</c:choose>
								</td>
								<td>{{o.surveyYn}}</td>
								<td><select name="passYn" v-model="o.passYn" :disabled="closeYn == 'Y' || '${lctre.autoPassYn }' == 'Y'?true:false">
										<option value="Y">O</option>
										<option value="N">X</option>
								</select></td>
							</tr>
							<tr v-if="!stdntList.length">
								<td colspan="9" class="h200">수강생이 없습니다.</td>
							</tr>
						</tbody>
					</table>

					<div class="fl tc">
					<!-- 	<button class="btn01 btn_greenl" onclick="fn_cancel(); return false;">선택한 교육생 수강취소</button> -->
					</div>

					<div class="fr tc" id="closeBtn3">
						<c:if test="${lctre.lctreType == 1}">
						<button onclick="fn_openCheckList('${lctre.eduSeq }')" class="btn02 btn_green">출석부</button>
						</c:if>
						<button class="btn02 btn_green" onclick="vm_sms3.callLayer()">안내문구발송</button>
						<button class="btn02 btn_green" onclick="fn_excelStdnt(); return false;">엑셀 다운로드</button>
						<!-- <button class="btn02 btn_green" onclick="fn_excelStdnt2(); return false;">온라인진행현황</button> -->
							<button v-if="closeYn == 'N'" class="btn03 btn_orangel" onclick="fn_saveRecord();">저장</button>
						<c:if test="${lctre.lctreType ne 3}">
							<template v-if="closeYn == 'N'">
							<button class="btn02 btn_orange" onclick="fn_closeConfirm(); return false;">교육 종료</button>
							</template>
							<template v-else>
							<button class="btn02 btn_orangel" onclick="fn_closeCancel(); return false;">종료 취소</button>
							</template>
						</c:if>
					</div>

					<input type="hidden" id="closeYn" value="${lctre.closeYn}">

					<!--// paging //-->
					<div class="page" id="tab3PageDiv">
						<div class="inner cf">
							<template v-if="page.currentPageNo != 1">
							<div class="page_prev0">	
								<a href="javascript:;" :onclick="'fn_pageTab3('+1+');'">&lt; 처음</a>
							</div>
							<div class="page_prev0">
								<a href="javascript:;" :onclick="'fn_pageTab3('+(page.currentPageNo - 1)+');'">&lt; 이전</a>
							</div>
							</template>
							<template v-for="o in (pageList)"> <template v-if="o == page.currentPageNo">
							<div class="page_now">
								<a>{{o}}</a>
							</div>
							</template> <template v-else>
							<div class="page_nomal">
								<a href="javascript:;" :onclick="'fn_pageTab3('+o+');'">{{o}}</a>
							</div>
							</template> </template>
							<template v-if="page.currentPageNo != page.totalPageCount && page.totalPageCount > 0">
							<div class="page_next0">
								<a href="javascript:;" :onclick="'fn_pageTab3('+(page.currentPageNo + 1) + ');'">다음 &gt;</a>
							</div>
							<div class="page_next0">
								<a href="javascript:;" :onclick="'fn_pageTab3('+(page.lastPageNo ) + ');'">끝 &gt;</a>
							</div>
							</template>
						</div>
					</div>
					<!--// paging //-->
</div>

<script>

//시간표  vue
var vm_3;
//시간표영역 준비되면 vue적용
$('#tabCon3').ready(function(){
	vm_3 = new Vue({
		el:'#vm-tab3',
		data:{
			checkYn:'${lctre.checkYn}',
			closeYn:'${lctre.closeYn}',
			lectTimeCnt:0,passCnt:0,totalCnt:0,
			stdntList:[],
			page:{},
			pageList:[],
			btnPassYn:'N',
			isAllChecked: false,
		},
		updated:function(r){
			$('[numberonly]').off('keyup').on('keyup', function() {
				$(this).val($(this).val().replace(/[^0-9]/g, ''));
			});
		},
		methods:{
			handleChangeAllCheck: function(){
				console.log(this.isAllChecked);
				this.stdntList = this.stdntList.map(item => ({
					...item,
					checked: this.isAllChecked
				}));
				
			},
			checkAttScore:function(i,limit){
				if(this.stdntList[i].attendScore>limit){
					this.stdntList[i].attendScore='';
				}
			},
			checkAsgScore:function(i,limit){
				if(this.stdntList[i].workshopScore>limit){
					this.stdntList[i].workshopScore='';
				}
			},
			checkTestScore:function(i,limit){
				if(this.stdntList[i].examScore>limit){
					this.stdntList[i].examScore='';
				}
			},
			setBtnPassYn: function(event) {
				var _this = this;
			   	var selectedValue = event.target.value; // 선택된 값
			  	console.log(selectedValue); // 'Y' 또는 'N'
			  	 // 모든 name="passYn"을 가진 select 요소에 값 적용
			    for(var i in _this.stdntList){
			    	var o = _this.stdntList[i];
			    	console.log(o);
			        o.passYn = selectedValue; // 값 변경 후 change 이벤트 트리거
			    }
			},
		},
	});
	
	//전체선택 
	$("#chkStdntAll").click(function() {
		if($("#chkStdntAll").is(":checked") == true){
			$("input[name=delUserIds]:checkbox").each(function() {
				$(this).prop("checked", true);
			});	
		}else{
			$("input[name=delUserIds]:checkbox").each(function() {
				$(this).prop("checked", false);
			});
		}
	});
	
	$('#tab3Row').change(function(){
		$("#tab3Page").val("1");
		fn_htmlWrite("3");
	});
});


</script>
<script>
function fn_changePass(o){
	$.ajax({
		type:'post',
		url:'${utcp.ctxPath}/admin/ajax/changeStdntPass.json',
		data:{eduSeq:o.eduSeq,userId:o.userId,passYn:o.passYn},
		success:function(r){
			console.log(r);
			if(r.result==1){
				fn_htmlWrite("3");
			}
		}
	});
}

function fn_saveRecord(){
	var exit=false;
	
	var formData={};
	formData.dataList=[];
	/*
	$('#tabCon3 table tbody tr').each(function(){
		var dataMap={};
		dataMap.eduSeq = $("#eduSeq").val();
		dataMap.userId = $(this).find('input[name=userId]').val();
		dataMap.attCnt = $(this).find('input[name=attCnt]').val();
		dataMap.workshopScore = $(this).find('input[name=workshopScore]').val();
		dataMap.examScore = $(this).find('input[name=examScore]').val();
		dataMap.passYn = $(this).find('select[name=passYn]').val();
		formData.dataList.push(dataMap);
		
		if(!$.isNumeric(dataMap.workshopScore) && !$.isNumeric(dataMap.examScore)){
			exit=true;
			return;
		}
	});
	
	if(exit){
		return;
	}
	*/
	formData.eduSeq = $('#eduSeq').val();
	formData.dataList = vm_3.stdntList;
	
	$.ajax({
		type:'post',
		contentType:'application/json',
		url:'${utcp.ctxPath}/admin/ajax/saveStdntScore.json',
		data:JSON.stringify(formData),
		success:function(r){
			if(r.result!=''){
				$("#messageStr").html(r.result);
			}else{
				//fn_gooroomee();
				$("#messageStr").html("저장되었습니다.");
				fn_tab('3');
			}
			location.href='#message';
		},
	});
}
</script>

<!-- 문자발송 레이어 -->
<div class="remodal remodal-is-initialized remodal-is-opened" data-remodal-id="md-sms3" id="vm-sms3" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc" style="width: 480px;"  tabindex="-1" data-remodal-options="closeOnOutsideClick: false">
	<button data-remodal-action="close" class="remodal-close" aria-label="Close"></button>
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC"><i class="fas fa-chevron-circle-right fs_22 pdr5"></i>문자발송</p>
		</div>
		<div class="modal-body">
			<div class="tbBox1">
				<textarea class="w100 h400" v-model="msg"></textarea>
			</div>
		</div>
		<div class="modal-footer">
			<div class="tc">
				<button type="button" @click="sendSms" class="remodal-confirm btn01 btn_green">발송</button>
			</div>
		</div>
	</div>
</div>
<script>
var vm_sms_msg = '';
vm_sms_msg += '0000년 0월 안전교육시스템에서 신청하신 보육교직원 안전교육이 현재 미수료 상태로, 0/0(0)까지 수강 완료하셔야 합니다. \n';
vm_sms_msg += '(마이페이지-진행중 교육 클릭-진도율 100% 여부 확인)\n';
vm_sms_msg += '* 0월 신청하신 안전교육과정 중 1개라도 미이수하신 경우 문자 발송됨\n\n';
vm_sms_msg += '※ 보육교직원 안전교육은 기본, 심화, 영아담당교사Ⅰ,Ⅱ, 유아담당교사Ⅰ,Ⅱ 과정 중 매년 1개만 이수하시면 됩니다. (문의: 1600-0611) \n';

var vm_sms3 = new Vue({
	el: '#vm-sms1',
	data: {
		msg: vm_sms_msg,
		noPassList: [],
	},
	methods: {
		sendSms: function(){
			var _this = this;
			
			//발송목록체크
			if(_this.noPassList.length == 0){
				alert('발송대상이 없습니다.');return;
			}
			
			if(!confirm('문자발송 하시겠습니까?')){
				return;
			}
			
			$.ajax({
				type: 'post',
				data: {msg: _this.msg},
				url: '/admin/sms/sendSmsNoPassList.ajax',
				success: function(r){
					if(r.result == 1){
						alert('문자발송 완료하였습니다.');
						$('[data-remodal-id=md-sms1]').remodal().close();
						location.reload();
					}else{
						alert(r.msg);
					}
					
				}
			});
			
		},
		callLayer: function(){
 			var toNum = vm_3.stdntList.filter(o=>o.checked).map(o=>o.mobile).join(',');
 			console.log(toNum);
			fn_md_smsSend('', toNum, '', fn_stdnt_closeSmeSend, true);
			//$('[data-remodal-id=md-sms1]').remodal().open();
			//location.href='#none';
		},
	},
});
function fn_stdnt_closeSmeSend(){
	alert('문자발송 완료하였습니다.');
}
</script>