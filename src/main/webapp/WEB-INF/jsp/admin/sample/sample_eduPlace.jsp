<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp" %>
<script type="text/javascript">
    $(function(){
    // pickadate //
    var $input = $('.datepicker').pickadate({
    monthsFull: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
    monthsShort: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12'],
    weekdaysFull: ['일요일', '월요일', '화요일', '수요일', '목요일', '금요일', '토요일'],
    weekdaysShort: ['일', '월', '화', '수', '목', '금', '토'],
    format: 'yyyy-mm-dd',
    formatSubmit: 'yyyy-mm-dd',
    today: "오늘",
    clear: "지우기",
    close: "닫기",
    container: '.cont_wrap',
    labelMonthNext: '다음달 넘어가기',
    labelMonthPrev: '이전달 넘어가기',
    labelMonthSelect: '월 선택',
    labelYearSelect: '년도 선택',
    selectYears: 200,
    selectMonths: true,
    //min:true
    });
    // pickadate //
});
</script>

<section class="pd025 pd200 po_re">
	<div class="po_re br5 bs_box">
		<form name="search_form" method="get" action="${utcp.ctxPath}/admin/member/mngrList.do">
       		<input type="hidden" id="page" name="page" value="1">     		
			<div class="tr mgb15">
				<label for="srchColumn" class="sound_only">검색대상선택</label>
				<select id="srchColumn" name="srchColumn" class="btn04 btn_blackl">
					<option value="userId">아이디</option>
					<option value="userNm">성명</option>
				</select>		
				<label for="sch_input" class="sound_only">검색어입력</label>
				<input type="text" id="srchWrd" name="srchWrd" value="" placeholder="검색" class="btn04 btn_blackl tl mgr5">		
				<button class="btn04 btn_black fr">검색</button>
			</div>
		</form>
		<table class="w100 tb01 tc">
			<caption class="sound_only">교육장소 관리 테이블</caption>
			<thead style="background:#f7f8fa;">
				<tr>
					<th>번호</th>
					<th>장소명</th>
					<th>위치</th>
					<th>메모</th>
					<th>현황</th>
					<th>설정</th>
				</tr>
			</thead>	
			<tbody>
				<tr>
					<td>3</td>
					<td>제1강의실</td>
					<td>국립과천과학관 교육동 3층</td>
					<td>&nbsp;</td>
					<td>
						<a href="#timeCal" class="btn04 btn_blue">이용현황</a>
					</td>
					<td>
						<a href="#timePop" class="btn04 btn_green">이용설정</a>
					</td>
				</tr>
				<tr>
					<td>2</td>
					<td>제1강의실</td>
					<td>국립과천과학관 교육동 3층</td>
					<td>&nbsp;</td>
					<td>
						<a href="#timeCal" class="btn04 btn_blue">이용현황</a>
					</td>
					<td>
						<a href="#timePop" class="btn04 btn_green">이용설정</a>
					</td>
				</tr>
				<tr>
					<td>1</td>
					<td>SW 전용강의실 (302)</td>
					<td>국립과천과학관 교육동 3층</td>
					<td>&nbsp;</td>
					<td>
						<a href="#timeCal" class="btn04 btn_blue">이용현황</a>
					</td>
					<td>
						<a href="#timePop" class="btn04 btn_green">이용설정</a>
					</td>
				</tr>
			</tbody>
		</table>
		<div class="fr tc">
			<button class="btn01 btn_greenl" onclick="fn_rgsFrm(); return false;">등록</button>
		</div>					
		
		<!--// paging //-->
		<div class="page">
			<div class="inner cf">
				<div class="page_now"><a href="javascript:;">1</a></div> 
			</div>
		</div>
		<!--// paging //-->
	</div>
</section>



<div class="remodal" data-remodal-id="timePop" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc">
	<button data-remodal-action="close" class="remodal-close" aria-label="Close"></button>
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC">예외일 입력</p>
		</div>
		<div class="modal-body">		
			<div class="tbBox mgt0">
				<table class="tc tb02 w100">
					<caption class="sound_only">예외일 입력 테이블</caption>
					<tbody>
						<tr>
							<th>예외일</th>
							<td class="tl">
								<input type="text" id="eduPeriodBegin" name="eduPeriodBegin" class="datepicker input_calendar ip6 tc"/>
								<button type="button" class="btn04 btn_blue" onclick="">추가</button>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<div class="modal-footer">
			<button onclick="fn_moveLogin(); return false;" class="remodal-confirm btn02 btn_green">설정</button>
			<button data-remodal-action="cancel" class="remodal-cancel btn02 btn_gray">취소</button>
		</div>
	</div>
