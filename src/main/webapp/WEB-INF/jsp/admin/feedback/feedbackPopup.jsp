<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>

<!DOCTYPE HTML>
<html lang="ko" xml:lang=“ko” xmlns=“http://www.w3.org/1999/xhtml”>
<head>

<meta charset="UTF-8">
<title><spring:eval expression="@prop['service.name']"/></title>
<meta http-equiv="Content-Type" content="text/html; utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no">
<meta http-equiv="imagetoolbar" content="no">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="keywords" content="<spring:eval expression="@prop['service.name']"/>, 사이버교육센터, LMS, 교육, 온라인교육, 온라인강의">
<meta name="description" content="<spring:eval expression="@prop['service.name']"/>">
<meta name="author" content="<spring:eval expression="@prop['service.name']"/>">
<meta property="og:type" content="website">
<meta property="og:title" content="<spring:eval expression="@prop['service.name']"/>">
<meta property="og:description" content="<spring:eval expression="@prop['service.name']"/>">
<meta name="format-detection" content="telephone=no">



<!--// style.css //-->
<link rel="stylesheet" type="text/css" href="${utcp.ctxPath}/resources/user/css/main.css">
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

<script type="text/javascript" src="${utcp.ctxPath}/resources/plugins/vue/vue.js" ></script>

<script type="text/javascript">
	
</script>

</head>
<body>
<input type="hidden" id="idx" value="${param.fbIdx }"/>
	<div id="wrap">
		<!--// header -->
		<div id="container">
			<div id="content">
				<div class="sub_txt">
					<span class=""><img src="${utcp.ctxPath}/resources/user/image/icon/icon_subtitle.png" alt="서브타이틀 아이콘" />{{fb.title}}</span>
				</div>
				<div class="listWrap cst" style="padding-top: 0">
					<div id="viewDiv">
						<!-- 
						<div class="fbTopTxt">
							<p>교육일자 : <span>YYYY-MM-DD</span></p>
							<p>강 사 : <span>아무개</span></p>
						</div>
						 -->
						<div class="scName pd20 tl fb" v-html="fb.content">
						</div>
						<div class="box box1">
							<span class="fs_14 tl dp_b"><b class="fc_blue">해당 문항에 만족하는 정도 및 내용에 클릭</b>해 주시기 바랍니다.</span>
							<div class="fb_table1">
								<comp-table3 :fb="fb" :list="qtList"></comp-table3>
							</div>
						</div>
					</div>
					<!--//viewDiv//-->
				</div>
				<!--//listWrap//-->
			</div>
			<!--// content -->
		</div>
		<!--// #container -->
	</div>
	<!--// #wrap -->

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
			var eduSeq = '${param.eduSeq}';
			var userId = '${param.userId}';
			$.ajax({
				url : '${utcp.ctxPath}/admin/feedback/feedbackInfo.json',
				data : {
					idx : $('#idx').val(),
					eduSeq : eduSeq,
					userId : userId,
				},
				success : function(r) {
					vm_feedbackReg.fb = r.feedbackInfo.fb;
					vm_feedbackReg.qtList = r.feedbackInfo.qtList;
					
					//rowspan 설정
					var tmp = vm_feedbackReg.qtList;
					for(var i=0;i<tmp.length;i++){
						tmp[i].qt.rowCnt=1;
						
						//console.log(tmp[i].qt.qtDiv);
						if(tmp[i].qt.qtDiv == '' || tmp[i].qt.qtDiv == null){
							continue;
						}
						var rowCnt=1;
						for(var i2=(i+1);i2<tmp.length;i2++){
							//console.log(tmp[i].qt.qtDiv+','+tmp[i2].qt.qtDiv);
							//console.log(tmp[i].qt.qtDiv==tmp[i2].qt.qtdiv);
							console.log(tmp[i].qt.qtIdx+','+tmp[i2].qt.qtIdx);
							if(tmp[i].qt.qtDiv==tmp[i2].qt.qtDiv){
								rowCnt++;
								tmp[i].qt.rowCnt++;
							}else{
								if(tmp.length!=i+1){
									i=i2-1;
									break;
								}
							}
							
							if(i2+1 == tmp.length){
								i=tmp.length;
								break;
							}
						}
					}
					console.log(vm_feedbackReg.qtList);
				},
			})
		}
		
		fn_feedbackReg();
	</script>

</body>
</html>