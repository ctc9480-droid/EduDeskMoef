$(document).ready(function(){

    //mobile gnb active
	$('.btn_ham').on('click', function() {
	    $(this).toggleClass('on');
	    $('.bg_modal, .gnbArea_m').toggle();
	    $('body').css('overflow', $(this).hasClass('on') ? 'hidden' : 'auto');
	});

    // mobile lnb show toggle
    $('.gnbArea_m .gnb > li > a').click(function(){

        $(this).next().slideToggle('fast');
        $(this).parents('li').toggleClass('select');
    });


	
    // gnb 컨트롤 관련
    var gnbPc = $('.main_gnbpc');
    gnbPc.on('mouseover focusin' , function(){
        gnbPc.stop().animate({'height' : '350'}, 200);
        gnbPc.addClass('gnb_open');
    });
    gnbPc.on('mouseleave focusout' , function(){
        gnbPc.stop().animate({'height' : '90'}, 200);
        var timer =  setInterval(function () {
            clearInterval(timer);
            gnbPc.removeClass('gnb_open');
        }, 210);
    });
    

  
  
  //function openMo() {
        //document.getElementById("header").style.width = "100%";
        //document.body.style.backgroundColor = "rgba(0,0,0,0.4)";
      //}
  
      //function closeMo() {
        //document.getElementById("header").style.width = "0%";
        //document.body.style.backgroundColor = "white";
      //}

    //sub nav
    //$('.navWrap > ul > li').click(function(){

        //$(this).toggleClass('active');
    //});


    // edu_detail_button //
    $(function() {
		$(".eduDtTab li a").click(function(event){
		event.preventDefault();
		$('html,body').animate({scrollTop:$(this.hash).offset().top - 122}, 800);
		});
	});


});

// header search form //
function openSch() {
    document.getElementById("hdSchForm").style.height = "120px";
    }

    function closeSch() {
    document.getElementById("hdSchForm").style.height = "0%";
    }