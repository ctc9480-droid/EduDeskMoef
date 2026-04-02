<%@page import="com.educare.component.VarComponent"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<%@ page import="com.educare.edu.member.service.SessionUserInfoHelper"%>
<!--// 230906hy 개인교육수정 //-->
<div class="remodal" data-remodal-id="md-lcrcpInfo" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc" id="vm-lcrcpInfo" data-remodal-options="closeOnOutsideClick: false">
	<button data-remodal-action="close" class="remodal-close" aria-label="Close"></button>
	<div class="modal-content">
		<!-- 
		<div class="modal-header">
			<p class="tit alignC">
				<i class="fas fa-chevron-circle-right fs_22 pdr5"></i>신청내역 수정
			</p>
		</div>
		-->
		<div class="modal-body">
			<div class="eduRcept">
				<h3 class="h3label">교육정보</h3>
				<table class="tb04 w100">
					<tbody>
						<tr>
							<th>교육명</th>
							<td colspan="3">[{{lctre.ctg1Nm}} > {{lctre.ctg2Nm}}] <br/>{{lctre.eduNm}}</td>
						</tr>
						<tr>
							<th>신청기간</th>
							<td>
								{{lctre.rceptPeriodBegin|fltDt2Str('YYYYMMDDhhmm','YYYY-MM-DD HH:mm')}} ~ 
								{{lctre.rceptPeriodEnd|fltDt2Str('YYYYMMDDhhmm','YYYY-MM-DD HH:mm')}}
							</td>
							<th>교육기간</th>
							<td>{{lctre.eduPeriodBegin}} ~ {{lctre.eduPeriodEnd}}</td>
						</tr>
					</tbody>
				</table>
				<h3 class="h3label">신청자정보</h3>
				<form action="#" method="post" id="lcrcpForm">
					<table class="tb04 w100">
						<tbody>
							<tr>
								<th>예약자명</th>
								<td>{{lcrcp.userNm}}</td>
								<th>생년월일</th>
								<td>{{user.birth}}</td>
								<th>성별</th>
								<td>{{user.addMfTypeNm}}</td>
							</tr>
							<tr>
								<th scope="row">연락처</th>
								<td colspan="5">
								{{lcrcp.mobile}}
								<!-- 
								<select v-model="lcrcp.mobile1" id="mobile1" title="휴대폰번호 선택" class="mobile1">
										<option value="010" selected="selected">010</option>
										<option value="011">011</option>
										<option value="016">016</option>
										<option value="017">017</option>
										<option value="018">018</option>
										<option value="019">019</option>
								</select> <input type="text" v-model="lcrcp.mobile2" maxlength="4" class="ip9" value=""> <input type="text" v-model="lcrcp.mobile3" maxlength="4" class="ip9" value="">
								 -->
								</td>
							</tr>
							<tr>
								<th scope="row">이메일</th>
								<td colspan="5">
								{{lcrcp.email}}
								<!--
								<input type="text" v-model="lcrcp.email1" class="ip1"> @ 
								<input type="text" class="ip1" v-model="lcrcp.email2"> 
								<select class="userEmail3" v-model="lcrcp.email3" @change="choiceEmail()">
										<option value="">직접입력</option>
										<option value="naver.com">naver.com</option>
										<option value="gmail.com">gmail.com</option>
										<option value="korea.kr">korea.kr</option>
								</select>
								 -->
								</td>
							</tr>
							
							<tr>
						<th scope="row">숙소이용선택</th>
						<td>
						<input v-model="lcrcp.dormiCapaCnt" type="radio" name="vm_dormiCapaCnt" value="0" id="vm_dormiCapaCnt1" />
						<label for="vm_dormiCapaCnt1">미이용</label>
						<input v-model="lcrcp.dormiCapaCnt" type="radio" name="vm_dormiCapaCnt" value="2" id="vm_dormiCapaCnt2" />
						<label for="vm_dormiCapaCnt2">2인실</label>
						<input v-model="lcrcp.dormiCapaCnt" type="radio" name="vm_dormiCapaCnt" value="4" id="vm_dormiCapaCnt3" />
						<label for="vm_dormiCapaCnt3">4인실</label>
						(<input v-model="lcrcp.isDormiAccess" type="checkbox" id="vm_dormiAccess" /> <label for="vm_dormiAccess" >장애인용</label>)
						<br>(* 숙소 이용 시 숙박비는 1만원~4만원)
						</td>
					</tr>
					<tr>
						<th scope="row">버스이용</th>
						<td>
						<input v-model="lcrcp.useTrans" type="radio" name="vm_useTrans" value="0" id="vm_useTrans1" />
						<label for="vm_useTrans1">미이용</label>
						<input v-model="lcrcp.useTrans" type="radio" name="vm_useTrans" value="1" id="vm_useTrans2" />
						<label for="vm_useTrans2">왕복</label>
						<input v-model="lcrcp.useTrans" type="radio" name="vm_useTrans" value="2" id="vm_useTrans3" />
						<label for="vm_useTrans3">입교</label>
						<input v-model="lcrcp.useTrans" type="radio" name="vm_useTrans" value="3" id="vm_useTrans4" />
                                                <label for="vm_useTrans3">퇴교</label>
						</td>
					</tr>
							
							<!-- 
							<template v-if="lctre.fee > 0 ">
							<tr>
								<th>결제방법 <span class="red">*</span></th>
								<td>
									<c:forEach items="<%=VarComponent.FEE_TP %>" var="o" varStatus="s">
											<c:if test="${s.index!=0}">
											<input type="radio" name="feeTp" id="feeTp${s.index }" value="${s.index }" v-model="lcrcp.feeTp" style="width:12px;" /> 
											<label for="feeTp${s.index }">${o }</label>&nbsp;
											</c:if>
										</c:forEach>
										<br/>※KTL 직원은 현장결제 선택
								</td>
							</tr>
							</template>
							 -->

							<tr v-for="(o,i) in etcIemData.etcIemList">
								<th>{{o.etcIemNm}}</th>
								<td colspan="5"><template v-if="o.dataInputTy == 'radio' "> 
								<template v-for="(o2,i2) in o.etcIemEx.split(',')"> 
								<input v-model="o.etcIemData" type="radio" :id="'etcIemSeq_'+o.etcIemSeq+'_'+i2" :name="'etcIemSeq_'+o.etcIemSeq" :value="o2" /> 
								<label :for="'etcIemSeq_'+o.etcIemSeq+'_'+i2">{{o2 }}</label> 
								</template> 
								</template> 
								<template v-else-if="o.dataInputTy == 'checkbox' "> 
								<template v-for="(o2,i2) in o.etcIemEx.split(',')"> 
								<input v-model="o.etcIemData2" type="checkbox" :name="'etcIemSeq_'+o.etcIemSeq" :id="'etcIemSeq_'+o.etcIemSeq+'_'+i2" :value="o2" /> 
								<label :for="'etcIemSeq_'+o.etcIemSeq+'_'+i2">{{o2 }}</label> </template> </template> <template v-else-if="o.dataInputTy == 'select' "> 
								<select v-model="o.etcIemData">
										<template v-for="(o2,i2) in o.etcIemEx.split(',')">
										<option :value="o2">{{o2}}</option>
										</template>
									</select> </template> <template v-else> <input type="text" v-model="o.etcIemData" id="'etcIemSeq_'+o.etcIemSeq+'_0'" /> </template></td>
							</tr>

							<tr style="display: none;">
								<th>첨부파일</th>
								<td colspan="5"><a v-if="lcrcp.fileOrg" :href="'${utcp.ctxPath}/admin/edu/attach/'+lcrcp.eduSeq+'/'+lcrcp.userId+'/rceptDownload.do'">[다운로드]</a></td>
							</tr>
						</tbody>
					</table>
				</form>
			</div>
		</div>
		<div class="modal-footer pdt20">
			<div class="tc">
				<!-- 
				<button v-if="!isPlay" type="button" class="remodal-confirm btn01 btn_green" onclick="fn_updateLcrcp();">저장</button>
				<button type="button" class="remodal-confirm btn01 btn_green" onclick="fn_updateLcrcp();">저장</button>
				 -->
				<button type="button" class="remodal-cancel btn01 btn_gray" data-remodal-action="close">닫기</button>
			</div>
		</div>
	</div>
