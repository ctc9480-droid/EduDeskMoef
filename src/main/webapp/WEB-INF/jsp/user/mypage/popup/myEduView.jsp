
<%@page import="com.educare.edu.member.service.SessionUserInfoHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<form id="myEduForm" name="myEduForm">
	<input type="hidden" name="tabNum" value="${tabNum}" /> <input
		type="hidden" name="tabAction" value="${tabAction}" id="tabAction" />
	<input type="hidden" name="boardIdx"
		value="${not empty param.boardIdx?param.boardIdx:0 }" id="boardIdx" />
	<input type="hidden" name="pageNo"
		value="${not empty param.pageNo?param.pageNo:1 }" /> <input
		type="hidden" name="eduSeq" id="edu_seq" value="${lctre.eduSeq }" />
	<input type="hidden" name="rceptSeq" id="rcept_seq"
		value="${stdnt.rceptSeq }" />
</form>

<div id="wrap">
	<div id="content" class="pdt0 w100 myEduView">
		<div class="listWrap pdt0 mypopWrap">
			<div id="listDiv">
				<div class="box_list">
					<ul id="lctreView">
						<li class="flex room">
							<div class="roomTit cf">
								강의실 <a href='javascript:window.close();'
									class="roomOut fc_white fr">강의실나가기<i
									class="fa-solid fa-x pdl3"></i></a>
							</div>
							<div class="flex">
								<div class="img_logo">
									<c:choose>
										<c:when
											test="${lctre.imgUseYn eq 'Y' and not empty lctre.imgRename}">
											<img
												src="<spring:eval expression="@prop['cloud.cdn.url']"/>/upload/web/lctreThum/${lctre.imgRename}?${utcp.convDateToStr(lctre.updDe,'yyyyMMddHHmmss')}"
												alt="교육이미지" width="112px" height="112px" />
										</c:when>
										<c:otherwise>
											<img
												src="${utcp.ctxPath}/resources/admin/images/default_img.png?${utcp.convDateToStr(lctre.updDe,'yyyyMMddHHmmss')}"
												alt="교육이미지" width="112px" height="112px" />
										</c:otherwise>
									</c:choose>
								</div>
								<div class="myedu_text">
									<div class="edu_text_wrap flex">
										<div class="text_title">
											<div class="">
												<p class="eduTypeWrap">
													<span class="eduLabel"> <c:if
															test="${lctre.fee > 0 }">
															<b class="price pay">유료</b>
														</c:if> <c:if test="${lctre.fee == 0 }">
															<b class="price free">무료</b>
														</c:if>
													</span> | <span class="eduType">${lctre.ctg1Nm}</span> | <span
														class="eduType1">[${lctre.lctreTypeNm }]</span>
												</p>

												<p class="eduSbj">
													<strong class="Sbj ">${lctre.eduNm} </strong>
													${lctre.addStatusBox }
												</p>
											</div>

										</div>
										<div class="text_info">
											<div>
												<span>  <span class="edutxt_tit">교육</span> <b class="fw_500">
														${lctre.eduPeriodBegin } ~ ${lctre.eduPeriodEnd } </b>
												</span> <br />
											</div>
										</div>
									</div>
									<div class="dp_b text_progress">
										<div class="edu_progress">
											<c:if test="${lctre.lctreType == 3 }">
												<span class="edudtType3"> 나의 진도율 : <%--  <b class="fc_orange">${stdnt.addMovRatio }</b>% --%>
													${stdnt.addMovRatio }%
												</span>
											</c:if>
											<c:if test="${lctre.lctreType != 3 }">
												<span class="edudtType4"> 출석 : ${rollbook.stdntList[0].attendCnt } / ${rollbook.stdntList[0].qrCnt }
												</span>
												<c:if test="${lctre.passAttPoint > 0 }">
												</c:if>
												<c:if test="${lctre.passAsgPoint > 0 }">
													<span class="edudtType4"> 과제점수 :
														${stdnt.workshopScore } </span>
												</c:if>
												<c:if test="${lctre.passTestPoint > 0 }">
													<span class="edudtType4"> 시험점수 : ${stdnt.examScore }
													</span>
												</c:if>
											</c:if>
											<span class="edudtType5"> 상태 : <b>${lctre.addPassNm }</b>
											</span>
										</div>
									</div>
									<div class="">
										<c:if test="${lctre.lctreType == 0 }">
											<%-- <button onclick="fn_openCheckList2(${param.eduSeq})" type="button" class="btn04 btn_whitel attBookPopBtn">출석부</button> --%>
										</c:if>
									</div>
								</div>
							</div>
						</li>
					</ul>
				</div>
				<!-- //box_list -->
				<div class="board_tab_onoff tapWrap">
					<ul class="board_tab">
						<li id="tab1" class="${tabNum == 1?'active':'' }">
							<p>
								<a href="javascript:;" onclick="fn_tab('1','1'); return false;">
									${lctre.lctreType eq 3?'학습하기':'교육시간표'}</a>
							</p>
						</li>
						<li id="tab2" class="${tabNum == 2?'active':'' }">
							<p>
								<a href="javascript:;" onclick="fn_tab('2','2'); return false;">교육
									상세</a>
							</p>
						</li>
						<li id="tab4" class="${tabNum == 4?'active':'' }">
							<p>
								<a href="javascript:;"
									onclick="fn_tab('4','boardList'); return false;">학습자료실</a>
							</p>
						</li>

						<%--  
						<li id="tab6" class="${tabNum == 6?'active':'' }">
							<p><a href="javascript:;" onclick="fn_tab('6','communityList'); return false;">커뮤니티</a></p>
						</li>
						--%>
						<li id="tab5" class="${tabNum == 5?'active':'' }">
							<p>
								<a href="javascript:;" onclick="fn_tab('5','5'); return false;">과제등록</a>
							</p>
						</li>

						<li id="tab8" class="${tabNum == 8?'active':'' }">
							<p>
								<a href="javascript:;" onclick="fn_tab('8','8'); return false;">설문</a>
							</p>
						</li>
						<li id="tab7" class="${tabNum == 7?'active':'' }">
							<p>
								<a href="javascript:;" onclick="fn_tab('7','7'); return false;">시험</a>
							</p>
						</li>
						<li id="tab3" class="${tabNum == 3?'active':'' }">
							<p>
								<a href="javascript:;" onclick="fn_tab('3','3'); return false;">수료증
									발급</a>
							</p>
						</li>
						<%-- dykim, 240618 
						<li id="tab9" class="${tabNum == 9?'active':'' }"> 
							<p><a href="javascript:;" onclick="fn_tab('9','homeworkList'); return false;">과제등록2</a></p>
						</li>
						--%>
					</ul>
					<!--// board_tab_con //-->
					<div class="board_tab_con">
						<c:import url="/user/mypage/popup/myEduView_inc_${tabAction }.do" />
					</div>
					<!--// board_tab_con //-->
				</div>


				<!-- //board_tab_onoff -->
			</div>
		</div>
	</div>
