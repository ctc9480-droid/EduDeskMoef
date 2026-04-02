<%@page import="com.educare.edu.member.service.SessionUserInfoHelper"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>

<script type="text/javascript">
	$(function() {
		// 건물 소방시설,설비 현황 cstTbSub101 - cstTbSub27
		$("a[id^=img-]")
				.click(
						function() {
							var no = this.id.replace('img-','');
							console.log(no);
							var popUrl = "/comm/popup/cstTbSubImg.do?no="+no;
							var popOption = "width=300px, height=330px, resizable=no, location=no, top=10px, left=10px;"
							window.open(popUrl, "img-popup", popOption);
						});
	});

	function fn_consultReg() {
		//필드 체크 Start
		//if( !$("input:radio[name=crConsultType]").is(":checked") ) { fn_Msg("[컨설팅 방식 선택]\n필수 선택값입니다."); $("#cr_consult_type_o").focus(); return; }
		//if( !$("input:radio[name=crReceiptYn]").is(":checked") ) { fn_Msg("[동의여부]\n필수 선택값입니다.");  $("#cr_receipt_yn_y").focus();return; }

		var temp = ""
		//소화설비
		$("input[name=cr_fire_facility]:checked").each(function() {
			if (temp == "") {
				temp = $(this).val();
			} else {
				temp = temp + "," + $(this).val();
			}
		});
		$("#crFireFacility").val(temp);
		temp = "";

		//경보설비
		$("input[name=cr_alert_facility]:checked").each(function() {
			if (temp == "") {
				temp = $(this).val();
			} else {
				temp = temp + "," + $(this).val();
			}
		});
		$("#crAlertFacility").val(temp);
		temp = "";

		//피난구조설비(피난기구)
		$("input[name=cr_resucue_facility1]:checked").each(function() {
			if (temp == "") {
				temp = $(this).val();
			} else {
				temp = temp + "," + $(this).val();
			}
		});
		$("#crResucueFacility1").val(temp);
		temp = "";

		//피난구조설비(그외)
		$("input[name=cr_resucue_facility2]:checked").each(function() {
			if (temp == "") {
				temp = $(this).val();
			} else {
				temp = temp + "," + $(this).val();
			}
		});
		$("#crResucueFacility2").val(temp);
		temp = "";

		//기타
		$("input[name=cr_etc_facility]:checked").each(function() {
			if (temp == "") {
				temp = $(this).val();
			} else {
				temp = temp + "," + $(this).val();
			}
		});
		$("#crEtcFacility").val(temp);
		temp = "";

		//가스
		$("input[name=cr_gas_facility]:checked").each(function() {
			if (temp == "") {
				temp = $(this).val();
			} else {
				temp = temp + "," + $(this).val();
			}
		});
		$("#crGasFacility").val(temp);
		temp = "";

		var cMsg = "";

		if ("${ crInfo.crIdx }" != 0) {
			cMsg = "수정하시겠습니까?";
		} else {
			cMsg = "신청하시겠습니까?";
		}

		if (!confirm(cMsg))
			return false;

		//$("#reg-form").submit();

		var formData = new FormData($('#reg-form')[0]);
		console.log("formData:", formData);

		$.ajax({
			url : '/consult/consultRegWriteProc.do',
			data : formData,
			method : 'post',
			enctype : 'multipart/form-data',
			contentType : false,
			processData : false,
			success : function(r) {
				console.log(r);
				if (r.resultCode >= 0) {
					//$("#msgStr").html("만족도조사 평가 완료 하였습니다.");
					location.href = "#fbFinPop";
				}
			},
			error : function(request, status, error) {
				console.log("code:" + request.status + " , message:"
						+ request.responseText + ", error:" + error);
			}
		});

	}
</script>

<div class="sub_txt">
	<span class=""><img src="${utcp.ctxPath}/resources/user/image/icon/icon_subtitle.png" alt="서브타이틀 아이콘">컨설팅 신청서</span>
</div>

