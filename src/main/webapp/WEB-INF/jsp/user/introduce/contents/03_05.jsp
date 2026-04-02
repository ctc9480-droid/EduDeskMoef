<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="https://cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.css" />
<link rel="stylesheet" type="text/css"
	href="https://cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick-theme.css" />
<link rel="stylesheet" type="text/css"
	href="${utcp.ctxPath}/resources/user/css/slick.css" />
<script type="text/javascript"
	src="${utcp.ctxPath}/resources/user/js/slick.min.js"></script>

<script>
document.addEventListener("DOMContentLoaded", function () {
	  const tabs = document.querySelectorAll(".introTab");
	  const contents = document.querySelectorAll(".introBox");

	  tabs.forEach((tab, index) => {
	    tab.addEventListener("click", function (e) {
	      e.preventDefault();

	      // 탭 모두 off
	      tabs.forEach(t => t.classList.remove("on"));
	      contents.forEach(c => c.classList.remove("on"));

	      // 선택된 탭, 내용 on
	      tab.classList.add("on");
	      contents[index].classList.add("on");
	      
	   // 탭이 열릴 때 slick 위치 재조정
	      const slider = contents[index].querySelector(".slider-for");
	      if (slider && $(slider).hasClass('slick-initialized')) {
	        $('.slider-for, .slider-nav').slick('setPosition');
	      }
	      
	    });
	  });
	});
	
$(document).ready(function() {
	
	$('.boxList').each(function() {
		  var $this = $(this);
		  $this.find('.slider-for').slick({
		    slidesToShow: 1,
		    slidesToScroll: 1,
		    arrows: true,
		    dots: true,
		    prevArrow: $this.find('.prevArrow'),
		    nextArrow: $this.find('.nextArrow')
		  });
		});
	});

</script>
<div class="sub_txt">
	<span class=""><img
		src="${utcp.ctxPath}/resources/user/image/icon/icon_subtitle.png"
		alt="">시설안내</span>
</div>
<div class="navBot navBot2">
	<ul>
		<li><a href="" class="introTab on">건물전경</a></li>
		<li><a href="" class="introTab">내부시설</a></li>
		<li><a href="" class="introTab">편의시설</a></li>
		<li><a href="" class="introTab">교육시설</a></li>
		<li><a href="" class="introTab">외부시설</a></li>
	</ul>
</div>

