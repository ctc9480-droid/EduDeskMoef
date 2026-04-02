<%@page import="com.educare.edu.member.service.SessionUserInfoHelper"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp" %>
<script>
var arrCsiIdx = new Array();
var arrCsdAnswer = new Array();

	$(document).ready(function(){
		var csConsultDate = "${ csvMain.csConsultDate }";
		console.log("csConsultDate:"+csConsultDate);
		
		if(csConsultDate != null && csConsultDate != undefined && csConsultDate != "" ) {
			var csDate = csConsultDate.split("-");

			$("#boardYear").val(csDate[0]);
			$("#fbMonth").val(csDate[1]);
			$("#fbDay").val(csDate[2]);
		} else {
			$("#fbMonth").val("${ fn:substring(today,5,7) }");						
			$("#fbDay").val("${ fn:substring(today,8,10) }");
		}
		
	});

	function fn_regData() {
		//debugger;
		var emailRegex = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
		var csConsultField = "";
		var csConsultDate = "";
		var csFacilityType = "";

		if($("#csCompanyNm").val() == "") {
			//$("#msgStr").html("필수사항입니다.");
			//location.href = "#fbFinPop";
			alert("어린이집명은 필수입니다.");
			$("#csCompanyNm").focus();
			return false;
		}

		if($("#csTel").val() == "") {
			//$("#msgStr").html("필수사항입니다.");
			//location.href = "#fbFinPop";
			alert("유선번호는 필수입니다.");
			$("#csTel").focus();
			return false;
		}

		if($("#csEmail").val() == "") {
			//$("#msgStr").html("필수사항입니다.");
			//location.href = "#fbFinPop";
			alert("이메일은 필수입니다.");
			$("#csEmail").focus();
			return false;
		} else if( !emailRegex.test($("#csEmail").val()) ) {
			//$("#msgStr").html("이메일 형식을 확인해주세요.");
			//location.href = "#fbFinPop";
			alert("이메일 형식을 확인해주세요.");
			$("#csEmail").focus();
			return false;
		}
		
		csConsultDate = $("#boardYear").val()+"-"+$("#fbMonth").val()+"-"+$("#fbDay").val();
		
		
		arrCsiIdx = new Array();
		arrCsdAnswer = new Array();
		var noSel = false;
		$("input[name=csiIdx]").each(function(index, item) {
			var val = $(item).val();
			arrCsiIdx.push(val);
			if($("input[name='csdAnswer"+val+"']:checked").val() == undefined) {
				if($("input[name='csdAnswer"+val+"'][value=5]").is(':enabled')) {
					arrCsdAnswer.push(0);
					noSel = true;	
				}
				else {
					arrCsdAnswer.push(0);
					//noSel = true;
				}
			} else {
				arrCsdAnswer.push($("input[name='csdAnswer"+val+"']:checked").val());	
			}
		});
		
		if(noSel) {
			//$("#msgStr").html("답변은 필수사항입니다.");
			//location.href = "#fbFinPop";
			alert("답변은 필수사항입니다.");
			return false;
		}
		
		$("input[name=csConsultField]").each(function() {
			if(this.checked) {
				csConsultField += this.value + ",";
			}
		});
		
		csConsultField = csConsultField.substring(0, csConsultField.length-1);
		csFacilityType = $("input[name=csFacilityType]:checked").val();
		
		
		var jsonData = { 
				"csIdx":$("#csIdx").val(), "cbIdx":$("#cbIdx").val(), "crIdx":$("#crIdx").val(), "csCompanyNm":$("#csCompanyNm").val()
				, "csFacilityType": csFacilityType, "csTel":$("#csTel").val(), "csEmail":$("#csEmail").val()
				, "csConsultDate":csConsultDate, "csConsultType":$("#csConsultType").val(), "csConsultField":csConsultField, "csEtcMemo":$("#csEtcMemo").val()
				, "arrCsiIdx":arrCsiIdx ,"arrCsdAnswer":arrCsdAnswer
		};

		console.log("jsonData:"+JSON.stringify(jsonData));
		
		if(!confirm("저장하시겠습니까?")) return false;
		
		$.ajax({
			url:'/consult/survey/surveyWriteProc.do',
			data:jsonData,
			method:'post',
			success:function(r){
				console.log(r);
				if(r.resultCode >= 0) {
					$("#msgStr").html("만족도조사 평가 완료 하였습니다.");
					location.href = "#fbFinPop";
				}
			}
		});
		
	}
