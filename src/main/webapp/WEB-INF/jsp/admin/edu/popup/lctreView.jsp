<%@page import="com.educare.component.VarComponent"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<jsp:useBean id="curDate" class="java.util.Date" />
<script src="${utcp.ctxPath}/resources/admin/js/wf_loading.js"
	type="text/javascript"></script>
<link href="${utcp.ctxPath}/resources/admin/css/wf_loading.css"
	rel="stylesheet" type="text/css" />
<script src="${utcp.ctxPath}/resources/ckeditor4_full/ckeditor.js"></script>

<!-- 
<script src="${utcp.ctxPath}/resources/user/js/moment.js" type="text/javascript"></script>
 -->

<script type="text/javascript">
$(function(){
	window.resizeTo(1477, 940);
	if('${param.tab}' != ''){
		fn_tab('${param.tab}');
	}else{
		fn_tab('5');
	}
	
	$("#checkAllUser").click(function() {
		if($("#checkAllUser").is(":checked") == true){
			$("input[name=bulkUserIds]:checkbox").each(function() {
				$(this).prop("checked", true);
			});	
		}else{
			$("input[name=bulkUserIds]:checkbox").each(function() {
				$(this).prop("checked", false);
			});
		}
	});
	
	$("#chkUserAll").click(function() {
		if($("#chkUserAll").is(":checked") == true){
			$("input[name=userIds]:checkbox").each(function() {
				if($(this).attr("class") == "chkAble"){
					$(this).prop("checked", true);
				}
			});	
		}else{
			$("input[name=userIds]:checkbox").each(function() {
				$(this).prop("checked", false);
			});
		}
	});
	
	
	
	$('#tab2Row').change(function(){
		$("#tab2Page").val("1");
		fn_htmlWrite("2");
	});
	
	//tab3은 vue가 적용되어 이벤트가 동작 안하는것 같다. 별도 이벤트에 등록함,220316
	
	$('#tab4Row').change(function(){
		$("#tab4Page").val("1");
		fn_htmlWrite("4");
	});
	
	$(document).on("click","input[name='userIds']",function(){
		if($(this).attr("class") != "chkAble"){
			$(this).prop("checked", false);
		}
	});
	
	$(document).on("change","select[class='atend']",function(){
		var userId = $(this).attr("id").replace("atend_", "");
		var atendYn = $(this).val();
		var workshopScore = $("#work_" + userId).val();
		var examScore = $("#exam_" + userId).val();
		
		fn_scoreUpd(userId, atendYn, workshopScore, examScore);
	});
	
	var cnt = 0;
	var size = 0;
	
	<c:if test='${attachList01 != null && fn:length(attachList01) > 0}'>
		<c:forEach var="attach" items="${attachList01}" varStatus="stat">
			cnt += 1;
			size += Number(${attach.fileSize});
		</c:forEach>
		$("#f_cnt_01").html(cnt);
		$("#f_size_01").html(Math.floor(size / 1000));
	</c:if>
	cnt = 0;
	size = 0;
	<c:if test='${attachList02 != null && fn:length(attachList02) > 0}'>
		<c:forEach var="attach" items="${attachList02}" varStatus="stat">
			cnt += 1;
			size += Number(${attach.fileSize});
		</c:forEach>
		$("#f_cnt_02").html(cnt);
		$("#f_size_02").html(Math.floor(size / 1000));
	</c:if>
	

});
function fn_stdntChange(obj){

	var userId = $(obj).attr("id").replace("work_", "");
	var atendYn = $("#atend_" + userId).val();
	var workshopScore = $("#work_" + userId).val();
	var examScore = $("#exam_" + userId).val();
	var attCnt = $("#exam_" + userId).val();
	
	fn_scoreUpd(userId, atendYn, workshopScore, examScore);
}

function fn_scoreUpd(userId, atendYn, workshopScore, examScore){
	$.ajax({
        url: "${utcp.ctxPath}/admin/edu/updScore.json",
        type: "POST",
        traditional : true,
        data: {
        	"eduSeq" : ${lctre.eduSeq}, 
        	"userId" : userId, 
        	"atendYn" : atendYn, 
        	"workshopScore" : workshopScore, 
        	"examScore" : examScore
        },
        cache: false,
		async: true,
        success: function(r) {
			if(r.isAdmin){
				fn_htmlWrite("3");
			}else{
				location.href = "#authMessage";
			}
		}
    });
}
function fn_tab(num){
	$("li[id^=tab-]").removeClass('active');
	$("#tab-"+num).addClass('active');
	fn_tabCont(num);
}
function fn_tabCont(num){
	$("[id^=tabCon-]").hide();
	$("#tabCon-"+num).show();
	fn_htmlWrite(num);
}
function fn_htmlWrite(num){
	if(num == "2"){
		call2RceptList();
	}else if(num == "3"){
		var page = $("#tab3Page").val();
		var row = $("#tab3Row").val();
		var srchWrd = $("#tab3SrchWrd").val();
		var srchColumn = $("#tab3SrchColumn").val();
		
		$.ajax({
			url: "${utcp.ctxPath}/user/edu/lectureStdntList.json",
			type: "post",
			data: {
				"page" : page,
				"row" : row,
				"srchWrd" : srchWrd,
				"srchColumn" : srchColumn,
				"eduSeq" : $('#eduSeq').val()
			},
			dataType: 'json',
		    crossDomain: true,
			success: function(r) {
				
				if(r.isAdmin){
					vm_3.totalCnt=r.totalCnt;
					vm_3.lectTimeCnt=r.lectTimeCnt;
					vm_3.passCnt=r.passCnt;
					vm_3.stdntList=r.dataList;
					vm_3.page=r.pageNavi;
					vm_3.pageList=[];
					vm_3.testCnt=r.testCnt;
					vm_3.test2Cnt=r.test2Cnt;
					for(var i=r.pageNavi.firstPageNoOnPageList;i<=r.pageNavi.lastPageNoOnPageList;i++){
						vm_3.pageList.push(i);
					}
				}else{
					location.href = "#authMessage";
				}
				
			}
		});
	}else if(num == "4"){
		vm_4.callList();
	}else if(num == "5"){
		vm_5.callTimeList();
	}else if(num == "6"){
		callEduBbsListData();
	}else if(num == "7"){
		vm_7.callListView(1);
	}else if(num == "8"){
		call8EduBbsListData();
	}else if(num == "9"){
		call9LcrcpList();
	}else if(num == '10'){
		vm_10.callListView(1);
	}else if(num == '11'){
		vm_11.getStdntList();
	}
}

