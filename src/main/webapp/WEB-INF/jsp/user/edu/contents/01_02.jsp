<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>



<div class="sub_txt">
	<span class=""><img
		src="${utcp.ctxPath}/resources/user/image/icon/icon_subtitle.png"
		alt="">월별교육일정</span>
</div>

<div class="po_re br5 bs_box cf">
	<div id="htmlCal">








		<div class="calTit tc">
			<a href="javascript:;" onclick="fn_moveMonth2('P'); return false;"
				class="prev"> <img src="/resources/user/image/btn/btn_left.png"
				alt="이전달"></a> 2025년<span> 09월</span> <a href="javascript:;"
				onclick="fn_moveMonth2('N'); return false;" class="next"> <img
				src="/resources/user/image/btn/btn_right.png" alt="다음달"></a>
		</div>

		<div id="calendar" class="rsvCal">
			<table id="calendar" class="w100 tb">
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
						<td class="day other-month"><span class="day">31</span></td>
						<td class="day "><span class="day">1</span> <a
							href="" class="tmType"><span class="classtype">오프라인</span>연금 투자 1차수</a></td>
						<td class="day "><span class="day">2</span></td>
						<td class="day "><span class="day">3</span></td>
						<td class="day "><span class="day">4</span></td>
						<td class="day "><span class="day">5</span></td>
						<td class="day "><span class="day">6</span></td>
					</tr>
					<tr class="days">
						<td class="day "><span class="day">7</span></td>
						<td class="day "><span class="day">8</span></td>
						<td class="day "><span class="day">9</span></td>
						<td class="day "><span class="day">10</span></td>
						<td class="day "><span class="day">11</span></td>
						<td class="day "><span class="day">12</span></td>
						<td class="day "><span class="day">13</span></td>
					</tr>
					<tr class="days">
						<td class="day "><span class="day">14</span></td>
						<td class="day "><span class="day">15</span> <a
							href="" class="tmType">
							<span class="classtype">오프라인</span>재테크교육 2차수
						</a></td>
						<td class="day "><span class="day">16</span></td>
						<td class="day "><span class="day">17</span></td>
						<td class="day "><span class="day">18</span></td>
						<td class="day "><span class="day">19</span></td>
						<td class="day "><span class="day">20</span></td>
					</tr>
					<tr class="days">
						<td class="day "><span class="day">21</span></td>
						<td class="day "><span class="day">22</span></td>
						<td class="day "><span class="day">23</span></td>
						<td class="day "><span class="day">24</span></td>
						<td class="day "><span class="day">25</span></td>
						<td class="day "><span class="day">26</span></td>
						<td class="day "><span class="day">27</span></td>
					</tr>
					<tr class="days">
						<td class="day "><span class="day">28</span></td>
						<td class="day "><span class="day">29</span></td>
						<td class="day "><span class="day">30</span></td>
						<td class="day other-month"><span class="day">1</span></td>
						<td class="day other-month"><span class="day">2</span></td>
						<td class="day other-month"><span class="day">3</span></td>
						<td class="day other-month"><span class="day">4</span></td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
</div>
