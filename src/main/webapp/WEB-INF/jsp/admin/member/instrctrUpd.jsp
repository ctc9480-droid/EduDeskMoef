<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp" %>
<script>
	var funcCd = "instrctr";
	var maxCnt = 5;
	var maxSize = 10000000;
	
	var cnt01 = 0;
	var size01 = 0;
	var cnt02 = 0;
	var size02 = 0;
	var cnt03 = 0;
	var size03 = 0;
	var cnt09 = 0;
	var size09 = 0;
	
	$(function(){
		$(".onlyNumber").keyup(function(event){      
	        var str;                     
	        if(event.keyCode != 8){
	            if (!(event.keyCode >=37 && event.keyCode<=40)) {
	                var inputVal = $(this).val();       
	                str = inputVal.replace(/[^0-9]/gi,'');            
	                $(this).val(str);             
	            }                   
	        }
	    });
		
		<c:if test='${attach01 != null && fn:length(attach01) > 0}'>
	    	<c:forEach var="attach" items="${attach01}" varStatus="stat">
	    		cnt01 += 1;
	    		size01 += Number(${attach.fileSize});
	    	</c:forEach>
	    	$("#f_cnt_01").html(cnt01);
			$("#f_size_01").html(Math.floor(size01 / 1000));
		</c:if>
		
		<c:if test='${attach02 != null && fn:length(attach02) > 0}'>
	    	<c:forEach var="attach" items="${attach02}" varStatus="stat">
	    		cnt02 += 1;
	    		size02 += Number(${attach.fileSize});
	    	</c:forEach>
	    	$("#f_cnt_02").html(cnt02);
			$("#f_size_02").html(Math.floor(size02 / 1000));
		</c:if>
	
		<c:if test='${attach03 != null && fn:length(attach03) > 0}'>
			<c:forEach var="attach" items="${attach03}" varStatus="stat">
				cnt03 += 1;
				size03 += Number(${attach.fileSize});
			</c:forEach>
			$("#f_cnt_03").html(cnt03);
			$("#f_size_03").html(Math.floor(size03 / 1000));
		</c:if>
		
		<c:if test='${attach09 != null && fn:length(attach09) > 0}'>
			<c:forEach var="attach" items="${attach09}" varStatus="stat">
				cnt09 += 1;
				size09 += Number(${attach.fileSize});
			</c:forEach>
			$("#f_cnt_09").html(cnt09);
			$("#f_size_09").html(Math.floor(size09 / 1000));
		</c:if>
		
		<c:if test='${moduleList != null && fn:length(moduleList) > 0}'>
			<c:forEach var="module" items="${moduleList}" varStatus="stat">
				$("input:checkbox[id=${module.ctgrySeq}]").prop("checked", true);	
			</c:forEach>
		</c:if>
		
		//강사 이메일 중복 체크
		$('#email').keyup(function(){
			$.ajax({
		        url: "instrctrEmailChk.json",
		        type: "POST",
		        data: { "email" : $("#email").val()},
		        success: function(r) {
		        	if(r.result!=''){
						$('#msg-email').html(r.result);
						$('#checkEmail').val('N');
					}else{
						$('#msg-email').text('Success');
						$('#checkEmail').val('Y');
					}
				}
		    });
		});
	});

	function fn_rgs(){
		
		if($("#userNm").val() == ""){
			$("#messageStr").html("성명은 필수입력입니다.");
			location.href = "#message";
			return;
		}
		
		if($("#mobile").val() == ""){
			$("#messageStr").html("휴대폰번호 는 필수입력입니다.");
			location.href = "#message";
			return;
		}
			
		$("#instrctrRgsFrm").attr("action", "/admin/member/instrctrUpdProc.do");
		$("#instrctrRgsFrm").submit();		

	}
	
	function fn_list(){
		location.href = "/admin/member/instrctrList.do";
	}
	
	function fn_uploadPop(fileSection){
		var totalCnt = Number($("#f_cnt_" + fileSection).text());
		var totalSize = Number($("#f_size_" + fileSection).text()) * 1000;
		fn_uploadInit(totalCnt, totalSize, maxCnt, maxSize, fileSection, funcCd);
	}
	
	function fn_uploadSuccess(tmpFileSeq, fileRename, fileOrg, fileType, fileSize, totalCnt, totalSize, fileSection, funcCd){
		$("#f_cnt_" + fileSection).html(totalCnt);
		$("#f_size_" + fileSection).html(totalSize);
		$("#fileSeqs_" + fileSection).val($("#fileSeqs_" + fileSection).val() + "|T_" + tmpFileSeq);
		$("#fileBox_" + fileSection).append( "<option value=\'T_" + tmpFileSeq + "\'>" + fileOrg + "</option>" );
	}
	
	function fn_fileDel(fileSection){
		var sectionId = "fileBox_" + fileSection;
		var num = $( "#" + sectionId + " option" ).index( $( "#" + sectionId + " option:selected" ) );
		if( num == -1 ) {
			$("#messageStr").html("선택된 파일이 없습니다.");
			location.href = "#message";
			$("#" + sectionId).focus();
		} else {
			var totalCnt = Number($("#f_cnt_" + fileSection).text());
			var totalSize = Number($("#f_size_" + fileSection).text());
			var fileSeq = $("select[id='" + sectionId + "'] option:selected").val();
			
			if(fileSeq.indexOf("T_") != -1){
				$.ajax({
			        url: "/tempFile/delete.json",
			        type: "POST",
			        data: { "tmpfileSeq" : fileSeq},
			        cache: false,
					async: true,
			        success: function(r) {
						if(r.isSuccess){
							$("#f_cnt_" + fileSection).html(totalCnt - 1);
							$("#f_size_" + fileSection).html(totalSize - Math.floor(Number(r.fileSize) / 1000));
							var seqs = $("#fileSeqs_" + fileSection).val();
							$("#fileSeqs_" + fileSection).val(seqs.replace("|" + fileSeq, ""));
							$("select[id='" + sectionId + "'] option:selected").remove();		
						}else{
							$("#messageStr").html(r.message);
							location.href = "#message";
						}
					}
			    });	
			}else{
				$.ajax({
			        url: "/instrctr/attach/size.json",
			        type: "POST",
			        data: { "fileSeq" : fileSeq},
			        cache: false,
					async: true,
			        success: function(r) {
						if(r.isSuccess){
							$("#f_cnt_" + fileSection).html(totalCnt - 1);
							$("#f_size_" + fileSection).html(totalSize - Math.floor(Number(r.fileSize) / 1000));
							var seqs = $("#fileSeqs_" + fileSection).val();
							$("#attachDelSeqs").val($("#attachDelSeqs").val() + "|" + fileSeq);
							$("select[id='" + sectionId + "'] option:selected").remove();		
						}else{
							$("#messageStr").html("오류가 발생하였습니다.");
							location.href = "#message";
						}
					}
			    });	
			}
		}
	}
	
	function fn_photoDel(){
		$("#photoOrg").val("");
		$("#photoRename").val("");
		$("#photo_div").html(
			"<label for=\"wr_file\"><span class=\"sound_only\">파일</span></label>" + 
			"<input type=\"file\" name=\"filePhoto\" id=\"filePhoto\" title=\"파일첨부\" class=\"frm_file\">"
		);
	}
	
	function fn_updPw(){
		$.ajax({
			url: "${utcp.ctxPath}/admin/member/instrctrPwUpd.json",
			type: "post",
			data: {
				userPw : $("#userPw").val(),userId:'${user.email}'
			},
			cache: false,
			async: true,
			success: function(r) {
				if(r.isAdmin){
					if(r.isSuccess){
						$("#messageStr").html("비밀번호가 변경되었습니다.");
						location.href = "#message";
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
</script>

<section class="pd025 pd200 po_re">
	<div class="po_re br5 bs_box">
		<form method="post" id="instrctrRgsFrm" enctype="multipart/form-data">
			<input type="hidden" name="userId" id="userId" value="${user.userId}"/>
			<input type="hidden" name="photoOrg" id="photoOrg" value="${user.photoOrg}"/>
			<input type="hidden" name="photoRename" id="photoRename" value="${user.photoRename}"/>
			<input type="hidden" name="attachDelSeqs" id="attachDelSeqs"/>
			<table width="100%" class="tb02 tc">
				<caption class="sound_only">강사등록테이블</caption>	
				<tbody>
					<tr>
						<th class="tdbg3 tc"><label for="userNm">아이디</label></th>
						<td class="tl">
							${user.userId}
						</td>
						<th class="tdbg3 tc"><label for="wr_file">패스워드</label></th>
						<td class="tl">
							<input type="text" name="userPw" id="userPw" class="ip2"/>
							<button class="btn04 btn_orange" onclick="fn_updPw(); return false;">비밀번호 변경</button>
						</td>
					</tr>
				
					<tr>
						<th class="tdbg3 tc"><label for="userNm">성&nbsp;&nbsp;&nbsp;&nbsp;명</label></th>
						<td class="tl">
							<input type="text" name="userNm" id="userNm" class="ip3" value="${user.userNm}"/>
						</td>
						<th class="tdbg3 tc"><label for="wr_file">사&nbsp;&nbsp;&nbsp;&nbsp;진</label></th>
						<td class="tl">
							<div class="write_div" id="photo_div">
								<c:choose>
									<c:when test="${user.photoOrg != null && user.photoOrg != ''}">
										${user.photoOrg}
										<button class="btn04 btn_gray" onclick="fn_photoDel(); return false;">삭제</button>
									</c:when>
									<c:otherwise>
										<label for="wr_file"><span class="sound_only">파일</span></label>
										<input type="file" name="filePhoto" id="filePhoto" title="파일첨부" class="frm_file ">
									</c:otherwise>
								</c:choose>
							</div>
						</td>
					</tr>
					<tr>
						<th class="tdbg3 tc"><label for="mobile">휴대폰번호 </label></th>
						<td class="tl">
							<input type="tel" name="mobile" id="mobile" value="${user.mobile}" class="ip3 tc onlyNumber" maxlength="12"/>
						</td>
						<th class="tdbg3 tc"><label for="email">이메일</label></th>
						<td class="tl">
							<input type="email" name="email" id="email" value="${user.email}" class="ip2"/>
							<span id="msg-email"></span>
						</td>									
					</tr>
					<tr>
						<th class="tdbg3 tc"><label for="belong">소&nbsp;&nbsp;&nbsp;&nbsp;속</label></th>
						<td class="tl">
							<input type="text" name="belong" id="belong" value="${user.belong}" class="ip3"/>
						</td>
						<th class="tdbg3 tc"><label for="position">직&nbsp;&nbsp;&nbsp;&nbsp;위</label></th>
						<td class="tl">
							<input type="text" name="position" id="position" value="${user.position}" class="ip3" />
						</td>									
					</tr>
					<tr>
						<th class="tdbg3 tc">전문분야</th>
						<td class="tl" colspan="3">
							<input type="text" name="area" id="area" class="ip3" value="${user.area }"/>
						</td>
					</tr>								
					<tr style="display:none;">
						<th class="tdbg3 tc">모&nbsp;&nbsp;&nbsp;&nbsp;듈</th>
						<td class="tl" colspan="3">
							<c:if test='${ctgryList != null && fn:length(ctgryList) > 0}'>
			                	<c:forEach var="ctgry" items="${ctgryList}" varStatus="stat">
			                		<label for="${ctgry.ctgrySeq}" class="mgr20">
										<input type="checkbox" id="${ctgry.ctgrySeq}" class="vm" name="ctgrySeqs" value="${ctgry.ctgrySeq}" />
										<span>${ctgry.ctgryNm}</span>
									</label>
			                	</c:forEach>
		                	</c:if>
						</td>
					</tr>
					<tr>
						<th class="tdbg3 tc">
							<label for="memo">메&nbsp;&nbsp;&nbsp;&nbsp;모</label>
						</th>
						<td class="tl" colspan="3">
							<textarea id="memo" name="memo" class="textareaCon" maxlength="65536">${user.memo}</textarea>
						</td>
					</tr>
					<tr>
						<th class="tdbg3 tc">
							<label for="fileBox_01">자격증</label>
						</th>
						<td class="tl" colspan="3">
							<input type="hidden" name="fileSeqs01" id="fileSeqs_01"/>
							<label for="fileBox_01" class="multiSelectBoxWrap">
								<span class="dp_b fc_blue">[총 <span id="f_cnt_01">0</span>개] <span id="f_size_01">0</span>KB / 10MB</span>
								<select id="fileBox_01" multiple="multiple" size="5" class="multiSelectBox">
									<c:if test='${attach01 != null && fn:length(attach01) > 0}'>
								    	<c:forEach var="attach" items="${attach01}" varStatus="stat">
								    		<option value="${attach.fileSeq}">${attach.fileOrg}</option>
								    	</c:forEach>
									</c:if>
								</select>
							</label>
							<div class="multiSelectBoxBtn">
								<button class="btn04 btn_blue" onclick="fn_uploadPop('01'); return false;">파일선택</button>
								<button class="btn04 btn_gray" onclick="fn_fileDel('01'); return false;">선택삭제</button>										
							</div>
						</td>
					</tr>
					<tr>
						<th class="tdbg3 tc">
							<label for="fileBox_02">이력서
								<span class="dp_b">(강의경력 포함)</span>
							</label>
						</th>
						<td class="tl" colspan="3">
							<input type="hidden" name="fileSeqs02" id="fileSeqs_02"/>
							<label for="fileBox_02" class="multiSelectBoxWrap">
								<span class="dp_b fc_blue">[총 <span id="f_cnt_02">0</span>개] <span id="f_size_02">0</span>KB / 10MB</span>
								<select id="fileBox_02" multiple="multiple" size="5" class="multiSelectBox">
									<c:if test='${attach02 != null && fn:length(attach02) > 0}'>
								    	<c:forEach var="attach" items="${attach02}" varStatus="stat">
								    		<option value="${attach.fileSeq}">${attach.fileOrg}</option>
								    	</c:forEach>
									</c:if>
								</select>
							</label>
							<div class="multiSelectBoxBtn">
								<button class="btn04 btn_blue" onclick="fn_uploadPop('02'); return false;">파일선택</button>
								<button class="btn04 btn_gray" onclick="fn_fileDel('02'); return false;">선택삭제</button>											
							</div>
						</td>
					</tr>
					<tr>
						<th class="tdbg3 tc">
							<label for="fileBox_03">내부교육수료증
								<span class="dp_b">(교육기록)</span>
							</label>
						</th>
						<td class="tl" colspan="3">
							<input type="hidden" name="fileSeqs03" id="fileSeqs_03"/>
							<label for="fileBox_03" class="multiSelectBoxWrap">
								<span class="dp_b fc_blue">[총 <span id="f_cnt_03">0</span>개] <span id="f_size_03">0</span>KB / 10MB</span>
								<select id="fileBox_03" multiple="multiple" size="5" class="multiSelectBox">
									<c:if test='${attach03 != null && fn:length(attach03) > 0}'>
								    	<c:forEach var="attach" items="${attach03}" varStatus="stat">
								    		<option value="${attach.fileSeq}">${attach.fileOrg}</option>
								    	</c:forEach>
									</c:if>
								</select>
							</label>
							<div class="multiSelectBoxBtn">
								<button class="btn04 btn_blue" onclick="fn_uploadPop('03'); return false;">파일선택</button>
								<button class="btn04 btn_gray" onclick="fn_fileDel('03'); return false;">선택삭제</button>										
							</div>
						</td>
					</tr>								
					<tr>
						<th class="tdbg3 tc">
							<label for="fileBox_09">기타자료</label>
						</th>
						<td class="tl" colspan="3">
							<input type="hidden" name="fileSeqs09" id="fileSeqs_09"/>
							<label for="fileBox_09" class="multiSelectBoxWrap">
								<span class="dp_b fc_blue">[총 <span id="f_cnt_09">0</span>개] <span id="f_size_09">0</span>KB / 10MB</span>
								<select id="fileBox_09" multiple="multiple" size="5" class="multiSelectBox">
									<c:if test='${attach09 != null && fn:length(attach09) > 0}'>
								    	<c:forEach var="attach" items="${attach09}" varStatus="stat">
								    		<option value="${attach.fileSeq}">${attach.fileOrg}</option>
								    	</c:forEach>
									</c:if>
								</select>
							</label>
							<div class="multiSelectBoxBtn">
								<button class="btn04 btn_blue" onclick="fn_uploadPop('09'); return false;">파일선택</button>
								<button class="btn04 btn_gray" onclick="fn_fileDel('09'); return false;">선택삭제</button>											
							</div>
						</td>
					</tr>										
				</tbody>
			</table>
		</form>		
		<div class="fl tc">
			<button class="btn02 btn_grayl" onclick="fn_list(); return false;">목록</button>
		</div>
		<div class="fr tc">
			<button class="btn01 btn_greenl" onclick="fn_rgs(); return false;">수정하기</button>
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

<!--// filelayerPop //-->
<jsp:include page="/WEB-INF/jsp/admin/common/upload.jsp"/>
<!--// filelayerPop //-->