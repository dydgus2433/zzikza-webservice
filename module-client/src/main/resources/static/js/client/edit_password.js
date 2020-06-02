$(document).ready(function(){
	console.log("edit password");
	
	$("#infoFrm").validate({
		errorClass : 'warring_cmm',
		errorElement : 'span',
		showErrors : function(){
			this.defaultShowErrors();
			var image = $('<img />')
			.attr('src', "/img/deco_warring.png")         // ADD IMAGE PROPERTIES.
			.attr('alt', '');
			$("span.warring_cmm").append(image);
		},
		errorPlacement: function(error, element) {
			console.log(error, element);
			if ($(element).next("span").length > 0){
	         		error.insertAfter($(element).next().next("input"));
		    } else if ($(element).next("input[type='button']").length > 0){
         		error.insertAfter($(element).next("input"));
	        } else {
	        	error.insertAfter(element);
	        }
     	},
		rules: {
	        passwordCheck: {
	        	equalTo: "#changePassword",
	            required: true
	        },
	        changePassword : {
	        	required: true,
	            passwordChk : true
	        }
	    },
        submitHandler: function (frm){
        	var formData = new FormData($("#infoFrm")[0])
        	var $targetForm = $(frm);
        	
        	console.log("submitHandler",formData,$targetForm);
            $.ajax({
        		url: '/api/user-password',
        		data : $("#infoFrm").serializeArray(),
        		type: 'PUT'
        		
        	}).done(function(data) {
        		console.log(data);
        		if(data.success){
        			if(data.success){
        				alert('수정이 완료 되었습니다.');
       					location.href = '/edit_memberMain';
        			} else{
            			alert('수정실패 했습니다. 다시 시도해주세요.');
            		}
        		}else{
        			alert('수정실패 했습니다. 다시 시도해주세요.');
        		}
        		
        	}).fail(function(jqXHR) {
            	alert(jqXHR.responseJSON.message);

            }).always(function() {
            	console.log('DONE');
            });
        } ,
        errorPlacement: function(error, element) {
            if (element.attr("name") == "rqrdTermYn"){
             	if($("#rqrdTermYn").siblings(".error").size() == 0){
             		error.insertAfter("#policy");
             	}
            } else {
            	error.insertAfter(element);
            }
          }
	});
	
});
