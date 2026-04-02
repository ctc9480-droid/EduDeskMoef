<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<div v-if="isActive" id="vm-10" class="poptestWrap">
	<table class="w100 tb07">
		<colgroup>
			<col style="" />
			<col style="" />
			<col style="" />
			<col style="" />
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
				<th>평가유형</th>
				<th>시험지명</th>
				<th>시험시간</th>
				<th>시간</th>
				<th>문제수</th>
				<th>합격기준</th>
				<th>응시자수</th>
				<th>상태</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<tr v-for="(o,i) in listObj.testList">
				<td>{{i+1}}</td>
				<td>{{o.addTryNoNm}}</td>
				<td class="tl"><a href="#none" @click="callRegView(o.testSeq)">{{o.testNm}} <span v-if="o.commentCnt>0">(<strong class="fc_red">{{o.commentCnt }}</strong>)
					</span>
				</a></td>
				<td>{{o.startDe|fltDate2Str('YYYY.MM.DD HH:mm')}} ~ {{o.endDe|fltDate2Str('YYYY.MM.DD HH:mm')}}</td>
				<td>{{o.timer}}분</td>
				<td>{{o.qstnCnt}}</td>
				<td>{{o.passMarks}}점</td>
				<td>{{o.testResultCnt}}</td>
				<td>
				<template v-if="o.status == 1">
				사용
				</template>
				<template v-else-if="o.status == 3">
				결과오픈
				</template>
				<template v-else>
				사용안함
				</template>
				</td>
				<td><a href="#none" @click="testResultStdntListView(o)" class="btn04 btn_orange stdnttbtn">보기</a></td>
			</tr>
			<tr v-if="!listObj.testList.length">
				<td colspan="11">데이터가 없습니다.</td>
			</tr>
		</tbody>
	</table>
	<div class="fr tc">
		<button @click="callRegView(0)" class="btn02 btn_blue fr">시험등록</button>
	</div>
</div>

<script>
var vm_10 = new Vue({
	el : '#vm-10',
	data : {
		isActive: true,
		listObj : {testList:[]},
	},
	methods:{
		callListView: function (pageNo){
			this.isActive = true;
			vm_tab10_testResult.isActive = false;
			var _this = this;
			var eduSeq = $('#eduSeq').val();
			$.ajax({
				url:'${utcp.ctxPath}/admin/quizTestEdu/getTestEduList.ajax',
				data:{eduSeq : eduSeq,  pageNo : pageNo},
				success:function(r){
					if(r.result == 1){
						_this.listObj = r.data;
						
						if(r.data.subList){
							vm_tab10_testReg.subList = r.data.subList;
						}
					}
				}
			});
		},
		callRegView: function (testSeq){
			vm_tab10_testReg.callRegView(testSeq);
		},
		testResultStdntListView: function(test){
			this.isActive = false;
			vm_tab10_testResult.callView(test);
		},
	},
	updated:function(){
		//console.log(this.infoObj);
	}
});

</script>

