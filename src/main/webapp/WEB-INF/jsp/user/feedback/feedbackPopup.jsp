<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>

<!DOCTYPE HTML>
<html lang="ko" xml:lang=“ko” xmlns=“http://www.w3.org/1999/xhtml”>
<head>

<meta charset="UTF-8">
<title>설문조사</title>
<meta http-equiv="Content-Type" content="text/html; utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no">
<meta http-equiv="imagetoolbar" content="no">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="keywords" content="<spring:eval expression="@prop['service.name']"/>, LMS, 교육, 온라인교육, 온라인강의">
<meta name="description" content="<spring:eval expression="@prop['service.name']"/>">
<meta name="author" content="<spring:eval expression="@prop['service.name']"/>">
<meta property="og:type" content="website">
<meta property="og:title" content="<spring:eval expression="@prop['service.name']"/>">
<meta property="og:description" content="<spring:eval expression="@prop['service.name']"/>">
<meta name="format-detection" content="telephone=no">



<!--// style.css //-->
<link rel="stylesheet" type="text/css" href="${utcp.ctxPath}/resources/user/css/sub.css">
<link rel="stylesheet" type="text/css" href="${utcp.ctxPath}/resources/user/css/slick.css" />
<link rel="stylesheet" type="text/css" href="${utcp.ctxPath}/resources/user/css/common.css">

<!--// favicon //-->
<link rel="apple-touch-icon" sizes="57x57" href="${utcp.ctxPath}/resources/favicon/apple-icon-57x57.png">
<link rel="apple-touch-icon" sizes="60x60" href="${utcp.ctxPath}/resources/favicon/apple-icon-60x60.png">
<link rel="apple-touch-icon" sizes="72x72" href="${utcp.ctxPath}/resources/favicon/apple-icon-72x72.png">
<link rel="apple-touch-icon" sizes="76x76" href="${utcp.ctxPath}/resources/favicon/apple-icon-76x76.png">
<link rel="apple-touch-icon" sizes="114x114" href="${utcp.ctxPath}/resources/favicon/apple-icon-114x114.png">
<link rel="apple-touch-icon" sizes="120x120" href="${utcp.ctxPath}/resources/favicon/apple-icon-120x120.png">
<link rel="apple-touch-icon" sizes="144x144" href="${utcp.ctxPath}/resources/favicon/apple-icon-144x144.png">
<link rel="apple-touch-icon" sizes="152x152" href="${utcp.ctxPath}/resources/favicon/apple-icon-152x152.png">
<link rel="apple-touch-icon" sizes="180x180" href="${utcp.ctxPath}/resources/favicon/apple-icon-180x180.png">
<link rel="icon" type="image/png" sizes="192x192" href="${utcp.ctxPath}/resources/favicon/android-icon-192x192.png">
<link rel="icon" type="image/png" sizes="32x32" href="${utcp.ctxPath}/resources/favicon/favicon-32x32.png">
<link rel="icon" type="image/png" sizes="96x96" href="${utcp.ctxPath}/resources/favicon/favicon-96x96.png">
<link rel="icon" type="image/png" sizes="16x16" href="${utcp.ctxPath}/resources/favicon/favicon-16x16.png">
<meta name="msapplication-TileColor" content="#ffffff">
<meta name="msapplication-TileImage" content="/resources/favicon/ms-icon-144x144.png">
<meta name="theme-color" content="#ffffff">

<!--// font //-->
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@300;400;500&display=swap" rel="stylesheet">

<!--// fontawesome //-->
<link href="${utcp.ctxPath}/resources/fontawesome/css/all.css" rel="stylesheet">

<!--// script.js //-->
<script src="${utcp.ctxPath}/resources/user/js/jquery-3.5.1.min.js"></script>
<script type="text/javascript" src="${utcp.ctxPath}/resources/user/js/slick.min.js"></script>
<script type="text/javascript" src="${utcp.ctxPath}/resources/user/js/common.js"></script>

<!--// modal //-->
<link rel="stylesheet" href="${utcp.ctxPath}/resources/admin/css/remodal.css">
<link rel="stylesheet" href="${utcp.ctxPath}/resources/admin/css/remodal-default-theme.css">
<script src="${utcp.ctxPath}/resources/admin/js/remodal.js" type="text/javascript"></script>
<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>


<!-- pickadate.js -->
<link rel="stylesheet" href="${utcp.ctxPath}/resources/plugins/pickadate/default.css">
<link rel="stylesheet" href="${utcp.ctxPath}/resources/plugins/pickadate/default.date.css">
<script src="${utcp.ctxPath}/resources/plugins/pickadate/picker.js"></script>
<script src="${utcp.ctxPath}/resources/plugins/pickadate/picker.date.js"></script>
<script src="${utcp.ctxPath}/resources/plugins/pickadate/legacy.js"></script>

