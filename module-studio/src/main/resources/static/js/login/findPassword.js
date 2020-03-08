var timerId = 123;
var timer = $(".timer");
var limit = 180;
var count = 0;



function startTimer(){
	stopTimer();
	$(".overTime").hide();
	limit = 180;
	timerId = setInterval(timeIt,1000);
}


function stopTimer(){
	count = 0;
	clearInterval(timerId);
}

function timeIt(){
	count++;
	timer.html(numberToTime(limit - count));
	if(limit == count){
		stopTimer();
		$("#secureBtn").html("인증");
		$(".secureNumber").hide();
		$(".overTime").show();
	}
}


function numberToTime(number){
	var minute = (Math.floor(number/60)+"").padStart(2,'0');
	var second = (number%60+"").padStart(2,'0');
	return minute+":"+second;
}



$(document).ready(function() {
	console.log('findPassword')
	
	///api/findPassword
	$("#findBtn").on('click', function(){
		console.log("#findBtn click");
		$.ajax({
			url: '/api/findPassword',
			data : $("#findFrm").serializeArray(),
			type: 'post'
			
		}).done(function(data) {
			console.log(data);
			if(data.rtnCode){
				if(data.rtnCode == '1'){
					alert('임시비밀번호가 발송 되었습니다.');
//					location.href = '/';
				} else{
					alert(data.rtnMsg);
				}
			}else{
				alert('발송이 실패 했습니다. 다시 시도해주세요.');
			}
			
		}).fail(function(jqXHR, textStatus, errorThrown) {
	    	console.error('FAIL REQUEST: ', textStatus);
			alert('처리중 오류가 발생하였습니다.');
	    }).always(function() {
	    	console.log('DONE');
	    });
	});
	
	
	$("#secureBtn").on('click', function(){
		//show 인증번호 인증타이머 인증버튼 
		if($("input[name='stdoId']").val() == ''){
			alert('아이디를 입력해주세요.')
			$("input[name='stdoId']").focus();
			return;
		}
		
		if($("input[name='mngCntt']").val() == ''){
			alert('휴대폰을 입력해주세요')
			$("input[name='mngCntt']").focus();
			return;
		}
		console.log("인증버튼");
		$.ajax({
			url : '/api/sendSecureCode',
			data : $("#findFrm").serializeArray(),
			type : 'post'

		}).done(function(data) {
			console.log(data);
			//인증번호
			$(".secureNumber").show();
			$("#secureBtn").html("재전송");
			//타이머 보여주기
			startTimer();
			
		}).fail(function(jqXHR, textStatus, errorThrown) {
			console.error('FAIL REQUEST: ', textStatus);
			alert('처리중 오류가 발생하였습니다.');
		}).always(function() {
			console.log('DONE');
		});
	});
	
	$("#checkBtn").on('click', function(){
		console.log("확인 버튼");
		//checkSecureCode
		if($("input[name='stdoId']").val() == ''){
			alert('아이디를 입력해주세요.')
			$("input[name='stdoId']").focus();
			return;
		}
		
		if($("input[name='mngCntt']").val() == ''){
			alert('휴대폰을 입력해주세요')
			$("input[name='mngCntt']").focus();
			return;
		}
		

		if($("input[name='checkSecure']").val() == ''){
			alert('인증번호를 입력해주세요.')
			$("input[name='checkSecure']").focus();
			return;
		}
		
		$.ajax({
			url : '/api/checkSecurePassCode',
			data : $("#findFrm").serializeArray(),
			type : 'post'

		}).done(function(data) {
			if(data.rtnCode == 'SUCCESS'){
				$(".overTime").hide();
				$(".secureNumber").hide();
				alert(data.rtnMsg);
				stopTimer();
			}else{
				alert(data.rtnMsg);
			}
		}).fail(function(jqXHR, textStatus, errorThrown) {
			console.error('FAIL REQUEST: ', textStatus);
			alert('처리중 오류가 발생하였습니다.');
		}).always(function() {
			console.log('DONE');
		});
	});
});

