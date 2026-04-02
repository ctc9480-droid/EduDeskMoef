<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<script type="text/javascript">

	function fn_moveMonth(command) {
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
			//action = "/user/edu/eduCal.do?srchYear=" + year + "&srchMonth=0" + month;
			$("#i_srchMonth").val('0' + month);
		} else {
			//action = "/user/edu/eduCal.do?srchYear=" + year + "&srchMonth=" + month;
			$("#i_srchMonth").val(month);
		}
		//location.href = action;
		$("#i_srchYear").val(year);
		fn_loadEduCal();
	}

	function fn_moveList() {
		location.href = "/user/edu/eduList.do?srchYear=" + $("#i_srchYear").val() + "&srchMonth=" + $("#i_srchMonth").val()
		+'&srchCtgry='+$("#i_srchCtgry").val()+'&srchCtgry2='+$("#i_srchCtgry2").val()+'&srchAgency='+$("#i_srchAgency").val()+'&srchColumn='+$("#i_srchColumn").val()+'&srchWrd='+$("#i_srchWrd").val()
		+'&srchDate='+$('#i_srchDate').val();
	}


</script>
<style>
@media(max-width:768px) {
.navBot ul li {
width:50%; 
} 
}
</style>
<input type="hidden" name="srchYear" id="i_srchYear" value="${vo.srchYear}"> 
<input type="hidden" name="srchMonth" id="i_srchMonth" value="${vo.srchMonth}"> 
<input type="hidden" name="srchDate" id="i_srchDate" value="${vo.srchDate}"> 
<input type="hidden" name="srchCtgry" id="i_srchCtgry" value="${vo.srchCtgry}" /> 
<input type="hidden" name="srchCtgry2" id="i_srchCtgry2" value="${vo.srchCtgry2}" /> 
<input type="hidden" name="srchAgency" id="i_srchAgency" value="${vo.srchAgency}" /> 
<input type="hidden" name="srchColumn" id="i_srchColumn" value="${vo.srchColumn}" /> 
<input type="hidden" name="srchWrd" id="i_srchWrd" value="${vo.srchWrd}" />
<div class="eduWrap cf">
	
	<div class="listWrap listWrapTop listWrapBox">
		<div class="box_sort flex">
			<div class="box_cate">
			    <%-- 
			    --%>
			    <span>
			        <label for="srchCtgry" class="sound_only">교육카테고리 선택</label>
			        <select id="srchCtgry" class="srchCtgry">
			            <option value="-1">교육과정</option>
			            <c:forEach var="data" items="${cateList}" varStatus="stat">
			                <option value="${data.ctgrySeq}" <c:if test='${vo.srchCtgry == data.ctgrySeq}'>selected</c:if>>${data.ctgryNm}</option>
			            </c:forEach>
			        </select>
			    </span> 
			    <input type="hidden" id="srchCtgry" value="${vo.srchCtgry }"/>
			    <span id="vm-detailCtgry">
			        <label for="srchCtgry2" class="sound_only">2차과정 전체</label>
			        <select id="srchCtgry2" class="srchCtgry">
			            <option value="-1">2차과정 전체</option>
			        </select>
			    </span>
			    <span id="vm-detailCtgry2" style="">
			        <label for="srchCtgry3" class="sound_only">3차과정 전체</label>
			        <select id="srchCtgry3" class="srchCtgry">
			            <option value="-1">3차과정 전체</option>
			        </select> 
			    </span>
			</div>
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
			            url: '${utcp.ctxPath}/comm/api/callCategory.json',
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
			                value: o.ctgrySeq,
			                text: o.ctgryNm
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
			            url: '${utcp.ctxPath}/comm/api/callCategory.json',
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
			                value: o.ctgrySeq,
			                text: o.ctgryNm
			            }));
			        });
			
			        select2 = '${vo.srchCtgry3 > 0?vo.srchCtgry3:-1}';
			        $('#srchCtgry3').val(select2);
			    }
			
			    fn_callCategory2('${vo.srchCtgry2}');
			});
			</script>
			<div class="box_search">
				
				<input type="text" id="srchWrd" value="${vo.srchWrd }"/>
				<button type="submit" onclick="fn_srch(); return false;">검색</button>
			</div>
		</div>
	</div>
</div>
<div id="htmlCal" class="htmlCal">

</div>
	<!-- /. wrap -->
<script> 
function fn_loadEduCal() {
	$.ajax({
		url : "/user/edu/eduCal2.do",
		type : "POST",
		data : {
			"srchYear" : $("#i_srchYear").val(),
			"srchMonth" : $("#i_srchMonth").val(),
			"srchCtgry" : $("#i_srchCtgry").val(),
			"srchCtgry2" : $("#i_srchCtgry2").val(),
			"srchAgency" : $("#i_srchAgency").val(),
			"srchColumn" : $("#i_srchColumn").val(),
			"srchWrd" : $("#i_srchWrd").val(),
			"srchSidoCd" : $("#srchSidoCd").val(),
		},
		cache : false,
		async : true,
		success : function(r) {
			$('#htmlCal').html(r);
		}
	});
}
function fn_srch(){
	$("#i_srchCtgry").val($("#srchCtgry").val());
	$("#i_srchCtgry2").val($("#srchCtgry2").val());
	$("#i_srchCtgry3").val($("#srchCtgry3").val());
	$("#i_srchAgency").val($("#srchAgency").val());
	$("#i_srchColumn").val($("#srchColumn").val());
	$("#i_srchWrd").val($("#srchWrd").val());
	$("#i_srchYear").val($("#srchYear").val());
	$("#i_eduAgency").val($("#eduAgency").val());
	fn_loadEduCal();
}
function fn_more(dateStr){
	$("#i_srchDate").val(dateStr);
	fn_moveList();
}
fn_loadEduCal();

$(document).ready(function(){
    $('#srchCtgry').change(function(){
		fn_srch();
	});
	$('#srchCtgry2').change(function(){
		fn_srch();
	});
});
</script>