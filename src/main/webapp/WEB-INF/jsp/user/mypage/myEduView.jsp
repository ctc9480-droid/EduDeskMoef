<%@page import="com.educare.edu.member.service.SessionUserInfoHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<div class="listWrap pdt0" style="padding-top: 0">
	<div class="box_list mgb0 bNo">
		<ul id="lctreView">
			<li class="flex eduDetail">
				<div class="img_logo imgView">
					<c:choose>
						<c:when test="${lctre.imgUseYn eq 'Y' and not empty lctre.imgRename}">
							<img src="<spring:eval expression="@prop['cloud.cdn.url']"/>/upload/web/lctreThum/${lctre.imgRename}" alt="교육이미지" width="112px" height="149px" />
						</c:when>
						<c:otherwise>
							<img src="${utcp.ctxPath}/resources/admin/images/default_img.png" alt="교육이미지" width="112px" height="149px" />
						</c:otherwise>
					</c:choose>
				<div>
						<ul>
							<li>
								<span>교육 기관 : </span>
								<span>${lctre.orgNm }</span>							
							</li>
							<li>
								<span>문&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;의 : </span>
								<span>${lctre.tel }</span>							
							</li>
						</ul>
						<c:if test="${isAttach }">
						<a href="${utcp.ctxPath}/user/edu/attach/${lctre.eduSeq }/zipDownload.do" class="downBtn btn_blue fc_white">자료 다운 <i class="fas fa-download"></i> </a>
						</c:if>
					</div>
				</div>
				<div class="text_info">
					<ul>
						<li class="eduTit">
							<span><strong class="tit">[${lctre.eduCtgryNm}] ${lctre.eduNm}</strong></span>
							${lctre.addStatusBox }
							<c:if test="${not empty lctre.instrctrNm }">
							<%-- <p>${lctre.instrctrNm} 강사</p> --%>
							</c:if>
						</li>
						<li>
							<span>교&nbsp;&nbsp;육&nbsp;&nbsp;일 : </span>
							<span>
							<c:choose>
							<c:when test="${lctre.lctreType == 0 }">
							${utcp.getEduPeriodTxt(lctre.eduSeq) }
							</c:when>
							<c:otherwise>
							${lctre.eduPeriodBegin} ~ ${lctre.eduPeriodEnd}
							</c:otherwise>
							</c:choose>
								<%-- <a href="javascript:openTimeTable(${lctre.eduSeq});" class="btn_blue" >교육 상세</a> --%>
							</span>
						</li>						
						<li><span>접수 기간 : </span> <span> 
						<c:choose>
									<c:when test='${lctre.rceptPeriodYn == "Y"}'>
										<fmt:parseDate value="${lctre.rceptPeriodBegin}" var="rceptBegin" pattern="yyyyMMddHHmm" />
										<fmt:parseDate value="${lctre.rceptPeriodEnd}" var="rceptEnd" pattern="yyyyMMddHHmm" />
										<fmt:formatDate value="${rceptBegin}" pattern="yyyy-MM-dd HH:mm" /> ~ 
						<fmt:formatDate value="${rceptEnd}" pattern="yyyy-MM-dd HH:mm" />
									</c:when>
									<c:otherwise>
                		없음
                	</c:otherwise>
								</c:choose> 
								<c:set var="calcDDay" value="${utcp.calcDDay(utcp.convStrToDate(lctre.rceptPeriodEnd,'yyyyMMddHHmm')) }"/>
								<c:if test="${calcDDay ne '-' }">
								<strong class="fc_orange">[${calcDDay}]</strong>
								</c:if>
						</span></li>
						<li><span>교육 인원 : </span> <span> 
						<c:choose>
						<c:when test="${lctre.personnel eq '0' }">
						무제한
						</c:when>
						<c:otherwise>
						${lctre.personnel}명 모집
						</c:otherwise>
						</c:choose>
						 <%-- / ${lctre.extPersonnel }명 대기 --%> / <strong class="color">${lctre.rceptCnt}</strong>명 신청
						</span></li>
						<li>
							<span>비&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;용 : </span> 
							<span>
								<c:choose>
									<c:when test="${lctre.fee != 0 }">
										<c:if test="${lctre.groupFee != 0 }">개인 </c:if><fmt:formatNumber type="number" maxFractionDigits="3" value='${lctre.fee}' />원 
										<c:if test="${lctre.groupFee != 0 }">
											/ 단체  <fmt:formatNumber type="number" maxFractionDigits="3" value='${lctre.groupFee}' />원 
										</c:if>
										
									</c:when>
									<c:otherwise>
									무료
									</c:otherwise>
								</c:choose>
							</span>
						</li>
						<li><span>장&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;소 : </span> 
						<c:if test="${not empty lctre.addr}">
						<span>${lctre.addr} ${lctre.addrEtc} ${lctre.addrDetail} 
						<c:if test='${lctre.addrMemo != "" && lctre.addrMemo != null}'>(${lctre.addrMemo})</c:if></span> 
							<a href="javascript:openPopup(1);" class="btn_grayl"><i class="fas fa-map-marker-alt"></i>약도</a>
						</c:if>
						<c:if test="${lctre.lctreType eq 3 }">
						<span>온라인 
						</span>
						</c:if>
						</li>
					</ul>
					
					<a href="javascript:;" onclick="fn_cancel(); return false;" class="eduBtn btn01 bg_red fc_white fr mgr20 fw_500">교육취소</a>
				</div>
			</li>
		</ul>
	</div>
	
	<table class="tb03 EduDt">
	<caption>상세안내</caption>
		<tr>
			<td>${lctre.cn}</td>
		</tr>
		
	</table>
	<div class="tb_btn">
		<ul>
			<a href="javascript:;" onclick="fn_moveList(); return false;"><li class="left">목록</li></a>
			
		</ul>
	</div>

	<!-- //board_tab_onoff -->
