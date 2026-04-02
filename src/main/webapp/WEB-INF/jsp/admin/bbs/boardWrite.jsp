<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<script src="${utcp.ctxPath}/resources/ckeditor4_full/ckeditor.js"></script>
<%-- 
<script src="${utcp.ctxPath}/resources/plugins/ckeditor5-build-classic/ckeditor.js"></script>
 --%>
<script>
	$(function() {
		<%-- 교육자료실 아닌경우 에디터 적용--%>
		<c:if test="${vo.boardType ne 'eduRecs'}">
		//에디터에서 모든 태그 허용,211110
		/* 
		 */
		CKEDITOR.config.allowedContent = true;
		CKEDITOR.replace('ckeditCn', {
			filebrowserUploadUrl : '${utcp.ctxPath}/editot/popupUpload.json?gubun=board&prefixStr=${vo.boardType}_${vo.idx}',
			height : 400,
		});
		<%--
		var editor;
		ClassicEditor
	    .create( document.querySelector( '#ckeditCn' ),{
	    	toolbar: {
	    		items : ['bold', 'italic', 'link', 'imageUpload', 'insertTable', 'undo','fontSize','fontFamily','highlight', 'fontColor', 'fontBackgroundColor', 'code'], 
	    		shouldNotGroupWhenFull: true
	    	},
	    	simpleUpload: {
	            // The URL that the images are uploaded to.
	            uploadUrl: '${utcp.ctxPath}/editot/popupUpload.json?gubun=board&prefixStr=${vo.boardType}_${vo.idx}',

	        },
			
	    } )
	    .then( function(newEditor) {
	        editor = newEditor;
	        
	    } )
	    .catch( function(error){
	        console.error( error );
	    } );
		--%>
		</c:if>
		$('select[name=fileTypeGb]').val('${boardMap.fileTypeGb}');
	});

	function fn_RegBbs() {
		if ($("#title").val() == "") {
			$("#messageStr").html("제목은 필수입력입니다.");
			location.href = "#message";
			return;
		}
<%-- 교육자료실 인경우 에디터 미적용--%>
	<c:if test="${vo.boardType eq 'eduRecs'}">
		if ($('#ckeditCn').val() == '') {
			$("#messageStr").html("내용은 필수입력입니다.");
			location.href = "#message";
			return false;
		}
		$('#content').val($('#ckeditCn').val());
	</c:if>
<%-- 교육자료실 아닌경우 에디터 적용 --%>
	<c:if test="${vo.boardType ne 'eduRecs'}">
		if (CKEDITOR.instances.ckeditCn.getData() == "") {
			$("#messageStr").html("내용은 필수입력입니다.");
			location.href = "#message";
			return false;
		} else {
			$('#content').val(CKEDITOR.instances.ckeditCn.getData());
		}
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
			url:'boardWriteProc.json',
			data:formData,
			method:'post',
			success:function(r){
				if(r.resultCode == 1){
					fn_upFile(r.idx);
					return;
				}else{
					if(r.resultCode == 6){
						alert('본인이 작성한 글이 아닙니다.');return;
					}
				}
			}
		});
	}
	function fn_upFile(idx){
		var formData = new FormData();
		formData.append("idx", idx);
		formData.append("boardType", '${vo.boardType}');
		<c:if test="${boardMst.useThumb == 1}">
		formData.append("thumbFile", $("#thumbFile")[0].files[0]);
		</c:if>
		$.ajax({
			contentType : false,
			processData : false,
			url:'fileUpload.json',
			data:formData,
			method:'post',
			success:function(r){
				if(r.resultCode==0){
					alert('시스템 오류');
					return false;
				}
				console.log(r);
				$('#idx').val(r.idx);
				if(myDropzone.getQueuedFiles().length > 0){
					homeLoader.show();
                    myDropzone.processQueue();//excute upload!!
                }else{
                	location.href='${vo.boardType }View.do?idx='+$('#idx').val();
                }
			}
		});
	}
	function fn_delThumb(idx){
		$.ajax({
			url:'deleteThumb.json',
			data:{idx:idx},
			success:function(r){
				if(r.resultCode==1){
					location.reload();
				}
			}
		});
	}
	function fn_delFile(fileSeq){
		$.ajax({
			url:'deleteFile.json',
			data:{fileSeq:fileSeq},
			success:function(r){
				if(r.resultCode==1){
					location.reload();
				}
			}
		});
	}