function fn_pageTab2(page){
	$("#tab2Page").val(page);
	fn_htmlWrite("2");
}

function fn_pageTab3(page){
	$("#tab3Page").val(page);
	fn_htmlWrite("3");
}

function fn_pageTab4(page){
	$("#tab4Page").val(page);
	fn_htmlWrite("4");
}

function fn_moveList(){
	location.href = "${utcp.ctxPath}/admin/edu/lctreList.do";
}

function fn_copyRgs(){
	location.href = "${utcp.ctxPath}/admin/edu/popup/lctreCopy.do?copyEduSeq=${lctre.eduSeq}";
}

function fn_upd(){
	location.href = "${utcp.ctxPath}/admin/edu/popup/lctreRgs.do?eduSeq=${lctre.eduSeq}";
}



function popClose(num){
	fn_htmlWrite(num);
	location.href = "#";
}

function popClose2(num){
	$(".closeAble").hide();
	$("#closeBtn2").html(
		"<button class=\"btn01 btn_orangel closeDisable\">마감완료</button>"		
	);
	location.href = "#";
}

function popClose3(num, command){
	if(command == "1"){
		/* $("#closeBtn3").html(
			"<button class=\"btn01 btn_green\">엑셀 다운로드</button> " + 
			"<button class=\"btn01 btn_orangel\" onclick=\"fn_closeCancel(); return false;\">종료 취소</button>"
		); */
		$("#closeYn").val("Y");
		vm_3.closeYn='Y';
	}else{
		/* $("#closeBtn3").html(
			"<button class=\"btn01 btn_green\">엑셀 다운로드</button> " + 
			"<button class=\"btn01 btn_orange\" onclick=\"fn_closeConfirm(); return false;\">교육 종료</button>"
		); */
		$("#closeYn").val("N");
		vm_3.closeYn='N';
	}
	popClose(num);
}

function fn_rceptClose(){
	var userIds = new Array();
	var i = 0;
	
	//if($("input[name='userIds']:checked").length == 0){
		//$("#messageTab2").html("선택된 신청자가 없습니다.");
		//location.href = "#messageTab2";
		//return;
	//}
	location.href = "#rceptClose";
}

function fn_rceptCloseProc(){
	/*
	var userIds = new Array();
	var i = 0;
	
	if($("input[name='userIds']:checked").length == 0){
		$("#messageTab2").html("선택된 신청자가 없습니다.");
		location.href = "#messageTab2";
		return;
	}else{
		$("input[name=userIds]:checked").each(function() {
			userIds[i] = $(this).val();
			i++;
		});
	}
	*/
	isloading.start();
	$.ajax({
        url: "${utcp.ctxPath}/admin/edu/rceptClose.json",
        type: "POST",
        traditional : true,
        data: {
        	"eduSeq" : $('#eduSeq').val()
        	//,"userIds" : userIds
        },
        cache: false,
		async: true,
        success: function(r) {
        	isloading.stop();
        	$("input[name=userIds]:checkbox").each(function() {
				$(this).prop("checked", false);
			});
        	$("#chkUserAll").prop("checked", false);
        	
			if(r.isAdmin){
				if(r.rtnMsg!=''){
					alert(r.rtnMsg);
					return;	
				}
				location.href = "#successClose2";
			}else{
				location.href = "#authMessage";
			}
		}
    });
}

function fn_cancel(){
	<c:if test="${lctre.fee>0}">
	//alert('유료교육은 관리자가 취소 할 수 없습니다.');return;
	</c:if>
	var userIds = new Array();
	var i = 0;
	
	if($("input[name='delUserIds']:checked").length == 0){
		$("#messageTab3").html("선택된 수강생이 없습니다.");
		location.href = "#messageTab3";
		return;
	}
	location.href = "#stdntCancel";
}

function fn_cancelProc(){
	var userIds = new Array();
	var i = 0;
	
	if($("input[name='delUserIds']:checked").length == 0){
		$("#messageTab3").html("선택된 수강생이 없습니다.");
		location.href = "#messageTab3";
		return;
	}else{
		$("input[name=delUserIds]:checked").each(function() {
			userIds[i] = $(this).val();
			i++;
		});
	}
	isloading.start();
	$.ajax({
        url: "${utcp.ctxPath}/admin/edu/eduStdntCancel.json",
        type: "POST",
        traditional : true,
        data: {
        	"eduSeq" : $('#eduSeq').val(), 
        	"userIds" : userIds
        },
        cache: false,
		async: true,
        success: function(r) {
        	isloading.stop();
        	$("input[name=delUserIds]:checkbox").each(function() {
				$(this).prop("checked", false);
			});
        	$("#chkStdntAll").prop("checked", false);
        	
			if(r.isAdmin){
				location.href = "#successCancel3";
			}else{
				location.href = "#authMessage";
			}
		}
    });
}

function fn_closeCancel(){
	location.href = "#eduCloseCancel";
}

function fn_closeConfirm(){
	location.href = "#eduClose";
}

