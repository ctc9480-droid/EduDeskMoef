<%@page import="com.educare.edu.menu.service.MenuUtil"%>
<%@page import="com.educare.util.XmlBean"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="com.educare.edu.member.service.SessionUserInfoHelper"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>

<!DOCTYPE HTML>
<html lang="ko" xml:lang=“ko” xmlns=“http://www.w3.org/1999/xhtml”>
<head>
<title><spring:eval expression="@prop['service.name']" /></title>
<meta http-equiv="Content-Type" content="text/html; utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no">
<meta http-equiv="imagetoolbar" content="no">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="keywords"
	content="<spring:eval expression="@prop['service.name']"/>, 학습관리, 시스템, 교육, 단체, 개인">
<meta name="description"
	content="<spring:eval expression="@prop['service.name']"/>">
<meta name="author"
	content="<spring:eval expression="@prop['service.name']"/>">
<meta property="og:type" content="website">
<meta property="og:title"
	content="<spring:eval expression="@prop['service.name']"/>">
<meta property="og:description"
	content="<spring:eval expression="@prop['service.name']"/>">
<meta name="format-detection" content="telephone=no">

<!-- style.css -->
<link rel="stylesheet" type="text/css"
	href="${utcp.ctxPath }/resources/user/css/main.css?3">
<link rel="stylesheet" type="text/css"
	href="${utcp.ctxPath}/resources/user/css/sub.css?3">
<link rel="stylesheet" type="text/css"
	href="${utcp.ctxPath}/resources/user/css/slick.css" />
<link rel="stylesheet" type="text/css"
	href="${utcp.ctxPath}/resources/user/css/common.css">

<!--// favicon -->
<link rel="apple-touch-icon" sizes="57x57"
	href="${utcp.ctxPath}/resources/favicon/apple-icon-57x57.png">
<link rel="apple-touch-icon" sizes="60x60"
	href="${utcp.ctxPath}/resources/favicon/apple-icon-60x60.png">
<link rel="apple-touch-icon" sizes="72x72"
	href="${utcp.ctxPath}/resources/favicon/apple-icon-72x72.png">
<link rel="apple-touch-icon" sizes="76x76"
	href="${utcp.ctxPath}/resources/favicon/apple-icon-76x76.png">
<link rel="apple-touch-icon" sizes="114x114"
	href="${utcp.ctxPath}/resources/favicon/apple-icon-114x114.png">
<link rel="apple-touch-icon" sizes="120x120"
	href="${utcp.ctxPath}/resources/favicon/apple-icon-120x120.png">
<link rel="apple-touch-icon" sizes="144x144"
	href="${utcp.ctxPath}/resources/favicon/apple-icon-144x144.png">
<link rel="apple-touch-icon" sizes="152x152"
	href="${utcp.ctxPath}/resources/favicon/apple-icon-152x152.png">
<link rel="apple-touch-icon" sizes="180x180"
	href="${utcp.ctxPath}/resources/favicon/apple-icon-180x180.png">
<link rel="icon" type="image/png" sizes="192x192"
	href="${utcp.ctxPath}/resources/favicon/android-icon-192x192.png">
<link rel="icon" type="image/png" sizes="32x32"
	href="${utcp.ctxPath}/resources/favicon/favicon-32x32.png">
<link rel="icon" type="image/png" sizes="96x96"
	href="${utcp.ctxPath}/resources/favicon/favicon-96x96.png">
<link rel="icon" type="image/png" sizes="16x16"
	href="${utcp.ctxPath}/resources/favicon/favicon-16x16.png">
<meta name="msapplication-TileColor" content="#ffffff">
<meta name="msapplication-TileImage"
	content="/resources/favicon/ms-icon-144x144.png">
<meta name="theme-color" content="#ffffff">

<link rel="stylesheet" href="https://unpkg.com/aos@2.3.1/dist/aos.css"> 
<!-- font -->
<link
	href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@300;400;500&display=swap"
	rel="stylesheet">

<!--// fontawesome //-->
<link href="${utcp.ctxPath}/resources/fontawesome/css/all.css"
	rel="stylesheet">

