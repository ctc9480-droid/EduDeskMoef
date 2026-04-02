$(function() {
	tabEvt();
});

$(window).ready(function(){
	ctrlWnH();
});

$(window).resize(function(){
	ctrlWnH();
});

/**
 * 새로고침 막기
$(document).keydown(function (e) {
	
    if(e.which === 116){
        if (typeof event == "object") {
            event.keyCode = 0;
        }
        return false;
    }
    
    if(e.which === 82 && e.ctrlKey) {
        return false;
    }
    
    if(event.which == 13) {
		try{
			$("#submit").click();
		}catch(e){
			console.log(e);
		}
		return true;
  	}
});
 */

/**
 * Init Tab Event
 */
function tabEvt() {

	$(".tabLinks").click(function() {
		$(this).parent().children().removeClass("active");
		$(this).addClass("active");
		
		$(this).parent().parent().children(".tabContent").removeClass("active");
		$(this).parent().parent().children("#" + $(this).attr("title")).addClass("active");
	});
}


/**
 * PopUp ON
 * @param objId : PopUp ID
 */
function onLayerPop(objId) {
	var h = (document.body.clientHeight / 2 - $("#" + objId).outerHeight() / 2);
	var w = (document.body.scrollWidth / 2 - $("#" + objId).outerWidth() / 2);
	
	$(".coverPopup").show();
	$("#" + objId).css({"top":h, "left":w}).show();
	
	$("#" + objId + " input:first-child").focus();
}


/**
 * PopUp Off
 * @param objId : PopUp ID
 */
function offLayerPop(objId) {
	
	$(".coverPopup").hide();
	$("#" + objId).hide();
}


/**
 * browser size change
 * Popup Cover Size Sync & Popup Location Sync
 */
function ctrlWnH() {
	$(".coverPopup").height(document.body.scrollHeight);
	$(".coverPopup").width(document.body.scrollWidth);
	
	$(".popup").not(":hidden").each(function() {
		var h = (document.body.clientHeight / 2 - $(this).outerHeight() / 2);
		var w = (document.body.scrollWidth / 2 - $(this).outerWidth() / 2);
		console.log(h + "/" + w);
		$(this).css({"top":h, "left":w});
	});
}


/**
 * 입력값에 스페이스 이외의
 * 의미있는 값이 있는지 체크
 * @param inputElement : tag Object
 * @return null : true / not null : false
 */
function isEmpty(obj) {
	
    if (obj.val() == null || obj.val().replace(/ /gi,'') == '') {
        return true;
    }
    return false;
}


/**
 * TAG 값 자동 설정
 * (Tag ID 와 Json Object의 key 값과
 *  맵핑하여 자동 값 설정)
 * @param object : JSON OBJECT
 */
function setFormVal(object) {
	var keys = Object.keys(object);
	
	for(var i=0; i<keys.length; i++) {
		if($('#' + keys[i]).length != 0) {
			
			if($('#' + keys[i])[0].type == 'radio') {
				$('input:radio[name=' + keys[i] + ']:input[value=' + object[keys[i]] + ']').attr('checked', true);
				
			} else if($('#' + keys[i])[0].type != 'file') {
				$('#' + keys[i]).val(object[keys[i]]);
				$('.' + keys[i]).html(object[keys[i]]);
				
			} else {
				var filePath = object[keys[i]].split('_');
				var fileName = filePath[filePath.length - 1];
				
				$('.' + keys[i]).children('.fileName').html(fileName);
			}
			
		}

	}
}

var $$viewCnt = 0; 
var $$boxWidth=700;						//박스넓이
var $$boxHeight=200;					//박스높이
var $$progressBarObj="";				
var $$expireTime=500;				

