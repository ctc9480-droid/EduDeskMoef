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
																var fileNm = "${certMap.sub.complCertNum}.pdf";
																doc
																		.save(fileNm);
															});
										});
					});
</script>

</head>
<body>
	<div id="wrap">
		<div class="certi7" id="pdfDiv">
			<h3 class="tl">증서번호 : <span>${certMap.sub.complCertNum }</span></h3>
			
			<h1>수&nbsp;&nbsp;료&nbsp;&nbsp;증</h1>
			
			<ul class="tl">
				<li><div class="certiNameTit">성 명</div><div class="pdl5 pdr5">:</div><div class="certiName">${certMap.user.userNm}</div></li>
				<li><div class="certiNameTit">소 속</div><div class="pdl5 pdr5">:</div><div class="certiName">${certMap.compNm }</div></li>
				<li><div class="certiNameTit">생 년 월 일</div><div class="pdl5 pdr5">:</div><div class="certiName">${utcp.convDateToStr(utcp.convStrToDate(certMap.user.birth,'yyyyMMdd'),'yyyy-MM-dd')}</div></li>
				<li><div class="certiNameTit">과 정 명</div><div class="pdl5 pdr5">:</div><div class="certiName">측정불확도 추정(공통)</div></li>
				<li><div class="certiNameTit">교 육 기 간</div><div class="pdl5 pdr5">:</div><div class="certiName">
				${utcp.convDateToStr(utcp.convStrToDate(certMap.subStartDt,'yyyyMMdd'),'yyyy-MM-dd') } ~ 
			${utcp.convDateToStr(utcp.convStrToDate(certMap.subEndDt,'yyyyMMdd'),'yyyy-MM-dd') }
			(${certMap.subClassTime }) </div></li>
			</ul>
			
			<p class="txt1">
				위 사람은 적합성평가 관리 등에 관한 법률 제17조,<br>
				KOLAS 전문인력 양성기관 지정 및 교육과정 운영요령<br>
				제20조에 따라 실시한 위의 교육과정을 이수하였기에 본 증서를 수여합니다.
			</p>
			
			<%-- 
			<p class="date">
			<span class="ctYear">${utcp.convDateToStr(certMap.stdnt.certDe,'yyyy') }</span>년<span class="ctMonth">${utcp.convDateToStr(certMap.stdnt.certDe,'MM') }</span>월<span class="ctDate">${utcp.convDateToStr(certMap.stdnt.certDe,'dd') }</span>일
			</p>
			 --%>
			
			<p class="date">
			<span class="ctYear">${utcp.convDateToStr(certMap.sub.complCertDe,'yyyy') }</span>년
			<span class="ctMonth">${utcp.convDateToStr(certMap.sub.complCertDe,'MM') }</span>월
			<span class="ctDate">${utcp.convDateToStr(certMap.sub.complCertDe,'dd') }</span>일
			</p>
			
			<div class="bot_Cname">
				한국산업기술시험원장
				<span class="dp_ib mark stp"><img src="${utcp.ctxPath }/resources/user/image/img/stp.png" alt="직인이미지"></span>
			</div>
		
		</div>

		<div class="btn_step3">
			<a href="#none" class="btn_step01" id="savePdf">출력하기</a>
		</div>
	</div>
	<!--// #wrap -->

</body>
</html>