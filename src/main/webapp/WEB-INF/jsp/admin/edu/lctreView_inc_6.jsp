<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<div id="vm-6">
	<div v-if="formKey == 'list'">
		<table width="100%" class="tb02 tc">
			<colgroup>
				<col style="width: 6%;" />
				<col style="width: 49%;" />
				<col style="width: 10%;" />
				<!-- <col style="width:10%;"/> -->
				<col style="width: 15%;" />
				<col style="width: 10%;" />
			</colgroup>
			<thead>
				<tr>
					<th>번호</th>
					<th>제목</th>
					<th>작성자</th>
					<!-- <th>첨부</th> -->
					<th>등록일</th>
					<th>조회수</th>
				</tr>
			</thead>
			<tbody>
				<tr v-for="(o,i) in listObj.lectureBoardList">
					<td>{{listObj.pageNavi.totalRecordCount - (i+((listObj.pageNavi.currentPageNo-1)*listObj.pageNavi.recordCountPerPage))}}</td>
					<td class="tl"><a href="#none" :onclick="'callViewForm('+o.idx+')'">{{o.title}} <span v-if="o.commentCnt>0">(<strong class="fc_red">{{o.commentCnt }}</strong>)
						</span>
					</a></td>
					<td>{{o.regNm}}</td>
					<!-- <td><a href="#none" download=""><img src="${utcp.ctxPath}/resources/admin/images/bd_file.png" alt="fileDownload"></a></td> -->
					<td>{{o.regDtime|fltDt2Str('YYYYMMDDHHmmssSSS','YYYY-MM-DD')}}</td>
					<td>{{o.hit}}</td>
				</tr>
				<tr v-if="!listObj.lectureBoardList.length">
					<td colspan="5">데이터가 없습니다.</td>
				</tr>
			</tbody>
		</table>
		<div class="fr tc">
			<button onclick="callRegForm()" class="btn01 btn_greenl">등록</button>
		</div>
		<!--// paging //-->
		<div class="page">
			<div class="inner cf">
				<template v-if="listObj.pageNavi.currentPageNo != 1">
				<div class="page_prev0">
					<a href="javascript:;" :onclick="'fn_pageTab6('+1+');'">&lt; 처음</a>
				</div>
				<div class="page_prev0">
					<a href="javascript:;" :onclick="'fn_pageTab6('+(listObj.pageNavi.currentPageNo - 1)+');'">&lt; 이전</a>
				</div>
				</template>
				<template v-for="o in (listObj.pageList)"> <template v-if="o == listObj.pageNavi.currentPageNo">
				<div class="page_now">
					<a>{{o}}</a>
				</div>
				</template> <template v-else>
				<div class="page_nomal">
					<a href="javascript:;" :onclick="'fn_pageTab6('+o+');'">{{o}}</a>
				</div>
				</template> </template>
				<template v-if="listObj.pageNavi.currentPageNo != listObj.pageNavi.totalPageCount && listObj.pageNavi.totalPageCount > 0">
				<div class="page_next0">
					<a href="javascript:;" :onclick="'fn_pageTab6('+(listObj.pageNavi.currentPageNo + 1) + ');'">다음 &gt;</a>
				</div>
				<div class="page_next0">
					<a href="javascript:;" :onclick="'fn_pageTab6('+(listObj.pageNavi.lastPageNo ) + ');'">끝 &gt;</a>
				</div>
				</template>
			</div>
		</div>
		<!--// paging //-->
	</div>
	<div v-if="formKey == 'reg'">
		<form id="lecture-board-form">
			<table class="w100 tb02 tl">
				<tbody>
					<!-- <tr>
					<th class="tc">공지</th>
					<td class="tl"><input type="checkbox" id="bdNoti" name="" class="tl"/><label for="bdNoti" class="dp_ib pdl10">공지</label></td>
				</tr> -->
					<tr>
						<th class="tc">제목</th>
						<td><input type="text" v-model="infoObj.title" id="title-6" class="ip4 tl" /></td>
					</tr>
					<tr>
						<th class="tc">내용</th>
						<td><comp6-ckeditor v-model="infoObj.content">
							</comp8-ckeditor></td>
					</tr>
					<tr>
						<th class="tdbg3 tc"><label for="fileBox_01">첨부파일</label></th>
						<td class="tl" colspan="">
							<!-- dropzone html --> <comp6-dropzone></comp6-dropzone>
							<div id="area-filelist">
								<ul>
									<li v-for="o in fileList">
										{{o.fileOrg }}, <a href="#none" :onclick="'fn_delFile('+o.fileSeq+')'">[삭제]</a>
									</li>
								</ul>
							</div>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
		<div class="fr tc">
			<button type="button" onclick="saveInfoObj()" class="btn01 btn_greenl">저장</button>
			<button type="button" onclick="callEduBbsListData()" class="btn01 btn_grayl">취소</button>
		</div>

		<!-- dropzone -->
		<script>

