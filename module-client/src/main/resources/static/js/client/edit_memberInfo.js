$(document).ready(function(){
	console.log("edit member info");
	$("#infoFrm").validate({
		invalidHandler : function(event,validator){
			console.log("invalidHandler",event,validator)
		},
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
// 			userName : {
// 	        	required: true
// 	        },
// 	        password : {
// 	        	required: true
// 	        },
	        tel: {
	            required: true,
	            minlength: 9,
// 	            secureSendChk : true
	        },
	        certificationValue : {
	        	timeChk: true,
	        	required: true,
// 	        	secureChk : true	
	        }
	    },
        submitHandler: function (frm){
        	console.log("submitHandler");
        	if($("#secureFlag").val() !== "true"){
        		alert("인증을 완료해주세요.");
        		$("#certificationValue").focus();
        		return false;
        	}

			const formData = new FormData($("#infoFrm")[0]);
			const $targetForm = $(frm);

			console.log("submitHandler",formData,$targetForm);
            $.ajax({
        		url: '/api/user',
        		data : $("#infoFrm").serializeObject(),
        		type: 'PUT',
        	}).done(function(data) {
        		console.log(data);
        		if(data.rows){
        			if(data.success){
        				alert('수정이 완료 되었습니다.');
        				location.reload();
        			} 
        		}else{
        			alert('수정이 실패했습니다. 다시 시도해주세요.');
        		}
        		
        	}).fail(function(jqXHR) {
            	alert(jqXHR.responseJSON.message);

            }).always(function() {
            	console.log('DONE');
            });
        } 
	});
	
	
	//인증번호 전송
    $("#secureBtn").on('click', function(){
    	console.log("secureBtn 인증번호 전송");
    	
    	if($("#tel").val() == ""){
    		alert("연락처를 입력해주세요.");
    		return;
    	}

    	if(!telRexChk($("#tel").val(), $("#tel")[0])){
    		alert("전화번호를 양식에 맞게 입력해주세요");
    		return;
    	}
    			    	
    	var data = $("#infoFrm").serializeArray();
		$.ajax({
			url : '/api/secure-code',
			type : 'post',
			data : data
		}).done(function(data){
			$(".secureNumber").show();
			$("#secureBtn").html("재전송");
			//타이머 보여주기
			startTimer();
		}).fail(function(jqXHR) {
			alert(jqXHR.responseJSON.message);
		}).always(function() {
			console.log('DONE');
		});
    	
	});
	
    $("#checkBtn").on('click', function(){
		console.log("인증번호확인 버튼");
		$.ajax({
			url : '/api/secure-code/check',
			type : 'post',
			data : $("#infoFrm").serializeObject()
		}).done(function(data){
			alert(data.msg);
			$("#secureFlag").val(data.success);
			if(data.success){
				$(".secureNumber").hide();
				var ele = $("<span />").text("인증이 확인되었습니다.");
				$("#secureBtn").after(ele);
				stopTimer();
			}
		}).fail(function(jqXHR) {
			alert(jqXHR.responseJSON.message);
		}).always(function() {
			console.log('DONE');
		});
    });
});