<!-- script.js -->
<script src="${utcp.ctxPath}/resources/user/js/jquery-3.5.1.min.js"></script>
<script type="text/javascript"
	src="${utcp.ctxPath}/resources/user/js/slick.min.js"></script>
<script type="text/javascript"
	src="${utcp.ctxPath}/resources/user/js/common.js"></script>
<script src="https://unpkg.com/aos@2.3.1/dist/aos.js"></script> 

<!--// modal //-->
<link rel="stylesheet"
	href="${utcp.ctxPath}/resources/user/css/remodal.css">
<link rel="stylesheet"
	href="${utcp.ctxPath}/resources/user/css/remodal-default-theme.css">
<script src="${utcp.ctxPath}/resources/admin/js/remodal.js"
	type="text/javascript"></script>

<script type="text/javascript"
	src="${utcp.ctxPath}/resources/plugins/vue/vue.js"></script>
<script type="text/javascript"
	src="${utcp.ctxPath}/resources/plugins/moment/moment.min.js"></script>
<script type="text/javascript"
	src="${utcp.ctxPath}/resources/plugins/moment/moment-with-locales.min.js"></script>
<script type="text/javascript"
	src="${utcp.ctxPath}/resources/plugins/vue/vue-custom.js"></script>

<%
	String active1 = System.getProperty("server.mode");
	if (active1 != null && active1.equals("prod")) {
%>

<%
	}