</script>
	</div>
	<div v-if="formKey == 'view'">
		<div class="tstyle_view">
			<div class="title">{{infoObj.title}}</div>
			<ul class="head info">
				<li>
					<span>작성일</span> <b>{{infoObj.regDtime|fltDt2Str('YYYYMMDDHHmmssSSS','YYYY-MM-DD')}}</b>
				</li>
				<li>
					<p></p> <span>조회수</span> <b>{{infoObj.hit}}</b>
				</li>
				<li>
					<p></p> <span>작성자</span> <b>{{infoObj.regNm}}</b>
				</li>

				<li class="" v-for="o in fileList">
					<a :href="'${utcp.ctxPath}/admin/edu/bbs/download/'+o.fileSeq+'.do'">
						<p></p>
					<img src="${utcp.ctxPath }/resources/user/image/icon/down_icon.gif" alt="파일 아이콘" class="vm"> <b>{{o.fileOrg}}</b>
					</a>
				</li>
			</ul>
			<div class="tb_contents" v-html="infoObj.content.replace(/\n/g, '<br>')"></div>

			<div class="w100 cf mgt20 mgb20">
				<a onclick="callEduBbsListData()" class="fl btn02 btn_gray">목록</a> 
				<a v-if="'${sessionScope.sessionUserInfo.userId }'==infoObj.regId" :onclick="'callRegForm('+infoObj.idx+')'" href="javascript:;" class="fr btn02 btn_blue">수정</a> 
				<a :onclick="'fn6_delEduBbs('+infoObj.idx+')'" href="javascript:;" class="fr btn02 btn_orange mgr5">삭제</a>
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
								<a href="javascript:" class="txt_name">{{o.regNm }}</a> <span class="txt_date">{{o.updDt|fltDate2Str('YY.MM.DD HH:mm') }}</span>
							</div>
							<div class="box_post dp_b">
								<p class="desc_info">
									<span  v-if="!o.isUpd">
									<span class="original_comment" v-html="text2Html(o.content)"> 
									</span>
									<template v-if="o.regId == '${sessionScope.sessionUserInfo.userId }'">
									<button  @click="updCommentForm(o)" type="button" class="btn04 btn_gray vt" >수정</button>
									</template>
									<template v-if="o.regId == '${sessionScope.sessionUserInfo.userId}' || 5 > ${sessionScope.sessionUserInfo.userMemLvl }">
									<button   @click="delCmntProc(o)" type="button" class="btn04 btn_grayl vt">삭제</button>
									</template>
									</span>
								
									<span v-if="o.isUpd">
									<textarea placeholder="최대 500자 이내로 입력하세요." maxlength="600" class="comtTa" v-model="o.content"></textarea>
									<button @click="regCmntProc(o)" type="button" class="btn04 btn_blue vt" >등록</button>
									<button @click="cnlCommentForm(o)" type="button" class="btn04 btn_gray vt" >취소</button>
									</span>
								</p>
							</div>
						</li>
						
					</ul>
				</div>

				<div class="commentWrite">
					<div class="box_textarea">
						<textarea placeholder="최대 500자 이내로 입력하세요." id="comment6-0" maxlength="500" class="comtTa" onkeyup="fn_checkTextArea(this)"></textarea>
						<button type="button" class="comtBtn btn_blue" @click="regCmntProc()">등록</button>
					</div>
				</div>

			</div>
			<!--// commentWrap //-->
		</div>


		
	</div>
