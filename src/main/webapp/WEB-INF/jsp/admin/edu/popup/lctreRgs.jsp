<%@page import="com.educare.component.VarComponent"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script src="${utcp.ctxPath}/resources/ckeditor4_full/ckeditor.js"></script>
<!-- pickadate.js -->
<link rel="stylesheet" href="${utcp.ctxPath}/resources/plugins/pickadate/default.css">
<link rel="stylesheet" href="${utcp.ctxPath}/resources/plugins/pickadate/default.date.css">
<script src="${utcp.ctxPath}/resources/plugins/pickadate/picker.js"></script>
<script src="${utcp.ctxPath}/resources/plugins/pickadate/picker.date.js"></script>
<script>
//<![CDATA[
$(function () {
	$("#rceptBeginDay").datepicker({
        onSelect: function (selectedDate) {
            var startDate = $(this).datepicker("getDate");
            if (startDate) {
                $("#rceptEndDay").datepicker("option", "minDate", startDate);
            }
        },
        beforeShow: function () {
            var startDate = $("#rceptEndDay").datepicker("getDate");
            return {
            	maxDate: startDate || null
            };
        }
    });
    $("#rceptEndDay").datepicker({
        onSelect: function (selectedDate) {
            var endDate = $(this).datepicker("getDate");
            if (endDate) {
                $("#rceptBeginDay").datepicker("option", "maxDate", endDate);
            }
        },
        beforeShow: function () {
            var startDate = $("#rceptBeginDay").datepicker("getDate");
            return {
            	minDate: startDate || null
            };
        }
    });
	$("#eduPeriodBegin").datepicker({
        onSelect: function (selectedDate) {
            var startDate = $(this).datepicker("getDate");
            if (startDate) {
                $("#eduPeriodEnd").datepicker("option", "minDate", startDate);
            }
        },
        beforeShow: function () {
            var startDate = $("#eduPeriodEnd").datepicker("getDate");
            return {
            	maxDate: startDate || null
            };
        }
    });
    $("#eduPeriodEnd").datepicker({
        onSelect: function (selectedDate) {
            var endDate = $(this).datepicker("getDate");
            if (endDate) {
                $("#eduPeriodBegin").datepicker("option", "maxDate", endDate);
            }
        },
        beforeShow: function () {
            var startDate = $("#eduPeriodBegin").datepicker("getDate");
            return {
            	minDate: startDate || null
            };
        }
    });
	// pickadate //
	
	$("#depositLimitDt").datepicker();
	CKEDITOR.replace('ckeditCn',{
		filebrowserUploadUrl: '${utcp.ctxPath}/editot/popupUpload.json?gubun=edu&prefixStr=edu_cn',
		height : 400
	});
	
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
	
	if('${lctre.instrctrId}'==''){
		$('#area-instrctrReg').hide();
		$('#check-noInstrctr').prop('checked',true);
	}
	$('#check-noInstrctr').click(function(){
		if(this.checked){
			$('#area-instrctrReg').hide();
		}else{
			$('#area-instrctrReg').show();
			
		}
	});
	
	<c:if test="${empty lctre.rceptPeriodEnd}">
		$('#rceptEndHour').val('23');
		$('#rceptEndMin').val('55');
	</c:if>

	<c:if test='${lctre.fee == "" || lctre.fee == null || lctre.fee == 0}'>
		$("#fee").val("0");
		$("#feeLimit").val("0");
		//$("#fee").attr("disabled", true);
		//$("#feeLimit").attr("disabled", true);
	</c:if>
	
	<c:if test='${lctre.groupFee == "" || lctre.groupFee == null || lctre.groupFee == 0}'>
		$("#groupFee").val("0");
		$("#groupComment").val("");
		$("#groupFee").attr("disabled", true);
		$("#groupComment").attr("disabled", true);
	</c:if>
	
	
	
    function onSelectHandler(date, context) {
        /**
         * @date is an array which be included dates(clicked date at first index)
         * @context is an object which stored calendar interal data.
         * @context.calendar is a root element reference.
         * @context.calendar is a calendar element reference.
         * @context.storage.activeDates is all toggled data, If you use toggle type calendar.
         * @context.storage.events is all events associated to this date
         */

        var $element = context.element;
        var $calendar = context.calendar;
        var $box = $element.siblings('.box').show();
        var text = '';

        if (date[0] !== null) {
            text += date[0].format('YYYY-MM-DD');
        }

        if (date[0] !== null && date[1] !== null) {
            text += ' ~ ';
        }
        else if (date[0] === null && date[1] == null) {
            text += '선택된 날짜가 없습니다.';
        }

        if (date[1] !== null) {
            text += date[1].format('YYYY-MM-DD');
        }

        $box.text(text);
    }


	// 캘린더 설정
	$('.calendar').pignoseCalendar({
		select: onSelectHandler,
		format: 'YYYY-MM-DD', // date format string 년월일로 포맷변경
		// disabledWeekdays: [1], // 매주월요일선택못함 SUN(0),MON(1),TUE(2),WED(3),THU(4),FRI(5),SAT(6)
		theme: 'blue', // 테마변경 light,blue/dark
		lang: 'ko', // 한국
		// minDate: moment().format("YYYY-MM-DD") //지난날짜 선택못함
	});	
	
	//
	$('input:radio[name=openYn][value=${not empty lctre.openYn?lctre.openYn:"N"}]').prop('checked',true);
	$('input:radio[name=smsAuto][value=${not empty lctre.smsAuto?lctre.smsAuto:"0"}]').prop('checked',true);
	
	//동영상 전용인경우 수료기준 등록 안함
	$('input:radio[name=lctreType]').click(function(){
		$('#area-passPoint1').show();
		$('#area-passPoint2').show();
		if(this.value==3){
			$('#area-passPoint1').hide();
			$('#area-passPoint2').hide();
		}
	});
	if('${lctre.lctreType}' == '3'){
		$('#area-passPoint1').hide();
		$('#area-passPoint2').hide();
	}
	
	$('#area-scrtyPw').hide();
	if('${lctre.scrtyYn}' == 'Y'){
		$('#area-scrtyPw').show();
	}
	//동영상 전용인경우 수료기준 등록 안함
	$('input:radio[name=scrtyYn]').click(function(){
		$('#area-scrtyPw').hide();
		if(this.value == 'Y'){
			$('#area-scrtyPw').show();
		}else{
			$('#scrtyPw').val('');
		}
	});
	
	if('${lctre.cancelLimitDt}' != ''){
        var formattedValue = '${lctre.cancelLimitDt}'.replace(/(\d{4})(\d{2})(\d{2})/, '$1-$2-$3');

		$('#i_cancelLimitDt').val(formattedValue);
	}
	
	$('input:radio[name=targetTp]').click(function(){
		$('#eduTp').attr('disabled',true);
		if(this.value == 2){
			$('#eduTp').attr('disabled',false);
		}
	});
	if('${lctre.targetTp}' == '2'){
		$('#eduTp').attr('disabled',false);
	}
});
//]]>
function fn_postSearch(){
	new daum.Postcode({
        oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 도로명 주소의 노출 규칙에 따라 주소를 표시한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var roadAddr = data.roadAddress; // 도로명 주소 변수
            var extraRoadAddr = ''; // 참고 항목 변수

            // 법정동명이 있을 경우 추가한다. (법정리는 제외)
            // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
            if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                extraRoadAddr += data.bname;
            }
            // 건물명이 있고, 공동주택일 경우 추가한다.
            if(data.buildingName !== '' && data.apartment === 'Y'){
               extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
            }
            // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
            if(extraRoadAddr !== ''){
                extraRoadAddr = ' (' + extraRoadAddr + ')';
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            $('#postNo').val(data.zonecode);
            $('#addr').val(roadAddr);
            
            // 참고항목 문자열이 있을 경우 해당 필드에 넣는다.
            //if(roadAddr != ''){
            //	$('#addrEtc').val(extraRoadAddr);
            //} else {
            //	$('#addrEtc').val("");
            //}
            $('#addrDetail').val("");
            $('#addrDetail').focus();
            
            $('#addrMemo').val("");
            $('#addrMemo').attr("readonly", false);
        }
    }).open();
}
function fn_rgs(){
	
	var compStnd01Hour = $("#compStnd01Hour").val();
	var compStnd01Min = $("#compStnd01Min").val();	
	$("#compStnd01").val(compStnd01Hour+compStnd01Min);
	
	var compStnd02Hour = $("#compStnd02Hour").val();
	var compStnd02Min = $("#compStnd02Min").val();	
	$("#compStnd02").val(compStnd02Hour+compStnd02Min);
	
	if($("#eduNm").val() == ""){
		alert("교육과정 명은 필수입력입니다.");
		$("#eduNm").focus();
		return;
	}
	if($("#ctg1Seq").val() == 0){
		alert("교육과정을 선택하세요.");
		$("#ctg1Seq").focus();
		return;
	}
	
	if(!document.querySelector('input[name="passCertYn"]:checked')){ 
		alert("기본수료증 사용여부를 선택해주세요.");
		$('#passCertY').focus;
		return;
	}	
	 
	var passCertInput=document.querySelector("#passIdx");
	var passCertY=document.querySelector("#passCertY");
	var passCertN=document.querySelector("#passCertN");
	if(passCertInput.value == 0 && passCertY.checked){ 
		alert("수료증을 선택해주세요.");
		$('#passCertNm').focus();
		return;
	}
	

	if(!document.querySelector('input[name="fbYn"]:checked')){ 
		$("#messageStr").html("만족도 조사 사용여부를 선택해주세요.");
		location.href = "#message";
		$("#inputId").val("fbYn");
		return;
	}	
	
	var fbIdx=document.querySelector("#fbIdx");
	var fbYnY=document.querySelector("#fbYnY");
	var fbYnN=document.querySelector("#fbYnN");
	if(fbIdx.value == 0 && fbYnY.checked){ 
		alert("만족도 조사를 선택해주세요.");
		$('#fbNm').focus();
		return;
	}
	console.log('fbIdx',fbIdx.value);

	//이미지 체크
	if( $("#imgFile").val()!='' && typeof $("#imgFile").val()!=='undefined' ){
		var ext = $('#imgFile').val().split('.').pop().toLowerCase();
		if($.inArray(ext, ['gif','png','jpg','jpeg']) == -1) {
			alert('gif,png,jpg,jpeg 파일만 업로드 할수 있습니다.');
			return;
		}
	}
	
	if($("input:checkbox[id=imgUseChk]").is(":checked") == true) {
		$("#imgUseYn").val("N");
	}else{
		$("#imgUseYn").val("Y");
	}
	
	if($("input:checkbox[id=rceptUseChk]").is(":checked") == true) {
		$("#rceptPeriodYn").val("N");
	}else{
		$("#rceptPeriodYn").val("Y");
		
		if($("#rceptBeginDay").val() == ""){
			alert("신청기간을 입력하세요.");
			$("#rceptBeginDay").focus();
			return;
		}else if($("#rceptEndDay").val() == ""){
			alert("신청기간을 입력하세요.");
			$("#rceptEndDay").focus();
			return;
		}else{
			var rceptBegin = $("#rceptBeginDay").val().replace(/-/gi, "") + $("#rceptBeginHour").val() + $("#rceptBeginMin").val();
			var rceptEnd = $("#rceptEndDay").val().replace(/-/gi, "") + $("#rceptEndHour").val() + $("#rceptEndMin").val();	
			console.log(rceptBegin);
			if(Number(rceptBegin) > Number(rceptEnd)){
				alert("신청기간을 입력하세요.");
				$("#rceptEndDay").focus();
				return;
			}else{
				$("#rceptPeriodBegin").val(rceptBegin);
				$("#rceptPeriodEnd").val(rceptEnd);
			}
		}
	}
	
	var eduBegin = $("#eduPeriodBegin").val();
	var eduEnd = $("#eduPeriodEnd").val();
	
	if(eduBegin == ""){
		alert("교육기간은 필수입력입니다.");
		$('#eduPeriodBegin').focus();
		return;
	}else if(eduEnd == ""){
		alert("교육기간은 필수입력입니다.");
		$("#eduPeriodEnd").focus();
		return;
	}else{
		if(Number(eduBegin.replace(/-/gi, "")) > Number(eduEnd.replace(/-/gi, ""))){
			alert("교육기간을 확인하세요.");
			$("#eduPeriodEnd").focus();
			return;
		}
	}
	
	if($("input:checkbox[id=check-noInstrctr]").is(":checked") == true) {
		$('#instrctrNm').val('');
		$('#instrctrId').val('');
	}
	
	
	if($("#closeType").val() == ""){
		$("#messageStr").html("마감옵션을 선택하세요.");
		location.href = "#message";
		$("#inputId").val("closeType");
		return;
	}
	
	//무료면 기본,단체 체크박스 해제
	if($('#isFeeN').is(':checked')){
		$("#fee").val('0');	
	}
	
	if(CKEDITOR.instances.ckeditCn.getData() == ""){
		$("#messageStr").html("상세내용을 입력하세요.");
		location.href = "#message";
		$("#inputId").val("ckeditCn");
		return;
	}else{
		$('#cn').val(CKEDITOR.instances.ckeditCn.getData());
	}
	//if(!document.querySelector('input[name="etcAgreeYn"]:checked')){ 
	//	$("#messageStr").html("기타 동의 여부를 선택해주세요.");
	//	location.href = "#message";
	//	$("#inputId").val("etcAgreeY");
	//	return;
	//}
	
	
	//var tel1_value=document.querySelector("#tel1").value;
	//var tel2_value=document.querySelector("#tel2").value;
	//var tel3_value=document.querySelector("#tel3").value;
	//document.querySelector("#tel").value = tel1_value+"-"+tel2_value+"-"+tel3_value;
// 	if($('#addrMemo').val() == ''){
// 		alert('교육장소를 입력하세요');$('#addrMemo').focus();
// 		return;
// 	}
	
	$('input[name=cancelLimitDt]').val($('#i_cancelLimitDt').val().replace(/-/g,''));
	
	var formData = $("form[name=rgsFrm]").serializeArray();
	
	//기타항목 관련
	/*  */
	var _regex = /^[가-힣a-zA-Z0-9!@#$%^&*()_+~.\/\-=\s]+(,[가-힣a-zA-Z0-9!@#$%^&*()_+~.\/\-=\s]+)+$/;
	var _regex2 = /['"]/g;
	for(i in vm_etc_iem.listEtc){
		var _etc = vm_etc_iem.listEtc[i];
		
		if(_regex2.test(_etc.etcIemNm)){
			alert('따옴표는 사용할 수 없습니다.');
			$('#etcIemNm-'+i).focus();
			return;
		}
		
		
		if(_etc.dataInputTy != 'text'){
			//console.log(_etc.etcIemEx);
			if(_etc.etcIemEx == ''){
				alert('선택명을 2개이상 저장하셔야 합니다.');
				$('#etcIemEx-'+i).focus();
				return;
			}
			if(!_regex.test(_etc.etcIemEx)){
				alert('신청폼 추가에서 규칙에 어긋난 항목이 존재합니다.');
				$('#etcIemEx-'+i).focus();
				return;
			}
			etcIemExArr = _etc.etcIemEx.split(',');
			if(etcIemExArr.length !== new Set(etcIemExArr).size){
				alert('중복된 값이 존재합니다.');
				$('#etcIemEx-'+i).focus();
				return;
			}
		}
	}
	var etcIemJson = JSON.stringify(vm_etc_iem.listEtc);
	formData.push({name : 'etcIemJson', value : JSON.stringify({etcIemList : vm_etc_iem.listEtc})});
	
	$.ajax({
		//contentType : false,
		//processData : false,
		//cache: false,
		async: false,
		data:formData,
		type:'post',
		url:'${utcp.ctxPath}/admin/edu/lctreRgsProc.ajax',
		success:function(r){
			if(r.result == 1 ){
				$('#eduSeq').val(r.data.eduSeq);
				
				formData = new FormData($("form[name=rgsFrm]")[0]);
				$.ajax({
					contentType : false,
					processData : false,
					cache: false,
					async: false,
					data:formData,
					type:'post',
					url:'${utcp.ctxPath}/admin/edu/lctreRgsFileThumProc.ajax',
					success:function(r){
					}
				});
				//console.log('length',myDropzone.getQueuedFiles().length);
				if(myDropzone.getQueuedFiles().length > 0){
					homeLoader.show();
                    myDropzone.processQueue();//excute upload!!
                }else{
                	location.href='lctreView.do?eduSeq='+r.data.eduSeq;
                	opener.parent.location.reload();
                }
			}
		}
	});
	//$("form[name='rgsFrm']").attr("action", "/admin/edu/lctreRgsProc.do");
	//$("form[name='rgsFrm']").submit();
}

function formatPhoneNumber() {
	var phone1 = document.getElementById("phone1").value;
	var phone2 = document.getElementById("phone2").value;
	var phone3 = document.getElementById("phone3").value;

	var tel = phone1 + "-" + phone2 + "-" + phone3;

	document.getElementById("tel").value = tel;
}

function fn_focus(){
	var inputId = "#" + $("#inputId").val();
	console.log(inputId);
	$(inputId).focus();
	//location.href = inputId;
}

function fn_default(){
	if($("input:checkbox[id=defaultChk]").is(":checked") == true) {
		$("#fee").attr("disabled", false);
		$("#feeLimit").attr("disabled", false);
	}else{
		$("#fee").attr("disabled", true);
		$("#feeLimit").attr("disabled", true);
	}
}

function fn_group(){
	if($("input:checkbox[id=groupChk]").is(":checked") == true) {
		$("#groupFee").attr("disabled", false);
		$("#groupComment").attr("disabled", false);
	}else{
		$("#groupFee").attr("disabled", true);
		$("#groupComment").attr("disabled", true);
	}
}

function fn_photoDel(){
	$("#imgOrg").val("");
	$("#imgRename").val("");
	$("#photo_div").html(
		"<label for=\"imgFile\"><span class=\"sound_only\">파일</span></label>" + 
		"<input type=\"file\" name=\"imgFile\" id=\"imgFile\" title=\"파일첨부\" class=\"frm_file ip2\">"
	);
}

function fn_bulkRgs(){
	$.ajax({
		url: "${utcp.ctxPath}/user/edu/bulkInstrctrList.json",
		type: "post",
		data: {"srchWrd" : $("#srchBelong").val()},
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
						dataHtml += "	<td><input type=\"radio\" name=\"bulkUserId\" value=\"" + list[i].userId + "," + list[i].userNm + "\" /></td>";
						dataHtml += "	<td>" + (list.length - i) + "</td>";
						dataHtml += "	<td>" + list[i].loginId + "</td>";
						dataHtml += "	<td>" + list[i].userNm + "</td>";
						dataHtml += "	<td>" + list[i].decMobile + "</td>";
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
function fn_lctreList(){
	if(!document.querySelector("#preEduY").checked){
		$("#messageStr").html("필수선행 교육 [있음]을 선택해주세요");
		location.href = "#message";
		$("#inputId").val("preEduY");
		return false;
	}
	
	$.ajax({
		url: "${utcp.ctxPath}/admin/ajax/esLectureList.ajax",
		type: "post",
		data: {"srchWrd" : $("#srchlec").val()},
		success: function(r) {
			if(r.result ==1 ){
				var list = r.data.list;
				var dataHtml = "";
				
				if(list == null || list.length < 1 || list == "") {
					dataHtml += "<tr>";
					dataHtml += "	<td colspan=\"8\" class=\"h200\">데이터가 없습니다.</td>";
					dataHtml += "</tr>";
				}else{
					for(var i in list){
						dataHtml += "<tr>";
						dataHtml += "	<td><input type=\"radio\" id=\"lectureId\" name=\"lectureId\" value=\"" + list[i].eduSeq + "," + list[i].eduNm + "\" /></td>";
						dataHtml += "	<td>" + list[i].ctg1Nm + "</td>";
						dataHtml += "	<td>" + list[i].ctg2Nm + "</td>";
						dataHtml += "	<td>" + list[i].eduNm + "</td>";
						dataHtml += "	<td>" + list[i].personnel + "</td>";
						dataHtml += "	<td>" + list[i].addStatusBox + "</td>";
						dataHtml += "	<td>" + list[i].openYn + "</td>";
						dataHtml += "	<td>" + list[i].instrctrNm + "</td>";
						dataHtml += "</tr>";
					}
				}
				document.querySelector("#lectureList").innerHTML = dataHtml;
				
			}else{
				alert(r.msg);
			}
			
		}
	});
	location.href= "#lecturRegist";
}
function fn_feedbackList(){
	if(!document.querySelector("#fbYnY").checked){
		$("#messageStr").html("만족도 조사 사용  [사용]을 선택해주세요");
		location.href = "#message";
		$("#inputId").val("fbYnY");
		return false;
	}
	$.ajax({
		url: "${utcp.ctxPath}/admin/ajax/fbList.ajax",
		type: "post",
		data: {"srchWrd" : $("#srchfb").val()},
		success: function(r) {
			if(r.result ==1 ){
				var list = r.data;
				var dataHtml = "";
				
				if(list == null || list.length < 1 || list == "") {
					dataHtml += "<tr>";
					dataHtml += "	<td colspan=\"5\" class=\"h200\">데이터가 없습니다.</td>";
					dataHtml += "</tr>";
				}else{
					for(var i in list){
						dataHtml += "<tr>";
						dataHtml += "	<td><input type=\"radio\" id=\"feedId\" name=\"feedId\" value=\"" + list[i].idx + "," + list[i].title + "\" /></td>";
						dataHtml += "	<td>" + list[i].title + "</td>";
						dataHtml += "	<td>" + list[i].qtType1Cnt + "</td>";
						dataHtml += "	<td>" + list[i].qtType0Cnt + "</td>";
						dataHtml += "	<td>" + list[i].regNm + "</td>";
						dataHtml += "</tr>";
					}
				}
				document.querySelector("#feedbackList").innerHTML = dataHtml;
				
			}else{
				alert(r.msg);
			}
		}
	});
	location.href= "#feedbackRegist";
}
function fn_passCertList(){
	if(!document.querySelector("#passCertY").checked){
		$("#messageStr").html("수료증  [사용]을 선택해주세요");
		location.href = "#message";
		$("#inputId").val("passCertY");
		return false;
	}
	$.ajax({
		url: "${utcp.ctxPath}/admin/ajax/passCertList.ajax",
		type: "post",
		data: {"srchWrd" : $("#srchpc").val(), "passTp": 1},
		success: function(r) {
			if(r.result ==1 ){
				var list = r.data;
				var dataHtml = "";
				
				if(list == null || list.length < 1 || list == "") {
					dataHtml += "<tr>";
					dataHtml += "	<td colspan=\"5\" class=\"h200\">데이터가 없습니다.</td>";
					dataHtml += "</tr>";
				}else{
					for(var i in list){
						dataHtml += "<tr>";
						dataHtml += "	<td><input type=\"radio\" id=\"passCertId\" name=\"passCertId\" value=\"" + list[i].passIdx + "," + list[i].title + "\" /></td>";
						dataHtml += "	<td>" + list[i].title + "</td>";
						dataHtml += "	<td>" + list[i].orgNm + "</td>";
						var regDt = new Date(list[i].regDt).toISOString().split("T");
						dataHtml += "	<td>" + regDt[0] + "</td>";
						dataHtml += "</tr>";
					}
				}
				document.querySelector("#passCertList").innerHTML = dataHtml;
				
			}else{
				alert(r.msg);
			}
		}
	});
	location.href= "#passCertRegist";
}
function fn_bulkUserRgs(){
	var userId = '';
	var userNm = '';
	var i = 0;
	
	if($("input[name='bulkUserId']:checked").length == 0){
		alert("강사를 선택하세요.");
		return;
	}else{
		userInfo=$("input[name=bulkUserId]:checked").val();
	}
	var userArr = userInfo.split(',');
	$('#instrctrId').val(userArr[0]);
	$('#instrctrNm').val(userArr[1]);
	$('#area-instrctrNm').html(userArr[1]);
	location.href = "#";
}
function fn_lectureRgs(){
	var userInfo = '';
	
	if(!document.querySelector("input[name=lectureId]:checked")){
		alert("교육을 선택하세요.");
		return;
	}else{
		userInfo=document.querySelector("input[name=lectureId]:checked").value;
	}
	var userArr = userInfo.split(',');
	document.querySelector('#preEduSeq').value = userArr[0];
	document.querySelector('#preEduNm').value = userArr[1];
	location.href = "#";
}
function fn_feedbackRgs(){
	var userInfo = '';
	
	if(!document.querySelector("input[name=feedId]:checked")){
		alert("만족도 조사을 선택하세요.");
		return;
	}else{
		userInfo=document.querySelector("input[name=feedId]:checked").value;
	}
	var userArr = userInfo.split(',');
	document.querySelector('#fbIdx').value = userArr[0];
	document.querySelector('#fbNm').value = userArr[1];
	location.href = "#";
}
function fn_passCertRgs(){
	var userInfo = '';
	
	if(!document.querySelector("input[name=passCertId]:checked")){
		alert("수료증을 선택하세요.");
		return;
	}else{
		userInfo=document.querySelector("input[name=passCertId]:checked").value;
	}
	var userArr = userInfo.split(',');
	document.querySelector('#passIdx').value = userArr[0];
	document.querySelector('#passCertNm').value = userArr[1];
	location.href = "#";
}
function fn_reset(type){
	if(type==1){
		document.querySelector("#passCertNm").value="";
		document.querySelector("#passIdx").value="0";
		return;
	}else if(type==2){
		document.querySelector("#fbNm").value="";
		document.querySelector("#fbIdx").value="0";
		return;
	}else{
		return;
	}
}

/*
 *	sub 
 */


function fn_cancel_lctreRgs(){
	if(('${param.eduSeq}')*1 > 0){
		location.href='lctreView.do?eduSeq=${param.eduSeq}&tab=1';
	}else{
		window.close();
	}
}
function fn_setDefaultAddr(code){
	if(code == 1){
		$('#postNo').val('32166');
		$('#addr').val('충청남도 태안군 안면읍 꽃지해안로 134');
		$('#addrDetail').val('태안 교육시설(나라키움 태안정책연수원)');
		$('#addrMemo').val('태안');
		$('#addrMemo').attr('readonly', true);
		var tmp = '[교통] \n교육시작일 및 종료일 버스 운행\n';
		tmp += '(교육 시작일)세종청사(중앙동) 출발 → 태안 교육시설 도착\n';
		tmp += '(교육 종료일)태안 교육시설 출발 → 세종 도착\n\n';
		tmp += '[주차]\n';
		tmp += '(제1주차장) 숙소동 40대\n';
		tmp += '-장애인주차장: 4대 / 전기차충전소: 5대\n';
		tmp += '(제2주차장) 교육동 40대\n';
		$('textarea[name=wayCome]').val(tmp);
	}else{
		$('#postNo').val('08389');
		$('#addr').val('서울특별시 구로구 디지털로 26길 61');
		$('#addrDetail').val('KTL 서울 분원');
		var tmp = '[지하철 이용시]\n';
		tmp += '2호선 구로디지털단지역 → 2,3번 출구 → 우체국 사거리에서 우체국 골목으로 200m 거리 → 에이스하이엔드타워2차(도보7분 소요)\n';
		tmp += '※김포공항 : 지하철 5호선 → 영등포구청역 환승2호선 → 구로디지털단지역\n\n';
		tmp += '[버스 이용시]\n';
		tmp += '이마트 구로점 하차 : 5536, 5628번\n';
		tmp += '구로디지털단지역(정류장) 하차 : 150, 505, 5524, 5531, 5601, 5617, 5620, 5621, 5623, 5625, 5627, 5633, 5713, 6612, 6635\n\n';
		tmp += '[마을버스 이용시]\n';
		tmp += '이마트 구로점 하차 : 구로09\n';
		tmp += '구로디지털단지역 하차 : 영등포01\n';
		tmp += '구로디지털단지역 하차 : 금천03, 금천06\n\n';
		tmp += '[자가용 이용시]\n';
		tmp += '남부순환도로 이용 : 시흥IC에서 여의도 방면으로 진입 → 첫 삼거리 신호에서 좌회전 → 첫 사거리 신호에서 좌회전(우체국 골목)→ 200m직진\n';
		tmp += '시흥대로(여의도 → 안양방면)이용 : 구로디지털단지(2호선)역 지나 → 300m 직진 → 첫 삼거리 신호에서 우회전 → 첫 사거리 신호에서 좌회전 (우체국 골목) → 200m직진\n';
		tmp += '시흥대로(안양 → 여의도 방면)이용 : 시흥 IC에서 여의고 방면으로 직진 → 첫 삼거리에서 좌회전 → 첫 사거리 신호에서 좌회전(우체국 골목) → 200m 직진\n';
		$('textarea[name=wayCome]').val(tmp);
	}
}
</script>
<div class="title_name">
	<h1>
		교육관리
		<ul>
			<li>
				<a href="main.jsp"> <img src="${utcp.ctxPath }/resources/admin/images/sub_title_icon.png" alt="홈 아이콘">
				</a>
			</li>
			<li class="mgl10 mgr10">
				<img src="${utcp.ctxPath }/resources/admin/images/sub_title_icon1.png" alt="오른쪽 화살표 아이콘">
			</li>
			<li>교육관리</li>
			<li class="mgl10 mgr10">
				<img src="${utcp.ctxPath }/resources/admin/images/sub_title_icon1.png" alt="오른쪽 화살표 아이콘">
			</li>
			<li>교육관리(재직자)</li>
		</ul>
	</h1>
</div>

<section class="pd025 pd200 po_re">
	<div class="po_re br5 bs_box cf">
		<form method="post" id="rgsFrm" name="rgsFrm" enctype="multipart/form-data">
			<input type="hidden" name="eduSeq" id="eduSeq" value="${not empty param.eduSeq?param.eduSeq:0}">
			<input type="hidden" name="copyEduSeq" value="${not empty param.copyEduSeq?param.copyEduSeq:0}">
			<input type="hidden" name="imgOrg" id="imgOrg" value="${lctre.imgOrg}" />
			<input type="hidden" name="imgRename" id="imgRename" value="${lctre.imgRename}" />
			<table width="100%" class="tb02 tc">
				<tbody>
					<tr >
						<th class="tdbg3 tc">기수</th>
						<td class="tl" colspan="3"><fmt:formatDate value="<%=new java.util.Date()%>" var="curYear" pattern="yyyy" /> <fmt:parseNumber value="${curYear}" var="year" /> <select name="eduYear">
								<option value="">연도선택</option>
								<c:forEach begin="2023" end="${year+1 }" varStatus="stat">
									<c:set value="${year+2-stat.count  }" var="choiceYear" />
									<option value="${choiceYear}" <c:if test="${choiceYear == lctre.eduYear}">selected</c:if>>${choiceYear}년</option>
								</c:forEach>
						</select> 
						제<input type="number" name="eduTerm" value="${lctre.eduTerm }" class="ip9"/>기
						</td>
					</tr>
					<tr>
						<th class="tdbg3 tc"><label for="eduNm">과정명 *</label></th>
						<td class="tl" colspan="3"><input type="text" name="eduNm" id="eduNm" class="ip2 mgr10" maxlength="400" value="${lctre.eduNm}" /> 
						<input type="radio" name="openYn" value="Y" id="openY" checked /> <label for="openY" class="pdl5 mgr10 cp">공개</label> 
						<input type="radio" name="openYn" value="N" id="openN" /> <label for="openN" class="pdl5 cp">비공개</label> 
						</td>
					</tr>
					<tr>
						<th class="tdbg3 tc">교육형태 *</th>
						<td class="tl" colspan="3">
							<input type="radio" name="lctreType" value="0" id="lctreType0" ${lctre.lctreType eq '0'?'checked':'' } />
							<label for="lctreType0" class="pdl5 cp">집합교육</label> 
							
							<input type="radio" name="lctreType" value="1" id="lctreType1" ${lctre.lctreType eq '1'?'checked':'' } />
							<label for="lctreType1" class="pdl5 mgr10 cp">온라인교육</label> 
							
							<input type="radio" name="lctreType" value="2" id="lctreType2" ${lctre.lctreType eq '2'?'checked':'' } />
							<label for="lctreType2" class="pdl5 mgr10 cp">집합교육(+온라인교육)</label>
							<%-- 
							 
							<input type="radio" name="lctreType" value="3" id="lctreType3" ${lctre.lctreType eq '3'?'checked':'' } />
							<label for="lctreType3" class="pdl5 mgr10 cp">동영상전용</label>
							 --%>
						</td>
					</tr>

					<tr>
						<th class="tdbg3 tc">교육카테고리 *</th>
						<td class="tl" colspan="3"><select name="ctg1Seq" id="ctg1Seq" class="vb">
								<option value="">1차카테고리</option>
								<c:forEach var="data" items="${cateList}" varStatus="stat">
									<option value="${data.ctgrySeq}" <c:if test='${lctre.ctg1Seq == data.ctgrySeq}'>selected</c:if>>${data.ctgryNm}</option>
								</c:forEach>
						</select> <select name="ctg2Seq" id="ctg2Seq" class="vb">
								<option value="0">2차카테고리</option>
								<c:forEach var="data" items="${cateList2}" varStatus="stat">
									<option value="${data.ctgrySeq}" <c:if test='${lctre.ctg2Seq == data.ctgrySeq}'>selected</c:if>>${data.ctgryNm}</option>
								</c:forEach>
						</select> <select name="ctg3Seq" id="ctg3Seq" class="vb">
								<option value="0" selected>3차카테고리</option>
								<c:forEach var="data" items="${cateList3}" varStatus="stat">
									<option value="${data.ctgrySeq}" <c:if test='${lctre.ctg3Seq == data.ctgrySeq}'>selected</c:if>>${data.ctgryNm}</option>
								</c:forEach>
						</select> <input type="hidden" name="instrctrId" id="instrctrId" value="${lctre.instrctrId }" /> <input type="hidden" name="instrctrNm" id="instrctrNm" value="${lctre.instrctrNm }" /></td>
					</tr>

					<tr style="display:none;">
						<th class="tdbg3 tc">강사 등록</th>
						<td class="tl" colspan="3"><span id="area-instrctrReg"> <span id="area-instrctrNm">${lctre.instrctrNm }</span>&nbsp;&nbsp;&nbsp;
								<button type="button" class="btn01 btn_greenl" onclick="fn_bulkRgs();">강사선택</button>
						</span> <input type="checkbox" id="check-noInstrctr" />강사없음</td>
					</tr>

					<tr>
						<th class="tdbg3 tc">신청기간</th>
						<td class="tl" colspan="3"><c:set var="rceptBegin" value="${utcp.convStrToDate(lctre.rceptPeriodBegin,'yyyyMMddHHmm')}" /> <c:set var="rceptDateBegin" value="${utcp.convDateToStr(rceptBegin,'yyyy-MM-dd')}" /> <c:set var="rceptHourBegin" value="${utcp.convDateToStr(rceptBegin,'HH')}" /> <c:set var="rceptMinuteBegin" value="${utcp.convDateToStr(rceptBegin,'mm')}" /> <c:set var="rceptEnd" value="${utcp.convStrToDate(lctre.rceptPeriodEnd,'yyyyMMddHHmm')}" /> <c:set var="rceptDateEnd" value="${utcp.convDateToStr(rceptEnd,'yyyy-MM-dd')}" /> <c:set var="rceptHourEnd" value="${utcp.convDateToStr(rceptEnd,'HH')}" /> <c:set var="rceptMinuteEnd" value="${utcp.convDateToStr(rceptEnd,'mm')}" /> 
						<input type="text" id="rceptBeginDay" value="${rceptDateBegin}" class="datepicker input_calendar ip6 tc" readonly/> 
						<select id="rceptBeginHour" class="vb">
								<c:forEach begin="0" end="23" varStatus="stat">
									<c:set var="hour" value="${stat.index}" />
									<c:if test="${hour < 10}">
										<c:set var="hour" value="0${hour}" />
									</c:if>
									<option value="${hour}" <c:if test="${rceptHourBegin == hour}">selected</c:if>>${hour}시</option>
								</c:forEach>
						</select> 
						<select id="rceptBeginMin" class="vb">
								<c:forEach var="m" begin="0" end="55" step="5"> 
									<fmt:formatNumber value="${m}" pattern="00" var="selMinute" />
									<option value="${selMinute}" <c:if test="${rceptMinuteBegin == selMinute}">selected</c:if>>${selMinute}분</option>
								</c:forEach>
						</select> <input type="hidden" id="rceptPeriodBegin" name="rceptPeriodBegin" /> <span class="pd10">~</span> 
						<input type="text" id="rceptEndDay" value="${rceptDateEnd}" class="datepicker input_calendar ip6 tc" readonly/> <select id="rceptEndHour" class="vb">
								<c:forEach begin="0" end="23" varStatus="stat">
									<c:set var="hour" value="${stat.index}" />
									<c:if test="${hour < 10}">
										<c:set var="hour" value="0${hour}" />
									</c:if>
									<option value="${hour}" <c:if test="${rceptHourEnd == hour}">selected</c:if>>${hour}시</option>
								</c:forEach>
						</select> 
						<select id="rceptEndMin" class="vb">
								<c:forEach var="m" begin="0" end="55" step="5"> 
									<fmt:formatNumber value="${m}" pattern="00" var="selMinute" />
									<option value="${selMinute}" <c:if test="${rceptMinuteEnd == selMinute}">selected</c:if>>${selMinute}분</option>
								</c:forEach>
						</select> <input type="hidden" id="rceptPeriodEnd" name="rceptPeriodEnd" /> <label for="rceptUseChk">
								<input type="checkbox" id="rceptUseChk" <c:if test="${lctre.rceptPeriodYn eq 'N'}">checked</c:if> />
								미설정
								<input type="hidden" id="rceptPeriodYn" name="rceptPeriodYn" />
							</label></td>
					</tr>
					<tr>
						<th class="tdbg3 tc">교육기간 *</th>
						<td class="tl" colspan="3"><c:set var="eduBegin" value="${utcp.convStrToDate(lctre.eduPeriodBegin,'yyyyMMddHHmm')}" /> 
						<c:set var="eduDateBegin" value="${utcp.convDateToStr(eduBegin,'yyyy-MM-dd')}" /> 
						<c:set var="eduHourBegin" value="${utcp.convDateToStr(eduBegin,'HH')}" /> 
						<c:set var="eduMinuteBegin" value="${utcp.convDateToStr(eduBegin,'mm')}" /> 
						<c:set var="eduEnd" value="${utcp.convStrToDate(lctre.eduPeriodEnd,'yyyyMMddHHmm')}" /> 
						<c:set var="eduDateEnd" value="${utcp.convDateToStr(eduEnd,'yyyy-MM-dd')}" /> 
						<c:set var="eduHourEnd" value="${utcp.convDateToStr(eduEnd,'HH')}" /> 
						<c:set var="eduMinuteEnd" value="${utcp.convDateToStr(eduEnd,'mm')}" /> 
						<input type="text" id="eduPeriodBegin" name="eduPeriodBegin" value="${lctre.eduPeriodBegin}" class="datepicker input_calendar ip6 tc" readonly/> 
						<span class="pd10">~</span> 
						<input type="text" id="eduPeriodEnd" name="eduPeriodEnd" value="${lctre.eduPeriodEnd}" class="datepicker input_calendar ip6 tl" readonly/>
<!-- 						<input type="text" id="eduTime" name="eduTime" class="ip6 tc"/>  -->
						</td>
					</tr>

					<tr style="display: none;">
						<th class="tdbg3 tc">취소가능일</th>
						<td class="tl" colspan="3"><input type="text" id="i_cancelLimitDt" class="datepicker input_calendar ip6 tc" /> <input type="hidden" name="cancelLimitDt" /></td>
					</tr>
					<tr>
						<th class="tdbg3 tc">모집인원</th>
						<td class="tl" colspan="3"><input type="number" min="0" name="personnel" id="personnel" class="ip9 mgr5" value="${lctre.personnel}" />명 <%-- 
							 --%> (최소인원 : <input type="number" min="0" name="minPersonnel" id="minPersonnel" value="${lctre.minPersonnel}" class="ip9 mgr5" />명) <input type="hidden" name="extPersonnel" id="extPersonnel" value="0" /> <!-- (대기인원 :  --> <!-- input type="number" name="extPersonnel" id="extPersonnel" value="${lctre.extPersonnel}" class="ip9 mgr5" style="display: none;"/><!-- 명) --> <!-- <input type="hidden" name="minPersonnel" id="minPersonnel" value="0" /> --> 
							 <select name="closeTp" id="closeType" class="mgl20 vb" style="display: none;">
								<!-- <option value="">승인 옵션</option> -->
								<option value="02" <c:if test='${lctre.closeTp == 2}'>selected</c:if>>관리자 승인</option>
								<option value="01" <c:if test='${lctre.closeTp == 1}'>selected</c:if>>선착순 승인</option>
						</select>
						</td>
					</tr>
					<tr>
						<th class="tdbg3 tc">신청자수 공개여부</th>
						<td class="tl" colspan="3">
						<input type="radio" name="rceptCntViewYn" value="Y" id="rceptCntViewY" ${lctre.rceptCntViewYn eq 'Y'?'checked':'' }/>
						<label for="rceptCntViewY">공개</label>
						<input type="radio" name="rceptCntViewYn" value="N" id="rceptCntViewN" ${lctre.rceptCntViewYn ne 'Y'?'checked':'' }/>
						<label for="rceptCntViewN">비공개</label>
						</td>
					</tr>
					<tr>
						<th class="tdbg3 tc">대상</th>
						<td class="tl" colspan="3">
							<jsp:include page="/WEB-INF/jsp/admin/edu/lctreRgs_target_inc.jsp" ></jsp:include>
						</td>
					</tr>
					<tr>
						<th class="tdbg3 tc">교육 금액</th>
						<td class="tl" colspan="3">
						<span class="dp_b"> 
						<label for="isFeeN"><input type="radio" name="isFee" id="isFeeN" checked />무료 </label>
						<label for="isFeeY"><input type="radio" name="isFee" id="isFeeY" />유료 </label>
						<!-- 
						(1인당 <input type="text" name="fee" id="fee" value="${lctre.fee}" class="ip5 mgl10 mgr5 onlyNumber tc" />원 )
						 -->
						<input type="hidden" name="fee" id="fee" value="${lctre.fee}"  />
						
						</span>
						</td>
					</tr>
					<tr style="display:none;">
						<th class="tdbg3 tc">납부 정보</th>
						<td class="tl" colspan="3">
						<div class="addrWrap">
						<!-- 
						식비 <input type="text" class="onlyNumber ip9" placeholder="" id="mealFee" name="mealFee" value="${lctre.mealFee}" />
						 -->
						
						식비납부계좌<input type="text" class=" ip2" placeholder="oo은행 000-00-00000, 예금주명" id="mealAccount" name="mealAccount" value="${lctre.mealAccount}" maxlength="50"/>
						<br/>
						<!-- 
						숙박비 <input type="text" class="onlyNumber ip9" placeholder="" id="dormiFee" name="dormiFee" value="${lctre.dormiFee}" />
						 -->
						
						숙박납부계좌<input type="text" class=" ip2" placeholder="oo은행 000-00-00000, 예금주명" id="dormiAccount" name="dormiAccount" value="${lctre.dormiAccount}" maxlength="50"/>
						<br/>
						납부기한<input type="text" id="depositLimitDt" name="depositLimitDt" value="${lctre.depositLimitDt}" class="datepicker input_calendar ip6 tc" readonly/> 
						</div>
						</td>
					</tr>

					<tr>
						<th class="tdbg3 tc">수료증 사용여부</th>
						<td class="tl" colspan="3"><input type="hidden" name="passIdx" id="passIdx" value="${lctre.passIdx }" /> 
						<input type="radio" name="passCertYn" value="N" id="passCertN" ${lctre.passCertYn ne 'Y'?'checked':'' } onclick="fn_reset(1)" />
							<label for="passCertN" class="pdl5 mgr15 cp">없음</label> 
							<input type="radio" name="passCertYn" value="Y" id="passCertY" ${lctre.passCertYn eq 'Y'?'checked':'' } />
							<label for="passCertY" class="pdl5 mgr15 cp">사용</label> ( &nbsp; <input type="text" name="passCertNm" id="passCertNm" class="ip1 mgr10" maxlength="400" value="${lctre.passCertNm eq null?'':lctre.passCertNm }" readonly />
							<button type="button" class="btn04 btn_orange" onclick="fn_passCertList();">선택</button> &nbsp; )</td>
					</tr>
					<tr style="display: none">
						<th class="tdbg3 tc">만족도 조사 사용유무</th>
						<td class="tl" colspan="3"><input type="hidden" name="fbIdx" id="fbIdx" value="${lctre.fbIdx }" /> 
						<input type="radio" name="fbYn" value="N" id="fbYnN" ${lctre.fbYn ne 'Y'?'checked':'' } onclick="fn_reset(2)" />
							<label for="fbYnN" class="pdl5 mgr15 cp">없음</label> <input type="radio" name="fbYn" value="Y" id="fbYnY" ${lctre.fbYn eq 'Y'?'checked':'' } />
							<label for="fbYnY" class="pdl5 mgr15 cp">사용</label> ( &nbsp; <input type="text" name="fbNm" id="fbNm" class="ip1 mgr10" maxlength="400" value=" ${lctre.fbTitle eq null?'':lctre.fbTitle }" readonly />
							<button type="button" class="btn04 btn_orange" onclick="fn_feedbackList();">선택</button> &nbsp; )</td>
					</tr>
					
					<tr>
						<th class="tdbg3 tc">내용 *</th>
						<td class="tl" colspan="3"><textarea id="ckeditCn" name="ckeditCn" placeholder="내용" class="w100 h500">${lctre.cn}</textarea> <input type="hidden" id="cn" name="cn" /></td>
					</tr>
					<tr>
						<th class="tdbg3 tc">수료기준</th>
						<td class="complete_wrap" colspan="3">
							<div class="comp_box mgr10">
								<p>총 교육시간</p>
								<input type="hidden" id="compStnd01" name="compStnd01" value="${lctre.compStnd01 }">
								
								<select id="compStnd01Hour" class="vb">
									<c:forEach begin="0" end="99" varStatus="stat">
										<fmt:formatNumber value="${stat.index}" pattern="00" var="hour" />
										<option value="${hour}" <c:if test="${fn:substring(lctre.compStnd01, 0, 2) == hour}">selected</c:if>>${hour}시</option>
									</c:forEach>
								</select> 
								<select id="compStnd01Min" class="vb ">
										<c:forEach var="m" begin="0" end="55" step="5"> 
											<fmt:formatNumber value="${m}" pattern="00" var="selMinute" />
											<option value="${selMinute}" <c:if test="${fn:substring(lctre.compStnd01, 2, 4) == selMinute}">selected</c:if>>${selMinute}분</option>
										</c:forEach>
								</select>
								
							</div>
							<div class="comp_box mgr10">
								<p>최소 이수시간</p>
								<input type="hidden" id="compStnd02" name="compStnd02" value="${lctre.compStnd02 }">
								<select id="compStnd02Hour" class="vb">
									<c:forEach begin="0" end="99" varStatus="stat">
										<fmt:formatNumber value="${stat.index}" pattern="00" var="hour" />
										<option value="${hour}" <c:if test="${fn:substring(lctre.compStnd02, 0, 2) == hour}">selected</c:if>>${hour}시</option>
									</c:forEach>
								</select> 
								<select id="compStnd02Min" class="vb ">
										<c:forEach var="m" begin="0" end="55" step="5"> 
											<fmt:formatNumber value="${m}" pattern="00" var="selMinute" />
											<option value="${selMinute}" <c:if test="${fn:substring(lctre.compStnd02, 2, 4) == selMinute}">selected</c:if>>${selMinute}분</option>
										</c:forEach>
								</select>
							</div>
							<div class="comp_box">
								<p>시험</p>
								<input type="text" id="compStnd03" name="compStnd03" class="ip1 mgr10" maxlength="20" value="${lctre.compStnd03 }">
							</div>
							<div class="comp_box">
								<p>과제</p>
								<input type="text" id="compStnd04" name="compStnd04" class="ip1 mgr10" maxlength="20" value="${lctre.compStnd04 }">
							</div>
							<div class="comp_box">
								<p>설문</p>
								<input type="text" id="compStnd05" name="compStnd05" class="ip1 mgr10" maxlength="20" value="${lctre.compStnd05 }">
							</div>
						</td>
					</tr>
					<tr>
						<th class="tdbg3 tc">대표이미지</th>
						<td class="tl" colspan="3">
							<div class="write_div">
								<span id="photo_div"> <c:choose>
										<c:when test="${not empty lctre and (lctre.imgOrg != '' && lctre.imgRename != '')}">
											${lctre.imgOrg}&nbsp;&nbsp;
											<button class="btn04 btn_gray" onclick="fn_photoDel(); return false;">삭제</button>
										</c:when>
										<c:otherwise>
											<label for="imgFile">
												<span class="sound_only">파일</span>
											</label>
											<input type="file" name="imgFile" id="imgFile" title="파일첨부" class="frm_file ip2">
										</c:otherwise>
									</c:choose>
								</span>
								<input type="hidden" name="imgUseYn" id="imgUseYn" />
								<!--// 생략시 기본 이미지 default_img.png //-->
								<br />
								<p class="fc_orange font_12 pdt5">이미지는 400px * 250px 사이즈로 등록해주세요.</p>
							</div>
						</td>
					</tr>
					<tr>
						<th class="tdbg3 tc"><label for="eduNm">문의전화</label></th>
						<td class="tl" colspan="3">
						<%-- 
						<input type="text" id="tel1" class="ip5" maxlength="3" size="3" onkeyup="moveOnMax(this, document.getElementById('tel2'))" required> - 
						<input type="text" id="tel2" class="ip5" maxlength="4" size="4" onkeyup="moveOnMax(this, document.getElementById('tel3'))" required> - 
						<input type="text" id="tel3" class="ip5" maxlength="4" size="4" onkeyup="formatPhoneNumber()" required> &nbsp; &nbsp; &nbsp; 
						담당자 이름 : <input type="text" id="inqNm" name="inqNm" value="${lctre.inqNm}" class="ip5" maxlength="10" size="4"> 
						<input type="hidden" name="tel" id="tel" value="${lctre.tel }"> 
						 --%>
						<%-- 
						--%>
						<input type="text" name="tel" id="tel" class="ip1 mgr10" maxlength="16" value="${lctre.tel }"/> 
						담당자 이름 : <input type="text" id="inqNm" name="inqNm" value="${lctre.inqNm}" class="ip5" maxlength="10" size="4"> 
						</td>
					</tr>
					<tr>
						<th class="tdbg3 tc">교육 장소</th>
						<td class="tl" colspan="3">
							<div id="area-offline" class="addrWrap">
								<input type="text" class="addr ip5 tc" placeholder="우편번호" id="postNo" name="postNo" value="${lctre.postNo}" />
								<button type="button" class="btn04 btn_gray" onclick="fn_postSearch(); return false;">검색</button>
								<button type="button" class="btn04 btn_blue" onclick="fn_setDefaultAddr(1); return false;">기본주소1</button>
								<!-- 
								<button type="button" class="btn04 btn_blue" onclick="fn_setDefaultAddr(2); return false;">기본주소2</button>
								 -->
								<br>
								<input type="text" class="addr1 ip2" placeholder="도로명주소" id="addr" name="addr" value="${lctre.addr}" />
								<br />
								<input type="text" class="addr3 ip2" placeholder="상세주소" id="addrDetail" name="addrDetail" value="${lctre.addrDetail}" />
<%-- 								<input type="text" class="addr4 ip1" placeholder="태안연수원" id="addrEtc" name="addrEtc" value="${lctre.addrEtc}" readonly /> --%>
<%-- 								<a href="javascript:window.open('${utcp.ctxPath }/comm/popup/tempMap.do?addr='+$('#addr').val(),'tempMap','scrollbar=n,width=800,height=600')" class="btn04 btn_grayl"> <i class="fas fa-map-marker-alt"></i>약도 --%>
<!-- 								</a> -->
							</div> <input type="text" class="ip1" id="addrMemo" name="addrMemo" placeholder="지역(배너)" value="${lctre.addrMemo}" />
							<br>
						</td>
					</tr>
					<tr id="area-waycome">
						<th class="tdbg3 tc"><label for="eduNm">오시는 길</label></th>
						<td class="tl" colspan="3"><textarea name="wayCome" style="width: 100%; height: 100px;">${lctre.wayCome }</textarea></td>
					</tr>
					<tr>
						<th>안내사항</th>
						<td>
							<%-- 임시주석250326
							<c:set var="tmp_eduEtc" value='<%="국민내일배움카드 과정은 <휴넷>과 <HRD-Net> 모두 신청해야 수강이 가능합니다.\n국민내일배움카드 과정에 교재는 포함되지 않습니다.\n국민내일배움카드 과정 결제시 쿠폰, 지식 포인트, 기프트카드 할인은 불가합니다." %>'/>
							 --%>
							<c:if test="${param.eduSeq > 0 }">
							<c:set var="tmp_eduEtc" value="${lctre.eduEtc }"/>
							</c:if>
							<textarea name="eduEtc" id="eduEtc" style="width: 100%; height: 100px;"><c:out value="${tmp_eduEtc }" escapeXml="false"/></textarea>
						</td>
					</tr>

					<tr style="">
						<th class="tdbg3 tc"><label for="eduNm">신청폼 추가</label></th>
						<td class="tl" colspan="3"><jsp:include page="/WEB-INF/jsp/admin/edu/lctreRgs_inc_iem.jsp"></jsp:include></td>
					</tr>
					<%-- 
					<tr style="">
						<th class="tdbg3 tc"><label for="eduNm">비밀번호 사용 여부</label></th>
						<td class="tl" colspan="3">
							<input type="radio" name="scrtyYn" value="N" id="scrtyN" ${lctre.scrtyYn eq 'N' or empty lctre.scrtyYn ?'checked':'' } /><label for="scrtyN" class="pdl5 mgr15 cp">사용안함</label>
							<input type="radio" name="scrtyYn" value="Y" id="scrtyY" ${lctre.scrtyYn eq 'Y'?'checked':'' } /><label for="scrtyY" class="pdl5 mgr15 cp">사용함</label>
							<span id="area-scrtyPw">
							<input type="password" name="scrtyPw" id="scrtyPw" class="ip1" placeholder="비밀번호"/>
							<input type="password" name="scrtyPwConfirm" id="scrtyPwConfirm" class="ip1" placeholder="비밀번호 확인"/>
							※비밀번호 미입력시 수정되지 않습니다.
							</span>
						</td>
					</tr>
					
					<tr style="">
						<th class="tdbg3 tc"><label for="eduNm">첨부파일 필수 여부</label></th>
						<td class="tl" colspan="3">
							<input type="radio" name="essntlFileYn" value="N" id="essntlFileN" ${lctre.essntlFileYn eq 'N'?'checked':'' } /><label for="essntlFileN" class="pdl5 mgr15 cp">사용안함</label>
							<input type="radio" name="essntlFileYn" value="Y" id="essntlFileY" ${lctre.essntlFileYn eq 'Y'?'checked':'' } /><label for="essntlFileY" class="pdl5 mgr15 cp">사용함</label>
						</td>
					</tr>
					 --%>
					<tr style="display: none;">
						<th class="tdbg3 tc"><label for="eduNm">기타 동의 여부 *</label></th>
						<td class="tl" colspan="3"><input type="radio" name="etcAgreeYn" value="N" id="etcAgreeN" ${lctre.etcAgreeYn eq 'N'?'checked':'' } />
							<label for="etcAgreeN" class="pdl5 mgr15 cp">사용안함</label> <input type="radio" name="etcAgreeYn" value="Y" id="etcAgreeY" ${lctre.etcAgreeYn eq 'Y'?'checked':'' } />
							<label for="etcAgreeY" class="pdl5 mgr15 cp">사용함</label></td>
					</tr>
					<tr>
						<th class="tdbg3 tc"><label>첨부파일</label></th>
						<td class="tl" colspan="">
							<!-- dropzone html -->
							<div class="dropzone" id="area-dropzone">
								<div class="fallback">
									<input type="file" name="file" multiple />
								</div>
								<input name="image_url" id="image_url" type="hidden" />
							</div>
							<div id="area-filelist">
								<ul>
									<c:forEach items="${attachList01 }" var="o">
										<li>${o.fileOrg },
											<a href="#none" onclick="fn_delFile('${o.fileSeq}')">[삭제]</a>
										</li>
									</c:forEach>
								</ul>
							</div>
						</td>
					</tr>
				</tbody>
			</table>

			<div class="tc">
				<button type="button" class="btn01 btn_greenl mgb20" onclick="fn_rgs();">저장</button>
				<button type="button" class="btn01 btn_grayl mgb20" onclick="fn_cancel_lctreRgs()">취소</button>
			</div>
		</form>
	</div>
</section>

<!--// 강사등록 모달//-->
<div class="remodal" data-remodal-id="groupRegist" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc">
	<button data-remodal-action="close" class="remodal-close" aria-label="Close"></button>
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC"><i class="fas fa-chevron-circle-right font_22 pdr5"></i>강사조회</p>
		</div>
		<div class="modal-body">
			<div class="dp_ib tc pd15">
				<label for="srchBelong" class="sound_only">검색어입력</label>
				<input type="text" id="srchBelong" placeholder="아이디,성명 검색" class="btn04 btn_blackl tl mgr5" />
				<button type="button" class="btn04 btn_black fr" onclick="fn_bulkRgs();">검색</button>
			</div>
			<div class="tbBox">
				<table class="tc w100 tb">
					<thead bgcolor="#f7f8fa">
						<tr>
							<th>&nbsp;</th>
							<th>NO</th>
							<th>아이디</th>
							<th>성명</th>
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
<!--// 강사등록 모달//-->
<!--// 필수선행교육등록 모달 //-->
<div class="remodal" data-remodal-id="lecturRegist" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc">
	<button data-remodal-action="close" class="remodal-close" aria-label="Close"></button>
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC"><i class="fas fa-chevron-circle-right font_22 pdr5"></i>필수 선행 교육 조회</p>
		</div>
		<div class="modal-body">
			<div class="dp_ib tc pd15">
				<label for="srchlec" class="sound_only">검색어입력</label>
				<input type="text" id="srchlec" placeholder="교육명 카테고리 검색" onKeyDown="if (event.keyCode === 13) fn_lctreList();" class="btn04 btn_blackl tl mgr5" />
				<button type="button" class="btn04 btn_black fr" onclick="fn_lctreList();">검색</button>
			</div>
			<div class="tbBox">
				<table class="tc w100 tb">
					<thead bgcolor="#f7f8fa">
						<tr>
							<th>&nbsp;</th>
							<th>카테고리</th>
							<th>상세카테고리</th>
							<th>교육명</th>
							<th width=57>모집 인원</th>
							<th width=100>접수상태</th>
							<th width=57>노출상태</th>
							<th>강사</th>
						</tr>
					</thead>
					<tbody id="lectureList">

					</tbody>
				</table>
			</div>
		</div>
		<div class="modal-footer">
			<div class="tc">
				<button onclick="fn_lectureRgs();" class="remodal-confirm btn01 btn_green">등록</button>
			</div>
		</div>
	</div>
</div>
<!--// 필수선행교육등록 모달 //-->
<!--// 만족도 조사지 모달 //-->
<div class="remodal" data-remodal-id="feedbackRegist" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc">
	<button data-remodal-action="close" class="remodal-close" aria-label="Close"></button>
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC"><i class="fas fa-chevron-circle-right font_22 pdr5"></i>만족도 조사 조회</p>
		</div>
		<div class="modal-body">
			<div class="dp_ib tc pd15">
				<label for="srchfb" class="sound_only">검색어입력</label>
				<input type="text" id="srchfb" placeholder="만족도 조사, 등록자 검색" onKeyDown="if (event.keyCode === 13) fn_feedbackList();" class="btn04 btn_blackl tl mgr5" />
				<button type="button" class="btn04 btn_black fr" onclick="fn_feedbackList();">검색</button>
			</div>
			<div class="tbBox">
				<table class="tc w100 tb">
					<thead bgcolor="#f7f8fa">
						<tr>
							<th>&nbsp;</th>
							<th>만족도 조사명</th>
							<th >객관식</th>
							<th >주관식</th>
							<th>등록자</th>
						</tr>
					</thead>
					<tbody id="feedbackList">

					</tbody>
				</table>
			</div>
		</div>
		<div class="modal-footer">
			<div class="tc">
				<button onclick="fn_feedbackRgs();" class="remodal-confirm btn01 btn_green">등록</button>
			</div>
		</div>
	</div>
</div>
<!--// 만족도 조사지 모달 //-->
<!--// 수료증 모달 //-->
<div class="remodal" data-remodal-id="passCertRegist" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc">
	<button data-remodal-action="close" class="remodal-close" aria-label="Close"></button>
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC"><i class="fas fa-chevron-circle-right font_22 pdr5"></i>수료증 조회</p>
		</div>
		<div class="modal-body">
			<div class="dp_ib tc pd15">
				<label for="srchpc" class="sound_only">검색어입력</label>
				<input type="text" id="srchpc" placeholder="수료증 검색" onKeyDown="if (event.keyCode === 13) fn_passCertList();" class="btn04 btn_blackl tl mgr5" />
				<button type="button" class="btn04 btn_black fr" onclick="fn_passCertList();">검색</button>
			</div>
			<div class="tbBox">
				<table class="tc w100 tb">
					<thead bgcolor="#f7f8fa">
						<tr>
							<th>&nbsp;</th>
							<th>수료증명</th>
							<th>발급기관명</th>
							<th>등록일</th>
						</tr>
					</thead>
					<tbody id="passCertList">

					</tbody>
				</table>
			</div>
		</div>
		<div class="modal-footer">
			<div class="tc">
				<button onclick="fn_passCertRgs();" class="remodal-confirm btn01 btn_green">등록</button>
			</div>
		</div>
	</div>
</div>
<!--// 수료증 모달 //-->
<div class="remodal messagePop messagePop2" data-remodal-id="message" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc">
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC">알림</p>
		</div>
		<div class="modal-body">
			<p class="messageTxt" id="messageStr"></p>
		</div>
		<input type="hidden" id="inputId" />
		<div class="modal-footer">
			<div class="tc">
				<button data-remodal-action="close" onclick="fn_focus(); return false;" class="remodal-confirm btn02 btn_orange">확인</button>
			</div>
		</div>
	</div>
</div>

<script>
$('#isFeeY').click(function(){
	$('#fee').attr('disabled',false);
	$('#fee').val(1);
});
$('#isFeeN').click(function(){
	$('#fee').attr('disabled',true);
	$('#fee').val(0);
});
if('${lctre.fee}'=='' || '${lctre.fee}'=='0'){
	$('#isFeeN').prop('checked',true);
	$('#fee').attr('disabled',true);
}else{
	$('#isFeeY').prop('checked',true);
}
/*
 * 번호입력
 */
function moveOnMax(current, nextFieldID) {
    if (current.value.length >= current.maxLength) {
        nextFieldID.focus();
    }
}
/*
 * 번호출력
 if(document.getElementById("tel").value){
	 var telValue = document.getElementById("tel").value;
	 var telArr = telValue.split("-"); // 전화번호를 "-"로 자른 배열
	 document.querySelector("#tel1").value=telArr[0];
	 document.querySelector("#tel2").value=telArr[1];
	 document.querySelector("#tel3").value=telArr[2];
 }
 */
 

</script>

<!-- 카테고리관련 -->
<script>
var ctg1SeqSelector = document.querySelector("#ctg1Seq");
var ctg2SeqSelector = document.querySelector("#ctg2Seq");
ctg1SeqSelector.addEventListener("change",function(){
	fn_getChildCtgrySeqList('ctg2Seq',this.value);
});
ctg2SeqSelector.addEventListener("change",function(){
	fn_getChildCtgrySeqList('ctg3Seq',this.value);
});

//자식카테고리 조회해서 셀렉트박스에 세팅
function fn_getChildCtgrySeqList(_id,ctgrySeq){
	//자식카테고리 가져옴
	$.ajax({
        url: "${utcp.ctxPath}/admin/edu/getCategoryChildList.json",
        type: "POST",
        data: { "parentSeq" : ctgrySeq },
        cache: false,
		async: true, 
        success: function(dataList) {
        	$("#"+_id).empty();
        	if(_id == 'ctg2Seq'){
	        	$("#"+_id).append("<option value=\"0\">2차 카테고리</option>"	);
	        	$("#ctg3Seq").empty();
	        	$("#ctg3Seq").append("<option value=\"0\">3차 카테고리</option>");
        	}else{
        		$("#"+_id).append("<option value=\"0\">3차 카테고리</option>"	);
        	}
        	if(dataList != null && dataList != undefined && dataList != "") {
        		$(dataList).each(function(i, e) {
        			$("#"+_id).append(
        				"<option value=\"" + this.ctgrySeq + "\">" + this.ctgryNm + "</option>"
        			);
        		});
        	}
		}
    });
}
</script>


<!-- dropzone -->
<script>
	Dropzone.autoDiscover = false;
	myDropzone = new Dropzone("#area-dropzone", {
		url : "${utcp.ctxPath}/admin/edu/lctreDropzoneProc.ajax",
		paramName: "dropzoneFiles",
        maxFilesize: 5,
        maxFiles: 5,
        //acceptedFiles: "image/*,application/pdf,.xlsx",
        //acceptedFiles: null,	// dykim, 210701, 모든 파일 업로드 가능
        autoProcessQueue: false,
        uploadMultiple: true,
        addRemoveLinks:true,
        parallelUploads:10,
        dictDefaultMessage:'+ 마우스로 파일을 끌고 오거나 여기를 클릭하세요',
        init: function () {
        	this.on("success", function(file, returnedData, myEvent) {
              	location.href='lctreView.do?eduSeq='+$('#eduSeq').val();
              	opener.parent.location.reload();
           	}); 
        	this.on('sending', function(file, xhr, formData) {
        		if(formData.get('eduSeq')==null){
        			formData.append('eduSeq', $('#eduSeq').val());
        		}
           	});  
        }
	});
	function fn_delFile(fileSeq){
		$.ajax({
			url:'deleteDropzoneFileLctre.ajax',
			data:{fileSeq:fileSeq, eduSeq :  $('#eduSeq').val()},
			success:function(r){
				if(r.result==1){
					location.reload();
				}
			}
		});
	}
</script>