<div class="listWrap cst" style="padding-top: 0">
	<div id="viewDiv">

		<!--// tab_con1 //-->
		<div class="cont">

			<div class="cstRgsWrap">

				<form name="formReg" id="reg-form" method="post" enctype="multipart/form-data">
					<input type="hidden" name="crIdx" id="crIdx" value="${ not empty crInfo ? crInfo.crIdx : '0' }" />
					<!-- <input type="hidden" name="cbIdx" id="cbIdx" value="${ cbInfo.idx }" /> -->
					<!-- 
								<input type="hidden" name="cbIdx" id="cbIdx" value="${ crInfo.cbIdx }" />
								<input type="hidden" name="crConsultType" id="cr_consult_type" value="${ crInfo.crConsultType }" />
								
                            	<input type="hidden" name="crTel" id="crTel" value="${ crInfo.crTel }" />
                            	<input type="hidden" name="crPhone" id="crPhone" value="${ crInfo.crPhone }" />
                            	<input type="hidden" name="crTotalNum" id="crTotalNum" value="${ crInfo.crTotalNum }" />
                            	<input type="hidden" name="crCurNum" id="crCurNum" value="${ crInfo.crCurNum }" />
                                <input type="hidden" name="crBusYn" id="crBusYn" value="${ crInfo.crBusYn }" />
                                <input type="hidden" name="crBusNum" id="crBusNum" value="${ crInfo.crBusNum }" />
                                <input type="hidden" name="crFireFacility" id="crFireFacility" value="${ crInfo.crFireFacility }" />
								<input type="hidden" name="crAlertFacility" id="crAlertFacility" value="${ crInfo.crAlertFacility }" />
								<input type="hidden" name="crResucueFacility1" id="crResucueFacility1" value="${ crInfo.crResucueFacility1 }" />
								<input type="hidden" name="crResucueFacility2" id="crResucueFacility2" value="${ crInfo.crResucueFacility2 }" />
								<input type="hidden" name="crEtcFacility" id="crEtcFacility" value="${ crInfo.crEtcFacility }" />
								<input type="hidden" name="crGasFacility" id="crGasFacility" value="${ crInfo.crGasFacility }" />
								<input type="hidden" name="crCenterType" id="crCenterType" value="${ crInfo.crCenterType }" />
								-->

					<input type="hidden" name="cr_addr1" id="addr1" value="${ crInfo.addr1 }" />
					<input type="hidden" name="cr_addr2" id="addr2" value="${ crInfo.addr2 }" />
					<input type="hidden" name="cr_addr3" id="addr3" value="${ crInfo.addr3 }" />


					<div class="cs_w oh">
						<p class="cs_w_tit tl">건물 소방시설,설비 현황</p> <span class="dp_b tl pdb10 fs_13 fc_gray">*어린이집 건물 내외부 소방시설/설비의 설치·보유 현황을 체크해주세요</span>
						<div class="table_layout po_re">
							<table class="cs_w_table tb04 fl" style="width: 50%">
								<caption class="sound_only">안전관리 컨설팅 건물 소방시설,설비 현황 신청폼1</caption>
								<colgroup>
									<col span="1" style="width: 20%;">
									<col span="1" style="width: 30%;">
								</colgroup>
								<thead>
									<tr>
										<th scope="row">구분</th>
										<th scope="row">해당설비</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<th scope="row" rowspan="4">소화설비</th>
										<td style="padding: 20px 20px 21px 20px !important;"><label for="cstWr20" class="dp_b">
												<input type="checkbox" id="cstWr20" name="crFireFacility" value="분말소화기(ABC)" class="mgr5" ${ fn:contains(crInfo.crFireFacility,'분말소화기(ABC)') ? 'checked' : '' }>
												분말소화기(ABC) <a id="img-1" href="#" class="cstTbimg1 dp_ib fr"><i class="fas fa-question-circle"></i></a>
											</label> <label for="cstWr21" class="dp_b">
												<input type="checkbox" id="cstWr21" name="crFireFacility" value="K급 소화기" class="mgr5" ${ fn:contains(crInfo.crFireFacility,'K급 소화기') ? 'checked' : '' }>
												K급 소화기 <a id="img-2" href="#" class="cstTbimg2 dp_ib fr"><i class="fas fa-question-circle"></i></a>
											</label> <label for="cstWr22" class="dp_b">
												<input type="checkbox" id="cstWr22" name="crFireFacility" value="투척용소화용구" class="mgr5" ${ fn:contains(crInfo.crFireFacility,'투척용소화용구') ? 'checked' : '' }>
												투척용소화용구 <a id="img-3" href="#" class="cstTbimg3 dp_ib fr"><i class="fas fa-question-circle"></i></a>
											</label> <label for="cstWr23" class="dp_b">
												<input type="checkbox" id="cstWr23" name="crFireFacility" value="자동확산소화기" class="mgr5" ${ fn:contains(crInfo.crFireFacility,'자동확산소화기') ? 'checked' : '' }>
												자동확산소화기 <a id="img-4" href="#" class="cstTbimg4 dp_ib fr"><i class="fas fa-question-circle"></i></a>
											</label> <label for="cstWr24" class="dp_b">
												<input type="checkbox" id="cstWr24" name="crFireFacility" value="주방자동소화장치" class="mgr5" ${ fn:contains(crInfo.crFireFacility,'주방자동소화장치') ? 'checked' : '' }>
												주방자동소화장치 <a id="img-5" href="#" class="cstTbimg5 dp_ib fr"><i class="fas fa-question-circle"></i></a>
											</label></td>
									</tr>
									<tr>
										<td><label for="cstWr25" class="dp_b">
												<input type="checkbox" id="cstWr25" name="crFireFacility" value="옥내소화전설비" class="mgr5" ${ fn:contains(crInfo.crFireFacility,'옥내소화전설비') ? 'checked' : '' }>
												옥내소화전설비 <a id="img-6" href="#" class="cstTbimg6 dp_ib fr"><i class="fas fa-question-circle"></i></a>
											</label></td>
									</tr>
									<tr>
										<td><label for="cstWr26" class="dp_b">
												<input type="checkbox" id="cstWr26" name="crFireFacility" value="스프링클러설비" class="mgr5" ${ fn:contains(crInfo.crFireFacility,'스프링클러설비') ? 'checked' : '' }>
												스프링클러설비<a id="img-7" href="#" class="cstTbimg7 dp_ib fr"><i class="fas fa-question-circle"></i></a>
											</label></td>
									</tr>
									<tr>
										<td><label for="cstWr27" class="dp_b">
												<input type="checkbox" id="cstWr27" name="crFireFacility" value="간이스프링클러설비" class="mgr5" ${ fn:contains(crInfo.crFireFacility,'간이스프링클러설비') ? 'checked' : '' }>
												간이스프링클러설비<a id="img-8" href="#" class="cstTbimg8 dp_ib fr"><i class="fas fa-question-circle"></i></a>
											</label></td>
									</tr>
									<tr>
										<th scope="row" rowspan="6">경보설비</th>
										<td><label for="cstWr28" class="dp_b">
												<input type="checkbox" id="cstWr28" name="crAlertFacility" value="단독경보형감지기" class="mgr5" ${ fn:contains(crInfo.crAlertFacility,'단독경보형감지기') ? 'checked' : '' }>
												단독경보형감지기<a id="img-9" href="#" class="cstTbimg9 dp_ib fr"><i class="fas fa-question-circle"></i></a>
											</label></td>
									</tr>
									<tr>
										<td><label for="cstWr29" class="dp_b">
												<input type="checkbox" id="cstWr29" name="crAlertFacility" value="비상경보설비" class="mgr5" ${ fn:contains(crInfo.crAlertFacility,'비상경보설비') ? 'checked' : '' }>
												비상경보설비<a id="img-10" href="#" class="cstTbimg10 dp_ib fr"><i class="fas fa-question-circle"></i></a>
											</label></td>
									</tr>
									<tr>
										<td><label for="cstWr30" class="dp_b">
												<input type="checkbox" id="cstWr30" name="crAlertFacility" value="자동화재탐지설비" class="mgr5" ${ fn:contains(crInfo.crAlertFacility,'자동화재탐지설비') ? 'checked' : '' }>
												자동화재탐지설비<a id="img-11" href="#" class="cstTbimg11 dp_ib fr"><i class="fas fa-question-circle"></i></a>
											</label></td>
									</tr>
									<tr>
										<td><label for="cstWr31" class="dp_b">
												<input type="checkbox" id="cstWr31" name="crAlertFacility" value="시각경보기" class="mgr5" ${ fn:contains(crInfo.crAlertFacility,'시각경보기') ? 'checked' : '' }>
												시각경보기<a id="img-12" href="#" class="cstTbimg12 dp_ib fr"><i class="fas fa-question-circle"></i></a>
											</label></td>
									</tr>
									<tr>
										<td><label for="cstWr32" class="dp_b">
												<input type="checkbox" id="cstWr32" name="crAlertFacility" value="자동화재속보설비" class="mgr5" ${ fn:contains(crInfo.crAlertFacility,'자동화재속보설비') ? 'checked' : '' }>
												자동화재속보설비<a id="img-13" href="#" class="cstTbimg13 dp_ib fr"><i class="fas fa-question-circle"></i></a>
											</label></td>
									</tr>
									<tr>
										<td><label for="cstWr33" class="dp_b">
												<input type="checkbox" id="cstWr33" name="crAlertFacility" value="가스누설경보기" class="mgr5" ${ fn:contains(crInfo.crAlertFacility,'가스누설경보기') ? 'checked' : '' }>
												가스누설경보기<a id="img-14" href="#" class="cstTbimg14 dp_ib fr"><i class="fas fa-question-circle"></i></a>
											</label></td>
									</tr>
								</tbody>
							</table>
							<table class="cs_w_table tb04 fl" style="width: 50%">
								<caption class="sound_only">안전관리 컨설팅 건물 소방시설,설비 현황 신청폼2</caption>
								<colgroup>
									<col span="1" style="width: 20%;">
									<col span="1" style="width: 30%;">
								</colgroup>
								<thead>
									<tr>
										<th scope="row">구분</th>
										<th scope="row">해당설비</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<th scope="row" rowspan="6">피난구조설비</th>
										<td>* 피난기구<br> <label for="cstWr34" class="dp_b">
												<input type="checkbox" id="cstWr34" name="crResucueFacility1" value="미끄럼대" class="mgr5" ${ fn:contains(crInfo.crResucueFacility1,'미끄럼대') ? 'checked' : '' }>
												미끄럼대<a id="img-15" href="#" class="cstTbimg15 dp_ib fr" id="img-15"><i class="fas fa-question-circle"></i></a>
											</label> <label for="cstWr35" class="dp_b">
												<input type="checkbox" id="cstWr35" name="crResucueFacility1" value="구조대" class="mgr5" ${ fn:contains(crInfo.crResucueFacility1,'구조대') ? 'checked' : '' }>
												구조대<a id="img-16" href="#" class="cstTbimg16 dp_ib fr"><i class="fas fa-question-circle"></i></a>
											</label> <label for="cstWr36" class="dp_b">
												<input type="checkbox" id="cstWr36" name="crResucueFacility1" value="기타" class="mgr5" ${ fn:contains(crInfo.crResucueFacility1,'기타') ? 'checked' : '' }>
												기타<a id="img-17" href="#" class="cstTbimg17 dp_ib fr"><i class="fas fa-question-circle"></i></a>
											</label>
										</td>
									</tr>
									<tr>
										<td><label for="cstWr37" class="dp_b">
												<input type="checkbox" id="cstWr37" name="crResucueFacility2" value="유도등" class="mgr5" ${ fn:contains(crInfo.crResucueFacility2,'유도등') ? 'checked' : '' }>
												유도등<a id="img-18" href="#" class="cstTbimg18 dp_ib fr"><i class="fas fa-question-circle"></i></a>
											</label></td>
									</tr>
									<tr>
										<td><label for="cstWr38" class="dp_b">
												<input type="checkbox" id="cstWr38" name="crResucueFacility2" value="유도표지" class="mgr5" ${ fn:contains(crInfo.crResucueFacility2,'유도표지') ? 'checked' : '' }>
												유도표지<a id="img-19" href="#" class="cstTbimg19 dp_ib fr"><i class="fas fa-question-circle"></i></a>
											</label></td>
									</tr>
									<tr>
										<td><label for="cstWr39" class="dp_b">
												<input type="checkbox" id="cstWr39" name="crResucueFacility2" value="비상조명등" class="mgr5" ${ fn:contains(crInfo.crResucueFacility2,'비상조명등') ? 'checked' : '' }>
												비상조명등<a id="img-20" href="#" class="cstTbimg20 dp_ib fr"><i class="fas fa-question-circle"></i></a>
											</label></td>
									</tr>
									<tr>
										<td><label for="cstWr40" class="dp_b">
												<input type="checkbox" id="cstWr40" name="crResucueFacility2" value="휴대용비상조명등" class="mgr5" ${ fn:contains(crInfo.crResucueFacility2,'휴대용비상조명등') ? 'checked' : '' }>
												휴대용비상조명등<a id="img-21" href="#" class="cstTbimg21 dp_ib fr"><i class="fas fa-question-circle"></i></a>
											</label></td>
									</tr>
									<tr>
										<td><label for="cstWr41" class="dp_b">
												<input type="checkbox" id="cstWr41" name="crResucueFacility2" value="피난안내도" class="mgr5" ${ fn:contains(crInfo.crResucueFacility2,'피난안내도') ? 'checked' : '' }>
												피난안내도<a id="img-22" href="#" class="cstTbimg22 dp_ib fr"><i class="fas fa-question-circle"></i></a>
											</label></td>
									</tr>
									<tr>
										<th scope="row" rowspan="3">기타</th>
										<td><label for="cstWr42" class="dp_b">
												<input type="checkbox" id="cstWr42" name="crEtcFacility" value="방화문, 방화셔터" class="mgr5" ${ fn:contains(crInfo.crEtcFacility,'방화문, 방화셔터') ? 'checked' : '' }>
												방화문, 방화셔터<a id="img-23" href="#" class="cstTbimg23 dp_ib fr"><i class="fas fa-question-circle"></i></a>
											</label></td>
									</tr>
									<tr>
										<td><label for="cstWr43" class="dp_b">
												<input type="checkbox" id="cstWr43" name="crEtcFacility" value="옥외피난계단" class="mgr5" ${ fn:contains(crInfo.crEtcFacility,'옥외피난계단') ? 'checked' : '' }>
												옥외피난계단<a id="img-24" href="#" class="cstTbimg24 dp_ib fr"><i class="fas fa-question-circle"></i></a>
											</label></td>
									</tr>
									<tr>
										<td><label for="cstWr44" class="dp_b">
												<input type="checkbox" id="cstWr44" name="crEtcFacility" value="방염" class="mgr5" ${ fn:contains(crInfo.crEtcFacility,'방염') ? 'checked' : '' }>
												방염<a id="img-25" href="#" class="cstTbimg25 dp_ib fr"><i class="fas fa-question-circle"></i></a>
											</label></td>
									</tr>
									<tr>
										<th scope="row" rowspan="2">가스</th>
										<td><label for="cstWr45" class="dp_b">
												<input type="checkbox" id="cstWr45" name="crGasFacility" value="LNG(도시가스)" class="mgr5" ${ fn:contains(crInfo.crGasFacility,'LNG(도시가스)') ? 'checked' : '' }>
												LNG(도시가스)<a id="img-26" href="#" class="cstTbimg26 dp_ib fr"><i class="fas fa-question-circle"></i></a>
											</label></td>
									</tr>
									<tr>
										<td><label for="cstWr46" class="dp_b">
												<input type="checkbox" id="cstWr46" name="crGasFacility" value="LPG" class="mgr5" ${ fn:contains(crInfo.crGasFacility,'LPG') ? 'checked' : '' }>
												LPG<a id="img-27" href="#" class="cstTbimg27 dp_ib fr"><i class="fas fa-question-circle"></i></a>
											</label></td>
									</tr>
								</tbody>
							</table>

							<div class="cstTbSub cstTbSub1">
								<img src="${utcp.ctxPath}/resources/user/image/img/cstTbSub01.jpg" alt="분말소화기 이미지" />
								<p><strong>분말소화기</strong> : 분말소화약제를 압력에 따라 방사하는 기구로서 일반화재, 유류화재, 전기화재에 효과가 있는 소화기</p>
							</div>
							<div class="cstTbSub cstTbSub2">
								<img src="${utcp.ctxPath}/resources/user/image/img/cstTbSub02.jpg" alt="K급소화기 이미지" />
								<p><strong>K급소화기</strong> : 주방화재 진화에 적합한 소화기로 동식물유(식용유 등)로 인해 발생한 주방 화재에 효과적으로 대응할 수 있는 소화기</p>
							</div>
							<div class="cstTbSub cstTbSub3">
								<img src="${utcp.ctxPath}/resources/user/image/img/cstTbSub03.jpg" alt="투척용소화용구 이미지" />
								<p><strong>투척용소화용구</strong> : 화점의 바닥이나 벽에 직접 던져 용기에 깨지면서 나오는 소화약제에 의해 불을 끄는 방식의 투척식 소화기</p>
							</div>
							<div class="cstTbSub cstTbSub4">
								<img src="${utcp.ctxPath}/resources/user/image/img/cstTbSub04.jpg" alt="자동확산소화기 이미지" />
								<p><strong>자동확산소화기</strong> : 화재를 감지하여 자동으로 소화약제를 방출 확산시켜 국소적으로 소화하는 소화기</p>
							</div>
							<div class="cstTbSub cstTbSub5">
								<img src="${utcp.ctxPath}/resources/user/image/img/cstTbSub05.jpg" alt="주방자동소화장치 이미지" />
								<p><strong>주방자동소화장치</strong> : 주방에 설치된 열발생 조리기구의 사용으로 인한 화재 발생 시 열원(전기 또는 가스)을 자동으로 차단하며 소화약제를 방출하는 소화장치</p>
							</div>
							<div class="cstTbSub cstTbSub6">
								<img src="${utcp.ctxPath}/resources/user/image/img/cstTbSub06.jpg" alt="옥내소화전설비 이미지" />
								<p><strong>옥내소화전설비</strong> : 소화전 내의 호스를 전개하여 화점에 물을 뿌려 불을 끄는 방식의 수동식 소화설비</p>
							</div>
							<div class="cstTbSub cstTbSub7">
								<img src="${utcp.ctxPath}/resources/user/image/img/cstTbSub07.jpg" alt="스프링클러설비 이미지" />
								<p><strong>스프링클러설비</strong> : 화재 발생 시 감열된 헤드의 개방으로 소화수를 뿌려 불을 끄는 자동식 소화설비</p>
							</div>
							<div class="cstTbSub cstTbSub8">
								<img src="${utcp.ctxPath}/resources/user/image/img/cstTbSub08.jpg" alt="간이스프링클러설비 이미지" />
								<p><strong>간이스프링클러설비</strong> : 스프링클러설비에 비해 설비를 간소화시킨 방식이며 스프링클러설비와 동일하게 화재 발생 시 감열된 헤드의 개방으로 소화수를 뿌려 불을 끄는 자동식 소화설비</p>
							</div>
							<div class="cstTbSub cstTbSub9">
								<img src="${utcp.ctxPath}/resources/user/image/img/cstTbSub09.jpg" alt="단독경보형감지기 이미지" />
								<p><strong>단독경보형감지기</strong> : 화재발생 상황을 단독으로 감지하여 자체에 내장된 음향장치로 경보하는 감지기</p>
							</div>
							<div class="cstTbSub cstTbSub10">
								<img src="${utcp.ctxPath}/resources/user/image/img/cstTbSub10.jpg" alt="비상경보설비 이미지" />
								<p><strong>비상경보설비</strong> : 수신기와 발신기로 구성되며 화재발생 상황을 경종이나 사이렌으로 경보하는 설비</p>
							</div>
							<div class="cstTbSub cstTbSub11">
								<img src="${utcp.ctxPath}/resources/user/image/img/cstTbSub11.jpg" alt="자동화재탐지설비 이미지" />
								<p><strong>자동화재탐지설비</strong> : 수신기, 발신기, 감지기로 구성되며 화재초기에 발생되는 열, 연기 또는 불꽃 등을 감지기에 의해 감지하여 자동적으로 경보를 발하는 설비</p>
							</div>
							<div class="cstTbSub cstTbSub12">
								<img src="${utcp.ctxPath}/resources/user/image/img/cstTbSub12.jpg" alt="시각경보기 이미지" />
								<p><strong>시각경보기</strong> : 자동화재탐지설비에서 발하는 화재신호를 시각경보기에 전달하여 청각장애인에게 점멸형태의 시각경보를 하는 장치</p>
							</div>
							<div class="cstTbSub cstTbSub13">
								<img src="${utcp.ctxPath}/resources/user/image/img/cstTbSub13.jpg" alt="자동화재속보설비 이미지" />
								<p><strong>자동화재속보설비</strong> : 화재신호를 통신망을 통하여 음성 등의 방법으로 소방관서에 자동으로 통보하는 설비</p>
							</div>
							<div class="cstTbSub cstTbSub14">
								<img src="${utcp.ctxPath}/resources/user/image/img/cstTbSub14.jpg" alt="가스누설경보기 이미지" />
								<p><strong>가스누설경보기</strong> : 보일러 등 가스연소기에서 액화석유가스(LPG), 액화천연가스(LNG) 등의 가연성가스가 새는 것을 탐지하여 관계자나 이용자에게 경보하여 주는 장치</p>
							</div>
							<div class="cstTbSub cstTbSub15">
								<img src="${utcp.ctxPath}/resources/user/image/img/cstTbSub15.jpg" alt="미끄럼대 이미지" />
								<p><strong>미끄럼대</strong> : 화재 또는 비상시에 피난층 이외의 층으로부터 피난층 또는 지면으로 피난자가 중력에 의하여 안전하게 미끄러져 내려오는 장치</p>
							</div>
							<div class="cstTbSub cstTbSub16">
								<img src="${utcp.ctxPath}/resources/user/image/img/cstTbSub16.jpg" alt="구조대 이미지" />
								<p><strong>구조대</strong> : 포지 등을 사용하여 자루형태로 만든 것으로서 화재 시 사용자가 그 내부에 들어가서 내려옴으로써 대피할 수 있는 기구</p>
							</div>
							<div class="cstTbSub cstTbSub17">
								<img src="${utcp.ctxPath}/resources/user/image/img/cstTbSub17.jpg" alt="기타 완강기 이미지" />
								<p><strong>기타</strong></p>
							</div>
							<div class="cstTbSub cstTbSub18">
								<img src="${utcp.ctxPath}/resources/user/image/img/cstTbSub18.jpg" alt="유도등 이미지" />
								<p><strong>유도등</strong> : 화재 시에 피난을 유도하기 위한 등으로서 정상상태에서는 상용전원에 따라 켜지고 상용전원이 정전되는 경우에는 비상전원으로 자동전환되어 켜지는 등</p>
							</div>
							<div class="cstTbSub cstTbSub19">
								<img src="${utcp.ctxPath}/resources/user/image/img/cstTbSub19.jpg" alt="유도표지 이미지" />
								<p><strong>유도표지</strong> : 피난구 또는 피난경로로 사용되는 출입구를 표시하여 피난을 유도하는 표지</p>
							</div>
							<div class="cstTbSub cstTbSub20">
								<img src="${utcp.ctxPath}/resources/user/image/img/cstTbSub20.jpg" alt="비상조명등 이미지" />
								<p><strong>비상조명등</strong> : 화재발생 등에 따른 정전 시에 안전하고 원활한 피난활동을 할 수 있도록 거실 및 피난통로 등에 설치되어 자동으로 점등되는 조명등</p>
							</div>
							<div class="cstTbSub cstTbSub21">
								<img src="${utcp.ctxPath}/resources/user/image/img/cstTbSub21.jpg" alt="휴대용비상조명등 이미지" />
								<p><strong>휴대용비상조명등</strong> : 화재발생 등으로 정전 시 안전하고 원할한 피난을 위하여 피난자가 휴대할 수 있는 조명등</p>
							</div>
							<div class="cstTbSub cstTbSub22">
								<img src="${utcp.ctxPath}/resources/user/image/img/cstTbSub22.jpg" alt="피난안내도 이미지" />
								<p><strong>피난안내도</strong> : 건물 내 사용자에게 피난에 필요한 요소를 설명하고 피난동선을 파악할 수 있도록 안내해주는 그림</p>
							</div>
							<div class="cstTbSub cstTbSub23">
								<img src="${utcp.ctxPath}/resources/user/image/img/cstTbSub23.jpg" alt="방화문,방화셔터 이미지" />
								<p><strong>방화문</strong> : 화재(화염과 연기)의 확산을 막기 위하여 설치한 문<br />
									<strong>방화셔터</strong> : 화재(화염과 연기)의 확산을 막기 위하여 설치한 셔터</p>
							</div>
							<div class="cstTbSub cstTbSub24">
								<img src="${utcp.ctxPath}/resources/user/image/img/cstTbSub24.jpg" alt="옥외피난계단 이미지" />
								<p><strong>옥외피난계단</strong> : 옥외에 설치된 피난계단으로 비상시 안전한 장소로 피난을 하기 위한 계단</p>
							</div>
							<div class="cstTbSub cstTbSub25">
								<img src="${utcp.ctxPath}/resources/user/image/img/cstTbSub25.jpg" alt="방염 이미지" />
								<p><strong>방염</strong> : 화재 시 피난시간을 확보하기 위하여 연소하기 쉬운 재질에 발화 및 화염확산을 지연시키는 가공처리</p>
							</div>
							<div class="cstTbSub cstTbSub26">
								<img src="${utcp.ctxPath}/resources/user/image/img/cstTbSub26.jpg" alt="LNG 가스누설경보기 이미지" />
								<p><strong>LNG</strong> : 도시가스로 부르기도 하며, 공기보다 가벼운 가스로서 누출 시 천정부에 체류하므로 가스누설경보기의 탐지부는 천정으로부터 30㎝ 이내에 설치</p>
							</div>
							<div class="cstTbSub cstTbSub27">
								<img src="${utcp.ctxPath}/resources/user/image/img/cstTbSub27.jpg" alt="LPG 가스누설경보기 이미지" />
								<p><strong>LPG</strong> : 공기보다 무거운 가스로서 누출 시 바닥부분에 체류하므로 가스누설경보기의 탐지부는 바닥으로부터 30㎝ 이내에 설치</p>
							</div>
						</div>

						<!--// 211216 hy
                                    <p class="font_14 tl">
                                        ※ 물음표에 마우스 이동 시 시설사진 및 정보 안내
                                    </p class="agree_p">
                                    //-->
					</div>
					<!--// cs_w //-->

					<div class="cs_w oh">
						<p class="cs_w_tit tl">건물현황 <span>*건축물대장의 경우 단독형태 건물에 운영할 시 첨부(아파트단지내, 복합건물 등 제외</span>
						</p>
						<div class="table_layout po_re">
							<table class="cs_w_table tb04">
								<caption class="sound_only">안전관리 컨설팅 건물현황 신청폼</caption>
								<colgroup>
									<col span="1" style="width: 20%;">
									<col span="1" style="width: 40%;">
									<col span="1" style="width: 40%;">
								</colgroup>
								<tbody>
									<tr>
										<!-- th scope="row"><span class="bdInfo">건축물대장</span></th -->
										<th scope="row" class="tc"><span class="bdInfo dp_b">건축물대장</span> 
										<a href="javascript:location.href=encodeURI('${utcp.ctxPath}/admin/cloud/download.do?cloudPath=upload/static/&cloudFile=건축물대장_발급방법_안내.hwp&downNm=건축물대장_발급방법_안내.hwp')" class="btn04 btn_blue mgt5">한글문서</a>
										</th>
										<td>
											<div class="write_div">
												<label for="imgFile">
													<span class="sound_only">파일</span>
												</label>
												<input type="file" name="imgFile1" id="imgFile1" title="파일첨부" class="frm_file ip2">
												<c:if test="${ not empty crInfo && crInfo.crIdx ne '0' && not empty crInfo.selectAttachList[0] }">
													<div class="imgFileEdit">
														<a href="/consult/attach/${ crInfo.selectAttachList[0].fileSeq }/download.do">${ crInfo.selectAttachList[0].caFileOrg }( ${ crInfo.selectAttachList[0].caFileSize } )</a>
													</div>
												</c:if>
											</div>
										</td>
										<td>
											<div class="write_div">
												<label for="imgFile">
													<span class="sound_only">파일</span>
												</label>
												<input type="file" name="imgFile2" id="imgFile2" title="파일첨부" class="frm_file ip2">
												<c:if test="${ not empty crInfo && crInfo.crIdx ne '0' && not empty crInfo.selectAttachList[1] }">
													<div class="imgFileEdit">
														<a href="/consult/attach/${ crInfo.selectAttachList[1].fileSeq }/download.do">${ crInfo.selectAttachList[1].caFileOrg }( ${ crInfo.selectAttachList[1].caFileSize } )</a>
													</div>
												</c:if>
											</div>
										</td>
									</tr>
								</tbody>
							</table>

							<!--// 211215 hy
                                        <div class="cstTbSub bdInfoSub">
                                            <img src="${utcp.ctxPath}/resources/user/image/img/fire1.png" alt="분말소화기 이미지">
                                            <p>
                                                건축물대장 발급방법 안내
                                                <span>
                                                    민원24 검색창에서 건축물대장을 클릭하고, 건축물 소재지를 입력합니다.
                                                    대장구분은 단독주책의 경우 일반(단독주택)을 선택하고, 아파트와 빌라 등의 경우 집합(아파트, 연립주택 등)을
                                                    선택합니다. 대장종류는 전유부를 선택합니다. 그리고 민원신청을 클릭합니다.
                                                    <br>
                                                    민원신청 버튼을 클릭하면  새로운 화면에서 주소 검색 후 아파트 동과 호수를 선택할 수 있는 화면들이 조회됩니다.
                                                    아파트 동과 호수를 선택하고 최종적으로 아파트 동과 호가 정확히 입력되었다면, 민원신청을 클릭하고 출력하면 됩니다.
                                                    <br>
                                                </span>
                                            </p>
                                        </div>
										-->
						</div>
					</div>
					<!--// cs_w //-->

				</form>
			</div>
			<!--// cs_cont //-->
		</div>
		<!--// cstRgsWrap //-->

		<div class="tb_btn">
			<c:choose>
				<c:when test="${ crInfo.crIdx ne '0' }">
					<button class="btn01 btn_orange mgt20" onclick="fn_consultReg();";>수정하기</button>
				</c:when>
				<c:otherwise>
					<button class="btn01 btn_orange mgt20" onclick="fn_consultReg();";>신청하기</button>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
	<!--// tab_con1 //-->

</div>
<!--//viewDiv//-->

<!--// 만족도 조사 완료 //-->
<div class="remodal messagePop1" data-remodal-id="fbFinPop" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc">
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC">알림</p>
		</div>
		<div class="modal-body">
			<p class="messageTxt" id="msgStr">신청서 추가정보 수정 완료 하였습니다.</p>
		</div>
		<div class="modal-footer">
			<div class="tc">
				<button type="button" data-remodal-action="cancel" onclick="opener.location.reload();window.close();" class="remodal-confirm btn02 btn_green">확인</button>
			</div>
		</div>
	</div>
</div>
<!--// 만족도 조사 완료 //-->

