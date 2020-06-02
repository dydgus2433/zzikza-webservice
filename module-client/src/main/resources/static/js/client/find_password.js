var timerId = 123;
var limit = 180;
var count = 0;


function startTimer(){
	stopTimer();
	$(".overTime").hide();
	limit = 180;
	count = 0;
	timerId = setInterval(timeIt,1000);
}


function stopTimer(){
	clearInterval(timerId);
}

function timeIt(){
	count++;
	$(".counting_number").html(numberToTime(limit - count));
	if(limit == count){
		stopTimer();
		$("#secureBtn").html("인증번호전송");
// 		$("#secureBtn").append('<span class="warring_cmm overTime" style="">입력시간을 초과하였습니다. 인증번호 재전송을 다시 시도하여 주십시오.</span>')
		$(".overTime").show();
	}
}


function numberToTime(number){
	var minute = (Math.floor(number/60)+"").padStart(2,'0');
	var second = (number%60+"").padStart(2,'0');
	return minute+":"+second;
}

$(document).ready(function(){
	console.log("find password");
	
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
	
	///api/findPassword
// 	$("#findBtn").on('click', function(){
// 		console.log("#findBtn click");
//
// 		if(!$("#findFrm").valid()){
// 			return false;
// 		}
//
// 		$.ajax({
// 			url: '/api/findPassword',
// 			data : $("#findFrm").serializeArray(),
// 			type: 'post'
//
// 		}).done(function(data) {
// 			console.log(data);
// 			if(data.success){
// 				if(data.success){
// 					alert('임시비밀번호가 발송 되었습니다.');
// //					location.href = '/';
// 				} else{
// 					alert(data.msg);
// 				}
// 			}else{
// 				alert('발송이 실패 했습니다. 다시 시도해주세요.');
// 			}
//
// 		}).fail(function(jqXHR) {
// 	    	alert(jqXHR.responseJSON.message);
// 	    }).always(function() {
// 	    	console.log('DONE');
// 	    });
// 	});
	
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
			
		}).fail(function(jqXHR) {
			alert(jqXHR.responseJSON.message);
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

		}).done(function(data) {
			if(data.success){
				$(".overTime").hide();
				$(".secureNumber").hide();
				alert(data.msg);
				stopTimer();
				// $(".findDiv").show();
			}else{
				alert(data.msg);
			}
		}).fail(function(jqXHR) {
			alert(jqXHR.responseJSON.message);
		}).always(function() {
			console.log('DONE');
		});
	});
	
	$.validator.addMethod('telRexChk', telRexChk, "올바른 전화번호를 입력해주세요");
	$.validator.addMethod('timeChk', timeChk, "입력시간을 초과하였습니다. 인증번호전송을 다시 시도하여 주십시오.");
});


function telRexChk(str, obj){
	var re = /(^02.{0}|^01.{1}|[0-9]{3})([0-9]{3,4})([0-9]{4})$/g;
	var valid = re.test(String(str).toLowerCase());
	if(valid){
		document.getElementById(obj.id).classList.remove("warring_cmm");
		document.getElementById(obj.id).classList.add('valid')
		$(obj).siblings(".warring_cmm").remove();
	}
	return valid;
}

function timeChk(){
	return !(limit == count);
}
