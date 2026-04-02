var lang={
	hl:'ko'
	,today:{ko:'오늘',en:'Today'}
	
};
Vue.filter('fltTodayFormat', function (pattern) {
	try{
	  	return moment(new Date()).format(pattern);
	}catch(e){
		return '';
	}
});
Vue.filter('fltLectTimeNm', function (value) {
  if (!value) return ''
  value = value.toString();
  return value.substring(0,2)+':'+value.substring(2,4);
});
Vue.filter('fltLectDtNm', function (value) {
  if (!value) return ''
  value = value.toString();
  return value.substring(0,4)+'.'+value.substring(4,6)+'.'+value.substring(6,8);
});

Vue.filter('fltLang', function (value) {
	try{
	  	return lang[value][lang.hl];
	}catch(e){
		return '';
	}
});
Vue.filter('fltDt2Str', function (value,pattern1,pattern2) {
	try{
	  	return moment(value,pattern1).format(pattern2);
	}catch(e){
		return '';
	}
});
Vue.filter('fltDate2Str', function (date,pattern) {
	try{
		if(UTIL.isEmpty(date)){
			return '-';
		}
	  	return moment(date).format(pattern);
	}catch(e){
		return '';
	}
});
Vue.filter('fltDtimeFormat', function (value,pattern) {
	try{
	  	return moment(value,'YYYYMMDDHHmmss').format(pattern);
	}catch(e){
		return '';
	}
});
//출결코드에 따른 출력표시
Vue.filter('fltAttCd2Str', function (value,type) {
	try{
		if(type=='RollBook'){
			switch (value) {
			case 'O':return '';
			case 'D':return '/';
			case 'Z':return '/';
			case 'X':return 'X';
			default:return '-';
			}
		}else{
			return '';
		}
	}catch(e){
		return '';
	}
});
Vue.filter('fltDt2Dday', function (value,pattern1) {
	try{
		var targetDate = moment(value,pattern1);
		var currentDate = moment();
		var dDay = targetDate.diff(currentDate);
		if(dDay < 0){
			return '-';
		}
		dDay = targetDate.diff(currentDate,'days');
		if(dDay == 0){
			dDay = 'Day';
		}
		return 'D-'+dDay;
	}catch(e){
		return '';
	}
});
function fltCharToHtml(value){
	try{
		return value.replace(/(\n|\r\n)/g, '<br/>')
	}catch(e){
		return value;
	}
}
Vue.filter('fltCharToHtml',function(value){
	try{
		console.log(value);
		return value.replace(/(\n|\r\n)/g, '<br/>')
	}catch(e){
		return value;
	}
});
Vue.filter('fltCurrency', function(value) {
	// 통화 포맷으로 변환하는 로직을 구현합니다.
	var formatter = new Intl.NumberFormat('en-US', {
		style: 'currency',
	    currency: 'KRW'
	});
	return formatter.format(value);
});

Vue.filter('fltRepeatWeekNm', function(repeatWeek2) {
	var result = "";
	try {
		repeatWeek2.slice().sort();
		for (var i = 0; i < repeatWeek2.length; i++) {
			var o = repeatWeek2[i];
			if (result !== "") {
				result += ",";
			}
			if (o === "1") {
				result += "일";
			} else if (o === "2") {
				result += "월";
			} else if (o === "3") {
				result += "화";
			} else if (o === "4") {
				result += "수";
			} else if (o === "5") {
				result += "목";
			} else if (o === "6") {
				result += "금";
			} else if (o === "7") {
				result += "토";
			}
		}
		return result;
	} catch (e) {
		return result;
	}
});
Vue.filter('fltPhoneNumber', function(phoneNumber) {
	var result = "";
	try {
		if (phoneNumber.match(/^\d{3}-\d{3,4}-\d{4}$/)) {
			 var parts = phoneNumber.split('-');
		        return parts[0] + '-****-' + parts[2];
	      } else {
	        return phoneNumber;
	      }
		return result;
	} catch (e) {
		return result;
	}
});
Vue.filter('fltPercentage', function(part,whole) {
	try {
		 if (isNaN(part) || isNaN(whole) || whole === 0) {
	      	return "0%";
		 }
		 var percentage = (part / whole) * 100;
		 if (percentage % 1 === 0) {
		      // If no decimal part, convert to integer
		      return percentage.toFixed(0) + "%";
		 } else {
		      // If there is a decimal part, display it with one decimal place
			 return percentage.toFixed(1) + "%";
		 }
	} catch (e) {
		return "ERROR";
	}
});

Vue.component('comp-datepicker', {
	template : '<input v-datepicker :value="value" type="text" class="input_calendar ip6 tc"/>',
	props: ['value','data_min','data_max'],
    mounted: function() {
        var vm = this;
        
        $(this.$el).datepicker({
            dateFormat: 'yy-mm-dd',
            onSelect: function(dateText) {
                vm.$emit('input', dateText);
            },
            beforeShow: function(input, inst) {
                setTimeout(function() {
                    $(inst.dpDiv).css('z-index', 10000);
                }, 0);
                
                console.log('vm.data_min',vm.data_min);
                if(vm.data_min)$(vm.$el).datepicker('option','minDate', new Date(vm.data_min));
                if(vm.data_max)$(vm.$el).datepicker('option','maxDate', new Date(vm.data_max));
            }
        });
        
    },
    watch: {
        value: function(newVal) {
        	//console.log('watch value',newVal);
            $(this.$el).datepicker('setDate', newVal);
            
        },
    },
    beforeDestroy: function() {
        $(this.$el).datepicker('destroy');
    }
});

Vue.component('comp-user-datepicker', {
	template : '<input v-datepicker :value="value" type="text" class=""  />',
	props: ['value'],
    mounted: function() {
        var vm = this;
        
        $(this.$el).datepicker({
            dateFormat: 'yy-mm-dd',
            changeYear: true,
            changeMonth: true,
            yearRange: '1950:2100',
            onSelect: function(dateText) {
                vm.$emit('input', dateText);
            },
            beforeShow: function(input, inst) {
                setTimeout(function() {
                    $(inst.dpDiv).css('z-index', 10000);
                }, 0);
            }
        });
    },
    watch: {
        value: function(newVal) {
            $(this.$el).datepicker('setDate', newVal);
        }
    },
    beforeDestroy: function() {
        $(this.$el).datepicker('destroy');
    }
});
