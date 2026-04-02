<%@page import="com.educare.edu.education.service.EduVO"%>
<%@page import="com.educare.edu.education.service.impl.EduMapper"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<jsp:useBean id="curDate" class="java.util.Date" />


<%
//교육건수 조회
String userId = request.getParameter("userId");
ServletContext conext = session.getServletContext();
WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(conext);
EduMapper eduMapper = (EduMapper)wac.getBean("EduMapper");
EduVO vo = new EduVO();
vo.setUserId(userId);
vo.setGubun("Open");
int openCnt = eduMapper.selectMyEduCnt(vo);
vo.setGubun("Stdnt");
int stdntCnt = eduMapper.selectMyEduCnt(vo);
int lcrcpCnt = eduMapper.selectLcrcpPageCnt(vo);

%>

<section class="pd025 pd200 po_re">
	<div class="po_re br5 bs_box">
		<table class="member_info">
			<caption class="sound_only">회원정보테이블</caption>
			<tbody>
				<tr>
					<td rowspan="5"><img src="${utcp.ctxPath }/resources/admin/images/memberNo.jpg" alt="회원사진"></td>
					<td>성명</td>
					<td>${user.userNm}</td>
				</tr>
				<tr>
                                <td>생년월일</td>
                                <td>${user.birth }</td>
                            </tr>
				<tr>
                                <td>연락처</td>
                                <td>${user.decMobile }</td>
                            </tr>
                            <tr>
                                <td>이메일</td>
                                <td>${user.decEmail }</td>
                            </tr>
			</tbody>
		</table>

		<div class="stdntRt cf">
			<ul>
				<li>
					<span class="icon bg-info-gradient"><i class="fas fa-check-square"></i></span> <span class="title">진행 교육</span> <span class="num"><a href="javascript:fn_tab('2');"><strong class="fc_red"><%=openCnt %></strong></a> 건</span>
				</li>
				<li>
					<span class="icon bg-danger-gradient"><i class="fas fa-award"></i></span> <span class="title">수강 교육</span> <span class="num"><a href="javascript:fn_tab('4');"><strong class="fc_red"><%=stdntCnt %></strong></a> 건</span>
				</li>
				<li>
					<span class="icon bg-warning-gradient"><i class="fas fa-calendar-plus"></i></span> <span class="title">신청 교육</span> <span class="num"><a href="javascript:fn_tab('6');"><strong class="fc_red"><%=lcrcpCnt %></strong></a> 건</span>
				</li>
				<!-- <li>
					<span class="icon bg-success-gradient"><i class="fab fa-buffer"></i></span> <span class="title">내가 작성한 글</span> <span class="num"><strong class="fc_red">0</strong> 건</span>
				</li> -->
			</ul>
		</div>

		<!--// board_tab_onoff //-->
		<div class="board_tab_onoff">

			<!--// board_tab //-->
			<ul class="board_tab">
				<li class="active">
					<p><a href="javascript:;" onclick="fn_tab(1);">프로필 상세</a></p>
				</li>
				<li>
					<p><a href="javascript:;" onclick="fn_tab(2);">진행중 교육</a></p>
				</li>
				<li style="display: none;">
					<p><a href="javascript:;" onclick="fn_tab(3);">교육신청 내역</a></p>
				</li>
				<li>
					<p><a href="javascript:;" onclick="fn_tab(4);">교육수강 내역</a></p>
				</li>
				<li style="display:none;"></li>
				<li>
					<p><a href="javascript:;" onclick="fn_tab(6);">교육신청 내역</a></p>
				</li>
				<!-- 
				<li>
					<p><a href="javascript:;" onclick="fn_tab(7);">SMS</a></p>
				</li>
				 -->
			</ul>
			<!--// board_tab //-->

			<!--// board_tab_con //-->
			<div class="board_tab_con">

				<!--// tab_con1 //-->
				<div class="cont" id="area-tab-1" style="display:;">
					<jsp:include page="stdntView_inc1.jsp"></jsp:include>
				</div>
				<!--// tab_con1 //-->

				<!--// tab_con2 //-->
				<div class="cont" id="area-tab-2" style="display: none;">
					<jsp:include page="stdntView_inc2.jsp"></jsp:include>
				</div>
				<!--// tab_con2 //-->
				<div class="cont" id="area-tab-5" style="display: none;"></div>
				<div class="cont" id="area-tab-6" style="display: none;">
					<jsp:include page="stdntView_inc6.jsp"></jsp:include>
				</div>
				<div class="cont" id="area-tab-7" style="display: none;">
					<jsp:include page="stdntView_inc7.jsp"></jsp:include>
				</div>

			</div>
			<!--// board_tab_con //-->
		</div>
		<!--// board_tab_onoff //-->
		<div class="fl tc">
			<button class="btn02 btn_grayl" onclick="location.href='stdntList.do';">목록</button>
			<%-- <button class="btn02 btn_grayl" onclick="location.href='stdntRgs.do?userId=${param.userId}'">수정</button> --%>
		</div>
	</div>
</section>
<input type="hidden" id="sv-gubun" />
<input type="hidden" id="sv-page" value="1" />
<script type="text/javascript">
if('${param.tab}' != ''){
	fn_tab('${param.tab}')
}
	function fn_tab(no) {
		$('#sv-page').val(1);
		$('.board_tab > li').removeClass('active');
		$('.board_tab > li').eq(no - 1).addClass('active');

		$('[id^=area-tab').hide();
		if (no == 1) {
			$('#area-tab-1').show();
		} else if ('2,3,4'.indexOf(no) >= 0) {
			$('#area-tab-2').show();
			if('2' == no){
				fn_getMyEduList('Open');
			}else if('4' == no){
				fn_getMyEduList('Stdnt');
			}
		} else if (no == 5) {
			fn_getStdntTmRceptList('${param.userId}',1);
			$('#area-tab-5').show();
		} else if (no == 6) {
			fn_getStdntLcRceptList('${param.userId}',1);
			$('#area-tab-6').show();
		}else if (no == 7) {
			fn_getStdntSmsList('${param.userId}',1);
			$('#area-tab-7').show();
		}

	}
</script>