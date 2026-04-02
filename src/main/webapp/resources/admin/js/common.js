 $(function(){

	/* side_bar_sub_menu */
	$(".side_nav > li > a").click(function(){
	if($(this).next("ul").css("display")=="block"){
		$(this).next("ul").next("img").attr({"src":"/resources/admin/images/plus.png"})
		$(this).next("ul").stop().slideUp(500);
		}else{
			$(".side_nav > li > a").next("ul").stop().slideUp(500);
			$(".side_nav > li > a").next("ul").next("img").attr({"src":"/resources/admin/images/plus.png"})
			$(this).next("ul").next("img").attr({"src":"/resources/admin/images/minus.png"})
		$(this).next("ul").stop().slideDown(500);
			}
	});
	/* side_bar_sub_menu */
	
	/* side_bar_open_close */
	$('.side_bar_close').click(function(){
		$('body').addClass('fix_sidebar');
	});
	$('.side_bar_open').click(function(){
		$('body').removeClass('fix_sidebar');
	});
	
    $(function () {
        $(window).bind("load resize", function () {
            width = (this.window.innerWidth > 0) ? this.window.innerWidth : this.screen.width;
            if (width < 1170) {
                $('body').addClass('fix_sidebar');
            }
            else {
                var checkMiniSidebar = localStorage.getItem('mini-sidebar');

                if (checkMiniSidebar == "yes" || checkMiniSidebar == "") {

                    $('body').addClass('fix_sidebar');
                } else {
                    $('body').removeClass('fix_sidebar');
                }
            }
        });
    });

	//function myFunction() {
	//  if (window.matchMedia("(max-width: 1024px)").matches) {
	//	$('body').removeClass('fix_sidebar');
	//}
	/* side_bar_open_close */
});