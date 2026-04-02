<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.educare.edu.menu.service.model.Menu"%>
<%@ page import="com.educare.edu.menu.service.MenuUtil"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<jsp:useBean id="curDate" class="java.util.Date" />
<style>
.no_data {
	height: 150px;
	width: 100%;
	line-height: 150px;
	text-align: center;
	vertical-align: middle;
	font-size: 1.25rem
}
@media(max-width:768px) {
.navBot ul li {
width: 50%;
}
.no_data {
font-size: 0.875rem;
}
}
</style>
<script>
	$(document).ready(function() {

		//sub slide
		$('#eduSlide').slick({

			infinite : true,
			speed : 500,
			autoplay : true,
			autoplaySpeed : 2000,
			slidesToShow : 3,
			slidesToScroll : 1,
			responsive : [ {
				breakpoint : 1100,
				settings : {
					slidesToShow : 2,
					dots : true,
					arrows : false
				}
			}, {
				breakpoint : 768,
				settings : {
					slidesToShow : 1,
					dots : true,
					arrows : false
				}
			} ]
		});

		//기관셀렉트박스 change 이벤트
		$('#srchAgency,#srchYear').change(function() {
			fn_srch();
		});
		
		//최초 로딩시 월 선택활성화를 감추기위해 임시설정,250319
		$('.box_month li').removeClass('select');
		$('#mon13').addClass('select');
		

	});

	function fn_monSrch(mon) {
		
		$('.box_month li').removeClass('select');

		$("#mon" + mon).addClass('select');

		if(mon == '13'){
			mon = '';
		}
		$("#i_srchYear").val($("#srchYear option:selected").val());
		$("#i_srchMonth").val(mon);
		fn_listUpd();
	}

	function fn_srch() {
		$("#i_srchCtgry").val($("#srchCtgry").val());
		$("#i_srchCtgry2").val($("#srchCtgry2").val());
		$("#i_srchCtgry3").val($("#srchCtgry3").val());
		$("#i_srchAgency").val($("#srchAgency").val());
		$("#i_srchColumn").val($("#srchColumn").val());
		$("#i_srchWrd").val($("#srchWrd").val());
		$("#i_srchYear").val($("#srchYear").val());
		$("#i_eduAgency").val($("#eduAgency").val());

		fn_listUpd();
	}

	function fn_calendar() {
		location.href = "${utcp.ctxPath}/user/edu/eduCal.do?srchYear="
				+ $("#i_srchYear").val() + "&srchMonth="
				+ $("#i_srchMonth").val() + '&srchCtgry='
				+ $("#i_srchCtgry").val() + '&srchCtgry2='
				+ $("#i_srchCtgry2").val() + '&srchCtgry3='
				+ $("#i_srchCtgry3").val() + '&srchAgency='
				+ $("#i_srchAgency").val() + '&srchColumn='
				+ $("#i_srchColumn").val() + '&srchWrd='
				+ $("#i_srchWrd").val();
	}

	function fn_listUpd() {
		$.ajax({
			url : "${utcp.ctxPath}/user/edu/eduList.json",
			type : "POST",
			data : {
				"srchYear" : $("#i_srchYear").val(),
				"srchMonth" : $("#i_srchMonth").val(),
				"srchCtgry" : $("#i_srchCtgry").val(),
				"srchCtgry2" : $("#i_srchCtgry2").val(),
				"srchCtgry3" : $("#i_srchCtgry3").val(),
				"srchAgency" : $("#i_srchAgency").val(),
				"srchColumn" : $("#i_srchColumn").val(),
				"srchWrd" : $("#i_srchWrd").val(),
// 				"srchSort" : $("#i_srchSort").val(),
				"srchRcept" : $("#i_srchRcept").val(),
				"srchSidoCd" : $("#srchSidoCd").val(),
				"eduAgency" : $("#i_eduAgency").val()
			},
			cache : false,
			async : true,
			success : function(r) {
				//접수상태,접수기간으로 재정렬
				/*
				try {

					r.lctreList.sort((a, b) => {
					  // checkRcept 오름차순 정렬
					  if (a.checkRcept !== b.checkRcept) {
					    return a.checkRcept - b.checkRcept;
					  }
					  // eduDt 오름차순 정렬 (문자열 비교)
					  return a.eduPeriodBegin.localeCompare(b.eduPeriodBegin);
					});
				} catch (e) {
				}
					*/
				vm_edulist.list = r.lctreList;
				
			}
		});
	}

	function fn_moveView(eduSeq) {
		location.href = "${utcp.ctxPath}/user/edu/eduView.do?eduSeq=" + eduSeq +"&srchCtgry=${param.srchCtgry}";
	}
