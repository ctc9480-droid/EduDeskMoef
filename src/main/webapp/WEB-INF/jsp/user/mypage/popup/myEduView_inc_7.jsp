<%@page import="com.educare.component.VarComponent"%>
<%@page import="com.educare.edu.quizBank.web.QuizBankComponent"%>
<%@page import="com.educare.edu.member.service.SessionUserInfoHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<div id="vm-mt7">
<div class="tableRespon">
	<table class="tb07 w100">
		<caption class="sound_only">시험 - 순서, 시험명, 관련과목, 능력단위요소, 유형, 시험시간, 시간, 문제수, 상태 순으로 정보를 제공합니다.</caption>
		<thead>
			<tr>
				<th width="50px">No</th>
				<th>평가형태</th>
				<th>시험지명</th>
				<!-- <th width="">과목</th> -->
				<th width="">시험시간</th>
				<th width="">시간</th>
				<th width="">문제수</th>
				<th width="">상태</th>
				<th width="" style="border-right: 0px;"></th>
			</tr>
		</thead>
		<tbody>
			<tr v-for="(o,i) in testList">
				<td>{{i+1}}</td>
				<td>
				{{o.addTryNoNm}}
				</td>
				<td class="tl">{{o.testNm}}</td>
				<!-- <td>{{o.subNm}}</td> -->
				<td>{{o.startDe|fltDate2Str('YYYY.MM.DD HH:mm')}} ~ {{o.endDe|fltDate2Str('YYYY.MM.DD HH:mm')}}</td>
				<td>{{o.timer}}분</td>
				<td>{{o.qstnCnt}}</td>
				<td>
				{{o.addTestResultStateNm}}
				</td>
				<td style="border-right: 0px;">
				<template v-if="o.status == 1 || o.status == 3">
				<a v-if="o.testResultState == 2 || (o.testResultState == 3 && o.status == 3)" :href="'javascript:fn_tab7_openResult('+o.testSeq+');'" class="btn04 btn_blue">결과보기</a>
				<a v-else-if="o.testResultState == 0 || o.testResultState == 1 " :href="'javascript:fn_tab7_openTest('+o.testSeq+');'" class="btn04 btn_orange">응시하기</a>
				</template>
				</td>
			</tr>
			<tr v-if="testList.length == 0">
				<td colspan="10">시험지가 없습니다.</td>
			</tr>
		</tbody>
	</table>
</div>
</div>
<script>
	var vm_mt7 = new Vue({
		el: '#vm-mt7',
		data: {
			testList: [],
		},
		mounted: function(){
			console.log('mount');
			this.getTestList();
		},
		methods: {
			getTestList: function(){
				var _this = this;
				var eduSeq= '${param.eduSeq}';
				$.ajax({
					data: {eduSeq: eduSeq,pageNo: 0},
					url: '${utcp.ctxPath}/user/quizTestEdu/getTestEduList.ajax',
					success: function(r){
						if(r.result == 1){
							_this.testList = r.data.testList;
						}
					}
				});
			},
			getTryNoNm: function(tryNo){
				if(tryNo > 1){
					return '재평가';
				}else{
					return '본평가';
				}
			}
		},
	});
	function fn_tab7_openTest(testSeq){
		vm_mt7t.getTestQstnList(testSeq);
		vm_mt7t.agree1 = false;
		vm_mt7t.playStep = 1;
		vm_mt7t.arrNo = 0;
		vm_mt7t.remainingTime = 0;
		
		
	}
	function fn_tab7_openResult(testSeq){
		var eduSeq = '${param.eduSeq}';
		var popUrl ='${utcp.ctxPath}/user/quizTestEdu/popup/resultStdnt.do?eduSeq='+eduSeq+'&testSeq='+testSeq;
	   	var popOption = 'width=900px, height=1080px, resizable=no, location=no, top=150px, left=350px;';
	   	window.open(popUrl,"시험결과",popOption); 
		
	}
	function fn_tab7_closeTest(){
		$('[data-remodal-id=md-mt7t]').remodal().close();
		location.reload();
	}
</script>

