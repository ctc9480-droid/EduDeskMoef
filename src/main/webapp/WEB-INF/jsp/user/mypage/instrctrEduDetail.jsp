<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<input type="hidden" id="eduSeq" value="${lctre.eduSeq }"/>
<div class="listWrap" style="padding-top: 0" id="vue-instrctrEduDetail">
	<div id="viewDiv">
		<div class="box_list" style="margin-bottom: 0px;">
			<ul>
				<li class="flex" style="border-bottom: 0px;"><span class="img_logo"> <c:choose>
							<c:when test="${lctre.imgUseYn eq 'Y' && not empty lctre.imgRename }">
								<img src="${utcp.ctxPath}/upload/edu/img/${lctre.imgRename }" alt="${lctre.eduNm }" width="112px" height="149px">
							</c:when>
							<c:otherwise>
								<img src="${utcp.ctxPath}/resources/admin/images/default_img.png" alt="${lctre.eduNm }" width="112px" height="149px">
							</c:otherwise>
						</c:choose>
				</span>
					<div class="text_title">
						<div>
							<c:set var="checkRcept" value="${utcp.checkRcept(lctre.eduSeq) }" />
							<p style="margin-top:0;" class="mgb15"><span class="mgr3">${lctre.onlineYn == 'Y'?'[온라인]':'' }</span>${lctre.eduCtgryNm }</p>
							<strong>${lctre.eduNm }</strong> 
							${lctre.addStatusBox }
							<p>${lctre.orgNm } / ${lctre.instrctrNm } 강사</p>
						</div>
					</div>
					<div class="text_info">
						<div>
							<span>
								[접수] <b class="fw_500">
								<c:if test="${lctre.rceptPeriodYn eq 'Y'}">
								${utcp.convDateToStr(utcp.convStrToDate(lctre.rceptPeriodBegin,'yyyyMMddHHmm'),'yyyy-MM-dd')} ~
							 	${utcp.convDateToStr(utcp.convStrToDate(lctre.rceptPeriodEnd,'yyyyMMddHHmm'),'yyyy-MM-dd')}
								</c:if>
								<c:if test="${lctre.rceptPeriodYn ne 'Y'}">
								미설정
								</c:if>
								</b>
							</span><br/>
    						<span>
								[교육] <b>${lctre.eduPeriodBegin}</b>
							</span><br/>
							<span class="fw_500">${lctre.personnel}명 모집 (<strong class="color fw_500">${lctre.rceptCnt}</strong>명 신청)</span>
						</div>
					</div></li>
			</ul>
		</div>

		<!--// board_tab_onoff //-->
		<div class="board_tab_onoff">

			<!--// board_tab //-->
			<ul class="board_tab">
				<li>
					<p>
						<a href="#none">내용 상세보기</a>
					</p>
				</li>
				<li>
					<p>
						<a href="#none">수강생 출석/성적/합격</a>
					</p>
				</li>
			</ul>
			<!--// board_tab //-->

			<!--// board_tab_con //-->
			<div class="board_tab_con">
				<!--// tab_con1 //-->
				<div class="cont">

					<table class="tb03" style="border-top: 3px solid #555;">
						<tbody>
							<tr>
								<th>접수 기간</th>
								<td>${utcp.convDateToStr(utcp.convStrToDate(lctre.rceptPeriodBegin,'yyyyMMddHHmm'),'yyyy-MM-dd HH:mm')} ~ ${utcp.convDateToStr(utcp.convStrToDate(lctre.rceptPeriodEnd,'yyyyMMddHHmm'),'yyyy-MM-dd HH:mm')}</td>
							</tr>
							<tr>
								<th>교육 기간</th>
								<td>${lctre.eduPeriodBegin} ~ ${lctre.eduPeriodEnd}</td>
							</tr>
							<tr>
								<th>교육 금액</th>
								<td><c:choose>
										<c:when test="${lctre.fee > 0 }">
											<fmt:formatNumber value="${lctre.fee }" maxFractionDigits="3" /> 원(부가세포함)
							<c:if test="${lctre.groupFee > 0 }">
								/ 단체 <fmt:formatNumber value="${lctre.groupFee }" maxFractionDigits="3" /> 원(부가세포함)
							</c:if>
										</c:when>
										<c:otherwise>
							무료
						</c:otherwise>
									</c:choose></td>
							</tr>
							<tr>
								<th>상세 내용</th>
								<td>${lctre.cn }</td>
							</tr>
							<tr>
								<th>교육 장소</th>
								<td>${lctre.addr }${lctre.addrEtc }<br>${lctre.addrDetail } (${lctre.addrMemo })
								</td>
							</tr>
						</tbody>
					</table>
					<div class="tb_btn">
						<ul>
							<a href="${utcp.ctxPath}/user/mypage/instrctrEduList.do"><li class="left">목록</li></a>
						</ul>
					</div>

				</div>
				<!--// tab_con1 //-->
				<!--// tab_con3 //-->
				<div class="cont">
					<input type="hidden" id="tab1-page" value=""/>
					<span class="tb_text"> 수강생 <strong class="fc_red">{{tab1.totalCnt}}</strong> 명
					</span>

					<div class="dp_ib fr tr mgb15">
						<label for="tab1-row" class="sound_only">리스트줄선택</label> <select name="sch_select" id="tab1-row" class="select01" style="text-align: left;">
							<option value="10">10줄</option>
							<option value="50">50줄</option>
							<option value="100">100줄</option>
							<option value="150">150줄</option>
						</select> <label for="tab1-srchColumn" class="sound_only">검색대상선택</label> <select name="sch_select" id="tab1-srchColumn" class="select01" style="text-align: left;">
							<option value="">선택</option>
							<option value="userNm">성명</option>
							<option value="belong">소속</option>
						</select> <label for="tab1-srchWrd" class="sound_only">검색어입력</label> <input type="text" name="sch_input" id="tab1-srchWrd" placeholder="검색" class="btn04 btn_blackl tl mgr5" />

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
								<td>{{ (tab1.totalCnt - (tab1.vo.page - 1) * tab1.vo.row - i) }}
								<input type="hidden" name="userId" v-model="vo.userId"/>
								</td>
								<td><a href="#none">{{vo.userNm}}</a></td>
								
								<td>{{vo.mobile}}</td>
								<td class="tl">{{vo.belong}}</td>
								<td>
									{{vo.attendCnt}} / {{lectTimeCnt}} ({{(vo.attendCnt/lectTimeCnt*100).toFixed(1)}}%)
								</td>
								<td><input name="workshopScore" :id="'tab1-workshopScore-'+i" type="text" class="ip5 tc" placeholder="40" :value="vo.workshopScore" maxlength="3" numberonly style="width:50px;"/></td>
								<td><input name="examScore" :id="'tab1-examScore-'+i" type="text" class="ip5 tc" placeholder="40" :value="vo.examScore" maxlength="3" numberonly style="width:50px;"/></td>
								<td class="fc_red">
									<select class="select01" name="passYn" v-model="vo.passYn" style="width:50px;padding:0 0 0 0;">
										<option value="Y">O</option>
										<option value="N">X</option>
									</select> 
								</td>
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
		<!--// board_tab_onoff //-->

	</div>
