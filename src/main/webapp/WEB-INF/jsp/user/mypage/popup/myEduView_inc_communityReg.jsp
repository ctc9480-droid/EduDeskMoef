<%@page import="com.educare.edu.member.service.SessionUserInfoHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>     
<script src="${utcp.ctxPath}/resources/ckeditor4_full/ckeditor.js"></script>

<!--// write //-->
<div class="popWrite">
    <form method="post" id="eduCommunityRegForm">
    	<input type="hidden" id="boardType" name="boardType" value="eduCommunity" /> 
		<input type="hidden" id="idx" name="idx" value="${lectureCommunity.idx }" /> 
		<input type="hidden" name="eduSeq" id="edu_seq" value="${param.eduSeq }" />
        <table class="tb04">
            <caption class="sound_only">옵션, 제목, 내용, 파일첨부 순으로 입력 하실 수 있습니다. </caption>
            <tbody>
                <tr>
                    <th scope="row">제목</th>
                    <td>
                        <input value="${lectureCommunity.title }" type="text" id="title" name="title"  maxlength="50" class="ip3" placeholder="제목"  style="width:70%;float:left;"/>
                    </td>
                </tr>
                <tr>
                    <th scope="row">공개여부</th>
                    <td>
                        <input type="radio" name="scrtyYn" id="scrtyYn1" value="N" ${lectureCommunity.scrtyYn ne 'Y'?'checked':'' } class="wauto vm mgb3"/>
                        <label class="mgr15 vm" for="scrtyYn1">공개</label>
                        <input type="radio" name="scrtyYn" id="scrtyYn2" value="Y" ${lectureCommunity.scrtyYn eq 'Y'?'checked':'' } class="wauto vm mgb3"/>
                        <label class="mgr15 vm" for="scrtyYn2">비공개</label>
                    </td>
                </tr>
                <tr>
                    <th scope="row">내용</th>
                    <td>
                        <textarea id="ckeditCn" name="ckeditCn" placeholder="내용" class="w100 h500" style="height: 100%"></textarea> 
                        <input type="hidden" id="content" name="content" /></td>
                    <!-- 
                      <textarea name="content" placeholder="내용" rows="4" cols="4" class="" style="width: 100%;">${lectureCommunity.content }</textarea>
                     -->
                    </td>
                </tr>
            </tbody>
        </table>
        <div class="w100 tc mgt20">
            <a href="javascript:fn_RegBbs();" class="btn02 btn_blue">작성완료</a>
            <a href="javascript:history.back();" class="btn02 btn_gray">취소</a>
        </div>
    </form>
</div>
<script>
//에디터에서 모든 태그 허용,211110
/* 
 */
CKEDITOR.config.allowedContent = true;
CKEDITOR.replace('ckeditCn', {
	filebrowserUploadUrl : '${utcp.ctxPath}/editot/popupUpload.json?gubun=lectureBoard&prefixStr=eduCommunity_'+$('#edu_seq').val(),
	height : 400,
});

function fn_RegBbs() {
	if ($("#title").val() == "") {
		//$("#messageStr").html("제목은 필수입력입니다.");
		//location.href = "#message";
		alert('제목은 필수입력입니다.');
		$("#title").focus();
		return;
	}
/* 
	 */
	if (CKEDITOR.instances.ckeditCn.getData() == "") {
		//$("#messageStr").html("내용은 필수입력입니다.");
		//location.href = "#message";
		alert('내용은 필수입력입니다.');
		return false;
	} else {
		var content = CKEDITOR.instances.ckeditCn.getData();
	}
	var title = document.querySelector("#title").value;
	//var content = $("textarea[id=content]").val();
	var boardType = document.querySelector("#boardType").value;
	var eduSeq = document.querySelector("#edu_seq").value;
	var scrtyYn = document.querySelector('input[type="radio"][name="scrtyYn"]:checked').value;
	var boardIdx = $('#boardIdx').val();
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
			boardIdx : boardIdx
		},
		success : function(r){
			if(r.result == 1){
				fn_boardView();
				return;
			}			
			alert(r.msg);
		}
	});
}
function fn_boardView(){
	$('#tabAction').val('communityList');
	$('#myEduForm').submit();
}

</script>


<!--// write //-->

