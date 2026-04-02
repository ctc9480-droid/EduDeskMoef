<%@page import="com.educare.edu.member.service.SessionUserInfoHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<div id="vm-mt8">
<div class="tableRespon">
	<table class="tb07 w100">
		<caption class="sound_only">시험 - 순서, 시험명, 관련과목, 능력단위요소, 유형, 시험시간, 시간, 문제수, 상태 순으로 정보를 제공합니다.</caption>
		<thead>
			<tr>
				<th width="50px">No</th>
				<th>설문기간</th>
				<th width="">설문지명</th>
				<th width="">작성일</th>
				<th width="" style="border-right: 0px;">상태</th>
			</tr>
		</thead>
		<tbody>
			<tr v-for="(o,i) in feList">
				<td>{{i+1}}</td>
				<td class="">{{o.startDe|fltDate2Str('YYYY-MM-DD HH:mm')}} ~ {{o.endDe|fltDate2Str('YYYY-MM-DD HH:mm')}}</td>
				<td>{{o.title}}</td>
				<td>
				<template v-if="o.feResultRegDe">
				{{o.feResultRegDe|fltDate2Str('YYYY-MM-DD HH:mm')}}
				</template>
				</td>
				<td style="border-right: 0px;">
				<template v-if="o.feResultState != 2">
					<a  href="#none" @click="openPopup(o.feSeq)" class="btn04 btn_orange">설문하기</a>
				</template>
				<template v-else-if="o.feResultState == 2">
					<a  href="#none"  class="btn04 btn_blue surb_comp">설문완료</a>
				</template>
				</td>
			</tr>
			<tr v-if="feList.length == 0">
				<td colspan="10">게시물이 없습니다.</td>
			</tr>
		</tbody>
	</table>
</div>
</div>
<script>
	var vm_mt8 = new Vue({
		el: '#vm-mt8',
		data: {
			feList: [],
		},
		mounted: function(){
			//console.log('mount');
			this.getFeList();
		},
		methods: {
			getFeList: function(){
				var _this = this;
				var eduSeq= '${param.eduSeq}';
				$.ajax({
					data: {eduSeq: eduSeq,pageNo: 0},
					url: '${utcp.ctxPath}/user/feedbackEdu/getFeedbackEduList.ajax',
					success: function(r){
						if(r.result == 1){
							_this.feList = r.data.feList;
						}
					}
				});
			},
			openPopup: function(feSeq){
				var feedbackPopup = window.open('${utcp.ctxPath}/user/feedback/feedbackPopup.do?eduSeq=${param.eduSeq}&feSeq='+feSeq,'feedbackPopup','width=980,height=720resizable=no, scrollbars=yes');
			}
		},
	});
</script>