%>
<script>
	$(document)
			.ready(
					function() {

						//main slide
						$('#mainSlide').slick({
							infinite : true,
							speed : 500,
							fade : true,
							cssEase : 'linear',
							autoplay : true,
							dots : true,
							arrows : true,
							appendDots : $('.Dots'),
							prevArrow : $('.prevArrow'),
							nextArrow : $('.nextArrow'),
							autoplaySpeed : 2500,
							slidesToShow : 1,
							slidesToScroll : 1,
							responsive : [ {
								breakpoint : 1100,
								settings : {
									slidesToShow : 1
								}
							}, {
								breakpoint : 768,
								settings : {
									slidesToShow : 1
								}
							}, {
								breakpoint : 480,
								settings : {
									slidesToShow : 1
								}
							} ]
						});

						$('.play').click(function() {
							$('.visual').slick('slickPlay');
						});

						$('.stop').click(function() {
							$('.visual').slick('slickPause');
						});

						var sw = 0;
						$('.btn_pause').click(function() {
							if (sw == 0) {
								$('.visualArea .btn_pause').addClass('on');
								$('#mainSlide').slick('slickPause');
								sw = 1;
							} else {
								$('.visualArea .btn_pause').removeClass('on');
								$('#mainSlide').slick('slickPlay');
								sw = 0;
							}
						});

						// main slide

						// edu slide
						$('.EduSlideCont').slick({
							infinite : true,
							speed : 500,
							autoplay : false,
							autoplaySpeed : 3000,
							pauseOnHover : true,
							slidesToShow : 2,
							slidesToScroll : 1,
							accessibility : true,
							arrows : false,
							dots : true,
							responsive : [ {
								breakpoint : 768,
								settings : {
									slidesToShow : 1,
								}
							}]
						});
						// edu slide

						// edu slide
						$('#newsSlide2').slick({
							infinite : true,
							speed : 500,
							autoplay : true,
							autoplaySpeed : 3000,
							pauseOnHover : true,
							slidesToShow : 4,
							slidesToScroll : 1,
							accessibility : true,
							responsive : [ {
								breakpoint : 1100,
								settings : {
									slidesToShow : 4,
									dots : false,
									arrows : true
								}
							}, {
								breakpoint : 768,
								settings : {
									slidesToShow : 1,
									dots : false,
									arrows : true
								}
							} ]
						});
						// edu slide
						
						//mainFacSlide
						$('.mainFacSlide').slick({
						    slidesToShow: 1,
						    centerMode: true,
						    centerPadding: '200px',
						    variableWidth: true,    
						    autoplay: true,
						    autoplaySpeed: 2000,
						    arrows: true,
						    prevArrow: $('.fac-slick-prev'), 
						    nextArrow: $('.fac-slick-next'),
						    infinite: true,
						    responsive: [
							      {
							        breakpoint: 1024,
							        settings: {
							        	slidesToShow: 1,
							        	centerPadding: '0px',
							            variableWidth: false
							        }
							      },
							      {
							        breakpoint: 768,
							        settings: {
							        	slidesToShow: 1,
							        	centerPadding: '0px',
							            variableWidth: false
							        }
							      }
							    ]
						  });
						
						//mainEduSlide
						
						$('.mainEduSlide').slick({
						    slidesToShow: 2.5,
						    slidesToScroll: 1,
						    infinite: false,
						    arrows: true,
						    prevArrow: $('.edu-slick-prev'), 
						    nextArrow: $('.edu-slick-next'),
						    dots: false,
						    variableWidth: false, 
						    adaptiveHeight: true,
						    responsive: [
						      {
						        breakpoint: 1024,
						        settings: {
						          slidesToShow: 1.5 // 태블릿에서 1개 반
						        }
						      },
						      {
						        breakpoint: 768,
						        settings: {
						          slidesToShow: 1.2 // 모바일에서 1개 조금 넘게
						        }
						      }
						    ]
						  });
						
						//var totalSlides = $slider.slick('getSlick').slideCount;
						//$('.mainEduSlide').after('<div class="slide-counter"><span class="current">1</span> / <span class="total">'+ totalSlides +'</span></div>');
						//$slider.on('afterChange', function(event, slick, currentSlide){
						    // slick에서 currentSlide는 0부터 시작 → +1 필요
						//    $('.slide-counter .current').text(currentSlide + 1);
						//  });
						
						// board_tab(ul) onoff //
						$('.boxNotice>.bd_tab_con').children().css('display',
								'none');
						$('.boxNotice>.bd_tab_con> div:first-child').css(
								'display', 'block');
						$('.boxNotice>.bd_tab li:first-child').addClass(
								'active');
						$('.boxNotice')
								.delegate(
										'.bd_tab>li',
										'click',
										function() {
											var index = $(this).parent()
													.children().index(this);
											$(this).siblings().removeClass();
											$(this).addClass('active');
											$(this).parent()
													.next('.bd_tab_con')
													.children().hide()
													.eq(index).show();
										});
						// board_tab(ul) onoff //

						$('#userPw').on('keypress', function(e) {
							if (e.keyCode == '13') {
								fn_login();
							}
						});

						// 저장된 쿠키값을 가져와서 ID 칸에 넣어준다. 없으면 공백으로 들어감.
						var key = getCookie("key");
						$("#userId").val(key);

						if ($("#userId").val() != "") { // 그 전에 ID를 저장해서 처음 페이지 로딩 시, 입력 칸에 저장된 ID가 표시된 상태라면,
							$("#idSaveCheck").attr("checked", true); // ID 저장하기를 체크 상태로 두기.
						}

						$("#idSaveCheck").change(function() { // 체크박스에 변화가 있다면,
							if ($("#idSaveCheck").is(":checked")) { // ID 저장하기 체크했을 때,
								setCookie("key", $("#userId").val(), 7); // 7일 동안 쿠키 보관
							} else { // ID 저장하기 체크 해제 시,
								deleteCookie("key");
							}
						});

						// ID 저장하기를 체크한 상태에서 ID를 입력하는 경우, 이럴 때도 쿠키 저장.
						$("#userId").keyup(function() { // ID 입력 칸에 ID를 입력할 때,
							if ($("#idSaveCheck").is(":checked")) { // ID 저장하기를 체크한 상태라면,
								setCookie("key", $("#userId").val(), 7); // 7일 동안 쿠키 보관
							}
						});

						//활성팝업불러오기
						$.ajax({
							type : 'post',
							url : '${utcp.ctxPath}/bbs/comm/popup/list.json',
							success : function(r) {
								var result = r.result;
								for ( var i in result) {
									//openPopup(result[i]);
								}
								vm_popupLayer.openLayer(result);
							}
						});
						
					});

	function setCookie(cookieName, value, exdays) {
		var exdate = new Date();
		exdate.setDate(exdate.getDate() + exdays);
		var cookieValue = escape(value)
				+ ((exdays == null) ? "" : "; expires=" + exdate.toGMTString());
		document.cookie = cookieName + "=" + cookieValue;
	}

	function deleteCookie(cookieName) {
		var expireDate = new Date();
		expireDate.setDate(expireDate.getDate() - 1);
		document.cookie = cookieName + "= " + "; expires="
				+ expireDate.toGMTString();
	}

	function getCookie(cookieName) {
		cookieName = cookieName + '=';
		var cookieData = document.cookie;
		var start = cookieData.indexOf(cookieName);
		var cookieValue = '';
		if (start != -1) {
			start += cookieName.length;
			var end = cookieData.indexOf(';', start);
			if (end == -1)
				end = cookieData.length;
			cookieValue = cookieData.substring(start, end);
		}
		return unescape(cookieValue);
	}

	function fn_login() {

		var userId = $("#userId").val();
		var userPw = $("#userPw").val();

		if (userId == "") {
			$("#messageStr").html("아이디를 입력하세요.");
			location.href = "#message";
			return;
		}

		if (userPw == "") {
			$("#messageStr").html("비밀번호를 입력하세요.");
			location.href = "#message";
			return;
		}

		$.ajax({
			url : "${utcp.ctxPath}/user/login.json",
			type : "post",
			data : {
				"loginId" : userId,
				"loginPw" : userPw
			},
			cache : false,
			async : true,
			success : function(r) {
				if (r.result == 1) {
					location.href = r.data.url;
				} else {
					$("#messageStr").html(r.msg);
					location.href = "#message";
				}
			}
		});
	}

	
	//팝업쿠키 가져오기
	function getPopupCookie(idx) {
		var name = 'popup_' + idx;
		var value = document.cookie.match('(^|;) ?' + name + '=([^;]*)(;|$)');
		return value ? value[2] : null;
	};
	//팝업열기
	function openPopup(popupObj) {

		var cookieCheck = getPopupCookie(popupObj.idx);
		if (cookieCheck != "N")
			window.open('${utcp.ctxPath}/bbs/comm/popup/open.do?idx='
					+ popupObj.idx, 'popup_' + popupObj.idx,
					'status=0,location=0,left=' + popupObj.posX + ',top='
							+ popupObj.posY + ',height='
							+ (popupObj.height + 60) + ',width='
							+ (popupObj.width + 2));

	}
