<%@page import="com.educare.edu.member.service.SessionUserInfoHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>    


<!--// view //-->
<div class="tstyle_view">
    <div class="title">${lectureBoard.title }</div>
    <ul class="head info">
        <li><span>작성일</span> <b>${utcp.convDateToStr(utcp.convStrToDate(lectureBoard.regDtime,'yyyyMMddHHmmssSSS'),'yyyy.MM.dd HH:mm')}</b></li>
        <li>
            <p></p> <span>조회수</span> <b>${lectureBoard.hit}</b>
        </li>
        <li>
            <p></p> <span>작성자</span> <b>${lectureBoard.addRegNmMasked}</b>
        </li>
    </ul>
    <div class="tb_contents">
   <%--  <p>${utcp.convNewLine(lectureBoard.content)}</p> --%>
   ${lectureBoard.content }
    </div>
    
    <div class="tb_files cf">
		<c:if test="${attachList ne null and attachList ne '[]' and attachList ne ''}">
			<div style="float:left;margin-top:10px;"><b>첨부파일</b></div>
			<div style="float:left;">
				<ul class="head info">
					<c:forEach items="${attachList }" var="map">
						<li><a href="${utcp.ctxPath}/user/edu/bbs/download/${map.fileSeq }.do"> 
						<img src="${utcp.ctxPath}/resources/user/image/icon/down_icon.gif" alt="파일 아이콘" /> <b>${map.fileOrg }(${map.replaceFileSize})</b></a></li><br/>
					</c:forEach>
				</ul>
			</div>
		</c:if>
	</div>
	
	<!--// comment //-->
	<div class="comment-list">
		<div class="comment_view">
			<ul class="list_comment">				
				<c:forEach items="${cmntList }" var="o" >
				<li>
					<div class="comment_section">
						<div class="comment_info">
							<div class="comment_post cf">
								<div class="profile_info">
									<div class="opt_more_g dp_ib">
										<span class="txt_name"><i class="fa-solid fa-circle-user pdr5"></i>
										${o.addRegNmMasked}
										</span>
									</div>
									<span class="txt_date dp_ib mgl10 fs_12" tabindex="0">${utcp.convDateToStr(o.regDt,'yy.MM.dd HH:mm') }</span>
								</div>
								<div class="box_post">
									<p class="desc_info">
										<span class="original_comment">${utcp.convNewLine(o.content) }</span>
									</p>
									
									<div class="box_textarea box_textarea_edit cf mgt15 mgl15">
										<textarea id="comment-${o.idx }" class="h80p fl" maxlength="500">${o.content }</textarea>
										<div class="btn_group fl">
											<button class="btn_blue" type="button" onclick="fn_regComment(${o.idx})">등록</button>
										</div>
									</div>
								</div>
								<c:if test="${o.regId eq sessionScope.sessionUserInfo.userId }">
								<div class="comment_btn">
									<div class="opt_more_g">
										<button type="button" class="btn_edit edit_on"><span class="btn_orange">수정</span></button>
										<button type="button" class="btn_del"><span class="btn_gray" onclick="fn_delComment(${o.idx})">삭제</span></button>
									</div>
								</div>
								</c:if>
							</div>
						</div>
					</div>
				</li>
				</c:forEach>
			</ul>
		</div>
		<div class="text_write_g comment_write">
			<div class="inner_text_write">
				<div class="box_textarea cf">
					<textarea id="comment0" class="h80p fl" placeholder="최대 500자 이내로 입력하세요." maxlength="500"></textarea>
					<div class="btn_group fl">
						<button class="btn_blue" type="button" onclick="fn_regComment(0)">등록</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!--// comment //-->
    
	<div class="fl">
		<a href="javascript:fn_boardView();" class="btn02 btn_grayl">목록</a>
	</div>
	<c:if test="${sessionScope.sessionUserInfo.userId eq lectureBoard.regId}">
	<div class="fr">
		<a href="javascript:fn_delBbs();" class="btn02 btn_gray">삭제</a>
		<a href="javascript:fn_udpBbs();" class="btn02 btn_blue">수정</a>
	</div>
	</c:if>
</div>
<!--// view //-->
<script>
	function fn_boardView(){
		$('#tabAction').val('boardView');
		$('#myEduForm').submit();
	}
	function fn_boardList(){
		$('#tabAction').val('boardList');
		$('#myEduForm').submit();
	}
	function fn_delBbs() {
		if(confirm("정말 삭제하시겠습니까?")) {
			var boardIdx = $('#boardIdx').val();
			$.ajax({
				url:'${utcp.ctxPath}/user/mypage/popup/boardDeleteProc.ajax',
				type : "post",
				dataType : "json",
				data :  {
					boardIdx : boardIdx
				},
				success : function(r){
					if(r.result == 1){
						fn_boardView();
						return;
					}
					alert(r.msg);
					return;
				}
			});
		}else{
			return false;
		}
	}
	function fn_udpBbs() {
		$('#tabAction').val('boardReg');
		$('#myEduForm').submit();
	}
</script>

<script>
$(document).ready(function(){
	  $(".edit_on").click(function(){
	    $(this).parent().parent().parent().find(".box_textarea_edit").slideToggle("fast");
	  });
	});
function fn_regComment(idx){
	var bIdx = document.querySelector("#boardIdx").value;
	var eduSeq = $('#edu_seq').val();
	var comment = $('#comment0').val();
	if(idx>0){
		comment = $('#comment-'+idx).val();
	}
	$.ajax({
		url:'${utcp.ctxPath}/user/mypage/popup/boardCommentWriteProc.ajax',
		type : "post",
		dataType : "json",
		data :  {
			bIdx : bIdx , comment : comment ,idx : idx,eduSeq : eduSeq
		},
		success : function(r){
			if(r.result == 1){
				fn_boardView();
			}else{
				alert(r.msg);
			}
		}
	});
}
function fn_delComment(idx) {
	if(confirm("정말 삭제하시겠습니까?")) {
		$.ajax({
			url:'${utcp.ctxPath}/user/mypage/popup/boardCommentDeleteProc.ajax',
			type : "post",
			dataType : "json",
			data :  {
				idx : idx
			},
			success : function(r){
				if(r.result == 1){
					fn_boardView();
				}else{
					alert(r.msg);
				}
			}
		});
	}else{
		return false;
	}
}
</script>
