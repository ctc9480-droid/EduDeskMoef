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
		<form id="form-movRgs">
		<input type="hidden" name="duration" value="${info.duration }" />
		<table width="100%" class="tb02 tc">
			<tbody>
				<tr>
					<th class="tdbg3 tc"><label for="title">제목</label></th>
					<td class="tl" colspan="3"><input value="${info.title }" type="text" name="title" id="title" class="ip2 mgr10" maxlength="400" placeholder="제목을 입력하세요." /></td>
				</tr>
				<tr style="display:none;">
					<th class="tdbg3 tc">강사명</th>
					<td class="tl" colspan="3"><input value="." type="hidden" name="instrctrNm" id="instrctrNm" class="ip9 mgr10" maxlength="400" placeholder="강사명" /></td>
				</tr>
				<tr>
					<th class="tdbg3 tc">동영상 첨부</th>
					<td class="tl" colspan="3">
						<div class="write_div">
							<label for="file-1"><span class="sound_only">파일</span></label>
							<c:if test="${empty info }">
								<input type="file" name="file_1" id="file-1" title="파일첨부" class="frm_file ip2" onchange="checkFileDuration();">
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
					<td class="tl" colspan="3"><textarea name="content" id="i-content" placeholder="내용" class="w100 h300">${info.content }</textarea> <input type="hidden" id="cn" name="cn" /></td>
				</tr>

			</tbody>
		</table>
		<div class="fl tc">
			<button type="button" class="btn02 btn_grayl mgb20" onclick="location.href='movList.do';">목록</button>
		</div>
		<div class="tc">
			<button type="button" class="btn01 btn_green mgb20" onclick="fn_upload(); return false;">등록하기</button>
		</div>
		</form>
	</div>
</section>

<form id="form-reg" method="post" enctype="multipart/form-data">
	<input type="hidden" name="idx" value="${info.idx }" /> <input type="hidden" id="duration" name="duration" value="${info.duration }" />
</form>

<script>
	//<![CDATA[
	function fn_upload() {
		if($('#title').val() == ''){
			alert('제목을 입력하세요');
			$('#title').focus();
			return;
		}
		
		//isloading.start();
		$("#area-bar p").text('파일이 준비 중 입니다.');
		//var timerId = setInterval(getProgres, 1000);

		var formData = new FormData($('#form-reg')[0]);
		formData.append('title', $('#title').val());
		formData.append('content', $('#i-content').val());
		formData.append('instrctrNm', $('#instrctrNm').val());
		if ($('#file-1')[0]) {
			formData.append('file_1', $('#file-1')[0].files[0]);
		}
		
		
		
		$.ajax({
			contentType : false,
			processData : false,
			type : 'post',
			url : '${utcp.ctxPath}/admin/ajax/uploadMov.ajax',
			data : formData,
			success : function(r) {
				//isloading.stop();
				//clearInterval(timerId);
				if (r.result == 0) {
					alert(r.msg);
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
	function getProgres() {
		var ele = document.getElementById('progressing');
		var width = 0;
		$.ajax({
			url : '${utcp.ctxPath}/admin/ajax/nosProgress.json',
			success : function(r) {
				if (r.data != null) {
					//$('#area-filesize').text(convByte(r.data.fileSize));
					//$('#area-allbyte').text(convByte(r.data.allByte));
					width=(r.data.allByte/r.data.fileSize*100).toFixed(1);
					$("#area-bar").css('width',width+'%');
				}
			}
		});
	}
	function convByte(bytes) {
		var sizes = [ 'Bytes', 'KB', 'MB', 'GB', 'TB' ];
		if (bytes == 0)
			return '0 Byte';
		var i = parseInt(Math.floor(Math.log(bytes) / Math.log(1024)));
		return (bytes / Math.pow(1024, i)).toFixed(1) + ' ' + sizes[i];
	};
	//]]>
</script>

<script>
	var videoMaxTime = "59:00"; //minutes:seconds   //video
	var audioMaxTime = "05:00"; //minutes:seconds   //audio
	var uploadMax = (1024 * 1024 * 1200*2); //bytes  
	//for seconds to time
	function secondsToTime(in_seconds) {
		var time = '';
		in_seconds = parseFloat(in_seconds.toFixed(2));

		var hours = Math.floor(in_seconds / 3600);
		var minutes = Math.floor((in_seconds - (hours * 3600)) / 60);
		var seconds = in_seconds - (hours * 3600) - (minutes * 60);
		//seconds = Math.floor( seconds );
		seconds = seconds.toFixed(0);
		if (hours < 10) {
			hours = "0" + hours;
		}
		if (minutes < 10) {
			minutes = "0" + minutes;
		}
		if (seconds < 10) {
			seconds = "0" + seconds;
		}
		var time = minutes + ':' + seconds;

		return time;

	}

	function checkFileDuration() {
		var file = document.querySelector('input[type=file]').files[0];
		var reader = new FileReader();
		var fileSize = file.size;

		if (fileSize > uploadMax) {
			alert('2GB 이하만 업로드 가능합니다.');
			$('#file-1').val("");
		} else {
			reader.onload = function(e) {
				if (file.type != "video/mp4") {
					alert('mp4만 업로드 가능합니다.');
				}
			};

			/*
			isloading.start();
			reader.onload = function(e) {

				if (file.type == "video/mp4" || file.type == "video/ogg"
						|| file.type == "video/webm") {
					var videoElement = document.createElement('video');
					videoElement.src = e.target.result;
					var timer = setInterval(function() {
						if (videoElement.readyState === 4) {
							getTime = secondsToTime(videoElement.duration);
							if (getTime > videoMaxTime) {
								alert(videoMaxTime+' video only');
								$('#file-1').val("");
							}
							$('#duration').val(getTime);
							isloading.stop();
							clearInterval(timer);
						}
					}, 500)
				} else if (file.type == "audio/mpeg"
						|| file.type == "audio/wav" || file.type == "audio/ogg") {

					var audioElement = document.createElement('audio');
					audioElement.src = e.target.result;
					var timer = setInterval(function() {
						if (audioElement.readyState === 4) {
							getTime = secondsToTime(audioElement.duration);
							if (getTime > audioMaxTime) {
								alert('1 minutes audio only')
							}
							isloading.stop();
							clearInterval(timer);
						}
					}, 500)
				} else {
					var timer = setInterval(function() {
						if (file) {
							alert('invaild File')
							$('#file-1').val("");
							isloading.stop();
							clearInterval(timer);
						}
					}, 500)
				}
			};
			 */
			if (file) {
				reader.readAsDataURL(file);
			} else {
				alert('nofile');
			}
		}
	}
</script>
