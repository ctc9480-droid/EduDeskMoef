<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>


<section class="pd025 pd200 po_re">
	<div class="po_re br5 bs_box cf">
		<form id="form-srch">
			<input type="hidden" name="pageNo"/>
			<div class="fl tr mgb15">
				<label for="srchWrd" class="sound_only">검색어입력</label>
				<input type="text" id="srchWrd" name="srchWrd" value="${param.srchWrd }" placeholder="만족도조사명 검색" class="btn04 btn_blackl tl mgr5">
				<button type="button" onclick="fn_srch();" class="btn04 btn_black fr">검색</button>
			</div>
		</form>
	
		<div class="tr fbTxt">
			<button class="btn01 btn_green fbBtn" onclick="location.href='feedbackReg.do';">
				<i class="fas fa-file-signature"></i> 만족도조사 만들기
			</button>
		</div>
		<div class="fbCon">
			<table width="100%" class="tb03 tc">
				<c:forEach items="${data.list }" var="o">
				<tr>
					<td class="tl"><a href="javascript:window.open('feedbackPopup.do?fbIdx=${o.idx}','feedbackPopup','resizable=no');">
						<span class="dp_ib fl font_22 fw_500 pdl15 pdr15">${o.title }</span></a> 
						<span class="fl dp_ib font_20">
					(객관식 <strong class="fw_400">${o.qtType1Cnt }</strong>개 / 주관식 <strong class="fw_400">${o.qtType0Cnt }</strong>개)
					</span> 
					<span class="fl cb dp_ib font_14 pdl15 mgr20">등록자 : ${o.regNm }</span> <span class="fl dp_ib font_14">등록일시 : ${utcp.convDateToStr(o.regTime,'yyyy.MM.dd') }</span>
					</td>
					<td class="tr">
						<c:if test="${sessionScope.sessionUserInfo.userId eq o.regId }">
						<%-- <button onclick="location.href='feedbackReg.do?idx=${o.idx }';" class="dp_b cb fr del mgt5 pdr15">
							<i class="fas fa-edit fc_red"></i>
						</button>
						<button onclick="location.href='#md-feedback-del-alert';$('#feedback-del-idx').val('${o.idx}');" class="dp_b cb fr del mgt5 pdr15">
							<i class="fas fa-trash-alt fc_red"></i>
						</button> --%>
						<a href="javascript:location.href='feedbackReg.do?idx=${o.idx }';" class="btn04 btn_blue">
						수정
						</a>
						<a href="javascript:location.href='#md-feedback-del-alert';$('#feedback-del-idx').val('${o.idx}');" class="btn04 btn_orange">
						삭제
						</a>
						</c:if>
						<a href="javascript:fn_copyFeedback(${o.idx });" class="btn04 btn_green">
						복사
						</a>
					</td>
				</tr>
				</c:forEach>
			</table>
			
			<c:set var="pageNavi" value="${data.pageNavi }" />
			<%request.setAttribute("pageNavi", pageContext.getAttribute("pageNavi"));%>
			<jsp:include page="/WEB-INF/jsp/admin/bbs/pageNavi.jsp" />
		</div>
	</div>
</section>

<div class="remodal messagePop messagePop2" data-remodal-id="md-feedback-del-alert" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc">
	<input type="hidden" id="feedback-del-idx"/>
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC">알림</p>
		</div>
		<div class="modal-body">
			<p class="messageTxt" id="messageStr">만족도조사를 삭제 하시겠습니까?</p>
		</div>
		<div class="modal-footer">
			<div class="tc">
				<button onclick="fn_delete()" data-remodal-action="cancel" class="remodal-confirm btn02 btn_orange">확인</button>
				<button data-remodal-action="cancel" class="remodal-confirm btn02 btn_gray">취소</button>
			</div>
		</div>
	</div>
</div>

<script>
function fn_srch(){
	fnc_paging(1);
}
function fnc_paging(pageNo){
	$('input[name=pageNo]').val(pageNo);
	$('#form-srch').submit();
}

function fn_delete(){
	$.ajax({
		type : 'post',
		url:'feedbackDelProc.json',data:{idx:$('#feedback-del-idx').val()},
		success:function(r){
		 	if(r.result==1){
		 		location.reload();
		 	}else{
		 		location.href='#md-alert';
		 		if(r.result==0)
		 			vm_alert.msg='오류가 발생하였습니다.';
		 		else if(r.result==2)
		 			vm_alert.msg='이미 만족도조사를 사용하는 강의가 있어 삭제 할 수 없습니다.';
		 		else if(r.result==3)
		 			vm_alert.msg='본인이 작성한 만족도조사가 아닙니다.';
		 	}
		 	
		}
	});
}

function fn_copyFeedback(idx){
	location.href = "feedbackCopy.do?copyIdx="+idx;
}
</script>
