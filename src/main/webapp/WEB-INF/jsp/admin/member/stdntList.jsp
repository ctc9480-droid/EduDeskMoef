<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp" %>
<script>
$(document).ready(function(){
	$('#srchMemLvl').val('${vo.srchMemLvl}');
	
	$('#srchWrd').on('keyup',function(o){
		if(o.keyCode == 13){
			fn_srchStdntList();
		}
	});
});

function fn_paging(page){
	$("#page").val(page);
	$("form[name='search_form']").submit();
}

function fn_rgs(){
	location.href = "${utcp.ctxPath}/admin/member/stdntRgs.do";
}

function fn_excel(){
	var srchColumn = $("#srchColumn").val();
	var srchWrd = $("#srchWrd").val();
	location.href = "${utcp.ctxPath}/admin/member/stdntListExcel2.do?srchColumn=" + srchColumn + "&srchWrd=" + srchWrd;
}
function fn_allowUser() {
	
	var userIds = [];
	var userMemLvls = [];
	
	$("select[name=userMemLvl]:not(:disabled)").each(
		function() {
			userIds.push($(this).attr('data-uid'));
			userMemLvls.push($(this).val());
		}
	)
	
	if(confirm('저장하시겠습니까?')){
		$.ajax({
			url : '${utcp.ctxPath}/admin/member/stdntAllowProc.json',
			traditional: true,
			data : {
				userIds: userIds,
				userMemLvls: userMemLvls
			},
			success : function(r) {
				alert("승인을 완료했습니다.");
				$('[name=search_form]').submit();
			}
		});	
	}
		
	
}

function fnDelete(userId){
	if(confirm('삭제하시겠습니까?')){
		$.ajax({
			url : '${utcp.ctxPath}/admin/member/userDelete.json',
			traditional: true,
			data : {
				userId: userId
			},
			success : function(r) {
				alert("삭제을 완료했습니다.");
				$('[name=search_form]').submit();
			}
		});	
	}
}

function fn_srchStdntList(){
	$("#page").val(1);
	$('[name=search_form]').submit();
}
</script>
<section class="pd025 pd200 po_re">
	<div class="po_re br5 bs_box">
		<span class="tb_text">
			총 <strong class="fc_red">${totalCnt}</strong> 명
		</span>
		<form name="search_form" method="get" action="${utcp.ctxPath}/admin/member/stdntList.do">
			<input type="text" name="dummy" style="display: none;"/>
			<input type="hidden" id="page" name="page" value="${vo.page}"/>
				
			<div class="tr mgb15">
				<!-- 
				<select name="srchMemLvl" id="srchMemLvl" class="btn04 btn_blackl">
					<option value="" >전체</option>
					<option value="7">준회원</option>
					<option value="9">정회원</option>
				</select>
 				-->			
				<label for="srchColumn" class="sound_only">검색대상선택</label>
				<select name="srchColumn" id="srchColumn" class="btn04 btn_blackl">
					<option value="userNm" <c:if test='${vo.srchColumn == "userNm"}'>selected</c:if>>성명</option>
					<option value="loginId" <c:if test='${vo.srchColumn == "loginId"}'>selected</c:if>>아이디</option>
				</select>
				
				<label for="srchWrd" class="sound_only">검색어입력</label>
				<input type="text" name="srchWrd" id="srchWrd" placeholder="검색" class="btn04 btn_blackl tl mgr5" value="${vo.srchWrd}"/>
				
				<button type="button" onclick="fn_srchStdntList()" class="btn04 btn_black fr">검색</button>
			</div>
		</form>
		<table width="100%" class="tb01 tc">
			<caption class="sound_only">회원정보테이블</caption>
			<thead bgcolor="#f7f8fa">
				<tr>
					<th>번호</th>
					<th>성명</th>
					<th>아이디</th>
					<th>구분</th>
					<th>성별</th>
					<th>휴대폰</th>
					<th>등록일</th>
					<th>사용승인</th>
					<th>삭제</th>
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
								<td>${user.loginId}</td>
								<td>${user.addUserTpNm }</td>
								<td>${user.addMfTypeNm }</td>
								<td>${user.decMobile }</td>
								<td>${utcp.convDateToStr(user.rgsde,'yyyy-MM-dd') }</td>
								<td>
									<select name="userMemLvl" data-uid="${user.userId}" >
										<option value="9" ${user.userMemLvl eq '9'?'selected':''}>승인</option>
										<option value="7" ${user.userMemLvl eq '7'?'selected':''}>미승인</option>
									</select>
								</td>
								<td>
									<button type="button" class="btn04 btn_green" onclick='fnDelete("${user.userId}");'>삭제</button>
								</td>
							</tr>
	                	</c:forEach>
                	</c:when>
                	<c:otherwise>
                		<tr>
                			<td colspan="8" class="h200"">데이터가 없습니다.</td>
                		</tr>
                	</c:otherwise>
                </c:choose>
			</tbody>
		</table>

		<div class="fr tc">
			<button class="btn01 btn_green" onclick="fn_allowUser();">저장</button>
			<button class="btn01 btn_green" onclick="fn_excel();">액셀 다운로드</button>
			<!-- <button class="btn01 btn_greenl" onclick="fn_rgs(); return false;">아이디 생성</button> -->
		</div>
		
		<!--// paging //-->
		<jsp:include page="/WEB-INF/jsp/admin/common/paging.jsp"/>
		<!--// paging //-->					
	</div>
</section>