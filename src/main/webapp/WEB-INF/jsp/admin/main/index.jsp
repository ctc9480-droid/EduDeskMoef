<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<section class="pd025 pd200 po_re mgt20">

	<div class="po_re br5 bs_box cf">
		<div class="dashboard-stats front-dashboard cf">

			<div class="cont">
				<a href="#">
					<div class="white-box">
						<div class="row cf">
							<div class="col-xs-3 fl">
								<div>
									<span class="bg-info-gradient"><i class="fas fa-clock"></i></span>
								</div>
							</div>
							<div class="col-xs-9 tr fr">
								<span class="widget-title"> 올해 총 교육</span><br> <span class="counter" id="area-total-1"> </span>
							</div>
						</div>
					</div>
				</a>
			</div>

			<div class="cont">
				<a href="#">
					<div class="white-box">
						<div class="row cf">
							<div class="col-xs-3 fl">
								<div>
									<span class="bg-warning-gradient"><i class="fas fa-exclamation-triangle"></i></span>
								</div>
							</div>
							<div class="col-xs-9 tr fr">
								<span class="widget-title"> 올해 총 신청자</span><br> <span class="counter" id="area-total-2">4</span>
							</div>
						</div>
					</div>
				</a>
			</div>

			<div class="cont">
				<a href="#">
					<div class="white-box">
						<div class="row cf">
							<div class="col-xs-3 fl">
								<div>
									<span class="bg-danger-gradient"><i class="fas fa-check-square"></i></span>
								</div>
							</div>
							<div class="col-xs-9 tr fr">
								<span class="widget-title"> 올해 총 수료자</span><br> <span class="counter" id="area-total-3">4</span>
							</div>
						</div>
					</div>
				</a>
			</div>

			<div class="cont">
				<a href="#">
					<div class="white-box">
						<div class="row cf">
							<div class="col-xs-3 fl">
								<div>
									<span class="bg-success-gradient"><i class="fab fa-buffer"></i></span>
								</div>
							</div>
							<div class="col-xs-9 tr fr">
								<span class="widget-title"> 진행중 교육</span><br> <span class="counter" id="area-total-4">4</span>
							</div>
						</div>
					</div>
				</a>
			</div>
			<div class="cont">
				<a href="#">
					<div class="white-box">
						<div class="row cf">
							<div class="col-xs-3 fl">
								<div>
									<span class="bg-pink-gradient"><i class="fab fa-buffer"></i></span>
								</div>
							</div>
							<div class="col-xs-9 tr fr">
								<span class="widget-title"> 접수중 교육</span><br> <span class="counter" id="area-total-5">4</span>
							</div>
						</div>
					</div>
				</a>
			</div>
			<div class="cont">
				<a href="#">
					<div class="white-box">
						<div class="row cf">
							<div class="col-xs-3 fl">
								<div>
									<span class="bg-pur-gradient"><i class="fab fa-buffer"></i></span>
								</div>
							</div>
							<div class="col-xs-9 tr fr">
								<span class="widget-title"> 오늘 신청자</span><br> <span class="counter" id="area-total-6">4</span>
							</div>
						</div>
					</div>
				</a>
			</div>
		</div>
	</div>

	<div class="po_re br5 bs_box cf">

		<!--// main_con1 //-->
		<div class="main_con main_con1">
			<h3 class="font_22 fw_500 pdl15 pdt20">
				<i class="fas fa-chevron-circle-right font_22 pdr5"></i> 오늘 교육신청 현황
			</h3>

			<div class="tbBox">
				<table class="tc w100 tb">
					<thead bgcolor="#f7f8fa">
						<tr>
							<th>교육명</th>
							<th>성명</th>
							<th>아이디</th>
							<th>소속</th>
							<th>신청상태</th>
						</tr>
					</thead>
					<tbody>
						<c:choose>
							<c:when test='${rceptList != null && fn:length(rceptList) > 0}'>
								<c:forEach var="rcept" items="${rceptList}" varStatus="stat">
									<tr>
										<td><a href="javascript:window.open('${utcp.ctxPath }/admin/edu/popup/lctreView.do?eduSeq=${rcept.eduSeq }','lctrePop','scrollbar=n,width=1024,height=840');">
										${rcept.etc}</a></td>
										<td>${rcept.userNm}</td>
										<td>${rcept.loginId}</td>
										<td>${rcept.belong}</td>
										<c:choose>
											<c:when test="${rcept.state == 2}">
												<td>승인</td>
											</c:when>
											<c:when test="${rcept.state == 1}">
												<td class="fc_blue">예약</td>
											</c:when>
											<c:otherwise>
												<td class="fc_orange">취소</td>
											</c:otherwise>
										</c:choose>
									</tr>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<tr>
									<td colspan="5" class="h200">데이터가 없습니다.</td>
								</tr>
							</c:otherwise>
						</c:choose>
					</tbody>
				</table>
			</div>
		</div>
		<!--// main_con1 //-->

		<div class="main_con main_con2">
			<h3 class="font_22 fw_500 pdl15 pdt20 mgr15">
				<i class="fas fa-chevron-circle-right font_22 pdr5"></i> 월별 통계
			</h3>
			<div class="tbBox">
				<canvas id="area-chart-1"></canvas>
			</div>
		</div>
	</div>
