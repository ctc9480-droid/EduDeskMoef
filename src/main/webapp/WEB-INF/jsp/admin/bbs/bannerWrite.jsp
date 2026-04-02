<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<!-- pickadate.js -->
<link rel="stylesheet" href="${utcp.ctxPath}/resources/plugins/pickadate/default.css">
<link rel="stylesheet" href="${utcp.ctxPath}/resources/plugins/pickadate/default.date.css">
<script src="${utcp.ctxPath}/resources/plugins/pickadate/picker.js"></script>
<script src="${utcp.ctxPath}/resources/plugins/pickadate/picker.date.js"></script>
<script>
	//<![CDATA[
	$(function() {
		//$('.datepicker').datepicker();
		$("#start_dt").datepicker({
	        onSelect: function (selectedDate) {
	        	console.log(selectedDate);
	            var startDate = $(this).datepicker("getDate");
	        	console.log(startDate);
	            if (startDate) {
	                $("#end_dt").datepicker("option", "minDate", startDate);
	            }
	        }
	    });
	    $("#end_dt").datepicker({
	        onSelect: function (selectedDate) {
	            var endDate = $(this).datepicker("getDate");
	            if (endDate) {
	                $("#start_dt").datepicker("option", "maxDate", endDate);
	            }
	        }
	    });
		// pickadate //
		
		
		$('input[name=status][value=${not empty bannerMap?bannerMap.status:1}]').prop('checked',true);
		if($('#startDtime').val() != ''){
			$('#start_dt').val($('#startDtime').val().substr(0,4)+'-'+$('#startDtime').val().substr(4,2)+'-'+$('#startDtime').val().substr(6,2));
			$('#start_hh').val('${bannerMap.startDtime}'.substr(8,2));
			$('#start_mm').val('${bannerMap.startDtime}'.substr(10,2));
		}else{
			$('#start_hh').val('00');
			$('#start_hh').val('00');
		}
		if($('#endDtime').val() != ''){
			$('#end_dt').val($('#endDtime').val().substr(0,4)+'-'+$('#endDtime').val().substr(4,2)+'-'+$('#endDtime').val().substr(6,2));
			$('#end_hh').val('${bannerMap.endDtime}'.substr(8,2));
			$('#end_mm').val('${bannerMap.endDtime}'.substr(10,2));
		}else{
			$('#end_hh').val('23');
			$('#end_mm').val('59');
		}
	});
	//]]>
	
	function fn_reg() {
		var $nodeObj = $('input[name=title]');
		if($nodeObj.val()==''){ $nodeObj.focus(); alert($nodeObj.attr('title')+'은(는) 필수 입력입니다.'); return; }
		
		//url체크
		var b_rul =  $("#url").val();
	    var Url = /(http|https):\/\/((\w+)[.])+(asia|biz|cc|cn|com|de|eu|in|info|jobs|jp|kr|mobi|mx|name|net|nz|org|travel|tv|tw|uk|us)(\/(\w*))*$/i;
	    var urlTest = Url.test(b_rul); 
	    
	    if(!urlTest){
	        //alert("URL형식이 잘못되었습니다.");
	        //$("#url").focus();
	        //return false;
	    }
		
		var startDtime = $('#start_dt').val().replace(/-/gi,'')+$('#start_hh').val()+$('#start_mm').val()+'00';
		var endDtime = $('#end_dt').val().replace(/-/gi,'')+$('#end_hh').val()+$('#end_mm').val()+'00';
		if(!UTIL.validateDateTime(startDtime,'YYYYMMDDHHmm')){
			alert('시작일을 확인하세요');
			return;
		}
		if(!UTIL.validateDateTime(endDtime,'YYYYMMDDHHmm')){
			alert('종료일을 확인하세요');
			return;
		}
		
		//파일첨부여부 체크
		var nodeFile = $("#file_img")[0].files[0];
		if('${bannerMap.fileImgNm}' == ''){
			if(typeof nodeFile == 'undefined' ){
				alert('배너파일을 선택하셔야 합니다.');
				return;
			}
		}
		
		$('#startDtime').val(startDtime);		
		$('#endDtime').val(endDtime);		
		
		var formData1 = $('#form_reg').serialize();
		$.ajax({
			url : "bannerWriteProc.json",
			type : "post",
			data : formData1,
			cache : false,
			async : true,
			success : function(r) {
				if(r.result==1){
					
					if(typeof nodeFile == 'undefined'){
						location.href='bannerList.do';
						return;
					}
					var formData2 = new FormData();
					formData2.append("file_img", $("#file_img")[0].files[0]);
					formData2.append("idx", r.idx);
					
					$.ajax({
						contentType : false,
						processData : false,
						url : "bannerImgProc.json",
						type : "post",
						data : formData2,
						cache : false,
						async : true,
						success : function(r2) {
							if(r2.result ==1){
								location.href='bannerList.do';
							}
						}
					});
				}
			}
		});
		
	}
	function fn_del(){
		if(!confirm('삭제 하시겠습니까?')){
			return;
		}
		location.href='bannerDeleteProc.do?idx=${banner.idx}';
	}
	 function validateIntegerInput(input) {
	      // 정수만 허용하는 정규식 (음수도 허용)
	      var validInteger = /^\d*$/;

	      // 입력값이 정수에 해당하지 않으면 입력값을 제거
	      if (!validInteger.test(input.value)) {
	        input.value = input.value.slice(0, -1); // 마지막 문자를 제거
	      }
	    }
