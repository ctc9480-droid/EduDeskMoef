<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<div id="vm-11">
	<div class="room-admin">
		<div class="room-top">
			<div class="room-total">
				<span class="tb_text"> 전체 <strong class="fc_red" id="totalTab2">{{page.totalRecordCount}}</strong> 명 / 배정 <strong class="fc_red" id="confirmTab2">{{assignedCount}}</strong> 명</span>
			</div>
			<div class="room-top-bot">
				<div class="room-tb-txt">
					<span>* 숙소정보가 변경되면 숙박비는 전부 재계산 됩니다.</span>
				</div> 
				<div class="room-select">
					<!-- 
					 -->
					<input type="text" class="room-input" style="width:100px;" v-model="baseMealFee">
					<button class="room-btn room-sel-btn" @click="setBaseMealFee">식비저장</button> /
					<button class="room-btn room-sel-btn" onclick="vm_11_dormiList.callDormiList('','')">숙소배정</button>
				</div>
			</div>
			<div class="room-bot-bot">
				<div class="room-bb-lt">
					<div class="room-bb-list room-bb-list01">
						<span class="room-bb-tit">식비납부계좌</span>
						<input type="text" class=" ip2" placeholder="oo은행 000-00-00000, 예금주명" id="mealAccount" name="mealAccount" value="${lctre.mealAccount}" maxlength="50"/>
					</div>
					<div class="room-bb-list room-bb-list02">
						<span class="room-bb-tit">숙박비납부계좌</span>
						<input type="text" class=" ip2" placeholder="oo은행 000-00-00000, 예금주명" id="dormiAccount" name="dormiAccount" value="${lctre.dormiAccount}" maxlength="50"/>
					</div>
					<div class="room-bb-list room-bb-list03">
						<span class="room-bb-tit">납부기한</span>
						<input type="text" id="depositLimitDt" name="depositLimitDt" value="${lctre.depositLimitDt}" class="datepicker input_calendar ip6 tc" readonly/>
					</div>
				</div>
				<div class="room-bb-rg">
					<button class="room-btn room-sel-btn btn_green" onclick="vm_sms11.callLayer()">(자동)안내문구발송</button>
				</div>
				
			</div>
			
		</div>
		<div class="room-table room-first-table">
			<form action="#">
				<table>
					<thead>
						<tr>
							<th>
								<input type="checkbox" v-model="isAllChecked" @change="handleChangeAllCheck" />
							</th>
							<th>성명</th>
							<th>ID</th>
							<th>연락처</th>
							<th>이메일</th>
							<th>소속</th>
							<th>성별</th>
							<th>버스이용</th>
							<th>식비</th>
							<th>요청숙소</th>
							<th>숙박비</th>
							<th>배정숙소</th>
							<th>숙소배정</th>
							<th>납부여부</th>
						</tr>
					</thead>
					<tbody>
						<tr v-for="(o,i) in list">
							<td><input type="checkbox" v-model="o.checked" /></td>
							<td>{{o.userNm}}</td>
							<td>{{o.loginId}}</td>
							<td>{{o.mobile}}</td>
							<td>{{o.email}}</td>
							<td>{{o.userOrgNm}}</td>
							<td>{{o.mfTypeNm}}</td>
							<td>
							<template v-if="o.useTrans == 1">
							왕복
							</template>
							<template v-else-if="o.useTrans == 2">
							입교
							</template>
							<template v-else-if="o.useTrans == 3">
							퇴교
							</template>
							<template v-else>
							미이용
							</template>
							</td>
							<td>
							<template v-if="o.isUpdMeal">
							<input type="text" v-model="o.mealFee"  @input="o.mealFee = o.mealFee.replace(/[^0-9]/g, '')" class="" style="width:100px;" maxlength="10"/>
							<a href="#none" class="pd5 btn_darkblue"  @click="setMealFee(o)"><i class="fa-solid fa-pen"></i></a>
							</template>
							<template v-else>
							{{o.mealFee|fltCurrency}}
							<a href="#none" class="pd5 btn_darkblue"  @click="updMealBox(o)"><i class="fa-solid fa-pen"></i></a>
							</template>
							</td>
							<td>
							<template v-if="o.rceptCapaCnt > 0">
							{{o.rceptCapaCnt}}인실
							</template>
							<template v-else>
							미이용
							</template>
							<template v-if="o.rceptAccessYn == 'Y'">
							(장애인)
							</template>
							</td>
							<td>
							<template v-if="o.isUpdDormi">
							<input type="text" v-model="o.dormiFee"  @input="o.dormiFee = o.dormiFee.replace(/[^0-9]/g, '')" class="" style="width:100px;" maxlength="10"/>
							<a href="#none" class="pd5 btn_darkblue"  @click="setDormiFee(o)"><i class="fa-solid fa-pen"></i></a>
							</template>
							<template v-else>
							{{o.dormiFee|fltCurrency}}
							<a href="#none" class="pd5 btn_darkblue"  @click="updDormiBox(o)"><i class="fa-solid fa-pen"></i></a>
							</template>
							</td>
							<td>
							<template v-if="o.dormiSeq > 0">
							{{o.capaCnt}}인실
							<template v-if="o.accessYn == 'Y'">
							(장애인)
							</template>
							</template>
							<td>
								<input type="text" :value="o.dormiNm" :onclick="'vm_11_dormiList.callDormiList('+o.userId+','+o.dormiNM+')'" class="room-complete" readonly=""/>
							</td>
							<td>
								<select name="depositYn" v-model="o.depositYn" @change="setDepositYn(o)">
									<option value="Y">O</option>
									<option value="N">X</option>
								</select>
							</td>
						</tr>
						
					</tbody>
				</table>
			</form>
			<div class="" style="margin-top: 20px; text-align: right;">
				<button onclick="fn_excelDormiMeal()" class="room-btn room-down" style="background: #7d7d7d;">엑셀다운로드</button>
				<!-- 
				<button class="room-btn room-submit" style="background: #7d7d7d;">저장</button>
				 -->
			</div>
			<!--// paging //-->
			<div class="page" id="tab2PageDiv">
				<div class="inner cf">
					<template v-if="page.currentPageNo != 1">
					<div class="page_prev0">
						<a href="javascript:;" :onclick="'fn_pageTab2('+(page.currentPageNo - 1)+');'">&lt; 이전</a>
					</div>
					</template>
					<template v-for="o in (page.firstPageNoOnPageList , page.lastPageNoOnPageList)"> <template v-if="o == page.currentPageNo">
					<div class="page_now">
						<a>{{o}}</a>
					</div>
					</template> <template v-else>
					<div class="page_nomal">
						<a href="javascript:;" :onclick="'fn_pageTab2('+o+');'">{{o}}</a>
					</div>
					</template> </template>
					<template v-if="page.currentPageNo != page.totalPageCount && page.totalPageCount > 0">
					<div class="page_next0">
						<a href="javascript:;" :onclick="'fn_pageTab2('+(page.currentPageNo + 1) + ');'">다음 &gt;</a>
					</div>
					</template>
				</div>
			</div>
			<!--// paging //-->
		</div>
	</div>
