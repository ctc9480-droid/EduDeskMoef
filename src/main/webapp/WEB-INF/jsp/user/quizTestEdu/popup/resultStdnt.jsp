<%@page import="com.educare.edu.comn.model.Test"%>
<%@page import="com.educare.edu.quizTest.vo.QuizTestVO"%>
<%@page import="java.util.Map"%>
<%@page import="com.educare.edu.member.service.SessionUserInfoHelper"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<%@ page import="java.lang.Math"%>

<%if(!SessionUserInfoHelper.isAdmin()){ 
	Map<String,Object> data = (Map<String,Object>)request.getAttribute("data");
	Test test = (Test)data.get("test");
	if(test.getStatus() != 3){
		return;
	}
}%>

<div id="wrapper" class="cf">
	<section class="pd20 po_re" id="vm-testResultStdnt">
		<div class="po_re br5 bs_box cf" autocomplete="off">
			<!--// 시험결과 //-->
			<div class="testResult">
				<a href='javascript:window.close();' class="roomOut fr"><i class="fa-solid fa-x"></i></a>
				<div class="modal-content">
					<div class="modal-header">
						<p class="tit">{{userTest.userNm}} 시험지({{userTest.testNm}})</p>
					</div>
					<div class="modal-body">
						<div class="mgt0 mgb0 cf po_re">
							<!-- 
							<div class="titBox cf">
								<button type="button" class="arrowBtn">
									<i class="fa-solid fa-arrow-left"></i>
								</button>
								<button type="button" class="arrowBtn">
									<i class="fa-solid fa-arrow-right"></i>
								</button>
							</div>

							<div class="w100 cf tc po_ab">
								<table class="tb w200 fr btBlack bbBlack blBlack brBlack">
									<caption></caption>
									<colgroup>
										<col style="width: 50%;">
										<col style="width: 50%;">
									</colgroup>
									<thead>
										<tr>
											<th class="bbBlack brBlack bg_white">교육생</th>
											<th class="bbBlack bg_white">담당교사</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td class="pd20 bbBlack brBlack h90">&nbsp;</td>
											<td class="pd20 bbBlack h90">&nbsp;</td>
										</tr>
									</tbody>
								</table>
							</div>
 							-->
							<table class="tc w100 tb mgb20 blGray brGray">
								<caption class="sound_only">평가일시, 훈련생, 교과목, 훈련교사, 능력단위명, 평가시간/총배점, 능력단위 요소명, 평가유형/수준, 평가점수, 성취수준, 성취기준, 능력단위요소 순으로 정보를 제공합니다.</caption>
								<colgroup>
									<col style="width: 15%;">
									<col style="width: 35%;">
									<col style="width: 15%;">
									<col style="width: 35%;">
								</colgroup>

								<tbody>
									<tr>
										<th>평가일시</th>
										<td>{{userTest.testResultStartDe|fltDate2Str('YYYY.MM.DD HH:mm')}}</td>
										<th>교육생</th>
										<td>{{userTest.userNm}}</td>
									</tr>
									<!-- <tr>
										<th>교과목</th>
										<td>품질경영활동</td>
										<th>훈련교사</th>
										<td>김만기, 김현규, 한재기</td>
									</tr> -->
									<tr>
										<th>평가시간</th>
										<td></td>
										<th>총배점</th>
										<td>{{userTest.totMarks}}</td>
									</tr>
									<!-- <tr>
										<th>능력단위 요소명</th>
										<td>AMS Module_A형</td>
										<th>평가유형/수준</th>
										<td>서답형(20문항)수준</td>
									</tr> -->
									<tr>
										<th>평가점수</th>
										<td><strong class="fc_darkblue">{{userTest.totMarksGet}}</strong></td>
										<th>성취수준</th>
										<td>{{userTest.addGradeNm}}</td>
									</tr>
									<tr>
										<th>채점자</th>
										<td>{{userTest.checkNm }}</td>
										<th>채점일시</th>
										<td>
										<template v-if="userTest.checkNm">
										{{userTest.checkDe|fltDate2Str('YYYY.MM.DD HH:mm')}}
										</template>
										</td>
									</tr>
									<tr>
										<th>성취기준</th>
										<td colspan="3">
											<table class="tb w100 mgb0 blGray brGray">
												<caption>수준과 점수구간 순으로 정보를 제공합니다.</caption>
												<thead>
													<tr>
														<th>수준</th>
														<th>5수준</th>
														<th>4수준</th>
														<th>3수준</th>
														<th>2수준</th>
														<th>1수준</th>
													</tr>
												</thead>
												<tbody>
													<tr>
														<td>점수구간</td>
														<td>90점 이상</td>
														<td>80~89점</td>
														<td>70~79점</td>
														<td>60~69점</td>
														<td>60점 미만</td>
													</tr>
												</tbody>
											</table>
										</td>
									</tr>
									<tr>
										<th></th>
										<th colspan="3">문 항</th>
									</tr>
									<tr v-for="(o,i) in userQtList">
										<th>&nbsp;</th>
										<td colspan="3">
											<div class="testCont cf" style="">
												<div class="testQ tl cf">
													<!-- 
													<p>시험지 영역</p>
 -->
													<!--// 객관식 단일선택 (s) //-->
													<div class="testQlist" :class="{'answer':o.qt.marksGet > 0,'wanswer':o.qt.marksGet == 0}">
														<dl>
															<dt>
																{{o.qt.ord}}. 
																<span v-html="text2Html(o.qt.qstnStr)">
																</span>
																<span>(배점 {{o.qt.marks}}점)</span>
															</dt>
															<template v-if="o.qt.qstnTp == 1">
															<dd v-for="(o2,i2) in o.chList">
																<input type="radio" :name="'md_mt7t_ch_'+o.qt.ord" :id="'md_mt7t_ch_'+o.qt.ord+'_'+i2" v-model="o.qt.optnStr" :value="o2.chSeq" :disabled="userTest.testResultState==2" />
																<label :for="'md_mt7t_ch_'+i2"> {{noArr[i2]}} {{o2.chStr}} </label>
															</dd>
															</template>
															<template v-else-if="o.qt.qstnTp == 2">
															<dd v-for="(o2,i2) in o.chList">
																<input type="checkbox" :name="'md_mt7t_ch_'+o.qt.ord" :id="'md_mt7t_ch_'+o.qt.ord+'_'+i2" v-model="o.qt.optnArr" :value="o2.chSeq" :disabled="userTest.testResultState==2" />
																<label :for="'md_mt7t_ch_'+i2"> {{noArr[i2]}} {{o2.chStr}} </label>
															</dd>
															</template>
															<template v-else-if="o.qt.qstnTp == 3"> <textarea v-model="o.qt.fillBlank" class="ta_size"></textarea> </template>
															<template v-else-if="o.qt.qstnTp == 4"> <textarea v-model="o.qt.answer" class="ta_size"></textarea> </template>
														</dl>
													</div>
													<!--// 객관식 단일선택 (e) //-->

												</div>
												<!--// testQ //-->


												<div class="testA tl">
													<p>답안지 영역</p>

													<div class="tbWrap">
														<table class="tb">
															<caption class="blind">답안지 영역 - No, 답안 순으로 정보를 제공합니다.</caption>
															<colgroup>
																<col style="width: 15%;">
																<col style="width: 80%;">
															</colgroup>
															<thead>
																<tr>
																	<th>답안</th>
																	<th>
																	<template v-if="3 > o.qt.qstnTp "> 
																	<template v-for="(o3,i3) in o.chList"> <span class="" v-if="o3.chAnsYn == 'Y'">{{noArr[i3]}}</span>&nbsp; </template> 
																	</template>
																	<%--
																	<template v-else-if="o.qt.qstnTp == 3">
																	<span class="" >{{o.qt.fillBlankCo}}</span>
																	</template>
																	 --%>
																	<template v-else-if="o.qt.qstnTp == 4 || o.qt.qstnTp == 3">
																	{{o.qt.fillBlankCo}}
																	<%if(SessionUserInfoHelper.isAdmin()){ %>
                                                                                <input type="radio" :id="'tr1_1'+o.qt.ord" :name="'tr1'+o.qt.ord" :checked="o.qt.marksGet > 0" @click="setCorrectYn(o.qt,'Y')" :disabled="userTest.testResultState == 3">
                                                                                <label :for="'tr1_1'+o.qt.ord">
                                                                                    		정답
                                                                                </label>
                                                                                <input type="radio" :id="'tr1_2'+o.qt.ord" :name="'tr1'+o.qt.ord" :checked="o.qt.marksGet == 0" @click="setCorrectYn(o.qt,'N')" :disabled="userTest.testResultState == 3">
                                                                                <label :for="'tr1_2'+o.qt.ord">
                                                                                    		오답
                                                                                </label>    
                                                                                <input type="text" class="ip11" v-model="o.qt.marksGet2"  @input="filterNumericInput(o.qt)" :disabled="userTest.testResultState == 3"/>                                                                            
																	<%} %>
																	</template>
																	</th>
																</tr>
																<tr>
																	<th>설명</th>
																	<td>{{o.qt.ansDesc}}</td>
																</tr>
															</thead>
														</table>
													</div>
												</div>
												<!--// testA //-->
											</div> <!--// test Con //-->
										</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
			<!--// 훈련일지 //-->
			<%if(SessionUserInfoHelper.isAdmin()){ %>
			<div class="tc">
			<template v-if="userTest.testResultState == 2 || userTest.testResultState == 1">
			<button class="btn04 btn_blue" @click="setComplete('Y')">채점완료</button>
			</template>
			<template v-if="userTest.testResultState == 3">
			<button class="btn04 btn_blue" @click="setComplete('N')">채점완료 취소</button>
			</template>
			</div>
			<%} %>
		</div>
	</section>
