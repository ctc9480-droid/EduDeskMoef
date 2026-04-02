<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.educare.edu.member.service.SessionUserInfoHelper"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<div id="content" class="pd20">
	<div class="sub_txt oh">
		<span><img src="${utcp.ctxPath}/resources/user/image/icon/icon_subtitle.png" alt="서브타이틀 아이콘">컨설팅 신청서</span>
	</div>
	<div class="listWrap cst pdt0">
		<div id="viewDiv">
			<!--// tab_con1 //-->
			<div class="cstRgsWrap">
				<form name="" id="form-cr" method="post">
					<input type="hidden" name="crIdx" value="${crInfo.crIdx }" />
					<div class="cs_w">
						<p class="cs_w_tit tl">기본정보</p>
						<div class="table_layout">
							<table class="cs_w_table tb04">
								<caption class="sound_only">안전관리 컨설팅 기본정보 신청폼</caption>
								<colgroup>
									<col span="1" style="width: 20%;">
									<col span="1" style="width: 30%;">
									<col span="1" style="width: 20%;">
									<col span="1" style="width: 30%;">
								</colgroup>
								<tbody>
									<tr>
										<th scope="row">기관명</th>
										<td>${ crInfo.crCompanyNm }</td>
										<th scope="row">설립유형</th>
										<td><select id="cr_center_type" name="crCenterType">
												<option value="">선택하세요.</option>
												<option value="국공립">국공립</option>
												<option value="사회복지법인">사회복지법인</option>
												<option value="법인.단체등">법인.단체등</option>
												<option value="민간">민간</option>
												<option value="가정">가정</option>
												<option value="협동">협동</option>
												<option value="직장">직장</option>
										</select></td>
									</tr>
									<tr>
										<th scope="row">설립일</th>
										<td><input type="text" id="cr_open_date" name="crOpenDate" value="${ crInfo.crOpenDate }" class="datepicker" readonly="readonly" /></td>
										<th scope="row">휴대폰번호 ()</th>
										<td><input type="hidden" name="crTel" />
											<ul class="mb_tel cf">
												<li>
													<select id="cr_tel1" name="cstWr8">
														<option value="02">02</option>
														<option value="031">031</option>
														<option value="032">032</option>
														<option value="033">033</option>
														<option value="041">041</option>
														<option value="042">042</option>
														<option value="043">043</option>
														<option value="044">044</option>
														<option value="051">051</option>
														<option value="052">052</option>
														<option value="053">053</option>
														<option value="054">054</option>
														<option value="055">055</option>
														<option value="061">061</option>
														<option value="062">062</option>
														<option value="063">063</option>
														<option value="064">064</option>
														<option value="070">070</option>
														<option value="010">010</option>
														<option value="011">011</option>
														<option value="016">016</option>
														<option value="017">017</option>
														<option value="018">018</option>
														<option value="019">019</option>
													</select>
												</li>
												<li class="hif">-</li>
												<li>
													<input type="text" required="" id="cr_tel2" value="" maxlength="4" onkeyup="this.value=this.value.replace(/[^0-9]/g,'');">
												</li>
												<li class="hif">-</li>
												<li>
													<input type="text" required="" id="cr_tel3" value="" maxlength="4" onkeyup="this.value=this.value.replace(/[^0-9]/g,'');">
												</li>
											</ul>
										</td>
									</tr>
									<tr>
										<th scope="row">이메일</th>
										<td><input type="text" required="" id="cr_email" name="crEmail" value="${ crInfo.crEmail }" placeholder="ex) abc@naver.com" /></td>
										<th scope="row">휴대번호</th>
										<td>
										<input type="hidden" name="crPhone"/>
										<ul class="mb_tel cf">
												<li>
													<select id="cr_phone1" name="cstWr12">
														<option value="010">010</option>
														<option value="011">011</option>
														<option value="016">016</option>
														<option value="017">017</option>
														<option value="018">018</option>
														<option value="019">019</option>
													</select>
												</li>
												<li class="hif">-</li>
												<li>
													<input type="text" required="" id="cr_phone2" name="cstWr13" value="" maxlength="4" onkeyup="this.value=this.value.replace(/[^0-9]/g,'');">
												</li>
												<li class="hif">-</li>
												<li>
													<input type="text" required="" id="cr_phone3" name="cstWr14" value="" maxlength="4" onkeyup="this.value=this.value.replace(/[^0-9]/g,'');">
												</li>
											</ul></td>
									</tr>
									<tr>
										<th scope="row">정원</th>
										<td><select required="" id="cr_total_num" name="crTotalNum" title="정원 15명 이하부터 300명 이하까지 선택하기">
												<option value="">선택</option>
												<option value="15명 이하">15명 이하</option>
												<option value="20명 이하">20명 이하</option>
												<option value="25명 이하">25명 이하</option>
												<option value="30명 이하">30명 이하</option>
												<option value="35명 이하">35명 이하</option>
												<option value="40명 이하">40명 이하</option>
												<option value="45명 이하">45명 이하</option>
												<option value="50명 이하">50명 이하</option>
												<option value="55명 이하">55명 이하</option>
												<option value="60명 이하">60명 이하</option>
												<option value="65명 이하">65명 이하</option>
												<option value="70명 이하">70명 이하</option>
												<option value="75명 이하">75명 이하</option>
												<option value="80명 이하">80명 이하</option>
												<option value="85명 이하">85명 이하</option>
												<option value="90명 이하">90명 이하</option>
												<option value="95명 이하">95명 이하</option>
												<option value="100명 이하">100명 이하</option>
												<option value="105명 이하">105명 이하</option>
												<option value="110명 이하">110명 이하</option>
												<option value="115명 이하">115명 이하</option>
												<option value="120명 이하">120명 이하</option>
												<option value="125명 이하">125명 이하</option>
												<option value="130명 이하">130명 이하</option>
												<option value="135명 이하">135명 이하</option>
												<option value="140명 이하">140명 이하</option>
												<option value="145명 이하">145명 이하</option>
												<option value="150명 이하">150명 이하</option>
												<option value="155명 이하">155명 이하</option>
												<option value="160명 이하">160명 이하</option>
												<option value="165명 이하">165명 이하</option>
												<option value="170명 이하">170명 이하</option>
												<option value="175명 이하">175명 이하</option>
												<option value="180명 이하">180명 이하</option>
												<option value="185명 이하">185명 이하</option>
												<option value="190명 이하">190명 이하</option>
												<option value="195명 이하">195명 이하</option>
												<option value="200명 이하">200명 이하</option>
												<option value="205명 이하">205명 이하</option>
												<option value="210명 이하">210명 이하</option>
												<option value="215명 이하">215명 이하</option>
												<option value="220명 이하">220명 이하</option>
												<option value="225명 이하">225명 이하</option>
												<option value="230명 이하">230명 이하</option>
												<option value="235명 이하">235명 이하</option>
												<option value="240명 이하">240명 이하</option>
												<option value="245명 이하">245명 이하</option>
												<option value="250명 이하">250명 이하</option>
												<option value="255명 이하">255명 이하</option>
												<option value="260명 이하">260명 이하</option>
												<option value="265명 이하">265명 이하</option>
												<option value="270명 이하">270명 이하</option>
												<option value="275명 이하">275명 이하</option>
												<option value="280명 이하">280명 이하</option>
												<option value="285명 이하">285명 이하</option>
												<option value="290명 이하">290명 이하</option>
												<option value="295명 이하">295명 이하</option>
												<option value="300명 이하">300명 이하</option>
										</select></td>
										<th scope="row">현원</th>
										<td><select required="" id="cr_cur_num" name="crCurNum" title="현원 15명이하 부터 300명 이하까지 선택하기">
												<option value="">선택</option>
												<option value="15명 이하">15명 이하</option>
												<option value="20명 이하">20명 이하</option>
												<option value="25명 이하">25명 이하</option>
												<option value="30명 이하">30명 이하</option>
												<option value="35명 이하">35명 이하</option>
												<option value="40명 이하">40명 이하</option>
												<option value="45명 이하">45명 이하</option>
												<option value="50명 이하">50명 이하</option>
												<option value="55명 이하">55명 이하</option>
												<option value="60명 이하">60명 이하</option>
												<option value="65명 이하">65명 이하</option>
												<option value="70명 이하">70명 이하</option>
												<option value="75명 이하">75명 이하</option>
												<option value="80명 이하">80명 이하</option>
												<option value="85명 이하">85명 이하</option>
												<option value="90명 이하">90명 이하</option>
												<option value="95명 이하">95명 이하</option>
												<option value="100명 이하">100명 이하</option>
												<option value="105명 이하">105명 이하</option>
												<option value="110명 이하">110명 이하</option>
												<option value="115명 이하">115명 이하</option>
												<option value="120명 이하">120명 이하</option>
												<option value="125명 이하">125명 이하</option>
												<option value="130명 이하">130명 이하</option>
												<option value="135명 이하">135명 이하</option>
												<option value="140명 이하">140명 이하</option>
												<option value="145명 이하">145명 이하</option>
												<option value="150명 이하">150명 이하</option>
												<option value="155명 이하">155명 이하</option>
												<option value="160명 이하">160명 이하</option>
												<option value="165명 이하">165명 이하</option>
												<option value="170명 이하">170명 이하</option>
												<option value="175명 이하">175명 이하</option>
												<option value="180명 이하">180명 이하</option>
												<option value="185명 이하">185명 이하</option>
												<option value="190명 이하">190명 이하</option>
												<option value="195명 이하">195명 이하</option>
												<option value="200명 이하">200명 이하</option>
												<option value="205명 이하">205명 이하</option>
												<option value="210명 이하">210명 이하</option>
												<option value="215명 이하">215명 이하</option>
												<option value="220명 이하">220명 이하</option>
												<option value="225명 이하">225명 이하</option>
												<option value="230명 이하">230명 이하</option>
												<option value="235명 이하">235명 이하</option>
												<option value="240명 이하">240명 이하</option>
												<option value="245명 이하">245명 이하</option>
												<option value="250명 이하">250명 이하</option>
												<option value="255명 이하">255명 이하</option>
												<option value="260명 이하">260명 이하</option>
												<option value="265명 이하">265명 이하</option>
												<option value="270명 이하">270명 이하</option>
												<option value="275명 이하">275명 이하</option>
												<option value="280명 이하">280명 이하</option>
												<option value="285명 이하">285명 이하</option>
												<option value="290명 이하">290명 이하</option>
												<option value="295명 이하">295명 이하</option>
												<option value="300명 이하">300명 이하</option>
										</select></td>
									</tr>
									<tr>
										<th scope="row">주소</th>
										<td colspan="3"><input type="text" name="crAddr" value="${ crInfo.crAddr }" /></td>
									</tr>
								</tbody>
							</table>
						</div>
						<!--// table_layout //-->
					</div>
					<!--// cs_w //-->

					<div class="cs_w">
						<p class="cs_w_tit tl">어린이집 현황</p>
						<div class="table_layout">
							<table class="cs_w_table tb04">
								<caption class="sound_only">안전관리 컨설팅 어린이집 현황 신청폼</caption>
								<colgroup>
									<col style="width: 20%;" />
									<col style="width: 30%;" />
									<col style="width: 20%;" />
									<col style="width: 30%;" />
								</colgroup>
								<tbody>
									<tr>
										<th scope="row">어린이 통학차량</th>
										<td><select required="" id="cr_bus_yn" name="crBusYn" class="scbus"  title="어린이 통학차량 운영여부 선택하기">
												<option value="">선택</option>
												<option value="운영">운영</option>
												<option value="미운영">미운영</option>
										</select></td>
										<th scope="row">차량 수</th>
										<td><select id="cr_bus_num" name="crBusNum" class="scbusNum" title="차량수 1대부터 5대까지 중 선택하기">
												<option value="">선택</option>
												<option value="1대">1대</option>
												<option value="2대">2대</option>
												<option value="3대">3대</option>
												<option value="4대">4대</option>
												<option value="5대">5대</option>
										</select></td>
									</tr>
									<tr>
										<th scope="row">건축(준공)년도</th>
										<td><select id="cr_build_year" name="crBuildYear" title="건축(준공)년도 선택하기">
												<option value="">선택</option>
												<fmt:formatDate value="<%=new java.util.Date()%>" pattern="yyyy" var="endYear" />
												<c:forEach var="o" varStatus="s" begin="1950" end="${endYear }">
													<option value="${endYear-s.count+1 }">${endYear-s.count+1 }년</option>
												</c:forEach>
										</select></td>
										<th scope="row">건물유형</th>
										<td><select id="cr_build_type" name="crBuildType" title="건물유형 단독,공용,아파트 중 선택하기">
												<option value="">선택</option>
												<option value="단독">단독</option>
												<option value="공용">공용</option>
												<option value="아파트">아파트</option>
										</select></td>
									</tr>
									<tr>
										<th scope="row">건물층수(지하층 포함)</th>
										<td><select required="" id="cr_build_floor" name="crBuildFloor" title="건물층수(지하층 포함)1층부터 8층까지 선택하기">
												<option value="">선택</option>
												<option value="1층">1층</option>
												<option value="2층">2층</option>
												<option value="3층">3층</option>
												<option value="4층">4층</option>
												<option value="5층">5층</option>
												<option value="6층">6층</option>
										</select></td>
										<th scope="row">건물전용면적(연면적)</th>
										<td><select id="cr_build_area" name="crBuildArea" title="건물전용면적 선택하기">
												<option value="">선택</option>
												<option value="50 ㎡">50 ㎡</option>
												<option value="100 ㎡">100 ㎡</option>
												<option value="150 ㎡">150 ㎡</option>
												<option value="200 ㎡">200 ㎡</option>
												<option value="250 ㎡">250 ㎡</option>
												<option value="300 ㎡">300 ㎡</option>
												<option value="350 ㎡">350 ㎡</option>
												<option value="400 ㎡">400 ㎡</option>
												<option value="450 ㎡">450 ㎡</option>
												<option value="500 ㎡">500 ㎡</option>
												<option value="550 ㎡">550 ㎡</option>
												<option value="600 ㎡">600 ㎡</option>
												<option value="650 ㎡">650 ㎡</option>
												<option value="700 ㎡">700 ㎡</option>
												<option value="750 ㎡">750 ㎡</option>
												<option value="800 ㎡">800 ㎡</option>
												<option value="850 ㎡">850 ㎡</option>
												<option value="900 ㎡">900 ㎡</option>
												<option value="950 ㎡">950 ㎡</option>
												<option value="1000 ㎡">1000 ㎡</option>
												<option value="1050 ㎡">1050 ㎡</option>
												<option value="1100 ㎡">1100 ㎡</option>
												<option value="1150 ㎡">1150 ㎡</option>
												<option value="1200 ㎡">1200 ㎡</option>
												<option value="1250 ㎡">1250 ㎡</option>
												<option value="1300 ㎡">1300 ㎡</option>
												<option value="1350 ㎡">1350 ㎡</option>
												<option value="1400 ㎡">1400 ㎡</option>
												<option value="1450 ㎡">1450 ㎡</option>
												<option value="1500 ㎡">1500 ㎡</option>
												<option value="1550 ㎡">1550 ㎡</option>
												<option value="1600 ㎡">1600 ㎡</option>
												<option value="1650 ㎡">1650 ㎡</option>
												<option value="1700 ㎡">1700 ㎡</option>
												<option value="1750 ㎡">1750 ㎡</option>
												<option value="1800 ㎡">1800 ㎡</option>
												<option value="1850 ㎡">1850 ㎡</option>
												<option value="1900 ㎡">1900 ㎡</option>
												<option value="1950 ㎡">1950 ㎡</option>
												<option value="2000 ㎡">2000 ㎡</option>
										</select></td>
									</tr>
									<tr>
										<th scope="row">비상재해대피시설</th>
										<td><select required="" id="cr_evac_facility" name="crEvacFacility" title="비상재해대피시설 종류 선택하기">
												<option value="">선택</option>
												<option value="없음">없음</option>
												<option value="비상계단">비상계단</option>
												<option value="미끄럼틀">미끄럼틀</option>
												<option value="간이스프링쿨러">간이스프링쿨러</option>
												<option value="스프링쿨러 및 자동화재탐지설비">스프링쿨러 및 자동화재탐지설비</option>
												<option value="기타">기타</option>
										</select></td>
										<th scope="row">최근 평가인증일</th>
										<td><input type="text" required id="last_cert_date" name="crLastCertDate" value="${crInfo.crLastCertDate }" class="datepicker" /></td>
									</tr>
								</tbody>
							</table>
						</div>
						<!--// table_layout //-->
					</div>
					<!--// cs_w //-->

					<!--// 220318 hy
					<div class="cs_w">
						<p class="cs_w_tit tl">남기실 말씀</p>
						<div class="table_layout">
							<table class="cs_w_table tb04">
								<caption class="sound_only">안전관리 컨설팅 신청폼 남기실 말씀</caption>
								<colgroup>
									<col span="1" style="width: 20%;">
									<col span="1" style="width: 30%;">
									<col span="1" style="width: 20%;">
									<col span="1" style="width: 30%;">
								</colgroup>
								<tbody>
									<tr>
										<th scope="row"><label for="cstWr47">남기실 말씀</label></th>
										<td colspan="3">
										<textarea name="crMemo" rows="" cols="">${ crInfo.crMemo }</textarea>
										</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
					//-->

					<div class="cs_w">
						<p class="cs_w_tit tl">상담희망시간</p>
						<div class="table_layout">
							<table class="cs_w_table tb04">
								<caption class="sound_only">안전관리 컨설팅 희망시간 선택</caption>
								<colgroup>
									<col style="width: 20%;" />
									<col style="width: 30%;" />
									<col style="width: 20%;" />
									<col style="width: 30%;" />
								</colgroup>
								<tbody>
									<tr>
										<th scope="row"><label for="crTime11">상담희망시간</label></th>
                                        <td colspan="3">
                                            <ul class="cstTime cf">
                                                <li>
                                                <input type="hidden" name="crCalltimeBegin"/>
                                                 <select id="cr_calltime_begin_hh" title="시를 선택 할 수 있습니다.">
                                                     <option value="09">09</option>
                                                     <option value="10">10</option>
                                                     <option value="11">11</option>
                                                     <option value="12">12</option>
                                                     <option value="13">13</option>
                                                     <option value="14">14</option>
                                                     <option value="15">15</option>
                                                     <option value="16">16</option>
                                                     <option value="17">17</option>
                                                     <option value="18">18</option>
                                                 </select>
                                                </li>
                                                <li class="hif">시</li>
                                                <!--// 분 선택
                                                <li>
                                                 <select id="" name="" title="분을 선택 할 수 있습니다.">
                                                     <option value="00">00</option>
                                                     <option value="10">10</option>
                                                     <option value="20">20</option>
                                                     <option value="30">30</option>
                                                     <option value="40">40</option>
                                                     <option value="50">50</option>
                                                 </select>
                                                </li>
                                                 //-->
                                                <li class="hif mgl5 mgr5">~</li>
                                                <li>
                                                <input type="hidden" name="crCalltimeEnd"/>
                                                 <select id="cr_calltime_end_hh" title="시를 선택 할 수 있습니다.">
                                                     <option value="09">09</option>
                                                     <option value="10">10</option>
                                                     <option value="11">11</option>
                                                     <option value="12">12</option>
                                                     <option value="13">13</option>
                                                     <option value="14">14</option>
                                                     <option value="15">15</option>
                                                     <option value="16">16</option>
                                                     <option value="17">17</option>
                                                     <option value="18">18</option>
                                                 </select>
                                                </li>
                                                <li class="hif">시</li>
                                                <!--// 분 선택
                                                <li>
                                                 <select id="" name="" title="분을 선택 할 수 있습니다.">
                                                     <option value="00">00</option>
                                                     <option value="10">10</option>
                                                     <option value="20">20</option>
                                                     <option value="30">30</option>
                                                     <option value="40">40</option>
                                                     <option value="50">50</option>
                                                 </select>
                                                </li>
                                                 //-->
                                            </ul>
                                        </td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
					<div class="cs_w" style="display: none;">
						<p class="cs_w_tit tl">온라인컨설팅 수행 후 방문컨설팅 희망</p>
						<div class="table_layout">
							<table class="cs_w_table tb04">
								<caption class="sound_only">방문컨설팅 희망 동의여부</caption>
								<colgroup>
									<col style="width: 20%;" />
									<col style="width: 30%;" />
									<col style="width: 20%;" />
									<col style="width: 30%;" />
								</colgroup>
								<tbody>
									<tr>
										<th scope="row">희망 여부</th>
										<td colspan="3" class="rdo">
										 <input type="radio" id="cr_visit_agree1" name="crVisitAgree" value="희망"   />
                                                <label for="cr_visit_agree1" class="mgr15">희망</label>
                                                <input type="radio" id="cr_visit_agree2" name="crVisitAgree" value="비희망"  />
                                                <label for="cr_visit_agree2">비희망</label>
										</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
					<div class="cs_w">
						<p class="cs_w_tit tl">어린이집 안전관리 컨설팅 신청 접수 동의</p>
						<div class="table_layout">
							<table class="cs_w_table tb04">
								<caption class="sound_only">안전관리 컨설팅 신청폼 동의여부</caption>
								<colgroup>
									<col style="width: 20%;" />
									<col style="width: 30%;" />
									<col style="width: 20%;" />
									<col style="width: 30%;" />
								</colgroup>
								<tbody>
									<tr>
										<th scope="row">동의 여부</th>
										<td colspan="3" class="rdo">
                                                <input type="radio" id="cr_reg_agree1" name="crRegAgree" value="동의"   />
                                                <label for="cr_reg_agree1" class="mgr15">동의</label>
                                                <input type="radio" id="cr_reg_agree2" name="crRegAgree" value="미동의"  />
                                                <label for="cr_reg_agree2">미동의</label>
										</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
					
					<div class="cs_w">
						<p class="cs_w_tit tl">컨설팅 실시여부 제3자(지자체 등) 제공 동의</p>
						<div class="table_layout">
							<table class="cs_w_table tb04">
								<caption class="sound_only">컨설팅 실시여부 제3자(지자체 등) 제공 동의 여부</caption>
								<colgroup>
									<col style="width: 20%;" />
									<col style="width: 30%;" />
									<col style="width: 20%;" />
									<col style="width: 30%;" />
								</colgroup>
								<tbody>
									<tr>
										<th scope="row">동의 여부</th>
										<td colspan="3" class="rdo">
										 <input type="radio" id="cr_visit_agree1" name="crThirdAgree" value="동의"   />
                                                <label for="cr_visit_agree1" class="mgr15">동의</label>
                                                <input type="radio" id="cr_third_agree2" name="crThirdAgree" value="미동의"  />
                                                <label for="cr_third_agree2">미동의</label>
										</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</form>
			</div>
			<!--// cs_cont //-->
			<!--// cstRgsWrap //-->
			<button type="button" class="btn01 btn_orange mgt20" onclick="fn_saveCR()";>저장하기</button>
		</div>
		<!--// tab_con1 //-->
	</div>
	<!--//viewDiv//-->