</div>
<script>
var vm_11 = new Vue({
	el : '#vm-11',
	data : {
		list: [],
		page: {},
		baseMealFee: 0,
		isAllChecked: false,
	},
	methods:{
		handleChangeAllCheck: function(){
			console.log(this.isAllChecked);
			this.list = this.list.map(item => ({
				...item,
				checked: this.isAllChecked
			}));
			
		},
		getStdntList: function(){
			var _this = this;
			var eduSeq = '${param.eduSeq}'*1;
			$.ajax({
				data: {eduSeq: eduSeq},
				url: '${utcp.ctxPath}/user/ajax/lectureDormiStdnt.ajax',
				success: function(r){
					_this.list = r.data.stdnt.map(o => ({
				        ...o,
				        isUpdMeal: false, // 여기서 반응형 속성 추가
				        isUpdDormi: false
				      }));
					_this.page = r.data.page;
				},
			});
		},
		setBaseMealFee: function(){
			if(!confirm('식비가 일괄 변경됩니다. 계속 진행하시겠습니까?')){
				return;
			}
			var _this = this;
			var eduSeq = '${param.eduSeq}'*1;
			var mealFee = this.baseMealFee;
			$.ajax({
				data: {eduSeq: eduSeq, mealFee: mealFee},
				url: '${utcp.ctxPath}/admin/ajax/setMealFee.ajax',
				success: function(r){
					if(r.result ==1){
						_this.getStdntList();
					}else{
						alert(r.msg);
					}
				}
			});
		},
		setMealFee: function(_o){
			var _this = this;
			var eduSeq = '${param.eduSeq}'*1;
			var mealFee = _o.mealFee;
			var userId = _o.userId;
			$.ajax({
				data: {eduSeq: eduSeq, mealFee: mealFee, userId: userId},
				url: '${utcp.ctxPath}/admin/ajax/setMealFee.ajax',
				success: function(r){
					if(r.result ==1){
						_this.getStdntList();
					}else{
						alert(r.msg);
					}
				}
			});
		},
		updMealBox: function(target){
			this.list.forEach(o => {
			      o.isUpdMeal = (o === target);
			      o.isUpdDormi = false;
			    });
		},
		setDormiFee: function(_o){
			var _this = this;
			var eduSeq = '${param.eduSeq}'*1;
			var dormiFee = _o.dormiFee;
			var userId = _o.userId;
			$.ajax({
				data: {eduSeq: eduSeq, dormiFee: dormiFee, userId: userId},
				url: '${utcp.ctxPath}/admin/ajax/setDormiFee.ajax',
				success: function(r){
					if(r.result ==1){
						_this.getStdntList();
					}else{
						alert(r.msg);
					}
				}
			});
		},
		updDormiBox: function(target){
			this.list.forEach(o => {
				o.isUpdMeal = false;
				o.isUpdDormi = (o === target);
			    });
		},
		setDepositYn: function(_o){
			var _this = this;
			var eduSeq = '${param.eduSeq}'*1;
			var depositYn = _o.depositYn;
			var userId = _o.userId;
			$.ajax({
				data: {eduSeq: eduSeq, depositYn: depositYn, userId: userId},
				url: '${utcp.ctxPath}/admin/ajax/setDepositYn.ajax',
				success: function(r){
					if(r.result ==1){
						_this.getStdntList();
					}else{
						alert(r.msg);
					}
				}
			});
		},
	},
	computed: {
	    assignedCount: function() {
	      return this.list.filter(s => s.dormiSeq > 0).length;
	    }
	  }
});
</script>

