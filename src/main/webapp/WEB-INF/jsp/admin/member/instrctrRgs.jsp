<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script src="${utcp.ctxPath}/resources/ckeditor4_full/ckeditor.js"></script>
<script>

	$(function() {
		
		CKEDITOR.replace('ckeditCn',{
			filebrowserUploadUrl: '${utcp.ctxPath}/editot/popupUpload.json?gubun=edu&prefixStr=',
			height : 400
		});

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
		
		$('#email3').change(function() {
			$('#email2').val(this.value);
		});
		
		//기타회원정보 세팅
		//$('select[name=mfType]').val('${user.mfType}');
		$('select[name=state]').val('${user.state}');
		var mobile = '${user.decMobile}'.split('-');
		var email = '${user.decEmail}'.split('@');
		$('#mobile1').val(mobile[0]);
		$('#mobile2').val(mobile[1]);
		$('#mobile3').val(mobile[2]);
		$('#email1').val(email[0]);
		$('#email2').val(email[1]);

	});
	//카테고리 가져오기
	$("#eduCtgrySeq").change(function(){
		$.ajax({
	        url: "${utcp.ctxPath}/admin/edu/getCategoryChildList.json",
	        type: "POST",
	        data: { "parentSeq" : $("#eduCtgrySeq").val() },
	        cache: false,
			async: true,
	        success: function(dataList) {
	        	$("#detailCtgrySeq").empty();
	        	$("#detailCtgrySeq").append(
        			"<option value=\"\">상세 카테고리</option>"	
        		);
	        	if(dataList != null && dataList != undefined && dataList != "") {
	        		$(dataList).each(function(i, e) {
	        			$("#detailCtgrySeq").append(
	        				"<option value=\"" + this.ctgrySeq + "\">" + this.ctgryNm + "</option>"
	        			);
	        		});
	        	}
			}
	    });
	});
	
	function fn_postSearch() {
		new daum.Postcode({
			oncomplete : function(data) {
				console.log(data);
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
				$('#addrDetail').focus();
			}
		}).open();
	}

	function fn_rgs() {
		
		<c:if test="${empty user }">
		if ($('#userPw').val() != $('#userPw2').val()) {
			alert('비밀번호가 일치하지 않습니다.');
			$('#userPw2').focus();
			return;
		}
		</c:if>
		
		if($("#userNm").val() == ""){
			alert("성명은 필수입력입니다.");
			$("#userNm").focus();
			return;
		}
		/* if($("#userBirth").val() == ""){
			$("#messageStr").html("생년월일은 필수입력입니다.");
			location.href = "#message";
			$("#inputId").val("userBirth");
			return;
		}
		if($("#userSex").val() == ""){
			$("#messageStr").html("성별은 필수입력입니다.");
			location.href = "#message";
			$("#inputId").val("userSex");
			return;
		}
		if($("#mobile1").val() == "" || $("#mobile2").val() == "" || $("#mobile3").val() == ""){
			$("#messageStr").html("휴대폰번호 는 필수입력입니다.");
			location.href = "#message";
			$("#inputId").val("mobile1");
			return;
		} */
		/* 
		if(CKEDITOR.instances.ckeditCn.getData() == ""){
			$("#messageStr").html("소개/경력은 입력하세요.");
			location.href = "#message";
			$("#inputId").val("ckeditCn");
			return;
		}
		 */
		$('#area').val(CKEDITOR.instances.ckeditCn.getData());
		if($("#state").val() == ""){
			alert("승인여부는 필수입력입니다.");
			$("#state").focus();
			return;
		}		
		$('#mobile').val(
				$('#mobile1').val() + '-' + $('#mobile2').val() + '-'
						+ $('#mobile3').val());
		$('#email').val(
				$('#email1').val() + '@' + $('#email2').val());
		
		var authValArr = [];
		for(var i in vm_cateSel.authList){
			var authInfo = vm_cateSel.authList[i];
			if(authInfo.authVal>0){
				authValArr.push(authInfo.authVal);
			}
		}
		
		//권한 중복체크
		var unList = new Set(authValArr);
		if(authValArr.length != unList.size){
			alert('중복강의분야가 존재합니다.');
			return;
		}
		$('#authValJson').val(JSON.stringify(authValArr));
		var formData = $('#form-rgs').serialize();
		$.ajax({
			type:'post',
			data : formData,
			url : 'instrctrRgsProc.json',
			success : function(r) {
				var msg = [ '알 수 없는 오류가 발생하였습니다.', '저장되었습니다.',
						'이미 사용중인 아이디 입니다.', '아이디가 형식이 올바르지 않습니다.' ];
				if (r.result == 1) {
					$('#md-name').val('instrctrView.do?userId=' + r.data.userId);
				}
				vm_alert.msg = msg[r.result];
				location.href = '#md-alert';
			}
			,error:function(e){
				console.log(e);
			}
		});
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
	function fn_focus(){
		var inputId = "#" + $("#inputId").val();
		$(inputId).focus();
		location.href = "#";
	}
	

</script>

<section class="pd025 pd200 po_re">
	<div class="po_re br5 bs_box">

		<form method="post" id="form-rgs">
			<input type="hidden" name="userId" id="userId" value="${param.userId }" />
			<input type="hidden" name="mobile" id="mobile" />
			<input type="hidden" name="email" id="email" />
			<input type="hidden" name="authValJson" id="authValJson" />
			<p class="fs_12 tr mgt30 mgb5"><span class="red">*</span> 은 필수입력 사항입니다. 반드시 입력하십시오.</p>
			<table class="w100 tb02 instrTb tc">
				<caption class="sound_only">강사 회원가입 신청폼</caption>
				<colgroup>
					<col style="width: 12%;" />
					<col style="width: 38%;" />
					<col style="width: 12%;" />
					<col style="width: 38%;" />
				</colgroup>
				<tbody>
					
					<%-- <tr>
						<th>아이디 <span class="required">&nbsp;&nbsp;&nbsp;</span></th>
						<td colspan="3" class="tl"><c:choose>
								<c:when test="${not empty user }">
									${user.loginId }
									<input type="hidden" id="loginId" name="loginId" value="${user.loginId }" class="ip1" required="" />
								</c:when>
								<c:otherwise>
									<input type="text" id="loginId" name="loginId" value="" class="ip1" required="" />
								</c:otherwise>
							</c:choose> <span class="dp_b mgt5 sptxt">※ 영문자, 숫자, _만 입력가능. 최소 6자 이상 입력하세요.</span>
						</td>
					</tr>
					<c:choose>
						<c:when test="${empty user }">
							<tr>
								<th>
										비밀번호 <span class="required">&nbsp;&nbsp;&nbsp;</span>
									</th>
								<td colspan="3" class="tl"><input type="password" id="userPw" name="userPw" value="" class="ip1" required="" /> 
									<span class="dp_b mgt5 sptxt">※ 암호는 최소 8글자로 영대문자, 영소문자, 숫자, 특수문자 중 3가지 이상의 조합이 필요합니다.</span> 
									<span class="dp_b sptxt">※ 비밀번호는 사용자 ID의 전부 또는 일부를 포함하지 않아야 합니다.</span>
								</td>
							</tr>
							<tr>
								<th><label for="userPw2">비밀번호 확인 <span class="required">&nbsp;&nbsp;&nbsp;</span></label></th>
								<td colspan="3" class="tl"><input type="password" id="userPw2" name="" value="" class="ip1" required="" /></td>
							</tr>
						</c:when>
						<c:otherwise>
							<tr>
								<th>패스워드</th>
								<td class="tl"><input type="text" name="userPw" id="userPw" class="ip2" />
									<button class="btn04 btn_orange" onclick="fn_updPw(); return false;">비밀번호 변경</button>
								</td>
							</tr>
						</c:otherwise>
					</c:choose> --%>
					<tr>
						<th><label for="userNm">성명 <span class="required">&nbsp;&nbsp;&nbsp;</span>	</label></th>
						<td colspan="3" class="tl">
							<input type="text" id="userNm" name="userNm" value="${user.userNm }" class="ip1" required="" />
						</td>
					</tr>
			

					<tr style="">
						<th><label for="mobile1"> 휴대폰번호</label></th>
						<td colspan="3" class="tl">
							<select id="mobile1" title="휴대폰번호 선택항목" required="">
									<option value="">선택</option>
									<option value="010">010</option>
									<option value="011">011</option>
									<option value="016">016</option>
									<option value="017">017</option>
									<option value="018">018</option>
									<option value="019">019</option>
							</select>
							<input type="text" id="mobile2" name="" class="ip5 onlyNumber" maxlength="4" required="" />
							<input type="text" id="mobile3" name="" class="ip5 onlyNumber" maxlength="4" required="" />
						</td>
					</tr>
					<tr style="display:none;">
						<th scope="row" class="tdbg3 tc"><label for="email1">이메일</label></th>
						<td colspan="3" class="tl">
							<input type="text" id="email1" name="" value="" class="ip5" required=""> @ 
							<input type="text" id="email2" name="" value="" class="ip5" required=""> 
							<label for="email3" class="sound_only">이메일 선택영역</label> 
							<select id="email3" name="" title="Email을 선택하여 입력하거나 직접 입력할 수 있습니다." required="">
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
						</td>
					</tr>
					<tr style="display:none;">
						<th> 강의분야 <span class="required">&nbsp;&nbsp;&nbsp;</span></th>
						<td colspan="3" class="tl">
							<div id="vm-cateSel">
							<div v-for="(o,i) in authList">
							<select class="vb" v-model="o.selVal" @change="callCategory(o.selVal,i)">
								<option value="0">카테고리 선택▼</option>
								<c:forEach var="data" items="${cateList}" varStatus="stat">
									<option value="${data.ctgrySeq}" >${data.ctgryNm}</option>
								</c:forEach>
							</select>
							
							<template v-for="(o2,i2) in o.selList">
							<select class="vb" v-model="o2.selVal" @change="setAuthVal(o2.selVal,i)">
								<option value="0">카테고리 선택▼</option>
								<option :value="o3.ctgrySeq" v-for="(o3,i3) in o2.cateList">{{o3.ctgryNm}}</option>
							</select>
							</template>
							<a href="#none" @click="delAuth(authList,i)"><span class="btn_add btn04 btn_gray">삭제</span></a>
							<a href="#none" @click="addAuth" v-if="authList.length==(i+1) " ><span class="btn_add btn04 btn_black">추가</span></a>
							</div>
							</div>
							
							<script>
							$(document).ready(function(){
								var callAuthList = [];
								<c:forEach items="${authList}" var="o" varStatus="s">
								callAuthList.push({ctgrySeq:'${o.ctgrySeq}'*1,parentSeq:'${o.parentSeq}'*1,ctgryDepth:'${o.ctgryDepth}'*1});
								</c:forEach>
								if(callAuthList.length){
									vm_cateSel.authList = [];
									for(var i in callAuthList){
										vm_cateSel.authList.push($.extend(true,{}, authItem));
										var auth = callAuthList[i];
										vm_cateSel.authList[i].authVal = auth.ctgrySeq;
										if(auth.ctgryDepth == 1){
											vm_cateSel.authList[i].selVal = auth.ctgrySeq;
											fn_callCategory(auth.ctgrySeq,i,0);
										}else{
											vm_cateSel.authList[i].selVal = auth.parentSeq;
											fn_callCategory(auth.parentSeq,i,auth.ctgrySeq);
										}
									}
									
								}
							});
							function fn_callCategory(ctgrySeq,i,selVal){
								if (ctgrySeq == 0){
						        	return;
						        }
								vm_cateSel.authList[i].authVal = ctgrySeq*1;
						        $.ajax({
						            data : {ctgrySeq: ctgrySeq },
						            url : '${utcp.ctxPath}/comm/api/callCategory.json',
						            success : function(r) {
						            	if(r.ctgryList.length){
											if(r.ctgryList[0].ctgryDepth == 2){
												var selList = vm_cateSel.authList[i].selList;
												if(selList.length>0){
													selList.splice(0,1, {cateList : r.ctgryList,selVal : 0});
												}else{
													vm_cateSel.authList[i].authVal = selVal*1;
													selList.push({cateList : r.ctgryList,selVal : selVal});
												}
											}
						            	}
						            }
						        });
							}
							var authItem = {
									selVal : 0,
									authVal : 0,
									selList : [],
								};
							var vm_cateSel = new Vue({
								el : '#vm-cateSel',
								data : {
									authList : [
										$.extend(true,{}, authItem)
									],
								},
								methods : {
									delAuth : function(l,i){
										if(l.length==1){
											alert('삭제 할 수 없습니다.');return;
										}
										l.splice(i,1);
									},
									addAuth : function(){
										vm_cateSel.authList.push($.extend(true,{}, authItem));
									},
									setAuthVal : function(ctgrySeq,i){
										vm_cateSel.authList[i].authVal = ctgrySeq;
									},
									callCategory : function(ctgrySeq,i){
										fn_callCategory(ctgrySeq,i,0);
									}
								},
							});
							
							</script>
						</td>
					</tr>		
					<tr style="display:none;">
						<th>
							<label for="postNo">주소</label>
						</th>
						<td colspan="3" class="add tl">
							<input type="text" id="postNo" name="postNo" readonly="" class="ip5 mgb5" required="" value="${user.postNo }" /> 
							<a href="javascript:fn_postSearch();"><span class="btn_add btn04 btn_black">우편번호찾기</span></a> <br> 
							<input type="text" id="addr" name="addr" readonly="" class="ip2 mgb5" required="" value="${user.addr }" /> &nbsp;&nbsp;상세 &nbsp;
							<input type="text" id="addrDetail" name="addrDetail" class="ip1 mgb5" value="${user.addrDetail }" />
						</td>
					</tr>
					<tr>
						<th> 소개 <!-- <span class="required">&nbsp;&nbsp;&nbsp;</span> --></th>
						<td class="tl" colspan="3">
							<textarea id="ckeditCn" name="ckeditCn" placeholder="내용" class="w100 h500">${user.area}</textarea>
							<input type="hidden" id="area" name="content"/>
						</td>
					</tr>
					<tr>
						<th><label for="state">승인여부 <span class="required">&nbsp;&nbsp;&nbsp;</span></label></th>
						<td colspan="3" class="add tl">
						<select name="state" id="state">
							<option value="">선택</option>
							<option value="W">대기</option>
							<option value="A">승인</option>
						</select>
						</td>
					</tr>
				</tbody>
			</table>
		</form>

		<div class="fl tc">
			<button type="button" class="btn01 btn_grayl" onclick="location.href='instrctrList.do'"; >목록</button>
		</div>

		<div class="fr tc">
			<button type="button" class="btn01 btn_orange" onclick="fn_rgs();">저장</button>
		</div>
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
		<input type="hidden" id="inputId" />
		<div class="modal-footer">
			<div class="tc">
				<button onclick="fn_focus(); return false;" class="remodal-confirm btn02 btn_orange">확인</button>
			</div>
		</div>
	</div>
</div>