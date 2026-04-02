<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp" %>
<script>
	$(function(){
		// board_tab(ul) onoff //
		$('.board_tab_onoff>.board_tab_con').children().css('display', 'none');
		$('.board_tab_onoff>.board_tab_con> div:first-child').css('display', 'block');
		$('.board_tab_onoff>.board_tab li:first-child').addClass('active');
		$('.board_tab_onoff').delegate('.board_tab>li', 'click', function() {
			var index = $(this).parent().children().index(this);
			$(this).siblings().removeClass();
			$(this).addClass('active');
			$(this).parent().next('.board_tab_con').children().hide().eq(index).show();
		});
		
		// board_tab(ul) onoff //
	    // 추가신청폼 팝업창
	    $(".addRgsPopBtn").click(function(){
	        var popUrl ="cstEduAddRgs.jsp";
	        var popOption = "width=1200px, height=900px, resizable=no, location=no, top=0px, left=0px,scrollbars=yes"
	        window.open(popUrl,"아동복지통합서비스 사이버교육센터 컨설팅 자가점검표 ",popOption);    
	    });	
	
	});
	function fn_openEduRgsPopup(idx) {
        var popUrl ="${utcp.ctxPath}/user/mypage/popup/myEduRgs.do?crIdx="+idx;
        var popOption = "width=1200px, height=900px, resizable=no, location=no, top=0px, left=0px,scrollbars=yes"
        window.open(popUrl,"아동복지통합서비스 사이버교육센터 신청서 정보 ",popOption);    
	}
	function fn_openEduViewPopup(idx) {
        var popUrl ="${utcp.ctxPath}/user/mypage/popup/myEduView.do?crIdx="+idx;
        var popOption = "width=1200px, height=900px, resizable=no, location=no, top=0px, left=0px,scrollbars=yes"
        window.open(popUrl,"아동복지통합서비스 사이버교육센터 신청서 정보 ",popOption);    
	}
	function fn_openEduRgsPlusPopup(idx, csIdx) {
        var popUrl ="${utcp.ctxPath}/user/mypage/popup/myEduAddRgs.do?csiType=TYPE1&crIdx="+idx+"&csIdx="+csIdx;
        var popOption = "width=1200px, height=900px, resizable=no, location=no, top=0px, left=0px,scrollbars=yes"
        window.open(popUrl,"아동복지통합서비스 사이버교육센터 신청서 추기정보 ",popOption);    
	}
	
	function fn_openSelfcheckfPopup(idx, csIdx) {
        var popUrl ="${utcp.ctxPath}/user/mypage/popup/mySelfCheck.do?csiType=TYPE1&crIdx="+idx+"&csIdx="+csIdx;
        var popOption = "width=1200px, height=900px, resizable=no, location=no, top=0px, left=0px,scrollbars=yes"
        window.open(popUrl,"아동복지통합서비스 사이버교육센터 컨설팅 자가점검표 ",popOption);    
	}
	
	function fn_openSurveyfPopup(idx, csvIdx) {
        var popUrl ="${utcp.ctxPath}/user/mypage/popup/myFeedbackSurvey.do?csiType=TYPE1&crIdx="+idx+"&csIdx="+csvIdx;
        var popOption = "width=1200px, height=900px, resizable=no, location=no, top=0px, left=0px,scrollbars=yes"
        window.open(popUrl,"아동복지통합서비스 사이버교육센터 컨설팅 만족도 조사 ",popOption);    
	}

	function fn_openConfirmfPopup(idx, csIdx, csvIdx) {
		console.log(csvIdx);
		
		if(csvIdx == 0) {
			//location.href = "#confirmPop";
			alert("설문조사 참여 후 증빙서류를 발급 할 수 있습니다.");
			return false;
		}
		
        var popUrl ="${utcp.ctxPath}/user/mypage/popup/myConsultConfirmForm.do?crIdx="+idx+"&csIdx="+csIdx+"&csvIdx="+csvIdx;
        var popOption = "width=1200px, height=900px, resizable=no, location=no, top=0px, left=0px,scrollbars=yes"
        window.open(popUrl,"아동복지통합서비스 사이버교육센터 컨설팅 확인증 ",popOption);    
	}

