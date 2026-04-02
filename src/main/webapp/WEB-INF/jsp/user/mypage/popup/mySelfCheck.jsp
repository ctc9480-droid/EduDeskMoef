<%@page import="com.educare.edu.member.service.SessionUserInfoHelper"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp" %>
<script>
var arCsiIdx = new Array();
var arCsdDispNo = new Array();
var arCsdUserCheck = new Array(); 
var arcsdSelfMemo = new Array();
var arcsdProfessMemo = new Array();
var selStepType = "소방";
var crBusYn = "";

	$(document).ready(function(){
		var inspectDt = "${ csMainInfo.csInspectDt }";
		inspectDt = inspectDt.split("-");
		//onsole.log(inspectDt);

		crBusYn = "${ crInfo.crBusYn }";
		$("#cs_inspect_dt_y").val(inspectDt[0]);
		$("#cs_inspect_dt_m").val(inspectDt[1]);
		$("#cs_inspect_dt_d").val(inspectDt[2]);
		
		// dykim, 초기 입력상태 저장
		fn_saveDataExtract(selStepType);
		
		$('[id^=area-homeicon]').hide();
		$('[id=area-homeicon-1-5]').show();
		$('[id=area-homeicon-1-6]').show();
		$('[id=area-homeicon-1-7]').show();
		$('[id=area-homeicon-2-1]').show();
		$('[id=area-homeicon-2-2]').show();
		$('[id=area-homeicon-2-3]').show();
		$('[id=area-homeicon-2-4]').show();
		$('[id=area-homeicon-2-5]').show();
		$('[id=area-homeicon-2-14]').show();
		$('[id=area-homeicon-3-1]').show();
		$('[id=area-homeicon-4-1]').show();
		$('[id=area-homeicon-4-2]').show();
		$('[id=area-homeicon-4-3]').show();
		$('[id=area-homeicon-4-4]').show();
		$('[id=area-homeicon-5-1]').show();
		$('[id=area-homeicon-5-2]').show();
		$('[id=area-homeicon-6-1]').show();
		$('[id=area-homeicon-6-2]').show();
		$('[id=area-homeicon-8-1]').show();
		$('[id=area-homeicon-8-2]').show();
		$('[id=area-homeicon-8-3]').show();
		$('[id=area-homeicon-9-1]').show();
	});

	function fn_areaShow(val, idx) {
		selStepType = val;
		
		if(val == "소방") {
//			$("#prevStep").val(1);
			$("#selFire").val(idx);
			$("#fireArea").css("display","");
			$("#fireActive").addClass("active");
			$("#trafficActive").removeClass();
			$("#itemType").val("소방");
			
			$("#trafficArea").css("display","none");
			fn_displayStep('소방', idx);
		} else {
//			$("#prevStep").val(1);
			$("#selTraffic").val(idx);
			$("#fireArea").css("display","none");
			$("#fireActive").removeClass();
			$("#trafficActive").addClass("active");
			$("#itemType").val("교통");

			$("#trafficArea").css("display","");
			fn_displayStep('교통', idx);
		}	
	} 
	
	function fn_displayStep(stepType, idx) {
		var prevType = $("#prevType").val();
		var prevStep = $("#prevStep").val();
		
		if(stepType != prevType) {
			$("#prevType").val(stepType);

			if(!fn_dataExtract(prevType, prevStep, false)) {
				if(confirm("입력하신 데이터가 존재합니다.\n저장하시겠습니까?")) {
					fn_formReg(prevStep, prevType);
					
					//return false;
				}
			}
		} else {
			if(!fn_dataExtract(stepType, prevStep, false)) {
				if(confirm("입력하신 데이터가 존재합니다.\n저장하시겠습니까?")) {
					fn_formReg(prevStep, stepType);
					
					//return false;
				}
			}
		}

		$("#prevStep").val(idx);		
		
		fn_showStepItem(stepType);
		
		// dykim, 초기 입력상태 저장
		fn_saveDataExtract(stepType);
	}

	function fn_showStepItem(stepType) {
		var idx = 0;
		var num = 0;
		if(stepType == "소방") {
			num = $("#selFire option").length;
			idx = $("#selFire option:selected").val();
		} else {
			num = $("#selTraffic option").length;
			idx = $("#selTraffic  option:selected").val();
		}
		
		for(var i=1; i<=num; i++) {
			if(stepType == "소방") {
				if(idx != i) {
					$("#fire"+i).css("display","none");
				} else {
					$("#fire"+idx).css("display","block");
				}			
			} else {
				if(idx != i) {
					$("#traffic"+i).css("display","none");
				} else {
					$("#traffic"+idx).css("display","block");
				}			
			}
		}
	}

	
	function fn_showStepItem2() {
		var idx = 0;
		var num = 0;
		
		if(selStepType == "소방") {
			num = $("#selFire option").length;
			idx = $("#selFire  option:selected").val();
		} else {
			num = $("#selTraffic option").length;
			idx = $("#selTraffic  option:selected").val();
		}
		
		
		for(var i=1; i<=num; i++) {
			if(selStepType == "소방") {
				if(idx != i) {
					$("#fire"+i).css("display","none");
				} else {
					$("#fire"+idx).css("display","block");
				}			
			} else {
				if(idx != i) {
					$("#traffic"+i).css("display","none");
				} else {
					$("#traffic"+idx).css("display","block");
				}			
			}
		}
		opener.location.reload();
	}
	
	function fn_saveDataExtract(stepType) {
		// dykim, 초기 입력상태 저장
		if(stepType == "소방") {
			$("#csd_step").val( $("#selFire  option:selected").val() );
		} else {
			$("#csd_step").val( $("#selTraffic  option:selected").val() );
		}
		
		fn_dataExtract(stepType, $("#csd_step").val(), true);
	}
	
	var saveArCsdUserCheck = new Array(); 
	var saveArCsdSelfMemo = new Array();
	var saveArCsdProfessMemo = new Array();
	
	function fn_dataExtract(stepType, idx, saveFlag) {
		arCsiIdx = new Array();
		arCsdDispNo = new Array();
		arCsdUserCheck = new Array(); 
		arCsdSelfMemo = new Array();
		arCsdProfessMemo = new Array();
		
		prevIdx = $("#prevStep").val();
		//onsole.log("fn_dataExtract - 이전 인덱스"+prevIdx+", 분야:"+stepType);
		
		if(stepType == "소방") {
			for(var i=1; i<=$("#itemSize_f_"+prevIdx).val(); i++) {
				var csiIdx = $("#csi_idx_f_"+prevIdx+"_"+i).val();
				arCsiIdx.push(csiIdx);
				
				var dispNo = $("#csd_disp_no_f_"+prevIdx+"_"+i).val();
				arCsdDispNo.push(dispNo);
				
				var radioVal = $("input[name=csdUserCheck_f_"+prevIdx+"_"+i+"]:checked").val();
				if(radioVal != undefined) {
					arCsdUserCheck.push(radioVal);
				} else {
					arCsdUserCheck.push("");
				}
				
				var sMemo = $("textarea[name=csdSelfMemo_f_"+prevIdx+"_"+i+"]").val();
				if(sMemo != undefined && sMemo != "") {
					arCsdSelfMemo.push(sMemo);
				} else {
					arCsdSelfMemo.push("");
				}
				
				var fMemo = $("textarea[name=csdProfessMemo_f_"+prevIdx+"_"+i+"]").val();
				if(fMemo != undefined && fMemo != "") {
					arCsdProfessMemo.push(fMemo);
				} else {
					arCsdProfessMemo.push("");
				}
			}
			
		} else {
			for(var i=1; i<=$("#itemSize_t_"+prevIdx).val(); i++) {
				var csiIdx = $("#csi_idx_t_"+prevIdx+"_"+i).val();
				arCsiIdx.push(csiIdx);

				var dispNo = $("#csd_disp_no_t_"+prevIdx+"_"+i).val();
				arCsdDispNo.push(dispNo);
				
				var radioVal = $("input[name=csdUserCheck_t_"+prevIdx+"_"+i+"]:checked").val();
				if(radioVal != undefined) {
					arCsdUserCheck.push(radioVal);
				} else {
					arCsdUserCheck.push("");
				}
				
				var sMemo = $("textarea[name=csdSelfMemo_t_"+prevIdx+"_"+i+"]").val();
				if(sMemo != undefined && sMemo != "") {
					arCsdSelfMemo.push(sMemo);
				} else {
					arCsdSelfMemo.push("");
				}
				
				var fMemo = $("textarea[name=csdProfessMemo_t_"+prevIdx+"_"+i+"]").val();
				if(fMemo != undefined && fMemo != "") {
					arCsdProfessMemo.push(fMemo);
				} else {
					arCsdProfessMemo.push("");
				}
			}
		}

		var bBlank = true;

		//onsole.log("점검사항 :"+arCsdUserCheck+" / 점검사용자의견:"+arCsdSelfMemo+" / 점검전문가소견:"+arCsdProfessMemo);
		
		if (saveFlag) {
			saveArCsdUserCheck = [];
			saveArCsdSelfMemo = [];
			saveArCsdProfessMemo = [];
			for(var j=0; j<arCsdDispNo.length; j++) {
				saveArCsdUserCheck[j] = arCsdUserCheck[j];
				saveArCsdSelfMemo[j] = arCsdSelfMemo[j];
				saveArCsdProfessMemo[j] = arCsdProfessMemo[j];
			}
			//saveArCsdUserCheck = arCsdUserCheck.filter(() => true); 	 
			//saveArCsdSelfMemo = arCsdSelfMemo.filter(() => true);	//[...arCsdSelfMemo];
			//saveArCsdProfessMemo = arCsdProfessMemo.filter(() => true);	//[...arCsdProfessMemo];
			
			//onsole.log("점검사항 save:"+saveArCsdUserCheck+" / 점검사용자의견:"+saveArCsdSelfMemo+" / 점검전문가소견:"+saveArCsdProfessMemo);
			return true;
		}
		//onsole.log("점검사항 save:"+saveArCsdUserCheck+" / 점검사용자의견:"+saveArCsdSelfMemo+" / 점검전문가소견:"+saveArCsdProfessMemo);
		
		if($("#userLevel").val() == 9) {
			for(var j=0; j<arCsdDispNo.length; j++) {
				//if( (arCsdUserCheck[j] != undefined && arCsdUserCheck[j] != "") || arCsdSelfMemo[j] != ""  ) {
				if( arCsdUserCheck[j] != saveArCsdUserCheck[j] || arCsdSelfMemo[j] != saveArCsdSelfMemo[j]  ) {	
					bBlank = false;
					break;
				}
			}
		} else if($("#userLevel").val() == 4) {
			for(var j=0; j<arCsdDispNo.length; j++) {
				//if( arCsdProfessMemo[j] != ""  ) {
				if( arCsdProfessMemo[j] != saveArCsdProfessMemo[j]  ) {
					bBlank = false;
					break;
				}
			}
		} else {
			for(var j=0; j<arCsdDispNo.length; j++) {
				//if( (arCsdUserCheck[j] != undefined && arCsdUserCheck[j] != "") || arCsdSelfMemo[j] != "" || arCsdProfessMemo[j] != "" ) {
				if(  arCsdUserCheck[j] != saveArCsdUserCheck[j] || arCsdSelfMemo[j] != saveArCsdSelfMemo[j] || arCsdProfessMemo[j] != saveArCsdProfessMemo[j] ) {
					bBlank = false;
					break;
				}
			}
		}
		
		
		//onsole.log("blankd:"+bBlank);
		
		if(bBlank == false) {
			$("#arrCsdDispNo").val(arCsdDispNo);
			$("#arrCsdUserCheck").val(arCsdUserCheck);
			$("#arrCsdSelfMemo").val(arCsdSelfMemo);
			$("#arrCsdProfessMemo").val(arCsdProfessMemo);
			
			return false;
		} else {
			return true; 
		}
	}
	
	//단계 변경 시 저장 처리
	function fn_formReg(idx, stepType) {
		var csStatus = "작성중";
		$("#csd_gubun").val(stepType);
		$("#csd_step").val($("#prevStep").val());
		$("#csInspectDt").val( $("#cs_inspect_dt_y").val()+"-"+$("#cs_inspect_dt_m").val()+"-"+$("#cs_inspect_dt_d").val() );
		
		var jsonData = { 
						"csIdx":$("#cs_idx").val(), "csInspectDt":$("#csInspectDt").val(), "csInspectNm":$("#cs_inspect_nm").val(), "csAgencyNm": $("#cs_agency_nm").val()
						,"csAgencyCity":$("#cs_agency_city").val(), "csAgencyGu":$("#cs_agency_gu").val(), "csAgencyTel":$("#cs_agency_tel").val()
						,"cbIdx":$("#cb_idx").val(), "crIdx":$("#cr_idx").val(), "csdGubun":$("#csd_gubun").val(), "csdStep":$("#csd_step").val()
						,"arrCsiIdx":arCsiIdx ,"arrCsdDispNo":arCsdDispNo, "arrCsdUserCheck":arCsdUserCheck, "arrCsdSelfMemo":arCsdSelfMemo, "arrCsdProfessMemo":arCsdProfessMemo
						,"csStatus": csStatus
				   	   };
		
		//onsole.log(JSON.stringify(jsonData));
		
		$.ajax({
			url:'/consult/selfcheck/selfcheckWriteProc.do',
			data:jsonData,
			method:'post',
			success:function(r){
				//onsole.log(r);
				$("#cs_idx").val(r.retMap.csIdx);
				
				$("#strPopupTxt1").html("저장되었습니다.");
				location.href = "#cstScRgsFin";
			}
		});
	}

	
	//저장버튼 클릭 시
	function fn_saveBtn() {
		$("#csd_gubun").val(selStepType);
		var csStatus = "작성중";
		
		if(selStepType == "소방") {
			$("#csd_step").val( $("#selFire  option:selected").val() );
		} else {
			$("#csd_step").val( $("#selTraffic  option:selected").val() );
		}

		if(crBusYn == "운영") {
			if(selStepType == "교통" && $("#csd_step").val() == "4") {
				csStatus = "작성완료";
			}
		} else {
			if(selStepType == "소방" && $("#csd_step").val() == "9") {
				csStatus = "작성완료";
			}
		}
		
		//if( fn_dataExtract(selStepType, $("#csd_step").val()) ) {
		//	if(!confirm("입력하신 데이터가 없습니다\n저장하시겠습니까?")) return false;
		//}
		
		
		$("#csInspectDt").val( $("#cs_inspect_dt_y").val()+"-"+$("#cs_inspect_dt_m").val()+"-"+$("#cs_inspect_dt_d").val() );
		
		fn_saveDataExtract(selStepType);
		if(!confirm("저장하시겠습니까?")) return false;
		
		var jsonData = { 
						"csIdx":$("#cs_idx").val(), "csInspectDt":$("#csInspectDt").val(), "csInspectNm":$("#cs_inspect_nm").val(), "csAgencyNm": $("#cs_agency_nm").val()
						,"csAgencyCity":$("#cs_agency_city").val(), "csAgencyGu":$("#cs_agency_gu").val(), "csAgencyTel":$("#cs_agency_tel").val()
						,"cbIdx":$("#cb_idx").val(), "crIdx":$("#cr_idx").val(), "csdGubun":$("#csd_gubun").val(), "csdStep":$("#csd_step").val()
						,"arrCsiIdx":arCsiIdx ,"arrCsdDispNo":arCsdDispNo, "arrCsdUserCheck":arCsdUserCheck, "arrCsdSelfMemo":arCsdSelfMemo, "arrCsdProfessMemo":arCsdProfessMemo
						,"csStatus": csStatus
				   	   };
		console.log(JSON.stringify(jsonData));

	
		
		$.ajax({
			url:'/consult/selfcheck/selfcheckWriteProc.do',
			data:jsonData,
			method:'post',
			success:function(r){
				console.log(r);
				$("#cs_idx").val(r.retMap.csIdx);
				
				// dykim, 초기 입력상태 저장
				fn_saveDataExtract(selStepType);
				
				$("#strPopupTxt1").html("저장되었습니다.");
				location.href = "#cstScRgsFin";
			}
		});
		
	}
	
	
	function fn_selfcheckComplete(idx) {
		$.ajax({
			url:'/consult/selfcheck/selfcheckComplete.do',
			data:{"csIdx":idx},
			method:'post',
			success:function(r){
				console.log(r);
				
				$("#strPopupTxt1").html("완료되었습니다.");
				location.href = "#cstScRgsFin";
			}
		});
	}
	
	function fn_PrevBtn() {
		var prevStep = 0;
		var curStep = 0;
		
		if(selStepType == "소방") {
			curStep = $("#selFire option:selected").val(); 
		} else {
			curStep = $("#selTraffic option:selected").val();
		}
		
		prevStep = parseInt(curStep) - 1;
		$("#prevStep").val(curStep);
		console.log(selStepType+","+prevStep+","+curStep);
		
		if( parseInt(prevStep) <= 0) {
			if(selStepType == "교통") {
				var idx = $("#selFire option").length;
				fn_areaShow("소방", idx);
			} else {
				$("#strPopupTxt2").html("첫 페이지 입니다.");
				location.href = "#onlyPopupMsg";
			}
		} else {
			if(selStepType == "소방") {
				$("#selFire").val(prevStep);
				fn_displayStep('소방', prevStep);
			} else {
				$("#selTraffic").val(prevStep);
				fn_displayStep('교통', prevStep);
			}
		}
	}
	
	function fn_NextBtn() {
		var itemlen = 0;
		var nextStep = 0;
		var curStep = 0;
		
		if(selStepType == "소방") {
			itemlen = $("#selFire option").length;
			curStep = $("#selFire option:selected").val(); 
		} else {
			itemlen = $("#selTraffic option").length;
			curStep = $("#selTraffic option:selected").val();
		}
		
		$("#prevStep").val(curStep);
		nextStep = parseInt(curStep) + 1;
		console.log(selStepType+","+nextStep+","+curStep+","+itemlen);
		
		if(selStepType == "소방") {
			if( parseInt(nextStep) > parseInt(itemlen) ) {
				if(crBusYn == "운영") {
					fn_areaShow("교통",1);	
				} else {
					$("#strPopupTxt2").html("마지막 페이지 입니다.");
					location.href = "#onlyPopupMsg";
				}
				
			} else {
				$("#selFire").val(nextStep);
				fn_displayStep('소방', nextStep);
			}
		} else {
			if( parseInt(nextStep) > parseInt(itemlen) ) {
				$("#strPopupTxt2").html("마지막 페이지 입니다.");
				location.href = "#onlyPopupMsg";
			} else {
				$("#selTraffic").val(nextStep);
				fn_displayStep('교통', nextStep);
			}
		}
	}
	
	function fn_print(){
		if(selStepType=='소방'){
			$('#fireArea').show();
			$('.cont_box').show();
		}else{
			$('#trafficArea').show();
			$('.cont_box').show();
		}
		window.print();
		//#fireArea
		//#trafficArea
		//.cont_box1
	}
	window.onafterprint = function() {
		//location.reload();
		fn_showStepItem(selStepType);
	};
	
