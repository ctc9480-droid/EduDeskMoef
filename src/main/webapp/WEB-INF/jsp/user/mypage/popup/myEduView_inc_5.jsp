<%@page import="java.util.HashMap"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="java.util.Map"%>
<%@page import="com.educare.edu.lectureTask.mapper.LectureTaskMapper"%>
<%@page import="com.educare.edu.member.service.SessionUserInfoHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<%
String eduSeq = request.getParameter("eduSeq");
ServletContext conext = session.getServletContext();
WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(conext);
LectureTaskMapper  lectureTaskMapper = (LectureTaskMapper)wac.getBean("LectureTaskMapper");
Map<String,String> fileParam = new HashMap<String,String>();
fileParam.put("eduSeq", eduSeq);
fileParam.put("userId", SessionUserInfoHelper.getUserId());
Map<String,String> fileMap = lectureTaskMapper.selectLectureTaskFileMap(fileParam);
pageContext.setAttribute("fileMap", fileMap);
%>

<script src="https://unpkg.com/dropzone@5/dist/min/dropzone.min.js"></script>
<link rel="stylesheet" href="https://unpkg.com/dropzone@5/dist/min/dropzone.min.css" type="text/css" />
<!-- dropzone html -->
<div class="dropzone" id="area-dropzone">
	<div class="fallback">
		<input name="file" type="file" multiple />
	</div>
	<input name="image_url" id="image_url" type="hidden" />
</div>
<div id="area-filelist">
<ul>
	<c:if test="${not empty fileMap and not empty fileMap.task_file_rename }">
	<li>
	<a href="${utcp.ctxPath}/user/mypage/popup/taskDownload.do?eduSeq=${param.eduSeq }" style="display:inline ;">${fileMap.task_file_org }</a> 
	<a href="#none" onclick="fn_delLectureTaskFile()" style="display:inline ;">[삭제]</a>
	</li>
	</c:if>
</ul>
</div>
<div class="w100 tc">
	<a href="javascript:updateReg();" class="btn02 btn_blue">업로드</a>
</div>
<!-- dropzone -->
<script>

	Dropzone.autoDiscover = false;
	myDropzone = new Dropzone("#area-dropzone", {
		url : "${utcp.ctxPath}/user/mypage/popup/taskDropzoneUpload.json",
		paramName: "file",
        maxFilesize: 5,
        maxFiles: 1,
        //acceptedFiles: "image/*,application/pdf,.xlsx",
        //acceptedFiles: null,	// dykim, 210701, 모든 파일 업로드 가능
        autoProcessQueue: false,
        uploadMultiple: true,
        addRemoveLinks:true,
        parallelUploads:1,
        dictDefaultMessage:'+ 마우스로 파일을 끌고 오거나 여기를 클릭하세요<br><br><font style="color:red">5MB 이하의 한개의 파일만 전송 가능합니다</font>',
        init: function () {
        	this.on("success", function(file, result, myEvent) {
        		alert("제출 완료 되었습니다.");
        		location.reload();
           	}); 
        	this.on('sending', function(file, xhr, formData) {
        		console.log(file);
        		formData.append('eduSeq', $('#edu_seq').val());
           	});  
        }
	});
	
	function updateReg() {
		if('${lctre.checkRcept}'=='4'){
			alert('교육이 종료되었습니다. 업로드 할 수 없습니다.');
			return;
		}
		if (myDropzone.getQueuedFiles().length > 0) {
			// 큐에 파일이 있을 경우, 큐를 업로드하고
			myDropzone.processQueue();
		} else {
			// 큐에 파일이 없을 경우 메시지를 표시
			alert("파일을 선택해주세요!");
		}
	}
	function fn_delLectureTaskFile() {
		if('${lctre.checkRcept}'=='4'){
			alert('교육이 종료되었습니다. 삭제 할 수 없습니다.');
			return;
		}
		var eduSeq = '${param.eduSeq}';
		$
				.ajax({
					url : '${utcp.ctxPath}/user/mypage/popup/deleteLectureTaskFile.ajax',
					data : {
						eduSeq : eduSeq
					},
					success : function(r) {
						if (r.resultCode == 1) {
							location.reload();
						}
					}
				});
	}
</script>