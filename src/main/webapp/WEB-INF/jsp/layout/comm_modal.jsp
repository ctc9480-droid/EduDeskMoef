<%@page import="com.educare.component.VarComponent"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.educare.edu.member.service.SessionUserInfoHelper"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<!-- 소속검색모달 -->
<div class="remodal" id="vm-orgInfo" data-remodal-id="md-orgInfo" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc">
	<button data-remodal-action="close" class="remodal-close" aria-label="Close"></button>
	<div class="modal-content mypage-modal">
		<div class="modal-header">
			<p class="tit alignC">소속 찾기</p> 
		</div>
		<div class="modal-body">
			<div class="modal-srch-box">
				<label >검색어입력</label>
				<input type="text" v-model="srchWrd" placeholder=""   autocomplete="off">
				<button type="button" class="btn04 btn_black fr" @click="callData">검색</button>
			</div>
			<div class="tbBox">
				<table class="tc w100">
					<thead bgcolor="#e3e3e3">
						<tr>
							<th>번호</th>
							<th>기관명</th>
						</tr>
					</thead>
					<tbody>
						<tr v-for="(o,i) in list">
							<td>{{(page.totalRecordCount - (page.currentPageNo - 1) * page.recordCountPerPage - i)}}
							</td>
							<td >
							<a href="javascript:;" @click="setOrg(o)" v-html="o.fullNm" class="modal-name-txt"></a>
							</td>
						</tr>
						<tr v-if="!list.length">
							<td colspan="2" class="nosrch">검색결과가 없습니다.</td>
						</tr>
					</tbody>
				</table>
				<!--// paging //-->
				<div class="page" id="tab3PageDiv">
					<div class="inner cf">
						<template v-if="page.currentPageNo != 1">
						<div class="page_prev0">	
							<a href="javascript:;" @click="movePage(1)">&lt;&lt;</a>
						</div>
						<div class="page_prev0">
							<a href="javascript:;" @click="movePage(page.currentPageNo - 1)">&lt;</a>
						</div>
						</template>
						<template v-for="o in Array.from({ length: page.lastPageNoOnPageList - page.firstPageNoOnPageList + 1 }, (_, i) => page.firstPageNoOnPageList + i)" :key="o">
						<template v-if="o == page.currentPageNo">
						<div class="page_now">
							<a>{{o}}</a>
						</div>
						</template> 
						<template v-else>
						<div class="page_nomal">
							<a href="javascript:;" @click="movePage(o)">{{o}}</a>
						</div>
						</template> </template>
						<template v-if="page.currentPageNo != page.totalPageCount && page.totalPageCount > 0">
						<div class="page_next0">
							<a href="javascript:;" @click="movePage((page.currentPageNo + 1))">&gt;</a>
						</div>
						<div class="page_next0">
							<a href="javascript:;" @click="movePage((page.lastPageNo ))">&gt;&gt;</a>
						</div>
						</template>
					</div>
				</div>
				<!--// paging //-->
			</div>
		</div>
		<div class="modal-footer">
			<div class="tc">
			</div>
		</div>
	</div>
</div>
<script>
var vm_orgInfo = new Vue({
	el: '#vm-orgInfo',
	data: {
		list: [],
		page: {},
		pageNo:1,
		srchWrd: '',
	},
	methods: {
		callData: function(){
			var _this = this;
			var srchWrd = this.srchWrd;
			var pageNo = this.pageNo;
			return $.ajax({
		        url: '/user/ajax/getEtcOrgCd.ajax',
		        data: { srchWrd: srchWrd ,pageNo: pageNo}
		    }).then(function(r){
		        if(r.result == 1){
		        	if(!UTIL.isEmpty(r.data.list)){
			            _this.list = r.data.list;
			            _this.page = r.data.pageNavi;
		        	}
		        }
		        return r; // 결과 반환
		    });
		},
		openModal: function (){
			var _this = this;
		    this.callData().then(function(r){
		    	console.log(r);
		        if(r.result == 1){
		            $('[data-remodal-id=md-orgInfo]').remodal().open();
		        }
		    });
		},
		movePage: function(pageNo){
			this.pageNo = pageNo;
			this.callData();
		},
		setOrg: function(o){
			$('[data-remodal-id=md-orgInfo]').remodal().close();
			$('#vm-etcOrgCd').val(o.orgCd);
			$('#vm-etcOrgNm').val(o.fullNm);
		},
	},
});
</script>

