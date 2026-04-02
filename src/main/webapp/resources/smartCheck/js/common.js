function init_script(){
	$('.modal-trigger').leanModal({
		dismissible: false, // Modal can be dismissed by clicking outside of the modal
		opacity: .5, // Opacity of modal background
		in_duration: 300, // Transition in duration
		out_duration: 200, // Transition out duration
		ready: function() {  }, // Callback for Modal open
		complete: function() {  } // Callback for Modal close
	});
	
	// dropdwon menu
	$(".dropdown-button").dropdown();
	// select
	$('select').material_select();

	var checkAll = $('.checkAll');
	var checkByTime = $('.checkByTime').hide();
	var checkAllBtn = $('a.checkAllBtn');
	var checkByTimeBtn = $('a.checkByTimeBtn');
	checkByTimeBtn.click(function() {
		checkAll.removeClass('active').slideUp('fast');
		checkByTime.addClass('active').slideDown('fast');
	});
	checkAllBtn.click(function() {
		checkByTime.removeClass('active').slideUp('fast');
		checkAll.addClass('active').slideDown('fast');
	});
	

	// menu fix
	var topWrap = $('.topWrapper');
	var topTab = $(".topWrapper > .tab");
	if(topTab.offset()){
		var topTabOffset = topTab.offset().top;
		$(window).scroll(function() {
			var winScroll = $(this).scrollTop();
			//onsole.log(winScroll);
			//onsole.log(topTabOffset);
			if( winScroll > topTabOffset ) {
				topWrap.addClass('fix');
			}else{
				topWrap.removeClass('fix');
			}
		});
	}

	// rollbook
	var rollbookTableWidth = ($('.statusRollBook .studentStatus table tr.time th').length+1) * 35;
	$('.statusRollBook .studentStatus table').css({
		//width:rollbookTableWidth
	});
}
init_script();
String.prototype.string = function(len){var s = '', i = 0; while (i++ < len) { s += this; } return s;};
String.prototype.subFormat = function(len){return "0".string(len - this.length) + this;};
Number.prototype.subFormat = function(len){return this.toString().subFormat(len);};
Date.prototype.format = function(f) {
	if (!this.valueOf()) return "";
	var weekName = ["일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일"];
	var d = this;
	return f.replace(/(yyyy|yy|MM|dd|E|hh|mm|ss|a\/p)/gi, function($1) {
		switch ($1) {
			case "yyyy": return d.getFullYear();
			case "yy": return (d.getFullYear() % 1000).subFormat(2);
			case "MM": return (d.getMonth() + 1).subFormat(2);
			case "dd": return d.getDate().subFormat(2);
			case "E": return weekName[d.getDay()];
			case "HH": return d.getHours().subFormat(2);
			case "hh": return ((h = d.getHours() % 12) ? h : 12).subFormat(2);
			case "mm": return d.getMinutes().subFormat(2);
			case "ss": return d.getSeconds().subFormat(2);
			case "a/p": return d.getHours() < 12 ? "오전" : "오후";
			default: return $1;
		}
	});
};


//내메뉴 새글 셋팅
function setMyMenuNewInfo(){
	/*
	 */
	var url = '';
    if($('body.student').length > 0){
    	url = '/stud/new/info';
    }else if($('body.professor').length > 0){
    	url = '/prof/new/info';
    }
	if(url != '') {
		$.ajax({
			type: "POST",
			url: url,
			success: function (data) {
				var obj = jQuery.parseJSON(data);
				var result = obj.result;
				if (result == 'SUCCESS') {
					var sum = obj.cnt_message + obj.cnt_question;
					if (sum == 0) $('#myNewCount').css('display', 'none');
					else {
						$('#myNewCount').css('display', '');
						$('#myNewCount').text(sum);
					}
					if (obj.cnt_message > 0) $('#newMessage').css('display', '');
					else $('#newMessage').css('display', 'none');
					if (obj.cnt_question > 0) $('#newQuestion').css('display', '');
					else $('#newQuestion').css('display', 'none');
				}
			},
			error: function (result) {
			}
		});
	}
}

