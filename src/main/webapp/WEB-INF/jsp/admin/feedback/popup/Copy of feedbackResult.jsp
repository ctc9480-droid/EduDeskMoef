<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<script src="${utcp.ctxPath}/resources/plugins/chartjs/chart.min.js"></script>
<script src="${utcp.ctxPath}/resources/plugins/chartjs/Chart.bundle.min.js"></script>
<script src="${utcp.ctxPath}/resources/plugins/chartjs/chartjs-plugin-labels.js"></script>

<div id="wrapper" class="cf">
	<section class="pd20 po_re">
		<div class="po_re br5 bs_box cf">
			<div class="fbCon mg0" id="vm-eduFeedback">
				<div class="fbConTit cf">
                        <h3>
                            <strong>단체교육 만족도조사 공통01</strong>
                            (객관식 5개/주관식 1개)
                        </h3>
                        <ul>
                            <li>등록자: 관리자1</li>
                            <li>설문일시 : 2024.05.01 17:00~</li>
                            <!--<li>담당교사 : 홍길동</li>-->
                            <li>
                                작성
                                <span class="time dp_ib fc_red">12</span>명 /
                                미작성 13명
                            </li>
                        </ul>
                    </div>
			
				<div v-if="fb">

					<div class="w100 tr fbTxt vm mgt20">
						<span class="fl dp_ib font_18">총 설문참가 : <strong class="fw_400 fc_red">{{stdntFeedbackCnt}}</strong> / <strong class="fw_400">{{stdntCnt}}</strong>명
						</span>
						<button class="btn01 btn_green mgb15" onclick="location.href='${utcp.ctxPath}/admin/excel/feedbackExcel.do?eduSeq=${param.eduSeq}';">Excel 다운로드</button>
					</div>

					<div>
						<comp-table3-edu :fb="fb" :list="qtList" :answer_cnt="stdntFeedbackCnt"></comp-table3-edu>
					</div>
				</div>
			</div>
		</div>
	</section>
</div>

<script type="text/x-template" id="tpl-table3-edu">
<table class="fbtb tl w100 mgb30">
	<caption class="sound_only"> 만족도조사 테이블</caption>
	
	<tbody>
		<template v-for="(o,i) in list"> 
		<template v-if="o.qt.qtType!=0"> 
			<template v-for="(o2,i2) in o.chList">
			<tr>
				<td v-if="o2.rowCnt>0" :rowspan="o2.rowCnt">{{o.qt.qtDiv}}</td>
				<td v-if="o2.rowCnt2>0" :rowspan="o2.rowCnt2">{{o.qt.question}}</td>
				<td :id="'qt-'+o.qt.qtIdx+'-ch-'+o2.chIdx" colspan="" class="">{{o2.choice}}</td>
				<td  colspan="" class="">
				<span :id="'as-'+o.qt.qtIdx+'-ch-'+o2.chIdx">{{o2.asCnt}}</span>
				 / <span style="">{{o2.asCnt|fltPercentage(o.qt.qtAsCnt)}}
				</td>
<%--
				<td colspan="" class=""><div class="bg_orange" :style="'width: '+(o2.asCnt/o.qt.qtAsCnt*100).toFixed(1)+'%;'">&nbsp;</div></td>
--%>				
				<td v-if="o2.rowCnt2>0" :rowspan="o2.rowCnt2">
<%--
				<comp-chartjs v-model="o"></comp-chartjs>
				<div v-chartle class="legend-div legendDiv w40 fl tl"></div>
--%>
				</td>
				</tr>
			</template> 
		</template> 
		<template v-else>
			<tr>
				<td v-if="o.rowCnt>0" :rowspan="o.rowCnt">{{o.qt.qtDiv}}</td>
				<td>{{o.qt.question}}</td>
				<td colspan="" class="">
				<template v-for="o2 in o.asList">
				{{o2.answer}}<br/>
				</template>
				</td>
				<td colspan="" class=""></td>
				<td colspan="" class=""></td>
			</tr>
		</template> 
		</template>
	</tbody>
</table>

</script>

