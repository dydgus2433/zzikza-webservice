$(document).ready(function () {
    //userName peopleCnt tel reqContent
    $("#joinFrm").validate({
        invalidHandler: function (event, validator) {
        },
        errorClass: 'warring_cmm',
        errorElement: 'span',
        showErrors: function () {
            this.defaultShowErrors();
            var image = $('<img />')
                .attr('src', "/img/deco_warring.png")         // ADD IMAGE PROPERTIES.
                .attr('alt', '');
            $("span.warring_cmm").append(image);
        },
        errorPlacement: function (error, element) {
            if (element.prop('type') == 'checkbox') {
                error.insertAfter($(element).siblings("a"));
            } else if ($(element).next("span").length > 0) {
                error.insertAfter($(element).next().next("input"));
            } else if ($(element).next("input[type='button']").length > 0) {
                error.insertAfter($(element).next("input"));
            } else {
                error.insertAfter(element);
            }
        },
        rules: {
            userName: {
                required: true
            },
            tel: {
                required: true,
                minlength: 9,
// 		            secureSendChk : true
            },
            certificationValue: {
                timeChk: true,
                required: true,
// 		        	secureChk : true	
            }, useTermYn: {
                requireChk: true
            }, cancelTermYn: {
                requireChk: true
            }, privateTermYn: {
                requireChk: true
            }, otherTermYn: {
                requireChk: true
            }
        },
        //userId password  passwordCheck userName tel certificationValue
        messages: {/* validate 메세지 */
            userId: {
                required: "아이디를 입력하세요",
                rangelength: "아이디는 4글자 이상 14글자 이하로 입력해주세요",
                remote: "{0} 는 이미 존재하는 아이디입니다."
            }, password: {
                required: "비밀번호를 입력하세요",
                minlength: "비밀번호를 입력해주세요"
            }, passwordCheck: {
                required: "비밀번호를 입력하세요",
                equalTo: "비밀번호가 일치하는지 확인해주세요"
            }, userName: {
                required: "이름을 입력하세요"
            }, tel: {
                required: "전화번호를 입력하세요",
                minlength: "전화번호를 입력해주세요"
            }, certificationValue: {
                required: "인증번호를 입력하세요"
            }
        },
        submitHandler: function (frm) {
            if ($("#secureFlag").val() !== "true") {
                alert("인증을 완료해주세요.");
                $("#certificationValue").focus();
                return false;
            }

            //check 여부는 alert으로 하자
            var data = $("#joinFrm").serializeArray();
            $.ajax({
                url: '/api/sns-user',
                type: 'POST',
                data: data
            }).done(function (result) {
                if (result.success) {
                    alert("찍자의 회원이 되신것을 진심으로 환영합니다.");
                    if (result.data.returl) {
                        location.href = decodeURIComponent(result.data.returl);
                    } else {
                        location.href = "/";
                    }
                } else {
                    alert(result.msg);
                }
            }).always(function () {
            });

        }
    });

    $.validator.addMethod('idChk', idChk, "영문/숫자 포함 5~15자리 이내로 입력해주세요");
    $.validator.addMethod('passwordChk', passwordChk, "문자/숫자 포함 형태의 6~15자리 이내로 입력해주세요.");
    $.validator.addMethod('emailChk', emailChk, "올바른 이메일 형식이 아닙니다.");
    $.validator.addMethod('telRexChk', telRexChk, "올바른 전화번호를 입력해주세요");
    $.validator.addMethod('requireChk', requireChk, "필수 체크 해주세요");

    //인증번호 전송
    $("#secureBtn").on('click', function () {
        if ($("#tel").val() == "") {
            alert("연락처를 입력해주세요.");
            return;
        }


// 		    	telRexChk(str, obj){
        if (!telRexChk($("#tel").val(), $("#tel")[0])) {
            alert("전화번호를 양식에 맞게 입력해주세요");
            return;
        }

        var data = $("#joinFrm").serializeArray();
        $.ajax({
            url: '/api/secure-code',
            type: 'post',
            data: data
        }).done(function (data) {
            $(".secureNumber").show();
            $("#secureBtn").html("재전송");
            //타이머 보여주기
            startTimer();
        }).always(function () {
        });

    });

    $("#checkBtn").on('click', function () {
        $.ajax({
            url: '/api/secure-code/check',
            type: 'post',
            data: $("#joinFrm").serializeArray()
        }).done(function (data) {
            alert(data.msg);
            $("#secureFlag").val(data.success);
            if (data.success) {
                // {userName: "이용현", userId: "tester3", userStatus: "Y", snsType: null}
                if (data.data.userId) {
                    alert("가입된 아이디가 있습니다.");
                } else {
                    $(".secureNumber").hide();
                    var ele = $("<span />").text("인증이 확인되었습니다.");
                    $("#secureBtn").after(ele);
                    stopTimer();
                }
            } else {
                alert(data.msg);
            }
        }).always(function () {
        });
    });


    //전체 동의 체크시 모두 체크
    $("#allCheck").off('click').on('click', function () {
        $("ul#termsCheck input[type=checkbox]").prop('checked', $("#allCheck").prop('checked'));

        if ($("#allCheck").prop('checked')) {
            $("ul#termsCheck li span.warring_cmm").hide();
        }
    })

    $("ul#termsCheck input[type=checkbox]").off('click').on('click', function () {
        var checkboxCnt = $("ul#termsCheck input[type=checkbox]").length;
        var checkCnt = $("ul#termsCheck :checked").length;
        $("#allCheck").prop('checked', (checkboxCnt == checkCnt));
    })
});
