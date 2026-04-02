<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>


<section class="pd025 pd200 po_re">
	<div class="po_re br5 bs_box cf">
		<form id="form-srch">
			<input type="hidden" name="pageNo"/>
			<div class="fl tr mgb15">
				<label for="srchWrd" class="sound_only">검색어입력</label>
				<input type="text" id="srchWrd" name="srchWrd" value="${param.srchWrd }" placeholder="수료증명 검색" class="btn04 btn_blackl tl mgr5">
				<button type="button" onclick="fn_srch();" class="btn04 btn_black fr">검색</button>
			</div>
		</form>
		<div class="fbCon">
			<table class="w100 tb03 tc">
				<c:forEach items="${data.list }" var="o">
				<tr>
					<td>${o.passIdx }</td>
					<td class="tl"><a href="javascript:window.open('passTplPopup.do?passIdx=${o.passIdx}','certPopup','resizable=no,width=700, height=1000');">
						<span class="dp_ib fl font_22 fw_500 pdl15 pdr15">${o.title }</span></a> 
						<span class="fl dp_ib font_20">
					</span> 
					<span class="fl cb dp_ib font_14 pdl15 mgr20">등록자 : 관리자</span> <span class="fl dp_ib font_14">등록일시 : ${utcp.convDateToStr(o.regDt,'yyyy.MM.dd') }</span>
					</td>
					<%-- <td class="tr">
						<button onclick="location.href='passTplReg.do?passIdx=${o.passIdx }';" class="dp_b cb fr del mgt5 pdr15">
							<i class="fas fa-edit fc_red"></i>
						</button>
					</td> --%>
				</tr>
				</c:forEach>
			</table>
			<c:set var="pageNavi" value="${data.pageNavi }" />
			<%request.setAttribute("pageNavi", pageContext.getAttribute("pageNavi"));%>
			<jsp:include page="/WEB-INF/jsp/admin/bbs/pageNavi.jsp" />
		</div>
	</div>
</section>

<script>
function fn_srch(){
	fnc_paging(1);
}
function fnc_paging(pageNo){
	$('input[name=pageNo]').val(pageNo);
	$('#form-srch').submit();
}
</script>

<div class="remodal messagePop messagePop2" data-remodal-id="md-feedback-del-alert" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc">
	<input type="hidden" id="feedback-del-idx"/>
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC">알림</p>
		</div>
		<div class="modal-body">
			<p class="messageTxt" id="messageStr">설문지를 삭제 하시겠습니까?</p>
		</div>
		<div class="modal-footer">
			<div class="tc">
				<button onclick="fn_delete()" data-remodal-action="cancel" class="remodal-confirm btn02 btn_orange">확인</button>
				<button data-remodal-action="cancel" class="remodal-confirm btn02 btn_gray">취소</button>
			</div>
		</div>
	</div>
</div>

