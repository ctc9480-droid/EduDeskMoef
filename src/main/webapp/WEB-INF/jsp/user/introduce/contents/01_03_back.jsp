<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<div class="sub_txt">
	<span class=""><img
		src="${utcp.ctxPath}/resources/user/image/icon/icon_subtitle.png"
		alt="">조직도</span>
</div>

<div class="sub_int" id="vm-0103">
	<table class="w100 tb04">
		<caption class="blind">번호, 구분, 제목, 첨부파일, 등록일, 조회가 나타나 있는 목록</caption>
	
		<thead>
			<tr>
				<th style="width:50px">연번</th>
				<th>담당업무</th>
				<th style="width:50px">담당자</th>
				<th style="width:50px">문의처</th>
			</tr>
		</thead>
		<tbody>
			<template v-for="(o,i) in orgList">
			<tr>
				<td>{{i+1}}</td>
				<td class="tl">
					<ul class="dot_list">
						<li>{{o[0]}}</li>
					</ul>
				</td>
				<td>{{o[1]}}</td>
				<td>{{o[2]}}</td>
			</tr>
			</template>
		</tbody>
	</table>
</div>

<script>
var data0103Arr =  [
	['KTL 아카데미 업무 총괄' 					,'최문석'		,'02-860-1321'	,'수석연구원'],
	['교육기획 및 운영, 예산관리'					,'김욱'		,'02-860-1329'	,'선임연구원'],
	['교육기획 및 홍보 '							,'신재환'		,'02-860-1327'	,'주임연구원'],
	['(재직자) KTL 아카데미 내부교육, 전문기술교육'	,'고상우'		,'02-860-1311'	,'주임연구원'],
	['(재직자) KOLAS 및 ISO심사원 과정, 사업주위탁교육 ','정동철'	,'02-860-1310'	,'주임연구원'],
	['(재직자) KTL 아카데미 전문기술교육  '			,'강현진'		,'02-860-1322'	,'연구원'],
	['(미취업자) 반도체 분야 교육'					,'김민우'		,'02-860-1390'	,'주임연구원'],
	['(미취업자) 의료기기 분야 교육 '				,'주시윤'		,'02-860-1323'	,'연구원'],
	['(미취업자) 적합성·산업안전보건 교육 '			,'안창항'		,'02-860-1326'	,'연구원'],
	['(미취업자) GMP 제약분야 교육 '				,'박성민'		,'02-860-1325'	,'연구원'],
	['(미취업자) 품질·인증 교육 '					,'김산들'		,'02-860-1328'	,'연구원']
]
var vm_0103 = new Vue({
	el: '#vm-0103',
	data: {
		orgList: data0103Arr
	},
});
</script>

