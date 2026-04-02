<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<jsp:useBean id="curDate" class="java.util.Date" />

<section class="pd025 pd200 po_re">
	<div class="po_re br5 bs_box">
		<table class="member_info">
			<caption class="sound_only">회원정보테이블</caption>
			<tbody>
				<tr>
					<td rowspan="4"><img src="${utcp.ctxPath}/resources/admin/images/memberNo.jpg" alt="회원사진" /></td>
					<td>성명</td>
					<td>${user.userNm}</td>
				</tr>
				<tr>
					<td>핸드폰</td>
					<td>${user.decMobile}</td>
				</tr>
				<%-- <tr>
					<td>아이디</td>
					<td>${user.loginId}</td>
				</tr> --%>
				
			</tbody>
		</table>

		<!--// board_tab_onoff //-->
		<div class="board_tab_onoff">

			<!--// board_tab //-->
			<ul class="board_tab">
				<li class="active">
					<p><a href="javascript:;" onclick="fn_tab(1);">프로필 상세보기</a></p>
				</li>
				<!-- 
				 -->
				<li>
					<p><a href="javascript:;" onclick="fn_tab(2);fn_getMyEduList('Open');">진행중 교육</a></p>
				</li>
				<li>
					<p><a href="javascript:;" onclick="fn_tab(3);fn_getMyEduList('Wait');">대기중 교육</a></p>
				</li>
				<li>
					<p><a href="javascript:;" onclick="fn_tab(4);fn_getMyEduList('All');">나의교육 내역</a></p>
				</li>
			</ul>
			<!--// board_tab //-->

			<!--// board_tab_con //-->
			<div class="board_tab_con">

				<!--// tab_con1 //-->
				<div class="cont" id="area-tab-1" style="display:;">
					<jsp:include page="instrctrView_inc1.jsp"></jsp:include>
				</div>
				<!--// tab_con1 //-->

				<!--// tab_con2 //-->
				<div class="cont" id="area-tab-2" style="display: none;">
					<jsp:include page="instrctrView_inc2.jsp"></jsp:include>
				</div>
				<!--// tab_con2 //-->

				<!--// tab_con3 //-->
				<div class="cont" id="area-tab-3" style="display: none;"></div>
				<!--// tab_con3 //-->
			</div>
			<!--// board_tab_con //-->
		</div>
		<!--// board_tab_onoff //-->
		
	</div>
</section>
<script type="text/javascript">
	function fn_tab(no) {
		$('.board_tab > li').removeClass('active');
		$('.board_tab > li').eq(no - 1).addClass('active');

		$('[id^=area-tab').hide();
		if (no == 1) {
			$('#area-tab-1').show();
		} else if ('2,3,4'.indexOf(no) >= 0) {
			$('#area-tab-2').show();
		} else {
			$('#area-tab-3').show();
		}

	}
</script>