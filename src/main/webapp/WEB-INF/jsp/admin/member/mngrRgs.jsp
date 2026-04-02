<%@page import="com.educare.edu.menu.service.MenuUtil"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<%--최고관리자라면 비번 이름배고 disabled처리 --%>
<c:if test="${user.userMemLvl == '1'}">
<script>
$(document).ready(function(){
	$('.area_tr_invis').attr('style','display:none;');
});
</script>
</c:if>

<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
var menuId;
var chk1 = 0;
var chk2 = 0;
var chk3 = 0;
var chk5 = 0;
var chk6 = 0;
var chk7 = 0;
var chk8 = 0;
var chk9 = 0;
	$(function() {
		<c:if test='${menuAuthList != null && fn:length(menuAuthList) > 0}'>
	    	<c:forEach var="auth" items="${menuAuthList}" varStatus="stat">
	    		$("input:checkbox[name=menuIds][value=${auth.menuId}]").prop("checked", true);
	    	</c:forEach>
		</c:if>
		
		
		$('input[id^=menu-').click(function() {
			var menuIdArr = this.id.split('-');
			if(menuIdArr.length==2){
				if($(this).is(':checked')){
					$('input[id^='+this.id+']').prop('checked',true);
				}else{
					$('input[id^='+this.id+']').prop('checked',false);
				}
			}
		});
		
		

		$('#email3').change(function() {
			$('#email2').val(this.value);
		});
	
			
		var tel = '${user.tel}'.split('-');
		var mobile = '${user.decMobile}'.split('-');
		var email = '${user.decEmail}'.split('@');
		$('#tel1').val(tel[0]);
		$('#tel2').val(tel[1]);
		$('#tel3').val(tel[2]);
		$('#mobile1').val(mobile[0]);
		$('#mobile2').val(mobile[1]);
		$('#mobile3').val(mobile[2]);
		$('#email1').val(email[0]);
		$('#email2').val(email[1]);
		
	});

	function fn_list() {
		location.href = "${utcp.ctxPath}/admin/member/mngrList.do";
	}

	function fn_rgs() {
		$('#mobile').val(
				$('#mobile1').val() + '-' + $('#mobile2').val() + '-'
						+ $('#mobile3').val());
		$('#tel').val(
				$('#tel1').val() + '-' + $('#tel2').val() + '-'
						+ $('#tel3').val());
		$('#email').val(
				$('#email1').val() + '@' + $('#email2').val());
		
		var _obj = $('#loginId');
		if(_obj.val() == ''){ alert('아이디를 입력하세요'); _obj.focus();return;		}
		
		if($('#userId').val() != ''){
			
		}else{
			_obj = $('#userPw');
			if(_obj && _obj.val() == ''){ alert('비밀번호를 입력하세요'); _obj.focus();return;		}
			if(!isValidPassword(_obj.val())){
				alert('비밀번호가 규칙에 맞지 않습니다.'); _obj.focus();return;
			}
			_obj = $('#userPw2');
			if(_obj && _obj.val() == ''){ alert('비밀번호확인을 입력하세요'); _obj.focus();return;		}
			if($('#userPw').val() != $('#userPw2').val()){
				alert('비밀번호 확인이 맞지않습니다.'); _obj.focus();return;
			}
		}
		
		_obj = $('#userNm');
		if(_obj && _obj.val() == ''){ alert('관리자명을 입력하세요'); _obj.focus();return;		}
		
		
		
		var formData = $('#mngrRgsFrm').serialize();

		$.ajax({
			url : "${utcp.ctxPath}/admin/member/mngrRgsProc.ajax",
			type : "post",
			data : formData,
			cache : false,
			async : true,
			success : function(r) {
				if (r.result != 1) {
					$("#messageStr").html(r.msg);
					location.href = "#message";
				} else {
					location.href = "#success";
				}
			}
		});
	}
	
	
	function fn_updPw(){
		var _obj = $('#userPw');
		if(!isValidPassword(_obj.val())){
			alert('비밀번호가 규칙에 맞지 않습니다.'); _obj.focus();return;
		}
		$.ajax({
			url: "${utcp.ctxPath}/admin/member/userPwUpd.json",
			type: "post",
			data: {
				userPw : $("#userPw").val(),userId:'${user.userId}'
			},
			cache: false,
			async: true,
			success: function(r) {
				if(r.isAdmin){
					if(r.isSuccess){
						vm_alert.msg = '비밀번호가 변경되었습니다.';
						location.href = '#md-alert';
					}else{
						vm_alert.msg = r.message;
						location.href = '#md-alert';
						return;
					}
				}else{
					location.href = "#authMessage";
				}
			}
		});
	}

	function fn_postSearch() {
		new daum.Postcode({
			oncomplete : function(data) {
				// 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

				// 도로명 주소의 노출 규칙에 따라 주소를 표시한다.
				// 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
				var roadAddr = data.roadAddress; // 도로명 주소 변수
				var extraRoadAddr = ''; // 참고 항목 변수

				// 법정동명이 있을 경우 추가한다. (법정리는 제외)
				// 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
				if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
					extraRoadAddr += data.bname;
				}
				// 건물명이 있고, 공동주택일 경우 추가한다.
				if (data.buildingName !== '' && data.apartment === 'Y') {
					extraRoadAddr += (extraRoadAddr !== '' ? ', '
							+ data.buildingName : data.buildingName);
				}
				// 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
				if (extraRoadAddr !== '') {
					extraRoadAddr = ' (' + extraRoadAddr + ')';
				}

				// 우편번호와 주소 정보를 해당 필드에 넣는다.
				$('#postNo').val(data.zonecode);
				$('#addr').val(roadAddr);

				// 참고항목 문자열이 있을 경우 해당 필드에 넣는다.
				if (roadAddr != '') {
					$('#addrEtc').val(extraRoadAddr);
				} else {
					$('#addrEtc').val("");
				}
				$('#addrDetail').focus();
			}
		}).open();
	}
	
	function isValidPassword(password) {
		  var regex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[!@#$%^&*()\-_=+{}\[\]:;"'<>,.?/\\|`~]).{8,}$/;
		  return regex.test(password);
		}
</script>
<section class="pd025 pd200 po_re">
	<div class="po_re br5 bs_box">
		<form method="post" id="mngrRgsFrm">
			<input type="hidden" name="userId" value="${user.userId }" />
			<input type="hidden" name="userMemLvl" value="2" />
			<input type="hidden" name="tel" id="tel" />
			<input type="hidden" name="mobile" id="mobile" />
			<input type="hidden" name="email" id="email" />
			<table class="w100 tb02 tc">
				<caption class="sound_only">관리자등록테이블</caption>
				<colgroup>
					<col style="width: 10%;">
					<col style="width: 10%;">
					<col style="width: 30%;">
					<col style="width: 20%;">
					<col style="width: 30%;">
				</colgroup>
				<tbody>
					<tr>
						<th scope="row" colspan="2" class="tdbg3 tc"><label for="userId">
								아이디 <span class="required">&nbsp;&nbsp;&nbsp;</span>
							</label></th>
						<td class="tl" colspan="3"><c:choose>
								<c:when test="${empty user }">
									<input type="text" name="loginId" value="${user.loginId }" id="loginId" class="ip1">
								</c:when>
								<c:otherwise>
						${user.loginId }
						<input type="hidden" name="loginId" value="${user.loginId }" id="loginId" class="ip1">
								</c:otherwise>
							</c:choose></td>
					</tr>

					<c:choose>
						<c:when test="${empty user }">
							<tr>
								<th scope="row" colspan="2" class="tdbg3 tc"><label for="userPw">
										비밀번호 <span class="required">&nbsp;&nbsp;&nbsp;</span>
									</label></th>
								<td class="tl"><input type="password" name="userPw" id="userPw" class="ip2">
								<br/>※ 암호는 최소 8글자로 영문자, 숫자, 특수문자  3가지 조합이 필요합니다.
								</td>
								<th scope="row" class="tdbg3 tc"><label for="userPw2">
										비밀번호 확인 <span class="required">&nbsp;&nbsp;&nbsp;</span>
									</label></th>
								<td class="tl"><input type="password" name="userPw2" id="userPw2" class="ip2">
								
								
								</td>
							</tr>
						</c:when>
						<c:otherwise>
							<tr>
								<th scope="row" colspan="2" class="tdbg3 tc"><label for="userPw">
										비밀번호 <span class="required">&nbsp;&nbsp;&nbsp;</span>
									</label></th>
								<td class="tl"><input type="password" name="userPw" id="userPw" class="ip2">
									<button class="btn04 btn_orange" onclick="fn_updPw(); return false;" type="button">비밀번호 변경</button>
									<br/>※ 암호는 최소 8글자로 영문자, 숫자, 특수문자  3가지 조합이 필요합니다.
									</td>
							</tr>
						</c:otherwise>
					</c:choose>
					

					<!--// 관리자계정 리스트에 노출되는 이름 //-->
					<tr class="">
						<th scope="row" colspan="2" class="tdbg3 tc"><label for="userNm">
								관리자명 <span class="required">&nbsp;&nbsp;&nbsp;</span>
							</label></th>
						<td class="tl" colspan="3"><input value="${user.userNm }" type="text" name="userNm" id="userNm" class="ip1"></td>
					</tr>
					<!--// 관리자계정 리스트에 노출되는 이름 //-->

					<tr class="area_tr_invis">
						<th scope="row" colspan="2" class="tdbg3 tc"><label for="mobile1">
								관리자 휴대폰 
							</label></th>
						<td class="tl" colspan="3"><select id="mobile1" name="mobile1" title="휴대폰번호 선택항목" required="">
								<option value="010" selected="">010</option>
								<option value="011">011</option>
								<option value="016">016</option>
								<option value="017">017</option>
								<option value="018">018</option>
								<option value="019">019</option>
						</select> - <input type="text" id="mobile2" name="" class="ip5 onlyNumber" maxlength="4" required=""> - <input type="text" id="mobile3" name="" class="ip5 onlyNumber" maxlength="4" required=""></td>
					</tr>
					<tr class="area_tr_invis">
						<th scope="row" colspan="2" class="tdbg3 tc"><label for="tel1">관리자 번호</label></th>
						<td class="tl" colspan="3"><select id="tel1" name="" title="전화번호 국번">
								<option value="02">02</option>
								<option value="031">031</option>
								<option value="032">032</option>
								<option value="033">033</option>
								<option value="041">041</option>
								<option value="042">042</option>
								<option value="043">043</option>
								<option value="044">044</option>
								<option value="051">051</option>
								<option value="052">052</option>
								<option value="053">053</option>
								<option value="054">054</option>
								<option value="055">055</option>
								<option value="061">061</option>
								<option value="062">062</option>
								<option value="063">063</option>
								<option value="064">064</option>
								<option value="070">070</option>
						</select> - <input type="text" id="tel2" name="" class="ip5 onlyNumber" maxlength="4"> - <input type="text" id="tel3" name="" class="ip5 onlyNumber" maxlength="4"></td>
					</tr>
					<tr class="area_tr_invis">
						<th scope="row" colspan="2" class="tdbg3 tc"><label for="email1">이메일</label></th>
						<td colspan="3" class="tl"><input type="text" id="email1" name="" value="" class="ip1" required=""> @ <input type="text" id="email2" name="" value="" class="ip1" required=""> <label for="email3" class="sound_only">이메일 선택영역</label> <select id="email3" name="" title="Email을 선택하여 입력하거나 직접 입력할 수 있습니다." required="">
								<option value="">직접입력</option>
								<option value="naver.com">naver.com</option>
								<option value="daum.net">daum.net</option>
								<option value="nate.com">nate.com</option>
								<option value="hotmail.com">hotmail.com</option>
								<option value="yahoo.com">yahoo.com</option>
								<option value="empas.com">empas.com</option>
								<option value="korea.com">korea.com</option>
								<option value="dreamwiz.com">dreamwiz.com</option>
								<option value="gmail.com">gmail.com</option>
						</select></td>
					</tr>
					
					<tr class="area_tr_invis">
						<th colspan="2">상태</th>
						<td class="tl">
							<select name="state">
							<option value="A" ${user.state eq 'A' ? 'selected':'' }>승인</option>
							<option value="W" ${user.state eq 'W' ? 'selected':'' }>잠금</option>
							<option value="S" ${user.state eq 'S' ? 'selected':'' }>탈퇴</option>
						</select>
						</td>
					</tr>
						<tr class="area_tr_invis" ${sessionScope.sessionUserInfo.userMemLvl ne '1' ?'style="display:none;"':''}>
							<th class="tdbg3 tc" colspan="2">메뉴권한</th>
							<td class="tl menu_td" colspan="3">
								<%
								pageContext.setAttribute("baseMenuList", MenuUtil.getAdminBaseMenuList());
								%>
								<c:forEach items="${baseMenuList }" var="o" varStatus="s">
								<c:if test="${o.cd ne 'index' and o.st == 1}">
								<label for="menu-${s.index }" class="cb font_16">
									<input type="checkbox" id="menu-${s.index }" name="menuIds" class="depth01" value="${o.cd }"/>${o.nm }
								</label>
								<c:if test="${empty o.sub }">
								<br/>
								</c:if>
								<c:if test="${not empty o.sub }">
								<div class="sub_menuIp mgb15">
									ㄴ
									<c:forEach items="${o.sub }" var="o2" varStatus="s2">
									<c:if test="${o2.st == 1 }">
									<label for="menu-${s.index }-${s2.index}">
										<input type="checkbox" class="m0100" id="menu-${s.index }-${s2.index}" name="menuIds" value="${o2.cd }" />
										${o2.nm }
									</label>
									</c:if>
									</c:forEach>
								</div> 
								</c:if>
								</c:if>
								</c:forEach>
							</td>
						</tr>
					
					<tr>
						<th class="tdbg3 tc" colspan="2"><label for="wr_name">접속이력</label></th>
						<td class="tl" colspan="3">
							<div class="accessList">
								<table width="100%" class="tb01 tc">
									<thead bgcolor="#f7f8fa">
										<tr>
											<th>NO</th>
											<th>접속일시</th>
											<th>접속 IP</th>
											<th>접근메뉴</th>
											<th>처리주체정보</th>
											<th>수행업무</th>
										</tr>
									</thead>
									<tbody class="accessList">
										<c:choose>
											<c:when test='${connLogList != null && fn:length(connLogList) > 0}'>
												<c:forEach var="log" items="${connLogList}" varStatus="stat">
													<tr>
														<td>${20 - stat.index}</td>
														<td><fmt:formatDate value="${log.connDe}" pattern="yyyy-MM-dd / HH:mm" /></td>
														<td>${log.ip}</td>
														<td>${log.menuNm}</td>
														<td>${log.accUserInfo}</td>
														<td>
														<c:choose>
														<c:when test="${log.logAct eq 'U' }">
														수정
														</c:when>
														<c:otherwise>
														조회
														</c:otherwise>
														</c:choose>
														</td>
													</tr>
												</c:forEach>
											</c:when>
											<c:otherwise>
												<tr>
													<td colspan="4" style="text-align: center; height: 80px;">접속이력이 없습니다.</td>
												</tr>
											</c:otherwise>
										</c:choose>
									</tbody>

								</table>
							</div>
						</td>
					</tr>
				</tbody>
			</table>
			<div class="fl tc">
				<button class="btn02 btn_grayl" onclick="fn_list(); return false;" type="button">목록</button>
			</div>
			<div class="fr tc">
				<button class="btn01 btn_greenl" onclick="fn_rgs(); return false;" type="button">등록하기</button>
			</div>
		</form>
	</div>
</section>

<div class="remodal messagePop messagePop2" data-remodal-id="message" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc">
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC">알림</p>
		</div>
		<div class="modal-body">
			<p class="messageTxt" id="messageStr"></p>
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
			<p class="messageTxt">등록되었습니다.</p>
		</div>
		<div class="modal-footer">
			<div class="tc">
				<button onclick="fn_list(); return false;" class="remodal-confirm btn02 btn_green">확인</button>
			</div>
		</div>
	</div>
</div>