<script>
function fn_excelDormiMeal(){
	location.href='${utcp.ctxPath}/admin/excel/dormiMealExcel.do?eduSeq=${param.eduSeq}';
}
</script>

<!-- 숙소배정 모달 -->
<div class="room-modal-bg" id="vm-11-dormiList">
	<div class="room-modal">
		<!-- <h2>
			<span class="date">2025.08.14 (목)</span> 숙소 현황
		</h2> -->
		
		<template v-if="userId == ''">
		<div class="room-modal-close">
			<span class="close-txt">닫기</span>
			<i class="fa-solid fa-xmark"></i>
		</div>
		<div class="room-modal-top">
			<p>
				<strong class="num">{{selectedSeqs.length}}</strong>개실 선택
			</p>
			<button class="room-btn" @click="setDormi(0)">배정하기</button>
		</div>
		</template>
		<template v-else-if="userId != '' && dormiNm != ''">
		<div class="room-modal-top">
		<p></p>
		<button class="room-btn" @click="delDormi(userId)">배정삭제</button>
		</div>
		</template>
		
		<div class="room-table room-second-table">
			<form action="#">
				<table>
					<thead>
						<tr>
							<th>
							<template v-if="userId">
							선택
							</template>
							<template v-else>
							<input type="checkbox" @click="eventChecked"/>
							</template>
							</th>
							<th>숙소명</th>
							<th>형태</th>
							<th>위치</th>
							<th>사용현황</th>
						</tr>
					</thead>
					<tbody>
						<template v-for="(o,i) in list">
						<tr>
							<td>
							<template v-if="userId">
							<button type="button" class="btn04 btn_blue" @click="setDormi(o.dormiSeq)">선택</button>
							</template>
							<template v-else>
							<input type="checkbox"  :value="o.dormiSeq" v-model="selectedSeqs"/>
							</template>
							</td>
							<td>{{o.dormiNm}}</td>
							<td>{{o.capaCnt}}인실
							<template v-if="o.accessYn == 'Y'">(장애인)</template>
							</td>
							<td>{{o.place}}</td>
							<td>{{o.assignedCnt}}/{{o.capaCnt}}</td>
						</tr>
						</template>
					</tbody>
				</table>
			</form>
		</div>

	</div>
</div>

<script>

