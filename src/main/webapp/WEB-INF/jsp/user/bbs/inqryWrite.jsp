<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<script src="${utcp.ctxPath}/resources/ckeditor4_full/ckeditor.js"></script>
<script>
	$(function() {
		CKEDITOR.replace('ckeditCn', {
			filebrowserUploadUrl : '${utcp.ctxPath}/editot/popupUpload.json?gubun=board&prefixStr=inqry_${vo.idx}',
			height : 400
		});
	});
	
	var form_node='';
	function fn_reg() {
		var inst = $('[data-remodal-id=bbs-message]').remodal()
		form_node = $('input[name=regNk]');
		if (form_node.val() == '') {
			$("#messageStr").html("성명은 필수입력입니다.");
			inst.open();
			return false;
		}
		<c:if test="${empty sessionScope.sessionUserInfo}">
		form_node = $('input[name=password]');
		if (form_node.val() == '') {
			$("#messageStr").html("비밀번호는 필수입력입니다.");
			inst.open();
			return false;
		}
		</c:if>
		/* 
		form_node = $('input[name=contact]');
		if (form_node.val() == '') {
			$("#messageStr").html("휴대폰번호 는 필수입력입니다.");
			inst.open();
			return false;
		}
		var emailRule = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;//이메일 정규식
		form_node = $('input[name=email]');
		if (!emailRule.test(form_node.val())) {
			$("#messageStr").html("이메일 형식이 올바르지 않습니다.");
			inst.open();
			return false;
		}
		 */
		form_node = $('input[name=title]');
		if (form_node.val() == '') {
			$("#messageStr").html("문의제목은 필수입력입니다.");
			inst.open();
			return false;
		}
		form_node = CKEDITOR.instances.ckeditCn;
		if (form_node.getData() == "") {
			$("#messageStr").html("문의내용은 필수입력입니다.");
			inst.open();
			return;
		} else {
			$('#form-content').val(CKEDITOR.instances.ckeditCn.getData());
		}
		form_node = $('input[name=title]');
		//if (!$('input[name=agreeGb]').is(':checked')) {
			//$("#messageStr").html('개인정보 수집 및 이용목적에 동의하셔야 합니다.');
			//inst.open();
			//return;
		//}
		$('#form_reg').attr('action', 'inqryWriteProc.do')
		$('#form_reg').submit();
	}
</script>
<div class="listWrap" style="padding-top: 0px">
	<div class="online_text qnatext">
		<img src="${utcp.ctxPath}/resources/user/image/icon/qnaicon.webp" alt="" />
	 	<p>문의하신 사항은 검토 후 최대한 빠르게 회신 드리도록 노력 하겠습니다.</p>
	</div>
	<form name="form_reg" id="form_reg" method="post">
		<input type="hidden" name="idx" value="${vo.idx }" /> 
		<table class="tb04">
			<tbody>
				<tr>
					<th class="">성명
					</th>
					<td class="" colspan=""><input type="text" name="regNk" value="${empty inqryMap.regNk?sessionScope.sessionUserInfo.userNm:inqryMap.regNk}" /></td>
					<c:if test="${empty sessionScope.sessionUserInfo }">
						<th class="">비밀번호
						</th>
						<td class="" colspan=""><input type="password" name="password" value="${inqryMap.password }" autocomplete="new-password" /></td>
					</c:if>
				</tr>
				<%-- 
				<tr>
					<th class="">휴대폰번호 
					</th>
					<td class="tl" colspan="3"><input type="text" name="contact" value="${empty inqryMap.contact?userInfo.mobile:inqryMap.contact}" maxlength="14" /></td>
				</tr>
				<tr>
					<th class="">이메일
					</th>
					<td class="" colspan="3"><input type="text" name="email" value="${empty inqryMap.email?userInfo.email:inqryMap.email}" maxlength="50" /></td>
				</tr>
				 --%>
				<tr>
					<th class="">문의 제목</th>
					<td class="" colspan="3"><input type="text" name="title" value="${inqryMap.title }" /></td>
				</tr>
				<tr>
					<th class="">문의 내용</th>
					<td class="" colspan="3"><textarea id="ckeditCn" name="ckeditCn" placeholder="내용">${inqryMap.content }</textarea><input type="hidden" id="form-content" name="content" /></td>
				</tr>
				<!-- 
				<tr>
					<th>정보동의</th>
					<td colspan="3">
						<div class="license_text">
							개인정보의 수집 및 이용에 대한 안내 <br> ● 개인정보의 수집.이용목적 <br> √ 원활한 상담을 위한 의사소통 경로 확보 <br>
						</div> <input type="checkbox" name="agreeGb" value="1"> 개인정보 수집 및 이용목적에 동의합니다.
					</td>
				</tr>
				 -->
			</tbody>
		</table>
		<div class="cal_btn">
			<a onclick="fn_reg();" href="#none" class="btn_calendar2">문의하기</a>
		</div>
	</form>
</div>


<div class="remodal messagePop messagePop2" data-remodal-id="bbs-message" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc" data-remodal-options="closeOnOutsideClick: false">
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC">알림</p>
		</div>
		<div class="modal-body">
			<p class="messageTxt" id="messageStr">
				
			</p>
		</div>
		<div class="modal-footer">
			<div class="tc">
				<button onclick="form_node.focus();" data-remodal-action="cancel" class="remodal-confirm btn02 btn_orange">확인</button>
			</div>
		</div>
	</div>
</div>