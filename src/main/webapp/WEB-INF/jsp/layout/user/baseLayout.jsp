<%@page import="com.educare.edu.member.service.model.UserInfo"%>
<%@page import="com.educare.edu.member.service.SessionUserInfoHelper"%>
<%@page import="java.util.List"%>
<%@page import="com.educare.edu.menu.service.model.Menu"%>
<%@page import="com.educare.edu.menu.service.MenuUtil"%>
<%@ page import="javax.servlet.http.HttpServletRequest"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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

<link rel="stylesheet" type="text/css"
	href="${utcp.ctxPath}/resources/user/css/main.css?2">
<link rel="stylesheet" type="text/css"
	href="${utcp.ctxPath}/resources/user/css/sub.css?1">
<link rel="stylesheet" type="text/css"
	href="${utcp.ctxPath}/resources/user/css/slick.css?1" />
<link rel="stylesheet" type="text/css"
	href="${utcp.ctxPath}/resources/user/css/common.css?1">

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

<!--// font //-->
<link
	href="//fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@300;400;500&display=swap"
	rel="stylesheet">
<link
	href="//fonts.googleapis.com/css2?family=Playball&amp;display=swap"
	rel="stylesheet">
<link
	href="//fonts.googleapis.com/css2?family=Nanum+Myeongjo:wght@400;700;800&amp;display=swap"
	rel="stylesheet">

<!--// fontawesome -->
<link href="${utcp.ctxPath}/resources/fontawesome/css/all.css"
	rel="stylesheet">

<!-- script.js -->
<script src="${utcp.ctxPath}/resources/user/js/jquery-3.5.1.min.js"></script>
<script type="text/javascript"
	src="${utcp.ctxPath}/resources/user/js/slick.min.js"></script>
<script type="text/javascript"
	src="${utcp.ctxPath}/resources/user/js/common.js"></script>
<script type="text/javascript"
	src="${utcp.ctxPath}/resources/common/js/util.js?1"></script>

<!--// modal //-->
<link rel="stylesheet"
	href="${utcp.ctxPath}/resources/user/css/remodal.css">
<link rel="stylesheet"
	href="${utcp.ctxPath}/resources/user/css/remodal-default-theme.css">
<script src="${utcp.ctxPath}/resources/admin/js/remodal.js"
	type="text/javascript"></script>

<!-- vue 
-->
<script type="text/javascript"
	src="${utcp.ctxPath}/resources/plugins/vue/vue.js"></script>
<script type="text/javascript"
	src="${utcp.ctxPath}/resources/plugins/moment/moment.min.js"></script>
<script type="text/javascript"
	src="${utcp.ctxPath}/resources/plugins/moment/moment-with-locales.min.js"></script>
<script type="text/javascript"
	src="${utcp.ctxPath}/resources/plugins/vue/vue-custom.js"></script>
<%-- 
<script type="text/javascript" src="${utcp.ctxPath}/resources/plugins/axios/axios.min.js"></script>
 --%>
<script>
	moment.locale('ko');
	//lang.hl='ko';
</script>

<script>
	$(document).on("contextmenu", function(e) {
		console.log("c" + e);
		//return false;
	});
</script>

<link rel="stylesheet" type="text/css"
	href="${utcp.ctxPath}/resources/plugins/jquery/jquery.loading-indicator.css" />
<script type="text/javascript"
	src="${utcp.ctxPath}/resources/plugins/jquery/jquery.loading-indicator.min.js"></script>
<script>
	var homeLoader;
	$(document).ready(function() {
		homeLoader = $('body').loadingIndicator({
			useImage : false
		}).data("loadingIndicator");
		homeLoader.hide();
		$('.loading-indicator-wrapper').css('position', 'fixed');
	});
	$(document).ajaxSend(function(evt, request, settings) {
		if (typeof homeLoader !== 'undefined') {
			homeLoader.show();
		}

	});
	$(document).ajaxStop(function() {
		if (typeof homeLoader !== 'undefined') {
			homeLoader.hide();
		}
	});
