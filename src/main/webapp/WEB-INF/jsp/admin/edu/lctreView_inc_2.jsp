<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<div id="vm-tab2">
	<input type="hidden" id="tab2Page" value="1" />
	<span class="tb_text"> 전체 <strong class="fc_red" id="totalTab2">{{totalCnt}}</strong> 명 / 승인 <strong class="fc_red" id="confirmTab2">{{stdntCnt}}</strong> 명
	</span>
	<input type="hidden" id="tab2Page" value="1" />
	<div class="dp_ib fr tr mgb15">
		<label for="tab2Row" class="sound_only">리스트줄선택</label>
		<select id="tab2Row" class="btn04 btn_blackl">
			<option value="10">10줄</option>
			<option value="50">50줄</option>
			<option value="100">100줄</option>
			<option value="150">150줄</option>
		</select>
		<select id="tab2SrchState" class=" btn_blackl">
			<option value="">신청상태선택</option>
			<option value="2" >승인</option>
			<option value="1" >신청</option>
			<option value="3" >취소</option>
		</select>

		<label for="tab2SrchWrd" class="sound_only">검색어입력</label>
		<input type="text" id="tab2SrchWrd" placeholder="검색" class="btn04 btn_blackl tl mgr5" />

		<button class="btn04 btn_black fr" onclick="fn_htmlWrite('2');">검색</button>
	</div>
	<table width="100%" class="tb02 tc inc-tb02">
		<thead bgcolor="#f7f8fa">
			<tr>
				<!-- <th><input type="checkbox" id="chkUserAll" /></th> -->
				<th>
					<input type="checkbox" v-model="isAllChecked" @change="handleChangeAllCheck" />
				</th>
				<th>성명</th>
				<th>아이디</th>
				<th>생년월일</th>
				<th>이메일</th>
				<th>휴대폰번호 </th>
				<!-- 
				<th>결제수단</th>
				<th>결제상태</th> 
				<th>결제금액</th>
				<th>접수번호</th>
				 -->
				
				<th>명찰/시간표</th> 
				<th>신청서</th> 
				<th>신청일</th>
				<th>신청상태</th>
			</tr>
		</thead>
		<tbody>
			<tr v-for="(o,i) in dataList">
				<td><input type="checkbox" v-model="o.checked" /></td>
				<td>{{o.userNm}}</td>
				<td>{{o.loginId}}</td>
				<td>{{o.birth}}</td>
				<td>{{o.email}}</td>
				<td>{{o.mobile}}</td>
				<!-- 
				<td>{{o.addFeeTpNm}}</td>
				<td>{{o.addLimsStateNm}}</td>
				<td>{{o.limsFee|fltCurrency}}</td>
				<td>{{o.limsSrNo}}</td>
				 -->
				
				<td>
					<a href="#none" @click="vf_nameCard(o)" class="btn04 btn_green">보기</a>
				</td>
				<td><a  href="#none" @click="vf_openRcept(o)" class="btn04 btn_blue">신청서</a></td> 
				<td>{{o.regDe|fltDate2Str('YYYY.MM.DD HH:mm')}}</td>
				<td>
				<select v-model="o.state" @change="vm_updState(o)">
					<option value="1">신청</option>
					<option value="4">접수</option>
					<option value="2">승인</option>
					<option value="3">취소</option>
				</select>
				</td>
			</tr>
			<tr v-if="!dataList.length">
				<td colspan="11" class="h200">신청자가 없습니다.</td>
			</tr>
		</tbody>
	</table>
	<div class="fl tc" id="closeBtn2">
		<c:choose>
			<c:when test='${lctre.rceptYn == "Y"}'>
				<c:if test="${lctre.lctreType ne 3 }">
				<!-- 
				<button class="btn01 btn_greenl closeAble" onclick="fn_rceptCloseOnly(); return false;">접수 마감</button> 
				-->
				</c:if>
			</c:when>
			<c:otherwise>
				<!-- 
				<button class="btn01 btn_orangel closeDisable" onclick="fn_rceptOpenOnly();" type="button">마감완료</button> 
				-->
			</c:otherwise>
		</c:choose>
	</div>

	<div class="fr tc">
		<button class="btn01 btn_green" onclick="vm_sms2.callLayer()">안내문구발송</button>
		<button class="btn01 btn_green" onclick="vm_email.callLayer()">이메일발송</button>
		<button class="btn01 btn_green" onclick="fn_excelRceptPv(); return false;">엑셀 다운로드</button>
		<!-- 
		<button class="btn01 btn_green" onclick="fn_openMdExcelLog('rceptExcel','${lctre.eduSeq}');">엑셀 다운로드</button>
			<button class="btn01 btn_greenl" onclick="fn_bulkRgs();">수강생 등록</button>
		 -->
		<c:if test='${lctre.rceptYn == "Y"}'>
		</c:if>
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
<script>
function fn_excelRceptPv(){
	location.href = "${utcp.ctxPath}/admin/excel/rceptExcel.do?eduSeq=${lctre.eduSeq}";
}

