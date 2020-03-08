$(document).ready(function() {
	$("#infoFrm").validate({
		rules: {
	        pw: {
	            required: true,
	            passwordChk : true
	        },
	        pwChk: {
	        	equalTo: "#pw",
	            required: true
	        },
	        bsnsNo : {
	        	required: true,
	        	bsnsNoChk : true
	        },
	        bsnsLicFile : {
//	        	required: true,
	        	extension : "png|jpg|jpeg"
	        }
	    },
        submitHandler: function (frm){
        	console.log("submitHandler");
        	var formData = new FormData($("#infoFrm")[0])
        	var $targetForm = $(frm);
        	
        	console.log("submitHandler",formData,$targetForm);
            $.ajax({
        		url: $targetForm.attr('action'),
        		enctype: 'multipart/form-data',
        		data : formData,
        		type: $targetForm.attr('method'),
        		processData: false,
                contentType: false,
                cache: false,
        		
        	}).done(function(data) {
        		console.log(data);
        		if(data.rows){
        			if(data.rows == 1){
        				alert('회원정보가 수정 되었습니다.');
        			} 
        		}else{
        			alert('수정이 실패했습니다. 다시 시도해주세요.');
        		}
        		
        	}).fail(function(jqXHR, textStatus, errorThrown) {
            	console.error('FAIL REQUEST: ', textStatus);
        		alert('처리중 오류가 발생하였습니다.');
            }).always(function() {
            	console.log('DONE');
            });
        } ,
        errorPlacement: function(error, element) {
        	error.insertAfter(element);
        }
	});
	
	/* 사업자 번호 체크 */
    $.validator.addMethod('bsnsNoChk', checkCorporateRegistrationNumber, "올바른 사업자번호 형식이 아닙니다.");
    $.validator.addMethod('passwordChk', passwordChk, "문자/숫자 포함 형태의 6~15자리 이내로 입력해주세요.");
    $.validator.addMethod( "extension", function( value, element, param ) {
    	param = typeof param === "string" ? param.replace( /,/g, "|" ) : "png|jpe?g|gif";
    	return this.optional( element ) || value.match( new RegExp( "\\.(" + param + ")$", "i" ) );
    });
	
    //숫자만 입력
    $("input[type='number']").off('keypress').on('keypress',function(e){
    	if (e.which && (e.which  > 47 && e.which  < 58 || e.which == 8)) {
    		
    	  } else {
    	    event.preventDefault();
    	  }
    });
});




function passwordChk(str, obj){
//	var re = /^.*(?=^.{6,15}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$/;
	//숫자와 문자만으로 비밀번호 검사(특수문자 필수 X)
	var re = /^.*(?=^.{6,15}$)(?=.*\d)(?=.*[a-zA-Z]).*$/;
	var valid = re.test(String(str).toLowerCase());
	if(valid){
		document.getElementById(obj.id).classList.remove("error");
		document.getElementById(obj.id).classList.add('valid')
		$(obj).siblings(".error").remove();
	}
	return valid;
}

// 사업자 등록증 유효성 검사
function checkCorporateRegistrationNumber(bizID, obj) {
	// bizID는 숫자만 10자리로 해서 문자열로 넘긴다. 
	var checkID = new Array(1, 3, 7, 1, 3, 7, 1, 3, 5, 1); 
	var tmpBizID, i, chkSum=0, c2, remander; 
	bizID = bizID.replace(/-/gi,''); 
	
	for (i=0; i<=7; i++) chkSum += checkID[i] * bizID.charAt(i); 
	c2 = "0" + (checkID[8] * bizID.charAt(8)); 
	c2 = c2.substring(c2.length - 2, c2.length); 
	chkSum += Math.floor(c2.charAt(0)) + Math.floor(c2.charAt(1)); 
	remander = (10 - (chkSum % 10)) % 10 ; 
    var valid = Math.floor(bizID.charAt(9)) == remander;
    if(valid){
    	console.log("Math.floor(valueMap[9]) === (10 - (checkSum % 10))", valid)
		$(obj).siblings(".error").remove();
	}
    return valid;
}

$(function() { 
	$.extend( $.validator.messages, 
			{ 	required: "필수 항목입니다.",
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
				maxlength: $.validator.format( "{0}자를 넘을 수 없습니다. " ), 
				minlength: $.validator.format( "{0}자 이상 입력하세요." ), 
				rangelength: $.validator.format( "문자 길이가 {0} 에서 {1} 사이의 값을 입력하세요." ), 
				range: $.validator.format( "{0} 에서 {1} 사이의 값을 입력하세요." ), 
				max: $.validator.format( "{0} 이하의 값을 입력하세요." ), 
				min: $.validator.format( "{0} 이상의 값을 입력하세요." ) } ); });