</div>
<!--// 단체교육수정 //-->
<script>
var vm_lcrcpInfo = new Vue({
	el : '#vm-lcrcpInfo',
	data : {
		lcrcp : {},
		lctre : {},
		etcIemData : {},
		fee : 0,
		isPlay : 0,
		user: {},
	},
	methods:{
		choiceEmail : function(){
			console.log(this.lcrcp.email3);
			this.lcrcp.email2 = this.lcrcp.email3;
		},
		convNewlines: function(text) {
			if(UTIL.isEmpty(text)){
				return text;
			}
          	return text.replace(/\n/g, '<br>');
        }
	}
});
function md_openLcrcpInfo(eduSeq,rceptSeq,userId){
	$.ajax({
		url : '${utcp.ctxPath}/admin/ajax/callLcrcpInfo.ajax',
		data : {eduSeq : eduSeq,rceptSeq : rceptSeq,userId : userId},
		success : function (r){
			
			if(r.result != 1){
				alert(r.msg);
				return;
			}
			//console.log(r.data);
			vm_lcrcpInfo.lcrcp = r.data.lcrcp;
			vm_lcrcpInfo.lctre = r.data.lctre;
			vm_lcrcpInfo.user = r.data.user;
			if(!UTIL.isEmpty(r.data.lcrcp.mobile)){
				var mobileArr = r.data.lcrcp.mobile.split('-');
				vm_lcrcpInfo.lcrcp.mobile1 = mobileArr[0];
				vm_lcrcpInfo.lcrcp.mobile2 = mobileArr[1];
				vm_lcrcpInfo.lcrcp.mobile3 = mobileArr[2];
			}
			if(r.data.lcrcp.email != null){
				var emailArr = r.data.lcrcp.email.split('@');
				vm_lcrcpInfo.lcrcp.email1 = emailArr[0];
				vm_lcrcpInfo.lcrcp.email2 = emailArr[1];
				vm_lcrcpInfo.lcrcp.email3 = emailArr[1];
			}
			vm_lcrcpInfo.etcIemData = JSON.parse(r.data.etcIemJson);
			console.log(vm_lcrcpInfo.etcIemData);
			if(vm_lcrcpInfo.etcIemData.etcIemList.length){
				for(var i in vm_lcrcpInfo.etcIemData.etcIemList){
					var etcIem = vm_lcrcpInfo.etcIemData.etcIemList[i];
					etcIem.etcIemData2 = [];
					if(etcIem.dataInputTy == 'checkbox' && etcIem.etcIemData){
						etcIem.etcIemData2 = etcIem.etcIemData.split(',');
					}
				}
			}
			vm_lcrcpInfo.lcrcp.isDormiAccess = false;
			if(vm_lcrcpInfo.lcrcp.dormiAccessYn == 'Y'){
				vm_lcrcpInfo.lcrcp.isDormiAccess = true;
			}
			vm_lcrcpInfo.isPlay = r.data.isPlay;
			
			
			$('[data-remodal-id=md-lcrcpInfo]').remodal().open();
			location.href='#none';
		}
	});
}
function fn_updateLcrcp() {
	var rceptSeq = vm_lcrcpInfo.lcrcp.rceptSeq;
	var feeTp = vm_lcrcpInfo.lcrcp.feeTp;
	
	//기타항목 
	var etcIemDataJson='';
	if(vm_lcrcpInfo.lctre.etcIemYn == 'Y'){
		if(vm_lcrcpInfo.etcIemData.etcIemList.length){
			for(var i in vm_lcrcpInfo.etcIemData.etcIemList){
				var etcIem = vm_lcrcpInfo.etcIemData.etcIemList[i];
				if(etcIem.dataInputTy == 'checkbox'){
					etcIem.etcIemData='';
					for(var i2 in etcIem.etcIemData2){
						console.log(etcIem.etcIemData2[i2]);
						if(i2 > 0){
							etcIem.etcIemData += ',';
						}
						etcIem.etcIemData += etcIem.etcIemData2[i2];
					}
				}
				
				if (etcIem.essntlInputYn == 'Y') {
					if (!etcIem.etcIemData) {
						alert(etcIem.etcIemNm + '을(를) 입력하셔야합니다.');
						$('#etcIemSeq_' + etcIem.etcIemSeq + '_0').focus();
						return;
					}
				}
			}
		}
		etcIemDataJson = JSON.stringify(vm_lcrcpInfo.etcIemData);
	}

	//신청명단데이타
	var formData = new FormData();
	formData.append("rceptSeq", rceptSeq);
	formData.append("etcIemDataJson", etcIemDataJson);
	formData.append("feeTp", feeTp);
	
	formData.append('etc',vm_lcrcpInfo.lcrcp.etc);
	$
			.ajax({
				processData : false,
				contentType : false,
				url : "${utcp.ctxPath}/user/ajax/lcrcpUpdate.ajax",
				type : "post",
				data : formData,
				cache : false,
				async : true,
				success : function(r) {
					if (r.result == 1) {
						alert('수정되었습니다.');
						$('[data-remodal-id=md-lcrcpInfo]').remodal().close();
						location.reload();
					}else{
						alert(r.msg);
					}
				}
			});
}
</script>

