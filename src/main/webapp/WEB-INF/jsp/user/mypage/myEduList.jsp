<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<script type="text/javascript">
	function fn_srch() {
		$("#i_srchCtgry").val($("#srchCtgry").val());
		$("#i_srchColumn").val($("#srchColumn").val());
		$("#i_srchWrd").val($("#srchWrd").val());
		$("#i_page").val(1);
		fn_listUpd();
	}

	function fn_page(page) {
		$("#i_page").val(page);
		fn_listUpd()
	}

	function fn_listUpd() {
		$.ajax({
			url : "${utcp.ctxPath}/user/mypage/myEduList.json",
			type : "POST",
			data : {
				"srchCtgry" : $("#i_srchCtgry").val(),
				"srchColumn" : $("#i_srchColumn").val(),
				"srchWrd" : $("#i_srchWrd").val(),
				"page" : $("#i_page").val(),
				"gubun" : "${gubun}"
			},
			dataType : 'json',
			crossDomain : true,
			success : function(r) {
				vm_rcepthistlist.list = r.dataList;
				vm_rcepthistlist.page = r.pageNavi;
			}
		});
	}

	function fn_openLastEdu() {
		window
				.open(
						'popup/myLastEdu.do',
						'openLastEdu',
						'toolbar=no,location=no,directories=no,status=,menubar=no,scrollbars=no,resizable=no,width=1280,height=840,top=0,left=150');
	}
</script>
<div class="listWrap" style="padding-top: 0">
	<div id="listDiv">
		<div class="box_sort flex listWrapBox">
			<div class="box_cate">
				<select id="srchCtgry" onchange="fn_srch()">
					<option value="0">전체</option>
					<c:forEach var="data" items="${eduList}" varStatus="stat">
						<option value="${data.ctgrySeq}"
							<c:if test='${vo.srchCtgry == data.ctgrySeq}'>selected</c:if>>${data.ctgryNm}</option>
					</c:forEach>
				</select>
			</div>
			<div class="box_search">
				<select id="srchColumn">
					<option value="eduNm"
						<c:if test='${vo.srchColumn == "eduNm"}'>selected</c:if>>교육명</option>
					<option value="instrctrNm"
						<c:if test='${vo.srchColumn == "instrctrNm"}'>selected</c:if>>강사명</option>
				</select> <input type="text" id="srchWrd" />
				<button type="submit" onclick="fn_srch(); return false;">검색</button>
			</div>
		</div>
		<form name="srchFrm" method="post">
			<input type="hidden" name="srchCtgry" id="i_srchCtgry"
				value="${vo.srchCtgry}" /> <input type="hidden" name="srchColumn"
				id="i_srchColumn" value="${vo.srchColumn}" /> <input type="hidden"
				name="srchWrd" id="i_srchWrd" value="${vo.srchWrd}" /> <input
				type="hidden" name="page" id="i_page" value="${vo.page}" />
		</form>
		<div class="box_list mypage_box_list">
			<ul id="lctreView">
				<template v-if="list.length">
				<li class="flex" v-for="o in list">
					<div class="edu_info_wrap">
						<span class="img_logo"> <template
								v-if="o.imgUseYn == 'Y' && o.imgRename != ''"> <img
								:src="'<spring:eval expression="@prop['cloud.cdn.url']"/>/upload/web/lctreThum/'+o.imgRename+'?'+o.updDe"
								alt="교육이미지" width="112px" height="149px" /> </template> <template v-else>
							<img
								:src="'${utcp.ctxPath}/resources/admin/images/default_img.png?'+o.updDe"
								alt="교육이미지" width="112px" height="149px" /> </template>
						</span>
						<div class="text_title">
							<div class="">
								<p class="eduTypeWrap">
									<span class="eduLabel"> <template v-if="o.tmSeq > 0">
										단체 </template> <template v-if="o.tmSeq == 0"> 개인 </template> | <template
											v-if="o.fee > 0 "> <b class="price pay">유료</b> </template> <template
											v-if="o.fee == 0 "> <b class="price free">무료</b> </template>
									</span> | <span class="eduType"> {{o.ctg1Nm}} > {{o.ctg2Nm}} <template
											v-if="o.ctg3Nm"> > {{o.ctg3Nm}} </template>
									</span> | <span class="eduType1">[{{o.lctreTypeNm }}]</span>
								</p> 
								<p class="eduSbj">
									<strong v-html="o.eduNm"></strong> <span v-html="o.addPassBox "
										class="compNo2"></span>
									<%-- <c:if test="${gubun ne 'Open' }">
								<span v-html="o.addPassBox "></span>
							</c:if>  
							<c:if test="${gubun eq 'Open' }">
								<span class="icon_tag comp" style="background-color: #eaeaea;color:gray;"><span v-html="o.addPassNm "></span></span>
							</c:if>  --%>
								</p>
								<p class="eduStnt">
									대상 : <span class="Stnt"> <span v-html="o.addTargetsStr"></span>
										<template v-if="o.targetEtc">({{o.targetEtc}})</template>
									</span>
								</p>

							</div>
						</div>
					</div>

					<div class="text_info">
						<div>
							<div class="text_info_wrap">
								<span class="edudtType edudtType1"> <span class="edutxt_tit">신청</span> <b class=""> <template
											v-if="o.rceptPeriodYn=='Y'">
										{{o.rceptPeriodBegin|fltDt2Str('YYYYMMDDhhmm','YYYY-MM-DD')}}
										~ {{o.rceptPeriodEnd|fltDt2Str('YYYYMMDDhhmm','YYYY-MM-DD')}}
										(<b class="dday">{{o.rceptPeriodEnd|fltDt2Dday('YYYYMMDDhhmm')}}</b>)
										</template> <template v-else> 미설정 </template>
								</b>
								</span> <span class="edudtType edudtType2"> <span class="edutxt_tit">교육</span> <b class=""> <template
											v-if="o.tmSeq > 0">
										{{o.tmEduDt|fltDt2Str('YYYYMMDD','YYYY-MM-DD')}}
										({{o.startTm|fltDt2Str('HHmm','HH:mm')}}~{{o.endTm|fltDt2Str('HHmm','HH:mm')}})
										</template> <template v-else> {{o.eduPeriodBegin}} <template
											v-if="o.eduPeriodBegin != o.eduPeriodEnd"> ~
										{{o.eduPeriodEnd}} </template> </template>
								</b>
								</span>
							</div>
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
							</span>
							 -->
							<button v-if="o.checkPass != 0" type="button"
								:onclick="'fn_openEdu('+o.eduSeq+')'"
								class="btn04 btn_blue scPopBtn">입장하기</button>
							<%--
							<button v-if="o.tmSeq==0" type="button" :onclick="'fn_cancelProc('+o.rceptSeq+','+o.eduSeq+')'" class="btn04 btn_orange mgl5 scPopBtn">교육취소</button>
							 --%>

						</div>
					</div>
				</li>
				</template>
				<template v-else>
				<li class="flex">
					<div class="no_data">진행중인 교육이 없습니다.</div>
				</li>
				</template>
			</ul>
		</div>

		<div class="board_pager" style="margin-bottom: 30px;">
			<div class="pagination" id="pageDiv">
				<template v-if="page.currentPageNo != 1"> <a
					href="javascript:;"
					:onclick="'fn_page('+(page.currentPageNo - 1)+'); return false;'">&laquo;</a>
				</template>
				<template
					v-for="o in (page.firstPageNoOnPageList , page.lastPageNoOnPageList)">
				<template v-if="o == page.currentPageNo"> <a
					href="javascript:;" class="active">{{o}}</a> </template> <template v-else>
				<a href="javascript:;" :onclick="'fn_page('+o+'); return false;'">{{o}}</a>
				</template> </template>
				<template
					v-if="page.currentPageNo != page.totalPageCount && page.totalPageCount > 0">
				<a href="javascript:;"
					onclick="fn_page('${pageNavi.currentPageNo + 1}'); return false;">&raquo;</a>
				</template>
			</div>
		</div>
	</div>