</script>

<!--// 수강생 등록 //-->
<div class="remodal" data-remodal-id="groupRegist" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc">
	<button data-remodal-action="close" class="remodal-close" aria-label="Close"></button>
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC"><i class="fas fa-chevron-circle-right font_22 pdr5"></i>수강생 등록</p>
		</div>
		<div class="modal-body">	
			<div class="dp_ib tc pd15">
				<label for="srchBelong" class="sound_only">검색어입력</label>
				<input type="text" id="srchBelong" placeholder="아이디,성명 검색" class="btn04 btn_blackl tl mgr5" />
				<button class="btn04 btn_black fr" onclick="fn_bulkRgs();">검색</button>
			</div>			
			<div class="tbBox">
				<table class="tc w100 tb">
					<thead bgcolor="#f7f8fa">
						<tr>
							<th><input type="checkbox" id="checkAllUser" /></th>
							<th>NO</th>
							<th>아이디</th>									
							<th>대상</th>									
							<th>성명</th>
							<th>이메일</th>									
							<th>휴대전화</th>
						</tr>
					</thead>
					<tbody id="userList">
					   							
					</tbody>
				</table>
			</div>
		</div>
		<div class="modal-footer">
			<div class="tc">
				<button onclick="fn_bulkUserRgs();" class="remodal-confirm btn01 btn_green">등록</button>
			</div>
		</div>
	</div>
</div>
<!--// 단체수강등록 //-->	

<script>
	var vm_2 = new Vue({
		el : '#vm-tab2',
		data : {
			dataList:[],
			pageNavi:{},
			totalCnt:0,
			page:{},
			stdntCnt:0,
			waitCnt:0,
			isAllChecked: false,
		},
		computed:{
			
		},
		updated : function() {
			
		},
		methods:{
			handleChangeAllCheck: function(){
				console.log(this.isAllChecked);
				this.dataList = this.dataList.map(item => ({
					...item,
					checked: this.isAllChecked
				}));
				
			},
			vm_updState: function(o){
				fn_updState(o);
			},
			vf_openRcept: function(o){
				md_openLcrcpInfo(o.eduSeq,o.rceptSeq,o.userId);
			},
			vf_openCouns: function(o){
				window.open('${utcp.ctxPath}/admin/lccns/popup/lccnsList.do?userId='+o.userId+'&eduSeq='+o.eduSeq,'stdntCouns','width=800,height=700');
			},
			vf_nameCard: function(o){
				window.open('${utcp.ctxPath}/comm/popup/nameCard.do?userId='+o.userId+'&eduSeq='+o.eduSeq,'nameCard','width=800,height=700');
			}
		},
	});
	function call2RceptList(){
		var pageNo = $("#tab2Page").val();
		var row = $("#tab2Row").val();
		var srchWrd = $("#tab2SrchWrd").val();
		var srchRceptState = $("#tab2SrchState").val();
		$.ajax({
			data : {
				eduSeq : '${param.eduSeq}',
				pageNo : pageNo,
				row : row,
				srchWrd : srchWrd,
				srchRceptState : srchRceptState,
			},
			url : '${utcp.ctxPath}/admin/edu/lectureRceptList.ajax',
			success : function (r){
				if(r.result == 1){
					vm_2.dataList = r.data.dataList;
					vm_2.page = r.data.page;
					vm_2.totalCnt = r.data.totalCnt;
					vm_2.stdntCnt = r.data.stdntCnt;
				}
			}
		});
	}
	function fn_pageTab2(page){
		$("#tab2Page").val(page);
		fn_htmlWrite("2");
	}
	
	function fn_bulkUserRgs(){
		var userIds = new Array();
		var i = 0;
		
		if($("input[name='bulkUserIds']:checked").length == 0){
			alert("등록할 교육생을 선택하세요.");
			return;
		}else{
			$("input[name=bulkUserIds]:checked").each(function() {
				userIds[i] = $(this).val();
				i++;
			});
		}
		//isloading.start();
		$.ajax({
	        url: "${utcp.ctxPath}/admin/edu/bulkUserRgs.json",
	        type: "POST",
	        traditional : true,
	        data: {
	        	"eduSeq" : $('#eduSeq').val(),
				"userIds" : userIds
	        },
	        cache: false,
			async: true,
	        success: function(r) {
	        	//isloading.stop();
				if(r.isSuccess){
					location.href = "#successTab2";
				}else{
					alert(r.message);
					//$("#messageTab2").html(r.message);
					//location.href = "#messageTab2";
				}
			}
	    });
	}
