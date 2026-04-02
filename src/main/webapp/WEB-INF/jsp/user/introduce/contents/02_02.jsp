<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<div class="sub_txt">
	<span class=""><img
		src="${utcp.ctxPath}/resources/user/image/icon/icon_subtitle.png" alt="">찾아오시는 길</span>
</div>
<div class="sub_int">

	<section class="cf">
		
		
		<!-- * 카카오맵 - 지도퍼가기 -->
<!-- 1. 지도 노드 -->
<div id="daumRoughmapContainer1701917723112" class="root_daum_roughmap root_daum_roughmap_landing w100"></div>

<!--
	2. 설치 스크립트
	* 지도 퍼가기 서비스를 2개 이상 넣을 경우, 설치 스크립트는 하나만 삽입합니다.
-->
<script charset="UTF-8" class="daum_roughmap_loader_script" src="https://ssl.daumcdn.net/dmaps/map_js_init/roughmapLoader.js"></script>

<!-- 3. 실행 스크립트 -->
<script charset="UTF-8">
	new daum.roughmap.Lander({
		"timestamp" : "1701917723112",
		"key" : "2h5b4",
		"mapWidth" : "640",
		"mapHeight" : "360"
	}).render();
</script>
		
		
		<div class="cont edu1">
			<ul class="listType1">
				<li>
					교통안내
					<ul class="listType2">
						<li>
							주소
							<ul class="listType4">
								<li><strong>13817 경기도 과천시 상하벌로 110 국립과천과학관 교육관</strong></li>
							</ul>
						</li>
						<li>
							교육관 대표전화번호
							<ul class="listType4">
								<li>
									<strong>02-3677-1367</strong>
									<span class="dp_b fc_blue">※ 교육 문의 외 일반 문의 전화번호: 02-3677-1500</span>
								</li>
							</ul>
						</li>
						<li>
							교육관 학습관리 시스템 접속
							<ul class="listType4">
								<li><strong>국립과천과학관 홈페이지 <a href="http://www.sciencecenter.go.kr" target="_blank" class="dp_ib">http://www.sciencecenter.go.kr</a> 에서 예약 &gt; 교육 선택</strong></li>
							</ul>
						</li>
						<li>
							이용시간
							<ul class="listType4">
								<li><strong>(화~일) 9:30 ~ 17:30</strong> <span class="fc_blue">※ 월요일 휴관</span></li>
							</ul>
						</li>
						<li>
							지하철 이용시
							<ul class="listType4">
								<li><strong>4호선 대공원역 6번 출구 앞 &gt; 정문 왼쪽 교육관 입구</strong></li>
								<li>4호선 경마공원역 5번 출구 (도보 약 10분 소요)</li>
							</ul>
						</li>
						<li>
							자가용 이용시
							<ul class="listType4">
								<li>
									과천/사당방면에서 오시는 길<br>
									과천대로 &gt; 대공원 방면으로 고가도로 &gt; 대공원역 삼거리에서 좌회전 &gt; 약 600m 진행 후 좌회전 &gt; 주차장입구
								</li>
								<li>
									양재/선바위방면에서 오시는 길<br>
									과천대로 &gt; 경마공원역 방향으로 직진 &gt; 과천과학관 출입구로 우회전 &gt; 주차장입구
								</li>
								<li>
									수원/안양방면에서 오시는길<br>
									과천대로 &gt; 사당 방면으로 지하차도 옆길 &gt; 대공원방면으로 우회전 &gt;  주차장입구
								</li>
								<li>
									고속도로 이용시
									<ul class="listType5">
										<li>
											영동, 경부 고속도로 이용 시<br>
											양재IC &gt; 양재대로(과천방향) &gt; 선바위검문소에서 좌회전 &gt; 경마공원 &gt; 과천과학주차장
										</li>
										<li>
											서해안고속도로 이용 시<br>
											조남 JC &gt; 서울외곽순환도로(판교방향) &gt; 평촌IC지나서 &gt; 학의분기점(과천방면) &gt; 과천터널 후 서울방향 &gt; 오른쪽차선으로 진행 &gt; 대공원방향으로 우회전 &gt; 주차장입구
										</li>
									</ul>
									<span class="dp_b fc_blue">※ 주말 및 공휴일에는 과학관 주변이 매우 혼잡하오니 지하철 이용을 권장합니다.</span>
								</li>
							</ul>
						</li>
					</ul>
				</li>
				<li>
					주차안내
					<img src="${utcp.ctxPath}/resources/user/image/img/edu_map.jpg" alt="수강생 차량 입차 및 주차 방법  1)반드시 제1출입구, 제2출입구 유인정산소 게이트로 진입  2)정산직원에게 수강증 제시(1차시:첫수업안내문자) 후 입차" class="mgb0">
					<ul class="listType2">
						<li>
							<strong>교육관은 서주차장에서 가장 가깝습니다.</strong>
							<ul class="listType4">
								<li>국립과천과학관 주차장 수용가능 차량: 총 1,112대</li>
							</ul>
						</li>
					</ul>
				</li>
			</ul>
			
			<table class="tb04 w100 fs_15">
                <caption class="sound_only">차종별 주차대수 - 승용차, 장애인, 대형 순으로 내용을 제공합니다.</caption>
                <colgroup>
                    <col style="width: 33%;">
                    <col style="width: 33%;">
                    <col style="width: 34%;">
                </colgroup>
                <thead>
                    <tr>
                        <th scope="col">승용차</th>
                        <th scope="col">장애인</th>
                        <th scope="col">대형</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td class="tl">
                        	<span class="dp_b">(총 1,016대)</span>
                        	<ul class="listType5">
                        		<li>중앙주차장 : 412대</li>
                        		<li>동주차장 : 86대</li>
                        		<li><strong class="fw_500">서주차장 : 518대</strong></li>
                        	</ul>
                        </td>
                        <td class="tl">
                        	<span class="dp_b">(총 44대)</span>
                        	<ul class="listType5">
                        		<li>중앙주차장 : 17대</li>
                        		<li>동주차장 : 3대</li>
                        		<li><strong class="fw_500">서주차장 : 24대</strong></li>
                        	</ul>
                        </td>
                        <td class="tl vt">
                        	<span class="dp_b">(총 44대)</span>
                        	<ul class="listType5">
                        		<li><strong class="fw_500">서주차장 : 40대</strong></li>
                        	</ul>
                        	<span class="dp_b fc_blue">※ 대형차량은 제 2출입구를 이용 바랍니다.</span>
                        </td>
                    </tr>
                </tbody>
            </table>
            
			<table class="tb04 w100 fs_15">
                <caption class="sound_only">차량별 요금 기준 - 구분, 요금(일일기준), 비고 순으로 내용을 제공합니다.</caption>
                <colgroup>
                    <col style="width: 33%;">
                    <col style="width: 33%;">
                    <col style="width: 34%;">
                </colgroup>
                <thead>
                    <tr>
                        <th scope="col">구분</th>
                        <th scope="col">요금(일일기준)</th>
                        <th scope="col">비고</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td class="tl"><strong class="fw_500">유료 과학교육 프로그램 수강생<br>(교육당일에 한함, 단체수업 제외)</strong></td>
                        <td class="tl"><strong class="fw_500">평일 무료,<br>주말 또는 공휴일 50% 할인</strong></td>
                        <td rowspan="5" class="tl blGray vt">
                        	<span class="fc_blue dp_b mgb20">※ 수강내역 안내 문자(1차시만 해당), 수강증을 제시 후 무료 또는 할인 주차 가능</span>
                        	<span class="dp_b">(이용시간)</span>
                        	<ul class="listType5">
                        		<li>입차 : 9:00 ~ 17:30</li>
                        		<li class="mgb10">출차 : 9:00 ~ 22:00</li>
                        		<li class="mgb10">16:00시 이후 입차시 50% 할인(전차종) 단, 기존 할인율 적용차량 제외</li>
                        		
                        		<li>신용카드, 교통카드 결제 가능</li>
                        		<li>면제차량의 경우, 증빙서류 확인필</li>
                        	</ul>
                        </td>
                    </tr>
                    <tr>
                        <td class="tl">일반차량</td>
                        <td class="tl">5,000원</td>
                    </tr>
                    <tr>
                        <td class="tl">대형차량<br>(대형승합자동차 및 대형화물자동차)</td>
                        <td class="tl">10,000원</td>
                    </tr>
                    <tr>
                        <td class="tl">경형자동차 및 친환경차량<br>다자녀 카드 소지 차량</td>
                        <td class="tl">정산금액의 50% 할인</td>
                    </tr>
                    <tr>
                        <td class="tl">장애인 차량<br>국가유공자 및 상이군경</td>
                        <td class="tl">주차요금 면제</td>
                    </tr>
                </tbody>
            </table>
		</div>


	</section>
</div>