</script>
<script type="text/javascript">
	$(document).ready(function() {
		var cookieCheck2 = getPopupCookie2('popup2');
		if (cookieCheck2 != "N") {
			//$('#ieModal').show();
		}

	});
	function close_pop(flag) {
		$('#ieModal').hide();
		closePopup2();
	};
	function setCookie2(name, value, expiredays) {
		var date = new Date();
		date.setDate(date.getDate() + expiredays);
		document.cookie = escape(name) + "=" + escape(value)
				+ ";path=/; expires=" + date.toUTCString() + ";";
	}
	function closePopup2() {
		if (document.getElementById("temp-check").checked) {
			console.log('in');
			setCookie("popup2", "N", 1);
		}
	}
	function getPopupCookie2(idx) {
		var name = 'popup2';
		var value = document.cookie.match('(^|;) ?' + name + '=([^;]*)(;|$)');
		return value ? value[2] : null;
	};
</script>
</head>
<body>
	<!--// IEpopup //-->
	<div id="ieModal" class="modal">
		<div class="modal-content">
			<div style="visibility: visible; width: 500px; margin: 0 auto;">
				<table cellpadding="0" cellspacing="0"
					style="width: 100%; margin: 0 auto;">
					<tbody>
						<tr>
							<td class="pd0">
								<p>
									<img src="${utcp.ctxPath}/resources/user/image/img/iepopup.jpg"
										alt="익스플로러 브라우저 사용 금지. 교육시스템 이용 시 크롬,엣지 사용권장" />
								</p>
							</td>
						</tr>
						<tr>
							<form name="notice_form"></form>
							<td class="bg_black">
								<p class="fl fc_white">
									<input type="checkbox" id="temp-check" class="fc_white">
									오늘 하루 이 창을 열지 않음
								</p>
								<p class="fr">
									<a href="javascript:;" onClick="close_pop();" class="fc_white">닫기</a>
								</p>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<!--// IEpopup //-->

	<div id="wrap" class="main">
		<jsp:include page="/WEB-INF/jsp/layout/user/header.jsp" >
			<jsp:param value="true" name="isMain"/>
		</jsp:include>

		<div id="container">
			<!--// mainVisual //-->
			<div class="mainVisual cf">
				<!--// visualArea //-->
				<div class="visualArea">
					<div class="visualWrap">
						<div id="mainSlide">
							<c:forEach items="${bannerList }" var="o">
								<div class="slide" style="background-image:url('${utcp.getCdnUrl('upload') }/web/banner/${o.fileImgRenm}');">
										<div class="slideImg">
											<img src="${utcp.getCdnUrl('upload') }/web/banner/${o.fileImgRenm}" alt="" />
										</div>
								</div>
							</c:forEach>
						<!-- 
							<div class="slide" style="background-image:url('${utcp.ctxPath}/resources/user/image/visual01.webp');">
								<div class="slideImg">
									<img src="${utcp.ctxPath}/resources/user/image/visual01.webp" alt="" />
								</div>
							</div>
							<div class="slide" style="background-image:url('${utcp.ctxPath}/resources/user/image/visual02.webp');">
								<div class="slideImg">
									<img src="${utcp.ctxPath}/resources/user/image/visual02.webp" alt="" />
								</div>
							</div>
							<div class="slide" style="background-image:url('${utcp.ctxPath}/resources/user/image/visual03.webp');">
								<div class="slideImg">
									<img src="${utcp.ctxPath}/resources/user/image/visual03.webp" alt="" />
								</div>
							</div>
						 -->
						</div>
						<div class="SlideTxt">
							<div class="inner">
								<h2 data-aos="fade-right">
									국민을 위한 정책 실현을 선도하는 <br /> <strong>신뢰받는 경제‧재정 인재 양성</strong>
								</h2>
								<div class="mainIndicator slideIndicator">
									<span class="prevArrow"><img
										src="${utcp.ctxPath}/resources/user/image/icon/slide_prev.png"
										alt="" /></span>
									<div class="Dots"></div>
									<div class="btn_pause">
										<!-- <button class="slickPlay">시작</button> -->
										<button class="slickPause">
											<img
												src="${utcp.ctxPath}/resources/user/image/icon/slide_pause.png"
												alt="" />
										</button>
									</div>
	
									<span class="nextArrow"><img
										src="${utcp.ctxPath}/resources/user/image/icon/slide_next.png"
										alt="" /></span>
								</div>
							</div>
						</div>
					</div>
					

					<div class="VisualEduWrap">
						<h2 class="EduTit">교육신청</h2>
						<div class="EduListWrap">
							<ul class="EduListCont">
							   
								<c:forEach var="o" items="${lctreList1}" varStatus="stat">
								<c:if test="${stat.index < 3 }">
								<li class="EduList">
									<div class="EduHash">
										<span class="hash01 hash">${o.lctreTypeNm }</span>
										 <span class="hash02">${o.addrMemo }</span>
									</div>
									<h3 class="EduListTit">${o.eduNm }</h3>
									<div class="EduDate">
										<div class="EduDateWrap">
											<div class="EduDate01 EduDateList">
												<h4>신청 기간</h4>
												<p>${utcp.convDateToStr(utcp.convStrToDate(o.rceptPeriodBegin,'yyyyMMddHHmm'),'yyyy.MM.dd') } ~
												${utcp.convDateToStr(utcp.convStrToDate(o.rceptPeriodEnd,'yyyyMMddHHmm'),'yyyy.MM.dd') }.</p>
											</div>
											<div class="EduDate02 EduDateList">
												<h4>교육 기간</h4>
												<p>${utcp.convDateToStr(utcp.convStrToDate(o.eduPeriodBegin,'yyyy-MM-dd'),'yyyy.MM.dd') } ~
												${utcp.convDateToStr(utcp.convStrToDate(o.eduPeriodEnd,'yyyy-MM-dd'),'yyyy.MM.dd') }.</p>
											</div>
										</div>
										<div class="EduSubBtn">
											<a href="javascript:return false;" onclick="goPage('교육신청','${utcp.ctxPath }/user/edu/eduView.do?eduSeq=${o.eduSeq}');"><span>신청하기</span> <img
												src="${utcp.ctxPath}/resources/user/image/icon/icon_arrow.png"
												alt="" /></a>
										</div>
									</div>
								</li>
								</c:if>
								</c:forEach>
								<c:if test="${empty lctreList1 }">
								<li>
									<h3>교옥일정이 없습니다.</h3>
								</li>
								</c:if>
							</ul>
							<div class="MoreViewBtn">
								
								<a href="javascript:return false;" onclick="goPage('교육신청','${utcp.ctxPath }/user/edu/eduList.do');"
								>더 보기 <img
									src="${utcp.ctxPath}/resources/user/image/icon/more.png" alt="" / class="btn_off"><img
									src="${utcp.ctxPath}/resources/user/image/icon/more_wt.png" alt="" / class="btn_on"></a>
							</div>
						</div>
					</div>
				
					<div class="EduListWrap mo">
						<div class="VisualEduSlide">
							<h2 class="EduTit">교육신청</h2>
							<div class="EduListCont EduSlideCont">
								<c:forEach var="o" items="${lctreList1}" varStatus="stat">
								<div class="EduList">
									<div class="EduHash">
										<span class="hash01 hash">집합교육</span> <span
											class="hash02">${o.addrMemo }</span>
									</div>
									<h3 class="EduListTit">${o.eduNm }</h3>
									<div class="EduDate">
										<div class="EduDateWrap">
											<div class="EduDate01 EduDateList">
												<h4>신청 기간</h4>
												<p>${utcp.convDateToStr(utcp.convStrToDate(o.rceptPeriodBegin,'yyyyMMddHHmm'),'yyyy.MM.dd') } ~
												${utcp.convDateToStr(utcp.convStrToDate(o.rceptPeriodEnd,'yyyyMMddHHmm'),'yyyy.MM.dd') }.</p>
											</div>
											<div class="EduDate02 EduDateList">
												<h4>교육 기간</h4>
												<p>${utcp.convDateToStr(utcp.convStrToDate(o.eduPeriodBegin,'yyyy-MM-dd'),'yyyy.MM.dd') } ~
												채${utcp.convDateToStr(utcp.convStrToDate(o.eduPeriodEnd,'yyyy-MM-dd'),'yyyy.MM.dd') }.</p>
											</div>
										</div>
										<div class="EduSubBtn">											
											<a href="javascript:return false;" onclick="goPage('교육신청','${utcp.ctxPath }/user/edu/eduView.do?eduSeq=${o.eduSeq}');">
											<span>신청하기</span> <img
												src="${utcp.ctxPath}/resources/user/image/icon/icon_arrow.png"
												alt="" /></a>
										</div>
									</div>
								</div>
								</c:forEach>
								<c:if test="${empty lctreList1 }">
								<div class="EduList">
									교옥일정이 없습니다.
								</div>
								</c:if>
							</div>
						</div>
					</div>
				</div>
				<!--// visualArea //-->
				<!--// mainVisual //-->
				<!--// quickMenu //-->
				<div class="quickMenuWrap">
					<ul class="quickMenu">
						<li class="menuList"><a
							href="${utcp.ctxPath}/user/introduce/contents/03_04.do"> <span><img
									src="${utcp.ctxPath}/resources/user/image/icon/quick01.png"
									alt="" /></span>
								<h3>오시는 길</h3>
						</a></li>
						<li class="menuList">
							<a href="javascript:return false;" onclick="goPage('교육신청','${utcp.ctxPath }/user/edu/eduList.do');">
							 <span><img
									src="${utcp.ctxPath}/resources/user/image/icon/quick02.png"
									alt="" /></span>
								<h3>교육 신청</h3>
						</a></li>
						<li class="menuList">							
							<a href="javascript:return false;" onclick="goPage('','${utcp.ctxPath }/user/introduce/contents/03_05.do');">
							<span><img
									src="${utcp.ctxPath}/resources/user/image/icon/quick03.png"
									alt="" /></span>
								<h3>시설 안내</h3>
						</a></li>
						<li class="menuList">
						<a href="javascript:return false;" onclick="goPage('나의 강의실','${utcp.ctxPath }/user/mypage/myEduStdntList.do');">
						 <span><img
									src="${utcp.ctxPath}/resources/user/image/icon/quick04.png"
									alt="" /></span>
								<h3>수료증 확인</h3>
						</a></li>
						<li class="menuList">
							<a href="javascript:return false;" onclick="goPage('학습지원','${utcp.ctxPath }/user/cs/inqryList.do');">
							<span><img
									src="${utcp.ctxPath}/resources/user/image/icon/quick05.png"
									alt="" /></span>
								<h3>교육 문의</h3>
						</a></li>