</div>



<script type="text/javascript">
$(function(){
	// board_tab(ul) onoff //
	$('.board_tab_onoff>.board_tab_con').children().css('display', 'none');
	$('.board_tab_onoff>.board_tab_con> div:first-child').css('display', 'block');
	$('.board_tab_onoff>.board_tab li:first-child').addClass('active');
	$('.board_tab_onoff').delegate('.board_tab>li', 'click', function() {
		var index = $(this).parent().children().index(this);
		$(this).siblings().removeClass();
		$(this).addClass('active');
		$(this).parent().next('.board_tab_con').children().hide().eq(index).show();
		
		//수강생 출석/성적/합격 탭 이벤트
		if(index==1){
			fn_tab1_search();
		}
	});
	// board_tab(ul) onoff //
	
	//전체 선택
	$('#tab1-checkbox-all').click(function(){
		if($(this).is(':checked')){
			$('input[id^=tab1-userId]').prop('checked',true);
		}else{
			$('input[id^=tab1-userId]').prop('checked',false);
		}
	});
	
	$('#tab1-row').change(function(r){
		fn_tab1_search();
	});
	
	
});

var vue_data_1={
	tab1:{},
	checkYn:'${lctre.checkYn}',
	tab1Paging:'',
};
var vm = new Vue({
	el:'#vue-instrctrEduDetail',
	data:vue_data_1,
	updated: function () {
		$('[numberonly]').on('keyup',function(){
			$(this).val($(this).val().replace(/[^0-9]/g,''));
		});
	}
});

//학생 리스트
function fn_tab1_loadStdntList(page,row,srchWrd,srchColumn){
	var param={};
	param.page=page;
	param.row=row;
	param.srchWrd=srchWrd;
	param.srchColumn=srchColumn;
	param.eduSeq=$('#eduSeq').val();
	$.ajax({
		data:param,
		url:'${utcp.ctxPath}/user/edu/lectureStdntList.json',
		success:function(r){
			vm.tab1=r;
			vm.tab1Paging=createPaging(r.pageNavi);
			vm.lectTimeCnt=r.lectTimeCnt;
			vm.passCnt=r.passCnt;
			vm.totalCnt=r.totalCnt;
		}
	});
}

