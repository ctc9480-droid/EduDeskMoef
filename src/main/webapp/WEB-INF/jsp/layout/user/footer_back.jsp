<%@page import="com.educare.edu.menu.service.MenuUtil"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>

<div id="footer">
	<footer class="flex" >
        <div class="foot_menu">
			<div class="grid_1100">
				<ul>
					<!-- <li><a href="#popPrivacy" class="fc_red" title="팝업 열림" target="_self">개인정보처리방침</a></li> -->
					<li><a href="https://www.ktl.re.kr/service/service_4/priv.do?menu_gubun=service&menu_no=3&menu_no2=&menu_no3=&params=" class="fc_red" title="새창열림" target="_blank">개인정보처리방침</a></li>
					<li><a href="#popEmail" title="팝업 열림" target="_self">이메일주소무단수집거부</a></li>
                   <!--  <li><a href="#popAgreement" title="팝업 열림" target="_self">이용약관</a></li> -->
                    <li><a href="#popCopyright" title="팝업 열림" target="_self">저작권정책</a></li>
                    <li><a href="#refund" title="팝업 열림" target="_self">환불규정</a></li>
				</ul>
				<div class="footFsWrap cf">
					<label for="footFs1" class="sound_only">KTL 패밀리사이트 바로가기</label>
					<select title="KTL 패밀리 사이트" id="footFs1" class="f_fs" title="KTL 패밀리 사이트 목록">
	                   	<option value="">KTL 패밀리 사이트</option>
						<option value="https://www.ktl.re.kr">대표홈페이지</option>
						<option value="https://customer.ktl.re.kr/web/main/index.do">대표홈페이지</option>
						<option value="https://www.certinfo.or.kr/index.do">해외인증정보시스템</option>
						<option value="https://k-tools.ktl.re.kr">KTL 장비관리플랫폼</option>
						<option value="https://stl.ktl.re.kr/web/main/index.do">디지털헬스케어 소프트웨어 시험평가센터</option>
						<option value="https://smarthc.ktl.re.kr/web/main/index.do">스마트헬스케어종합지원센터</option>
						<option value="https://ptp.ktl.re.kr/ilc/main/index.do">숙련도 시험</option>
						<option value="https://stagesafety.or.kr">공연장안전지원센터</option>
						<option value="https://safety.kbrainc.com/main/">공연장안전지원센터 사이버아카데미</option>
	                </select>
					<button type="submit" value="이동" class="btn_go" title="새 창 이동">이동</button>

					<label for="footFs2" class="sound_only">주요기관 홈페이지 바로가기</label>
					<select title="주요기관 홈페이지" id="footFs2" class="f_fs" title="주요기관 홈페이지 목록">
                        <option value="">주요기관 홈페이지</option>
						<option value="https://sgdata.ksga.org/kr/main.do">스마트그리드 데이터 센터</option>
						<option value="https://tradenavi.or.kr/CmsWeb/viewPage.req?idx=PG0000000090">통합무역정보서비스 trade NAVI</option>
						<option value="https://www.oico.kr/main/main.php">산업통산자원부 산업융합촉진 옴부즈만</option>
						<option value="https://www.acrc.go.kr">국민권익위원회</option>
						<option value="https://www.juso.go.kr/openIndexPage.do">도로명주소 안내</option>
						<option value="https://ecomileage.seoul.go.kr/home/index.do">eco 마일리지</option>
						<option value="http://www.safetykorea.kr">국가기술표준원 제품안전정보센터</option>
						<option value="https://istans.or.kr/mainMenu.do">ISTANS</option>
						<option value="https://www.hikorea.go.kr">Hi Korea 외국인을 위한 전자정부</option>
						<option value="https://www.g4b.go.kr:441/svc/main.do">G4B 기업지원 플러스</option>
						<option value="https://www.kosti.or.kr">Kosti 전략물자관리원</option>
						<option value="https://www.yestrade.go.kr/user/main.do?method=main">yes trade</option>
						<option value="http://www.comis.go.kr/index.jsp">COMIS</option>
						<option value="https://www.standard.go.kr/KSCI/portalindex.do">e 나라표준인증</option>
						<option value="https://www.utradehub.or.kr/porgw/index.jsp?sso=ok">U trade Hub</option>
						<option value="https://www.bizinfo.go.kr/web/index.do">기업마당</option>
						<option value="https://www.gov.kr/portal/main?Mcode=9999#none">정부24</option>
						<option value="https://www.knowtbt.kr">KNOWTBT 해외기술규제정보시스템</option>
						<option value="https://www.index.go.kr">e-나라지표</option>
						<option value="https://blog.naver.com/hellopolicy">정책공감</option>
						<option value="https://www.korea.kr/main.do">정책브리핑</option>
						<option value="https://www.g4b.go.kr:441/svc/main.do">기업지원플러스</option>
						<option value="https://www.gov.kr/portal/main/nologin">민원24</option>
						<option value="https://www.clean.go.kr/index.es?sid=a1">국민권익위원회</option>
						<option value="https://www.better.go.kr/fz/intro/RrcIntro.jsp">규제개혁위원회</option>
						<option value="https://www.etube.re.kr/main/mainIndex.do">e-Tube 산업기술개발장비</option>
						<option value="http://www.easylaw.go.kr/CSP/Main.laf">찾기쉬운 생활법령정보</option>
						<option value="https://www.better.go.kr/zz.main.PortalMain.laf">통합규제 정보포털</option>
						<option value="http://president.globalwindow.org">정상외교 경제활용포털</option>
						<option value="http://www.alio.go.kr/home.do">ALIO</option>
						<option value="https://www.culture.go.kr/index.do">문화포털</option>
						<option value="http://www.ssc.go.kr">사회보장위원회</option>
						<option value="http://www.fta.go.kr/main/">FTA강국 코리아</option>
                    </select>
					<button type="submit" value="이동" class="btn_go" title="새 창 이동">이동</button>
				</div>
			</div><!--//grid_1100//-->
        </div><!--//foot_menu//-->

		<div class="grid_1100 flex-vc tc">
			<h2>
				<a href="${utcp.ctxPath }/" title="페이지 이동"><img src="${utcp.ctxPath }/resources/user/image/ft_logo.png" alt="ktl 한국산업기술시험원 Korea Testing Laboratory" /></a>
			</h2>
			
			<div class="corpWrap">
				<div class="box_comInfo">
					<span class="text_tel">
						한국산업기술시험원&nbsp;&nbsp;&nbsp;&nbsp;대표자명 : 김세종&nbsp;&nbsp;&nbsp;&nbsp;사업자번호 : 113-82-06228<br>
						주소 : 우) 52852 경상남도진주시 충의로 10(충무공동)한국산업기술시험원&nbsp;&nbsp;&nbsp;&nbsp;전화번호 : 080-808-0114 &nbsp;&nbsp;&nbsp;&nbsp;홈페이지문의 : 02-860-1312
					</span>
					<span class="text_copy">Copyright (c) 2023 Korea Testinglaboratory. All rights Reserved.</span>
				</div>
			</div>
		</div><!--//grid_1100//-->
        <script type="text/javascript">
            document.addEventListener("DOMContentLoaded", function() {
                document.querySelectorAll(".footFsWrap .btn_go").forEach(function(button) {
                    button.addEventListener("click", function() {
                        var select = this.previousElementSibling;
                        if (select && select.value) {
                            window.open(select.value, "_blank");
                        }
                    });
                });
            });
        </script>
	</footer>
