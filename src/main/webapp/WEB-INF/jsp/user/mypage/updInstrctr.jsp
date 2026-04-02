<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script type="text/javascript">
	$(function() {
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

		$('#userPw').on('keypress', function(e) {
			if (e.keyCode == '13') {
				fn_authChk();
			}
		});


		//연락처,모바일 세팅,성별
		$('#mfType').val('${userInfo.mfType}');
		var tel = '${userInfo.tel}'.split('-');
		var mobile = '${userInfo.mobile}'.split('-');
		var email = '${userInfo.email}'.split('@');
		$('#tel1').val(tel[0]);
		$('#tel2').val(tel[1]);
		$('#tel3').val(tel[2]);
		$('#mobile1').val(mobile[0]);
		$('#mobile2').val(mobile[1]);
		$('#mobile3').val(mobile[2]);
		$('#email1').val(email[0]);
		$('#email2').val(email[1]);
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
					$('#addrEtc').val(extraRoadAddr);
				} else {
					$('#addrEtc').val("");
				}
				$('#addrDetail').focus();
			}
		}).open();
	}

	function fn_authChk() {
		$.ajax({
			url : "/member/passWdChk.json",
			type : "post",
			data : {
				"userPw" : $("#userPw").val()
			},
			cache : false,
			async : true,
			success : function(r) {
				if (r.isLogined) {
					if (r.isSuccess) {
						fn_updFrom();
					} else {
						$("#userPw").val("");
						$("#messageStr").html(r.message);
						location.href = "#message";
					}
				} else {
					location.href = "#login";
				}
			}
		});
	}

	function fn_updFrom() {

		$("#passwd").hide();
		$("#updFrm").show();
	}

	function fn_updProc() {

		var isJoin = true;
		$('#rgsFrm').find('input,select').each(function() {
			if (this.required && (this.value == '' || this.value == 0)) {
				vm_alert_join.msg = this.title;
				vm_alert_join.focusId = this.id;
				location.href = '#md-alert-join';
				//onsole.log(this.required+','+this.name+','+this.value);
				isJoin = false;
				return false;
			}
		});

		if (!isJoin) {
			return;
		}

		$('#mobile').val(
				$('#mobile1').val() + '-' + $('#mobile2').val() + '-'
						+ $('#mobile3').val());
		$('#tel').val(
				$('#tel1').val() + '-' + $('#tel2').val() + '-'
						+ $('#tel3').val());
		$('#email').val($('#email1').val() + '@' + $('#email2').val());
		/* if($("#userNmEn").val() == ""){
			$("#messageStr").html("영문 성명은 필수입력입니다.");
			location.href = "#message";
			return;
		} */

		var formDate = $("#rgsFrm").serializeArray();
		//vue에 설정된 input은 serialize에 적용되지 않아서 추가로 설정,211103
		
		$.ajax({
			url : "/member/updProc.json",
			type : "post",
			data : formDate,
			cache : false,
			async : true,
			success : function(r) {
				if (r.isSuccess) {
					location.href = "#success";
				} else {
					$("#messageStr").html(r.message);
					location.href = "#message";
				}
			}
		});
	}
</script>