function startProgress(msg){
	$$viewCnt++;
	$$blockEa=60;						    // 채워질블럭개수
	var m_boxWidth=400;						// 박스넓이
	var m_boxHeight=200;					// 박스높이
	var speed = 1;							// 막대가 꽉차는데 걸리는 시간 (초)
	var m_expireTime=$$expireTime;					// 최대실행시간
	var m_msgColor = "#E0844F";
	
	var viewProgressBar = document.getElementById("progressBar_" + $$progressBarObj);
	var progressBar = null;
	if(viewProgressBar != null){
		progressBar = viewProgressBar;
		
		var tagObj = document.getElementById("progressBarTag_" + $$progressBarObj);
		$$boxWidth = 670;
		$$boxHeight = 41;
		// 0으로 리턴됨.. 썩을
		// $$boxWidth = tagObj.offsetWidth;
		// $$boxHeight = tagObj.offsetHeight;
		//
		// $$boxWidth = tagObj.clientWidth;
		// $$boxHeight = tagObj.clientHeight;
	}
	else{
		var str = new StringBuffer();
		
		str.append("<table id='tblProgressBar' bgcolor='white' border='0' cellpadding='0' cellspacing='0' style='border:1px solid #DDDDDD;width:100%;height:100%'>");
		str.append("	<tr height='5'> 			 ");
		str.append("		<td></td> 				 ");
		str.append("	</tr> 						 ");
		str.append("	<tr height='20' valign='top'>");
		str.append("		<td>                     ");
		str.append("			<table border='0' cellpadding='0' cellspacing='0' width='100%'> ");
		str.append("				<tr>                                                        ");
		str.append("					<td style='padding-top:5px; padding-left:15px'>           ");
		str.append("						<span id='progressBarMsg' style='font-size:10pt;color:"+m_msgColor+";font-weight:bold'>"+msg+"</span> ");
		str.append("					</td>                                                   ");
		str.append("					<td width='30px' align='right' style='padding-right:10px;'><span style='font-size:9pt;color:#A6A6A6;' onclick=\"closeProgressBar();\">[CLOSE]</span></td>  ");
		str.append("				</tr>            ");
		str.append("			</table>             ");
		str.append("		</td>                    ");
		str.append("	</tr>                        ");
		str.append("	<tr > 						 ");
		str.append("		<td style='padding-left:25px;'><img style='width:270px;height:auto;' src='"+top.getContextPath()+"/images/login/bi.png'  /></td>");
		str.append("	</tr> 						 ");
		str.append("	<tr height='30'>             ");
		str.append("		<td>&nbsp;&nbsp;         ");
		str.append("			<div id='progressBarTag_" + $$progressBarObj + "'>&nbsp;</div> ");
		str.append("		</td>                    ");
		str.append("	</tr>                        ");
		str.append("</table>                         ");
		
		// 화면 가운데 배치
		var winX = document.body.offsetWidth / 2 - parseInt(m_boxWidth) / 2;
		var winY = document.body.offsetHeight / 2 - parseInt(m_boxHeight) / 2;
		
		progressBar = document.createElement("div");
		progressBar.setAttribute("id", "progressBar_" + $$progressBarObj);
		progressBar.style.display = "none";
		progressBar.style.zIndex = "99999999";
		progressBar.style.position="absolute";
		progressBar.style.top=winY+"px";
		progressBar.style.left=winX+"px";
		progressBar.style.width=m_boxWidth+"px";
		progressBar.style.height=m_boxHeight+"px";
		progressBar.innerHTML = str.toString();
		
		$$boxWidth = m_boxWidth;
		$$boxHeight = 30;
	}	
	
	if(viewProgressBar == null) {
		document.body.insertBefore(progressBar,null);
	}
	
	if(progressBar.style.display == "none") {
		progressBar.style.display = "";
	}
	$$currentBlock = -1;
	showProgressBar();
	
	try {
		if($$showInterval != null){
			clearInterval($$showInterval);
		}
	} catch (e) {console.log(e);}
	
	$$showInterval = setInterval(showProgressBar, speed * 1000 /$$blockEa);
	// 일정 시간이 지나면 사라지도록
	setTimeout("closeProgressBar()", m_expireTime * 1000);
}