</script>

<c:if test="${empty csvMain }">${crInfo.crCenterType }
<script>
$(document).ready(function(){
	$('#csCompanyNm').val('${crInfo.crCompanyNm}');
	var crCenterType1 = '${crInfo.crCenterType }'.replace(/[^\uAC00-\uD7AF\u1100-\u11FF\u3130-\u318F]/gi,"");                                  
	$('input:radio[name=csFacilityType]').each(function(o){
		var crCenterType2 = this.value.replace(/[^\uAC00-\uD7AF\u1100-\u11FF\u3130-\u318F]/gi,"");
		if(crCenterType1==crCenterType2){
			this.checked=true;
		}
	});
	$('#csTel').val('${crInfo.crTel}');
	$('#csEmail').val('${crInfo.crEmail}');
});
</script>
</c:if>

            <div class="sub_txt">
                <span class=""><img src="${utcp.ctxPath}/resources/user/image/icon/icon_subtitle.png">어린이집 안전관리 컨설팅 만족도 조사</span>
            </div>

            <div class="listWrap cst" style="padding-top: 0">
                <div id="viewDiv" >

                        <form action="#" method="post">
                        	<input type="hidden" name="csIdx" id="csIdx" value="${ csvMain.csIdx }" />
                    		<input type="hidden" name="cbIdx" id="cbIdx" value="${ crInfo.cbIdx }" />
                    		<input type="hidden" name="crIdx" id="crIdx" value="${ crInfo.crIdx }" />
                    		<input type="hidden" id="csConsultType" value="${ crInfo.crConsultType }" />
                    		
                            <div class="scName pd20 tc fb">
                                어린이집 안전관리 컨설팅에 참여해 주셔서 감사합니다. <br>
                                만족도 조사는 사업에 참여하신 여러분들의 의견을 최대한 반영하여 보다  <br>
                                나은 컨설팅을 만들기 위한 귀중한 자료로 활용되오며 해당 목적 외에는 
                                사용되지 않습니다. <br>
                                아래 내용을 참고하여 만족도 조사 부탁드립니다. 감사합니다. <br>
                                <b class="fc_blue"> 담당자 / 전화(02-6902-2474), 이메일(jco4197@csia.or.kr)</b>
                            </div>
                            
                            <div class="fb_table">
                                <table class="tb04 w100">
                                    <caption class="sound_only">컨설팅 기본정보 테이블</caption>
                                    <col span="1" style="width:20%;"/>
                                    <col span="1" style="width:80%;"/>
                                    <tr>
                                        <th><label for="fbcpName">어린이집명</label></th>
                                        <td>
                                        <%-- <input type="text" name="csCompanyNm" id="csCompanyNm" value="${ csvMain.csCompanyNm }"> --%>
                                        <input type="hidden" name="csCompanyNm" id="csCompanyNm" value="${ crInfo.crCompanyNm }">
                                        ${crInfo.crCompanyNm }
                                        </td>
                                    </tr>
					<tr>
						<th>시설유형</th>
						<td>
							<%-- <p><label for="fbcpType1">
									<input type="radio" id="cs_facility_type_a" name="csFacilityType" value="국·공립" ${ empty csvMain or csvMain.csFacilityType eq '국·공립' ? 'checked' : '' }>
									국·공립
								</label></p>
							<p><label for="fbcpType2">
									<input type="radio" id="cs_facility_type_b" name="csFacilityType" value="사회복지법인" ${ csvMain.csFacilityType eq '사회복지법인' ? 'checked' : '' }>
									사회복지법인
								</label></p>
							<p><label for="fbcpType3">
									<input type="radio" id="cs_facility_type_c" name="csFacilityType" value="법인·단체 등" ${ csvMain.csFacilityType eq '법인·단체 등' ? 'checked' : '' }>
									법인·단체 등
								</label></p>
							<p><label for="fbcpType4">
									<input type="radio" id="cs_facility_type_d" name="csFacilityType" value="민간" ${ csvMain.csFacilityType eq '민간' ? 'checked' : '' }>
									민간
								</label></p>
							<p><label for="fbcpType5">
									<input type="radio" id="cs_facility_type_e" name="csFacilityType" value="가정" ${ csvMain.csFacilityType eq '가정' ? 'checked' : '' }>
									가정
								</label></p>
							<p><label for="fbcpType6">
									<input type="radio" id="cs_facility_type_f" name="csFacilityType" value="협동" ${ csvMain.csFacilityType eq '협동' ? 'checked' : '' }>
									협동
								</label></p>
							<p><label for="fbcpType7">
									<input type="radio" id="fbcpType7" name="csFacilityType" value="직장" ${ csvMain.csFacilityType eq '직장' ? 'checked' : '' }>
									직장
								</label></p> --%>
								<input type="hidden" name="csFacilityType" id="csCompanyNm" value="${ crInfo.crCenterType }">
                                            ${crInfo.crCenterType }
						</td>
					</tr>
					<tr>
                                        <th><label for="fbTel">유선번호</label></th>
                                        <td>
                                        <%-- <input type="text" id="csTel" value="${ csvMain.csTel }" name="csTel" onKeyup="this.value=this.value.replace(/[^0-9-]/g,'','');" > --%>
                                        <input type="hidden" name="csTel" value="${crInfo.crTel }">
                                        ${crInfo.crTel }
                                        </td>
                                    </tr>
                                    <tr>
                                        <th><label for="fbEmail">이메일</label></th>
                                        <td>
                                        <input type="hidden" id="csEmail" value="${ crInfo.crEmail }" name="csEmail">
                                        ${crInfo.crEmail }
                                        </td>
                                    </tr>
                
                                    <tr>
                                        <th>컨설팅 일자</th>
                                        <!--// 방문형일때는 사용자 화면에서 사용자가 직접 입력/온라인일때는 자가점검표 컨설팅소견 완료 일자로 입력돼야함 //-->
                                        <td>
											<select id="boardYear" class="fbDate w10" disabled>
												<c:forEach var="i" begin="0" end="${2099-2021}">
												<c:set var="yearOption" value="${ 2099-i }" />
												<option value="${ yearOption }" ${ fn:substring(today,0,4) eq yearOption ? "selected" : "" }>${ yearOption }</option>
												</c:forEach>
											</select>
                                            -
                                            <label for="fbMonth" class="sound_only w20">월</label>
                                            <select id="fbMonth" name="" class="fbDate">
	                                            <option value="01">01</option>
	                                            <option value="02">02</option>
	                                            <option value="03">03</option>
	                                            <option value="04">04</option>
	                                            <option value="05">05</option>
	                                            <option value="06">06</option>
	                                            <option value="07">07</option>
	                                            <option value="08">08</option>
	                                            <option value="09">09</option>
	                                            <option value="10">10</option>
	                                            <option value="11">11</option>
	                                            <option value="12">12</option>
                                            </select>
                                            -
                                            <label for="fbDay" class="sound_only w20">일</label>
                                            <select id="fbDay" name="" class="fbDate">
                                                <option value="01">01</option>
                                                <option value="02">02</option>
                                                <option value="03">03</option>
                                                <option value="04">04</option>
                                                <option value="05">05</option>
                                                <option value="06">06</option>
                                                <option value="07">07</option>
                                                <option value="08">08</option>
                                                <option value="09">09</option>
                                                <option value="10">10</option>
                                                <option value="11">11</option>
                                                <option value="12">12</option>
                                                <option value="13">13</option>
                                                <option value="14">14</option>
                                                <option value="15">15</option>
                                                <option value="16">16</option>
                                                <option value="17">17</option>
                                                <option value="18">18</option>
                                                <option value="19">19</option>
                                                <option value="20">20</option>
                                                <option value="21">21</option>
                                                <option value="22">22</option>
                                                <option value="23">23</option>
                                                <option value="24">24</option>
                                                <option value="25">25</option>
                                                <option value="26">26</option>
                                                <option value="27">27</option>
                                                <option value="28">28</option>
                                                <option value="29">29</option>
                                                <option value="30">30</option>
                                                <option value="31">31</option>
                                            </select>
                                        </td>
                                    </tr>
                                </table>
                            </div>			
                
                            <h5 class="h5_label">1. 컨설팅 기본정보</h5>
                            <div class="fb_table">
                                <table class="tb04 w100">
                                    <caption class="sound_only">컨설팅 기본정보 테이블</caption>
                                    <colgroup>
                                        <col span="1" style="width:40%;"/>
                                        <col span="1" style="width:60%;"/>
                                    </colgroup>
                                    <tr>
                                        <th>1-1. 컨설팅 받으신 유형을 선택하여 주십시오.</th>
                                        <td>
                                            <p class="radio_yes">
                                                <input type="radio" id="chkYes" name="csConsultType" value="방문형(전문가 직접방문)" class="radio_bt chk1" ${crInfo.crConsultType eq '방문형' ? 'checked' : '' } disabled>
                                                <label for="chkYes">방문형(전문가 직접방문)</label>	
                                            </p>
                                            <p class="radio_no">
                                                <input type="radio" id="chkNo" name="csConsultType" value="온라인(자가점검)" class="radio_bt chk2" ${crInfo.crConsultType eq '온라인' ? 'checked' : '' } disabled>
                                                <label for="chkNo">온라인(자가점검)</label>	
                                            </p>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th>1-2. 컨설팅 받으신 분야를 선택하여 주십시오. <span class="fc_blue fw_500">* 중복 체크 가능</span></th>
                                        <td>
                                            <p>
                                                <input type="checkbox" id="fbClassA" name="csConsultField" value="소방" checked disabled>
                                                <label for="fbClassA">소방</label>	
                                            </p>
                                            <p>
                                                <input type="checkbox" id="fbClassB" name="csConsultField" value="교통" ${ crInfo.crBusYn eq '운영' ? 'checked' : '' }>
                                                <label for="fbClassB">교통(통학차량)</label>	
                                            </p>
                                        </td>
                                    </tr>
                                </table>
                            </div>
                            
                            <!--// 방문형일때 보여야할 영역 //-->
                            <div class="box box1" id="visitArea">
                                <div class="box1_area">
                                    <h5 class="h5_label">2. 컨설팅 분야별 만족도 (${crInfo.crConsultType})</h5>
                                    <div class="fb_table">
                                        <table class="tb04 w100">
                                            <caption class="sound_only">컨설팅 분야별 만족도(방문형) 테이블</caption>
                                            <colgroup>
                                                <col span="1" style="width:28%;"/>
                                                <col span="1" style="width:12%;"/>
                                                <col span="1" style="width:12%;"/>
                                                <col span="1" style="width:12%;"/>
                                                <col span="1" style="width:12%;"/>
                                                <col span="1" style="width:12%;"/>
                                                <col span="1" style="width:12%;"/>
                                            </colgroup>
                                            <thead>
                                                <tr>
                                                    <th scope="col">문항</th>
                                                    <th scope="col">분야</th>
                                                    <th scope="col">매우<br>만족</th>
                                                    <th scope="col">만족</th>
                                                    <th scope="col">보통</th>
                                                    <th scope="col">불만족</th>
                                                    <th scope="col">매우<br>불만족</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                            	<c:forEach var="retInfo" items="${csvItemDetail}" varStatus="stat">
                                               	<c:if test="${ retInfo.csiConsultField ne '공통'  }">
                                                <tr>
                                                	<input type="hidden" name="csiIdx" id="csi_idx" value="${ retInfo.csiIdx }" />
                                                	<c:if test="${ stat.count % 2 == 1 }">
                                                    <td rowspan="2">${ retInfo.csiItemNm } </td>
                                                    </c:if>
                                                    <td class="tc">${ retInfo.csiConsultField }</td>
                                                    <td colspan="5" class="rdg tc">
                                                        <ul class="chk_list ty02">
                                                            <li>
                                                                <input type="radio" id="csi_consult_field_${ retInfo.csiIdx }_5" name="csdAnswer${ retInfo.csiIdx }" value="5" ${retInfo.csvdInfo.csdAnswer eq '5' ? 'checked' : '' } ${ retInfo.csiConsultField eq '교통' and crInfo.crBusYn eq '미운영' ? 'disabled="disabled"' : '' } >
                                                                <label for="csi_consult_field_${ retInfo.csiIdx }_5">⑤</label>
                                                            </li>
                                                            <li>
                                                                <input type="radio" id="csi_consult_field_${ retInfo.csiIdx }_4" name="csdAnswer${ retInfo.csiIdx }" value="4" ${retInfo.csvdInfo.csdAnswer eq '4' ? 'checked' : '' } ${ retInfo.csiConsultField eq '교통' and crInfo.crBusYn eq '미운영' ? 'disabled="disabled"' : '' } >
                                                                <label for="csi_consult_field_${ retInfo.csiIdx }_4">④</label>
                                                            </li>
                                                            <li>
                                                                <input type="radio" id="csi_consult_field_${ retInfo.csiIdx }_3" name="csdAnswer${ retInfo.csiIdx }" value="3" ${retInfo.csvdInfo.csdAnswer eq '3' ? 'checked' : '' } ${ retInfo.csiConsultField eq '교통' and crInfo.crBusYn eq '미운영' ? 'disabled="disabled"' : '' } >
                                                                <label for="csi_consult_field_${ retInfo.csiIdx }_3">③</label>
                                                            </li>
                                                            <li>
                                                                <input type="radio" id="csi_consult_field_${ retInfo.csiIdx }_2" name="csdAnswer${ retInfo.csiIdx }" value="2" ${retInfo.csvdInfo.csdAnswer eq '2' ? 'checked' : '' } ${ retInfo.csiConsultField eq '교통' and crInfo.crBusYn eq '미운영' ? 'disabled="disabled"' : '' } >
                                                                <label for="csi_consult_field_${ retInfo.csiIdx }_2">②</label>
                                                            </li>
                                                            <li>
                                                                <input type="radio" id="csi_consult_field_${ retInfo.csiIdx }_1" name="csdAnswer${ retInfo.csiIdx }" value="1" ${retInfo.csvdInfo.csdAnswer eq '1' ? 'checked' : '' } ${ retInfo.csiConsultField eq '교통' and crInfo.crBusYn eq '미운영' ? 'disabled="disabled"' : '' } >
                                                                <label for="csi_consult_field_${ retInfo.csiIdx }_1">①</label>
                                                            </li>
                                                        </ul>
                                                    </td>
                                                </tr>
                                                </c:if>
                                            	</c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                    
                                    <h5 class="h5_label">3. 종합만족도</h5>
                                    <div class="fb_table">
                                        <table class="tb04 w100">
                                            <caption class="sound_only">종합만족도 테이블</caption>
                                            <colgroup>
                                                <col span="1" style="width:40%;"/>
                                                <col span="1" style="width:12%;"/>
                                                <col span="1" style="width:12%;"/>
                                                <col span="1" style="width:12%;"/>
                                                <col span="1" style="width:12%;"/>
                                                <col span="1" style="width:12%;"/>
                                            </colgroup>
                                            <thead>
                                                <tr>
                                                    <th scope="col" colspan="">문항</th>
                                                    <th scope="col">매우<br>만족</th>
                                                    <th scope="col">만족</th>
                                                    <th scope="col">보통</th>
                                                    <th scope="col">불만족</th>
                                                    <th scope="col">매우<br>불만족</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                            	<c:forEach var="retInfo" items="${csvItemDetail}" varStatus="stat">
                                               	<c:if test="${ retInfo.csiConsultField eq '공통'  }">
                                                <tr>
                                                	<input type="hidden" name="csiIdx" id="csi_idx" value="${ retInfo.csiIdx }" />
                                                    <td rowspan="">${ retInfo.csiItemNm }</td>
                                                    <td colspan="5" class="rdg tc">
                                                        <ul class="chk_list ty02">
                                                            <li>
                                                                <input type="radio" id="csi_consult_field_${ retInfo.csiIdx }_5" name="csdAnswer${ retInfo.csiIdx }" value="5"  ${retInfo.csvdInfo.csdAnswer eq '5' ? 'checked' : '' } >
                                                                <label for="csi_consult_field_${ retInfo.csiIdx }_5">⑤</label>
                                                            </li>
                                                            <li>
                                                                <input type="radio" id="csi_consult_field_${ retInfo.csiIdx }_4" name="csdAnswer${ retInfo.csiIdx }" value="4" ${retInfo.csvdInfo.csdAnswer eq '4' ? 'checked' : '' } >
                                                                <label for="csi_consult_field_${ retInfo.csiIdx }_4">④</label>
                                                            </li>
                                                            <li>
                                                                <input type="radio" id="csi_consult_field_${ retInfo.csiIdx }_3" name="csdAnswer${ retInfo.csiIdx }" value="3" ${retInfo.csvdInfo.csdAnswer eq '3' ? 'checked' : '' } >
                                                                <label for="csi_consult_field_${ retInfo.csiIdx }_3">③</label>
                                                            </li>
                                                            <li>
                                                                <input type="radio" id="csi_consult_field_${ retInfo.csiIdx }_2" name="csdAnswer${ retInfo.csiIdx }" value="2" ${retInfo.csvdInfo.csdAnswer eq '2' ? 'checked' : '' } >
                                                                <label for="csi_consult_field_${ retInfo.csiIdx }_2">②</label>
                                                            </li>
                                                            <li>
                                                                <input type="radio" id="csi_consult_field_${ retInfo.csiIdx }_1" name="csdAnswer${ retInfo.csiIdx }" value="1" ${retInfo.csvdInfo.csdAnswer eq '1' ? 'checked' : '' } >
                                                                <label for="csi_consult_field_${ retInfo.csiIdx }_1">①</label>
                                                            </li>
                                                        </ul>
                                                    </td>
                                                </tr>
                                               	</c:if>
                                               	</c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                            <!--// 방문형 //-->


                            <div class="box">
                                <h5 class="h5_label">4. 기타 의견 및 개선사항</h5>
                                <textarea class="fbTarea w100" name="csEtcMemo" id="csEtcMemo" >${ csvMain.csEtcMemo }</textarea>
                            </div>
                
                            <p class="bot_tit">감사합니다.</p>

                            <div class="w100 oh tc mgb10">
                                <button type="button" class="btn01 btn_blue" onclick="fn_regData();">${ empty csvMain ? '만족도조사 평가완료' : '만족도조사 평가 수정' }</button>
                            </div>
                        </form>

                </div><!--//viewDiv//-->
            </div><!--//listWrap//-->

		<!--// 만족도 조사 완료 //-->
		<div class="remodal messagePop1" data-remodal-id="fbFinPop" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc">
			<div class="modal-content">
				<div class="modal-header">
					<p class="tit alignC">알림</p>
				</div>
				<div class="modal-body">
					<p class="messageTxt" id="msgStr">
						만족도조사 평가 완료 하였습니다.
					</p>
				</div>
				<div class="modal-footer">
					<div class="tc">
						<button type="button" data-remodal-action="cancel" onclick="opener.location.reload();window.close();" class="remodal-confirm btn02 btn_green">확인</button>
					</div>
				</div>
			</div>
		</div>
		<!--// 만족도 조사 완료 //-->
