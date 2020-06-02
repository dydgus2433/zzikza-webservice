$(document).ready(function() {

    $('.mn').click(function(){
       $('.header .qmn').slideToggle(); 
    })
    $('.mmn').click(function(){
       $('.header .qmn').slideToggle(); 
    })

    $('.searchBtn').click(function(){
        $('.searchBox').slideToggle();              
    });    

    $('.searchBox').mouseleave(function(){
        $('.searchBox').hide();
        $('.autoWord').removeClass('on'); 
    });
    $('a.closePop').click(function(){
        $('.searchBox').hide();
        $('.autoWord').removeClass('on'); 
    });

    $('.searchBox input[type=text]').click(function(){
        $('.autoWord').addClass('on');                
    });
    $('.autoWord').click(function(){
        $('.autoWord').removeClass('on');                
    });

	$('.sortBox a:nth-child(1)').click(function(){
        $(this).addClass('on');
		$(this).toggleClass('up');
		$('.sortBox a:nth-child(2)').removeClass('on');
		$('.sortBox a:nth-child(3)').removeClass('on');
    });

	$('.sortBox a:nth-child(2)').click(function(){
        $(this).addClass('on');
		$(this).toggleClass('up');
		$('.sortBox a:nth-child(1)').removeClass('on');
		$('.sortBox a:nth-child(3)').removeClass('on');
    });

	$('.sortBox a:nth-child(3)').click(function(){
        $(this).addClass('on');
		$(this).toggleClass('up');
		$('.sortBox a:nth-child(1)').removeClass('on');
		$('.sortBox a:nth-child(2)').removeClass('on');

    });
    

//    $(window).scroll(function(){  
//		$('#scroll').animate({top:$(window).scrollTop() + 153 +"px" },{queue: false, duration: 350});    
//	}); 

	//When the user scrolls down 20px from the top of the document, show the button
	window.onscroll = function() {scrollFunction()};

	
});

function scrollFunction() {
	var mybutton = document.getElementById("myBtn");
	if (document.body.scrollTop > 20 || document.documentElement.scrollTop > 20) {
	 mybutton.style.display = "block";
	} else {
	 mybutton.style.display = "none";
	}
	}

	//When the user clicks on the button, scroll to the top of the document
	function topFunction() {
	var mybutton = document.getElementById("myBtn");
//	document.body.scrollTop = 0; // For Safari
//	document.documentElement.scrollTop = 0; // For Chrome, Firefox, IE and Opera
//	
	$('html, body').animate({scrollTop: '0'}, 500);
	
	}

/*
$(document).mouseup(function (e) {

    var container = $(".searchBox");
    if (!container.is(e.target) && container.has(e.target).length === 0){
        container.css("display","none");
    }	

});
*/
