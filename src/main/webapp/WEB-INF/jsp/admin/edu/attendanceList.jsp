<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<section class="pd025 pd200 po_re">
	<div class="po_re br5 bs_box cf">
		<div class="cont">
			<c:if test="${sessionScope.sessionUserInfo.userMemLvl==1 }">
			<div class="dp_ib fl tl mgb15">
				<label for="sch_select" class="sound_only">기관명선택</label> 
				<select id="sch-org-cd" class="">
					<option value="">전체기관</option>
					<c:forEach items="${orgList }" var="o">
						<option value="${o.orgCd }" ${param.reqOrgCd eq o.orgCd?'selected':'' }>${o.orgNm }</option>
					</c:forEach>
				</select>
			</div>
			</c:if>

			<div class="box_sort listWrapBox dp_b w100 mgb15">
				<div class="box_cate" id="vm-category">
					<select class="srchCtgry" id="sch-ctgry-1" v-model="reqCtgry1">
						<option value="0">카테고리</option>
						<option v-for="o in ctgryList1" :value="o.ctgrySeq" >{{o.ctgryNm}}</option>
					</select> 
					<select class="srchCtgry" id="sch-ctgry-2" v-model="reqCtgry2">
						<option value="0">하위 카테고리 전체</option>
						<option v-for="o in ctgryList2" :value="o.ctgrySeq">{{o.ctgryNm}}</option>
					</select>
					<script>
					var reqCtgry1='${param.reqCtgry1}'*1;
					var reqCtgry2='${param.reqCtgry2}'*1;
					var ctgryList1=[];
					var ctgryList2=[];
					var vm_category = new Vue({
						el:'#vm-category',
						data:{reqCtgry1:reqCtgry1,reqCtgry2:reqCtgry2,
							ctgryList1:[],ctgryList2:[]},
					});
					$.ajax({
						url:'${utcp.ctxPath}/comm/api/callAllCategory.json',
						success:function(r){
							vm_category.ctgryList1=r.ctgryList1;
							for(i in r.ctgryList2){
								if(r.ctgryList2[i].parentSeq==reqCtgry1){
									vm_category.ctgryList2.push(r.ctgryList2[i]);
								}
							}
							$('#sch-ctgry-1').change(function(){
								reqCtgry1=this.value;
								vm_category.ctgryList2=[];
								for(i in r.ctgryList2){
									if(r.ctgryList2[i].parentSeq==reqCtgry1){
										vm_category.ctgryList2.push(r.ctgryList2[i]);
									}
								}	
							});
													
						}
					});
					</script>
				</div>
				<div class="box_search">
					<select class="srchColumn" class="mgr10" id="sch-wrd-key">
						<option value="edu_nm">교육명</option>
						<option value="instctr_nm">강사명</option>
					</select> <input type="text" id="sch-wrd" />
					<button type="button" onclick="fn_search()">검색</button>
				</div>
			</div>

			<span class="tb_text">
				총 <strong class="fc_red">${totalCnt}</strong> 개
			</span>
			<div class="dp_ib fr tr">
				<div class="sortWrap cf">
					<ul>
						<li>
							<a href="#">가나다순</a>
							<span>|</span>
						</li>
						<li>
							<a href="#">날짜순</a>
						</li>
					</ul>
				</div>
			</div>


			<table width="100%" class="tb01 tc">
				<caption class="sound_only">회원정보테이블</caption>
				<thead bgcolor="#f7f8fa">
					<tr>
						<th>NO</th>
						<th>교육명</th>
						<th>카테고리</th>
						<th>강사명</th>
						<th>수강생</th>
						<th>총 교육수</th>
						<th>출석률</th>
						<th>시작일</th>
						<th>상태</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="o" items="${list}" varStatus="stat">
						<tr>
							<td>${totalCnt - o.rNum + 1}</td>
							<td><a href="attendanceView.do?eduSeq=${o.eduSeq }">${o.eduNm}</a></td>
							<td>${o.eduCtgryNm }</td>
							<td>${o.profNm }</td>
							<td>${o.stdntCnt }</td>
							<td>${o.timeCnt }</td>
							<td><fmt:formatNumber type="percent" value="${o.attRatio }" pattern="0.0%" /></td>
							<td>${o.eduPeriodBegin }</td>
							<td>-</td>
						</tr>
					</c:forEach>
					<c:if test="${empty list }">
						<tr>
							<td colspan="8" class="h200"">데이터가 없습니다.</td>
						</tr>
					</c:if>
				</tbody>
			</table>


			<!--// paging //-->
			<jsp:include page="/WEB-INF/jsp/admin/common/paging.jsp" />
			<!--// paging //-->
		</div>
	</div>
</section>

<form name="search_form" method="get">
	<input type="hidden" name="pageNo" id="pageNo" value="${empty param.pageNo?1:param.pageNo }" />
	<input type="hidden" name="srchWrd" id="srchWrd" value="${param.srchWrd}" />
	<input type="hidden" name="reqCtgry1" value="${param.reqCtgry1}" />
	<input type="hidden" name="reqCtgry2" value="${param.reqCtgry2}" />
	<input type="hidden" name="reqOrgCd" value="${param.reqOrgCd}" />
</form>
<script>
	$(document).ready(function(){
		$('#sch-org-cd').change(function(){
			fn_search();
		});
	});
	function fn_paging(page) {
		$("#pageNo").val(page);
		fn_search();
	}
	function fn_search(){
		$('[name=reqCtgry1]').val($('#sch-ctgry-1').val());
		$('[name=reqCtgry2]').val($('#sch-ctgry-2').val());
		$('[name=reqOrgCd]').val($('#sch-org-cd').val());
		$('[name=reqWrdKey]').val($('#sch-wrd-key').val());
		$('[name=reqWrd]').val($('#sch-wrd').val());
		$("form[name='search_form']").submit();
	}
</script>