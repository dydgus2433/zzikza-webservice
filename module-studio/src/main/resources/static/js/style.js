
/* === 전체메뉴 보기 === */  /* 20190806 */
$(document).ready(function(){	
//	$(".snb_menu_s").hide();
	$(".snb_menu li a").off('click').on('click',function() {
		$(this).next(".snb_menu_s").slideToggle();
	});
	
	$(".all_menu").off('click').on('click',function() {
		$("#snb").css('opacity', '1').css('z-index', '10');
		$("#snb").append('<div class="outline"></div>');
		$(this).hide();
	});
	$(".all_close").off('click').on('click',function() {
		$("#snb").css('opacity', '0').css('z-index', '-1');
		$(".outline").remove();
		$(".all_menu").show();
	});
});
$(window).resize(function(){ 
   if (window.matchMedia('(max-width:1024px)').matches) { 
		$("#snb").css('opacity', '0').css('z-index', '-1');
		$(".all_menu").show();
		$(".outline").remove();
   }
   else {
		$("#snb").css('opacity', '1').css('z-index', '10');
		$(".all_menu").hide();
		$(".outline").remove();
   }
});


/* === 팝업 === */
$(document).ready(function() {
	$(".popup").hide();
	$(".btn_book_new").off('click').on('click',function () {
		$(".popup_book").show();
	});

	$(".btn_qna_view").off('click').on('click',function () {
		$(".popup_qna").show();
	});

	$(".btn_popup_close").off('click').on('click',function () {
		$(".popup").hide();
	});

});

/* === height === */ /* 20190806 */
$(window).load(function(){
	$('.container').css('height', $(document).height() - '76');
	$('.container>div').css('height', $('.container').height());
	$(document).resize(function() {
		$('.container').css('height', $(document).height() - '76');
		$('.container>div').css('height', $('.container').height());
	});
});

/* === datepicker === */
$(function() {
    $( ".datepicker" ).datepicker({
		dateFormat: "yy-mm-dd",
    });
});

/* === multi === */
$(function() {
    $(".multi li").off('click').on('click',function () {
		$(this).toggleClass("active");
    });
});