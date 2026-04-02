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
																var fileNm = "${certMap.stdnt.passCertNum}.pdf";
																doc
																		.save(fileNm);
															});
										});
					});
</script>
<script type="text/javascript">
	$(document)
			.ready(
					function() {
						$('#savePdf2')
								.click(
										function() {
											html2canvas($('#pdfDiv2')[0],{
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
																var fileNm = "${certMap.stdnt.passCertNum}_훈련내용.pdf";
																doc
																		.save(fileNm);
															});
										});
					});
</script>
</head>
<body>
	<div id="wrap">
		<div class="certi1" id="pdfDiv">
			<h3 class="tl"><span>${certMap.stdnt.passCertNum}</span>호</h3>
			
			<h1>수료증</h1>
			
			<ul class="tl">
				<li><div class="certiNameTit">성 명</div><div class="pdl3 pdr3">:</div><div class="certiName">${certMap.user.userNm}</div></li>
				<li><div class="certiNameTit">생년월일</div><div class="pdl3 pdr3">:</div><div class="certiName">${utcp.convDateToStr(utcp.convStrToDate(certMap.user.birth,'yyyyMMdd'),'yyyy.MM.dd')}</div></li>
				<li><div class="certiNameTit">교육과정</div><div class="pdl3 pdr3">:</div><div class="certiName">${certMap.lctre.ctg3Nm }</div></li>
				<li><div class="certiNameTit">교육기간</div><div class="pdl3 pdr3">:</div><div class="certiName">
				${utcp.convDateToStr(utcp.convStrToDate(certMap.lctre.eduPeriodBegin,'yyyy-MM-dd'),'yyyy.MM.dd')} 
				~ ${utcp.convDateToStr(utcp.convStrToDate(certMap.lctre.eduPeriodEnd,'yyyy-MM-dd'),'yyyy.MM.dd')} </div></li>
			</ul>
			
			<p class="tc txt1">
				위 사람은 ｢국민내일배움카드 운영규정｣<br>제38조 제3항에 따라 위의<br>직업능력개발훈련과정을 수료하였으므로<br>이 증서를 수여합니다.
			</p>
			
			<%-- 
			 --%>
			<p class="date">
			<span class="ctYear">${utcp.convDateToStr(certMap.stdnt.certDe,'yyyy') }</span>년<span class="ctMonth">${utcp.convDateToStr(certMap.stdnt.certDe,'MM') }</span>월<span class="ctDate">${utcp.convDateToStr(certMap.stdnt.certDe,'dd') }</span>일
			</p>
			
			<div class="bot_Cname">
				한국산업기술시험원장
				<span class="dp_ib mark stp"><img src="${utcp.ctxPath }/resources/user/image/img/stp.png" alt="직인이미지"></span>
			</div>
		
		</div>
		
		<div class="btn_step3">
			<a href="#none" class="btn_step01" id="savePdf">출력하기</a>
		</div>
		
		<div class="certiTable" id="pdfDiv2">
            <div class="sub_txt">
                <span class=""><img src="../resources/user/image/icon/icon_subtitle.png" alt="">세부 훈련내용</span>
            </div>
			<table class="tb02">
                <caption class="blind">세부훈련내용 - No, 분류, 교과목, 시수 순으로 정보를 제공합니다.</caption>
                <colgroup>
                    <col style="width:7%">
                    <col style="">
                    <col style="width:23%">
                </colgroup>
                <thead>
                    <tr>
                        <th>No</th>
                        <th>교과목</th>
                        <th>시수</th>
                    </tr>
                </thead>
                <tbody>
                   	<c:forEach items="${certMap.subList }" var="o" varStatus="s">
                    <tr>
                        <td>${s.count }</td>
                        <td>${o.subNm }</td>
                        <td>${o.classTmNm }</td>
                    </tr>
                   	</c:forEach>
                   <!--  <tr>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr> -->
                </tbody>
            </table>
		</div>
		<div class="btn_step3">
			<a href="#none" class="btn_step01" id="savePdf2">출력하기</a>
		</div>

	</div>
	<!--// #wrap -->


</body>
</html>