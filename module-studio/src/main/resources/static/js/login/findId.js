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
	const $certificationValue = $("input[name='certificationValue']");

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
				url: '/api/secure-code',
				data: $("#findFrm").serializeArray(),
				type: 'post'

			}).done(function (data) {
				console.log(data);
				//인증번호
				$(".secureNumber").show();
				$("#secureBtn").html("재전송");
				//타이머 보여주기
				startTimer();

			}).fail(function (jqXHR) {
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
		
		if($certificationValue.val() === ''){
			alert('인증번호를 입력해주세요.')
			$certificationValue.focus();
			return;
		}
		//secure-code/check
		$.ajax({
			url : '/api/secure-code/check',
			data : $("#findFrm").serializeArray(),
			type : 'post'

		}).done(function(data) {
			// {id: "STD0000000436", studioId: "tester2", studioName: "찍자컴퍼니", accountStatus: "Y"}
			const result = data.data;
			if(data.success){
				const $secureBtn = $("#secureBtn");
				$(".overTime").hide();
				$(".secureNumber").hide();
				alert(data.msg);
				$(".showId").show();
				$("#showId").html("아이디는 <b>"+result.studioId+"</b> 입니다.");
				$secureBtn.html("인증완료");
				$secureBtn.attr("disabled", true);
				stopTimer();
			}else{
				alert(data.msg);
			}
		}).fail(function(jqXHR) {
			alert(jqXHR.responseJSON.message);
		}).always(function() {
			console.log('DONE');
		});
	});

});
