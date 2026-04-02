<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp" %>
<c:set var="user" value="${vo.userInfo}"/>
<script>
	var menuId;
	var chk1 = 0;
	var chk2 = 0;
	var chk3 = 0;
	var chk5 = 0;
	var chk6 = 0;
	$(function(){	
		<c:if test='${vo.menuAuthList != null && fn:length(vo.menuAuthList) > 0}'>
	    	<c:forEach var="auth" items="${vo.menuAuthList}" varStatus="stat">
	    		menuId = "${auth.menuId}";
	    		if(menuId.substring(3,4) == "1") chk1 += 1;
	    		if(menuId.substring(3,4) == "2") chk2 += 1;
	    		if(menuId.substring(3,4) == "3") chk3 += 1;
	    		if(menuId.substring(3,4) == "5") chk5 += 1;
	    		if(menuId.substring(3,4) == "6") chk5 += 1;
	    		
	    		if(chk1 > 0) $("#A_010000").prop("checked", true);
	    		if(chk2 > 0) $("#A_020000").prop("checked", true);
	    		if(chk3 > 0) $("#A_030000").prop("checked", true);
	    		if(chk5 > 0) $("#A_050000").prop("checked", true);
	    		if(chk6 > 0) $("#A_060000").prop("checked", true);
	    		
	    		$("#" + menuId).prop("checked", true);
	    	</c:forEach>
		</c:if>
		
		$('.depth01').click(function(){
			if($(".depth01").is(":checked")){
				$(".m0100").prop("checked", true);
			}else{
				$(".m0100").prop("checked", false);
			}
		});
		
		$('.m0100').click(function(){
			if($('input:checkbox[class="m0100"]:checked').length > 0){
				$(".depth01").prop("checked", true);
			}else{
				$(".depth01").prop("checked", false);
			}
		});
		
		$('.depth02').click(function(){
			if($(".depth02").is(":checked")){
				$(".m0200").prop("checked", true);
			}else{
				$(".m0200").prop("checked", false);
			}
		});
		
		$('.m0200').click(function(){
			if($('input:checkbox[class="m0200"]:checked').length > 0){
				$(".depth02").prop("checked", true);
			}else{
				$(".depth02").prop("checked", false);
			}
		});
		
		$('.depth03').click(function(){
			if($(".depth03").is(":checked")){
				$(".m0300").prop("checked", true);
			}else{
				$(".m0300").prop("checked", false);
			}
		});
		
		$('.m0300').click(function(){
			if($('input:checkbox[class="m0300"]:checked').length > 0){
				$(".depth03").prop("checked", true);
			}else{
				$(".depth03").prop("checked", false);
			}
		});
		
		$('.depth05').click(function(){
			if($(".depth05").is(":checked")){
				$(".m0500").prop("checked", true);
			}else{
				$(".m0500").prop("checked", false);
			}
		});
		$('.m0500').click(function(){
			if($('input:checkbox[class="m0500"]:checked').length > 0){
				$(".depth05").prop("checked", true);
			}else{
				$(".depth05").prop("checked", false);
			}
		});
		$('.depth07').click(function(){
			if($(".depth07").is(":checked")){
				$(".m0700").prop("checked", true);
			}else{
				$(".m0700").prop("checked", false);
			}
		});
		$('.m0700').click(function(){
			if($('input:checkbox[class="m0700"]:checked').length > 0){
				$(".depth07").prop("checked", true);
			}else{
				$(".depth07").prop("checked", false);
			}
		});
		$('.depth08').click(function(){
			if($(".depth08").is(":checked")){
				$(".m0800").prop("checked", true);
			}else{
				$(".m0800").prop("checked", false);
			}
		});
		$('.m0800').click(function(){
			if($('input:checkbox[class="m0800"]:checked').length > 0){
				$(".depth08").prop("checked", true);
			}else{
				$(".depth08").prop("checked", false);
			}
		});
		$('.depth09').click(function(){
			if($(".depth09").is(":checked")){
				$(".m0900").prop("checked", true);
			}else{
				$(".m0900").prop("checked", false);
			}
		});
		$('.m0900').click(function(){
			if($('input:checkbox[class="m0900"]:checked').length > 0){
				$(".depth09").prop("checked", true);
			}else{
				$(".depth09").prop("checked", false);
			}
		});
		
		//기관이름 중복 체크
		$('#orgNm').keyup(function(){
			var orgNm = this.value;
			var orgCd = $('input[name=orgCd]').val();
			$.ajax({
				data:{orgNm:orgNm,orgCd:orgCd},url:'checkOrgNm.json',type:'post',
				success:function(r){
					if(r.result!=''){
						$('#msg-orgnm').text(r.result);
						$('#checkOrgNm').val('N');
					}else{
						$('#msg-orgnm').text('');
						$('#checkOrgNm').val('Y');
					}
				}
			});
		});
		
		//관리자구분 별 항목 온오프,${user.userMemLvl}
		if('${user.userMemLvl}'=='3'){
			$('#tr-orgNm').hide();
			$('#tr-orgEn').hide();
			$('#tr-jigin').hide();
		}
	});
	
	function fn_updPw(){
		$.ajax({
			url: "${utcp.ctxPath}/admin/member/mngrPwUpd.json",
			type: "post",
			data: {
				"userId" : $("#userId").val(),
				"userPw" : $("#userPw").val()
			},
			cache: false,
			async: true,
			success: function(r) {
				if(r.isSuperAdmin){
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
	
	function fn_list(){
		location.href = "${utcp.ctxPath}/admin/member/mngrList.do";
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
		
		var formData = new FormData($('#mngrRgsFrm')[0]);
		console.log(formData);
		formData.append("jigin", $("#jigin")[0].files[0]);
		
		$.ajax({
			contentType : false,
			processData : false,
			url: "${utcp.ctxPath}/admin/member/mngrUpdProc.json",
			type: "post",
			data: formData,
			cache: false,
			async: true,
			success: function(r) {
				if(r.isSuperAdmin){
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
	
	function fn_del(){
		$.ajax({
			url: "${utcp.ctxPath}/admin/member/mngrDelete.json",
			type: "post",
			data: {
				"userId" : $("#userId").val()
			},
			cache: false,
			async: true,
			success: function(r) {
				if(r.isSuperAdmin){
					if(r.isSuccess){
						location.href = "#successDel";
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
		location.href = "/admin/member/mngrUpd.do?userId=" + $("#userId").val();
	}
</script>



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
						<input type="hidden" id="userId" name="userId" value="${user.userId}" />
						<input type="hidden" id="loginId" name="loginId" value="${user.loginId}" />
						<input type="hidden" id="userMemLvl" name="userMemLvl" value="${user.userMemLvl}" />
						<input type="hidden" name="orgCd" value="${user.orgCd }"/>
						${user.loginId}
					</td>									
				</tr>
				<tr id="tr-orgNm">
					<th class="tdbg3 tc"><label for="orgCd">기관명</label></th>
						<td class="tl" colspan="3">
							<input type="text" name="orgNm" id="orgNm" class="ip1" maxlength="50" placeholder="기관이름" value="${org.orgNm }"/> 
							<input type="hidden" id="checkOrgNm" value="Y"/> 
							<span id="msg-orgnm"></span>
						</td>
					</tr>
				<tr>
				<tr id="tr-orgEn">
					<th class="tdbg3 tc"><label for="orgEn">기관영문</label></th>
						<td class="tl" colspan="3">
							<input type="text" name="orgEn" id="orgEn" class="ip1" maxlength="50" placeholder="기관영문" value="${org.orgEn }"/> 
						</td>
					</tr>
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
						<input type="text" class="ip1" id="tel" name="tel" value="${user.tel}" maxlength="13"/>
					</td>
				</tr>
				<tr id="tr-jigin">
					<th class="tdbg3 tc">직인</th>
					<td>
						<c:if test="${not empty org.jiginFileNm }">
					<img src="http://edu.cdn.gov-ntruss.com/upload/jigin/${org.jiginFileNm }" width="100"/>
					</c:if>
					<input type="checkbox" name="jiginDel" />삭제 
					<input type="file" id="jigin"/>
					</td>
				</tr>
				<c:if test="${user.userMemLvl != '1'}">		
				<%-- <tr>
					<th class="tdbg3 tc"><label for="orgEn">모듈</label></th>
					<td class="tl menu_td" colspan="3">
						<div class="sub_menuIp">
						<label><input type="checkbox" class="m0500" id="useCheck" name="useCheck" value="1" ${org.useCheck==1?'checked':'' }/> 전자출결</label>
						<label><input type="checkbox" class="m0500" id="useGooroomee" name="useGooroomee" value="1" ${org.useGooroomee==1?'checked':'' }/> 구루미</label>
						<label><input type="checkbox" class="m0500" id="useMov" name="useMov" value="1" ${org.useMov==1?'checked':'' }/> 동영상</label>
						</div>
					</td>
				</tr> --%>
				<tr>
					<th class="tdbg3 tc">메뉴권한</th>
					<td class="tl menu_td" colspan="3">
						<label for="A_010000" class="cb font_16">
							<input type="checkbox" id="A_010000" class="depth01" />
							회원관리
						</label>
						<div class="sub_menuIp mgb15">
							ㄴ
							<label for="A_010100">
								<input type="checkbox" class="m0100" id="A_010100" name="menuIds" value="A_010100" />회원정보
							</label>
							<label for="A_010200">
								<input type="checkbox" class="m0100" id="A_010200" name="menuIds" value="A_010200" />강사정보
							</label>
							<label for="A_010300">
								<input type="checkbox" class="m0100" id="A_010300" name="menuIds" value="A_010300" />휴면계정 관리
							</label>
						</div>
						<label for="A_020000" class="cb font_16">
							<input type="checkbox" id="A_020000" class="depth02" />
							교육관리
						</label>
						<div class="sub_menuIp mgb15">
							ㄴ
							<label for="A_020100">
								<input type="checkbox" class="m0200" id="A_020100" name="menuIds" value="A_020100" />교육관리
							</label>
							<label for="A_020200">
								<input type="checkbox" class="m0200" id="A_020200" name="menuIds" value="A_020200" />카테고리 관리
							</label>
							<label for="A_020300">
								<input type="checkbox" class="m0200" id="A_020300" name="menuIds" value="A_020300" />동영상 관리
							</label>
							<label for="A_020400">
								<input type="checkbox" class="m0200" id="A_020400" name="menuIds" value="A_020400" />피드백 관리
							</label>
							<label for="A_020500">
								<input type="checkbox" class="m0200" id="A_020500" name="menuIds" value="A_020500" />전자출결현황
							</label>
						</div>																					
						<label for="A_030000" class="cb font_16">
							<input type="checkbox" id="A_030000" class="depth03" />
							통계관리
						</label>
						<div class="sub_menuIp mgb15">
							ㄴ
							<label for="A_030100">
								<input type="checkbox" class="m0300" id="A_030100" name="menuIds" value="A_030100" />연도별 통계
							</label>
							<label for="A_030200">
								<input type="checkbox" class="m0300" id="A_030200" name="menuIds" value="A_030200" />월별 통계
							</label>
							<label for="A_030300">
								<input type="checkbox" class="m0300" id="A_030300" name="menuIds" value="A_030300" />기관별 통계
							</label>
							<label for="A_030400">
								<input type="checkbox" class="m0300" id="A_030400" name="menuIds" value="A_030400" />수강료 통계
							</label>
						</div>												
						<label for="A_040100" class="cb font_16 mgb15">
							<input type="checkbox" id="A_040100" name="menuIds" value="A_040100" />
							교육 자료실
						</label><br/>
						<label for="A_050000" class="cb font_16">
							<input type="checkbox" id="A_050000" class="depth05" />
							사이트 관리
						</label>
						<div class="sub_menuIp mgb15">
							ㄴ
							<label for="A_050100">
								<input type="checkbox" class="m0500" id="A_050100" name="menuIds" value="A_050100" />공지사항 관리
							</label>
							<label for="A_050200">
								<input type="checkbox" class="m0500" id="A_050200" name="menuIds" value="A_050200" />온라인 문의 관리
							</label>
							<label for="A_050300">
								<input type="checkbox" class="m0500" id="A_050300" name="menuIds" value="A_050300" />자유 게시판 관리
							</label>
							<label for="A_050400">
								<input type="checkbox" class="m0500" id="A_050400" name="menuIds" value="A_050400" />자료실 관리
							</label>
							<label for="A_050500">
								<input type="checkbox" class="m0500" id="A_050500" name="menuIds" value="A_050500" />팝업창 관리
							</label>
							<label for="A_050600">
								<input type="checkbox" class="m0500" id="A_050600" name="menuIds" value="A_050600" />SMS 관리
							</label>
						</div>
						<label for="A_060100" class="cb font_16 mgb15">
							<input type="checkbox" id="A_060100" name="menuIds" value="A_060100" />
							시험지관리
						</label>
						<div class="sub_menuIp mgb15">
						</div>
						<label for="A_070000" class="cb font_16">
							<input type="checkbox" id="A_070000" class="depth07" />
							컨설팅
						</label>
						<div class="sub_menuIp mgb15">
							ㄴ
							<label for="A_070100">
								<input type="checkbox" class="m0700" id="A_070100" name="menuIds" value="A_070100" />신청관리
							</label>
							<label for="A_070200">
								<input type="checkbox" class="m0700" id="A_070200" name="menuIds" value="A_070200" />자료실
							</label>
						</div>
						<label for="A_080000" class="cb font_16">
							<input type="checkbox" id="A_080000" class="depth08" />
							공모전
						</label>
						<div class="sub_menuIp mgb15">
							ㄴ
							<label for="A_080100">
								<input type="checkbox" class="m0800" id="A_080100" name="menuIds" value="A_080100" />신청관리
							</label>
							<label for="A_080200">
								<input type="checkbox" class="m0800" id="A_080200" name="menuIds" value="A_080200" />갤러리
							</label>
						</div>
						<label for="A_090000" class="cb font_16">
							<input type="checkbox" id="A_090000" class="depth07" />
							체험관
						</label>
						<div class="sub_menuIp mgb15">
							ㄴ
							<label for="A_090100">
								<input type="checkbox" class="m0900" id="A_090100" name="menuIds" value="A_090100" />신청관리
							</label>
							<label for="A_090200">
								<input type="checkbox" class="m0900" id="A_090200" name="menuIds" value="A_090200" />공지사항
							</label>
						</div>
					</td>
				</tr>
				</c:if>
				<tr>
					<th class="tdbg3 tc"><label for="wr_name">접속이력</label></th>
					<td class="tl" colspan="3">
						<div class="accessList">
							<table width="100%" class="tb01 tc">
								<thead bgcolor="#f7f8fa">
									<tr>
										<th>NO</th>
										<th>접속일시</th>
										<th>접속 IP</th>
										<th>접근메뉴</th>
									</tr>
								</thead>
								<tbody class="accessList">
									<c:choose>
										<c:when test='${vo.connLogList != null && fn:length(vo.connLogList) > 0}'>
						                	<c:forEach var="log" items="${vo.connLogList}" varStatus="stat">
												<tr>
													<td>${20 - stat.index}</td>
													<td><fmt:formatDate value="${log.connDe}" pattern="yyyy-MM-dd / HH:mm" /></td>
													<td>${log.ip}</td>
													<td>${log.menuNm}</td>
												</tr>
						                	</c:forEach>
					                	</c:when>
					                	<c:otherwise>
					                		<tr>
					                			<td colspan="4" style="text-align:center; height:80px;">접속이력이 없습니다.</td>
					                		</tr>
					                	</c:otherwise>
					                </c:choose>												
								</tbody>
								
							</table>
						</div>
					</td>
				</tr>								
			</tbody>
		</form>
		</table>
		<div class="fl tc">
			<button class="btn02 btn_grayl" onclick="fn_list(); return false;">목록</button>
		</div>
			<div class="fr tc">
				<button class="btn01 btn_green" onclick="fn_confirmMsg('U'); return false;">수정</button>
		<c:if test="${user.userMemLvl != '1'}">			
				<button class="btn01 btn_black" onclick="fn_confirmMsg('D'); return false;">삭제</button>
		</c:if>								
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
