<%@page import="com.educare.edu.menu.service.MenuUtil"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>

<div id="footer">
	<footer class="flex">
			<div class="inner flex">
				<h2>
					<a href="${utcp.ctxPath }/" title="페이지 이동"><img
						src="${utcp.ctxPath }/resources/admin/images/logo_adm_wt.png"
						alt="재정경제부 융복합경제재정교육플랫폼" /></a>
				</h2>

				<div class="corpWrap">
					<div class="foot_menu"> 
						<ul>
							<li><a href="#popPrivacy" class="privacy" title="팝업 열림"
								target="_self">개인정보처리방침</a></li>
							<!-- <li><a
								href="https://www.ktl.re.kr/service/service_4/priv.do?menu_gubun=service&menu_no=3&menu_no2=&menu_no3=&params="
								class="fc_red" title="새창열림" target="_blank">개인정보처리방침</a></li> -->
							<li><a href="#popEmail" title="팝업 열림" target="_self">이메일무단수집거부</a></li>
							<!--  <li><a href="#popAgreement" title="팝업 열림" target="_self">이용약관</a></li> -->
							<li><a href="#popCopyright" title="팝업 열림" target="_self">저작권정책</a></li>
							<!-- <li><a href="#refund" title="팝업 열림" target="_self">환불규정</a></li> -->
							<li><a href="${utcp.ctxPath}/user/introduce/contents/03_04.do"  target="_self">오시는길</a></li>
							<li><a href="#sitemap" title="팝업 열림" target="_self">사이트맵</a></li>
						</ul>
					</div>
					<div class="box_comInfo">
						<p>충청남도 태안군 안면읍 꽃지해안로 134 나라키움 태안정책연수원</p>
						<p>교육문의: 044-215-2639, email: ceef@korea.kr</p> 
						<p class="copy">Copyright &copy; 2025 All rights Reserved.</p>
					</div>
				</div>
			</div>
			<!--//grid_1100//-->
			<script type="text/javascript">
				document.addEventListener("DOMContentLoaded", function() {
					document.querySelectorAll(".footFsWrap .btn_go").forEach(
							function(button) {
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

<div class="popup_wrap" id="vm-popupLayer">
	<template v-for="(o,i) in list">
	<div class="popup_modal" :style="getPopupStyle(i)">
		<div class="popup_tit">
			<h2>{{o.title}}</h2>
			<div class="popup_close_btn" @click="close(i)">
				<span><img src="${utcp.ctxPath}/resources/user/image/icon/popup_close01.png" alt="닫기" /></span>
			</div>
		</div>
		<div class="popup_cont_wrap">
			<div class="popup_cont" v-html="o.content">
				
			</div>
			<div class="popup_close_txt" @click="close2(i)">
				<span><img src="${utcp.ctxPath}/resources/user/image/icon/popup_close02.png" alt="오늘 하루 이 창을 열지 않음" />오늘 하루 이 창을 열지 않음</span>
			</div>
		</div>
	</div>
	</template>
</div>
<script>
var vm_popupLayer = new Vue({
	el: '#vm-popupLayer',
	data: {
		list: [],
	},
	methods: {
		openLayer: function(list){
			for(var i in list){
				var cookieCheck = getPopupCookie(list[i].idx);
				console.log(cookieCheck);
				if (cookieCheck != "N"){
					this.list.push(list[i]);
				}
			}
		},
		getPopupStyle: function(i) {
	      const isMobile = window.innerWidth <= 768;
	      if (i === 0) return {};
	      const topIncrement = 40;
	      return {
	        position: "absolute",
	        top: (100 + i * topIncrement) + "px",
	        left: isMobile ? "50%" : (50 + i * 2) + "%",
	        zIndex: (1 + i),
	      };
	    },
	    close: function(index) {
	      this.list.splice(index, 1);
	    },
	    close2: function(index){
	    	var idx = this.list[index].idx;
	    	setCookie("popup_"+idx, "N", 1);
	    	this.close(index);
	    },
		
	},
});
</script>

<!--// #footer -->

<!--// 사이트맵 //-->
<div class="remodal sitemapWrap" data-remodal-id="sitemap" role="dialog"
	aria-labelledby="modal1Title" aria-describedby="modal1Desc">
	<button data-remodal-action="close" class="remodal-close"
		aria-label="Close"></button>
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC">
				<i class="fas fa-chevron-circle-right fs_22 pdr5"></i>사이트맵
			</p>
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
													<li><a href="javascript:return false;" onclick="goPage('${baseMenuList[i].nm }','${o.url}');">${o.nm }</a></li>
												</c:if>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<li><a href="${utcp.ctxPath}${baseMenuList[i].url}">${baseMenuList[i].nm }</a>
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
<div class="remodal popWrap" data-remodal-id="popEmail" role="dialog"
	aria-labelledby="modal1Title" aria-describedby="modal1Desc">
	<button data-remodal-action="close" class="remodal-close"
		aria-label="Close"></button>

	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC">이메일주소무단수집거부</p>
		</div>
		<div class="modal-body">
			<div class="div_outline">
				<div class="email_area">
					<div class="email_box">
						<p class="normal_txt mgb30">무차별적으로 보내지는 타사의 메일을 차단하기 위해, 본
							웹사이트에 게시된 이메일 주소가 전자우편 수집 프로그램이나 그 밖의 기술적 장치를 이용하여 무단으로 수집되는 것을
							거부하며, 이를 위반시 정보통신망법의해 형사처벌을 유념하시기 바랍니다.</p>
						<p class="big_txt">재정경제부 융복합경제재정교육플랫폼</p>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!--// 이메일주소무단수집거부 //-->

<!--// 저작권정책 //-->
<div class="remodal popWrap" data-remodal-id="popCopyright"
	role="dialog" aria-labelledby="modal1Title"
	aria-describedby="modal1Desc">
	<button data-remodal-action="close" class="remodal-close"
		aria-label="Close"></button>

	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC">저작권정책</p>
		</div>
		<div class="modal-body">
			<div class="copy">
				<div class="rfConWrap">
					<h4 class="h4_style rfCon01">
						본 서비스를 통하여 제공하는 정보 중 재정경제부 융복합경제재정교육플랫폼에서 제작한 정보에 대한 저작권과 판권은 <b
							class="fc_darkblue">재정경제부 융복합경제재정교육플랫폼</b>의 소유이며 저작권법의 보호를 받습니다.
					</h4>
					<p class="fs_16 tl pd15">따라서 본 서비스의 사용자는 아래 사항을 준수해야 합니다.</p>
					<ul class="textList1">
						<li>재정경제부 융복합경제재정교육플랫폼의 사전허가 없이 어떠한 매체에도 직·간접적으로 복사, 배포,
							판매하거나 인터넷, 모바일 및 데이터베이스를 비롯한 각종 정보서비스 등에 사용하는 것을 금합니다.</li>
						<li>특히, 기업이나 기관단체에서 사내 사용을 위해 데이터베이스를 구축하거나 이에 해당하는 정보서비스를 하는
							것은 사용처가 사내에 한정되고 비영리 목적이라도 저작권법에 위배됩니다.</li>
					</ul>
				</div>
			</div>
		</div>
	</div>
</div>
<!--// 저작권정책 //-->

<!--// 환불규정 //-->
<div class="remodal popWrap" data-remodal-id="refund" role="dialog"
	aria-labelledby="modal1Title" aria-describedby="modal1Desc">
	<button data-remodal-action="close" class="remodal-close"
		aria-label="Close"></button>

	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC">환불규정</p>
		</div>
		<div class="refund">
			<div class="rfConWrap">
				<h4 class="h4_style rfCon01">결제 및 환불 (취소)</h4>
				<ul class="textList1">
					<li>재정경제부 융복합경제재정교육플랫폼 홈페이지를 통하여 교육 신청이 가능하며, 교육비 납부는 온라인 결제,
						계좌이체, 세금계산서 등의 방법을 활용</li>
					<li>(취소 및 환불) 교육 시작 1일전까지 취소 요청한 신청자를 대상으로 100% 환불 가능
						<ul class="lineList2">
							<li><em>-</em>교육 진행 중 취소는 환불 불가 (미수료자 포함)</li>
						</ul>
					</li>
				</ul>
			</div>
		</div>
	</div>
</div>
<!--// 개인정보처리방침 //-->
<div class="remodal popWrap" data-remodal-id="popPrivacy" role="dialog"
	aria-labelledby="modal1Title" aria-describedby="modal1Desc">
	<button data-remodal-action="close" class="remodal-close" aria-label="Close"></button>
	
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC">개인정보처리방침</p>
		</div>
		<div class="modal-body" style="max-height: 700px;">
			<div class="div_outline">
				<div class="privacy">
					<p class="grayBox">
						<img src="${utcp.ctxPath}/resources/user/image/icon/privacy.png" alt="" />
						재정경제부 융복합경제재정교육플랫폼(이하 ‘재정경제부’)은 정보주체의 자유와 권리 보호를 위해 「개인정보 보호법」 및 관계 법령이 정한 바를 준수하여, 
						적법하게 개인정보를 처리하고 안전하게 관리하고 있습니다. 
						이에 「개인정보 보호법」 제30조에 따라 정보주체에게 개인정보 처리에 관한 절차 및 기준을 안내하고, 
						이와 관련한 고충을 신속하고 원활하게 처리할 수 있도록 하기 위하여 다음과 같이 개인정보 처리방침을 수립ㆍ공개합니다.
					</p>
						
					<div class="toolwrap">
						<div class="tooltip" onclick="toggleTooltip(this)">
							<button>
								<img src="${utcp.ctxPath}/resources/user/image/icon/pvlb02.png" alt="" />
								<div class="text">처리목적</div>
							</button>
							<span class="tooltiptext" onclick="preventClose(event)">
								<div class="toolTit">처리목적</div>
								<div class="toolCon">
									<ul class="textList1">
										<li>재정경제부의 개인정보 처리목적은 본문을 참고하시기 바랍니다.</li>
									</ul>
								</div>
								<span class="close" onclick="closeTooltip(event)"><img src="${utcp.ctxPath}/resources/user/image/icon/toolClose.png" alt="닫기"></span>
							</span>
						</div>
						
						<div class="tooltip" onclick="toggleTooltip(this)">
							<button>
								<img src="${utcp.ctxPath}/resources/user/image/icon/pvlb10.png" alt="" />
								<div class="text">보유기간</div>
							</button>
							<span class="tooltiptext" onclick="preventClose(event)">
								<div class="toolTit">보유기간</div>
								<div class="toolCon">
									<ul class="textList1">
										<li>재정경제부의 개인정보 처리 및 보유기간은 본문을 참고하시기 바랍니다.</li>
									</ul>
								</div>
								<span class="close" onclick="closeTooltip(event)"><img src="${utcp.ctxPath}/resources/user/image/icon/toolClose.png" alt="닫기"></span>
							</span>
						</div>
						
						<div class="tooltip" onclick="toggleTooltip(this)">
							<button>
								<img src="${utcp.ctxPath}/resources/user/image/icon/pvlb01.png" alt="" />
								<div class="text">처리항목</div>
							</button>
							<span class="tooltiptext" onclick="preventClose(event)">
								<div class="toolTit">처리항목</div>
								<div class="toolCon">
									<ul class="textList1">
										<li>재정경제부의 처리하는 개인정보의 항목은 본문을 참고하시기 바랍니다.</li>
									</ul>
								</div>
								<span class="close" onclick="closeTooltip(event)"><img src="${utcp.ctxPath}/resources/user/image/icon/toolClose.png" alt="닫기"></span>
							</span>
						</div>
						
						<div class="tooltip" onclick="toggleTooltip(this)">
							<button>
								<img src="${utcp.ctxPath}/resources/user/image/icon/pvlb08.png" alt="" />
								<div class="text">제3자 제공</div>
							</button>
							<span class="tooltiptext" onclick="preventClose(event)">
								<div class="toolTit">제3자 제공</div>
								<div class="toolCon">
									<ul class="textList1">
										<li>재정경제부의 개인정보 제3자 제공은 본문을 참고하시기 바랍니다.</li>
									</ul>
								</div>
								<span class="close" onclick="closeTooltip(event)"><img src="${utcp.ctxPath}/resources/user/image/icon/toolClose.png" alt="닫기"></span>
							</span>
						</div>
						
						<div class="tooltip" onclick="toggleTooltip(this)">
							<button>
								<img src="${utcp.ctxPath}/resources/user/image/icon/pvlb09.png" alt="" />
								<div class="text">처리위탁</div>
							</button>
							<span class="tooltiptext" onclick="preventClose(event)">
								<div class="toolTit">처리위탁</div>
								<div class="toolCon">
									<ul class="textList1">
										<li>재정경제부의 개인정보 처리 위탁은 본문을 참고하시기 바랍니다.</li>
									</ul>
								</div>
								<span class="close" onclick="closeTooltip(event)"><img src="${utcp.ctxPath}/resources/user/image/icon/toolClose.png" alt="닫기"></span>
							</span>
						</div>
						
						<div class="tooltip" onclick="toggleTooltip(this)">
							<button>
								<img src="${utcp.ctxPath}/resources/user/image/icon/pvlc01.png" alt="" />
								<div class="text">정보주체의 권리의무</div>
							</button>
							<span class="tooltiptext" onclick="preventClose(event)">
								<div class="toolTit">정보주체의 권리의무</div>
								<div class="toolCon">
									<ul class="textList1">
										<li>재정경제부의 정보주체와 법정대리인 권리·의무 및 행사방법은 본문을 참고하시기 바랍니다.</li>
									</ul>
								</div>
								<span class="close" onclick="closeTooltip(event)"><img src="${utcp.ctxPath}/resources/user/image/icon/toolClose.png" alt="닫기"></span>
							</span>
						</div>
						
						<div class="tooltip" onclick="toggleTooltip(this)">
							<button>
								<img src="${utcp.ctxPath}/resources/user/image/icon/pvlc07.png" alt="" />
								<div class="text">개인정보보호책임자</div>
							</button>
							<span class="tooltiptext" onclick="preventClose(event)">
								<div class="toolTit">개인정보보호책임자</div>
								<div class="toolCon">
									<ul class="textList1">
										<li>재정경제부의 개인정보 보호책임자에 관한 사항은 본문을 참고하시기 바랍니다.
											<ul class="lineList1">
												<li><em>-</em>개인정보보호 담당부서 : 경영기획팀<br>☎ 02-6902-2426</li>
											</ul>
										</li>
									</ul>
								</div>
								<span class="close" onclick="closeTooltip(event)"><img src="${utcp.ctxPath}/resources/user/image/icon/toolClose.png" alt="닫기"></span>
							</span>
						</div>
						
						<div class="tooltip" onclick="toggleTooltip(this)">
							<button>
								<img src="${utcp.ctxPath}/resources/user/image/icon/pvlc11.png" alt="" />
								<div class="text">열람청구</div>
							</button>
							<span class="tooltiptext" onclick="preventClose(event)">
								<div class="toolTit">열람청구</div>
								<div class="toolCon">
									<ul class="textList1">
										<li>재정경제부의 개인정보 열람청구는 본문을 참고하시기 바랍니다.고충처리 및 권익침해에 관한 세부사항은 본문을 참고하시기 바랍니다.
											<ul class="lineList1">
												<li><em>-</em>개인정보 열람청구 접수·처리부서 : 경영기획팀<br>☎ 02-6902-2426</li>
											</ul>
										</li>
									</ul>
								</div>
								<span class="close" onclick="closeTooltip(event)"><img src="${utcp.ctxPath}/resources/user/image/icon/toolClose.png" alt="닫기"></span>
							</span>
						</div>
						
						<div class="tooltip" onclick="toggleTooltip(this)">
							<button>
								<img src="${utcp.ctxPath}/resources/user/image/icon/pvlc10.png" alt="" />
								<div class="text">권익침해 구제</div>
							</button>
							<span class="tooltiptext" onclick="preventClose(event)">
								<div class="toolTit">권익침해 구제</div>
								<div class="toolCon">
									<ul class="textList1">
										<li>재정경제부의 권익침해 구제방법은 본문을 참고하시기 바랍니다.
											<ul class="lineList1">
												<li><em>-</em>개인정보분쟁조정위원회 : 1833-6972</li>
												<li><em>-</em>개인정보침해 신고센터 : (국번없이) 118</li>
												<li><em>-</em>대검찰청 사이버수사과 : (국번없이) 1301</li>
												<li><em>-</em>경찰청 사이버수사국 : (국번없이) 182</li>
											</ul>
										</li>
									</ul>
								</div>
								<span class="close" onclick="closeTooltip(event)"><img src="${utcp.ctxPath}/resources/user/image/icon/toolClose.png" alt="닫기"></span>
							</span>
						</div>
					</div>
						
					<div class="titleWrap">
						<h3>목차</h3>
						<ul class="titUl1">
							<li><button data-target=".pvCon01" class="pvbtn">제1조 (개인정보의 처리목적)</button></li>
							<li><button data-target=".pvCon02" class="pvbtn">제2조 (개인정보의 처리목적, 수집항목, 보유 및 이용기간)</button></li>
							<li><button data-target=".pvCon03" class="pvbtn">제3조 (개인정보의 제3자 제공)</button></li>
							<li><button data-target=".pvCon04" class="pvbtn">제4조 (개인정보 처리의 위탁)</button></li>
							<li><button data-target=".pvCon05" class="pvbtn">제5조 (개인정보 파기절차 및 방법)</button></li>
							<li><button data-target=".pvCon06" class="pvbtn">제6조 (정보주체와 법정대리인의 권리·의무 및 행사방법)</button></li>
						</ul>
						<ul class="titUl2">
							<li><button data-target=".pvCon07" class="pvbtn">제7조 (개인정보의 안전성 확보조치)</button></li>
							<li><button data-target=".pvCon08" class="pvbtn">제8조 (개인정보 자동 수집장치의 설치·운영 및 거부에 관한 사항)</button></li>
							<li><button data-target=".pvCon09" class="pvbtn">제9조 (개인정보 보호책임자에 관한 사항)</button></li>
							<li><button data-target=".pvCon10" class="pvbtn">제10조 (개인정보 열람청구)</button></li>
							<li><button data-target=".pvCon11" class="pvbtn">제11조 (권익침해 구제방법)</button></li>
							<li><button data-target=".pvCon12" class="pvbtn">제12조 (개인정보 처리방침의 변경)</button></li>
						</ul>
					</div>
					
					<div class="pvConWrap">
						<h4 class="h4_style pvCon01">제1조(개인정보의 처리목적)<img src="${utcp.ctxPath}/resources/user/image/icon/pvmb02.png" alt=""></h4>
						<p></p>
						<ul class="numList1">
							<li>
								재정경제부는 다음의 목적을 위하여 개인정보를 처리합니다. 처리하고 있는 개인정보는 다음의 목적 이외의 용도로는 이용되지 않으며, 이용 목적이 변경되는 경우에는 ｢개인정보 보호법｣ 제18조에 따라 별도의 동의를 받는 등 필요한 조치를 이행할 예정입니다.
								<ul class="numList2">
									<li><em>①</em>회원에 대한 공제업무 및 서비스 제공</li>
									<li><em>②</em>안전예방사업(안전교육, 공모전, 컨설팅 등) 및 회원복리후생 관련 서비스 제공</li>
								</ul>
							</li>
						</ul>
						<!--// 1조 끝 -->

						
						<h4 class="h4_style pvCon02">제2조(개인정보의 처리목적, 수집항목, 보유 및 이용기간)<img src="${utcp.ctxPath}/resources/user/image/icon/pvmb01.png" alt=""></h4>
						<ul class="numList1">
							<li>
								<em>①</em>재정경제부는 다음과 같이 정보주체의 개인정보를 처리합니다.
							</li>
						</ul>
						<div class="pri_table_wrap">
						<table class="privacy_table01" cellspacing="0" cellpadding="0" summary="서비스명, 수집형태, 수집항목, 처리목적, 보유 및 이용기간 순으로 정보를 제공합니다.">
							<caption class="sound_only">서비스명, 수집형태, 수집항목, 처리목적, 보유 및 이용기간 순으로 정보를 제공합니다.</caption>
							<colgroup>
								<col style="width: ;">
								<col style="width: ;">
								<col style="width: ;">
								<col style="width: ;">		
								<col style="width: ;">		
							</colgroup>
							<thead>
								<tr>
									<th>서비스명</th>
									<th>수집형태</th>
									<th>수집항목</th>
									<th>처리목적</th>
									<th>보유 및 이용기간</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td rowspan="2">융복합경제재정교육시스템<br>회원관리</td>
									<td>필수</td>
									<td>이메일주소, 비밀번호, 이름, 성별, 생년월일, 핸드폰번호</td>
									<td rowspan="2" class="tl">
										- 교육 수행 및 보수교육 연계<br>
										- 컨설팅<br>
										- 융복합경제재정교육 관련 정보 제공, 홍보
									</td>
									<td rowspan="2">회원 탈퇴 시<br>까지</td>
								</tr>
								<tr>
									<td>선택</td>
									<td class="brGray">전화번호</td>
								</tr>
							</tbody>
						</table>
						</div>
						<!--// 2조 끝 -->
				
						
						<h4 class="h4_style pvCon03">제3조(개인정보의 제3자 제공)<img src="${utcp.ctxPath}/resources/user/image/icon/pvmb08.png" alt=""></h4>
						<ul class="numList1">
							<li><em>①</em>재정경제부는 정보주체의 동의, 법률의 특별한 규정 등 ｢개인정보 보호법｣ 제17조 및 제18조에 해당하는 경우에만 개인정보를 제3자에게 제공합니다.</li>
							<li><em>②</em>재정경제부는 원활환 서비스 제공을 위해 다음의 경우 정보주체의 동의를 얻어 필요 최소한의 범위로만 제공합니다.</li>
						</ul>
						<div class="pri_table_wrap">
						<table class="privacy_table01" cellspacing="0" cellpadding="0" summary="개인정보의 제3자 제공 - 제공받는자, 제공목적, 제공항목, 보유 및 이용기간 순으로 정보를 제공합니다.">
							<caption class="sound_only">개인정보의 제3자 제공 - 제공받는자, 제공목적, 제공항목, 보유 및 이용기간 순으로 정보를 제공합니다.</caption>
							<colgroup>
								<col style="width: 23%;">
								<col style="width: 23%;">
								<col style="width: 31%;">
								<col style="width: 23%;">	
							</colgroup>
							<thead>
								<tr>
									<th>제공받는자</th>
									<th>제공목적</th>
									<th>제공항목</th>
									<th>보유 및 이용기간</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>교육 관련 유관기관, 교육별 관리기관</td>
									<td>교육 이수 확인, 교육 실적, 보수교육 연계, 교과목 면제 신청</td>
									<td class="tl">
										- 교육정보(수행기관명, 교육주제, 수료일자)<br>
										- 수료자정보(이름, 생년월일, 소속 기관 정보)
									</td>
									<td>회원 탈퇴 시 까지</td>
								</tr>
							</tbody>
						</table>
						</div>
						<!--// 3조 끝 -->


						<h4 class="h4_style pvCon04">제4조(개인정보 처리의 위탁)<img src="${utcp.ctxPath}/resources/user/image/icon/pvmb08.png" alt=""></h4>
						<ul class="numList1">
							<li>
								<em>①</em>재정경제부는 원활한 개인정보 업무처리를 위하여 다음과 같이 개인정보 처리업무를 위탁하고 있습니다.
								<ul class="numList2">
									<li><em>※</em><a href="#none" download="개인정보 처리업무 위탁 내역.hwp">재정경제부 개인정보 처리업무 위탁 내역 </a></li>
								</ul>
							</li>
							<li><em>②</em>재정경제부는 위탁계약 체결 시 ｢개인정보 보호법｣ 제26조에 따라 위탁업무 수행목적 외 개인정보 처리금지, 안전성 확보조치, 재위탁 제한, 수탁자에 대한 관리ㆍ감독, 손해배상 등 책임에 관한 사항을 계약서 등 문서에 명시하고, 수탁자가 개인정보를 안전하게 처리하는지를 감독하고 있습니다.</li>
							<li><em>③</em>위탁업무의 내용이나 수탁자가 변경될 경우에는 지체없이 본 개인정보 처리방침을 통하여 공개하도록 하겠습니다.</li>
						</ul>
						<!--// 4조 끝 -->


						<h4 class="h4_style pvCon05">제5조(개인정보 파기절차 및 방법)<img src="${utcp.ctxPath}/resources/user/image/icon/pvmb12.png" alt=""></h4>
						<ul class="numList1">
							<li><em>①</em>재정경제부는 개인정보 보유기간의 경과, 처리목적 달성 등 개인정보가 불필요하게 되었을 때에는 지체없이 해당 개인정보를 파기합니다.</li>
							<li><em>②</em>정보주체로부터 동의받은 개인정보 보유기간이 경과하거나 처리목적이 달성되었음에도 불구하고 다른 법령에 따라 개인정보를 계속 보존하여야 하는 경우에는, 해당 개인정보를 별도의 데이터베이스(DB)로 옮기거나 보관장소를 달리하여 보존합니다.</li>
							<li>
								<em>③</em>개인정보 파기의 절차 및 방법은 다음과 같습니다.
								<ul class="numList2">
									<li>
										<em>1.</em>파기절차
										<ul class="lineList1">
											<li><em>-</em>재정경제부는 파기하여야 하는 개인정보에 대해 개인정보 파기계획을 수립하여 파기합니다. </li>
											<li><em>-</em>재정경제부는 파기 사유가 발생한 개인정보를 선정하고, 재정경제부의 개인정보 보호책임자의 승인을 받아 개인정보를 파기합니다.</li>
										</ul>
									</li>
									<li>
										<em>2.</em>파기방법
										<ul class="lineList1">
											<li><em>-</em>재정경제부는 전자적 파일 형태로 기록·저장된 개인정보는 기록을 재생할 수 없도록 파기하며, 종이 문서에 기록·저장된 개인정보는 분쇄기로 분쇄하거나 소각하여 파기합니다.</li>
										</ul>
									</li>
								</ul>
							</li>
						</ul>
						<!--// 5조 끝 -->


						<h4 class="h4_style pvCon06">제6조(정보주체와 법정대리인의 권리․의무 및 행사방법)<img src="${utcp.ctxPath}/resources/user/image/icon/pvmc01.png" alt=""><img src="${utcp.ctxPath}/resources/user/image/icon/pvmc06.png" alt=""></h4>
						<ul class="numList1">
							<li>
								<em>①</em>정보주체는 재정경제부에 대해 언제든지 개인정보 열람·정정·삭제·처리정지 요구 등의 권리를 행사할 수 있습니다.
								<ul>
									<li><em>※</em>만 14세 미만 아동에 관한 개인정보의 열람 등 요구는 법정대리인이 직접 해야 하며, 만 14세 이상의 미성년자인 정보주체는 정보주체의 개인정보에 관하여 미성년자 본인이 권리를 행사하거나 법정대리인을 통하여 권리를 행사할 수도 있습니다.</li>
								</ul>
							</li>
							<li><em>②</em>권리 행사는 ｢개인정보 보호법｣ 시행령 제41조제1항에 따라 서면, 전자우편, 모사전송(FAX) 등을 통하여 할 수 있으며, 재정경제부는 이에 대해 지체 없이 조치하겠습니다.</li>
							<li>
								<em>③</em>권리 행사는 정보주체의 법정대리인이나 위임을 받은 자 등 대리인을 통하여 할 수도 있습니다. 이 경우 ｢개인정보 처리 방법에 관한 고시 별지 11호 서식｣에 따른 위임장을 제출하여야 합니다.
								<ul>
									<li><em>※</em><a href="${utcp.ctxPath}/resources/user/231220_2.hwp" download="[별지 11] 위임장(개인정보 처리 방법에 관한 고시).hwp" class="dp_ib">｢개인정보 처리 방법에 관한 고시 별지 제11호｣ 위임장 <i class="fas fa-download"></i></a></li>
								</ul>
							</li>
							<li><em>④</em>개인정보 열람 및 처리정지 요구는 ｢개인정보 보호법｣ 제35조제4항, 제37조제2항에 의하여 정보주체의 권리가 제한될 수 있습니다.</li>
							<li><em>⑤</em>개인정보의 정정 및 삭제 요구는 다른 법령에서 그 개인정보가 수집 대상으로 명시되어 있는 경우에는 그 삭제를 요구할 수 없습니다.</li>
							<li><em>⑥</em>재정경제부는 정보주체 권리에 따른 열람의 요구, 정정·삭제의 요구, 처리정지의 요구 시 열람 등 요구를 한 자가 본인이거나 정당한 대리인인지를 확인합니다.</li>
						</ul>
						<!--// 6조 끝 -->


						<h4 class="h4_style pvCon07">제7조(개인정보의 안전성 확보조치)<img src="${utcp.ctxPath}/resources/user/image/icon/pvmc02.png" alt=""></h4>
						<ul class="numList1">
							<li>
								재정경제부는 개인정보의 안전성 확보를 위해 다음과 같은 조치를 취하고 있습니다.
								<ul class="numList2">
									<li>
										<em>1.</em>관리적 조치
										<ul class="lineList1">
											<li><em>-</em>｢개인정보의 안전성 확보조치 기준｣에 의거하여 재정경제부 내부관리계획 수립 및 시행</li>
											<li><em>-</em>개인정보취급자는 반드시 필요한 인원에 한하여 지정ㆍ관리하며, 정기적인 교육을 시행</li>
										</ul>
									</li>
									<li>
										<em>2.</em>기술적 조치
										<ul class="lineList1">
											<li><em>-</em>개인정보처리시스템 등의 접근권한의 부여, 변경, 말소를 통해 개인정보에 대한 접근 통제</li>
											<li><em>-</em>침입차단시스템과 침입방지시스템을 이용해 외부로부터의 무단 접근 통제</li>
											<li><em>-</em>개인정보처리시스템에 접속한 기록을 최소 1년 이상 보관, 관리</li>
											<li><em>-</em>정보주체의 중요 개인정보는 암호화 되어 저장 및 관리</li>
											<li><em>-</em>해킹이나 컴퓨터 바이러스 등에 의한 개인정보 유출 및 훼손을 막기 위하여 보안프로그램을 설치하고 주기적인 점검</li>
										</ul>
									</li>
									<li>
										<em>3.</em>물리적 조치
										<ul class="lineList1">
											<li><em>-</em>외부로부터 통제된 구역인 전산실, 자료보관실 등 접근 통제</li>
										</ul>
									</li>
								</ul>
							</li>
						</ul>
						<!--// 7조 끝 -->


						<h4 class="h4_style pvCon08">제8조(개인정보 자동 수집 장치의 설치․운영 및 거부에 관한 사항)<img src="${utcp.ctxPath}/resources/user/image/icon/pvmb04.png" alt=""></h4>
						<ul class="numList1">
							<li>
								<em>①</em>재정경제부는 이용자에게 개별적인 맞춤서비스를 제공하기 위해 이용 정보를 저장하고 수시로 불러오는 ‘쿠키(cookie)’를 사용합니다.
							</li>
							<li>
								<em>②</em>쿠키는 웹사이트를 운영하는데 이용되는 서버가 이용자의 컴퓨터 브라우저에게 보내는 소량의 정보이며 이용자의 PC 컴퓨터 내의 하드디스크에 저장되기도 합니다.
								<ul class="numList2">
									<li>
										<em>1.</em>쿠키의 사용목적 : 이용자가 방문한 각 서비스와 웹사이트들에 대한 방문 및 이용형태, 인기 검색어, 보안접속 여부 등을 파악하여 이용자에게 최적화된 정보 제공을 위해 사용됩니다.
									</li>
									<li>
										<em>2.</em>쿠키의 설치·운영 및 거부 : 이용자는 쿠키 저장을 다음과 같이 거부할 수 있습니다.
										<ul class="lineList1">
											<li class="pdl30"><em>예1)</em>웹브라우저(Chrome) 우측 상단의 [설정] → [개인 정보 보호 및 보안] → [사이트 설정] → [쿠키 및 사이트 데이터] → 쿠키 차단 설정</li>
											<li class="pdl30"><em>예2)</em>웹브라우저(Edge) 우측 상단의 [설정] → [쿠키 및 사이트 권한] → 쿠키 및 저장된 데이터 → 쿠키 차단 설정</li>
											<li class="pdl30"><em>예3)</em>웹브라우저(Internet Explorer) 상단의 [도구] → [인터넷 옵션] → 개인정보 메뉴의 [고급] → 쿠키 차단 설정</li>
										</ul>
									</li>
									<li>
										<em>3.</em>쿠키 저장을 거부할 경우 맞춤형 서비스 이용에 어려움이 발생할 수 있습니다.
									</li>
								</ul>
							</li>
						</ul>
						<!--// 8조 끝 -->	


						<h4 class="h4_style pvCon09">제9조(개인정보 보호책임자에 관한 사항)<img src="${utcp.ctxPath}/resources/user/image/icon/pvmc07.png" alt=""></h4>
						<ul class="numList1">
							<li>
								<em>①</em>재정경제부는 개인정보 처리에 관한 업무를 총괄해서 책임지고, 개인정보 처리와 관련한 정보주체의 불만처리 및 피해구제 등을 위하여 아래와 같이 개인정보 보호책임자를 지정하고 있습니다.
							</li>
						</ul>
						<div class="pri_table_wrap">
						<table class="privacy_table01" cellspacing="0" cellpadding="0" summary="개인정보보호책임자게 관한 사항 - 연번, 구분, 성명, 직위, 개인정보보호 담당부서 및 연락처 순으로 정보를 제공합니다.">
							<caption class="sound_only">개인정보보호책임자게 관한 사항 - 연번, 구분, 성명, 직위, 개인정보보호 담당부서 및 연락처 순으로 정보를 제공합니다.</caption>
							<colgroup>
								<col style="width: 5%;">
								<col style="width: 20%;">
								<col style="width: 15%;">
								<col style="width: 15%;">
								<col style="width: 45%;">
							</colgroup>
							<thead>
								<tr>
									<th>연번</th>
									<th>구분</th>
									<th>성명</th>
									<th>직위</th>
									<th>개인정보보호 담당부서 및 연락처</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>1</td>
									<td>개인정보 보호책임자</td>
									<td>김후진</td>
									<td>기획조정실장</td>
									<td class="tl" rowspan="2">
										- 전화 : 044-215-2642
									</td>
								</tr>
								<tr>
									<td>2</td>
									<td>개인정보 보호담당자</td>
									<td>이철영</td>
									<td class="brGray">사무관</td>
								</tr>
							</tbody>
						</table>
						</div>
						<ul class="numList1">
							<li>
								<em>②</em>정보주체는 재정경제부의 서비스(또는 사업)를 이용하시면서 발생한 모든 개인정보보호 관련 문의, 불만처리, 피해구제 등에 관한 사항을 개인정보 보호책임자 및 담당부서로 문의할 수 있습니다. 재정경제부는 정보주체의 문의에 대해 지체 없이 답변 및 처리해드릴 것입니다.
							</li>
						</ul>
						<!--// 9조 끝 -->


						<h4 class="h4_style pvCon10">제10조(개인정보 열람청구)<img src="${utcp.ctxPath}/resources/user/image/icon/pvmc11.png" alt=""></h4>
						<ul class="numList1">
							<li>
								<em>①</em>정보주체는 ｢개인정보 보호법｣ 제35조에 따른 개인정보의 열람 청구를 아래의 부서에 할 수 있고, 재정경제부는 정보주체의 개인정보 열람청구가 신속하게 처리되도록 노력하겠습니다.
								<ul class="lineList1">
									<li><em>▶</em>개인정보 열람청구 접수·처리부서</li>
									<li><em>-</em>부 서 명 : 미래융복합경제교육운영팀</li>
									<li><em>-</em>담 당 자 : 박찬규 팀장</li>
									<li><em>-</em>연 락 처 : ☎044-215-2660</li>
								</ul>
							</li>
							<li>
								<em>②</em>정보주체는 제1항의 열람청구 접수ㆍ처리부서 이외에 개인정보보호위원회 개인정보보호 포털을 통해 개인정보 열람청구를 할 수 있습니다.
								<ul>
									<li>
										<em>※</em>개인정보보호위원회 개인정보보호 포털<a href="https://www.privacy.go.kr/front/main/main.do" target="_blank" class="dp_ib">(www.privacy.go.kr)</a> → 민원마당 → 개인정보 열람등요구 (휴대폰 또는 아이핀을 통한 실명인증 필요)
									</li>
								</ul>
							</li>
						</ul>
						<!--// 10조 끝 -->

					
						<h4 class="h4_style pvCon11">제11조(권익침해 구제방법)<img src="${utcp.ctxPath}/resources/user/image/icon/pvmc10.png" alt=""></h4>
						<ul class="numList1">
							<li>
								<em>①</em>정보주체는 개인정보침해로 인한 구제를 받기 위하여 개인정보분쟁조정위원회, 한국인터넷진흥원 개인정보침해신고센터 등에 분쟁해결이나 상담 등을 신청할 수 있습니다. 이 밖에 기타 개인정보침해의 신고, 상담에 대하여는 아래의 기관에 문의하시기 바랍니다.
								<ul class="lineList1">
									<li><em>-</em>개인정보분쟁조정위원회 (<a href="https://www.kopico.go.kr/main/main.do" target="_blank">www.kopico.go.kr</a>) : (국번없이) 1833-6972</li>
									<li><em>-</em>개인정보침해 신고센터 (<a href="https://privacy.kisa.or.kr/main.do" target="_blank">privacy.kisa.or.kr</a>) : (국번없이) 118</li>
									<li><em>-</em>대검찰청 사이버수사과 (<a href="https://www.spo.go.kr/site/spo/main.do"  target="_blank">www.spo.go.kr</a>) : (국번없이) 1301</li>
									<li><em>-</em>경찰청 사이버수사국 (<a href="https://ecrm.police.go.kr/minwon/main" target="_blank">ecrm.cyber.go.kr</a>) : (국번없이) 182</li>
								</ul>
							</li>
							<li>
								<em>②</em>｢개인정보 보호법｣ 제35조(개인정보의 열람), 제36조(개인정보의 정정·삭제), 제37조(개인정보의 처리정지 등)의 규정에 의한 요구에 대하여 공공기관의 장이 행한 처분 또는 부작위로 인하여 권리 또는 이익의 침해를 받은 자는 행정심판법이 정하는 바에 따라 행정심판을 청구할 수 있습니다.
								<ul>
									<li><em>※</em>행정심판에 대한 자세한 사항은 중앙행정심판위원회(<a href="https://www.simpan.go.kr/nsph/index.do" target="_blank" class="dp_ib">www.simpan.go.kr</a>) 참고</li>
								</ul>
							</li>
						</ul>
						<!--// 11조 끝 -->
						

						<h4 class="h4_style pvCon12">제12조(개인정보 처리방침의 변경)<img src="${utcp.ctxPath}/resources/user/image/icon/pvmc03.png" alt=""></h4>
						<ul class="numList1">
							<li>
								<em>①</em>본 방침은 2023. 12. 20.부터 적용됩니다. 직전 방침에서 다음 내용이 변경되었습니다.
							</li>
						</ul>
						<div class="pri_table_wrap">
						<table class="privacy_table01" cellspacing="0" cellpadding="0" summary="개인정보 처리방침의 변경 - 개정 전 목차, 개정 후 목차, 개정사유 순으로 정보를 제공합니다.">
							<caption class="sound_only">개인정보 처리방침의 변경 - 개정 전 목차, 개정 후 목차, 개정사유 순으로 정보를 제공합니다.</caption>
							<thead>
								<tr>
									<th>개정 전 목차</th>
									<th>개정 후 목차</th> 
									<th>개정사유</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td class="tl fs_12 ls-1 lh15">
										- 제1조(개인정보의 처리목적 및 보유기간, 처리하는 개인정보의 항목)<br>
										- 제2조(개인정보의 제3자 제공에 관한 사항)<br>
										- 제3조(개인정보처리의 위탁)<br>
										- 제4조(정보주체의 권리·의무 및 행사방법에 관한 사항)<br>
										- 제5조(개인정보의 파기절차 및 파기방법)<br>
										- 제6조(개인정보 자동 수집 장치의 설치·운영 및 거부에 관한 사항)<br>
										- 제7조(영상정보처리기기 운영·관리에 관한 사항)<br>
										- 제8조(개인정보의 안전성 확보조치에 관한 사항)<br>
										- 제9조(개인정보 열람청구)<br>
										- 제10조(개인정보 보호책임자에 관한 사항)<br>
										- 제11조(개인정보 처리방침 적용 및 변경)<br>
										- 제12조(개인정보 처리방침 적용 및 변경)
									</td>
									<td class="tl fs_12 ls-1 lh15">
										- 제1조 (개인정보의 처리목적)<br>
										- 제2조 (개인정보의 처리목적, 수집항목, 보유 및 이용기간)<br>
										- 제3조 (개인정보의 제3자 제공)<br>
										- 제4조 (개인정보 처리의 위탁)<br>
										- 제5조 (개인정보 파기절차 및 방법)<br>
										- 제6조 (정보주체와 법정대리인의 권리·의무 및 행사방법)<br>
										- 제7조 (개인정보의 안전성 확보조치)<br>
										- 제8조 (개인정보 자동 수집장치의 설치·운영 및 거부에 관한 사항)<br>
										- 제9조 (개인정보 보호책임자에 관한 사항)<br>
										- 제10조 (개인정보 열람청구)<br>
										- 제11조 (권익침해 구제방법)<br>
										- 제12조 (개인정보 처리방침의 변경)
									</td>
									<td class="tl fs_12 ls-1 lh15">
										- 개인정보 보호법 제30조 법적 의무사항 명확화<br>
										- 개인정보 처리방침 작성지침(일반, 2022.3.) 준수
									</td>
								</tr>
							</tbody>
						</table>
						<!-- <ul class="numList1">
							<li><em>②</em>이전의 개인정보 처리방침은 아래에서 확인할 수 있습니다.</li>
						</ul>
						// 12조 끝
						
						<div class="w100 mgt20">
							<a href="https://www.csia.or.kr/cms/node/6001.do?page=1" target="_blank" class="w100 btn04 btn_blackl">~ 2022. 11. 30. 바로가기</a>
						</div> -->
					</div> 
					<!--// pvConWrap //-->

				</div>
				<!--// privacy //-->
			</div>
		</div>
	</div>
</div>

</div>
<!--// 환불규정 //-->


<script>
    function toggleTooltip(element) {
        var tooltips = document.querySelectorAll('.tooltip');
        tooltips.forEach(function(tooltip) {
            var tooltipText = tooltip.querySelector('.tooltiptext');
            if (tooltip === element) {
                tooltipText.classList.toggle('active');
            } else {
                tooltipText.classList.remove('active');
            }
        });
    }

    function closeTooltip(event) {
        event.stopPropagation();
        var tooltipText = event.target.closest('.tooltiptext');
        tooltipText.classList.remove('active');
    }

    function preventClose(event) {
        event.stopPropagation();
    }

    var tooltips = document.querySelectorAll('.tooltip');

    tooltips.forEach(function(tooltip) {
        tooltip.addEventListener('click', function(event) {
            event.stopPropagation();
        });
    });

    document.addEventListener('click', function(event) {
        tooltips.forEach(function(tooltip) {
            var tooltipText = tooltip.querySelector('.tooltiptext');
            if (!tooltipText.contains(event.target)) {
                tooltipText.classList.remove('active');
            }
        });
    });
</script>
<script>
    const buttons = document.querySelectorAll('.pvbtn');

    buttons.forEach(button => {
        button.addEventListener('click', () => {
            const targetClass = button.getAttribute('data-target');
            const targetSection = document.querySelector(targetClass);
            if (targetSection) {
                targetSection.scrollIntoView({ behavior: 'smooth' });
            }
        });
    });
</script>

