<%@page import="com.educare.edu.member.service.SessionUserInfoHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<div class="tableRespon">
 <table class="tb02">
                        <caption class="sound_only">커뮤니티 - 번호, 제목, 작성자, 첨부, 조회수, 작성일 순으로 정보를 제공합니다.</caption>
                        <colgroup>
                            <col style="width:5%;">
                            <col style="width:56%;">
                            <col style="width:10%;">
                            <col style="width:7%;">
                            <col style="width:10%;">
                            <col style="width:12%;">
                        </colgroup>
						<thead>
							<tr>
								<th scope="col">번호</th>
								<th scope="col">제목</th>
								<th scope="col">작성자</th>
								<th scope="col">첨부</th>
								<th scope="col">조회수</th>									
								<th scope="col">작성일</th>
							</tr>
						</thead>
						<tbody>
						   <tr>
								<td>3</td>
                                <td class="tl">좋은 강의</td>
								<td>홍길동</td>
								<td></td>
								<td>4895</td>
								<td>2021-05-21</td>
							</tr>
                            <tr>
								<td>2</td>
                                <td class="tl">추천</td>
								<td>홍길동</td>
								<td></td>
								<td>524</td>
								<td>2021-05-21</td>
							</tr>
                            <tr>
								<td>1</td>
                                <td class="tl">유익해요</td>
								<td>홍길동</td>
								<td></td>
								<td>55</td>
								<td>2021-05-21</td>
							</tr>
						</tbody>
					</table>
</div>
                    <div class="w100 tr">
                        <a href="javascript:;" class="btn02 btn_blue">작성</a>
                    </div>