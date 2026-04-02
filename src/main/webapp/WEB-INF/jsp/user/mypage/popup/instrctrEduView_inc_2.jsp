<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>

				

						<!--// tab_con2 //-->
						<div class="cont" style="">

							<table class="tc w100 timetb tb02">
								<caption class="sound_only">교육시간표 테이블</caption>
								<thead>
									<tr>
										<th scope="col" class="vm">차시</th>
										<th scope="col" class="vm">교육일</th>
										<th scope="col" class="vm">교육시간</th>
										<th scope="col" class="vm">내용</th>
										<th scope="col" class="vm">강사</th>
										<th scope="col" class="vm">교육형태</th>
										<th scope="col" class="vm" colspan="">교육입장</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${timeList }" var="o" varStatus="s">
										<tr>
											<td>${o.timeSeq }</td>
											<td>${utcp.convDateToStr(utcp.convStrToDate(o.eduDt,'yyyyMMdd'),'yyyy.MM.dd (E)')}</td>
											<td>${fn:substring(o.startTm,0,2)}:${fn:substring(o.startTm,2,4)}~${fn:substring(o.endTm,0,2)}:${fn:substring(o.endTm,2,4)}</td>
											<td class="tl">${o.description }</td>
											<td>${o.instrNm }</td>
											<td>${o.classHowNm }</td>
											<td>
											<c:if test="${o.classHow eq 2 }">
											<input type="text" id="url-${s.index }" class="ip1" value="${o.url }"/> 
											<input type="text" id="urlPw-${s.index }" class="ip1" value="${o.urlPw }" style="width:50px;"/> 
											<a href="javascript:fn_saveUrl(${o.eduSeq},${o.timeSeq},${s.index });" class="btn04 btn_orange fc_white">저장</a>
											<c:choose>
											<c:when test="${not empty o.url}">
											<a href="javascript:window.open($('#url-${s.index }').val(),'','')" class="btn04 btn_orange fc_white">화상입장</a>
											</c:when>
											<c:otherwise>
											</c:otherwise>
											</c:choose>
											</c:if>	
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>

						</div>
						<!--// tab_con2 //-->
<script>
function fn_saveUrl(eduSeq,timeSeq,idx){
	var url = $('#url-'+idx).val();
	var urlPw = $('#urlPw-'+idx).val();
	$.ajax({
		type:'post',
		data:{eduSeq:eduSeq,timeSeq:timeSeq,url:url,urlPw:urlPw},
		url:'${utcp.ctxPath}/user/ajax/saveLectureTimeUrl.json',
		success:function(r){
			if(r.result==1){
				alert('저장 되었습니다.');
				$('#tabNum').val('2');
				$('#myEduForm').submit();
			}else{
				alert('오류가 발생하였습니다.');
			}
		}
	});
	
}
</script>