</script>
			<form id="reg-form" name="regForm" method="post">
				<input type="hidden" name="csIdx" id="cs_idx" value="${empty csMainInfo ? "0" : csMainInfo.csIdx }" /> <!-- 자가점검순번 -->
				<input type="hidden" name="csInspectDt" id="csInspectDt" value="" /> <!-- 점검일시 -->
				<input type="hidden" name="csStatus" id="csStatus" value="미입력" /> <!-- 입력진행상태 -->
				<input type="hidden" name="cbIdx" id="cb_idx" value="${ crInfo.cbIdx }" /> <!-- 컨설팅등록순번 -->
				<input type="hidden" name="crIdx" id="cr_idx" value="${ crInfo.crIdx }" /> <!-- 컨설팅신청순번 -->
				<input type="hidden" name="csiIdx" id="csi_idx" value="" /> <!-- 자가점검아이템순번 -->
				<input type="hidden" name="csdGubun" id="csd_gubun" value="" /> <!-- 구분:소방/교통 -->
				<input type="hidden" name="csdStep" id="csd_step" value="" /> <!-- 점검단계 -->
				<input type="hidden" name="arrCsdDispNo[]" id="arrCsdDispNo" value="" /> <!-- 점검별세부단계배열 -->
				<input type="hidden" name="arrCsdUserCheck[]" id="arrCsdUserCheck" value="" /> <!-- 점검사항배열 -->
				<input type="hidden" name="arrCsdSelfMemo[]" id="arrCsdSelfMemo" value="" /> <!-- 자체점검의견사항배열 -->
				<input type="hidden" name="arrCsdProfessMemo[]" id="arrCsdProfessMemo" value="" /> <!-- 전문가컨설팅소견배열 -->
			</form>
			
            <div class="sub_txt">
                <span class="dp_ib"><img src="${utcp.ctxPath}/resources/user/image/icon/icon_subtitle.png" alt="서브타이틀 아이콘" />온라인 컨설팅 자가 점검표</span>
                <span class="sub_txt_span">※ 반드시 게시판에 있는 컨설팅 가이드 영상을 시청 후 자가 점검해주세요!</span>
            </div>
			
            <div class="listWrap cst" style="padding-top: 0">
                <div id="viewDiv" >
                    <form action="#" method="post">
					<input type="hidden" id="prevStep" value="1" />
					<input type="hidden" id="prevType" value="소방" />
					<input type="hidden" id="itemType" value="소방" />
					<input type="hidden" id="userLevel" value="${ sessionScope.sessionUserInfo.userMemLvl }" />

                    <div class="scName">
                        <ul>
                            <li>
                                <span><label for="scYear">점검일시 :</label></span>
                                <input type="text" id="cs_inspect_dt_y" name="" value="" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" />년 
                                <input type="text" id="cs_inspect_dt_m" value="" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" />월 
                                <input type="text" id="cs_inspect_dt_d" value="" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" />일 
                            </li>
                            <li>
                                <span><label for="scinspector">점검자 :</label></span> 
                                <input class="name" type="text" id="cs_inspect_nm" name="" value="${ csMainInfo.csInspectNm }" /> 원장님 
                            </li>
                            <li>
                                <span><label for="scTel">대상 :</label></span> 
                                <input class="name" type="text" id="cs_agency_nm" name="" value="${ csMainInfo.csAgencyNm }" />어린이집 
                                ( <input class="name" type="text" id="cs_agency_city" name="" value="${ csMainInfo.csAgencyCity }" /> 시 
                                <input class="name" type="text" id="cs_agency_gu" name="" value="${ csMainInfo.csAgencyGu }" /> 구)
                            </li>
                           <li>
                               <span><label for="scTel">유선전화 :</label></span> 
                               <input class="tel" type="text" id="cs_agency_tel"  name="" value="${ csMainInfo.csAgencyTel }" onKeyup="this.value=this.value.replace(/[^0-9-]/g,'','');" />
                            </li>
                        </ul>
                    </div>
                    
                    <p class="fs_16 dp_b pdt10 pdb10 tl"><img src="${utcp.ctxPath}/resources/user/image/icon/homeIco.png" alt="home icon"> 가정(소규모) 어린이집의 경우 해당이미지가 표시된 항목은 필수 점검 사항입니다.</p>

                    <!--// board_tab_onoff //-->
                    <div class="board_tab_onoff po_re">
 
                        <!--// 완료버튼 // 220407,사용자에서는 완료버튼이 필요 없어서 인쇄 버튼으로 변경한다.
                        <button type="button" class="btn01 btn_blue po_ab fr finBtn" onclick="fn_selfcheckComplete('${ csMainInfo.csIdx }')"; ${ sessionScope.sessionUserInfo.userMemLvl ne '4' ? 'style="display:none"' : ''  }>완료</button>
                        -->
                        <!--// 완료버튼 //-->
						<button type="button" class="btn01 btn_blue po_ab fr finBtn" onclick="fn_print();">인쇄</button>
						
						
                        <!--// board_tab //-->
                        <ul class="board_tab">
                            <li id="fireActive" class="active">
                                <p><a href="#" onclick="fn_areaShow('소방',1);">소방분야</a></p>
                            </li>
                            <c:if test='${ crInfo.crBusYn eq "운영" }'>
                            <li id="trafficActive">
                                <p><a href="#" onclick="fn_areaShow('교통',1);">교통분야</a></p>
                            </li>
							</c:if>      
                        </ul>

                        <!--// board_tab_con //-->
                        <div class="board_tab_con">

                            
                            <!--// tab_con1_소방 //-->
                            <div class="cont" id="fireArea" style="display:block;">

                                <div class="scSelect">
                                    <label for="scSelect1" class="sound_only">회차</label>
                                    <select name="scSelect1" id="selFire" class="w100" onchange="fn_displayStep('소방', this.value)">
                                    	<c:forEach var="retInfo" items="${retInfo.selectSelfCheckItemStepList}" varStatus="stat">
                                    	<c:if test='${ retInfo.csiGubun eq "소방" }'>
                                    	<option value="${ retInfo.csiStep }">${ retInfo.csiStepNm }</option>
                                    	</c:if>
                                    	</c:forEach>
                                    </select>
                                </div>
                                

                                <!--// 소방1화 //-->
                                <input type="hidden" id="itemSize_f_1" value="${ fn:length(retInfo.selectFireList1) }" />
                                <div class="cont_box cont_box1" id="fire1">
                                    
                                    <table class="tb04">
                                        <caption class="sound_only">1화 관리영역 (소방계획서, 비상대피도, 문서관리편) 테이블</caption>
                                        <col style="width:6%;"/>
                                        <col style="width:20%;"/>
                                        <col style="width:56%;"/>
                                        <col style="width:6%;"/>
                                        <col style="width:6%;"/>
                                        <col style="width:6%;"/>
                                        <thead>
                                            <tr>
                                                <th scope="col" rowspan="2">번호</th>
                                                <th scope="col" rowspan="2">평가항목</th>
                                                <th scope="col" rowspan="2">세부 평가 항목</th>
                                                <th scope="col" colspan="3">점검사항</th>
                                            </tr>
                                            <tr>
                                                <th>적합</th>
                                                <th>부적합</th>
                                                <th>해당없음</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                        	<c:forEach var="retInfo" items="${retInfo.selectFireList1}" varStatus="stat">
                                        	<input type="hidden" id="csi_idx_f_${ retInfo.csiStep }_${ stat.count }" value="${ retInfo.csiIdx }" />
                                        	<input type="hidden" id="csd_step_f_${ retInfo.csiStep }_${ stat.count }" value="${ retInfo.csiStep }" />
                                        	<input type="hidden" id="csd_disp_no_f_${ retInfo.csiStep }_${ stat.count }" value="${ retInfo.csiDispNo }" />
                                            <tr>
                                                <td rowspan="3" class="bbDarkgray">${ retInfo.csiDispNo }<span id="area-homeicon-${ retInfo.csiDispNo }"><img src="${utcp.ctxPath}/resources/user/image/icon/homeIco.png" alt="home icon"></span></td>
                                                <td class="tc fw_500">${ retInfo.csiEvalItem }</td>
                                                <td>${ retInfo.csiDetailEvalItem }
                                                </td>
                                                <td class="tc"><label for="cstckWr1_1_1_1"><input type="radio" name="csdUserCheck_f_${ retInfo.csiStep }_${ stat.count }" id="csd_user_check_f_f_${ retInfo.csiStep }_${ stat.count }" value="적합" ${ retInfo.csdInfo.csdUserCheck eq '적합' ? 'checked' : '' } ></label></td>
                                                <td class="tc"><label for="cstckWr1_1_1_2"><input type="radio" name="csdUserCheck_f_${ retInfo.csiStep }_${ stat.count }" id="csd_user_check_f_i_${ retInfo.csiStep }_${ stat.count }" value="부적합" ${ retInfo.csdInfo.csdUserCheck eq '부적합' ? 'checked' : '' } ></label></td>
                                                <td class="tc"><label for="cstckWr1_1_1_3"><input type="radio" name="csdUserCheck_f_${ retInfo.csiStep }_${ stat.count }" id="csd_user_check_f_n_${ retInfo.csiStep }_${ stat.count }" value="해당없음" ${ retInfo.csdInfo.csdUserCheck eq '해당없음' ? 'checked' : '' } ></label></td>
                                            </tr>
                                            <tr>
                                                <th scope="col"><label for="cstckWr1_1_1_4">자체 점검 의견 사항 (어린이집 관계자 작성)</label></th>
                                                <td colspan="4" class="tc"><textarea class="comnt" id="cs_self_memo_f_${ retInfo.csiStep }_${ stat.count }" name="csdSelfMemo_f_${ retInfo.csiStep }_${ stat.count }" placeholder="자체 점검 시 궁금한점이나, 특이사항, 느껴진점 등을 기록합니다." >${ retInfo.csdInfo.csdSelfMemo }</textarea></td>
                                            </tr>
                                            <tr>
                                                <th scope="col" class="bbDarkgray"><label for="cstckWr1_1_1_5">컨설팅 소견 (소방전문가 소견)</label></th>
                                                <td colspan="4" class="tc bbDarkgray"><textarea class="comnt" id="cs_profess_memo_f_${ retInfo.csiStep }_${ stat.count }" name="csdProfessMemo_f_${ retInfo.csiStep }_${ stat.count }" placeholder="비대면 컨설팅 소방전문가 피드백">${ retInfo.csdInfo.csdProfessMemo }</textarea></td>
                                            </tr>
                                        	</c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                                <!--// 소방1화 //-->


                                <!--// 소방2화 //-->
                                <input type="hidden" id="itemSize_f_2" value="${ fn:length(retInfo.selectFireList2) }" />
                                <div class="cont_box cont_box2" id="fire2" style="display:none">
                                    <table class="tb04">
                                        <caption class="sound_only">2화 소화설비 (소화기의 종류, 엔진펌프, 스프링클러) 테이블</caption>
                                        <col style="width:6%;"/>
                                        <col style="width:20%;"/>
                                        <col style="width:56%;"/>
                                        <col style="width:6%;"/>
                                        <col style="width:6%;"/>
                                        <col style="width:6%;"/>
                                        <thead>
                                            <tr>
                                                <th scope="col" rowspan="2">번호</th>
                                                <th scope="col" rowspan="2">평가항목</th>
                                                <th scope="col" rowspan="2">세부 평가 항목</th>
                                                <th scope="col" colspan="3">점검사항</th>
                                            </tr>
                                            <tr>
                                                <th>적합</th>
                                                <th>부적합</th>
                                                <th>해당없음</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                        	<c:forEach var="retInfo" items="${retInfo.selectFireList2}" varStatus="stat">
											<input type="hidden" id="csi_idx_f_${ retInfo.csiStep }_${ stat.count }" value="${ retInfo.csiIdx }" />
											<input type="hidden" id="csd_step_f_${ retInfo.csiStep }_${ stat.count }" value="${ retInfo.csiStep }" />
											<input type="hidden" id="csd_disp_no_f_${ retInfo.csiStep }_${ stat.count }" value="${ retInfo.csiDispNo }" />
                                            <tr>
                                                <td rowspan="3" class="bbDarkgray">${ retInfo.csiDispNo }<span id="area-homeicon-${ retInfo.csiDispNo }"><img src="${utcp.ctxPath}/resources/user/image/icon/homeIco.png" alt="home icon"></span></td>
                                                <td class="tc fw_500">${ retInfo.csiEvalItem }</td>
                                                <td>${ retInfo.csiDetailEvalItem }</td>
                                                <td class="tc"><label for="cstckWr1_2_1_1"><input type="radio" name="csdUserCheck_f_${ retInfo.csiStep }_${ stat.count }" id="cstckWr1_2_1_1" value="적합" ${ retInfo.csdInfo.csdUserCheck eq '적합' ? 'checked' : '' } ></label></td>
                                                <td class="tc"><label for="cstckWr1_2_1_2"><input type="radio" name="csdUserCheck_f_${ retInfo.csiStep }_${ stat.count }" id="cstckWr1_2_1_2" value="부적합" ${ retInfo.csdInfo.csdUserCheck eq '부적합' ? 'checked' : '' } ></label></td>
                                                <td class="tc"><label for="cstckWr1_2_1_3"><input type="radio" name="csdUserCheck_f_${ retInfo.csiStep }_${ stat.count }" id="cstckWr1_2_1_3" value="해당없음" ${ retInfo.csdInfo.csdUserCheck eq '해당없음' ? 'checked' : '' } ></label></td>
                                            </tr>
                                            <tr>
                                                <th scope="col"><label for="cstckWr1_2_1_4">자체 점검 의견 사항 (어린이집 관계자 작성)</label></th>
                                                <td colspan="4" class="tc"><textarea class="comnt" id="cstckWr1_2_1_4" name="csdSelfMemo_f_${ retInfo.csiStep }_${ stat.count }" placeholder="자체 점검 시 궁금한점이나, 특이사항, 느껴진점 등을 기록합니다." >${ retInfo.csdInfo.csdSelfMemo }</textarea></td>
                                            </tr>
                                            <tr>
                                                <th scope="col" class="bbDarkgray"><label for="cstckWr1_2_1_5">컨설팅 소견 (소방전문가 소견)</label></th>
                                                <td colspan="4" class="tc bbDarkgray"><textarea class="comnt" id="cstckWr1_2_1_5" name="csdProfessMemo_f_${ retInfo.csiStep }_${ stat.count }" placeholder="비대면 컨설팅 소방전문가 피드백">${ retInfo.csdInfo.csdProfessMemo }</textarea></td>
                                            </tr>
                                        	</c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                                <!--// 소방2화 //-->

                                <!--// 소방3화 //-->
                                <input type="hidden" id="itemSize_f_3" value="${ fn:length(retInfo.selectFireList3) }" />
                                <div class="cont_box cont_box3" id="fire3" style="display:none">
                                    
                                    <table class="tb04">
                                        <caption class="sound_only">3화 경보설비 (자동화재탐지설비,시각경보기편) 테이블</caption>
                                        <col style="width:6%;"/>
                                        <col style="width:20%;"/>
                                        <col style="width:56%;"/>
                                        <col style="width:6%;"/>
                                        <col style="width:6%;"/>
                                        <col style="width:6%;"/>
                                        <thead>
                                            <tr>
                                                <th scope="col" rowspan="2">번호</th>
                                                <th scope="col" rowspan="2">평가항목</th>
                                                <th scope="col" rowspan="2">세부 평가 항목</th>
                                                <th scope="col" colspan="3">점검사항</th>
                                            </tr>
                                            <tr>
                                                <th>적합</th>
                                                <th>부적합</th>
                                                <th>해당없음</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                        	<c:forEach var="retInfo" items="${retInfo.selectFireList3}" varStatus="stat">
											<input type="hidden" id="csi_idx_f_${ retInfo.csiStep }_${ stat.count }" value="${ retInfo.csiIdx }" />
											<input type="hidden" id="csd_step_f_${ retInfo.csiStep }_${ stat.count }" value="${ retInfo.csiStep }" />
											<input type="hidden" id="csd_disp_no_f_${ retInfo.csiStep }_${ stat.count }" value="${ retInfo.csiDispNo }" />
                                            <tr>
                                                <td rowspan="3" class="bbDarkgray">${ retInfo.csiDispNo }<span id="area-homeicon-${ retInfo.csiDispNo }"><img src="${utcp.ctxPath}/resources/user/image/icon/homeIco.png" alt="home icon"></span></td>
                                                <td class="tc fw_500">${ retInfo.csiEvalItem }</td>
                                                <td>${ retInfo.csiDetailEvalItem }</td>
                                                <td class="tc"><label for="cstckWr1_3_1_1"><input type="radio" name="csdUserCheck_f_${ retInfo.csiStep }_${ stat.count }" id="cstckWr1_3_1_1" value="적합" ${ retInfo.csdInfo.csdUserCheck eq '적합' ? 'checked' : '' } ></label></td>
                                                <td class="tc"><label for="cstckWr1_3_1_2"><input type="radio" name="csdUserCheck_f_${ retInfo.csiStep }_${ stat.count }" id="cstckWr1_3_1_2" value="부적합" ${ retInfo.csdInfo.csdUserCheck eq '부적합' ? 'checked' : '' } ></label></td>
                                                <td class="tc"><label for="cstckWr1_3_1_3"><input type="radio" name="csdUserCheck_f_${ retInfo.csiStep }_${ stat.count }" id="cstckWr1_3_1_3" value="해당없음" ${ retInfo.csdInfo.csdUserCheck eq '해당없음' ? 'checked' : '' } ></label></td>
                                            </tr>
                                            <tr>
                                                <th scope="col"><label for="cstckWr1_3_1_4">자체 점검 의견 사항 (어린이집 관계자 작성)</label></th>
                                                <td colspan="4" class="tc"><textarea class="comnt" id="cstckWr1_3_1_4" name="csdSelfMemo_f_${ retInfo.csiStep }_${ stat.count }" placeholder="자체 점검 시 궁금한점이나, 특이사항, 느껴진점 등을 기록합니다." >${ retInfo.csdInfo.csdSelfMemo }</textarea></td>
                                            </tr>
                                            <tr>
                                                <th scope="col" class="bbDarkgray"><label for="cstckWr1_3_1_5">컨설팅 소견 (소방전문가 소견)</label></th>
                                                <td colspan="4" class="tc bbDarkgray"><textarea class="comnt" id="cstckWr1_3_1_5" name="csdProfessMemo_f_${ retInfo.csiStep }_${ stat.count }" placeholder="비대면 컨설팅 소방전문가 피드백">${ retInfo.csdInfo.csdProfessMemo }</textarea></td>
                                            </tr>
											</c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                                <!--// 소방3화 //-->

                                <!--// 소방4화 //-->
                                <input type="hidden" id="itemSize_f_4" value="${ fn:length(retInfo.selectFireList4) }" />
                                <div class="cont_box cont_box4"  id="fire4" style="display:none">
                                    
                                    <table class="tb04">
                                        <caption class="sound_only">4화 피난구조설비 (구조대,유도등편) 테이블</caption>
                                        <col style="width:6%;"/>
                                        <col style="width:20%;"/>
                                        <col style="width:56%;"/>
                                        <col style="width:6%;"/>
                                        <col style="width:6%;"/>
                                        <col style="width:6%;"/>
                                        <thead>
                                            <tr>
                                                <th scope="col" rowspan="2">번호</th>
                                                <th scope="col" rowspan="2">평가항목</th>
                                                <th scope="col" rowspan="2">세부 평가 항목</th>
                                                <th scope="col" colspan="3">점검사항</th>
                                            </tr>
                                            <tr>
                                                <th>적합</th>
                                                <th>부적합</th>
                                                <th>해당없음</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                        	<c:forEach var="retInfo" items="${retInfo.selectFireList4}" varStatus="stat">
											<input type="hidden" id="csi_idx_f_${ retInfo.csiStep }_${ stat.count }" value="${ retInfo.csiIdx }" />
											<input type="hidden" id="csd_step_f_${ retInfo.csiStep }_${ stat.count }" value="${ retInfo.csiStep }" />
											<input type="hidden" id="csd_disp_no_f_${ retInfo.csiStep }_${ stat.count }" value="${ retInfo.csiDispNo }" />
                                            <tr>
                                                <td rowspan="3" class="bbDarkgray">${ retInfo.csiDispNo }<span id="area-homeicon-${ retInfo.csiDispNo }"><img src="${utcp.ctxPath}/resources/user/image/icon/homeIco.png" alt="home icon"></span></td>
                                                <td class="tc fw_500">${ retInfo.csiEvalItem }</td>
                                                <td>${ retInfo.csiDetailEvalItem }</td>
                                                <td class="tc"><label for="cstckWr1_4_1_1"><input type="radio" name="csdUserCheck_f_${ retInfo.csiStep }_${ stat.count }" id="cstckWr1_4_1_1" value="적합" ${ retInfo.csdInfo.csdUserCheck eq '적합' ? 'checked' : '' } ></label></td>
                                                <td class="tc"><label for="cstckWr1_4_1_2"><input type="radio" name="csdUserCheck_f_${ retInfo.csiStep }_${ stat.count }" id="cstckWr1_4_1_2" value="부적합" ${ retInfo.csdInfo.csdUserCheck eq '부적합' ? 'checked' : '' } ></label></td>
                                                <td class="tc"><label for="cstckWr1_4_1_3"><input type="radio" name="csdUserCheck_f_${ retInfo.csiStep }_${ stat.count }" id="cstckWr1_4_1_3" value="해당없음" ${ retInfo.csdInfo.csdUserCheck eq '해당없음' ? 'checked' : '' } ></label></td>
                                            </tr>
                                            <tr>
                                                <th scope="col"><label for="cstckWr1_4_1_4">자체 점검 의견 사항 (어린이집 관계자 작성)</label></th>
                                                <td colspan="4" class="tc"><textarea class="comnt" id="cstckWr1_4_1_4" name="csdSelfMemo_f_${ retInfo.csiStep }_${ stat.count }" placeholder="자체 점검 시 궁금한점이나, 특이사항, 느껴진점 등을 기록합니다." >${ retInfo.csdInfo.csdSelfMemo }</textarea></td>
                                            </tr>
                                            <tr>
                                                <th scope="col" class="bbDarkgray"><label for="cstckWr1_4_1_5">컨설팅 소견 (소방전문가 소견)</label></th>
                                                <td colspan="4" class="tc bbDarkgray"><textarea class="comnt" id="cstckWr1_4_1_5" name="csdProfessMemo_f_${ retInfo.csiStep }_${ stat.count }" placeholder="비대면 컨설팅 소방전문가 피드백">${ retInfo.csdInfo.csdProfessMemo }</textarea></td>
                                            </tr>
											</c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                                <!--// 소방4화 //-->

                                <!--// 소방5화 //-->
                                <input type="hidden" id="itemSize_f_5" value="${ fn:length(retInfo.selectFireList5) }" />
                                <div class="cont_box cont_box5" id="fire5" style="display:none">
                                    
                                    <table class="tb04">
                                        <caption class="sound_only">5화 피난방화설비 양방향대피편 테이블</caption>
                                        <col style="width:6%;"/>
                                        <col style="width:20%;"/>
                                        <col style="width:56%;"/>
                                        <col style="width:6%;"/>
                                        <col style="width:6%;"/>
                                        <col style="width:6%;"/>
                                        <thead>
                                            <tr>
                                                <th scope="col" rowspan="2">번호</th>
                                                <th scope="col" rowspan="2">평가항목</th>
                                                <th scope="col" rowspan="2">세부 평가 항목</th>
                                                <th scope="col" colspan="3">점검사항</th>
                                            </tr>
                                            <tr>
                                                <th>적합</th>
                                                <th>부적합</th>
                                                <th>해당없음</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                        	<c:forEach var="retInfo" items="${retInfo.selectFireList5}" varStatus="stat">
											<input type="hidden" id="csi_idx_f_${ retInfo.csiStep }_${ stat.count }" value="${ retInfo.csiIdx }" />
											<input type="hidden" id="csd_step_f_${ retInfo.csiStep }_${ stat.count }" value="${ retInfo.csiStep }" />
											<input type="hidden" id="csd_disp_no_f_${ retInfo.csiStep }_${ stat.count }" value="${ retInfo.csiDispNo }" />
                                            <tr>
                                                <td rowspan="3" class="bbDarkgray">${ retInfo.csiDispNo }<span id="area-homeicon-${ retInfo.csiDispNo }"><img src="${utcp.ctxPath}/resources/user/image/icon/homeIco.png" alt="home icon"></span></td>
                                                <td class="tc fw_500">${ retInfo.csiEvalItem }</td>
                                                <td>${ retInfo.csiDetailEvalItem }</td>
                                                <td class="tc"><label for="cstckWr1_5_1_1"><input type="radio" name="csdUserCheck_f_${ retInfo.csiStep }_${ stat.count }" id="cstckWr1_5_1_1" value="적합" ${ retInfo.csdInfo.csdUserCheck eq '적합' ? 'checked' : '' } ></label></td>
                                                <td class="tc"><label for="cstckWr1_5_1_2"><input type="radio" name="csdUserCheck_f_${ retInfo.csiStep }_${ stat.count }" id="cstckWr1_5_1_2" value="부적합" ${ retInfo.csdInfo.csdUserCheck eq '부적합' ? 'checked' : '' } ></label></td>
                                                <td class="tc"><label for="cstckWr1_5_1_3"><input type="radio" name="csdUserCheck_f_${ retInfo.csiStep }_${ stat.count }" id="cstckWr1_5_1_3" value="해당없음" ${ retInfo.csdInfo.csdUserCheck eq '해당없음' ? 'checked' : '' } ></label></td>
                                            </tr>
                                            <tr>
                                                <th scope="col"><label for="cstckWr1_5_1_4">자체 점검 의견 사항 (어린이집 관계자 작성)</label></th>
                                                <td colspan="4" class="tc"><textarea class="comnt" id="cstckWr1_5_1_4" name="csdSelfMemo_f_${ retInfo.csiStep }_${ stat.count }" placeholder="자체 점검 시 궁금한점이나, 특이사항, 느껴진점 등을 기록합니다." >${ retInfo.csdInfo.csdSelfMemo }</textarea></td>
                                            </tr>
                                            <tr>
                                                <th scope="col" class="bbDarkgray"><label for="cstckWr1_5_1_5">컨설팅 소견 (소방전문가 소견)</label></th>
                                                <td colspan="4" class="tc bbDarkgray"><textarea class="comnt" id="cstckWr1_5_1_5" name="csdProfessMemo_f_${ retInfo.csiStep }_${ stat.count }" placeholder="비대면 컨설팅 소방전문가 피드백">${ retInfo.csdInfo.csdProfessMemo }</textarea></td>
                                            </tr>
											</c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                                <!--// 소방5화 //-->

                                <!--// 소방6화 //-->
                                <input type="hidden" id="itemSize_f_6" value="${ fn:length(retInfo.selectFireList6) }" />
                                <div class="cont_box cont_box6" id="fire6" style="display:none">
                                    
                                    <table class="tb04">
                                        <caption class="sound_only">6화 가스설비 가스누설경보기, 보일러실, 가스배관 테이블</caption>
                                        <col style="width:6%;"/>
                                        <col style="width:20%;"/>
                                        <col style="width:56%;"/>
                                        <col style="width:6%;"/>
                                        <col style="width:6%;"/>
                                        <col style="width:6%;"/>
                                        <thead>
                                            <tr>
                                                <th scope="col" rowspan="2">번호</th>
                                                <th scope="col" rowspan="2">평가항목</th>
                                                <th scope="col" rowspan="2">세부 평가 항목</th>
                                                <th scope="col" colspan="3">점검사항</th>
                                            </tr>
                                            <tr>
                                                <th>적합</th>
                                                <th>부적합</th>
                                                <th>해당없음</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                        	<c:forEach var="retInfo" items="${retInfo.selectFireList6}" varStatus="stat">
											<input type="hidden" id="csi_idx_f_${ retInfo.csiStep }_${ stat.count }" value="${ retInfo.csiIdx }" />
											<input type="hidden" id="csd_step_f_${ retInfo.csiStep }_${ stat.count }" value="${ retInfo.csiStep }" />
											<input type="hidden" id="csd_disp_no_f_${ retInfo.csiStep }_${ stat.count }" value="${ retInfo.csiDispNo }" />
                                            <tr>
                                                <td rowspan="3" class="bbDarkgray">${ retInfo.csiDispNo }<span id="area-homeicon-${ retInfo.csiDispNo }"><img src="${utcp.ctxPath}/resources/user/image/icon/homeIco.png" alt="home icon"></span></td>
                                                <td class="tc fw_500">${ retInfo.csiEvalItem }</td>
                                                <td>${ retInfo.csiDetailEvalItem }</td>
                                                <td class="tc"><label for="cstckWr1_6_1_1"><input type="radio" name="csdUserCheck_f_${ retInfo.csiStep }_${ stat.count }" id="cstckWr1_6_1_1" value="적합" ${ retInfo.csdInfo.csdUserCheck eq '적합' ? 'checked' : '' } ></label></td>
                                                <td class="tc"><label for="cstckWr1_6_1_2"><input type="radio" name="csdUserCheck_f_${ retInfo.csiStep }_${ stat.count }" id="cstckWr1_6_1_2" value="부적합" ${ retInfo.csdInfo.csdUserCheck eq '부적합' ? 'checked' : '' } ></label></td>
                                                <td class="tc"><label for="cstckWr1_6_1_3"><input type="radio" name="csdUserCheck_f_${ retInfo.csiStep }_${ stat.count }" id="cstckWr1_6_1_3" value="해당없음" ${ retInfo.csdInfo.csdUserCheck eq '해당없음' ? 'checked' : '' } ></label></td>
                                            </tr>
                                            <tr>
                                                <th scope="col"><label for="cstckWr1_6_1_4">자체 점검 의견 사항 (어린이집 관계자 작성)</label></th>
                                                <td colspan="4" class="tc"><textarea class="comnt" id="cstckWr1_6_1_4" name="csdSelfMemo_f_${ retInfo.csiStep }_${ stat.count }" placeholder="자체 점검 시 궁금한점이나, 특이사항, 느껴진점 등을 기록합니다." >${ retInfo.csdInfo.csdSelfMemo }</textarea></td>
                                            </tr>
                                            <tr>
                                                <th scope="col" class="bbDarkgray"><label for="cstckWr1_6_1_5">컨설팅 소견 (소방전문가 소견)</label></th>
                                                <td colspan="4" class="tc bbDarkgray"><textarea class="comnt" id="cstckWr1_6_1_5" name="csdProfessMemo_f_${ retInfo.csiStep }_${ stat.count }" placeholder="비대면 컨설팅 소방전문가 피드백">${ retInfo.csdInfo.csdProfessMemo }</textarea></td>
                                            </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                                <!--// 소방6화 //-->


                                <!--// 소방7화 //-->
                                <input type="hidden" id="itemSize_f_7" value="${ fn:length(retInfo.selectFireList7) }" />
                                <div class="cont_box cont_box7" id="fire7" style="display:none">
                                    
                                    <table class="tb04">
                                        <caption class="sound_only">7화 위험물 관리 보일러 연료 위험물 수납용기 테이블</caption>
                                        <col style="width:6%;"/>
                                        <col style="width:20%;"/>
                                        <col style="width:56%;"/>
                                        <col style="width:6%;"/>
                                        <col style="width:6%;"/>
                                        <col style="width:6%;"/>
                                        <thead>
                                            <tr>
                                                <th scope="col" rowspan="2">번호</th>
                                                <th scope="col" rowspan="2">평가항목</th>
                                                <th scope="col" rowspan="2">세부 평가 항목</th>
                                                <th scope="col" colspan="3">점검사항</th>
                                            </tr>
                                            <tr>
                                                <th>적합</th>
                                                <th>부적합</th>
                                                <th>해당없음</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                        	<c:forEach var="retInfo" items="${retInfo.selectFireList7}" varStatus="stat">
											<input type="hidden" id="csi_idx_f_${ retInfo.csiStep }_${ stat.count }" value="${ retInfo.csiIdx }" />
											<input type="hidden" id="csd_step_f_${ retInfo.csiStep }_${ stat.count }" value="${ retInfo.csiStep }" />
											<input type="hidden" id="csd_disp_no_f_${ retInfo.csiStep }_${ stat.count }" value="${ retInfo.csiDispNo }" />
                                            <tr>
                                                <td rowspan="3" class="bbDarkgray">${ retInfo.csiDispNo }<span id="area-homeicon-${ retInfo.csiDispNo }"><img src="${utcp.ctxPath}/resources/user/image/icon/homeIco.png" alt="home icon"></span></td>
                                                <td class="tc fw_500">${ retInfo.csiEvalItem }</td>
                                                <td>${ retInfo.csiDetailEvalItem }</td>
                                                <td class="tc"><label for="cstckWr1_7_1_1"><input type="radio" name="csdUserCheck_f_${ retInfo.csiStep }_${ stat.count }" id="cstckWr1_7_1_1" value="적합" ${ retInfo.csdInfo.csdUserCheck eq '적합' ? 'checked' : '' } ></label></td>
                                                <td class="tc"><label for="cstckWr1_7_1_2"><input type="radio" name="csdUserCheck_f_${ retInfo.csiStep }_${ stat.count }" id="cstckWr1_7_1_2" value="부적합" ${ retInfo.csdInfo.csdUserCheck eq '부적합' ? 'checked' : '' } ></label></td>
                                                <td class="tc"><label for="cstckWr1_7_1_3"><input type="radio" name="csdUserCheck_f_${ retInfo.csiStep }_${ stat.count }" id="cstckWr1_7_1_3" value="해당없음" ${ retInfo.csdInfo.csdUserCheck eq '해당없음' ? 'checked' : '' } ></label></td>
                                            </tr>
                                            <tr>
                                                <th scope="col"><label for="cstckWr1_7_1_4">자체 점검 의견 사항 (어린이집 관계자 작성)</label></th>
                                                <td colspan="4" class="tc"><textarea class="comnt" id="cstckWr1_7_1_4" name="csdSelfMemo_f_${ retInfo.csiStep }_${ stat.count }" placeholder="자체 점검 시 궁금한점이나, 특이사항, 느껴진점 등을 기록합니다." ></textarea></td>
                                            </tr>
                                            <tr>
                                                <th scope="col" class="bbDarkgray"><label for="cstckWr1_7_1_5">컨설팅 소견 (소방전문가 소견)</label></th>
                                                <td colspan="4" class="tc bbDarkgray"><textarea class="comnt" id="cstckWr1_7_1_5" name="csdProfessMemo_f_${ retInfo.csiStep }_${ stat.count }" placeholder="비대면 컨설팅 소방전문가 피드백">${ retInfo.csdInfo.csdProfessMemo }</textarea></td>
                                            </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                                <!--// 소방7화 //-->

                                <!--// 소방8화 //-->
                                <input type="hidden" id="itemSize_f_8" value="${ fn:length(retInfo.selectFireList8) }" />
                                <div class="cont_box cont_box8" id="fire8" style="display:none">
                                    
                                    <table class="tb04">
                                        <caption class="sound_only">8화 전기안전관리 멀티텝, 전선 누전차단기 점검 테이블</caption>
                                        <col style="width:6%;"/>
                                        <col style="width:20%;"/>
                                        <col style="width:56%;"/>
                                        <col style="width:6%;"/>
                                        <col style="width:6%;"/>
                                        <col style="width:6%;"/>
                                        <thead>
                                            <tr>
                                                <th scope="col" rowspan="2">번호</th>
                                                <th scope="col" rowspan="2">평가항목</th>
                                                <th scope="col" rowspan="2">세부 평가 항목</th>
                                                <th scope="col" colspan="3">점검사항</th>
                                            </tr>
                                            <tr>
                                                <th>적합</th>
                                                <th>부적합</th>
                                                <th>해당없음</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                        	<c:forEach var="retInfo" items="${retInfo.selectFireList8}" varStatus="stat">
											<input type="hidden" id="csi_idx_f_${ retInfo.csiStep }_${ stat.count }" value="${ retInfo.csiIdx }" />
											<input type="hidden" id="csd_step_f_${ retInfo.csiStep }_${ stat.count }" value="${ retInfo.csiStep }" />
											<input type="hidden" id="csd_disp_no_f_${ retInfo.csiStep }_${ stat.count }" value="${ retInfo.csiDispNo }" />
                                            <tr>
                                                <td rowspan="3" class="bbDarkgray">${ retInfo.csiDispNo }<span id="area-homeicon-${ retInfo.csiDispNo }"><img src="${utcp.ctxPath}/resources/user/image/icon/homeIco.png" alt="home icon"></span></td>
                                                <td class="tc fw_500">${ retInfo.csiEvalItem }</td>
                                                <td>${ retInfo.csiDetailEvalItem }</td>
                                                <td class="tc"><label for="cstckWr1_8_1_1"><input type="radio" name="csdUserCheck_f_${ retInfo.csiStep }_${ stat.count }" id="cstckWr1_8_1_1" value="적합" ${ retInfo.csdInfo.csdUserCheck eq '적합' ? 'checked' : '' } ></label></td>
                                                <td class="tc"><label for="cstckWr1_8_1_2"><input type="radio" name="csdUserCheck_f_${ retInfo.csiStep }_${ stat.count }" id="cstckWr1_8_1_2" value="부적합" ${ retInfo.csdInfo.csdUserCheck eq '부적합' ? 'checked' : '' } ></label></td>
                                                <td class="tc"><label for="cstckWr1_8_1_3"><input type="radio" name="csdUserCheck_f_${ retInfo.csiStep }_${ stat.count }" id="cstckWr1_8_1_3" value="해당없음" ${ retInfo.csdInfo.csdUserCheck eq '해당없음' ? 'checked' : '' } ></label></td>
                                            </tr>
                                            <tr>
                                                <th scope="col"><label for="cstckWr1_8_1_4">자체 점검 의견 사항 (어린이집 관계자 작성)</label></th>
                                                <td colspan="4" class="tc"><textarea class="comnt" id="cstckWr1_8_1_4" name="csdSelfMemo_f_${ retInfo.csiStep }_${ stat.count }" placeholder="자체 점검 시 궁금한점이나, 특이사항, 느껴진점 등을 기록합니다." >${ retInfo.csdInfo.csdSelfMemo }</textarea></td>
                                            </tr>
                                            <tr>
                                                <th scope="col" class="bbDarkgray"><label for="cstckWr1_8_1_5">컨설팅 소견 (소방전문가 소견)</label></th>
                                                <td colspan="4" class="tc bbDarkgray"><textarea class="comnt" id="cstckWr1_8_1_5" name="csdProfessMemo_f_${ retInfo.csiStep }_${ stat.count }" placeholder="비대면 컨설팅 소방전문가 피드백">${ retInfo.csdInfo.csdProfessMemo }</textarea></td>
                                            </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                                <!--// 소방8화 //-->

                                <!--// 소방9화 //-->
                                <input type="hidden" id="itemSize_f_9" value="${ fn:length(retInfo.selectFireList9) }" />
                                <div class="cont_box cont_box9" id="fire9" style="display:none">
                                    
                                    <table class="tb04">
                                        <caption class="sound_only">9화 방염관리 방염대상물, 방염라벨, 방염성능검사 확인 표시 테이블</caption>
                                        <col style="width:6%;"/>
                                        <col style="width:20%;"/>
                                        <col style="width:56%;"/>
                                        <col style="width:6%;"/>
                                        <col style="width:6%;"/>
                                        <col style="width:6%;"/>
                                        <thead>
                                            <tr>
                                                <th scope="col" rowspan="2">번호</th>
                                                <th scope="col" rowspan="2">평가항목</th>
                                                <th scope="col" rowspan="2">세부 평가 항목</th>
                                                <th scope="col" colspan="3">점검사항</th>
                                            </tr>
                                            <tr>
                                                <th>적합</th>
                                                <th>부적합</th>
                                                <th>해당없음</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                        	<c:forEach var="retInfo" items="${retInfo.selectFireList9}" varStatus="stat">
											<input type="hidden" id="csi_idx_f_${ retInfo.csiStep }_${ stat.count }" value="${ retInfo.csiIdx }" />
											<input type="hidden" id="csd_step_f_${ retInfo.csiStep }_${ stat.count }" value="${ retInfo.csiStep }" />
											<input type="hidden" id="csd_disp_no_f_${ retInfo.csiStep }_${ stat.count }" value="${ retInfo.csiDispNo }" />
                                            <tr>
                                                <td rowspan="3" class="bbDarkgray">${ retInfo.csiDispNo }<span id="area-homeicon-${ retInfo.csiDispNo }"><img src="${utcp.ctxPath}/resources/user/image/icon/homeIco.png" alt="home icon"></span></td>
                                                <td class="tc fw_500">${ retInfo.csiEvalItem }</td>
                                                <td>${ retInfo.csiDetailEvalItem }</td>
                                                <td class="tc"><label for="cstckWr1_9_1_1"><input type="radio" name="csdUserCheck_f_${ retInfo.csiStep }_${ stat.count }" id="cstckWr1_9_1_1" value="적합" ${ retInfo.csdInfo.csdUserCheck eq '적합' ? 'checked' : '' } ></label></td>
                                                <td class="tc"><label for="cstckWr1_9_1_2"><input type="radio" name="csdUserCheck_f_${ retInfo.csiStep }_${ stat.count }" id="cstckWr1_9_1_2" value="부적합" ${ retInfo.csdInfo.csdUserCheck eq '부적합' ? 'checked' : '' } ></label></td>
                                                <td class="tc"><label for="cstckWr1_9_1_3"><input type="radio" name="csdUserCheck_f_${ retInfo.csiStep }_${ stat.count }" id="cstckWr1_9_1_3" value="해당없음" ${ retInfo.csdInfo.csdUserCheck eq '해당없음' ? 'checked' : '' } ></label></td>
                                            </tr>
                                            <tr>
                                                <th scope="col"><label for="cstckWr1_9_1_4">자체 점검 의견 사항 (어린이집 관계자 작성)</label></th>
                                                <td colspan="4" class="tc"><textarea class="comnt" id="cstckWr1_9_1_4" name="csdSelfMemo_f_${ retInfo.csiStep }_${ stat.count }" placeholder="자체 점검 시 궁금한점이나, 특이사항, 느껴진점 등을 기록합니다." >${ retInfo.csdInfo.csdSelfMemo }</textarea></td>
                                            </tr>
                                            <tr>
                                                <th scope="col" class="bbDarkgray"><label for="cstckWr1_9_1_5">컨설팅 소견 (소방전문가 소견)</label></th>
                                                <td colspan="4" class="tc bbDarkgray"><textarea class="comnt" id="cstckWr1_9_1_5" name="csdProfessMemo_f_${ retInfo.csiStep }_${ stat.count }" placeholder="비대면 컨설팅 소방전문가 피드백">${ retInfo.csdInfo.csdProfessMemo }</textarea></td>
                                            </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                                <!--// 소방9화 //-->
                                
                            </div>
                            <!--// tab_con1_소방 //-->

                            <!--// tab_con2_교통 //-->
                            
                            <div class="cont"  id="trafficArea" style="display:none;">

                                <div class="scSelect">
                                    <label for="scSelect1" class="sound_only">교통회차</label>
                                    <select name="scSelect1" id="selTraffic" class="w100" onchange="fn_displayStep('교통', this.value)">
                                    	<c:forEach var="retInfo" items="${retInfo.selectSelfCheckItemStepList}" varStatus="stat">
                                    	<c:if test='${ retInfo.csiGubun eq "교통" }'>
                                    	<option value="${ retInfo.csiStep }">${ retInfo.csiStepNm }</option>
                                    	</c:if>
                                    	</c:forEach>
                                    </select>
                                </div>

                                <!--// 교통1화 //-->
                                <input type="hidden" id="itemSize_t_1" value="${ fn:length(retInfo.selectTrafficList1) }" />
                                <div class="cont_box cont_box1" id="traffic1">

                                    <table class="tb04">
                                        <caption class="sound_only">1편 관계서류 (신고증명서, 안전수칙 등) 테이블</caption>
                                        <col style="width:6%;"/>
                                        <col style="width:20%;"/>
                                        <col style="width:56%;"/>
                                        <col style="width:6%;"/>
                                        <col style="width:6%;"/>
                                        <col style="width:6%;"/>
                                        <thead>
                                            <tr>
                                                <th scope="col" rowspan="2">번호</th>
                                                <th scope="col" rowspan="2">평가항목</th>
                                                <th scope="col" rowspan="2">세부 평가 항목</th>
                                                <th scope="col" colspan="3">점검사항</th>
                                            </tr>
                                            <tr>
                                                <th>적합</th>
                                                <th>부적합</th>
                                                <th>해당없음</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                        	<c:forEach var="retInfo" items="${retInfo.selectTrafficList1}" varStatus="stat">
											<input type="hidden" id="csi_idx_t_${ retInfo.csiStep }_${ stat.count }" value="${ retInfo.csiIdx }" />
											<input type="hidden" id="csd_step_t_${ retInfo.csiStep }_${ stat.count }" value="${ retInfo.csiStep }" />
											<input type="hidden" id="csd_disp_no_t_${ retInfo.csiStep }_${ stat.count }" value="${ retInfo.csiDispNo }" />
                                            <tr>
                                                <td rowspan="3" class="bbDarkgray">${ retInfo.csiDispNo }</td>
                                                <td class="tc fw_500">${ retInfo.csiEvalItem }</td>
                                                <td>${ retInfo.csiDetailEvalItem }</td>
                                                <td class="tc"><label for="cstckWr2_1_1_1"><input type="radio" name="csdUserCheck_t_${ retInfo.csiStep }_${ stat.count }" id="cstckWr2_1_1_1" value="적합" ${ retInfo.csdInfo.csdUserCheck eq '적합' ? 'checked' : '' } ></label></td>
                                                <td class="tc"><label for="cstckWr2_1_1_2"><input type="radio" name="csdUserCheck_t_${ retInfo.csiStep }_${ stat.count }" id="cstckWr2_1_1_2" value="부적합" ${ retInfo.csdInfo.csdUserCheck eq '부적합' ? 'checked' : '' } ></label></td>
                                                <td class="tc"><label for="cstckWr2_1_1_3"><input type="radio" name="csdUserCheck_t_${ retInfo.csiStep }_${ stat.count }" id="cstckWr2_1_1_3" value="해당없음" ${ retInfo.csdInfo.csdUserCheck eq '해당없음' ? 'checked' : '' } ></label></td>
                                            </tr>
                                            <tr>
                                                <th scope="col"><label for="cstckWr2_1_1_4">자체 점검 의견 사항 (어린이집 관계자 작성)</label></th>
                                                <td colspan="4" class="tc"><textarea class="comnt" id="cstckWr2_1_1_4" name="csdSelfMemo_t_${ retInfo.csiStep }_${ stat.count }" placeholder="자체 점검 시 궁금한점이나, 특이사항, 느껴진점 등을 기록합니다." >${ retInfo.csdInfo.csdSelfMemo }</textarea></td>
                                            </tr>
                                            <tr>
                                                <th scope="col" class="bbDarkgray"><label for="cstckWr2_1_1_5">컨설팅 소견 (소방전문가 소견)</label></th>
                                                <td colspan="4" class="tc bbDarkgray"><textarea class="comnt" id="cstckWr2_1_1_5" name="csdProfessMemo_t_${ retInfo.csiStep }_${ stat.count }" placeholder="비대면 컨설팅 소방전문가 피드백">${ retInfo.csdInfo.csdProfessMemo }</textarea></td>
                                            </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                                <!--// 교통1편 //-->

                                <!--// 교통2편 //-->
                                <input type="hidden" id="itemSize_t_2" value="${ fn:length(retInfo.selectTrafficList2) }" />
                                <div class="cont_box cont_box1" id="traffic2" style="display:none">

                                    <table class="tb04">
                                        <caption class="sound_only">2편 기타사항 (안전교육, 운전자 관련 서류 등) 테이블</caption>
                                        <col style="width:6%;"/>
                                        <col style="width:20%;"/>
                                        <col style="width:56%;"/>
                                        <col style="width:6%;"/>
                                        <col style="width:6%;"/>
                                        <col style="width:6%;"/>
                                        <thead>
                                            <tr>
                                                <th scope="col" rowspan="2">번호</th>
                                                <th scope="col" rowspan="2">평가항목</th>
                                                <th scope="col" rowspan="2">세부 평가 항목</th>
                                                <th scope="col" colspan="3">점검사항</th>
                                            </tr>
                                            <tr>
                                                <th>적합</th>
                                                <th>부적합</th>
                                                <th>해당없음</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                        	<c:forEach var="retInfo" items="${retInfo.selectTrafficList2}" varStatus="stat">
											<input type="hidden" id="csi_idx_t_${ retInfo.csiStep }_${ stat.count }" value="${ retInfo.csiIdx }" />
											<input type="hidden" id="csd_step_t_${ retInfo.csiStep }_${ stat.count }" value="${ retInfo.csiStep }" />
											<input type="hidden" id="csd_disp_no_t_${ retInfo.csiStep }_${ stat.count }" value="${ retInfo.csiDispNo }" />
                                            <tr>
                                                <td rowspan="3" class="bbDarkgray">${ retInfo.csiDispNo }</td>
                                                <td class="tc fw_500">${ retInfo.csiEvalItem }</td>
                                                <td>${ retInfo.csiDetailEvalItem }</td>
                                                <td class="tc"><label for="cstckWr2_2_1_1"><input type="radio" name="csdUserCheck_t_${ retInfo.csiStep }_${ stat.count }" id="cstckWr2_2_1_1" value="적합" ${ retInfo.csdInfo.csdUserCheck eq '적합' ? 'checked' : '' } ></label></td>
                                                <td class="tc"><label for="cstckWr2_2_1_2"><input type="radio" name="csdUserCheck_t_${ retInfo.csiStep }_${ stat.count }" id="cstckWr2_2_1_2" value="부적합" ${ retInfo.csdInfo.csdUserCheck eq '부적합' ? 'checked' : '' } ></label></td>
                                                <td class="tc"><label for="cstckWr2_2_1_3"><input type="radio" name="csdUserCheck_t_${ retInfo.csiStep }_${ stat.count }" id="cstckWr2_2_1_3" value="해당없음" ${ retInfo.csdInfo.csdUserCheck eq '해당없음' ? 'checked' : '' } ></label></td>
                                            </tr>
                                            <tr>
                                                <th scope="col"><label for="cstckWr2_2_1_4">자체 점검 의견 사항 (어린이집 관계자 작성)</label></th>
                                                <td colspan="4" class="tc"><textarea class="comnt" id="cstckWr2_2_1_4" name="csdSelfMemo_t_${ retInfo.csiStep }_${ stat.count }" placeholder="자체 점검 시 궁금한점이나, 특이사항, 느껴진점 등을 기록합니다." >${ retInfo.csdInfo.csdSelfMemo }</textarea></td>
                                            </tr>
                                            <tr>
                                                <th scope="col" class="bbDarkgray"><label for="cstckWr2_2_1_5">컨설팅 소견 (소방전문가 소견)</label></th>
                                                <td colspan="4" class="tc bbDarkgray"><textarea class="comnt" id="cstckWr2_2_1_5" name="csdProfessMemo_t_${ retInfo.csiStep }_${ stat.count }" placeholder="비대면 컨설팅 소방전문가 피드백">${ retInfo.csdInfo.csdProfessMemo }</textarea></td>
                                            </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                                <!--// 교통2편 //-->


                                <!--// 교통3편 //-->
                                <input type="hidden" id="itemSize_t_3" value="${ fn:length(retInfo.selectTrafficList3) }" />
                                <div class="cont_box cont_box1" id="traffic3" style="display:none">

                                    <table class="tb04">
                                        <caption class="sound_only">3편 실내설비 (어린이하차확인장치, 안전띠 등) 테이블</caption>
                                        <col style="width:6%;"/>
                                        <col style="width:20%;"/>
                                        <col style="width:56%;"/>
                                        <col style="width:6%;"/>
                                        <col style="width:6%;"/>
                                        <col style="width:6%;"/>
                                        <thead>
                                            <tr>
                                                <th scope="col" rowspan="2">번호</th>
                                                <th scope="col" rowspan="2">평가항목</th>
                                                <th scope="col" rowspan="2">세부 평가 항목</th>
                                                <th scope="col" colspan="3">점검사항</th>
                                            </tr>
                                            <tr>
                                                <th>적합</th>
                                                <th>부적합</th>
                                                <th>해당없음</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                        	<c:forEach var="retInfo" items="${retInfo.selectTrafficList3}" varStatus="stat">
											<input type="hidden" id="csi_idx_t_${ retInfo.csiStep }_${ stat.count }" value="${ retInfo.csiIdx }" />
											<input type="hidden" id="csd_step_t_${ retInfo.csiStep }_${ stat.count }" value="${ retInfo.csiStep }" />
											<input type="hidden" id="csd_disp_no_t_${ retInfo.csiStep }_${ stat.count }" value="${ retInfo.csiDispNo }" />
                                            <tr>
                                                <td rowspan="3" class="bbDarkgray">${ retInfo.csiDispNo }</td>
                                                <td class="tc fw_500">${ retInfo.csiEvalItem }</td>
                                                <td>${ retInfo.csiDetailEvalItem }</td>
                                                <td class="tc"><label for="cstckWr2_3_1_1"><input type="radio" name="csdUserCheck_t_${ retInfo.csiStep }_${ stat.count }" id="cstckWr2_3_1_1" value="적합" ${ retInfo.csdInfo.csdUserCheck eq '적합' ? 'checked' : '' } ></label></td>
                                                <td class="tc"><label for="cstckWr2_3_1_2"><input type="radio" name="csdUserCheck_t_${ retInfo.csiStep }_${ stat.count }" id="cstckWr2_3_1_2" value="부적합" ${ retInfo.csdInfo.csdUserCheck eq '부적합' ? 'checked' : '' } ></label></td>
                                                <td class="tc"><label for="cstckWr2_3_1_3"><input type="radio" name="csdUserCheck_t_${ retInfo.csiStep }_${ stat.count }" id="cstckWr2_3_1_3" value="해당없음" ${ retInfo.csdInfo.csdUserCheck eq '해당없음' ? 'checked' : '' } ></label></td>
                                            </tr>
                                            <tr>
                                                <th scope="col"><label for="cstckWr2_3_1_4">자체 점검 의견 사항 (어린이집 관계자 작성)</label></th>
                                                <td colspan="4" class="tc"><textarea class="comnt" id="cstckWr2_3_1_4" name="csdSelfMemo_t_${ retInfo.csiStep }_${ stat.count }" placeholder="자체 점검 시 궁금한점이나, 특이사항, 느껴진점 등을 기록합니다." >${ retInfo.csdInfo.csdSelfMemo }</textarea></td>
                                            </tr>
                                            <tr>
                                                <th scope="col" class="bbDarkgray"><label for="cstckWr2_3_1_5">컨설팅 소견 (소방전문가 소견)</label></th>
                                                <td colspan="4" class="tc bbDarkgray"><textarea class="comnt" id="cstckWr2_3_1_5" name="csdProfessMemo_t_${ retInfo.csiStep }_${ stat.count }" placeholder="비대면 컨설팅 소방전문가 피드백">${ retInfo.csdInfo.csdProfessMemo }</textarea></td>
                                            </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                                <!--// 교통3편 //-->

                                <!--// 교통4편 //-->
                                <input type="hidden" id="itemSize_t_4" value="${ fn:length(retInfo.selectTrafficList4) }" />
                                <div class="cont_box cont_box1" id="traffic4" style="display:none">

                                    <table class="tb04">
                                        <caption class="sound_only">4편 실외설비 (어린이보호표지, 등화장치 등) 테이블</caption>
                                        <col style="width:6%;"/>
                                        <col style="width:20%;"/>
                                        <col style="width:56%;"/>
                                        <col style="width:6%;"/>
                                        <col style="width:6%;"/>
                                        <col style="width:6%;"/>
                                        <thead>
                                            <tr>
                                                <th scope="col" rowspan="2">번호</th>
                                                <th scope="col" rowspan="2">평가항목</th>
                                                <th scope="col" rowspan="2">세부 평가 항목</th>
                                                <th scope="col" colspan="3">점검사항</th>
                                            </tr>
                                            <tr>
                                                <th>적합</th>
                                                <th>부적합</th>
                                                <th>해당없음</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                        	<c:forEach var="retInfo" items="${retInfo.selectTrafficList4}" varStatus="stat">
											<input type="hidden" id="csi_idx_t_${ retInfo.csiStep }_${ stat.count }" value="${ retInfo.csiIdx }" />
											<input type="hidden" id="csd_step_t_${ retInfo.csiStep }_${ stat.count }" value="${ retInfo.csiStep }" />
											<input type="hidden" id="csd_disp_no_t_${ retInfo.csiStep }_${ stat.count }" value="${ retInfo.csiDispNo }" />
                                            <tr>
                                                <td rowspan="3" class="bbDarkgray">${ retInfo.csiDispNo }</td>
                                                <td class="tc fw_500">${ retInfo.csiEvalItem }</td>
                                                <td>${ retInfo.csiDetailEvalItem }</td>
                                                <td class="tc"><label for="cstckWr2_4_1_1"><input type="radio" name="csdUserCheck_t_${ retInfo.csiStep }_${ stat.count }" id="cstckWr2_4_1_1" value="적합" ${ retInfo.csdInfo.csdUserCheck eq '적합' ? 'checked' : '' } ></label></td>
                                                <td class="tc"><label for="cstckWr2_4_1_2"><input type="radio" name="csdUserCheck_t_${ retInfo.csiStep }_${ stat.count }" id="cstckWr2_4_1_2" value="부적합" ${ retInfo.csdInfo.csdUserCheck eq '부적합' ? 'checked' : '' } ></label></td>
                                                <td class="tc"><label for="cstckWr2_4_1_3"><input type="radio" name="csdUserCheck_t_${ retInfo.csiStep }_${ stat.count }" id="cstckWr2_4_1_3" value="해당없음" ${ retInfo.csdInfo.csdUserCheck eq '해당없음' ? 'checked' : '' } ></label></td>
                                            </tr>
                                            <tr>
                                                <th scope="col"><label for="cstckWr2_4_1_4">자체 점검 의견 사항 (어린이집 관계자 작성)</label></th>
                                                <td colspan="4" class="tc"><textarea class="comnt" id="cstckWr2_4_1_4" name="csdSelfMemo_t_${ retInfo.csiStep }_${ stat.count }" placeholder="자체 점검 시 궁금한점이나, 특이사항, 느껴진점 등을 기록합니다." >${ retInfo.csdInfo.csdSelfMemo }</textarea></td>
                                            </tr>
                                            <tr>
                                                <th scope="col" class="bbDarkgray"><label for="cstckWr2_4_1_5">컨설팅 소견 (소방전문가 소견)</label></th>
                                                <td colspan="4" class="tc bbDarkgray"><textarea class="comnt" id="cstckWr2_4_1_5" name="csdProfessMemo_t_${ retInfo.csiStep }_${ stat.count }" placeholder="비대면 컨설팅 소방전문가 피드백">${ retInfo.csdInfo.csdProfessMemo }</textarea></td>
                                            </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                                <!--// 교통4편 //-->

                            </div>
                            <!--// tab_con2_교통 //-->
 

                        </div>
                        <!--// board_tab_con //-->		
                        
                        
                        <div class="tb_btn mgb0">
                            <button type="button" class="btn01 btn_yellow mgt20" onclick="fn_PrevBtn()" >이전</button>
                            <button type="button" class="btn01 btn_yellow mgt20" onclick="fn_NextBtn()" >다음</button>
                            <button type="button" class="btn01 btn_green mgt20" onclick="fn_saveBtn()" >저장</button>
                        </div>

                    </div>
                    <!--// board_tab_onoff //-->
                </form>
                </div><!--//viewDiv//-->
            </div><!--//listWrap//-->
			


    <div class="remodal messagePop1" data-remodal-id="cstScRgsFin" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc">
        <div class="modal-content">
            <div class="modal-header">
                <p class="tit alignC">알림</p>
            </div>
            <div class="modal-body">
                <p class="messageTxt" id="strPopupTxt1">
                    완료되었습니다.
                </p>
            </div>
            <div class="modal-footer">
                <div class="tc">
                    <button data-remodal-action="cancel" onclick="fn_showStepItem2();" class="remodal-confirm btn01 btn_green">확인</button>
                </div>
            </div>
        </div>
    </div>

    <div class="remodal messagePop1" data-remodal-id="onlyPopupMsg" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc">
        <div class="modal-content">
            <div class="modal-header">
                <p class="tit alignC">알림</p>
            </div>
            <div class="modal-body">
                <p class="messageTxt" id="strPopupTxt2">
                    
                </p>
            </div>
            <div class="modal-footer">
                <div class="tc">
                	<button data-remodal-action="cancel" class="remodal-confirm btn02 btn_green">확인</button>
                </div>
            </div>
        </div>
    </div>
    