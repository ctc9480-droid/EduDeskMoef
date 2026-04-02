<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<div id="wrap">
	<!--// header -->
	<div class="cstTbSubPop cstTbSub cstTbSub1">
		<img :src="'/resources/user/image/img/cstTbSub'+imgNm+'.jpg'" alt="분말소화기 이미지" />
		<p>
		<template v-for="(o,i) in descList"><strong>{{o.txt}}</strong> : {{o.txt2}}<br/></template>
		</p>
	</div>
</div>
<!--// #wrap -->
<script>
var data = [
	{imgNm:'01',descList:[{txt:'분말소화기',txt2:'분말소화약제를 압력에 따라 방사하는 기구로서 일반화재, 유류화재, 전기화재에 효과가 있는 소화기'}]},		
	{imgNm:'02',descList:[{txt:'K급 소화기',txt2:'주방화재 진화에 적합한 소화기로 동식물유(식용유 등)로 인해 발생한 주방 화재에 효과적으로 대응할 수 있는 소화기'}]},		
	{imgNm:'03',descList:[{txt:'투척용소화용구',txt2:'화점의 바닥이나 벽에 직접 던져 용기에 깨지면서 나오는 소화약제에 의해 불을 끄는 방식의 투척식 소화기'}]},		
	{imgNm:'04',descList:[{txt:'자동확산소화기',txt2:'화재를 감지하여 자동으로 소화약제를 방출 확산시켜 국소적으로 소화하는 소화기'}]},		
	{imgNm:'05',descList:[{txt:'주방자동소화장치',txt2:'주방에 설치된 열발생 조리기구의 사용으로 인한 화재 발생 시 열원(전기 또는 가스)을 자동으로 차단하며 소화약제를 방출하는 소화장치'}]},		
	{imgNm:'06',descList:[{txt:'옥내소화전설비',txt2:'소화전 내의 호스를 전개하여 화점에 물을 뿌려 불을 끄는 방식의 수동식 소화설비'}]},		
	{imgNm:'07',descList:[{txt:'스프링클러설비',txt2:'화재 발생 시 감열된 헤드의 개방으로 소화수를 뿌려 불을 끄는 자동식 소화설비'}]},		
	{imgNm:'08',descList:[{txt:'간이스프링클러설비',txt2:'스프링클러설비에 비해 설비를 간소화시킨 방식이며 스프링클러설비와 동일하게 화재 발생 시 감열된 헤드의 개방으로 소화수를 뿌려 불을 끄는 자동식 소화설비'}]},		
	{imgNm:'09',descList:[{txt:'단독경보형감지기',txt2:'화재발생 상황을 단독으로 감지하여 자체에 내장된 음향장치로 경보하는 감지기'}]},		
	{imgNm:'10',descList:[{txt:'비상경보설비',txt2:'수신기와 발신기로 구성되며 화재발생 상황을 경종이나 사이렌으로 경보하는 설비'}]},		
	{imgNm:'11',descList:[{txt:'자동화재탐지설비',txt2:'수신기, 발신기, 감지기로 구성되며 화재초기에 발생되는 열, 연기 또는 불꽃 등을 감지기에 의해 감지하여 자동적으로 경보를 발하는 설비'}]},		
	{imgNm:'12',descList:[{txt:'시각경보기',txt2:'자동화재탐지설비에서 발하는 화재신호를 시각경보기에 전달하여 청각장애인에게 점멸형태의 시각경보를 하는 장치'}]},		
	{imgNm:'13',descList:[{txt:'자동화재속보설비',txt2:'화재신호를 통신망을 통하여 음성 등의 방법으로 소방관서에 자동으로 통보하는 설비'}]},		
	{imgNm:'14',descList:[{txt:'가스누설경보기',txt2:'보일러 등 가스연소기에서 액화석유가스(LPG), 액화천연가스(LNG) 등의 가연성가스가 새는 것을 탐지하여 관계자나 이용자에게 경보하여 주는 장치'}]},		
	{imgNm:'15',descList:[{txt:'미끄럼대',txt2:'화재 또는 비상시에 피난층 이외의 층으로부터 피난층 또는 지면으로 피난자가 중력에 의하여 안전하게 미끄러져 내려오는 장치'}]},		
	{imgNm:'16',descList:[{txt:'구조대',txt2:'포지 등을 사용하여 자루형태로 만든 것으로서 화재 시 사용자가 그 내부에 들어가서 내려옴으로써 대피할 수 있는 기구'}]},		
	{imgNm:'17',descList:[]},		
	{imgNm:'18',descList:[{txt:'유도등',txt2:'화재 시에 피난을 유도하기 위한 등으로서 정상상태에서는 상용전원에 따라 켜지고 상용전원이 정전되는 경우에는 비상전원으로 자동전환되어 켜지는 등'}]},		
	{imgNm:'19',descList:[{txt:'유도표지',txt2:'피난구 또는 피난경로로 사용되는 출입구를 표시하여 피난을 유도하는 표지'}]},		
	{imgNm:'20',descList:[{txt:'비상조명등',txt2:'화재발생 등에 따른 정전 시에 안전하고 원활한 피난활동을 할 수 있도록 거실 및 피난통로 등에 설치되어 자동으로 점등되는 조명등'}]},		
	{imgNm:'21',descList:[{txt:'휴대용비상조명등',txt2:'화재발생 등으로 정전 시 안전하고 원할한 피난을 위하여 피난자가 휴대할 수 있는 조명등'}]},		
	{imgNm:'22',descList:[{txt:'피난안내도',txt2:'건물 내 사용자에게 피난에 필요한 요소를 설명하고 피난동선을 파악할 수 있도록 안내해주는 그림'}]},		
	{imgNm:'23',descList:[{txt:'방화문',txt2:'화재(화염과 연기)의 확산을 막기 위하여 설치한 문'},{txt:'방화셔터',txt2:'화재(화염과 연기)의 확산을 막기 위하여 설치한 셔터'}]},		
	{imgNm:'24',descList:[{txt:'옥외피난계단',txt2:'옥외에 설치된 피난계단으로 비상시 안전한 장소로 피난을 하기 위한 계단'}]},		
	{imgNm:'25',descList:[{txt:'방염',txt2:'화재 시 피난시간을 확보하기 위하여 연소하기 쉬운 재질에 발화 및 화염확산을 지연시키는 가공처리'}]},		
	{imgNm:'26',descList:[{txt:'LNG',txt2:'도시가스로 부르기도 하며, 공기보다 가벼운 가스로서 누출 시 천정부에 체류하므로 가스누설경보기의 탐지부는 천정으로부터 30㎝ 이내에 설치'}]},		
	{imgNm:'27',descList:[{txt:'LPG',txt2:'공기보다 무거운 가스로서 누출 시 바닥부분에 체류하므로 가스누설경보기의 탐지부는 바닥으로부터 30㎝ 이내에 설치'}]},		
];
var vm_img = new Vue({
	el:'#wrap',
	data:{imgNm:'',descList:[{txt:'',txt2:''}]},
});

var no='${param.no}'*1;
no=no-1;
vm_img.imgNm=data[no].imgNm;
vm_img.descList=data[no].descList;
</script>