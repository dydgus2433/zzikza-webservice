$.validator.addMethod('idChk', idChk, "영문/숫자 포함 5~15자리 이내로 입력해주세요");
$.validator.addMethod('passwordChk', passwordChk, "문자/숫자 포함 형태의 6~15자리 이내로 입력해주세요.");
$.validator.addMethod('emailChk', emailChk, "올바른 이메일 형식이 아닙니다.");
$.validator.addMethod('telRexChk', telRexChk, "올바른 전화번호를 입력해주세요");
$.validator.addMethod('requireChk', requireChk, "필수 체크 해주세요");
$.validator.addMethod('timeChk', timeChk, "입력시간을 초과하였습니다. 인증번호전송을 다시 시도하여 주십시오.");
$.validator.addMethod('secureChk', secureChk, "인증이 실패했습니다.");
$.validator.addMethod('secureSendChk', secureChk, "인증번호를 발송해주세요.");
$.validator.addMethod('limitDate', limitDate, "6개월 이내의 날짜를 설정해주세요.");
$.validator.addMethod('limitTime', limitTime, "현재 이후의 시간을 설정해주세요.");
$.validator.addMethod('payMethodChk', payMethodChk, "결제수단을 선택해주세요.");
$.validator.addMethod( "extension", function( value, element, param ) {
	param = typeof param === "string" ? param.replace( /,/g, "|" ) : "png|jpe?g|gif";
	return this.optional( element ) || value.match( new RegExp( "\\.(" + param + ")$", "i" ) );
});

$.extend( $.validator.messages,
		{ required: "필수 항목입니다.", remote: "항목을 수정하세요.", email: "유효하지 않은 E-Mail주소입니다.", url: "유효하지 않은 URL입니다.", date: "올바른 날짜를 입력하세요."
, dateISO: "올바른 날짜(ISO)를 입력하세요.", number: "유효한 숫자가 아닙니다.", 
digits: "숫자만 입력 가능합니다.", creditcard: "신용카드 번호가 바르지 않습니다.", 
equalTo: "같은 값을 다시 입력하세요.", extension: "올바른 확장자가 아닙니다.", maxlength: $.validator.format( "{0}자를 넘을 수 없습니다. " ), 
minlength: $.validator.format( "{0}자 이상 입력하세요." ), rangelength: $.validator.format( "문자 길이가 {0} 에서 {1} 사이의 값을 입력하세요." ), 
range: $.validator.format( "{0} 에서 {1} 사이의 값을 입력하세요." ), max: $.validator.format( "{0} 이하의 값을 입력하세요." )
, min: $.validator.format( "{0} 이상의 값을 입력하세요." ) } );


function limitDate(str, obj){
	var date = new Date();
	date.setMonth(date.getMonth() +6);
	return new Date(str) <  date ;
}

function limitTime(str, obj){
	var rsrvStrtTime = $("#rsrvStrtTime").val();
	var date = new Date($("#rsrvStrtDt").val());
	date.setHours(rsrvStrtTime.split(":")[0], rsrvStrtTime.split(":")[1], 0);
//	date.setHours(rsrvStrtTime);
	console.log(date);
	return new Date() < date;
}

function requireChk(str, obj){
	var valid = $(obj).prop('checked');
	return valid;
}

function emailChk(str, obj){
	var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	var valid = re.test(String(str).toLowerCase());
	return valid;
}
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


function idChk(str, obj){
	var re = /^[a-z]+[a-z0-9]{4,14}$/g;
	var valid = re.test(String(str).toLowerCase());
	return valid;
}


function passwordChk(str, obj){
//	var re = /^.*(?=^.{6,15}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$/;
	//숫자와 문자만으로 비밀번호 검사(특수문자 필수 X)
	var re = /^.*(?=^.{6,15}$)(?=.*\d)(?=.*[a-zA-Z]).*$/;
	var valid = re.test(String(str).toLowerCase());
	return valid;
}


//타이머 체크
function timeChk(){
	return !(limit == count);
}

//인증 체크
function secureChk(){
	return $("#secureFlag").val() == 'SUCCESS';
}

//결제 수단

function payMethodChk(str, obj){
	var pg_company = $("#pg_company").val();
	var pay_method = $("#pay_method").val();
	return $("#pg_company").val() == "" || $("#pay_method").val() == "";
}
