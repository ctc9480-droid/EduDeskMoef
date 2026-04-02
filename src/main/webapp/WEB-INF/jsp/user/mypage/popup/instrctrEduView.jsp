<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<form id="myEduForm" name="myEduForm">
<input type="hidden" name="tabNum" value="${tabNum}"/>
<input type="hidden" name="tabAction" value="${tabAction}" id="tabAction"/>
<input type="hidden" name="boardIdx" value="${not empty param.boardIdx?param.boardIdx:0 }" id="boardIdx"/>
<input type="hidden" name="pageNo" value="${not empty param.pageNo?param.pageNo:1 }"/>
<input type="hidden" name="eduSeq" id="edu_seq" value="${lctre.eduSeq }" />
</form>

<div id="wrap" >
	<div id="content" class="pdt0 w100">
		<div class="listWrap pdt0 mypopWrap" >
			<div id="listDiv">
				<div class="box_list">
					<ul id="lctreView">
						<li class="flex room">
							<div class="roomTit cf fs_13">
								강의실 <a href='javascript:window.close();' class="roomOut fc_yellow fr">강의실나가기<i class="fa-solid fa-x pdl3"></i></a>
							</div>
							<div class="img_logo mgt10">
								<c:choose>
									<c:when test="${lctre.imgUseYn eq 'Y' && not empty lctre.imgRename }">
										<img src="<spring:eval expression="@prop['cloud.cdn.url']"/>/upload/web/lctreThum/${lctre.imgRename }?${utcp.convDateToStr(lctre.updDe,'yyyyMMddHHmmss')}" alt="${lctre.eduNm }" width="112px" height="149px">
									</c:when>
									<c:otherwise>
										<img src="${utcp.ctxPath}/resources/admin/images/default_img.png?${utcp.convDateToStr(lctre.updDe,'yyyyMMddHHmmss')}" alt="${lctre.eduNm }" width="112px" height="149px">
									</c:otherwise>
								</c:choose>
							</div>
							<div class="text_title">
								<div class="">
								<p class="eduTypeWrap">
	                             <span class="eduLabel">
	                             <c:if test="${lctre.fee > 0 }">
	                             <b class="price pay">유료</b>
	                             </c:if>
	                             <c:if test="${lctre.fee == 0 }">
	                             <b class="price free">무료</b>
	                             </c:if>
	                             </span>
	                             |
	                             <span class="eduType">${lctre.ctg1Nm}</span> |
	                             <span class="eduType1">[${lctre.lctreTypeNm }]</span>
	                         </p>
	
	                         <p class="eduSbj">
	                                 	교육명 :
									<strong class="Sbj ">${lctre.eduNm} </strong>
								${lctre.addStatusBox }
	                         </p>
								</div>
								
							</div>
							<div class="text_info">
								<div>
									<span> [접수] <b class="fw_500"> <c:if test="${lctre.rceptPeriodYn eq 'Y'}">
								${utcp.convDateToStr(utcp.convStrToDate(lctre.rceptPeriodBegin,'yyyyMMddHHmm'),'yyyy-MM-dd')} ~
							 	${utcp.convDateToStr(utcp.convStrToDate(lctre.rceptPeriodEnd,'yyyyMMddHHmm'),'yyyy-MM-dd')}
								</c:if> <c:if test="${lctre.rceptPeriodYn ne 'Y'}">
								미설정
								</c:if>
									</b>
									</span>
									<br />
									<span> [교육] <b class="fw_500">
											${lctre.eduPeriodBegin } ~ ${lctre.eduPeriodEnd }
									</b>
									</span>
									<br />
									<span class=""> [인원] ${lctre.personnel}명 모집 (<strong class="color fw_500">${lctre.rceptCnt}</strong>명 신청)
									</span>
								</div>
							</div>
                       <!--   -->
							<div class="dp_b text_progress bg_darkblue">
                       <div>
                            <span class="edudtType4">
                              	 출석률 : ${attRatio }%
                            </span>
                            <%-- 
                            <span class="edudtType4">
                                수료점수 : ${lctre.passPoint }점 이상
                            </span>
                            <c:if test="${lctre.passAttPoint > 0 }">
							<span class="edudtType4"> 출석 : ${lctre.passAttPoint }  </span>
							</c:if>
							<c:if test="${lctre.passAsgPoint > 0 }">
								<span class="edudtType4"> 과제점수 : ${lctre.passAsgPoint } </span>
							</c:if>
							<c:if test="${lctre.passTestPoint > 0 }">
								<span class="edudtType4"> 시험점수 : ${lctre.passTestPoint } </span>
							</c:if>
                   			 --%>
                        </div> 
                    </div>
                       
						</li>
					</ul>
				</div>

				<!--// board_tab_onoff //-->
				<div class="board_tab_onoff">
				
					<!--// board_tab //-->
					<ul class="board_tab">
						<li class="${tabNum == 1?'active':'' }">
							<p><a href="#none" onclick="fn_tab('1','1'); return false;">내용 상세보기</a></p>
						</li>
						<li class="${tabNum == 2?'active':'' }">
							<p><a href="#none" onclick="fn_tab('2','2'); return false;">교육 시간표</a></p>
						</li>
						<li class="${tabNum == 3?'active':'' }">
							<p><a href="#none" onclick="fn_tab('3','3'); return false;">수강생 현황</a></p>
						</li>
						<li id="tab4" class="${tabNum == 4?'active':'' }">
							<p><a href="#none" onclick="fn_tab('4','boardList'); return false;">알림마당</a></p>
						</li>
						<li id="tab6" class="${tabNum == 6?'active':'' }">
							<p><a href="#none" class="ud_bak" onclick="fn_tab('6','communityList'); return false;">커뮤니티</a></p>
						</li>
						<!-- 
						<li id="tab7" class="${tabNum == 7?'active':'' }">
							<p><a href="#none" onclick="fn_tab('7','taskList'); return false;">과제</a></p>
						</li>
						 -->
						<li id="tab8" class="${tabNum == 8?'active':'' }">
							<p><a href="#none" class="ud_bak" onclick="fn_tab('8','testList'); return false;">시험</a></p>
						</li>
					</ul>
					<!--// board_tab //-->

					<!--// board_tab_con //-->
					<div class="board_tab_con">
						<c:import url="/user/mypage/popup/instrctrEduView_inc_${tabAction }.do"/>
					</div>
				</div>
				<!--// board_tab_onoff //-->

			</div>
		</div>
	</div>
</div>


<script type="text/javascript">
function fn_tab(n,action){
	
	$('input[name=tabNum]').val(n);
	$('input[name=tabAction]').val(action);
	$('input[name=eduSeq]').val(document.querySelector("#edu_seq").value);
	$('input[name=boardIdx]').val(document.querySelector("#boardIdx").value);
	$('#myEduForm').submit();
}
</script>

<!-- 공통 알림 -->
<div id="remodal-alert" class="remodal messagePop2" data-remodal-id="remodal-alert" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc">
	<div class="modal-content">
		<div class="modal-header">
			<p class="tit alignC">알림</p>
		</div>
		<div class="modal-body">
			<p class="messageTxt" id="messageTab2">{{alert}}</p>
		</div>
		<div class="modal-footer">
			<div class="tc">
				<button data-remodal-action="cancel" class="remodal-confirm btn02 btn_orange">확인</button>
			</div>
		</div>
	</div>
</div>
<script>
	var _remodal = $('[data-remodal-id=remodal-alert]').remodal();
	var _vue = new Vue({
		el : '#remodal-alert',
		data : {
			alert : '알림 내용'
		}
	});
</script>
