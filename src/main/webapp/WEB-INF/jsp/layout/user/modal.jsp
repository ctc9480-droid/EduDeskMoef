<%@page import="com.educare.component.VarComponent"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.educare.edu.member.service.SessionUserInfoHelper"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<input type="hidden" id="video-edu-seq"/>
<input type="hidden" id="video-time-seq"/>
<input type="hidden" id="video-all-time"/>
<input type="hidden" id="video-history-time"/>

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
							</tr>
							<tr>
								<th scope="row">연락처</th>
								<td>
								{{lcrcp.mobile}}
								<!-- 
								<label for="mobile1" class="sound_only">휴대폰번호 를 선택 하실 수 있습니다.</label> 
								<select v-model="lcrcp.mobile1" id="mobile1" title="휴대폰번호 선택" class="mobile1" readonly>
										<option value="010" selected="selected">010</option>
										<option value="011">011</option>
										<option value="016">016</option>
										<option value="017">017</option>
										<option value="018">018</option>
										<option value="019">019</option>
								</select> 
								<input type="text" v-model="lcrcp.mobile2" maxlength="4" class="ip1" value=""> 
								<input type="text" v-model="lcrcp.mobile3" maxlength="4" class="ip1" value=""> 
								 -->
							</td>
							</tr>
							<tr>
								<th scope="row">이메일</th>
								<td>
								{{lcrcp.email}}
								<!-- 
								<input type="text" v-model="lcrcp.email1" class="ip1"> @ 
								<input type="text" class="ip1" v-model="lcrcp.email2" > 
								<select name="" id="email3" class="userEmail3" v-model="lcrcp.email3" @change="choiceEmail()">
									<option value="">직접입력</option>
									<option value="naver.com">naver.com</option>
									<option value="daum.net">daum.net</option>
									<option value="nate.com">nate.com</option>
									<option value="hotmail.com">hotmail.com</option>
									<option value="yahoo.com">yahoo.com</option>
									<option value="empas.com">empas.com</option>
									<option value="korea.com">korea.com</option>
									<option value="dreamwiz.com">dreamwiz.com</option>
									<option value="gmail.com">gmail.com</option>
									<option value="korea.kr">korea.kr</option>
								</select>
								 -->
								</td>
							</tr>
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
							<tr style="display:none;">
								<th>기타 남길말</th>
								<td colspan="3"><textarea class="ta_size" id="etc" name="etc" v-model="lcrcp.etc"></textarea></td>
							</tr>
							
							<tr v-for="(o,i) in etcIemData.etcIemList">
								<th>{{o.etcIemNm}}</th>
								<td>
								<template v-if="o.dataInputTy == 'radio' ">
									<template v-for="(o2,i2) in o.etcIemEx.split(',')" >
										<input v-model="o.etcIemData" type="radio" :id="'etcIemSeq_'+o.etcIemSeq+'_'+i2"  :value="o2" style="width:12px;" disabled/>
										<label :for="'etcIemSeq_'+o.etcIemSeq+'_'+i2" >{{o2 }}</label>
									</template>
								</template>
								<template v-else-if="o.dataInputTy == 'checkbox' ">
									<template v-for="(o2,i2) in o.etcIemEx.split(',')" >
										<input v-model="o.etcIemData2" type="checkbox" :name="'etcIemSeq_'+o.etcIemSeq" :id="'etcIemSeq_'+o.etcIemSeq+'_'+i2"  :value="o2" readonly/>
										<label :for="'etcIemSeq_'+o.etcIemSeq+'_'+i2" >{{o2 }}</label>
									</template>
								</template>
								<template v-else-if="o.dataInputTy == 'select' ">
									<select v-model="o.etcIemData" readonly>
										<template v-for="(o2,i2) in o.etcIemEx.split(',')" >
										<option :value="o2">{{o2}}</option>
										</template>
									</select>
								</template>
								<template v-else>
									<input type="text" v-model="o.etcIemData" id="'etcIemSeq_'+o.etcIemSeq+'_0'"/>
								</template>
								</td>
							</tr>
							
							<tr style="display:none;">
								<th>첨부파일</th>
								<td colspan="3">
								<a v-if="lcrcp.fileOrg" :href="'${utcp.ctxPath}/admin/edu/attach/'+lcrcp.eduSeq+'/'+lcrcp.userId+'/rceptDownload.do'">[다운로드]</a>
								</td>
							</tr>
		
						</tbody>
					</table>
					<template v-if="lctre.targetTp == 1">
					<table class="tb03 w100">
				<caption class="sound_only">재직자 회원가입 신청폼</caption>
				<colgroup>
					<col width="30%">
					<col width="70%">
				</colgroup>
				<tbody>
					<tr>
						<th><label for="facId">구분</label> <span class="red">*</span></th>
						<td>
							{{comp.bizTpNm}}
							</td>
					</tr>
					<tr>
						<th><label for="facId">기관명</label> <span class="red">*</span></th>
						<td><input type="text" id="facId" name="facId" v-model="comp.compNm" class="ip1" required="required" readonly> 
								
								<label for="faccompgb1" >
								<input type="radio" v-model="faccompgb" name="faccompgb" id="faccompgb1" value="1" class="mgr5" disabled>
								기관소속
								</label>
								<label for="faccompgb2" >
								<input type="radio" v-model="faccompgb" name="faccompgb" id="faccompgb2" value="2" class="mgr5" disabled>
								무소속
								</label>
						<label for="faccompgb3" >
								<input type="radio" v-model="faccompgb" name="faccompgb" id="faccompgb3" value="3" class="mgr5" disabled>
								한국산업기술시험원소속
							</label>  
							<input v-if="faccompgb == 3" type="text" id="facnm" name="facnm" v-model="comp.employNum" class="ip9" placeholder="사번입력" readonly disabled>
							</td>
					</tr>
					<tr>
						<th><label for="facTeam">근무부서</label></th>
						<td><input type="text" v-model="comp.deptNm" class="ip1" readonly></td>
					</tr>
					<tr>
						<th><label for="facLv">직위</label></th>
						<td><input type="text" v-model="comp.posiNm" class="ip1" readonly></td>
					</tr>
					<tr>
						<th><label for="facName">대표자명</label> <span class="red">*</span></th>
						<td><input type="text" id="facName" v-model="comp.ceoNm" class="ip1" readonly></td>
					</tr>
					<tr>
						<th><label for="mobile1">주소</label> <span class="red">*</span></th>
						<td class="add"><input type="text" v-model="comp.postNo" class="ip1" readonly> <br> 
						<input type="text" v-model="comp.addr" class="ip2" readonly>  <input type="text" v-model="comp.addrDtl" class="ip2" readonly></td>
					</tr>
					<tr>
						<th><label for="facTel">대표전화</label> <span class="red">*</span></th>
						<td><input type="text" id="facTel" name="facTel" v-model="comp.tel" class="ip1" readonly> <span class="dp_ib mgt5 sptxt">Ex) 031-000-0000</span></td>
					</tr>
					<tr>
						<th><label for="facNum">사업자등록번호</label> <span class="red">*</span></th>
						<td><input type="text" id="facNum" name="facNum" v-model="comp.bsnLcnsNo" readonly class="ip1" maxlength="12"> <span class="dp_ib mgt5 sptxt">Ex) 123-45-67890</span></td>
					</tr>
					
					<tr  v-if="faccompgb == 1">
						<th><label for="facFile">사업자등록증</label> <span class="red">*</span></th>
						<td>
					 	<a v-if="comp.bsnLcnsOrg" :href="'/admin/cloud/download.do?cloudPath=upload/member/&cloudFile='+comp.bsnLcnsRnm+'&downNm='+comp.bsnLcnsOrg" >{{comp.bsnLcnsOrg}}</a>
						</td>
					</tr>
					<tr  v-if="faccompgb == 1">
						<th><label for="facFile">회사통장사본</label> </th>
						<td>
					 	<a v-if="comp.bsnBankOrg" :href="'/admin/cloud/download.do?cloudPath=upload/member/&cloudFile='+comp.bsnBankRnm+'&downNm='+comp.bsnBankOrg" >{{comp.bsnBankOrg}}</a>
						</td>
					</tr>
				</tbody>
			</table>
					</template>
					<template v-if="lctre.targetTp == 2"> 
					학력정보
					<table class="tb04 w100">
						<thead>
							<tr>
								<th>기간</th>
								<th>학교명</th>
								<th>학과</th>
								<th>복수 or 부전공</th>
								<th>소재지</th>
								<th>졸업구분(졸업 or 수료)</th>
								<th>성적(평점/만점)</th>
							</tr>
						</thead>
						<tr v-for="o in schoolList">
							<td>{{o.beginDtStr}} ~ {{o.endDtStr}}</td>
							<td>{{o.schoolNm}}</td>
							<td>{{o.majorNm}}</td>
							<td>{{o.submjrNm}}</td>
							<td>{{o.location}}</td>
							<td>{{o.grdtTp==2?'졸업':'수료'}}</td>
							<td>{{o.mark}} / {{o.fullMark}}</td>
						</tr>
						<tr v-if="schoolList.length == 0">
							<td colspan="7">학력정보가 없습니다.</td>
						</tr>
					</table>
					자격정보
					<table class="tb04 w100">
						<thead>
							<tr>
								<th>자격명</th>
						<th>취득일</th>
						<th>발급처</th>
							</tr>
						</thead>
						<tr v-for="o in certifList">
							<td>{{o.crtfNm}}</td>
							<td>{{o.crtfDtStr}}</td>
							<td>{{o.issueNm}}</td>
						</tr>
						<tr v-if="certifList.length == 0">
							<td colspan="3">자격정보가 없습니다.</td>
						</tr>
					</table>
					어학정보
					<table class="tb04 w100">
						<thead>
							<tr>
								<th>외국어명</th>
						<th>공인테스트명</th>
						<th>성적</th>
						<th>시험일자</th>
							</tr>
						</thead>
						<tr v-for="o in languaList">
							<td>{{o.langNm}}</td>
							<td>{{o.testNm}}</td>
							<td>{{o.mark}}</td>
							<td>{{o.testDtStr}}</td>
						</tr>
						<tr v-if="languaList.length == 0">
							<td colspan="4">어학정보가 없습니다.</td>
						</tr>
					</table>
					경력정보
					<table class="tb04 w100">
						<thead>
							<tr>
								<th>기간</th>
						<th>회사명</th>
						<th>담당업무</th>
							</tr>
						</thead>
						<tr v-for="o in careerList">
							<td>{{o.beginDtStr}} ~ {{o.endDtStr}}</td>
							<td>{{o.compNm}}</td>
							<td>{{o.taskNm}}</td>
						</tr>
						<tr v-if="careerList.length == 0">
							<td colspan="3">어학정보가 없습니다.</td>
						</tr>
					</table>
					기타정보
					<table class="tb04 w100">
						<tr>
							<th>고용보험 가입여부</th>
							<td>{{lcrcp.empInsrTp == 1?'예':'아니오'}}</td>
							<th>내일배움카드 발급여부</th>
							<td>{{lcrcp.learnCardTp == 1?'예':'아니오'}}</td>
							<th>국민취업지원제도 가입여부</th>
							<td>{{lcrcp.empSuccTp == 1?'예':'아니오'}}</td>
							<th>사업자등록 여부</th>
							<td>{{lcrcp.bizRgsTp == 1?'예':'아니오'}}</td>
						</tr>
					</table>
					지원동기
					<table class="tb04 w100">
						<tr>
							<td>
							<span v-html="convNewlines(lcrcp.etc)"></span>
							</td>
						</tr>
					</table>
					</template>
				</form>
			</div>
		</div>
		<div class="modal-footer pdt20">
			<div class="tc">
				<!-- 
				<button v-if="!isPlay" type="button" class="remodal-confirm btn01 btn_green" onclick="fn_updateLcrcp();">저장</button>
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
		schoolList: [],
		certifList: [],
		languaList: [],
		careerList: [],
		comp: {},
		faccompgb: 1,//소속여부, 1:기관소속, 2:무소속, 3:ktl
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
function md_openLcrcpInfo(eduSeq,rceptSeq){
	$.ajax({
		url : '${utcp.ctxPath}/admin/ajax/callLcrcpInfo.ajax',
		data : {eduSeq : eduSeq,rceptSeq : rceptSeq},
		success : function (r){
			
			if(r.result != 1){
				alert(r.msg);
				return; 
			}
			//console.log('lcrcp',r.data);
			vm_lcrcpInfo.lcrcp = r.data.lcrcp;
			vm_lcrcpInfo.lctre = r.data.lctre;
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
			
			vm_lcrcpInfo.isPlay = r.data.isPlay;
			
			//추가정보 세팅
			vm_lcrcpInfo.comp = {};
			vm_lcrcpInfo.schoolList = [];
			vm_lcrcpInfo.certifList = [];
			vm_lcrcpInfo.languaList = [];
			vm_lcrcpInfo.careerList = [];
			if(!UTIL.isEmpty(r.data.lcrcp.schoolListJson)){
				console.log(r.data.lcrcp.schoolListJson);
				vm_lcrcpInfo.schoolList = JSON.parse(r.data.lcrcp.schoolListJson);
			}
			if(!UTIL.isEmpty(r.data.lcrcp.certifListJson)){
				vm_lcrcpInfo.certifList = JSON.parse(r.data.lcrcp.certifListJson);
			}
			if(!UTIL.isEmpty(r.data.lcrcp.languaListJson)){
				vm_lcrcpInfo.languaList = JSON.parse(r.data.lcrcp.languaListJson);
			}
			if(!UTIL.isEmpty(r.data.lcrcp.careerListJson)){
				vm_lcrcpInfo.careerList = JSON.parse(r.data.lcrcp.careerListJson);
			}
			if(!UTIL.isEmpty(r.data.lcrcp.compJson)){
				vm_lcrcpInfo.comp = JSON.parse(r.data.lcrcp.compJson);
				
				if(vm_lcrcpInfo.comp.employYn == 'Y'){
					vm_lcrcpInfo.faccompgb = 3;
				}else{
					if(vm_lcrcpInfo.comp.compNm == '개인' && vm_lcrcpInfo.comp.bsnLcnsNo == '000-00-00000'){
						vm_lcrcpInfo.faccompgb = 2;
					}
				}
			}
			
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
	
	formData.append('compJson',JSON.stringify(vm_lcrcpInfo.comp));
	formData.append('schoolListJson',JSON.stringify(vm_lcrcpInfo.schoolList));
	formData.append('certifListJson',JSON.stringify(vm_lcrcpInfo.certifList));
	formData.append('languaListJson',JSON.stringify(vm_lcrcpInfo.languaList));
	formData.append('careerListJson',JSON.stringify(vm_lcrcpInfo.careerList));
	formData.append('empInsrTp',JSON.stringify(vm_lcrcpInfo.lcrcp.empInsrTp));
	formData.append('learnCardTp',JSON.stringify(vm_lcrcpInfo.lcrcp.learnCardTp));
	formData.append('empSuccTp',JSON.stringify(vm_lcrcpInfo.lcrcp.empSuccTp));
	formData.append('bizRgsTp',JSON.stringify(vm_lcrcpInfo.lcrcp.bizRgsTp));
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



<!--// 시간표 보기 //-->
<div class="remodal" data-remodal-id="md-timetable" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc" id="vm-timetable">
	<button data-remodal-action="close" class="remodal-close" aria-label="Close"></button>
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC">
				<i class="fas fa-chevron-circle-right fs_22 pdr5"></i>교육 시간표
			</p>
		</div>
		<div class="modal-body">
			<div class="tbBox">
				<table class="tc w100">
					<thead bgcolor="#f7f8fa">
						<tr>
							<th>차시</th>
							<th>날짜</th>
							<th>시간</th>
							<th>내용</th>
							<th>강사</th>
							<th>형태</th>
							<!-- <th>입장/비밀번호</th> -->
						</tr>
					</thead>
					<tbody>
						<tr v-for="o in timeList">
							<td>{{o.timeSeq }}</td>
							<td>{{o.eduDt|fltDt2Str('YYYYMMDD','YYYY.MM.DD (ddd)') }}</td>
							<td>{{o.startTm|fltDt2Str('HHmm','HH:mm') }} ~ {{o.endTm|fltDt2Str('HHmm','HH:mm') }}</td>
							<td class="tl">{{o.description }}</td>
							<td>{{o.instrNm }}</td>
							<td>{{o.classHowNm }}</td>
							<%-- 
							<td>
								<%if(SessionUserInfoHelper.isLogined()){ %>
								<template v-if="o.classHow==1||o.classHow==2||o.classHow==3">
									<a href="#none" :onclick="'fn_checkOnline('+o.eduSeq+','+o.timeSeq+')'" class="btn04 btn_blue">{{o.historyCnt>0&&o.classHow==3?'재입장하기':'입장하기'}}</a>
									<span v-if="o.classHow==3 && o.historyCnt>0 && o.movTime>0 && o.movAllTime">
									(진도율 : {{(o.movTime/o.movAllTime*100).toFixed(1)}}%)
									</span>
								</template>
								<%} %>
							</td>
							 --%>
						</tr>
						<tr v-if="!timeList.length"><td colspan="7">시간표가 생성되지 않았습니다.</td></tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>
<!--// 단체수강등록 //-->
<script>
var vm_timetable = new Vue({
	el:'#vm-timetable',
	data:{
		timeList:[]
	},
	updated:function(){
		
	}
});



function fn_checkOnline(eduSeq,timeSeq){
	
	//임시처리 교육번호88은 모바일에서 막기
	if(eduSeq==88 && UTIL.isMobile()){
		alert('PC에서 시청해주세요');
		return;
	}
	
	if(timeSeq<1){
		return ;
	}
	$.ajax({
		type:'post',data:{eduSeq:eduSeq,timeSeq:timeSeq},url:'${utcp.ctxPath}/user/ajax/checkOnline.json',
		success:function(r){
			//$('#md-name').val('#md-timetable');
			$('#md-name').val('#none');
			if(r.result==1){
				if(r.classHow==1){
					window.open(r.url,'online','');
				}else if(r.classHow==2){
					window.open(r.url,'online','');
				}else if(r.classHow==3){
					
					
					//내가 지금 보고 있는 비디오 정보를 로컬스토리지에 저장,작업 미완료
					$('#video-edu-seq').val(eduSeq);//localStorage.setItem('eduSeq',eduSeq);
					$('#video-time-seq').val(timeSeq);//localStorage.setItem('timeSeq',timeSeq);
					$('#video-history-time').val(r.movTime);//localStorage.setItem('historyTime',r.movTime);
					
					var url=r.url
					video = document.getElementById('md-mov-video');
					
					video.src = '<spring:eval expression="@prop['cloud.cdn.mov.url']"/>'+r.url;
					function fncSkip(){
						video.currentTime=$('#video-history-time').val();//localStorage.getItem('historyTime');
						video.removeEventListener('canplay',fncSkip);
					}
				    video.addEventListener('canplay',fncSkip);
				    <c:if test="${utcp.isEduClose(lctre)}">
				    video.addEventListener('loadedmetadata',function(){
				    	//복습시에는 스킵가능하도록 전체시간 저장
						$('#video-history-time').val(video.duration);
				    });
				    </c:if>
				  	//location.href='#md-mov';
					$('[data-remodal-id=md-mov]').remodal().open();
					location.href='#none';	
					
					//퀴즈,챕터세팅
					vm_player.chapterData=[];
					vm_player.quizShowList=[];
					for(var i in r.movTimeList){
						if(r.movTimeList[i].type == 0){
							vm_player.chapterData.push(r.movTimeList[i]);
							//챕터번호 세팅
							var chapterTime = r.movTimeList[i].mvTime*1;
							var movTime = r.movTime*1;
							//console.log(chapterTime+','+movTime);
							if(chapterTime<movTime&&i!=0){
								vm_player.chapterSeq++;
							}
						}else{
							vm_player.quizShowList.push(r.movTimeList[i]);
						}
					}
					
					//퀴즈 오프,동영상 온
					$('[id^=quiz-area]').css('display','none');
					$('#mov-area').css('display','');
				}
				return;
			}
			//vm_alert.MESSAGE=r.message;
			//location.href='#md-alert';
			alert(r.message);
			
		}
	});
}

</script>

<!-- 동영상재생 팝업 -->
<div class="remodal onlineView" id="vm-player" data-remodal-id="md-mov" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc" data-remodal-options="closeOnOutsideClick: false">
	<button onclick="fn_md_mov_close()" class="remodal-close" aria-label="Close"></button>
	<div class="modal-content">
		<div class="modal-body po_re oh">
			<%--
			<div  class="onlineListWrap">
				<div class="toggleBtn">
					<img src="${utcp.ctxPath}/resources/user/image/arrowLeft.png" alt="동영상 리스트 열림닫힘 버튼" />
				</div>
				<div class="onlineList">
					<div class="pagination">
						<a :href="chapterData[chapterSeq-1]?('javascript:fn_loadMovTime('+chapterData[chapterSeq-1].mvTime+');'):'javascript:;'"><i class="fas fa-chevron-circle-left"></i></a> 
						<a href="javascript:;"><strong>{{chapterSeq+1}}</strong> / {{chapterData.length}}</a> 
						<a :href="chapterData[chapterSeq+1]?('javascript:fn_loadMovTime('+chapterData[chapterSeq+1].mvTime+');'):'javascript:;'"><i class="fas fa-chevron-circle-right"></i></a>
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
			--%>
			<!--// onlineListWrap //-->
			<div class="videoWrap2" id="alert-area" style="display: none; ">
			<div class="player-alert nextBtn" onclick="fn_closePlayerAlert();">다음 페이지 보기 <i class="fas fa-play"></i></div>
			</div>
			<div class="videoWrap" id="quiz-area" style="display: none; ">
			</div>
			<div class="videoWrap" id="mov-area">
				<video class="w100 videoCon" id="md-mov-video" controls="controls" style="width: 100%;" disablePictureInPicture  controlsList="nodownload noplaybackrate" oncontextmenu="return false;"></video>
			</div>
		</div>
		<div class="modal-footer">
			<%--
			<div v-if="chapterData.length" class="pagination">
				<a :href="chapterData[chapterSeq-1]?('javascript:fn_loadMovTime('+chapterData[chapterSeq-1].mvTime+');'):'javascript:;'"><i class="fas fa-chevron-circle-left"></i></a> 
				<a href="javascript:;"><strong>{{chapterSeq+1}}</strong> / {{chapterData.length}}</a> 
				<a :href="chapterData[chapterSeq+1]?('javascript:fn_loadMovTime('+chapterData[chapterSeq+1].mvTime+');'):'javascript:;'"><i class="fas fa-chevron-circle-right"></i></a>
			</div> 
			--%>
		</div>
	</div>
</div>
<script>
	//player vm
	var vm_player = new Vue({
		el : '#vm-player',
		data : {
			quizShowList : [],
			chapterData : [{title:'${lctre.eduNm}',movTime:'0'}],
			chapterSeq : 0,
			chapterTime0 : 0,
			chapterTime1 : 0,
			isAlert:true,
		},
		watch:{
			chapterData:function(v0,v1){
				console.log('in');
			}
			
		}
	});

	// playlist toggle //
	$(".toggleBtn").click(
					function() {
						if ($(".onlineListWrap").css("right") == "0px") {
							$(".toggleBtn").children("img").attr({
								"src" : "/resources/user/image/arrowLeft.png"
							})
							$(".onlineListWrap").stop().css("right", "-300px");
						} else {
							$(".toggleBtn").children(".onlineListWrap").stop().css(
									"right", "0px");
							$(".toggleBtn").children("img").attr({
								"src" : "/resources/user/image/arrowLeft.png"
							})
							$(".toggleBtn").children("img").attr({
								"src" : "/resources/user/image/arrowRight.png"
							})
							$(".onlineListWrap").stop().css("right", "0px");
						}
					});
	
	  $(".toggleBtn").click(function(){
		    $(".onlineListWrap").toggleClass("hh");
		});
	// playlist toggle //

	//퀴즈종료되면 발생하는 이벤트
	window.addEventListener('message', function(e) {
		//오류찾기위한 함수 추가
		temp_check();
		
		$('[id^=quiz-area]').css('display', 'none');
		$('#mov-area').css('display', '');
		video.play();
		vm_player.isAlert=true;
	});
	//

	function fn_md_mov_close() {
		video = document.getElementById('md-mov-video');
		$('#video-history-time').val(video.currentTime); //localStorage.setItem('historyTime', video.currentTime);//
		if (!video.paused) {
			//계속 동영상 스트리밍이 발생하여 넣은 로직 같은데 어차피 아래에서 새로고침 하기때문에 의미 없어서 주석 처리함
			//video.pause();
			//video.currentTime = 0;
		}

		fn_saveHistoryTime();
		$('[data-remodal-id=md-mov]').remodal().close();
		location.reload();
	}
	//로컬스토리지에 있는 정보를 디비에 저장한다. 5초간격으로 저장한다.
	function fn_saveHistoryTime() {
		//var historyTime = $('#video-history-time').val();//localStorage.getItem('historyTime');
		var historyTime = video.currentTime;
		var allTime = $('#video-all-time').val();//localStorage.getItem('allTime');
		var eduSeq = $('#video-edu-seq').val();//localStorage.getItem('eduSeq');
		var timeSeq = $('#video-time-seq').val();//localStorage.getItem('timeSeq');
		
		//isNan체크 
		if(isNaN(allTime) || isNaN(historyTime)){
			alert('동영상 재생 중 오류가 발생하였습니다. 동영상화면을 닫고 다시 재생해주시기 바랍니다.');
			video.pause();
			return;
		}
		
		$.ajax({
			//async:false,
			type : 'post',
			url : '${utcp.ctxPath}/user/ajax/saveHistoryTime.json',
			data : {
				eduSeq : eduSeq,
				timeSeq : timeSeq,
				historyTime : historyTime,
				allTime : allTime
			},
			success : function(r) {
				if (r.result == 2) {
					video = document.getElementById('md-mov-video');
					if (!video.paused) {
						video.pause();
						video.currentTime = 0;
					}
					vm_alert.MESSAGE = '동영상이 교체 되었습니다. 다시 시청하시기 바랍니다.'
					location.href = '#md-alert';

				} else if (r.result == 0) {
					video = document.getElementById('md-mov-video');
					if (!video.paused) {
						video.pause();
					}
					vm_alert.MESSAGE = '재생 중 오류가 발생하였습니다. 다시 시청하시기 바랍니다.'
					location.href = '#md-alert';
				}
			}
		});
	}

	function fn_loadMovTime(movTime) {
		video.pause();
		var historyTime = $('#video-history-time').val();//localStorage.getItem('historyTime');
		if (historyTime < movTime) {
			video.currentTime=historyTime;
			alert('해당 챕터를 완료하지 않았습니다.');
			return;
		}
		video.currentTime = movTime;
	}
	/* ,*/
	var isCheck = true;
	var isSaveHistoryTime = false;
	document.getElementById('md-mov-video').addEventListener(
			'timeupdate',
			function(e) {
				var preHistoryTime = $('#video-history-time').val()*1;//localStorage.getItem('historyTime')*1;
				var currentTime = this.currentTime*1;
				var diffTime =  Math.abs((preHistoryTime)
						- (currentTime ));
				//console.log(diffTime);
				if (diffTime > 1) {
					isCheck = false;
				} else {
					isCheck = true;
				}
				$('#video-all-time').val(this.duration);//localStorage.setItem('allTime', this.duration);//원래 아래 if문안에 있었는데 갱신이 안되서 항상 갱신 하도록 바꿈, 모니터링 필요,210624
				if (isCheck) {
					$('#video-history-time').val(this.currentTime);//localStorage.setItem('historyTime', this.currentTime);
					var dtime = new Date();
					var duration = Math.floor(dtime.getTime() / 1000 % 5);
					if (duration == 0 && !isSaveHistoryTime) {
						fn_saveHistoryTime();
						isSaveHistoryTime = true;
					}
					if (duration != 0) {
						isSaveHistoryTime = false;
					}
				}

				//퀴즈나오는 시간
				//console.log(preHistoryTime);
				for ( var i in vm_player.quizShowList) {
					var quizShow = vm_player.quizShowList[i];
					var showTime = parseFloat(quizShow.mvTime);
					//if(preHistoryTime<showTime || preHistoryTime>(showTime+5)){
					if( currentTime<showTime || currentTime>(showTime+5)){
						continue;
					}
					//if (!vm_player.quizShowList[i].isPass && preHistoryTime > showTime && preHistoryTime < (showTime + 1)) {
					if (!vm_player.quizShowList[i].isPass && currentTime > showTime && currentTime < (showTime + 1)) {
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
						vm_player.isAlert=false;
						break;
					}
				}
				//챕터번호체크
				for ( var i=0;i<vm_player.chapterData.length; i++) {
					var chapter = vm_player.chapterData[i];
					var chapterTime = parseFloat(chapter.mvTime);
					
					//챕터번호 구하기
					if(chapterTime<currentTime){
						vm_player.chapterSeq=i;
					}
					
					//챕터종료시 동영상 멈춤
					/**/
					if((!vm_player.isAlert) || (currentTime<chapterTime || currentTime>(chapterTime+5))){
						continue;
					}
					
					if ( (i>0 && i < vm_player.chapterData.length-1) && !vm_player.chapterData[i].isPass && currentTime > chapterTime && currentTime < (chapterTime + 1)) {
						vm_player.chapterData[i].isPass = true;
						vm_player.isAlert=false;
						
						video.pause();
						if (video.exitFullscreen) {
							video.exitFullscreen();
						} else if (video.mozCancelFullScreen) {
							video.mozCancelFullScreen();
						} else if (video.webkitExitFullScreen) {
							video.webkitExitFullScreen();
						}
						$('#alert-area').show();
						break;
					}
					
					//챕터이동 막기
					if (chapterTime > this.currentTime) {
						vm_player.chapterSeq--;
						break;
					}
					
					vm_player.chapterSeq = i;
				}
			});
	document.getElementById('md-mov-video').addEventListener('seeking',
			function(e) {
				var preHistoryTime = $('#video-history-time').val();//localStorage.getItem('historyTime');
				var diffTime = (preHistoryTime * 1) - (this.currentTime * 1);
				if (diffTime < 0) {
					this.currentTime = preHistoryTime;
				}
			});
	function fn_closePlayerAlert(){
		$('#alert-area').hide();
		video.play();
		vm_player.isAlert=true;
	}
	
	function temp_check(){
		var historyTime = video.currentTime;
		var allTime = $('#video-all-time').val();//localStorage.getItem('allTime');
		var eduSeq = $('#video-edu-seq').val();//localStorage.getItem('eduSeq');
		var timeSeq = $('#video-time-seq').val();//localStorage.getItem('timeSeq');
		$.ajax({
			type : 'post',
			url : '${utcp.ctxPath}/user/ajax/temp_check.json',
			data : {
				eduSeq : eduSeq,
				timeSeq : timeSeq,
				historyTime : historyTime,
				allTime : allTime
			},
			success:function(r){
								
			}
			
		});
	}
</script>

<!--// 출석현황,전자출결을 사용했을 경우만 //-->
<div class="remodal attModal" data-remodal-id="scView" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc" id="vm-checktable" data-remodal-options="closeOnOutsideClick: false">
			<button onclick="fn_md_attend_close()" class="remodal-close" aria-label="Close"></button>
			<div class="modal-content">
				<div class="modal-header">
					<p class="tit alignC"><i class="fas fa-chevron-circle-right fs_22 pdr5"></i>출결현황</p>
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
						<table class="tc w100">
							<thead bgcolor="#f7f8fa">
		<tr>
			<th rowspan="" class="fixedSide"></th>
			<th rowspan="" class="fixedSide"></th>
			<template v-for="o in date_list">
			<th :colspan="o.eduDtCnt" >{{o.eduDt|fltDt2Str('YYYYMMDD','MM.DD (ddd)')}}</th>
			</template>
			<th rowspan=""></th>
		</tr>
		<tr>
			<th rowspan="" class="fixedSide">이름</th>
			<th rowspan="" class="fixedSide">아이디</th>
			<th v-for="o in time_list" class="prd">
			<select v-if="o.classHow!=3 && o.checkId=='${sessionScope.sessionUserInfo.userId}'" :onchange="'saveAttendTime('+o.eduSeq+','+o.timeSeq+',this.value)'" style="width: 26px;text-indent:0;">
				<option value="">-</option>
				<option value="O">O</option>
				<option value="X">Χ</option>
			</select>
			{{o.timeSeq}}</th>
			<th rowspan="">합계</th>
		</tr>
	</thead>
	<tbody>
		<tr v-for="o in stdnt_list">
			<td class="fixedSide">{{o.userNm}}</td>
			<td class="fixedSide">{{o.loginId}}</td>
			<td v-for="o2 in o.stdntAttList" class="prd">
				<template v-if="o2.classHow!=3 && o2.checkId=='${sessionScope.sessionUserInfo.userId}'">
				
				<select v-model="o2.attCd" :onchange="'saveAttendInfo('+o.eduSeq+','+o2.timeSeq+','+o.userId+',this.value)'" style="width: 26px;text-indent:0;" >
				<option value="O">O</option>
				<option value="X">Χ</option>
				</select>
				</template>
				<template v-else >
				<span class="prd">
				{{o2.attNm3}}
				</span>
				</template>
			</td>
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
	el:'#vm-checktable',
	data:{eduSeq:0,userId:'',
		dateList:[],
		timeList:[],
		stdntList:[],
	},
});
function fn_openCheckList(eduSeq,userId){
	$.ajax({
		url:'${utcp.ctxPath}/user/ajax/rollBook.json',data:{eduSeq:eduSeq,userId:userId},
		success:function(r){
			vm_checktable.dateList=r.dateList;
			vm_checktable.timeList=r.timeList;
			vm_checktable.stdntList=r.stdntList;
			vm_checktable.eduSeq=eduSeq;
			vm_checktable.userId=userId;
			location.href='#scView';
		},
	});
}
function fn_checktable_excel(){
	console.log('userId : '+vm_checktable.userId);
	location.href='${utcp.ctxPath}/user/excel/rollBook.do?eduSeq='+vm_checktable.eduSeq;
}
function openTimeTable(eduSeq){
	$.ajax({
		async:false,
		data:{eduSeq:eduSeq},url:'${utcp.ctxPath}/comm/modal/timeTable.json',
		success:function(r){
			console.log(r);
			vm_timetable.timeList = r.timeList;
			location.href='#md-timetable';
		}
	});
}
function saveAttendInfo(eduSeq,timeSeq,userId,attCd){
	$.ajax({
		url:'${utcp.ctxPath}/user/ajax/saveAttendInfo.ajax',
		data:{eduSeq:eduSeq,timeSeq:timeSeq,userId:userId,attCd:attCd},
		success:function(r){
			if(r.result == 1){
				fn_openCheckList($('#edu_seq').val());
			}
			
		},
	});
}
function saveAttendTime(eduSeq,timeSeq,attCd){
	if(!confirm('전체학생의 출결이 변경됩니다. 진행하시겠습니까?')){
		return;
	}
	$.ajax({
		url:'${utcp.ctxPath}/user/ajax/saveAttendTime.ajax',
		data:{eduSeq:eduSeq,timeSeq:timeSeq,attCd:attCd},
		success:function(r){
			if(r.result == 1){
				fn_openCheckList($('#edu_seq').val());
			}
			
		},
	});
}
function fn_md_attend_close(){
	$('[data-remodal-id=scView]').remodal().close();
	location.reload();
}
</script>

<!-- 알림통합 -->
<div class="remodal messagePop messagePop1" id="vm-alert" data-remodal-id="md-alert" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc">
<input type="hidden" id="md-name" value="#"/>
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC">알림</p>
		</div>
		<div class="modal-body">
			<p class="messageTxt" v-html="MESSAGE"></p>
		</div>
		<div class="modal-footer">
			<div class="tc">
				<button onclick="location.href=$('#md-name').val();" class="remodal-confirm btn02 btn_green" data-remodal-action="confirm">확인</button>
			</div>
		</div>
	</div>
</div>
<script>
var vm_alert = new Vue({
	el:'#vm-alert',
	data:{MESSAGE:''},
});
</script>
<!-- 알림통합2 -->
<div class="remodal messagePop messagePop1" id="vm-alert2" data-remodal-id="md-alert2" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc">
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC">알림</p>
		</div>
		<div class="modal-body">
			<p class="messageTxt" v-html="MESSAGE"></p>
		</div>
		<div class="modal-footer">
			<div class="tc">
				<button onclick="$('[data-remodal-id=md-alert2]').remodal().close();location.reload()" class="remodal-confirm btn02 btn_green">확인</button>
			</div>
		</div>
	</div>
</div>
<script>
var vm_alert2 = new Vue({
	el:'#vm-alert2',
	data:{MESSAGE:''},
});
</script>

<script>
//모달창이 닫혀졌을때 이벤트
$(document).on('closed', '.remodal', function (e) {
	if(e.currentTarget.id=='vm-player'){
		if(video){
			video.pause();
		}
	}
});
</script>
