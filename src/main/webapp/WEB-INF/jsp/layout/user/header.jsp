<%@page import="com.educare.util.XmlBean"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.educare.edu.menu.service.model.Menu"%>
<%@ page import="com.educare.edu.menu.service.MenuUtil"%>
<%@ page import="com.educare.edu.member.service.SessionUserInfoHelper"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>

<script>
	function fn_confirm() {
		location.href = "#confirmPop";
	}

	function fn_logout() {
		location.href = "${utcp.ctxPath}/user/logout.do";
	}

	function myBell() {
		document.getElementById("myBellDropdown").classList.toggle("show");
	}

	window.onclick = function(event) {
		if (!event.target.matches('.dropbtn span')) {
			var dropdowns = document.getElementsByClassName("dropdown-content");
			var i;
			for (i = 0; i < dropdowns.length; i++) {
				var openDropdown = dropdowns[i];
				if (openDropdown.classList.contains('show')) {
					openDropdown.classList.remove('show');
				}
			}
		}
	}

	/* header 클래스 추가 */
	document.addEventListener("DOMContentLoaded", function () {
	    const header = document.querySelector("header");

	    function updateHeaderClass() {
	        if (window.innerWidth < 768) {
	            // 모바일 → 항상 on
	            header.classList.add("on");
	        } else {
	            <% if ("true".equals(String.valueOf(request.getParameter("isMain")))) { %>
	            // PC → 스크롤 여부로 제어
	            if (window.scrollY > 0) {
	                header.classList.add("on");
	            } else {
	                header.classList.remove("on");
	            }
	            <% } %>
	        }
	    }

	    // 최초 실행
	    updateHeaderClass();

	    // 스크롤 시 실행 (PC에서만 의미 있음)
	    window.addEventListener("scroll", updateHeaderClass);

	    // 화면 크기 바뀔 때도 실행
	    window.addEventListener("resize", updateHeaderClass);
	});
	var userMemLvl = '${sessionScope.sessionUserInfo.userMemLvl}';
	function goPage(nm,url){
		if((nm =='나의 강의실' || nm =='교육신청') && userMemLvl != '9'){
			alert("공무원인증을 하지 않은 사용자입니다. 인증 후 수강신청 부탁드립니다.");
			location.href = '${utcp.ctxPath}'+"/user/mypage/updUser.do";
			return false;
		}
		
		if(nm =='학습지원' && !userMemLvl){
			alert("로그인 후 접근가능메뉴입니다.");
			return false;
		}
		location.href = '${utcp.ctxPath}'+url;
	}

</script>
<c:if test="${!param.isMain}">
<style>
#wrap {
	padding-top: 90px;
}
@media(max-width:1280px){
#wrap {padding-top: 54px;}
}