<!-- 시험등록 폼 모달 -->
<div class="remodal" data-remodal-id="md-tab10-testReg" id="vm-tab10-testReg" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc" data-remodal-options="closeOnOutsideClick: false">
	<button data-remodal-action="close" class="remodal-close" aria-label="Close"></button>
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC">시험등록</p>
		</div>
		<div class="modal-body pdl15 pdr15">
			<div class="tbBox1 mgt0">
				<table class="tl w100 tb04">
					<caption class="sound_only">시험지, 형태, 카테고리선택, 시험시간, 채점방식, 합격기준, 시험 설명 순으로 정보를 제공합니다.</caption>
					<tbody>
						<tr>
							<th class="">평가형태</th>
							<td class="tl">
								{{getTryNoNm(infoObj.tryNo)}}
							</td>
						</tr>
						<tr>
							<th class="">시험지명</th>
							<td class="tl"><input type="text" v-model="infoObj.testNm" id="title-6" class="ip2 " /></td>
						</tr>

						<tr>
							<th class="">사용여부</th>
							<td class="tl">
							<template v-if="infoObj.status == 3">
							결과오픈
							</template>
							<template v-else>
							<input type="radio" name="edu_10_reg_status" v-model="infoObj.status" value="1" id="edu-10-reg-status1"> 
							<label class="mgr10" for="edu-10-reg-status1"> 사용 </label> 
							<input type="radio" name="edu_10_reg_status" v-model="infoObj.status" value="2" id="edu-10-reg-status2"> 
							<label class="mgr10" for="edu-10-reg-status2"> 사용안함 </label></td>
							</template>
						</tr>
						<tr style="display:none;">
							<th class="">과목</th>
							<td class="tl">
								<select v-model="infoObj.subSeq">
									<option value="0">과목선택</option>
									<option :value="o.subSeq" v-for="(o,i) in subList">{{o.subNm}}</option>
								</select>
								<span style="font-size: smaller;">*과목 수료증,합격증 발급시 선택</span>
							</td>
						</tr>
						<tr>
							<th>시험지</th>
							<td><input v-model="infoObj.testTmplNm" type="text" class="ip2" readonly>
								<button @click="callTestTmplListView()" type="button" class="btn04 btn_gray">찾기</button></td>
						</tr>
						<tr>
							<th>시험시간</th>
							<td class="tl"><comp-datepicker v-model="infoObj.startDt" :data_max="infoObj.endDt"></comp-datepicker> <select v-model="infoObj.startHh">
									<option v-for="o in 23" :value="o < 10 ? '0' + o : o">{{o < 10 ? '0' + o : o}}</option>
							</select> <select v-model="infoObj.startMm">
									<option v-for="(o,i) in 60" :value="i < 10 ? '0' + i : i">{{i < 10 ? '0' + i : i}}</option>
							</select> ~ <comp-datepicker v-model="infoObj.endDt" :data_min="infoObj.startDt"></comp-datepicker> <select v-model="infoObj.endHh">
									<option v-for="o in 23" :value="o < 10 ? '0' + o : o">{{o < 10 ? '0' + o : o}}</option>
							</select> <select v-model="infoObj.endMm">
									<option v-for="(o,i) in 60" :value="i < 10 ? '0' + i : i">{{i < 10 ? '0' + i : i}}</option>
							</select>
							</td>
						</tr>
						<tr>
							<th>채점방식</th>
							<td><label for="md-edu10-markTp1" class="mgr10">
									<input type="radio" v-model="infoObj.markTp" name="md_edu10_markTp" value="1" id="md-edu10-markTp1" />
									자동채점
								</label> <label for="md-edu10-markTp2" class="mgr10">
									<input type="radio" v-model="infoObj.markTp" name="md_edu10_markTp" value="2" id="md-edu10-markTp2" />
									수동채점
								</label>
								<span style="font-size: smaller;">*자동채점: 시험결과 바로 확인 가능, 수동채점: 관리자(강사)가 출결오픈시 시험결과 확인 가능</span>
								</td>
						</tr>
						<tr>
							<th>합격기준</th>
							<td><input type="text" class="ip9" v-model="infoObj.passMarks"> 점 이상</td>
						</tr>
						<tr>
							<th class="">제한시간</th>
							<td class="tl"><input type="text" v-model="infoObj.timer" class="ip9" /> 분</td>
						</tr>
						<tr style="display: none;">
							<th>시험 설명</th>
							<td><textarea v-model="infoObj.descr" class="w100 h110"></textarea></td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<div class="modal-footer">
			
			<button @click="saveInfoObj(2)" class="remodal-confirm btn02 btn_green" v-if="infoObj.tryNo != 2 && infoObj.testSeq > 0 && infoObj.test2Cnt == 0">재평가 등록</button>
			<button @click="saveInfoObj" class="remodal-confirm btn02 btn_green">등록</button>
		</div>
	</div>
