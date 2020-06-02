$(document).ready(function(){
	$("#btnSubmit").off('click').on('click', function(){
		//validation check
		
		//userName ppCnt tel reqContent
		$("#reqFrm").validate({
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
			if ($(element).next("input[type='button']").length > 0){
             		error.insertAfter($(element).next("input"));
            } else {
            	error.insertAfter(element);
            }
			
         },
			rules: {
				userName : {
		        	required: true
		        	//중복체크 해달라고 해야함
		        },
// 		        tel: {
// 		            required: true,
// 		            minlength: 9`
// 		        },
		        ppCnt : {
		        	required: true,
		        	digits : true
		        },
		        reqContent : {
		        	required: true,
		        	maxlength : 1000
		        },
		        gugun : {
		        	required: true
		        }
		    },
		    messages:{/* validate 메세지 */
		    	userName:{
		    		required:"이름을 입력하세요"
		    	},
// 		    	tel: {
// 		            required: "전화번호를 입력하세요",
// 		            minlength: "전화번호를 입력해주세요"
// 		        },
		        ppCnt: {
		            required: "촬영인원을 입력하세요"
		        },
		        reqContent: {
		            required: "내용을 입력하세요"
		        }
		    },
	        submitHandler: function (frm){
	        	
	        	var formData = new FormData($("#reqFrm")[0]);
	        	
	        	// 파일 검사 해야할듯 
//	        	if($("input[name=image]")[0].files){
//	        		formData.append("image", $("input[name=image]")[0].files[0]);
//		        	formData.append("images", $("input[name=image]")[0].files);
//	        	}
	        	
	        	
				
	        	var data = $("#reqFrm").serializeArray();
	    		$.ajax({
	    			url : '/api/insertRequestWithFile',
	    			type : 'post',
	    			enctype : 'multipart/form-data',
	    			data : formData,
	    			processData: false,
	    	        contentType: false,
	    			
	    		}).done(function(data) {
	    			console.info(data);
	    			if(data.success){
	    				alert('문의사항이 등록 되었습니다.')
	    				location.href = '/'
	    			}else{
	    				alert('입력값을 다시 확인해주세요.')
	    			}
	    		}).fail(function(jqXHR) {
	    	    	alert(jqXHR.responseJSON.message);

	    	    }).always(function() {
	    		});
	        }
		}); //end
		
	});
	gugunSelect();
	$("#sido").on('change', function(){
		gugunSelect();
	});
});

function gugunSelect(){
	$.ajax({
		url : "/api/selectGuguns",
		method : 'post',
		data : { sido : $("#sido").val()},
	}).done(function(data){
		var rows = data.rows;
		var html = "";
		
		for(var i = 0 ; i < data.rows.length; i++){
			var row = rows[i];
			html += "<option value="+row.commCdNm+">"+row.commCdNm+"</option>"
		}
		$("#gugun").html(html);
	}).fail(function(jqXHR) {
    	alert(jqXHR.responseJSON.message);

    }).always(function() {
    });
		
}
