$(document).ready(function(){
	console.log("infoW");
	$(".btn-danger").on("click", function(){
		if(confirm("가입된 정보를 관리하기 힘드실 수 있습니다. 탈퇴 하시겠습니까?")){
			$.ajax({
				url : "/api/studio/withdrawal",
				method : "post"
			}).done(function(data){
				if(data){
					if(data.rows == 1){
						alert("탈퇴가 완료되었습니다.");
						location.href = '/logout?returl=/';
					}else{
						alert('탈퇴하지 못했습니다.');
					}
				}
			}).fail(function(jqXHR, textStatus, errorThrown) {
				console.log("fail",jqXHR, textStatus, errorThrown);
			}).always(function() {
		    	console.info('DONE');
		    });
		}
		
	})
})