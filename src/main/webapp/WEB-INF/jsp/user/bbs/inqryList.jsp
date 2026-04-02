<%@page import="com.educare.edu.member.service.SessionUserInfoHelper"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<script>
	function fnc_paging(pageNo) {
		document.search_form.pageNo.value = pageNo;
		document.search_form.submit();
	}
	function fn_view(idx,lockStatus){
		$('#form-idx').val(idx);
		if(lockStatus==1){
			$('[data-remodal-id=alert-pw]').remodal().open();
		}else{
			$('#form-password').val($('#remodal-password').val());	
			$('#form-view').attr('action','${utcp.ctxPath}/user/cs/inqryView.do');
			$('#form-view').submit();
		}
	}
	function fn_view_final(){
		if($('#remodal-password').val()==''){
			$('.pwTxt').css('visibility','visible')
			return;
		}
		$('#form-password').val($('#remodal-password').val());	
		$('#form-view').attr('action','${utcp.ctxPath}/user/cs/inqryView.do');
		$('#form-view').submit();
	}
</script>

<form id="form-view" method="post">
	<input type="hidden" name="idx" id="form-idx" />
	<input type="hidden" name="password" id="form-password" />
</form>
<div class="listWrap" style="padding-top: 0px">
	<form name="search_form" method="get">
		<input type="hidden" id="pageNo" name="pageNo" value="1" />
		<div class="searchBox cf">
			<div class="box_sort cf">
				<div class="box_search">
					<input type="text" id="searchWord" name="searchWord" value="${vo.searchWord }">
					<label for="searchWord" class="sound_only">검색어 입력</label>
					<button type="submit">검색</button>
				</div>
			</div>
		</div>
	</form>
	<table class="tb02 mt30 inqry_board">
		<tr>
			<th class="inq_num" width="50px">NO</th>
			<th class="inq_tit">제목</th>
			<th class="inq_name" width="100px">작성자</th>
			<th class="inq_date" width="100px">작성일</th>
			<th class="inq_state" width="100px" style="border-right: 0px;">상태</th>
		</tr>
		<c:forEach items="${inqryList }" var="map">
			<tr>
				<td class="inq_num">${(pageNavi.totalRecordCount+1)-map.rNum }</td>
				<td class="inq_tit tl"><a href="#none" onclick="fn_view(${map.idx},${map.lockStatus })">${map.title } <c:if test="${map.lockStatus==1 or map.lockStatus==2}">
							<img src="${utcp.ctxPath}/resources/user/image/icon/icon_online.png" alt="자물쇠 아이콘" />
						</c:if>
				</a></td>
				<td class="inq_name">${utcp.getMaskedName(map.regNk) }</td>
				<td class="inq_date">${map.replaceRegDtime('yyyy.MM.dd') }</td>
				<td class="inq_state" style="border-right: 0px;">${map.statusNm }</td>
			</tr>
		</c:forEach>
		<c:if test="${empty inqryList}">
			<tr>
				<td colspan="5" style="border-right: 0px;">자료가 없습니다</td>
			</tr>
		</c:if>
	</table>
	<div class="tb_btn">
		<ul>
			<%if(SessionUserInfoHelper.isLogined()){ %>
			<c:if test="${fn:indexOf('7,9',sessionScope.sessionUserInfo.userMemLvl)>=0 }">
			<li class="right"><a href="inqryWrite.do">글쓰기</a></li>
			</c:if>
			<%} %>
		</ul>
	</div>
	<jsp:include page="/WEB-INF/jsp/user/bbs/pageNavi.jsp" />
</div>

<!--// 비밀번호 확인 //-->
<div class="remodal messagePop" data-remodal-id="alert-pw" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc">
	<button data-remodal-action="close" class="remodal-close" aria-label="Close"></button>
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC">비밀번호 확인</p>
		</div>
		<div class="modal-body">
			<div class="fileBox" style="margin: 30px auto;">
				<input type="password" id="remodal-password" class="ip1 pwActive">
				<label for="remodal-password" class="sound_only">비밀번호 입력</label>
				<span class="pwTxt">비밀번호를 입력해주세요.</span>
			</div>
		</div>
		<div class="modal-footer">
			<div class="tc">
				<button onclick="fn_view_final()" data-remodal-action="" class="remodal-confirm btn02 btn_green">확인</button>
			</div>
		</div>
	</div>
</div>
<!--// 비밀번호 확인 //-->