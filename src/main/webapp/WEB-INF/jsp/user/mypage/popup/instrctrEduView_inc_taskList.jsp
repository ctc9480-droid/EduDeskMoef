<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<!--// tab_con3 //-->
<div class="cont" id="vue-instrctrEduDetail">
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

	<table width="100%" class="tb02 tc" >

		<thead bgcolor="#f7f8fa">
			<tr>
				<th>NO</th>
				<th>성명</th>
				<th>휴대폰번호 </th>
				<th>소속</th>
				<th>과제</th>
			</tr>
		</thead>

		<tbody>
			<tr v-for="(vo,i) in tab1.dataList" class="score_data">
				<td>{{ (tab1.totalCnt - (tab1.vo.page - 1) * tab1.vo.row - i) }} <input type="hidden" name="userId" v-model="vo.userId" />
				</td>
				<td><a href="#none">{{vo.userNm}}</a></td>

				<td>{{vo.mobile}}</td>
				<td class="tl">{{vo.belong}}</td>
				<td class="tl"><a href="#" onclick="javascript:fn_taskFileDownload();"> <img src="${utcp.ctxPath}/resources/user/image/icon/down_icon.gif" alt="파일 아이콘"> <b>{{vo.taskFileRename}}</b></a></td>
			</tr>
		</tbody>
	</table>
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
				vm.tab1Paging = createPaging(r.pageNavi);
				vm.lectTimeCnt = r.lectTimeCnt;
				vm.passCnt = r.passCnt;
				vm.totalCnt = r.totalCnt;
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




<script type="text/javascript">
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
//학생 리스트 - 과제 다운로드 /bbs/comm/download/.do
function fn_taskFileDownload(eduSeq) {
  // HTTP GET 요청을 보내면서 파일 다운로드를 수행합니다.
  location.href = '${utcp.ctxPath}/user/mypage/popup/taskDownload.do?eduSeq='+eduSeq;
}
</script>