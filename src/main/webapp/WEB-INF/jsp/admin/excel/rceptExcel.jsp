<%@page import="org.codehaus.jackson.type.TypeReference"%>
<%@page import="org.codehaus.jackson.map.ObjectMapper"%>
<%@page import="com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException"%>
<%@page import="com.educare.edu.comn.model.UserLang"%>
<%@page import="com.educare.edu.comn.model.UserCareer"%>
<%@page import="com.educare.edu.comn.model.UserCertif"%>
<%@page import="com.educare.edu.comn.model.UserComp"%>
<%@page import="com.educare.edu.comn.model.UserSchool"%>
<%@page import="com.educare.edu.comn.vo.ResultVO"%>
<%@page import="com.educare.edu.education.service.EduService"%>
<%@page import="org.springframework.beans.BeanUtils"%>
<%@page import="com.educare.util.LncUtil"%>
<%@page import="com.fasterxml.jackson.core.JsonProcessingException"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.educare.edu.comn.mapper.LectureEtcIemDataMapper"%>
<%@page import="com.educare.edu.comn.vo.LectureEtcIemDataVO"%>
<%@page import="com.educare.edu.comn.mapper.LectureEtcIemMapper"%>
<%@page import="com.educare.edu.comn.vo.LectureEtcIemVO"%>
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
ServletContext conext = session.getServletContext();
WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(conext);
EduService eduService = (EduService)wac.getBean("EduService");
EduMapper eduMapper = (EduMapper)wac.getBean("EduMapper");

int eduSeq = Integer.parseInt(request.getParameter("eduSeq"));

Lecture lctre = eduMapper.selectLctreByPk(eduSeq);

ResultVO result = eduService.getLectureRceptList(0,0,eduSeq,0,"");
Map<String,Object> rstData = (Map<String,Object>)result.getData();
List<LectureEtcIemVO> etcIemList = (List<LectureEtcIemVO>)rstData.get("etcIemList");

pageContext.setAttribute("lctre", lctre);
pageContext.setAttribute("dataList", rstData.get("dataList"));
pageContext.setAttribute("eduNm", lctre.getEduNm());
pageContext.setAttribute("etcIemList", etcIemList);


String fileNm1 = ""+lctre.getEduNm()+" ";
fileNm1 = fileNm1.replaceAll(" ", "_");
fileNm1 = fileNm1.replaceAll(",", "_");
fileNm1 = "[신청현황] "+LncUtil.filterFileNm(fileNm1);
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
 table {
            width: 100%; /* 테이블 전체 너비를 100%로 설정 */
            table-layout: auto; /* 셀 너비를 콘텐츠 길이에 맞추기 위해 자동 레이아웃 설정 */
        }
        td {
            white-space: pre-wrap; /* 줄바꿈을 유지하고, 긴 텍스트는 한 줄로 표시 */
            max-width: 1px; /* 너비를 최소로 설정하여 콘텐츠에 맞게 늘어남 */
        }

td, th {
	border: 1px solid #d9d9d9;
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
	<table class="">
		<caption>${eduNm }</caption>
		<thead bgcolor="#f7f8fa">
			<tr>
				<th rowspan="">연번</th>
				<th>아이디</th>
				<th>과정명</th>
				<th>소속</th>
				<th>직급</th>
				<th>성명</th>
				<th>생년월일</th>
				<th>성별</th>
				<th>사무실전화</th>
				<th>휴대폰</th>
				<th>버스탑승여부</th>
				<th>숙소이용여부</th>
				<th>이메일</th>
				<th>교육신청일</th>
				<th>상태</th>
				<c:forEach items="${etcIemList }" var="o">
				<th>${o.etcIemNm }</th>
				</c:forEach>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${dataList}" var="o" varStatus="s">
			<c:set var="etcIemJson" value="${o.etcIemJson }"/>
			<%
			ObjectMapper om = new ObjectMapper();
			
			String etcIemJson = (String)pageContext.getAttribute("etcIemJson");
			System.out.println(etcIemJson);
			List<LectureEtcIemVO> etcList = new ArrayList<LectureEtcIemVO>();
			Map<String, Object> etcIemMap = om.readValue(etcIemJson, Map.class);
			List<Map<String, Object>> etcIemList2 = (List<Map<String, Object>>) etcIemMap.get("etcIemList");
			for(Map<String, Object> etcIem : etcIemList2){
				LectureEtcIemVO leiVO = new LectureEtcIemVO();
				leiVO.setEtcIemNm(etcIem.get("etcIemNm").toString());
				leiVO.setEtcIemSeq(LncUtil.nvlInt(etcIem.get("etcIemSeq")));
				leiVO.setEtcIemEx(LncUtil.replaceNull(etcIem.get("etcIemEx")));
				leiVO.setDataInputTy(etcIem.get("dataInputTy").toString());
				leiVO.setEssntlInputYn(LncUtil.replaceNull(etcIem.get("essntlInputYn")));
				leiVO.setUseYn(LncUtil.replaceNull(etcIem.get("useYn")));
				leiVO.setEtcIemData(LncUtil.replaceNull(etcIem.get("etcIemData")));
				etcList.add(leiVO);
			}
			pageContext.setAttribute("etcList", etcList);
			%>
			
			<tr>
				<td >${s.count }</td>
				<td>${o.loginId }</td>
				<td>${o.eduNm }</td>
				<td>${o.userOrgNm }</td>
				<td>${o.userGradeNm }</td>
				<td>${o.userNm }</td>
				<td>${o.birth }</td>
				<td>${o.addMfTypeNm }</td>
				<td>${o.tel }</td>
				<td>${o.mobile }</td>
				<td>
					<c:choose>
						<c:when test="${o.useTransYn == '0.0'}">미이용</c:when>
						<c:when test="${o.useTransYn == '1.0'}">왕복</c:when>
						<c:when test="${o.useTransYn == '2.0'}">입교</c:when>
						<c:when test="${o.useTransYn == '3.0'}">퇴교</c:when>
					</c:choose>
				</td>
				<td>${o.dormiCapaYn }</td>				
				<td>${o.email }</td>
				<td>${utcp.convDateToStr(o.regDe,'yyyy.MM.dd HH:mm') }</td>
				<td>${o.addStateNm }</td>
				<c:forEach items="${etcList }" var="o2">
					<%-- <td rowspan="${rowCnt }">${o2.etcIemData }</td> --%>
					<td>${o2.etcIemData }</td>
				</c:forEach>
				
			</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>