/*
function startProgress(msg){
	$$viewCnt++;
	$$blockEa=60;						    // 채워질블럭개수
	var m_boxWidth=400;						// 박스넓이
	var m_boxHeight=200;					// 박스높이
	var speed = 1;							// 막대가 꽉차는데 걸리는 시간 (초)
	var m_expireTime=$$expireTime;					// 최대실행시간
	var m_msgColor = "#E0844F";
	
	var viewProgressBar = document.getElementById("progressBar_" + $$progressBarObj);
	var progressBar = null;
	if(viewProgressBar != null){
		progressBar = viewProgressBar;
		
		var tagObj = document.getElementById("progressBarTag_" + $$progressBarObj);
		$$boxWidth = 670;
		$$boxHeight = 41;
		// 0으로 리턴됨.. 썩을
		// $$boxWidth = tagObj.offsetWidth;
		// $$boxHeight = tagObj.offsetHeight;
		//
		// $$boxWidth = tagObj.clientWidth;
		// $$boxHeight = tagObj.clientHeight;
	}
	else{
		var str = new StringBuffer();

		str.append("<table id='tblProgressBar' bgcolor='white' border='0' cellpadding='0' cellspacing='0' style='border:1px solid #DDDDDD;width:100%;height:100%'>");
		str.append("	<tr height='5'> 			 ");
		str.append("		<td></td> 				 ");
		str.append("	</tr> 						 ");
		str.append("	<tr height='20' valign='top'>");
		str.append("		<td>                     ");
		str.append("			<table border='0' cellpadding='0' cellspacing='0' width='100%'> ");
		str.append("				<tr>                                                        ");
		str.append("					<td style='padding-top:5px; padding-left:15px'>           ");
		str.append("						<span id='progressBarMsg' style='font-size:10pt;color:"+m_msgColor+";font-weight:bold'>"+msg+"</span> ");
		str.append("					</td>                                                   ");
		str.append("					<td width='30px' align='right' style='padding-right:10px;'><span style='font-size:9pt;color:#A6A6A6;' onclick=\"closeProgressBar();\">[CLOSE]</span></td>  ");
		str.append("				</tr>            ");
		str.append("			</table>             ");
		str.append("		</td>                    ");
		str.append("	</tr>                        ");
		str.append("	<tr > 						 ");
		str.append("		<td style='padding-left:25px;'><img style='width:270px;height:auto;' src='"+top.getContextPath()+"/images/login/bi.png'  /></td>");
		str.append("	</tr> 						 ");
		str.append("	<tr height='30'>             ");
		str.append("		<td>&nbsp;&nbsp;         ");
		str.append("			<div id='progressBarTag_" + $$progressBarObj + "'>&nbsp;</div> ");
		str.append("		</td>                    ");
		str.append("	</tr>                        ");
		str.append("</table>                         ");

		// 화면 가운데 배치
		var winX = document.body.offsetWidth / 2 - parseInt(m_boxWidth) / 2;
		var winY = document.body.offsetHeight / 2 - parseInt(m_boxHeight) / 2;

		progressBar = document.createElement("div");
		progressBar.setAttribute("id", "progressBar_" + $$progressBarObj);
		progressBar.style.display = "none";
		progressBar.style.zIndex = "99999999";
		progressBar.style.position="absolute";
		progressBar.style.top=winY+"px";
		progressBar.style.left=winX+"px";
		progressBar.style.width=m_boxWidth+"px";
		progressBar.style.height=m_boxHeight+"px";
		progressBar.innerHTML = str.toString();

		$$boxWidth = m_boxWidth;
		$$boxHeight = 30;
	}	
	
	if(viewProgressBar == null) {
		document.body.insertBefore(progressBar,null);
	}
	
	if(progressBar.style.display == "none") {
		progressBar.style.display = "";
	}
	$$currentBlock = -1;
	showProgressBar();
	
	try {
		if($$showInterval != null){
			clearInterval($$showInterval);
		}
	} catch (e) {console.log(e);}
	
	$$showInterval = setInterval(showProgressBar, speed * 1000 /$$blockEa);
	// 일정 시간이 지나면 사라지도록
	setTimeout("closeProgressBar()", m_expireTime * 1000);
}
*/