</div>

<script>
	var vm_testResultStdnt = new Vue(
			{
				el : '#vm-testResultStdnt',
				data : {
					userTest : {},
					userQtList : [],
					noArr : [ '①', '②', '③', '④', '⑤', '⑥' ],
				},
				mounted : function() {
					this.getTestQstnList();
				},
				methods : {
					setComplete: function(completeYn){
						var _this = this;
						var eduSeq = '${param.eduSeq}';
						var testSeq = '${param.testSeq}';
						var userId = '${param.userId}';
						$.ajax({
							type: 'post',
							data: {eduSeq: eduSeq, testSeq: testSeq, userId: userId,completeYn: completeYn},
							url: '${utcp.ctxPath}/user/quizTestEdu/saveTestQstnComplete.ajax',
							success: function(r){
								if(r.result == 1){
									_this.getTestQstnList();
								}
							}
						});
					},
					//관리자 정답여부 저장
					setCorrectYn: function(o,yn){
						var _this = this;
						var eduSeq = '${param.eduSeq}';
						var testSeq = '${param.testSeq}';
						var userId = '${param.userId}';
						var correctYn = yn;
						var tqSeq = o.tqSeq;
						var marksGet = o.marksGet2;
						$.ajax({
							type: 'post',
							data: {eduSeq: eduSeq, testSeq: testSeq, userId: userId, tqSeq: tqSeq,correctYn: correctYn,marksGet: marksGet},
							url: '${utcp.ctxPath}/user/quizTestEdu/saveTestQstnCorrect.ajax',
							success: function(r){
								if(r.result == 1){
									_this.getTestQstnList();
								}
							}
						});
					},
					
					//시험지조회
					getTestQstnList : function() {
						var _this = this;
						var eduSeq = '${param.eduSeq}';
						var testSeq = '${param.testSeq}';
						var userId = '${param.userId}';
						$
								.ajax({
									data : {
										eduSeq : eduSeq,
										testSeq : testSeq,
										userId : userId
									},
									url : '${utcp.ctxPath}/user/quizTestEdu/getTestEduQstnList.ajax',
									success : function(r) {
										if (r.result == 1) {
											_this.userQtList = r.data.userQtList;
											_this.userTest = r.data.userTest;
											for ( var i in _this.userQtList) {
												_this
														.convOt(_this.userQtList[i].qt);
											}
										
											var a=$('input[name=md_mt7t_ch]:checked').val();
											console.log(a);
										}
									}
								});
					},
					convOt: function(o){
						o.optnStr = '';
						o.optnArr = [];
						
						if(!UTIL.isEmpty(o.optn)){
							if(o.qstnTp == 1){
								o.optnStr = JSON.parse(o.optn)[0];
							}else if(o.qstnTp == 2){
								o.optnArr = JSON.parse(o.optn);
							}
						}
						
						//marksGet설정
						o.marksGet2 = o.marksGet;
						//this.$forceUpdate();
					},
					filterNumericInput: function(qt) {
					      var value = qt.marksGet2;
					      // 숫자 이외의 문자 제거
					      value = value.replace(/[^0-9]/g, "");
					      if(value > qt.marks){
					    	  value = qt.marks;
					      }
					      qt.marksGet2 = value;
					      Vue.set(qt, 'marksGet2', value);
					    },
					    text2Html : function(v){
							return fltCharToHtml(v);
						},
				}
			});
</script>