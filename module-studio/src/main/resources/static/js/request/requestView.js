$(document).ready(function(){

	$("#listBtn").off('click').on('click',function(){
		location.href = "/request/list";
	});
	

	$(".btn_req").off('click').on('click',function(){
		var seq = $(this).data('seq')
		location.href= '/request/product/write?id='+seq;
	});
})