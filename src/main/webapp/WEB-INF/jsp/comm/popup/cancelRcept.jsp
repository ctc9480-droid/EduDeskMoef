<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<div id="wrap">
	<div id="content" class="pdt0 w100">
		<form id="form-refund" method="post" enctype="multipart/form-data">
			<table class="tb01">
				<caption>
					<span class="ir-text">신청서 작성 테이블</span>
				</caption>
				<colgroup>
					<col class="w150px">
					<col>
					<col class="w150px">
					<col>
				</colgroup>
				<tbody>
					<tr>
						<th class=""><span class="pointless">*</span> 구분</th>
						<td colspan="3">
							<div class="slt_wrap ">
								<select name="gubun2" class="" title="환불 시험취소 여부 선택">
									<option value="01" selected>환불</option>
								</select>
							</div>
							<div class="slt_wrap" id="childgubun">
								<select name="gubun"  title="환불/시험취소 구분">
									<option value="">선택하세요</option>
									<option value="01">과입금</option>
									<option value="02">이중입금</option>
									<option value="07">수수료변경</option>
									<option value="08">기타</option>
								</select>

							</div>
						</td>
					</tr>
					<tr>
						<th class="choice_type1"><span class="pointless">*</span> 환불의뢰 사업장</th>
						<td colspan="3">
							<div class="">
								<input type="radio" name="requestPlace" value="01" id="inp-rdoa1" class="" checked="" title="환불의뢰 사업장 서울">
								<label for="inp-rdoa1" class="inp_rdo">서울</label>
							</div>
							<div class="">
								<input type="radio" name="requestPlace" value="02" id="inp_rdob2" class="" title="환불의뢰 사업장 안산">
								<label for="inp_rdob2" class="inp_rdo">안산</label>
							</div>
							<div class="">
								<input type="radio" name="requestPlace" value="03" id="inp_rdob3" class="" title="환불의뢰 사업장 진주">
								<label for="inp_rdob3" class="inp_rdo">진주</label>
							</div>
							<div class="">
								<input type="radio" name="requestPlace" value="04" id="inp_rdob4" class="" title="환불의뢰 사업장 대전">
								<label for="inp_rdob4" class="inp_rdo">대전</label>
							</div>
							<div class="">
								<input type="radio" name="requestPlace" value="05" id="inp_rdob5" class="" title="환불의뢰 사업장 창원">
								<label for="inp_rdob5" class="inp_rdo">창원</label>
							</div>
							<div class="">
								<input type="radio" name="requestPlace" value="06" id="inp_rdob6" class=" " title="환불의뢰 사업장 원주">
								<label for="inp_rdob6" class="inp_rdo">원주</label>
							</div>

							<div class="">
								<input type="radio" name="requestPlace" value="08" id="inp_rdob7" class=" " title="환불의뢰 사업장 거창">
								<label for="inp_rdob7" class="inp_rdo">여수</label>
							</div>
						</td>
					</tr>
					<tr>
						<th class=""><span class="pointless">*</span> 작성자</th>
						<td><input type="text" name="name" value="" maxlength="50" class="inp_txt case1 required" title="작성자"></td>

						<th class=""><span class="pointless">*</span> 비밀번호</th>
						<td><input type="text" name="password" maxlength="4" class="inp_txt case1 required" title="비밀번호"></td>

					</tr>
					<tr>
						<th class="">전화번호(회사)</th>
						<td><input type="text" name="telephone" maxlength="20" value="" class=" " title="전화번호(회사)"> <br> <span> ※ 입력예 : 02-1234-5678</span></td>
						<th class=""><span class="pointless">*</span> 휴대폰번호</th>
						<td><input type="text" name="cellphone" maxlength="20" class="  " value="" title="휴대폰번호"> <br> <span> ※ 입력예 : 010-1234-5678</span></td>
					</tr>
					<tr>
						<th class=""><span class="pointless">*</span>업체명</th>
						<td><input type="text" name="cmnm" class="  " value="" maxlength="50" title="업체명"></td>
						<th class=""><span class="pointless">*</span>사업자 등록번호</th>
						<td><input type="text" name="cmpnBrno" maxlength="15" class="  " title="사업자 등록번호" value=""> <br> <span>※ 입력예 : 123-12-12345</span></td>
					</tr>
					<tr class="">
						<th class=""><span class="">*</span> 결제유형</th>
						<td colspan="3">
							<div class="slt_wrap ">
								<select name="depositSort" id="depositSort" title="결제유형" class="slt_box pay_after required" onchange="null">
									<option value="">선택하세요</option>
									<option value="01">계좌이체</option>
									<option value="02">카드결제(방문)</option>
									<option value="04">카드결제(온라인)</option>
									<option value="03">현금</option>

								</select>

							</div>
						</td>
					</tr>
					<tr class="pay_after_tr">
						<th class="choice_type1"><span class="pointless">*</span> 결제일자</th>
						<td><input type="text" id="data1" name="depositDate" value="" class="inp_txt pay_after case1 required hasDatepicker" title="결제일자" readonly="readonly">
							<button type="button" class="ui-datepicker-trigger">
								<img src="/resource/templete/cms1/src/img/sub/calendar.png" alt="날짜선택" title="날짜선택">
							</button></td>
						<th class="choice_type1"><span class="pointless">*</span> 결제금액</th>
						<td><input type="text" onkeyup="fn_numFormat('totDepositMoney', this);" maxlength="25" value="" class="inp_txt pay_after required" title="결제금액"> <input type="hidden" name="totDepositMoney" id="totDepositMoney" value="" class="inp_txt pay_after validate-digits case1 required" title="결제금액"></td>
					</tr>
					<tr class="pay_after_tr">
						<th class="choice_type1"><span class="pointless">*</span>입금자명</th>
						<td colspan="3"><input type="text" maxlength="33" name="depositName" value="" class="inp_txt pay_after case1 required" title="입금자명"></td>
					</tr>
				</tbody>
			</table>
			
			<table class="tb01">
				<caption><span class="ir-text">신청서 작성 테이블</span></caption>
				<colgroup>
					<col class="w150px">
					<col>
					<col class="w150px">
					<col>
				</colgroup>
				<tbody>
					<tr class="pay_after_tr">
						<th class="choice_type1"><span class="pointless">*</span>환불 요청금액</th>
						<td>
							<input type="text" onkeyup="fn_numFormat('refund', this);" maxlength="25" value="" class="inp_txt pay_after required" title="환불 요청금액">
							<input type="hidden" name="refund" id="refund" value="" class="inp_txt pay_after validate-digits case1 required" title="환불 요청금액">
						</td>
						<th class="choice_type1"><span class="pointless">*</span>환불 은행명</th>
						<td><input type="text" name="refundBankName" maxlength="13" value="" class="inp_txt pay_after case1 required noCard" title="환불 은행명"></td>
					</tr>
					<tr class="pay_after_tr">
						<th class="choice_type1"><span class="pointless">*</span>예금주</th>
						<td><input type="text" name="accountHolder" maxlength="20" value="" class="inp_txt pay_after case1 noCard required" title="예금주"> <br>
						<span>※ 예금주는 법인 또는 대표자만 가능</span></td>
						<th class="choice_type1"><span class="pointless">*</span>환불 계좌번호</th>
						<td><input type="text" name="refundAccountNumber" maxlength="20" value="" class="inp_txt pay_after case1 noCard required" title="환불 계좌번호"> <br>
						<span>※ 입력예 : 123-451-234567</span></td>
					</tr>
					<tr class="pay_after_tr">
						<th class="choice_type1"><span class="pointless">*</span>통장사본 첨부</th>
						<td colspan="3">
							<div class="upload_box">
								<input type="text" value="" id="filetxt" class="inp_txt noCard pay_after upload_text case4" readonly="readonly" title="통장사본">
								<div class="upload-btn_wrap">
									<button type="button" title="파일찾기">
										<span>첨부 파일</span>
									</button>
									<input type="file" id="file1" name="file1" class="input_file" title="통장사본">
								</div>
							</div>
						</td>
					</tr>
					<tr>
						<th class="choice_type1"><span class="pointless">*</span>접수번호</th>
						<td colspan="3">
						<input type="text" maxlength="12" id="unfcRcpnSrno" name="unfcRcpnSrno" value="" title="접수번호 입력" class="inp_txt case1 required">
							 <br> * 통합접수번호(12-123456 형태) 또는 개별 접수번호(12-123456-12 형태) 한 건만 입력해 주세요. <br>* 세부사항은 취소사유기재 란에 입력해 주세요.</td>
					</tr>
					<tr id="refund_reason">
						<th class="choice_type1"><label for="cancel-t"><span class="pointless">*</span>취소사유 기재</label></th>
						<td colspan="3">
							
								<input type="text" id="cancel-t" name="content" maxlength="200" placeholder="취소사유를 200자 이내로 입력해 주세요." class="inp_txt wd100 required" title="취소사유" value="">
						</td>
					</tr>
				</tbody>
			</table>
			
		</form>
		<div class="w100 tc mgt20">
			<button class="btn04 btn_blue" onclick="fn_refundRcept()">신청</button>
		</div>
	</div>
</div>

<script>
	function fn_refundRcept() {
		/*
		*/
		$('#form-refund').attr('action','http://192.168.0.164:8080/user/ajax/testRefundProc.do');
		$('#form-refund').submit();
		
	}
	
	const form = document.getElementById('form-refund'); // 폼 선택
	form.addEventListener('submit', (event) => {
	  event.preventDefault(); // 기본 동작 방지

	 
	});
</script>