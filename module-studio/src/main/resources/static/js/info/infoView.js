$(document).ready(function() {
	$("#infoFrm").validate({
		rules: {
			studioName: {
	            required: true
	        },
	        managerName: {
	            required: true
	        },
	        password: {
	            required: true,
	            passwordChk : true
	        },
	        passwordChk: {
	        	equalTo: "#password",
	            required: true
	        },
	        changePassword : {
	        	required: true,
	            passwordChk : true
	        },
	        managerEmail : {
	        	required: true,
	        	emailChk : true
	        },
	        requiredTermStatus :  {
	        	required: true,
	        	requireChk : true
	        },
	        tel :{
	        	telRexChk : true
	        }, managerTel :{
	        	telRexChk : true
	        }
	    },
        submitHandler: function (frm){
//        	console.log("submitHandler");
//        	console.log('$("#infoFrm").serializeArray()',$("#infoFrm").serializeArray())
        	var formData = new FormData($("#infoFrm")[0])
        	var $targetForm = $(frm);
        	
        	console.log("submitHandler",formData,$targetForm);
            $.ajax({
        		url: $targetForm.attr('action'),
        		data : $("#infoFrm").serializeArray(),
        		type: $targetForm.attr('method')
        		
        	}).done(function(data) {
        		console.log(data);
        		if(data.success){
        				alert('수정이 완료 되었습니다.');
//        				location.href = '/';
        		}else{
        			alert('수정실패 했습니다. 다시 시도해주세요.');
        		}
        		
        	}).fail(function(jqXHR, textStatus, errorThrown) {
            	alert(jqXHR.responseJSON.message);
            }).always(function() {
            	console.log('DONE');
            });
        } ,
        errorPlacement: function(error, element) {
            if (element.attr("name") == "requiredTermStatus"){
             	if($("#requiredTermStatus").siblings(".error").size() == 0){
             		error.insertAfter("#policy");
             	}
            } else {
            	error.insertAfter(element);
            }
          }
	});
	
});