</div>
<script>
var vm_tab10_infoObj = {testSeq: 0,status: 1,startHh:'09',startMm:'00',endHh:'17',endMm:'00',tryNo: 1,prntTestSeq: 0,subSeq: 0, markTp: 2};
var vm_tab10_testReg = new Vue({
	el: '#vm-tab10-testReg',
	data: {
		infoObj: $.extend(true,{}, vm_tab10_infoObj),
		subList: [],
	},
	methods: {
		selectTestTmpl: function(o){
			this.infoObj.testTmplSeq = o.testTmplSeq;
			this.infoObj.testTmplNm = o.testTmplNm;
			this.infoObj.timer = o.timeLimit;
			this.$forceUpdate();
		},
		callTestTmplListView: function (){
			
			vm_tab10_testChoice.loadTestTmplList();
		},
		saveInfoObj: function (tryNo){
			var _this = this;
			var o = _this.infoObj;
			if(UTIL.isEmpty(o.testNm)){
				alert('시험지명을 입력하세요');
				return;
			}
			if(UTIL.isEmpty(o.subSeq) || o.subSeq == 0){
				//alert('과목을 선택하세요');
				//return;
			}
			if(UTIL.isEmpty(o.testTmplSeq) || o.testTmplSeq == 0){
				alert('시험지를 선택하세요');
				return;
			}
			if(UTIL.isEmpty(o.startDt)){
				alert('시험시작일을 선택하세요');
				return;
			}
			if(UTIL.isEmpty(o.passMarks)){
				alert('합격기준점수를 입력하세요');
				return;
			}
			if(UTIL.isEmpty(o.timer)){
				alert('제한시간을 선택하세요');
				return;
			}
			
			
			var eduSeq = $('#eduSeq').val();
			var startDt = o.startDt.replace(/-/g,'');
			var endDt = o.endDt.replace(/-/g,'');
			var ajaxData = {
					testSeq:		o.testSeq,
					eduSeq: 		eduSeq,
					testTmplSeq: 	o.testTmplSeq,
					testNm: 		o.testNm,
					startDeStr: 	startDt+''+o.startHh+''+o.startMm,
					endDeStr: 		endDt+''+o.endHh+''+o.endMm,
					status: 		o.status,
					timer:			o.timer,
					passMarks:		o.passMarks,
					markTp:			o.markTp,
					tryNo:			o.tryNo,
					prntTestSeq:	o.prntTestSeq,
					subSeq:			o.subSeq,
			};
			
			if(tryNo > 1){
				if(!confirm('재평가를 등록하시겠습니까?')){
					return;
				}
				ajaxData.tryNo = tryNo;
				ajaxData.prntTestSeq = o.testSeq;
				ajaxData.testSeq = 0;
			}
			
			$.ajax({
				type: 'post',
				data:ajaxData,
				url:'${utcp.ctxPath}/admin/quizTestEdu/saveTestEduMap.ajax',
				success:function(r){
					if(r.result == 1){
						vm_10.callListView(1);
						$('[data-remodal-id=md-tab10-testReg]').remodal().close();
					}else{
						alert(r.msg);
					}
				}
			});
		},
		callRegView: function(testSeq){
			$('[data-remodal-id=md-tab10-testReg]').remodal().open();
			location.href='#';
			
			var _this = this;
			var eduSeq = $('#eduSeq').val();
			if(testSeq == 0){
				this.infoObj = $.extend(true,{}, vm_tab10_infoObj);
				return;
			}
			$.ajax({
				data:{eduSeq : eduSeq, testSeq: testSeq},
				url:'${utcp.ctxPath}/admin/quizTestEdu/getTestEduMap.ajax',
				success:function(r){
					if(r.result == 1){
						_this.infoObj = r.data.test;
						_this.infoObj.testTmplNm = r.data.tmpl.testTmplNm;
						
						_this.infoObj.startDt = _this.$options.filters.fltDate2Str(_this.infoObj.startDe,'YYYY-MM-DD');
						_this.infoObj.startHh = _this.$options.filters.fltDate2Str(_this.infoObj.startDe,'HH');
						_this.infoObj.startMm = _this.$options.filters.fltDate2Str(_this.infoObj.startDe,'mm');
						_this.infoObj.endDt = _this.$options.filters.fltDate2Str(_this.infoObj.endDe,'YYYY-MM-DD');
						_this.infoObj.endHh = _this.$options.filters.fltDate2Str(_this.infoObj.endDe,'HH');
						_this.infoObj.endMm = _this.$options.filters.fltDate2Str(_this.infoObj.endDe,'mm');
						
						_this.infoObj.test2Cnt = r.data.test2Cnt;
						
						
					}
				}
			});
		},
		getTryNoNm: function(tryNo){
			if(tryNo == 2){
				return '재평가';
			}else{
				return '본평가';
			}
			return 'test';
		}
	},
});
</script>

