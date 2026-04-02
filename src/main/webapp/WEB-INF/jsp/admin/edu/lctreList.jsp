<%@page import="com.educare.component.VarComponent"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp" %>
<jsp:useBean id="curDate" class="java.util.Date" />
<script>
$(function () {
	$("#srchCtgry ,#srchCtgry2, #srchCtgry3, #eduYear, #eduTerm").change(function(){
		if(this.id == 'srchCtgry'){
			$('#srchCtgry2').val(0);
			$('#srchCtgry3').val(0);
		}
		if(this.id == 'srchCtgry2'){
			$('#srchCtgry3').val(0);
		}
		$("form[name='searchFrm']").submit();
	});
	
	$('#allChk').click(function(o){
		if(this.checked){
			$('input[name=eduSeqChk]').prop('checked',true);
		}else{
			$('input[name=eduSeqChk]').prop('checked',false);
		}
	});
});

function fn_moveRgs(){
	//location.href = "/admin/edu/lctreRgs.do";
	window.open('${utcp.ctxPath}/admin/edu/popup/lctreRgs.do','lctrePop','scrollbar=n,width=1324,height=840');
}

function fn_paging(page){
	$("#page").val(page);
	$("form[name='searchFrm']").submit();
}
function fn_openYnAll(openYn){
	if(!$('input[name=eduSeqChk]:checked').length>0){
		alert('교육을 선택하세요');
		return;
	}
	
	var openYnNm = '비공개';
	if(openYn == 'Y'){
		openYnNm = '공개';
	}
	if(!confirm('선택하신 교육을 '+openYnNm+'로 변경하시겠습니까?')){
		return;
	}
	var eduSeqArr = [];
	$('input[name=eduSeqChk]').each(function(){
		if(this.checked){
			eduSeqArr.push(this.value);
		}
	});
	console.log(eduSeqArr);
	$.ajax({
		type : 'post',
		url : '${utcp.ctxPath}/admin/ajax/saveOpenYnAll.ajax',
		data : {
			openYn : openYn,
			jsonData : JSON.stringify(eduSeqArr),
		},
		success : function (r){
			if(r.result == 1){
				location.reload();
			}
		}
	});
}

