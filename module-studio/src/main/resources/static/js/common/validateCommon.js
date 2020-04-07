$(document).ready(function () {
    /* 사업자 번호 체크 */
    $.validator.addMethod('businessNumberChk', checkCorporateRegistrationNumber, "올바른 사업자번호 형식이 아닙니다.");
    $.validator.addMethod('requireChk', requireChk, "필수항목에 동의해주세요.");
    $.validator.addMethod('passwordChk', passwordChk, "문자/숫자 포함 형태의 6~15자리 이내로 입력해주세요.");
    $.validator.addMethod('idChk', idChk, "영문/숫자 포함 5~15자리 이내로 입력해주세요");
    $.validator.addMethod('telRexChk', telRexChk, "올바른 전화번호를 입력해주세요");
    $.validator.addMethod('emailChk', emailChk, "올바른 이메일 형식이 아닙니다.");
    $.validator.addMethod("extension", function (value, element, param) {
        param = typeof param === "string" ? param.replace(/,/g, "|") : "png|jpe?g|gif";
        return this.optional(element) || value.match(new RegExp("\\.(" + param + ")$", "i"));
    });

    //숫자만 입력
    $("input[type='number']").off('keypress').on('keypress', function (e) {
        if (e.which && (e.which > 47 && e.which < 58 || e.which === 8)) {

        } else {
            e.preventDefault();
        }
    });
});

function validateEffect(re, str, obj) {
    const valid = re.test(String(str).toLowerCase());
    if (valid) {
        document.getElementById(obj.id).classList.remove("error");
        document.getElementById(obj.id).classList.add('valid')
        $(obj).siblings(".error").remove();
    }
    return valid;
}

function passwordChk(str, obj) {
//	var re = /^.*(?=^.{6,15}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$/;
    //숫자와 문자만으로 비밀번호 검사(특수문자 필수 X)
    const re = /^.*(?=^.{6,15}$)(?=.*\d)(?=.*[a-zA-Z]).*$/;
    return validateEffect(re, str, obj);
}

function idChk(str, obj) {
    const re = /^[a-z]+[a-z0-9]{4,14}$/g;
    return validateEffect(re, str, obj);
}


function emailChk(str, obj) {
    const re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return validateEffect(re, str, obj);
}


function companyNameChk(str, obj) {
    const re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return validateEffect(re, str, obj);
}

function telRexChk(str, obj) {
    const re = /(^02.{0}|^01.{1}|[0-9]{3})([0-9]{3,4})([0-9]{4})$/g;
    return validateEffect(re, str, obj);
}

// 사업자 등록증 유효성 검사
function checkCorporateRegistrationNumber(bizID, obj) {
    // bizID는 숫자만 10자리로 해서 문자열로 넘긴다.
    const checkID = [1, 3, 7, 1, 3, 7, 1, 3, 5, 1];
    let tmpBizID, i, chkSum = 0, c2, remander;
    bizID = bizID.replace(/-/gi, '');

    for (i = 0; i <= 7; i++) chkSum += checkID[i] * bizID.charAt(i);
    c2 = "0" + (checkID[8] * bizID.charAt(8));
    c2 = c2.substring(c2.length - 2, c2.length);
    chkSum += Math.floor(c2.charAt(0)) + Math.floor(c2.charAt(1));
    remander = (10 - (chkSum % 10)) % 10;
    const valid = Math.floor(bizID.charAt(9)) === remander;
    if (valid) {
        console.log("Math.floor(valueMap[9]) === (10 - (checkSum % 10))", valid)
        $(obj).siblings(".error").remove();
    }
    return valid;
}


function requireChk(a,b,c){
    console.log("requireChk",a,b,c);
    const required = $("#requiredTermStatus").prop('checked');
    if(required){
        $("#requiredTermStatus").val("Y");
    }else{
        $("#requiredTermStatus").val("N");
    }
    return required;
}

$(function () {
    $.extend($.validator.messages,
        {
            required: "필수 항목입니다.",
            remote: "항목을 수정하세요.",
            email: "유효하지 않은 E-Mail주소입니다.",
            url: "유효하지 않은 URL입니다.",
            date: "올바른 날짜를 입력하세요.",
            dateISO: "올바른 날짜(ISO)를 입력하세요.",
            number: "유효한 숫자가 아닙니다.",
            digits: "숫자만 입력 가능합니다.",
            creditcard: "신용카드 번호가 바르지 않습니다.",
            equalTo: "같은 값을 다시 입력하세요.",
            extension: "올바른 확장자가 아닙니다.",
            maxlength: $.validator.format("{0}자를 넘을 수 없습니다. "),
            minlength: $.validator.format("{0}자 이상 입력하세요."),
            rangelength: $.validator.format("문자 길이가 {0} 에서 {1} 사이의 값을 입력하세요."),
            range: $.validator.format("{0} 에서 {1} 사이의 값을 입력하세요."),
            max: $.validator.format("{0} 이하의 값을 입력하세요."),
            min: $.validator.format("{0} 이상의 값을 입력하세요.")
        });
});

