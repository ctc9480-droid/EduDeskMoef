<%@page import="com.educare.component.VarComponent"%>
<%@page import="com.educare.util.LncUtil"%>
<%@page import="com.educare.edu.comn.vo.LectureEtcIemVO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="com.fasterxml.jackson.databind.ObjectMapper"%>
<%@page import="com.educare.edu.education.service.model.Lecture"%>

<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<%@ page import="com.educare.edu.member.service.SessionUserInfoHelper"%>
<%@ page import="com.educare.edu.member.service.model.UserInfo"%>
<%@ page import="java.security.MessageDigest"%>
<!-- 기타항목 출력 -->
<%
List<LectureEtcIemVO> etcList = new ArrayList<LectureEtcIemVO>();

Lecture lctre = (Lecture)request.getAttribute("lctre");
ObjectMapper om = new ObjectMapper();
System.out.println(lctre.getEtcIemJson());
Map<String, Object> etcIemMap = om.readValue(lctre.getEtcIemJson(), Map.class);
List<Map<String, Object>> etcIemList = (List<Map<String, Object>>) etcIemMap.get("etcIemList");
for(Map<String, Object> etcIem : etcIemList){
	LectureEtcIemVO leiVO = new LectureEtcIemVO();
	leiVO.setEtcIemNm(etcIem.get("etcIemNm").toString());
	leiVO.setEtcIemSeq(LncUtil.nvlInt(etcIem.get("etcIemSeq")));
	leiVO.setEtcIemEx(LncUtil.replaceNull(etcIem.get("etcIemEx")));
	leiVO.setDataInputTy(etcIem.get("dataInputTy").toString());
	leiVO.setEssntlInputYn(LncUtil.replaceNull(etcIem.get("essntlInputYn")));
	leiVO.setUseYn(LncUtil.replaceNull(etcIem.get("useYn")));
	etcList.add(leiVO);
}
pageContext.setAttribute("etcList",etcList);
%>

<%
	boolean isStdnt = false;
	isStdnt = SessionUserInfoHelper.isLogined()
			&& UserInfo.MEM_LVL_STDNT.equals(SessionUserInfoHelper
					.getUserMemLvl());
