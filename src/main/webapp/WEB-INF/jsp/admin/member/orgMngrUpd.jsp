<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp" %>
<script>
	$(function(){	
		//기관이름 중복 체크
		$('#orgNm').keyup(function(){
			var orgNm = this.value;
			var orgCd = $('input[name=orgCd]').val();
			$.ajax({
				data:{orgNm:orgNm,orgCd:orgCd},url:'checkOrgNm.json',type:'post',
				success:function(r){
					if(r.result!=''){
						$('#msg-orgnm').html(r.result);
						$('#checkOrgNm').val('N');
					}else{
						$('#msg-orgnm').text('');
						$('#checkOrgNm').val('Y');
					}
				}
			});
		});
	});
	
	function fn_updPw(){
		$.ajax({
			url: "${utcp.ctxPath}/admin/member/orgMngrPwUpd.json",
			type: "post",
			data: {
				"userPw" : $("#userPw").val()
			},
			cache: false,
			async: true,
			success: function(r) {
				if(r.isAdmin){
					if(r.isSuccess){
						$("#successStr").html("비밀번호가 변경되었습니다.");
						location.href = "#success";
					}else{
						$("#messageStr").html(r.message);
						location.href = "#message";
						return;
					}
				}else{
					location.href = "#authMessage";
				}
			}
		});
	}
	
	function fn_confirmMsg(command){
		var proc = "";
		if(command == "U"){
			proc = "#confirmUpd";
		}else{
			proc = "#confirmDel";
		}
		location.href = proc;
	}
	
	function fn_upd(){
		//var formData = $("#mngrRgsFrm").serializeArray();
		
		var formData = new FormData($('#mngrRgsFrm')[0]);
		console.log(formData);
		formData.append("jigin", $("#jigin")[0].files[0]);
		//return;
		
		$.ajax({
			contentType : false,
			processData : false,
			url: "${utcp.ctxPath}/admin/member/orgMngrUpdProc.json",
			type: "post",
			data: formData,
			cache: false,
			async: true,
			success: function(r) {
				if(r.isAdmin){
					if(r.isSuccess){
						$("#successStr").html("수정되었습니다.");
						location.href = "#success";
					}else{
						$("#messageStr").html(r.message);
						location.href = "#message";
						return;
					}
				}else{
					location.href = "#authMessage";
				}
			}
		});
	}
	
	function fn_reload(){
		location.href = "/admin/member/orgMngrUpd.do";
	}
</script>

<c:set var="user" value="${vo.userInfo}"/>