<!--// 출석현황,전자출결을 사용했을 경우만 //-->
<div class="remodal attModal" data-remodal-id="scView" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc" id="vm-checktable">
	<button data-remodal-action="close" class="remodal-close" aria-label="Close"></button>
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC"><i class="fas fa-chevron-circle-right fs_22 pdr5"></i>온라인 출결현황</p>
		</div>
		<div class="modal-body">
			<div class="tbBox">
				<comp-md-checktable-1 :date_list="dateList" :time_list="timeList" :stdnt_list="stdntList"></comp-md-checktable-1>
			</div>
		</div>
		<div class="modal-footer">
			<div class="tc">
				<button type="button" onclick="fn_checktable_excel()" class="remodal-confirm btn01 btn_green">EXCEL 다운</button>
			</div>
		</div>
	</div>
</div>
<!--//  //-->
<script type="text/x-template" id="tmp-md-checktable-1">
				<table class="tc w100 tb">
					<thead bgcolor="#f7f8fa">
						<tr>
							<th rowspan="2">이름</th>
							<th rowspan="2">아이디</th>
							<template v-for="o in date_list">
							<th :colspan="o.eduDtCnt">{{o.eduDt|fltDt2Str('YYYYMMDD','MM.DD (ddd)')}}</th>
							</template>
							<th rowspan="2" class="blGray">합계</th>
						</tr>
						<tr>
							<th v-for="o in time_list">{{o.timeSeq}}</th>
						</tr>
					</thead>
					<tbody>
						<tr v-for="o in stdnt_list">
							<td>{{o.userNm}}</td>
							<td>{{o. loginId}}</td>
							<td v-for="o2 in o.stdntAttList">{{o2.addAttStr}}</td>
							<td>{{o.attendCnt}} / {{o.timeCnt}}</td>
						</tr>
					</tbody>
				</table>