%>
<jsp:useBean id="curDate" class="java.util.Date" />
<script type="text/javascript">
	$(document).ready(function() {
		
	});

	function fn_moveList() {
		location.href = "${utcp.ctxPath}/user/edu/eduList.do";
	}

	function fn_auth() {
		location.href = "#auth";
	}

	function fn_moveLogin() {
		location.href = "${utcp.ctxPath}/user/login.do";
	}

	function fn_moveView() {
		location.href = "${utcp.ctxPath}/user/edu/eduView.do?eduSeq=${lctre.eduSeq}&srchCtgry=${param.srchCtgry}";
	}

	function fn_rcept() {
		
		var userId = "${user.userId}";
		var eduSeq = "${lctre.eduSeq}";
		
		<c:if test="${lctre.fee > 0}">
		<%--
		if(!$('input[name=feeTp]:checked').val()){
			alert('결제방법을 선택하셔야 합니다.');
			$('#feeTp1').focus();
			return;
		}
		--%>
		</c:if>
		
		 // 숙소이용선택 체크 여부 확인
//         if($("input[name='dormiCapaCnt']:checked").length === 0){
//             alert("숙소이용선택을 해주세요.");
//             return false;
//         }
        // 버스이용 체크 여부 확인
        if($("input[name='useTrans']:checked").length === 0){
            alert("버스이용을 선택해주세요.");
            return false;
        }
        // dormiAccess 값 처리 (체크 여부에 따라 Y/N)
        var dormiAccessVal = $("#dormiAccess").is(":checked") ? "Y" : "N";
        // 선택된 값 출력 (예시)
        var dormiCapaCntVal = $("input[name='dormiCapaCnt']:checked").val() || '0';
        var useTransVal = $("input[name='useTrans']:checked").val();
        //console.log("숙소 선택:", dormiCapaCntVal);
        //console.log("버스 선택:", useTransVal);
        //console.log("장애인용:", dormiAccessVal);
		
		//기타항목 
		var etcIemDataJson = {etcIemList : []};
		<c:if test="${lctre.etcIemYn == 'Y'}">
		var etcIemJson = '${lctre.etcIemJson}';
		var etcIemObj = JSON.parse(etcIemJson);
		for(i in etcIemObj.etcIemList){
			var etcIem = etcIemObj.etcIemList[i];
			var _value = '';
			if(etcIem.dataInputTy == 'checkbox'){
				$('input:checkbox[name=etcIemSeq_'+etcIem.etcIemSeq+']:checked').each(function(i){
					console.log(i);
					if(i!=0){
						_value += ',';
					}
					_value += this.value;
				});
			}else if(etcIem.dataInputTy == 'radio'){
				_value = $('input:radio[name=etcIemSeq_'+etcIem.etcIemSeq+']:checked').val();
			}else{
				_value = $('[name=etcIemSeq_'+etcIem.etcIemSeq+']').val();
			}
			//console.log(_value);
			if(etcIem.essntlInputYn == 'Y'){
				if(!_value){
					alert(etcIem.etcIemNm+'을(를) 입력하셔야합니다.');
					$('#etcIemSeq_'+etcIem.etcIemSeq+'_0').focus();
					return;
				}
			}
			etcIem.etcIemData = _value;
		}
		//console.log(etcIemObj);
		etcIemDataJson = JSON.stringify(etcIemObj);
		//return;
		</c:if>
		
		var formData = new FormData($('form[name=formRcept]')[0]);
		formData.append('dormiCapaCnt', dormiCapaCntVal);
		formData.append('userId', userId);
		formData.append('eduSeq', eduSeq);
		formData.append("file_rcept", $("#fileRcept")[0].files[0]);
		formData.append("etcIemDataJson", etcIemDataJson);
		formData.append("dormiAccessYn", dormiAccessVal);
		
		<c:if test="${lctre.essntlFileYn eq 'Y'}">
			if ($('#fileRcept').get(0).files.length === 0) {
			  // 파일이 첨부되지 않은 경우
			  alert('파일을 첨부하셔야 합니다.');
			  return;
			}
		</c:if>
		
		<c:if test="${lctre.etcAgreeYn eq 'Y'}">
			if($('input:radio[name=agree3]:checked').val() != 'Y'){
				alert('개인정보 수집 및 이용약관에 동의하셔야 합니다.');
				return;
			}
		</c:if>
		
		$
				.ajax({
					processData : false,
					contentType : false,
					url : "${utcp.ctxPath}/user/edu/rceptProc.ajax",
					type : "post",
					data : formData,
					cache : false,
					async : true,
					success : function(r) {
						if (r.result == 1) {
							location.href = 'eduRceptResult.do?eduSeq='+ eduSeq+'&srchCtgry=${param.srchCtgry}';
						} else if(r.result == 2){
							location.href = '${utcp.ctxPath}/user/pay/easypay/order.do?eduSeq=${lctre.eduSeq}';
						}else {
							$("#messageStr").html(r.msg);
							location.href = "#message";
						}
					}
				});
	}
	
