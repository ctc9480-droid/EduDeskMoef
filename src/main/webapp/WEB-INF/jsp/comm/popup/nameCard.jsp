<%@page import="java.util.List"%>
<%@page import="com.educare.edu.comn.model.LectureTime"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.stream.Collectors"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<style>
/*교육상세 - 신청자 - 명찰시간표 팝업*/
.name_card {
	padding: 4%;
}
.card_pdf {
	margin-bottom: 60px;
	padding: 20px;
}
.card_pdf h2 {
	background: #eee;
    padding: 10px;
    text-align: center;
    font-size: 1.5rem;
    border-radius: 10px;
    margin-bottom: 30px;
}
.card_layout {
	/*width: 565px;height:710px;
	width:359.055px;
	height:461.102px;*/
	width:333px;
	height:430px;
    border: 1px solid #ddd;
    margin: 10px auto;
    box-sizing: border-box;
}
.name_layout {
	text-align: center;
	border-bottom: 12px solid #0a4595;
}
.name_top {
	display: flex;
    flex-direction: column;
    justify-content: center;
    height: 30%;
	background: url('../../../resources/user/image/namecard_bg.webp') no-repeat center center;
	background-size:cover;
    color: #fff;
    font-size: 27px;
    line-height: 140%;
    font-weight: 600;
    letter-spacing: -1px;
    padding: 10px 0;
    
}
.name_top p {
	word-break: break-all;
}
.name_bot {
	/*min-height: 430px;*/
    display: inline-flex;
    flex-direction: column;
    justify-content: center;
    padding-top: 20px;
}
.name_bot p {
	font-size: 25px;
	color:#0a4595;
	font-weight: 600;
	padding-bottom: 10px; 
	letter-spacing: -1px;
}
.name_bot .name_txt {
    margin-top: 14px;
    display: inline-block;
}
.name_bot .name_txt h3 span {
	position: relative;
	padding: 26px 4px 0;
    display: inline-block;
}
.name_bot .name_txt h3 span::before{
	content:'';
	display: block;
	width:160%;
	height:5px;
	background:#666;
	position: absolute;
	top:0;
	left: 50%;
	transform:translateX(-50%); 
}
.name_bot h3 {
	font-size: 60px;
}
.name_bot h3 span:not(:last-child) {
	margin-right: 60px;
}
.time_layout {
	padding: 48px 30px;
	border-bottom: 12px solid #0a4595; 
}
.time_layout h3 {
	font-size: 17px;
	color:#0a4595;
	position: relative;
}
.time_layout h3::after{
	content:'';
	display: block;
	width:76%;
	height:5px;
	background: #666;
	position: absolute;
	right:0;
	top:50%;
	transform:translateY(-50%);
}
.time_layout table {
	width:100%;
	margin-top: 15px;
	border-collapse: collapse;
}
.time_layout th {
	background: #0a4595;
	color: #fff;
	font-weight: 400;
    padding: 8px 0;
    border-right: 1px solid #fff;
    letter-spacing: 7.3px;
    padding-left: 7.3px;
    font-size: 11px;
    border-collapse: collapse;
}
.time_layout td {
	border: 0.5px solid #333;
    padding: 6px 3px;
    line-height: 124%;
    font-size: 11px;
    border-collapse: collapse;
}
.time_layout td:last-child {
	border-right: 0;
	padding-left:4px; 
}
.time_layout .time {
	text-align: center;
}
.time_layout .date {
	text-align: center;
	border-left: 0;
}
.time_layout .time_info {
	margin-top:13px;
	font-size: 9px;
}
.time_layout .time_info li {
	display: flex;
	align-items:center;
	padding-bottom: 7px;
	letter-spacing: -0.5px;
}
.time_layout .time_info li::before{
	content:'';
	display: block;
	width:3px;
	height:3px;
	border-radius:50%;
	background: #0a4595;
	margin-right: 3px;
}
.time_layout .time_info li h4 {
	padding-right: 6px;
    margin-right: 6px;
    position: relative;
}
.time_layout .time_info li h4::after{
	content:'';
	display: block;
	width:1.5px;
	height:100%;
	background:#333;
	position: absolute;
	right:0;
	top:50%;
	transform:translateY(-50%);
}
.name_card .down_btn {
	text-align: center;
}
.name_card button {
	margin: 0 auto;
}
.name_bot_wrap {
	display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: space-around;
    height: 70%;
}
.name_logo {
	width: 110px;
	margin: 0 auto;
}
.name_logo img {
	width:100%;
}
</style>
<div class="name_card">
	<div class="card_pdf name_tag" id="pdfDiv">
		<h2>명찰</h2>
		<div class="card_layout name_layout">
			<div class="name_top">
				<p>${data.lctre.eduYear }년 제${data.lctre.eduTerm }기</p>
				<p>${data.lctre.eduNm }</p>
			</div>
			<div class="name_bot_wrap">
				<div class="name_bot">
					<c:set var="orgNm" value="${data.user.userOrgNm}" />
					<c:set var="orgList" value="${fn:split(orgNm, ' ')}" />
					<c:choose>
    					<c:when test="${fn:length(orgList) >= 3}">
    				<p>${orgList[0]} ${orgList[fn:length(orgList) - 1]}</p>
					    </c:when>
					    <c:otherwise>
				 	<p>${orgNm}</p>
					 	</c:otherwise>
					</c:choose>
				 	<p>${data.user.userGradeNm }</p>
				 	<div class="name_txt">
				 		<h3><span>${data.user.userNm }</span></h3>
				 	</div>
				 </div>
				 <div class="name_logo">
				 	<img src="../../../resources/user/image/namecard_logo.png" alt="재정경제부" />
				 </div>
			</div>
		</div>
	</div>
	<div class="card_pdf time_tag" id="pdfDiv2">
		<h2>시간표</h2>
		<div class="card_layout time_layout" style="height: auto;">
			<h3><span>프로그램</span></h3>
			<table>
				<colgroup>
					<col width="20%">
					<col width="32%">
					<col width="48%">
				</colgroup>
				<thead>
					<tr>
						<th>일정</th>
						<th>시간</th>
						<th>과목명</th>
					</tr>
				</thead>
				<tbody>
				
				<%
				Map<String,Object> rstData = (Map<String,Object>) request.getAttribute("data");
				List<LectureTime> time = (List<LectureTime>) rstData.get("time");

				// 날짜별 강의 개수 계산
				Map<String, Integer> dateCountMap = new LinkedHashMap<>();
				for (LectureTime lt : time) {
				    dateCountMap.put(lt.getEduDt(), dateCountMap.getOrDefault(lt.getEduDt(), 0) + 1);
				}

				request.setAttribute("dateCountMap", dateCountMap);
				request.setAttribute("timeList", time);
				%>
					<c:forEach items="${timeList}" var="o">
					  <tr>
					    <c:if test="${prevDate != o.eduDt}">
					      <td rowspan="${dateCountMap[o.eduDt]}" class="date">${utcp.convDateToStr(utcp.convStrToDate(o.eduDt,'yyyyMMdd'),'M.d.(E)')}</td>
					      <c:set var="prevDate" value="${o.eduDt}" />
					    </c:if>
					    <td class="time">${fn:substring(o.startTm,0,2)}:${fn:substring(o.startTm,2,4)} ~ 
  ${fn:substring(o.endTm,0,2)}:${fn:substring(o.endTm,2,4)}</td>
					    <td>${o.description}</td>
					  </tr>
					</c:forEach>
				</tbody>
			</table>
			<div class="time_info">
				<ul>
					<li>
						<h4>강의실 입소</h4>
						<p>QR 등 1일 3회 출석체크</p>
					</li>
					<li>
						<h4>숙소동</h4>
						<p>프론트(교육동) 041-675-5420 <!-- / 10시까지 운영 --></p>
					</li>
					<li>
						<h4>구내식당 이용 시간</h4>
						<p>아침 07:30~8:30, 점심 11:30~13:00, 저녁 17:30~19:00</p>
					</li>
				</ul>
			</div>
		</div>
	</div>
	<div class="down_btn">
		<button class="btn01 btn_green" id="saveAllPdf">다운로드</button>
	</div>
