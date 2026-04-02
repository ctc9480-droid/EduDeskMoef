<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<%@ page import="com.educare.edu.member.service.SessionUserInfoHelper"%>
<%@ page import="com.educare.edu.member.service.model.UserInfo"%>
<%
	boolean isStdnt = false;
	isStdnt = SessionUserInfoHelper.isLogined()
			&& UserInfo.MEM_LVL_STDNT.equals(SessionUserInfoHelper.getUserMemLvl());
%>
<jsp:useBean id="curDate" class="java.util.Date" />
<script type="text/javascript">
	function fn_moveList() {
		location.href = "${utcp.ctxPath}/user/edu/eduList.do?srchCtgry=${srchCtgry}";
	}

	function fn_auth() {
		location.href = "#auth";
	}

	function fn_moveLogin() {
		location.href = "${utcp.ctxPath}/user/login.do";
	}

	function fn_moveRcept() {
		/* 같은 과정 수료했는지 체크, 임시 주석처리,230411 
		$.ajax({
			data:{eduSeq:'${lctre.eduSeq}'},
			url:'${utcp.ctxPath}/user/ajax/checkDupYearCtgryPass.json',
			success:function(r){
				if(r.result==2){
					if(!confirm(r.msg)){
						return false;
					}
				}
				location.href = "${utcp.ctxPath}/user/edu/eduRcept.do?eduSeq=${lctre.eduSeq}";
			}
		});
		 */
		location.href = "${utcp.ctxPath}/user/edu/eduRcept.do?eduSeq=${lctre.eduSeq}&srchCtgry=${srchCtgry}";
	}

	function fn_message() {
		location.href = "#message";
	}
	function openPopup(no) {
		if (no == 1) {
			window
					.open(
							'${utcp.ctxPath}/comm/popup/eduMap.do?eduSeq=${lctre.eduSeq}',
							'eduMap', 'width=422,height=655,left=100,top=50');
		}
	}
	function fn_saveLikeEdu(eduSeq, state) {
		$.ajax({
			type : 'post',
			url : '${utcp.ctxPath}/user/ajax/setLikeEdu.ajax',
			data : {
				eduSeq : eduSeq,
				state : state
			},
			success : function(r) {
				if (r.result == 1) {
					location.reload();
					return;
				}
				alert(r.msg);
			},
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
<div class="detailWrap cf">



	<div class="detailTit">
		<h3 class="eduTit">
			<span class="fc_blue"> [${lctre.ctg1Nm}] <span
				class="icon_tag etc">${lctre.lctreTypeNm }</span> <span
				class="icon_tag etc"> 
				<c:choose>
						<c:when test="${lctre.fee != 0 }">
										유료
									</c:when>
						<c:otherwise> 
									무료
									</c:otherwise>
					</c:choose>
			</span>
			</span> <span class="dp_b"><strong class="tit">${lctre.eduNm }</strong></span>
		</h3>
	</div>
	<div class="detailInfoWrap">
		<div class="detailInfo">
			<div class="dtInfoTxt">
				<div class="text_info">
					<ul>
						<li class="full"><span class="dt">신청기간</span> <span class="dd"> <c:choose>
									<c:when test='${lctre.rceptPeriodYn == "Y"}'>
									${utcp.convDateToStr(utcp.convStrToDate(lctre.rceptPeriodBegin,'yyyyMMddHHmm'),'yyyy-MM-dd HH:mm')} ~
									${utcp.convDateToStr(utcp.convStrToDate(lctre.rceptPeriodEnd,'yyyyMMddHHmm'),'yyyy-MM-dd HH:mm')}
									<c:set var="calcDDay"
											value="${utcp.calcDDay(utcp.convStrToDate(lctre.rceptPeriodEnd,'yyyyMMddHHmm')) }" />
										<c:if test="${calcDDay ne '-' }">
											<%-- <strong class="fc_orange">[${calcDDay}]</strong> --%>
										</c:if>
									</c:when>
									<c:otherwise>
	                				없음
	                				</c:otherwise>
								</c:choose> ${lctre.addStatusBox }
						</span></li>
						<li class="full"><span class="dt">교육기간 </span> <span class="dd">
								${lctre.eduPeriodBegin} <c:if
									test="${lctre.eduPeriodBegin != lctre.eduPeriodEnd }">
							~ ${lctre.eduPeriodEnd} 
							</c:if> 
						</span></li>
						<li><span class="dt">기&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;수
						</span><span class="dd">제${lctre.eduTerm }기</span></li>
						<fmt:formatNumber value="${lctre.compStnd01}" pattern="0000" var="compStnd01" />
						<c:if test="${not empty compStnd01 }">
							<li><span class="dt">교육시간 </span> <span class="dd">${fn:substring(compStnd01, 0, 2) }:${not empty compStnd01 and fn:length(compStnd01) >= 4 ? fn:substring(compStnd01, 2, 4) : '00'}</span>
							</li>
						</c:if>
						<li><span class="dt">교육방법 </span> <span class="dd">${lctre.lctreTypeNm }</span>
						</li>
						<li><span class="dt">참가 대상 </span> <span class="dd">${lctre.addTargetsStr }
								<c:if test="${lctre.eduTp > 0  }">
										 (${lctre.addEduTpNm })
										</c:if>
						</span></li>
						<li><span class="dt">모집인원 </span> <span class="dd"> <c:choose>
									<c:when test="${lctre.personnel eq '0' }">
							무제한
							</c:when>
									<c:otherwise>
										<fmt:formatNumber value="${lctre.personnel}" pattern="#,##0" />명 모집
							</c:otherwise>
								</c:choose>
						</span></li>
						<%--
						<li class="full"><span class="dt">비&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;용
						</span> <span class="dd"> <c:choose>
									<c:when test="${lctre.fee != 0 }">
										<c:if test="${lctre.groupFee != 0 }">개인 </c:if>
										<fmt:formatNumber type="number" maxFractionDigits="3"
											value='${lctre.fee}' />원 
											<c:if test="${lctre.groupFee != 0 }">
												/ 단체  <fmt:formatNumber type="number" maxFractionDigits="3"
												value='${lctre.groupFee}' />원 
											</c:if>

									</c:when>
									<c:otherwise>
										무료
										</c:otherwise>
								</c:choose>
						</span></li>
						 --%>
						
						<c:if test="${not empty lctre.addr}">
							<li class="full"><span class="dt">장&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;소
							</span> <span class="dd"> ${lctre.addr} ${lctre.addrDetail} <c:if
										test='${lctre.addrEtc != "" && lctre.addrEtc != null}'>(${lctre.addrEtc})</c:if>
<!-- 									<a href="javascript:openPopup(1);" class="btn04 btn_grayl"><i -->
<!-- 										class="fas fa-map-marker-alt pdr3"></i>약도</a> -->
							</span></li>
						</c:if>
<%-- 						<c:if test="${not empty lctre.eduEtc }"> --%>
<!-- 							<li class="full"><span class="dt">유의사항</span> <span class="dd"> -->
<%-- 									${utcp.convNewLine(lctre.eduEtc) } </span></li> --%>
<%-- 						</c:if> --%>
					</ul>
				</div>
				<div class="btn_info cf edu-btn-info">
					<%-- <c:if test="${checkLclike == 1 }">
						<a href="javascript:fn_saveLikeEdu(${lctre.eduSeq },2)" class="eduBtn btn01 bg_red fc_white fl fw_500">찜</a>
					</c:if> --%>
					<%-- <c:if test="${checkLclike != 1 }">
						<a href="javascript:fn_saveLikeEdu(${lctre.eduSeq },1)" class="eduBtn btn01 bg_gray fc_white fl mgr20 fw_500">찜하기</a>
					</c:if> --%>


					<c:choose>
						<c:when test="${empty rcept or (rcept.state == 3)}">
							<c:if test="${lctre.checkRcept == 2}">
								<a href="javascript:;" onclick="fn_moveRcept(); return false;"
									class="eduBtn btn01 fc_white fw_500">교육신청</a>
							</c:if>
						</c:when>
						<c:when test="${rcept.state == 1}">
							<a href="#none" class="eduBtn btn01 bg_darkgray fc_white fr fw_500">교육신청완료</a>
						</c:when>
						<c:when test="${rcept.state == 2}">
							<a href="#none" class="eduBtn btn01 bg_darkgray fc_white fr fw_500">교육신청완료</a>
						</c:when>
					</c:choose>
				</div>
				<div>
					<ul class="mgb10">
						<li><span>문&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;의
								: <c:if test="${lctre.inqNm ne null and lctre.inqNm ne ''}"> (${lctre.inqNm})</c:if>
						</span> <span>${lctre.tel}</span></li>
					</ul>
					<c:if test="${isAttach }">
						<a
							href="${utcp.ctxPath}/user/edu/attach/${lctre.eduSeq }/zipDownload.do"
							class="downBtn btn_blue fc_white btn04">자료 다운 <i
							class="fas fa-download"></i>
						</a>
					</c:if>
				</div>
			</div>
		</div>
		<div class="dtInfoThum">
			<c:choose>
				<c:when test='${lctre.imgUseYn == "Y" && lctre.imgRename != ""}'>
					<img
						src="<spring:eval expression="@prop['cloud.cdn.url']"/>/upload/web/lctreThum/${lctre.imgRename}?${utcp.convDateToStr(lctre.updDe,'yyyyMMddHHmmss')}"
						alt="교육이미지" />
				</c:when>
				<c:otherwise>
					<img width="400" height="250"
						src="${utcp.ctxPath}/resources/admin/images/default_img.png?${utcp.convDateToStr(lctre.updDe,'yyyyMMddHHmmss')}"
						alt="교육이미지" />
				</c:otherwise>
			</c:choose>
		</div>

	</div>

	<div class="detailCon">
		<div class="detailTabWrap">
			<div class="eduDt">
				<!--// board_tab_onoff //-->
				<div class="board_tab_onoff">
					<!--// board_tab //-->
					<ul class="board_tab">
						<li id="tab1" class="active">
							<p>
								<a href="javascript:;">과정소개</a>
							</p>
						</li>
						<li id="tab2" class="">
							<p>
								<a href="javascript:;">커리큘럼</a>
							</p>
						</li>
					</ul>
					<!--// board_tab //-->

					<!--// board_tab_con //-->
					<div class="board_tab_con">

						<!--// tab_con1 //-->
						<div class="cont" id="tabCon1">
							<div class="sub_txt">
								<span class="board-tab-tit"><img
									src="${utcp.ctxPath}/resources/user/image/icon/icon_subtitle.png"
									alt="">교육내용</span>
							</div>
							${lctre.cn}
						</div>
						<!--// tab_con1 //-->
						<div class="cont" id="tabCon2">
							<div class="sub_txt">
								<span class="board-tab-tit"><img
									src="${utcp.ctxPath}/resources/user/image/icon/icon_subtitle.png"
									alt="">수료기준</span>
							</div>
								<table class="w100 tb06 tc">
									<caption class="sound_only">수료기준</caption>
									<!-- <colgroup>
										<col style="width: 4%;">
										<col style="">
										<col style="width: 12%;">
										<col style="">
										<col style="width: 8%;">
										<col style="width: 8%;">
										<col style="width: 10%;">
									</colgroup> -->
									<thead>
										<tr>
											<th scope="col">총 교육시간</th>
											<th scope="col">최소 이수시간</th>
											<th scope="col">시험</th>
											<th scope="col">과제</th>
											<th scope="col">설문</th>
										</tr>
									</thead>
									<tbody>
										<fmt:formatNumber value="${lctre.compStnd01}" pattern="0000" var="compStnd01" />
										<fmt:formatNumber value="${lctre.compStnd02}" pattern="0000" var="compStnd02" />
										<tr>
											<td class="tc">${fn:substring(compStnd01, 0, 2) }:${not empty compStnd01 and fn:length(compStnd01) >= 4 ? fn:substring(compStnd01, 2, 4) : '00'}</td>
											<td class="tc">${fn:substring(compStnd02, 0, 2) }:${not empty compStnd02 and fn:length(compStnd02) >= 4 ? fn:substring(compStnd02, 2, 4) : '00'}</td>
											<td class="tc">${lctre.compStnd03 }</td>
											<td class="tc">${lctre.compStnd04 }</td>
											<td class="tc">${lctre.compStnd05 }</td>
										</tr>
									</tbody>
								</table>
						</div>
						<!--// tab_con3 //-->
						<div class="cont" id="tabCon3">
							<div class="sub_txt">
								<span class="board-tab-tit"><img
									src="${utcp.ctxPath}/resources/user/image/icon/icon_subtitle.png"
									alt="">커리큘럼</span>
							</div>
							
							<div class="tabcon_wrap">
								<table class="w100 tb06 tc">
									<caption class="sound_only">수업시간표 - 교시, 날짜, 시간, 내용,
										강사, 형태, 강의실 순으로 구성된 표</caption>
									<colgroup>
										<col style="width: 4%;"> 
										<col style="">
										<col style="width: 12%;">
										<col style="">
										<col style="width: 8%;">
										<col style="width: 8%;">
										<col style="width: 10%;">
									</colgroup>
									<thead>
										<tr>
											<th scope="col">교시</th>
											<th scope="col">날짜</th>
											<th scope="col">시간</th>
											<th scope="col">내용</th>
											<th scope="col">강사</th>
											<th scope="col">형태</th>
											<th scope="col">강의실</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${timeList }" var="o" varStatus="s">
											<tr>
												<td class="tc">${s.count }</td>
												<td class="tc">${utcp.convDateToStr(utcp.convStrToDate(o.eduDt,'yyyyMMdd'),'yyyy-MM-dd (E)') }</td>
												<td class="tc">${utcp.convDateToStr(utcp.convStrToDate(o.startTm,'HHmm'),'HH:mm') }~${utcp.convDateToStr(utcp.convStrToDate(o.endTm,'HHmm'),'HH:mm') }</td>
												<td class="tl pdl30">${o.description }</td>
												<td class="tc">${o.instrNm }</td>
												<td class="tc">${o.classHowNm }</td>
												<td class="tc">${o.roomNm }</td>
											</tr>
										</c:forEach>
										<c:if test="${empty timeList }">
											<tr>
												<td colspan="6" class="none">시간표가 없습니다.</td>
											</tr>
										</c:if>
									</tbody>
								</table>
							</div>
						</div>
						<!--// tab_con3 //-->
						<c:if test="${not empty lctre.wayCome }">
						<div class="cont" id="tabCon4">
							<div class="sub_txt">
								<span class="board-tab-tit"><img
									src="${utcp.ctxPath}/resources/user/image/icon/icon_subtitle.png"
									alt="">오시는 길</span>
							</div>
							${utcp.convNewLine(lctre.wayCome) }
						</div>
						</c:if>
						<c:if test="${not empty lctre.eduEtc }">
						<div class="cont" id="tabCon5">
							<div class="sub_txt">
								<span class="board-tab-tit"><img
									src="${utcp.ctxPath}/resources/user/image/icon/icon_subtitle.png"
									alt="">안내사항</span>
							</div>
							${utcp.convNewLine(lctre.eduEtc) }
						</div>
						</c:if>
					</div>
					<!--// board_tab_con //-->
				</div>
				<!--// board_tab_onoff //-->
			</div>
		</div>


	</div>


</div>

<div class="listWrap pdt0">
	<div class="tb_btn">
		<ul>
			<li class="left"><a href="javascript:;"
				onclick="fn_moveList(); return false;" class="fc_white">목록</a></li>
		</ul>
	</div>
</div>

<div class="remodal messagePop messagePop1" data-remodal-id="auth"
	role="dialog" aria-labelledby="modal1Title"
	aria-describedby="modal1Desc">
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC">알림</p>
		</div>
		<div class="modal-body">
			<p class="messageTxt">
				로그인이 필요한 서비스입니다.<br />로그인 하시겠습니까?
			</p>
		</div>
		<div class="modal-footer">
			<div class="tc">
				<button onclick="fn_moveLogin(); return false;"
					class="remodal-confirm btn02 btn_green">확인</button>
				<button data-remodal-action="cancel"
					class="remodal-cancel btn02 btn_gray">취소</button>
			</div>
		</div>
	</div>
</div>

<div class="remodal messagePop messagePop2" data-remodal-id="message"
	role="dialog" aria-labelledby="modal1Title"
	aria-describedby="modal1Desc">
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC">알림</p>
		</div>
		<div class="modal-body">
			<p class="messageTxt" id="messageStr">사용자 계정으로 로그인 후 이용하세요</p>
		</div>
		<div class="modal-footer">
			<div class="tc">
				<button data-remodal-action="cancel"
					class="remodal-confirm btn02 btn_orange">확인</button>
			</div>
		</div>
	</div>
</div>