</script>
<section class="pd025 pd200 po_re">
	<div class="po_re br5 bs_box">
		<div class="listWrap listWrapTop listWrapBox">
			<form name="searchFrm" method="get" action="lctreList.do">
				<div class="box_sort flex">
					<input type="hidden" name="page" id="page" value="${vo.page}">
					<div class="box_cate">
						<%-- 
						<select id="eduYear" name="eduYear" class="srchCtgry wauto mgl10">
							<fmt:formatDate value="<%=new java.util.Date()%>" var="curYear" pattern="yyyy" />
							<fmt:parseNumber value="${curYear}" var="year" />
							<option value="">연도선택</option>
							<c:forEach begin="2023" end="${year+1 }" varStatus="stat">
								<c:set value="${year+2-stat.count  }" var="choiceYear" />
								<option value="${choiceYear}" <c:if test="${choiceYear == vo.eduYear}">selected</c:if>>${choiceYear}년</option>
							</c:forEach>
						</select>
						<select id="eduTerm" name="eduTerm" class="srchCtgry wauto">
							<c:forEach items="<%=VarComponent.EDU_TERM %>" var="o" varStatus="s">
								<c:if test="${s.index==0 }">
								<c:set var="eduTermNm" value="학기선택"/>
								</c:if>
								<c:if test="${s.index!=0 }">
								<c:set var="eduTermNm" value="${o }"/>
								</c:if>
								<option value="${s.index }" ${vo.eduTerm == s.index?'selected':'' }>${eduTermNm }</option>
							</c:forEach>
						</select>
						 --%>
						<label for="CtgryType" class="sound_only">1교육과정 선택</label>
						<select id="srchCtgry" name="srchCtgry" class="srchCtgry wauto" title="교육과정을 선택 하실 수 있습니다.">
							<option value="0">1차과정 전체</option>
							<c:forEach var="data" items="${cateList}" varStatus="stat">
								<option value="${data.ctgrySeq}" <c:if test='${vo.srchCtgry == data.ctgrySeq}'>selected</c:if>>${data.ctgryNm}</option>
							</c:forEach>
						</select> 
						<label for="CtgryType" class="sound_only">2교육과정 선택</label>
						<select id="srchCtgry2" name="srchCtgry2" class="srchCtgry wauto" title="교육과정을 선택 하실 수 있습니다.">
							<option value="0">2차과정 전체</option>
							<c:forEach var="data" items="${cateList2}" varStatus="stat">
								<option value="${data.ctgrySeq}" <c:if test='${vo.srchCtgry2 == data.ctgrySeq}'>selected</c:if>>${data.ctgryNm}</option>
							</c:forEach>
						</select> 
						<label for="CtgryType" class="sound_only">3교육과정 선택</label>
						<select id="srchCtgry3" name="srchCtgry3" class="srchCtgry wauto" title="교육과정을 선택 하실 수 있습니다.">
							<option value="0">3차과정 전체</option>
							<c:forEach var="data" items="${cateList3}" varStatus="stat">
								<option value="${data.ctgrySeq}" <c:if test='${vo.srchCtgry3 == data.ctgrySeq}'>selected</c:if>>${data.ctgryNm}</option>
							</c:forEach>
						</select> 
						
					</div>
					<div class="box_search">
						<%-- <label for="srchSelect" class="sound_only">교육명,기관명 선택</label>
						<select id="srchSelect" name="srchColumn" class="srchColumn" title="교육명과 기관명 중 선택 하실 수 있습니다.">
							<option value="eduNm" <c:if test='${vo.srchColumn == "eduNm"}'>selected</c:if>>교육명</option>
							<option value="orgNm" <c:if test='${vo.srchColumn == "orgNm"}'>selected</c:if>>기관명</option>
						</select> --%>
						<input type="text" id="srchWrd" name="srchWrd" class="" value="${param.srchWrd }"/>
						<button type="button" onclick="fn_paging(1)">검색</button>
					</div>
				</div>
			</form>
		</div>
		<!-- 
		<div class="sortWrap cf">
			<ul>
				<li>
					<a href="#">가나다순</a><span>|</span>
				</li>
				<li>
					<a href="#">교육일순</a><span>|</span>
				</li>
				<li>
					<a href="#">접수마감일순</a>
				</li>
			</ul>
		</div>
		 -->
		<table width="100%" class="tb03 tc bt0">	
			<thead>
				<tr>
					<th colspan="4" class="tl pd10">
						<label for="allChk"><input type="checkbox" name="" id="allChk" value="" class="mgr5" style="transform : scale(1.5);">전체선택</label>
					</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test='${dataList != null && fn:length(dataList) > 0}'>
	                	<c:forEach var="lctre" items="${dataList}" varStatus="stat">
	                		<tr>
								<td class="tl vt">
								<div class="listCkb"><input type="checkbox" name="eduSeqChk" value="${lctre.eduSeq}" style="transform : scale(1.5);"></div>
								</td>
								<td class="tl w10">
									<c:choose>
										<c:when test='${lctre.imgUseYn == "Y" && lctre.imgRename != ""}'>
											<img src="<spring:eval expression="@prop['cloud.cdn.url']"/>/upload/web/lctreThum/${lctre.imgRename}?${utcp.convDateToStr(lctre.updDe,'yyyyMMddHHmmss')}" alt="교육 썸네일" />
										</c:when>
					                	<c:otherwise>
					                		<img src="${utcp.ctxPath}/resources/admin/images/default_img.png?${utcp.convDateToStr(lctre.updDe,'yyyyMMddHHmmss')}" alt="<spring:eval expression="@prop['service.name']"/>" />
					                	</c:otherwise>
					                </c:choose>
								</td>
								<td class="tl">
                                    <div class="dp_b">
                                        <span class="dp_ib">
                                        [
                                        <c:if test="${lctre.fee > 0 }">
                                        <b class="price pay">유료</b>
                                        </c:if>
                                        <c:if test="${lctre.fee == 0 }">
                                        <b class="price free">무료</b>
                                        </c:if>
                                        </span>
                                        |
                                        <span class="eduType">
                                        ${lctre.ctg1Nm}
                                        > ${lctre.ctg2Nm}
                                        <c:if test="${not empty lctre.ctg3Nm }">
                                        > ${lctre.ctg3Nm }
                                        </c:if>
                                        </span>
                                        |
                                        <b class="price free">${lctre.lctreTypeNm }</b>
                                        ]
                                        <span class="dp_ib eduType1">[${lctre.addEduTpNm }]</span>
                                        <!--//개인//-->
                                        <c:if test="${lctre.openYn eq 'Y' }">
                                        <button type="button" class="btn04 btn_bluel">공개</button>
                                        </c:if>
                                        <c:if test="${lctre.openYn eq 'N' }">
                                        <button type="button" class="btn04 btn_greenl">비공개</button>
                                        </c:if>
                                        <!--//개인//-->
                                    </div>
                                    <%--230608hy 
									<span class="dp_b">${lctre.onlineYn eq 'Y'?'[온라인]':'' } 
									${lctre.eduCtgryNm }
									<c:if test="${not empty lctre.detailCtgryNm }">
									> ${lctre.detailCtgryNm}
									</c:if>
									</span>
									--%>
									<%-- 
									<a href="${utcp.ctxPath}/admin/edu/lctreView.do?eduSeq=${lctre.eduSeq}">
										<span class="dp_ib font_22 fw_500 pdr15">${lctre.eduNm}</span>
									</a>
									 --%>

                                    <p class="eduSbj">
										<a href="javascript:window.open('${utcp.ctxPath}/admin/edu/popup/lctreView.do?eduSeq=${lctre.eduSeq}','lctrePop','scrollbar=n,width=1477,height=980');">
											<span class="dp_ib font_22 fw_500 pdr15">${lctre.eduNm} 제${lctre.eduTerm }기</span>
										</a>
										${lctre.addStatusBox }
                                    </p>
                                    <p class="eduStnt">대상 : 
                                    <span class="Stnt">
                                    	${lctre.addTargetsStr }
                                    </span></p>
									<%--230608 ${lctre.addStatusBox }
									<c:choose>
										<c:when test='${lctre.openYn == "Y"}'>
											<span class="exp">노출</span>
										</c:when>
					                	<c:otherwise>
					                		<span class="expNo">미노출</span>
					                	</c:otherwise>
					                </c:choose>	
					                 --%>	                
									<%-- <span class="dp_b font_14">${lctre.instrctrNm} 강사</span> --%>
								</td>
								<td class="tl">
									<span class="dp_b font_14">[접수]
										<c:choose>
										<c:when test="${lctre.rceptPeriodYn eq 'Y' }">
										 ${utcp.convDateToStr(utcp.convStrToDate(lctre.rceptPeriodBegin,'yyyyMMddHHmm'),'yyyy-MM-dd')} ~
										 ${utcp.convDateToStr(utcp.convStrToDate(lctre.rceptPeriodEnd,'yyyyMMddHHmm'),'yyyy-MM-dd')}
										
										 <c:set var="calcDDay" value="${utcp.calcDDay(utcp.convStrToDate(lctre.rceptPeriodEnd,'yyyyMMddHHmm')) }"/>
										<c:if test="${calcDDay ne '-' }">
										  (<strong class="fc_red fw_400">${calcDDay }</strong>)
										 </c:if>
										</c:when>
										<c:otherwise>
										미설정
										</c:otherwise>
										</c:choose>
									</span>
									<span class="dp_b font_14">[교육] ${lctre.eduPeriodBegin } ~ ${lctre.eduPeriodEnd }</span>
									<%-- 230608hy 
									<span class="dp_b font_14">
										<c:choose>
						<c:when test="${lctre.personnel eq '0' }">
						무제한
						</c:when>
						<c:otherwise>
						<fmt:formatNumber value="${lctre.personnel}" pattern="#,##0"/>명 모집
						</c:otherwise>
						</c:choose>
										(<strong class="fc_red fw_400"><fmt:formatNumber value="${lctre.rceptCnt + lctre.waitCnt}" pattern="#,##0"/></strong>명 신청)
									</span>
									--%>
                                    <span class="dp_b font_14">[인원]
                                        ${lctre.minPersonnel }~${lctre.personnel }명 모집 
                                        (<strong class="fc_red fw_400"><fmt:formatNumber value="${lctre.rceptCnt + lctre.waitCnt}" pattern="#,##0"/></strong>명 신청)
                                        <!-- (매주<strong>수,목</strong>) -->
                                        <%--
                                        <button type="button" class="btn04 btn_blue mgl5 roomPopBtn">입장하기</button>
                                        <!--//단체//-->
                                        <button type="button" class="btn04 btn_purple mgl5 roomPopBtn">신청현황</button>
                                        <!--//단체//-->
                                         --%>
                                    </span>   
								</td>
							</tr>
	                	</c:forEach>
                	</c:when>
                	<c:otherwise>
                		<tr>
                			<td colspan="3" class="h200">데이터가 없습니다.</td>
                		</tr>
                	</c:otherwise>
                </c:choose>								
			</tbody>
		</table>					
		
		<div class="fl tc">
			<button class="btn01 btn_bluel" onclick="fn_openYnAll('Y'); return false;">공개</button>
			<button class="btn01 btn_grayl" onclick="fn_openYnAll('N'); return false;">비공개</button>
		</div>		
		<div class="fr tc">
			<button class="btn01 btn_greenl" onclick="fn_moveRgs(); return false;">교육생성</button>
		</div>		
		
		<c:if test="${vo.row > 0 }">
		<!--// paging //-->
		<jsp:include page="/WEB-INF/jsp/admin/common/paging.jsp"/>
		<!--// paging //-->	
		</c:if>
	</div>
</section>

<script>
document.addEventListener('keydown', function(event) {
  	if (event.keyCode === 13) {
  		fn_paging(1)
  	};
}, true);
</script>