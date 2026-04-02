$(function () {

    $(".tab_content").hide();
    $(".tab_content:first").show();

    $("ul.tabs li").click(function () {
        $("ul.tabs li").removeClass("active");
        $(this).addClass("active");
        $(".tab_content").hide()
        var activeTab = $(this).attr("rel");
        $("#" + activeTab).fadeIn()
    });
});


//팝업시작
	function wrapWindowByMask(){
		var maskHeight = $(document).height();  
		var maskWidth = $(window).width();  

		$('#mask').css({'width':maskWidth,'height':maskHeight});  
		$('#mask').fadeIn(400);      
		$('#mask').fadeTo("slow",0.8);    
	}

	function wrapWindowByDefaultMask(){
		var maskHeight = $(document).height();  
		var maskWidth = $(window).width();  
		
		$('#default-mask').css({'width':maskWidth,'height':maskHeight});  
		$('#default-mask').fadeIn(400);      
		$('#default-mask').fadeTo("slow",0.8);    
	}
	
	function popOn(targetClassNm) {
		wrapWindowByMask();
		$('.' + targetClassNm).show();
	}
	function popOff() {
		$(".default-pop .pop-txt").html();
		$('#mask, .window').hide();	
	}

	function defaultPopOn(msg) {
		$(".default-pop .pop-txt").html(msg);
		
		wrapWindowByDefaultMask();
		$('.default-pop').show();				
	}
	function defaultPopOff() {
		$(".default-pop .pop-txt").html();
		$('#default-mask, .default-window').hide();	
	}

	$(document).ready(function(){
//팝업 열기		
		$('.id-input-btn').click(function(e){
			e.preventDefault();
			wrapWindowByMask();
			$('.id-input').show();
		});
		$('.idpwd-input-btn').click(function(e){
			e.preventDefault();
			wrapWindowByMask();
			$('.idpwd-input').show();				
		});	
		$('.qr-down-btn').click(function(e){
			e.preventDefault();
			wrapWindowByMask();
			$('.qr-down').show();				
		});		
		$('.mobile-reg-btn').click(function(e){
			e.preventDefault();
			wrapWindowByMask();
			$('.mobile-reg').show();				
		});		
		$('.reg-ok-pop-btn').click(function(e){
			e.preventDefault();
			wrapWindowByMask();
			$('.reg-ok-pop').show();				
		});	
		$('.auth-pop-btn').click(function(e){
			e.preventDefault();
			wrapWindowByMask();
			$('.auth-pop').show();				
		});		
		$('.ioc-select-btn').click(function(e){
			e.preventDefault();
			wrapWindowByMask();
			$('.ioc-select').show();				
		});			
		$('.ioc-fingerp-btn').click(function(e){
			e.preventDefault();
			wrapWindowByMask();
			$('.ioc-finger').show();				
		});	
		$('.ioc-idp-btn').click(function(e){
			e.preventDefault();
			wrapWindowByMask();
			$('.ioc-id').show();				
		});			
		
		
		
		
		
		
//팝업닫기
		$('.close').click(function (e) {  
		    e.preventDefault();
		    $(".default-pop .pop-txt").html();
		    $('#mask, .window').hide();  
		});     		
		$('.default-close, .default-pop-ok').click(function (e) {  
			e.preventDefault();
			$(".default-pop .pop-txt").html();
			$('#default-mask, .default-window').hide();  
		});     		
//영역외 클릭시 닫기
		$('#mask').click(function () {  
		    $(this).hide();
		    $(".default-pop .pop-txt").html();
		    $('.window').hide();  
		});   
		$('#default-mask').click(function () {  
			$(this).hide();
			$(".default-pop .pop-txt").html();
			$('.default-window').hide();  
		});   
	});

