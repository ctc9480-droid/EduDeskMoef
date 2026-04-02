<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp" %>
<script>
	var instrctrFileType = ['GIF','PNG','JPG','JPEG', 'HWP', 'XLS', 'XLSX', 'PPT', 'PPTX', 'DOC', 'DOCX', 'PDF'];
	
	$(function(){
		$("#fileFrm").submit(function(e) {
		    e.preventDefault();    
		    var formData = new FormData(this);
	
		    $.ajax({
		        url: "/tempFile/upload.json",
		        type: 'POST',
		        data: formData,
		        cache: false,
		        contentType: false,
		        processData: false,
		        success: function(r) {
					if(r.isSuccess){
						var totalCnt = Number($("#totalCnt").val()) + 1;
						var totalSize = Math.floor((Number($("#totalSize").val()) + r.fileSize) / 1000);
						var fileSection = $("#fileSection").val();
						var funcCd = $("#funcCd").val();
						var tmpFileSeq = r.fileSeq;
						var fileRename = r.fileRename;
						var fileOrg = r.fileOrg;
						var fileType = r.fileType;
						var fileSize = r.fileSize;
						fn_uploadSuccess(tmpFileSeq, fileRename, fileOrg, fileType, fileSize, totalCnt, totalSize, fileSection, funcCd);
						fn_close();						
					}else{
						alert(r.message);
					}
				}
		    });
		});
	});
	

	function fn_uploadInit(totalCnt, totalSize, maxCnt, maxSize, fileSection, funcCd){
		location.href = "#uploadPopup";
		$("#totalCnt").val(totalCnt);
		$("#maxCnt").val(maxCnt);
		$("#totalSize").val(totalSize);
		$("#maxSize").val(maxSize);
		$("#fileSection").val(fileSection);
		$("#funcCd").val(funcCd);
	}
	
	function fn_fileUp(){
		var fileValue = $("#upFile").val().split("\\");
		var fileName = fileValue[fileValue.length-1];
		var ext = fileName.substring(fileName.lastIndexOf(".")+1).toUpperCase();
		var uploadAble = "";
		
		if($("#funcCd").val() == "instrctr" || $("#funcCd").val() == "board"){
			uploadAble = instrctrFileType;
			if($.inArray(ext, uploadAble) == -1){
				$("#upFile").val("");
				$("#fileNm").val("");	
				alert("업로드 가능한 파일이 아닙니다.");
			}else{
				$("#fileNm").val(fileName);	
			}
		}
	}
	
	function fn_close(){
		
		var divId = $("#fileSection").val();
		
		$("#totalCnt").val("");
		$("#maxCnt").val("");
		$("#totalSize").val("");
		$("#maxSize").val("");
		$("#fileSection").val("");
		$("#funcCd").val("");
		$("#upFile").val("");
		$("#fileNm").val("");	
		
		location.href = "#";
	}
	
	function fn_uploadExcute(){
		if($("#fileNm").val() == ""){
			alert("업로드 할 파일을 선택하세요.");
			return;
		}
		$("#fileFrm").submit();
	}
	
</script>

<div class="remodal" data-remodal-id="uploadPopup" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc">
	<button class="remodal-close" aria-label="Close" onclick="fn_close(); return false;"></button>
	<div class="modal-content">
		<div class="modal-header layerheader">
			<p class="tit alignC">파일 업로드</p>
		</div>
		<div class="modal-body">
			<div class="fileBox">
				<form id="fileFrm" method="post" enctype="multipart/form-data">
					<input type="hidden" id="totalCnt" name="totalCnt" />
					<input type="hidden" id="maxCnt" name="maxCnt" />
					<input type="hidden" id="totalSize" name="totalSize" />
					<input type="hidden" id="maxSize" name="maxSize" />
					<input type="hidden" id="fileSection" />
					<input type="hidden" id="funcCd" />
					<input type="text" id="fileNm" class="fileName" readonly/>
					<label for="upFile" class="btn_file btn_black">찾아보기</label>
					<input type="file" name="upFile" id="upFile" onchange="fn_fileUp(); return false;">
				</form>
			</div>
		</div>
		<div class="modal-footer">
			<div class="tr">
				<button class="remodal-confirm btn02 btn_green" onclick="fn_uploadExcute(); return false;">확인</button>
			</div>
		</div>
	</div>
</div>