<!-- 직급검색모달 -->
<div class="remodal  " id="vm-gradeInfo" data-remodal-id="md-gradeInfo" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc">
	<button data-remodal-action="close" class="remodal-close" aria-label="Close"></button>
	<div class="modal-content mypage-modal"> 
		<div class="modal-header">
			<p class="tit alignC">직급 찾기</p>
		</div>
		<div class="modal-body">
			<div class="modal-srch-box">
				<label class="sound_only">검색어입력</label>
				<input type="text" v-model="srchWrd" placeholder=""   autocomplete="off">
				<button type="button" class="btn04 btn_black fr" @click="callData">검색</button>
			</div>
			<div class="tbBox">
				<table class="tc w100">
					<thead bgcolor="#e3e3e3">
						<tr>
							<th>번호</th>
							<th>직급</th>
							<th>직종명</th>
							<th>직렬명</th>
							<th>직류명</th>
						</tr>
					</thead>
					<tbody>
						<tr v-for="(o,i) in list">
							<td>{{(page.totalRecordCount - (page.currentPageNo - 1) * page.recordCountPerPage - i)}}
							</td>
							<td >
							<a href="javascript:;" @click="setOrg(o)" v-html="o.gradeNm"></a>
							</td>
							<td>{{o.jikjongNm}}</td>
							<td>{{o.jikryulNm}}</td>
							<td>{{o.jikryuNm}}</td>
						</tr>
						<tr v-if="!list.length">
							<td colspan="5">검색결과가 없습니다.</td>
						</tr>
					</tbody>
				</table>
				<!--// paging //-->
				<div class="page" id="tab3PageDiv">
					<div class="inner cf">
						<template v-if="page.currentPageNo != 1">
						<div class="page_prev0">
							<a href="javascript:;" @click="movePage(1)">&lt;&lt;</a>
						</div>
						<div class="page_prev0">
							<a href="javascript:;" @click="movePage(page.currentPageNo - 1)">&lt;</a>
						</div>
						</template>
						<template v-for="o in Array.from({ length: page.lastPageNoOnPageList - page.firstPageNoOnPageList + 1 }, (_, i) => page.firstPageNoOnPageList + i)" :key="o">
						<template v-if="o == page.currentPageNo">
						<div class="page_now">
							<a>{{o}}</a>
						</div>
						</template> 
						<template v-else>
						<div class="page_nomal">
							<a href="javascript:;" @click="movePage(o)">{{o}}</a>
						</div>
						</template> </template>
						<template v-if="page.currentPageNo != page.totalPageCount && page.totalPageCount > 0">
						<div class="page_next0">
							<a href="javascript:;" @click="movePage((page.currentPageNo + 1))"> &gt;</a>
						</div>
						<div class="page_next0">
							<a href="javascript:;" @click="movePage((page.lastPageNo ))">&gt;&gt;</a>
						</div>
						</template>
					</div>
				</div>
				<!--// paging //-->
			</div>
		</div>
		<div class="modal-footer">
			<div class="tc">
			</div>
		</div>
	</div>
</div>
<script>

$('#vm-etcGradeNm').on('keypress', function(e) {
	if (e.keyCode == '13') {
		vm_gradeInfo();
	}
});


var vm_gradeInfo = new Vue({
	el: '#vm-gradeInfo',
	data: {
		list: [],
		page: {},
		pageNo:1,
		srchWrd: '',
	},
	methods: {
		callData: function(){
			var _this = this;
			var srchWrd = this.srchWrd;
			var pageNo = this.pageNo;
			return $.ajax({
		        url: '/user/ajax/getEtcGradeCd.ajax',
		        data: { srchWrd: srchWrd ,pageNo: pageNo}
		    }).then(function(r){
		        if(r.result == 1){
		        	if(!UTIL.isEmpty(r.data.list)){
			            _this.list = r.data.list;
			            _this.page = r.data.pageNavi;
		        	}
		        }
		        return r; // 결과 반환
		    });
		},
		openModal: function (){
			var _this = this;
		    this.callData().then(function(r){
		    	console.log(r);
		        if(r.result == 1){
		            $('[data-remodal-id=md-gradeInfo]').remodal().open();
		        }
		    });
		},
		movePage: function(pageNo){
			this.pageNo = pageNo;
			this.callData();
		},
		setOrg: function(o){
			$('[data-remodal-id=md-gradeInfo]').remodal().close();
			$('#vm-etcGradeCd').val(o.gradeCd);
			$('#vm-etcGradeNm').val(o.gradeNm);
		},
	},
});
</script>


<script>
//입교증 팝업창
function open_myRceptPop(eduSeq,rceptSeq,userId){
	if(UTIL.isEmpty(userId)){
		userId = '';
	}
    var popUrl ="${utcp.ctxPath}/comm/popup/myLcRceptPop.do?eduSeq="+eduSeq+"&rceptSeq="+rceptSeq+"&userId="+userId;
    var popOption = "width=700px, height=1000px, resizable=no, location=no, top=150px, left=350px;"
    window.open(popUrl,"입교증",popOption);    
}
</script>