function fn_eduCloseProc(){
	isloading.start();
	$.ajax({
        url: "${utcp.ctxPath}/admin/edu/eduClose.json",
        type: "POST",
        traditional : true,
        data: {
        	"eduSeq" : $('#eduSeq').val()
        },
        cache: false,
		async: true,
        success: function(r) {
        	isloading.stop();
			if(r.isAdmin){
				$("#successClose3").html(r.result);
				location.href = "#successClose3";
			}else{
				location.href = "#authMessage";
			}
		}
    });
}

function fn_eduCloseCancelProc(){
	isloading.start();
	$.ajax({
        url: "${utcp.ctxPath}/admin/edu/eduCloseCancel.json",
        type: "POST",
        traditional : true,
        data: {
        	"eduSeq" : $('#eduSeq').val()
        },
        cache: false,
		async: true,
        success: function(r) {
        	isloading.stop();
			if(r.isAdmin){
				location.href = "#successEduCancel3";
			}else{
				location.href = "#authMessage";
			}
		}
    });
}



function fn_serveyView(userId){
	javascript:window.open('${utcp.ctxPath}/admin/feedback/feedbackPopup.do?eduSeq=${lctre.eduSeq}&userId='+userId,'feedbackPopup','resizable=no');
}

var maxCnt = 10;
var maxSize = 100000000;

function fn_uploadPop(eduSeq,fileSection){
	var totalCnt = Number($("#f_cnt_"+fileSection).text());
	var totalSize = Number($("#f_size_"+fileSection).text()) * 1000;
	fn_uploadInit(totalCnt, totalSize, maxCnt, maxSize, eduSeq, fileSection);
}

function fn_uploadSuccess(fileSeq, fileRename, fileOrg, fileType, fileSize, totalCnt, totalSize,fileSection){
	$("#f_cnt_"+fileSection).html(totalCnt);
	$("#f_size_"+fileSection).html(totalSize);
	$("#fileBox_"+fileSection).append( "<option value=\"" + fileSeq + "\">" + fileOrg + "</option>" );
}

function fn_fileDel(eduSeq,fileSection){

	var num = $( "#fileBox_"+fileSection+" option" ).index( $( "#fileBox_"+fileSection+" option:selected" ) );
	if( num == -1 ) {
		$("#messageStr").html("선택된 파일이 없습니다.");
		location.href = "#message";
		$("#fileBox_"+fileSection).focus();
	} else {
		var totalCnt = Number($("#f_cnt").text());
		var totalSize = Number($("#f_size").text());
		var fileSeq = $("select[id='fileBox_"+fileSection+"'] option:selected").val();
		
		$.ajax({
	        url: "${utcp.ctxPath}/admin/edu/attachDelete.json",
	        type: "POST",       
	        data: { 
	        	"eduSeq" : eduSeq, 
	        	"fileSeq" : fileSeq 
	        },               
	        cache: false,
			async: true,
	        success: function(r) {
				if(r.isSuccess){
					$("#f_cnt").html(totalCnt - 1);
					$("#f_size").html(totalSize - Math.floor(Number(r.fileSize) / 1000));
					$("select[id='fileBox_"+fileSection+"'] option:selected").remove();		
				}else{
					$("#messageStr").html(r.message);
					location.href = "#message";
				}
			}
	    });
	}
}

function fn_fileDown(eduSeq,fileSection){
	var fileSeq = $("select[id='fileBox_"+fileSection+"'] option:selected").val();
	var num = $( "#fileBox_"+fileSection+" option" ).index( $( "#fileBox_"+fileSection+" option:selected" ) );
	if( num == -1 ) {
		$("#messageStr").html("다운로드 할 파일을 선택하세요.");
		location.href = "#message";
		$("#fileBox_"+fileSection+"").focus();
	}else{
		location.href = "${utcp.ctxPath}/user/edu/attach/" + fileSeq + "/download.do"	
	}
}



function fn_excelStdnt(){
	location.href = "${utcp.ctxPath}/admin/excel/stdntExcel.do?eduSeq=${lctre.eduSeq}";
}
function fn_excelStdnt2(){
	location.href = "${utcp.ctxPath}/admin/excel/totalOnline.do?eduSeq=${lctre.eduSeq}";
}







function fn_online_history_excel(eduSeq,timeSeq){
	location.href='${utcp.ctxPath}/admin/excel/onlineHistory.do?eduSeq='+eduSeq+'&timeSeq='+timeSeq;
}

function fn_rceptCloseOnly(){
	$.ajax({
        url: "${utcp.ctxPath}/admin/edu/rceptCloseOnly.ajax",
        type: "POST",
        data: {
        	"eduSeq" : $('#eduSeq').val()
        },
        success: function(r) {
			if(r.result==1){
				fn_tab(2);
			}
		}
    });
}
function fn_rceptOpenOnly(){
	$.ajax({
        url: "${utcp.ctxPath}/admin/edu/rceptOpenOnly.ajax",
        type: "POST",
        data: {
        	"eduSeq" : $('#eduSeq').val()
        },
        success: function(r) {
			if(r.result==1){
				fn_tab(2);
			}
		}
    });
}
function fn_createQrAttend(qrType){
	if(!confirm('기존 QR이미지는 사용할 수 없습니다. 새로 생성하시겠습니까?')){
		return;
	}
	$.ajax({
		type:'post',
		data:{eduSeq:$('#eduSeq').val(), qrType: qrType},
		url:'${utcp.ctxPath}/admin/ajax/createQrAttend.ajax',
		success:function(r){
			console.log(r);
			if(r.result == 1){
				location.href='lctreView.do?eduSeq=${param.eduSeq}&tab=1';
			}
		}
	});
}



</script>

<input type="hidden" name="eduSeq" id="eduSeq" value="${lctre.eduSeq }" />
<input type="hidden" name="checkYn" id="checkYn"
	value="${lctre.checkYn}" />