</script>
<script>
Vue.component('comp-md-checktable-1', {	
	template: '#tmp-md-checktable-1',
	props:['date_list','time_list','stdnt_list'],
});
	var vm_checktable = new Vue({
		el : '#vm-checktable',
		data : {
			eduSeq : 0,
			userId : '',
			dateList : [],
			timeList : [],
			stdntList : [],
		},
	});
	function fn_openCheckList(eduSeq, userId) {
		$.ajax({
			url : '${utcp.ctxPath}/admin/ajax/rollBook.json',
			data : {
				eduSeq : eduSeq,
				userId : userId
			},
			success : function(r) {
				vm_checktable.dateList = r.dateList;
				vm_checktable.timeList = r.timeList;
				vm_checktable.stdntList = r.stdntList;
				vm_checktable.eduSeq = eduSeq;
				vm_checktable.userId = userId;

				$('[data-remodal-id=scView]').remodal().open();
				location.href = '#none';
			},
		});
	}
	function fn_checktable_excel() {
		console.log('userId : ' + vm_checktable.userId);
		var userId = vm_checktable.userId;
		if(typeof userId =='undefined'){
			userId='';
		}
		location.href = '${utcp.ctxPath}/admin/excel/rollBook.do?eduSeq='
				+ vm_checktable.eduSeq + '&userId=' + userId;
	}
</script>


<div class="remodal messagePop messagePop2" data-remodal-id="md-alert" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc" id="vm-alert">
	<input type="hidden" id="md-name" value="#" />
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC">알림</p>
		</div>
		<div class="modal-body">
			<p class="messageTxt" id="messageStr">{{msg}}</p>
		</div>
		<div class="modal-footer">
			<div class="tc">
				<button onclick="location.href=$('#md-name').val();" class="remodal-confirm btn02 btn_orange">확인</button>
			</div>
		</div>
	</div>
</div>
<script>
	var vm_alert = new Vue({
		el : '#vm-alert',
		data : {
			msg : ''
		},
	});
</script>

<!-- 동영상재생 팝업 -->
<div class="remodal onlineView" id="vm-player" data-remodal-id="md-mov" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc" data-remodal-options="closeOnOutsideClick: false">
	<button onclick="fn_md_mov_close()" class="remodal-close" aria-label="Close"></button>
	<div class="modal-content">
		<div class="modal-body po_re oh">
			<div class="onlineListWrap">
				<div class="toggleBtn">
					<img src="${utcp.ctxPath}/resources/user/image/arrowLeft.png" alt="동영상 리스트 열림닫힘 버튼" />
				</div>
				<div class="onlineList">
					<div class="pagination">
						<a :href="chapterData[chapterSeq-1]?('javascript:fn_loadMovTime('+chapterData[chapterSeq-1].mvTime+');'):'javascript:;'"><i class="fas fa-chevron-circle-left"></i></a> <a href="javascript:;"><strong>{{chapterSeq+1}}</strong> / {{chapterData.length}}</a> <a :href="chapterData[chapterSeq+1]?('javascript:fn_loadMovTime('+chapterData[chapterSeq+1].mvTime+');'):'javascript:;'"><i class="fas fa-chevron-circle-right"></i></a>
					</div>
					<div class="palyList">
						<ul>
							<li v-for="(o,i) in chapterData">
								<a :href="'javascript:fn_loadMovTime('+o.mvTime+');'"> <span class="num">{{i+1}}</span> <span class="text">{{o.title}}</span>
								</a>
							</li>
						</ul>
					</div>
				</div>
				<!--// onlineList //-->
			</div>
			<div class="videoWrap" id="quiz-area" style="display: none;"></div>
			<div class="videoWrap" id="mov-area">
				<video class="w100 videoCon" id="md-mov-video" controls="controls" style="width: 100%;" controlsList="nodownload" oncontextmenu="return false;"></video>
			</div>
		</div>
		<div class="modal-footer">
			<div v-if="chapterData.length" class="pagination">
				<a :href="chapterData[chapterSeq-1]?('javascript:fn_loadMovTime('+chapterData[chapterSeq-1].mvTime+');'):'javascript:;'"><i class="fas fa-chevron-circle-left"></i></a> <a href="javascript:;"><strong>{{chapterSeq+1}}</strong> / {{chapterData.length}}</a> <a :href="chapterData[chapterSeq+1]?('javascript:fn_loadMovTime('+chapterData[chapterSeq+1].mvTime+');'):'javascript:;'"><i class="fas fa-chevron-circle-right"></i></a>
			</div>
		</div>
	</div>