</div>

<script src="${utcp.ctxPath}/resources/plugins/html2canvas/html2canvas.min.js"></script>
<script src="${utcp.ctxPath}/resources/user/js/jspdf.min.js"></script>
<script>
$('#saveAllPdf').click(function () {
    var doc = new jsPDF('p', 'mm', 'a4');

    // 첫 번째 영역 캡처
    html2canvas($('#pdfDiv')[0], { scale: 2 }).then(function (canvas1) {
        var imgData1 = canvas1.toDataURL('image/png');
        var imgWidth = 210; // A4 width
        var pageHeight = imgWidth * 1.414;
        var imgHeight1 = canvas1.height * imgWidth / canvas1.width;

        doc.addImage(imgData1, 'PNG', 0, 0, imgWidth, imgHeight1);

        // 두 번째 영역 캡처
        html2canvas($('#pdfDiv2')[0], { scale: 2 }).then(function (canvas2) {
            var imgData2 = canvas2.toDataURL('image/png');
            var imgHeight2 = canvas2.height * imgWidth / canvas2.width;

            doc.addPage(); // 새 페이지 추가
            doc.addImage(imgData2, 'PNG', 0, 0, imgWidth, imgHeight2);
        	// 파일 저장
            var fileNm = "명찰_시간표_${data.user.userNm}.pdf";
            doc.save(fileNm);
            
            
        });
    });
});
</script>

