<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<div class="listWrap" style="padding-top: 0;" >
	<div id="vm-mySms">
		<div class="board_tab_onoff tl">
			<!--// board_tab_con //-->
			<div class="board_tab_con">
					<!-- 
					<div class="tr mgb15">
					<a href="javascript:fn_openSmeSend();" class="btn04 btn_green">문자발송</a>
					</div>
					 -->
				<!--// tab_con1 //-->
				<div class="cont tableRespon">
					<table class="tc w100 tb01">
						<caption class="sound_only">단체교육 신청내역 테이블</caption>
						<thead>
							<tr>
								<th scope="col" class="vm">번호</th>
								<th scope="col" class="vm" >내용</th>
								<th scope="col" class="vm" width="">수신번호</th>
								<th scope="col" class="vm">발신번호</th>
								<th scope="col" class="vm" >발송상태</th>
								<th scope="col" class="vm" >발송일</th>
							</tr>
						</thead>
						<tbody>
							<template v-for="(o,i) in datas.smsList">
							<tr>
								<td class="fs_12 ls-05">{{datas.pageNavi.totalRecordCount - (((datas.pageNavi.currentPageNo-1)*datas.pageNavi.recordCountPerPage) + i)}}</td>
								<td class="fs_12 ls-05 tl" > {{o.message}}</td>
								<td class="fs_12 ls-05 " > {{o.decToNum}} </td>
								<td class="fs_12 ls-05 " > {{o.fromNum}} </td>
								<td class="fs_12 ls-05 " > {{o.addResultNm}} </td>
								<td class="fs_12 ls-05" >
								<template v-if="o.resultCd == 1">
								{{o.sendDt|fltDate2Str('YYYY-MM-DD HH:mm:ss')}}
								</template>
								</td>
							</tr>
							</template>
							<tr v-if="!datas.smsList.length">
								<td colspan="11">데이터가 없습니다</td>
							</tr>
						</tbody>
					</table>
					
					<!--// paging //-->
					<div class="page" v-if="datas.pageNavi">
						<div class="inner cf">
							<template v-if="datas.pageNavi.currentPageNo != 1">
								<div class="page_prev0">
								<a href="javascript:;" :onclick="'fn_pgStdntSms('+(datas.pageNavi.currentPageNo - 1)+'); return false;'">&laquo;</a>
								</div>
							</template>
							<template v-for="o in (datas.pageNavi.firstPageNoOnPageList , datas.pageNavi.lastPageNoOnPageList)" >
								<template v-if="o == datas.pageNavi.currentPageNo">
									<div class="page_now"><a href="javascript:;" >{{o}}</a></div>
								</template>
								<template v-else>
									<div class="page_nomal"><a href="javascript:;" :onclick="'fn_pgStdntSms('+o+'); return false;'">{{o}}</a></div>
								</template>
							</template> 
							<template v-if="datas.pageNavi.currentPageNo != datas.pageNavi.totalPageCount && datas.pageNavi.totalPageCount > 0">
						  		<div class="page_next0">
						  		<a href="javascript:;" :onclick="'fn_pgStdntSms('+(datas.pageNavi.currentPageNo + 1)+'); return false;'">&raquo;</a>
						  		</div>
						  	</template>
						</div>
					</div>
					<!--// paging //-->

				</div>
				<!--// tab_con1 //-->


			</div>
			<!--// board_tab_con //-->
		</div>
		<!--// board_tab_onoff //-->

	</div>
</div>
<script>
var vm_mySms = new Vue({
	el : '#vm-mySms',
	data : {
		datas : {
			smsList:[],
		},
	},
});
function fn_pgStdntSms(page){
	fn_getStdntSmsList('${param.userId}',page);
}
function fn_getStdntSmsList(userId,page){
	$.ajax({
		url : '${utcp.ctxPath}/admin/ajax/getStdntSmsList.ajax',
		data : {userId : userId, page : page},
		success : function (r){
			if(r.result == 1){
				console.log(r);
				vm_mySms.datas = r.data;
			
			}
		}
	});
}
function fn_openSmeSend(){
	fn_md_smsSend('','${user.mobile}','${user.userId}',fn_stdnt_closeSmeSend);
}
function fn_stdnt_closeSmeSend(){
	fn_pgStdntSms(1);
}
</script>