</script>
<section class="pd025 pd200 po_re">
	<div class="po_re br5 bs_box">
		<form name="form_reg" id="form_reg" method="post" enctype="multipart/form-data">
			<input type="hidden" name="idx" value="${vo.idx }" />
			<input type="hidden" id="startDtime" name="startDtime" value="${bannerMap.startDtime }" />
			<input type="hidden" id="endDtime" name="endDtime" value="${bannerMap.endDtime }" />
			
			<table width="100%" class="tb02 tc">
				<caption class="sound_only">팝업창테이블</caption>
				<tbody>
					<tr>
						<th class="tdbg3 tc">게시여부</th>
						<td class="tl" colspan=""><input type="radio" name="status" value="1" id="pop_on" />
						<label for="pop_on" class="pdl5 mgr10 cp">게시</label> 
						<input type="radio" name="status" value="2"  id="pop_off"/>
						<label for="pop_off" class="pdl5 cp">미게시</label></td>
					<tr>
						<th class="tdbg3 tc"><label for="pop_title">제목</label></th>
						<td class="tl" colspan=""><input type="text" name="title" value="${bannerMap.title }" class="ip4" maxlength="100" title="제목"/></td>
					</tr>
					<tr>
						<th class="tdbg3 tc"><label for="link">링크</label></th>
						<td class="tl" colspan=""><input type="text" id="url" name="url" value="${bannerMap.url }" class="ip4" maxlength="100" /></td>
					</tr>
					<tr>
						<th class="tdbg3 tc"><label for="link">순서</label></th>
						<td class="tl" colspan=""><input type="text" id="sort" name="sort" oninput="validateIntegerInput(this)" value="${not empty bannerMap.sort?bannerMap.sort:1 }" class="ip9" maxlength="2" /></td>
					</tr>
					<tr>
						<th class="tdbg3 tc">게시기간</th>
						<td class="tl" colspan="3">
						<input readonly type="text" id="start_dt" class="datepicker input_calendar ip6 tc" maxlength="10"/> 
						<select id="start_hh" class="vb">
						<c:forEach begin="0" end="23" varStatus="s" var="o">
						<option value="${o<10?('0'+=o):o}">${o<10?('0'+=o):o}</option>
						</c:forEach>
						</select> :
						<select id="start_mm" class="vb">
						<c:forEach begin="0" end="59" varStatus="s" var="o">
						<option value="${o<10?('0'+=o):o}">${o<10?('0'+=o):o}</option>
						</c:forEach>
						</select> 
						<span class="pd10">~</span> 
						<input readonly type="text" id="end_dt" class="datepicker input_calendar ip6 tc" maxlength="10"/> 
						<select id="end_hh" class="vb">
						<c:forEach begin="0" end="23" varStatus="s" var="o">
						<option value="${o<10?('0'+=o):o}">${o<10?('0'+=o):o}</option>
						</c:forEach>
						</select> :
						<select id="end_mm" class="vb">
						<c:forEach begin="0" end="59" varStatus="s" var="o">
						<option value="${o<10?('0'+=o):o}">${o<10?('0'+=o):o}</option>
						</c:forEach>
						</select> 
						</td>
					</tr>
					<tr>
						<th class="tdbg3 tc">내용</th>
						<td class="tl">
							<c:if test="${not empty bannerMap.fileImgNm }">
							${bannerMap.fileImgNm }
							</c:if>
							<input type="file" id="file_img" name="file_img" />
							<!-- 
							<span class="fc_orange">※ 배너 사이즈는 1920px * 450px로 등록하세요.</span>
							 -->
						</td>
					</tr>
				</tbody>
			</table>
			<div class="fl tc">
				<button type="button" onclick="location.href='${utcp.ctxPath}/admin/bbs/bannerList.do';" class="btn02 btn_grayl">목록</button>
			</div>

			<div class="fr tc">
			
				<button type="button" onclick="fn_reg();" class="btn01 btn_greenl">${vo.idx eq ''? '작성':'수정'}</button>
				<c:if test="${not empty bannerMap }">
				<button type="button" onclick="fn_del();" class="btn01 btn_greenl">삭제</button>
				</c:if>
			</div>
		</form>
	</div>
</section>