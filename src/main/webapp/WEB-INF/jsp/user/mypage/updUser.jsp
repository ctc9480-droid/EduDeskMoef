<%@page import="com.educare.util.XmlBean"%>
<%@page import="com.educare.component.VarComponent"%>
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

<!-- 	<link rel="stylesheet" type="text/css" href="/resources/css/jquery-ui.css" /> -->
<!-- 	<link rel="stylesheet" type="text/css" href="/resources/css/style.css" /> -->
	<link rel="stylesheet" type="text/css" href="/resources/css/ui.css" />
<!-- 	<script type="text/javascript" src="/resources/js/plugin/jquery-3.2.1.min.js"></script> -->
<!-- 	<script type="text/javascript" src="/resources/js/plugin/jquery-ui.min.js"></script> -->
	<script type="text/javascript" src="/resources/js/ui.js"></script>
<!-- 	<script type="text/javascript" src="/resources/js/common.js"></script> -->

<form action="/gpkisecureweb/jsp/updUser_response.jsp" method="post" name="popForm">
    <input disabled type="hidden" name="challenge" value="<%=challenge%>" />
	<input type="hidden" name="sessionid" id="sessionid" value="<%=sessionid%>" />
</form>

<hidden id="contextPathHolder" th:data-contextPath="${httpServletRequest.getContextPath()}" />

<div id="mask"></div>
<div id="default-mask"></div>

<div class="qr-down window">
	<div class="pop-title">
		<span>데모 검증서버(SP) QR Code 로그인</span>
		<div class="closepop" onclick="javascript:trStatusInfoStop();">
			<a href="#" class="close">[Close]</a>
		</div>
	</div>
	<div class="pop-cont">
		<div class="" style="margin-bottom: 20px;">모바일 공무원증 APP을 실행하시고, QR 촬영하여 로그인을 진행하세요.</div>
		<div class="pop-qr" style="float: none; margin-right: 0; padding-bottom: 0;">
			<img id="authQrImg" alt="QR Code" />
		</div>
		<div class="time" style="color: red; margin-bottom: 20px;" id="elapsedTime">1:59</div>
		<div class="">검증이 완료된 후 [OK]버튼을 눌러 로그인을 진행해주세요.</div>
		<div class="pop-bottom">
			<button class="default-pop-ok">OK</button>
		</div>
	</div>
</div>

<div class="listWrap mypform" style="padding-top: 0">
	<div id="updFrm" style="display:">
		<form id="rgsFrm">
			<input type="hidden" name="smsYn" value="${user.smsYn }"/>
			
			<input type="hidden" name="subDn" value="${param.subDn }" />