</div>
<script>
var video;
//player vm
var vm_player = new Vue({
	el:'#vm-player',
	data:{quizShowList:[],
		chapterData : [{title:'${lctre.eduNm}',movTime:'0'}],
		chapterSeq : 0,
		chapterTime0 : 0,
		chapterTime1 : 0,
	},
	
});
document.getElementById('md-mov-video').addEventListener('timeupdate', function(e){
	
	//퀴즈나오는 시간
	var currentTime=this.currentTime*1;
	for ( var i in vm_player.quizShowList) {
		var quizShow = vm_player.quizShowList[i];
		var showTime = parseFloat(quizShow.mvTime);
		if (!vm_player.quizShowList[i].isPass
				&& currentTime > showTime
				&& currentTime < (showTime + 1)) {
			vm_player.quizShowList[i].isPass = true;
			$('#quiz-area').html('<iframe src="${utcp.getCdnUrl('upload/webCon/') }'+vm_player.quizShowList[i].url+'" class="iframeP" title="퀴즈프레임" allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture; mute();" frameborder="0" allowfullscreen></iframe>');
			$('#quiz-area').css('display', 'block');
			$('#mov-area').css('display', 'none');
			video.pause();
			if (video.exitFullscreen) {
				video.exitFullscreen();
			} else if (video.mozCancelFullScreen) {
				video.mozCancelFullScreen();
			} else if (video.webkitExitFullScreen) {
				video.webkitExitFullScreen();
			}

			$('#vm-player').focus();
			break;
		}
	}
	
	//챕터번호체크
	for ( var i in vm_player.chapterData) {
		var chapter = vm_player.chapterData[i];
		var chapterTime = parseFloat(chapter.mvTime);
		vm_player.chapterSeq = i*1;
		if (chapterTime > this.currentTime) {
			vm_player.chapterSeq--;
			break;
		}
	}
});
//퀴즈종료되면 발생하는 이벤트
window.addEventListener('message', function(e) {
	$('[id^=quiz-area]').css('display','none');
	$('#mov-area').css('display','');
	if(!UTIL.isEmpty(video)){
		video.play();
	}
});
//
$(document).on('closed', '.remodal', function (e) {
	if(e.currentTarget.id=='vm-player'){
		if(video){
			video.pause();
		}
	}
});
	function fn_playMov(mvIdx) {
		$.ajax({
			type : 'get',
			data : {
				mvIdx : mvIdx,
			},
			url : '${utcp.ctxPath}/admin/ajax/lectureMov.json',
			success : function(r) {
				if (r.result == 1) {
					
					var url = '${utcp.getCdnUrl('mov/')}'+r.mov.orgCd+'/'+r.mov.fileRename;
					video = document.getElementById('md-mov-video');
					video.src = url;
					location.href = '#md-mov';
					
					//퀴즈,챕터세팅
					vm_player.chapterData=[];
					vm_player.quizShowList=[];
					for(var i in r.movTime){
						if(r.movTime[i].type == 0){
							vm_player.chapterData.push(r.movTime[i]);
						}else{
							vm_player.quizShowList.push(r.movTime[i]);
						}
					}
					
					//퀴즈 오프,동영상 온
					$('[id^=quiz-area]').css('display','none');
					$('#mov-area').css('display','');
					
					return;
				}
				vm_alert.msg = r.message;
				location.href = '#md-alert';
			}
		});
	}
