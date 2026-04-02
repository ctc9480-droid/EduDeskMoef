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
					<th>숙소명</th>
					<th>위치</th>
					<th>인실/장애인용</th>
					<th>메모</th>
					<th>현황</th>
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
						<td><a href="classDormiMngReg.do?dormiSeq=${o.dormiSeq }">${o.dormiNm }</a></td>
						<td>${o.place }</td>
						<td>${o.capaCnt }인실
						${o.accessYn eq 'Y'?'(장애인용)':'' }
						</td>
						<td>${o.memo }</td>
						<td><a href="javascript:fn_openCalRoom('${o.dormiSeq }','${o.dormiNm }');" class="btn04 btn_blue">이용현황</a> 
						</td>
					</tr>
				</c:forEach>
				<c:if test="${empty result.data.list  }">
					<tr>
						<td colspan="5">데이터가 없습니다.</td>
					</tr>
				</c:if>
			</tbody>
		</table>
		<div class="fl tc">
			<button class="btn01 btn_orange" onclick="openMdDormiPrice()">가격설정</button>
		</div>
		<div class="fr tc">
			<button class="btn01 btn_greenl" onclick="location.href='classDormiMngReg.do?dormiSeq=0';">등록</button>
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

<!--// edu_status_pop //-->
<input type="hidden" name="srchYear" id="i_srchYear" value="${vo2.srchYear }"> 
<input type="hidden" name="srchMonth" id="i_srchMonth" value="${vo2.srchMonth }"> 
<input type="hidden" name="dormiSeq" id="i_dormiSeq"> 
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
function fn_openCalRoom(dormiSeq,roomNm){
	$('#i_srchYear').val('');
	$('#i_srchMonth').val('');
	$('#area-roomNm').text(roomNm);
	
	$('#i_dormiSeq').val(dormiSeq);
	fn_loadEduCal(dormiSeq);
	$('[data-remodal-id=timeCal]').remodal().open();
	location.href='#none';
}
function fn_loadEduCal(dormiSeq) {
	$.ajax({
		url : "classDormiMngCalInfo.do",
		type : "POST",
		data : {
			"srchYear" : $("#i_srchYear").val(),
			"srchMonth" : $("#i_srchMonth").val(),
			"dormiSeq"	: dormiSeq
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
	var dormiSeq = $("#i_dormiSeq").val();
	
	fn_loadEduCal(dormiSeq);
}
</script>

<div class="remodal" data-remodal-id="md-dormiPrice" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc" id="vm-dormiPrice">
	<button data-remodal-action="close" class="remodal-close" aria-label="Close"></button>
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC">가격설정</p>
		</div>
		<div class="modal-body">
			<div class="tbBox1 mgt0">
				<form id="form-md-dormiPrice">
				<table class="tc w100 tb">
					<caption class="sound_only">가격설정 입력 테이블</caption>
					<thead>
						<tr>
							<th>구분</th>
							<th>2인실</th>
							<th>4인실</th>
							<th>기간</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${price }" var="o">
						<c:choose>
						<c:when test="${o.seasonGb eq 'off' }">
						<tr >
							<th>기본</th>
							<td > <input type="text" name="feeOff2" value="${o.fee2 }" class="ip9 onlyNumber" maxlength="7"/>원 </td>
							<td > <input type="text" name="feeOff4" value="${o.fee4 }" class="ip9 onlyNumber" maxlength="7"/>원 </td>
							<td></td>
						</tr>
						</c:when>
						<c:otherwise>
						<tr >
							<th>성수기</th>
							<td > <input type="text" name="feePeak2" value="${o.fee2 }" class="ip9 onlyNumber" maxlength="7"/>원 </td>
							<td > <input type="text" name="feePeak4" value="${o.fee4 }" class="ip9 onlyNumber" maxlength="7"/>원 </td>
							<td>
							<input type="text" class="datepicker ip5"  name="startDt" value="${o.startDt }" readonly/> ~
							<input type="text" class="datepicker ip5"  name="endDt" value="${o.endDt }" readonly/>
							</td>
						</tr>
						</c:otherwise>
						</c:choose>
						</c:forEach>
					</tbody>
				</table>
				</form>
			</div>
		</div>
		<div class="modal-footer">
			<button onclick="saveDormiPrice()" class="remodal-confirm btn02 btn_green">저장</button>
			<button data-remodal-action="cancel" class="remodal-cancel btn02 btn_orange">취소</button>
		</div>
	</div>
</div>

<script>
$(function() {
	$('.datepicker').datepicker();
	
	$(".onlyNumber").keyup(function(event){      
        var str;                     
        if(event.keyCode != 8){
            if (!(event.keyCode >=37 && event.keyCode<=40)) {
                var inputVal = $(this).val();       
                str = inputVal.replace(/[^0-9]/gi,'');            
                $(this).val(str);             
            }                   
        }
    });
});
function openMdDormiPrice(){
	$('[data-remodal-id=md-dormiPrice]').remodal().open();
}
function saveDormiPrice(){
	var formData = $('#form-md-dormiPrice').serialize();
	$.ajax({
		type: 'post',
		data: formData,
		url: 'savePriceProc.ajax',
		success: function(r){
			if(r.result == 1){
				alert('저장되었습니다.');
				$('[data-remodal-id=md-dormiPrice]').remodal().close();
				location.reload();
			}else{
				alert(r.msg);
				
			}
		}
	});
}
</script>