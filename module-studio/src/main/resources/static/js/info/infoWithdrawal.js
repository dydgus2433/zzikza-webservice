$(document).ready(function(){
	console.log("infoW");
	$(".btn-danger").on("click", function(){
		if(confirm("탈퇴 하시겠습니까?")){
			$.ajax({
				url : "/api/studio/withdrawal",
				method : "DELETE"
			}).done(function(data){
					if(data.success){
						alert("탈퇴가 완료되었습니다.");
						location.href = '/logout?returl=/';
					}else{
						alert(data.msg);
					}
			}).always(function() {
		    	console.info('DONE');
		    });
		}
		
	})
})