</script>
            <div class="listWrap" style="padding-top:0">
                <div id="listDiv">

                    <div class="board_tab_onoff">
                        <ul class="board_tab">
                            <li class="active">
                                <p><a href="javascript:;">안전컨설팅</a></p>
                            </li>
                        </ul>

                        <!--// board_tab_con //-->
                        <div class="board_tab_con">
                            
                            <!--// tab_con1 //-->
                            <div class="cont">
                                <table class="tc w100 timetb">
                                    <caption class="sound_only">안전컨설팅 신청내역 테이블</caption>
                                    <thead>
                                        <tr>
                                            <th scope="col" class="vm">번호</th>
                                            <th scope="col" class="vm">구분</th>
                                            <th scope="col" class="vm">신청시설</th>
                                            <th scope="col" class="vm">신청분야</th>
                                            <th scope="col" class="vm">상태</th>
                                            <th scope="col" class="vm">신청서</th>
                                            <th scope="col" class="vm">시설현황</th>
                                            <th scope="col" class="vm">자가점검표</th>
                                            <th scope="col" class="vm">만족도조사</th>
                                            <th scope="col" class="vm">확인증</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="data" items="${myEtcList}" varStatus="stat">
                                        <tr>
                                            <td>${ fn:length(myEtcList) - stat.index }</td>
                                            <td>${ data.cbTitle }</td>
                                            <td>${ data.crCompanyNm }</td>  <!-- t_consult_register - cr_company_nm  -->
                                            <td>소방${data.crBusYn eq '운영' ? '/교통' : '' }</td>
                                            <!-- 
                                            <td> data.cbReceiptD </td>
                                            <td> data.cbReceiptD </td>
                                            -->
                                            <td>${ data.crStatus }</td>
                                            <td>
                                            <c:choose>
                                            <c:when test="${fn:indexOf('선정,완료',data.crStatus)>=0}">
                                            <a href="#none" id="scPopBtn" onclick="fn_openEduViewPopup(${ data.crIdx},${ data.csIdx });" class="btn04 btn_black">보기</a>
                                            </c:when>
                                            <c:otherwise>
                                            <a href="#none" id="scPopBtn" onclick="fn_openEduRgsPopup(${ data.crIdx},${ data.csIdx });" class="btn04 btn_black">수정</a>
                                            </c:otherwise>
                                            </c:choose>
                                            </td>
                                            <c:choose>
                                            <c:when test="${fn:indexOf('선정,완료',data.crStatus)>=0}">
                                            <td><a href="#" id="scPopBtn" onclick="fn_openEduRgsPlusPopup(${ data.crIdx},${ data.csIdx });" class="btn04 btn_black">
                                            	${not empty (data.crFireFacility+=data.crAlertFacility+=data.crResucueFacility1+=data.crResucueFacility2+=data.crEtcFacility+=data.crGasFacility)?'수정':'등록' }
                                            	</a>
                                            </td>
                                            <td><a href="#" id="scPopBtn" onclick="fn_openSelfcheckfPopup(${ data.crIdx},${ data.csIdx });" class="btn04 btn_black">
                                            	${data.csIdx==0?'등록':'수정' } </a>
                                            </td>
                                            </c:when>
											<c:otherwise>
											<td></td><td></td>
											</c:otherwise>                                            
                                            </c:choose>
                                            <c:choose>
                                            <c:when test="${data.crStatus eq '완료' }">
                                            <td><a href="#" id="fbPopBtn" onclick="fn_openSurveyfPopup(${ data.crIdx},${ data.csvIdx });"  class="btn04 btn_black">등록</a></td>
                                            <td><a href="#" id="cfPopBtn" onclick="fn_openConfirmfPopup(${ data.crIdx},${ data.csIdx },${ data.csvIdx });" class="btn04 btn_black">출력</a></td>
                                        	</c:when>
                                        	<c:otherwise>
                                        	<td></td><td></td>
                                        	</c:otherwise>
                                        	</c:choose>
                                        </tr>
                                    </c:forEach>	
                                    </tbody>
                                </table>
                                
                            </div>
                            <!--// tab_con1 //-->

                            <!--// tab_con2 //-->
                            <div class="cont" style="display: block;">
                                
                            </div>
                            <!--// tab_con2 //-->

                            <!--// tab_con3 //-->
                            <div class="cont" style="display: block;">

                            </div>
                            <!--// tab_con3 //-->


                        </div>
                        <!--// board_tab_con //-->	
                    </div>
                    <!--// board_tab_onoff //-->
                    

                    <div class="box_sort flex listWrapBox">
                        <div class="box_search mgauto">
                            <select class="srchColumn" title="제목+내용 검색">
                                <option value="titCont" >제목+내용</option>
                            </select>
                            <input type="text" id="srchWrd"/>
                            <button type="submit" onclick="fn_srch(); return false;">검색</button>
                        </div>
                    </div>
                    <form name="srchFrm" method="post">
                        <input type="hidden" name="srchCtgry" id="i_srchCtgry" value=""/>
                        <input type="hidden" name="srchColumn" id="i_srchColumn" value=""/>
                        <input type="hidden" name="srchWrd" id="i_srchWrd" value=""/>
                        <input type="hidden" name="page" id="i_page" value="1"/>
                    </form>
                </div>
                
            </div>

		<!--// popup_message //-->
		<div class="remodal messagePop2" data-remodal-id="confirmPop" role="dialog" aria-labelledby="modal1Title" aria-describedby="modal1Desc">
			<div class="modal-content">
				<div class="modal-header">
					<p class="tit alignC">알림</p>
				</div>
				<div class="modal-body">
					<p class="messageTxt">
						설문조사 참여 후 증빙서류를 발급 할 수 있습니다.
					</p>
				</div>
				<div class="modal-footer">
					<div class="tc">
						<button type="button" data-remodal-action="cancel" class="remodal-confirm btn02 btn_orange">확인</button>
					</div>
				</div>
			</div>
		</div>
		<!--// popup_message //-->
	
