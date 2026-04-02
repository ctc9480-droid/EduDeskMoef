var UTIL = {
	checkFileSize:function(node,max){
		var result = {rst:1,msg:''};
		
		var _$obj=node;
		if(!_$obj[0].files[0]){
			return result;
		}
		/* var ext = _$obj.val().split(".").pop().toLowerCase();		    
		if($.inArray(ext, ["jpg", "jpeg", "png", "gif", "bmp", "pdf"]) == -1) {
			alert("첨부파일은 이미지 파일만 등록 가능합니다.");
			_$obj.val("");
			return false;
		} */
		
		var maxSize = max * 1024 * 1024; // MB
		var fileSize = _$obj[0].files[0].size;
		if(fileSize > maxSize){
			result.msg='첨부파일 사이즈는 '+max+'MB 이내로 등록 가능합니다.';
			result.rst=0;
			_$obj.val("");
			return result;
		}
		
		return result;
	},
	checkFileType:function(node,typeArr){
		var result = {rst:1,msg:''};
		
		var _$obj=node;
		if(!_$obj[0].files[0]){
			return result;
		}
		var ext = _$obj.val().split(".").pop().toLowerCase();		    
		console.log($.inArray(ext, typeArr));
		if($.inArray(ext, typeArr) == -1) {
			
			var typeNms = '';
			for(var i in typeArr){
				if(i>0){
					typeNms+=',';
				}
				typeNms+=typeArr[i];
			}
			result.msg='첨부파일은 '+typeNms+' 가능합니다.';
			result.rst=0;
			_$obj.val("");
			return result;
		}
		
		return result;
	},
	isMobile:function(){
		return /Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent);
	},
	getDt2Str : function(dt,pattern){
		var r = moment(dt).format(pattern);
		return r;
	},
	getWeekDates: function (inputDate) {
		// 입력된 날짜의 요일을 가져옴 (0: 일요일, 1: 월요일, ..., 6: 토요일)
	    var inputDay = inputDate.getDay();
	    
	    // 입력된 날짜를 기준으로 이번 주 일요일 날짜를 구함
	    var startOfWeek = new Date(inputDate);
	    startOfWeek.setDate(inputDate.getDate() - inputDay);
	    
	    // 이번 주 각 요일의 날짜를 구함
	    var daysOfWeek = [];
	    for (var i = 0; i < 7; i++) {
	        var day = new Date(startOfWeek);
	        day.setDate(startOfWeek.getDate() + i);
	        
	        // 'YYYYMMDD' 형식의 문자열로 변환하여 배열에 추가
	        var year = day.getFullYear();
	        var month = ('0' + (day.getMonth() + 1)).slice(-2); // 월은 0부터 시작하므로 1을 더해줌
	        var date = ('0' + day.getDate()).slice(-2);
	        daysOfWeek.push(year + month + date);
	    }
	    
	    return daysOfWeek;
	},
	getFormattedDate: function (date,format) {
	    // 오늘 날짜를 가져옴
	    var today = new Date(date);

	    // 년, 월, 일을 추출
	    var year = today.getFullYear();
	    var month = ('0' + (today.getMonth() + 1)).slice(-2); // 월은 0부터 시작하므로 1을 더해줌
	    var day = ('0' + today.getDate()).slice(-2);

	    // 형식에 따라 날짜를 포맷팅
	    switch(format) {
	        case 'YYYY-MM-DD':
	            return year + '-' + month + '-' + day;
	        case 'YYYY/MM/DD':
	            return year + '/' + month + '/' + day;
	        case 'DD-MM-YYYY':
	            return day + '-' + month + '-' + year;
	        case 'DD/MM/YYYY':
	            return day + '/' + month + '/' + year;
	        case 'MM-DD-YYYY':
	            return month + '-' + day + '-' + year;
	        case 'MM/DD/YYYY':
	            return month + '/' + day + '/' + year;
	        case 'YYYYMMDD':
	            return year + month + day;
	        default:
	            throw new Error('지원되지 않는 형식입니다: ' + format);
	    }
	},
	isEmpty: function (value) {
	    var r = (value === null || value == null || value === undefined  || value == undefined || value === '');
	    return r;
	},
	isEmpty2: function (list) {
		for(var i in list){
	    	var value = list[i];
	    	var r = (typeof value === null ||value == null || value === undefined || value === '');
	    	if(r){
	    		return true;
	    	}
	    }
	    return false;
	},
	parseDate: function (dateString, format) {
	    const date = moment(dateString, format, true);

	    if (date.isValid()) {
	        return date.toDate();
	    } else {
	        throw new Error("Invalid date format. Please use one of the following formats: ");
	    }
	},
	calcBetweenMin: function(startDe,endDe){
		// null 또는 undefined 체크
	    if (startDe == null || endDe == null) {
	        throw new Error("Both startDe and endDe must be valid Date objects.");
	    }

	    // 두 매개변수가 Date 객체인지 확인
	    if (!(startDe instanceof Date) || !(endDe instanceof Date)) {
	        throw new Error("Both startDe and endDe must be valid Date objects.");
	    }

	    // 두 날짜 사이의 차이를 밀리초 단위로 계산
	    const diffMs = endDe - startDe;

	    // 밀리초를 분으로 변환
	    const diffMinutes = diffMs / (1000 * 60);

	    return diffMinutes;
	},
	formatMin2Str: function(min,format){
		// null 또는 undefined 체크
	    if (min == null) {
	        throw new Error("min must be provided.");
	    }

	    // min 값이 숫자인지 확인
	    if (typeof min !== 'number' || min < 0) {
	        throw new Error("min must be a non-negative number.");
	    }

	    // 60분 이하일 경우 '00분' 형식으로 반환
	    if (min < 60) {
	        return min + "분";
	    }

	    // 60분 이상일 경우 '00시간 00분' 형식으로 반환
	    const hours = Math.floor(min / 60);
	    const minutes = min % 60;
	    return hours + "시간 " + minutes + "분";
	},
	validateDateTime: function(dateStr, format) {
	    let regex, year, month, day, hour, minute;

	    if (format === "YYYYMMDDHHmm") {
	        regex = /^\d{14}$/;
	    } else if (format === "YYYYMMDD") {
	        regex = /^\d{8}$/;
	    } else if (format === "YYYYMM") {
	        regex = /^\d{6}$/;
	    } else {
	        return false; // 지원하지 않는 포맷
	    }

	    // 정규식 검사 (형식에 맞는지 확인)
	    if (!regex.test(dateStr)) {
	        return false;
	    }

	    // 연, 월 추출
	    year = parseInt(dateStr.substring(0, 4), 10);
	    month = parseInt(dateStr.substring(4, 6), 10);

	    // 월 범위 체크 (1~12)
	    if (month < 1 || month > 12) return false;

	    if (format === "YYYYMMDDHHmm" || format === "YYYYMMDD") {
	        day = parseInt(dateStr.substring(6, 8), 10);
	        let lastDay = new Date(year, month, 0).getDate();
	        if (day < 1 || day > lastDay) return false;
	    }

	    if (format === "YYYYMMDDHHmm") {
	        hour = parseInt(dateStr.substring(8, 10), 10);
	        minute = parseInt(dateStr.substring(10, 12), 10);
	        if (hour < 0 || hour > 23) return false;
	        if (minute < 0 || minute > 59) return false;
	    }

	    return true;
	},
	random: function(length) {
		var chars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
		  var str = '';

		  for (var i = 0; i < length; i++) {
		    str += chars.charAt(Math.floor(Math.random() * chars.length));
		  }

		  return str;
	},
}