//탭1 검색 
function fn_tab1_search(gubun){
	if(gubun==1){//gubun==1이면 검색버튼 눌렀을 때, 검색조건 선택여부 체크 
		if($('#tab1-srchColumn').val()==''){
			_vue.alert='검색 조건을 선택하세요';
			_remodal.open();
			return;
		}
	}
	fn_tab1_loadStdntList(
			$('#tab1-page').val()
			,$('#tab1-row').val()
			,$('#tab1-srchWrd').val()
			,$('#tab1-srchColumn').val()
	);
}

function fn_tab1_page(no){
	$('#tab1-page').val(no);
	fn_tab1_search();
}

//탭1 선택 저장
function fn_tab1_saveScore(){
	var formData={};
	formData.eduSeq = $('#eduSeq').val();
	formData.dataList=[];
	$('tr.score_data').each(function(){
		var dataMap={};
		dataMap.eduSeq = $("#eduSeq").val();
		dataMap.userId = $(this).find('input[name=userId]').val();
		dataMap.attCnt = $(this).find('input[name=attCnt]').val();
		dataMap.workshopScore = $(this).find('input[name=workshopScore]').val();
		dataMap.examScore = $(this).find('input[name=examScore]').val();
		dataMap.passYn = $(this).find('select[name=passYn]').val();
		formData.dataList.push(dataMap);
	});
	$.ajax({
		data:JSON.stringify(formData),
		//,url:'${utcp.ctxPath}/user/edu/saveUserScoreInfoArr.json'
		url:'${utcp.ctxPath}/user/ajax/saveStdntScore.json',
		type:'post',
		contentType:'application/json',
		success:function(r){
			if(r.result!=''){
				_vue.alert=r.result;
				_remodal.open();
			}else{
				_vue.alert='정상적으로 저장되었습니다.';
				_remodal.open();
				fn_tab1_search();
			}
		}
	});
}

function createPaging(pageNavi){
	var pageHtml = '';
	pageHtml += "<div class=\"inner cf\">";
	if(pageNavi.currentPageNo != 1){
		pageHtml +=	"	<div class=\"page_prev0\"><a href=\"javascript:;\" onclick=\"fn_tab1_page('" + (pageNavi.currentPageNo - 1) + "');\">&lt; 이전</a></div>";	
	}
	var subHtml = "";
	for(var i = pageNavi.firstPageNoOnPageList; i <= pageNavi.lastPageNoOnPageList; i++){
		if(i == pageNavi.currentPageNo){
			subHtml = "<div class=\"page_now\"><a>" + i + "</a></div>";
		}else{
			subHtml = "<div class=\"page_nomal\"><a href=\"javascript:;\" onclick=\"fn_tab1_page('" + i + "');\">" + i + "</a></div>";
		}
		pageHtml += subHtml;
	}
	if(pageNavi.currentPageNo != pageNavi.totalPageCount && pageNavi.totalPageCount > 0){
		pageHtml +=	"	<div class=\"page_next0\"><a href=\"javascript:;\" onclick=\"fn_tab1_page('" + (pageNavi.currentPageNo + 1) + "');\">다음 &gt;</a></div>";	
	}
	pageHtml += "</div>";
	return pageHtml;
}

function fn_gooroomee(){
	$.ajax({
		type:'post',data:{eduSeq:14,timeSeq:7},url:'${utcp.ctxPath}/user/remote/gooroomee/login.json',
		success:function(result){
			if(result.msg){
				alert(result.msg);return;
			}
			window.open('https://biz.gooroomee.com/room/otp/'+result.otp,'','');
		}
	});
}

</script>

<!-- 공통 알림 -->
<div id="remodal-alert" class="remodal messagePop2" data-remodal-id="remodal-alert" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc">
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC">알림</p>
		</div>
		<div class="modal-body">
			<p class="messageTxt" id="messageTab2">
				{{alert}}
			</p>
		</div>
		<div class="modal-footer">
			<div class="tc">
				<button data-remodal-action="cancel" class="remodal-confirm btn02 btn_orange">확인</button>
			</div>
		</div>
	</div>
</div>
	<script>
	var _remodal = $('[data-remodal-id=remodal-alert]').remodal();
	var _vue = new Vue({
		el:'#remodal-alert',data:{alert:'알림 내용'}
	});
	</script>