<!-- 						<li class="menuList"><a -->
<%-- 							href="${utcp.ctxPath }/user/introduce/contents/03_01.do"> <span><img --%>
<%-- 									src="${utcp.ctxPath}/resources/user/image/icon/quick06.png" --%>
<!-- 									alt="" /></span> -->
<!-- 								<h3>인사말</h3> -->
<!-- 						</a></li> -->
					</ul>
				</div>
				<!--// quickMenu //-->

				<!-- // ABOUT // -->

				<div class="mainAbout section">
					<div class="txt" data-aos="fade-right">
						<div class="inner">
							<h2>
								체계적인 경제 재정 교육 <span> <br /> <strong>태안정책연수원</strong>이 함께
									합니다.
								</span>
							</h2>
							<p>인재를 키웁니다. 지식을 나눕니다. <br /> 저희 나라키움 태안정책연수원을 찾아 주신 교육생 여러분 반갑습니다.
							</p>
						</div>
					</div>
					<div class="img" data-aos="fade-left">
						<img src="${utcp.ctxPath}/resources/user/image/img/main_about.jpg"
							alt="" />
					</div>
				</div>


				<!-- // 교육원 소식 // -->

				<div class="mainNotice section">
					<div class="inner">
						<h2 class="mainTit">교육원 소식</h2>
						<div class="mainNoticeWrap">
							<div class="mainNotice01 mainNoticeBoard" data-aos="fade-down">
								<div class="noticeTit">
									<h3>공지사항</h3>
									<div class="mainMoreBtn">
										
											<a href="javascript:return false;" onclick="goPage('학습지원','${utcp.ctxPath}/user/bbs/noticeList.do');" class="btn_more">
											더 보기 <img
											src="${utcp.ctxPath}/resources/user/image/icon/more.png"
											alt="" /></a>
									</div>
								</div>
								<div class="noticeBoard">
									<ul>
										<c:forEach items="${noticeList }" var="map">
											<li>
												<a href="javascript:return false;" onclick="goPage('학습지원','${utcp.ctxPath}/user/bbs/noticeView.do?idx=${map.idx}');" class="btn_more">
													<span class="boardTit">${map.title }</span> <span
													class="boardDate">${map.replaceRegDtime('yy-MM-dd') }</span>
											</a></li>
										</c:forEach>
									</ul>
								</div>
							</div>
							<div class="mainNotice02 mainNoticeBoard" data-aos="fade-down" data-aos-delay="100">
								<div class="noticeTit">
									<h3>자료실</h3>
									<div class="mainMoreBtn">
											<a href="javascript:return false;" onclick="goPage('학습지원','${utcp.ctxPath}/user/bbs/recsList.do');" class="btn_more">
											더 보기 <img
											src="${utcp.ctxPath}/resources/user/image/icon/more.png"
											alt="" /></a>
									</div>
								</div>
								<div class="noticeBoard">
									<ul>
										<c:forEach items="${recsList }" var="map">
											<li>
												<a href="javascript:return false;" onclick="goPage('학습지원','${utcp.ctxPath}/user/bbs/recsView.do?idx=${map.idx }');" class="btn_more">
													<span class="boardTit">${map.title }</span> <span
													class="boardDate">${map.replaceRegDtime('yy-MM-dd') }</span>
											</a></li>
										</c:forEach>
									</ul>
								</div>
							</div>
						</div>

					</div>
				</div>

			</div>

			<!-- 시설 소개  -->
			<div class="mainFac section">
				<h2 class="mainTit">태안정책연수원 시설 소개</h2>
				<div class="mainFacSlide" data-aos="fade-down">
					<div class="facList">
						<div class="facImg">
						  <a href="${utcp.ctxPath}/user/introduce/contents/03_05.do" class="facLink">
						  	<img src="${utcp.ctxPath}/resources/user/image/img/main_fac01.webp" alt="Slide 1">
						  </a>
						</div>
						<div class="faxTxtWrap">
						  <h3 class="factxt"><a href="${utcp.ctxPath}/user/introduce/contents/03_05.do" class="facLink"><span>내부시설</span></a></h3>
						</div>
					</div>
					<div class="facList">
						<div class="facImg">
						  <a href="${utcp.ctxPath}/user/introduce/contents/03_05.do" class="facLink">
						  	<img src="${utcp.ctxPath}/resources/user/image/img/main_fac02.webp" alt="Slide 1">
						  </a>
						</div>
						<div class="faxTxtWrap">
						  <h3 class="factxt"><a href="${utcp.ctxPath}/user/introduce/contents/03_05.do" class="facLink"><span>편의시설</span></a></h3>
						</div>
					</div>
					<div class="facList">
						<div class="facImg">
						  <a href="${utcp.ctxPath}/user/introduce/contents/03_05.do" class="facLink">
						  	<img src="${utcp.ctxPath}/resources/user/image/img/main_fac03.webp" alt="Slide 1">
						  </a>
						</div>
						<div class="faxTxtWrap">
						  <h3 class="factxt"><a href="${utcp.ctxPath}/user/introduce/contents/03_05.do" class="facLink"><span>교육시설</span></a></h3>
						</div>
					</div>
					<div class="facList">
						<div class="facImg">
						  <a href="${utcp.ctxPath}/user/introduce/contents/03_05.do" class="facLink">
						  	<img src="${utcp.ctxPath}/resources/user/image/img/main_fac04.webp" alt="Slide 1">
						  </a>
						</div>
						<div class="faxTxtWrap">
						  <h3 class="factxt"><a href="${utcp.ctxPath}/user/introduce/contents/03_05.do" class="facLink"><span>외부시설</span></a></h3>
						</div>
					</div>
				</div>
				<div class="facSlideArrow">
					<button type="button" class="fac-slick-prev"><img src="${utcp.ctxPath}/resources/user/image/facarrowprev.png" alt="이전슬라이드"></button>
					<button type="button" class="fac-slick-next"><img src="${utcp.ctxPath}/resources/user/image/facarrownext.png" alt="다음슬라이드"></button>
				</div>
				<div class="mainMoreBtn">
					<a href="${utcp.ctxPath}/user/introduce/contents/03_05.do" class="btn_more">더
						보기 <img
						src="${utcp.ctxPath}/resources/user/image/icon/more_wt.png" alt="" />
					</a>
				</div>
			</div>
		
			<!--// grid1280 //-->




			<!--// bd_tab_con //-->
		</div>
		<!--// boxNotice //-->
	</div>
	<!--// boardArea //-->
	</div>
	<!--// grid1280 //-->
	</div>
	<!--// content -->
	</div>
	<!--// #container -->

	<jsp:include page="/WEB-INF/jsp/layout/user/footer.jsp" flush="true" />
	<!--// #footer -->

	<div class="remodal messagePop messagePop2" data-remodal-id="message"
		role="dialog" aria-labelledby="modal1Title"
		aria-describedby="modal1Desc">
		<div class="modal-content">
			<div class="modal-header">
				<p class="tit alignC">알림</p>
			</div>
			<div class="modal-body">
				<p class="messageTxt" id="messageStr"></p>
			</div>
			<div class="modal-footer">
				<div class="tc">
					<button data-remodal-action="cancel"
						class="remodal-confirm btn02 btn_orange">확인</button>
				</div>
			</div>
		</div>
	</div>
	<!--// popup_message //-->

	<script>
	$(document).ready(function () {
        AOS.init({
          duration: 1000,  // 애니메이션 속도
          once: false,      // 스크롤 다시 올라가도 한 번만 실행
          offset: 120      // 실행 위치
        });
      });
	</script>
	<jsp:include page="/WEB-INF/jsp/layout/user/modal.jsp" flush="false" />
	<jsp:include page="/WEB-INF/jsp/layout/user/firebase.jsp" flush="false" />
</body>
</html>