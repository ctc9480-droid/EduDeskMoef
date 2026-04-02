<%@page import="com.educare.util.XmlBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>

<%

%>

<!DOCTYPE HTML>
<html lang="ko" xml:lang=“ko” xmlns=“http://www.w3.org/1999/xhtml”>
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

<!-- style.css -->
<link rel="stylesheet" type="text/css" href="${utcp.ctxPath}/resources/user/css/sub.css">
<link rel="stylesheet" type="text/css" href="${utcp.ctxPath}/resources/user/css/slick.css" />
<link rel="stylesheet" type="text/css" href="${utcp.ctxPath}/resources/user/css/common.css">
<link rel="stylesheet" type="text/css" href="${utcp.ctxPath}/resources/common/css/style.css">

<!--// favicon -->
<link rel="apple-touch-icon" sizes="57x57" href="${utcp.ctxPath}/resources/favicon/apple-icon-57x57.png">
<link rel="apple-touch-icon" sizes="60x60" href="${utcp.ctxPath}/resources/favicon/apple-icon-60x60.png">
<link rel="apple-touch-icon" sizes="72x72" href="${utcp.ctxPath}/resources/favicon/apple-icon-72x72.png">
<link rel="apple-touch-icon" sizes="76x76" href="${utcp.ctxPath}/resources/favicon/apple-icon-76x76.png">
<link rel="apple-touch-icon" sizes="114x114" href="${utcp.ctxPath}/resources/favicon/apple-icon-114x114.png">
<link rel="apple-touch-icon" sizes="120x120" href="${utcp.ctxPath}/resources/favicon/apple-icon-120x120.png">
<link rel="apple-touch-icon" sizes="144x144" href="${utcp.ctxPath}/resources/favicon/apple-icon-144x144.png">
<link rel="apple-touch-icon" sizes="152x152" href="${utcp.ctxPath}/resources/favicon/apple-icon-152x152.png">
<link rel="apple-touch-icon" sizes="180x180" href="${utcp.ctxPath}/resources/favicon/apple-icon-180x180.png">
<link rel="icon" type="image/png" sizes="192x192" href="${utcp.ctxPath}/resources/favicon/android-icon-192x192.png">
<link rel="icon" type="image/png" sizes="32x32" href="${utcp.ctxPath}/resources/favicon/favicon-32x32.png">
<link rel="icon" type="image/png" sizes="96x96" href="${utcp.ctxPath}/resources/favicon/favicon-96x96.png">
<link rel="icon" type="image/png" sizes="16x16" href="${utcp.ctxPath}/resources/favicon/favicon-16x16.png">
<meta name="msapplication-TileColor" content="#ffffff">
<meta name="msapplication-TileImage" content="/resources/favicon/ms-icon-144x144.png">
<meta name="theme-color" content="#ffffff">

<!--// font //-->
<link href="//fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@300;400;500&display=swap" rel="stylesheet">
<link href="//fonts.googleapis.com/css2?family=Playball&amp;display=swap" rel="stylesheet">
<link href="//fonts.googleapis.com/css2?family=Nanum+Myeongjo:wght@400;700;800&amp;display=swap" rel="stylesheet">

<!--// fontawesome //-->
<link href="${utcp.ctxPath }/resources/fontawesome/css/all.css" rel="stylesheet">

<!-- script.js -->
<script src="${utcp.ctxPath}/resources/user/js/jquery-3.5.1.min.js"></script>
<script type="text/javascript" src="${utcp.ctxPath}/resources/user/js/slick.min.js"></script>
<script type="text/javascript" src="${utcp.ctxPath}/resources/user/js/common.js"></script>
<script type="text/javascript" src="${utcp.ctxPath}/resources/common/js/util.js"></script>

<!--// modal //-->
<link rel="stylesheet" href="${utcp.ctxPath}/resources/admin/css/remodal.css">
<link rel="stylesheet" href="${utcp.ctxPath}/resources/admin/css/remodal-default-theme.css">
<script src="${utcp.ctxPath}/resources/admin/js/remodal.js" type="text/javascript"></script>
<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>