<%-- 			<input type="hidden" name="encIv" value="${param.encIv }" /> --%>
<%-- 			<input type="hidden" name="encKey" value="${param.encKey }" /> --%>
	
			<!-- 본인인증 데이터 -->
			<input type="hidden" name="authTypeNm" id="authTypeNm" /> <input
				type="hidden" name="authUserNm" id="authUserNm" /> <input
				type="hidden" name="authBirth" id="authBirth" /> <input
				type="hidden" name="authMobile" id="authMobile" /> <input
				type="hidden" name="authEncData" id="authEncData" />
			<table class="tb03 userform">
				<caption class="sound_only">회원가입 신청폼</caption>
				<colgroup>
					<col width="20%"> 
					<col width="80%">
				</colgroup>
				<tbody>
					<tr style="">
						<th><label for="loginId">아이디</label> <span class="red">*</span></th>
						<td colspan="" class="flex idfiled">
							<div class="idBox">
								${user.loginId }
							</div>
							<div class="idBtn">
								<div>
									<button type="button" class="btn04" onclick="Login(this, popForm, false);">GPKI 인증서 연계</button>
									<span>${user.subDn == null?'미인증':'인증' }</span>
								</div> 
								<div>
									<button type="button" class="btn04" onclick="doLogin();">모바일공무원증 연계</button>
									<span>${user.subDn2 == null?'미인증':'인증' }</span>
								</div>
								
							</div>
						</td>
					</tr>
					<tr>
						<th>비밀번호 <span class="red">*</span></th>
						<td colspan=""><input type="password" name="userPw"
							id="userPw" class="nm_size" 
							title="비밀번호는 필수 입니다." autocomplete="new-password" /><span
							class="dp_b mgt5 sptxt">* 비밀번호 입력 기준: 영문, 숫자,
								특수문자(!@#$^&*만 허용)를 조합한 9 ~ 20자리 <br />(동일한 문자 혹은 연속된 문자 4번 이상
								금지)
						</span></td>
					</tr>
					<tr>
						<th>성명 <span class="red">*</span></th>
						<td colspan="">${user.userNm }</td>
					</tr>
					<tr>
						<th>성별 <span class="red">*</span></th>
						<td colspan="">${user.addMfTypeNm }</td>
					</tr>
					<tr>
						<th>생년월일 <span class="red">*</span></th>
						<td colspan="">${user.birth }</td>
					</tr>
					<tr class="tb03_em">
						<th>소속 기관 e-mail <span class="red">*</span></th>
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
						<td colspan=""><input type="text" id="mobile1"
							class="p_size " maxlength="4"  required="required"
							title="휴대폰번호는 필수 입니다." /> - <input type="text" id="mobile2"
							class="p_size onlyNumber" maxlength="4" 
							title="휴대폰번호는 필수 입니다." /> - <input type="text" id="mobile3"
							class="p_size onlyNumber" maxlength="4" 
							title="휴대폰번호는 필수 입니다." /> <input type="hidden" id="mobile"
							name="mobile" /></td>
					</tr>
					<!-- 
					 -->
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
						</select> - <input type="text" id="tel2" class="p_size onlyNumber" maxlength="4" /> - <input type="text" id="tel3" class="p_size onlyNumber" maxlength="4" />
						<input type="hidden" id="tel"
							name="tel" />
						</td>
					</tr>
					<tr class="tb03_address">
						<th><label for="mobile1">주소</label></th>
						<td class="add">
							<input type="text" name="postNo" value="${user.postNo }" id="upd-postNo" class="p_size" readonly="readonly" maxlength="6"> <a
							href="javascript: fn_postSearch();"><span class="btn_add">우편번호찾기</span></a>
							<br> <input type="text" name="addr" value="${user.addr }" id="upd-addr" class="w50 address_area"
							maxlength="50" readonly="readonly"> <span class="more_txt">상세</span> <input type="text"
							name="addrDetail" value="${user.addrDetail }" class="w30 more_address" maxlength="50"
							></td>
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
							 placehoder="소속을 입력해주세요"> <a
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
							 placehoder="직급을 입력해주세요" name="userGradeNm" value="${user.userGradeNm }"> <a
							href="javascript:;" onclick="vm_gradeInfo.openModal()"><span class="btn_add">직급
									검색</span></a>
							<p>※ 직급 검색에서 검색되지 않는 경우 직접입력을 통해 입력해주세요.</p></td>
					</tr>
					<tr>
						<th>수신동의</th>
						<td colspan="">
						<label for="agreeSms-1">
							<input type="checkbox" name="i_smsYn" value="Y" id="agreeSms-1" ${user.smsYn == 'Y'?'checked':'' } /> SMS/MMS 수신동의 </label> 
							<span class="red sms_warning">※ sms 수신동의를 하지 않더라도 수강과 관련된 문자는 알림으로 전달됩니다.</span>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
		<div class="btn_step2">
			<a href="#none" onclick="fn_updProc(); return false;"
				class="btn_step01">수정하기</a> <a href="/" class="btn_step02">취소</a>
			<a href="javascript:fn_quit();" class="btn_step02">탈퇴</a>
		</div>
	</div>
</div>

<script
	src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script type="text/javascript">
    function fn_quit() {
    	
    	if(!confirm("${user.userNm}님 회원탈퇴하시겠습니까?")) return;
    	
    	$.ajax({
    	      url : '${utcp.ctxPath}/user/mypage/userDelete.json',
    	      traditional: true,
    	      data : {
    	        userId: "${user.userId}"
    	      },
    	      success : function(r) {
    	        alert("삭제을 완료했습니다.");
    	        location.href = "/user/logout.do";
    	      }
    	    }); 
    }
</script>