<div class="listWrap pdt0 mypopWrap">
	<div id="listDiv">
		<div class="box_list">
			<ul id="lctreView">
				<li class="flex room">
					<div class="roomTit cf fs_13">
						강의실 <a href='javascript:window.close();'
							class="roomOut fc_yellow fr">강의실나가기<i
							class="fa-solid fa-x pdl3"></i></a>
					</div>
					<div class="img_logo mgt10">
						<c:choose>
							<c:when test='${lctre.imgUseYn == "Y" && lctre.imgRename != ""}'>
								<img
									src="<spring:eval expression="@prop['cloud.cdn.url']"/>/upload/web/lctreThum/${lctre.imgRename}?${utcp.convDateToStr(lctre.updDe,'yyyyMMddHHmmss')}"
									alt="교육이미지" />
							</c:when>
							<c:otherwise>
								<img
									src="${utcp.ctxPath}/resources/admin/images/default_img.png?${utcp.convDateToStr(lctre.updDe,'yyyyMMddHHmmss')}"
									alt="<spring:eval expression="@prop['service.name']"/>" />
							</c:otherwise>
						</c:choose>
					</div>
					<div class="text_title">
						<div class="cp">
							<p class="eduTypeWrap">
								<span class="eduLabel"> | <c:if test="${lctre.fee > 0 }">
										<b class="price pay">유료</b>
									</c:if> <c:if test="${lctre.fee == 0 }">
										<b class="price free">무료</b>
									</c:if>
								</span> | <span class="eduType"> ${lctre.ctg1Nm} >
									${lctre.ctg2Nm } <c:if test="${not empty lctre.ctg3Nm }">
	                             > ${lctre.ctg3Nm }
	                             </c:if>
								</span> | <span class="eduType1">[${lctre.lctreTypeNm }]</span>
							</p>

							<p class="eduSbj">
								<a
									href="javascript:window.open('${utcp.ctxPath}/admin/edu/popup/lctreView.do?eduSeq=${lctre.eduSeq}','lctrePop','scrollbar=n,width=1024,height=840');">
									<span class="Sbj fw_500">${lctre.eduNm} </span>
								</a> ${lctre.addStatusBox }
							</p>
							<p class="eduStnt">
								대상 : <span class="Stnt"> <span>${lctre.addTargetsStr }</span>
								</span>
							</p>
						</div>
					</div>
					<div class="text_info">
						<span class="dp_b font_14">[접수] <c:choose>
								<c:when test="${lctre.rceptPeriodYn eq 'Y' }">
						${utcp.convDateToStr(utcp.convStrToDate(lctre.rceptPeriodBegin,'yyyyMMddHHmm'),'yyyy-MM-dd') } ~ ${utcp.convDateToStr(utcp.convStrToDate(lctre.rceptPeriodEnd,'yyyyMMddHHmm'),'yyyy-MM-dd') }
						</c:when>
								<c:otherwise>미설정</c:otherwise>
							</c:choose>
						</span> <span class="dp_b font_14">[교육] ${lctre.eduPeriodBegin } ~
							${lctre.eduPeriodEnd }</span> <span class="dp_b font_14">[인원] <c:choose>
								<c:when test="${lctre.personnel eq '0' }">
									무제한
									</c:when>
								<c:otherwise>
									${lctre.personnel}명 모집
									</c:otherwise>
							</c:choose> (<strong class="fc_red fw_400">${lctre.rceptCnt}</strong>명 신청) <%-- / <span>대기접수 <strong class="fc_red">${lctre.extPersonnel}</strong>명 가능
								</span> --%>
						</span>
					</div>
					<div class="dp_b text_progress bg_darkblue vm">
						<div class="fl">
							<span class="edudtType4"> 출석률 : <b class="fc_orange">${attRatio }</b>%
							</span>
							<%-- 
							<span class="edudtType5"> 상태 : <b class="fc_yellow">${lctre.addStatusNm }</b>
							</span>
							 --%>
						</div>
						<div class="fr tr">
							<c:choose>
								<c:when test="${lctre.lctreType == 0 || lctre.lctreType == 2 }">
									<button onclick="fn_openCheckList2(${param.eduSeq})"
										type="button" class="btn04 btn_whitel attBookPopBtn">출석부</button>
								</c:when>
								<c:otherwise>
									<button onclick="fn_openCheckList(${param.eduSeq})"
										type="button" class="btn04 btn_whitel attBookPopBtn">출석부</button>
								</c:otherwise>
							</c:choose>
							<!-- 
                            <button type="button" class="btn04 btn_whitel">단체문자</button>
							 -->
						</div>
					</div>
				</li>
			</ul>
		</div>

		<!--// board_tab_onoff //-->
		<div class="board_tab_onoff tapWrap">

			<!--// board_tab //-->
			<ul class="board_tab">
				<li id="tab-5">
					<p>
						<a href="javascript:;" onclick="fn_tab('5'); return false;">교육시간표</a>
					</p>
				</li>
				<li id="tab-1">
					<p>
						<a href="javascript:;" onclick="fn_tab('1'); return false;">교육상세</a>
					</p>
				</li>
				<li id="tab-2">
					<p>
						<a href="javascript:;" onclick="fn_tab('2'); return false;">신청자</a>
					</p>
				</li>
				<li id="tab-11">
					<p>
						<a href="javascript:;" onclick="fn_tab('11'); return false;">식비/숙소배정</a>
					</p>
				</li>
				<li id="tab-3">
					<p>
						<a href="javascript:;" onclick="fn_tab('3'); return false;">교육현황/출결</a>
					</p>
				</li>
				<li id="tab-6">
					<p>
						<a href="javascript:;" onclick="fn_tab('6'); return false;">학습자료실</a>
					</p>
				</li>
				<!-- <li id="tab-8">
					<p><a href="javascript:;" onclick="fn_tab('8'); return false;">커뮤니티</a></p>
				</li> -->
				<li id="tab-7">
					<p>
						<a href="javascript:;" onclick="fn_tab('7'); return false;">설문
							관리</a>
					</p>
				</li>
				<li id="tab-10">
					<p>
						<a href="javascript:;" onclick="fn_tab('10'); return false;">시험관리</a>
					</p>
				</li>
				<li id="tab-4">
					<p>
						<a href="javascript:;" onclick="fn_tab('4'); return false;">증빙서</a>
					</p>
				</li>

			</ul>
			<!--// board_tab //-->
			
			<!--// board_tab_con //-->
			<div class="board_tab_con">

				<!--// tab_con1 //-->
				<div class="cont" id="tabCon-1">
					<div class="fr tc ictre-btn-top">
						<%-- 수정,삭제 가능시기/수정가능항목 등 협의 필요 잠시 보류 --%>
						<button class="btn02 btn_green" onclick="fn_upd(); return false;">수정</button>
						<!--  
						-->
						<button class="btn01 btn_greenl" onclick="fn_copyRgs(); return false;">복사 등록</button>
					</div>
					<table class="w100 tb04 tc">
						<colgroup>
							<col style="width: 200px;">
							<col>
						</colgroup>
						<tbody>
							<%-- 							<tr>
								<th class="tc tdbg3">접수 기간</th>
								<td class="tl" colspan="3"><c:choose>
										<c:when test='${lctre.rceptPeriodYn == "Y"}'>
											<fmt:parseDate value="${lctre.rceptPeriodBegin}" var="rceptBegin" pattern="yyyyMMddHHmm" />
											<fmt:parseDate value="${lctre.rceptPeriodEnd}" var="rceptEnd" pattern="yyyyMMddHHmm" />
											<fmt:formatDate value="${rceptBegin}" pattern="yyyy년 MM월 dd일 HH:mm" /> ~ 
											<fmt:formatDate value="${rceptEnd}" pattern="yyyy년 MM월 dd일 HH:mm" />
										</c:when>
										<c:otherwise>
					                		없음
					                	</c:otherwise>
									</c:choose>
								</td>
							</tr> --%>
							<c:if
								test="${not empty lctre.eduYear or not empty lctre.eduTerm }">
								<tr>
									<th class="tc tdbg3">학년도</th>
									<td class="tl"><c:if test="${not empty lctre.eduYear }">
								${lctre.eduYear}년
								</c:if> <c:if test="${not empty lctre.eduTerm }">
								제 ${vcp.getEduTermNm(lctre.eduTerm)}기
								</c:if></td>
								</tr>
							</c:if>
							<c:if test="${not empty lctre.instrctrId }">
								<tr>
									<th class="tc tdbg3">강사</th>
									<td class="tl">${lctre.instrctrNm}</td>
								</tr>
							</c:if>


							
							<tr>
								<th class="tc tdbg3">교육대상</th>
								<td class="tl">${lctre.addTargetsStr }<c:if
										test="${lctre.eduTp > 0  }">
									 (${lctre.addEduTpNm })
									</c:if>
								</td>
							</tr>
							<tr>
								<th class="tc tdbg3">교육 금액</th>
								<td class="tl">
								<c:if test='${lctre.fee != 0}'>
									유료
									</c:if>
									<c:if test='${lctre.fee == ""}'>
									무료
									</c:if></td>
							</tr>
							<!-- 
							<tr>
								<th class="tc tdbg3">납부정보</th>
								<td class="tl">
								식비 : ${lctre.mealFee }원  (계좌 : ${lctre.mealAccount })<br/>
								숙박비 : ${lctre.dormiFee }원 (계좌 : ${lctre.dormiAccount })<br/>
								납부기한 : ${lctre.depositLimitDt}
									</td>
							</tr>
							 -->
							
							<tr>
								<th class="tdbg3 tc">수료증 사용여부</th>
								<td class="tl">
									<c:choose>
									<c:when test="${lctre.passCertYn eq 'Y' }">
									사용
									<b>${lctre.passCertNm eq null?'': ( '('+= lctre.passCertNm+=')') }</b>
									</c:when>
									<c:otherwise>
									수료증 사용 안함
									</c:otherwise>
									</c:choose>
								</td>
							</tr>
