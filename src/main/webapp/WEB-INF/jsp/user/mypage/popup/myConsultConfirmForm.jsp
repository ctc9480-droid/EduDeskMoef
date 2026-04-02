<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<script src="${utcp.ctxPath}/resources/plugins/html2canvas/html2canvas.min.js"></script>
<script src="${utcp.ctxPath}/resources/user/js/jspdf.min.js"></script> 
<script>
	$(document).ready(function() {
	});
	
	function fn_savePdf(val,idx) {
		$.ajax({
			url:'/consult/updateConsultingConfirmInfo.do',
			data:{"crConfirmNo":val,"crIdx":idx},
			method:'post',
			success:function(r){
				console.log(r);
				if(r.resultCode >= 0) {
					html2canvas($('#pdfDiv')[0]).then(function(canvas) {
						var imgData = canvas.toDataURL('image/png');
					     
					    var imgWidth = 210; // 이미지 가로 길이(mm) / A4 기준 210mm
					    var pageHeight = imgWidth * 1.414;  // 출력 페이지 세로 길이 계산 A4 기준
					    var imgHeight = canvas.height * imgWidth / canvas.width;
					    var heightLeft = imgHeight;
					    var margin = 0; // 출력 페이지 여백설정
					    var doc = new jsPDF('p', 'mm', 'a4');
					    var width = doc.internal.pageSize.getWidth();
					    var height = doc.internal.pageSize.getHeight();
					    var position = 0;
					       
					    // 첫 페이지 출력
					    doc.addImage(imgData, 'PNG', margin, position, imgWidth, imgHeight);
					    heightLeft -= pageHeight;
					         
					    // 한 페이지 이상일 경우 루프 돌면서 출력
					    while (heightLeft >= 20) {
					        position = heightLeft - imgHeight;
					        doc.addPage();
					        doc.addImage(imgData, 'PNG', 0, position, imgWidth, imgHeight);
					        heightLeft -= pageHeight;
					    }
					 
					    // 파일 저장
					    var fileNm = "컨설팅확인증.pdf";
					    doc.save(fileNm);
					});
				}
			}
		});
	}
</script>
    <div id="wrap">
		<div class="cst cfm" id="pdfDiv">
		
			<h2 class="tl fs_15">제 <span>${ crInfo.crConfirmNo }</span> 호</h2>
			
			<h1 class="w100">어린이집 안전관리 온라인 컨설팅 확인증</h1>

			<!--//<ul class="tr">
				<li>
                    <span class="cfmtit">어린이집명</span>:
                    <span class="cfmCont cfAgent">${ crInfo.crCompanyNm }</span>
                </li>
				<li>
                    <span class="cfmtit">분야</span>:
                    <span class="cfmCont cfType">소방${ crInfo.crBusYn eq '운영' ? ', 교통(통학버스)' : '' }</span>
                </li>
			</ul>//-->
			
			<ul class="tr">
				<li class="dp_ib">
                    <span class="cfmtit dp_b tr mgb10">어린이집명 : </span>
                    <span class="cfmtit dp_b tr">분야 : </span>
                    
                </li>
				<li class="dp_ib">
                    <span class="cfmCont cfAgent dp_b tl mgb10">${ crInfo.crCompanyNm }</span>
                    <span class="cfmCont cfType dp_b tl">소방${ crInfo.crBusYn eq '운영' ? ', 교통(통학버스)' : '' }</span>
                </li>
			</ul>

			<p class="tl txt1">

                <!--// 자가점검표에서 컨설턴트 소견 작성 완료후 저장한 일자
                <span class="dp_b tc fs_18">
                    ( 컨설팅 일자 : ${ fn:substring(crInfo.crModTime,0,4) }년 ${ fn:substring(crInfo.crModTime,5,7) }월 ${ fn:substring(crInfo.crModTime,8,10) }일 )
                </span>
                                           자가점검표에서 컨설턴트 소견 작성 완료후 저장한 일자 //-->
			</p>

            <!--// 만족도조사 작성 완료후 저장한 일자 //-->
			<p class="date">
				<span class="ctYear">${ fn:substring(csvInfo.csModTime,0,4) }</span>년&nbsp; 
				<span class="ctMonth">${ fn:substring(csvInfo.csModTime,5,7) }</span>월&nbsp; 
				<span class="ctDate">${ fn:substring(csvInfo.csModTime,8,10) }</span>일
			</p>
            <!--// 만족도조사 작성 완료후 저장한 일자 //-->

			<div class="bot_Cname po_re">
				<span class="name">이사장</span>
				<span class="mark stp po_ab"><img src="${utcp.ctxPath}/resources/admin/images/csiaStp.png" alt="직인이미지" /></span>
			</div>

		</div>
		
		<div class="btn_step3">
			<a href="#none" class="btn_step01" id="savePdf" onclick="fn_savePdf('${ crInfo.crConfirmNo }',${ crInfo.crIdx });">출력하기</a>
		</div>
	</div>
	<!--// #wrap -->
