<%@page import="org.codehaus.jackson.map.ObjectMapper"%>
<%@page import="com.educare.util.LncUtil"%>
<%@page import="org.codehaus.jackson.map.exc.UnrecognizedPropertyException"%>
<%@page import="org.codehaus.jackson.type.TypeReference"%>
<%@page import="com.educare.edu.comn.model.UserComp"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<script src="${utcp.ctxPath}/resources/user/js/jquery-3.5.1.min.js"></script>
<script src="${utcp.ctxPath}/resources/user/js/common.js"></script>


<script src="${utcp.ctxPath}/resources/plugins/html2canvas/html2canvas.min.js"></script>
<script src="${utcp.ctxPath}/resources/user/js/jspdf.min.js"></script>


<script type="text/javascript">
	$(document)
			.ready(
					function() {
						$('#savePdf')
								.click(
										function() {
											html2canvas($('#pdfDiv')[0],{
												scale : 2,
											})
													.then(
															function(canvas) {
																var imgData = canvas
																		.toDataURL('image/png');

																var imgWidth = 210; // 이미지 가로 길이(mm) / A4 기준 210mm
																var pageHeight = imgWidth * 1.414; // 출력 페이지 세로 길이 계산 A4 기준
																var imgHeight = canvas.height
																		* imgWidth
																		/ canvas.width;
																var heightLeft = imgHeight;
																var margin = 0; // 출력 페이지 여백설정
																var doc = new jsPDF(
																		'p',
																		'mm',
																		'a4');
																var width = doc.internal.pageSize
																		.getWidth();
																var height = doc.internal.pageSize
																		.getHeight();
																var position = 0;

																// 첫 페이지 출력
																doc
																		.addImage(
																				imgData,
																				'PNG',
																				margin,
																				position,
																				imgWidth,
																				imgHeight);
																heightLeft -= pageHeight;

																// 한 페이지 이상일 경우 루프 돌면서 출력
																while (heightLeft >= 20) {
																	position = heightLeft
																			- imgHeight;
																	doc
																			.addPage();
																	doc
																			.addImage(
																					imgData,
																					'PNG',
																					0,
																					position,
																					imgWidth,
																					imgHeight);
																	heightLeft -= pageHeight;
																}

																// 파일 저장
																var fileNm = "${certMap.sub.complCertNum }.pdf";
																doc
																		.save(fileNm);
															});
										});
					});
</script>
	<div id="wrap">
		<div class="certi8 entercerti" id="pdfDiv">
			<div class="certi-logo">
				<img src="${utcp.ctxPath }/resources/user/image/img/entcerti_logo.png" alt="입교증로고" />
			</div>
			<h1>입&nbsp;교&nbsp;증</h1>
			<ul class="tl">
				<li><div class="certiNameTit">소속</div><div class="pdl3 pdr3">:</div><div class="certiName">${data.user.userOrgNm }</div></li>
				<li><div class="certiNameTit">직위</div><div class="pdl3 pdr3">:</div><div class="certiName">${data.user.userGradeNm }</div></li>
				<li><div class="certiNameTit">성명</div><div class="pdl3 pdr3">:</div><div class="certiName">${data.user.userNm }</div></li>
				<li><div class="certiNameTit">과정</div><div class="pdl3 pdr3">:</div><div class="certiName">${data.lctre.eduNm }</div></li>
				<li><div class="certiNameTit">교육기간</div><div class="pdl3 pdr3">:</div><div class="certiName">
					${utcp.convDateToStr(utcp.convStrToDate(data.lctre.eduPeriodBegin,'yyyy-MM-dd'),'yyyy.MM.dd.') }  ~ ${utcp.convDateToStr(utcp.convStrToDate(data.lctre.eduPeriodEnd,'yyyy-MM-dd'),'yyyy.MM.dd.') }
				</div></li>
				<li><div class="certiNameTit">교육구분</div><div class="pdl3 pdr3">:</div><div class="certiName">${data.lctre.ctg1Nm }</div></li>
			</ul>
			
			<p class="entertxt tl mgt40" style="margin-right: 10px;">&nbsp;&nbsp;위 사람은 재정경제부에서 주관하는 교육과정에 정상적으로 수강신청이 되었음을 증명합니다.</p>
			<p class="enterdate tc mgt40">${utcp.convDateToStr(data.lcrcp.regDe,'yyyy년 MM월 dd일') }</p>
			<div class="bot_Cname"> 
				재정경제부장관
				<span class="dp_ib mark stp"><img src="${utcp.ctxPath }/resources/user/image/img/stp.png" alt="직인이미지"></span>
			</div>
		
		</div>

		<div class="btn_step3">
			<!-- <a href="javascript:window.print();" class="btn_step01">출력하기</a> -->
			<a href="#none" class="btn_step01" id="savePdf">출력하기</a>
		</div>
	</div>

<!-- 
<div id="wrap">
	<div id="content" class="pdt10 w100">

		<div class="siCard">
			<h3>입교증 (준비중)</h3>
			<table class="tb01">
				<caption class="sound_only">접수증 - 교육명, 교육기간, 교육장소, 교육비,
					신청기과느 수강자, 연락처, 생년월일, 이메일, 신청상태 순으로 정보를 제공합니다.</caption>
				<colgroup>
					<col width="18%">
					<col width="32%">
					<col width="18%">
					<col width="32%">
				</colgroup>
				<tbody>
					<tr>
						<th>교육명</th>
						<td class="tl">
							<%-- <span class="dp_b fc_blue">[${data.lctre.ctg3Nm }]</span> --%>
							${data.lctre.eduNm }</td>
						<th>교육기간</th>
						<td class="tl">
						${utcp.convDateToStr(utcp.convStrToDate(data.lctre.eduPeriodBegin,'yyyy-MM-dd'),'yyyy-MM-dd') } 
						~ ${utcp.convDateToStr(utcp.convStrToDate(data.lctre.eduPeriodEnd,'yyyy-MM-dd'),'yyyy-MM-dd') }
						<br/>(${classTime })
						</td>
					</tr>
					<tr>
						<th>교육장소</th>
						<td class="tl">${data.lctre.addrMemo }</td>
						<th>교육비</th>
						<td class="tl fc_red"><c:choose>
								<c:when test="${data.lctre.fee != 0 }">
									<fmt:formatNumber type="number" maxFractionDigits="3"
										value='${data.lctre.fee}' />원 
								</c:when>
								<c:otherwise>
									무료
									</c:otherwise>
							</c:choose></td>
					</tr>
					<tr>
						<th>신청기관</th>
						<td class="tl">${data.user.userOrgNm}</td>
						<th>수강자</th>
						<td class="tl">${data.user.userNm }</td>
					</tr>
					<tr>
						<th>연락처</th>
						<td class="tl">${data.lcrcp.mobile }</td>
						<th>생년월일</th>
						<td class="tl">${data.lcrcp.birth }</td>
					</tr>
					<tr>
						<th>이메일</th>
						<td class="tl">${data.lcrcp.email }</td> 
						<th>신청상태</th>
						<td class="tl">접수완료</td>
					</tr>
				</tbody>
			</table>

			<p class="fs_16 tc mgt30">상기와 같이 수강접수 되었음을 확인하여 드립니다.</p>
			<p class="fs_12 tc mg15">${utcp.convDateToStr(data.lcrcp.regDe,'yyyy년 MM월 dd일') }</p>
			<p class="fs_16 tc">재정경제부</p> 
		</div>
		<div class="btn_step3">
			<a href="javascript:window.print();" class="btn_step01">출력하기</a>
		</div>

	</div>
	content -->

</div>
<!--// #wrap -->