function showProgressBar(){
	var m_blockClolor= "#E0844F";			//블럭색상
	var m_blockWidth=$$boxWidth/$$blockEa - 2;					//블럭넓이(단위: Pixel)
	var m_blockHeight=$$boxHeight;					//블럭높이(단위: Pixel)
	
	$$currentBlock = $$currentBlock +1;
	//투명도 계산(그라데이션)
	var opacity = $$currentBlock * 100 / $$blockEa;
	var tag = "";
	tag += "<div style='float:left; display:inline;background-Color:"+ m_blockClolor +";color:black;font-size:5pt;";
	tag += ";width:"+m_blockWidth+"px;height:"+m_blockHeight+"px;";
	tag += ";opacity:"+opacity/100+";'></div>";
	tag += "<span style='float:left; display:inline;width:2px'></span>";
	
	var tagObj = document.getElementById("progressBarTag_" + $$progressBarObj);
	
	if($$currentBlock == 0){
		tagObj.innerHTML = tag;
	}
	else{
		tagObj.innerHTML = tagObj.innerHTML+""+tag;
	}
	if($$currentBlock > $$blockEa)
	{
		$$currentBlock = -1;
	}
	
}

function closeProgressBar(){
	try{
		$$viewCnt--;
		if($$viewCnt <= 0){
			clearInterval($$showInterval);
			var progressBar = document.getElementById("progressBar_" + $$progressBarObj);
			progressBar.style.display = "none";
		}
	}
	catch(e){
		
	}
}

function stopProgress(){
	closeProgressBar();
}



function excelExport(url,param,fileNm){
	
	var fileDownLoadFrm = document.createElement("iframe");
	fileDownLoadFrm.setAttribute("name","fileDownLoadFrm");
	fileDownLoadFrm.setAttribute("id","fileDownLoadFrm");
	fileDownLoadFrm.style.zIndex = "99999999";
	fileDownLoadFrm.style.position="absolute";
	fileDownLoadFrm.style.width="0px";
	fileDownLoadFrm.style.height="0px";
	document.body.insertBefore(fileDownLoadFrm,null);	
	var params = "";
	
	if(fileNm == null) fileNm = "excelExport";
	if(param.indexOf("=") == -1){
		params = "&downFileNm="+fileNm;
	}
	else{
		params += param+"&downFileNm="+fileNm;
	}
	startProgress("엑셀 다운로드는 시간이 다소 걸릴 수 있습니다.");
	fileDownLoadFrm.src = url+"?"+getDataEncode(params);
	setTimeout("stopProgress()",2000);
}


function getDataEncode(data)
{
	var rtnSb = new StringBuffer();
	try{
		var dataArray = data.split("&");
		for(var i=0; i< dataArray.length;i++){
			var dataSet = dataArray[i].split("=");
			rtnSb.append("&"+encodeURIComponent(dataSet[0])+"="+encodeURIComponent(dataSet[1]));	
		}
		return rtnSb.toString();
	}catch(e){
		defaultPopOn("Load.getDataEncode: " + e.decription);
		return null;
	}
}


/**
 * 패스워드 변경시 특수문자 체크 
 * @param str
 * @returns {Boolean}
 */
function isPwdSpecialInStr(str){
	
	var regExp = /[!,@,#,$,%,^,&,*,?,_,~]/gi;
	var result = false;
	try{
		if(regExp.test(str)){
			result = true;
		}
	}catch(e){
		result = false;
	}
	return result;
}

/**
 * 대문자가 속해 있는지여부 체크
 * @param str
 * @returns {Boolean}
 */
function isUpperInStr(str){
	var regExp = /[A-Z]/;
	var result = false;
	try{
		if(regExp.test(str)){
			result = true;
		}
	}catch(e){
		result = false;
	}
	return result;
}

/**
 * 소문자가 속해 있는지여부 체크 
 * @param str
 * @returns {Boolean}
 */
function isLowerInStr(str){
	
	var regExp = /[a-z]/;
	var result = false;
	try{
		if(regExp.test(str)){
			result = true;
		}
	}catch(e){
		result = false;
	}
	return result;
}

/**
 * 숫자가 속해 있는지여부 체크
 * @param str
 * @returns {Boolean}
 */
function isNumberInStr(str){
	var regExp = /\d/;
	var result = false;
	try{
		if(regExp.test(str)){
			result = true;
		}
	}catch(e){
		result = false;
	}
	return result;
}

/**
 * from Data jsonObject으로 변환
 */
$.fn.serializeObject = function() {
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
        if (o[this.name] !== undefined) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};

/**
 * 랜덤 문자열 만들기
 */
function getRandomUniqueId(length) {
	return Date.now().toString(36) + Math.random().toString(36).substr(2, length);
}

