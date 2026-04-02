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
							<span class="img_logo"> <template v-if="o.imgUseYn == 'Y' && o.imgRename != ''"> <img :src="'<spring:eval expression="@prop['cloud.cdn.url']"/>/upload/web/lctreThum/'+o.imgRename" alt="교육이미지" width="112px" height="149px" /> </template> <template v-else> <img src="${utcp.ctxPath}/resources/admin/images/default_img.png" alt="교육이미지" width="112px" height="149px" /> </template>
							</span>
							<div class="text_title">
								<div :onclick="'fn_getMyEduInfo('+o.eduSeq+');'" style="cursor: pointer">
									<p style="margin-top: 0;" class="mgb5"><span class="mgr3">[{{o.lctreTypeNm}}]</span>{{o.eduCtgryNm }} <span v-html="o.addStatusBox "></span> <span v-html="o.addPassBox "></span></p> <strong>{{o.eduNm}}</strong>
									<p>{{o.orgNm}} / {{o.instrctrNm}} 강사</p>
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
				<div class="board_pager" style="margin-bottom: 30px;">
					<div class="pagination" id="pageDiv" v-if="list.paginationInfo">
						<template v-if="list.paginationInfo.currentPageNo != 1"> <a href="javascript:;" :onclick="'fn_page('+(list.paginationInfo.currentPageNo - 1)+'); return false;'">&laquo;</a> </template>
						<template v-for="o in (list.paginationInfo.firstPageNoOnPageList , list.paginationInfo.lastPageNoOnPageList)"> <template v-if="o == list.paginationInfo.currentPageNo"> <a href="javascript:;" class="active">{{o}}</a> </template> <template v-else> <a href="javascript:;" :onclick="'fn_page('+o+'); return false;'">{{o}}</a> </template> </template>
						<template v-if="list.paginationInfo.currentPageNo != list.paginationInfo.totalPageCount && list.paginationInfo.totalPageCount > 0"> <a href="javascript:;" :onclick="'fn_page('+(list.paginationInfo.currentPageNo+1)+'); return false;'">&raquo;</a> </template>
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
				<li :class="tab.on=='1'?'active':''">
					<p><a href="javascript:;" v-on:click="tab.on='1'">교육 시간표</a></p>
				</li>
				<li :class="tab.on=='2'?'active':''">
					<p><a href="javascript:;" v-on:click="tab.on='2'">교육 상세</a></p>
				</li>
				<li :class="tab.on=='3'?'active':''">
					<p><a href="javascript:;" v-on:click="tab.on='3';fn_tab1_search()">수강생 현황</a></p>
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
								<td><template v-if="(o.classHow==1||o.classHow==2||o.classHow==3) && info.lctre.checkRcept == 3"> <template v-if="o.classHow==3 && o.historyCnt>0 && o.movTime>0 && o.movAllTime>0"> <span> (진도율 : {{o.addMovRatio}}% ) </span> </template> </template></td>
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
								<td class="tl"><template v-if="info.lctre.fee != 0 "> <template v-if="info.lctre.groupFee != 0 ">개인 </template> {{info.lctre.addFeeCurrency}}원  <template v-if="info.lctre.groupFee != 0 "> / 단체 {{info.lctre.addGroupFeeCurrency}}원 (부가세포함) </template> </template> <template v-else> 무료 </template></td>
							</tr>
							<tr>
								<th class="tdbg3">상세 내용</th>
								<td class="tl">상세 내용</td>
							</tr>
							<tr>
								<th class="tdbg3">교육 장소</th>
								<td class="tl">{{info.lctre.addr}} {{info.lctre.detail}} <br />{{info.lctre.memo}}
								</td>
							</tr>
						</tbody>
					</table>
				</div>
				<!--// tab_con2 //-->

				<!--// tab_con3 //-->
				<div class="cont" v-if="tab.on=='3'">
					<input type="hidden" id="tab1-page" value="" />
					<span class="tb_text"> 수강생 <strong class="fc_red">{{tab1.totalCnt}}</strong> 명
					</span>

					<div class="dp_ib fr tr mgb15">
						<label for="tab1-row" class="sound_only">리스트줄선택</label>
						<select name="sch_select" id="tab1-row" class="select01" style="text-align: left;">
							<option value="10">10줄</option>
							<option value="50">50줄</option>
							<option value="100">100줄</option>
							<option value="150">150줄</option>
						</select>
						<label for="tab1-srchColumn" class="sound_only">검색대상선택</label>
						<select name="sch_select" id="tab1-srchColumn" class="select01" style="text-align: left;">
							<option value="">선택</option>
							<option value="userNm">성명</option>
							<option value="belong">소속</option>
						</select>
						<label for="tab1-srchWrd" class="sound_only">검색어입력</label>
						<input type="text" name="sch_input" id="tab1-srchWrd" placeholder="검색" class="btn04 btn_blackl tl mgr5" />

						<button class="btn04 btn_black fr" onclick="fn_tab1_search(1)" type="button">검색</button>
					</div>

					<table width="100%" class="tb02 tc">

						<thead bgcolor="#f7f8fa">
							<tr>
								<th>NO</th>
								<th>성명</th>
								<th>휴대폰번호 </th>
								<th>소속</th>
								<th>출석</th>
								<th>점수1</th>
								<th>점수2</th>
								<th>합격여부</th>
							</tr>
						</thead>

						<tbody>
							<tr v-for="(vo,i) in tab1.dataList" class="score_data">
								<td>{{ (tab1.totalCnt - (tab1.vo.page - 1) * tab1.vo.row - i) }} <input type="hidden" name="userId" v-model="vo.userId" />
								</td>
								<td><a href="#none">{{vo.userNm}}</a></td>

								<td>{{vo.mobile}}</td>
								<td class="tl">{{vo.belong}}</td>
								<td>{{vo.attendCnt}} / {{lectTimeCnt}} ({{(vo.attendCnt/lectTimeCnt*100).toFixed(1)}}%)</td>
								<td>{{vo.workshopScore}}</td>
								<td>{{vo.examScore}}</td>
								<td class="fc_red">{{vo.passYn}}</td>
							</tr>
						</tbody>
					</table>

					<div class="fl tc">
						<!-- <button class="btn01 btn_greenl" onclick="location.href='#checkList'">출석부 업로드</button> -->
					</div>

					<div class="fr tc">
						<c:if test="${lctre.closeYn eq 'N'}">
							<button class="btn01 btn_green" onclick="fn_tab1_saveScore()">저장</button>
						</c:if>
					</div>

					<!--// paging //-->
					<div class="page" v-html="tab1Paging"></div>
					<!--// paging //-->
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
				on : '1'
			},
			view : 'list',
			list : {
				dataList : []
			},
			info : {},
			
			tab1 : {},
			tab1Paging : '',
		},
	});
	function fn_getMyEduList(gubun) {
		var userId = '${param.userId}';
		$.ajax({
			url : '${utcp.ctxPath}/admin/ajax/instrctrEduList.json',
			data : {
				gubun : gubun,
				userId : userId
			},
			success : function(r) {
				vm_myedu.list = r;
				vm_myedu.view = 'list';
			}
		});
	}
	function fn_getMyEduInfo(eduSeq) {
		//강좌팝업 뜨는걸로 교체
		window.open('${utcp.ctxPath}/admin/edu/popup/lctreView.do?eduSeq='+eduSeq,'lctrePop','scrollbar=n,width=1477,height=980');
		return;
		var userId = '${param.userId}';
		$.ajax({
			url : '${utcp.ctxPath}/admin/ajax/instrctrEduInfo.json',
			data : {
				eduSeq : eduSeq,
				userId : userId
			},
			success : function(r) {
				vm_myedu.info = r;
				vm_myedu.view = 'info';
			}
		});
	}
	function fn_certView(eduSeq, userId, command) {
		var popupWidth = 890;
		var popupHeight = 940;

		var popupX = (window.screen.width / 2) - (popupWidth / 2);
		var popupY = (window.screen.height / 2) - (popupHeight / 2);

		var url = "/admin/edu/certView.do?eduSeq=" + eduSeq + "&userId="
				+ userId + "&command=" + command;

		window.open(url, "증빙서류", 'status=no, height=' + popupHeight
				+ ', width=' + popupWidth + ', left=' + popupX + ', top='
				+ popupY);
	}
	
	//학생 리스트
	function fn_tab1_loadStdntList(page, row, srchWrd, srchColumn) {
		var param = {};
		param.page = page;
		param.row = row;
		param.srchWrd = srchWrd;
		param.srchColumn = srchColumn;
		param.eduSeq = vm_myedu.info.lctre.eduSeq;
		$.ajax({
			data : param,
			url : '${utcp.ctxPath}/user/edu/lectureStdntList.json',
			success : function(r) {
				vm_myedu.tab1 = r;
				vm_myedu.tab1Paging = createPaging(r.pageNavi);
				vm_myedu.lectTimeCnt = r.lectTimeCnt;
				vm_myedu.passCnt = r.passCnt;
				vm_myedu.totalCnt = r.totalCnt;
			}
		});
	}

	//탭1 검색 
	function fn_tab1_search(gubun) {
		if (gubun == 1) {//gubun==1이면 검색버튼 눌렀을 때, 검색조건 선택여부 체크 
			if ($('#tab1-srchColumn').val() == '') {
				vm_alert.msg= '검색 조건을 선택하세요';
				location.href='#md-alert';
				return;
			}
		}
		fn_tab1_loadStdntList($('#tab1-page').val(), $('#tab1-row').val(), $(
				'#tab1-srchWrd').val(), $('#tab1-srchColumn').val());
	}

	function fn_tab1_page(no) {
		$('#tab1-page').val(no);
		fn_tab1_search();
	}

	function createPaging(pageNavi) {
		var pageHtml = '';
		pageHtml += "<div class=\"inner cf\">";
		if (pageNavi.currentPageNo != 1) {
			pageHtml += "	<div class=\"page_prev0\"><a href=\"javascript:;\" onclick=\"fn_tab1_page('"
					+ (pageNavi.currentPageNo - 1) + "');\">&lt; 이전</a></div>";
		}
		var subHtml = "";
		for (var i = pageNavi.firstPageNoOnPageList; i <= pageNavi.lastPageNoOnPageList; i++) {
			if (i == pageNavi.currentPageNo) {
				subHtml = "<div class=\"page_now\"><a>" + i + "</a></div>";
			} else {
				subHtml = "<div class=\"page_nomal\"><a href=\"javascript:;\" onclick=\"fn_tab1_page('"
						+ i + "');\">" + i + "</a></div>";
			}
			pageHtml += subHtml;
		}
		if (pageNavi.currentPageNo != pageNavi.totalPageCount
				&& pageNavi.totalPageCount > 0) {
			pageHtml += "	<div class=\"page_next0\"><a href=\"javascript:;\" onclick=\"fn_tab1_page('"
					+ (pageNavi.currentPageNo + 1) + "');\">다음 &gt;</a></div>";
		}
		pageHtml += "</div>";
		return pageHtml;
	}
</script>