<!-- pickadate.js -->
<link rel="stylesheet" href="${utcp.ctxPath}/resources/plugins/pickadate/default.css">
<link rel="stylesheet" href="${utcp.ctxPath}/resources/plugins/pickadate/default.date.css">
<script src="${utcp.ctxPath}/resources/plugins/pickadate/picker.js"></script>
<script src="${utcp.ctxPath}/resources/plugins/pickadate/picker.date.js"></script>
<script src="${utcp.ctxPath}/resources/plugins/pickadate/legacy.js"></script>
<script type="text/javascript">
	$(function() {
		// pickadate //
		var $input = $('.datepicker').pickadate(
				{
					monthsFull : [ '1월', '2월', '3월', '4월', '5월', '6월', '7월',
							'8월', '9월', '10월', '11월', '12월' ],
					monthsShort : [ '1', '2', '3', '4', '5', '6', '7', '8',
							'9', '10', '11', '12' ],
					weekdaysFull : [ '일요일', '월요일', '화요일', '수요일', '목요일', '금요일',
							'토요일' ],
					weekdaysShort : [ '일', '월', '화', '수', '목', '금', '토' ],
					format : 'yyyymmdd',
					formatSubmit : 'yyyymmdd',
					today : "오늘",
					clear : "지우기",
					close : '닫기',
					container : '#container',
					labelMonthNext : '다음달 넘어가기',
					labelMonthPrev : '이전달 넘어가기',
					labelMonthSelect : '월 선택',
					labelYearSelect : '년도 선택',
					selectYears : 200,
					selectMonths : true
				//min:true
				});
		// pickadate //
	});
</script>

<!-- vue -->
<script type="text/javascript" src="${utcp.ctxPath}/resources/plugins/vue/vue.js"></script>
<script type="text/javascript" src="${utcp.ctxPath}/resources/plugins/moment/moment.min.js"></script>
<script type="text/javascript" src="${utcp.ctxPath}/resources/plugins/moment/moment-with-locales.min.js"></script>
<script type="text/javascript" src="${utcp.ctxPath}/resources/plugins/vue/vue-custom.js"></script>

<script type="text/javascript">
	var pwChk = false;
	$(document).ready(function() {
	});

	function fn_nextStep(step) {
		location.href = 'join.do?stepNo=' + step;
	}
</script>


<link rel="stylesheet" type="text/css" href="${utcp.ctxPath}/resources/plugins/jquery/jquery.loading-indicator.css" /> 
	<script type="text/javascript" src="${utcp.ctxPath}/resources/plugins/jquery/jquery.loading-indicator.min.js"></script>
	<script>
	var homeLoader;
	$( document ).ready(function() {
		homeLoader = $('body').loadingIndicator({
			useImage: false
		}).data("loadingIndicator");
		homeLoader.hide();
		$('.loading-indicator-wrapper').css('position','fixed');
	});
	$( document ).ajaxSend(function(evt, request, settings) {
		if(typeof homeLoader !== 'undefined'){
			homeLoader.show();
		}
			
	});
	$( document ).ajaxStop(function() {
		if(typeof homeLoader !== 'undefined'){
			homeLoader.hide();
		}
	});
	</script>

<!-- jquery ui -->
	<link rel="stylesheet" href="${utcp.ctxPath }/resources/plugins/jquery/jquery-ui.css">
	<script src="${utcp.ctxPath }/resources/plugins/jquery/jquery-ui.js"></script>
	<script>
	// Datepicker 한글화 설정
    $.datepicker.setDefaults({
        closeText: '닫기',
        prevText: '이전',
        nextText: '다음',
        currentText: '오늘',
        monthNames: ['1월','2월','3월','4월','5월','6월',
                     '7월','8월','9월','10월','11월','12월'],
        monthNamesShort: ['1월','2월','3월','4월','5월','6월',
                          '7월','8월','9월','10월','11월','12월'],
        dayNames: ['일요일','월요일','화요일','수요일','목요일','금요일','토요일'],
        dayNamesShort: ['일','월','화','수','목','금','토'],
        dayNamesMin: ['일','월','화','수','목','금','토'],
        weekHeader: '주',
        dateFormat: 'yy-mm-dd',
        firstDay: 0,
        isRTL: false,
        showMonthAfterYear: true,
        yearSuffix: '년',
        yearRange: "1950:2100",
    });
	</script>
