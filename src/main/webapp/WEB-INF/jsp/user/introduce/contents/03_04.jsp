<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>


<div class="sub_txt">
	<span class=""><img
		src="${utcp.ctxPath}/resources/user/image/icon/icon_subtitle.png"
		alt="">찾아 오시는길</span>
</div>



<div class="location">
	<div class="board_tab_onoff mgt0">

		<!--// board_tab_con //-->
		<div class="board_tab_con">

			<!--// tab_con1 //-->
			<div class="cont">
				<div class="map">


					<!-- * 카카오맵 - 지도퍼가기 -->
					<!-- 1. 지도 노드 -->
					<div id="daumRoughmapContainer1754458482441"
						class="root_daum_roughmap root_daum_roughmap_landing"></div>

					<!--
					2. 설치 스크립트
					* 지도 퍼가기 서비스를 2개 이상 넣을 경우, 설치 스크립트는 하나만 삽입합니다.
				-->
					<script charset="UTF-8" class="daum_roughmap_loader_script"
						src="https://ssl.daumcdn.net/dmaps/map_js_init/roughmapLoader.js"></script>

					<!-- 3. 실행 스크립트 -->
					<script charset="UTF-8">
						new daum.roughmap.Lander({
							"timestamp" : "1754458482441",
							"key" : "6w57koi2k6e",
							"mapWidth" : "1280",
							"mapHeight" : "360"
						}).render();
					</script>
<style>
.map .cont {
	display: none;
}
</style>

				</div>

				<div class="infoBox">
					<h2 class="mapTit">태안 교육시설(나라키움 태안정책연수원)</h2>

					<dl>
						<dt>주소</dt>
						<dd>충남 태안군 안면읍 승언리 157-43 (꽃지해안로 134)</dd>
					</dl>
					<dl>
						<dt>대표전화</dt>
						<dd>041-675-5420</dd>
					</dl>
				</div>

				<div class="transportBox">
					<div class="trsp trsp01">
						<div class="trsp_icon">
							<img src="${utcp.ctxPath}/resources/user/image/icon/trsp01.webp" alt="" />
						</div>
						<h3>
							교통 <span class="trsp_txt">교육시작일 및 종료일 버스 운행</span>
						</h3>
						<div class="trans_wrap">
							<div>
								<b class="fc_darkblue">(교육 시작일)</b>세종청사(중앙동) 출발 → 태안 교육시설 도착
							</div>
							<div>
								<b class="fc_darkblue">(교육 종료일)</b>태안 교육시설 출발 → 세종 도착
							</div>
						</div>
						
					</div>
					<div class="trsp trsp02">
						<div class="trsp_icon">
							<img src="${utcp.ctxPath}/resources/user/image/icon/trsp02.webp" alt="" />
						</div>
						<h3>
							주차
						</h3>
						<div class="trans_wrap">
							<div>
								<b class="fc_darkblue">(제1주차장)</b> 숙소동 <strong>40대</strong> <br /> <span>-장애인주차장: 4대 / 전기차충전소: 5대</span>
							</div>
							<div>
								<b class="fc_darkblue">(제2주차장)</b> 교육동 <strong>40대</strong>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!--// tab_con1 //-->

		</div>
	</div>
