<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<div id="vm-tab5" class="timetable_tab_onoff">

	<ul class="timetable_tab tr">
		<li :class="activeTabNo == 1?'active':''">
			<p><a href="javascript:;" class="btn04" @click="activeTab(1)">전체</a></p>
		</li>
		<li :class="activeTabNo == 2?'active':''">
			<p><a href="javascript:;" class="btn04" @click="activeTab(2)">일간</a></p>
		</li>
		<li :class="activeTabNo == 3?'active':''">
			<p><a href="javascript:;" class="btn04" @click="activeTab(3)">주간</a></p>
		</li>
		<li :class="activeTabNo == 4?'active':''">
			<p><a href="javascript:;" class="btn04" @click="activeTab(4)">월간</a></p>
		</li>
	</ul>
	<div class="timetable_tab_con">
		<div v-if="activeTabNo == 1 || activeTabNo == 2" class="cont">
			<div class="timetable_date" v-if="activeTabNo == 2">
				<ul>
					<li>{{dayStr|fltDt2Str('YYYYMMDD','MM.DD (ddd)')}}</li>
					<li>
						<a href="javascript:;" @click="callDayData(-1)"><i class="fa-solid fa-caret-left"></i></a>
					</li>
					<li>
						<a href="javascript:;" @click="callDayData(1)"><i class="fa-solid fa-caret-right"></i></a>
					</li>
					<li>오늘</li>
				</ul>
			</div>

			<div class="tr mgb15">
				<span class="fl">{{totalEduTimeStr}}</span>
				<label for="eduu" class="fl">
					<!-- <input type="checkbox" id="eduu" class="mgt3 mgr3" name="" value="">
					순차교육 -->
				</label>
				<a href="#none" @click="delTimeSelected" class="btn04 btn_gray">선택수업삭제</a> 
				<a href="#none" onclick="fn_tab5_openLectureTimeReg(1,0)" class="btn04 btn_greenl">수업등록</a> 
				<!-- 
				<a href="#none" onclick="fn_tab5_openLectureTimeReg(2,0)" class="btn04 btn_greenl">반복수업등록</a>
				 -->
			</div>

			<table class="w100 tb07">
				<caption class="sound_only">교육시간표 - 교시, 날짜, 시작시간, 종료시간, 수업명, 강사, 형태, 장소 순으로 정보를 제공합니다.</caption>
				<thead bgcolor="#f7f8fa">
					<tr>
						<th>
							<input type="checkbox" v-model="isAllChecked" @change="handleChangeAllCheck" />
						</th>
						<c:if test="${lctre.lctreType eq 3 }">
							<th>교시</th>
						</c:if>
						<c:if test="${lctre.lctreType ne 3}">
							<th>교시</th>
							<th>날짜</th>
							<th>시작시간</th>
							<th>종료시간</th>
						</c:if>
						<th>내용</th>
						<th>강사</th>
						<th>형태</th>
						<th>강의실</th>
						<th>노출여부</th>
						<th></th>
					</tr>
				</thead>
				<tbody>
					<tr v-for="(o,i) in dayList">
						<td>
							<input type="checkbox" v-model="o.isDel"/>
						</td>
						<c:if test="${lctre.lctreType == 3}">
							<td>{{o.timeSeq}}</td>
						</c:if>
						<c:if test="${lctre.lctreType != 3}">
							<td>{{i+1}}</td>
							<td>{{o.eduDt|fltDt2Str('YYYYMMDD','YYYY-MM-DD (ddd)')}}</td>
							<td>{{o.startTm|fltDt2Str('HHmm','HH:mm')}}</td>
							<td>{{o.endTm|fltDt2Str('HHmm','HH:mm')}}</td>
						</c:if>
						<td class="tl">
						{{o.description}}
						</td>
						<td>{{o.checkNm}}</td>
						<td :class="o.classHow==1?'fc_orange':''">{{o.classHowNm }}</td>
						<td>
						<template v-if="o.classHow==0 | o.classHow == 4"> 
						{{o.roomNm}}
						</template>
						<template v-else-if="o.classHow==1"> 
						<a :href="o.url" target="_blank">바로가기</a> </template> 
						<template v-if="o.classHow==3"> 
						<a href="#none" :onclick="'fn_playMov('+o.mvIdx+')'">시청하기</a> 
						</template>
						<span :id="'areahwasang-'+i" :style="o.classHow!=2?'display:none':''"> <a v-if="o.url" :href="o.url" target="_blank" type="button" class="btn04 btn_green mgr10">입장</a>
						</span></td>
						<td v-html="o.status == 9?'No':''">
					
						</td>
						<td>
						<!-- 
						<a href="#" class="pd5 btn_darkblue"><i class="fa-solid fa-eye"></i></a> 
						 -->
						<a href="#none" @click="delTime(o.timeSeq)" class="pd5 btn_darkblue"><i class="fa-solid fa-trash-can"></i></a> 
						<a href="#none" @click="callUpdTime(o.timeSeq)" class="pd5 btn_darkblue"><i class="fa-solid fa-pen"></i></a></td>
					</tr>
					<tr v-if="!dayList.length">
						<td colspan="10">시간표가 생성되지 않았습니다.</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div v-if="activeTabNo == 3" class="cont">
			<div class="timetable_date">
				<ul>
					<li v-if="weekList.length == 7">{{weekList[0].eduDt|fltDt2Str('YYYYMMDD','MM.DD')}} - {{weekList[6].eduDt|fltDt2Str('YYYYMMDD','MM.DD')}}</li>
					<li>
						<a href="javascript:;" @click="callWeekData(-7)"><i class="fa-solid fa-caret-left"></i></a>
					</li>
					<li>
						<a href="javascript:;" @click="callWeekData(7)"><i class="fa-solid fa-caret-right"></i></a>
					</li>
					<li>오늘</li>
				</ul>
			</div>
			<div id="weekcalendar-wrap">
				<div id="Weekcalendar">
					<ul class="weekdays">
						<li>교시</li>
						<li v-for="(o,i) in weekList">{{o.eduDt|fltDt2Str('YYYYMMDD','M/D (ddd)')}}</li>
					</ul>

					<ul class="days" v-for="(o,i) in maxGyosi">
						<li class="day">
							<div class="date">{{o}}</div>
						</li>
						<li class="day" v-for="(o2,i2) in weekList">
							<div class="date">
								<ul v-if="o2.gyosiList.length >=(i+1)">
									<li>{{o2.gyosiList[i].startTm|fltDt2Str('HHmm','HH:mm')}} ~ {{o2.gyosiList[i].endTm|fltDt2Str('HHmm','HH:mm')}}</li>
									<li>
										<a href="#">{{o2.gyosiList[i].description}}</a>
									</li>
									<li>({{o2.gyosiList[i].checkNm}})</li>
									<li>{{o2.gyosiList[i].roomNm}}</li>
								</ul>
							</div>
						</li>
					</ul>
				</div>
			</div>
		</div>

		<div v-if="activeTabNo == 4" class="cont">
			<div class="timetable_date">
				<ul>
					<li>{{monthNo}}월</li>
					<li>
						<a href="javascript:;" @click="callMonthData(-1)"><i class="fa-solid fa-caret-left"></i></a>
					</li>
					<li>
						<a href="javascript:;" @click="callMonthData(1)"><i class="fa-solid fa-caret-right"></i></a>
					</li>
					<li>오늘</li>
				</ul>
			</div>
			<div id="calendar-wrap" class="timetable">
				<div id="calendar">
					<ul class="weekdays">
						<li>일</li>
						<li>월</li>
						<li>화</li>
						<li>수</li>
						<li>목</li>
						<li>금</li>
						<li>토</li>
					</ul>

					<ul class="days" v-for="(o,i) in monthList">
						<li :class="'day '+(o2.isMonth?'':'other-month')" v-for="(o2,i2) in o">
							<div class="date">{{o2.calDt|fltDt2Str('YYYYMMDD','DD')}}</div>
							<div class="event" v-for="(o3,i3) in o2.gyosiList">
								<div class="event-desc">
									<span class="num" onclick="location.href='#'">{{i3+1}}.{{o3.description}}</span>
								</div>
							</div>
						</li>
					</ul>

				</div>
				<!-- /. calendar -->
			</div>
			<!--// calendar_wrap //-->
		</div>
		<!--// 월간 //-->

	</div>