</script>
<div class="eduWrap cf">

	<div class="listWrap listWrapTop listWrapBox">
		<div class="box_sort eduSch flex">
			<div class="box_cate">
			    <%-- 
			    --%>
			    <span>
			        <label for="srchCtgry" class="sound_only">교육카테고리 선택</label>
			        <select id="srchCtgry" class="srchCtgry">
			            <option value="-1">전체</option>
			            <c:forEach var="data" items="${cateList}" varStatus="stat">
			                <option value="${data.ctgrySeq}" <c:if test='${vo.srchCtgry == data.ctgrySeq}'>selected</c:if>>${data.ctgryNm}</option>
			            </c:forEach>
			        </select>
			    </span> 
			    <input type="hidden" id="srchCtgry" value="${vo.srchCtgry }"/>
			    <span id="vm-detailCtgry">
			        <label for="srchCtgry2" class="sound_only">2차과정 전체</label>
			        <select id="srchCtgry2" class="srchCtgry">
			            <option value="-1">2차과정 전체</option>
			        </select>
			    </span>
			    <span id="vm-detailCtgry2" style="">
			        <label for="srchCtgry3" class="sound_only">3차과정 전체</label>
			        <select id="srchCtgry3" class="srchCtgry">
			            <option value="-1">3차과정 전체</option>
			        </select> 
			    </span>
			</div>
			<script>
			$(function() {
			    var select1 = -1;
			    var selectList1 = [];
			    $('#srchCtgry').change(function() {
			        fn_callCategory(this.value);
			    });
			    function fn_callCategory(ctgrySeq) {
			        if (ctgrySeq == 0) return;
			        select1 = -1;
			        $.ajax({
			            data: {
			                ctgrySeq: ctgrySeq
			            },
			            url: '${utcp.ctxPath}/comm/api/callCategory.json',
			            async: false,
			            success: function(r) {
			                selectList1 = r.ctgryList;
			                select1 = -1;
			            }
			        });
			        $('#srchCtgry2').empty();
			        $('#srchCtgry2').append($('<option>', {
			            value: -1,
			            text: '2차과정 전체'
			        }));
			
			        $.each(selectList1, function(i, o) {
			            $('#srchCtgry2').append($('<option>', {
			                value: o.ctgrySeq,
			                text: o.ctgryNm
			            }));
			        });
			
			        
			    }
			
			    fn_callCategory('${vo.srchCtgry}');
			    select1 = '${vo.srchCtgry2 > 0?vo.srchCtgry2:-1}';
		        console.log(select1);
		        $('#srchCtgry2').val(select1);
			});
			
			
			$(function() {
			    var select2 = -1;
			    var selectList2 = [];
			    $('#srchCtgry2').change(function() {
			        fn_callCategory2(this.value);
			    });
			    function fn_callCategory2(ctgrySeq) {
			        if (ctgrySeq == 0) return;
			        select2 = -1;
			        $.ajax({
			            data: {
			                ctgrySeq: ctgrySeq
			            },
			            url: '${utcp.ctxPath}/comm/api/callCategory.json',
			            async: false,
			            success: function(r) {
			                selectList2 = r.ctgryList;
			                select2 = -1;
			                
			                $('#vm-detailCtgry2').hide();
			                if(ctgrySeq>0){
			                	$('#vm-detailCtgry2').show();
			                }
			            }
			        });
			        $('#srchCtgry3').empty();
			        $('#srchCtgry3').append($('<option>', {
			            value: -1,
			            text: '3차과정 전체'
			        }));
			
			        $.each(selectList2, function(i, o) {
			            $('#srchCtgry3').append($('<option>', {
			                value: o.ctgrySeq,
			                text: o.ctgryNm
			            }));
			        });
			
			        select2 = '${vo.srchCtgry3 > 0?vo.srchCtgry3:-1}';
			        $('#srchCtgry3').val(select2);
			    }
			
			    fn_callCategory2('${vo.srchCtgry2}');
			});
			</script>
			
			<div class="box_search">
				<label for="srchColumn" class="sound_only">교육제목 선택</label>
               <!--  <select id="srchColumn" name="eduAgency" class="srchColumn" title="교육제목을 선택 하실 수 있습니다." style="display:none;"> 
                	<option value="eduNm" >교육명</option>
                    <option value="instrctrNm" >강사명</option>
                </select> -->
				<input type="text" id="srchWrd" value="${vo.srchWrd }" />
				<label for="srchWrd" class="sound_only">검색어 입력</label>
				<button type="submit" onclick="fn_srch(); return false;">검색</button>
			</div>
		</div>
	</div>

