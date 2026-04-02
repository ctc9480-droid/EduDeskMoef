<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp" %>
<script>
	function fn_paging(page){
		$("#page").val(page);
		$("form[name='search_form']").submit();
	}
	
	function fn_rgsFrm(){
		location.href = "${utcp.ctxPath}/admin/member/mngrRgs.do";
	}
	
	function fn_release(userId){
		$("#releaseId").val(userId);
		location.href = "#message";
	}
	
	function fn_releaseExecute(){
		$.ajax({
	        url: "${utcp.ctxPath}/admin/member/lockRelease.json",
	        type: "POST",
	        data: {
	        	"userId" : $("#releaseId").val()
	        },
	        cache: false,
			async: true,
	        success: function(r) {
				if(r.isSuperAdmin){
					$("form[name='search_form']").submit();
				}else{
					location.href = "#authMessage";
				}
			}
	    });
	}
	

</script>
<section class="pd025 pd200 po_re">
	<div class="po_re br5 bs_box">
		<span class="tb_text">
			총 <strong class="fc_red">${totalCnt}</strong>명
		</span>
		<form name="search_form" method="get" action="mngrList.do">
       		<input type="hidden" id="page" name="page" value="${vo.page}"/>     		
			<div class="tr mgb15">
				<label for="srchColumn" class="sound_only">검색대상선택</label>
				<select id="srchColumn" name="srchColumn" class="btn04 btn_blackl">
					<option value="userId" <c:if test='${vo.srchColumn == "userId"}'>selected</c:if>>아이디</option>
					<option value="userNm" <c:if test='${vo.srchColumn == "userNm"}'>selected</c:if>>성명</option>
				</select>		
				<label for="sch_input" class="sound_only">검색어입력</label>
				<input type="text" id="srchWrd" name="srchWrd" value="${vo.srchWrd}" placeholder="검색" class="btn04 btn_blackl tl mgr5" />		
				<button class="btn04 btn_black fr">검색</button>
			</div>
		</form>
		<table width="100%" class="tb01 tc">
			<caption class="sound_only">관리자 계정 테이블</caption>
			<thead bgcolor="#f7f8fa">
				<tr>
					<th>NO</th>
					<th>성명</th>
					<th>아이디</th>
					<th>최종로그인</th>
					<th>로그인 횟수</th>
					<!-- 
					<th>잠금여부</th>
					 -->
					
					<th>회원상태</th>
				</tr>
			</thead>	
			<tbody>
				<c:choose>
					<c:when test='${dataList != null && fn:length(dataList) > 0}'>
	                	<c:forEach var="user" items="${dataList}" varStatus="stat">
	                		<tr>
								<td>${totalCnt - (vo.page - 1) * vo.row - stat.index}</td>
								<td>
									<a href="mngrRgs.do?userId=${user.userId}">${user.userNm}</a>
								</td>
								<td>${user.loginId}</td>
								<td><fmt:formatDate value="${user.lstLoginDe}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
								<td><p id="totalLoginCnt"><fmt:formatNumber value="${user.totalLoginCnt}" pattern="###,###,###" /></p></td>
								<!--
								<td>
									<fmt:parseNumber value="${curDate.time / (1000*60*60*24)}" integerOnly="true" var="curDateNum" scope="request" />
					                <fmt:parseNumber value="${user.lstLoginDe.time / (1000*60*60*24)}" integerOnly="true" var="lstLoginNum" scope="request" />
					                <c:choose>
					                	<c:when test="${curDateNum - lstLoginNum > 90 && user.userMemLvl != '1'}">
					                		<button class="btn04 btn_orange" onclick="fn_release('${user.userId}'); return false;">잠김</button>
					                	</c:when>
					                	<c:otherwise>
					                		정상
					                	</c:otherwise>
					                </c:choose>
								</td>
								-->
								<td>
									${user.addStateNm }
								</td>
							</tr>
	                	</c:forEach>
                	</c:when>
                	<c:otherwise>
                		<tr>
                			<td colspan="6" class="h200">데이터가 없습니다.</td>
                		</tr>
                	</c:otherwise>
                </c:choose>
			</tbody>
		</table>
		<div class="fr tc">
			<c:if test="${sessionScope.sessionUserInfo.userMemLvl == '1' }">
			<button class="btn01 btn_greenl" onclick="fn_rgsFrm(); return false;">부 관리자 등록</button>
			</c:if>
		</div>					
		
		<!--// paging //-->
		<jsp:include page="/WEB-INF/jsp/admin/common/paging.jsp"/>
		<!--// paging //-->
	</div>
</section>

<div class="remodal messagePop messagePop2" data-remodal-id="message" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc">
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC">알림</p>
		</div>
		<div class="modal-body">
			<p class="messageTxt">
				해제 하시겠습니까?
			</p>
		</div>
		<input type="hidden" id="releaseId" />
		<div class="modal-footer">
			<div class="tc">
				<button onclick="fn_releaseExecute(); return false;" class="remodal-confirm btn02 btn_orange">확인</button>
				<button data-remodal-action="cancel" class="remodal-cancel btn02 btn_gray">취소</button>
			</div>
		</div>
	</div>
</div>