function calculateDistance(_obj){
	$(_obj).each(function(){
		var loc = $(this).attr('geolocate');
		var locArr = loc.split(':');
		var stuLoc = locArr[0];
		var limit = locArr[1]*1;
		var proLoc = $('#reglocate').val();
		
		if(proLoc==''){
			return false;
		}
		
		var proLocArr = proLoc.split('|');
		var stuLocArr = stuLoc.split('|');
		
		var proLocX = proLocArr[0];
		var proLocY = proLocArr[1];
		var stuLocX = stuLocArr[0];
		var stuLocY = stuLocArr[1];
		
		var distanceX = proLocX-stuLocX;
		var distanceY = proLocY-stuLocY;
		
		//onsole.log(proLoc+':'+stuLoc);
		if(proLoc&&stuLoc){
			var dis = calculateXY(proLocX*1,proLocY*1,stuLocX*1,stuLocY*1);
			//onsole.log(dis);
			if(dis>limit){
				$(this).css('display','');
			}
		}else{
			$(this).css('display','');
		}
	});
	
}

function calculateXY(lat1, lon1, lat2, lon2) {
    var R = 60*1.1515*1.609344*100000.0; // km
    var dLat = (lat2-lat1).toRad();
    var dLon = (lon2-lon1).toRad(); 
    var a = Math.sin(dLat/2) * Math.sin(dLat/2) +
            Math.cos(lat1.toRad()) * Math.cos(lat2.toRad()) * 
            Math.sin(dLon/2) * Math.sin(dLon/2); 
    var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
    var d = R * c;
    return d;
  }


