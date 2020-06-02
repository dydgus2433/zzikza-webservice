$(document).ready(function(){
	$(".btn-default").on("click", function(){
		location.href = '/edit_memberMain'
	})
	
	$(".btn-danger").on("click", function(){
		$.ajax({
			url : "/api/withdrawal",
			method : "post"
		}).done(function(data){
			console.log(data);
			
			if(data){
				if(data.success){
					location.href = '/logout';
				}else{
					alert('탈퇴하지 못했습니다.');
				}
			}
			
			console.log("reviews_list",data);
		}).fail(function(jqXHR) {
			alert(jqXHR.responseJSON.message);
		}).always(function() {
	    	console.info('DONE');
	    });
	})
})