<!-- 시험지 검색  -->
<div class="layerPop" id="vm-tab10-testChoice">
	<button class="remodal-close" @click="closeLayer"></button>
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC"><i class="fas fa-chevron-circle-right font_22 pdr5"></i>시험지 등록</p>
		</div>
		<div class="modal-body">
			<div class="dp_ib tc pd15">
				
				<span>
			        <label for="srchCtgry" class="sound_only">교육카테고리 선택</label>
			        <select id="srchCtgry" class="srchCtgry">
			            <option value="-1">교육과정</option>
			            <c:forEach var="data" items="${cateList}" varStatus="stat">
			                <option value="${data.qstnCtgSeq}" <c:if test='${vo.srchCtgry == data.qstnCtgSeq}'>selected</c:if>>${data.ctgNm}</option>
			            </c:forEach>
			        </select>
			    </span> 
			    <input type="hidden" id="srchCtgry" value="${vo.srchCtgry }"/>
			    <span id="vm-detailCtgry">
			        <label for="srchCtgry2" class="sound_only">2차과정 전체</label>
			        <select id="srchCtgry2" class="srchCtgry">
			            <option value="-1">2차과정 전체</option>
			        </select>
			    </span>
			    <span id="vm-detailCtgry2" style="">
			        <label for="srchCtgry3" class="sound_only">3차과정 전체</label>
			        <select id="srchCtgry3" class="srchCtgry">
			            <option value="-1">3차과정 전체</option>
			        </select> 
			    </span>
			<script>
			$(function() {
			    var select1 = -1;
			    var selectList1 = [];
			    $('#srchCtgry').change(function() {
			        fn_callCategory(this.value);
			    });
			    function fn_callCategory(ctgrySeq) {
			        if (ctgrySeq == 0) return;
			        select1 = -1;
			        $.ajax({
			            data: {
			                ctgrySeq: ctgrySeq
			            },
			            url: '${utcp.ctxPath}/comm/api/callQuizCategory.json',
			            async: false,
			            success: function(r) {
			                selectList1 = r.ctgryList;
			                select1 = -1;
			            }
			        });
			        $('#srchCtgry2').empty();
			        $('#srchCtgry2').append($('<option>', {
			            value: -1,
			            text: '2차과정 전체'
			        }));
			
			        $.each(selectList1, function(i, o) {
			            $('#srchCtgry2').append($('<option>', {
			            	value: o.qstnCtgSeq,
				             text: o.ctgNm
			            }));
			        });
			
			        
			    }
			
			    fn_callCategory('${vo.srchCtgry}');
			    select1 = '${vo.srchCtgry2 > 0?vo.srchCtgry2:-1}';
		        console.log(select1);
		        $('#srchCtgry2').val(select1);
			});
			
			
			$(function() {
			    var select2 = -1;
			    var selectList2 = [];
			    $('#srchCtgry2').change(function() {
			        fn_callCategory2(this.value);
			    });
			    function fn_callCategory2(ctgrySeq) {
			        if (ctgrySeq == 0) return;
			        select2 = -1;
			        $.ajax({
			            data: {
			                ctgrySeq: ctgrySeq
			            },
			            url: '${utcp.ctxPath}/comm/api/callQuizCategory.json',
			            async: false,
			            success: function(r) {
			                selectList2 = r.ctgryList;
			                select2 = -1;
			                
			                $('#vm-detailCtgry2').hide();
			                if(ctgrySeq>0){
			                	$('#vm-detailCtgry2').show();
			                }
			            }
			        });
			        $('#srchCtgry3').empty();
			        $('#srchCtgry3').append($('<option>', {
			            value: -1,
			            text: '3차과정 전체'
			        }));
			
			        $.each(selectList2, function(i, o) {
			            $('#srchCtgry3').append($('<option>', {
			            	value: o.qstnCtgSeq,
				             text: o.ctgNm
			            }));
			        });
			
			        select2 = '${vo.srchCtgry3 > 0?vo.srchCtgry3:-1}';
			        $('#srchCtgry3').val(select2);
			    }
			
			    fn_callCategory2('${vo.srchCtgry2}');
			});
			</script>
				
				<label for="md-edu-10-srchWrd" class="sound_only">검색어입력</label>
				<input type="text" id="md-edu-10-srchWrd" v-model="srchWrd" placeholder="시험지명 검색" class="btn04 btn_blackl tl mgr5" />
				<button @click="loadTestTmplList()" class="btn04 btn_black fr">검색</button>
			</div>
			<div class="tbBox" style="max-height: 500px;">
				<table class="tc w100 tb">
					<thead bgcolor="#f7f8fa">
						<tr>
							<th>NO</th>
							<th>시험지명</th>
							<th>문제수</th>
							<th>등록자</th>
							<th>등록일</th>
							<th></th>
						</tr>
					</thead>
					<tbody>
						<tr v-for="(o,i) in testTmplList">
							<td>{{testTmplList.length-i}}</td>
							<td>{{o.testTmplNm}}</td>
							<td>{{o.qstnCnt}}</td>
							<td>{{o.regNm}}</td>
							<td>{{o.regDe|fltDate2Str('YYYY.MM.DD')}}</td>
							<td><a href="#none" class="btn04 btn_blue" @click="selectTestTmpl(o)">선택</a></td>
						</tr>
						<tr v-if="testTmplList.length == 0">
							<td colspan="6">
						시험지가 없습니다.
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<div class="modal-footer">
			<div class="tc">
				<!-- <button onclick="fn_bulkUserRgs();" class="remodal-confirm btn01 btn_green">등록</button> -->
			</div>
		</div>
	</div>
