$(document).ready(function() {
	$("#infoFrm").validate({
		rules: {
	        password: {
	            required: true,
//	            passwordChk : true
	        },
	        changePassword: {
	            required: true,
	            passwordChk : true
	        },
	        passwordChk: {
	        	equalTo: "#changePassword",
	            required: true
	        }
	    },
        submitHandler: function (frm){
        	console.log("submitHandler");
        	var formData = new FormData($("#infoFrm")[0])
        	var $targetForm = $(frm);
        	
        	console.log("submitHandler",formData,$targetForm);
            $.ajax({
        		url: $targetForm.attr('action'),
        		enctype: 'multipart/form-data',
        		data : formData,
        		type: $targetForm.attr('method'),
        		processData: false,
                contentType: false,
                cache: false,
        		
        	}).done(function(data) {
        		console.log(data);
        		if(data.success){
        				alert('수정이 완료 되었습니다.');
        		}else{
        			alert('수정이 실패했습니다. 다시 시도해주세요.');
        		}
        		
        	}).fail(function(jqXHR) {
            	alert(jqXHR.responseJSON.message);
            }).always(function() {
            	console.log('DONE');
            });
        } ,
        errorPlacement: function(error, element) {
        	error.insertAfter(element);
          }
	});
});


