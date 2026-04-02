<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp" %>
<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script src="${utcp.ctxPath}/resources/ckeditor4/ckeditor.js"></script>
<script>
//<![CDATA[
$(function () {
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
	CKEDITOR.replace('ckeditCn',{
		filebrowserUploadUrl: '${utcp.ctxPath}/editot/popupUpload.do',
		height : 400
	});
	
	$(".onlyNumber").keyup(function(event){      
        var str;                     
        if(event.keyCode != 8){
            if (!(event.keyCode >=37 && event.keyCode<=40)) {
                var inputVal = $(this).val();       
                str = inputVal.replace(/[^0-9]/gi,'');            
                $(this).val(str);             
            }                   
        }
    });
	
	$("#eduCtgrySeq").change(function(){
		$.ajax({
	        url: "${utcp.ctxPath}/admin/edu/getInstrctrList.json",
	        type: "POST",
	        data: { "ctgrySeq" : $("#eduCtgrySeq").val() },
	        cache: false,
			async: true,
	        success: function(dataList) {
	        	$("#instrctrId").empty();
	        	$("#instrctrId").append(
        			"<option value=\"\">강사선택</option>"	
        		);
	        	if(dataList != null && dataList != undefined && dataList != "") {
	        		$(dataList).each(function(i, e) {
	        			$("#instrctrId").append(
	        				"<option value=\"" + this.userId + "\">" + this.userNm + "</option>"
	        			);
	        		});
	        	}
			}
	    });
	});
	
	<c:if test='${lctre.fee == "" || lctre.fee == null || lctre.fee == 0}'>
		$("#fee").val("");
		$("#feeLimit").val("");
		$("#fee").attr("disabled", true);
		$("#feeLimit").attr("disabled", true);
	</c:if>
	
	<c:if test='${lctre.groupFee == "" || lctre.groupFee == null || lctre.groupFee == 0}'>
		$("#groupFee").val("");
		$("#groupComment").val("");
		$("#groupFee").attr("disabled", true);
		$("#groupComment").attr("disabled", true);
	</c:if>
	
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
        }
        else if (date[0] === null && date[1] == null) {
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
        }
        else if (date[0] === null && date[1] == null) {
            text += '선택된 날짜가 없습니다.';
        }

        if (date[1] !== null) {
            text += date[1].format('YYYY-MM-DD');
        }

        $box.text(text);
    }


	// 캘린더 설정
	$('.calendar').pignoseCalendar({
		select: onSelectHandler,
		format: 'YYYY-MM-DD', // date format string 년월일로 포맷변경
		// disabledWeekdays: [1], // 매주월요일선택못함 SUN(0),MON(1),TUE(2),WED(3),THU(4),FRI(5),SAT(6)
		theme: 'blue', // 테마변경 light,blue/dark
		lang: 'ko', // 한국
		// minDate: moment().format("YYYY-MM-DD") //지난날짜 선택못함
	});	
	
});
//]]>

function fn_postSearch(){
	new daum.Postcode({
        oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 도로명 주소의 노출 규칙에 따라 주소를 표시한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var roadAddr = data.roadAddress; // 도로명 주소 변수
            var extraRoadAddr = ''; // 참고 항목 변수

            // 법정동명이 있을 경우 추가한다. (법정리는 제외)
            // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
            if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                extraRoadAddr += data.bname;
            }
            // 건물명이 있고, 공동주택일 경우 추가한다.
            if(data.buildingName !== '' && data.apartment === 'Y'){
               extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
            }
            // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
            if(extraRoadAddr !== ''){
                extraRoadAddr = ' (' + extraRoadAddr + ')';
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            $('#postNo').val(data.zonecode);
            $('#addr').val(roadAddr);
            
            // 참고항목 문자열이 있을 경우 해당 필드에 넣는다.
            if(roadAddr != ''){
            	$('#addrEtc').val(extraRoadAddr);
            } else {
            	$('#addrEtc').val("");
            }
            $('#addrDetail').focus();
        }
    }).open();
}