//화면디자인용 스크립트 교수,학생 공통으로 쓰자
let viewObj = {
	weekClassArr:['sun', 'mon', 'tue', 'wed', 'thu', 'fri', 'sat']
	,setDayPosition:function (){
	  	$(".classListWeek table.calendar tr th:gt(0)").each(function(index){
	     	//박스 맞추기
	  		var eleLeft = $(this).position().left;
	      	$(".classListWeek > ul.class li."+viewObj.weekClassArr[index+1]).css("left",eleLeft);
	      	
	      	//시간리스트  하일라이트 처리
	      	if($(this).hasClass('active')){
	      		$(".classListWeek > table.calendar td."+viewObj.weekClassArr[index+1]).addClass('active');
	      	}
	    });
	}
	,setBoxPosition:function(){
		var bh=30;
		var bt=53
		//시간 영역 적용
		var date = new Date();date.setHours(8,0,0,0);
		var date1 = new Date();
		var date2 = new Date();
		$('.lecture-time-list').each(function(){
			//요일 클래스추가
			var lectureTimeData = $(this).attr('data-lecture-time');
			var ltdArr = lectureTimeData.split(',');
			var dayOfWeek = viewObj.weekClassArr[new Date(ltdArr[0].substring(0,4)+'-'+ltdArr[0].substring(4,6)+'-'+ltdArr[0].substring(6,8)).getDay()];
			$(this).addClass(dayOfWeek);
			
			//시간에 따라 박스 크기,위치 조정
			date1.setHours(ltdArr[1].substring(0,2), ltdArr[1].substring(2,4),0,0);
			date2.setHours(ltdArr[2].substring(0,2), ltdArr[2].substring(2,4),0,0);
			var top = date1.getTime()-date.getTime();
			top=(53+(top/120000));
			$(this).css('top',top);
			var height=date2.getTime()-date1.getTime();
			height=((height/120000));
			$(this).css('height',height);
			
		});		
	}
	,setPaging:function(classNm,formObj){
		var $node=$('.'+classNm);
		var tc = (formObj.tc==undefined?0:formObj.tc)*1;;
		var lc = (formObj.lc==undefined?5:formObj.lc)*1;;
		var pc = 5*1;
		var pn = (formObj.pn==undefined?1:formObj.pn)*1;
		
		/* 기본형 미완성
		var lastPn = Math.floor((tc-1)/lc)+1;
		var startPn = (Math.floor((pn-1)/pc)*pc)+1;
		var endPn = startPn+pc-1;
		endPn = endPn>lastPn?lastPn:endPn;
		*/
		/* 자동 이동형 */
		var lastPn = Math.floor((tc-1)/lc)+1;
		var startPn = pn-Math.floor(pc/2);startPn=startPn<1?1:startPn;
		var endPn = startPn+pc-1;
		endPn = endPn>lastPn?lastPn:endPn;
		
		var html = '<div class="page">\n';
		html+=' <a href="#none" pn="1"><img src="/resources/images/common/arrow1.png" alt="맨앞" class="arrow1" /></a> \n';
		/*html+=' <a href="#none" pn="1"><img src="/resources/images/common/arrow2.png" alt="이전" class="arrow2" /></a> \n';*/
		for(var i=startPn;i<=endPn;i++){
			html+=' <a href="#none" pn="'+i+'"><span>'+i+'</span></a> \n';
		}
		/*html+=' <a href="#none" pn="1"><img src="/resources/images/common/arrow3.png" alt="다음" class="arrow3" /></a> \n';*/
		html+=' <a href="#none" pn="'+lastPn+'"><img src="/resources/images/common/arrow4.png" alt="맨끝" class="arrow4" /></a> \n';
		html+=' </div> \n';
		$node.html(html);
		//클릭이벤트
		$node.find('a').click(function(){
			var pn1=$(this).attr('pn');
			formObj.pn=pn1;
			tabView(formObj.tab);
		});
		
		
	}
};
let GeoLoc = {
	setLocation:function(){
		/*if (navigator.geolocation) { // GPS를 지원하면
			navigator.geolocation.getCurrentPosition(function(position) {
				onsole.log(position.coords.latitude + ' ' + position.coords.longitude);
		    }, function(error) {
		    	onsole.log(error);
		    }, {
		    	enableHighAccuracy: false,
		    	maximumAge: 0,
		    	timeout: Infinity
		    });
		} else {
		    onsole.log('GPS를 지원하지 않습니다');
		}*/
		
		if (navigator.geolocation) { // GPS를 지원하면
			navigator.geolocation.watchPosition(function(position) {
				GeoLoc.regLocate = position.coords.latitude + ',' + position.coords.longitude;
				//onsole.log('success : '+GeoLoc.regLocate);
		    }, function(error) {
		    	GeoLoc.regLocate = '';
		    }, {
		    	enableHighAccuracy: false,
		    	maximumAge: 0,
		    	timeout: Infinity
		    });
		} else {
			GeoLoc.regLocate = '';
		}
		
	},
	getDistance:function(loc1,loc2){
		var result=0;
		if(typeof loc1!=='string' || loc1==''){
			result= 0;
		}else if(typeof loc2!=='string' || loc2==''){
			result= 99999999;
		}else{
			var lat1=loc1.split(',')[0]*1;
			var lon1=loc1.split(',')[1]*1;
			var lat2=loc2.split(',')[0]*1;
			var lon2=loc2.split(',')[1]*1;
			
			//onsole.log(loc1+' '+loc2);
			var R = 6371*1000; // km
		    var dLat = (lat2-lat1).toRad();
		    var dLon = (lon2-lon1).toRad(); 
		    var a = Math.sin(dLat/2) * Math.sin(dLat/2) +
		            Math.cos(lat1.toRad()) * Math.cos(lat2.toRad()) * 
		            Math.sin(dLon/2) * Math.sin(dLon/2); 
		    var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
		    var d = R * c;
		    result=d;
		}
		//onsole.log(result);
		return result;
	},
	regLocate:''
}

//??
Number.prototype.toRad = function() {
    return this * Math.PI / 180;
}
//form to object plugin
jQuery.fn.serializeObject = function() { var obj = null; try { if(this[0].tagName && this[0].tagName.toUpperCase() == "FORM" ) { var arr = this.serializeArray(); if(arr){ obj = {}; jQuery.each(arr, function() { obj[this.name] = this.value; }); } } }catch(e) { alert(e.message); }finally {} return obj; }