</div>
<script>
$(document).ready(function(){
	//$('#tab1,#tab2,#tab3,#tab4,#tab5,#tab6').removeClass('active');
	//$('#tab${tabNum}').addClass('active');
});
function fn_tab(n,action){
	
	$('input[name=tabNum]').val(n);
	$('input[name=tabAction]').val(action);
	$('input[name=eduSeq]').val(document.querySelector("#edu_seq").value);
	$('input[name=boardIdx]').val(document.querySelector("#boardIdx").value);
	$('#myEduForm').submit();
}
function fn_certView(eduSeq,subSeq,mode) {

	var popupWidth = 890;
	var popupHeight = 940;

	var popupX = (window.screen.width / 2) - (popupWidth / 2);
	var popupY = (window.screen.height / 2) - (popupHeight / 2);

	var url = "${utcp.ctxPath}/user/edu/certView.do?eduSeq=" + eduSeq + "&subSeq="+subSeq+"&mode="+mode;

	window.open(url, "증빙서류", 'status=no, height=' + popupHeight
			+ ', width=' + popupWidth + ', left=' + popupX + ', top='
			+ popupY);
}
function fn_callTab3(){
	localStorage.setItem('myEduView','tab3');
	location.reload();
}
if(localStorage.getItem('myEduView')=='tab3'){
	fn_tab(3);
}
localStorage.removeItem('myEduView');
</script>

