<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp" %>
<script>
function fn_save(num){
	var title = $("#title" + num).val();
	var message = $("#message" + num).val();
	var autoSend = "N";
	var sendHour = $("#sendHour").val();
	var setupSeq = num;
	
	if($("input:checkbox[id=autoSend" + num + "]").is(":checked") == true){
		autoSend = "Y";
	}
	
	$.ajax({
        url: "${utcp.ctxPath}/admin/site/smsUpd.json",
        type: "POST",
        data: {
        	"setupSeq" : setupSeq,
        	"title" : title,
        	"message" : message, 
        	"autoSend" : autoSend, 
        	"sendHour" : sendHour
        },
        cache: false,
		async: true,
        success: function(r) {
			if(r.isAdmin){
				location.href = "#success";
			}else{
				location.href = "#authMessage";
			}
		}
    });
}

function fn_reload(){
	location.href = "/admin/site/smsMng.do";
}
</script>


<section class="pd025 pd200 po_re mgt20">
	<div class="po_re br5 bs_box cf">

		<!--// sms_con1 //-->
		<div class="sms_con sms_con1">
			<h3 class="font_22 fw_500 pdl15 pdt20">
				<i class="fas fa-chevron-circle-right font_22 pdr5"></i>
				신청완료
			</h3>

			<div class="tbBox1">
				<table class="tc w100">
					<thead bgcolor="#f7f8fa">
						<tr>
							<th>
								<input type="text" id="title1" class="ip4 fw_500" placeholder="제목을 입력하세요." value="${sms1.title}"/>
							</th>
						</tr>
					</thead>
					<tbody>
					   <tr>
							<td>
								<textarea id="message1" cols="44" rows="80" placeholder="내용을 입력하세요.">${sms1.message}</textarea>
							</td>
						</tr>
					</tbody>
				</table>
				<div class="tbBox_bot">
					<div class="chkBox">
						<ul>
							<li>
								<label for="autoSend1">
									<input type="checkbox" id="autoSend1" class="vm" <c:if test="${sms1.autoSend == 'Y'}">checked</c:if>/>
									자동발송
								</label>
							</li>
						</ul>
					</div>
					<div class="txtBox">
						<p class="mgb15">
							<span class="tit">예약어</span>
							{{eduName}} : 교육과정명<br/>
							{{eduDate}} : 교육일정<br/>
							{{eduPlace}} : 교육장소
						</p>
						<p>※ 사용자가 교육 신청 시 발송됩니다.</p>
					</div>
					<button type="button" class="btn04 btn_green mgt15 mgb15" onclick="fn_save('1'); return false;">저장</button>
				</div>
			</div>
		</div>
		<!--// sms_con1 //-->


		<!--// sms_con2 //-->
		<div class="sms_con sms_con2">
			<h3 class="font_22 fw_500 pdl15 pdt20 mgr15">
				<i class="fas fa-chevron-circle-right font_22 pdr5"></i>
				교육3일전 안내 문자
			</h3>

			<div class="tbBox1">
				<table class="tc w100">
					<thead bgcolor="#f7f8fa">
						<tr>
							<th>
								<input type="text" id="title2" class="ip4 fw_500" placeholder="제목을 입력하세요." value="${sms2.title}"/>
							</th>
						</tr>
					</thead>
					<tbody>
					   <tr>
							<td>
								<textarea id="message2" cols="44" rows="80" placeholder="내용을 입력하세요.">${sms2.message}</textarea>
							</td>
						</tr>
					</tbody>
				</table>
				<div class="tbBox_bot">
					<div class="chkBox">
						<ul>
							<li>
								<label for="autoSend2">
									<input type="checkbox" id="autoSend2" class="vm" <c:if test="${sms2.autoSend == 'Y'}">checked</c:if>/>
									자동발송
								</label>
							</li>
						</ul>
					</div>
					<div class="txtBox">
						<p class="mgb15">
							<span class="tit">예약어</span>
							{{eduName}} : 교육과정명<br/>
							{{eduDate}} : 교육일정<br/>
							{{eduPlace}} : 교육장소
						</p>
						<p>
							※ 교육 시작 3일전 
							<select id="sendHour" class="timeSel">
								<c:forEach begin="0" end="23" varStatus="stat">
									<c:set var="hour" value="${stat.index}"/>
			                		<c:if test="${hour < 10}"><c:set var="hour" value="0${hour}"/></c:if>
									<option value="${hour}" <c:if test="${sms2.sendHour == hour}">selected</c:if>>${hour}시</option>
								</c:forEach>
							</select>시에 일괄 발송됩니다.
						</p>
					</div>
					<button type="button" class="btn04 btn_green mgt15 mgb15" onclick="fn_save('2'); return false;">저장</button>
				</div>
			</div>
		</div>
		<!--// sms_con2 //-->
		
		
		<!--// sms_con3 //-->
		<div class="sms_con sms_con3">
			<h3 class="font_22 fw_500 pdl15 pdt20 mgr15">
				<i class="fas fa-chevron-circle-right font_22 pdr5"></i>
				교육결과 발표 후							
			</h3>

			<div class="tbBox1">
				<table class="tc w100">
					<thead bgcolor="#f7f8fa">
						<tr>
							<th>
								<input type="text" id="title3" class="ip4 fw_500" placeholder="제목을 입력하세요." value="${sms3.title}"/>
							</th>
						</tr>
					</thead>
					<tbody>
					   <tr>
							<td>
								<textarea id="message3" cols="44" rows="80" placeholder="내용을 입력하세요.">${sms3.message}</textarea>
							</td>
						</tr>
					</tbody>
				</table>
				<div class="tbBox_bot">
					<div class="chkBox">
						<ul>
							<li>
								<label for="autoSend3">
									<input type="checkbox" id="autoSend3" class="vm" <c:if test="${sms3.autoSend == 'Y'}">checked</c:if>/>
									자동발송
								</label>
							</li>
						</ul>
					</div>
					<div class="txtBox">
						<p class="mgb15">
							<span class="tit">예약어</span>
							{{eduName}} : 교육과정명<br/>
							{{eduDate}} : 교육일정<br/>
							{{eduPlace}} : 교육장소
						</p>
						<p>※ 교육관리 &gt; 교육관리 &gt; 수강생 출석/성적/합격탭에서 [교육종료] 시점에 일괄 발송됩니다.</p>
					</div>
					<button type="button" class="btn04 btn_green mgt15 mgb15" onclick="fn_save('3'); return false;">저장</button>
				</div>
			</div>
		</div>
		<!--// sms_con3 //-->
	</div>
</section>

<div class="remodal messagePop messagePop1" data-remodal-id="success" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc">
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC">알림</p>
		</div>
		<div class="modal-body">
			<p class="messageTxt">
				저장되었습니다.
			</p>
		</div>
		<div class="modal-footer">
			<div class="tc">
				<button onclick="fn_reload(); return false;" class="remodal-confirm btn02 btn_green">확인</button>
			</div>
		</div>
	</div>
</div>