<!-- 							<tr> -->
<!-- 								<th class="tdbg3 tc">만족도조사 사용유무</th> -->
<!-- 								<td class="tl"> -->
<%-- 								<c:choose> --%>
<%-- 									<c:when test="${lctre.fbYn eq 'Y' }"> --%>
<!-- 									사용 -->
<%-- 								<b>${lctre.fbTitle eq null?'': ( '('+= lctre.fbTitle+=')') }</b> --%>
<%-- 									</c:when> --%>
<%-- 									<c:otherwise> --%>
<!-- 									만족도 사용 안함 -->
<%-- 									</c:otherwise> --%>
<%-- 									</c:choose> --%>
								
<!-- 								</td> -->
<!-- 							</tr> -->
							<tr>
								<th class="tc tdbg3">교육내용</th>
								<td class="tl h300">${lctre.cn}</td>
							</tr>
							<%-- 
							<tr>
								<th class="tdbg3 tc">수료기준</th>
								<td class="tl" colspan="3">총 <b>${lctre.passPoint eq null ? '0' : lctre.passPoint }점
								</b> 이상 수료 &nbsp; &nbsp; &nbsp; (수료방식 : ${lctre.autoPassYn eq 'Y'?'자동수료':'수동수료' }
									)
								</td>
							</tr>
							 --%>
							 <tr>
								<th class="tdbg3 tc">수료기준</th>
								<td class="tl" colspan="3">
								<div class="comp_box" style="float:left; width:100px;">
								<p>총 교육시간</p>
								<fmt:formatNumber value="${lctre.compStnd01}" pattern="0000" var="compStnd01" />
								${fn:substring(compStnd01, 0, 2) }시 ${not empty compStnd01 and fn:length(compStnd01) >= 4 ? fn:substring(compStnd01, 2, 4) : '00'}분
							</div>
							<div class="comp_box" style="float:left;width:100px;">
								<p>최소 이수시간</p>
								<fmt:formatNumber value="${lctre.compStnd02}" pattern="0000" var="compStnd02" />
								${fn:substring(compStnd02, 0, 2) }시 ${not empty compStnd02 and fn:length(compStnd02) >= 4 ? fn:substring(compStnd02, 2, 4) : '00'}분
							</div>
							<div class="comp_box" style="float:left;width:100px;">
								<p>시험</p>
								${lctre.compStnd03 }
							</div>
							<div class="comp_box" style="float:left;width:100px;">
								<p>과제</p>
								${lctre.compStnd04 }
							</div>
							<div class="comp_box" style="float:left;width:100px;">
								<p>설문</p>
								${lctre.compStnd05 }
							</div>
								</td>
							</tr>
							<tr>
								<th class="tc tdbg3">문의전화</th>
								<td class="tl">${lctre.tel}<c:if
										test="${lctre.inqNm ne null and lctre.inqNm ne ''}"> ( 담당자 : ${lctre.inqNm})</c:if>
								</td>
							</tr>
							<tr>
								<th class="tc tdbg3">교육 장소</th>
								<td class="tl">${lctre.addr}<c:if
										test="${lctre.addrDetail ne null and lctre.addrDetail ne ''}"> ( 상세주소 : ${lctre.addrDetail})</c:if>
								</td>
							</tr>
							<tr>
								<th class="tc tdbg3">오시는 길</th>
								<td class="tl">${utcp.convNewLine(lctre.wayCome) }</td>
							</tr>
							<tr>
								<th>유의사항</th>
								<td>${utcp.convNewLine(lctre.eduEtc) }</td>
							</tr>
							<tr style="display:none;">
								<th class="tc tdbg3">출결QR코드</th>
								<td class="tl" colspan="3"><c:choose>
										<c:when
											test="${lctre.lctreType == 0 or lctre.lctreType == 2 }">
											<div class="w50 fl cf">
												<c:if test="${not empty qrAttendBegin }">

													<a
														href="javascript:window.open('${utcp.ctxPath }/DATA/upload/web/qr/attendBegin_${lctre.eduSeq }.png?${utcp.getNow2Str('yyyyMMddHHmmss')}','preView','width=310,height=420');"
														class="dp_ib fl"> <img
														src="${utcp.ctxPath }/DATA/upload/web/qr/attendBegin_${lctre.eduSeq }.png?${utcp.getNow2Str('yyyyMMddHHmmss')}"
														width="100px" />
													</a>
													<span class="dp_b fl pdt10 pdb20"> 생성시간 :
														${utcp.convDateToStr(qrAttendBegin.genDe,'yyyy.MM.dd HH:mm:ss') }
													</span>
													<a
														href="javascript:location.href=encodeURI('${utcp.ctxPath }/admin/cloud/download.do?downNm=qr_${lctre.eduSeq }.png&cloudFile=attendBegin_${lctre.eduSeq }.png&cloudPath=upload/web/qr/')"
														class="dp_ib fl btn01 btn_greenl mgr3"> QR 다운로드</a>
												</c:if>
												<button type="button" class="dp_ib fl btn02 btn_greenl"
													onclick="fn_createQrAttend('attendBegin')">QR생성</button>
											</div>
										
										</c:when>
										<c:otherwise>
										집합교육 수업만 QR생성이 가능합니다.
									</c:otherwise>
									</c:choose></td>
							</tr>

							<%-- 
							<tr>
								<th class="tc tdbg3">비밀번호 사용 여부</th>
								<td class="tl">${lctre.scrtyYn eq "Y" ? "사용":"미사용"}
								</td>
							</tr>	
							<tr>
								<th class="tc tdbg3">기타 동의 여부</th>
								<td class="tl">${lctre.etcAgreeYn eq "Y" ? "사용":"미사용"}
								</td>
							</tr>	
							<tr>
								<th class="tc tdbg3">파일첨부 필수 여부</th>
								<td class="tl">${lctre.essntlFileYn eq "Y" ? "사용":"미사용"}
								</td>
							</tr>	
							 --%>
							<tr>
								<th class="tc tdbg3">첨부파일</th>
								<td class="tl"><c:forEach items="${attachList01 }" var="o">
										<a
											href="${utcp.ctxPath}/user/edu/attach/${o.fileSeq }/download.do">${o.fileOrg }
										</a>
										<br />
									</c:forEach></td>
							</tr>



						</tbody>
					</table>
					<!-- 
					<div class="fl tc">
						<button class="btn02 btn_grayl" onclick="fn_moveList(); return false;">목록</button>
					</div>
					 -->
					<div class="fr tc">
						<%-- 수정,삭제 가능시기/수정가능항목 등 협의 필요 잠시 보류 --%>
						<button class="btn02 btn_green" onclick="fn_upd(); return false;">수정</button>
						<!-- 
						 -->
						<button class="btn01 btn_greenl"
							onclick="fn_copyRgs(); return false;">복사 등록</button>
					</div>
				</div>
				<!--// tab_con1 //-->


				<!--// tab_con2 //-->
				<div class="cont" id="tabCon-2">
					<jsp:include page="/WEB-INF/jsp/admin/edu/lctreView_inc_2.jsp"></jsp:include>
				</div>
				<!--// tab_con2 //-->

				<!--// tab_con3 //-->
				<div class="cont" id="tabCon-3">
					<jsp:include page="/WEB-INF/jsp/admin/edu/lctreView_inc_3.jsp"></jsp:include>
				</div>
				<!--// tab_con3 //-->

				<!--// tab_con4 //-->
				<div class="cont" id="tabCon-4">
					<jsp:include page="/WEB-INF/jsp/admin/edu/lctreView_inc_4.jsp"></jsp:include>
				</div>
				<!--// tab_con4 //-->
				<div class="cont" id="tabCon-11">
					<jsp:include page="/WEB-INF/jsp/admin/edu/lctreView_inc_11.jsp"></jsp:include>
				</div>
				<div class="cont" id="tabCon-5">
					<jsp:include page="/WEB-INF/jsp/admin/edu/lctreView_inc_5.jsp"></jsp:include>
				</div>
				<div class="cont" id="tabCon-6">
					<jsp:include page="/WEB-INF/jsp/admin/edu/lctreView_inc_6.jsp"></jsp:include>
				</div>
				<div class="cont" id="tabCon-7">
					<jsp:include page="/WEB-INF/jsp/admin/edu/lctreView_inc_7.jsp"></jsp:include>
				</div>
				<%-- <div class="cont" id="tabCon-8">
					<jsp:include page="/WEB-INF/jsp/admin/edu/lctreView_inc_8.jsp"></jsp:include>
				</div> --%>
				<div class="cont" id="tabCon-10">
					<jsp:include page="/WEB-INF/jsp/admin/edu/lctreView_inc_10.jsp"></jsp:include>
				</div>
			</div>
			<!--// board_tab_con //-->
		</div>
		<!--// board_tab_onoff //-->
	</div>