<script type="text/javascript">
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

		try {
			var mobile = '${user.decMobile}';
			var mobileArr = mobile.split('-');
			$('#mobile1').val(mobileArr[0]);
			$('#mobile2').val(mobileArr[1]);
			$('#mobile3').val(mobileArr[2]);
		} catch (e) {
		}
		try {
			var tel = '${user.decTel}';
			var telArr = tel.split('-');
			$('#tel1').val(telArr[0]);
			$('#tel2').val(telArr[1]);
			$('#tel3').val(telArr[2]);
		} catch (e) {
		}
		try {
			var email = '${user.decEmail}';
			var emailArr = email.split('@');
			$('#email1').val(emailArr[0]);
			$('#email2').val(emailArr[1]);
			$('#email3').val(emailArr[1]);
		} catch (e) {
		}
		
		$("#email3").change(function() {
			$('#email2').val(this.value);
		});
		
		$('input[name=userTp][value=${user.userTp}]').prop('checked',true);
	});
	
	function fn_updProc() {
		var isJoin=true;
		$('#rgsFrm').find('input,select').each(function(){
			if(this.required && (this.value==''||this.value==0)){
				alert(this.title);
				$('#'+this.id).focus();
				isJoin=false;
				return false;
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
		
			
		var formData = new FormData($("#rgsFrm")[0]);
		
		//vue에 설정된 input은 serialize에 적용되지 않아서 추가로 설정,211103
		$.ajax({
			url : "${utcp.ctxPath}/member/updStdntProc.ajax",
			type : "post",
			contentType: false,
            processData: false,
			data : formData,
			//cache : false,
			//async : false,
			success : function(r) {
				if (r.result == 1) {
					alert('정상적으로 수정되었습니다.');
					location.reload();
				}else{
					alert(r.msg);
				}
			}
		});
	}
	function isEmpty (value) {
		console.log('isEmpty',value);
	    return typeof value === null || value === undefined || value === '';
	}
</script>

<script>
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
</script>

<%
	String domainNm = XmlBean.getConfigValue("domain.name");

	String sCheckplusUrl = domainNm + "/user/checkplus_main.do"; // 휴태폰본인인증 URL
%>
<script>
function fnMobileCheckPopup(){
	window.open('<%=sCheckplusUrl%>?onlyMobile=1',
						'popupChk',
						'width=500, height=550, top=100, left=100, fullscreen=no, menubar=no, status=no, toolbar=no, titlebar=yes, location=no, scrollbar=no');
	}
	function fn_checkplus_callback2(authMobile) {
		document.getElementById("authMobile").value = authMobile;
		var mobileArr = $('#authMobile').val().split('-');
		$('#mobile1').val(mobileArr[0]);
		$('#mobile2').val(mobileArr[1]);
		$('#mobile3').val(mobileArr[2]);
	}
</script>

<script type="text/javascript">
	/* javascript 파일에서 root 경로를 사용하기 위한 전역변수 */
	var URL_ROOT = $('#contextPathHolder').attr('data-contextPath') ? $('#contextPathHolder').attr('data-contextPath') : '';

	function doLogin(formId) {
	    popOn("qr-down");
	    $.ajax({
	        url: (URL_ROOT + "/spnoneprofile/qrVerifyReq.do"),
	        type: "POST",
	        dataType: "json",
	        async: true,
	        success: function(data) {
	            if (data["result"]) {
	                $("#authQrImg").attr("src", data["qrData"]);
	                trStatusInfo(119, data["csrfToken"]);

	            } else {
	                popOff();
	                defaultPopOn(decodeURIComponent(data["resultMsg"]));
	            }
	        }
	    });
	}

	var $$timerResultConfirm;

	function trStatusInfo(expireTime, csrfToken) {
	    $('#mask').one("click", function() {
	        trStatusInfoStop();
	    });

	    var obj = new Object();
	    obj["csrfToken"] = csrfToken;

	    $$timerResultConfirm = setInterval(function() {
	        if (expireTime != 0) {
	            expireTime -= 1;
	        }
	        var elapsedMin = Math.floor(expireTime / 60);
	        var elapsedSec = Math.floor(expireTime % 60);
	        $("#elapsedTime").html(elapsedMin + ":" + (elapsedSec > 9 ? elapsedSec : ("0" + elapsedSec)));

	        $.ajax({
	            url: (URL_ROOT + "/spnoneprofile/txCheck.do"),
	            type: "POST",
	            data: obj,
	            dataType: "json",
	            async: true,

	            success: function(data) {
	                if (data["result"]) {

	                    if (data["txCompleteCode"] == "COMPLETE") {
	                        popOff();
	                        trStatusInfoStop();
	                        location.href = URL_ROOT + "/user/mypage/updateMobileId.do";
	                    }

	                } else {
	                    if (data.txCompleteCode == "TIMEOUT") {
	                        defaultPopOn("Authentication timeout.");
	                    } else {
	                        defaultPopOn(data.txCompleteCode);
	                    }
	                    popOff();
	                    trStatusInfoStop();
	                }
	            }
	        });

	    }, 1000);
	}

	function trStatusInfoStop() {
	    clearInterval($$timerResultConfirm);
	}

	$(document).ready(function() {
	    if ($('#default-pop').length != 0) {
	        popOn("default-pop");
	    }
	});
</script>
