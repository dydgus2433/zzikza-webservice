var timerId = 123;
var timer = $(".timer");
var limit = 0;
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
	console.log('find Id');
	const $managerName = $("input[name='managerName']");
	const $input = $("input[name='managerTel']");
	const $checkSecure = $("input[name='checkSecure']");

	$("#secureBtn").on('click', function(){
		//show 인증번호 인증타이머 인증버튼 
		console.log('인증버튼' );
		if ($managerName.val() !== '') {
			if ($input.val() === '') {
				alert('담당자 휴대폰 번호를 입력해주세요')
				$input.focus();
				return;
			}
			$.ajax({
				url: '/api/sendSecureCode',
				data: $("#findFrm").serializeArray(),
				type: 'post'

			}).done(function (data) {
				console.log(data);
				//인증번호
				$(".secureNumber").show();
				$("#secureBtn").html("재전송");
				//타이머 보여주기
				startTimer();

			}).fail(function (jqXHR, textStatus, errorThrown) {
				alert(jqXHR.responseJSON.message);
			}).always(function () {
				console.log('DONE');
			});
		} else {
			alert('담당자 이름을 입력해주세요.');
			$managerName.focus();

		}
	});
	
	$("#checkBtn").on('click', function(){
		console.log("확인 버튼");
		if($managerName.val() === ''){
			alert('담당자 이름을 입력해주세요.')
			$managerName.focus();
			return;
		}
		
		if($input.val() === ''){
			alert('담당자 휴대폰 번호를 입력해주세요')
			$input.focus();
			return;
		}
		
		if($checkSecure.val() === ''){
			alert('인증번호를 입력해주세요.')
			$checkSecure.focus();
			return;
		}
		//checkSecureCode
		$.ajax({
			url : '/api/checkSecureCode',
			data : $("#findFrm").serializeArray(),
			type : 'post'

		}).done(function(data) {
			
			
			if(data.rtnCode === 'SUCCESS'){
				const $secureBtn = $("#secureBtn");
				$(".overTime").hide();
				$(".secureNumber").hide();
				alert(data.rtnMsg);
				$(".showId").show();
				$("#showId").html("아이디는 <b>"+data.studioId+"</b> 입니다.");
				$secureBtn.html("인증완료");
				$secureBtn.attr("disabled", true);
				stopTimer();
			}else{
				alert(data.rtnMsg);
			}
		}).fail(function(jqXHR, textStatus, errorThrown) {
			alert(jqXHR.responseJSON.message);
		}).always(function() {
			console.log('DONE');
		});
	});

});
