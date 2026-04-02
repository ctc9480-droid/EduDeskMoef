<%@page import="com.educare.edu.member.service.SessionUserInfoHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>    
<script src="${utcp.ctxPath}/resources/ckeditor4_full/ckeditor.js"></script>

<!--// view //-->
<div class="tstyle_view tb02" id="vm-edu-board">
    	<div class="title">
    	<c:if test="${lectureCommunity.scrtyYn eq 'Y' }">
    	<img src="${utcp.ctxPath}/resources/user/image/icon/icon_online.png" alt="자물쇠 아이콘" />
    	</c:if>
    	${lectureCommunity.title}</div>
    <ul class="head info">
        <li><span>작성일</span> <b>${utcp.convDateToStr(utcp.convStrToDate(lectureCommunity.regDtime,'yyyyMMddHHmmssSSS'),'yyyy.MM.dd HH:mm')}</b></li>
        <li>
            <p></p> <span>조회수</span> <b>${lectureCommunity.hit}</b>
        </li>
        <li>
            <p></p> <span>작성자</span> <b>${lectureCommunity.addRegNmMasked}</b>
        </li>
        
    </ul>
    	<div class="tb_contents">
    	<%-- <p>${utcp.convNewLine(lectureCommunity.content)}</p> --%>
    	${utcp.convNewLine(lectureCommunity.content)}
    	</div>
    <div class="commentWrap">
		<div class="commentNum">
			<p>댓글 (<b>{{cmntList.length}}</b>)개
			</p>
		</div>

		<div class="commentList">
			<ul>
				<li v-for="o in cmntList">
					<div class="profile_info dp_b">
						<a href="javascript:" class="txt_name">{{o.regNm }}</a> <span class="txt_date">{{o.regDt|fltDate2Str('YY.MM.DD HH:mm') }}</span>
					</div>
					<div class="box_post dp_b">
						<p class="desc_info"><span v-if="!o.isUpd"> <span class="original_comment" v-html="text2Html(o.content)"> </span> <template v-if="o.regId == '${sessionScope.sessionUserInfo.userId }'">
								<button @click="updCommentForm(o)" type="button" class="btn04 btn_gray vt">수정</button>
								</template> <template v-if="o.regId == '${sessionScope.sessionUserInfo.userId}' || 5 > ${sessionScope.sessionUserInfo.userMemLvl }">
								<button @click="delCmntProc(o)" type="button" class="btn04 btn_grayl vt">삭제</button>
								</template>
						</span> <span v-if="o.isUpd"> <textarea placeholder="최대 500자 이내로 입력하세요." maxlength="600" class="comtTa" v-model="o.content"></textarea>
								<button @click="regCmntProc(o)" type="button" class="btn04 btn_blue vt">등록</button>
								<button @click="cnlCommentForm(o)" type="button" class="btn04 btn_gray vt">취소</button>
						</span></p>
					</div>
				</li>

			</ul>
		</div>

		<div class="commentWrite">
			<div class="box_textarea">
				<textarea placeholder="최대 500자 이내로 입력하세요." id="comment-0" maxlength="600" class="comtTa" onkeyup="fn_checkTextArea(this)"></textarea>
				<button type="button" class="comtBtn btn_blue" @click="regCmntProc()">등록</button>
			</div>
		</div>

	</div>
                           
</div>


            	<div class="fl"><a href="javascript:fn_boardList();" class="btn02 btn_grayl">목록</a></div>
            <c:if test="${sessionScope.sessionUserInfo.userId eq lectureCommunity.regId}">
	            	<div class="fr">
	            	<a href="javascript:fn_delBbs();" class="btn02 btn_gray">삭제</a>
	            	<a href="javascript:fn_udpBbs();" class="btn02 btn_blue">수정</a>
	            	</div>
	        </c:if>
	

<!--// view //-->
<script>

function fn_boardView(){
	$('#tabAction').val('communityView');
	$('#myEduForm').submit();
}
function fn_boardList(){
	$('#tabAction').val('communityList');
	$('#myEduForm').submit();
}
function fn_udpBbs() {
	$('#tabAction').val('communityReg');
	$('#myEduForm').submit();
}
function fn_delBbs() {
	if(confirm("정말 삭제하시겠습니까?")) {
		var boardIdx = document.querySelector("#boardIdx").value;
		$.ajax({
			url:'${utcp.ctxPath}/user/mypage/popup/communityDeletProc.json',
			type : "post",
			dataType : "json",
			data :  {
				boardIdx : boardIdx
			},
			success : function(r){
				alert(r.msg);
				fn_boardView();
			}
		});
	}else{
		return false;
	}
}
</script>

<script>
var vm_edu_board = new Vue({
	el : '#vm-edu-board',
	data : {
		cmntList : [],
		idx: '${param.boardIdx}',
	},
	mounted: function(){
		this.callBoard(this.idx);
	},
	methods:{
		callBoard: function (idx){
			var _this = this;
			var eduSeq = $('#edu_seq').val();
			$.ajax({
				data:{eduSeq : eduSeq, boardType : 'eduNotice', idx : idx},
				url:'${utcp.ctxPath}/user/edu/bbs/getInfoObj.ajax',
				success:function(r){
					if(r.result == 1){
						_this.cmntList = r.data.cmntList;
						$('textarea[id^=comment0]').val('');
					}
				}
			});
		},
		delCmntProc: function(o) {
			if(confirm("정말 삭제하시겠습니까?")) {
				var idx = o.idx;
				$.ajax({
					url:'${utcp.ctxPath}/user/edu/bbs/boardCommentDeleteProc.ajax',
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
		},
		regCmntProc: function (o){
			console.log(o);
			var bIdx = document.querySelector("#boardIdx").value;
			var idx = 0;
			var eduSeq = $('#edu_seq').val();
			var comment = $('#comment-0').val();
			if(!UTIL.isEmpty(o)){
			    idx = o.idx;
				comment = o.content;
			}
			$.ajax({
				url:'${utcp.ctxPath}/user/edu/bbs/boardCommentWriteProc.ajax',
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
		},
		updCommentForm : function(o){
			this.$set(o,'isUpd',true);
		},
		cnlCommentForm : function(o){
			this.$set(o,'isUpd',false);
		},
		
		text2Html : function(v){
			return fltCharToHtml(v);
		},
		
	},
	updated:function(){
	
	}
});
</script>