</script>

<script>

//신청서 상태 변경
function fn_updState(o){
	var userId = o.userId;
	var state = o.state;
	var eduSeq = o.eduSeq;
	var rceptSeq = o.rceptSeq;
	$.ajax({
		type:'post',url:'${utcp.ctxPath}/admin/ajax/updRceptState.ajax',
		data:{eduSeq : eduSeq,userId : userId,
			state : state, rceptSeq : rceptSeq,},
		async:false,
		success:function(r){
			if(r.result == 1){
				fn_htmlWrite("2");
			}else if (r.result == 6){//결제취소하기
				if(!confirm('결제완료된 신청서입니다. 결제취소 처리 하시겠습니까?')){
					return;
				}
				$.ajax({
					url : '${utcp.ctxPath}/user/pay/easypay/cancel.ajax',
					data : {
						eduSeq : eduSeq,
						userId : userId
					},
					success : function(r){
						console.log(r);
						if(r.result == 1){
						
						}else{
							alert('결제취소에 실패하였습니다. 관리자에게 문의하세요');
						}
					}
				});
			}else{
				alert(r.msg);
				fn_htmlWrite("2");
			}
		}
	});
}
function fn_bulkRgs(){
	$("#checkAllUser").prop("checked", false);
	$.ajax({
		url:"${utcp.ctxPath}/user/edu/bulkStdntList.json",
		type: "post",
		data: {
			"eduSeq" : $('#eduSeq').val(),
			"srchWrd" : $("#srchBelong").val()
		},
		dataType: 'json',
		crossDomain: true,
		success: function(r) {
			if(r.isAdmin){
				var list = r.dataList;
				var dataHtml = "";
				
				if(list == null || list.length < 1 || list == "") {
					dataHtml += "<tr>";
					dataHtml += "	<td colspan=\"6\" class=\"h200\">데이터가 없습니다.</td>";
					dataHtml += "</tr>";
				}else{
					$(list).each(function(i) {
						dataHtml += "<tr>";
						dataHtml += "	<td><input type=\"checkbox\" name=\"bulkUserIds\" value=\"" + list[i].userId + "\" /></td>";
						dataHtml += "	<td>" + (list.length - i) + "</td>";
						dataHtml += "	<td>" + list[i].loginId + "</td>";
						dataHtml += "	<td>" + list[i].addUserTpNm + "</td>";
						dataHtml += "	<td>" + list[i].userNm + "</td>";
						dataHtml += "	<td>" + list[i].email + "</td>";
						dataHtml += "	<td>" + list[i].mobile + "</td>";
						dataHtml += "</tr>";
					});	
				}
				$("#userList").html(dataHtml);	
			}else{
				location.href = "#authMessage";
			}
			
		}
	});

	location.href= "#groupRegist";
}
</script>

