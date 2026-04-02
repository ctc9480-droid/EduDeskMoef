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

<c:if test="${empty certMap.user.userEnNm }">
<script>
alert('영문이름이 필요한 수료증입니다. 회원정보수정에서 영문이름을 입력 후 발급받으시기 바랍니다.');
window.close();
</script>
</c:if>

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
		<div class="certi5" id="pdfDiv">
			<h3 class="tl">Certificate No. : <span>${certMap.sub.passCertNum }</span></h3>
			<h3 class="tl">Exam date : <span>${utcp.convDateToStrByLocale(certMap.examDate,'dd MMM yyyy','ENGLISH') }</span></h3>
			<h3 class="tl">Issue date : <span>${utcp.convDateToStrByLocale(certMap.sub.passCertDe,'dd MMM yyyy','ENGLISH') }</span></h3>
			
			<h1><span class="dp_b">CERTIFICATE OF ATTAINMENT</span></h1>
			
			<ul class="tc">
				<li><div class="certiNameTit">${certMap.user.userEnNm }</div></li>
				<li>has passed</li>
			</ul>
			
			<h2>Good Manufacturing Practice<span class="dp_b">Expert Exam</span></h2>
			
			<p class="tl txt2">
				and has been assessed as 'Competent' in the following knowledge:
			</p>
			<table class="tb01 fl">
                <colgroup>
                    <col style="width: 25%">
                    <col style="width: 75%">
                </colgroup>
                <tbody>
                    <tr>
                        <th rowspan="3">Grade</th>
                        <td class="tl">□ GMP Associate(Lv 3.)</td>
                    </tr>
                    <tr>
                        <td class="tl">■ GMP Specialist(Lv 2.)</td>
                    </tr>
                    <tr>
                        <td class="tl">□ GMP Professional(Lv 1.)</td>
                    </tr>
                </tbody>
            </table>
            
            <table class="tb01 fl">
                <colgroup>
                    <col style="width: 25%">
                    <col style="width: 75%">
                </colgroup>
                <tbody>
                    <tr>
                        <th rowspan="3">Scope</th>
                        <td class="tl">■ Pharmaceutical</td>
                    </tr>
                    <tr>
                        <td class="tl">□ Food</td>
                    </tr>
                    <tr>
                        <td class="tl">□ Cosmetic</td>
                    </tr>
                </tbody>
            </table>
			
			<ul class="w100 tl lineStyle">
				<li><em>- Associate (Lv 3.)</em>Know that principles of GMP and how to managing Quality Control.</li>
				<li><em>- Specialist (Lv 2.)</em>Understand that Data analysis ability and Knowledge of GMP.</li>
				<li><em>- Professional (Lv 1.)</em>Ensure that Data analysis ability, Knowledge of GMP and Experience based problem solving skills.</li>
			</ul>
			
			<div class="bot_Cname">
				Approved by________________
				<span class="dp_ib mark stp"><img src="${utcp.ctxPath }/resources/user/image/img/ctSign.png" alt="한국산업기술시험원장 사인"></span>
                President of KTL
			</div>
			<div class="bot_copy">
                <img src="${utcp.ctxPath }/resources/user/image/img/favicon.png" alt="한국산업기술시험원장 사인">
				<span class="dp_ib mgr30">
                    Korea Testing Laboratory (Chungmugong-dong), 10<br>
                    Chungui-ro, Jinju-si, Gyengsangnam-do, Korea
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