<div class="introWrap">
	<div class="intro01 introBox on">
		<img src="${utcp.ctxPath}/resources/user/image/img/intro05.webp"
			alt="" / class="intro01_img">
	</div>
	<div class="intro02 introBox slide">
		<div class="introBoxWrap">
			<h2 class="introTit">숙소동</h2>
			<div class="boxList boxList00">
				<div class="boxImg">
					<div class="boxSlider"> 
						<div class="boxSliderCont"> 
							<button type="button" class="prevArrow">
								<img
									src="${utcp.ctxPath}/resources/user/image/icon/slide_prev.png"
									alt="" />
							</button>
							<button type="button" class="nextArrow">
								<img
									src="${utcp.ctxPath}/resources/user/image/icon/slide_next.png"
									alt="" />
							</button>
							<div class="slider slider-for">
								<div>
									<div class="boxSliderWrap">
										<img
											src="${utcp.ctxPath}/resources/user/image/img/room01.webp"
											alt="" />
									</div>
								</div> 
								<div> 
									<div class="boxSliderWrap">
										<img
											src="${utcp.ctxPath}/resources/user/image/img/room02.webp"
											alt="" />
									</div>
								</div>
								<div> 
									<div class="boxSliderWrap">
										<img
											src="${utcp.ctxPath}/resources/user/image/img/room03.webp"
											alt="" />
									</div>
								</div>
								<div> 
									<div class="boxSliderWrap">
										<img
											src="${utcp.ctxPath}/resources/user/image/img/room04.webp"
											alt="" />
									</div>
								</div>
								<div> 
									<div class="boxSliderWrap">
										<img
											src="${utcp.ctxPath}/resources/user/image/img/room05.webp"
											alt="" />
									</div>
								</div>
								<div> 
									<div class="boxSliderWrap">
										<img
											src="${utcp.ctxPath}/resources/user/image/img/room06.webp"
											alt="" />
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<h2 class="introTit">Rooms</h2>
			<div class="boxList boxList01">
				<div class="boxImg">
					<div class="boxSlider">
						<div class="boxSliderCont">
							<button type="button" class="prevArrow">
								<img
									src="${utcp.ctxPath}/resources/user/image/icon/slide_prev.png"
									alt="" />
							</button>
							<button type="button" class="nextArrow">
								<img
									src="${utcp.ctxPath}/resources/user/image/icon/slide_next.png"
									alt="" />
							</button>
							<div class="slider slider-for">
								<div>
									<div class="boxSliderWrap">
										<img
											src="${utcp.ctxPath}/resources/user/image/img/room0801.webp"
											alt="" />
									</div>
								</div>
								<div>
									<div class="boxSliderWrap">
										<img
											src="${utcp.ctxPath}/resources/user/image/img/room07.webp" 
											alt="" /> 
									</div>
								</div>
								<div>
									<div class="boxSliderWrap">
										<img
											src="${utcp.ctxPath}/resources/user/image/img/room0802.webp"
											alt="" />
									</div>
								</div>
								
							</div>
						</div>
					</div>
				</div>
				<div class="boxTxt">
					<h3>
						<span>4인룸</span>
					</h3>
					<ul>
						<li>규모 : 13실</li>
						<li>8층 (808~809호), 7층 (701~709호), <br />
						601호, 401호
						</li>
					</ul>
				</div>
			</div>

			<div class="boxList boxList03">
				<div class="boxImg">
					<div class="boxSlider">
						<div class="boxSliderCont">
							<button type="button" class="prevArrow">
								<img
									src="${utcp.ctxPath}/resources/user/image/icon/slide_prev.png"
									alt="" />
							</button>
							<button type="button" class="nextArrow">
								<img
									src="${utcp.ctxPath}/resources/user/image/icon/slide_next.png"
									alt="" />
							</button>
							<div class="slider slider-for">
								<div>
									<div class="boxSliderWrap">
										<img
											src="${utcp.ctxPath}/resources/user/image/img/room08.webp"
											alt="" />
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="boxTxt">
					<h3>
						<span>2인룸</span>
					</h3>
					<ul>
						<li>규모 : 39실</li>
						<li>4층 (402~411호), 5층 (505~517호), <br />6층 (602~617호)</li>
					</ul>
				</div> 
			</div>
			<div class="boxList boxList04">
				<div class="boxImg">
					<div class="boxSlider">
						<div class="boxSliderCont">
							<button type="button" class="prevArrow">
								<img
									src="${utcp.ctxPath}/resources/user/image/icon/slide_prev.png"
									alt="" />
							</button>
							<button type="button" class="nextArrow">
								<img
									src="${utcp.ctxPath}/resources/user/image/icon/slide_next.png"
									alt="" />
							</button>
							<div class="slider slider-for">
								<div>
									<div class="boxSliderWrap">
										<img
											src="${utcp.ctxPath}/resources/user/image/img/room09.webp"
											alt="" />
									</div>
								</div>
								<div>
									<div class="boxSliderWrap">
										<img
											src="${utcp.ctxPath}/resources/user/image/img/room0901.webp"
											alt="" />
									</div>
								</div>
								<div>
									<div class="boxSliderWrap">
										<img
											src="${utcp.ctxPath}/resources/user/image/img/room0902.webp"
											alt="" />
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="boxTxt">
					<h3>
						<span>장애인룸</span>
					</h3>
					<ul>
						<li>규모 : 3실</li>
						<li>301호 (4인실/취사 가능)</li>
						<li>310호, 311호 (2인실/취사 불가)</li>
					</ul>
				</div>
			</div>
		</div>
	</div>
	<div class="intro03 introBox">
		<div class="introBoxWrap">
			<h2 class="introTit">8층</h2>
			<div class="boxList boxList01">
				<div class="boxImg">
					<div class="boxSliderWrap">
						<img
							src="${utcp.ctxPath}/resources/user/image/img/fac01.webp"
							alt="" />
					</div>
				</div>
				<div class="boxTxt">
					<h3>
						<span>무인카페</span>
					</h3>
					<ul>
						<li>이용 시간 : 09~21시</li>
					</ul>
				</div>
			</div>
		</div>
		<div class="introBoxWrap">
			<h2 class="introTit">7층</h2>
			<div class="boxList boxList01">
				<div class="boxImg">
					<div class="boxSliderWrap">
						<img src="${utcp.ctxPath}/resources/user/image/img/fac02.webp"
							alt="" />
					</div>
				</div>
				<div class="boxTxt">
					<h3>
						<span>당구장</span>
					</h3>
					<ul>
						<li>이용 시간 : 10~21시 / 프런트 예약</li>
					</ul>
				</div>
			</div>
		</div>
		<div class="introBoxWrap">
			<h2 class="introTit">6층</h2>
			<div class="boxList boxList01">
				<div class="boxImg">
					<div class="boxSliderWrap">
						<img src="${utcp.ctxPath}/resources/user/image/img/fac03.webp"
							alt="" />
					</div>
				</div>
				<div class="boxTxt">
					<h3>
						<span>유아휴게실</span>
					</h3>
					<ul>
						<li>이용 시간 : 09~21시</li>
					</ul>
				</div>
			</div>
		</div>
		<div class="introBoxWrap">
			<h2 class="introTit">5층</h2>
			<div class="boxList boxList01">
				<div class="boxImg">
					<div class="boxSliderWrap">
						<img src="${utcp.ctxPath}/resources/user/image/img/fac04.webp"
							alt="" />
					</div>
				</div>
				<div class="boxTxt">
					<h3>
						<span>공동취사장</span>
					</h3>
					<ul>
						<li>이용 시간 : 08~21시</li>
					</ul>
				</div>
			</div>
		</div>
		<div class="introBoxWrap">
			<h2 class="introTit">4층</h2>
			<div class="boxList boxList01">
				<div class="boxImg">
					<div class="boxSliderWrap">
						<img src="${utcp.ctxPath}/resources/user/image/img/fac05.webp"
							alt="" />
					</div>
				</div>
				<div class="boxTxt">
					<h3>
						<span>코인세탁실</span>
					</h3>
					<ul>
						<li>이용 시간 : 07~21시</li>
					</ul>
				</div>
			</div>
		</div>
		<div class="introBoxWrap">
			<h2 class="introTit">2층</h2>
			<div class="boxList boxList01">
				<div class="boxImg">
					<div class="boxSliderWrap">
						<img src="${utcp.ctxPath}/resources/user/image/img/fac06.webp"
							alt="" />
					</div>
				</div>
				<div class="boxTxt">
					<h3>
						<span>구내식당</span>
					</h3>
					<ul class="boxflex">
						<li>
							<span class="boxlitit">이용 시간 :</span>
							<div>
								<p class="boxlitxt">(조식) 07:30~08:30</p>
								<p class="boxlitxt">(중식) 11:30~13:00</p>
								<p class="boxlitxt">(석식) 17:30~19:00</p>
							</div>
							
						</li>
					</ul>
				</div>
			</div>
		</div>
		<div class="introBoxWrap">
			<h2 class="introTit">1층</h2>
			<div class="boxList boxList01">
				<div class="boxImg">
					<div class="boxSliderWrap">
						<img src="${utcp.ctxPath}/resources/user/image/img/fac07.webp"
							alt="" />
					</div>
				</div>
				<div class="boxTxt">
					<h3>
						<span>카페/편의점</span>
					</h3>
					<ul>
						<li>이용 시간 : 08~18시</li>
					</ul>
				</div>
			</div>
		</div>
		<div class="introBoxWrap">
			<h2 class="introTit">B1</h2>
			<div class="boxList boxList01">
				<div class="boxImg">
					<div class="boxSliderWrap">
						<img src="${utcp.ctxPath}/resources/user/image/img/fac09.webp"
							alt="" />
					</div>
				</div>
				<div class="boxTxt">
					<h3>
						<span>체력단련실</span>
					</h3>
					<ul>
						<li>이용 시간 : 07~22시</li>
					</ul>
				</div>
			</div>
			<div class="boxList boxList02">
				<div class="boxImg">
					<div class="boxSliderWrap">
						<img src="${utcp.ctxPath}/resources/user/image/img/fac10.webp"
							alt="" />
					</div>
				</div>
				<div class="boxTxt">
					<h3>
						<span>레크레이션룸(노래방)</span>
					</h3>
					<ul>
						<li>이용 시간 : 15~22시</li>
					</ul>
				</div>
			</div>
			<div class="boxList boxList03">
				<div class="boxImg">
					<div class="boxSliderWrap">
						<img src="${utcp.ctxPath}/resources/user/image/img/fac11.webp"
							alt="" />
					</div>
				</div>
				<div class="boxTxt">
					<h3>
						<span>탁구대</span>
					</h3>
					<ul>
						<li>이용 시간: 09~22시 / 프런트 예약</li>
					</ul>
				</div>
			</div>
		</div>

	</div>
	<div class="intro04 introBox">
		<div class="introBoxWrap">
			<h2 class="introTit">교육 및 강의시설</h2>
			<div class="boxList boxList01">
				<div class="boxImg">
					<div class="boxSlider"> 
						<div class="boxSliderCont"> 
							<button type="button" class="prevArrow">
								<img
									src="${utcp.ctxPath}/resources/user/image/icon/slide_prev.png"
									alt="" />
							</button>
							<button type="button" class="nextArrow">
								<img
									src="${utcp.ctxPath}/resources/user/image/icon/slide_next.png"
									alt="" />
							</button>
							<div class="slider slider-for">
								<div>
									<div class="boxSliderWrap">
										<img
											src="${utcp.ctxPath}/resources/user/image/img/edufac01.webp"
											alt="" />
									</div>
								</div> 
								<div> 
									<div class="boxSliderWrap">
										<img
											src="${utcp.ctxPath}/resources/user/image/img/edufac0101.webp"
											alt="" />
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="boxTxt">
					<h3>
						<span>대강당</span>
					</h3>
				</div>
			</div>
		</div>
		<div class="introBoxWrap hori">
			<div class="boxList boxList01">
				<div class="boxImg">
					<div class="boxSliderWrap">
						<img src="${utcp.ctxPath}/resources/user/image/img/edufac02.webp"
							alt="" />
					</div>
				</div>
				<div class="boxTxt">
					<h3>
						<span>대강의실 (120석)</span>
					</h3>
				</div>
			</div>
			<div class="boxList boxList02">
				<div class="boxImg">
					<div class="boxSliderWrap">
						<img src="${utcp.ctxPath}/resources/user/image/img/edufac03.webp"
							alt="" />
					</div>
				</div>
				<div class="boxTxt">
					<h3>
						<span>중강의실 (60~80석)/2개소</span>
					</h3>
				</div>
			</div>
		</div>
		<div class="introBoxWrap hori">
			<div class="boxList boxList01">
				<div class="boxImg">
					<div class="boxSliderWrap">
						<img src="${utcp.ctxPath}/resources/user/image/img/edufac04.webp"
							alt="" />
					</div>
				</div>
				<div class="boxTxt">
					<h3>
						<span>스마트강의실 (54석)</span>
					</h3>
				</div>
			</div>
			<div class="boxList boxList02">
				<div class="boxImg">
					<div class="boxSliderWrap">
						<img src="${utcp.ctxPath}/resources/user/image/img/edufac05.webp"
							alt="" />
					</div>
				</div>
				<div class="boxTxt">
					<h3>
						<span>전산강의실 (47석)</span>
					</h3>
				</div>
			</div>
		</div>
		<div class="introBoxWrap hori">
			<div class="boxList boxList01">
				<div class="boxImg">
					<div class="boxSliderWrap">
						<img src="${utcp.ctxPath}/resources/user/image/img/edufac06.webp"
							alt="" />
					</div>
				</div>
				<div class="boxTxt">
					<h3>
						<span>소강의실 (30~40석)/2개소</span>
					</h3>
				</div>
			</div>
			<div class="boxList boxList02">
				<div class="boxImg">
					<div class="boxSliderWrap">
						<img src="${utcp.ctxPath}/resources/user/image/img/edufac07.webp"
							alt="" />
					</div>
				</div>
				<div class="boxTxt">
					<h3>
						<span>분임토의실(6개소)</span>
					</h3>
				</div>
			</div>
		</div>
	</div>
	<div class="intro05 introBox">
		<div class="introBoxWrap">
			<h2 class="introTit">야외 체육시설</h2>
			<div class="boxList boxList01">
				<div class="boxImg">
					<div class="boxSlider">
						<div class="boxSliderCont">
							<button type="button" class="prevArrow">
								<img
									src="${utcp.ctxPath}/resources/user/image/icon/slide_prev.png"
									alt="" />
							</button>
							<button type="button" class="nextArrow">
								<img
									src="${utcp.ctxPath}/resources/user/image/icon/slide_next.png"
									alt="" />
							</button>
							<div class="slider slider-for">
								<div>
									<div class="boxSliderWrap">
										<img
											src="${utcp.ctxPath}/resources/user/image/img/out01.webp"
											alt="" />
									</div>
								</div>
								<div>
									<div class="boxSliderWrap">
										<img
											src="${utcp.ctxPath}/resources/user/image/img/out0102.webp"
											alt="" />
									</div>
								</div>
								<div>
									<div class="boxSliderWrap">
										<img
											src="${utcp.ctxPath}/resources/user/image/img/out0103.webp"
											alt="" />
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="boxTxt">
					<ul>
						<li>축구장, 농구장, 족구장, 테니스장 등 다양한 체육시설을 이용하실 수 있습니다. <br />프런트에서
							장비 등을 대여 받으세요.
						</li>
						<li>이용 시간: 09~20시 / 폭염, 한파, 호우 등의 경우 이용 불가</li>
					</ul>
				</div>
			</div>
		</div>
		<div class="introBoxWrap">
			<h2 class="introTit">야외 바베큐장</h2>
			<div class="boxList boxList02">
				<div class="boxImg">
					<div class="boxSliderWrap">
						<img src="${utcp.ctxPath}/resources/user/image/img/out02.webp"
							alt="" />
					</div>
				</div>
				<div class="boxTxt">
					<ul>
						<li>4~11월 중순 운영</li>
						<li>이용시간: 17~21시 / 셀프BBQ / 프런트 예약</li>
					</ul>
				</div>
			</div>
		</div>
	</div>