</div>
<input type="hidden" name="eduSeq" id="edu_seq" value="${lctre.eduSeq }" />
<input type="hidden" name="eduSeq" id="edu_seq" value="${rcept.rceptSeq}" />

<!-- 신청취소 -->
<div class="remodal messagePop messagePop2" data-remodal-id="md-cancel-1" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc">
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC">교육신청취소</p>
		</div>
		<div class="modal-body">
			<p class="messageTxt">취소하시겠습니까?</p>
		</div>
		<div class="modal-footer">
			<div class="tc">
				<button onclick="fn_cancelProc(); return false;" class="remodal-confirm btn02 btn_orange">확인</button>
				<button data-remodal-action="cancel" class="remodal-cancel btn02 btn_gray">취소</button>
			</div>
		</div>
	</div>
</div>
<div class="remodal messagePop messagePop1" data-remodal-id="md-success" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc">
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC">알림</p>
		</div>
		<div class="modal-body">
			<p class="messageTxt">취소처리 되었습니다.</p>
		</div>
		<div class="modal-footer">
			<div class="tc">
				<button onclick="location.href='myEdu${gubun}List.do';" class="remodal-confirm btn02 btn_green">확인</button>
			</div>
		</div>
	</div>
</div>
<div class="remodal messagePop messagePop2" data-remodal-id="cancel-edu-message" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc">
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC">알림</p>
		</div>
		<div class="modal-body">
			<p class="messageTxt" id="messageStr"></p>
		</div>
		<div class="modal-footer">
			<div class="tc">
				<button onclick="location.href='#'" class="remodal-confirm btn02 btn_orange">확인</button>
			</div>
		</div>
	</div>
</div>
<script>
function fn_cancel(){
	location.href= "#md-cancel-1";
}
function fn_cancelProc(){
	var eduSeq = $("#edu_seq").val(); 
	var rceptSeq = $('#rcept_seq').val();
	$.ajax({
		url: "${utcp.ctxPath}/user/edu/cancelRcept.ajax",
		type: "post",
		data: {
			"eduSeq" : eduSeq,
			"rceptSeq" : rceptSeq
		},
		cache: false,
		async: true,
		success: function(r) {
			if(r.result == 1){
				if(r.result == 1){
					location.href = "#md-success";
				}else{
					$("#messageStr").html(r.msg);
					location.href = "#cancel-edu-message";
				}	
			}else{
				alert(r.msg);
			}
		}
	});
}
function fn_moveList() {
	location.href = "${utcp.ctxPath}/user/mypage/myEdu${gubun}List.do";
}
</script>