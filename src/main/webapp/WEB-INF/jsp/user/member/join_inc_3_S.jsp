<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>

<%@ include file="/gpkisecureweb/jsp/gpkisecureweb.jsp" %>

<%
	String challenge = gpkiresponse.getChallenge();
	String sessionid = gpkirequest.getSession().getId();
	String url = javax.servlet.http.HttpUtils.getRequestURL(request).toString();
	session.setAttribute("currentpage",url);
%>

<script type="text/javascript" src="${utcp.ctxPath}/gpkisecureweb/client/GPKIWeb/js/ext/jquery.blockUI.js"></script>

<script type="text/javascript" src="${utcp.ctxPath}/gpkisecureweb/client/GPKIWeb/js/GPKIWeb_Config.js"></script>
<script type="text/javascript" src="${utcp.ctxPath}/gpkisecureweb/client/GPKIWeb/js/ext/GPKI_Config.js"></script>

<script type="text/javascript" src="${utcp.ctxPath}/gpkisecureweb/client/gpkijs_1.2.1.5.min.js" id="DSgpkijs"></script>
<script type="text/javascript" src="${utcp.ctxPath}/gpkisecureweb/client/GenerateContent.js" id="DSGenInterface"></script>
<script type="text/javascript" src="${utcp.ctxPath}/gpkisecureweb/client/GPKISecureWebJS.js"></script>
<script type="text/javascript" src="${utcp.ctxPath}/gpkisecureweb/client/GPKIJS_Crypto.js" id="DSGPKIJS_Crypto"></script>

<script type="text/javascript" src="${utcp.ctxPath}/gpkisecureweb/client/var.js"></script>
<script type="text/javascript" src="${utcp.ctxPath}/gpkisecureweb/client/GPKISecureWebNP2.js"></script>

<form action="/gpkisecureweb/jsp/join_inc_response.jsp" method="post" name="popForm">
    <input disabled type="hidden" name="challenge" value="<%=challenge%>" />
	<input type="hidden" name="sessionid" id="sessionid" value="<%=sessionid%>" />
</form>

<form method="post" id="rgsFrm" name="rgsFrm" autocomplete="off">
	<input type="hidden" name="smsYn" value="${user.smsYn }"/>
	<input type="hidden" name="userMemLvl" value="9" /> 
		
	<input type="hidden" name="subDn" value="${param.subDn }" />