</div>
</div>




<%-- <div class="boxList boxList02">
	<div class="boxImg">
		<div class="boxSlider">
			<div class="boxSliderCont">
				<button type="button" class="prevArrow">
					<img src="${utcp.ctxPath}/resources/user/image/icon/slide_prev.png"
						alt="" />
				</button>
				<button type="button" class="nextArrow">
					<img src="${utcp.ctxPath}/resources/user/image/icon/slide_next.png"
						alt="" />
				</button>
				<div class="slider slider-for">
					<div>
						<div class="boxSliderWrap">
							<img src="${utcp.ctxPath}/resources/user/image/img/intro05.png"
								alt="" />
						</div>
					</div>
					<div>
						<div class="boxSliderWrap">
							<img src="${utcp.ctxPath}/resources/user/image/img/intro05.png"
								alt="" />
						</div>
					</div>
					<div>
						<div class="boxSliderWrap">
							<img src="${utcp.ctxPath}/resources/user/image/img/intro05.png"
								alt="" />
						</div>
					</div>
					<div>
						<div class="boxSliderWrap">
							<img src="${utcp.ctxPath}/resources/user/image/img/intro05.png"
								alt="" />
						</div>
					</div>
					<div>
						<div class="boxSliderWrap">
							<img src="${utcp.ctxPath}/resources/user/image/img/intro05.png"
								alt="" />
						</div>
					</div>
				</div>
			</div>

			<div class="slider slider-nav">
				<div>
					<div class="boxSliderWrap">
						<img src="${utcp.ctxPath}/resources/user/image/img/intro05.png"
							alt="" />
					</div>
				</div>
				<div>
					<div class="boxSliderWrap">
						<img src="${utcp.ctxPath}/resources/user/image/img/intro05.png"
							alt="" />
					</div>
				</div>
				<div>
					<div class="boxSliderWrap">
						<img src="${utcp.ctxPath}/resources/user/image/img/intro05.png"
							alt="" />
					</div>
				</div>
				<div>
					<div class="boxSliderWrap">
						<img src="${utcp.ctxPath}/resources/user/image/img/intro05.png"
							alt="" />
					</div>
				</div>
				<div>
					<div class="boxSliderWrap">
						<img src="${utcp.ctxPath}/resources/user/image/img/intro05.png"
							alt="" />
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="boxTxt">
		<h3>
			<span>대강의실 B</span>
		</h3>
		<ul>
			<li>위치 : 교육동 1층</li>
			<li>규모 : 438석, 장애인5석 포함</li>
			<li>시설장비 : 빔프로젝트, 컴퓨터, 앰프, 전자교탁, 전자칠판</li>
		</ul>
	</div>
</div> --%>