</div>

<!--// edu_status_pop //-->
<div class="remodal cal" data-remodal-id="timeCal" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc">
	<button data-remodal-action="close" class="remodal-close" aria-label="Close"></button>
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC pdt0">제3강의실 이용 및 예약현황</p>
		</div>
		<div class="modal-body">
			<div id="htmlCal">
				<div class="calTit tc pdt15">
					<a href="javascript:;" onclick="fn_moveMonth('P'); return false;" class="prev"><img src="${utcp.ctxPath}/resources/user/image/btn/btn_left.png" alt="이전달"></a>
					2023년<span> 5월</span>
					<a href="javascript:;" onclick="fn_moveMonth('N'); return false;" class="next"><img src="${utcp.ctxPath}/resources/user/image/btn/btn_right.png" alt="다음달"></a>
				</div>
				
				<div id="calendar" class="rsvCal timeCal">
					<table id="calendar" class="w100">
						<tbody>
							<tr class="weekdays">
								<th>일</th>
								<th>월</th>
								<th>화</th>
								<th>수</th>
								<th>목</th>
								<th>금</th>
								<th>토</th>
							</tr>
							<tr class="days">
								<td>
									<span class="day">30</span>
								</td>
								<td>
									<span class="day">1</span>
								</td>
								<td>
									<span class="day">2</span>
								</td>
								<td>
									<span class="day">3</span>
								</td>
								<td>
									<span class="day">4</span>
								</td>
								<td>
									<span class="day">5</span>
								</td>
								<td>
									<span class="day">6</span>
								</td>
							</tr>
							<tr class="days">
								<td>
									<span class="day">7</span>
								</td>
								<td>
									<span class="day">8</span>
									<a href="javascript:;" class="tmType">11:00</a>
									<a href="javascript:;" class="tmType">14:00</a>
								</td>
								<td>
									<span class="day">9</span>
								</td>
								<td>
									<span class="day">10</span>
								</td>
								<td>
									<span class="day">11</span>
								</td>
								<td>
									<span class="day">12</span>
									<a href="javascript:;" class="tmType">11:00</a>
									<a href="javascript:;" class="tmType">14:00</a>
								</td>
								<td>
									<span class="day">13</span>
								</td>
							</tr>
							<tr class="days">
								<td>
									<span class="day">14</span>
								</td>
								<td>
									<span class="day">15</span>
									<a href="javascript:;" class="tmType">11:00</a>
									<a href="javascript:;" class="tmType">14:00</a>
								</td>
								<td>
									<span class="day">16</span>
								</td>
								<td>
									<span class="day">17</span>
								</td>
								<td>
									<span class="day">18</span>
								</td>
								<td>
									<span class="day">19</span>
								</td>
								<td>
									<span class="day">20</span>
								</td>
							</tr>
							<tr class="days">
								<td>
									<span class="day">21</span>
								</td>
								<td>
									<span class="day">22</span>
									<a href="javascript:;" class="tmType">11:00</a>
									<a href="javascript:;" class="tmType">14:00</a>
								</td>
								<td>
									<span class="day">23</span>
								</td>
								<td>
									<span class="day">24</span>
								</td>
								<td>
									<span class="day">25</span>
								</td>
								<td>
									<span class="day">26</span>
									<a href="javascript:;" class="tmType">11:00</a>
									<a href="javascript:;" class="tmType">14:00</a>
								</td>
								<td>
									<span class="day">27</span>
								</td>
							</tr>
							<tr class="days">
								<td>
									<span class="day">28</span>
								</td>
								<td>
									<span class="day">29</span>
									<a href="javascript:;" class="tmType">11:00</a>
									<a href="javascript:;" class="tmType">14:00</a>
								</td>
								<td>
									<span class="day">30</span>
								</td>
								<td>
									<span class="day">31</span>
								</td>
								<td>
									<span class="day">1</span>
								</td>
								<td>
									<span class="day">2</span>
								</td>
								<td>
									<span class="day">3</span>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>

		</div>
	</div>
</div>
<!--// edu_status_pop //-->  
        