</script>
<section class="pd025 pd200 po_re">
	<div class="po_re br5 bs_box">
		<form name="reg_form" id="reg-form" method="post">
			<input type="hidden" id="boardType" name="boardType" value="${vo.boardType }" /> 
			<input type="hidden" name="pIdx" value="${vo.pIdx }" /> 
			<input type="hidden" id="idx" name="idx" value="${vo.idx }" /> 
			<input type="hidden" name="attachDelSeqs" id="attachDelSeqs" />

			<table width="100%" class="tb02 tc">
				<caption class="sound_only">게시판 등록 테이블</caption>
				<tbody>
					<%-- 게시여부 사용하려면 주석 해제
					--%>
					<tr>
						<th class="tdbg3 tc"><label for="mn_id1">상단고정</label></th>
						<td class="tl">
						<select name="noticeYn">
							<option value="Y" ${boardMap.noticeYn eq 'Y'?'selected':'' }>네</option>
							<option value="N" ${empty boardMap or boardMap.noticeYn eq 'N'?'selected':'' }>아니오</option>
						</select>
						</td>
					</tr>
					<tr>
						<th class="tdbg3 tc"><label for="mn_id1">게시여부</label></th>
						<td class="tl">
								<select name="status">
								<c:forEach items="${vo.statusArr }" var="nm" varStatus="status">
									<c:if test="${status.index > 0 }">
									<option value="${status.index }" ${status.index==boardMap.status?'selected':'' }> ${nm }
									</c:if>
								</c:forEach>
								</select>
						</td>
					</tr>
					<tr>
						<th class="tdbg3 tc"><label for="wr_name">제목</label></th>
						<td class="tl"><input type="text" id="title" name="title" value="${boardMap.title }" maxlength="50" class="ip4" style="${boardMap.status}" placeholder="제목" /></td>
					</tr>
					<c:if test="${not empty boardMst.addCateArr }">
					<tr>
						<th class="tdbg3 tc"><label for="cate">카테고리</label></th>
						<td class="tl">
						<select name="cate" id="cate">
								<c:forEach items="${boardMst.addCateArr}" var="o" varStatus="s">
									<option value="${o}">${o }</option>
								</c:forEach>
						</select>
						</td>
					</tr>
					</c:if>
					<tr>
						<th class="tdbg3 tc"><label for="mn_id1">내용</label></th>
						<td class="tl"><textarea id="ckeditCn" name="ckeditCn" placeholder="내용" class="w100 h500" style="height: 100%">${boardMap.content }</textarea> <input type="hidden" id="content" name="content" /></td>
					</tr>
					<c:if test="${boardMst.useThumb == 1}">
					<tr>
						<th class="tdbg3 tc">
						썸네일
						</th>
						<td class="tl">
							<input type="file" id="thumbFile" />
							<c:if test="${not empty boardMap.thumbFile }">
							<img src="${utcp.getCdnUrl('upload/board/') }${boardMap.thumbFile}" height="50"/>
							<button type="button" onclick="fn_delThumb('${boardMap.idx}')" class="btn04 btn_black"> 삭제</button>
							</c:if>
							(이미지사이즈 : 340x220)
						</td>
					</tr>
					</c:if>
					<c:if test="${boardMst.useFileTypeGb == 1}">
					<tr>
						<th class="tdbg3 tc">
						첨부파일구분
						</th>
						<td class="tl">
							<select name="fileTypeGb">
								<option value="img">이미지</option>
								<option value="doc">문서</option>
								<option value="pdf">pdf</option>
								<option value="ppt">ppt</option>
								<option value="vid">동영상</option>
							</select>
						</td>
					</tr>
					</c:if>
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
								<li>${o.fileOrg }, <a href="#none" onclick="fn_delFile('${o.fileSeq}')">[삭제]</a></li>
								</c:forEach>
							</ul>
							</div>
						</td>
					</tr>
				</tbody>
			</table>
		</form>

		<div class="fl tc">
			<button onclick="location.href='${vo.boardType }List.do'" class="btn02 btn_grayl">목록</button>
		</div>

		<div class="fr tc">
			<button type="button" class="btn01 btn_greenl" onclick="fn_RegBbs();">저장</button>
		</div>
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

<!-- dropzone -->
<script>
	var totalUploadSize=0;
	var fileName;
	Dropzone.autoDiscover = false;
	myDropzone = new Dropzone("#area-dropzone", {
		url : "dropzoneUpload.json",
		paramName: "file",
        maxFilesize: 50, // 업로드 파일수
        maxFiles: 5,  // 최대업로드용량 : 50MB
        acceptedFiles: '.jpeg,.jpg,.png,.gif,.hwp,.doc,.pdf,.xls,.xlsx,.zip,.JPEG,.JPG,.PNG,.GIF,.HWP,.DOC,.PDF,.XLS,.XLSX,.ZIP', // 이미지 파일 포맷만 허용
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
<c:if test="${not empty boardMap }">
<script>
$('#cate').val('${boardMap.cate}');
</script>
</c:if>