function fn_rgs(){
	
	if($("#eduNm").val() == ""){
		$("#messageStr").html("교육과정 명은 필수입력입니다.");
		location.href = "#message";
		$("#inputId").val("eduNm");
		return;
	}
	
	if($("#eduCtgrySeq").val() == ""){
		$("#messageStr").html("교육과정을 선택하세요.");
		location.href = "#message";
		$("#inputId").val("eduCtgrySeq");
		return;
	}else{
		$("#eduCtgryNm").val($("#eduCtgrySeq option:checked").text());
	}
	
	if($("#detailCtgrySeq").val() == ""){
		$("#messageStr").html("상세과정을 선택하세요.");
		location.href = "#message";
		$("#inputId").val("detailCtgrySeq");
		return;
	}else{
		$("#detailCtgryNm").val($("#detailCtgrySeq option:checked").text());
	}
	
	if($("#instrctrId").val() == ""){
		$("#messageStr").html("강사를 선택하세요.");
		location.href = "#message";
		$("#inputId").val("instrctrId");
		return;
	}else{
		$("#instrctrNm").val($("#instrctrId option:checked").text());
	}
	
	if($("input:checkbox[id=imgUseChk]").is(":checked") == true) {
		$("#imgUseYn").val("N");
	}else{
		$("#imgUseYn").val("Y");
	}
	
	var eduBegin = $("#eduPeriodBegin").val();
	var eduEnd = $("#eduPeriodEnd").val();
	
	if(eduBegin == ""){
		$("#messageStr").html("교육기간은 필수입력입니다.");
		location.href = "#message";
		$("#inputId").val("eduPeriodBegin");
		return;
	}else if(eduEnd == ""){
		$("#messageStr").html("교육기간은 필수입력입니다.");
		location.href = "#message";
		$("#inputId").val("eduPeriodEnd");
		return;
	}else{
		if(Number(eduBegin.replace(/-/gi, "")) > Number(eduEnd.replace(/-/gi, ""))){
			$("#messageStr").html("교육기간을 확인하세요.");
			location.href = "#message";
			$("#inputId").val("eduPeriodEnd");
			return;
		}
	}
	
	if($("input:checkbox[id=rceptUseChk]").is(":checked") == true) {
		$("#rceptPeriodYn").val("N");
	}else{
		$("#rceptPeriodYn").val("Y");
		
		if($("#rceptBeginDay").val() == ""){
			$("#messageStr").html("접수기간을 입력하세요.");
			location.href = "#message";
			$("#inputId").val("rceptBeginDay");
			return;
		}else if($("#rceptEndDay").val() == ""){
			$("#messageStr").html("접수기간을 입력하세요.");
			location.href = "#message";
			$("#inputId").val("rceptEndDay");
			return;
		}else{
			var rceptBegin = $("#rceptBeginDay").val().replace(/-/gi, "") + $("#rceptBeginHour").val() + $("#rceptBeginMin").val();
			var rceptEnd = $("#rceptEndDay").val().replace(/-/gi, "") + $("#rceptEndHour").val() + $("#rceptEndMin").val();	
			
			if(Number(rceptBegin) > Number(rceptEnd)){
				$("#messageStr").html("접수기간을 확인하세요.");
				location.href = "#message";
				$("#inputId").val("rceptEndDay");
				return;
			}else{
				$("#rceptPeriodBegin").val(rceptBegin);
				$("#rceptPeriodEnd").val(rceptEnd);
			}
		}
	}
	
	if($("#closeType").val() == ""){
		$("#messageStr").html("마감옵션을 선택하세요.");
		location.href = "#message";
		$("#inputId").val("closeType");
		return;
	}
	
	if(CKEDITOR.instances.ckeditCn.getData() == ""){
		$("#messageStr").html("상세내용을 입력하세요.");
		location.href = "#message";
		$("#inputId").val("ckeditCn");
		return;
	}else{
		$('#cn').val(CKEDITOR.instances.ckeditCn.getData());
	}
	
	if($("#postNo").val() == "" || $("#addr").val() == ""){
		$("#messageStr").html("교육장소를 입력하세요.");
		location.href = "#message";
		$("#inputId").val("postNo");
		return;
	}
	
	$("form[name='rgsFrm']").attr("action", "/admin/edu/lctreRgsProc.do");
	$("form[name='rgsFrm']").submit();
}

