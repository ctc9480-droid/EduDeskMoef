<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<input type="hidden" id="page-lctre" />
<input type="hidden" id="page-tmlct" />
<input type="hidden" id="totalCnt-lctre" />
<input type="hidden" id="totalCnt-tmlct" />
<div class="totalSch">
	<p class="totalTit">'<strong>${totalSrchText}</strong>'에 대한 검색 결과 총 <em id="area-totalCnt">0</em>건
	</p>

	<div class="totalCon">
		<div class="listWrap" id="vm-totalSearchTmlct">

			<div class="box_list totalEduList">
				<p class="totalEduType">단체교육</p>
				<ul>
					<template v-if="list.length">
					<li class="flex" v-for="o in list">
						<span class="img_logo"> <template v-if="o.imgUseYn == 'Y' && o.imgRename != ''"> <img :src="'<spring:eval expression="@prop['cloud.cdn.url']"/>/upload/web/lctreThum/'+o.imgRename" alt="교육이미지" width="112px" height="149px" /> </template> <template v-else> <img src="${utcp.ctxPath}/resources/admin/images/default_img.png?1" alt="교육이미지" width="112px" height="149px" /> </template>
						</span>
						<div class="text_title">
							<div class="cp" :onclick="'fn_moveView('+o.tmSeq+',\'tmlct\')'" style="cursor: pointer">
								<p class="eduTypeWrap"><span class="eduLabel"> <template v-if="o.tmSeq > 0"> 단체 </template> <template v-if="o.tmSeq == 0"> 개인 </template> | <template v-if="o.fee > 0 "> <b class="price pay">유료</b> </template> <template v-if="o.fee == 0 "> <b class="price free">무료</b> </template>
								</span> | <span class="eduType">{{o.detailCtgryNm}}</span> | <span class="eduType1">[{{o.lctreTypeNm }}]</span></p>
								<p class="eduSbj"><strong v-html="o.eduNm"></strong> <span v-html="o.addStatusBox "></span></p>
								<p class="eduStnt">대상 : <span class="Stnt" v-html="o.addTargetsStr"> </span></p>

							</div>
						</div>
						<div class="text_info">
							<div>
								<span class="edudtType1"> [접수] <b class=""> <template v-if="o.rceptPeriodYn=='Y'"> {{o.rceptPeriodBegin|fltDt2Str('YYYYMMDDHHmm','YYYY-MM-DD')}} ~ {{o.rceptPeriodEnd|fltDt2Str('YYYYMMDDHHmm','YYYY-MM-DD')}} (<b class="dday">{{o.rceptPeriodEnd|fltDt2Dday('YYYYMMDDHHmm')}}</b>) </template> <template v-else> 미설정 </template>
								</b>
								</span> <span class="edudtType2"> [교육] <b class="">{{o.tmPeriodBegin|fltDt2Str('YYYYMMDD','YYYY-MM-DD')}} ~ {{o.tmPeriodEnd|fltDt2Str('YYYYMMDD','YYYY-MM-DD')}}</b>
								</span> <span class="edudtType3"> [인원] <template v-if="o.personnel==0"> 무제한 </template> <template v-else> <template v-if="o.minPersonnel>0"> {{o.minPersonnel}} ~ </template> {{o.personnel}}명 모집 </template>
								</span>
							</div>
						</div>
					</li>
					</template>
					<template v-else>
					<li>검색 결과가 없습니다.</li>
					</template>
				</ul>
			</div>
			<!-- // totalEduList // -->
			<div class="board_pager" style="margin-bottom: 30px;">
				<div class="pagination">
					<template v-if="page.currentPageNo != 1"> <a href="javascript:;" :onclick="'fn_page('+(page.currentPageNo - 1)+',\'tmlct\'); return false;'">&laquo;</a> </template>
					<template v-for="o in (page.firstPageNoOnPageList , page.lastPageNoOnPageList)"> <template v-if="o == page.currentPageNo"> <a href="javascript:;" class="active">{{o}}</a> </template> <template v-else> <a href="javascript:;" :onclick="'fn_page('+o+',\'tmlct\'); return false;'">{{o}}</a> </template> </template>
					<template v-if="page.currentPageNo != page.totalPageCount && page.totalPageCount > 0"> <a href="javascript:;" :onclick="'fn_page('+(page.currentPageNo + 1)+',\'tmlct\'); return false;'">&raquo;</a> </template>
				</div>
			</div>
		</div>
		<!-- // listWrap // -->

		<div class="listWrap" id="vm-totalSearchLctre">
			<div class="box_list totalEduList">
				<p class="totalEduType">개인교육</p>
				<ul>
					<template v-if="list.length">
					<li class="flex" v-for="o in list">
						<span class="img_logo"> <template v-if="o.imgUseYn == 'Y' && o.imgRename != ''"> <img :src="'<spring:eval expression="@prop['cloud.cdn.url']"/>/upload/web/lctreThum/'+o.imgRename" alt="교육이미지" width="112px" height="149px" /> </template> <template v-else> <img src="${utcp.ctxPath}/resources/admin/images/default_img.png?1" alt="교육이미지" width="112px" height="149px" /> </template>
						</span>
						<div class="text_title">
							<div class="cp" :onclick="'fn_moveView('+o.eduSeq+',\'lctre\')'" style="cursor: pointer">
								<p class="eduTypeWrap"><span class="eduLabel"> <template v-if="o.tmSeq > 0"> 단체 </template> <template v-if="o.tmSeq == 0"> 개인 </template> | <template v-if="o.fee > 0 "> <b class="price pay">유료</b> </template> <template v-if="o.fee == 0 "> <b class="price free">무료</b> </template>
								</span> | <span class="eduType">{{o.detailCtgryNm}}</span> | <span class="eduType1">[{{o.lctreTypeNm }}]</span></p>
								<p class="eduSbj"><strong v-html="o.eduNm"></strong> <span v-html="o.addStatusBox "></span></p>
								<p class="eduStnt">대상 : <span class="Stnt" v-html="o.addTargetsStr"> </span></p>

							</div>
						</div>
						<div class="text_info">
							<div>
								<template v-if="o.rceptVipPeriodYn=='Y'"> <span class="edudtType1"> [연간] <b class=""> {{o.rceptVipPeriodBegin|fltDt2Str('YYYYMMDDHHmm','YYYY-MM-DD HH:mm')}} ~ {{o.rceptVipPeriodEnd|fltDt2Str('YYYYMMDDHHmm','YYYY-MM-DD HH:mm')}} (<b class="dday">{{o.rceptVipPeriodEnd|fltDt2Dday('YYYYMMDDHHmm')}}</b>)
								</b></template>
								<span class="edudtType1"> [접수] <b class=""> <template v-if="o.rceptPeriodYn=='Y'"> {{o.rceptPeriodBegin|fltDt2Str('YYYYMMDDHHmm','YYYY-MM-DD HH:mm')}} ~ {{o.rceptPeriodEnd|fltDt2Str('YYYYMMDDHHmm','YYYY-MM-DD HH:mm')}} (<b class="dday">{{o.rceptPeriodEnd|fltDt2Dday('YYYYMMDDHHmm')}}</b>) </template> <template v-else> 미설정 </template>
								</b>
								</span> <span class="edudtType2"> [교육] <b class="">{{o.eduPeriodBegin}} ~ {{o.eduPeriodEnd}}</b>
								</span> <span class="edudtType3"> [인원 ]<template v-if="o.personnel==0"> 무제한 </template> <template v-else> <template v-if="o.minPersonnel>0"> {{o.minPersonnel}} ~ </template> {{o.personnel}}명 모집 </template>
								/ <strong class="color">{{o.rceptCnt}}</strong>명 신청
								</span>
							</div>
						</div>
					</li>
					</template>
					<template v-else>
					<li>검색 결과가 없습니다.</li>
					</template>
				</ul>
			</div>
			<!-- // totalEduList // -->
			<div class="board_pager" style="margin-bottom: 30px;">
				<div class="pagination">
					<template v-if="page.currentPageNo != 1"> <a href="javascript:;" :onclick="'fn_page('+(page.currentPageNo - 1)+',\'lctre\'); return false;'">&laquo;</a> </template>
					<template v-for="o in (page.firstPageNoOnPageList , page.lastPageNoOnPageList)"> <template v-if="o == page.currentPageNo"> <a href="javascript:;" class="active">{{o}}</a> </template> <template v-else> <a href="javascript:;" :onclick="'fn_page('+o+',\'lctre\'); return false;'">{{o}}</a> </template> </template>
					<template v-if="page.currentPageNo != page.totalPageCount && page.totalPageCount > 0"> <a href="javascript:;" :onclick="'fn_page('+(page.currentPageNo + 1)+',\'lctre\'); return false;'">&raquo;</a> </template>
				</div>
			</div>
		</div>
		<!-- // listWrap // -->
	</div>
	<!-- // totalCon // -->