function fn_md_mov_close(){
	video = document.getElementById('md-mov-video');
	if(!video.paused){
		video.pause();
    	video.currentTime = 0;
	}
	$('[data-remodal-id=md-mov]').remodal().close();
}
function fn_loadMovTime(movTime) {
	video.currentTime = movTime;
	video.pause();
}
//playlist toggle //
$(".toggleBtn").click(
	function() {
		if ($(".onlineListWrap").css("right") == "0px"){
			$(".toggleBtn").children("img").attr({
				"src" : "${utcp.ctxPath}/resources/user/image/arrowLeft.png"
			})
			$(".onlineListWrap").stop().css("right", "-300px");
		} else {
			$(".toggleBtn").children(".onlineListWrap").stop().css(
					"right", "0px");
			$(".toggleBtn").children("img").attr({
				"src" : "${utcp.ctxPath}/resources/user/image/arrowLeft.png"
			})
			$(".toggleBtn").children("img").attr({
				"src" : "${utcp.ctxPath}/resources/user/image/arrowRight.png"
			})
			$(".onlineListWrap").stop().css("right", "0px");
		}
});

$(".toggleBtn").click(function(){
    $(".onlineListWrap").toggleClass("hh");
});

</script>

<script>
	function receipt(pgPayNo, payType) {
		var controlNo = pgPayNo;
		var payment = '01';
		if (payType == '2') {
			payment = '02';
		} else if (payType == '3') {
			payment = '03';
		}

		window
				.open(
						'<spring:eval expression="@prop['pay.easypay.receiptUrl']"/>?controlNo='
								+ controlNo + '&payment=' + payment,
						'MEMB_POP_RECEIPT',
						'toolbar=0,scroll=1,menubar=0,status=0,resizable=0,width=380,height=700');
	}
</script>

<div class="remodal" data-remodal-id="md-sms-send" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc" style="width: 480px;" id="vm-mdSendSms" data-remodal-options="closeOnOutsideClick: false">
	<button data-remodal-action="close" class="remodal-close" aria-label="Close"></button>
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC pdt0"><i class="fas fa-chevron-circle-right fs_22 pdr5"></i>문자발송</p>
		</div>
		<div class="modal-body">
			<table class="tb01 w100">
				<tbody>

					<tr>
						<th>발신번호<span class="required pd5">&nbsp;</span></th>
						<td class="tl"><input type="text" maxlength="13" v-model="fromNum" class="ip2"></td>

					</tr>
					<tr>
						<th>수신번호<span class="required pd5">&nbsp;</span></th>
						<td class="tl"><input type="text" maxlength="130" class="ip2 send-num" v-model="toNum" id="toNum"></td>
					</tr>

					<tr>
						<th>메시지<span class="required pd5">&nbsp;</span></th>
						<td colspan="" class="tl"><textarea v-model="toMsg" class="ta_size"></textarea></td>
					</tr>

				</tbody>
			</table>

		</div>
		<div class="modal-footer">
			<div class="tc">
				<button type="button" :onclick="'fn_md_smsSendProc('+callback+')'" class="remodal-confirm btn01 btn_green">발송</button>
			</div>
		</div>
	</div>
</div>
<script>
var vm_sms_msg = '';
vm_sms_msg += '0000년 0월 안전교육시스템에서 신청하신 안전교육이 현재 미수료 상태로, 0/0(0)까지 수강 완료하셔야 합니다. \n';
vm_sms_msg += '(마이페이지-진행중 교육 클릭-진도율 100% 여부 확인)\n';
vm_sms_msg += '* 0월 신청하신 교육과정 중 1개라도 미이수하신 경우 문자 발송됨\n\n';
vm_sms_msg += '※ (문의: 044-215-2660) \n';
var vm_mdSendSms = new Vue({
	el : '#vm-mdSendSms',
	data : {
		fromNum : '044-215-2660',
		toNum : '',
		toMsg : vm_sms_msg,
		userId : '',
		callback : null,
	},
});
function fn_md_smsSend(fromNum,toNum,userId,callback,readonly){
 	$('#toNum').attr("readonly", false);
	console.log(fromNum);
	if(fromNum != ''){
// 		console.log(fromNum);
		vm_mdSendSms.fromNum = fromNum;
	}
	vm_mdSendSms.toNum = toNum;
	vm_mdSendSms.userId = userId;
	vm_mdSendSms.callback = callback;
	$('[data-remodal-id=md-sms-send]').remodal().open();
}
function fn_md_smsSendProc(callback){
	var fromNum = vm_mdSendSms.fromNum;
	var toNum = vm_mdSendSms.toNum;
	var toMsg = vm_mdSendSms.toMsg;
	var userId = vm_mdSendSms.userId;
	
	if(toNum==''){
		alert('수신번호를 입력하세요.');
		return;
	}
	if(toMsg==''){
		alert('메시지를 입력하세요.');
		return;
	}
	
	if(!confirm('문자를 발송하시겠습니까?')){
		return;
	}
	
	$.ajax({
		type : 'post',
		data : {toNumList : toNum, fromNum : fromNum, toMsg : toMsg, userId : userId},
		url : '${utcp.ctxPath}/admin/ajax/setSendSms.ajax',
		success : function (r){
			if(r.result == 1){
				$('[data-remodal-id=md-sms-send]').remodal().close();
				callback();
			}else{
				alert(r.msg);
			}
			
		}
	});
}
</script>