</div>

<script>
	var vm_5_inputDate = new Date();
	var vm_5_inputWeek = new Date();
	var vm_5_inputMonth = new Date();
	//시간표영역 준비되면 vue적용
	var vm_5 = new Vue({
		el : '#vm-tab5',
		data : {
			activeTabNo : 1,
			timeList : [],
			weekList : [ {
				eduDt : ''
			}, ],
			maxGyosi : 0,
			monthList : [],
			monthNo : 0,
			dayList : [],
			dayStr : '',
			isAllChecked: false,
		},
		mounted : function() {
			//console.log('mounted');
			//this.$set(this, 'dayList', this.timeList);
		},
		computed: {
			totalEduTimeStr: function(){
				var result = "";
				var totalEduMin = 0;
				for(var i in this.timeList){
					var o = this.timeList[i];
					var startDe = UTIL.parseDate(o.eduDt+""+o.startTm,'YYYYMMDDHHmm');
					var endDe = UTIL.parseDate(o.eduDt+""+o.endTm,'YYYYMMDDHHmm');
					var eduMin = UTIL.calcBetweenMin(startDe,endDe);
					totalEduMin += eduMin;
				}
				result = UTIL.formatMin2Str(totalEduMin);
				
				return result;
			},
		},
		methods : {
			handleChangeAllCheck: function(){
				console.log(this.isAllChecked);
				this.dayList = this.dayList.map(item => ({
					...item,
					isDel: this.isAllChecked
				}));
				//console.log(this.timeList);
				//this.$forceUpdate();
			},
			delTimeSelected: function(){
				if(!confirm('선택 시간을 삭제하시겠습니까?')){return;};
				var _this = this;
				var eduSeq = '${param.eduSeq}';
				var timeSeqArr = [];
				for(var i in this.dayList){
					var o = this.dayList[i];
					if(o.isDel){
						timeSeqArr.push(o.timeSeq);
					}
				}
				$.ajax({
					type: 'post',
					data: {eduSeq: eduSeq, timeSeqArr: timeSeqArr},
					url: '${utcp.ctxPath}/admin/ajax/delLectureTimeSelected.ajax',
					success: function(r){
						if(r.result == 1){
							_this.callTimeList();
						}
					}
				});
			},
			callUpdTime: function(timeSeq){
				console.log(timeSeq);
				fn_tab5_openLectureTimeReg(1,timeSeq);
				
			},
			delTime: function(timeSeq){
				if(!confirm('해당 시간을 삭제하시겠습니까?')){return;};
				var _this = this;
				var eduSeq = '${param.eduSeq}';
				$.ajax({
					type: 'post',
					data: {eduSeq: eduSeq, timeSeq: timeSeq},
					url: '${utcp.ctxPath}/admin/ajax/delLectureTime.ajax',
					success: function(r){
						if(r.result == 1){
							_this.callTimeList();
						}
					}
				});
			},
			callTimeList : function() {
				var _this = this;
				$.ajax({
					url : "${utcp.ctxPath}/admin/ajax/lectureTimeList.json",
					data : {
						eduSeq : $('#eduSeq').val()
					},
					success : function(r) {
						//if (r.list.length > 0) {//빈값도 갱신되어야 학때문에 주석처리함, 조건이 들어간 이유는 나중에 찾아봐야함,250317
							_this.timeList = r.list;
							_this.dayList = r.list;
						//}
					}
				});
			},
			activeTab : function(no) {
				this.activeTabNo = no;
				if (no == 1) {
					this.dayList = this.timeList;
				} else if (no == 2) {
					this.callDayData(0);
				} else if (no == 3) {
					this.callWeekData(0);
				} else if (no == 4) {
					this.callMonthData(0);
				}
			},
			callDayData : function(moveCnt) {
				if (moveCnt == 0) {
					vm_5_inputDate = new Date();
				} else if (moveCnt != 0) {
					vm_5_inputDate.setDate(vm_5_inputDate.getDate() + moveCnt);
				}

				var dayDt = UTIL.getFormattedDate(vm_5_inputDate, 'YYYYMMDD');

				this.dayStr = dayDt;

				this.dayList = [];
				for ( var i in this.timeList) {
					var o = this.timeList[i];
					if (dayDt == o.eduDt) {
						this.dayList.push(o);
					}
				}
			},
			callWeekData : function(moveCnt) {
				this.maxGyosi = 0;
				// 이번 주 날짜 배열
				if (moveCnt == 0) {
					vm_5_inputWeek = new Date();
				} else if (moveCnt != 0) {
					vm_5_inputWeek.setDate(vm_5_inputWeek.getDate() + moveCnt);
				}
				//요일가져오기
				var weekArr = UTIL.getWeekDates(vm_5_inputWeek);
				// 빈 배열을 미리 초기화
				this.weekList = new Array(weekArr.length).fill(null).map(
						function() {
							return {
								eduDt : null
							};
						});
				// 각 배열 위치에 값을 설정
				for (var i = 0; i < weekArr.length; i++) {
					this.weekList[i].eduDt = weekArr[i];

					//gyosiList생성하여 추가함
					var gyosiList = [];
					for ( var i2 in this.timeList) {
						var o = this.timeList[i2];
						//console.log(o.eduDt);
						if (o.eduDt == weekArr[i]) {
							gyosiList.push(o);
						}
					}

					this.weekList[i].gyosiList = gyosiList;

					if (this.maxGyosi < gyosiList.length) {
						this.maxGyosi = gyosiList.length;
					}
				}

			},
			callMonthData : function(moveCnt) {
				var calendar = [];

				if (moveCnt == 0) {
					vm_5_inputMonth = new Date();
				} else if (moveCnt != 0) {
					vm_5_inputMonth.setMonth(vm_5_inputMonth.getMonth()
							+ moveCnt);
				}

				var currentMonth = vm_5_inputMonth.getMonth();
				this.monthNo = currentMonth + 1;
				var startDate = new Date(vm_5_inputMonth.getFullYear(),
						currentMonth, 1);
				var endDate = new Date(vm_5_inputMonth.getFullYear(),
						currentMonth + 1, 0);

				var firstDay = startDate.getDay();
				var daysInMonth = endDate.getDate();
				// 첫째 주의 시작일 계산
				startDate.setDate(startDate.getDate() - firstDay);

				// 달력 데이터 생성
				while (startDate <= endDate) {
					console.log(2, startDate)
					var week = [];
					for (var i = 0; i < 7; i++) {
						var calDt = UTIL
								.getFormattedDate(startDate, 'YYYYMMDD');
						var dateObj = {
							'calDt' : calDt,
							'isMonth' : false,
							'gyosiList' : [],
						};
						if (startDate.getMonth() === currentMonth
								&& startDate.getDate() <= daysInMonth) {
							dateObj.isMonth = true;
						}

						//수업시간데이터에서 해당수업 저장
						var gyosiList = [];
						for ( var i2 in this.timeList) {
							var o = this.timeList[i2];
							//console.log(o.eduDt);
							if (o.eduDt == calDt) {
								gyosiList.push(o);
							}
						}
						dateObj.gyosiList = gyosiList;

						week.push(dateObj);
						startDate.setDate(startDate.getDate() + 1);
					}
					calendar.push(week);
				}
				this.monthList = calendar;
			},
		},
		updated : function() {
		}
	});
