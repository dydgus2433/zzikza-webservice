$(document).ready(function(){
	console.log("문의관리목록");
	
	$(".btn_insert").off('click').on('click',function(e){
		$("#popup").html("");
		console.log(e);
		var seq = $(this).data('seq');
		
		$.ajax({
			url :  '/question/popup',
			data : {id : seq},
			beforeSend  : function(xmlHttpRequest){
				xmlHttpRequest.setRequestHeader("AJAX", "true"); // ajax 호출을  header에 기록
			}
		}).done(function(result){
			console.log(result);
			$("#popup").html(result);
			$(".popup_qna").show();
		}).fail(function(jqXHR, textStatus, errorThrown) {
	    	alert(jqXHR.responseJSON.message);
	    }).always(function() {
	    	console.log('DONE');
	    });	
	})
});

