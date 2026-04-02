<%@page import="com.educare.edu.member.service.SessionUserInfoHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>     
<div class="tableRespon">
<table class="tb07" >
					<thead >
						<tr>
							<th class="vm">차시</th>
							<c:if test="${lctre.lctreType ne 3 }">
							<th class="vm">날짜</th>
							<th class="vm">시간</th>
							</c:if>
							<th class="vm">내용</th>
							<th class="vm">강사</th>
							<th class="vm">형태</th>
<!-- 							<th class="vm">입장/비밀번호</th> -->
<!-- 							<th class="vm">완료</th> -->
<%-- 							<c:if test="${lctre.lctreType == 0 or lctre.lctreType == 2}"> --%>
<!-- 							<th>출석</th> -->
<%-- 							</c:if> --%>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${timeList }" var="o" varStatus="s">
							<tr v-for="o in timeList">
								<td>
								<c:choose>
								<c:when test="${lctre.lctreType == 3 }">
								${o.timeSeq }
								</c:when>
								<c:otherwise>
								${s.count }
								</c:otherwise>
								</c:choose>
								</td>
								<c:if test="${lctre.lctreType ne 3 }">
								<td>${utcp.convDateToStr(utcp.convStrToDate(o.eduDt,'yyyyMMdd'),'yyyy.MM.dd (E)')}</td>
								<td>${fn:substring(o.startTm,0,2)}:${fn:substring(o.startTm,2,4)}~${fn:substring(o.endTm,0,2)}:${fn:substring(o.endTm,2,4)}</td>
								</c:if>
								<td class="tl">${o.description }</td>
								<td>
								<c:if test="${o.checkId > 0 }">
									${o.checkNm }
								</c:if>
								</td>
								<td>${o.classHowNm }</td>
<!-- 								<td> -->
<%-- 								<c:set var="now" value="<%=new java.util.Date()%>" /> --%>
<%-- 									<c:if test="${lctre.eduPeriodBegin <= utcp.convDateToStr(now,'yyyy-MM-dd') }">  --%>
<%-- 										<c:if test="${not empty stdnt}"> --%>
<%-- 											<c:if test="${o.classHow == 1 || o.classHow == 2 || o.classHow == 3}"> --%>
<%-- 											<a href="#none" onclick="fn_checkOnline(${o.eduSeq},${o.timeSeq})" class="btn04 btn_blue"> --%>
<!-- 											입장하기 -->
<!-- 											</a> -->
<%-- 											</c:if> --%>
<%-- 										</c:if> --%>
										
<%-- 										<c:if test="${o.classHow == 2 }"> --%>
<%-- 										<c:if test="${not empty o.urlPw }"> --%>
<%-- 										/ PW:${o.urlPw } --%>
<%-- 										</c:if> --%>
<%-- 										</c:if> --%>
										
<%-- 										<fmt:parseNumber var="movTime" value="${o.movTime }" /> --%>
<%-- 										<fmt:parseNumber var="movAllTime" value="${o.movAllTime }" /> --%>

<%-- 										<c:if test="${o.classHow==3 && o.historyCnt>0 && movTime>0 && movAllTime>0}"> --%>
<!-- 											<span> -->
<!-- 												(진도율 : -->
<%-- 												<c:choose> --%>
<%-- 												<c:when test="${(movAllTime-movTime)<2 }"> --%>
<!-- 												100% -->
<%-- 												</c:when> --%>
<%-- 												<c:otherwise> --%>
<%-- 												<fmt:formatNumber value="${movTime/movAllTime }" type="percent" pattern="0.0%" /> --%>
<%-- 												</c:otherwise> --%>
<%-- 												</c:choose> --%>
<!-- 												) -->
<!-- 											</span> -->
<%-- 										</c:if> --%>
<%-- 									</c:if> --%>
<%-- 									<c:if test="${o.classHow == 0}"> --%>
<%-- 										<c:if test="${o.roomSeq > 0 }"> --%>
<%-- 											${o.roomNm } --%>
<%-- 										</c:if> --%>
<%-- 									</c:if> --%>
<%-- 									</c:if> --%>
<!-- 									</td> -->
<%-- 								<td>${o.attCd }</td> --%>
<%-- 								<c:if test="${(lctre.lctreType == 0 or lctre.lctreType == 2) and o.gyosi == 1}"> --%>
<%-- 								<td rowspan="${o.rowCnt }"> --%>
<%-- 									<c:if test="${o.classHow != 3 }"> --%>
<%-- 									<c:forEach items="${rollbook.stdntList[0].stdntAttList}" var="o2"> --%>
<%-- 										<c:if test="${o2.eduDt eq o.eduDt }"> --%>
<%-- 											${o2.addAttStr } --%>
<%-- 											<br/>입실 : ${utcp.convDateToStr(o2.beginDe,'HH:mm:ss') }  --%>
<%-- 											<br/>퇴실 : ${utcp.convDateToStr(o2.endDe,'HH:mm:ss') } --%>
<%-- 										</c:if> --%>
<%-- 									</c:forEach> --%>
<%-- 									</c:if> --%>
<!-- 								</td> -->
<%-- 								</c:if> --%>
							</tr>
						</c:forEach>
						<c:if test="${empty timeList }">
							<tr>
								<td colspan="7">시간표가 생성되지 않았습니다.</td>
							</tr>
						</c:if>
					</tbody>
				</table>
</div>
