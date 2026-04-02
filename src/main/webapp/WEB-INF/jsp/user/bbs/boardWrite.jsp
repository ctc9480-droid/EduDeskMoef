<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<script src="${utcp.ctxPath}/resources/ckeditor4_full/ckeditor.js"></script>
<script>
	$(function() {
		<c:if test="${boardMst.edtrYn == 1}">
		CKEDITOR.replace('ckeditCn', {
			filebrowserUploadUrl : '${utcp.ctxPath}/editot/popupUpload.do',
			height : 400
		});
		</c:if>
	});

	//파일첨부
	

	var form_node = '';
	function fn_RegBbs() {
		if ($("#form-title").val() == "") {
			alert("제목은 필수입력입니다."); return;
		}
		<c:if test="${boardMst.edtrYn == 1}">
		if (CKEDITOR.instances.ckeditCn.getData() == "") {
			alert("내용은 필수입력입니다.");
			return false;
		} 
		$('#bbs-content').val(CKEDITOR.instances.ckeditCn.getData());
		</c:if>
		<c:if test="${boardMst.edtrYn != 1}">
		if ($('#ckeditCn').val() == '') {
			alert("내용은 필수입력입니다.");
			return false;
		}
		$('#bbs-content').val($('#ckeditCn').val());
		</c:if>
		
		<c:if test="${boardMst.useThumb == 1}">
		var result = UTIL.checkFileType($('#thumbFile'),['jpg','png','jpeg']);
		if(result.rst==0){
			alert(result.msg);
			return;
		}
		</c:if>
		
		var formData = $('#reg-form').serialize();
		$.ajax({
			url:'boardWriteProc.ajax',
			data:formData,
			method:'post',
			success:function(r){
				if(r.resultCode == 1){
					$('#idx').val(r.idx);
					<c:if test="${boardMst.fileYn == 1 }">
					if(myDropzone.getQueuedFiles().length > 0){
						homeLoader.show();
	                    myDropzone.processQueue();//excute upload!!
	                }else{
	                	location.href='${vo.boardType }View.do?idx='+$('#idx').val();
	                }
					</c:if>
					<c:if test="${boardMst.fileYn != 1 }">
					 <c:choose>
						<c:when test="${boardMst.boardType eq 'hope'}">
						location.href='${vo.boardType }List.do';
						</c:when>
						<c:otherwise>
		                location.href='${vo.boardType }View.do?idx='+$('#idx').val();
						</c:otherwise>
						</c:choose>
					</c:if>
					return;
				}else{
					if(r.resultCode == 6){
						alert('본인이 작성한 글이 아닙니다.');return;
					}
				}
			}
		});
	}
	
	function fn_delFile(fileSeq){
		if(!confirm('삭제하시겠습니까?')){return;}
		$.ajax({
			url:'deleteFile.ajax',
			data:{fileSeq:fileSeq},
			success:function(r){
				if(r.resultCode==1){
					location.reload();
				}
			}
		});
	}
</script>