<!-- 시험지영역 -->
<div class="remodal testView" data-remodal-id="md-mt7t" id="vm-mt7t" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc" data-remodal-options="closeOnOutsideClick: false">
	<button data-remodal-action="close" class="remodal-close" aria-label="Close">나가기</button>
	<div class="modal-content">
		<div class="modal-header">
			<div class="testTop">
				<h3>
					시험명 : <strong>{{userTest.testNm}}</strong>
				</h3>
				<ul>
					<li>[총 {{userQtList.length}}문제]</li>
					<li>시험시간 {{userTest.timer}}분</li>
					<li>
						잔여시간 : <span class="time dp_ib fc_red">{{formattedTime}}</span>
					</li>
					<li v-if="userTest.testResultStartDe">시작시간 : {{userTest.testResultStartDe|fltDate2Str('YYYY.MM.DD HH:mm:ss')}}</li>
					<li v-if="userTest.testResultState > 1">종료시간 : {{userTest.testResultEndDe|fltDate2Str('YYYY.MM.DD HH:mm:ss')}}</li>
				</ul>
			</div>
		</div>
		<div class="modal-body">
			<div v-if="playStep == 1" class="testInfo">
				<p class="tit tc fdb mgb20">시험응시 안내</p>
				<label for="md-mt7t-agree1" class="dp_ib mgr15">
					<input type="checkbox" id="md-mt7t-agree1" v-model="agree1" class="vm mgr5" />
					아래 안내 내용을 정확히 확인 하였음에 동의합니다.
				</label>
				<button type="button" class="btn04 btn_blue" @click="playTest">시험시작</button>

				<div class="tl">
					<ul class="numList1">
						<li>
							<em>1.</em>시험응시 시간 : <b class="fc_red">{{userTest.timer}}분</b>
						</li>
						<li>
							<em>2.</em>유의사항
						</li>
						<li>
							<em>가.</em>본인 PC점검
							<ul class="lineList1">
								<li>
									<em>-</em>일반랜선이 아닌 무선인터넷(Wifi) 사용으로 인해 발생하는 불이익(끊김 등)은 본인의 책임이니 반드시 안정적인 인터넷 환경에서 응시하시기 바랍니다.
								</li>
							</ul>
						</li>
						<li>
							<em>나.</em>응시시간 제한
							<ul class="lineList1">
								<li>
									<em>-</em>시험 시작 후 제한시간이 경과되면 답안이 자동제출 되오니 유의하시기 바랍니다.
								</li>
							</ul>
						</li>
						<li>
							<em>다.</em>부정행위 처리
							<ul class="lineList1">
								<li>
									<em>-</em>부정행위(특수키 사용, 화면전환 등)를 반복하면 위반내역이 담당교수님에게 전달 됩니다.
								</li>
								<li>
									<em>-</em>부정행위는 성적처리 시 감점 또는 0점 처리 될 수 있으니 유의하시기 바랍니다.
								</li>
							</ul>
						</li>
					</ul>
				</div>
			</div>

			<!--// 문제영역 //-->
			<div v-if="playStep == 2" class="testCont cf">
				<template v-if="userQtList.length > 0">
				<div class="testQ tl cf">
					<p>시험지 영역</p>
					<div class="testQlist" :key="componentKey">
						<dl>
							<dt>
								{{userQtList[arrNo].qt.ord}}. <span v-html="text2Html(userQtList[arrNo].qt.qstnStr)"></span> <span>(배점 {{userQtList[arrNo].qt.marks}}점)</span>
								<div>
								<img v-if="userQtList[arrNo].qt.qstnFnRnm" width="300px" :src="'${utcp.ctxPath }/DATA/<%=QuizBankComponent.UPLOAD_PATH%>'+userQtList[arrNo].qt.qstnSeq+'/'+userQtList[arrNo].qt.qstnFnRnm"/>
								</div>
							</dt>
							<template v-if="userQtList[arrNo].qt.qstnTp == 1">
							<dd v-for="(o2,i2) in userQtList[arrNo].chList" >
								<input type="radio" name="md_mt7t_ch" :id="'md_mt7t_ch_'+i2" v-model="userQtList[arrNo].qt.optnStr" :value="o2.chSeq" :disabled="!isPlay"/>
								<label :for="'md_mt7t_ch_'+i2"> {{noArr[i2]}} 
								{{o2.chStr}} 
								</label>
								<br/><img v-if="o2.chFnRnm" height="200px" :src="'${utcp.ctxPath }/DATA/<%=QuizBankComponent.UPLOAD_PATH%>'+o2.qstnSeq+'/'+o2.chFnRnm"/>
							</dd>
							</template>
							<template v-else-if="userQtList[arrNo].qt.qstnTp == 2">
							<dd v-for="(o2,i2) in userQtList[arrNo].chList">
								<input type="checkbox" name="md_mt7t_ch" :id="'md_mt7t_ch_'+i2" v-model="userQtList[arrNo].qt.optnArr" :value="o2.chSeq" :disabled="!isPlay"/>
								<label :for="'md_mt7t_ch_'+i2"> {{noArr[i2]}} {{o2.chStr}} </label>
							</dd>
							</template>
							<template v-else-if="userQtList[arrNo].qt.qstnTp == 3"> 
							<dd>
							<input v-model="userQtList[arrNo].qt.fillBlank" class="w100" type="text" :disabled="!isPlay"/></dd> </template>
							<template v-else-if="userQtList[arrNo].qt.qstnTp == 4"> <dd>
							<textarea v-model="userQtList[arrNo].qt.answer" class="ta_size" :disabled="!isPlay"></textarea> </dd></template>
						</dl>
					</div>
					<div class="tc">
						<button type="button" @click="moveQuiz(arrNo,arrNo-1)" class="prevBtn">
							<span>이전</span>
						</button>
						<button type="button" @click="moveQuiz(arrNo,arrNo+1)" class="nextBtn">
							<span>다음</span>
						</button>
					</div>
				</div>
				<div class="testA tl">
					<p>답안지 영역</p>
					<div class="tbWrap">
						<table class="tb">
							<caption class="blind">답안지 영역 - No, 답안 순으로 정보를 제공합니다.</caption>
							<colgroup>
								<col style="width: 15%;">
								<col style="width: 80%;">
							</colgroup>
							<thead>
								<tr>
									<th>No</th>
									<th>답안</th>
								</tr>
							</thead>
							<tbody>
								<tr v-for="(o2,i2) in userQtList">
									<td>{{o2.qt.ord}}</td>
									<td>
									<template v-if="userTest.testResultState == 3">
										<template v-if="3 > o2.qt.qstnTp ">
											<template v-for="(o3,i3) in o2.chList">
											<span class="ansCrrt" v-if="o3.chAnsYn == 'Y'">{{noArr[i3]}}</span>&nbsp;
											</template>
										</template>
									</template> 
									<template v-else>
									<span v-if="o2.qt.answeredYn == 'Y'" class="ansFin">완료</span> <span v-else class="ansNo">미완료</span>
									</template>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
				</template>
			</div>
			<!--// 문제영역 //-->

			<!-- 완료영역 -->
			<div v-if="playStep == 3" class="testFinCont pd30 cf">
				<p class="fs_30 mgb40">
						수고하셨습니다.
					</p>
				<div class="tc">
					<button type="button" @click="moveQuiz(-1,arrNo-1)" class="prevBtn">
						<span>이전</span>
					</button>
					<button type="button" class="finBtn" @click="doneQuiz()" v-if="userTest.testResultState==1">
						<span>제출</span>
					</button>
				</div>
			</div>
			
			<div v-if="playStep == 4" class="resultFinCont cf pd15" style="">
					<table class="tc w100 tb mgb20 blGray brGray">
						<caption class="sound_only">평가일시, 훈련생, 교과목, 훈련교사, 능력단위명, 평가시간/총배점, 능력단위 요소명, 평가유형/수준, 평가점수, 성취수준, 성취기준, 능력단위요소 순으로 정보를 제공합니다.</caption>
						<colgroup>
							<col style="width: 15%;">
							<col style="width: 35%;">
							<col style="width: 15%;">
							<col style="width: 35%;">
						</colgroup>

						<tbody>
							<tr>
								<th>평가일시</th>
								<td>{{userTest.testResultStartDe|fltDate2Str('YYYY.MM.DD HH:mm')}}</td>
								<th>훈련생</th>
								<td>{{userTest.userNm}}</td>
							</tr>
							<tr>
								<th>평가시간</th>
								<td></td>
								<th>총배점</th>
								<td>{{userTest.totMarks}}</td>
							</tr>
							<tr>
								<th>평가점수</th>
								<td><strong class="fc_darkblue">{{userTest.totMarksGet}}</strong></td>
								<th>성취수준</th>
								<td>{{userTest.addGradeNm}}</td>
							</tr>
							<tr>
										<th>채점자</th>
										<td>{{userTest.checkNm }}</td>
										<th>채점일시</th>
										<td>
										<template v-if="userTest.checkNm">
										{{userTest.checkDe|fltDate2Str('YYYY.MM.DD HH:mm')}}
										</template>
										</td>
									</tr>
							<tr>
								<th>성취기준</th>
								<td colspan="3">
									<table class="tb w100 mgb0 blGray brGray">
										<caption class="sound_only">수준과 점수구간 순으로 정보를 제공합니다.</caption>
										<thead>
											<tr>
												<th>수준</th>
												<th>5수준</th>
												<th>4수준</th>
												<th>3수준</th>
												<th>2수준</th>
												<th>1수준</th>
											</tr>
										</thead>
										<tbody>
											<tr>
												<td>점수구간</td>
												<td>90점 이상</td>
												<td>80~89점</td>
												<td>70~79점</td>
												<td>60~69점</td>
												<td>60점 미만</td>
											</tr>
										</tbody>
									</table>
								</td>
							</tr>
						</tbody>
					</table>
					<button data-remodal-action="cancel" class="remodal-cancel btn02 btn_orange testCcBtn">닫기</button>
				</div>

		</div>
		<div class="modal-footer"></div>
	</div>
