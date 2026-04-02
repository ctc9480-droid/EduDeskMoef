<%@page import="com.educare.edu.member.service.SessionUserInfoHelper"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<c:choose>
<c:when test="${empty mb_id }">
	<c:if test="${pageContext.request.method eq 'POST' }">
		<script>
		alert('잘못된 아이디/비밀번호 입니다.');
		</script>
	</c:if>
<form method="post">
	<div class="box_search">
		<p class="fs_16 fw_500 mgb15">
			<img src="${utcp.ctxPath}/resources/user/image/icon/icon_subtitle.png" alt="서브타이틀 아이콘"/>
			이전 교육시 사용하던 ID와 패스워드를 입력하시면, 이전 교육내용을 보실 수 있습니다.
			<span class="fs_14 fw_400">(한번만 입력하시면 다음부터는 자동으로 보실 수 있습니다)</span>
		</p>
		<input type="text" name="mb_id" placeholder="아이디"/>
		<input type="password" name="mb_password" placeholder="비밀번호"/>
		<button>로그인</button>
	</div>
</form>
</c:when>
<c:otherwise>
<script>
function delConn(){
	console.log(document.formRe.reConn.value);
	document.formRe.reConn.value='Y';
	document.formRe.submit();
}
</script>
<form name="formRe" method="post" ">
	<input type="hidden" name="reConn" value="N"/>
	<div class="box_search">
		<button type="button" onclick="delConn()">재연결</button>
	</div>
</form>
<div id="wrap">
	<div id="container">
		<div id="content">
			<div class="sub_txt">
				<span class=""><img src="${utcp.ctxPath}/resources/user/image/icon/icon_subtitle.png" alt="서브타이틀 아이콘" />2021년 이전 교육이력</span>
			</div>
			<div class="listWrap cst pdt0" >
				<div id="viewDiv">
					<div class="pe_table">
						<table class="tb02 tc w100">
							<caption class="sound_only">신청한 교육이력 목록 테이블</caption>
							<colgroup>
								<col width="35px" />
								<col />
								<col />
								<col width="60px"/>
								<col width="35px"/>
								<col width="70px"/>
								<col />
								<col width="100px"/>
								<col width="90px"/>
								<col width="70px"/>
								<col width="60px"/>
							</colgroup>
							<thead>
								<tr>
									<th scope="col">번호</th>
									<th scope="col">시도명</th>
									<th scope="col">기관명</th>
									<th scope="col">해당년도</th>
									<th scope="col">회차</th>
									<th scope="col">교육구분</th>
									<th scope="col">과정명</th>
									<th scope="col">접수기간</th>
									<th scope="col">교육일시</th>
									<th scope="col">상태</th>
									<th scope="col">수료증</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${list }" var="o" varStatus="s">
								<tr>
									<td>${fn:length(list)-s.index }</td>
									<td>${o.cate1 }</td>
									<td>${o.cate2 }</td>
									<td>${o.wr_3  }</td>
									<td>${o.wr_10 }</td>
									<td>${o.wr_1 }</td>
									<td>${o.wr_2 }</td>
									<td>${utcp.convDateToStr(o.date1_1,'yyyy-MM-dd') } ~ ${utcp.convDateToStr(o.date2_1,'yyyy-MM-dd') }</td>
									<td>${utcp.convDateToStr(o.date3_1,'yyyy-MM-dd') }</td>
									<td>${o.addStatusNm }</td>
									<td><c:if test="${o.ing == 4 }"><a href="javascript:fn_certView(${o.idx });" class="btn03 btn_black">보기</a></c:if></td>
								</tr>
								</c:forEach>
								<c:if test="${empty list }">
								<tr>
									<td colspan="10">데이터가 없습니다.</td>
								</tr>
								</c:if>
							</tbody>
						</table>
					</div>
				</div>
				<!--//viewDiv//-->
			</div>
			<!--//listWrap//-->
		</div>
		<!--// content -->
	</div>
	<!--// #container -->
</div>
<!--// #wrap -->

<script>
function fn_certView(idx) {
	var eduSeq = $("#edu_seq").val();

	var popupWidth = 890;
	var popupHeight = 940;

	var popupX = (window.screen.width / 2) - (popupWidth / 2);
	var popupY = (window.screen.height / 2) - (popupHeight / 2);

	var url = "${utcp.ctxPath}/user/edu/certViewOld.do?idx=" + idx;

	window.open(url, "증빙서류", 'status=no, height=' + popupHeight
			+ ', width=' + popupWidth + ', left=' + popupX + ', top='
			+ popupY);
}
</script>
</c:otherwise>

</c:choose>