<%--

<div class="name_card">
	<div class="card_pdf name_tag">
		<h2>명찰</h2>
		<div class="card_layout name_layout">
			<div class="name_top">
				<p>2025년 제3기</p>
				<p>행정법 실무 핵심과정(태안)</p>
			</div>
			 <div class="name_bot">
			 	<p>재정경제부 기획조정실</p>
			 	<p>정책기획관 혁신정책담당관</p>
			 	<div class="name_txt">
			 		<h3><span>홍</span><span>길</span><span>동</span></h3>
			 	</div>
			 </div>
		</div>
	</div>
	<div class="card_pdf time_tag">
		<h2>시간표</h2>
		<div class="card_layout time_layout">
			<h3><span>프로그램</span></h3>
			<table>
				<thead>
					<tr>
						<th>일정</th>
						<th>시간</th>
						<th>과목명</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td rowspan='3' class="date">7.28.(월)</td>
						<td class="time">12:30 ~ 13:00</td>
						<td>등록 및 과정 안내</td>
					</tr>
					<tr>
						<td class="time">13:00 ~ 15:00</td>
						<td>
							<strong>행정법 1(총칙)</strong>
							<p>김혜영 법제처 법제교육과 서기관</p>
						</td>
					</tr>
					<tr>
						<td class="time">15:00 ~ 17:00</td>
						<td>
							<strong>행정법 2(작용법)</strong>
							<p>박일남 법제처 법제교육과 사무관</p>
						</td>
					</tr>
					<tr>
						<td rowspan='4' class="date">7.29.(화)</td>
						<td class="time">10:00 ~ 12:00</td>
						<td>
							<strong>행정법 3(실효성확보수단)</strong>
							<p>이동희 위촉교수</p>
						</td>
					</tr>
					<tr>
						<td class="time">12:00 ~ 13:00</td>
						<td>
							<p>점심식사</p>
						</td>
					</tr>
					<tr>
						<td class="time">13:00 ~ 15:00</td>
						<td>
							<strong>행정절차법</strong>
							<p>조정필 법제처 법제교육과 과장</p>
						</td>
					</tr>
					<tr>
						<td class="time">15:00 ~ 17:00</td>
						<td>
							<p>현장학습</p>
						</td>
					</tr>
					<tr>
						<td rowspan='2' class="date">7.30.(수)</td>
						<td class="time">10:00 ~ 12:00</td>
						<td>
							<strong>행정기본법</strong>
							<p>조정필 법제처 법제교육과 과장</p>
						</td>
					</tr>
					<tr>
						<td class="time">12:00 ~ 12:30</td>
						<td>
							<p>설문조사</p>
						</td>
					</tr>
				</tbody>
			</table>
			<div class="time_info">
				<ul>
					<li>
						<h4>강의실 입소</h4>
						<p>9시 15분 등교 QR 등 1일 3회 출첵</p>
					</li>
					<li>
						<h4>숙소동</h4>
						<p>프론트(교육동) 041-675-5420 / 10시까지 운영</p>
					</li>
					<li>
						<h4>구내식당 이용 시간</h4>
						<p>아침 07:30~8:30, 점심 11:30~13:00, 저녁 17:30~19:00</p>
					</li>
				</ul>
			</div>
		</div>
	</div>
	<div class="down_btn">
		<button class="btn01 btn_green">다운로드</button>
	</div>
</div>
 --%>