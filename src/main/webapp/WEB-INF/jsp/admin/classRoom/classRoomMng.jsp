<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>

<section class="pd025 pd200 po_re">
	<div class="po_re br5 bs_box">
		<form name="searchForm" id="searchForm">
			<input type="hidden" id="page" name="page" value="1">
			<div class="tr mgb15">
				<!-- <label for="srchColumn" class="sound_only">검색대상선택</label>
				<select id="srchColumn" name="srchColumn" class="btn04 btn_blackl">
					<option value="userId">아이디</option>
					<option value="userNm">성명</option>
				</select>	 -->
				<label for="srchWrd" class="sound_only">검색어입력</label>
				<input type="text" id="srchWrd" name="srchWrd" value="${param.srchWrd }" placeholder="검색" class="btn04 btn_blackl tl mgr5">
				<button class="btn04 btn_black fr">검색</button>
			</div>
		</form>
		<table class="w100 tb01 tc">
			<caption class="sound_only">교육장소 관리 테이블</caption>
			<thead style="background: #f7f8fa;">
				<tr>
					<th>번호</th>
					<th>장소명</th>
					<th>위치</th>
					<th>메모</th>
					<th>현황</th>
					<th>설정</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${result.data.list }" var="o" varStatus="s">

					<tr>
						<td>
							${result.data.pageNavi.totalRecordCount - (((result.data.pageNavi.currentPageNo-1)*result.data.pageNavi.recordCountPerPage) + s.index) } 
							<%-- 
							${s.count }
							--%> 
						</td>
						<td><a href="classRoomMngReg.do?roomSeq=${o.roomSeq }">${o.roomNm }</a></td>
						<td>${o.place }</td>
						<td>${o.memo }</td>
						<td><a href="javascript:fn_openCalRoom('${o.roomSeq }','${o.roomNm }');" class="btn04 btn_blue">이용현황</a> 
						</td>
						<td><a href="javascript:fn_openMdRoomExcpt(${o.roomSeq });" class="btn04 btn_green">휴무일</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<div class="fr tc">
			<button class="btn01 btn_greenl" onclick="fn_openMdRoomExcpt(0);">전체휴무일</button>
			<button class="btn01 btn_greenl" onclick="location.href='classRoomMngReg.do?roomSeq=0';">등록</button>
		</div>
		<%-- 
		 --%>
		<c:set var="pageNavi" value="${result.data.pageNavi }" />
		<% request.setAttribute("pageNavi", pageContext.getAttribute("pageNavi"));%>
		<jsp:include page="/WEB-INF/jsp/admin/common/paging.jsp" />
	</div>
</section>
<script>
function fn_paging(page){
	$("#page").val(page);
	$("#searchForm").submit();
}
</script>

<div class="remodal" data-remodal-id="md-roomExcpt" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc" id="vm-roomExcpt">
	<button data-remodal-action="close" class="remodal-close" aria-label="Close"></button>
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC">{{roomNm}} 휴무일 입력</p>
		</div>
		<div class="modal-body">
			<div class="tbBox1 mgt0">
				<table class="tc w100 tb">
					<caption class="sound_only">휴무일 입력 테이블</caption>
					<tbody>
						<tr v-for="(o,i) in list">
							<th>휴무일</th>
							<td class="tl">
							<comp-datepicker readonly v-model="o.startDt" :data_max="o.endDt"></comp-datepicker> ~ 
							<comp-datepicker readonly v-model="o.endDt" :data_min="o.startDt"></comp-datepicker> <input type="text" v-model="o.memo" class="ip1 mgr10" />
								<button v-if="list.length > 0" type="button" class="btn04 btn_orange" @click="delExcpt(i)">삭제</button>
								<button v-if="list.length==(i+1)" type="button" class="btn04 btn_blue" @click="addExcpt">추가</button></td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<div class="modal-footer">
			<button @click="saveExcpt" class="remodal-confirm btn02 btn_green">저장</button>
			<button data-remodal-action="cancel" class="remodal-cancel btn02 btn_gray">취소</button>
		</div>
	</div>
</div>
<script>

