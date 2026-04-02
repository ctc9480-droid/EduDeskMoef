<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<section class="pd025 po_re">

	<div class="po_re br5 bs_box cf">
		<!--// tab_con3 //-->
		<div class="cont">
			<!--// 강의 //-->
			<table width="100%" class="tb03 tc">
				<tbody>
					<tr>
						<td class="tl"><c:choose>
								<c:when test='${lctre.imgUseYn == "Y" && lctre.imgRename != ""}'>
									<img src="<spring:eval expression="@prop['cloud.cdn.url']"/>/upload/web/lctreThum/${lctre.imgRename}" alt="교육이미지" />
								</c:when>
								<c:otherwise>
									<img src="${utcp.ctxPath}/resources/admin/images/default_img.png" alt="교육이미지" />
								</c:otherwise>
							</c:choose></td>
						<td class="tl"><span class="dp_ib">${lctre.onlineYn == 'Y'?'[온라인]':''  } ${lctre.eduCtgryNm }</span> ${lctre.addStatusBox } <span class="dp_b font_22 fw_500 pdr15">${lctre.eduNm }</span> <span class="dp_b font_14">${lctre.orgNm } / ${lctre.instrctrNm } 강사</span></td>
						<td class="tl"><span class="dp_b font_14">[접수] ${utcp.convDateToStr(utcp.convStrToDate(lctre.rceptPeriodBegin,'yyyyMMddHHmm'),'yyyy-MM-dd')} ~ ${utcp.convDateToStr(utcp.convStrToDate(lctre.rceptPeriodEnd,'yyyyMMddHHmm'),'yyyy-MM-dd')}</span> <span class="dp_b font_14">[교육] ${lctre.eduPeriodBegin } ~ ${lctre.eduPeriodEnd }</span> <span class="dp_b font_14">
								${lctre.personnel }명 모집 (<strong class="fc_red fw_400">${lctre.rceptCnt + lctre.waitCnt }</strong>명 신청)
							</span></td>
					</tr>
				</tbody>
			</table>
			<!--// 강의 //-->

			<span class="tb_text font_18">
				출결현황 (<strong class="fw_500">89</strong>%)
			</span>

			<div class="dp_ib fr tr mgb15">
				<button class="btn04 btn_green" type="button" onclick="fn_checktable_excel2()">엑셀 다운로드</button>
				<button class="btn04 btn_orange">수정</button>
			</div>

			<div id="tableScroll" class="tableScroll">
				<div class="tableWrap" id="vm-checktable2">
					<comp-md-checktable-2 :date_list="dateList" :time_list="timeList" :stdnt_list="stdntList"> </comp-md-checktable-1>
				</div>
				<!--// tableWrap //-->
			</div>
			<!--// tableScroll //-->

			<div class="fl tc">
				<button class="btn02 btn_grayl mgb20" onclick="location.href='attendanceList.do';">목록</button>
			</div>
		</div>
		<!--// tab_con3 //-->
	</div>
</section>
<script type="text/javascript" src="${utcp.ctxPath}/resources/smartCheck/js/tableHeadFixer.js"></script>

<script type="text/x-template" id="tmp-md-checktable-2">
<table class="mainTable" id="fixTable">
	<thead bgcolor="#f7f8fa">
		<tr>
			<th rowspan="" class="fixedSide"></th>
			<th rowspan="" class="fixedSide"></th>
			<template v-for="o in date_list">
			<th :colspan="o.eduDtCnt" >{{o.eduDt}}</th>
			</template>
			<th rowspan=""></th>
		</tr>
		<tr>
			<th rowspan="" class="fixedSide">이름</th>
			<th rowspan="" class="fixedSide">아이디</th>
			<th v-for="o in time_list" class="prd">{{o.timeSeq}}</th>
			<th rowspan="">합계</th>
		</tr>
	</thead>
	<tbody>
		<tr v-for="o in stdnt_list">
			<td class="fixedSide">{{o.userNm}}</td>
			<td class="fixedSide">{{o.email}}</td>
			<td v-for="o2 in o.stdntAttList" class="prd"><span class="prd">{{o2.attNm3}}</span></td>
			<td>{{o.attendCnt}} / {{o.timeCnt}}</td>
		</tr>
	</tbody>
</table>
</script>
<script>
	Vue.component('comp-md-checktable-2', {
		template : '#tmp-md-checktable-2',
		props : [ 'date_list', 'time_list', 'stdnt_list' ],
	});
	var vm_checktable2 = new Vue({
		el : '#vm-checktable2',
		data : {
			eduSeq : 0,
			userId : '',
			dateList : [],
			timeList : [],
			stdntList : [],
		},
		updated:function(){
			/* 
			$("#fixTable").tableHeadFixer({
				"left" : 2,
				"right" : 1,
			});

			$('#parent').css('height', $(window).height() - 500);
			$(window).resize(function() {
				$('#parent').css('height', $(window).height() - 500);
			}); 
			*/
			 jQuery(".mainTable").clone(true).appendTo('#tableScroll').addClass('clone'); 
		},
	});
	function fn_openCheckList2(eduSeq) {
		$.ajax({
			url : '${utcp.ctxPath}/admin/ajax/rollBook.json',
			data : {
				eduSeq : eduSeq,
			},
			success : function(r) {
				vm_checktable2.dateList = r.dateList;
				vm_checktable2.timeList = r.timeList;
				vm_checktable2.stdntList = r.stdntList;
				vm_checktable2.eduSeq = eduSeq;
				vm_checktable2.userId = userId;
			},
		});
	}
	function fn_checktable_excel2() {
		location.href = '${utcp.ctxPath}/admin/excel/rollBook.do?eduSeq='
				+ vm_checktable2.eduSeq;
	}
	fn_openCheckList2('${param.eduSeq}');
</script>
