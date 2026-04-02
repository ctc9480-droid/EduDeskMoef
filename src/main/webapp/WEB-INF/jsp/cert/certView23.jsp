<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<!DOCTYPE HTML>
<html lang="ko-KR">
<head>

<meta charset="UTF-8">
<title><spring:eval expression="@prop['service.name']"/></title>
<meta http-equiv="Content-Type" content="text/html; utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no">
<meta http-equiv="imagetoolbar" content="no">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="keywords" content="<spring:eval expression="@prop['service.name']"/>, LMS, 교육, 온라인교육, 온라인강의">
<meta name="description" content="<spring:eval expression="@prop['service.name']"/>">
<meta name="author" content="<spring:eval expression="@prop['service.name']"/>">
<meta property="og:type" content="website">
<meta property="og:title" content="<spring:eval expression="@prop['service.name']"/>">
<meta property="og:description" content="<spring:eval expression="@prop['service.name']"/>">
<meta name="format-detection" content="telephone=no">

<!--// css //-->
<link rel="stylesheet" type="text/css" href="${utcp.ctxPath}/resources/user/css/sub.css">
<link rel="stylesheet" type="text/css" href="${utcp.ctxPath}/resources/user/css/common.css">

<!--// font_css //-->
<link rel="stylesheet" type="text/css" href="${utcp.ctxPath}/resources/admin/css/font.css">
<link href="https://fonts.googleapis.com/css2?family=Nanum+Myeongjo:wght@400;700;800&display=swap" rel="stylesheet">
<link href="https://fonts.googleapis.com/css2?family=Playball&display=swap" rel="stylesheet">


<!-- script.js -->
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
																var fileNm = "${certMap.sub.passCertNum}.pdf";
																doc
																		.save(fileNm);
															});
										});
					});
</script>

</head>
<body>
	<div id="wrap">
		<div class="certi2" id="pdfDiv">
			<h3 class="tl">Certificate No. : <span>${certMap.sub.passCertNum }</span></h3>
			<h3 class="tl">Exam date : <span>${utcp.convDateToStrByLocale(certMap.examDate,'dd MMM yyyy','ENGLISH') }</span></h3>
			<h3 class="tl">Issue date : <span>${utcp.convDateToStrByLocale(certMap.sub.passCertDe,'dd MMM yyyy','ENGLISH') }</span></h3>
			
			<h1>CERTIFICATE<span class="dp_b">OF ATTAINMENT</span></h1>
			
			<ul class="tc">
				<li>This is to certify that</li>
				<li><div class="certiNameTit">${certMap.user.userEnNm }</div></li>
				<li>has passed following exam</li>
			</ul>
			<p class="tc txt1">
				ISO 14001 : 2015<br>
                EMS Lead Auditor Course
			</p>
			<p class="tl txt2">
				and has been assessed as 'Competent' in the following competency units:<br><br>
                PCAA-EMS : Environmental Management System<br>
                PCAA-AMS : Guidelines for auditing management systems
			</p>
			
			<div class="bot_Cname">
				Approved by________________
				<span class="dp_ib mark stp"><img src="${utcp.ctxPath }/resources/user/image/img/ctSign.png" alt="한국산업기술시험원장 사인"></span>
                President of KTL
			</div>
			<div class="bot_copy">
                <img src="${utcp.ctxPath }/resources/user/image/img/favicon.png" alt="한국산업기술시험원장 사인">
                <span class="dp_ib mgr30">
                    Korea Testing Laboratory, 87 Digital-ro 26-gil,<br>
                    Guro-gu, Seoul 08389, Korea, Republic of
                </span>
                <img src="${utcp.ctxPath }/resources/user/image/img/pcaaLogo.png" alt="PCAA" class="pcaa">
			</div>
			
		</div>

		<div class="btn_step3">
			<a href="#none" class="btn_step01" id="savePdf">출력하기</a>
		</div>
	</div>
	<!--// #wrap -->

</body>
</html>