<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<script>
	var data = {};
	var viewData = {};
	function fnc_reg_comment() {

		$.ajax({
			type : 'post',
			url : 'boardCommentWriteProc.json',
			data : data,
			success : function(rData) {
				if (rData.boardComment.result == 1) {
					location.reload();
				} else {
					alert('에러발생');
				}
			}
		});
	}
	
	function fnc_mod_comment(idx) {
		var data = {};
		data.idx = idx;
		$.ajax({
			type : 'post',
			url : 'boardCommentModCheck.json',
			data : data,
			success : function(rData) {
				if (rData.boardComment.result == 1) {
					document.getElementById('commentIdx').value = idx;
					viewData.content = rData.boardComment.content;
					getHtmlCommentArea(idx);
				} else {
					alert('에러발생');
				}
			}
		});
	}
	
	function fnc_open_reply_comment(idx) {
		$('div[id^=comment-area-]').empty();
		var _node = $('#comment-area div').clone();
		document.getElementById('commentPIdx').value = idx;
		$('#comment-area-' + idx).html(_node);
	}
	
	function fnc_reply_comment() {
		fnc_reg_comment();
	}
	
	function fnc_comment() {
		$('div[id^=comment-area-]').empty();
		document.getElementById('commentPIdx').value = 0;

		data.bIdx = document.comment_form.bIdx.value;
		data.content = document.getElementById('commentContent').value;
		data.idx = document.getElementById('commentIdx').value;
		data.pIdx = document.getElementById('commentPIdx').value;
		fnc_reg_comment();
	}
	
	function getHtmlCommentArea(idx, pIdx) {
		var html = '<textarea id="commentContent">' + viewData.content
				+ '</textarea>';
		console.log(html);
	}
	
	function fn_del(idx) {
		location.href = "#message";
	}
	
	function fn_deleteExecute(){
		$('#form_view').attr('method','post');
		$('#form_view').attr('action','boardDeleteProc.do');
		$('#form_view').submit();
	}
