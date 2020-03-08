$(document).ready(function(){
	$(".prdRow").off('click').on('click',function(){
		var seq = $(this).data('seq')
		location.href=contextPath + '/prod/view?prdId='+seq;
	})
});