</head>
<body>
	<div id="wrap">
		<jsp:include page="/WEB-INF/jsp/layout/user/header.jsp" />
		<div id="container">
			<div id="content">
				<div class="listWrap step2" style="padding-top: 0px;">
					<div class="login_tiitle">회원가입</div>
					<div class="step01">
						<div class="step">
							<ul>
								<li>
									<c:choose>
										<c:when test="${stepNo eq '1' }">
											<img src="${utcp.ctxPath}/resources/user/image/icon/icon_join01_on.png" alt="약관동의 아이콘" />
										</c:when>
										<c:otherwise>
											<img src="${utcp.ctxPath}/resources/user/image/icon/icon_join01_off.png" alt="약관동의 아이콘" />
										</c:otherwise>
									</c:choose>
								</li>
								<li class="step_txt">
									STEP1
									<br>
									약관동의
								</li>
							</ul>
						</div>
						<div class="step_arrow">
							<img src="${utcp.ctxPath}/resources/user/image/icon/icon_arroow.png" alt="오른쪽 화살표" />
						</div>
						<div class="step">
							<ul>
								<li>
									<c:choose>
										<c:when test="${stepNo eq '2' }">
											<img src="${utcp.ctxPath}/resources/user/image/icon/icon_join01_on.png" alt="약관동의 아이콘" />
										</c:when>
										<c:otherwise>
											<img src="${utcp.ctxPath}/resources/user/image/icon/icon_join01_off.png" alt="약관동의 아이콘" />
										</c:otherwise>
									</c:choose>
								</li>
								<li class="step_txt">
									STEP2
									<br>
									본인인증
								</li>
							</ul>
						</div>
						<div class="step_arrow">
							<img src="${utcp.ctxPath}/resources/user/image/icon/icon_arroow.png" alt="오른쪽 화살표" />
						</div>
						<div class="step">
							<ul>
								<li>
									<c:choose>
										<c:when test="${ fn:indexOf('3',stepNo)>=0 }">
											<img src="${utcp.ctxPath}/resources/user/image/icon/icon_join02_on.png" alt="정보입력 아이콘" />
										</c:when>
										<c:otherwise>
											<img src="${utcp.ctxPath}/resources/user/image/icon/icon_join02_off.png" alt="정보입력 아이콘" />
										</c:otherwise>
									</c:choose>
								</li>
								<li class="step_txt">
									STEP3
									<br>
									정보입력
								</li>
							</ul>
						</div>
						<div class="step_arrow">
							<img src="${utcp.ctxPath}/resources/user/image/icon/icon_arroow.png" alt="오른쪽 화살표" />
						</div>

						<div class="step">
							<ul>
								<li>
									<c:choose>
										<c:when test="${stepNo eq '4' }">
											<img src="${utcp.ctxPath}/resources/user/image/icon/icon_join02_on.png" alt="가입완료 아이콘" />
										</c:when>
										<c:otherwise>
											<img src="${utcp.ctxPath}/resources/user/image/icon/icon_join02_off.png" alt="가입완료 아이콘" />
										</c:otherwise>
									</c:choose>
								</li>
								<li class="step_txt">
									STEP4
									<br>
									가입완료
								</li>
							</ul>
						</div>
					</div>
					<form name="form_join" method="post">
						<input type="hidden" name="stepNo" value="${stepNo }" />
						<input type="hidden" name="joinGb" value="${joinGb }" />
						
						<!-- 본인인증 데이터 -->
						<input type="hidden" name="authTypeNm"  id="authTypeNm" value="${param.authTypeNm }"/>
						<input type="hidden" name="authUserNm"  id="authUserNm" value="${param.authUserNm }"/>
						<input type="hidden" name="authBirth"   id="authBirth" value="${param.authBirth }" />
						<input type="hidden" name="authMfType"  id="authMfType" value="${param.authMfType }" />
						<input type="hidden" name="authMobile"  id="authMobile" value="${param.authMobile }"/>
						<input type="hidden" name="authEncData" id="authEncData" value="${param.authEncData }"/>
					</form>
					<c:set var="join_suffix" value=""/>
					<c:if test="${stepNo eq '3' }">
						<c:set var="join_suffix" value="_${joinGb }"/>
					</c:if>
					<jsp:include page="/WEB-INF/jsp/user/member/join_inc_${stepNo }${join_suffix }.jsp"></jsp:include>
					<script>
					function fn_join_next(stepNo,joinGb) {
						if(stepNo == 2){
							var agree1 = $('input:radio[name=joinAgree1]:checked').val();
							//var agree2 = $('input:radio[name=joinAgree2]:checked').val();
							//var agree3 = $('input:radio[name=joinAgree3]:checked').val();
							if(agree1 != 'Y'){ alert('개인정보수집 및 이용에 동의하셔야 합니다.');$('#join-agree1-1').focus();return; }
							//if(agree2 != 'Y'){ alert('개인정보 수집,이용 내역에 동의하셔야 합니다.');$('#join-agree1-1').focus();return; }
							//if(agree3 != 'Y'){ alert('개인정보 제3자 제공 동의에 동의하셔야 합니다.');$('#join-agree1-1').focus();return; }
						}
						
						var _form = $('form[name=form_join]');
						$('input[name=stepNo]').val(stepNo);
						if(joinGb){
							$('input[name=joinGb]').val(joinGb);
						}
						_form.submit();
					}
					function fn_checkplus_callback(authTypeNm,authUserNm,authBirth,sGender,authMobile,authEncData){
						console.log(authTypeNm);
						console.log(authUserNm);
						console.log(authBirth);
						console.log(sGender);
						console.log(authMobile);
						console.log(authEncData);
						document.getElementById("authTypeNm").value = authTypeNm; 
						document.getElementById("authUserNm").value = authUserNm; 
						document.getElementById("authBirth").value = authBirth; 
						document.getElementById("authMfType").value = sGender; 
						document.getElementById("authMobile").value = authMobile; 
						document.getElementById("authEncData").value = authEncData; 
						fn_join_next(3);
					}
					function fn_checkplus_callback2(authMobile){
						document.getElementById("authMobile").value = authMobile; 
						var mobileArr = $('#authMobile').val().split('-');
						$('#mobile1').val(mobileArr[0]);
						$('#mobile2').val(mobileArr[1]);
						$('#mobile3').val(mobileArr[2]);
					}
					</script>
					
					<%
						String domainNm = request.getScheme() + "://" + request.getServerName(); //XmlBean.getConfigValue("domain.name");
						
						String sCheckplusUrl = domainNm + "/user/checkplus_main.do";      // 휴태폰본인인증 URL
						String sIpinUrl = domainNm + "/user/ipin_main.do";      // IPIN 본인인증 URL
					%>
					<script language='javascript'>
						function fnMobileCheckPopup(onlyMobilep){
							console.log("휴대폰 본인확인 서비스")
							var onlyMobile = '0';
							if(onlyMobilep){
								onlyMobile = onlyMobilep;
							}
							window.open('<%= sCheckplusUrl %>?onlyMobile='+onlyMobile, 'popupChk', 'width=500, height=550, top=100, left=100, fullscreen=no, menubar=no, status=no, toolbar=no, titlebar=yes, location=no, scrollbar=no');
						}
						
						function fnIpinCheckPopup(){
							window.open('<%= sIpinUrl %>', 'ipinPopupChk', 'width=500, height=550, top=100, left=100, fullscreen=no, menubar=no, status=no, toolbar=no, titlebar=yes, location=no, scrollbar=no');
						}
					</script>
				</div>
			</div>
			<!--// content -->
		</div>
		<!--// #container -->
		<jsp:include page="/WEB-INF/jsp/layout/user/footer.jsp" />
	</div>
	<!--// #wrap -->
		<jsp:include page="/WEB-INF/jsp/layout/comm_modal.jsp" flush="false" />
</body>
</html>

