$(function() {

	// rollover img
	var overimg = $("img.overImg");
	$(overimg).mouseenter(function() {
		$(this).attr("src", $(this).attr("src").replace("_off","_on"));
	});
	$(overimg).mouseout(function() {
		$(this).attr("src", $(this).attr("src").replace("_on", "_off"));
	});

	$(window).load(function(){

		// scroll : side
		// $(".side-nav").mCustomScrollbar({
		// 	theme:"minimal-dark",
		// 	scrollInertia: 200,
		// 	mouseWheel:{ scrollAmount: 160 }
		// });

		// scroll : rollbook
		// $(".studentStatus").mCustomScrollbar({
		// 	theme:"minimal-dark",
		// 	axis:"x",
		// 	scrollInertia: 200,
		// 	mouseWheel:{ scrollAmount: 160 }
		// });

		// select
		$('select').material_select();

		// dropdwon menu
		$(".dropdown-button").dropdown();

		// modal popup
		$('.modal-trigger').leanModal();

		// side
		$(".showStudent, .showProfessor").sideNav({
			menuWidth: 300
		});
		$('.closeSide').click(function() {
			$(".showStudent").sideNav('hide');
		});
			
	});

	// manage class

	$('.manageClassList > ul > li p.subject').click(function() {
		var thisLecture = $(this).parent('li');
		var thisLectureList = thisLecture.children('dl.lectureList');
		var thisLectureNum = thisLectureList.children('dd').length;
		var thisLectureListH = thisLectureNum*36;
		console.log(thisLectureListH);
		thisLecture.toggleClass("active");
		if(thisLecture.hasClass("active")){
			thisLectureList.animate({
				height:thisLectureListH
			}, { duration:100});
		} else {
			thisLectureList.animate({
				height:0
			}, { duration:100});
		};
	});

	// rollbook

	var rollbookTableWidth = $('.statusRollBook .studentStatus table th').length * 30;
	$('.statusRollBook .studentStatus table').css({
		width:rollbookTableWidth
	})

	//  class list day : 출석 시간별 나눠 부르기

	var checkAll = $('.checkAll');
	var checkByTime = $('.checkByTime').hide();
	var checkAllBtn = $('a.checkAllBtn');
	var checkByTimeBtn = $('a.checkByTimeBtn');
	checkByTimeBtn.click(function(e) {
		e.preventDefault();
		checkAll.removeClass('active').slideUp('fast');
		checkByTime.addClass('active').slideDown('fast');
	});
	checkAllBtn.click(function(e) {
		e.preventDefault();
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
		if( winScroll > topTabOffset ) {
			topWrap.addClass('fix');
		}else{
			topWrap.removeClass('fix');
		}
	});
	}

	
	var topWrap = $('.topWrapper');
	var topTab = $(".topWrapper > .fixtr");
	if(topTab.offset()){
	var topTabOffset = topTab.offset().top;
	$(window).scroll(function() {
		var winScroll = $(this).scrollTop();
		if( winScroll > topTabOffset ) {
			topWrap.addClass('fixed');
		}else{
			topWrap.removeClass('fixed');
		}
	});
	}
	
});


