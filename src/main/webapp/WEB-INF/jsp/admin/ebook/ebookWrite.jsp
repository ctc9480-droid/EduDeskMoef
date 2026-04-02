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
		$('.datepicker').pickadate(
				{
					monthsFull : [ '1월', '2월', '3월', '4월', '5월', '6월', '7월',
							'8월', '9월', '10월', '11월', '12월' ],
					monthsShort : [ '1', '2', '3', '4', '5', '6', '7', '8',
							'9', '10', '11', '12' ],
					weekdaysFull : [ '일요일', '월요일', '화요일', '수요일', '목요일', '금요일',
							'토요일' ],
					weekdaysShort : [ '일', '월', '화', '수', '목', '금', '토' ],
					format : 'yyyy-mm-dd',
					formatSubmit : 'yyyy-mm-dd',
					today : "오늘",
					clear : "지우기",
					close : '닫기',
					container : '.pd025',
					labelMonthNext : '다음달 넘어가기',
					labelMonthPrev : '이전달 넘어가기',
					labelMonthSelect : '월 선택',
					labelYearSelect : '년도 선택',
					selectYears : 200,
					selectMonths : true
				//min:true
				});
		// pickadate //
		
		function onSelectHandler(date, context) {
			/**
			 * @date is an array which be included dates(clicked date at first index)
			 * @context is an object which stored calendar interal data.
			 * @context.calendar is a root element reference.
			 * @context.calendar is a calendar element reference.
			 * @context.storage.activeDates is all toggled data, If you use toggle type calendar.
			 * @context.storage.events is all events associated to this date
			 */

			var $element = context.element;
			var $calendar = context.calendar;
			var $box = $element.siblings('.box').show();
			var text = '';

			if (date[0] !== null) {
				text += date[0].format('YYYY-MM-DD');
			}

			if (date[0] !== null && date[1] !== null) {
				text += ' ~ ';
			} else if (date[0] === null && date[1] == null) {
				text += '선택된 날짜가 없습니다.';
			}

			if (date[1] !== null) {
				text += date[1].format('YYYY-MM-DD');
			}

			$box.text(text);
		}

		// 캘린더 설정
		$('.calendar').pignoseCalendar({
			select : onSelectHandler,
			format : 'YYYY-MM-DD', // date format string 년월일로 포맷변경
			// disabledWeekdays: [1], // 매주월요일선택못함 SUN(0),MON(1),TUE(2),WED(3),THU(4),FRI(5),SAT(6)
			theme : 'blue', // 테마변경 light,blue/dark
			lang : 'ko', // 한국
		// minDate: moment().format("YYYY-MM-DD") //지난날짜 선택못함
		});
		
		$('input[name=status][value=${empty ebookMap.status?2:ebookMap.status}]').prop('checked',true);
		if($('#startDtime').val()!=''){
			$('#start_dt').val($('#startDtime').val().substr(0,4)+'-'+$('#startDtime').val().substr(4,2)+'-'+$('#startDtime').val().substr(6,2));
			$('#start_hh').val('${ebookMap.startDtime}'.substr(8,2));
			$('#start_mm').val('${ebookMap.startDtime}'.substr(10,2));
		}
		if($('#endDtime').val()!=''){
			$('#end_dt').val($('#endDtime').val().substr(0,4)+'-'+$('#endDtime').val().substr(4,2)+'-'+$('#endDtime').val().substr(6,2));
			$('#end_hh').val('${ebookMap.endDtime}'.substr(8,2));
			$('#end_mm').val('${ebookMap.endDtime}'.substr(10,2));
		}
		
		$('#file_thum').change(function(){
			var result = UTIL.checkFileType($(this),['jpg','png','jpeg']);
			if(result.rst==0){
				alert(result.msg);
			}
		});
		$('#file_pdf').change(function(){
			var result = UTIL.checkFileType($(this),['pdf']);
			if(result.rst==0){
				alert(result.msg);
			}
		});
	});
	//]]>
	
	function fn_reg() {
		//url체크
		var b_rul =  $("#url").val();
	    var Url = /(http|https):\/\/((\w+)[.])+(asia|biz|cc|cn|com|de|eu|in|info|jobs|jp|kr|mobi|mx|name|net|nz|org|travel|tv|tw|uk|us)(\/(\w*))*$/i;
	    var urlTest = Url.test(b_rul); 
	    
	    if(!urlTest){
	        //alert("URL형식이 잘못되었습니다.");
	        //$("#url").focus();
	        //return false;
	    }
		/* 
		var startDtime = $('#start_dt').val().replace(/-/gi,'')+$('#start_hh').val()+$('#start_mm').val()+'00';
		var endDtime = $('#end_dt').val().replace(/-/gi,'')+$('#end_hh').val()+$('#end_mm').val()+'00';
		$('#startDtime').val(startDtime);		
		$('#endDtime').val(endDtime);		
		 */
		var formData1 = $('#form_reg').serialize();
		$.ajax({
			url : "ebookWriteProc.json",
			type : "post",
			data : formData1,
			cache : false,
			async : true,
			success : function(r) {
				if(r.result==1){
					var a = $("#file_pdf")[0].files[0];
					var a2 = $("#file_thum")[0].files[0];
					if(typeof a=='undefined' && typeof a2=='undefined'){
						location.href='ebookList.do';
						return;
					}
					var formData2 = new FormData();
					formData2.append("file_pdf", $("#file_pdf")[0].files[0]);
					formData2.append("file_thum", $("#file_thum")[0].files[0]);
					formData2.append("idx", r.idx);
					
					$.ajax({
						contentType : false,
						processData : false,
						url : "ebookFileProc.json",
						type : "post",
						data : formData2,
						cache : false,
						async : true,
						success : function(r2) {
							if(r2.result ==1){
								location.href='ebookList.do';
							}
						}
					});
				}
			}
		});
		
	}