</div>
<script>
var vm_mt7t = new Vue({
	el: '#vm-mt7t',
	data: {
		userTest: {},
		userQtList: [],
		arrNo: 0,
		noArr: ['①','②','③','④','⑤','⑥'],
		playStep: 1,
		agree1: false,
		remainingTime: 0,
		timerId: null,
		componentKey: 0,
		isPlay: true,
	},
	computed: {
		formattedTime: function(){
			var minutes = Math.floor(this.remainingTime / 60);
            var seconds = this.remainingTime % 60;
            return this.padZero(minutes)+':'+this.padZero(seconds);
		}
	},
	updated: function(){
	},
	methods: {
		padZero: function(value) {
            return String(value).padStart(2, '0');
        },
        startTimer: function() {
        	//
        	if(this.userTest.testResultState == 2){
        		this.isPlay = false;
        		clearInterval(this.timerId);
        		return;
        	}
        	
        	//타이머가 0이면 무제한이
        	if(this.userTest.timer == 0){
        		this.isPlay = true;
        		return;
        	}
        	
			var limitDe = this.userTest.testResultStartDe+(this.userTest.timer*60*1000);
			var now = Date.now();
		   	// 남은 시간 계산 (밀리세컨드 단위)
		    var remainingTimeInMs = limitDe - now;

		    // 밀리세컨드를 분으로 변환
		    this.remainingTime = Math.floor(remainingTimeInMs / 1000);
        	if(this.remainingTime < 0){
        		this.remainingTime = 0;
        	}
            var _this = this;
            clearInterval(this.timerId);
            this.timerId = setInterval(function() {
                //console.log('interval',_this.remainingTime);
            	if ((_this.remainingTime*1) > 0) {
                    _this.remainingTime--;
                }else{
                	//console.log();
                	_this.isPlay = false;
                	clearInterval(_this.timerId);
                }
            }, 1000);
        },
		//시험지조회
		getTestQstnList: function(testSeq){
			var _this = this;
			var eduSeq = '${param.eduSeq}';
			$.ajax({
				data: {eduSeq: eduSeq, testSeq: testSeq},
				url: '${utcp.ctxPath}/user/quizTestEdu/getTestEduQstnList.ajax',
				success: function(r){
					if(r.result == 1){
						_this.userQtList = r.data.userQtList;
						_this.userTest = r.data.userTest;
						for(var i in _this.userQtList){
							_this.convOt(_this.userQtList[i].qt);
						}
						if(r.data.userTest.testResultState == 2){
							_this.playStep = 2;
						}
						if(r.data.userTest.testResultState == 3){
							_this.playStep = 4;
						}
						
						if(!_this.isEmpty(r.data.userTest.testResultStartDe)){
							_this.startTimer();
						}
						$('[data-remodal-id=md-mt7t]').remodal().open();
						location.href='#none';
						return;
					}
					alert(r.msg);
				}
			});
		},
		saveTestQstnAnswer: function(arrNo){
			if(this.userTest.testResultState == 2){
				return;
			}
			if(arrNo < 0){
				return;
			}
			
			var _this = this;
			var qt = _this.userQtList[arrNo].qt;
			
			if(qt.qstnTp == 1){
				if(this.isEmpty(qt.optnStr)){
					return;
				}
				qt.optn = '['+qt.optnStr+']';
			}else if(qt.qstnTp == 2){
				if(this.isEmpty(qt.optnArr)){
					return;
				}
				qt.optn = JSON.stringify(qt.optnArr);
			}
			
			var eduSeq= '${param.eduSeq}';
			$.ajax({
				type: 'post',
				data: {
					eduSeq: eduSeq, 
					testSeq: _this.userTest.testSeq, 
					tqSeq: qt.tqSeq,
					qstnTp: qt.qstnTp,
					optn: qt.optn, 
					fillBlank: qt.fillBlank, 
					answer: qt.answer, 
				},
				url: '${utcp.ctxPath}/user/quizTestEdu/saveTestQstnAnswer.ajax',
				success: function(r){
					if(r.result == 1){
						for(var i in _this.userQtList){
							var qt1 = _this.userQtList[i].qt;
							if(r.data.qt && qt1.tqSeq == r.data.qt.tqSeq){
								_this.userQtList[i].qt = r.data.qt;
								_this.convOt(_this.userQtList[i].qt);
							}
						}
					}
				}
			});
			
		},
		playTest: function(){
			if(!this.agree1){
				alert('안내내용에 동의하셔야 합니다.');
				return;
			}
			var _this = this;
			$.ajax({
				type: 'post',
				data: {
					eduSeq: '${param.eduSeq}',
					testSeq: _this.userTest.testSeq, 
				},
				url: '${utcp.ctxPath}/user/quizTestEdu/startTestQstnAnswer.ajax',
				success: function(r){
					if(r.result == 1){
						_this.playStep = 2;
						_this.userTest.testResultStartDe = r.data.tr.startDe;
						_this.startTimer();
					}else{
						alert(r.msg);						
					}
				}
			});
		},
		moveQuiz: function(arrNo,moveNo){
			if(arrNo == -1){
				this.playStep = 2;
			}
			//답안저장처리
			this.saveTestQstnAnswer(arrNo);	
			
			if(moveNo < 0){
				alert('첫번째 문제입니다.');
				return;
			}
			if(moveNo>=this.userQtList.length){
				this.playStep=3;
				if(this.userTest.testResultState == 0){
					this.userTest.testResultState = 1;//250113,왜 강제로 상태를 바꾸는지 확인 필요
				}
				//alert('마지막 문제입니다.');
				//return;
			}
			this.arrNo = moveNo;
			this.componentKey += 1; // 강제로 재렌더링
		},
		
		doneQuiz: function(arrNo){
			//다풀었는지 체크
			
			if(this.isPlay){
				if(!this.checkDone()){
					alert('아직 풀지 않은 문제가 있습니다. 다시 확인 하시기 바랍니다.');
					return;
				}
				if(!confirm('제출하시면 수정하실 수 없습니다. 계속 진행하시겠습니까?')){
					return;
				}
			}else{
				
			}
			
			
			var _this = this;
			$.ajax({
				type: 'post',
				data: {
					eduSeq: '${param.eduSeq}',
					testSeq: _this.userTest.testSeq
				},
				url: '${utcp.ctxPath}/user/quizTestEdu/doneTestQstnAnswer.ajax',
				success: function(r){
					if(r.result == 1){
						fn_tab7_closeTest();
					}
				}
			});
		},
		checkDone: function(){
			var _this = this;
			for(var i in _this.userQtList){
				var qt = _this.userQtList[i].qt;
				if(qt.qstnTp == 1){
					if(this.isEmpty(qt.optnStr)){
						return false;
					}
				}else if(qt.qstnTp == 2){
					if(this.isEmpty(qt.optnArr)){
						return false;
					}
				}else if(qt.qstnTp == 3 ){
					if(this.isEmpty(qt.fillBlank)){
						return false;
					}
				}else if(qt.qstnTp == 4 ){
					if(this.isEmpty(qt.answer)){
						return false;
					}
				}
			}
			return true;
		},
		convOt: function(o){
			o.optnStr = '';
			o.optnArr = [];
			
			if(!this.isEmpty(o.optn)){
				if(o.qstnTp == 1){
					o.optnStr = JSON.parse(o.optn)[0];
				}else if(o.qstnTp == 2){
					o.optnArr = JSON.parse(o.optn);
				}
			}
			
			//console.log(o);
			//this.$forceUpdate();
		},
		isEmpty: function (value) {
		    return value === null || value === undefined || value === '';
		},
		text2Html : function(v){
			return fltCharToHtml(v);
		},
	}
});
</script>