</div>
<div class="listWrap edulistWrap">
	<div class="box_date flex">
		<fmt:formatDate value="${curDate}" var="curYear" pattern="yyyy" />
		<fmt:parseNumber value="${curYear}" var="year" />
		<select id="srchYear">
			<c:forEach begin="2023" end="${year+1 }" varStatus="stat">
				<c:set value="${year+2-stat.count  }" var="choiceYear" />
				<option value="${choiceYear}" <c:if test="${choiceYear == vo.srchYear}">selected</c:if>>${choiceYear}년</option>
			</c:forEach>
		</select>
		<div class="month_wrap">
			<ul class="box_month flex flex-tl">
				<li id="mon13"> 
					<span onclick="fn_monSrch('13'); return false;">전체</span>
				</li>
				<li id="mon01" <c:if test="${vo.srchMonth == '01'}">class="select"</c:if>>
					<span onclick="fn_monSrch('01'); return false;">1월</span>
				</li>
				<li id="mon02" <c:if test="${vo.srchMonth == '02'}">class="select"</c:if>>
					<span onclick="fn_monSrch('02'); return false;">2월</span>
				</li>
				<li id="mon03" <c:if test="${vo.srchMonth == '03'}">class="select"</c:if>>
					<span onclick="fn_monSrch('03'); return false;">3월</span>
				</li>
				<li id="mon04" <c:if test="${vo.srchMonth == '04'}">class="select"</c:if>>
					<span onclick="fn_monSrch('04'); return false;">4월</span>
				</li>
				<li id="mon05" <c:if test="${vo.srchMonth == '05'}">class="select"</c:if>>
					<span onclick="fn_monSrch('05'); return false;">5월</span>
				</li>
				<li id="mon06" <c:if test="${vo.srchMonth == '06'}">class="select"</c:if>>
					<span onclick="fn_monSrch('06'); return false;">6월</span>
				</li>
				<li id="mon07" <c:if test="${vo.srchMonth == '07'}">class="select"</c:if>>
					<span onclick="fn_monSrch('07'); return false;">7월</span>
				</li>
				<li id="mon08" <c:if test="${vo.srchMonth == '08'}">class="select"</c:if>>
					<span onclick="fn_monSrch('08'); return false;">8월</span>
				</li>
				<li id="mon09" <c:if test="${vo.srchMonth == '09'}">class="select"</c:if>>
					<span onclick="fn_monSrch('09'); return false;">9월</span>
				</li>
				<li id="mon10" <c:if test="${vo.srchMonth == '10'}">class="select"</c:if>>
					<span onclick="fn_monSrch('10'); return false;">10월</span>
				</li>
				<li id="mon11" <c:if test="${vo.srchMonth == '11'}">class="select"</c:if>>
					<span onclick="fn_monSrch('11'); return false;">11월</span>
				</li>
				<li id="mon12" <c:if test="${vo.srchMonth == '12'}">class="select"</c:if>>
					<span onclick="fn_monSrch('12'); return false;">12월</span>
				</li>
			</ul>
		</div>
		<a href="${utcp.ctxPath}/user/edu/contents/01_03.do" class="mgt10 btn_calendar on"><i class="fa-solid fa-calendar-days mgr5"></i>연간일정표</a>
		<!-- <a href="eduCal.do?srchCtgry=${param.srchCtgry }" class="mgt10 btn_calendar on"><i class="fa-solid fa-calendar-days mgr5"></i>연간일정표</a> -->
	</div>

	<form name="srchFrm" method="get" action="${utcp.ctxPath}/user/edu/eduList.do">
		<input type="hidden" name="srchYear" id="i_srchYear" value="${vo.srchYear}" />
		<!-- 첫호출시는 월 선택 안하기로 함,250319  
		<input type="hidden" name="srchMonth" id="i_srchMonth" value="${vo.srchMonth}" />
		-->
		<input type="hidden" name="srchMonth" id="i_srchMonth" value="" />
		
		<input type="hidden" name="srchCtgry" id="i_srchCtgry" value="${vo.srchCtgry}" />
		<input type="hidden" name="srchCtgry" id="i_srchCtgry2" value="${vo.srchCtgry2}" />
		<input type="hidden" name="srchCtgry" id="i_srchCtgry3" value="${vo.srchCtgry3}" />
		<input type="hidden" name="srchAgency" id="i_srchAgency" value="${vo.srchAgency}" />
		<input type="hidden" name="srchColumn" id="i_srchColumn" value="${vo.srchColumn}" />
		<input type="hidden" name="srchWrd" id="i_srchWrd" value="${vo.srchWrd}" />
<%-- 		<input type="hidden" name="srchSort" id="i_srchSort" value="${vo.srchSort}" /> --%>
		<input type="hidden" name="srchRcept" id="i_srchRcept" value="${vo.srchRcept}" />
	</form>

	<div class="box_list">
		<div class="sortWrap cf">
