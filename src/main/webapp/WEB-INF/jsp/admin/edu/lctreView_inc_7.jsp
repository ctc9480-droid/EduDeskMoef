<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<div id="vm-7">
	<div v-if="formKey == 'list'">
		
		<table width="100%" class="tb07">
			<colgroup>
				<col style="width: 50px;" />
				<col style="" />
				<col style="" />
				<col style="" />
				<col style="" />
				<col style="" />
				<col style="" />
			</colgroup>
			<thead>
				<tr>
					<th>No</th>
					<th>설문지명</th>
					<th>설문기간</th>
					<th>작성자</th>
					<th>등록일</th>
					<th>QR</th>
					<th>결과</th>
				</tr>
			</thead>
			<tbody>
<%-- 				<c:if test="${lctre.fbIdx > 0 }"> --%>
<!-- 				<tr> -->
<!-- 					<td>메인</td> -->
<!-- 					<td colspan="2" class="tl">{{listObj.fb.title}}</td> -->
<!-- 					<td>{{listObj.fb.regNm}}</td> -->
<!-- 					<td>{{listObj.fb.regTime|fltDate2Str('YYYY-MM-DD HH:mm')}}</td> -->
<!-- 					<td><button type="button" @click="" class="btn04 btn_green" >생성</button></td> -->
<!-- 					<td> -->
<%-- 					<button type="button" @click="openResult(0,${lctre.fbIdx})" class="btn04 btn_blue" >결과</button> --%>
<!-- 					</td> -->
<!-- 				</tr> -->
<%-- 				</c:if> --%>
				<tr v-for="(o,i) in listObj.feList">
					<td>
						<%-- {{listObj.pageNavi.totalRecordCount - (i+((listObj.pageNavi.currentPageNo-1)*listObj.pageNavi.recordCountPerPage))}} --%> {{listObj.feList.length-i}}
					</td>
					<td class="tl"><a href="#none" @click="callRegView(o.feSeq)">{{o.title}}</a></td>
					<td>{{o.startDe|fltDate2Str('YYYY-MM-DD HH:mm')}} ~ {{o.endDe|fltDate2Str('YYYY-MM-DD HH:mm')}}</td>
							<td>{{o.regNm}}</td>
							<td>{{o.regDe|fltDate2Str('YYYY-MM-DD HH:mm')}}</td>
							<td><button type="button" @click="genQr(4,o)" class="btn04 btn_green" >생성</button></td>
							<td><button type="button" @click="openResult(o.feSeq,o.fbIdx)" class="btn04 btn_blue" >결과</button></td>
				</tr>
				<tr v-if="!listObj.feList.length">
					<td colspan="7">데이터가 없습니다.</td>
				</tr>
			</tbody>
		</table>
		<div class="fr tc">
			<button @click="callRegView(0)" class="btn01 btn_greenl">등록</button>
		</div>
	</div>

	<div v-if="formKey == 'reg'">
		<table class="w100 tb02 tl">
			<tbody>
				<tr style="display: none;">
					<th class="tc">설문명</th>
					<td class="tl"><input type="text" v-model="infoObj.fbNm" class="ip2" maxlength="50" value="설문명" /></td>
				</tr>
				<tr>
					<th class="tc">설문지선택</th>
					<td class="tl"><input type="text" readonly v-model="infoObj.title" @click="callFeedbackListView()" class="ip2 " /></td>
				</tr>
				<tr>
					<th class="tc">설문기간</th>
					<td class="tl">
					<comp-datepicker v-model="infoObj.startDt" readonly :data_max="infoObj.endDt" ></comp-datepicker>
					
					<select v-model="infoObj.startHh">
							<option v-for="o in 23" :value="o < 10 ? '0' + o : o">{{o < 10 ? '0' + o : o}}</option>
					</select> <select v-model="infoObj.startMm">
							<option v-for="(o,i) in 60" :value="i < 10 ? '0' + i : i">{{i < 10 ? '0' + i : i}}</option>
					</select> ~ 
					<comp-datepicker v-model="infoObj.endDt" readonly :data_min="infoObj.startDt"></comp-datepicker>
					<select v-model="infoObj.endHh">
							<option v-for="o in 23" :value="o < 10 ? '0' + o : o">{{o < 10 ? '0' + o : o}}</option>
					</select> <select v-model="infoObj.endMm">
							<option v-for="(o,i) in 60" :value="i < 10 ? '0' + i : i">{{i < 10 ? '0' + i : i}}</option>
					</select></td>
				</tr>
				<tr style="display: none;">
					<th class="tc">사용여부</th>
					<td class="tl"><input type="radio" v-model="infoObj.state" name="edu_7_reg_state" value="1" id="edu-7-reg-state1" /> <label for="edu-7-reg-state1">사용</label> <input type="radio" v-model="infoObj.state" name="edu_7_reg_state" value="2" id="edu-7-reg-state2" /> <label for="edu-7-reg-state2">사용안함</label></td>
				</tr>
			</tbody>
		</table>

		<div class="fr tc">
			<button type="button" @click="saveInfoObj" class="btn01 btn_greenl">저장</button>
			<button type="button" @click="callListView(1)" class="btn01 btn_grayl">취소</button>
		</div>
	</div>
	<div class="remodal" data-remodal-id="md-edu-7-feedbackList" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc">
		<button data-remodal-action="close" class="remodal-close" aria-label="Close"></button>
		<div class="modal-content">
			<div class="modal-header">
				<p class="tit alignC"><i class="fas fa-chevron-circle-right font_22 pdr5"></i>설문지 등록</p>
			</div>
			<div class="modal-body">
				<div class="dp_ib tc pd15">
					<label for="md-edu-7-srchWrd" class="sound_only">검색어입력</label>
					<input type="text" id="md-edu-7-srchWrd" v-model="srchWrd" placeholder="설문지 검색" class="btn04 btn_blackl tl mgr5" />
					<button @click="loadFeedbackList" class="btn04 btn_black fr">검색</button>
				</div>
				<div class="tbBox">
					<table class="tc w100 tb">
						<thead bgcolor="#f7f8fa">
							<tr>
								<th>NO</th>
								<th>설문지명</th>
								<th>등록자</th>
								<th>등록일</th>
								<th></th>
							</tr>
						</thead>
						<tbody>
							<tr v-for="(o,i) in feedbackList">
								<td>{{feedbackList.length-i}}</td>
								<td>{{o.title}}</td>
								<td>{{o.regNm}}</td>
								<td>{{o.regTime|fltDate2Str('YYYY.MM.DD')}}</td>
								<td><a href="#none" class="btn04 btn_blue" @click="selectFeedback(o)">선택</a></td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<div class="modal-footer">
				
			</div>
		</div>
	</div>
