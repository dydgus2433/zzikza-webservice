$(document).ready(function(){
	$(".prdRow").off('click').on('click',function(){
		var seq = $(this).data('seq')
		location.href= '/prod/view?prdId='+seq;
	})
});

