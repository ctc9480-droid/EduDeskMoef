<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<script>
	//<![CDATA[
	function fn_del() {
		$('[data-remodal-id=del]').remodal().open();
	}
	function fn_del_final() {
		$('#form_view').attr('action', 'inqryDeleteProc.do');
		$('#form_view').submit();
	}
	function fn_upd_inqry(){
		$('#form_view').attr('action', 'inqryWrite.do');
		$('#form_view').submit();
	}
	//]]>
</script>
<form id="form_view" method="post">
	<input type="hidden" name="idx" value="${inqryMap.idx }" /> 
	<input type="hidden" name="password" value="${inqryMap.password }"/>
</form>
<div class="listWrap" style="padding-top: 0px">
	<div class="notice-box"><p>문의하신 사항은 검토 후 최대한 빠르게 <br class="mo">회신 드리도록 노력 하겠습니다.</p></div>

	<table class="tb04">
		<tr>
			<th class="">성명
			</td>
			<td class="tl" colspan="3">${inqryMap.regNm }</td>
		</tr>
		<%-- <tr>
			<th class="">휴대폰번호 </th>
			<td class="" colspan="3">${inqryMap.contact }</td>
		</tr>
		<tr>
			<th class="">이메일</th>
			<td class="" colspan="3">${inqryMap.email }</td>
		</tr> --%>
		<tr>
			<th class="">문의 제목</th>
			<td class="" colspan="3">${inqryMap.title }</td>
		</tr>
		<tr>
			<th class="">문의 내용</th>
			<td class="" colspan="3">${inqryMap.content }</td>
		</tr>
		<c:if test="${inqryMap.status eq '2' }">
			<tr>
				<th class="inq_ans">답변 내용</th>
				<td class="" colspan="3">${utcp.convNewLine(inqryMap.answer) }</td>
			</tr>
		</c:if>
	</table>
	<div class="tb_btn">
		<ul>
			<li class="left"><a href="inqryList.do">목록</a></li>
			<c:if test="${inqryMap.status eq 1 }">
				<c:if test="${inqryMap.regId eq sessionScope.sessionUserInfo.userId or empty inqryMap.regId}">
					<c:if test="${inqryMap.status == 1 }">
						<li class="right" style="margin-left: 10px;"><a href="#none" onclick="fn_upd_inqry();">수정</a></li>
					</c:if>
					<li class="right" style="margin-left: 10px;"><a href="#none" onclick="fn_del();">삭제</a></li>
				</c:if>
			</c:if>
		</ul>
	</div>
</div>

<div class="remodal messagePop messagePop2" data-remodal-id="del" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc" data-remodal-options="closeOnOutsideClick: false">
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC">알림</p>
		</div>
		<div class="modal-body">
			<p class="messageTxt" id="messageStr">삭제하시겠습니까?</p>
		</div>
		<div class="modal-footer">
			<div class="tc">
				<button data-remodal-action="cancel" class="remodal-confirm btn02 btn_orange">취소</button>
				<button onclick="fn_del_final();" data-remodal-action="" class="remodal-confirm btn02 btn_orange">확인</button>
			</div>
		</div>
	</div>
</div>