<div class="listWrap" style="padding-top: 0">
	<form id="rgsFrm">
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
					<td colspan="3">${userInfo.loginId }</td>
				</tr>

				<tr>
					<th>이름 <span class="red">*</span></th>
					<td colspan="3"><input type="text" id="userNm" name="userNm" class="p_size" required="required" title="이름은 필수 입니다." value="${userInfo.userNm }" /></td>
				</tr>
				<tr>
					<th>성별 <span class="red">*</span></th>
					<td colspan=""><select name="mfType" id="mfType" required="required" title="성별은 필수 입니다.">
							<option value="">선택하세요</option>
							<option value="01">남</option>
							<option value="02">여</option>
					</select></td>
					<th>생년월일 <span class="red">*</span></th>
					<td colspan=""><input type="text" id="birth" name="birth" class="p_size datepicker" maxlength="8" readonly="readonly" required="required" title="생년월일은 필수 입니다." value="${userInfo.birth }" /></td>
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
					</select> - <input type="text" id="mobile2" class="p_size onlyNumber" maxlength="4" required="required" title="휴대폰번호는 필수 입니다." /> - <input type="text" id="mobile3" class="p_size onlyNumber" maxlength="4" required="required" title="휴대폰번호는 필수 입니다." /> <input type="hidden" id="mobile" name="mobile" /></td>
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
					<td colspan="3"><input type="text" class="nm_size" id="email1" title="Email을 선택하여 입력하거나 직접 입력할 수 있습니다." required /> @ <input type="text" class="nm_size" id="email2" title="Email을 선택하여 입력하거나 직접 입력할 수 있습니다." required /> <label for="email3" class="sound_only">이메일 선택영역</label> <select id="email3" name="">
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
					</select> <input type="hidden" id="email" name="email" /></td>
				</tr>

				<tr>
					<th><label for="postNo">주소</label> <span class="red">*</span></th>
					<td colspan="3" class="add"><input type="text" class="p_size" id="postNo" name="postNo" onclick="fn_postSearch(); return false;" value="${userInfo.postNo }" /> <a href="javascript:;" onclick="fn_postSearch(); return false;"><span class="btn_add">우편번호찾기</span></a> <br /> <input type="text" class="w100" style="width: 400px;" placeholder="도로명주소" id="addr" name="addr" readonly value="${userInfo.addr }" /> <br /> <input type="text" class="w100" style="width: 400px;" placeholder="상세주소" id="addrDetail" name="addrDetail" value="${userInfo.addrDetail }" /> <input type="text" class="w100" placeholder="구주소" id="addrEtc" name="addrEtc" value="${userInfo.addrEtc }" /></td>
				</tr>
			</tbody>
		</table>
	</form>
	<div class="btn_step2">
		<a href="javascript:;" onclick="fn_updProc(); return false;" class="btn_step01">수정하기</a> <a href="/" class="btn_step02">취소</a>
	</div>
</div>


<div class="remodal messagePop messagePop1" data-remodal-id="success" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc">
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC">알림</p>
		</div>
		<div class="modal-body">
			<p class="messageTxt">수정되었습니다.</p>
		</div>
		<div class="modal-footer">
			<div class="tc">
				<button onclick="location.href='/'" class="remodal-confirm btn02 btn_green">확인</button>
			</div>
		</div>
	</div>
</div>

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

<div class="remodal messagePop messagePop2" data-remodal-id="login" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc">
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC">알림</p>
		</div>
		<div class="modal-body">
			<p class="messageTxt">로그인 후 이용하세요</p>
		</div>
		<div class="modal-footer">
			<div class="tc">
				<button onclick="location.href='${utcp.ctxPath}/user/login.do'" class="remodal-confirm btn02 btn_orange">확인</button>
			</div>
		</div>
	</div>
</div>


<!-- pickadate.js -->
<link rel="stylesheet" href="${utcp.ctxPath}/resources/plugins/pickadate/default.css">
<link rel="stylesheet" href="${utcp.ctxPath}/resources/plugins/pickadate/default.date.css">
<script src="${utcp.ctxPath}/resources/plugins/pickadate/picker.js"></script>
<script src="${utcp.ctxPath}/resources/plugins/pickadate/picker.date.js"></script>
<script src="${utcp.ctxPath}/resources/plugins/pickadate/legacy.js"></script>
<script type="text/javascript">
	$(function() {
		// pickadate //
		var $input = $('.datepicker').pickadate(
				{
					monthsFull : [ '1월', '2월', '3월', '4월', '5월', '6월', '7월',
							'8월', '9월', '10월', '11월', '12월' ],
					monthsShort : [ '1', '2', '3', '4', '5', '6', '7', '8',
							'9', '10', '11', '12' ],
					weekdaysFull : [ '일요일', '월요일', '화요일', '수요일', '목요일', '금요일',
							'토요일' ],
					weekdaysShort : [ '일', '월', '화', '수', '목', '금', '토' ],
					format : 'yyyymmdd',
					formatSubmit : 'yyyymmdd',
					today : "오늘",
					clear : "지우기",
					close : '닫기',
					container : '#container',
					labelMonthNext : '다음달 넘어가기',
					labelMonthPrev : '이전달 넘어가기',
					labelMonthSelect : '월 선택',
					labelYearSelect : '년도 선택',
					selectYears : 200,
					selectMonths : true
				//min:true
				});
		// pickadate //
	});
</script>