</script>



<!--// 출결자 검색 레이어//-->
<div class="layerPop" id="vm-tab5-instrctrChoice">
        <div class="lpCloseBtn" @click="closeLayer()"><i class="fa-solid fa-xmark"></i></div>
        <div id="">
            <div class="modal-content">
                <div class="modal-header">
                    <p class="tit alignC"><i class="fas fa-chevron-circle-right font_22 pdr5"></i>강사 찾기</p>
                </div>
                <div class="modal-body">	
                    <div class="dp_ib tc pd15">
                        <label for="srchBelong" class="sound_only">검색어입력</label>
                        <input type="text" v-model="srchWrd" placeholder="성명 검색" class="btn04 btn_blackl tl mgr5">
                        <button type="button" class="btn04 btn_black fr" @click="callInstrctr">검색</button>
                    </div>			
                    <div class="tbBox">
                        <table class="tc tb02 w100">
                            <caption class="sound_only">강사 찾기 테이블</caption>
                            <thead>
                                <tr>
                                  
                                    <th>NO</th>
<!--                                     <th>아이디</th>									 -->
                                    <th>성명</th>
<!--                                     <th>이메일</th> -->
                                    <th>휴대전화</th>
                                    <th></th>
                                </tr>
                            </thead>
                            <tbody >
                                <tr v-for="(o,i) in instrList">
                                    <td>{{instrList.length - i}}</td>