</div>


<div class="remodal messagePop2" data-remodal-id="messageTab2"
	role="dialog" aria-labelledby="modal1Title"
	aria-describedby="modal1Desc">
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC">알림</p>
		</div>
		<div class="modal-body">
			<p class="messageTxt" id="messageTab2"></p>
		</div>
		<div class="modal-footer">
			<div class="tc">
				<button onclick="popClose('2'); return false;"
					class="remodal-confirm btn02 btn_orange">확인</button>
			</div>
		</div>
	</div>
</div>

<div class="remodal messagePop2" data-remodal-id="messageTab3"
	role="dialog" aria-labelledby="modal1Title"
	aria-describedby="modal1Desc">
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC">알림</p>
		</div>
		<div class="modal-body">
			<p class="messageTxt" id="messageTab3"></p>
		</div>
		<div class="modal-footer">
			<div class="tc">
				<button onclick="popClose('3'); return false;"
					class="remodal-confirm btn02 btn_orange">확인</button>
			</div>
		</div>
	</div>
</div>

<div class="remodal messagePop1" data-remodal-id="successTab2"
	role="dialog" aria-labelledby="modal1Title"
	aria-describedby="modal1Desc">
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC">알림</p>
		</div>
		<div class="modal-body">
			<p class="messageTxt">등록되었습니다.</p>
		</div>
		<div class="modal-footer">
			<div class="tc">
				<button onclick="popClose('2'); return false;"
					class="remodal-confirm btn02 btn_green">확인</button>
			</div>
		</div>
	</div>