<%-- 	<input type="hidden" name="encIv" value="${param.encIv }" /> --%>
<%-- 	<input type="hidden" name="encKey" value="${param.encKey }" /> --%>
	
	<p class="fs_12 tr mgt30 mgb5">
		<span class="red">*</span> 은 필수입력 사항입니다. 반드시 입력하십시오.
	</p>
	<table class="tb03">
		<caption class="sound_only">회원가입 신청폼</caption>
		<colgroup>
			<col width="20%">
			<col width="80%">
		</colgroup>
		<tbody>
			<tr style="">
				<th><label for="loginId">아이디</label> <span class="red">*</span></th>
				<td colspan="" class="flex idfiled">
					<div class="idBox idBox2">
						<div class="idBox2_inner">
							<input type="text" name="loginId" id="loginId" class="nm_size"
								required title="아이디는 필수 입니다." placeholder="아이디를 입력하세요" />
							<button type="button" class="btn04 btn_navy"
								onclick="fn_checkLoginId();">중복확인</button>
						</div>
						<p id="area-msg-checkId" class="dp_b mgt5 sptxt wnmsg idbox_info">* 아이디
							입력 기준: 영문 소문자, 숫자를 조합한 6 ~ 20자리</p>
					</div>
					<%-- <div class="idBtn">
						<div>
							<button type="button" class="btn04" onclick="">GPKI 인증서 연계</button>
							<span>${param.subDn == null?'미인증':'인증' }</span>
						</div>
						<div>
							<button type="button" class="btn04" onclick="">모바일공무원증
								연계</button>
							<span>미인증</span>
						</div>

					</div> --%>
				</td>
			</tr>
			<tr style="">
				<th><label>공공기관 인증</label></th>
				<td>
					<!-- 
					<button type="button" class="btn04 btn_navy" onclick="Login(this, popForm, false);">GPKI 인증서 연계</button>
					 -->
					<ul class="certi_warning dp_b mgt5 sptxt">
						<li>- 수강신청을 위해서는 행정전자서명 인증서(GPKI, EPKI) 인증이 필요합니다.</li>
						<li>- 행정전자서명 인증서 또는 모바일 공무원증은 회원가입 후 등록할 수 있습니다.</li>
						<li>- 공무원인증서 발급이 어려운 경우 재정경제부 융복합경제재정교육플랫폼 대표메일로 승인요청 메일을 보내주시면 확인 후 승인처리 됩니다.(1~3일 소요)</li>
						<li>- 메일을 이용한 승인절차 : 홈 > 학습지원 > 자주찾는질문 참고</li>
					</ul>
				</td>
			</tr>
			<tr>
				<th>비밀번호 <span class="red">*</span></th>
				<td colspan=""><input type="password" name="userPw"
					id="userPw1" class="nm_size" required="required"
					title="비밀번호는 필수 입니다." autocomplete="new-password" /> <span
					class="dp_b mgt5 sptxt">* 비밀번호 입력 기준: 영문, 숫자, 특수문자(!@#$^&*만
						허용)를 조합한 9 ~ 20자리 <br />(동일한 문자 혹은 연속된 문자 4번 이상 금지)
				</span></td>
			</tr>
			<tr>
				<th>비밀번호 확인 <span class="red">*</span></th>
				<td colspan=""><input type="password" id="userPw2" class="nm_size" required="required" title="비밀번호확인은 필수 입니다." /> <span id="area-msg-userPw2" class="dp_ib fs_12 sptxt mgl5"></span></td>
			</tr>
			<tr>
				<th>성명 <span class="red">*</span></th>
				<td colspan=""><input type="text" id="userNm" name="userNm"
					class="p_size" readonly title="이름은 필수 입니다."></td>
			</tr>
			<tr>
				<th>성별 <span class="red">*</span></th>
				<td colspan="">
				<input type="hidden" id="mfType" name="mfType" value="">
				<select id="mfType_" name="mfType_" disabled>						
						<option value="0">여</option>
						<option value="1">남</option>
				</select></td>
			</tr>
			<tr>
				<th>생년월일 <span class="red">*</span></th>
				<td colspan=""><input type="text" id="birth" name="birth"
					class="p_size" maxlength="8" readonly
					title="생년월일은 필수 입니다." /></td>
			</tr>
			<tr class="tb03_em">
				<th>소속 기관 e-mail<span class="red">*</span></th>
				<td colspan=""><input type="text" class="nm_size" id="email1"
					title="Email을 선택하여 입력하거나 직접 입력할 수 있습니다." required /> @ <input
					type="text" class="nm_size" id="email2"
					title="Email을 선택하여 입력하거나 직접 입력할 수 있습니다." required /> <label
					for="email3" class="sound_only">이메일 선택영역</label> <select
					id="email3" name="">
						<option value="">직접입력</option>
						<option value="naver.com">naver.com</option>
						<option value="korea.kr">korea.kr</option>
						<option value="gmail.com">gmail.com</option>
				</select> <input type="hidden" id="email" name="email" /></td>
			</tr>
			<tr class="tb03_phone">
				<th><label for="mobile1">휴대전화</label> <span class="red">*</span></th>
				<td colspan=""><input type="text" id="mobile1" class="p_size "
					maxlength="4"  title="휴대폰번호는 필수 입니다." /> - <input
					type="text" id="mobile2" class="p_size onlyNumber" maxlength="4"
					title="휴대폰번호는 필수 입니다." /> - <input type="text" id="mobile3"
					class="p_size onlyNumber" maxlength="4" title="휴대폰번호는 필수 입니다." />
					<input type="hidden" id="mobile" name="mobile" /></td>
			</tr>
			<tr class="tb03_phone">
				<th><label for="mobile2">사무실전화</label></th>
				<td colspan=""><select id="tel1" name="tel1">
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
				</select> - <input type="text" id="tel2" class="p_size onlyNumber"
					maxlength="4" title="휴대폰번호는 필수 입니다." /> - <input type="text"
					id="tel3" class="p_size onlyNumber" maxlength="4"
					title="휴대폰번호는 필수 입니다." /> <input type="hidden" id="tel"
					name="tel" /></td>
			</tr>
			<tr class="tb03_address">
				<th><label for="mobile1">주소</label></th>
				<td class="add"><div class="add_num"><input type="text" id="upd-postNo" name="postNo"
					class="p_size" readonly="readonly" maxlength="6"> <a
					href="javascript: fn_postSearch();"><span class="btn_add">우편번호찾기</span></div></a>
					<br> <input type="text" id="upd-addr" class="w50" name="addr">상세 <input type="text" v-model="comp.addrDtl" >
				</td>
			</tr>
			<tr>
						<th><label for="attach">소속</label> <span class="red">*</span></th>
						<td>
							<div class="attach">
								<label for="attach1"> 
									<input type="radio" id="attach1" class="mgr5" name="userTp" value="1"> 재정경제부
								</label> 
								<label for="attach2"> 
									<input type="radio" id="attach2" class="mgr5" name="userTp" value="2"> 기획예산처
								</label> 
								<label for="attach5"> 
									<input type="radio" id="attach5" class="mgr5" name="userTp" value="5"> 중앙행정기관
								</label> 
								<label for="attach3"> 
									<input type="radio" id="attach3" class="mgr5" name="userTp" value="3"> 지자체
								</label> 
								<label for="attach4"> 
									<input type="radio" id="attach4" class="mgr5" name="userTp" value="4"> 공공기관
								</label> 
								<!-- <label for="attach5"> <input type="radio" name="attach"
									id="attach5" class="mgr5" name="userTp" value="5"> 공무원
								</label> -->
							</div> 
							<input type="hidden" id="vm-etcOrgCd" name="userOrgCd" value="${user.userOrgCd }"/>
							<input type="text" id="vm-etcOrgNm" name="userOrgNm" value="${user.userOrgNm }" class="p_size w50" 
							 placeholder="소속을 입력해주세요"> <a
							href="javascript:;" onclick="vm_orgInfo.openModal()"><span class="btn_add">소속
									검색</span></a>
							<p>※ 행정조직 중 자신이 속한 조직이 없는 경우 상위 조직을 선택해주세요.</p>
							<p>※ 소속 검색에서 검색되지 않는 경우 직접입력을 통해 입력해주세요.</p>
						</td>
					</tr>
			<tr>

						<th><label for="comrank">직급</label> <span class="red">*</span></th>
						<td>
						<input type="hidden" id="vm-etcGradeCd" name="userGradeCd" value="${user.userGradeCd }"/>
						<input type="text" id="vm-etcGradeNm" class="p_size w50" 
							 placeholder="직급을 입력해주세요" name="userGradeNm" value="${user.userGradeNm }"> <a
							href="javascript:;" onclick="vm_gradeInfo.openModal()"><span class="btn_add">직급
									검색</span></a>
							<p>※ 직급 검색에서 검색되지 않는 경우 직접입력을 통해 입력해주세요.</p></td>
					</tr>
					<tr>
						<th>수신동의</th>
						<td colspan="">
						<label for="agreeSms-1">
							<input type="checkbox" name="i_smsYn" value="Y" id="agreeSms-1" ${user.smsYn == 'Y'?'checked':'' } /> SMS/MMS 수신동의 </label> 
							<span class="red">※ sms 수신동의를 하지 않더라도 수강과 관련된 문자는 알림으로 전달됩니다.</span>
						</td>
					</tr>
		</tbody>
	</table>
