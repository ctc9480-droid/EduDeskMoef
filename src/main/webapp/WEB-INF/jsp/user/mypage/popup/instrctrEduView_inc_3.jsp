<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>

				

						<!--// tab_con3 //-->
						<div class="cont" id="vue-instrctrEduDetail">
							<input type="hidden" id="tab1-page" value="1" />
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

							<table width="100%" class="tb02 tc" >

								<thead bgcolor="#f7f8fa">
									<tr>
										<th>NO</th>
										<th>성명</th>
										<th>휴대폰번호 </th>
										<c:if test="${lctre.passAttPoint > 0 }">
										</c:if>
										<th>출석</th>
										<th>과제</th>
										<c:if test="${lctre.passTestPoint > 0 }">
										</c:if>
										<th>시험</th>
										<th>수료여부</th>
									</tr>
								</thead>

								<tbody>
									<tr v-for="(vo,i) in tab1.dataList" class="score_data">
										<td>{{ (tab1.totalCnt - (tab1.vo.page - 1) * tab1.vo.row - i) }} <input type="hidden" name="userId" v-model="vo.userId" />
										</td>
										<td><a href="#none">{{vo.userNm}}</a></td>

										<td>{{vo.mobile|fltPhoneNumber}}</td>
										<c:if test="${lctre.passAttPoint > 0 }">
										</c:if>
										<td>
										<c:choose>
										<c:when test="${lctre.lctreType == 0 or lctre.lctreType == 2 }">
										{{vo.dateCnt - vo.absentCnt}} / {{vo.dateCnt}} ({{vo.attRatio}})
										</c:when>
										<c:otherwise>
										{{vo.attendCnt}} / {{lectTimeCnt}}
										</c:otherwise>
										</c:choose>
										<%-- 
										<input v-on:input="checkAttScore(i,${lctre.passAttPoint })" name="attendScore" :id="'tab1-attendScore-'+i" type="text" class="ip5 tc"  v-model="vo.attendScore" maxlength="3" numberonly style="width: 50px;" />
										 --%>
										</td>
										<td>
										<template v-if="vo.taskFileRename">
										<a class="dp_ib" :href="'${utcp.ctxPath}/user/mypage/popup/taskDownload.do?eduSeq=${lctre.eduSeq }&userId='+vo.userId">
										<img src="${utcp.ctxPath}/resources/user/image/icon/down_icon.gif" alt="파일 아이콘">
										</a>
										</template>
										<%-- 
										<input v-on:input="checkAsgScore(i,${lctre.passAsgPoint })" name="workshopScore" :id="'tab1-workshopScore-'+i" type="text" class="ip5 tc"  v-model="vo.workshopScore" maxlength="3" numberonly style="width: 50px;" />
										 --%>
										</td>
										<c:if test="${lctre.passTestPoint > 0 }">
										</c:if>
										<td>
										 본평가:{{vo.testPassCnt}}/{{testCnt}} 재평가:{{vo.test2PassCnt}}/{{test2Cnt}}
										<%-- 
										<input v-on:input="checkTestScore(i,${lctre.passTestPoint })" name="examScore" :id="'tab1-examScore-'+i" type="text" class="ip5 tc"  v-model="vo.examScore" maxlength="3" numberonly style="width: 50px;" />
										 --%>
										</td>
										<td class="fc_red"><c:choose>

												<c:when test="${lctre.lctreType ne 3 }">
													<select class="select01" name="passYn" v-model="vo.passYn" ${lctre.closeYn eq 'Y'?'disabled':'' } style="width: 50px; padding: 0 0 0 0;">
														<option value="Y">O</option>
														<option value="N">X</option>
													</select>
												</c:when>
												<c:otherwise>
								{{vo.passYn}}
								</c:otherwise>
											</c:choose></td>
									</tr>
								</tbody>
							</table>

							<div class="fl tc">
								<!-- <button class="btn01 btn_greenl" onclick="location.href='#checkList'">출석부 업로드</button> -->
								<c:choose>
								<c:when test="${lctre.lctreType == 0 or lctre.lctreType == 2 }">
								<a href="#none" onclick="fn_openCheckList2(${lctre.eduSeq})" class="btn01 btn_orange">출석부</a>
								</c:when>
								<c:otherwise>
								<a href="#none" onclick="fn_openCheckList(${lctre.eduSeq})" class="btn01 btn_orange">출석부</a>
								</c:otherwise>
								</c:choose>
							</div>

							<div class="fr tc">
								<c:if test="${	 authLv == 1 && lctre.closeYn ne 'Y'}">
									
									<button class="btn01 btn_green" onclick="fn_tab1_saveScore()">저장</button>
									<!-- 
									<button class="btn01 btn_orange" onclick="fn_tab1_eduClose()">교육종료</button>
									 -->
								</c:if>
								
							</div>

							<!--// paging //-->
							<div class="page" v-html="tab1Paging"></div>
							<!--// paging //-->

						</div>
						<!--// tab_con3 //-->



