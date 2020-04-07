/**
 *
 */
$(document).ready(function () {
    $("#id").focus();

    $("#studioId").on("keypress", function (e) {
        if (e.keyCode == 13) {
            e.preventDefault();
            $("#password").focus();
        }
    });

    $("#password").on("keypress", function (e) {
        if (e.keyCode == 13) {
            e.preventDefault();
            $("#loginBtn").trigger("click");
        }
    });
    $("#loginFrm").validate({
        rules: {
            studioId: {
                required: true,
                minlength: 5
                //중복체크 해달라고 해야함
            },
            password: {
                required: true,
                minlength: 5
            }

        },
        messages: {/* validate 메세지 */
            studioId: {
                required: "아이디를 입력하세요",
                rangelength: "아이디는 5글자 이상 15글자 이하로 입력해주세요",
            }, password: {
                required: "비밀번호를 입력하세요"
//		            minlength: 5
            }
        },
        submitHandler: function (frm) {
            frm.submit();   //유효성 검사를 통과시 전송
        }
    });


});
