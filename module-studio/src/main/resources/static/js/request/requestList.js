$(document).ready(function(){
	console.log("요청관리목록");
	
	$(".btn_insert").off('click').on('click',function(){
		var seq = $(this).data('seq')
		location.href=contextPath + '/request/prod/view?reqId='+seq;
	});
	
	$(".tCont tr").children("td").not(":last-child").on("click", function(){
		console.log($(this));
		var seq = $(this).parent().data("seq");
		location.href=contextPath + '/request/view?reqId='+seq;
	});
});

