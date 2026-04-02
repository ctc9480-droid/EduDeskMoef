<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="com.educare.edu.comn.vo.FeedbackVO"%>
<%@page import="java.util.List"%>
<%@ page language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<%-- 
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.3/Chart.bundle.min.js">
<script src="https://cdn.jsdelivr.net/npm/chartjs-plugin-datalabels"></script>
 --%>
<script src="${utcp.ctxPath}/resources/plugins/chartjs/chart.min.js"></script>
<script src="${utcp.ctxPath}/resources/plugins/chartjs/Chart.bundle.min.js"></script>
<script src="${utcp.ctxPath}/resources/plugins/chartjs/chartjs-plugin-labels.js"></script>

<section class="pd025 pd200 po_re">
	<div class="po_re br5 bs_box">

		<div class="tr mgb15">
			<form id="form-srch">
				<input type="hidden" name="excelYn" id="excelYn" />
				<input type="hidden" name="excelFileNm" id="excelFileNm" />
				<input type="hidden" name="excel2Yn" id="excel2Yn" />
				<input type="hidden" name="fbIdx" value="${vo.fbIdx }" />
				<c:forEach items="${eduSeqChk }" var="o">
				<input type="hidden" name="eduSeqChk" value="${o}" />
				</c:forEach>
			</form>
		</div>

		<table class="w100 tb01">
			<thead>
				<tr>
					<th rowspan="">구분</th>
					<th rowspan="">항목</th>
					<th rowspan="">답변</th>
					<th rowspan="">개수</th>
					<th rowspan=""></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${feedbackInfo.qtList}" var="o" varStatus="s">
					<c:set var="isFirstDiv" value="false" />
					<c:set var="rowCnt" value="0" />
					<c:if test="${o.qt.qtDiv ne feedbackInfo.qtList[s.index-1].qt.qtDiv }">
						<c:set var="isFirstDiv" value="true" />
					</c:if>
					<c:if test="${isFirstDiv }">
						<c:forEach begin="${s.index }" end="${fn:length(feedbackInfo.qtList)-1 }" var="i2">
							<c:if test="${o.qt.qtDiv eq feedbackInfo.qtList[i2].qt.qtDiv }">
								<c:set var="rowCnt" value="${rowCnt+fn:length(feedbackInfo.qtList[i2].chList) }" />
								<c:if test="${feedbackInfo.qtList[i2].qt.qtType == 0 }">
									<c:set var="rowCnt" value="${rowCnt+1 }" />
								</c:if>
							</c:if>
						</c:forEach>
					</c:if>

					<c:if test="${o.qt.qtType != 0 }">
						<c:forEach items="${o.chList }" var="o2" varStatus="s2">
							<c:set var="rowCnt2" value="0" />
							<c:if test="${o2.chIdx == 1 }">
								<c:set var="rowCnt2" value="${fn:length(o.chList) }" />
							</c:if>
							<tr>
								<%-- 
							 --%>
								<c:if test="${rowCnt > 0 }">
									<td rowspan="${rowCnt }">${o.qt.qtDiv}</td>
								</c:if>
								<c:if test="${rowCnt2 > 0 }">
									<td class="tl" rowspan="${rowCnt2 }">${o.qt.question}</td>
								</c:if>
								<td id="qt-${o.qt.qtIdx }-ch-${o2.chIdx}" class="tl">${o2.choice }</td>
								<td id="as-${o.qt.qtIdx }-ch-${o2.chIdx}" class="brGray">${o2.asCnt }</td>
								<c:if test="${rowCnt2 > 0 }">
								<td rowspan="${rowCnt2 }">
								<canvas id="chart-qt-${o.qt.qtIdx }" class="fl"></canvas>
								<div id="legend-div-${o.qt.qtIdx }" class="legend-div legendDiv w50 fl tl"></div>
								</td>
								</c:if>
							</tr>
							<c:if test="${rowCnt>1 }">
								<c:set var="rowCnt" value="0" />
							</c:if>
						</c:forEach>
					</c:if>
					<c:if test="${o.qt.qtType == 0 }">
						<tr>
							<%-- 
							 --%>
							<c:if test="${rowCnt > 0 }">
								<td rowspan="${rowCnt }">${o.qt.qtDiv}</td>
							</c:if>
							<td class="tl">${o.qt.question}</td>
							<td class="tl" colspan=3><c:forEach items="${o.asList }" var="as">
							${as.answer }<br />
								</c:forEach></td>
						</tr>
					</c:if>
				</c:forEach>
			</tbody>
		</table>
		<div class="fl tc mgb20">
			<button type="button" class="btn01 btn_greenl" onclick="fn_list()">목록</button>
		</div>
		<div class="fr tc mgb20">
			<button type="button" class="btn01 btn_greenl" onclick="fn_downExcel();">엑셀 다운로드</button>
		</div>

	</div>
</section>
<form id="form-list">
</form>
<script>
function customLegend (chart) {
    var ul = document.createElement('ul');
    var color = chart.data.datasets[0].backgroundColor;

    chart.data.labels.forEach(function (label, index) {
    	//console.log(label);
    	//console.log(color[index]);
        ul.innerHTML += '<li><span style="background-color: '+color[index]+'; display: inline-block; width: 30px; height: 10px;"></span> '+label+'</li>';
    });

    return ul.outerHTML;
};
$('[id^=chart-qt]').each(function(i,o){
	var qtIdx = i+1;
	
	var chart_labels = [];
	var chart_datas = [];
	$('[id^=qt-'+qtIdx+'-ch]').each(function(){
		chart_labels.push($(this).text());
	});
	$('[id^=as-'+qtIdx+'-ch]').each(function(){
		chart_datas.push($(this).text());
	});
	var ctx = document.getElementById('chart-qt-'+qtIdx).getContext('2d');
	var myChart = new Chart(ctx, {
	    type: 'pie',
	    data: {
	        labels: chart_labels,
	        datasets: [{
	            label: '1',
	            data: chart_datas,
	            backgroundColor: ["#dd3711", "#ff9700", "#ffd200", "#0f9719", "#35c0cd", "#3566cd", "#5935cd", "#c435cd", "#cd358b", "#cd5535"],
	        }]
	    },
	    options: {
	    	maintainAspectRatio: false,
            responsive: false,
            legend: {
                display: false,
                //position:'right',
           	},
           	tooltip: { // 기존 툴팁 사용 안 함
                enabled: false
              },
            legendCallback: customLegend,
            plugins : {
            	labels : {
            		fontColor : '#fff',
            	},
            },
        }
	    
	});
	//console.log(myChart);
	document.getElementById('legend-div-'+qtIdx).innerHTML = myChart.generateLegend();
});
</script>

<script>
function fn_srch() {
	$('#form-srch').submit();
};
function fn_list() {
	$('#form-list').attr('action','statFbLctreList.do');
	$('#form-list').submit();
};
function fn_downExcel() {
	$('#excelFileNm').val('${feedbackInfo.fb.title}');
	$('#excelYn').val('Y');
	$('#form-srch').submit();
	$('#excelYn').val('');
};
</script>