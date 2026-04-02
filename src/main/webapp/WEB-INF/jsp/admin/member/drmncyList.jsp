<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp" %>
<script>
function fn_paging(page){
	$("#page").val(page);
	$("form[name='search_form']").submit();
}

function fn_release(userId){
	$("#releaseId").val(userId);
	location.href = "#message";
}

function fn_releaseExecute(){
	$.ajax({
        url: "${utcp.ctxPath}/admin/member/lockReleaseStdnt.json",
        type: "POST",
        data: {
        	"userId" : $("#releaseId").val()
        },
        cache: false,
		async: true,
        success: function(r) {
			if(r.isAdmin){
				location.href= "#success";
			}else{
				location.href = "#authMessage";
			}
		}
    });
}

function fn_releaseSuccess(){
	$("form[name='search_form']").submit();
}

</script>
<section class="pd025 pd200 po_re">
	<div class="po_re br5 bs_box">
		<span class="tb_text">
			총 <strong class="fc_red">${totalCnt}</strong> 명
		</span>
		<form name="search_form" method="get" action="${utcp.ctxPath}/admin/member/drmncyList.do">
			<input type="hidden" id="page" name="page" value="${vo.page}"/>
			<div class="tr mgb15">
				<label for="srchColumn" class="sound_only">검색대상선택</label>
				<select name="srchColumn" id="srchColumn" class="btn04 btn_blackl">
					<option value="userNm" <c:if test='${vo.srchColumn == "userNm"}'>selected</c:if>>성명</option>
					<option value="loginId" <c:if test='${vo.srchColumn == "loginId"}'>selected</c:if>>아이디</option>
				</select>
				
				<label for="srchWrd" class="sound_only">검색어입력</label>
				<input type="text" name="srchWrd" id="srchWrd" placeholder="검색" class="btn04 btn_blackl tl mgr5" value="${vo.srchWrd}"/>
				
				<button class="btn04 btn_black fr">검색</button>
			</div>
		</form>		
		<table width="100%" class="tb01 tc">
			<caption class="sound_only">휴면회원정보테이블</caption>
			<thead bgcolor="#f7f8fa">
				<tr>
					<th>NO</th>
					<th>성명</th>
					<th>아이디</th>
					<th>연락처</th>
					<th>소속</th>
					<th>가입일자</th>
					<th>최종 로그인</th>
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
									<a href="${utcp.ctxPath}/admin/member/stdntView.do?userId=${user.userId}">${user.userNm}</a>
								</td>
								<td>${user.email}</td>
								<td>${user.mobile}</td>
								<td>${user.belong}</td>
								<td><fmt:formatDate value="${user.rgsde}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
								<td><fmt:formatDate value="${user.lstLoginDe}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
								<td>
									<button class="btn04 btn_orange" onclick="fn_release('${user.userId}'); return false;">휴면</button>
								</td>
							</tr>
	                	</c:forEach>
                	</c:when>
                	<c:otherwise>
                		<tr>
                			<td colspan="8" class="h200">데이터가 없습니다.</td>
                		</tr>
                	</c:otherwise>
                </c:choose>
			</tbody>
		</table>
		
		<!--// paging //-->
		<jsp:include page="/WEB-INF/jsp/admin/common/paging.jsp"/>
		<!--// paging //-->						
	</div>
</section>

<div class="remodal messagePop messagePop1" data-remodal-id="message" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc">
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC">알림</p>
		</div>
		<div class="modal-body">
			<p class="messageTxt">
				휴면해제 하시겠습니까?
			</p>
		</div>
		<input type="hidden" id="releaseId" />
		<div class="modal-footer">
			<div class="tc">
				<button onclick="fn_releaseExecute(); return false;" class="remodal-confirm btn02 btn_green">확인</button>
				<button data-remodal-action="cancel" class="remodal-cancel btn02 btn_gray">취소</button>
			</div>
		</div>
	</div>
</div>

<div class="remodal messagePop messagePop1" data-remodal-id="success" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc">
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC">알림</p>
		</div>
		<div class="modal-body">
			<p class="messageTxt">
				휴면 해제되었습니다.
			</p>
		</div>
		<div class="modal-footer">
			<div class="tc">
				<button onclick="fn_releaseSuccess(); return false;" class="remodal-confirm btn02 btn_green">확인</button>
			</div>
		</div>
	</div>
</div>