</div>




<!--// #footer -->

<!--// 사이트맵 //-->
<div class="remodal sitemapWrap" data-remodal-id="sitemap" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc">
	<button data-remodal-action="close" class="remodal-close" aria-label="Close"></button>
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC"><i class="fas fa-chevron-circle-right fs_22 pdr5"></i>사이트맵</p>
		</div>
		<div class="modal-body">
			<div class="sitemap cf">
				<%
					pageContext.setAttribute("baseMenuList", MenuUtil.getBaseMenuList());
				%>
				<c:forEach begin="0" end="4" var="i">
					<c:if test="${baseMenuList[i].st == 1 }">
						<dl class="sitemap sitemap_1st">
							<dt>${baseMenuList[i].nm }</dt>
							<dd>
								<ul class="s_depth_menu">
									<c:choose>
										<c:when test="${not empty baseMenuList[i].sub }">
											<c:forEach items="${baseMenuList[i].sub }" var="o">
												<c:if test="${o.st == 1 }">
													<li>
														<a href="${utcp.ctxPath}${o.url}">${o.nm }</a>
													</li>
												</c:if>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<li>
												<a href="${utcp.ctxPath}${baseMenuList[i].url}">${baseMenuList[i].nm }</a>
											</li>
										</c:otherwise>
									</c:choose>
								</ul>
							</dd>
						</dl>
					</c:if>
				</c:forEach>


			</div>
		</div>
	</div>