<!-- 신청서 모달 -->
<div class="remodal" id="vm-rcept-view" data-remodal-id="md-rcept-view" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc">
	<button data-remodal-action="close" class="remodal-close" aria-label="Close"></button>
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC"><i class="fas fa-chevron-circle-right font_22 pdr5"></i>신청서</p>
		</div>
		<div class="modal-body">	
			<div class="tbBox">
				<table class="tc w100 tb">
					<tr>
						<th>아이디</th>
						<td>{{rceptObj.loginId}}</td>
					</tr>
					<tr>
						<th>이름</th>
						<td>{{rceptObj.userNm}}</td>
					</tr>	
					<tr>
						<th>email</th>
						<td>{{rceptObj.email}}</td>
					</tr>	
					<tr>
						<th>휴대폰번호 </th>
						<td>{{rceptObj.mobile}}</td>
					</tr>	
					<!-- <tr>
						<th>소속</th>
						<td>{{rceptObj.belong}}</td>
					</tr> -->	
					<tr>
						<th>기타남길말</th>
						<td>{{rceptObj.etc}}</td>
					</tr>	
					<tr v-for="(o,i) in rceptEtcList">
						<th>{{o.etcIemNm}}</th>
						<td>{{o.etcIemData}}</td>
					</tr>
					<tr>
						<th>첨부파일</th>
						<td>
							<a v-if="rceptObj.fileOrg" :href="'${utcp.ctxPath}/user/edu/attach/'+rceptObj.eduSeq+'/'+rceptObj.userId+'/rceptDownload.do'">[다운로드]</a>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<div class="modal-footer">
			
		</div>
	</div>
</div>
<!--// 단체수강등록 //-->	
<script>
var vm_rcept_view = new Vue({
	el : '#vm-rcept-view',
	data : {
		rceptObj:{},
		rceptEtcList:[],
	},
	methods:{
		
	},
});

</script>

<!--// 결제상태 변경 및  비고 작성 //-->
<div id="vm-change-pay-state9" class="remodal" data-remodal-id="md-change-pay-state9" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc">
	<button data-remodal-action="close" class="remodal-close" aria-label="Close"></button>
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC"><i class="fas fa-chevron-circle-right font_22 pdr5"></i>결제 상태 변경</p>
		</div>
		<div class="modal-body pdl15 pdr15">	
			<table class="w100 tb02 tc">
				<tbody>
					<tr>
						<th class="tc tdbg3">결제수단</th>
						<td class="tl" colspan="">
							{{rcept.addPayTypeNm}} 
						<template v-if="rcept.pgPayNo">
							<button class="btn04 btn_blue" type="button" :onclick="'receipt(\''+rcept.pgPayNo+'\',\''+rcept.pgType+'\');'">영수증</button>
						</template>
						</td>
					</tr>
					<tr>
						<th class="tc tdbg3">결제상태</th>
						<td class="tl" colspan="">
							<template v-if="!rcept.pgPayNo">
							<select v-model="rcept.payState">
								<option value="0">선택</option>
								<option value="1">결제완료</option>
								<option value="3">결제취소</option>
							</select>
							</template>
							<template v-else>
								{{rcept.addPayStateNm}}
							</template> 
						</td>
					</tr>
					<tr>
						<th class="tc tdbg3">환불계좌정보</th>
						<td class="tl" colspan="">
							은행명 &nbsp;&nbsp;&nbsp;&nbsp;: {{payBack.bankNm}}<br/>
							예금주명 : {{payBack.accountNm}}<br/>
							계좌번호 : {{payBack.accountNo}}
						</td>
					</tr>
					<tr>
						<th class="tc tdbg3">비고</th>
						<td class="tl" colspan="">
							<textarea v-model="memo.memoPay" class="w100 h100p" style="height: 70px;"></textarea>
						</td>
					</tr>
				</tbody>
			</table>
			
		</div>
		<div class="modal-footer">
			<div class="tc">
				<button :onclick="'fn_savePayState('+rcept.rceptSeq+');'" class="remodal-confirm btn01 btn_green">저장</button>
			</div>
		</div>
	</div>
