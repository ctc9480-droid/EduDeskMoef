<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<script src="${utcp.ctxPath}/resources/admin/js/wf_loading.js" type="text/javascript"></script>
<link href="${utcp.ctxPath}/resources/admin/css/wf_loading.css" rel="stylesheet" type="text/css" />

<style>
<!--
.graph {
    width: 500px; /* width and height are arbitrary, just make sure the #bar styles are changed accordingly */
    height: 30px;
    border: 1px solid #888; 
    background: -webkit-linear-gradient(top, rgba(168,168,168,1) 0%,rgba(204,204,204,1) 23%);
    background: -ms-linear-gradient(top, rgba(168,168,168,1) 0%,rgba(204,204,204,1) 23%);
    position: relative;
}
#area-bar {
    height: 30px; /* Not 30px because the 1px top-border brings it up to 30px to match #graph */
    background: -webkit-linear-gradient(top, #dcf4ff 0%,#7496c1 100%); 
    background: -ms-linear-gradient(top, #dcf4ff 0%,#7496c1 100%); 
}
#area-bar p { position: absolute; text-align: center; width: 100%; margin: 0; line-height: 30px; }
-->
</style>


<section class="pd025 pd200 po_re">
	<div class="po_re br5 bs_box cf">
		<input type="hidden" name="duration" value="${info.duration }" />
		<table width="100%" class="tb02 tc">
			<tbody>
				<tr>
					<th class="tdbg3 tc"><label for="eduNm">제목</label></th>
					<td class="tl" colspan="3"><input value="${info.title }" type="text" name="title" id="title" class="ip2 mgr10" maxlength="400" placeholder="제목을 입력하세요." /></td>
				</tr>
				<tr>
					<th class="tdbg3 tc">강사명</th>
					<td class="tl" colspan="3"><input value="${info.instrctrNm }" type="text" name="instrctrNm" id="instrctrNm" class="ip9 mgr10" maxlength="400" placeholder="강사명" /></td>
				</tr>
				<tr>
					<th class="tdbg3 tc">동영상 첨부</th>
					<td class="tl" colspan="3">
						<div class="write_div">
							<label for="movFile"><span class="sound_only">파일</span></label>
							<c:if test="${empty info }">
								<input type="file" multiple="multiple" name="file_1" id="file-1" title="파일첨부" class="frm_file ip2" onchange="checkFileDuration();">
								<br />※ie에서는 제대로 동작하지 않을 수 있습니다. chrome이나 edge를 사용하시기 바랍니다.
								<br />※MP4동영상, 1GB이하 가능
								
							</c:if>
							<c:if test="${not empty info }">
							${info.fileOrg }
							</c:if>
							<br />
							<div id="progress" class="graph"><div id="area-bar" style="width:0%"><p></p></div></div>
						</div>
					</td>
				</tr>

				<tr>
					<th class="tdbg3 tc">상세 내용</th>
					<td class="tl" colspan="3"><textarea name="content" id="content" placeholder="내용" class="w100 h300">${info.content }</textarea> <input type="hidden" id="cn" name="cn" /></td>
				</tr>

			</tbody>
		</table>
		<div class="fl tc">
			<button class="btn02 btn_grayl mgb20" onclick="location.href='movList.do';">목록</button>
		</div>
		<div class="tc">
			<button class="btn01 btn_green mgb20" onclick="fn_upload(); return false;">등록하기</button>
		</div>
	</div>
</section>

<form id="form-reg" method="post" enctype="multipart/form-data">
	<input type="hidden" name="idx" value="${info.idx }" /> <input type="hidden" id="duration" name="duration" value="${info.duration }" />
</form>

<script>
	//<![CDATA[
	function fn_upload() {
		//isloading.start();
		$("#area-bar p").text('파일이 준비 중 입니다.');

		var formData = new FormData($('#form-reg')[0]);
		formData.append('title', $('#title').val());
		formData.append('content', $('#content').val());
		formData.append('instrctrNm', $('#instrctrNm').val());
		if ($('#file-1')[0]) {
			formData.append('file_1', $('#file-1')[0].files[0]);
		}
		$.ajax({
			contentType : false,
			processData : false,
			type : 'post',
			url : '${utcp.ctxPath}/admin/ajax/uploadMov.json',
			data : formData,
			success : function(r) {
				//isloading.stop();
				if (r.result == 0) {
					vm_alert.msg = r.message;
					location.href = '#md-alert';
					return;
				}
				location.href = 'movList.do';
			},
			xhr : function() { //XMLHttpRequest 재정의 가능
				var xhr = $.ajaxSettings.xhr();
				xhr.upload.onprogress = function(e) { //progress 이벤트 리스너 추가
					var percent = e.loaded * 100 / e.total;
					$("#area-bar").css('width',percent+'%');
					if (percent == 100) {
						$("#area-bar p").text('파일을 업로드 하고 있습니다.');
					}
				};
				return xhr;
			},
		});
	}


	//]]>
</script>

