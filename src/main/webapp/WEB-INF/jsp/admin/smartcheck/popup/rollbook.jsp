<%@page import="com.educare.component.VarComponent"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<%@ page import="java.lang.Math"%>
<style>
#table-scroll thead{
	position: sticky;
	top: 0;
	z-index: 1;
}
#table-scroll thead th.fixed
,#table-scroll tbody td.fixed{
	position: sticky;
	left: 0;
}
#table-scroll tbody td.fixed{
	z-index: 2;
	background: #fff;
}
.roll_time {
	display: flex;
	margin-bottom: 15px;
}
.roll_time .timelist {
	margin-right: 15px;
	font-size: 0.875rem;
}
.roll_time span {
	display: inline-block;
}
.roll_time .listtit {
	background:#003964;
	color:#fff;
	padding:3px 10px;
	border-radius:999px;
	margin-right: 2px;
}
</style>



<!--// wrapper s //-->
	<div id="wrapper" class="cf">
		
		<!--// header -->
        <!--// cont_wrap //-->
        <div class="cont_wrap" style="">
			

            <!--// title_name //-->		
            <div class="title_name">
                <h1>${lctre.eduNm } 출석부
                </h1>
            </div>
            <!--// title_name //-->
            <section class="pd025 po_re">
            <ul class="roll_time">
            	<li class="timelist">
            		<span class="listtit">아침</span>
            		<span class="listtxt">오전8시 - 11시 59분 59초</span>
            	</li>
            	<li class="timelist">
            		<span class="listtit">점심</span>
            		<span class="listtxt">오전12시 - 오후 15시 59분 59초</span>
            	</li>
            	<li class="timelist">
            		<span class="listtit">저녁</span>
            		<span class="listtxt">오후 16시 - 20시</span>
            	</li>
            </ul>
                <div class="po_re br5 bs_box cf attBookWrap">
                        <div id="table-scroll" class="table-container">
                        <table id="main-table" class="tb tc main-table">
                            <caption>출석부 - 연번, 성명, 주민등록번호, 훈련생 유형, 훈련생 상태, 훈련 일수, 출석 일수, 결석 일수, 휴가 일수, 공가 일수, 출석률 일, 출석률 분 , 날짜 순으로 정보를 제공합니다.</caption>
                            <thead>
                                <tr>
                                    <th class="fixed" rowspan="2">연번</th>
                                    <th class="fixed" rowspan="2">성명</th>
                                    <th class="fixed" rowspan="2">소속기관명</th>
                                    <th class="fixed" rowspan="2">직급명</th>
                                    <!-- 
                                    <th rowspan="">훈련<br>일수</th>
                                    <th rowspan="">출석<br>일수</th>
                                    <th rowspan="">결석<br>일수</th>
                                    <th rowspan="">공가<br>일수</th>
                                    <th rowspan="">출석률<br>일<br>(%)</th>
                                    <th rowspan="">출석률<br>분<br>(%)</th>
                                     -->
                                    <template v-for="o in dateList">
									<th colspan="3">{{o.eduDt|fltDt2Str('YYYYMMDD','MM.DD (ddd)')}}
									<!-- 
									<br/>{{o.startTm|fltDt2Str('HHmm','HH:mm')}}~{{o.endTm|fltDt2Str('HHmm','HH:mm')}}
									 -->
									
									</th>
									</template>
                                </tr>
                                <tr>
                                	<template v-for="o in dateList">
                                	<th>
                                	아침<br/>
                                	<template v-if="o.isUpdQr1">
	                                	<input type="text" @input="formatTime(o,'qr1BeginTm')" v-model="o.qr1BeginTm" class="dp_b ip11 mg3" placeholder="00:00" maxlength="5"/> ~
	                                	<input type="text" @input="formatTime(o,'qr1EndTm')" v-model="o.qr1EndTm" class="dp_b ip11 mg3" placeholder="00:00" maxlength="5"/>
	                                	<a href="#none" @click="genQr(1,o)">저장</a>
	                                	<a href="#none" @click="updQrCancel(1,o)">취소</a>
                                	</template>
                                	<template v-else>
	                                	<template v-if="o.isQr1 == 1">
	                                	{{o.qr1BeginTm}} ~ {{o.qr1EndTm}}
	                                	<br/>
											<a :href="'javascript:window.open(\'${utcp.ctxPath }/DATA/upload/web/qr/attend_${lctre.eduSeq }_'+o.eduDt+'_1.png?${utcp.getNow2Str('yyyyMMddHHmmss')}\',\'preView\',\'width=310,height=420\');'"
												class="dp_ib fl"> 
												[확인]
											</a>
										</template>
	                                	<a href="#none" @click="updQr(1,o)">[qr생성]</a>
                                	</template>
                                	</th>
                                	<th>
                                	점심<br/>
                                	<template v-if="o.isUpdQr2">
	                                	<input type="text" @input="formatTime(o,'qr2BeginTm')" v-model="o.qr2BeginTm" class="dp_b ip11 mg3" placeholder="00:00" maxlength="5"/> ~
	                                	<input type="text" @input="formatTime(o,'qr2EndTm')" v-model="o.qr2EndTm" class="dp_b ip11 mg3" placeholder="00:00" maxlength="5"/>
	                                	<a href="#none" @click="genQr(2,o)">저장</a>
	                                	<a href="#none" @click="updQrCancel(2,o)">취소</a>
                                	</template>
                                	<template v-else>
	                                	<template v-if="o.isQr2 == 1">
		                                	{{o.qr2BeginTm}} ~ {{o.qr2EndTm}}<br/>
											<a :href="'javascript:window.open(\'${utcp.ctxPath }/DATA/upload/web/qr/attend_${lctre.eduSeq }_'+o.eduDt+'_2.png?${utcp.getNow2Str('yyyyMMddHHmmss')}\',\'preView\',\'width=310,height=420\');'"
												class="dp_ib fl"> 
												[확인]
											</a>
										</template>
	                                	<a href="#none" @click="updQr(2,o)">[qr생성]</a>
                                	</template>
                                	</th>
                                	<th>
                                	저녁<br/>
                                	<template v-if="o.isUpdQr3">
	                                	<input type="text" @input="formatTime(o,'qr3BeginTm')" v-model="o.qr3BeginTm" class="dp_b ip11 mg3" placeholder="00:00" maxlength="5"/> ~
	                                	<input type="text" @input="formatTime(o,'qr3EndTm')" v-model="o.qr3EndTm" class="dp_b ip11 mg3" placeholder="00:00" maxlength="5"/>
	                                	<a href="#none" @click="genQr(3,o)">저장</a>
	                                	<a href="#none" @click="updQrCancel(3,o)">취소</a>
                                	</template>
                                	<template v-else>
	                                	<template v-if="o.isQr3 == 1">
		                                	{{o.qr3BeginTm}} ~ {{o.qr3EndTm}}<br/>
											<a :href="'javascript:window.open(\'${utcp.ctxPath }/DATA/upload/web/qr/attend_${lctre.eduSeq }_'+o.eduDt+'_3.png?${utcp.getNow2Str('yyyyMMddHHmmss')}\',\'preView\',\'width=310,height=420\');'"
												class="dp_ib fl"> 
												[확인]
											</a>
										</template>
	                                	<a href="#none" @click="updQr(3,o)">[qr생성]</a>
                                	</template>
                                	</th>
                                	</template>
                                </tr>
                            </thead>
                            <tbody>
                                <tr v-for="(o,i) in stdntList">
                                	<td class="fixed">{{i+1}}</td>
									<td class="fixed">{{o.userNm}}</td>
									<td class="fixed">{{o.userOrgNm}}</td>
									<td class="fixed">{{o.userGradeNm}}</td>
                                    <!-- 
                                    <td>{{o.dateCnt}}</td>
                                    <td>{{o.attendCnt}}</td>
                                    <td>{{o.absentFinalCnt}}</td>
                                    <td>{{o.attend2Cnt}}</td>
                                    <td>{{o.attRatio}}</td>
                                    <td>{{o.att2Ratio}}</td>
                                     -->
									<template v-for="o2 in o.stdntAttList">
									<td>
										<span class="dp_b tc fs_18">{{o2.at1Cd}}</span>
                                        <template v-if="o2.isUpd1">
                                        <input @input="formatTime(o2,'at1HhMm')" v-model="o2.at1HhMm" type="text" placeholder="00:00" class="dp_b ip11 mg3" maxlength="5">
                                        <button type="button" class="btn04" @click="updAttProc(1,o2)">저장</button>
                                        </template>
                                        <template v-else>
                                        {{o2.at1HhMm}}
                                        <c:if test="${isCheck }">
                                        <br/><button type="button" class="btn04" @click="updAtt(1,o2)">수정</button>
                                        </c:if>
                                        </template>
									</td>
									<td>
										<span class="dp_b tc fs_18">{{o2.at2Cd}}</span>
                                        <template v-if="o2.isUpd2">
                                        <input @input="formatTime(o2,'at2HhMm')" v-model="o2.at2HhMm" type="text" placeholder="00:00" class="dp_b ip11 mg3" maxlength="5">
                                        <button type="button" class="btn04" @click="updAttProc(2,o2)">저장</button>
                                        </template>
                                        <template v-else>
                                        {{o2.at2HhMm}}
                                        <c:if test="${isCheck }">
                                        <br/><button type="button" class="btn04" @click="updAtt(2,o2)">수정</button>
                                        </c:if>
                                        </template>
									</td>
									<td>
										<span class="dp_b tc fs_18">{{o2.at3Cd}}</span>
                                        <template v-if="o2.isUpd3">
                                        <input @input="formatTime(o2,'at3HhMm')" v-model="o2.at3HhMm" type="text" placeholder="00:00" class="dp_b ip11 mg3" maxlength="5">
                                        <button type="button" class="btn04" @click="updAttProc(3,o2)">저장</button>
                                        </template>
                                        <template v-else>
                                        {{o2.at3HhMm}}
                                        <c:if test="${isCheck }">
                                        <br/><button type="button" class="btn04" @click="updAtt(3,o2)">수정</button>
                                        </c:if>
                                        </template>
									</td>
									</template>
								</tr>
                            </tbody>
                        </table>		
                        </div>		
                </div>
                <button class="btn02 btn_green" onclick="fn_excelStdnt(); return false;">엑셀 다운로드</button> 
            </section>
			
		</div>
		<!--// cont_wrap //-->
			
	</div>
	<!--// wrapper e //-->
	
	<script>