</script>
<style>
@media(max-width:768px) {
.navBot ul li {
width:50%; 
} 
}
</style>
<div id="rgsDiv">
	<div class="listWrap" style="padding-top: 0px;">
		<div class="box_list" style="margin-bottom: 0px;">
			<ul>
				<li class="flex rcept_li">
					<div class="edu_info_wrap">
						<span class="img_logo"> <c:choose>
								<c:when test='${lctre.imgUseYn == "Y" && lctre.imgRename != ""}'>
									<img src="<spring:eval expression="@prop['cloud.cdn.url']"/>/upload/web/lctreThum/${lctre.imgRename}" alt="교육이미지" width="112px" height="149px" />
								</c:when>
								<c:otherwise>
									<img src="${utcp.ctxPath}/resources/admin/images/default_img.png?1" alt="교육이미지" width="112px" height="149px" />
								</c:otherwise>
							</c:choose>
						</span>
						<div class="text_title">
							<div class="cp">
								<p class="eduTypeWrap"><span class="eduLabel"> 개인 | <c:if test="${lctre.fee > 0}">
											<b class="price pay">유료</b>
										</c:if> <c:if test="${lctre.fee == 0}">
											<b class="price free">무료</b>
										</c:if>
								</span> | <span class="eduType">${lctre.ctg1Nm}</span> | <span class="eduType1">[${lctre.lctreTypeNm }]</span></p>
								<p class="eduSbj"><strong>${lctre.eduNm}</strong> <span>${lctre.addStatusBox }</span></p>
								<p class="eduStnt">대상 : ${lctre.addTargetsStr }<span class="Stnt">
								</span></p>
							</div>
						</div>
					</div>
					<div class="text_info">
						<div>
							<div class="text_info_wrap">
								<span class="edudtType edudtType1"><span class="edutxt_tit">접수</span> <b class=""> <c:if test="${lctre.rceptPeriodYn=='Y'}"> 
							${utcp.convDateToStr(utcp.convStrToDate(lctre.rceptPeriodBegin,'yyyyMMddHHmm'),'yyyy-MM-dd HH:mm')} ~ 
							${utcp.convDateToStr(utcp.convStrToDate(lctre.rceptPeriodEnd,'yyyyMMddHHmm'),'yyyy-MM-dd HH:mm')} 
							(<b class="dday">${utcp.calcDDay(utcp.convStrToDate(lctre.rceptPeriodEnd,'yyyyMMddHHmm'))}</b>)
							</c:if> <c:if test="${lctre.rceptPeriodYn == 'N' }"> 미설정 </c:if>
								</b>
								</span> <span class="edudtType edudtType2"><span class="edutxt_tit">교육</span> <b class=""> 
								${lctre.eduPeriodBegin} ~ 
								${lctre.eduPeriodEnd} </b>
								</span> <span class="edudtType edudtType3"><span class="edutxt_tit">인원</span> <b><c:if test="${lctre.personnel==0}">
							무제한
							</c:if> <c:if test="${lctre.personnel>0}">
										<c:if test="${o.minPersonnel>0}">
							${lctre.minPersonnel} ~
							</c:if>
							${lctre.personnel}명 모집 
							</c:if> 
							<c:if test="${lctre.rceptCntViewYn eq 'Y' }">
							(<strong class="color"><fmt:formatNumber value="${lctre.rceptCnt}" pattern="#,##0" /></strong>명 신청)
							</c:if>
								</b></span>
							</div>
						</div>
					</div>
				</li>
			</ul>
		</div>
		<div class="eduRcept">
			<div class="sub_txt2">
				<span class=""><img src="${utcp.ctxPath }/resources/user/image/icon/icon_subtitle.png" alt="">신청자 정보</span>
			</div>
			<form action="#" method="post" name="formRcept">
				<input type="hidden" name="etc"/>
				<table class="tb03">
					<tr>
						<th>이름</th>
						<td>${user.userNm}</td>
					</tr>
					<tr>
						<th scope="row">휴대폰번호</th>
						<td>
						${user.decMobile }
						</td>
					</tr>
					<tr>
						<th scope="row">이메일</th>
						<td>
						${user.decEmail }
						</td>
					</tr>
					<!-- <tr>
						<th scope="row">숙소이용선택</th>
						<td>
						<input type="radio" name="dormiCapaCnt" value="0" id="dormiCapaCnt1"/>
						<label for="dormiCapaCnt1">미이용</label>
						<input type="radio" name="dormiCapaCnt" value="2" id="dormiCapaCnt2"/>
						<label for="dormiCapaCnt2">2인실</label>
						<input type="radio" name="dormiCapaCnt" value="4" id="dormiCapaCnt3"/>
						<label for="dormiCapaCnt3">4인실</label>
						(<input type="checkbox" id="dormiAccess"/> <label for="dormiAccess">장애인용</label>)
						<br>(* 숙소 이용 시 숙박비는 1만원~4만원)
						</td>
					</tr> -->
					<tr>
						<th scope="row">버스이용</th>
						<td>
						<input type="radio" name="useTrans" value="0" id="useTrans1" />
						<label for="useTrans1">미이용</label>
						<input type="radio" name="useTrans" value="1" id="useTrans2"/>
						<label for="useTrans2">왕복</label>
						<input type="radio" name="useTrans" value="2" id="useTrans3"/>
						<label for="useTrans3">입교</label>
						<input type="radio" name="useTrans" value="3" id="useTrans4"/>
						<label for="useTrans4">퇴교</label>
						</td>
					</tr>
					
					<c:if test="${lctre.fee > 0 }">
					<%--
					<tr>
						<th>결제방법 <span class="red">*</span></th>
						<td>
							<c:forEach items="<%=VarComponent.FEE_TP %>" var="o" varStatus="s">
									<c:if test="${s.index!=0}">
									<input type="radio" name="feeTp" id="feeTp${s.index }" value="${s.index }" /> 
									<label for="feeTp${s.index }">${o }</label>&nbsp;
									</c:if>
								</c:forEach>
								<br/>※KTL 직원은 현장결제 선택
						</td>
					</tr>
					 --%>
					</c:if>
					
					<c:if test="${lctre.etcIemYn eq 'Y'}">
						<c:forEach items="${etcList }" var="o">
							<tr>
								<th>${o.etcIemNm }
								<c:if test="${o.essntlInputYn eq 'Y' }">
								<span class="red">*</span>
								</c:if>
								</th>
								<td><c:choose>
										<c:when test="${o.dataInputTy eq 'text' }">
											<input type="text" name="etcIemSeq_${o.etcIemSeq }" id="etcIemSeq_${o.etcIemSeq }_0" />
										</c:when>
										<c:when test="${o.dataInputTy eq 'radio' }">
											<c:forEach items="${fn:split(o.etcIemEx,',') }" var="o2" varStatus="s2">
												<input type="radio" id="etcIemSeq_${o.etcIemSeq }_${s2.index}" name="etcIemSeq_${o.etcIemSeq }" value="${o2 }" />
												<label for="etcIemSeq_${o.etcIemSeq }_${s2.index}">${o2 }</label>
											</c:forEach>
										</c:when>
										<c:when test="${o.dataInputTy eq 'checkbox' }">
											<c:forEach items="${fn:split(o.etcIemEx,',') }" var="o2" varStatus="s2">
												<input type="checkbox" id="etcIemSeq_${o.etcIemSeq }_${s2.index}" name="etcIemSeq_${o.etcIemSeq }" value="${o2 }" />
												<label for="etcIemSeq_${o.etcIemSeq }_${s2.index}">${o2 }</label>
											</c:forEach>
										</c:when>
										<c:when test="${o.dataInputTy eq 'select' }">
											<select id="etcIemSeq_${o.etcIemSeq }_0" name="etcIemSeq_${o.etcIemSeq }">
												<c:forEach items="${fn:split(o.etcIemEx,',') }" var="o2" varStatus="s2">
													<option value="${o2 }">${o2 }</option>
												</c:forEach>
											</select>
										</c:when>
									</c:choose></td>
							</tr>
						</c:forEach>
					</c:if>
					
					<tr style="display: none;">
						<th>첨부파일</th>
						<td colspan="3"><input type="file" id="fileRcept" /></td>
					</tr>

				</table>
				<c:if test="${lctre.etcAgreeYn eq 'Y'}">
					<section id="etcAgree">
						<h3 class="h3label">개인정보 약관 동의</h3>
						<div class="Terms">
							<h4 class="fc_blue">
								<i class="fa-solid fa-square-caret-right fc_gray mgr5"></i>개인정보 수집 및 이용 동의
							</h4>
							<p>국립과천과학관은 홈페이지 회원관리 및 예약 내역 처리를 목적으로 다음과 같이 개인정보를 수집 및 처리합니다.</p>
							<ul>
								<li>
									<i class="fa-solid fa-caret-right mgr5 fc_gray"></i>수집 및 이용 목적 : 프로그램 운영, 정보 안내, 확인서 발급 등
								</li>
								<li>
									<i class="fa-solid fa-caret-right mgr5 fc_gray"></i>개인정보 항목 : 이름, 생년월일, 휴대전화번호, 전자우편(이메일), 교육 등 행사 활동사진 및 동영상 촬영 결과물 등
								</li>
								<li>
									<i class="fa-solid fa-caret-right mgr5 fc_gray"></i>이용 및 관리 기간 : 홈페이지 탈퇴 시까지
								</li>
							</ul>
							<p>귀하는 개인정보 수집 및 이용 동의 거부 권리가 있으며, 동의하지 않을 경우 전시/행사/교육 프로그램에 예약 진행에 제한이 있을 수 있습니다.</p>
						</div>

						<div>
							<p class="fs_14 tl mgt15">개인정보 수집 및 이용약관에 동의하십니까?</p>
							<div class="term_ck tl mgb20">
								<label for="agree3-1" class="mgr10">
									<input type="radio" id="agree3-1" name="agree3" value="Y">
									동의합니다.
								</label>
								<label for="agree3-2">
									<input type="radio" id="agree3-2" name="agree3" value="N" checked="checked" title="개인정보 수집,이용 동의 약관에 동의하세요.">
									동의하지 않습니다.
								</label>
							</div>
						</div>

					</section>
				</c:if>
				
			</form>
			
		</div>
		
		<div class="tb_btn">
			<ul>
				<li class="left cancelBtn"><a href="javascript:;" onclick="fn_moveView(); return false;">취소</a></li>
				<c:choose>
					<c:when test="${empty rcept }">
						<c:if test="${lctre.checkRcept == 2 }">
							<li class="right"><a href="javascript:;" onclick="fn_rcept(); return false;">교육신청</a></li>
						</c:if>
					</c:when>
				</c:choose>
			</ul>
		</div>
	</div>
</div>

<div class="remodal messagePop messagePop1" data-remodal-id="auth" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc">
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC">알림</p>
		</div>
		<div class="modal-body">
			<p class="messageTxt">로그인이 필요한 서비스입니다.<br />로그인 하시겠습니까?
			</p>
		</div>
		<div class="modal-footer">
			<div class="tc">
				<button onclick="fn_moveLogin(); return false;" class="remodal-confirm btn02 btn_green">확인</button>
				<button data-remodal-action="cancel" class="remodal-cancel btn02 btn_gray">취소</button>
			</div>
		</div>
	</div>
</div>

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