</script>
<section class="pd025 pd200 po_re">
	<div class="po_re br5 bs_box" style="width:1080px;">
		<form id="form_view">
			<input type="hidden" name="idx" value="${boardMap.idx }" />
			<input type="hidden" name="boardType" value="${boardMap.boardType }" /> 
		</form>
		<table width="100%" class="tb04 tl tb_fix">
			<caption class="sound_only">게시판 상세보기 테이블</caption>
			<colgroup>
				<col width="" />
				<col width="" />
				<col width="20%" />
				<col width="*" />
			</colgroup>
			<thead>
				<tr>
					<th colspan="4" class="pdl10 pdt15 pdb15 font_18">
					<c:if test="${not empty boardMap.cate }">[${boardMap.cate}]</c:if>
					${boardMap.title }</th>
				</tr>
				<tr>
					<th colspan="2"><span class="fc_gray">작성일</span>${boardMap.replaceRegDtime('yyyy-MM-dd') }</th>
					<th><span class="fc_gray">조회수</span>${boardMap.hit }</th>
					<th><span class="fc_gray">작성자</span>${boardMap.regNm2 }</th>
					
				</tr>
			</thead>
			<tbody>
				<tr>
					<td colspan="4" class="tl tb_img">
						<c:choose>
						<c:when test="${boardMst.edtrYn == 1 }">
							${boardMap.content }
						</c:when>
						<c:otherwise>
							${boardMap.content2 }
						</c:otherwise>
						</c:choose>
						
						
						<%-- 교육자료실인경우 이미지파일이면 본문에 출력 --%>
						<c:if test="${boardMap.boardType eq 'eduRecs' }">
							<br/>
							<c:forEach items="${boardAttachList }" var="map">
								<c:if test="${fn:contains('jpg,jpeg,png,gif',map.fileType)}">
									<br/><img src="${map.fileToImg2 }" alt=""/>
								</c:if>
							</c:forEach>
						</c:if>
					</td>
				</tr>
				<c:if test="${not empty boardMst.tmp01 }">
				<tr>
					<th colspan="1" class="tdbg3 tc"><label for="mn_id1">${boardMst.tmp01 }</label></th>
					<td colspan="3" class="tl">${boardMap.tmp01 }
					</td>
				</tr>
				</c:if>
				<tr>
					<th colspan="4">
						<div style="float:left;"><b>첨부파일</b></div>
						<div style="float:left;">
							<c:forEach items="${boardAttachList }" var="map">
								<span class="tb_file">
									<a href="${utcp.ctxPath}/bbs/comm/download/${map.fileSeq }.do"><img src="${utcp.ctxPath}/resources/admin/images/file.png" alt="file_icon" /> ${map.fileOrg } (${map.replaceFileSize})</a><br/>
								</span>
							</c:forEach>
						</div>
					</th>
				</tr>
			</tbody>
		</table>
		
		<!-- 230830 hy -->
		<c:if test="${vo.boardType eq 'notice' }">
		<div class="bbsViewList">
			<c:if test="${not empty boardMapNext  }">
			<div class="tableRow">
	            <div class="tableCell tableHead">
	                <p class="prev">이전글</p>
	            </div>
	            <div class="tableCell tit">
	                <a href="${vo.boardType }View.do?idx=${boardMapNext.idx }">${boardMapNext.title }</a>
	            </div>
            </div>
            </c:if>
            <c:if test="${not empty boardMapPrev  }">
			<div class="tableRow">
	            <div class="tableCell tableHead">
	                <p class="next">다음글</p>
	            </div>
	            <div class="tableCell tit">
	                <a href="${vo.boardType }View.do?idx=${boardMapPrev.idx }">${boardMapPrev.title }</a>
	            </div>
            </div>
            </c:if>
		</div>
		</c:if>
		<!-- 230830 hy -->
		
		
		<div class="fl tc">
			<a href="${vo.boardType }List.do" class="btn02 btn_grayl">목록</a>
		</div>
		<div class="fr tc">
			<c:if test="${boardMap.gLvl eq '0' and vo.boardType eq 'free'}">
			<a href="${vo.boardType }Write.do?pIdx=${boardMap.idx }" class="btn01 btn_black">답글</a>
			</c:if>
			<c:if test="${sessionScope.sessionUserInfo.userId eq boardMap.regId}">
			<button onclick="location.href='${vo.boardType }Write.do?idx=${boardMap.idx }'" class="btn01 btn_green" type="button">수정</button>
			<button onclick="fn_del()" class="btn01 btn_gray" type="button">삭제</button>
			</c:if>
		</div>
	</div>
	<%-- 댓글 기능 미완성, 운영시에 숨김처리 해야함 --%>
	<div class="" style="margin-top: 100px; display:none;">
		<form name="comment_form">
			<input type="hidden" id="commentIdx" value="0" /> <input type="hidden" id="commentPIdx" value="0" /> <input type="hidden" name="bIdx" value="${boardMap.idx }" />
		</form>
		<ul>
			<c:forEach items="${boardCommentList }" var="map">
				<li style="width: 100%;"><span style="float: left;">${map.childFlagStr }</span> <span style="float: left;">${map.content2 }</span> <span style="float: right;">[${map.regNk }] [${map.regDtime2 }] <a href="#none" onclick="fnc_mod_comment('${map.idx}');">[edit]</a> <a href="#none" onclick="fnc_open_reply_comment('${map.idx}');">[답글]</a>
				</span>
					<div id="comment-area-${map.idx }"></div> <br />
				</li>
			</c:forEach>
		</ul>
		<div>
			<textarea id="commentContent" style="width: 100%; height: 100px;"></textarea>
			<a href="#none" onclick="fnc_comment()">댓글달기</a>
		</div>
		<div id="comment-area" style="display: none;">
			<div>
				<textarea id="commentContentRe" style="width: 100%; height: 100px;"></textarea>
				<a href="#none" onclick="fnc_reply_comment()">댓글달기</a>
			</div>
		</div>
	</div>
</section>

<div class="remodal messagePop messagePop2" data-remodal-id="message" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc">
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