<script type="text/javascript" src="${utcp.ctxPath}/resources/plugins/vue/vue.js"></script>

<script type="text/javascript">
	
</script>

</head>
<body>
	<input type="hidden" id="edu_seq" value="${param.eduSeq }" />
	<div id="wrap">

		<!--// header -->

		<div id="content">

			<div class="sub_txt">
				<span class=""><img src="${utcp.ctxPath}/resources/user/image/icon/icon_subtitle.png" alt="서브타이틀 아이콘" />{{fb.title}}</span>
			</div>

			<div class="listWrap cst" style="padding-top: 0">
				<div id="viewDiv">

					<!-- 
						<div class="fbTopTxt">
							<p>교육일자 : <span>2021-11-29</span></p>
							<p>강 사 : <span>김현정</span></p>
						</div> 
						-->

					<div class="scName pd20 tl fb" v-html="fb.content"></div>


					<div class="box box1">
						<span class="fs_14 tl dp_b"><b class="fc_blue">해당 문항에 만족하는 정도 및 내용에 클릭</b>해 주시기 바랍니다.</span>
						<div class="fb_table1" id="vm-feedbackReg">

							<comp-table3 :fb="fb" :list="qtList"></comp-table3>

						</div>
					</div>
					<p class="bot_tit">감사합니다.</p>
					<div class="w100 oh tc mgb10">
						<button type="button" class="btn01 btn_blue" onclick="fn_save_feedback_answer()">만족도조사 평가완료</button>
					</div>
				</div>
				<!--//viewDiv//-->
			</div>
			<!--//listWrap//-->
		</div>
		<!--// content -->
	</div>
	<!--// #container -->
	<!--// #wrap -->





	<!--// feedback //-->
	<div class="remodal messagePop messagePop1" data-remodal-id="md-feedback-alert" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc">
		<div class="modal-content">
			<div class="modal-header">
				<p class="tit alignC">알림</p>
			</div>
			<div class="modal-body">
				<p class="messageTxt">제출하시겠습니까? 다시 제출하실 수 없습니다.</p>
			</div>
			<div class="modal-footer">
				<div class="tc">
					<button onclick="fn_save_feedback_answer()" class="remodal-confirm btn02 btn_green">확인</button>
					<button onclick="fn_feedbackReg(1)" class="remodal-confirm btn02 btn_green">취소</button>
				</div>
			</div>
		</div>
	</div>
	<div class="remodal test2" data-remodal-id="md-feedbackReg" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc" id="vm-feedbackReg">
		<button data-remodal-action="close" class="remodal-close" aria-label="Close"></button>
		<div class="modal-content">
			<div class="modal-header">
				<p class="tit tl fdb">교육만족도 설문조사 <span class="cb dp_b font_14 pdt10">교육의 질 향상을 위한 설문입니다. 성실하게 답변해주시면 감사하겠습니다.</span>
				</p>
			</div>
			<div class="modal-body">
				<div class="fbScore">
					<comp-table3 :fb="fb" :list="qtList"></comp-table3>
				</div>
			</div>
			<div class="modal-footer">
				<div class="tc">
					<button onclick="location.href='#md-feedback-alert'" class="remodal-confirm btn01 btn_blue">제출</button>
				</div>
			</div>
		</div>
	</div>
	<script>
	function closeModal() {
		if ('${param.mobile}' == 'Y') {
	        var modal = $('[data-remodal-id=md-feedback-success]').remodal();
	        modal.close();

	        setTimeout(function() {
	            window.location.replace('/user/index.do'); // 모바일에서만 URL 이동
	        }, 300); // 모달 닫힘 애니메이션 시간에 맞춰 딜레이
	    } else {
	        // PC일 경우 기존 동작 유지
	        window.close();
	        opener.parent.location.reload();
	    }
	}
	</script>
	<div class="remodal messagePop messagePop1" data-remodal-id="md-feedback-success" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc">
		<div class="modal-content">
			<div class="modal-header">
				<p class="tit alignC">알림</p>
			</div>
			<div class="modal-body">
				<p class="messageTxt">설문조사에 참여해주셔서 감사합니다.</p>
			</div>
			<div class="modal-footer">
				<div class="tc">
					<button onclick="closeModal();" class="remodal-confirm btn02 btn_green">확인</button>
				</div>
			</div>
		</div>
	</div>
	<script type="text/x-template" id="tpl-table3">
	<table class="tb04 w100" >
		<caption class="sound_only">안전사고 예방교육 만족도조사 테이블</caption>
		<colgroup>
			<col span="1" style="width: 8%;" />
			<col span="1" style="width: 32%;" />
		</colgroup>
		<tbody>	
		<template v-for="(o,i) in list">
			<tr>
				
				<td v-if="o.qt.rowCnt>0" :rowspan="o.qt.rowCnt">{{o.qt.qtDiv}}</td> 
				<td>{{o.qt.question}}</td> 
				<td colspan="" :class="'tc '+(o.qt.qtType==0?'tatd':'rdg')">
					<template v-if="o.qt.qtType==1 || o.qt.qtType==3">
                	<ul class="chk_list ty02">			
						<template v-for="(o2,i2) in o.chList">
						<li>
                      		<input type="radio" :id="'choice-'+i+'-'+i2" :name="'choice_'+i" :value="o2.chIdx" v-model="o.qt.asChIdx"/>
                          	<label :for="'choice-'+i+'-'+i2">{{o2.choice}}</label>
                    	</li>
						</template>							
					</ul>
					</template>
					<template v-else-if="o.qt.qtType==2">							
                    <ul class="chk_list ty02">			
						<template v-for="(o2,i2) in o.chList">
						<li>
                      		<input type="checkbox" :id="'choice-'+i+'-'+i2" :name="'choice_'+i" :value="o2.chIdx" v-model="o.qt.asChIdx2"/>
                          	<label :for="'choice-'+i+'-'+i2">{{o2.choice}}</label>
                    	</li>
						</template>							
					</ul>
					</template>	
					<template v-else>
						<textarea class="w100 fbTarea" name="" rows="2" v-model="o.qt.answer"></textarea>
					</template>						
				</td>
			</tr>
		</template>
		</tbody>
	</table>
	</script>
	<script>
		Vue.component('comp-table3', {
			template : '#tpl-table3',
			props : [ 'fb', 'list' ],
		});
		var vm_feedbackReg = new Vue({
			el : '#wrap',
			data : {
				fb : {},
				qtList : [],
			},
		});
		function fn_feedbackReg() {
			$.ajax({
				url : '${utcp.ctxPath}/user/feedback/feedbackInfo.json',
				data : {
					eduSeq : $('#edu_seq').val(),
					feSeq: '${param.feSeq}',
				},
				success : function(r) {
					if (!r.RESULT) {
						vm_alert.MESSAGE = r.MESSAGE;
						location.href = '#md-alert';
						return;
					}
					vm_feedbackReg.fb = r.feedbackInfo.fb;
					vm_feedbackReg.qtList = r.feedbackInfo.qtList;
					
					//rowspan 설정
					var tmp = vm_feedbackReg.qtList;
					for(var i=0;i<tmp.length;i++){
						tmp[i].qt.rowCnt=1;
						
						if(tmp[i].qt.qtDiv == ''){
							continue;
						}
						var rowCnt=1;
						for(var i2=(i+1);i2<tmp.length;i2++){
							//console.log(tmp[i].qt.qtDiv==tmp[i2].qt.qtdiv)
							if(tmp[i].qt.qtDiv==tmp[i2].qt.qtDiv){
								rowCnt++;
								tmp[i].qt.rowCnt++;
							}else{
								i=i2-1;
								break;
							}
							
							if(i2+1 == tmp.length){
								i=tmp.length;
								break;
							}
						}
					}
				},
			})
		}
		function fn_save_feedback_answer() {
			
			var formData = {};
			formData.eduSeq = $('#edu_seq').val();
			formData.feSeq = '${param.feSeq}'
			formData.fb = vm_feedbackReg.fb;
			formData.qtList = vm_feedbackReg.qtList;
			
			for(var i in formData.qtList){
				if(formData.qtList[i].qt.essntlYn != 'Y'){
					continue;
				}
				if(formData.qtList[i].qt.qtType == 1 || formData.qtList[i].qt.qtType == 3){
					if(formData.qtList[i].qt.asChIdx==0){
						alert('['+formData.qtList[i].qt.question+'] 항목을 모두 입력해 주십시오');
						return;
					}
				}else if(formData.qtList[i].qt.qtType == 0){
					if(!formData.qtList[i].qt.answer){
						alert('['+formData.qtList[i].qt.question+'] 항목을 모두 입력해 주십시오');
						return;
					}
				}
			}
			$.ajax({
				type : 'post',
				contentType : 'application/json',
				url : '${utcp.ctxPath}/user/feedback/saveFeedbackAnswer.json',
				data : JSON.stringify(formData),
				success : function(r) {
					console.log(r);
					location.href = '#md-feedback-success';
				},
			});
		}
		fn_feedbackReg();
	</script>

</body>
</html>