</div>
<script>
//결제상태 변경 vue
var vm_2_1 = new Vue({
	el : '#vm-change-pay-state9',
	data : {
		rcept:{},
		payBack:{},
		memo:{},
	},
});
function fn_openModal_payState(userId,rceptSeq){
	var eduSeq = $('#eduSeq').val();
	$.ajax({
		data:{eduSeq:eduSeq,userId:userId,
			rceptSeq : rceptSeq,	
		},
		url:'${utcp.ctxPath}/admin/ajax/getRceptInfo.json',
		success:function(r){
			vm_2_1.rcept={};
			vm_2_1.memo={};
			vm_2_1.payBack={};
			if(r.result==1){
				vm_2_1.rcept=r.data.rcept;
				vm_2_1.memo=r.data.memo;
				vm_2_1.payBack=r.data.payBack;
				$('[data-remodal-id=md-change-pay-state9]').remodal().open();
			}else{
				alert('오류');
			}
		}
	});
}
function fn_savePayState(rceptSeq){
	
}
</script>

<!-- 문자발송 레이어 -->
<div class="remodal remodal-is-initialized remodal-is-opened" data-remodal-id="md-sms2" id="vm-sms2" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc" style="width: 480px;"  tabindex="-1" data-remodal-options="closeOnOutsideClick: false">
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

var vm_sms2 = new Vue({
	el: '#vm-sms2',
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
 			var toNum = vm_2.dataList.filter(o=>o.checked).map(o=>o.mobile).join(',');
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

<!-- 이메일발송 레이어 -->
<div class="remodal remodal-is-initialized remodal-is-opened" data-remodal-id="md-email" id="vm-email" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc" style="width: 480px;"  tabindex="-1" data-remodal-options="closeOnOutsideClick: false">
	<button data-remodal-action="close" class="remodal-close" aria-label="Close"></button>
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC"><i class="fas fa-chevron-circle-right fs_22 pdr5"></i>이메일발송</p>
		</div>
		<div class="modal-body">
			<div class="tbBox1">
				<textarea class="w100 h400" v-model="msg"></textarea>
			</div>
		</div>
		<div class="modal-footer">
			<div class="tc">
				<button type="button" @click="sendEmail" class="remodal-confirm btn01 btn_green">발송</button>
			</div>
		</div>
	</div>
</div>
<script>
// var vm_sms_msg = '';
// vm_sms_msg += '0000년 0월 안전교육시스템에서 신청하신 보육교직원 안전교육이 현재 미수료 상태로, 0/0(0)까지 수강 완료하셔야 합니다. \n';
// vm_sms_msg += '(마이페이지-진행중 교육 클릭-진도율 100% 여부 확인)\n';
// vm_sms_msg += '* 0월 신청하신 안전교육과정 중 1개라도 미이수하신 경우 문자 발송됨\n\n';
// vm_sms_msg += '※ 보육교직원 안전교육은 기본, 심화, 영아담당교사Ⅰ,Ⅱ, 유아담당교사Ⅰ,Ⅱ 과정 중 매년 1개만 이수하시면 됩니다. (문의: 1600-0611) \n';

var vm_email = new Vue({
	el: '#vm-email',
	data: {
// 		msg: vm_sms_msg,
		noPassList: [],
	},
	methods: {
		sendEmail: function(){
			var _this = this;
			
			//발송목록체크
			if(_this.noPassList.length == 0){
				alert('발송대상이 없습니다.');return;
			}
			
			if(!confirm('이메일발송 하시겠습니까?')){
				return;
			}
			
			$.ajax({
				type: 'post',
				data: {msg: _this.msg},
				url: '/admin/sms/sendEmailNoPassList.ajax',
				success: function(r){
					if(r.result == 1){
						alert('이메일발송 완료하였습니다.');
						$('[data-remodal-id=md-email]').remodal().close();
						location.reload();
					}else{
						alert(r.msg);
					}
					
				}
			});
			
		},
		callLayer: function(){
			var toNum = vm_2.dataList.filter(o=>o.checked).map(o=>o.email).join(',');
 			console.log(toNum);
			fn_md_emailSend('', toNum, '', fn_stdnt_closeEmailSend, true);
			//$('[data-remodal-id=md-sms1]').remodal().open();
			//location.href='#none';
		},
	},
});
function fn_stdnt_closeEmailSend(){
	alert('이메일발송 완료하였습니다.');
}
</script>