<!-- etc -->
<script>
function fn_openCheckList2(eduSeq,userId){
	var popUrl ='${utcp.ctxPath}/admin/smartCheck/popup/rollbook.do?eduSeq='+eduSeq;
	if(!UTIL.isEmpty(userId)){
		popUrl += '&userId='+userId;
	}
   	var popOption = 'width=900px, height=1080px, resizable=no, location=no, top=150px, left=350px;';
   	window.open(popUrl,"출석부",popOption); 
}
</script>

<div class="remodal" data-remodal-id="md-email-send" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc" style="width: 480px;" id="vm-mdSendEmail" data-remodal-options="closeOnOutsideClick: false">
	<button data-remodal-action="close" class="remodal-close" aria-label="Close"></button>
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC pdt0"><i class="fas fa-chevron-circle-right fs_22 pdr5"></i>이메일발송</p>
		</div>
		<div class="modal-body">
			<table class="tb01 w100">
				<tbody>

					<tr>
						<th>발신주소<span class="required pd5">&nbsp;</span></th>
						<td class="tl"><input type="text" maxlength="23" v-model="fromAddr" class="ip6" readonly></td>

					</tr>
					<tr>
						<th>수신주소<span class="required pd5">&nbsp;</span></th>
						<td class="tl"><input type="text" maxlength="230" class="ip6" style="width:98%" v-model="toAddr" id="toAddr"></td>
					</tr>

					<tr>
						<th>메시지<span class="required pd5">&nbsp;</span></th>
						<td colspan="" class="tl"><textarea v-model="toMsg" class="ta_size"></textarea></td>
					</tr>

				</tbody>
			</table>

		</div>
		<div class="modal-footer">
			<div class="tc">
				<button type="button" :onclick="'fn_md_emailSendProc('+callback+')'" class="remodal-confirm btn01 btn_green">발송</button>
			</div>
		</div>
	</div>
</div>
<script>
var vm_mdSendEmail = new Vue({
	el : '#vm-mdSendEmail',
	data : {
		fromAddr : 'ceef@korea.kr',
		toAddr : '',
		toMsg : '',
		userId : '',
		callback : null,
	},
});
function fn_md_emailSend(fromAddr,toAddr,userId,callback,readonly){
	$('#toAddr').attr("readonly", readonly);
	console.log(fromAddr);
	if(fromAddr != ''){
// 		console.log(fromAddr);
		vm_mdSendEmail.fromAddr = fromAddr;
	}
	vm_mdSendEmail.toAddr = toAddr;
	vm_mdSendEmail.userId = userId;
	vm_mdSendEmail.callback = callback;
	$('[data-remodal-id=md-email-send]').remodal().open();
}
function fn_md_emailSendProc(callback){
	var fromAddr = vm_mdSendEmail.fromAddr;
	var toAddr = vm_mdSendEmail.toAddr;
	var toMsg = vm_mdSendEmail.toMsg;
	var userId = vm_mdSendEmail.userId;
	
	if(toAddr==''){
		alert('수신주소를 입력하세요.');
		return;
	}
	if(toMsg==''){
		alert('메시지를 입력하세요.');
		return;
	}
	
	if(!confirm('이메일을 발송하시겠습니까?')){
		return;
	}
	
	$.ajax({
		type : 'post',
		data : {toAddrList : toAddr, fromAddr : fromAddr, toMsg : toMsg, userId : userId},
		url : '${utcp.ctxPath}/admin/ajax/setSendEmail.ajax',
		success : function (r){
			if(r.result == 1){
				$('[data-remodal-id=md-email-send]').remodal().close();
				callback();
			}else{
				alert(r.msg);
			}
			
		}
	});
}
</script>

<div class="remodal" data-remodal-id="md-sms2-send" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc" style="width: 480px;" id="vm-mdSendSms2" data-remodal-options="closeOnOutsideClick: false">
	<button data-remodal-action="close" class="remodal-close" aria-label="Close"></button>
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC pdt0"><i class="fas fa-chevron-circle-right fs_22 pdr5"></i>문자발송</p>
		</div>
		<div class="modal-body">
			<table class="tb01 w100">
				<tbody>

					<tr>
						<th>발신번호<span class="required pd5">&nbsp;</span></th>
						<td class="tl"><input type="text" maxlength="13" v-model="fromNum" class="ip2"></td>

					</tr>
					<tr>
						<th>수신번호<span class="required pd5">&nbsp;</span></th>
						<td class="tl"><input type="text" maxlength="130" class="ip2 send-num" v-model="toNum" id="toNum2"></td>
					</tr>

					<tr>
						<th>문구 양식<span class="required pd5">&nbsp;</span></th>
						<td colspan="" class="tl"><textarea class="ta_size sns_ta_size" readonly>안녕하십니까, 재정경제부 『교육명』 교육비 납부 관련해서 아래와 같이 안내드립니다.