</div>
<!--// popup_message //-->


<div class="remodal messagePop3" data-remodal-id="rceptClose"
	role="dialog" aria-labelledby="modal1Title"
	aria-describedby="modal1Desc">
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC">승인마감</p>
		</div>
		<div class="modal-body">
			<p class="messageTxt">
				최종선발 후 취소 및 추가 승인 하실 수 없습니다.<br />승인된 신청자는 수강생으로 등록됩니다.<br />승인마감
				하시겠습니까?
			</p>
		</div>
		<div class="modal-footer">
			<div class="tc">
				<button onclick="fn_rceptCloseProc(); return false;"
					class="remodal-confirm btn02 btn_orange">확인</button>
				<button data-remodal-action="cancel"
					class="remodal-cancel btn02 btn_gray">취소</button>
			</div>
		</div>
	</div>
</div>

<div class="remodal messagePop3" data-remodal-id="eduClose"
	role="dialog" aria-labelledby="modal1Title"
	aria-describedby="modal1Desc">
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC">교육종료</p>
		</div>
		<div class="modal-body">
			<p class="messageTxt">
				교육을 종료하시면 성적 수정이 불가능합니다.<br />교육종료 하시겠습니까?
			</p>
		</div>
		<div class="modal-footer">
			<div class="tc">
				<button onclick="fn_eduCloseProc(); return false;"
					class="remodal-confirm btn02 btn_orange">확인</button>
				<button data-remodal-action="cancel"
					class="remodal-cancel btn02 btn_gray">취소</button>
			</div>
		</div>
	</div>
</div>

<div class="remodal messagePop3" data-remodal-id="eduCloseCancel"
	role="dialog" aria-labelledby="modal1Title"
	aria-describedby="modal1Desc">
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC">종료취소</p>
		</div>
		<div class="modal-body">
			<p class="messageTxt">
				수강생 최종합격여부 및 증명서 발급번호가 초기화 됩니다.<br />종료취소 하시겠습니까?
			</p>
		</div>
		<div class="modal-footer">
			<div class="tc">
				<button onclick="fn_eduCloseCancelProc(); return false;"
					class="remodal-confirm btn02 btn_orange">확인</button>
				<button data-remodal-action="cancel"
					class="remodal-cancel btn02 btn_gray">취소</button>
			</div>
		</div>
	</div>
</div>

<div class="remodal messagePop3" data-remodal-id="stdntCancel"
	role="dialog" aria-labelledby="modal1Title"
	aria-describedby="modal1Desc">
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC">수강취소</p>
		</div>
		<div class="modal-body">
			<p class="messageTxt">
				수강취소 후 재수강 등록 할 수 없습니다.<br />수강취소 처리 하시겠습니까?
			</p>
		</div>
		<div class="modal-footer">
			<div class="tc">
				<button onclick="fn_cancelProc(); return false;"
					class="remodal-confirm btn02 btn_orange">확인</button>
				<button data-remodal-action="cancel"
					class="remodal-cancel btn02 btn_gray">취소</button>
			</div>
		</div>
	</div>
</div>

