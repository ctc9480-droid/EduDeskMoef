<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<div class="listWrap" style="padding-top: 0;" >
	<div id="vm-myLcrcp">
		<div class="board_tab_onoff tl">
			<!--// board_tab_con //-->
			<div class="board_tab_con">
				<!--// tab_con1 //-->
				<div class="cont tableRespon">
					<table class="tc w100 tb01">
						<caption class="sound_only">단체교육 신청내역 테이블</caption>
						<thead>
							<tr>
								<th scope="col" class="vm">순번</th>
								<th scope="col" class="vm" width="">과정</th>
								<th scope="col" class="vm" width="">교육명</th>
								<th scope="col" class="vm">신청상태</th>
								<th scope="col" class="vm" style="min-width: ;">신청일</th>
								<th scope="col" class="vm" style="min-width: ;">입교증</th>
								<th scope="col" class="vm" style="min-width: ;"></th>
								<!-- 
								<th scope="col" class="vm">영수증</th>
								 -->
							</tr>
						</thead>
						<tbody>
							<template v-for="(o,i) in datas.lcrcpList">
							<tr>
								<td class="fs_12 ls-05">{{datas.pageNavi.totalRecordCount - (((datas.pageNavi.currentPageNo-1)*datas.pageNavi.recordCountPerPage) + i)}}</td>
								<td class="fs_12 ls-05" > {{o.ctg1Nm}}</td>
								<td class="fs_12 ls-05 tl" > 
								<span v-html="o.eduNm"></span>
								<br/>
								<span class="dp_b fs_13 fc_darkgray pdt5">{{o.eduPeriodBegin}}~{{o.eduPeriodEnd }}</span>
								</td>
								<td class="fs_12 ls-05" > 
								<span :class="o.rceptState == 1?'eduAppl':o.rceptState == 2?'eduAppr':'eduCanc'">
								{{o.addRceptStateNm}}
								</span>
								</td>
								<td class="fs_12 ls-05" >{{o.regDe|fltDate2Str('YYYY-MM-DD HH:mm')}}</td>
								<td>
								<template v-if="o.rceptState == 2">
												<a :href="'javascript:open_myRceptPop('+o.eduSeq+','+o.rceptSeq+','+o.userId+');'">
													<img src="${utcp.ctxPath }/resources/user/image/icon/manage_print.png" />
												</a>
											</template>
								</td>
								<td></td>
							</tr>
							</template>
							<tr v-if="!datas.lcrcpList.length">
								<td colspan="11">데이터가 없습니다</td>
							</tr>
						</tbody>
					</table>
					
					<!--// paging //-->
					<div class="page" v-if="datas.pageNavi">
						<div class="inner cf">
							<template v-if="datas.pageNavi.currentPageNo != 1">
								<div class="page_prev0">
								<a href="javascript:;" :onclick="'fn_pgMyLcrcp('+(datas.pageNavi.currentPageNo - 1)+'); return false;'">&laquo;</a>
								</div>
							</template>
							<template v-for="o in (datas.pageNavi.firstPageNoOnPageList , datas.pageNavi.lastPageNoOnPageList)" >
								<template v-if="o == datas.pageNavi.currentPageNo">
									<div class="page_now"><a href="javascript:;" >{{o}}</a></div>
								</template>
								<template v-else>
									<div class="page_nomal"><a href="javascript:;" :onclick="'fn_pgMyLcrcp('+o+'); return false;'">{{o}}</a></div>
								</template>
							</template> 
							<template v-if="datas.pageNavi.currentPageNo != datas.pageNavi.totalPageCount && datas.pageNavi.totalPageCount > 0">
						  		<div class="page_next0">
						  		<a href="javascript:;" :onclick="'fn_pgMyLcrcp('+(datas.pageNavi.currentPageNo + 1)+'); return false;'">&raquo;</a>
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
var vm_myLcrcp = new Vue({
	el : '#vm-myLcrcp',
	data : {
		datas : {
			lcrcpList:[],
		},
	},
});
function fn_pgMyLcrcp(page){
	fn_getStdntLcRceptList('${param.userId}',page);
}
function fn_getStdntLcRceptList(userId,page){
	$.ajax({
		url : '${utcp.ctxPath}/admin/ajax/getStdntLcRceptList.ajax',
		data : {userId : userId, page : page},
		success : function (r){
			if(r.result == 1){
				console.log(r);
				vm_myLcrcp.datas = r.data;
			
			}
		}
	});
}
</script>
