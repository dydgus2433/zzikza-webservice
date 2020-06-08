var timerId = 123;
var limit = 180;
var count = 0;


$(document).ready(function(){
	console.log("find id");
	
	$("#findFrm").validate({
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
	        tel : {
	        	required: true,
	        	telRexChk : true
	        },
	        certificationValue : {
	        	timeChk: true,
	        	required : true
	        }
	    },
	    //userName tel
	    messages:{/* validate 메세지 */
	    	userName:{
	    		required:"이름을 입력하세요"
	    	},tel: {
	            required: "전화번호를 입력하세요"
	        },certificationValue: {
	            required: "인증번호를 입력해주세요."
	        }
	    },
	    submitHandler: function (frm){
	    	//certificationValueCode
	    }
	});
	
	$("#secureBtn").on('click', function(){
		//show 인증번호 인증타이머 인증버튼 
		console.log("인증버튼");
// 		var form = document.getElementById("findFrm");
// 	    form.submit();
		$(".secureNumber").hide();
		$(".showId").hide();
		if(!$("#findFrm").valid()){
			return false;
		}else{
			
		}
	    $.ajax({
			url : '/api/secure-code',
			data : $("#findFrm").serializeArray(),
			type : 'post'

		}).done(function(data) {
			console.log(data);
			//인증번호
			$(".secureNumber").show();
			$("#secureBtn").html("재전송");
			
			//타이머 보여주기
			startTimer();
			
		}).always(function() {
			console.log('DONE');
		});		
	});
	
	$("#checkBtn").on('click', function(){
		console.log("확인 버튼");
		
		if(!$("#findFrm").valid()){
			return false;
		}
		
		$.ajax({
			url : '/api/secure-code/check',
			data : $("#findFrm").serializeArray(),
			type : 'post'

		}).done(function(result) {
			if(result.success){
				$(".overTime").hide();
				$(".secureNumber").hide();
				$(".showId").show();
				if(result.data.snsType){
					alert("SNS로 가입된 아이디 입니다. SNS로 로그인 해주세요.");
				}else{
					$("#showId").html("<span>아이디는 <b>"+result.data.userId+"</b> 입니다.</span>");
				}
				stopTimer();
			}else{
				alert(result.msg);
			}
		}).always(function() {
			console.log('DONE');
		});
	});
	
	
});

