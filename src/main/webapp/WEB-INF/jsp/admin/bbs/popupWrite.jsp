<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<script src="${utcp.ctxPath}/resources/ckeditor4_full/ckeditor.js"></script>
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
		// pickadate //
		if($('#start_dt').val()==''){
			$('#start_dt').val(moment(new Date()).format('YYYY-MM-DD'));
		}
		if($('#end_dt').val()==''){
			$('#end_dt').val(moment(new Date()).format('YYYY-MM-DD'));
		}
		
		var sel_opt_hh = '';
		for (var i = 0; i < 24; i++) {
			var hh = (i + '');
			hh = hh.length == 1 ? '0' + hh : hh;
			sel_opt_hh += '<option value="'+hh+'">' + hh + '시' + '</option>';
		}
		$('#start_hh').html(sel_opt_hh);
		$('#end_hh').html(sel_opt_hh);
		$('#start_hh').val('${empty popupMap.startHh?"00":popupMap.startHh}');
		$('#end_hh').val('${empty popupMap.endHh?"00":popupMap.endHh}');
		
		$(function(){
			CKEDITOR.replace('ckeditCn',{
				filebrowserUploadUrl: '${utcp.ctxPath}/editot/popupUpload.json?gubun=popup&prefixStr=${vo.idx}',
				height : 400
			});
		});
		
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

		function onApplyHandler(date, context) {
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
	});
	//]]>
	
	function fn_reg() {
		
		$('#content').val(CKEDITOR.instances.ckeditCn.getData());
		
		$('#form_reg').attr('action','popupWriteProc.do');
		$('#form_reg').submit();
	}
</script>
<section class="pd025 pd200 po_re">
	<div class="po_re br5 bs_box">
		<form name="form_reg" id="form_reg" method="post">
			<input type="hidden" name="idx" value="${vo.idx }" />
			<table width="100%" class="tb02 tc">
				<caption class="sound_only">팝업창테이블</caption>
				<tbody>
					<tr>
						<th class="tdbg3 tc">게시여부</th>
						<td class="tl" colspan=""><input type="radio" name="status" value="1" id="pop_on" ${popupMap.status eq '1'?'checked':'' }>
						<label for="pop_on" class="pdl5 mgr10 cp">게시</label> 
						<input type="radio" name="status" value="2" ${popupMap.status eq '2'?'checked':'' } id="pop_off">
						<label for="pop_off" class="pdl5 cp">미게시</label></td>
					<tr>
						<th class="tdbg3 tc">제목</th>
						<td class="tl" colspan=""><input type="text" name="title" value="${popupMap.title }" class="ip4" maxlength="100" /></td>
					</tr>
					<tr>
						<th class="tdbg3 tc">게시기간</th>
						<td class="tl" colspan="3"><input readonly type="text" name="startDt" id="start_dt" value="${popupMap.startDt }" class="datepicker input_calendar ip6 tc" /> 
						<select name="startHh" id="start_hh" class="vb">
						</select> <span class="pd10">~</span> <input readonly type="text" name="endDt" id="end_dt" value="${popupMap.endDt }" class="datepicker input_calendar ip6 tc" /> 
						<select name="endHh" id="end_hh" class="vb">
						</select></td>
					</tr>
					<tr>
						<th class="tdbg3 tc">창 크기</th>
						<td class="tl" colspan="3">가로 <input type="number" name="width" value="${popupMap.width }" id="pop_sizeW" class="ip5" min="0"> px / 
						세로 <input type="number" name="height" value="${popupMap.height }" id="pop_sizeH" class="ip5" min="0"> px
						</td>
					</tr>
					<tr>
						<th class="tdbg3 tc">창 위치</th>
						<td class="tl" colspan="3">상단에서 <input type="number" name="posY" value="${popupMap.posY }" id="pop_posiT" class="ip5" min="0"> px / 
						좌측에서 <input type="number" name="posX" value="${popupMap.posX }" id="pop_posiL" class="ip5" min="0"> px
						</td>
					</tr>
					<tr>
						<th class="tdbg3 tc">내용</th>
						<td>
							<textarea id="ckeditCn" name="ckeditCn" placeholder="내용" class="w100 h500" style="height: 100%">${popupMap.content }</textarea>
							<input type="hidden" id="content" name="content" />
						</td>
					</tr>
				</tbody>
			</table>
			<div class="fl tc">
				<button type="button" onclick="location.href='${utcp.ctxPath}/admin/bbs/popupList.do';" class="btn02 btn_grayl">목록</button>
			</div>

			<div class="fr tc">
				<button type="button" onclick="fn_reg();" class="btn01 btn_greenl">${vo.idx eq ''? '작성':'수정'}</button>
			</div>
		</form>
	</div>
</section>