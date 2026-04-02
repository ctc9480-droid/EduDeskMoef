<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp" %>
<script>
	function fn_paging(page){
		$("#page").val(page);
		$("form[name='search_form']").submit();
	}
	
	function fn_rgsFrm(){
		location.href = "${utcp.ctxPath}/admin/member/oprtRgs.do";
	}
	
	function fn_release(userId){
		$("#releaseId").val(userId);
		location.href = "#message";
	}
	
	
</script>
<section class="pd025 pd200 po_re">
	<div class="po_re br5 bs_box">
		<span class="tb_text">
			총 <strong class="fc_red">${totalCnt}</strong>명
		</span>
		<form name="search_form" method="get" action="${utcp.ctxPath}/admin/member/oprtList.do">
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
					<th>아이디</th>
					<th>성명</th>
					<th>휴대폰번호 </th>
					<th>등록일</th>
					<th>최근접속</th>
					<th>상태</th>
				</tr>
			</thead>	
			<tbody>
				<c:choose>
					<c:when test='${dataList != null && fn:length(dataList) > 0}'>
	                	<c:forEach var="user" items="${dataList}" varStatus="stat">
	                		<tr>
								<td>${totalCnt - (vo.page - 1) * vo.row - stat.index}</td>
								<td>${user.loginId}</td>
								<td>
									<a href="oprtRgs.do?userId=${user.userId}">${user.userNm}</a>
								</td>
								<td>${user.decMobile }</td>
								<td><fmt:formatDate value="${user.rgsde}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
								<td><fmt:formatDate value="${user.lstLoginDe}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
								<td>
									${user.addStateNm }
								</td>
							</tr>
	                	</c:forEach>
                	</c:when>
                	<c:otherwise>
                		<tr>
                			<td colspan="9" class="h200">데이터가 없습니다.</td>
                		</tr>
                	</c:otherwise>
                </c:choose>
			</tbody>
		</table>
		<div class="fr tc">
			<button class="btn01 btn_greenl" onclick="fn_rgsFrm(); return false;">운영자 등록</button>
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