</div>
<script>

var vm_tab10_testChoice = new Vue({
	el: '#vm-tab10-testChoice',
	data: {
		testTmplList: [],
		srchWrd: '',
	},
	mounted: function(){
		$('#vm-tab10-testChoice').hide();
	},
	methods: {
		closeLayer: function(){
			$('#vm-tab10-testChoice').hide();
		},
		selectTestTmpl: function(o){
			$('#vm-tab10-testChoice').hide();
			vm_tab10_testReg.selectTestTmpl(o);
		},
		loadTestTmplList: function(){
			$('#vm-tab10-testChoice').show();
			console.log('2',$('#vm-tab10-testChoice').attr('style'));
			//return;
			var _this = this;
			var eduSeq = $('#eduSeq').val();
			var srchWrd = _this.srchWrd;
			var srchCtgry = $('#srchCtgry').val();
			var srchCtgry2 = $('#srchCtgry2').val();
			var srchCtgry3 = $('#srchCtgry3').val();
			$.ajax({
				type : 'post',
				url:'${utcp.ctxPath}/admin/quizTestEdu/getTestTmplList.ajax',
				data : {eduSeq: eduSeq,srchWrd: srchWrd, srchCtgry: srchCtgry, srchCtgry2: srchCtgry2, srchCtgry3: srchCtgry3},
				async : false,
				success : function(r){
					if(r.result == 1){
						_this.testTmplList = r.data.testTmplList;
						console.log('3',$('#vm-tab10-testChoice').attr('style'));
					}else{
						alert(r.msg);
					}
				}
			});
			console.log('4',$('#vm-tab10-testChoice').attr('style'));
		},
		updated: function(){
			console.log('5',$('#vm-tab10-testChoice').attr('style'));
		}
	}
});
</script>

<!-- 시험상세 시작 -->
<div v-if="isActive" class="poptestStntWrap" id="vm-tab10-testResult">

	<div class="testStnTit">
		<h3></h3>
		<div class="testTop cf">
			<h3>
				시험명 : <strong>{{test.testNm}}</strong>
			</h3>
			<ul>
			<!-- 
				<li>과목: {{test.testNm}}</li>
				<li>능력단위명 : 품질정보관리</li>
			-->
				<li>평가유형 : {{test.addTryNoNm}}</li>
				<li>
					응시 <span class="time dp_ib fc_red">{{test.testResultCnt}}</span>명 / 전체 {{test.testStdntCnt }}명
				</li>
			</ul> 
		</div>
	</div>
	<div class="w50 cf mgt15 mgb15 dp_f flex-tl fl">
	</div>
	<div class="w50 cf mgt15 mgb15 dp_f flex-tr fl">
	