<script>
	Vue.directive('chartjs', {
		inserted : function(el, binding, vNode) {
			var qt = vNode.context.value;
			//console.log(qt);
			var chart_labels = [];
			for ( var i in qt.chList) {
				chart_labels.push(qt.chList[i].choice);
			}
			var chart_datas = [];
			for ( var i in qt.chList) {
				chart_datas.push(qt.chList[i].asCnt);
			}
			var ctx = el.getContext('2d');
			var myChart = new Chart(ctx, {
				type : 'pie',
				data : {
					labels : chart_labels,
					datasets : [ {
						label : '# of Votes',
						data : chart_datas,
						backgroundColor : [ "#dd3711", "#ff9700", "#ffd200",
								"#0f9719", "#35c0cd", "#3566cd", "#5935cd",
								"#c435cd", "#cd358b", "#cd5535" ],
					} ]
				},
				options : {
					//maintainAspectRatio: false,
					//aspectRatio:2,
					//height : 200;
					//width : 150,
					responsive : false,
					legend : {
						display : false,
					//position:'right',
					},
					legendCallback : customLegend,
					plugins : {
						labels : {
							fontColor : '#fff',
						},
					},
				}

			});
			$(el).next().html(myChart.generateLegend());
			//el.innerHTML = myChart.generateLegend();
		}
	});
	Vue.component('comp-chartjs', {
		template : '<canvas v-chartjs class="fl"></canvas>',
		props : [ 'value' ],
	});
	Vue.component('comp-table3-edu', {
		template : '#tpl-table3-edu',
		props : [ 'fb', 'list', 'answer_cnt' ],
	});
	var vm_eduFeedback = new Vue(
			{
				el : '#vm-eduFeedback',
				data : {
					stdntCnt : 0,
					stdntFeedbackCnt : 0,
					fb : {},
					qtList : [],
				},
				mounted : function() {
					$
							.ajax({
								url : '${utcp.ctxPath}/admin/ajax/eduFeedbackInfo.json',
								data : {
									eduSeq : '${param.eduSeq}',
									feSeq : '${param.feSeq}',
								},
								success : function(r) {
									//console.log(r);
									vm_eduFeedback.stdntCnt = r.stdntCnt;
									vm_eduFeedback.stdntFeedbackCnt = r.stdntFeedbackCnt;
									//vm_eduFeedback.feedbackInfo = r.feedbackInfo;
									//console.log(vm_eduFeedback.feedbackInfo);
									vm_eduFeedback.fb = r.feedbackInfo.fb;
									vm_eduFeedback.qtList = r.feedbackInfo.qtList;

									//rowspan 설정
									//console.log(tmp);
									var tmp = vm_eduFeedback.qtList;
									for (var i = 0; i < tmp.length; i++) {
										var qtObj = tmp[i];

										tmp[i].rowCnt = 0;
										var isFirst = false;
										if (i == 0
												|| qtObj.qt.qtDiv != tmp[i - 1].qt.qtDiv) {
											isFirst = true;
										}
										if (isFirst) {
											for (var i2 = i; i2 < tmp.length; i2++) {
												if (tmp[i].qt.qtDiv == tmp[i2].qt.qtDiv) {
													tmp[i].rowCnt += tmp[i2].chList.length;
													if (tmp[i2].qt.qtType == 0) {
														tmp[i].rowCnt++;
													}
												}

											}
										}

										//rowCnt2
										if (tmp[i].chList.length > 0) {
											tmp[i].chList[0].rowCnt = tmp[i].rowCnt;
										}
										for (var i2 = 0; i2 < tmp[i].chList.length; i2++) {
											if (i2 == 0) {
												tmp[i].chList[i2].rowCnt2 = tmp[i].chList.length;
											}
										}
									}

								}
							});
				},
			});
</script>

<script>
	function customLegend(chart) {
		var ul = document.createElement('ul');
		var color = chart.data.datasets[0].backgroundColor;

		chart.data.labels
				.forEach(function(label, index) {
					ul.innerHTML += '<li><span style="background-color: '+color[index]+'; display: inline-block; width: 30px; height: 10px;"></span> '
							+ label + '</li>';
				});

		return ul.outerHTML;
	};
</script>