<section class="pd025 pd200 po_re">
	<div class="po_re br5 bs_box">
		<table width="100%" class="tb02 tc">
		<form method="post" id="mngrRgsFrm">
		<caption class="sound_only">관리자수정테이블</caption>		
			<tbody>
				<tr>
					<th class="tdbg3 tc"><label for="userNm">성&nbsp;&nbsp;&nbsp;명</label></th>
					<td class="tl" colspan="3">
						<input type="text" class="ip1" id="userNm" name="userNm" value="${user.userNm}" />
					</td>
				</tr>
				<tr>
					<th class="tdbg3 tc">아이디</th>
					<td class="tl" colspan="3">
						<input type="hidden" class="ip1" id="userId" name="userId" value="${user.userId}" readonly />
						${user.loginId }
					</td>									
				</tr>
				<tr>
					<th class="tdbg3 tc">기관코드</th>
					<td class="tl" colspan="3">
						${user.orgCd }
						<input type="hidden" name="orgCd" value="${user.orgCd }"/>
					</td>									
				</tr>
				<tr>
						<th class="tdbg3 tc"><label for="orgCd">기관명</label></th>
						<td class="tl" colspan="3">
							<input type="text" name="orgNm" id="orgNm" class="ip1" maxlength="50" placeholder="기관이름" value="${org.orgNm }"/> 
							<input type="hidden" id="checkOrgNm" value="Y"/> 
							<span id="msg-orgnm"></span>
						</td>
					</tr>
				<tr>
				<tr>
						<th class="tdbg3 tc"><label for="orgCd">영문명</label></th>
						<td class="tl" colspan="3">
							<input type="text" name="orgEn" id="orgEn" class="ip1" maxlength="50" placeholder="기관영문" value="${org.orgEn }"/> 
						</td>
					</tr>
				<tr>
				<tr>
					<th class="tdbg3 tc"><label for="userPw">비밀번호</label></th>
					<td class="tl" colspan="3">
						<input type="password" class="ip1" id="userPw" name="userPw" value="" maxlength="20" />
						<button class="btn04 btn_orange" onclick="fn_updPw(); return false;">비밀번호 변경</button>
					</td>								
				</tr>		
				<tr>
					<th class="tdbg3 tc"><label for="userNm">전화번호</label></th>
					<td class="tl" colspan="3">
						<input type="text" class="ip1" id="tel" name="tel" value="${user.tel}" />
					</td>
				</tr>	
				<tr>
					<th class="tdbg3 tc">직인</th>
					<td>
					<c:if test="${not empty org.jiginFileNm }">
					<img src="http://edu.cdn.gov-ntruss.com/upload/jigin/${org.jiginFileNm }" width="100"/>
					</c:if>
					<input type="checkbox" name="jiginDel" />삭제 
					<input type="file" id="jigin"/>  </td>
				</tr>				
				<%-- <tr>
					<th class="tdbg3 tc"><label for="userNm">추가 기능</label></th>
					<td class="tl" colspan="3">
						<input type="checkbox" class="ip1" id="useCheck" name="useCheck" value="1" ${org.useCheck==1?'checked':'' } disabled="disabled"/> 전자출결
						<input type="checkbox" class="ip1" id="useGooroomee" name="useGooroomee" value="1" ${org.useGooroomee==1?'checked':'' } disabled="disabled"/> 구루미
						<input type="checkbox" class="ip1" id="useMov" name="useMov" value="1" ${org.useMov==1?'checked':'' } disabled="disabled"/> 동영상
					</td>
				</tr>	 --%>				
			</tbody>
		</form>
		</table>
		<c:if test="${user.userMemLvl != '1'}">			
			<div class="fr tc">
				<button class="btn01 btn_green" onclick="fn_confirmMsg('U'); return false;">수정</button>
			</div>
		</c:if>								
	</div>
</section>

<div class="remodal messagePop messagePop2" data-remodal-id="message" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc">
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
				<button data-remodal-action="cancel" class="remodal-confirm btn02 btn_orange">확인</button>
			</div>
		</div>
	</div>
</div>

<div class="remodal messagePop messagePop1" data-remodal-id="success" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc">
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC">알림</p>
		</div>
		<div class="modal-body">
			<p class="messageTxt" id="successStr">
				
			</p>
		</div>
		<div class="modal-footer">
			<div class="tc">
				<button onclick="fn_reload(); return false;" class="remodal-confirm btn02 btn_green">확인</button>
			</div>
		</div>
	</div>
</div>

<div class="remodal messagePop messagePop1" data-remodal-id="confirmUpd" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc">
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC">알림</p>
		</div>
		<div class="modal-body">
			<p class="messageTxt" id="confirmUpdStr">
				수정하시겠습니까?
			</p>
		</div>
		<div class="modal-footer">
			<div class="tc">
				<button onclick="fn_upd(); return false;" class="remodal-confirm btn02 btn_green">확인</button>
				<button data-remodal-action="cancel" class="remodal-cancel btn02 btn_gray">취소</button>
			</div>
		</div>
	</div>
</div>

<div class="remodal messagePop messagePop2" data-remodal-id="confirmDel" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc">
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC">알림</p>
		</div>
		<div class="modal-body">
			<p class="messageTxt" id="confirmDelStr">
				삭제 후 복구 할 수 없습니다.<br/>삭제 하시겠습니까?
			</p>
		</div>
		<div class="modal-footer">
			<div class="tc">
				<button onclick="fn_del(); return false;" class="remodal-confirm btn02 btn_orange">확인</button>
				<button data-remodal-action="cancel" class="remodal-cancel btn02 btn_gray">취소</button>
			</div>
		</div>
	</div>
</div>

<div class="remodal messagePop messagePop2" data-remodal-id="successDel" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc">
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC">알림</p>
		</div>
		<div class="modal-body">
			<p class="messageTxt" id="successDel">
				삭제되었습니다.
			</p>
		</div>
		<div class="modal-footer">
			<div class="tc">
				<button onclick="fn_list(); return false;" class="remodal-confirm btn02 btn_orange">확인</button>
			</div>
		</div>
	</div>
</div>
