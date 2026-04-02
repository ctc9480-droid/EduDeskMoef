<%@page import="java.util.Date"%>
<%@page import="com.educare.util.DateUtil"%>
<%@page import="com.educare.edu.education.service.EduVO"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="com.educare.edu.education.service.impl.EduMapper"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="com.educare.edu.menu.service.model.Menu"%>
<%@ page import="com.educare.edu.menu.service.MenuUtil"%>
<%@ page import="com.educare.edu.member.service.SessionUserInfoHelper"%>
<%@ page import="com.educare.edu.member.service.model.UserInfo"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<c:if test="${sessionScope.sessionUserInfo.userMemLvl eq '9' }">

<%
//교육건수 조회
String userId = SessionUserInfoHelper.getUserId();
ServletContext conext = session.getServletContext();
WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(conext);
EduMapper eduMapper = (EduMapper)wac.getBean("EduMapper");
EduVO vo = new EduVO();
vo.setUserId(userId);
vo.setSrchDate(DateUtil.getDate2Str(new Date(),	"yyyy-MM-dd"));
vo.setGubun("Open");
int openCnt = eduMapper.selectMyEduCnt(vo);
vo.setGubun("Stdnt");
int stdntCnt = eduMapper.selectMyEduCnt(vo);
int lcrcpCnt = eduMapper.selectLcrcpPageCnt(vo);

%>

<div class="userInfobox">
	<div class="profile">
		<div class="userImg dp_ib mgr15">
			<img src="${utcp.ctxPath }/resources/user/image/userInfoImg2.png" alt="">
		</div>
		<div class="dp_ib tl">
			<h3 class="fw_400">
				<b class="fc_blue fw_400">${userNm }</b> 님 안녕하세요? (교육생)
			</h3>
			<p class="mgt10 mgb10">최종접속 : ${utcp.convDateToStr(lstLoginDe,'yyyy-MM-dd HH:mm') }</p>
			<p>총 수료 : <b class="fc_red">${myStat.passCnt }</b> 개
			</p>
		</div>
	</div>
	<div class="user_edu_wrap">
		<ul class="user_edu_list">
			<li class="edu_list">
			<a href="${utcp.ctxPath }/user/mypage/myEduOpenList.do" class="edu_list_link">
				<div class="edu_list_top">
					<div class="img">
						<img src="${utcp.ctxPath}/resources/user/image/icon/eduList03.png" alt="" />
					</div>
					<h4>학습공간</h4>
				</div>
				<p><strong class="num"><%=openCnt %></strong>개</p>
			</a>
			</li>
			<li class="edu_list">
			<a href="${utcp.ctxPath }/user/mypage/myLcRceptList.do" class="edu_list_link">
				<div class="edu_list_top">
					<div class="img">
						<img src="${utcp.ctxPath}/resources/user/image/icon/eduList02.png" alt="" />
					</div>
					<h4>교육신청 내역</h4>
				</div>
				<p><strong class="num"><%=lcrcpCnt %></strong>개</p>
				</a>
			</li>
			<li class="edu_list">
			<a href="${utcp.ctxPath }/user/mypage/myEduStdntList.do" class="edu_list_link">
				<div class="edu_list_top">
					<div class="img">
						<img src="${utcp.ctxPath}/resources/user/image/icon/eduList01.png" alt="" />
					</div>
					<h4>교육수강 내역</h4>
				</div>
				<p><strong class="num"><%=stdntCnt %></strong>개</p>
				</a>
			</li>
		</ul>
	</div>

	<ul class="po_ab">
		<li class="dp_ib">
			<a href="${utcp.ctxPath }/user/mypage/updUser.do"><i class="fas fa-user-edit"></i> 개인정보 수정</a>
		</li>
	</ul>
</div>

<br/>
<!-- 
<div class="stdntRt cf">
	<ul>
		<li>
			<div>
				<span class="icon bg-info-gradient"><i class="fas fa-check-square"></i></span> <span class="title">진행중 교육</span>
			</div>
			<span class="num"><strong class="fc_red">2</strong> 건</span>
		</li>
		<li>
			<div>
				<span class="icon bg-danger-gradient"><i class="fas fa-award"></i></span> <span class="title">신청중 교육</span>
			</div>
			<span class="num"><strong class="fc_red">3</strong> 건</span>
		</li>
		<li>
			<div>
				<span class="icon bg-warning-gradient"><i class="fas fa-calendar-plus"></i></span> <span class="title">지난교육내역</span>
			</div>
			<span class="num"><strong class="fc_red">1</strong> 건</span>
		</li>
		<li>
			<div>
				<span class="icon bg-success-gradient"><i class="fab fa-buffer"></i></span> <span class="title">교육상담내역</span>
			</div>
			<span class="num"><strong class="fc_red">3</strong> 건</span>
		</li>
	</ul>
</div>
 -->
</c:if>