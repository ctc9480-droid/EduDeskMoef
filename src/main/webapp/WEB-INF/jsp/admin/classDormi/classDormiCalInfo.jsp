<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp" %>


<section class="pd025 pd200 po_re">
	<div class="po_re br5 bs_box cf">
		<div id="htmlCal">
			
		</div>
	</div>
</section>
<input type="hidden" name="srchYear" id="i_srchYear" value="${vo.srchYear}"> 
<input type="hidden" name="srchMonth" id="i_srchMonth" value="${vo.srchMonth}"> 
<script>
function fn_moveMonth2(command) {
	var year;
	var month;
	var action;
	if (command == "P") {
		if ($("#i_srchMonth").val() == "01") {
			year = Number($("#i_srchYear").val()) - 1;
			month = 12;
		} else {
			year = $("#i_srchYear").val();
			month = Number($("#i_srchMonth").val()) - 1;
		}
	} else {
		if ($("#i_srchMonth").val() == "12") {
			year = Number($("#i_srchYear").val()) + 1;
			month = 1;
		} else {
			year = $("#i_srchYear").val();
			month = Number($("#i_srchMonth").val()) + 1;
		}
	}
	if (month < 10) {
		$("#i_srchMonth").val('0' + month);
	} else {
		$("#i_srchMonth").val(month);
	}
	//location.href = action;
	$("#i_srchYear").val(year);
	fn_loadEduCal();
}
function fn_loadEduCal() {
	$.ajax({
		url : "classDormiCalInfo2.do",
		type : "POST",
		data : {
			"srchYear" : $("#i_srchYear").val(),
			"srchMonth" : $("#i_srchMonth").val(),
		},
		cache : false,
		async : true,
		success : function(r) {
			$('#htmlCal').html(r);
		}
	});
}
fn_loadEduCal();
</script>

<!--// edu_status_pop //-->
<div class="remodal" data-remodal-id="md-classDormiInfo" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc" id="vm-classDormiInfo">
	<button data-remodal-action="close" class="remodal-close" aria-label="Close"></button>
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC">{{eduDt|fltDt2Str('YYYYMMDD','YYYY.MM.DD (ddd)')}} 숙소 현황</p>
		</div>
		<div class="modal-body">
			<div class="tl dp_b pd15">
				<p class="dp_ib">
					배정<span class="dp_ib pdl5 fc_red">{{prvtCnt}}</span>개
				</p>
			</div>		
			<div class="tbBox mgt0">
				<table class="tc tb02 w100">
					<caption class="sound_only">교육현황 테이블</caption>
					<thead>
						<tr>
							<th>번호</th>
							<th>이름</th>
							<th>날짜</th>
							<th>숙소명</th>
							<th>&nbsp;</th>
						</tr>
					</thead>
					<tbody>
						<tr v-for="(o,i) in list">
							<td>{{i+1}}</td>
							<td>{{o.userNm}}</td>
							<td>{{o.startDt}} ~ {{o.endDt}}</td>
							
							<td>
								{{o.dormiNm}}
								<!--  <button type="button" class="btn04 btn_orange" onclick="">입장</button> -->
							</td>
							<td>
							<!-- 
								<a :href="'javascript:window.open(\'${utcp.ctxPath}/admin/edu/popup/lctreView.do?eduSeq='+o.eduSeq+'\',\'lctrePop\',\'scrollbar=n,width=1024,height=840\');'" class="btn04 btn_blue">신청상세</a>
							 -->
								
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>
<!--// edu_status_pop //-->  
<script>
var vm_classDormiInfo = new Vue({
	el : '#vm-classDormiInfo',
	data : {
		eduDt : '',
		list : [],
	},
	computed : {
		teamCnt : function(){
			return this.list.filter(function(item) {
				//item.cnfmUserNm = 'test';
				 return item.eduSeq > 0;
			}).length;
		},
		prvtCnt : function(){
			return this.list.filter(function(item) {
				//item.cnfmUserNm = 'test';
				 return item.eduSeq > 0;
			}).length;
		},
	},
});
function fn_openClassDormiInfo(eduDt){
	
	vm_classDormiInfo.eduDt = eduDt;
	vm_classDormiInfo.list = [];
	$.ajax({
		data : {eduDt : eduDt},
		url : 'getClassDormiByEduDt.ajax',
		success : function (r){
			if(r.result != 1){
				alert(r.msg);
				return;
			}
			vm_classDormiInfo.list = r.data.list;
			console.log(vm_classDormiInfo.list);
			$('[data-remodal-id=md-classDormiInfo]').remodal().open();
			location.href='#none';
		}
	});
}
</script>