</style>
</c:if>
<header class="${!param.isMain ? 'on':''  }">

	<div id="header">
		<!--// search Form //-->
		<div id="hdSchForm" class="overlay">
			<a href="javascript:;" class="closebtn" onclick="closeSch()"
				title="닫기">&times;</a>
			<div class="overlay-content">
				<div class="header-search">
					<form id="searchForm"
						action="${utcp.ctxPath}/comm/totalSearch/searchResult.do">
						<div class="input-group">
							<label for="srchText2" class="sound_only">검색어 입력</label> <input
								type="text" class="form-control" name="totalSrchText"
								id="srchText2" placeholder="검색어를 입력하세요." maxlength="100"
								value="${param.totalSrchText }"> <span
								class="input-group-btn">
								<button class="btn btn-default" type="submit">
									<i class="fa fa-search"></i>
								</button>

							</span>
						</div>
					</form>
					<script>
						$('#searchForm').submit(function(event) {
							event.preventDefault(); // 서브밋 막기
							if ($('input[name=totalSrchText]').val() == '') {
								alert('검색어를 입력하세요');
								return;
							}
							this.submit();
						});
					</script>
				</div>
			</div>
		</div>
		<!--// search Form //-->


		<div class="hdWrap">
			<h1>
				<a href="${utcp.ctxPath}/"><img
					src="${utcp.ctxPath}/resources/user/image/logo.png" class="off"
					alt="<spring:eval expression="@prop['service.name']"/>" /><img
					src="${utcp.ctxPath}/resources/user/image/logo_on.png" class="on"
					alt="<spring:eval expression="@prop['service.name']"/>" /></a>
			</h1>

			<div class="header_rg">
				<ul class="utilArea">
					<c:choose>
						<c:when test="${empty sessionScope.sessionUserInfo }">
							<li><a href="${utcp.ctxPath}/user/login.do">로그인</a></li>
							<li><a href="${utcp.ctxPath}/user/join.do"" >회원가입</a></li>
						</c:when>
						<c:otherwise>
							<li><a href="javascript:;" style="cursor: default;"><img src="${utcp.ctxPath}/resources/user/image/icon/person.webp" alt="" /><strong><%=SessionUserInfoHelper.getUserNm()%></strong>님</a>
							</li>
							<li><a href="javascript:;"
								onclick="fn_confirm(); return false;">로그아웃</a></li>
							<c:choose>
								<c:when
									test="${sessionScope.sessionUserInfo.userMemLvl eq '8' }">
									<li class=""><a
										href="${utcp.ctxPath}/user/mypage/instrctrEduOpenList.do">마이페이지</a>
									</li>
								</c:when>
								<c:otherwise>
									<li class="dropdownWrap po_re dp_ib">
										<button onclick="myBell()" class="dropbtn">
											<span><i class="fa-solid fa-circle-user"></i> 나의강의실</span>
											<!--<span class="alarm"> </span>-->
										</button>
										<div id="myBellDropdown" class="dropdown-content">
											<div class="feedHeaderTab">
												<div id="feed" class="tabItem">
													<h2>
														<span>나의강의실</span>
													</h2>
												</div>
											</div>
											<div id="FeedLayerList">
												<div id="FeedListAreaWrap">
													<div id="FeedListArea">
														<ul class="feed_list">
															<li><a
																href="${utcp.ctxPath}/user/mypage/myEduOpenList.do"
																class="link"> <span class="txt"> 진행중 교육 </span>
															</a></li>
															<li><a
																href="${utcp.ctxPath}/user/mypage/myEduRceptList.do"
																class="link"> <span class="txt"> 신청중 교육 </span>
															</a></li>
															<li><a
																href="${utcp.ctxPath}/user/mypage/myEduStdntList.do"
																class="link"> <span class="txt"> 나의교육내역 </span>
															</a></li>
															<li><a
																href="${utcp.ctxPath}/user/mypage/myLclikeList.do"
																class="link"> <span class="txt"> 찜한교육 </span>
															</a></li>
															<li><a
																href="${utcp.ctxPath}/user/mypage/myLcRceptList.do"
																class="link"> <span class="txt"> 신청내역 </span>
															</a></li>
															<li><a href="${utcp.ctxPath}/user/mypage/updUser.do"
																class="link"> <span class="txt"> 내정보수정 </span>
															</a></li>
														</ul>
													</div>
												</div>
											</div>
										</div>
									</li>

								</c:otherwise>
							</c:choose>

						</c:otherwise>
					</c:choose>
				</ul>
				<div class="gnbArea">
					<ul class="gnb flex">
						<li><a href="#sitemap">
								<button type="button" class="">
									<span></span> <span></span> <span></span>
								</button>
						</a></li>

					</ul>
				</div>
			</div>
			<button type="button" class="btn_ham">
				<div class="ham-wrap">
					<span class="ham-bar ham-bar01"></span>
					<span class="ham-bar ham-bar02"></span>
					<span class="ham-bar ham-bar03"></span>
				</div>
			</button>
			<span class="bg_modal"></span>
			<div class="gnbArea_m">
				<%-- <button type="button" class="btn_close">
					<img src="${utcp.ctxPath}/resources/user/image/btn/btn_close.jpg"
						alt="닫기 버튼" />
				</button> --%>
				<div class="joinWrap">
					<%
						if (SessionUserInfoHelper.isLogined()) {
					%>
					<a href="javascript:;" class="fw_700 nametag" style="cursor: default;"><%=SessionUserInfoHelper.getUserNm()%>님</a>
					<a href="javascript:;" onclick="fn_confirm(); return false;">로그아웃</a>
<%-- 
					<c:choose>
						<c:when test="${sessionScope.sessionUserInfo.userMemLvl eq '8' }">
							<a href="${utcp.ctxPath}/user/mypage/instrctrEduOpenList.do">마이페이지</a>
						</c:when>
						<c:otherwise>
							<a href="${utcp.ctxPath}/user/mypage/myLcRceptList.do">마이페이지</a>
						</c:otherwise>
					</c:choose> --%>
					<%
						} else {
					%>



					<a href="${utcp.ctxPath}/user/login.do" class="btn_login">로그인</a> <a
						href="${utcp.ctxPath}/user/join.do" class="btn_join">회원가입</a>
					<%
						}
					%>

					<%-- 
						 --%>


				</div>
				<ul class="gnb">
					<%
						pageContext.setAttribute("baseMenuList", MenuUtil.getBaseMenuList());
					%>
					<c:forEach begin="0" end="4" var="i">
						<c:if test="${baseMenuList[i].st == 1 }">
							<li><c:choose>
									<c:when test="${not empty baseMenuList[i].sub }">
										<a href="#none"><strong>${baseMenuList[i].nm }</strong></a>
										<ul class="lnb">
											<c:forEach items="${baseMenuList[i].sub }" var="o">
												<c:if test="${o.st == 1 }">
													<li><a href="javascript:return false;" onclick="goPage('${baseMenuList[i].nm }','${o.url}');">${o.nm }</a></li>
												</c:if>
											</c:forEach>
										</ul>
									</c:when>
									<c:otherwise>
										<a href="javascript:return false;" onclick="goPage('${baseMenuList[i].nm }','${o.url}');">${baseMenuList[i].nm }</a>
									</c:otherwise>
								</c:choose></li>
						</c:if>
					</c:forEach>
					<li><a href="#sitemap">사이트맵</a></li>

				</ul>
				<!-- 
				<div class="siteWrap">
					<a href="https://www.sciencecenter.go.kr" class="btn_csia" target="_blank" title="새창열림">국립과천과학관</a> 
					<a href="${utcp.ctxPath }/" class="btn_ecsia">교육관 학습관리 시스템</a>
				</div>
				 -->
			</div>
		</div>
		<!--// hdWrap //-->


		<!--// pc 하위 메뉴 //-->
		<div class="main_gnbpc">
			<ul class="maingnb_menu" id="mainGnbDesk">
				<c:forEach begin="0" end="4" var="i">
					<c:if test="${baseMenuList[i].st == 1 }">
						<li class="gnbm${i+1 }"><c:choose>
								<c:when test="${not empty baseMenuList[i].sub }">									
									<a href="javascript:return false;" onclick="goPage('${baseMenuList[i].nm }','${baseMenuList[i].url}');"><strong>${baseMenuList[i].nm }</strong></a>
									<ul class="gnbsubmenu">
										<c:forEach items="${baseMenuList[i].sub }" var="o">
											<c:if test="${o.st == 1 }">
												<li><a href="javascript:return false;" onclick="goPage('${baseMenuList[i].nm }','${o.url}');"><span>${o.nm }</span></a>
												</li>
											</c:if>
										</c:forEach>
									</ul>
								</c:when>
								<c:otherwise>
									<a href="${baseMenuList[i].url }"><strong>${baseMenuList[i].nm }6</strong></a>
									<ul class="gnbsubmenu">
										<li><a href="javascript:return false;" onclick="goPage('${baseMenuList[i].nm }','${baseMenuList[i].url}');"><span>${baseMenuList[i].nm }</span></a>
										</li>
									</ul>
								</c:otherwise>
							</c:choose></li>
					</c:if>
				</c:forEach>
			</ul>

		</div>
		<!--// pc 하위 메뉴 //-->
	</div>
	<!-- #header -->
</header>

<!--// popup_message //-->
<div class="remodal messagePop messagePop1" data-remodal-id="confirmPop"
	role="dialog" aria-labelledby="modal1Title"
	aria-describedby="modal1Desc">
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC">로그아웃</p>
		</div>
		<div class="modal-body">
			<p class="messageTxt">로그아웃 하시겠습니까?</p>
		</div>
		<div class="modal-footer">
			<div class="tc">
				<button onclick="fn_logout(); return false;"
					class="remodal-confirm btn02 btn_green">확인</button>
				<button data-remodal-action="cancel"
					class="remodal-cancel btn02 btn_gray">취소</button>
			</div>
		</div>
	</div>
</div>
<!--// popup_message //-->