</form>


<div class="btn_step2">
	<a href="javascript:;" onclick="fn_joinProc(); return false;"
		class="btn_step01">회원가입</a> <a href="${utcp.ctxPath}/user/index.do"
		class="btn_step02">취소</a>
</div>


<script type="text/javascript">
	var checkLoginId = false;//로그인아이디 유효성 체크
	var checkUserPw1 = false;//패스워드 유효성 체크
	var checkUserPw2 = false;//패스워드 확인 체크
	var authEmail = false;
	$(document).ready(function() {
		$(".onlyNumber").keyup(function(event) {
			var str;
			if (event.keyCode != 8) {
				if (!(event.keyCode >= 37 && event.keyCode <= 40)) {
					var inputVal = $(this).val();
					str = inputVal.replace(/[^0-9]/gi, '');
					$(this).val(str);
				}
			}
		});

		$('#loginId').focusout(function() {
			//fn_checkLoginId();
		});

		$("#userPw1").focusout(function() {
			checkUserPw1 = false;
			var userPw = $('#userPw1').val();
			$.ajax({
				type : 'post',
				data : {
					userPw : userPw
				},
				url : '${utcp.ctxPath}/user/ajax/checkUserPw.json',
				success : function(r) {
					if (r.result) {
						$('#area-msg-userPw1').removeClass('fc_red');
						$('#area-msg-userPw1').addClass('fc_blue');
						checkUserPw1 = true;
					} else {
						$('#area-msg-userPw1').removeClass('fc_blue');
						$('#area-msg-userPw1').addClass('fc_red');
					}
					$('#area-msg-userPw1').text(r.message);
				}
			});
		});

		$("#userPw2").change(function() {
			checkUserPw2 = false;
			if ($("#userPw1").val() != $("#userPw2").val()) {
				$('#area-msg-userPw2').removeClass('fc_blue');
				$('#area-msg-userPw2').addClass('fc_red');
				$('#area-msg-userPw2').text('비밀번호가 일치하지 않습니다.');
			} else {
				checkUserPw2 = true;
				$('#area-msg-userPw2').removeClass('fc_red');
				$('#area-msg-userPw2').addClass('fc_blue');
				$('#area-msg-userPw2').text('비밀번호가 일치 합니다.');
			}
		});

		$("#email3").change(function() {
			$('#email2').val(this.value);
		});

		
		
		//본인인증 데이터 세팅하기
		$('#userNm').val($('#authUserNm').val());
		$('#birth').val($('#authBirth').val());
		$('#mfType_').val($('#authMfType').val());
		$('#mfType').val($('#authMfType').val());
		
		//var mobileArr = $('#authMobile').val().split('-');
		//$('#mobile1').val(mobileArr[0]);
		//$('#mobile2').val(mobileArr[1]);
		//$('#mobile3').val(mobileArr[2]);
		
		// 앞자리 3~4자리 구분
		var mobile = $('#authMobile').val().replace(/[^0-9]/g, ''); // 숫자만 추출
		if (mobile.length === 10) {
		    // 10자리: 000-123-1234
		    $('#mobile1').val(mobile.substring(0, 3));
		    $('#mobile2').val(mobile.substring(3, 6));
		    $('#mobile3').val(mobile.substring(6));
		} else if (mobile.length === 11) {
		    // 11자리: 010-1234-1234
		    $('#mobile1').val(mobile.substring(0, 3));
		    $('#mobile2').val(mobile.substring(3, 7));
		    $('#mobile3').val(mobile.substring(7));
		}
	});

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
				$('#upd-postNo').val(data.zonecode);
			$('#upd-addr').val(roadAddr);

				// 참고항목 문자열이 있을 경우 해당 필드에 넣는다.
				if (roadAddr != '') {
					//$('#addrEtc').val(extraRoadAddr);
				} else {
					//$('#addrEtc').val("");
				}
				$('#addrDetail').focus();
			}
		}).open();
	}

	function fn_joinProc() {
		
		
		
		/* 
		 */
		var isJoin=true;
		$('#rgsFrm').find('input,select').each(function(){
			if(this.required && (this.value==''||this.value==0)){
				alert(this.title);
				$('#'+this.id).focus();
				isJoin=false;
				return false;
			}
			if(this.name == 'loginId'){
				if(!checkLoginId){
					$('#loginId').focus();
					isJoin=false;
					return false;
				}
			}
			if(this.id == 'userPw1'){
				if(!checkUserPw1){
					$('#userPw1').focus();
					isJoin=false;
					return false;
				}
			}
			if(this.id == 'userPw2'){
				if(!checkUserPw1){
					$('#userPw2').focus();
					isJoin=false;
					return false;
				}
			}
		});
		if(!isJoin){
			return;
		}
		
		if($('input[name=userTp]:checked').length == 0){
			alert('소속기관 유형을 선택하세요');
			return;
		}
		if($('input[name=userOrgNm]').val() == ''){
			alert('소속을 입력하세요');
			return;
		}
		if($('input[name=userGradeNm]').val() == ''){
			alert('직급을 입력하세요');
			return;
		}
		
		$('#mobile').val(
				$('#mobile1').val() + '-' + $('#mobile2').val() + '-'
						+ $('#mobile3').val());
		$('#tel').val(
				$('#tel1').val() + '-' + $('#tel2').val() + '-'
						+ $('#tel3').val());
		$('#email').val($('#email1').val() + '@' + $('#email2').val());

		if($('input:checkbox[name=i_smsYn]').is(':checked')){
			$('input[name=smsYn]').val('Y');
		}else{
			$('input[name=smsYn]').val('N');
		}
		
		if($('#mobile').val() == ''){
	      alert('휴대폰번호를 입력하세요');
	      return;
	    }
		
		//이메일 동의여부
		/* if($('input:radio[name=userEmailYn]:checked').length == 0){
			alert('이메일 동의 여부를 체크하셔야 합니다.');
			$('#agreeEmail-1').focus();
			return;
		} */

		//sms 동의여부
		/* if($('input:radio[name=userSmsYn]:checked').length == 0){
			alert('SMS 동의 여부를 체크하셔야 합니다.');
			$('#agreeSms-1').focus();
			return;
		} */
		
		
		
			
		var formData = new FormData($("#rgsFrm")[0]);
	
		
		//인증정보 세팅하기
		formData.append('authTypeNm',$('#authTypeNm').val());
		formData.append('authEncData',$('#authEncData').val());
		
		$.ajax({
			url : "${utcp.ctxPath}/member/joinProc.ajax",
			type : "post",
			contentType: false,
            processData: false,
			data : formData,
			success : function(r) {
				if (r.result == 1) {
					fn_nextStep(4);
				} else {
					alert(r.msg);
				}
			}
		});
	}
	function fn_checkLoginId() {
		checkLoginId = false;
		var loginId = $('#loginId').val();
		$.ajax({
			type: 'post',
			data : {
				loginId : loginId
			},
			url : '${utcp.ctxPath}/user/ajax/checkLoginId.ajax',
			success : function(r) {
				if (r.result == 1) {
					$('#area-msg-checkId').removeClass('fc_red');
					$('#area-msg-checkId').addClass('fc_blue');
					checkLoginId = true;
				} else {
					$('#area-msg-checkId').removeClass('fc_blue');
					$('#area-msg-checkId').addClass('fc_red');
					if (r.result == 2) {
						authEmail = true;
					}
				}
				$('#area-msg-checkId').text(r.msg);
			}
		});
	}
</script>