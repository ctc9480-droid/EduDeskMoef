<%@page import="com.educare.edu.comn.mapper.FeedbackEduMapper"%>
<%@page import="com.educare.edu.comn.model.FeedbackEdu"%>
<%@page import="com.educare.util.LncUtil"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.educare.edu.feedback.mapper.FeedbackMapper"%>
<%@page import="org.springframework.util.ObjectUtils"%>
<%@page import="com.educare.edu.comn.vo.FeedbackVO"%>
<%@page import="com.educare.edu.education.service.EduService"%>
<%@page import="com.educare.edu.member.service.SessionUserInfoHelper"%>
<%@page import="com.educare.edu.feedback.service.FeedbackService"%>
<%@page import="java.util.Map"%>
<%@page import="com.educare.edu.comn.mapper.LectureTimeStdntMapper"%>
<%@page import="com.educare.edu.comn.model.LectureTimeStdnt"%>
<%@page import="com.educare.edu.education.service.model.LectureStdnt"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="com.educare.edu.education.service.model.LectureRcept"%>
<%@page import="java.util.List"%>
<%@page import="com.educare.edu.education.service.impl.EduMapper"%>
<%@page import="com.educare.edu.education.service.model.Lecture"%>
<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
//파라미터
int eduSeq = Integer.parseInt(request.getParameter("eduSeq"));
int feSeq = Integer.parseInt(request.getParameter("feSeq"));
//빈설정
ServletContext conext = session.getServletContext();
WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(conext);

//빈가져오기
EduService eduService = (EduService)wac.getBean("EduService");
FeedbackService feedbackService = (FeedbackService)wac.getBean("FeedbackService");
FeedbackMapper feedbackMapper = (FeedbackMapper)wac.getBean("FeedbackMapper");
FeedbackEduMapper feedbackEduMapper = (FeedbackEduMapper)wac.getBean("FeedbackEduMapper");

//시작
Lecture lecture = eduService.getLctreDetail(eduSeq);
int fbIdx = 0;
//설문지 조회
if(feSeq > 0){
	FeedbackEdu fe = feedbackEduMapper.selectByPk(feSeq);
	fbIdx = fe.getFbIdx();
}else{
	fbIdx = lecture.getFbIdx();
}

Map<String, Object> feedbackInfo = feedbackService.getFeedbackInfo(fbIdx );
List<Map<String, Object>> qtList =  (List<Map<String, Object>>) feedbackInfo.get("qtList");
for(Map<String, Object> qtMap:qtList){
	FeedbackVO qt = (FeedbackVO) qtMap.get("qt");
	if(qt.getQtType()!=0){
		List<FeedbackVO> chList =  (List<FeedbackVO>) qtMap.get("chList");
		int qtAsCnt = 0;
		for(FeedbackVO feedbackChoice:chList){
			feedbackChoice.setEduSeq(eduSeq);
			feedbackChoice.setFeSeq(feSeq);
			FeedbackVO asMap = feedbackMapper.selectFeedbackAnswerByChIdxCnt(feedbackChoice);
			if(!ObjectUtils.isEmpty(asMap)){
				feedbackChoice.setAsCnt(asMap.getAsCnt());
				qtAsCnt+=asMap.getAsCnt();
			}
		}
		qt.setQtAsCnt(qtAsCnt);
	}
	if(qt.getQtType()==0){
		qt.setEduSeq(eduSeq);
		qt.setFeSeq(feSeq);
		List<FeedbackVO> asList = feedbackMapper.selectFeedbackAnswerByFbIdxQtIdx(qt);
		qtMap.put("asList", asList);
	}
}

pageContext.setAttribute("eduNm", lecture.getEduNm());
pageContext.setAttribute("feedbackInfo", feedbackInfo);

String fileNm1 = "[설문조사 현황] "+lecture.getEduNm()+"_"+lecture.getEduSeq();
fileNm1 = fileNm1.replaceAll(" ", "_");
fileNm1 = fileNm1.replaceAll(",", "_");
fileNm1 = LncUtil.filterFileNm(fileNm1);
fileNm1=new String(fileNm1.getBytes("KSC5601"), "8859_1");
/*
*/
response.setHeader("Content-Disposition", "attachment; filename="+fileNm1+".xls");
response.setHeader("Content-Description", "JSP Generated Data");
response.setContentType("application/vnd.ms-excel"); 





%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
td, th {
	border: 1px solid #d9d9d9;
		mso-number-format:'@';
}

tr {
	mso-height-source: auto;
	mso-ruby-visibility: none;
}

col {
	mso-width-source: auto;
	mso-ruby-visibility: none;
}

br {
	mso-data-placement: same-cell;
}

ruby {
	ruby-align: left;
}
</style>

</head>
<body>
<c:set var="colcnt" value="3"/>
	<table class="w100 tb01">
			<caption>${eduNm }</caption>
			<thead>
				<tr>
					<th rowspan="">구분</th>
					<th rowspan="">항목</th>
					<th rowspan="">답변</th>
					<th rowspan="">카운트</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${feedbackInfo.qtList}" var="o" varStatus="s">
					<c:set var="isFirstDiv" value="false" />
					<c:set var="rowCnt" value="0" />
					<c:if test="${o.qt.qtDiv ne feedbackInfo.qtList[s.index-1].qt.qtDiv }">
						<c:set var="isFirstDiv" value="true" />
					</c:if>
					<c:if test="${isFirstDiv }">
						<c:forEach begin="${s.index }" end="${fn:length(feedbackInfo.qtList)-1 }" var="i2">
							<c:if test="${o.qt.qtDiv eq feedbackInfo.qtList[i2].qt.qtDiv }">
								<c:set var="rowCnt" value="${rowCnt+fn:length(feedbackInfo.qtList[i2].chList) }" />
								<c:if test="${feedbackInfo.qtList[i2].qt.qtType == 0 }">
									<c:set var="rowCnt" value="${rowCnt+1 }" />
								</c:if>
							</c:if>
						</c:forEach>
					</c:if>

					<c:if test="${o.qt.qtType != 0 }">
						<c:forEach items="${o.chList }" var="o2" varStatus="s2">
							<c:set var="rowCnt2" value="0" />
							<c:if test="${o2.chIdx == 1 }">
								<c:set var="rowCnt2" value="${fn:length(o.chList) }" />
							</c:if>
							<tr>
								<%-- 
							 --%>
								<c:if test="${rowCnt > 0 }">
									<td rowspan="${rowCnt }">${o.qt.qtDiv}</td>
								</c:if>
								<c:if test="${rowCnt2 > 0 }">
									<td rowspan="${rowCnt2 }">${o.qt.question}</td>
								</c:if>
								<td>${o2.choice }</td>
								<td>${o2.asCnt }</td>
							</tr>
							<c:if test="${rowCnt>1 }">
								<c:set var="rowCnt" value="0" />
							</c:if>
						</c:forEach>
					</c:if>
					<c:if test="${o.qt.qtType == 0 }">
						<tr>
							<%-- 
							 --%>
							<c:if test="${rowCnt > 0 }">
								<td rowspan="${rowCnt }">${o.qt.qtDiv}</td>
							</c:if>
							<td>${o.qt.question}</td>
							<td><c:forEach items="${o.asList }" var="as">
							${as.answer }<br />
								</c:forEach></td>
							<td></td>
						</tr>
					</c:if>
				</c:forEach>
			</tbody>
		</table>
</body>
</html>