<!--                                     <td>{{o.loginId}}</td> -->
                                    <td>{{o.userNm}}</td>
<!--                                     <td>{{o.decEmail}}</td> -->
                                    <td>{{o.decMobile}}</td>
                                    <td><button type="button" @click="choiceInstr(o)" class="btn04 btn_blue">선택</button></td>
                                </tr>
                                <tr v-if="instrList.length == 0">
                                	<td colspan="6">데이터가 없습니다.</td>
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
<!--// 강사등록 //-->
<script>
	var vm_tab5_instrctrChoice = new Vue({
		el: '#vm-tab5-instrctrChoice',
		data: {
			instrList: [],
			srchWrd: '',
			timeIndex: 0,
		},
		methods: {
			callInstrctr: function(index){
				if(!UTIL.isEmpty(index)){
					this.timeIndex = index;
				}
				var _this = this;
				$.ajax({
					url : "${utcp.ctxPath}/user/edu/bulkInstrctrList.json",
					data : {
						"srchWrd" : _this.srchWrd,
					},
					success : function(r) {
						if(!UTIL.isEmpty(r.dataList)){
							_this.instrList = r.dataList;
						}	
						$('#vm-tab5-instrctrChoice').show();
					}
				});
			},
			closeLayer: function(){
				$('#vm-tab5-instrctrChoice').hide();
			},
			choiceInstr: function(o){
				vm_tab5_lectureTimeReg.choiceInstr(o,this.timeIndex);
				this.closeLayer();
			}
			
		},
	});
	
	