</section>

<script src="${utcp.ctxPath}/resources/plugins/chartjs/chart.min.js"></script>
<script>
	fn_stat_month();
	fn_stat_total();
	function fn_stat_total() {
		$.ajax({
			url : '${utcp.ctxPath}/admin/ajax/statTotal.json',
			success : function(r) {
				console.log(r);
				$('#area-total-1').text(r.datas.totalEduCnt);
				$('#area-total-2').text(r.datas.totalRceptStdntCnt);
				$('#area-total-3').text(r.datas.totalComplStdntCnt);
				$('#area-total-4').text(r.datas.playEduCnt);
				$('#area-total-5').text(r.datas.rceptEduCnt);
				$('#area-total-6').text(r.datas.nowRceptStdntCnt);
			},
		});
	}
	function fn_stat_month() {
		$.ajax({
			url : '${utcp.ctxPath}/admin/ajax/statMonth.json',
			success : function(r) {
				console.log(r);
				var list = r.datas.list;
				var labels = [];
				var data1 = [];
				var data2 = [];
				for (i in list) {
					var o = list[i];
					labels.push(o.year + '/' + o.month);
					data1.push(o.cnt);
					data2.push(o.cnt2);
				}
				setChart01(labels, data1, data2);
			},
		});
	}
	function setChart01(labels, data1, data2) {
		var color = Chart.helpers.color;
		var barChartData = {
			//labels: ['January', 'February', 'March', 'April', 'May', 'June', 'July'],
			labels : labels,
			datasets : [
					{
						label : '신청자 수',
						borderColor: ['rgba(255,99,132,1)',],
					    backgroundColor: ['rgba(255, 99, 132, 0.2)',],
						borderWidth : 1,
						data : data1
					},
					{
						label : '수료자 수',
						borderColor: ['rgba(133,117,255,1)',],
					    backgroundColor: ['rgba(133,117,255, 0.2)',],
						borderWidth : 1,
						data : data2
					} ]

		};
		var ctx = document.getElementById('area-chart-1').getContext('2d');
		window.myBar = new Chart(ctx, {
			type : 'bar',
			data : barChartData,
			options : {
				responsive : true,
				maintainAspectRatio : false,
				legend : {
					position : 'top',
				},
				title : {
					display : false,
					text : '월별 통계'
				},
			    scales: {
			    	y: {
			            beginAtZero: true,
			            ticks: {
			                stepSize: 1,
			                callback: function(value) {
			                    return Number.isInteger(value) ? value : '';
			                }
			            }
			        }
			    }
			}
		});
	}
</script>