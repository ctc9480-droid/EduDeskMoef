<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp" %>
<script type="text/javascript">

function fn_srch(){
	$("#i_srchCtgry").val($("#srchCtgry").val());
	$("#i_srchColumn").val($("#srchColumn").val());
	$("#i_srchWrd").val($("#srchWrd").val());
	$("#i_page").val(1);
	fn_listUpd();
}

function fn_page(page){
	$("#i_page").val(page);
	fn_listUpd()
}


function fn_listUpd(){
	$.ajax({
        url: "${utcp.ctxPath}/user/mypage/instrctrEduList.json",
        type: "POST",
        data: { 
        	"srchCtgry" : $("#i_srchCtgry").val(),
        	"srchColumn" : $("#i_srchColumn").val(),
        	"srchWrd" : $("#i_srchWrd").val(),
        	"page" : $("#i_page").val(),
        	"gubun" : "${gubun}"
        },  
        dataType: 'json',
	    crossDomain: true,
		success: function(r) {
			vm_rcepthistlist.list=r.dataList;
			vm_rcepthistlist.page=r.pageNavi;
		}
    });
}




</script>
<div class="listWrap" style="padding-top:0">
	<div id="listDiv">
	    <div class="box_sort flex listWrapBox">
	       <div class="box_cate">
		        <select id="srchCtgry" onchange="fn_srch()">
		            <option value="">전체</option>
		            <c:forEach var="data" items="${eduList}" varStatus="stat">
						<option value="${data.ctgrySeq}" <c:if test='${vo.srchCtgry == data.ctgrySeq}'>selected</c:if>>${data.ctgryNm}</option>
					</c:forEach>
	    		</select>
			</div>
			<div class="box_search">
			    <select id="srchColumn">
			        <option value="eduNm" <c:if test='${vo.srchColumn == "eduNm"}'>selected</c:if>>교육명</option>
					<option value="instrctrNm" <c:if test='${vo.srchColumn == "instrctrNm"}'>selected</c:if>>강사명</option>
				</select>
				<input type="text" id="srchWrd"/>
				<button type="submit" onclick="fn_srch(); return false;">검색</button>
	    	</div>
	    </div>
	    <form name="srchFrm" method="post">
			<input type="hidden" name="srchCtgry" id="i_srchCtgry" value="${vo.srchCtgry}"/>
			<input type="hidden" name="srchColumn" id="i_srchColumn" value="${vo.srchColumn}"/>
			<input type="hidden" name="srchWrd" id="i_srchWrd" value="${vo.srchWrd}"/>
			<input type="hidden" name="page" id="i_page" value="${vo.page}"/>
		</form>
	    <div class="box_list">
	        <ul id="lctreView">
				<template v-if="list.length">
				<li class="flex " v-for="o in list"><%--li class에 ud 넣으면 알림아이콘 표시 됨 --%>
     				<span class="img_logo">
						<template v-if="o.imgUseYn == 'Y' && o.imgRename != ''">
							<img :src="'<spring:eval expression="@prop['cloud.cdn.url']"/>/upload/web/lctreThum/'+o.imgRename+'?'+o.updDe" alt="교육이미지" width="112px" height="149px"/>
						</template>
						<template v-else>
							<img :src="'${utcp.ctxPath}/resources/admin/images/default_img.png?'+o.updDe" alt="교육이미지" width="112px" height="149px"/>
						</template>
					</span>
					<div class="text_title">
  						<div class="cp" :onclick="'fn_openEdu('+o.eduSeq+')'" style="cursor:pointer">
							<p class="eduTypeWrap">
							<span class="eduLabel">
	                        	
	                        <template v-if="o.fee > 0 ">
	                        <b class="price pay">유료</b>
	                        </template>
	                        <template v-if="o.fee == 0 ">
	                        <b class="price free">무료</b>
	                        </template>
	                        </span>
	                        |
	                        <span class="eduType">{{o.ctg1Nm}}</span> |
	                        <span class="eduType1">[{{o.lctreTypeNm }}]</span>
							</p>
							<p class="eduSbj">
							<strong v-html="o.eduNm"></strong>
							<%-- 
							<c:if test="${gubun ne 'Open' }">
								<span v-html="o.addPassBox "></span>
							</c:if> 
							<c:if test="${gubun eq 'Open' }">
								<span class="icon_tag comp" style="background-color: #eaeaea;color:gray;"><span v-html="o.addPassNm "></span></span>
							</c:if> 
							 --%>
							 <!-- 
							 <span v-html="o.addStatusBox"></span>
							  -->
							</p>
							<p class="eduStnt">대상 : <span class="Stnt" >
							 {{o.addTargetsStr}}
							</span></p>
							
  						</div>
					</div>
					<div class="text_info">
						<div>
							<span class="edudtType1"> [접수] <b class=""> <template v-if="o.rceptPeriodYn=='Y'"> 
							{{o.rceptPeriodBegin|fltDt2Str('YYYYMMDDhhmm','YYYY-MM-DD')}} ~ {{o.rceptPeriodEnd|fltDt2Str('YYYYMMDDhhmm','YYYY-MM-DD')}} 
							(<b class="dday">{{o.rceptPeriodEnd|fltDt2Dday('YYYYMMDDhhmm')}}</b>)
							</template> 
							<template v-else> 미설정 </template>
							</b>
							</span>
							<span class="edudtType2"> [교육] <b class="">
							<template v-if="o.tmSeq > 0">
							{{o.tmEduDt|fltDt2Str('YYYYMMDD','YYYY-MM-DD')}}
							</template>
							<template v-else>
							{{o.eduPeriodBegin}} ~ {{o.eduPeriodEnd}}
							</template>
							</b>
							</span>
							<!-- 
							<span class="edudtType3">
							<template v-if="o.personnel==0">
							무제한
							</template>
							<template v-else>
							<template v-if="o.minPersonnel>0">
							{{o.minPersonnel}} ~
							</template>
							{{o.personnel}}명 모집 
							</template>
							(<strong class="color fw_500">{{o.rceptCnt}}</strong>명 신청)
							</span>
							 -->
							<button type="button" :onclick="'fn_openEdu('+o.eduSeq+')'" class="btn04 btn_blue scPopBtn">입장하기</button>
    					</div>
					</div>
				</li>
				</template>
				<template v-else>
				<li class="flex">
					<div class="no_data">데이터가 없습니다.</div>
			 	</li>
				</template>
	        </ul>
	    </div>
		<div class="board_pager" style="margin-bottom:30px;">
			<div class="pagination" id="pageDiv">
				<template v-if="page.currentPageNo != 1">
					<a href="javascript:;" :onclick="'fn_page('+(page.currentPageNo - 1)+'); return false;'">&laquo;</a>
				</template>
				<template v-for="o in (page.firstPageNoOnPageList , page.lastPageNoOnPageList)" >
					<template v-if="o == page.currentPageNo">
						<a href="javascript:;" class="active">{{o}}</a>
					</template>
					<template v-else>
						<a href="javascript:;" :onclick="'fn_page('+o+'); return false;'">{{o}}</a>
					</template>
				</template> 
				<template v-if="page.currentPageNo != page.totalPageCount && page.totalPageCount > 0">
			  		<a href="javascript:;" onclick="fn_page('${pageNavi.currentPageNo + 1}'); return false;">&raquo;</a>
			  	</template>
			</div>
		</div>
	</div>
</div>



<script>
$(document).ready(function(){
	fn_listUpd();
});
//리스트
var vm_rcepthistlist = new Vue({
	el:'#listDiv',
	data:{
		list:[]
		,page:{}
	},
});
</script>

<!-- 교육팝업 전용 -->
<script>
function fn_openEdu(eduSeq){
	window.open('${utcp.ctxPath}/user/mypage/popup/instrctrEdu${gubun}View.do?eduSeq='+eduSeq,'eduPop','width=1280,height=860,left=100,top=50');
}
</script>