<div class="listWrap" style="padding-top: 0px">
	<form name="reg_form" id="reg-form" method="post" enctype="multipart/form-data">
		<input type="hidden" name="boardType" value="${vo.boardType }" />
		<input type="hidden" name="pIdx" value="${vo.pIdx }" />
		<input type="hidden" name="idx" id="idx" value="${vo.idx }" />
		<input type="hidden" name="attachDelSeqs" id="attachDelSeqs" />
		<table class="tb04">
			<tr>
				<th class="tdbg3 tc"><label for="wr_name">제목</label></th>
				<td class="tl"><input type="text" id="form-title" name="title" value="${boardMap.title }${reTitle}" maxlength="20" class="ip4" style="${boardMap.status}" /></td>
			</tr>
			<tr>
				<th class="tdbg3 tc"><label for="mn_id1">내용</label></th>
				<td class="tl"><textarea id="ckeditCn" name="ckeditCn" placeholder="내용">${boardMap.content }</textarea> <input type="hidden" id="bbs-content" name="content" /></td>
			</tr>
			<c:if test="${not empty boardMst.tmp01 }">
			<tr>
				<th class="tdbg3 tc"><label for="mn_id1">${boardMst.tmp01 }</label></th>
				<td class="tl"><input type="text" name="tmp01" value="${boardMap.tmp01 }" maxlength="200" class="ip4" />
				</td>
			</tr>
			</c:if>
			<c:if test="${boardMst.fileYn == 1 }">
			<tr>
				<th class="tdbg3 tc"><label for="fileBox_01">첨부파일</label></th>
				<td class="tl" colspan="">
					<!-- dropzone html -->
					<div class="dropzone" id="area-dropzone">
						<div class="fallback">
							<input name="file" type="file" multiple />
						</div>
						<input name="image_url" id="image_url" type="hidden" />
					</div>
					<div id="area-filelist">
						<ul>
							<c:forEach items="${boardAttachList }" var="o">
								<li>${o.fileOrg },
									<a href="#none" onclick="fn_delFile('${o.fileSeq}')">[삭제]</a>
								</li>
							</c:forEach>
						</ul>
					</div>
				</td>
			</tr>
			</c:if>
		</table>
	</form>
	<div class="tb_btn">
		<ul>
			<a href="${vo.boardType }List.do"><li class="left">취소</li></a>
			<a href="#none" onclick="fn_RegBbs();"><li class="right">${vo.idx eq ''? '글쓰기':'수정하기'}</li></a>
		</ul>
	</div>
</div>

<!-- popup1 -->
<div class="remodal messagePop messagePop2" data-remodal-id="bbs-message" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc" data-remodal-options="closeOnOutsideClick: false">
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC">알림</p>
		</div>
		<div class="modal-body">
			<p class="messageTxt" id="messageStr"></p>
		</div>
		<div class="modal-footer">
			<div class="tc">
				<button onclick="form_node.focus();" data-remodal-action="cancel" class="remodal-confirm btn02 btn_orange">확인</button>
			</div>
		</div>
	</div>
</div>
<!-- -->



<!-- dropzone -->
<script src="${utcp.ctxPath}/resources/plugins/dropzone/dropzone.js"></script>
<link rel="stylesheet" href="${utcp.ctxPath}/resources/plugins/dropzone/dropzone.css">
<script>
	var totalUploadSize=0;
	var fileName;
	Dropzone.autoDiscover = false;
	myDropzone = new Dropzone("#area-dropzone", {
		url : "dropzoneUpload.ajax",
		paramName: "file",
        maxFilesize: 5, // 최대업로드용량 : 5MB
        maxFiles: 1,  // 업로드 파일수
        acceptedFiles: '.jpeg,.jpg,.png,.gif,.hwp,.doc,.pdf,.xls,.zip,.JPEG,.JPG,.PNG,.GIF,.HWP,.DOC,.PDF,.XLS,.ZIP', // 이미지 파일 포맷만 허용
        //acceptedFiles: null,	//  모든 파일 업로드 가능
        autoProcessQueue: false, // 자동으로 보내기. true : 파일 업로드 되자마자 서버로 요청, false : 서버에는 올라가지 않은 상태
        uploadMultiple: true, // 다중업로드 기능
        addRemoveLinks:true, // 업로드 후 파일 삭제버튼 표시 여부
        parallelUploads:100, // 동시파일업로드 수(이걸 지정한 수 만큼 여러파일을 한번에 넘긴다.)
        dictDefaultMessage:'+ 마우스로 파일을 끌고 오거나 여기를 클릭하세요<br><br><font style="color:red">총 5MB 이하만 전송 가능합니다</font>',
        init: function () {
        	
        	this.on("success", function(file, returnedData, myEvent) {
        		console.log('test');
              	location.href='${vo.boardType }View.do?idx='+$('#idx').val();
           	}); 
        	this.on('sending', function(file, xhr, formData) {
        		if(formData.get('idx')==null){
        			
        			formData.append('idx', $('#idx').val());
        			formData.append('boardType', $('#boardType').val());
        		}
           	});  
        	this.on("addedfile", function(file) { 
        		addCheckFile(myDropzone, file);
        	});
        	this.on("removefile", function(file) { 
        		console.log(12);
        	});
        }
	});
</script>