</script>


<!--// 동영상 검색  //-->
<div class="layerPop" id="vm-tab5-movChoice">
        <div class="lpCloseBtn" @click="closeLayer()"><i class="fa-solid fa-xmark"></i></div>
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC"><i class="fas fa-chevron-circle-right font_22 pdr5"></i>동영상찾기</p>
		</div>
		<div class="modal-body">
			<div class="dp_ib tc pd15">
				<label for="" class="sound_only">검색어입력</label>
				<input type="text"  v-model="srchWrd" placeholder="제목,내용,파일명 검색" class="btn04 btn_blackl tl mgr5" />
				<button type="button" class="btn04 btn_black fr" @click="callMov">검색</button>
			</div>
			<div class="tbBox">
				<table class="tc w100 tb">
					<thead bgcolor="#f7f8fa">
						<tr>
							<th>&nbsp;</th>
							<th>제목</th>
							<th>파일명</th>
							<th>등록일</th>
							<th></th>
						</tr>
					</thead>
					<tbody id="">
						<tr v-for="(o,i) in list">
							<td>{{list.length - i}}</td>
							<td>{{o.title}}</td>
							<td>{{o.fileOrg}}</td>
							<td>{{o.regTime|fltDate2Str('YYYY.MM.DD HH:mm')}}</td>
                            <td><button type="button" @click="choiceMv(o)" class="btn04 btn_blue">선택</button></td>
						</tr>
						<tr v-if="list.length == 0">
							<td colspan="4">데이터가 없습니다.</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<div class="modal-footer">
			
		</div>
	</div>
</div>
<script>
	var vm_tab5_movChoice = new Vue({
		el : '#vm-tab5-movChoice',
		data : {
			list : [],
			srchWrd: '',
			timeIndex: 0,
		},
		methods: {
			callMov: function(){
				var _this = this;
				var srchWrd = _this.srchWrd;
				$.ajax({
					url : "${utcp.ctxPath}/admin/ajax/movList.json",
					data : {
						srchWrd : srchWrd,
					},
					success : function(r) {
						_this.list = r.list;
						$('#vm-tab5-movChoice').show();
					}
				});
			},
			choiceMv: function(o){
				vm_tab5_lectureTimeReg.choiceMv(o,this.timeIndex);
				this.closeLayer();
			},
			closeLayer: function(){
				$('#vm-tab5-movChoice').hide();
			},
		},
	});
</script>

<!--// 과목 검색  //-->
<div class="layerPop" id="vm-tab5-subChoice">
        <div class="lpCloseBtn" @click="closeLayer()"><i class="fa-solid fa-xmark"></i></div>
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC"><i class="fas fa-chevron-circle-right font_22 pdr5"></i>과목찾기</p>
		</div>
		<div class="modal-body">
			<div class="dp_ib tc pd15">
				<label for="" class="sound_only">검색어입력</label>
				<input type="text"  v-model="srchWrd" placeholder="제목,내용,파일명 검색" class="btn04 btn_blackl tl mgr5" />
				<button type="button" class="btn04 btn_black fr" @click="callSub(-1)">검색</button>
			</div>
			<div class="tbBox">
				<table class="tc w100 tb">
					<thead bgcolor="#f7f8fa">
						<tr>
							<th>NO</th>
							<th>과목명</th>
							<th>수료증</th>
							<th>합격증</th>
							<th>등록일</th>
							<th></th>
						</tr>
					</thead>
					<tbody id="">
						<tr v-for="(o,i) in list">
							<td>{{list.length - i}}</td>
							<td>{{o.subNm}}</td>
							<td>{{o.complNm}}</td>
							<td>{{o.passNm}}</td>
							<td>{{o.regTime|fltDate2Str('YYYY.MM.DD HH:mm')}}</td>
                            <td><button type="button" @click="choiceSub(o)" class="btn04 btn_blue">선택</button></td>
						</tr>
						<tr >
							<td colspan="4"></td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<div class="modal-footer">
			
		</div>
	</div>
</div>
<script>
	var vm_tab5_subChoice = new Vue({
		el : '#vm-tab5-subChoice',
		data : {
			list : [],
			srchWrd: '',
			timeIndex: 0,
		},
		methods: {
			callSub: function(i){
				if(i >= 0){
					this.timeIndex = i;
				}
				var _this = this;
				var srchWrd = _this.srchWrd;
				$.ajax({
					url : "${utcp.ctxPath}/admin/lcsub/lcsubList.ajax",
					data : {
						srchWrd : srchWrd,
					},
					success : function(r) {
						if(r.result == 1){
							_this.list = r.data.list;
							$('#vm-tab5-subChoice').show();
						}
						
					}
				});
			},
			choiceSub: function(o){
				console.log(this.timeIndex);
				vm_tab5_lectureTimeReg.choiceSub(o,this.timeIndex);
				this.closeLayer();
			},
			closeLayer: function(){
				$('#vm-tab5-subChoice').hide();
			},
		},
	});
