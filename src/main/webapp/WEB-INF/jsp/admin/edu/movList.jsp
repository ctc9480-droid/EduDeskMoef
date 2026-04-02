<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<section class="pd025 pd200 po_re">
	<div class="po_re br5 bs_box cf">
	<form name="search_form">
	<input type="hidden" id="pageNo" name="pageNo" value="1" />
		<div class="dp_ib fr tr mgb15">
			<!-- <label for="srchColumn" class="sound_only">검색대상선택</label> 
			<select id="srchColumn" name="srchColumn" class="btn04 btn_blackl">
				<option value="eduNm">교육과정 명</option>
				<option value="instrctrNm">강사</option>
			</select> -->

			<label for="sch_input" class="sound_only">검색어입력</label>
			<input type="text" name="srchWrd" id="srchWrd" placeholder="검색" class="btn04 btn_blackl tl mgr5" value="${param.srchWrd }">
			<button class="btn04 btn_black fr">검색</button>
		</div>
		</form>
		<table width="100%" class="tb03 tc mov">
			<c:forEach items="${list }" var="o">
			<tr>
				<td class="tl"><a href="#"><span class="dp_ib font_22 fw_500 pdr15">${o.title }</span></a> 
				<%-- <span class="dp_b font_14 pdb5">${o.instrctrNm } 강사 (${o.duration })</span>  --%>
				<span class="dp_b font_14">${o.content }</span> 
				<span class="dp_b font_14">업로드 : ${utcp.convDateToStr(o.regTime,'yyyy.MM.dd HH:mm') }</span></td>
 				<td class="tl">
					<button class="btn02 btn_blue" onclick="fn_playMov(${o.idx });">시청하기</button>
					<%-- 
					<c:if test="${sessionScope.sessionUserInfo.userMemLvl==1 or sessionScope.sessionUserInfo.userMemLvl==2 or sessionScope.sessionUserInfo.userId==o.regId}">
					 --%>
					<c:if test="${sessionScope.sessionUserInfo.userMemLvl==1 or sessionScope.sessionUserInfo.userId==o.regId}">
						<button class="btn02 btn_green" onclick="location.href='movRgs.do?idx=${o.idx}'">수정</button>
						<button class="btn02 btn_gray" onclick="location.href='#del-alert';$('#del-idx').val('${o.idx}');">삭제</button>
					</c:if>
					
				</td>
			</tr>
			</c:forEach>
			<c:if test="${empty list }">
			<tr>
				<td colspan="2">동영상이 없습니다.</td>
			</tr>
			</c:if>
		</table>

		<div class="fr tc">
			<button class="btn01 btn_greenl" onclick="location.href='movRgs.do'";>등록</button>
		</div>
	</div>
	<jsp:include page="/WEB-INF/jsp/admin/bbs/pageNavi.jsp" />
</section>

<div class="remodal messagePop messagePop2" data-remodal-id="del-alert" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc">
	<input type="hidden" id="del-idx"/>
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC">알림</p>
		</div>
		<div class="modal-body">
			<p class="messageTxt" id="messageStr">동영상을 삭제 하시겠습니까?</p>
		</div>
		<div class="modal-footer">
			<div class="tc">
				<button onclick="fn_del()" data-remodal-action="cancel" class="remodal-confirm btn02 btn_orange">확인</button>
				<button data-remodal-action="cancel" class="remodal-confirm btn02 btn_gray">취소</button>
			</div>
		</div>
	</div>
</div>

<script>
	function fn_del(){
		$.ajax({
			url:'${utcp.ctxPath}/admin/ajax/deleteMov.json',data:{idx:$('#del-idx').val()},
			success:function(r){
				if(r.result == 0){
					alert('동영상을 삭제 할 수 없습니다.');
					return;
				}
				console.log(r);
				location.reload();
			}
		});
	}
	function fnc_paging(pageNo) {
		document.search_form.pageNo.value = pageNo;
		document.search_form.submit();
	}
</script>