</script>

<!-- jquery ui -->
<link rel="stylesheet"
	href="${utcp.ctxPath }/resources/plugins/jquery/jquery-ui.css">
<script src="${utcp.ctxPath }/resources/plugins/jquery/jquery-ui.js"></script>
<script>
	// Datepicker 한글화 설정
	$.datepicker.setDefaults({
		closeText : '닫기',
		prevText : '이전',
		nextText : '다음',
		currentText : '오늘',
		monthNames : [ '1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월',
				'10월', '11월', '12월' ],
		monthNamesShort : [ '1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월',
				'9월', '10월', '11월', '12월' ],
		dayNames : [ '일요일', '월요일', '화요일', '수요일', '목요일', '금요일', '토요일' ],
		dayNamesShort : [ '일', '월', '화', '수', '목', '금', '토' ],
		dayNamesMin : [ '일', '월', '화', '수', '목', '금', '토' ],
		weekHeader : '주',
		dateFormat : 'yy-mm-dd',
		firstDay : 0,
		isRTL : false,
		showMonthAfterYear : true,
		yearSuffix : '년'
	});
</script>
<script>
/*header*/
$(document).ready(function () {
    // 무조건 처음에 클래스 추가
    $('header').addClass('on');
});

$(window).scroll(function () {
    // 아래 조건이 true일 때만 추가. remove는 절대 없음.
    if ($(window).scrollTop() >= 50) {
        $('header').addClass('on'); // 이미 있어도 중복 없음
    }
    // 절대 removeClass 쓰지 마세요!
});
</script>
</head>
<body>
	<div id="wrap">
		<tiles:insertAttribute name="header" />
		<div id="container">
			<tiles:insertAttribute name="subBox" />
			<div id="content">
				<%
					pageContext.setAttribute("menuList", MenuUtil.getSubMenuList());

					String baseLayout_uri = MenuUtil.getReqUriAll(request);
					pageContext.setAttribute("baseLayout_uri", baseLayout_uri);
				%>
				<c:if
					test="${fn:indexOf(baseLayout_uri,'/mypage/')>=0  && fn:indexOf(baseLayout_uri,'/updUser.do') < 0 }">
					<c:import url="/comm/jspInc/mypageInfo.do" />
				</c:if>

				<div class="navBot">
					<c:choose>
						<c:when test="${not empty menuList}">
							<ul>
								<c:forEach items="${menuList }" var="o">
									<c:if test="${o.st == 1 }">
										<c:choose>
											<c:when
												test="${fn:indexOf('updUser,updPw,familyMember',o.cd)>=0 }">
												<li class="upd fr"><a href="${utcp.ctxPath}${o.url }"
													class="${not empty o.on?'on':'' }"><i
														class="fas ${o.cd eq 'updUser'?'fa-user-edit':'fa-unlock' }"></i>${o.nm }</a>
												</li>
											</c:when>
											<c:otherwise>
												<li class="${fn:indexOf(o.url,'/mypage/')>=0?'fl':'' }">
													<a href="${utcp.ctxPath}${o.url }"
													class="${not empty o.on?'on':'' }">${o.nm }</a>
												</li>
											</c:otherwise>
										</c:choose>
									</c:if>
								</c:forEach>
							</ul>
						</c:when>
						<c:otherwise>
							<!-- <ul>
					<li><a href="#none" >메뉴</a></li>
				</ul> -->
						</c:otherwise>
					</c:choose>
				</div>



				<tiles:insertAttribute name="body" />
			</div>
			<!--// content -->
		</div>
		<!--// #container -->
		<tiles:insertAttribute name="footer" />
	</div>
	<!--// #wrap -->

	<tiles:insertAttribute name="modal" />
	<jsp:include page="/WEB-INF/jsp/layout/comm_modal.jsp" flush="false" />
	<%-- 
	<jsp:include page="/WEB-INF/jsp/layout/user/firebase.jsp" flush="false" /> --%>
</body>
</html>