<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<section class="pd025 pd200 po_re">
	<div class="po_re br5 bs_box">
		<div class="fbCon" id="vm-eduFeedback">
			<div>

				<div class="tr fbTxt">
					<span class="fl dp_ib font_18">총 설문참가 : <strong class="fw_400 fc_red">{{answerCnt}}</strong> 명
					</span>
				</div>

				<div>
					<template v-for="(o,i) in feedbackInfo.qtList"> <template v-if="o.qt.qtType==1||o.qt.qtType==2||o.qt.qtType==3">
					<table class="fbtb tl w100 mgb30">
						<thead>
							<tr>
								<th colspan="3" class="tl pd10">{{o.qt.question}}</th>
							</tr>
						</thead>
						<tbody>
							<tr v-for="(o2,i2) in o.chList">
								<td class="tl">{{i2+1}}. {{o2.choice}}</td>
								<td class="tbGray">{{o2.asCnt}} <span class="fc_orange tr mgl20">({{(o2.asCnt/o.qt.qtAsCnt*100).toFixed(1)}}%)</span></td>
								<td class="tbGray graphtd"><div class="bg_orange" :style="'width: '+(o2.asCnt/o.qt.qtAsCnt*100).toFixed(1)+'%;'">&nbsp;</div></td>
							</tr>
						</tbody>
					</table>
					</template> <template v-else>
					<table class="fbtb tl w100 mgb30">
						<tbody>
							<tr>
								<th colspan="3" class="tl pd10">{{o.qt.question}}</th>
							</tr>
							<tr v-for="(o2,i) in o.asList">
								<td class="tl">{{o2.answer}}</td>
							</tr>
							<tr v-if="!o.asList.length">
								<td>-</td>
							</tr>

						</tbody>
					</table>
					</template> </template>
				</div>
			</div>
		</div>
	</div>
</section>
<input type="hidden" name="idx" id="idx" value="${param.idx }" />
<script>
	var vm_eduFeedback = new Vue({
		el : '#vm-eduFeedback',
		data : {
			answerCnt : 0,
			feedbackInfo : {
				fb : {}
			},
		},
	});
	function fn_openEduFeedback() {
		$.ajax({
			url : '${utcp.ctxPath}/admin/ajax/fbOpenResult.json',
			data : {
				idx : $('#idx').val()
			},
			success : function(r) {
				//console.log(r);
				vm_eduFeedback.answerCnt = r.data.answerCnt;
				vm_eduFeedback.feedbackInfo = r.data.feedbackInfo;
				//console.log(vm_eduFeedback.feedbackInfo);
			}
		});
	}
	function fn_deleteEduFeedback() {
		$
				.ajax({
					url : '${utcp.ctxPath}/admin/ajax/deleteEduFeedback.json',
					data : {
						eduSeq : $('#eduSeq').val()
					},
					success : function(r) {
						fn_tab(7);
					}
				});
	}
	fn_openEduFeedback();
</script>