<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<div id="vm-myedu">
	<div v-if="view=='list'">
		<div class="listWrap pdt0">
			<div id="listDiv">
				<div class="box_list">
					<ul id="lctreView">
						<template v-if="list.dataList.length">
						<li class="flex" v-for="o in list.dataList">
							<span class="img_logo"> 
							<template v-if="o.imgUseYn == 'Y' && o.imgRename != ''">
								<img :src="'<spring:eval expression="@prop['cloud.cdn.url']"/>/upload/web/lctreThum/'+o.imgRename+'?'+o.updDe" alt="교육이미지" width="112px" height="149px"/>
							</template>
							<template v-else>
								<img :src="'${utcp.ctxPath}/resources/admin/images/default_img.png?'+o.updDe" alt="교육이미지" width="112px" height="149px"/>
							</template>
							</span>
							<div class="text_title">
								<div class="cp" :onclick="'fn_getMyEduInfo('+o.eduSeq+');'" style="cursor: pointer">
									<p class="eduTypeWrap">
									<span class="eduLabel">
			                        <template v-if="o.fee > 0 ">
			                        <b class="price pay">유료</b>
			                        </template>
			                        <template v-if="o.fee == 0 ">
			                        <b class="price free">무료</b>
			                        </template>
			                        </span>
			                        |
			                        <span class="eduType">{{o.detailCtgryNm}}</span> |
			                        <span class="eduType1">[{{o.lctreTypeNm }}]</span>
									</p>
									<p class="eduSbj">
									<strong v-html="o.eduNm"></strong>
									<span v-html="o.addPassBox "></span>
									<%-- <c:if test="${gubun ne 'Open' }">
										<span v-html="o.addPassBox "></span>
									</c:if> 
									<c:if test="${gubun eq 'Open' }">
										<span class="icon_tag comp" style="background-color: #eaeaea;color:gray;"><span v-html="o.addPassNm "></span></span>
									</c:if>  --%>
									</p>
									<p class="eduStnt">대상 : <span class="Stnt" >
									 <span v-html="o.addTargetsStr"></span>
								 <template v-if="o.targetEtc">({{o.targetEtc}})</template>
									</span></p>
								</div>
							</div>
							<div class="text_info">
								<div>
									<span class="dp_b fw_500"> [접수] <template v-if="o.rceptPeriodBegin!=''"> {{o.rceptPeriodBegin|fltDt2Str('YYYYMMDDhhmm','YYYY-MM-DD')}} ~ {{o.rceptPeriodEnd|fltDt2Str('YYYYMMDDhhmm','YYYY-MM-DD')}} </template> <template v-else> 미설정 </template>
									</span> <span class="dp_b fw_500"> [교육] {{o.eduPeriodBegin}} ~ {{o.eduPeriodEnd}} </span> <span class="dp_b">{{o.personnel}}명 모집 (<strong class="fc_red">{{o.rceptCnt}}</strong>명 신청)
									</span>
								</div>
							</div>
						</li>
						</template>
						<template v-else>
						<li class="flex">
							<div class="no_data">데이터가 없습니다.</div>
						</li>
						</template>
					</ul>
				</div>
				<div class="page" style="margin-bottom: 30px;">
					<div class="inner cf" id="pageDiv" v-if="list.paginationInfo">
						<template v-if="list.paginationInfo.currentPageNo != 1"> 
						<div class="page_prev">
						<a href="javascript:;" :onclick="'fn_page('+(list.paginationInfo.currentPageNo - 1)+'); return false;'">&lt;</a> 
						</div>
						</template>
						<template v-for="o in (list.paginationInfo.firstPageNoOnPageList , list.paginationInfo.lastPageNoOnPageList)"> 
						<template v-if="o == list.paginationInfo.currentPageNo"> 
						<div class="page_now">
						<a href="javascript:;" class="active">{{o}}</a> 
						</div>
						</template> <template v-else> 
						<div class="page_nomal">
						<a href="javascript:;" :onclick="'fn_page('+o+'); return false;'">{{o}}</a> 
						</div>
						</template> </template>
						<template v-if="list.paginationInfo.currentPageNo != list.paginationInfo.totalPageCount && list.paginationInfo.totalPageCount > 0"> 
						<div class="page_next">
						<a href="javascript:;" :onclick="'fn_page('+(list.paginationInfo.currentPageNo+1)+'); return false;'">&gt;</a> 
						</div>
						</template>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div v-else>
		<div class="listWrap pdt0">
			<div id="listDiv">
				<div class="box_list">
					<ul id="lctreView">
						<li class="flex">
							<span class="img_logo"> <img v-if="info.lctre.imgUseYn=='Y' && info.lctre.imgRename!=''" :src="'<spring:eval expression="@prop['cloud.cdn.url']"/>/upload/web/lctreThum/'+info.lctre.imgRename" alt="교육이미지" width="112px" height="149px" /> <img v-else src="${utcp.ctxPath}/resources/admin/images/default_img.png" alt="교육이미지" width="112px" height="149px" />
							</span>
							<div class="text_title">
								<div>
									<p style="margin-top: 0;" class="mgb5"><span class="mgr3">[{{info.lctre.lctreTypeNm}}]</span> {{info.lctre.eduCtgryNm }} <span v-html="info.lctre.addStatusBox"></span></p> <strong>{{info.lctre.eduNm }}</strong>
									<p>{{info.lctre.orgNm}}/{{info.lctre.instrctrNm}}강사</p>
								</div>
							</div>
							<div class="text_info">
								<div>
									<span> [교육] <b class="fw_500">{{info.lctre.eduPeriodBegin }} ~ {{info.lctre.eduPeriodEnd }}</b>
									</span>
									<br />
									<template v-if="info.lctre.checkRcept == 4 && info.lctre.fbIdx != 0 && info.stdnt.passYn == 'Y'"> <template v-if="info.stdnt.surveyYn=='Y'"> 이미 설문조사에 참여 하셨습니다. </template> <template v-else>
									<button class="btn01 btn_view" onclick="fn_feedbackReg()">설문조사 참여</button>
									</template> </template>
								</div>
							</div>
						</li>
					</ul>
				</div>

			</div>
		</div>
		<div class="board_tab_onoff">

			<!--// board_tab //-->
			<ul class="board_tab">
				<li :class="tab.on=='2'?'active':''">
					<p><a href="javascript:;" v-on:click="tab.on='2'">교육 상세</a></p>
				</li>
				<li :class="tab.on=='1'?'active':''">
					<p><a href="javascript:;" v-on:click="tab.on='1'">교육 시간표</a></p>
				</li>
				<li :class="tab.on=='3'?'active':''">
					<p><a href="javascript:;" v-on:click="tab.on='3'">수료증 발급</a></p>
				</li>
			</ul>
			<!--// board_tab //-->

			<!--// board_tab_con //-->
			<div class="board_tab_con">

				<!--// tab_con1 //-->
				<div class="cont" v-if="tab.on=='1'">
					<table class="tc tb01 w100 timetb">
						<caption class="sound_only">일반 진행중교육 테이블</caption>
						<thead>
							<tr>
								<th class="vm">차시</th>
								<th class="vm">날짜</th>
								<th class="vm">시간</th>
								<th class="vm">내용</th>
								<th class="vm">강사</th>
								<th class="vm">형태</th>
								<th class="vm">입장/비밀번호</th>
							</tr>
						</thead>
						<tbody>
							<tr v-for="(o,i) in info.timeList">
								<td>{{o.timeSeq}}</td>
								<td>{{o.eduDt2}}</td>
								<td>{{o.startHour}}:{{o.startMin}} ~ {{o.endHour}}:{{o.endMin}}</td>
								<td class="tl">{{o.description}}</td>
								<td>{{o.instrNm}}</td>
								<td>{{o.classHowNm}}</td>
								<td>
								<!-- <template v-if="(o.classHow==1||o.classHow==2||o.classHow==3) && info.lctre.checkRcept == 3">  -->
								<template v-if="o.classHow==3 && o.historyCnt>0 && o.movTime>0 && o.movAllTime>0"> 
								<span> (진도율 : {{o.addMovRatio}}% ) </span> 
								</template> 
								<!-- </template> -->
								</td>
							</tr>
						</tbody>
					</table>
				</div>
				<!--// tab_con1 //-->

				<!--// tab_con2 //-->
				<div class="cont" v-if="tab.on=='2'">
					<table class="tb06 tc w100 timetb edutb">
						<tbody>
							<tr>
								<th class="tdbg3">교육 기간</th>
								<td class="tl">{{info.lctre.eduPeriodBegin}} ~ {{info.lctre.eduPeriodEnd}}</td>
							</tr>
							<tr>
								<th class="tdbg3">교육 금액</th>
								<td class="tl">
										<template v-if="info.lctre.fee != 0 ">
											<template v-if="info.lctre.groupFee != 0 ">개인 </template>
											{{info.lctre.addFeeCurrency}}원 
										<template v-if="info.lctre.groupFee != 0 ">
											/ 단체 {{info.lctre.addGroupFeeCurrency}}원 
										</template>
										</template>
										<template v-else>
									무료
									</template>
									</td>
							</tr>
							<tr>
								<th class="tdbg3">상세 내용</th>
								<td class="tl">상세 내용</td>
							</tr>
							<tr>
								<th class="tdbg3">교육 장소</th>
								<td class="tl">
								{{info.lctre.addr}}
								{{info.lctre.detail}}
								<br/>{{info.lctre.memo}}
								</td>
							</tr>
						</tbody>
					</table>
				</div>
				<!--// tab_con2 //-->

				<!--// tab_con3 //-->
				<div class="cont" v-if="tab.on=='3'">
					<div class="certiListWrap">
                        <ul>
                            
                            
                            <li >
                            	<template v-if="info.lctre.passIdx == 0">
                            	<span>기본 수료증이 없는 과정입니다.</span>
                            	</template>
                                <template v-else-if="((info.lctre.fbIdx>0 && info.stdnt.surveyYn=='Y') || (info.lctre.fbIdx==0)) && (info.lctre.checkRcept==4 || info.lctre.lctreType==3) && info.stdnt.passYn=='Y' ">
                                <span>제목 : {{info.lctre.ctg3Nm}}</span>
                                <span>종류 : <strong class="fc_red">기본</strong></span>
                                <span>발급날짜 : {{info.stdnt.certDe|fltDate2Str('YYYY.MM.DD')}}</span>
                                <span>고유번호 : {{info.stdnt.passCertNum}}호</span>
                                <a :href="'javascript:fn_certView('+info.lctre.eduSeq+',0,1,${param.userId });'" class="btn01 btn_darkblue fr">출력하기</a>
                            	</template>
                            	<template v-else-if="(info.lctre.fbIdx>0 && info.stdnt.surveyYn != 'Y') && (info.lctre.checkRcept==4 || info.lctre.lctreType==3) && info.stdnt.passYn=='Y'">
										만족도조사 후 수료증을 발급받으실 수 있습니다.
								</template>
                            	<template v-else-if="info.lctre.closeYn != 'Y' && info.stdnt.passYn == 'Y'">
									<span>아직 교육이 종료되지 않아 과정수료증을 발급 받을 수 없습니다.</span>
								</template>
								<template v-else>
									<span>미수료</span>
								</template>
                            </li>
                            
                        </ul>
                    </div>
				
					<%-- 250122hy
					<div class="print" id="print" style="display: block;">
						<ul>
							<li>
								<img src="${utcp.ctxPath}/resources/user/image/icon/icon_print02.png" alt="수료증 출력 아이콘"><span>COMPLETION</span>
								<br>
								수료증을 출력합니다.
								<p class="p_on cp" :onclick="'fn_certView('+info.lctre.eduSeq+',\''+info.stdnt.userId+'\',\'\')'">출력하기</p>
							</li>
						</ul>
					</div>
					--%>
				</div>
				<!--// tab_con3 //-->
			</div>
			<!--// board_tab_con //-->
		</div>
	</div>