var roomExcptList0 = {startDt : '', endDt : ''};
var vm_roomExcpt= new Vue({
	el : '#vm-roomExcpt',
	data : {
		roomNm : '',
		roomSeq : 0,
		list : [$.extend(true,{}, roomExcptList0)]
	},
	methods:{
		addExcpt : function(){
			var obj2 = $.extend(true,{}, roomExcptList0);
			this.list.push(obj2);
		},
		delExcpt : function(i){
			if(this.list.length==1){
				this.list[i].startDt='';
				this.list[i].endDt='';
			}else{
				this.list.splice(i,1);
			}
		},
		saveExcpt : function(){
			var listData = this.list;
			var roomSeq = this.roomSeq;
			
			for(i in this.list){
				var excpt = this.list[i];
				if(this.list.length== 1 && excpt.startDt == '' && excpt.endDt == ''){
					listData = [];
					break;	
				}
				if(excpt.startDt == '' || excpt.endDt == ''){
					alert('날짜를 입력하세요');
					return;
				}
				
				
				
				if(excpt.startDt > excpt.endDt){
					alert('종료일이 시작일보다 작은 휴무일이 존재합니다.');
					return;
				}
				//excpt.startDt = excpt.startDt.replace(/-/gi,'');
				//excpt.endDt = excpt.endDt.replace(/-/gi,'');
			}
			//중복기간 체크
			var isOverlap = isOverlapping(this.list);
			if(isOverlap){
				alert('겹치는 기간이 존재합니다.');
				return;
			}
			$.ajax({
				type : 'post',
				url : 'classRoomExcptProc.ajax',
				data : {roomSeq : roomSeq, dataJson : JSON.stringify({listData:listData})},
				success : function (r){
					if(r.result == 1){
						$('[data-remodal-id=md-roomExcpt]').remodal().close();
					}
				}
			});
		},
	},
});
function isOverlapping(periods) {
    for (var i = 0; i < periods.length; i++) {
    	console.log(periods[i].startDt);
        if (!periods[i].startDt || !periods[i].endDt) continue; // 빈 값 건너뛰기

        var startA = new Date(periods[i].startDt);
        var endA = new Date(periods[i].endDt);

        for (var j = i + 1; j < periods.length; j++) {
            if (!periods[j].startDt || !periods[j].endDt) continue; // 빈 값 건너뛰기

            var startB = new Date(periods[j].startDt);
            var endB = new Date(periods[j].endDt);

            if (startA <= endB && startB <= endA) {
                return true; // 겹치는 기간이 있음
            }
        }
    }
    return false; // 겹치는 기간이 없음
}

function fn_openMdRoomExcpt(roomSeq){
	$.ajax({
		url : 'classRoomExcptInfo.ajax',	
		data : {roomSeq : roomSeq},
		success : function (r){
			if(r.result != 1){
				return;
			}
			
			vm_roomExcpt.roomSeq = roomSeq;
			vm_roomExcpt.roomNm = '전체';
			if(r.data.room){
				vm_roomExcpt.roomNm = r.data.room.roomNm;
			}
			vm_roomExcpt.list = [$.extend(true,{}, roomExcptList0)];
			if(r.data.list.length > 0){
				vm_roomExcpt.list = r.data.list;
				for(var i in vm_roomExcpt.list){
					var o = vm_roomExcpt.list[i];
					var sDt = o.startDt;
					var eDt = o.endDt;
					console.log();
					o.startDt = sDt.substring(0,4)+'-'+sDt.substring(4,6)+'-'+sDt.substring(6,8);
					o.endDt = eDt.substring(0,4)+'-'+eDt.substring(4,6)+'-'+eDt.substring(6,8);
				}
			}
			
			
			$('[data-remodal-id=md-roomExcpt]').remodal().open();
			location.href='#none';
		}
	});
	
}

</script>

<!--// edu_status_pop //-->
<input type="hidden" name="srchYear" id="i_srchYear" value="${vo2.srchYear }"> 
<input type="hidden" name="srchMonth" id="i_srchMonth" value="${vo2.srchMonth }"> 
<input type="hidden" name="roomSeq" id="i_roomSeq"> 
<div class="remodal cal" data-remodal-id="timeCal" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc">
	<button data-remodal-action="close" class="remodal-close" aria-label="Close"></button>
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC pdt0"><span id="area-roomNm">강의실</span> 이용 및 예약현황</p>
		</div>
		<div class="modal-body">
			<div id="htmlCal">
				
			</div>
		</div>
	</div>
</div>
<!--// edu_status_pop //-->

<script>
function fn_openCalRoom(roomSeq,roomNm){
	$('#i_srchYear').val('');
	$('#i_srchMonth').val('');
	$('#area-roomNm').text(roomNm);
	
	$('#i_roomSeq').val(roomSeq);
	fn_loadEduCal(roomSeq);
	$('[data-remodal-id=timeCal]').remodal().open();
	location.href='#none';
}
function fn_loadEduCal(roomSeq) {
	$.ajax({
		url : "classRoomMngCalInfo.do",
		type : "POST",
		data : {
			"srchYear" : $("#i_srchYear").val(),
			"srchMonth" : $("#i_srchMonth").val(),
			"roomSeq"	: roomSeq
		},
		cache : false,
		async : true,
		success : function(r) {
			$('#htmlCal').html(r);
		}
	});
}
function fn_moveMonth2(command) {
	var year;
	var month;
	var action;
	if($('#i_srchYear').val() == ''){
		$('#i_srchYear').val('${vo2.srchYear}')
	}
	if($('#i_srchMonth').val() == ''){
		$('#i_srchMonth').val('${vo2.srchMonth}')
	}
	if (command == "P") {
		if ($("#i_srchMonth").val() == "01") {
			year = Number($("#i_srchYear").val()) - 1;
			month = 12;
		} else {
			year = $("#i_srchYear").val();
			month = Number($("#i_srchMonth").val()) - 1;
		}
	} else {
			console.log($("#i_srchYear").val());
		if ($("#i_srchMonth").val() == "12") {
			console.log($("#i_srchYear").val());
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
	var roomSeq = $("#i_roomSeq").val();
	
	fn_loadEduCal(roomSeq);
}
</script>