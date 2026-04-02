<%@page import="com.educare.edu.member.service.SessionUserInfoHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<script src="${utcp.ctxPath}/resources/ckeditor4_full/ckeditor.js"></script>

<!--// write //-->
<div class="popWrite">
	<form name="eduCommunityForm">
		<input type="hidden" name="boardType" value="eduCommunity" />
		<input type="hidden" name="idx" value="${eduCommunity.idx }" />
		<table class="tb04">
			<caption class="sound_only">옵션, 제목, 내용, 파일첨부 순으로 입력 하실 수 있습니다.</caption>
			<tbody>
				<tr>
					<th scope="row">제목</th>
					<td><input type="text" id="title" name="title" maxlength="50" class="ip4" placeholder="제목" value="${lectureCommunity.title }" /></td>
				</tr>
				<tr>
					<th scope="row">공개여부</th>
					<td><input type="radio" name="scrtyYn" id="scrtyYn1" value="N" ${lectureCommunity.scrtyYn ne 'Y'?'checked':'' } />
						<label for="scrtyYn1">공개</label> <input type="radio" name="scrtyYn" id="scrtyYn2" value="Y" ${lectureCommunity.scrtyYn eq 'Y'?'checked':'' } />
						<label for="scrtyYn2">비공개</label></td>
				</tr>
				<tr>
					<th scope="row">내용</th>
					<td>
					
					<%-- <textarea id="i_content" name="content" placeholder="내용" class="w100 h500" style="height: 200px;">${lectureCommunity.content }</textarea> --%>
					<textarea id="ckeditCn" name="ckeditCn" placeholder="내용" class="w100 h500" style="height: 200px;">${lectureCommunity.content }</textarea> 
                    	<input type="hidden" id="content" name="content" />
					</td>
					</td>
				</tr>
			</tbody>
		</table>
		<div class="w100 tc">
			<a href="javascript:fn_RegBbs();" class="btn02 btn_blue">작성완료</a> <a href="javascript:fn_cancelBbs();" class="btn02 btn_gray">취소</a>
		</div>
	</form>
</div>
<script>
CKEDITOR.config.allowedContent = true;
CKEDITOR.replace('ckeditCn', {
	filebrowserUploadUrl : '${utcp.ctxPath}/editot/popupUpload.json?gubun=lectureBoard&prefixStr=eduCommunity_'+$('#edu_seq').val(),
	height : 400,
});

function fn_RegBbs() {
	if ($("#title").val() == "") {
		alert('제목은 필수 입력입니다.');
		return;
	}

	//var content = document.eduCommunityForm.content.value;
	//if ($("#i_content").val() == "") {
	//	alert("내용은 필수입력입니다.");
	//	return false;
	//} 
	var title = document.eduCommunityForm.title.value;
	var boardType = document.eduCommunityForm.boardType.value;
	var eduSeq = $('#edu_seq').val();
	var scrtyYn = document.querySelector('input[type="radio"][name="scrtyYn"]:checked').value;
	var boardIdx = $('#boardIdx').val();
	
	if (CKEDITOR.instances.ckeditCn.getData() == "") {
		alert('내용은 필수입력입니다.');
		return false;
	} else {
		var content = CKEDITOR.instances.ckeditCn.getData();
	}
	
	$.ajax({
		url:'${utcp.ctxPath}/user/mypage/popup/communityWriteProc.json',
		type : "post",
		dataType : "json",
		data :  { 
			title : title,
			content : content,
			boardType : boardType,
			eduSeq : eduSeq,
			scrtyYn : scrtyYn,
			boardIdx : boardIdx,
		},
		success : function(r){
			if(r.result == 1){
				fn_boardView();
				return;
			}
			alert(r.msg);
			return;
		}
	});
}
function fn_boardView(){
	$('#tabAction').val('communityList');
	$('#myEduForm').submit();
}
function fn_cancelBbs(){
	if($('#boardIdx').val()!='0'){
		$('#tabAction').val('communityView');
	}else{
		$('#tabAction').val('communityList');
	}
		$('#myEduForm').submit();
}
</script>


<!--// write //-->

