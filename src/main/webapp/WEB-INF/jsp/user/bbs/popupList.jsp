<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<script>
	$(document).ready(function() {
		$('#idxs_all').click(function() {
			if ($(this).is(':checked')) {
				$('input[name=idxs]').prop('checked', true);
			} else {
				$('input[name=idxs]').prop('checked', false);
			}
		});
	});
	function fn_del() {
		if($('input[name=idxs]:checked').length == 0){
			$("#messageStr").html("선택된 데이터가 없습니다.");
			location.href = "#message";
			return;
		}
		
		location.href = "#confirm";
	}
	
	function fn_deleteExecute(){
		var obj = $('input[name=idxs]').clone();
		$('#form_del').append(obj);
		document.form_del.action = 'popupDeleteProc.do';
		document.form_del.submit();
	}
	
	function fnc_paging(pageNo) {
		document.search_form.pageNo.value = pageNo;
		document.search_form.submit();
	}
	
	function fn_popup_preview(idx) {
		window.open('/bbs/comm/popup/preview.do?idx=' + idx, 'preview',
				'status=0,location=0,width=300,height=300,top=500,left=1000');
	}
</script>
<section class="pd025 pd200 po_re">
	<div class="po_re br5 bs_box">
		<span class="tb_text"> 총 <strong class="fc_red">${pageNavi.totalRecordCount }</strong> 건
		</span>
		<form name="search_form" method="get">
			<input type="hidden" id="pageNo" name="pageNo" value="1" />
			<div class="tr mgb15">
				<label for="sch_select" class="sound_only">검색대상선택</label> <select name="searchSelect" class="btn04 btn_blackl">
					<option value="title" ${vo.searchSelect eq 'title'?'selected':'' }>제목</option>
					<option value="content" ${vo.searchSelect eq 'content'?'selected':'' }>내용</option>
				</select> <label for="searchWord" class="sound_only">검색어입력</label> <input id="searchWord" name="searchWord" value="${vo.searchWord }" type="text" class="btn04 btn_blackl tl mgr5" />
				<button class="btn04 btn_black fr" type="submit">검색</button>
			</div>
		</form>
		<table width="100%" class="tb01 tc tb_fix">
			<caption class="sound_only">팝업테이블</caption>
			<colgroup>
				<col width="5%" />
				<col width="7%" />
				<col width="30%" />
				<col width="*" />
				<col width="10%" />
				<col width="12%" />
				<col width="10%" />
			</colgroup>
			<thead bgcolor="#f7f8fa">
				<tr>
					<th><input type="checkbox" id="idxs_all"/></th>
					<th>NO</th>
					<th>팝업 제목</th>
					<th>게시기간</th>
					<th>게시여부</th>
					<th>작성일</th>
					<th>미리보기</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${popupList }" var="map">
					<tr>
						<td><input type="checkbox" name="idxs" value="${map.idx }" /></td>
						<td>${map.rNum }</td>
						<td class="tl el"><a href="popupWrite.do?idx=${map.idx }">${map.title }</a></td>
						<td>${map.replaceStartDtime('yyyy-MM-dd HH:mm')} ~ ${map.replaceEndDtime('yyyy-MM-dd HH:mm')}</td>
						<td>${map.status eq '1'?'Y':'N'}</td>
						<td>${map.replaceRegDtime('yyyy-MM-dd') }</td>
						<td><a href="#none" onclick="fn_popup_preview('${map.idx}')"> <i class="fas fa-search font_16 fc_gray"></i>
						</a></td>
					</tr>
				</c:forEach>
				<c:if test="${empty popupList}">
					<tr>
						<td colspan="7">자료가 없습니다</td>
					</tr>
				</c:if>
			</tbody>
		</table>
		<div class="fl tc">
			<button class="btn02 btn_gray" onclick="fn_del();">선택삭제</button>
		</div>
		<div class="fr tc">
			<button type="button" onclick="location.href='${utcp.ctxPath}/admin/bbs/popupWrite.do'" class="btn01 btn_greenl">작성</button>
		</div>
		<jsp:include page="/WEB-INF/jsp/admin/bbs/pageNavi.jsp" />
	</div>
</section>
<form name="form_del" id="form_del" method="post" style="display: none;">
</form>

<div class="remodal messagePop messagePop2" data-remodal-id="message" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc">
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC">알림</p>
		</div>
		<div class="modal-body">
			<p class="messageTxt" id="messageStr">
				
			</p>
		</div>
		<div class="modal-footer">
			<div class="tc">
				<button data-remodal-action="cancel" class="remodal-confirm btn02 btn_orange">확인</button>
			</div>
		</div>
	</div>
</div>

<div class="remodal messagePop messagePop2" data-remodal-id="confirm" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc">
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC">알림</p>
		</div>
		<div class="modal-body">
			<p class="messageTxt">
				삭제 후 복구할 수 없습니다.<br/>삭제 하시겠습니까?
			</p>
		</div>
		<div class="modal-footer">
			<div class="tc">
				<button onclick="fn_deleteExecute(); return false;" class="remodal-confirm btn02 btn_orange">확인</button>
				<button data-remodal-action="cancel" class="remodal-cancel btn02 btn_gray">취소</button>
			</div>
		</div>
	</div>
</div>
