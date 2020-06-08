var timerId = 123;
var timer = $(".timer");
var limit = 180;
var count = 0;


function startTimer() {
    stopTimer();
    $(".overTime").hide();
    limit = 180;
    timerId = setInterval(timeIt, 1000);
}


function stopTimer() {
    count = 0;
    clearInterval(timerId);
}

function timeIt() {
    count++;
    timer.html(numberToTime(limit - count));
    if (limit == count) {
        stopTimer();
        $("#secureBtn").html("인증");
        $(".secureNumber").hide();
        $(".overTime").show();
    }
}


function numberToTime(number) {
    var minute = (Math.floor(number / 60) + "").padStart(2, '0');
    var second = (number % 60 + "").padStart(2, '0');
    return minute + ":" + second;
}


$(document).ready(function () {
    console.log('findPassword')

    ///api/findPassword
    $("#findBtn").on('click', function () {
        console.log("#findBtn click");
        $.ajax({
            url: '/api/findPassword',
            data: $("#findFrm").serializeArray(),
            type: 'post'

        }).done(function (data) {
            console.log(data);
            if (data.success) {
                if (data.success === '1') {
                    alert('임시비밀번호가 발송 되었습니다.');
//					location.href = '/';
                } else {
                    alert(data.msg);
                }
            } else {
                alert(data.msg);
            }
        }).always(function () {
            console.log('DONE');
        });
    });


    $("#secureBtn").on('click', function () {
        //show 인증번호 인증타이머 인증버튼
        if ($("input[name='studioId']").val() == '') {
            alert('아이디를 입력해주세요.')
            $("input[name='studioId']").focus();
            return;
        }

        if ($("input[name='managerTel']").val() == '') {
            alert('휴대폰을 입력해주세요')
            $("input[name='managerTel']").focus();
            return;
        }
        console.log("인증버튼");
        $.ajax({
            url: '/api/secure-code',
            data: $("#findFrm").serializeArray(),
            type: 'post'

        }).done(function (data) {
            if (data.success) {
                //인증번호
                $(".secureNumber").show();
                $("#secureBtn").html("재전송");
                //타이머 보여주기
                startTimer();
            } else {
                alert(data.msg);
            }
        }).always(function () {
            console.log('DONE');
        });
    });

    $("#checkBtn").on('click', function () {
        console.log("확인 버튼");
        //secure-code/check
        if ($("input[name='studioId']").val() == '') {
            alert('아이디를 입력해주세요.')
            $("input[name='studioId']").focus();
            return;
        }

        if ($("input[name='managerTel']").val() == '') {
            alert('휴대폰을 입력해주세요')
            $("input[name='managerTel']").focus();
            return;
        }


        if ($("input[name='certificationValue']").val() == '') {
            alert('인증번호를 입력해주세요.')
            $("input[name='certificationValue']").focus();
            return;
        }

        $.ajax({
            url: '/api/secure-password-code/check',
            data: $("#findFrm").serializeArray(),
            type: 'post'

        }).done(function (data) {
            if (data.success) {
                $(".overTime").hide();
                $(".secureNumber").hide();
                alert(data.msg);
                stopTimer();
            } else {
                alert(data.msg);
            }
        }).always(function () {
            console.log('DONE');
        });
    });
});