</div>

<div class="remodal messagePop messagePop1" data-remodal-id="md-success"
	role="dialog" aria-labelledby="modal1Title"
	aria-describedby="modal1Desc">
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC">알림</p>
		</div>
		<div class="modal-body">
			<p class="messageTxt">취소처리 되었습니다.</p>
		</div>
		<div class="modal-footer">
			<div class="tc">
				<button onclick="location.href='myEdu${gubun}List.do';"
					class="remodal-confirm btn02 btn_green">확인</button>
			</div>
		</div>
	</div>
</div>


<script>
	$(document).ready(function() {
		fn_listUpd();
	});
	//리스트
	var vm_rcepthistlist = new Vue({
		el : '#listDiv',
		data : {
			list : [],
			page : {}
		},
	});
</script>

<!-- 교육팝업 전용 -->
<script>
	function fn_openEdu(eduSeq) {
		window.open(
				'${utcp.ctxPath}/user/mypage/popup/myEdu${gubun}View.do?eduSeq='
						+ eduSeq, 'eduPop',
				'width=1280,height=860,left=100,top=50');
	}

	function fn_cancelProc(rceptSeq, eduSeq) {
		if (confirm("교육을 취소하시겠습니까?")) {
			$
					.ajax({
						url : "${utcp.ctxPath}/user/edu/cancelRcept.ajax",
						type : "post",
						data : {
							"eduSeq" : eduSeq,
							"rceptSeq" : rceptSeq
						},
						cache : false,
						async : true,
						success : function(r) {
							if (r.result == 1) {
								location.href = "#md-success";
								//$("#messageStr").html(r.msg);
								//location.href = "#cancel-edu-message";
							} else if (r.result == -1) {
								location.href = '${utcp.ctxPath}/user/pay/easypay/cancel.do?eduSeq='
										+ eduSeq;
							} else {
								alert(r.msg);
							}
						}
					});
		} else {
			return false;
		}
	}
</script>