</div>
<!--//listWrap//-->

<script>
	// pickadate //
	var $input = $('.datepicker').pickadate(
			{
				monthsFull : [ '1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월',
						'9월', '10월', '11월', '12월' ],
				monthsShort : [ '1', '2', '3', '4', '5', '6', '7', '8', '9',
						'10', '11', '12' ],
				weekdaysFull : [ '일요일', '월요일', '화요일', '수요일', '목요일', '금요일',
						'토요일' ],
				weekdaysShort : [ '일', '월', '화', '수', '목', '금', '토' ],
				format : 'yyyy-mm-dd',
				formatSubmit : 'yyyy-mm-dd',
				today : "오늘",
				clear : "지우기",
				close : '닫기',
				container : '#content',
				labelMonthNext : '다음달 넘어가기',
				labelMonthPrev : '이전달 넘어가기',
				labelMonthSelect : '월 선택',
				labelYearSelect : '년도 선택',
				selectYears : 200,
				selectMonths : true
			//min:true
			});
	// pickadate //
	$('#cr_center_type').val('${crInfo.crCenterType}');
	var cr_tel = '${ crInfo.crTel }'.split('-');
	$('#cr_tel1').val(cr_tel[0]);
	$('#cr_tel2').val(cr_tel[1]);
	$('#cr_tel3').val(cr_tel[2]);
	var cr_phone = '${ crInfo.crPhone }'.split('-');
	$('#cr_phone1').val(cr_phone[0]);
	$('#cr_phone2').val(cr_phone[1]);
	$('#cr_phone3').val(cr_phone[2]);
	$('#cr_total_num').val('${crInfo.crTotalNum}');
	$('#cr_cur_num').val('${crInfo.crCurNum}');
	$('#cr_bus_yn').val('${crInfo.crBusYn}');
	$('#cr_bus_num').val('${crInfo.crBusNum}');
	$('#cr_build_year').val('${crInfo.crBuildYear}');
	$('#cr_build_type').val('${crInfo.crBuildType}');
	$('#cr_build_floor').val('${crInfo.crBuildFloor}');
	$('#cr_build_area').val('${crInfo.crBuildArea}');
	$('#cr_evac_facility').val('${crInfo.crEvacFacility}');
	$('input:radio[name=crVisitAgree][value=${empty crInfo.crVisitAgree?"비희망":crInfo.crVisitAgree}]').prop('checked',true);		
	$('input:radio[name=crRegAgree][value=${not empty crInfo.crRegAgree?crInfo.crRegAgree:"동의"}]').prop('checked',true);
	$('input:radio[name=crThirdAgree][value=${empty crInfo.crThirdAgree?"미동의":crInfo.crThirdAgree}]').prop('checked',true);		
	$('#cr_calltime_begin_hh').val('${fn:substring(crInfo.crCalltimeBegin,0,2)}');
	$('#cr_calltime_end_hh').val('${fn:substring(crInfo.crCalltimeEnd,0,2)}');
	toggleDisabledCrBusNum();
	
	$('#cr_bus_yn').change(function(){
		toggleDisabledCrBusNum();
	});
	
	function toggleDisabledCrBusNum(){
		if($('#cr_bus_yn').val()=='운영'){
			$('#cr_bus_num').prop('disabled',false);
		}else{
			$('#cr_bus_num').prop('disabled',true);
			$('#cr_bus_num').val('');
		}
	}
	
	function fn_saveCR() {
		var crTel = $('#cr_tel1').val()+'-'+$('#cr_tel2').val()+'-'+$('#cr_tel3').val()
		var crPhone = $('#cr_phone1').val()+'-'+$('#cr_phone2').val()+'-'+$('#cr_phone3').val()
		$('input[name=crTel]').val(crTel);
		$('input[name=crPhone]').val(crPhone);
		$('input[name=crCalltimeBegin]').val($('#cr_calltime_begin_hh').val()+'00');
		$('input[name=crCalltimeEnd]').val($('#cr_calltime_end_hh').val()+'00');
		var data = $('#form-cr').serializeArray();
		$.ajax({
			type : 'post',
			data : data,
			url : 'myEduRgsProc.json',
			success : function(r) {
				if (r.result == 1) {
					alert('수정되었습니다.');
					location.reload();
				} else {
					alert('알 수 없는 오류가 발생하였습니다. 관리자에게 문의 하세요');
				}
			}
		});
	}
</script>