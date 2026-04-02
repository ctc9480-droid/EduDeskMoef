<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
test
<script type="text/javascript">
	var checkLoginId = false;//로그인아이디 유효성 체크
	var checkUserPw1 = false;//패스워드 유효성 체크
	var checkUserPw2 = false;//패스워드 확인 체크
	var authEmail	 = false;
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
		
		$('#loginId').focusout(function(){
			fn_checkLoginId();
		});

		$("#userPw1").focusout(function() {
			checkUserPw1=false;
			var userPw = $('#userPw1').val();
			$.ajax({
				type:'post',
				data:{userPw:userPw},
				url:'${utcp.ctxPath}/user/ajax/checkUserPw.json',
				success:function(r){
					if(r.result){
						$('#area-msg-userPw1').removeClass('fc_red');
						$('#area-msg-userPw1').addClass('fc_blue');
						checkUserPw1=true;
					}else{
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
				$('#postNo').val(data.zonecode);
				$('#addr').val(roadAddr);

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
		var isJoin=true;
		$('#rgsFrm').find('input,select').each(function(){
			if(this.required && (this.value==''||this.value==0)){
				vm_alert_join.msg=this.title;
				vm_alert_join.focusId=this.id;
				location.href='#md-alert-join';
				//onsole.log(this.required+','+this.name+','+this.value);
				isJoin=false;
				return false;
			}
		});
		if(!checkLoginId){
			return;
		}
		if(!isJoin||!checkLoginId||!checkUserPw1||!checkUserPw2){
			return;
		}
		
		
		$('#mobile').val($('#mobile1').val()+'-'+$('#mobile2').val()+'-'+$('#mobile3').val());
		$('#tel').val($('#tel1').val()+'-'+$('#tel2').val()+'-'+$('#tel3').val());
		$('#email').val($('#email1').val()+'@'+$('#email2').val());
		
		var formDate = $("#rgsFrm").serialize();

		//onsole.log(formDate);
		$.ajax({
			url : "/member/joinProc.json",
			type : "post",
			data : formDate,
			cache : false,
			async : true,
			success : function(r) {
				if (r.isSuccess) {
					fn_nextStep(4);
				} else {
					vm_alert_join.msg=r.message;
					location.href='#md-alert-join';
				}
			}
		});
	}
	function fn_checkLoginId(){
		checkLoginId = false;
		var loginId = $('#loginId').val();
		$.ajax({
			data : {
				loginId : loginId
			},
			url : '${utcp.ctxPath}/user/ajax/checkLoginId.json',
			success : function(r) {
				if (r.result==1) {
					$('#area-msg-checkId').removeClass('fc_red');
					$('#area-msg-checkId').addClass('fc_blue');
					checkLoginId = true;
				}else {
					$('#area-msg-checkId').removeClass('fc_blue');
					$('#area-msg-checkId').addClass('fc_red');
					if(r.result==2){
						authEmail=true;
					}
				}
				$('#area-msg-checkId').text(r.message);
			}
		});
	}
</script>

<form method="post" id="rgsFrm" autocomplete="off">
<input type="hidden" name="userMemLvl" value="8"/>
	<p class="fs_12 tr mgt30 mgb5"><span class="red">*</span> 은 필수입력 사항입니다. 반드시 입력하십시오.</p>
	<table class="tb03">
		<caption class="sound_only">강사 회원가입 신청폼</caption>
		<colgroup>
			<col width="12%" />
			<col width="38%" />
			<col width="12%" />
			<col width="38%" />
		</colgroup>
		<tbody>
			<tr style="">
				<th><label for="loginId">아이디</label> <span class="red">*</span></th>
				<td colspan="3"><input type="text" id="loginId" name="loginId" class="nm_size" required="required" title="아이디는 필수 입니다." placeholder="e-mail"/> 
				<button type="button" class="btn04 btn_orange" onclick="fn_sendEmailAuthNo();" id="btn-authEmail">이메일 인증</button> 
				<span id="area-msg-checkId" class="dp_ib fs_12 fc_red sptxt mgl5"></span> 
				<span class="dp_b mgt5 sptxt">※ 아이디는 e-mail로 생성하셔야 합니다</span>
			</tr>
			<tr>
				<th>비밀번호 <span class="red">*</span></th>
				<td colspan="3"><input type="password" name="userPw" id="userPw1" class="nm_size" required="required" title="비밀번호는 필수 입니다."/> 
				<span id="area-msg-userPw1" class="dp_ib fs_12 sptxt mgl5"></span> 
				<span class="dp_b mgt5 sptxt">※ 암호는 최소 8글자로 영대문자, 영소문자, 숫자, 특수문자 중 3가지 이상의 조합이 필요합니다.</span> 
				<!-- <span class="dp_b sptxt">※ 비밀번호는 사용자 ID의 전부 또는 일부를 포함하지 않아야 합니다.</span> -->
				</td>
			</tr>
			<tr>
				<th>비밀번호 확인 <span class="red">*</span></th>
				<td colspan="3"><input type="password" id="userPw2" class="nm_size" required="required" title="비밀번호확인은 필수 입니다."/>
				<span id="area-msg-userPw2" class="dp_ib fs_12 sptxt mgl5"></span> 
				</td>
			</tr>
			<tr>
				<th>이름 <span class="red">*</span></th>
				<td colspan="3"><input type="text" id="userNm" name="userNm" class="p_size" required="required" title="이름은 필수 입니다."/></td>
			</tr>
			<tr>
				<th>성별 <span class="red">*</span></th>
				<td colspan=""><select name="mfType" id="mfType" required="required" title="성별은 필수 입니다.">
						<option value="">선택하세요</option>
						<option value="1">남</option>
						<option value="0">여</option>
				</select></td>
				<th>생년월일 <span class="red">*</span></th>
				<td colspan=""><input type="text" id="birth" name="birth" class="p_size datepicker" maxlength="8" readonly="readonly" required="required" title="생년월일은 필수 입니다." /></td>
			</tr>
			<tr>
				<th>직위 <span class="red">*</span></th>
				<td colspan="3">
					
				</td>
			</tr>
			<tr>
				<th><label for="mobile1">휴대폰번호</label> <span class="red">*</span></th>
				<td colspan=""><select id="mobile1" title="휴대폰번호 선택항목" required>
						<option value="010" selected>010</option>
						<option value="011">011</option>
						<option value="016">016</option>
						<option value="017">017</option>
						<option value="018">018</option>
						<option value="019">019</option>
				</select> - <input type="text" id="mobile2" class="p_size onlyNumber" maxlength="4" required="required" title="휴대폰번호는 필수 입니다."/> - 
				<input type="text" id="mobile3" class="p_size onlyNumber" maxlength="4" required="required" title="휴대폰번호는 필수 입니다."/> <input type="hidden" id="mobile" name="mobile" /></td>
				<th><label for="tel1">전화번호</label></th>
				<td colspan=""><select id="tel1" name="" title="전화번호 국번">
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
				</select> - <input type="text" id="tel2" class="p_size onlyNumber" maxlength="4" /> - <input type="text" id="tel3" class="p_size onlyNumber" maxlength="4" /> <input type="hidden" id="tel" name="tel" /></td>
			</tr>
			<tr>
				<th>e-mail <span class="red">*</span></th>
				<td colspan="3"><input type="text" class="nm_size" id="email1" title="Email을 선택하여 입력하거나 직접 입력할 수 있습니다." required/> @ 
				<input type="text" class="nm_size" id="email2" title="Email을 선택하여 입력하거나 직접 입력할 수 있습니다." required/> 
				<label for="email3" class="sound_only">이메일 선택영역</label> 
				<select id="email3" name="" >
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
				</select>
				<input type="hidden" id="email" name="email" /> 
				</td>
			</tr>

			<tr>
				<th><label for="postNo">주소</label> <span class="red">*</span></th>
				<td colspan="3" class="add"><input type="text" class="p_size" id="postNo" name="postNo" onclick="fn_postSearch(); return false;" /> <a href="javascript:;" onclick="fn_postSearch(); return false;"><span class="btn_add">우편번호찾기</span></a> <br /> <input type="text" class="w100" style="width: 400px;" placeholder="도로명주소" id="addr" name="addr" readonly /> <br /> <input type="text" class="w100" style="width: 400px;" placeholder="상세주소" id="addrDetail" name="addrDetail" /> <input type="text" class="w100" placeholder="구주소" id="addrEtc" name="addrEtc" /></td>
			</tr>
		</tbody>
	</table>
</form>
<div class="btn_step2">
	<a href="javascript:;" onclick="fn_joinProc(); return false;" class="btn_step01">다음단계</a>
	<a href="${utcp.ctxPath}/user/index.do" class="btn_step02">취소</a>
</div>

