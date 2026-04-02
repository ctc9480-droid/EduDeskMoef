<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/include/includePageTaglib.jsp"%>
<!DOCTYPE HTML>
<html lang="ko" xml:lang=“ko” xmlns=“http://www.w3.org/1999/xhtml”>
<head>
<!-- style.css -->
<link rel="stylesheet" type="text/css" href="${utcp.ctxPath}/resources/user/css/main.css">
<link rel="stylesheet" type="text/css" href="${utcp.ctxPath}/resources/user/css/sub.css">
<link rel="stylesheet" type="text/css" href="${utcp.ctxPath}/resources/user/css/slick.css" />
<link rel="stylesheet" type="text/css" href="${utcp.ctxPath}/resources/user/css/common.css">
<link rel="stylesheet" type="text/css" href="${utcp.ctxPath}/resources/common/css/style.css">
<style>
html, body {
	width: 100%; height: 100%;
}

    .wrap {position: absolute;left: 0;bottom: 40px;width: 288px;height: 132px;margin-left: -144px;text-align: left;overflow: hidden;font-size: 12px;font-family: 'Malgun Gothic', dotum, '돋움', sans-serif;line-height: 1.5;}
    .wrap * {padding: 0;margin: 0;}
    .wrap .info {width: 286px;height: 120px;border-radius: 5px;border-bottom: 2px solid #ccc;border-right: 1px solid #ccc;overflow: hidden;background: #fff;}
    .wrap .info:nth-child(1) {border: 0;box-shadow: 0px 1px 2px #888;}
    .info .title {padding: 5px 0 0 10px;height: 30px;background: #eee;border-bottom: 1px solid #ddd;font-size: 18px;font-weight: bold;}
    .info .close {position: absolute;top: 10px;right: 10px;color: #888;width: 17px;height: 17px;background: url('https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/overlay_close.png');}
    .info .close:hover {cursor: pointer;}
    .info .body {position: relative;overflow: hidden;}
    .info .desc {position: relative;margin: 13px 0 0 90px;height: 75px;}
    .desc .ellipsis {overflow: hidden;text-overflow: ellipsis;white-space: nowrap;}
    .desc .jibun {font-size: 11px;color: #888;margin-top: -2px;}
    .info .img {position: absolute;top: 6px;left: 5px;width: 73px;height: 71px;border: 1px solid #ddd;color: #888;overflow: hidden;}
    .info:after {content: '';position: absolute;margin-left: -12px;left: 50%;bottom: 0;width: 22px;height: 12px;background: url('https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/vertex_white.png')}
    .info .link {color: #5085BB;}
    
    #divpop{width: 100%; height: 100%;}
    #divpop table{height:100%;}
</style>
<script src="${utcp.ctxPath}/resources/user/js/jquery-3.5.1.min.js"></script>
</head>

<body>
	<!-- POPUP start -->
	<div id="divpop">
		<table class="mg0" cellpadding="0" cellspacing="0">
			<thead>
				<tr>
					<td class="tit"><img src="${utcp.ctxPath}/resources/user/image/icon/icon_subtitle.png" alt="서브타이틀 아이콘">약도</td>
				</tr>			
			</thead>
			<tbody>
				<c:choose>
				<c:when test="${empty lctre.addr }">
				<tr>
					<td>주소가 존재하지 않습니다.</td>
				</tr>
				</c:when>
				<c:otherwise>
				<tr>
					<td class="mapCon" id="map"></td>
				</tr>
				</c:otherwise>
				</c:choose>
				
				<tr bgcolor="f7f7f7">
					<td class="mapTxt">
						<p>
						${utcp.convNewLine(lctre.wayCome) }
						</p>
					</td>
				</tr>
			</tbody>

			<tfoot>
				<tr>
					<td class="bg_black mapBtn">
						<p class="tc" style="text-align: center;">
							<a href="javascript:window.close()">닫기</a>
						</p>
					</td>
				</tr>
			</tfoot>
		</table>
	</div>
	
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=9264db505097ae405c35e6a3313fd53e&libraries=services"></script>
<script>


var geocoder = new kakao.maps.services.Geocoder();
var placesService = new kakao.maps.services.Places();
var callback = function(result, status) {
    if (status === kakao.maps.services.Status.OK) {
        
        var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
        mapOption = { 
            center: new kakao.maps.LatLng(result[0].y, result[0].x), // 지도의 중심좌표
            level: 3 // 지도의 확대 레벨
        };
        var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다
        //마커가 표시될 위치입니다 
        var markerPosition  = new kakao.maps.LatLng(result[0].y, result[0].x); 
        //마커를 생성합니다
        var marker = new kakao.maps.Marker({
        position: markerPosition
        });
        //마커가 지도 위에 표시되도록 설정합니다
        marker.setMap(map);
        
    }
};
<c:set var="addressSearch" value="서울시"/>
<c:if test="${not empty lctre.addr}">
<c:set var="addressSearch" value="${lctre.addr}"/>
</c:if>
if('${lctre.addrEtc}'!=''){
	placesService.keywordSearch("${lctre.addrEtc} ", callback);
}else{
	geocoder.addressSearch("${addressSearch}", callback);
}

//팝업리사이즈
var strWidth = $('#divpop').outerWidth() + (window.outerWidth - window.innerWidth);
var strHeight = $('#divpop').outerHeight() + (window.outerHeight - window.innerHeight);
window.resizeTo( strWidth, strHeight );

$('#map').css('width',window.innerWidth);
$('#map').css('height',window.innerHeight-200);
$(window).resize(function(){
//	console.log(this.innerHeight);
	$('#map').css('width',this.innerWidth);
	$('#map').css('height',this.innerHeight-200);
});
//window.resizeTo(1048, 840);
</script>
</body>
</html>