$(document).ready(function () {
    $("#loginFrm").validate({
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
            if ($(element).next("input[type='button']").length > 0) {
                error.insertAfter($(element).next("input"));
            } else {
                error.insertAfter(element);
            }

        },
        rules: {
            userId: {
                required: true
                //중복체크 해달라고 해야함
            },
            password: {
                required: true
            }
        },
        //userId password  passwordCheck userName tel telChk
        messages: {/* validate 메세지 */
            userId: {
                required: "이름을 입력하세요"
            }, password: {
                required: "비밀번호를 입력하세요"
            }
        },
        submitHandler: function (frm) {
            $.post({
                url: '/loginProcess',
                // dataType : 'json',
                // contentType: 'application/json',
                type: 'post',
                data: $("#loginFrm").serializeArray()
            }).done(function (result) {
                if (result.success) {
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

    //Cookie
    // 저장된 쿠키값을 가져와서 ID 칸에 넣어준다. 없으면 공백으로 들어감.
    var userInputId = getCookie("userInputId");
    $("input[name='userId']").val(userInputId);

    if ($("input[name='userId']").val() != "") { // 그 전에 ID를 저장해서 처음 페이지 로딩 시, 'userId'에 저장된 ID가 표시된 상태라면,
        $("#idCheck").attr("checked", true); // ID 저장하기를 체크 상태로 두기.
    }

    $("#idCheck").change(function () { // 체크박스에 변화가 있다면,
        if ($("#idCheck").is(":checked")) { // ID 저장하기 체크했을 때,
            var userInputId = $("input[name='userId']").val();
            setCookie("userInputId", userInputId, 7); // 7일 동안 쿠키 보관
        } else { // ID 저장하기 체크 해제 시,
            deleteCookie("userInputId");
        }
    });

    // ID 저장하기를 체크한 상태에서 ID를 입력하는 경우, 이럴 때도 쿠키 저장.
    $("input[name='userId']").keyup(function () { // ID 입력 칸에 ID를 입력할 때,
        if ($("#idCheck").is(":checked")) { // ID 저장하기를 체크한 상태라면,
            var userInputId = $("input[name='userId']").val();
            setCookie("userInputId", userInputId, 7); // 7일 동안 쿠키 보관
        }
    });
});


function setCookie(cookieName, value, exdays) {
    var exdate = new Date();
    exdate.setDate(exdate.getDate() + exdays);
    var cookieValue = escape(value) + ((exdays == null) ? "" : "; expires=" + exdate.toGMTString());
    document.cookie = cookieName + "=" + cookieValue;
}

function deleteCookie(cookieName) {
    var expireDate = new Date();
    expireDate.setDate(expireDate.getDate() - 1);
    document.cookie = cookieName + "= " + "; expires=" + expireDate.toGMTString();
}

function getCookie(cookieName) {
    cookieName = cookieName + '=';
    var cookieData = document.cookie;
    var start = cookieData.indexOf(cookieName);
    var cookieValue = '';
    if (start != -1) {
        start += cookieName.length;
        var end = cookieData.indexOf(';', start);
        if (end == -1) end = cookieData.length;
        cookieValue = cookieData.substring(start, end);
    }
    return unescape(cookieValue);
}