<%-- 
		<a href="#quizPopRgs" class="btn04 btn_greenl mgr5"><img src="${utcp.ctxPath }/resources/admin/images/excel.png" alt="excel" style="width: 15px; vertical-align: middle;">다운로드</a> 
 --%>
	</div>
	<table class="tb07 w100">
		<caption class="sound_only">시험 응시자 목록 - No, 이름, ID, 연락처, 제출시간, 채점여부, 평가점수, 성취수준 순으로 정보를 제공합니다.</caption>
		<thead>
			<tr>
				<th scope="col">No</th>
				<th scope="col">이름</th>
				<th scope="col">ID</th>
				<th scope="col">연락처</th>
				<th scope="col">제출시간</th>
				<th scope="col">상태</th>
				<th scope="col">평가점수</th>
				<th scope="col">성취수준</th>
				<th scope="col"></th>
			</tr>
		</thead>
		<tbody>
			<tr v-for="(o,i) in stdntList">
				<td>{{ stdntList.length - i }}</td>
				<td>{{o.userNm}}</td>
				<td class="tl">{{o.loginId}}</td>
				<td>{{o.mobile}}</td>
				<td>
				<template v-if="o.testResultState > 0">
				{{o.endDe|fltDate2Str('YYYY.MM.DD HH:mm')}}
				</template>
				</td>
				<td>{{o.addTestResultStateNm}}</td>
				<td>{{o.marks}}/100</td>
				<td>{{o.addGradeNm}}</td>
				<td><a href="javascript:;" @click="openStdntResult(o)" class="btn04 btn_orange testResultBtn">결과보기</a>
				<a href="#none" @click="reload"><i class="fa fa-refresh" aria-hidden="true"></i></a>
				</td>
			</tr>
		</tbody>
	</table>
	<div class="fl tc">
		<button @click="moveTestList()" class="btn02 btn_gray fr">뒤로</button>
	</div>
	<div class="fr tc">
		<button @click="exceldown1" class="btn02 btn_green" >엑셀</button>
		<template v-if="test.status == 1">
		<button @click="openResultAll('Y')" class="btn02 btn_green" >결과오픈</button>
		</template>
		<template v-if="test.status == 3">
		<button @click="openResultAll('N')" class="btn02 btn_green" >결과닫기</button>
		</template>
	</div>
</div>
<script>
var vm_tab10_testResult = new Vue({
	el: '#vm-tab10-testResult',
	data: {
		isActive: false,
		test: {},
		stdntList: [],
	},
	methods: {
		reload: function(){
			this.callView(this.test);
		},
		exceldown1: function(){
			var testSeq = this.test.testSeq;
			location.href = "${utcp.ctxPath}/admin/excel/quizTestEduStdntListExcel.do?eduSeq=${param.eduSeq}&testSeq="+testSeq;
		},
		callView: function(test){
			this.isActive = true;
			this.test = test;
			
			var _this = this;
			var eduSeq = '${param.eduSeq}';
			var testSeq = test.testSeq;
			$.ajax({
				data: {eduSeq: eduSeq, testSeq: testSeq,},
				url: '${utcp.ctxPath}/admin/quizTestEdu/getTestResult.ajax',
				success: function(r){
					if(r.result == 1){
						_this.stdntList = r.data.stdntList;
					}
				}
			});
		},
		openStdntResult: function(o){
			var eduSeq = '${param.eduSeq}';
			var testSeq = o.testSeq;
			var userId = o.userId;
			var popUrl ='${utcp.ctxPath}/user/quizTestEdu/popup/resultStdnt.do?eduSeq='+eduSeq+'&testSeq='+testSeq+'&userId='+userId;
		   	var popOption = 'width=900px, height=1080px, resizable=no, location=no, top=150px, left=350px;';
		   	window.open(popUrl,"훈련일지",popOption); 
		},
		moveTestList: function(){
			vm_10.callListView(1);
		},
		openResultAll: function(openYn){
			if('Y' == openYn){
				if(!confirm('모든학생에게 결과가 오픈됩니다. 계속 진행하시겠습니까')){
					return;
				}
			}
			var _this = this;
			var eduSeq = '${param.eduSeq}';
			var testSeq = this.test.testSeq;
			$.ajax({
				type: 'post',
				url: '${utcp.ctxPath}/admin/quizTestEdu/openResultTest.ajax',
				data: {eduSeq: eduSeq, testSeq: testSeq,openYn: openYn},
				success: function(r){
					console.log(r);
					if(r.result == 1){
						alert('정상적으로 처리되었습니다.');
						_this.callView(_this.test);
					}
				},
			});
		},
		fn_isResultOpen: function(){
			//임시 처리
			for(var i in this.stdntList){
				var chk = this.stdntList.some(o => o.testResultState != 3 );
				if(chk){
					return false;
				}
			}
			return true;
		},
	},
});
</script>