</script>

<!-- 시간등록 모달 -->
<div class="remodal" data-remodal-id="md-tab5-lectureTimeReg" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc" id="vm-tab5-lectureTimeReg" data-remodal-options="closeOnOutsideClick: false,closeOnEscape: false">
	<button data-remodal-action="close" class="remodal-close" aria-label="Close"></button>
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC"><i class="fas fa-chevron-circle-right fs_22 pdr5"></i>수업 등록</p>
		</div>
		<div class="modal-body">
			<div class="tbBox mgt0 mgb0 mxh500">
				<table class="tl w100 tb04 mgb10">
					<tbody>
						<tr v-if="dateTp == 2">
							<th>반복 <span class="red">*</span></th>
							<td class="tl">
							<comp-datepicker v-model="dateInfo.startDtStr" :data_max="dateInfo.endDtStr"></comp-datepicker> ~
							<comp-datepicker v-model="dateInfo.endDtStr" :data_min="dateInfo.startDtStr"></comp-datepicker> 
								<div class="dp_b w100 mgt10">
									<label for="md_tab5_weeks1" class="mgr10">
										<input id="md_tab5_weeks1" type="checkbox" name="md_tab5_weeks" v-model="dateInfo.weeks" value="1" />
										일
									</label>
									<label for="md_tab5_weeks2" class="mgr10">
										<input id="md_tab5_weeks2" type="checkbox" name="md_tab5_weeks" v-model="dateInfo.weeks" value="2" />
										월
									</label>
									<label for="md_tab5_weeks3" class="mgr10">
										<input id="md_tab5_weeks3" type="checkbox" name="md_tab5_weeks" v-model="dateInfo.weeks" value="3" />
										화
									</label>
									<label for="md_tab5_weeks4" class="mgr10">
										<input id="md_tab5_weeks4" type="checkbox" name="md_tab5_weeks" v-model="dateInfo.weeks" value="4" />
										수
									</label>
									<label for="md_tab5_weeks5" class="mgr10">
										<input id="md_tab5_weeks5" type="checkbox" name="md_tab5_weeks" v-model="dateInfo.weeks" value="5" />
										목
									</label>
									<label for="md_tab5_weeks6" class="mgr10">
										<input id="md_tab5_weeks6" type="checkbox" name="md_tab5_weeks" v-model="dateInfo.weeks" value="6" />
										금
									</label>
									<label for="md_tab5_weeks7" class="mgr10">
										<input id="md_tab5_weeks7" type="checkbox" name="md_tab5_weeks" v-model="dateInfo.weeks" value="7" />
										토
									</label>
								</div></td>
						</tr>
						<tr v-else>
							<th>교육일 <span class="red">*</span></th>
							<td class="tl">
							<comp-tab5-datepicker v-model="dateInfo.eduDtStr"></comp-tab5-datepicker>
							</td>
						</tr>
					</tbody>
				</table>
				<template v-for="(o,i) in timeInfo">
				<table class="tl w100 tb04 mgb10">
					<tr>
						<th>교육시간 <span class="red">*</span></th>
						<td class="tl"><select v-model="o.startHour">
								<option :value="(i4<10?'0':'')+i4" v-for="(o4,i4) in 24">{{(i4<10?'0':'')+i4}}</option>
						</select> : <select v-model="o.startMin">
								<option :value="(i4<10?'0':'')+i4" v-for="(o4,i4) in 60">{{(i4<10?'0':'')+i4}}</option>
						</select> <select v-model="o.endHour">
								<option :value="(i4<10?'0':'')+i4" v-for="(o4,i4) in 24">{{(i4<10?'0':'')+i4}}</option>
						</select> : <select v-model="o.endMin">
								<option :value="(i4<10?'0':'')+i4" v-for="(o4,i4) in 60">{{(i4<10?'0':'')+i4}}</option>
						</select></td>
					</tr>
					<tr style="display:none;">
						<th>과목</th>
						<td>
							<input type="text" v-model="o.subNm" class="ip2" readonly="readonly"/>
							<button type="button" class="btn04 btn_gray" @click="callSub(i)">찾기</button>
							<button v-if="o.subSeq" type="button" class="btn04 btn_gray" @click="delSub(o,i)">삭제</button>
						</td>
					</tr>
					<tr>
						<th>수업명 <span class="red">*</span></th>
						<td><input type="text" class="ip2" v-model="o.description" /></td>
					</tr>
					<tr>
						<th>수업구분</th>
						<td><select v-model="o.classHow">
								<option value="0">오프라인 교육</option>
								<option value="1">온라인 교육</option>
								<option value="2">실시간 화상(URL)</option>
								<option value="3">동영상</option>
								<option value="4">시험</option>
								<!-- <option value="5">설문</option> -->
						</select> 
						<template v-if="o.classHow == 1 || o.classHow == 2">
						<input type="text" v-model="o.url" class="ip2" placeholder="URL"/>
						<input type="text" v-model="o.urlPw" class="ip9" maxlength="20" placeholder="비밀번호"/>
						</template>
						<template v-else-if="o.classHow == 3">
						<input type="text" v-model="o.mvNm" class="ip2" />
						<button type="button" class="btn04 btn_gray" :onclick="'vm_tab5_movChoice.callMov('+i+')'">찾기</button>
						</template>
						</td>
					</tr>
					<tr>
						<th>교강사</th>
						<td><input type="text" class="ip2" v-model="o.checkNm" readonly="readonly">
							<button type="button" class="btn04 btn_black" :onclick="'vm_tab5_instrctrChoice.callInstrctr('+i+')'">찾기</button>
							<button v-if="o.checkId" type="button" class="btn04 btn_gray" @click="delInstr(o,i)">삭제</button>
							</td>
					</tr>
					 
					<tr>
						<th>강의실</th>
						<td><select v-model="o.roomSeq">
								<option value="0" >강의실선택</option>
								<option v-for="(o,i) in roomList" :value="o.roomSeq">{{o.roomNm}}</option>
						</select></td>
					</tr>
					<tr style="">
						<th>노출여부</th>
						<td>
						<input type="radio" class="" v-model="o.status" value="1" name="lctreTime_status"> 노출 
						<input type="radio" class="" v-model="o.status" value="9" name="lctreTime_status"> 미노출 
						<div class="tr" v-if="timeSeq == 0">
          <button type="button" class="btn04 btn_orange" v-if="i > 0" @click="delRow(i)">삭제</button>
        </div>
						</td>
					</tr>
				</table>
				</template>
				<div class="tr" v-if="timeSeq == 0">
					<button type="button" class="btn04 btn_orange" @click="addTime">추가</button>
				</div>
			</div>
		</div>
		<div class="modal-footer">
			<div class="tc">
				<button type="button" class="btn01 btn_green" @click="saveTime()">등록</button>
			</div>
		</div>
	</div>
