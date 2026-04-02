<%@page import="com.educare.edu.member.service.SessionUserInfoHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>     
<script src="${utcp.ctxPath}/resources/ckeditor4_full/ckeditor.js"></script>

<!-- dropzone -->
<script src="${utcp.ctxPath}/resources/plugins/dropzone/dropzone.js"></script>
<link rel="stylesheet" href="${utcp.ctxPath}/resources/plugins/dropzone/dropzone.css">

<!--// write //-->
<div class="popWrite">
    <form name="eduNoticeForm">
    	<input type="hidden" name="boardType" value="eduNotice" /> 
    	<input type="hidden" name="idx" value="${eduNotice.idx }" /> 
    	<input type="hidden" name="scrtyYn" value="N" /> 
        <table class="tb04">
            <caption class="sound_only">옵션, 제목, 내용, 파일첨부 순으로 입력 하실 수 있습니다.</caption>
            <tbody>
                <tr>
                    <th scope="row">제목</th>
                    <td>
                        <input type="text" id="title" name="title"  maxlength="50" class="ip4" placeholder="" value="${lectureBoard.title }"/>
                    </td>
                </tr>
                <tr>
                    <th scope="row">내용</th>
                    <td>
                    	<textarea id="ckeditCn" name="ckeditCn" placeholder="내용" class="w100 h500" style="height: 200px;">${lectureBoard.content }</textarea> 
                    	<input type="hidden" id="content" name="content" />
                        </td>
                    </td>
                </tr>
                <tr>
					<th class="tdbg3 tc"><label for="fileBox_01">첨부파일</label></th>
					<td class="tl" colspan="">
						<!-- dropzone html -->
						<div class="dropzone" id="board-dropzone">
							<div class="fallback">
								<input name="file" type="file" multiple />
							</div>
							<input name="image_url" id="image_url" type="hidden" />
						</div>
							<c:forEach items="${attachList }" var="o">
							<div><span>${o.fileOrg }</span> <a href="#none" onclick="fn_delFile(${o.fileSeq})">[삭제]</a></div>
							</c:forEach>
					</td>
				</tr>
            </tbody>
        </table>
        <div class="w100 tc">
            <a href="javascript:fn_RegBbs();" class="btn02 btn_blue">작성완료</a>
            <a href="javascript:fn_cancelBbs();" class="btn02 btn_gray">취소</a>
        </div>
    </form>
</div>
<script>
CKEDITOR.config.allowedContent = true;
CKEDITOR.replace('ckeditCn', {
	filebrowserUploadUrl : '${utcp.ctxPath}/editot/popupUpload.json?gubun=lectureBoard&prefixStr=eduNotice_'+$('#edu_seq').val(),
	height : 400,
});

Dropzone.autoDiscover = false;
var boardDropzone = new Dropzone("#board-dropzone", {
	url : "${utcp.ctxPath}/user/edu/bbs/dropzoneUpload.ajax",
	paramName: "file",
    maxFilesize: 5,
    maxFiles: 2,
    //acceptedFiles: "image/*,application/pdf,.xlsx",
    //acceptedFiles: null,	// dykim, 210701, 모든 파일 업로드 가능
    autoProcessQueue: false,
    uploadMultiple: true,
    addRemoveLinks:true,
    parallelUploads:2,
    dictDefaultMessage:'+ 마우스로 파일을 끌고 오거나 여기를 클릭하세요<br><br><font style="color:red">5MB 이하의 파일만 전송 가능합니다</font>',
    init: function () {
    	this.on("success", function(file, returnedData, myEvent) {
    		
    		fn_boardView();
       	}); 
    	this.on('sending', function(file, xhr, formData) {
    		if(formData.get('idx')==null){
    			
    			formData.append('idx', $('#boardIdx').val());
    			formData.append('eduSeq', document.eduNoticeForm.idx.eduSeq);
    		}
       	});  
    }
});
function fn_delFile(fileSeq){
	$.ajax({
		url:'${utcp.ctxPath}/user/edu/bbs/deleteFile.ajax',
		data:{fileSeq:fileSeq},
		success:function(r){
			if(r.resultCode==1){
				location.reload();
				return;
			}
			alert('오류가 발생하였습니다.');
		}
	});
}

function fn_RegBbs() {
	if ($("#title").val() == "") {
		alert('제목은 필수 입력 입니다.');
		return;
	}

	var title = document.eduNoticeForm.title.value;
	var boardType = document.eduNoticeForm.boardType.value;
	var eduSeq = $('#edu_seq').val();
	var scrtyYn = document.eduNoticeForm.scrtyYn.value;
	var boardIdx = $('#boardIdx').val();
	//var content = document.eduNoticeForm.content.value;
	if (CKEDITOR.instances.ckeditCn.getData() == "") {
		alert('내용은 필수입력입니다.');
		return false;
	} else {
		var content = CKEDITOR.instances.ckeditCn.getData();
	}
	
	$.ajax({
		url:'${utcp.ctxPath}/user/mypage/popup/boardWriteProc.json',
		type : "post",
		dataType : "json",
		data :  {
			title : title,
			content : content,
			boardType : boardType,
			eduSeq : eduSeq,
			boardIdx : boardIdx,
		},
		success : function(r){
			if(r.result == 1){
				$('#boardIdx').val(r.data.idx);
				if(boardDropzone.getQueuedFiles().length > 0){
					boardDropzone.processQueue();//excute upload!!
	            }else{
					fn_boardView();
	            }
				alert('저장되었습니다.');
				return;
			}
			alert(r.msg);
			return;
		}
	});
}
function fn_boardView(){
	//$('#tabAction').val('boardView');
	$('#tabAction').val('boardList');//목록으로
	$('#myEduForm').submit();
}
function fn_cancelBbs(){
	if($('#boardIdx').val()!='0'){
		$('#tabAction').val('boardView');
	}else{
		$('#tabAction').val('boardList');
	}
		$('#myEduForm').submit();
}

</script>


<!--// write //-->