<div class="remodal messagePop1" data-remodal-id="successClose2"
	role="dialog" aria-labelledby="modal1Title"
	aria-describedby="modal1Desc">
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC">알림</p>
		</div>
		<div class="modal-body">
			<p class="messageTxt">승인마감 처리되었습니다.</p>
		</div>
		<div class="modal-footer">
			<div class="tc">
				<button onclick="popClose2('2'); return false;"
					class="remodal-confirm btn02 btn_green">확인</button>
			</div>
		</div>
	</div>
</div>

<div class="remodal messagePop1" data-remodal-id="successCancel3"
	role="dialog" aria-labelledby="modal1Title"
	aria-describedby="modal1Desc">
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC">알림</p>
		</div>
		<div class="modal-body">
			<p class="messageTxt">수강취소 처리되었습니다.</p>
		</div>
		<div class="modal-footer">
			<div class="tc">
				<button onclick="popClose('3'); return false;"
					class="remodal-confirm btn02 btn_green">확인</button>
			</div>
		</div>
	</div>
</div>

<div class="remodal messagePop1" data-remodal-id="successClose3"
	role="dialog" aria-labelledby="modal1Title"
	aria-describedby="modal1Desc">
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC">알림</p>
		</div>
		<div class="modal-body">
			<p class="messageTxt" id="successClose3"></p>
		</div>
		<div class="modal-footer">
			<div class="tc">
				<button onclick="popClose3('3', '1'); return false;"
					class="remodal-confirm btn02 btn_green">확인</button>
			</div>
		</div>
	</div>
</div>

<div class="remodal messagePop1" data-remodal-id="successEduCancel3"
	role="dialog" aria-labelledby="modal1Title"
	aria-describedby="modal1Desc">
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC">알림</p>
		</div>
		<div class="modal-body">
			<p class="messageTxt">종료취소 처리되었습니다.</p>
		</div>
		<div class="modal-footer">
			<div class="tc">
				<button onclick="popClose3('3', '2'); return false;"
					class="remodal-confirm btn02 btn_green">확인</button>
			</div>
		</div>
	</div>
</div>


<div class="remodal messagePop messagePop2" data-remodal-id="message"
	role="dialog" aria-labelledby="modal1Title"
	aria-describedby="modal1Desc">
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC">알림</p>
		</div>
		<div class="modal-body">
			<p class="messageTxt" id="messageStr"></p>
		</div>
		<div class="modal-footer">
			<div class="tc">
				<button data-remodal-action="cancel"
					class="remodal-confirm btn02 btn_orange">확인</button>
			</div>
		</div>
	</div>
</div>


<!--// feedback //-->
<div class="remodal test2" data-remodal-id="md-feedbackReg"
	role="dialog" aria-labelledby="modal1Title"
	aria-describedby="modal1Desc" id="vm-feedbackReg">
	<button data-remodal-action="close" class="remodal-close"
		aria-label="Close"></button>
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit tl fdb">
				만족도조사<span class="cb dp_b font_14 pdt10">교육의 질 향상을 위한 설문입니다.
					성실하게 답변해주시면 감사하겠습니다.</span>
			</p>
		</div>
		<div class="modal-body">
			<div class="fbScore">
				<comp-table3 :fb="fb" :list="qtList"></comp-table3>
			</div>
		</div>
	</div>
</div>
<!--// feedback //-->
<script type="text/x-template" id="tpl-table3">
				<table width="100%" class="tb03 tc">
					<template v-for="(o,i) in list">
					<template v-if="o.qt.qtType==1||o.qt.qtType==3">
					<tr>
						<td class="tl">
						<span class="dp_b font_14 fw_500 mgb10">{{i+1}}. {{o.qt.question}}</span> 
						<span class="cb dp_b font_14 pdl15 mgr20"> 
						<template v-for="(o2,i2) in o.chList">
							<label :for="'choice-'+i+'-'+i2" class="dp_ib mgr15">
								<input readonly type="radio" :id="'choice-'+i+'-'+i2" class="mgr5" :name="'choice_'+i" :value="o2.chIdx" v-model="o.qt.asChIdx"/>{{o2.choice}}
							</label> 
						</template>
						</span></td>
					</tr>
					</template>
					<template v-else-if="o.qt.qtType==2">
					<tr>
						<td class="tl">
						<span class="dp_b font_14 fw_500 mgb10">{{i+1}}. {{o.qt.question}}</span> 
						<span class="cb dp_b font_14 pdl15 mgr20"> 
						<template v-for="(o2,i2) in o.chList">
							<label :for="'choice-'+i+'-'+i2" class="dp_ib mgr15">
								<input readonly type="checkbox" :id="'choice-'+i+'-'+i2" class="mgr5" :name="'choice_'+i" :value="o2.chIdx" v-model="o.qt.asChIdx2"/>{{o2.choice}}
							</label> 
						</template>
						</span></td>
					</tr>
					</template>
					<template v-else>
					<tr>
						<td class="tl"><span class="dp_b font_14 fw_500 mgb10">{{i+1}}. {{o.qt.question}}</span> 
						<span class="cb dp_b font_14 pdl15 mgr20"> <textarea cols="100" rows="5" class="pd5" style="height: auto;" v-model="o.qt.answer"></textarea>
						</span></td>
					</tr>
					</template>
					</template>
				</table>
</script>
<script>
Vue.component('comp-table3', {	
	template: '#tpl-table3',
	props:['fb','list'],
});
	var vm_feedbackReg = new Vue({
		el : '#vm-feedbackReg',
		data : {
			fb : {},
			qtList : [],
		},
	});

</script>

<!--// filelayerPop //-->
<jsp:include page="/WEB-INF/jsp/admin/edu/upload.jsp" />
<!--// filelayerPop //-->
<!--// filelayerPop //-->
<jsp:include page="/WEB-INF/jsp/layout/admin/modal.jsp" />
<!--// filelayerPop //-->