<!--// feedback //-->
<div class="remodal messagePop messagePop1"
	data-remodal-id="md-feedback-alert" role="dialog"
	aria-labelledby="modal1Title" aria-describedby="modal1Desc">
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC">알림</p>
		</div>
		<div class="modal-body">
			<p class="messageTxt">제출하시겠습니까? 다시 제출하실 수 없습니다.</p>
		</div>
		<div class="modal-footer">
			<div class="tc">
				<button onclick="fn_save_feedback_answer()"
					class="remodal-confirm btn02 btn_green">확인</button>
				<button onclick="fn_feedbackReg(1)"
					class="remodal-confirm btn02 btn_green">취소</button>
			</div>
		</div>
	</div>
</div>
<div class="remodal test2" data-remodal-id="md-feedbackReg"
	role="dialog" aria-labelledby="modal1Title"
	aria-describedby="modal1Desc" id="vm-feedbackReg">
	<button data-remodal-action="close" class="remodal-close"
		aria-label="Close"></button>
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit tl fdb">
				만족도조사 <span class="cb dp_b font_14 pdt10">교육의 질 향상을 위한 설문입니다.
					성실하게 답변해주시면 감사하겠습니다.</span>
			</p>
		</div>
		<div class="modal-body">
			<div class="fbScore">
				<comp-table3 :fb="fb" :list="qtList"></comp-table3>
			</div>
		</div>
		<div class="modal-footer">
			<div class="tc">
				<button onclick="location.href='#md-feedback-alert'"
					class="remodal-confirm btn01 btn_blue">제출</button>
			</div>
		</div>
	</div>
</div>
<div class="remodal messagePop messagePop1"
	data-remodal-id="md-feedback-success" role="dialog"
	aria-labelledby="modal1Title" aria-describedby="modal1Desc">
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC">알림</p>
		</div>
		<div class="modal-body">
			<p class="messageTxt">만족도조사에 참여해주셔서 감사합니다.</p>
		</div>
		<div class="modal-footer">
			<div class="tc">
				<button data-remodal-action="confirm"
					class="remodal-confirm btn02 btn_green">확인</button>
			</div>
		</div>
	</div>
</div>
<script type="text/x-template" id="tpl-table3">
				<table width="100%" class="tb03 tc">
					<template v-for="(o,i) in list">
					<template v-if="o.qt.qtType==1||o.qt.qtType==2">
					<tr>
						<td class="tl">
						<span class="dp_b font_14 fw_500 mgb10">{{i+1}}. {{o.qt.question}}</span> 
						<span class="cb dp_b font_14 pdl15 mgr20"> 
						<template v-for="(o2,i2) in o.chList">
							<label :for="'choice-'+i+'-'+i2" class="dp_ib mgr15">
							<template v-if="o.qt.qtType==1">
								<input type="radio" :id="'choice-'+i+'-'+i2" class="mgr5" :name="'choice_'+i" :value="o2.chIdx" v-model="o.qt.asChIdx"/>{{o2.choice}}
							</template>							
							<template v-if="o.qt.qtType==2">							
								<input type="checkbox" :id="'choice-'+i+'-'+i2" class="mgr5" :name="'choice_'+i" :value="o2.chIdx" v-model="o.qt.asChIdx2"/>{{o2.choice}}
							</template>							
							</label> 
						</template>
						</span></td>
					</tr>
					</template>
					<template v-else>
					<tr>
						<td class="tl"><span class="dp_b font_14 fw_500 mgb10">{{i+1}}. {{o.qt.question}}</span> 
						<span class="cb dp_b font_14 pdl15 mgr20"> <textarea cols="100" rows="5" class="pd5" style="height: auto;" v-model="o.qt.answer"></textarea>
						</span></td>
					</tr>
					</template>
					</template>
				</table>