</div>
<!--// 사이트맵 //-->


<!--// 이메일주소무단수집거부 //-->
<div class="remodal popWrap" data-remodal-id="popEmail" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc">
	<button data-remodal-action="close" class="remodal-close" aria-label="Close"></button>
	
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC">이메일주소무단수집거부</p>
		</div>
		<div class="modal-body">
			<div class="div_outline">
				<div class="email_area">
					<div class="email_box">
						<p class="normal_txt mgb30">
							무차별적으로 보내지는 타사의 메일을 차단하기 위해, 본 웹사이트에 게시된 이메일 주소가 전자우편 수집 프로그램이나
그 밖의 기술적 장치를 이용하여 무단으로 수집되는 것을 거부하며, 이를 위반시 정보통신망법의해 형사처벌을 유념하시기 바랍니다.
						</p>
						<p class="big_txt">
							한국산업기술시험원 KTL아카데미
						</p>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!--// 이메일주소무단수집거부 //-->

<!--// 저작권정책 //-->
<div class="remodal popWrap" data-remodal-id="popCopyright" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc">
	<button data-remodal-action="close" class="remodal-close" aria-label="Close"></button>
	
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC">저작권정책</p>
		</div>
		<div class="modal-body">
			<div class="copy">
				<div class="rfConWrap">
                    <h4 class="h4_style rfCon01">본 서비스를 통하여 제공하는 정보 중 한국산업기술시험원 KTL아카데미에서 제작한 정보에 대한
                        저작권과 판권은 <b class="fc_darkblue">한국산업기술시험원 KTL아카데미</b>의 소유이며 저작권법의 보호를 받습니다.</h4>
					<p class="fs_16 tl pd15">
                        따라서 본 서비스의 사용자는 아래 사항을 준수해야 합니다.</p>
                    <ul class="textList1">
                        <li>한국산업기술시험원 KTL아카데미의 사전허가 없이 어떠한 매체에도 직·간접적으로 복사, 배포, 판매하거나 인터넷, 모바일 및 데이터베이스를 비롯한 각종 정보서비스 등에 사용하는 것을 금합니다.</li>
                        <li>특히, 기업이나 기관단체에서 사내 사용을 위해 데이터베이스를 구축하거나 이에 해당하는 정보서비스를 하는 것은 사용처가 사내에 한정되고 비영리 목적이라도 저작권법에 위배됩니다.</li>
                    </ul>
				</div>
			</div>
		</div>
	</div>
</div>
<!--// 저작권정책 //-->

<!--// 환불규정 //-->
<div class="remodal popWrap" data-remodal-id="refund" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc">
	<button data-remodal-action="close" class="remodal-close" aria-label="Close"></button>
	
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC">환불규정</p>
		</div>
			<div class="refund">
				<div class="rfConWrap">
					<h4 class="h4_style rfCon01">결제 및 환불 (취소)</h4>
                    <ul class="textList1">
                        <li>KTL 아카데미 홈페이지를 통하여 교육 신청이 가능하며, 교육비 납부는 온라인 결제, 계좌이체, 세금계산서 등의 방법을 활용</li>
                        <li>
                            (취소 및 환불) 교육 시작 1일전까지 취소 요청한 신청자를 대상으로 100% 환불 가능
                            <ul class="lineList2">
                                <li><em>-</em>교육 진행 중 취소는 환불 불가 (미수료자 포함)</li>
                            </ul>
                        </li>
                    </ul>
				</div>
			</div>
		</div>
	</div>
</div>
<!--// 환불규정 //-->