var vm_smartcheck = new Vue({
	el: '#table-scroll',
	data: {
		dateList : [],
		stdntList : [],
		formattedTime:'',
	},
	mounted: function(){
		this.callRollBook();
	},
	methods: {
		checkTimeFormat: function(time) {
			console.log(time);
			// null 체크
		    if (time === null) {
		        return true; // 형식 통과 실패 처리
		    }
		 	// 공백만 있는 경우 처리
		    if (/^\s*$/.test(time)) {
		        return true; // 형식 통과 실패 처리
		    }
			const timePattern = /^\s*([01]?\d|2[0-3])\s*:\s*([0-5]\d)\s*$/;
	       	return timePattern.test(time);
	    },
		updAttProc: function(atNo,o){
			var _this = this;
			var eduSeq = '${param.eduSeq}';
			var userId = o.userId;
			var eduDt = o.eduDt;
			var hhMm = '';
			if(atNo == 1){
				hhMm = o.at1HhMm;
			}else if(atNo == 2){
				hhMm = o.at2HhMm;
			}else if(atNo == 3){
				hhMm = o.at3HhMm;
			}
			var qrType = atNo;
			var attTp = o.isAttTp?2:1;
			
			if(!this.checkTimeFormat(hhMm) ){
				alert('시간형태가 올바르지 않습니다. HH:mm형태로 입력하시기 바랍니다.');
				return;
			}
			
			$.ajax({
				type: 'post',
				data: {eduSeq: eduSeq, userId: userId, eduDt: eduDt, attTp: attTp, qrType: qrType, hhMm: hhMm},
				url: '${utcp.ctxPath}/admin/smartCheck/attDayProc2.ajax',
				success: function(r){
					if(r.result == 1){
						_this.callRollBook();
						return;
					}
					alert(r.msg);
				}
			});
		},
		updAtt: function(atNo,o){
			this.stdntList.forEach(item => {
			  	item.stdntAttList.forEach(item2 => {
				  	if(atNo == 1){
				  		item2.isUpd1 = false;
				  	}else if(atNo == 2){
				  		item2.isUpd2 = false;
				  	}else if(atNo == 3){
				  		item2.isUpd3 = false;
				  	}
			        if(o.userId == item2.userId && o.eduDt == item2.eduDt){
				  		if(atNo == 1){
					  		item2.isUpd1 = true;
					  	}else if(atNo == 2){
					  		item2.isUpd2 = true;
					  	}else if(atNo == 3){
					  		item2.isUpd3 = true;
					  	}
			        }  
			  	});
			});
			this.$forceUpdate();
		},
		callRollBook: function(){
			var _this = this;
			var eduSeq = '${param.eduSeq}';
			$.ajax({
				url : '${utcp.ctxPath}/admin/smartCheck/rollBookDay.ajax',
				data : {
					eduSeq : eduSeq,
				},
				success : function(r) {
					if(r.result == 1){
	 					_this.dateList = r.data.dateList;
						_this.stdntList = r.data.stdntList;
						
						console.log(_this.dateList);
						console.log(_this.stdntList);
						
						_this.stdntList.forEach(item => {
						  	item.stdntAttList.forEach(item2 => {
						          item2.isAttTp = item2.attTp == 2;
						  	});
						});
						_this.dateList.forEach(item => {
						  	item.qr1BeginTm = Vue.filter('fltDate2Str')(item.qr1BeginDe,'HH:mm');
						  	item.qr1EndTm = Vue.filter('fltDate2Str')(item.qr1EndDe,'HH:mm');
						  	item.qr2BeginTm = Vue.filter('fltDate2Str')(item.qr2BeginDe,'HH:mm');
						  	item.qr2EndTm = Vue.filter('fltDate2Str')(item.qr2EndDe,'HH:mm');
						  	item.qr3BeginTm = Vue.filter('fltDate2Str')(item.qr3BeginDe,'HH:mm');
						  	item.qr3EndTm = Vue.filter('fltDate2Str')(item.qr3EndDe,'HH:mm');
						});
						
						// ✅ 특정 userId만 필터링
						if ('${param.userId}') {
							var userId = '${param.userId}';
							_this.stdntList = _this.stdntList.filter(item => item.userId == userId);
						}
					}
				},
			});
		},
		genQr: function(qrType,o){
			if(!confirm('qr생성 하시겠습니까?')){
				return;
			}
			var qrDt = o.eduDt;
			var qrBeginTm = '';
			var qrEndTm = '';
			if(qrType == 1){
				qrBeginTm = o.qr1BeginTm;
				qrEndTm = o.qr1EndTm;
			}else if(qrType == 2){
				qrBeginTm = o.qr2BeginTm;
				qrEndTm = o.qr2EndTm;
			}else if(qrType == 3){
				qrBeginTm = o.qr3BeginTm;
				qrEndTm = o.qr3EndTm;
			}
			var eduSeq = '${param.eduSeq}';
			
			if(!this.checkTimeFormat(qrBeginTm) || !this.checkTimeFormat(qrEndTm) ){
				alert('시간형태가 올바르지 않습니다. HH:mm형태로 입력하시기 바랍니다.');
				return;
			}
			
			$.ajax({
				type:'post',
				data:{eduSeq: eduSeq, qrType: qrType,qrDt: qrDt, qrBeginTm: qrBeginTm, qrEndTm: qrEndTm},
				url:'${utcp.ctxPath}/admin/ajax/createQrAttend.ajax',
				success:function(r){
					console.log(r);
					if(r.result == 1){
						location.reload();
					}
				}
			});
		},
		updQr: function(atNo,o){
			this.dateList.forEach(item => {
		  		item.isUpdQr1 = false;
		  		item.isUpdQr2 = false;
		  		item.isUpdQr3 = false;
			});
			if(atNo == 1){
		  		o.isUpdQr1 = true;
		  	}else if(atNo == 2){
		  		o.isUpdQr2 = true;
		  	}else if(atNo == 3){
		  		o.isUpdQr3 = true;
		  	}
			this.$forceUpdate();
		},
		updQrCancel: function(atNo,o){
			if(atNo == 1){
		  		o.isUpdQr1 = false;
		  	}else if(atNo == 2){
		  		o.isUpdQr2 = false;
		  	}else if(atNo == 3){
		  		o.isUpdQr3 = false;
		  	}
			this.$forceUpdate();
		},
		formatTime : function(o, key) {
			var val = o[key].replace(/\D/g, ''); // 숫자만 추출
			if (val.length > 4)
				val = val.slice(0, 4); // 최대 4자리까지만 허용

			var formatted = '';
			if (val.length >= 3) {
				formatted = val.slice(0, 2) + ':' + val.slice(2);
			} else {
				formatted = val;
			}
			o[key] = formatted;
			this.$forceUpdate();
		},
	},
});

function fn_excelStdnt(){
//	location.href = "${utcp.ctxPath}/admin/excel/rollBook2.do?eduSeq=${lctre.eduSeq}&userId=${param.userId}";
	location.href = "${utcp.ctxPath}/admin/excel/rollBook2.do?eduSeq=${lctre.eduSeq}";
}
</script>