</div>
<!-- // totalSch // -->

<script>
var vm_totalSearchLctre = new Vue({
	el : '#vm-totalSearchLctre',
	data : {
		list:[]
		,page:{}
	},
});
var vm_totalSearchTmlct = new Vue({
	el : '#vm-totalSearchTmlct',
	data : {
		list:[]
		,page:{}
	},
});
function fn_callTotalSearch(gubun){
	var page = $('#page-lctre').val();
	if(gubun == 'tmlct'){
		page = $('#page-tmlct').val();
	}
	$.ajax({
		data : {gubun : gubun, totalSrchText : '${totalSrchText}', page : page},
		url : 'searchResult.ajax',
		success : function(r){
			if(gubun == 'lctre'){
				vm_totalSearchLctre.list=r.data.dataList;
				vm_totalSearchLctre.page=r.data.pageNavi;
				$('#totalCnt-lctre').val(r.data.pageNavi.totalRecordCount);
			}else{
				vm_totalSearchTmlct.list=r.data.dataList;
				vm_totalSearchTmlct.page=r.data.pageNavi;
				$('#totalCnt-tmlct').val(r.data.pageNavi.totalRecordCount);
			}
			var totalCnt = ($('#totalCnt-tmlct').val()*1)+($('#totalCnt-lctre').val()*1);
			$('#area-totalCnt').text(totalCnt);
		}
	});
}
function fn_page(page,gubun){
	if(gubun == 'tmlct'){
		$('#page-tmlct').val(page);
	}else{
		$('#page-lctre').val(page);
	}
	fn_callTotalSearch(gubun)
}
function fn_moveView(seq,gubun) {
	if(gubun == 'tmlct'){
		location.href = "${utcp.ctxPath}/user/edu/tmlctView.do?tmSeq=" + seq;
	}else{
		location.href = "${utcp.ctxPath}/user/edu/eduView.do?eduSeq=" + seq;
	}
}
fn_callTotalSearch('lctre');
fn_callTotalSearch('tmlct');
</script>