</div>

<script>
	var vm_myedu = new Vue({
		el : '#vm-myedu',
		data : {
			tab : {
				on : '2'
			},
			view : 'list',
			list : {
				dataList : []
			},
			info : {},
		},
	});
	function fn_getMyEduList(gubun) {
		$('#sv-gubun').val(gubun);
		var userId = '${param.userId}';
		$.ajax({
			url : '${utcp.ctxPath}/admin/ajax/myEduList.json',
			data : {
				gubun : gubun,
				userId : userId,
				page:$('#sv-page').val(),
			},
			success : function(r) {
				vm_myedu.list = r;
				vm_myedu.view = 'list';
			}
		});
	}
	function fn_getMyEduInfo(eduSeq) {
		var userId = '${param.userId}';
		$.ajax({
			url : '${utcp.ctxPath}/admin/ajax/myEduInfo.json',
			data : {
				eduSeq : eduSeq,
				userId : userId
			},
			success : function(r) {
				vm_myedu.info = r;
				vm_myedu.view = 'info';
				vm_myedu.tab.on = '2';
			}
		});
	}
	/* function fn_certView(eduSeq,userId, command){
		var popupWidth = 890;
		var popupHeight = 940;

		var popupX = (window.screen.width / 2) - (popupWidth / 2);
		var popupY= (window.screen.height / 2) - (popupHeight / 2);
		
		var url = "${utcp.ctxPath}/admin/edu/certView.do?eduSeq="+eduSeq+"&userId=" + userId + "&command=" + command;
		
		window.open(url, "증빙서류", 'status=no, height=' + popupHeight  + ', width=' + popupWidth  + ', left='+ popupX + ', top='+ popupY);
	} */
	
	function fn_certView(eduSeq,subSeq,mode,userId) {

		var popupWidth = 890;
		var popupHeight = 940;

		var popupX = (window.screen.width / 2) - (popupWidth / 2);
		var popupY = (window.screen.height / 2) - (popupHeight / 2);

		var url = "${utcp.ctxPath}/user/edu/certView.do?eduSeq=" + eduSeq + "&subSeq="+subSeq+"&mode="+mode+"&userId="+userId;

		window.open(url, "증빙서류", 'status=no, height=' + popupHeight
				+ ', width=' + popupWidth + ', left=' + popupX + ', top='
				+ popupY);
	}
	
	function fn_page(page){
		$("#sv-page").val(page);
		fn_getMyEduList($('#sv-gubun').val());
	}
</script>