</div>
<script>
	Vue.component('comp-tab5-datepicker', {
		template : '<input v-datepicker :value="value" type="text" class="input_calendar ip6 tc"  />',
		props: ['value'],
	    mounted: function() {
	        var vm = this;
	        $(this.$el).datepicker({
	            dateFormat: 'yy-mm-dd',
	            onSelect: function(dateText) {
	                vm.$emit('input', dateText);
	            },
	            beforeShow: function(input, inst) {
	                setTimeout(function() {
	                    $(inst.dpDiv).css('z-index', 10000);
	                }, 0);
	            }
	        });
	    },
	    watch: {
	        value: function(newVal) {
	            $(this.$el).datepicker('setDate', newVal);
	        }
	    },
	    beforeDestroy: function() {
	        $(this.$el).datepicker('destroy');
	    }
	});
	var tab5_timeInfo = {
		startHour : '09',
		startMin : '00',
		endHour : '12',
		endMin : '00',
		classHow : 0,
		roomSeq: 0,
		status: 1,
	};
	var vm_tab5_lectureTimeReg = new Vue({
		el : '#vm-tab5-lectureTimeReg',
		data : {
			timeSeq: 0,
			dateTp: 1,
			dateInfo : {
				startDtStr : '',
				endDtStr : '',
				weeks : [],
			},
			timeInfo : [ $.extend(true, {}, tab5_timeInfo) ],
			roomList : [],
			
		},
		mounted : function() {
			this.callRoom();
		},
		methods : {
			callSub: function(i){
				vm_tab5_subChoice.callSub(i);
			},
			callRoom: function(){
				var _this = this;
				$.ajax({
					url: '${utcp.ctxPath}/admin/ajax/callLectureRoomChoice.ajax',
					success: function(r){
						if(r.result == 1){
							_this.roomList = r.data.roomList;
						}
					}
				});
			},
			addTime : function() {
				this.timeInfo.push($.extend(true, {}, tab5_timeInfo));
			},
      delRow : function(idx) {
    	  this.timeInfo.splice(idx, 1);
        },
			callTime: function(timeSeq){
				if(timeSeq == 0){
					Vue.set(this.timeInfo, 0, $.extend(true, {}, tab5_timeInfo));
					this.dateInfo.eduDtStr = '';
					return;
				}
				
				var _this = this;
				$.ajax({
					data: {eduSeq: '${param.eduSeq}', timeSeq: timeSeq},
					url: '${utcp.ctxPath}/admin/ajax/callLectureTime.ajax',
					success: function(r){
						if(r.result == 1){
							var time = r.data.time;
							var eduDtStr = '';
							//재설정
							if(!UTIL.isEmpty(time.eduDt)){
								eduDtStr = _this.$options.filters.fltDt2Str(time.eduDt,'YYYYMMDD','YYYY-MM-DD');
							}
							//vue변경
							Vue.set(_this.timeInfo, 0, time);
							Vue.set(_this.dateInfo,'eduDtStr',eduDtStr);
							
							//_this.callRoom();
						}
					}
				});
			},
			saveTime : function() {
				var timeSeq = this.timeSeq;
				var weeks = vm_tab5_lectureTimeReg.dateInfo.weeks;
				var times = [];
				var startDtStr = this.dateInfo.startDtStr;
				var endDtStr = this.dateInfo.endDtStr;
				var eduDtStr = this.dateInfo.eduDtStr;
				var dateTp = this.dateTp;
				if(dateTp == 1 && eduDtStr == ''){
					alert('교육일을 입력하셔야 합니다.');return;
				}
				if(dateTp == 2){
					if(startDtStr == '' && endDtStr == ''){
						alert('교육기간을 입력하셔야 합니다.');return;
					}
					if(weeks.length == 0){
						alert('요일을 입력하셔야 합니다.');return;
					}
				}
				
				for ( var i in this.timeInfo) {
					console.log(this.timeInfo[i].description);
					if(UTIL.isEmpty(this.timeInfo[i].description)){
						alert('수업명을 입력하셔야 합니다.');return;
					}
					times.push(JSON.stringify(vm_tab5_lectureTimeReg.timeInfo[i]));
				}
				
				$.ajax({
					type : 'post',
					data : {
						eduSeq : '${param.eduSeq}',
						timeSeq: timeSeq,
						dateTp : dateTp,
						startDtStr : startDtStr,
						endDtStr : endDtStr,
						eduDtStr : eduDtStr,
						weeks : weeks,
						times : times
					},
					url : '${utcp.ctxPath}/admin/ajax/addLectureTime.ajax',
					success : function(r) {
						if (r.result == 1) {
							//성공
							fn_tab('5');
							$('[data-remodal-id=md-tab5-lectureTimeReg]')
									.remodal().close();
						}else{
							alert(r.msg);
						}
					}
				});
			},
			choiceInstr: function(o,index){
				//this.timeInfo[index].checkNm = o.userNm;
				
			    Vue.set(this.timeInfo, index, Object.assign({}, this.timeInfo[index], { checkNm: o.userNm,checkId: o.userId }));
			},
			choiceMv: function(o,index){
				
				//this.timeInfo[index].checkNm = o.userNm;
			    Vue.set(this.timeInfo, index, Object.assign({}, this.timeInfo[index], { mvIdx: o.idx ,mvNm: o.title }));
			},
			choiceSub: function(o,index){
				console.log(o);
				console.log(index);
				//this.timeInfo[index].checkNm = o.userNm;
			    Vue.set(this.timeInfo, index, Object.assign({}, this.timeInfo[index], { subSeq: o.subSeq ,subNm: o.subNm }));
			},
			delInstr: function(o,index){
				//this.timeInfo[index].checkNm = o.userNm;
				
			    Vue.set(this.timeInfo, index, Object.assign({}, this.timeInfo[index], { checkNm: '',checkId:'' }));
			},
			delSub: function(o,index){
				//this.timeInfo[index].checkNm = o.userNm;
				
			    Vue.set(this.timeInfo, index, Object.assign({}, this.timeInfo[index], { subSeq: 0,subNm:'' }));
			},
		},
	});
	function fn_tab5_openLectureTimeReg(dateTp,timeSeq) {
		if(timeSeq == 0){
			vm_tab5_lectureTimeReg.timeInfo = [ $.extend(true, {}, tab5_timeInfo) ];
		}
		if(UTIL.isEmpty(dateTp)){
			dateTp = 1;
		}
		vm_tab5_lectureTimeReg.dateTp = dateTp;
		vm_tab5_lectureTimeReg.timeSeq = timeSeq;
		vm_tab5_lectureTimeReg.callTime(timeSeq);
		
		$('[data-remodal-id=md-tab5-lectureTimeReg]').remodal().open();
	}
</script>