<!-- 			<ul> -->
<!-- 				<li> -->
<!-- 					<a href="javascript:$('#i_srchSort').val('eduNmAsc');fn_srch();">가나다순</a><span>|</span> -->
<!-- 				</li> -->
<!-- 				<li> -->
<!-- 					<a href="javascript:$('#i_srchSort').val('eduDtAsc');fn_srch();">교육일순</a><span>|</span> -->
<!-- 				</li> -->
<!-- 				<li> -->
<!-- 					<a href="javascript:$('#i_srchSort').val('rceptEndDtAsc');fn_srch();">접수마감일순</a> -->
<!-- 				</li> -->
<!-- 			</ul> -->
			<select onchange="$('#i_srchRcept').val(this.value); fn_srch();" style="width: 117px;">
				<option value="" <c:if test="${'' == vo.srchRcept}">selected</c:if>>전체</option>
				<option value="Y" <c:if test="${'Y' == vo.srchRcept}">selected</c:if>>신청 기간 중</option>
			</select>
		</div>
		<ul id="lctreView">
			<template v-if="list.length">
			<template v-for="o in list">
			<li class="flex" :onclick="'fn_moveView('+o.eduSeq+');'" >
				<div class="edu_info_wrap"> 
					<span class="img_logo"> <template v-if="o.imgUseYn == 'Y' && o.imgRename != ''"> 
					<img :src="'<spring:eval expression="@prop['cloud.cdn.url']"/>/upload/web/lctreThum/'+o.imgRename+'?'+o.updDe" alt="교육이미지" width="112px" height="149px" /> </template> 
					<template v-else> <img :src="'${utcp.ctxPath}/resources/admin/images/default_img.png?'+o.updDe" alt="교육이미지" width="112px" height="149px" /> </template>
					</span>
					<div class="text_title">
						<div class="cp" :onclick="'fn_moveView('+o.eduSeq+');'" style="cursor: pointer">
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
	                        <span class="eduType">
	                        {{o.ctg1Nm}}
	                        > {{o.ctg2Nm}}
	                        <template v-if="o.ctg3Nm">
	                        > {{o.ctg3Nm}}
	                        </template>
	                        </span> |
	                        <span class="eduType1">[{{o.lctreTypeNm }}]</span>
							</p>
							
							<p class="eduSbj">
							<strong v-html="o.eduNm+' 제'+o.eduTerm+'기'"></strong>
							<span v-html="o.addStatusBox "></span>
							</p>
							<p class="eduStnt">대상 : <span class="Stnt" >
							<span v-html="o.addTargetsStr"></span>
							 <template v-if="o.targetEtc">({{o.targetEtc}})</template>
							</span>
							</p>
						</div>
					</div>
				</div>
				<div class="text_info">
					<div>
						<div class="text_info_wrap">
						<span class="edudtType edudtType1"> <span class="edutxt_tit">신청</span> <b class=""> 
						<template v-if="o.rceptPeriodYn=='Y'"> 
						{{o.rceptPeriodBegin|fltDt2Str('YYYYMMDDHHmm','YYYY-MM-DD HH:mm')}} ~ {{o.rceptPeriodEnd|fltDt2Str('YYYYMMDDHHmm','YYYY-MM-DD HH:mm')}} 
						(<b class="dday">{{o.rceptPeriodEnd|fltDt2Dday('YYYYMMDDHHmm')}}</b>)
						</template> 
						<template v-else> 미설정 </template>
						</b>
						</span>
						<span class="edudtType edudtType2"> <span class="edutxt_tit">교육</span> <b class="">{{o.eduPeriodBegin}}
						<template v-if="o.eduPeriodBegin != o.eduPeriodEnd">
						 ~ {{o.eduPeriodEnd}}
						 </template>
						</b>
						</span>
						<span class="edudtType edudtType3"> <span class="edutxt_tit">인원</span><b>
						<template v-if="o.personnel==0">
						무제한
						</template>
						<template v-else>
						<template v-if="o.minPersonnel>0">
						{{o.minPersonnel}} ~
						</template>
						{{o.personnel}}명 모집 
						</template>
						<template v-if="o.rceptCntViewYn == 'Y'">
						/ <strong class="color">{{o.rceptCnt}}</strong>명 신청
						</template></b>
						</span>
					</div>
					</div>
				</div>
			
			</li>
			</template>
			</template>
			<template v-else>
			<li class="flex">
				<div class="no_data">개설된 교육이 없습니다.</div>
			</li>
			</template>
		</ul>
	</div>
</div>

<script>
	$(document).ready(function() {
		fn_srch();
		
		
		$('#srchCtgry').change(function(){
			fn_srch();
		});
		$('#srchCtgry2').change(function(){
			fn_srch();
		});
		$('#srchCtgry3').change(function(){
			fn_srch();
		});
	});
	//리스트
	var vm_edulist = new Vue({
		el : '#lctreView',
		data : {
			list : [],
			page : {}
		},
	});
</script>