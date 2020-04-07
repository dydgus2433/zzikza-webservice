$(document).ready(function(){
	console.log("요청관리목록");
	
	$(".btn_insert").off('click').on('click',function(){
		const seq = $(this).data('seq');
		location.href= '/request/product/write?id='+seq;
	});
	
	$(".tCont tr").children("td").not(":last-child").on("click", function(){
		console.log($(this));
		const seq = $(this).parent().data("seq");
		location.href= '/request/view?id='+seq;
	});
});