var vm_11_dormiList = new Vue({
	el : '#vm-11-dormiList',
	data : {
		list: [],
		selectedSeqs: [],
		userId:'',
		dormiNm: '',
	},
	methods:{
		callDormiList: function(userId,dormiNm){
			this.userId = userId;
			this.dormiNm = dormiNm;
			var _this = this;
			var eduSeq = '${param.eduSeq}'*1;
			$.ajax({
				data: {eduSeq: eduSeq, userId: userId},
				url: '${utcp.ctxPath}/user/ajax/getClassDormiList.ajax',
				success: function(r){
					if(r.result == 1){
						_this.list = r.data.list;
						$('#vm-11-dormiList').addClass('on');
					}
				},
			});
		},
		delDormi: function(userId){
			var _this = this;
			var eduSeq = '${param.eduSeq}'*1;
			$.ajax({
				traditional: true,
				data: {eduSeq: eduSeq, userId: userId },
				url: '${utcp.ctxPath}/user/ajax/delClassDormi.ajax',
				success: function(r){
					if(r.result == 1){
						$('#vm-11-dormiList').removeClass('on');
						_this.selectedSeqs =[];
						vm_11.getStdntList();
					}else{
						alert(r.msg);
					}
				},
			});
		},
		setDormi: function(dormiSeq){
			var _this = this;
			var eduSeq = '${param.eduSeq}'*1;
			var dormiSeqs = _this.selectedSeqs;
			var userId = this.userId
			if(!userId){
				if(dormiSeqs.length == 0){
					alert('숙소를 선택하세요');
					return;
				}
				if(!confirm('전제 학생이 재배정 됩니다. 계속 진행하시겠습니까?')){
					return;
				}
			}
			
			
			$.ajax({
				traditional: true,
				data: {eduSeq: eduSeq, dormiSeqs: dormiSeqs, userId: userId, dormiSeq: dormiSeq },
				url: '${utcp.ctxPath}/user/ajax/setClassDormi.ajax',
				success: function(r){
					if(r.result == 1){
						$('#vm-11-dormiList').removeClass('on');
						_this.selectedSeqs =[];
						vm_11.getStdntList();
					}else if(r.result == 2){
						alert(r.msg);
						_this.selectedSeqs =[];
						vm_11.getStdntList();
					}else{
						alert(r.msg);
					}
				},
			});
		},
		eventChecked: function(o){
			if (event.target.checked) {
			  	// 전체 선택
		      	this.selectedSeqs = this.list.map(o => o.dormiSeq);
		    } else {
		      	// 전체 해제
		      	this.selectedSeqs = [];
		    }
		},
	},
});
//문서 전체에 클릭 이벤트 등록
document.addEventListener('click', function(e) {
    // 클릭된 요소가 레이어 내부에 포함되어 있는지 확인
    const overlay = document.getElementById("vm-11-dormiList");
    const modalClose = document.querySelector(".room-modal-close");
    if (e.target === overlay) {
        // 레이어 외부를 클릭한 경우 -> 닫기 처리
    	$('#vm-11-dormiList').removeClass('on');
    }
    if (modalClose && modalClose.contains(e.target)) {
        $('#vm-11-dormiList').removeClass('on');
    }
    
});
$("#depositLimitDt").datepicker();
CKEDITOR.replace('ckeditCn',{
	filebrowserUploadUrl: '${utcp.ctxPath}/editot/popupUpload.json?gubun=edu&prefixStr=edu_cn',
	height : 400
});
 

</script>

<!-- 문자발송 레이어 -->
<div class="remodal remodal-is-initialized remodal-is-opened" data-remodal-id="md-sms11" id="vm-sms11" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc" style="width: 480px;"  tabindex="-1" data-remodal-options="closeOnOutsideClick: false">
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

var vm_sms11 = new Vue({
	el: '#vm-sms11',
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
 			var toNum = vm_11.list.filter(o=>o.checked).map(o=>o.mobile).join(',');
 			console.log(toNum);
 			var extra = {
 				eduNm : '${lctre.eduNm}',
				eduPeriodBegin : '${lctre.eduPeriodBegin}',
				eduPeriodEnd : '${lctre.eduPeriodEnd}',
				mealAccount : '${lctre.mealAccount}',
				dormiAccount : '${lctre.dormiAccount}',
				depositLimitDt : '${lctre.depositLimitDt}',
				mealFeeList : vm_11.list.filter(o=>o.checked).map(o=>o.mealFee).join(','),
				dormiFeeList : vm_11.list.filter(o=>o.checked).map(o=>o.dormiFee).join(','),
 			};
 			fn_md_smsSend('', toNum, '', fn_stdnt_closeSmeSend, true, extra);
			//$('[data-remodal-id=md-sms1]').remodal().open();
			//location.href='#none';
		},
	},
});
function fn_stdnt_closeSmeSend(){
	alert('문자발송 완료하였습니다.');
}
</script>