function fn_focus(){
	var inputId = "#" + $("#inputId").val();
	$(inputId).focus();
	location.href = "#";
}

function fn_default(){
	if($("input:checkbox[id=defaultChk]").is(":checked") == true) {
		$("#fee").attr("disabled", false);
		$("#feeLimit").attr("disabled", false);
	}else{
		$("#fee").attr("disabled", true);
		$("#feeLimit").attr("disabled", true);
	}
}

function fn_group(){
	if($("input:checkbox[id=groupChk]").is(":checked") == true) {
		$("#groupFee").attr("disabled", false);
		$("#groupComment").attr("disabled", false);
	}else{
		$("#groupFee").attr("disabled", true);
		$("#groupComment").attr("disabled", true);
	}
}

function fn_defaultAddr(){
	if($("input:checkbox[id=defaultAddr]").is(":checked") == true) {
		$("#postNo").val("06242");
		$("#addr").val("서울 강남구 역삼로 111");
		$("#addrEtc").val("(역삼동)");
		$("#addrDetail").val("");
	}else{
		$("#postNo").val("");
		$("#addr").val("");
		$("#addrEtc").val("");
		$("#addrDetail").val("");
	}
}
</script>
<section class="pd025 pd200 po_re">
	<div class="po_re br5 bs_box cf">
		<form method="post" id="rgsFrm" name="rgsFrm" enctype="multipart/form-data">
			<table width="100%" class="tb02 tc">
				<tbody>
					<tr>
						<th class="tdbg3 tc"><label for="eduNm">교육과정 명</label></th>
						<td class="tl" colspan="3">
							<input type="text" name="eduNm" id="eduNm" class="ip2 mgr10" maxlength="400" value="${lctre.eduNm}"/>
							<input type="radio" name="openYn" value="Y" id="openY" <c:if test='${lctre.openYn == "Y"}'>checked</c:if>/>
							<label for="openY" class="pdl5 mgr10 cp">노출</label>
							<input type="radio" name="openYn" value="N" id="openN" <c:if test='${lctre.openYn == "N"}'>checked</c:if>/>
							<label for="openN" class="pdl5 cp">미노출</label>										
						</td>
					</tr>
					<tr>
						<th class="tdbg3 tc">교육과정</th>
						<td class="tl" colspan="3">
							<select name="eduCtgrySeq" id="eduCtgrySeq" class="vb">
								<option value="">교육과정</option>
								<c:forEach var="data" items="${eduList}" varStatus="stat">
									<option value="${data.ctgrySeq}" <c:if test='${lctre.eduCtgrySeq == data.ctgrySeq}'>selected</c:if>>${data.ctgryNm}</option>
								</c:forEach>
							</select>
							<input type="hidden" name="eduCtgryNm" id="eduCtgryNm" />
							<select name="detailCtgrySeq" id="detailCtgrySeq" class="vb">
								<option value="">상세과정</option>
								<c:forEach var="data" items="${detailList}" varStatus="stat">
									<option value="${data.ctgrySeq}" <c:if test='${lctre.detailCtgrySeq == data.ctgrySeq}'>selected</c:if>>${data.ctgryNm}</option>
								</c:forEach>
							</select>
							<input type="hidden" name="detailCtgryNm" id="detailCtgryNm" />
							<select name="instrctrId" id="instrctrId" class="vb">
								<option value="">강사선택</option>
								<c:forEach var="data" items="${instrctrList}" varStatus="stat">
									<option value="${data.userId}" <c:if test='${lctre.instrctrId == data.userId}'>selected</c:if>>${data.userNm}</option>
								</c:forEach>
							</select>
							<input type="hidden" name="instrctrNm" id="instrctrNm" />
						</td>
					</tr>
					<tr>
						<th class="tdbg3 tc">대표 이미지</th>
						<td class="tl" colspan="3">
							<div class="write_div">
								<label for="imgFile"><span class="sound_only">파일</span></label>
								<input type="file" name="imgFile" id="imgFile" title="파일첨부" class="frm_file ip2">
								<label for="imgUseChk" class="mgl10 mgr10">
									<input type="checkbox" id="imgUseChk" class="vm" <c:if test='${lctre.imgUseYn != "Y"}'>checked</c:if>/> 생략
								</label>
								<input type="hidden" name="imgUseYn" id="imgUseYn" />
								<!--// 생략시 기본 이미지 default_img.png //-->
							</div>									
						</td>
					</tr>
					<tr>
						<th class="tdbg3 tc">교육 기간</th>
						<td class="tl" colspan="3">
							<input type="text" id="eduPeriodBegin" name="eduPeriodBegin" class="datepicker input_calendar ip6 tc" />
							<span class="pd10">~</span>
							<input type="text" id="eduPeriodEnd" name="eduPeriodEnd" class="datepicker input_calendar ip6 tc" />
						</td>
					</tr>
					<tr>
						<th class="tdbg3 tc">접수 기간</th>
						<td class="tl" colspan="3">
							<input type="text" id="rceptBeginDay" class="datepicker input_calendar ip6 tc"/>
							<select id="rceptBeginHour" class="vb">
								<c:forEach begin="0" end="23" varStatus="stat">
									<c:set var="hour" value="${stat.index}"/>
			                		<c:if test="${hour < 10}"><c:set var="hour" value="0${hour}"/></c:if>
									<option value="${hour}">${hour}시</option>
								</c:forEach>
							</select>
							<select id="rceptBeginMin" class="vb">
								<option value="00">00분</option>
								<option value="10">10분</option>
								<option value="20">20분</option>
								<option value="30">30분</option>
								<option value="40">40분</option>
								<option value="50">50분</option>
							</select>
							<input type="hidden" id="rceptPeriodBegin" name="rceptPeriodBegin" />
							<span class="pd10">~</span>
							<input type="text" id="rceptEndDay" class="datepicker input_calendar ip6 tc"/>
							<select id="rceptEndHour" class="vb">
								<c:forEach begin="0" end="23" varStatus="stat">
									<c:set var="hour" value="${stat.index}"/>
			                		<c:if test="${hour < 10}"><c:set var="hour" value="0${hour}"/></c:if>
									<option value="${hour}">${hour}시</option>
								</c:forEach>
							</select>
							<select id="rceptEndMin" class="vb">
								<option value="00">00분</option>
								<option value="10">10분</option>
								<option value="20">20분</option>
								<option value="30">30분</option>
								<option value="40">40분</option>
								<option value="50">50분</option>
							</select>
							<input type="hidden" id="rceptPeriodEnd" name="rceptPeriodEnd" />
							<label for="rceptUseChk">
								<input type="checkbox" id="rceptUseChk" <c:if test='${lctre.rceptPeriodYn != "Y"}'>checked</c:if>/> 미설정
								<input type="hidden" id="rceptPeriodYn" name="rceptPeriodYn" />
							</label>
						</td>
					</tr>
					<tr>
						<th class="tdbg3 tc">모집 인원</th>
						<td class="tl" colspan="3">
							<input type="number" name="personnel" id="personnel" class="ip9 mgr5" value="${lctre.personnel}"/>명 
							<%-- (최소인원 : <input type="number" name="minPersonnel" id="minPersonnel" value="${lctre.minPersonnel}" class="ip9 mgr5"/>명)
							<input type="hidden" name="extPersonnel" id="extPersonnel" value="0"/> --%>
							(최소인원 : <input type="number" name="extPersonnel" id="extPersonnel" value="${lctre.extPersonnel}" class="ip9 mgr5"/>명)
							<input type="hidden" name="minPersonnel" id="minPersonnel" value="0"/>
							<select name="closeType" id="closeType" class="mgl20 vb">
								<option value="">마감 옵션</option>
								<option value="01" <c:if test='${lctre.closeType == "01"}'>selected</c:if>>선착순 등록 마감</option>
								<option value="02" <c:if test='${lctre.closeType == "02"}'>selected</c:if>>관리자 임의 마감</option>
							</select>
						</td>
					</tr>
					<tr>
						<th class="tdbg3 tc">교육 금액</th>
						<td class="tl" colspan="3">
							<span class="dp_b mgb10">
								<label for="defaultChk">
									<input type="checkbox" id="defaultChk" class="vm mgr5" <c:if test='${lctre.fee != ""}'>checked</c:if> onclick="fn_default();"/>기본
								</label>
								<input type="text" name="fee" id="fee" value="${lctre.fee}" class="ip5 mgl10 mgr5 onlyNumber tc"/>원 
								<input type="text" name="feeLimit" id="feeLimit" value="${lctre.feeLimit}" class="ip9 mgl10 onlyNumber tc"/> 일 전까지 미결제시 취소처리 될 수 있습니다.
							</span>
							<span class="dp_b">
								<label for="groupChk">
									<input type="checkbox" id="groupChk" class="vm mgr5" <c:if test='${lctre.groupFee != ""}'>checked</c:if> onclick="fn_group();"/>단체
								</label>
								<input type="text" name="groupFee" id="groupFee" value="${lctre.groupFee}" class="ip5 mgl10 mgr5 onlyNumber tc"/>원 
								<input type="text" name="groupComment" id="groupComment" value="${lctre.groupComment}" class="ip8 mgl10" placeholder="단체 할인시 결제 창 안내문구 입력"/>
							</span>
						</td>
					</tr>
					<tr>
						<th class="tdbg3 tc">상세 내용</th>
						<td class="tl" colspan="3">
							<textarea id="ckeditCn" name="ckeditCn" placeholder="내용" class="w100 h500">${lctre.cn}</textarea>
							<input type="hidden" id="cn" name="cn" />
						</td>
					</tr>								
					<tr>
						<th class="tdbg3 tc">교육 장소</th>
						<td class="tl" colspan="3">
							<div class="addrWrap">
								<label for="defaultAddr" class="dp_b">
									<input type="checkbox" id="defaultAddr" onclick="fn_defaultAddr();"/> 에듀데스크
								</label>
								<input type="text" class="ip3" id="addrMemo" name="addrMemo" placeholder="추가 장소 메모" value="${lctre.addrMemo}"/><br>
								<input type="text" class="addr ip5 tc" placeholder="우편번호" id="postNo" name="postNo" value="${lctre.postNo}" onclick="fn_postSearch(); return false;"/>
								<button type="button" class="btn04 btn_gray" onclick="fn_postSearch(); return false;">검색</button><br>
								<input type="text" class="addr1 ip2" placeholder="도로명주소" id="addr" name="addr" value="${lctre.addr}" readonly/><br/>
								<input type="text" class="addr3 ip2" placeholder="상세주소" id="addrDetail" name="addrDetail" value="${lctre.addrDetail}" />
								<input type="text" class="addr4 ip1" placeholder="참고항목" id="addrEtc" name="addrEtc" value="${lctre.addrEtc}" readonly/>
							</div>
						</td>
					</tr>
					<tr>
						<th class="tdbg3 tc"><label for="eduNm">문의전화</label></th>
						<td class="tl" colspan="3">
							<input type="text" name="tel" id="tel" class="ip1 mgr10" maxlength="13" ${lctre.tel }/>
						</td>
					</tr>
					<tr>
						<th class="tdbg3 tc"><label for="eduNm">오시는 길</label></th>
						<td class="tl" colspan="3">
							<textarea name="wayCome" style="width:100%;height: 100px;">${lctre.wayCome }</textarea>
						</td>
					</tr>
				</tbody>
			</table>
			
			<div class="tc">
				<button class="btn01 btn_greenl mgb20" onclick="fn_rgs(); return false;">등록하기</button>
			</div>						
		</form>							
	</div>
</section>

<div class="remodal messagePop messagePop2" data-remodal-id="message" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc">
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC">알림</p>
		</div>
		<div class="modal-body">
			<p class="messageTxt" id="messageStr">
				
			</p>
		</div>
		<input type="hidden" id="inputId" />
		<div class="modal-footer">
			<div class="tc">
				<button onclick="fn_focus(); return false;" class="remodal-confirm btn02 btn_orange">확인</button>
			</div>
		</div>
	</div>
</div>