[교육기간]
0000-00-00 ~ 0000-00-00

[교육비]
식비: 00,000원 숙박비: 00,000원

[납부계좌]
식비: 국민 123-123-1234, 홍길동
숙박비: 국민 123-123-1234, 홍길동

[납부기한]
0000-00-00까지</textarea></td>
					</tr>

				</tbody>
			</table>

		</div>
		<div class="modal-footer">
			<div class="tc">
				<button type="button" :onclick="'fn_md_sms2SendProc('+callback+')'" class="remodal-confirm btn01 btn_green">발송</button>
			</div>
		</div>
	</div>
</div>
<script>



//수신번호 전화번호 - 넣기
$(document).on("keyup", ".send-num", function() { 
	$(this).val( $(this).val().replace(/[^0-9]/g, "").replace(/(^02|^050[0-9]|^1[0-9]{3}|^0[0-9]{2})([0-9]+)?([0-9]{4})$/,"$1-$2-$3").replace("--", "-") );
});

var vm_mdSendSms2 = new Vue({
	el : '#vm-mdSendSms2',
	data : {
		fromNum : '02-3677-1367',
		toNum : '',
// 		toMsg : '',
		userId : '',
		callback : null,
		
		eduNm : '',
		eduPeriodBegin : '',
		eduPeriodEnd : '',
		mealAccount : '',
		dormiAccount : '',
		depositLimitDt : '',
		mealFeeList : '',
		dormiFeeList : '',
	},
});
function fn_md_sms2Send(fromNum, toNum, userId, callback, readonly, extra){
 	$('#toNum2').attr("readonly", false);
	console.log(fromNum, toNum, userId);

	if(fromNum != ''){
// 		console.log(fromNum);
		vm_mdSendSms2.fromNum = fromNum;
	}
	vm_mdSendSms2.toNum = toNum;
	vm_mdSendSms2.userId = userId;
	vm_mdSendSms2.callback = callback;
	console.log(extra);
	vm_mdSendSms2.eduNm = extra.eduNm;
	vm_mdSendSms2.eduPeriodBegin = extra.eduPeriodBegin;
	vm_mdSendSms2.eduPeriodEnd = extra.eduPeriodEnd;
	vm_mdSendSms2.mealAccount = extra.mealAccount;
	vm_mdSendSms2.dormiAccount = extra.dormiAccount;
	vm_mdSendSms2.depositLimitDt = extra.depositLimitDt;
	vm_mdSendSms2.mealFeeList = extra.mealFeeList;
	vm_mdSendSms2.dormiFeeList = extra.dormiFeeList;
	$('[data-remodal-id=md-sms2-send]').remodal().open();
}
function fn_md_sms2SendProc(callback){
	var fromNum = vm_mdSendSms2.fromNum;
	var toNum = vm_mdSendSms2.toNum;
// 	var toMsg = vm_mdSendSms2.toMsg;
	var userId = vm_mdSendSms2.userId;
	
	var eduNm = vm_mdSendSms2.eduNm;
	var eduPeriodBegin = vm_mdSendSms2.eduPeriodBegin;
	var eduPeriodEnd = vm_mdSendSms2.eduPeriodEnd;
	var mealAccount = vm_mdSendSms2.mealAccount;
	var dormiAccount = vm_mdSendSms2.dormiAccount;
	var depositLimitDt = vm_mdSendSms2.depositLimitDt;
	var mealFeeList = vm_mdSendSms2.mealFeeList;
	var dormiFeeList = vm_mdSendSms2.dormiFeeList;
	
	if(toNum==''){
		alert('수신번호를 입력하세요.');
		return;
	}
// 	if(toMsg==''){
// 		alert('메시지를 입력하세요.');
// 		return;
// 	}
	
	if(!confirm('문자를 발송하시겠습니까?')){
		return;
	}
	console.log("data:::"+data);
	$.ajax({
		type : 'post',
		data : {fromNum : fromNum, toNumList : toNum,
// 			toMsg : toMsg,
			userId : userId,
			
			eduNm : eduNm, eduPeriodBegin : eduPeriodBegin, eduPeriodEnd : eduPeriodEnd,
			mealAccount : mealAccount, dormiAccount : dormiAccount, depositLimitDt : depositLimitDt,
			mealFeeList : mealFeeList, dormiFeeList : dormiFeeList},
		url : '${utcp.ctxPath}/admin/ajax/setSendSms2.ajax',
		success : function (r){
			if(r.result == 1){
				$('[data-remodal-id=md-sms2-send]').remodal().close();
				callback();
			}else{
				alert(r.msg);
			}
			
		}
	});
}
</script>
