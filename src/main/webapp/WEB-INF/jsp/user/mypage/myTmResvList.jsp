<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<div class="listWrap" style="padding-top: 0">
	<div id="listDiv">

		<div class="board_tab_onoff">
			 <ul class="board_tab">
				<li id="tab1" class="">
					<p><a href="?tabNm=" >신청내역</a></p>
				</li>
				<li id="tab2" class="active">
					<p><a href="?tabNm=resv" >선점내역</a></p>
				</li>
			</ul>
		
			<!--// board_tab_con //-->
			<div class="board_tab_con">

				<!--// tab_con1 //-->
				<div class="cont tableRespon">
					<table class="tb07 timetb">
						<caption class="sound_only">선점내역 리스트</caption>
						<thead>
							<tr>
								<th scope="col" class="vm">순번</th>
								<th scope="col" class="vm">교육명</th>
								<th scope="col" class="vm">금액</th>
								<th scope="col" class="vm">신청인원</th>
								<th scope="col" class="vm" style="min-width: 90px;">교육일</th>
								<th scope="col" class="vm" style="min-width: 79px;">교육시간</th>
								<th scope="col" class="vm" style="min-width: 115px;">선점 유효시간</th>
								<th scope="col" class="vm">신청</th>
								<th scope="col" class="vm">취소</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="o" items="${data.tmResvList}" varStatus="s">
								<tr>
									<td class="fs_12 ls-05">${s.count }</td>
									<td class="fs_12 ls-05">${o.eduNm }</td>
									<td>
									<fmt:formatNumber value="${o.fee }" type="currency" currencySymbol="￦" minFractionDigits="0" maxFractionDigits="0" />
									</td>
									<td>${o.minPersonnel}~${o.personnel}명</td>
									<td class="fs_12 ls-05">${utcp.convDateToStr(utcp.convStrToDate(o.tmEduDt,'yyyyMMdd'),'yyyy-MM-dd {E)') }</td>
									<td class="fs_12 ls-05">${utcp.convDateToStr(utcp.convStrToDate(o.startTm,'HHmm'),'HH:mm') } ~ 
									${utcp.convDateToStr(utcp.convStrToDate(o.endTm,'HHmm'),'HH:mm') }</td>
									<td class="fs_12 ls-05 fc_red">${utcp.convDateToStr(o.resvDt,'yyyy-MM-dd HH:mm:ss') }</td>
									<td><a href="${utcp.ctxPath}/user/edu/tmlctRcept.do?tmSeq=${o.tmSeq}&tmPlanSeq=${o.tmPlanSeq}&tmSubSeq=${o.tmSubSeq}&tmEduDt=${o.tmEduDt}" class="btn04 btn_blue">신청</a></td>
									<td><a href="javascript:fn_cancelTmResv('${o.tmSeq }','${o.tmPlanSeq }','${o.tmSubSeq }','${o.tmEduDt }');" class="btn04 btn_blue">취소</a> </td>
								</tr>
							</c:forEach>
							<c:if test="${empty data.tmResvList }">
								<tr>
									<td colspan="9">내역이 없습니다.</td>
								</tr>
							</c:if>
						</tbody>
					</table>

				</div>
				<!--// tab_con1 //-->

				<!--// tab_con2 //-->
				<div class="cont" style="display: block;"></div>
				<!--// tab_con2 //-->

				<!--// tab_con3 //-->
				<div class="cont" style="display: block;"></div>
				<!--// tab_con3 //-->


			</div>
			<!--// board_tab_con //-->
		</div>
		<!--// board_tab_onoff //-->
<!-- 
		<form name="form_search" method="get">
		<div class="box_sort flex listWrapBox">
			<div class="box_search mgauto">
				<select class="srchColumn" title="제목+내용 검색">
					<option value="titCont">제목+내용</option>
				</select>
				<input type="text" id="srchWrd" />
				<button type="submit" onclick="fn_srch(); return false;">검색</button>
			</div>
		</div>
		</form>
		 -->
	</div>
</div>


<script>
function fn_cancelTmResv(tmSeq,tmPlanSeq,tmSubSeq,tmEduDt){
	if(!confirm('취소하시겠습니까?')){
		return;
	}
	$.ajax({
		type : 'post',
		url : 'myTmResvCancel.ajax',
		data : {tmSeq : tmSeq, tmPlanSeq : tmPlanSeq, tmSubSeq : tmSubSeq, tmEduDt : tmEduDt},
		success : function (r){
			if(r.result!=1){
				alert(r.msg);
				return;
			}
			location.reload();
			
		}
	});
}
</script>