</div>

<script>
var init_vm7_infoObj = {feSeq: 0, fbNm: '설문명', startHh: '09', startMm: '00', endHh: '23', endMm: '59', state: 1};
var vm_7 = new Vue({
	el : '#vm-7',
	data : {
		formKey : 'list',
		listObj : {feList : []},
// 		listObj : {fb : null, feList : []},
		infoObj : $.extend(true, {}, init_vm7_infoObj),
		feedbackList : [],
		srchWrd : '',
	},
	methods:{
		callListView: function (pageNo){
			var _this = this;
			var eduSeq = $('#eduSeq').val();
			$.ajax({
				url:'${utcp.ctxPath}/admin/feedbackEdu/getFeedbackEduList.ajax',
				data:{eduSeq : eduSeq,  pageNo : pageNo},
				success:function(r){
					if(r.result == 1){
						_this.listObj = r.data;
						_this.formKey = 'list';
					}
				}
			});
		},
		saveInfoObj: function (){
			var _this = this;
			var o = _this.infoObj;
			if(UTIL.isEmpty(o.fbNm))	{ alert('설문명을 입력하세요');return; }
			if(UTIL.isEmpty(o.fbIdx))	{ alert('설문지를 선택하세요');return; }
			if(UTIL.isEmpty(o.startDt) || UTIL.isEmpty(o.endDt))	{ alert('설문기간을 선택하세요');return; }
			
			var eduSeq = $('#eduSeq').val();
			var startDt = o.startDt.replace(/-/g,'');
			var endDt = o.endDt.replace(/-/g,'');
			var ajaxData = {
					feSeq:			o.feSeq,
					eduSeq: 		eduSeq,
					fbIdx: 			o.fbIdx,
					fbNm: 			o.fbNm,
					startDeStr: 	startDt+''+o.startHh+''+o.startMm,
					endDeStr: 		endDt+''+o.endHh+''+o.endMm,
					state: 			o.state,
			};
			$.ajax({
				data:ajaxData,
				url:'${utcp.ctxPath}/admin/feedbackEdu/saveFeedbackEduMap.ajax',
				success:function(r){
					if(r.result == 1){
						_this.callListView(1);
					}else{
						alert(r.msg);
					}
				}
			});
		},
		callRegView: function (feSeq){
			var _this = this;
			if(feSeq == 0){
				this.infoObj = $.extend(true,{}, init_vm7_infoObj);
				_this.formKey = 'reg';
				return;
			}
			
			//if(!idx){
			//	vm_6.infoObj={};
			//}
			var eduSeq = $('#eduSeq').val();
			$.ajax({
				data:{eduSeq : eduSeq, feSeq: feSeq},
				url:'${utcp.ctxPath}/admin/feedbackEdu/getFeedbackEduMap.ajax',
				success:function(r){
					if(r.result == 1){
						_this.infoObj = r.data.fe;
						_this.infoObj.title = r.data.feedbackInfo.fb.title;
						
						_this.infoObj.startDt = _this.$options.filters.fltDate2Str(_this.infoObj.startDe,'YYYY-MM-DD');
						_this.infoObj.startHh = _this.$options.filters.fltDate2Str(_this.infoObj.startDe,'HH');
						_this.infoObj.startMm = _this.$options.filters.fltDate2Str(_this.infoObj.startDe,'mm');
						_this.infoObj.endDt = _this.$options.filters.fltDate2Str(_this.infoObj.endDe,'YYYY-MM-DD');
						_this.infoObj.endHh = _this.$options.filters.fltDate2Str(_this.infoObj.endDe,'HH');
						_this.infoObj.endMm = _this.$options.filters.fltDate2Str(_this.infoObj.endDe,'mm');
						
						_this.formKey = 'reg';
					}
				}
			});
		},
		
		callFeedbackListView: function (){
			
			this.srchWrd = '';
			this.loadFeedbackList();
			$('[data-remodal-id=md-edu-7-feedbackList]').remodal().open();
		},
		loadFeedbackList: function(){
			var _this = this;
			var srchWrd = this.srchWrd;
			$.ajax({
				type : 'get',
				url:'${utcp.ctxPath}/admin/feedback/feedbackList.json',
				async : false,
				data: {srchWrd: srchWrd},
				success : function(r){
					if(r.result == 1){
						_this.feedbackList = r.feedbackList;
					}else{
						alert('에러가 발생하였습니다.');
					}
				}
			});
		},
		selectFeedback: function(o){
			$('[data-remodal-id=md-edu-7-feedbackList]').remodal().close();
			this.infoObj.fbIdx = o.idx;
			this.infoObj.title = o.title;
			this.$forceUpdate();
		},
		openResult: function(feSeq,fbIdx){
			var eduSeq = $('#eduSeq').val();
			window.open('${utcp.ctxPath}/admin/feedback/popup/feedbackResult.do?fbIdx='+fbIdx+'&eduSeq='+eduSeq+'&feSeq='+feSeq,'feedbackResult','width=800,height=1000');
		},
		genQr: function(qrType,o){
			if(!confirm('qr생성 하시겠습니까?')){
				return;
			}
			var _this = this;
			var qrStartDt = _this.$options.filters.fltDate2Str(o.startDe, 'YYYY-MM-DD HH:mm');
			var qrEndDt = _this.$options.filters.fltDate2Str(o.endDe, 'YYYY-MM-DD HH:mm');
			var qrNm = o.title;
			var eduSeq = '${param.eduSeq}';
			var feSeq = o.feSeq
			
// 			if(!this.checkTimeFormat(qrBeginTm) || !this.checkTimeFormat(qrEndTm) ){
// 				alert('시간형태가 올바르지 않습니다. HH:mm형태로 입력하시기 바랍니다.');
// 				return;
// 			}

			$.ajax({
				type:'post',
				data:{eduSeq: eduSeq, feSeq: feSeq, qrType: qrType, qrStartDt: qrStartDt, qrEndDt: qrEndDt, qrNm: qrNm},
				url:'${utcp.ctxPath}/admin/ajax/createQrFeedback.ajax',
				success:function(r){
					console.log(r);
					if(r.result == 1){
// 						location.reload();
						window.open('${utcp.ctxPath}/DATA/upload/web/qr/feedback_${lctre.eduSeq }_'+o.feSeq+'_4.png?${utcp.getNow2Str('yyyyMMddHHmmss')}','preView','width=310,height=420');
					}
				}
			});
		},		
	},
	updated:function(){
		//console.log(this.infoObj);
	}
});
</script>
