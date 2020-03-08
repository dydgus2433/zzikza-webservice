$(document).ready(function(){
	console.log("상품목록");
	
	$(".prdRow").off('click').on('click',function(){
		var seq = $(this).data('seq')
		location.href=contextPath + '/request/prod/view?prdId='+seq;
	})
});

