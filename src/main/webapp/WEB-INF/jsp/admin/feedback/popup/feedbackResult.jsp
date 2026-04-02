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
						<strong>{{fb.title}}</strong> (객관식 {{fb.qtType1Cnt}}개/주관식 {{fb.qtType0Cnt}}개)
					</h3>
					<ul>
						<li>등록자: {{fb.regNm}}</li>
						<template v-if="fb.feSeq > 0">
						<li>설문일시 : {{fe.startDe|fltDate2Str('YYYY.MM.DD HH:mm')}} ~ {{fe.endDe|fltDate2Str('YYYY.MM.DD HH:mm')}}</li>
						</template>
						<!--
						<li>담당교사 : 홍길동</li>-->
						<li></li>
					</ul>
				</div>

				<div>

					<div class="w100 tr fbTxt vm mgt20">
						<span class="fl dp_ib font_18">총 설문참여 : <strong class="fw_400 fc_red">{{stdntFeedbackCnt}}</strong> / <strong class="fw_400">{{stdntCnt}}</strong>명
						</span>
						<button class="btn01 btn_green mgb15" onclick="location.href='${utcp.ctxPath}/admin/excel/feedbackExcel.do?eduSeq=${param.eduSeq}&feSeq=${param.feSeq }';">Excel 다운로드</button>
					</div>

					<div>
						<template v-for="(o,i) in qtList"> 
						<template v-if="o.qt.qtType!=0"> 
						<table class="fbtb tl w100 mgb30">
							<thead>
								<tr>
									<th colspan="3" class="tl pd10">{{o.qt.question}}</th>
								</tr>
							</thead>
							<tbody>
								<template v-for="(o2,i2) in o.chList">
								<tr>
									<td class="tl">{{o2.choice}}</td>
									<td class="tbGray">{{o2.asCnt}} <span class="fc_orange tr mgl20">({{o2.asCnt|fltPercentage(o.qt.qtAsCnt)}})</span></td>
									<td class="tbGray graphtd"><div class="bg_orange" :style="{width:fltPercentage0(o2.asCnt,o.qt.qtAsCnt)}">&nbsp;</div></td>
								</tr>
								</template>
							</tbody>
						</table>
						</template>
						<template v-else>
						<table class="fbtb tl w100 mgb30">
                            <tbody>
                                
                                <tr>
                                    <th colspan="3" class="tl pd10">{{o.qt.question}}</th>
                                </tr>
                                <template v-for="o2 in o.asList">
                                <tr>
                                    <td class="tl">{{o2.answer}}</td>
                                </tr>
								</template>
                                
                            </tbody>
                        </table>
						</template>
						</template>
					</div>
				</div>
			</div>
		</div>
	</section>
</div>

<script>
	var vm_eduFeedback = new Vue(
			{
				el : '#vm-eduFeedback',
				data : {
					stdntCnt : 0,
					stdntFeedbackCnt : 0,
					fb : {},
					fe: {},
					qtList : [],
				},
				methods: {
					fltPercentage0: function(value,total){
						if(total == 0){
							return '0%';
						}
						console.log(value);
						console.log(total);
						return this.$options.filters.fltPercentage(value, total);
					}
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
									vm_eduFeedback.fe = r.fe;
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
