<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<section class="pd025 pd200 po_re">
	<div class="po_re br5 bs_box">
		<form id="form-srch">
			<input type="hidden" name="pageNo"/>
			<div class="fl tr mgb15">
				<select name="srchCtg1Seq" class="">
					<option value="0">1차카테고리</option>
					<c:forEach items="${cateList }" var="o">
						<option value="${o.qstnCtgSeq }" ${param.srchCtg1Seq == o.qstnCtgSeq?'selected':'' }>${o.ctgNm }</option>
					</c:forEach>
				</select>
				<select name="srchCtg2Seq" class="">
					<option value="0">2차카테고리</option>
					<c:forEach items="${cateList2 }" var="o">
						<option value="${o.qstnCtgSeq }" ${param.srchCtg2Seq == o.qstnCtgSeq?'selected':'' }>${o.ctgNm }</option>
					</c:forEach>
				</select>
				<select name="srchCtg3Seq" class="">
					<option value="0">3차카테고리</option>
					<c:forEach items="${cateList3 }" var="o">
						<option value="${o.qstnCtgSeq }" ${param.srchCtg3Seq == o.qstnCtgSeq?'selected':'' }>${o.ctgNm }</option>
					</c:forEach>
				</select>
				
				<label for="srchWrd" class="sound_only">검색어입력</label>
				<input type="text" id="srchWrd" name="srchWrd" value="${param.srchWrd }" placeholder="시험지명 검색" class="btn04 btn_blackl tl mgr5">
				<button type="button" onclick="fn_srch();" class="btn04 btn_black fr">검색</button>
			</div>
		</form>
			<table class="w100 tb01 tc">
				<thead>
					<tr>
						<th>번호</th>
						<th>시험지명</th>
						<th>카테고리</th>
						<th>작성자</th>
						<th>등록일</th>
						<th>문제수</th>
						<th>시간</th>
						<th></th>
					</tr>
				</thead>
				<c:forEach items="${data.list}" var="o" varStatus="s">
					<tr>
						<td>${data.pageNavi.totalRecordCount-(data.pageNavi.firstRecordIndex+s.index) }</td>
						<td class="tl">${o.testTmplNm }</td>
						<td>${o.ctg1Nm }</td>
						<td>${o.regNm }</td>
						<td>${utcp.convDateToStr(o.regDe,'yyyy-MM-dd HH:mm') }</td>
						<td>${o.qstnCnt }</td>
						<td>${o.timeLimit }분</td>
						<td>
						<button type="button" onclick="location.href='quizTestReg.do?testTmplSeq=${o.testTmplSeq }';" class="btn04 btn_blue">수정</button>
						<button type="button" onclick="fn_delQuizTest(${o.testTmplSeq })" class="btn04 btn_orange">삭제</button>
						</td>
						
					</tr>
				</c:forEach>
				<c:if test="${empty data.list }">
					<tr>
						<td colspan="8">데이터가 없습니다.</td>
					</tr>
				</c:if>
			</table>

			<c:set var="pageNavi" value="${data.pageNavi }" />
			<%request.setAttribute("pageNavi", pageContext.getAttribute("pageNavi"));%>
			<jsp:include page="/WEB-INF/jsp/admin/bbs/pageNavi.jsp" />
			<div class="tr fbTxt">
			<button class="btn01 btn_green fbBtn" onclick="location.href='quizTestReg.do';">
				<i class="fas fa-file-signature"></i> 시험지 만들기
			</button>
		</div>
		</div>
	</div>
</section>

<script>
$('select[name=srchCtg1Seq],select[name=srchCtg2Seq],select[name=srchCtg3Seq]').change(function(){
	fn_srch();
});
function fn_srch(){
	if($('select[name=srchCtg1Seq]').val()=='0'){
		$('select[name=srchCtg2Seq]').val('0');
		$('select[name=srchCtg3Seq]').val('0');
	}
	fnc_paging(1);
}
function fnc_paging(pageNo){
	$('input[name=pageNo]').val(pageNo);
	$('#form-srch').submit();
}

function fn_delQuizTest(testTmplSeq){
	if(!confirm('문제를 삭제하시겠습니까?')){
		return;
	}
	$.ajax({
		type: 'post',
		data: {testTmplSeq: testTmplSeq},
		url: '${utcp.ctxPath}/admin/quizTest/delQuizTestQstn.ajax',
		success: function(r){
			if(r.result == 1){
				location.reload();
			}else{
				alert(r.msg);
			}
			
		}
	});
}
</script>