</script>
<section class="pd025 pd200 po_re">
	<div class="po_re br5 bs_box">
		<form name="form_reg" id="form_reg" method="post" enctype="multipart/form-data">
			<input type="hidden" name="idx" value="${vo.idx }" />
			<input type="hidden" id="startDtime" name="startDtime" value="${ebookMap.startDtime }" />
			<input type="hidden" id="endDtime" name="endDtime" value="${ebookMap.endDtime }" />
			
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
						<td class="tl" colspan=""><input type="text" name="title" value="${ebookMap.title }" class="ip4" maxlength="100" /></td>
					</tr>
					<tr style="display: none;">
						<th class="tdbg3 tc"><label for="link">링크</label></th>
						<td class="tl" colspan=""><input type="text" id="url" name="url" value="${ebookMap.url }" class="ip4" maxlength="100" /></td>
					</tr>
					<tr>
						<th class="tdbg3 tc"><label for="link">순서</label></th>
						<td class="tl" colspan=""><input type="number" id="sort" name="sort" value="${ebookMap.sort }" class="ip9" maxlength="2" /></td>
					</tr>
					<tr style="display: none;">
						<th class="tdbg3 tc">게시기간</th>
						<td class="tl" colspan="3">
						<input type="text" id="start_dt" class="datepicker input_calendar ip6 tc" /> 
						<select id="start_hh" class="vb">
						<c:forEach begin="1" end="23" varStatus="s" var="o">
						<option value="${o<10?('0'+=o):o}">${o<10?('0'+=o):o}</option>
						</c:forEach>
						</select> :
						<select id="start_mm" class="vb">
						<c:forEach begin="0" end="59" varStatus="s" var="o">
						<option value="${o<10?('0'+=o):o}">${o<10?('0'+=o):o}</option>
						</c:forEach>
						</select> 
						<span class="pd10">~</span> 
						<input type="text" id="end_dt" class="datepicker input_calendar ip6 tc" /> 
						<select id="end_hh" class="vb">
						<c:forEach begin="1" end="23" varStatus="s" var="o">
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
						<th class="tdbg3 tc">썸네일</th>
						<td class="tl">
							<c:if test="${not empty ebookMap.thumNm }">
							<img src="${utcp.getCdnUrl('upload/ebook/') }${ebookMap.thumRenm}" height="50"/>
							</c:if>
							<input type="file" id="file_thum" name="file_thum" />
							<span class="fc_orange">※ 사이즈는 200px * 200px로 등록하세요.</span>
						</td>
					</tr>
					<tr>
						<th class="tdbg3 tc">PDF</th>
						<td class="tl">
							<c:if test="${not empty ebookMap.fileNm }">
							${ebookMap.fileNm }
							</c:if>
							<input type="file" id="file_pdf" name="file_pdf" />
							<span class="fc_orange">※ pdf만 등록하세요.</span>
						</td>
					</tr>
				</tbody>
			</table>
			<div class="fl tc">
				<button type="button" onclick="location.href='${utcp.ctxPath}/admin/ebook/ebookList.do';" class="btn02 btn_grayl">목록</button>
			</div>

			<div class="fr tc">
				<button type="button" onclick="fn_reg();" class="btn01 btn_greenl">${vo.idx eq ''? '작성':'수정'}</button>
			</div>
		</form>
	</div>
</section>