<script type="text/javascript">
	$(document).ready(function(){
		
	});

	var vue_data_1 = {
		tab1 : {},
		checkYn : '${lctre.checkYn}',
		tab1Paging : '',
	};
	var vm = new Vue({
		el : '#vue-instrctrEduDetail',
		data : vue_data_1,
		updated : function() {
			$('[numberonly]').off('keyup').on('keyup', function() {
				$(this).val($(this).val().replace(/[^0-9]/g, ''));
			});
		},
		methods:{
			checkAttScore:function(i,limit){
				if(this.tab1.dataList[i].attendScore>limit){
					this.tab1.dataList[i].attendScore='';
				}
			},
			checkAsgScore:function(i,limit){
				if(this.tab1.dataList[i].workshopScore>limit){
					this.tab1.dataList[i].workshopScore='';
				}
			},
			checkTestScore:function(i,limit){
				if(this.tab1.dataList[i].examScore>limit){
					this.tab1.dataList[i].examScore='';
				}
			},
		},
	});
	
	fn_tab1_search();
	
	//학생 리스트
	function fn_tab1_loadStdntList(page, row, srchWrd, srchColumn) {
		var param = {};
		param.page = page;
		param.row = row;
		param.srchWrd = srchWrd;
		param.srchColumn = srchColumn;
		param.eduSeq = $('#edu_seq').val();
		$.ajax({
			data : param,
			url : '${utcp.ctxPath}/user/edu/lectureStdntList.json',
			success : function(r) {
				vm.tab1 = r;
				//vm.tab1Paging = createPaging(r.pageNavi);
				vm.lectTimeCnt = r.lectTimeCnt;
				vm.passCnt = r.passCnt;
				vm.totalCnt = r.totalCnt;
				vm.testCnt=r.testCnt;
				vm.test2Cnt=r.test2Cnt;
			}
		});
	}

	//탭1 검색 
	function fn_tab1_search(gubun) {
		if (gubun == 1) {//gubun==1이면 검색버튼 눌렀을 때, 검색조건 선택여부 체크 
			if ($('#tab1-srchColumn').val() == '') {
				_vue.alert = '검색 조건을 선택하세요';
				_remodal.open();
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

	//탭1 선택 저장
	function fn_tab1_saveScore() {
		var formData = {};
		formData.eduSeq = $('#edu_seq').val();
		
		formData.dataList = vm.tab1.dataList;
		$.ajax({
			data : JSON.stringify(formData),
			url : '${utcp.ctxPath}/user/ajax/saveStdntScore.json',
			type : 'post',
			contentType : 'application/json',
			success : function(r) {
				if (r.result != '') {
					_vue.alert = r.result;
					_remodal.open();
				} else {
					_vue.alert = '정상적으로 저장되었습니다.';
					_remodal.open();
					fn_tab1_search();
				}
			}
		});
	}
	function fn_tab1_eduClose(){
		if(!confirm('정말로 교육을 종료하시겠습니까? \n교육종료시 성적수정이 불가능합니다.')){
			return;
		}
		$.ajax({
	        url: "${utcp.ctxPath}/user/ajax/eduClose.ajax",
	        type: "POST",
	        traditional : true,
	        data: {
	        	"eduSeq" : $('#edu_seq').val()
	        },
	        cache: false,
			async: true,
	        success: function(r) {
				if(r.result == 1){
					vm_alert2.MESSAGE = r.msg;
					location.href = "#md-alert2";
				}else{
					alert(r.msg);
				}
			}
	    });
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

	function fn_openCheckList2(eduSeq){
		var popUrl ='${utcp.ctxPath}/admin/smartCheck/popup/rollbook.do?eduSeq='+eduSeq;
	   	var popOption = 'width=900px, height=1080px, resizable=no, location=no, top=150px, left=350px;';
	   	window.open(popUrl,"출석부",popOption); 
	}
</script>