</div>



<script>


	var myDropzone6;
	Vue.component('comp6-ckeditor', {	
		template: '<textarea v-ckeditor id="ckeditCn6" v-model="value"></textarea>',
		props:['value'],
		directives : {
	        ckeditor : {
	            inserted:function (el, binding, vNode) {
					
					CKEDITOR.config.allowedContent = true;
					CKEDITOR.replace('ckeditCn6', {
						filebrowserUploadUrl : '${utcp.ctxPath}/editot/popupUpload.json?gubun=lectureBoard&prefixStr=eduNotice_'+$('#eduSeq').val(),
						height : 400,
					});
	            }
	        }
	    },
	});
	Vue.component('comp6-dropzone', {	
		template: '<div id="area-dropzone" class="dropzone" v-dropzone> <div class="fallback"> <input name="file" type="file" multiple /> </div> <input name="image_url" id="image_url" type="hidden" /></div>',
		props:['value'],
		directives : {
	        dropzone : {
	            inserted:function (el, binding, vNode) {
	            	if (typeof myDropzone !== "undefined"){
						myDropzone6.destroy();
					}
					Dropzone.autoDiscover = false;
					myDropzone6 = new Dropzone("#area-dropzone", {
						url : "${utcp.ctxPath}/admin/edu/bbs/dropzoneUpload.ajax",
						paramName: "file",
				        maxFilesize: 5,
				        maxFiles: 5,
				        //acceptedFiles: "image/*,application/pdf,.xlsx",
				        //acceptedFiles: null,	// dykim, 210701, 모든 파일 업로드 가능
				        autoProcessQueue: false,
				        uploadMultiple: true,
				        addRemoveLinks:true,
				        parallelUploads:10,
				        dictDefaultMessage:'+ 마우스로 파일을 끌고 오거나 여기를 클릭하세요',
				        init: function () {
				        	this.on("success", function(file, returnedData, myEvent) {
				        		
				        		callViewForm(vm_6.infoObj.idx);
				           	}); 
				        	this.on('sending', function(file, xhr, formData) {
				        		if(formData.get('idx')==null){
				        			
				        			formData.append('idx', vm_6.infoObj.idx);
				        			formData.append('eduSeq', $('#eduSeq').val());
				        		}
				           	});  
				        }
					});
	            }
	        }
	    },
	});
	var vm_6 = new Vue({
		el : '#vm-6',
		data : {
			formKey : 'list',
			listObj : {pageNavi:{},lectureBoardList:[]},
			infoObj : {idx : 0},
			fileList : [],
			cmntList : [],
		},
		methods:{
			delCmntProc: function(o) {
				if(confirm("정말 삭제하시겠습니까?")) {
					var idx = o.idx;
					$.ajax({
						url:'${utcp.ctxPath}/admin/edu/bbs/boardCommentDeleteProc.ajax',
						type : "post",
						dataType : "json",
						data :  {
							idx : idx
						},
						success : function(r){
							if(r.result == 1){
								callViewForm(vm_6.infoObj.idx);
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
				var bIdx = this.infoObj.idx;
				var idx = 0;
				var eduSeq = $('#edu_seq').val();
				var comment = $('#comment6-0').val();
				if(!UTIL.isEmpty(o)){
				    idx = o.idx;
					comment = o.content;
				}
				$.ajax({
					url:'${utcp.ctxPath}/admin/edu/bbs/boardCommentWriteProc.ajax',
					type : "post",
					dataType : "json",
					data :  {
						bIdx : bIdx , comment : comment ,idx : idx,eduSeq : eduSeq
					},
					success : function(r){
						if(r.result == 1){
							$('#comment6-0').val('');
							callViewForm(vm_6.infoObj.idx);
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
	
	/**
	페이징 가능한 리스트 조회
	*/
	function callEduBbsListData(pageNo){
		var eduSeq = $('#eduSeq').val();
		$.ajax({
			url:'${utcp.ctxPath}/admin/edu/bbs/getListObj.ajax',
			data:{eduSeq : eduSeq, boardType : 'eduNotice', pageNo : pageNo},
			success:function(r){
				if(r.result == 1){
					vm_6.listObj = r.data;
					
					vm_6.listObj.pageList=[];
					for(var i=r.data.pageNavi.firstPageNoOnPageList;i<=r.data.pageNavi.lastPageNoOnPageList;i++){
						vm_6.listObj.pageList.push(i);
					}
					
					vm_6.formKey = 'list';
				}
			}
		});
	}
	function saveInfoObj(){
		var eduSeq = $('#eduSeq').val();
		
		var idx = vm_6.infoObj.idx;
		var title = vm_6.infoObj.title;
		if(title == '' || title == null){
			$('#title-6').focus();
			alert('제목은 필수입력입니다.');
			return false;
		}
		//var content = vm_6.infoObj.content;
		
		var content;
		if (CKEDITOR.instances.ckeditCn6.getData() == "") {
			alert('내용은 필수입력입니다.');
			CKEDITOR.instances.ckeditCn6.focus();
			return false;
		} else {
			content = CKEDITOR.instances.ckeditCn6.getData();
		}
		
		$.ajax({
			data:{eduSeq : eduSeq, boardType : 'eduNotice', idx : idx, title : title, content : content },
			url:'${utcp.ctxPath}/admin/edu/bbs/saveInfoObj.ajax',
			success:function(r){
				if(r.result == 1){
					vm_6.infoObj.idx = r.data.idx;
					if(myDropzone6.getQueuedFiles().length > 0){
						homeLoader.show();
	                    myDropzone6.processQueue();//excute upload!!
	                }else{
						callViewForm(r.data.idx);
	                }
				}else{
					alert(r.msg);
				}
			}
		});
	}
	function callRegForm(idx){
		//if(!idx){
		//	vm_6.infoObj={};
		//}
		var eduSeq = $('#eduSeq').val();
		$.ajax({
			data:{eduSeq : eduSeq, boardType : 'eduNotice', idx : idx},
			url:'${utcp.ctxPath}/admin/edu/bbs/getInfoObj.ajax',
			success:function(r){
				if(r.result == 1){
					vm_6.infoObj = r.data.lectureBoardInfo;
					vm_6.fileList = r.data.attachList;
					
					console.log(1);
					vm_6.formKey = 'reg';
				}
			}
		});
	}
	function callViewForm(idx){
		var eduSeq = $('#eduSeq').val();
		$.ajax({
			data:{eduSeq : eduSeq, boardType : 'eduNotice', idx : idx},
			url:'${utcp.ctxPath}/admin/edu/bbs/getInfoObj.ajax',
			success:function(r){
				if(r.result == 1){
					vm_6.infoObj = r.data.lectureBoardInfo;
					vm_6.fileList = r.data.attachList;
					vm_6.cmntList = r.data.cmntList;
					vm_6.formKey = 'view';
					$('.comment-list textarea[id^=comment6-0]').val('');
					$('.box_textarea_edit').css('display','')
				}
			}
		});
	}
	function fn_pageTab6(pageNo){
		callEduBbsListData(pageNo);
	}
	function fn_delFile(fileSeq){
		$.ajax({
			url:'${utcp.ctxPath}/admin/edu/bbs/deleteFile.ajax',
			data:{fileSeq:fileSeq},
			success:function(r){
				if(r.resultCode==1){
					callRegForm(vm_6.infoObj.idx);
				}
			}
		});
	}
	//알림마당 삭제 함수
	function fn6_delEduBbs(idx){
		if(!confirm('삭제 하시겠습니까?')){
			return;
		}
		$.ajax({
			type : 'post',
			url : '${utcp.ctxPath}/admin/edu/bbs/deleteInfoObj.ajax',
			data : {idx : idx},
			success : function(r){
				if(r.result == 1){
					callEduBbsListData();
				}else{
					alert(r.msg);
				}
			}
		});
	}
	
	
	
	function fn_checkTextArea(_this){
		console.log(1,_this.value);
		var limitLength = 500;
		if(_this.value.length > limitLength){
			_this.value = _this.value.slice(0,limitLength);
			console.log(2,_this.value);
			
		}
	}
</script>

