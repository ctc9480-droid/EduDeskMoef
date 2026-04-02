<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<div id="vm-4">
	<span class="tb_text"> 수강생 <strong class="fc_red" id="totalTab4">{{page.totalRecordCount}}</strong>
		명
	</span> <input type="hidden" id="tab4Page" value="1" />
	<div class="dp_ib fr tr mgb15">
		<label for="tab4Row" class="sound_only">리스트줄선택</label> <select
			id="tab4Row" class="btn04 btn_blackl">
			<option value="10">10줄</option>
			<option value="50">50줄</option>
			<option value="100">100줄</option>
			<option value="150">150줄</option>
		</select> <label for="tab4SrchColumn" class="sound_only">검색대상선택</label> <select
			id="tab4SrchColumn" class="btn04 btn_blackl">
			<option value="">선택</option>
			<option value="userNm">성명</option>
			<option value="belong">소속</option>
		</select> <label for="tab4SrchWrd" class="sound_only">검색어입력</label> <input
			type="text" id="tab4SrchWrd" placeholder="검색"
			class="btn04 btn_blackl tl mgr5" />

		<button class="btn04 btn_black fr" onclick="fn_htmlWrite('4');">검색</button>
	</div>

	<table width="100%" class="tb01 tc">
		<caption class="sound_only">이수내역테이블</caption>
		<thead bgcolor="#f7f8fa">
			<tr>
				<th>NO</th>
				<th>성명</th>
				<th>휴대폰번호</th>
				<th>설문조사</th>
<!-- 				<th>종류</th> -->
<!-- 				<th>증서</th> -->
				<th>수료증 발급일</th>
<!-- 				<th>고유번호</th> -->
				<th>입교증</th>
				<th>수료증</th>
				<th>영수증</th>
			</tr>
		</thead>
		<tbody>
			<template v-for="(o,i) in list">
			<tr>
				<td rowspan="">{{page.totalRecordCount - (((page.currentPageNo-1)*page.recordCountPerPage) + i)}}</td>
				<td>{{o.stdnt.userNm}}</td>
				<td >{{o.stdnt.mobile}}</td>
				<td >
				<template v-if="o.stdnt.surveyYn == 'Y'">
					<a :href="'javascript:fn_serveyView('+o.stdnt.userId+');'">완료</a>
				</template><template v-else>
					Ⓧ
				</template>
				</td>
<!-- 				<td >수료증</td> -->
<!-- 				<td>{{o.stdnt.passCertIssue == 'Y'?'발급가능':''}}</td> -->
				<td>{{o.stdnt.certDe|fltDate2Str('YYYY-MM-DD')}}</td>
<!-- 				<td><a href="#none" @click="certView(o.stdnt.userId,0,1)">{{o.stdnt.passCertNum}}</a></td> -->
				<td>
					<a href="#none" @click="open_myRceptPop(o.stdnt.eduSeq, o.stdnt.rceptSeq, o.stdnt.userId)">
				    	<img :src="`${ctxPath}/resources/user/image/icon/manage_print.png`" />
					</a>
				</td>
				<td>
					<a href="#none" v-if="o.stdnt.passYn == 'Y'" @click="certView(o.stdnt.userId, 0, 1)">
				    	<img :src="`${ctxPath}/resources/user/image/icon/manage_print.png`" />
					</a>
				</td>
				<td>
					<a href="#none" v-if="o.stdnt.depositYn == 'Y'" @click="alert('준비 중입니다.')">
				    	<img :src="`${ctxPath}/resources/user/image/icon/manage_print.png`" />
					</a>
				</td>
			</tr>
			</template>
			<template v-if="!list.length">
			<tr>
				<td colspan="10">데이터가 없습니다.</td>
			</tr>
			</template>
		</tbody>
	</table>
	
	<!--// paging //-->
	<div class="page" >
		<div class="inner cf">
			<template v-if="page.currentPageNo != 1">
			<div class="page_prev0">
				<a href="javascript:;" :onclick="'fn_pageTab4('+(page.currentPageNo - 1)+');'">&lt; 이전</a>
			</div>
			</template>
			<template v-for="o in (page.firstPageNoOnPageList , page.lastPageNoOnPageList)"> <template v-if="o == page.currentPageNo">
			<div class="page_now">
				<a>{{o}}</a>
			</div>
			</template> <template v-else>
			<div class="page_nomal">
				<a href="javascript:;" :onclick="'fn_pageTab4('+o+');'">{{o}}</a>
			</div>
			</template> </template>
			<template v-if="page.currentPageNo != page.totalPageCount && page.totalPageCount > 0">
			<div class="page_next0">
				<a href="javascript:;" :onclick="'fn_pageTab4('+(page.currentPageNo + 1) + ');'">다음 &gt;</a>
			</div>
			</template>
		</div>
	</div>
	<!--// paging //-->
</div>
<script>
function fn_pageTab4(page){
	$("#tab4Page").val(page);
	fn_htmlWrite("4");
}
</script>
<script>
var vm_4 = new Vue({
	el: '#vm-4',
	data: {
		list: [],
		page: {},
		totalCnt: 0,
	},
	methods: {
		callList: function(){
			var _this = this;
			var page = $("#tab4Page").val();
			var row = $("#tab4Row").val();
			var srchWrd = $("#tab4SrchWrd").val();
			var srchColumn = $("#tab4SrchColumn").val();
			
			$.ajax({
				//url: "${utcp.ctxPath}/user/edu/lectureStdntList.json",
				url: "${utcp.ctxPath}/user/edu/lectureStdntCertList.ajax",
				data: {
					"page" : page,
					"row" : row,
					"srchWrd" : srchWrd,
					"srchColumn" : srchColumn,
					"eduSeq" : $('#eduSeq').val()
				},
				success: function(r) {
					if(r.result == 1){
						_this.list = r.data.list;
						_this.page = r.data.page;
					}
				},
			});
		},
		certView: function(userId,subSeq,mode){
			fn_certView(userId,subSeq,mode);
		},
		setCertSub: function(eduSeq,userId,subSeq,certMode,state){
			var _this = this;
			$.ajax({
				data: {eduSeq: eduSeq, userId: userId, subSeq: subSeq, certMode: certMode, state: state},
				url: '${utcp.ctxPath}/admin/ajax/setCertSub.ajax',
				success: function(r){
					if(r.result == 1){
						_this.callList();
					}else{
						alert(r.msg);	
					}
				}
			});
		},
	},
});
</script>
<script>
function fn_certView(userId, subSeq,mode){
	var popupWidth = 700;
	var popupHeight = 1000;

	var popupX = (window.screen.width / 2) - (popupWidth / 2);
	var popupY= (window.screen.height / 2) - (popupHeight / 2);
	
	var url = "${utcp.ctxPath}/user/edu/certView.do?eduSeq=${lctre.eduSeq}&userId=" + userId + "&subSeq=" + subSeq+"&mode="+mode;
	
	window.open(url, "증빙서류", 'status=no, height=' + popupHeight  + ', width=' + popupWidth  + ', left='+ popupX + ', top='+ popupY);
}
</script>