</script>
<script>
	Vue.component('comp-table3', {
		template : '#tpl-table3',
		props : [ 'fb', 'list' ],
	});
	var vm_feedbackReg = new Vue({
		el : '#vm-feedbackReg',
		data : {
			fb : {},
			qtList : [],
		},
	});
	function fn_feedbackReg() {
		var feedbackPopup = window.open('${utcp.ctxPath}/user/feedback/feedbackPopup.do?eduSeq='+$('#edu_seq').val(),'feedbackPopup','width=1280,height=720resizable=no, scrollbars=yes');
	}
	function fn_save_feedback_answer() {

		var formData = {};
		formData.eduSeq = $('#edu_seq').val();
		formData.fb = vm_feedbackReg.fb;
		formData.qtList = vm_feedbackReg.qtList;
		$.ajax({
			type : 'post',
			contentType : 'application/json',
			url : '${utcp.ctxPath}/user/feedback/saveFeedbackAnswer.json',
			data : JSON.stringify(formData),
			success : function(r) {
				console.log(r);
				location.href = '#md-feedback-success';
			},
		});
	}
</script>

<!-- 신청취소 -->
<div class="remodal messagePop messagePop2"
	data-remodal-id="md-cancel-1" role="dialog"
	aria-labelledby="modal1Title" aria-describedby="modal1Desc">
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC">교육신청취소</p>
		</div>
		<div class="modal-body">
			<p class="messageTxt">취소하시겠습니까?</p>
		</div>
		<div class="modal-footer">
			<div class="tc">
				<button onclick="fn_cancelProc(); return false;"
					class="remodal-confirm btn02 btn_orange">확인</button>
				<button data-remodal-action="cancel"
					class="remodal-cancel btn02 btn_gray">취소</button>
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
				<button onclick="fn_reload(); return false;"
					class="remodal-confirm btn02 btn_green">확인</button>
			</div>
		</div>
	</div>
</div>
<div class="remodal messagePop messagePop2"
	data-remodal-id="cancel-edu-message" role="dialog"
	aria-labelledby="modal1Title" aria-describedby="modal1Desc">
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC">알림</p>
		</div>
		<div class="modal-body">
			<p class="messageTxt" id="messageStr"></p>
		</div>
		<div class="modal-footer">
			<div class="tc">
				<button onclick="location.href='#'"
					class="remodal-confirm btn02 btn_orange">확인</button>
			</div>
		</div>
	</div>
</div>
<script>
function fn_cancel(){
	location.href= "#md-cancel-1";
}
function fn_cancelProc(){
	var eduSeq = $("#edu_seq").val(); 
	var rceptSeq = $("#rcept_seq").val(); 
	$.ajax({
		url: "${utcp.ctxPath}/user/edu/cancelRcept.ajax",
		type: "post",
		data: {
			"eduSeq" : eduSeq,
			"rceptSeq" : rceptSeq
		},
		cache: false,
		async: true,
		success: function(r) {
			if(r.result == 1){
				alert('교육취소하였습니다.');
				opener.parent.location.reload();
				window.close();
			}else{
				alert(r.msg);
			}
		}
	});
}
function fn_reload(){
	location.href = "${utcp.ctxPath}/user/mypage/myEdu${gubun}List.do";
}


</script>
<%-- 이중으로 등록확인되어 주석처리 ,모니터링 필요,241007kbj
<jsp:include page="/WEB-INF/jsp/layout/user/modal.jsp" />
 --%>