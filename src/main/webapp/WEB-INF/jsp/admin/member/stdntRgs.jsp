<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<!-- pickadate.js -->
<link rel="stylesheet" href="${utcp.ctxPath}/resources/plugins/pickadate/default.css">
<link rel="stylesheet" href="${utcp.ctxPath}/resources/plugins/pickadate/default.date.css">
<script src="${utcp.ctxPath}/resources/plugins/pickadate/picker.js"></script>
<script src="${utcp.ctxPath}/resources/plugins/pickadate/picker.date.js"></script>
<!-- <script src="${utcp.ctxPath}/resources/plugins/pickadate/legacy.js"></script> call stack 에러가 발생하여 주석처리, 갑자기 발생하였음 , 원인파악 불가-->
<script type="text/javascript">
	$(function() {
		$('.datepicker').pickadate(
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
					container : '.pd025',
					labelMonthNext : '다음달 넘어가기',
					labelMonthPrev : '이전달 넘어가기',
					labelMonthSelect : '월 선택',
					labelYearSelect : '년도 선택',
					selectYears : 200,
					selectMonths : true
				//min:true
				});
		// pickadate //

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

		//기타회원정보 세팅
		var mfType = '${user.mfType}';
		if(mfType != ''){
			$('input:radio[name=mfType][value='+mfType+']').prop('checked',true);
		}
		var tel = '${user.tel}'.split('-');
		var mobile = '${user.mobile}'.split('-');
		$('#tel1').val(tel[0]);
		$('#tel2').val(tel[1]);
		$('#tel3').val(tel[2]);
		$('#mobile1').val(mobile[0]);
		$('#mobile2').val(mobile[1]);
		$('#mobile3').val(mobile[2]);
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

	function fn_rgs() {
		$('#mobile').val(
				$('#mobile1').val() + '-' + $('#mobile2').val() + '-'
						+ $('#mobile3').val());
		var formData = $('#rgsFrm').serialize();
		$.ajax({
			type : 'post',
			url : 'stdntRgsProc.json',
			data : formData,
			success : function(r) {
				var msg = [ '알 수 없는 오류가 발생하였습니다.', '저장되었습니다.', '아이디가 중복되었습니다.',
						'이메일형식이 아닙니다.' ];
				if (r.result == 1) {
					$('#md-name').val('stdntView.do?userId='+r.userId);
				}
				vm_alert.msg = msg[r.result];
				location.href = "#md-alert";
			}
		});

		//$("#rgsFrm").attr("action", "/admin/member/stdntRgsProc.do");
		//$("#rgsFrm").submit();1
	}
	function fn_updPw(){
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
	$(document).ready(function() {

	});
</script>
<section class="pd025 pd200 po_re">
	<div class="po_re br5 bs_box">
		<span class="tb_text"> (<span class="required">&nbsp;&nbsp;&nbsp;</span>) 필수 기입 항목
		</span>
		<form method="post" id="rgsFrm">
			<input type="hidden" name="userId" value="${param.userId}" />
			<table width="100%" class="tb02 tc stdntTb">
				<caption class="sound_only">아이디단건등록테이블</caption>
				<tbody>
					<tr>
						<th class="tdbg3 tc "><label for="email">
								아&nbsp;&nbsp;이&nbsp;&nbsp;디 <span class="required">&nbsp;&nbsp;&nbsp;</span>
							</label></th>
						<td class="tl" colspan="5"><c:if test="${empty param.userId }">
								<input type="text" id="loginId" name="loginId" class="ip1" value="${user.loginId }" />
							</c:if> <c:if test="${not empty param.userId }">
								<input type="hidden" name="loginId" value="${user.loginId}" />
							${user.loginId }
							</c:if></td>
					</tr>
					<tr>
						<th class="tdbg3 tc "><label for="compUserId">
								아&nbsp;&nbsp;이&nbsp;&nbsp;디 <span class="required">&nbsp;&nbsp;&nbsp;</span>
							</label></th>
						<td class="tl" colspan="5">
						${user.compUserId }
						</td>
					</tr>
					<tr>
						<th class="tdbg3 tc"><label for="userNm">
								이&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;름 <span class="required">&nbsp;&nbsp;&nbsp;</span>
							</label></th>
						<td class="tl"><input type="text" name="userNm" id="userNm" class="ip1" value="${user.userNm }" /></td>
						<th class="tdbg3 tc"><label for="userSexM">
								성&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;별 <span class="required">&nbsp;&nbsp;&nbsp;</span>
							</label></th>
						<td class="tl"><label for="userSexM">
								<input type="radio" name="mfType" id="userSexM" class="ip1" value="01" ${user.mfType eq "01"? 'checked':'' }/>
								남
							</label> <label for="userSexF">
								<input type="radio" name="mfType" id="userSexF" class="ip1" value="02" ${user.mfType eq "02"? 'checked':''}/>
								여
							</label></td>
						<th class="tdbg3 tc"><label for="userBirth">
								생년월일 <span class="required">&nbsp;&nbsp;&nbsp;</span>
							</label></th>
						<td class="tl">${user.birth }</td>
					</tr>
					<tr>
						<th class="tdbg3 tc"><label for="userMb1">
								핸&nbsp;&nbsp;드&nbsp;&nbsp;폰 <span class="required">&nbsp;&nbsp;&nbsp;</span>
							</label></th>
						<td class="tl" colspan="5">
							<select name="mobile1" id="mobile1" itemname="휴대폰번호">
									<option value="010">010</option>
									<option value="011">011</option>
									<option value="016">016</option>
									<option value="017">017</option>
									<option value="018">018</option>
									<option value="019">019</option>
							</select> 
							<input type="text" id="mobile2" name="mobile2" value="" class="ip5"> 
							<input type="text" id="mobile3" name="mobile3" value="" class="ip5"> 
							<input type="hidden" name="mobile" id="mobile" value="${user.mobile }" />
						</td>
					</tr>
				</tbody>
			</table>
			
			<table width="100%" class="tb02 tc stdntTb">
				<caption class="sound_only">회원등록기타정보테이블</caption>
				<colgroup>
					<col style="width: 10%">
					<col style="width: 40%">
					<col style="width: 10%">
					<col style="width: 40%">
				</colgroup>
				<tbody>
					<tr>
						<th class="tdbg3 tc">회원가입일</th>
						<td class="tl">${utcp.convDateToStr(user.rgsde,'yyyy-MM-dd HH:mm') }</td>
						<th class="tdbg3 tc">최근접속일</th>
						<td class="tl">${utcp.convDateToStr(user.lstLoginDe,'yyyy-MM-dd HH:mm') }</td>
					</tr>
					<%-- <tr>
						<th class="tdbg3 tc">상태</th>
						<td class="tl" colspan="3">${user.addStateNm }
						<select name="state">
							<option value="A">승인</option>
							<option value="S">탈퇴</option>
						</select>
						</td>
					</tr> --%>
				</tbody>
			</table>

			<div class="fl tc">
				<button class="btn02 btn_grayl" onclick="history.back();" type="button">뒤로</button>
			</div>
			<div class="tc">
				<!-- <button class="btn01 btn_greenl mgb20" onclick="fn_rgs(); return false;" type="button">저장</button> -->
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
