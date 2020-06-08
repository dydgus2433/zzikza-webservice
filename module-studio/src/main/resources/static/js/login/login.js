$(document).ready(function() {
	$("#id").focus();

	$("#studioId").on("keypress", function(e) {
		if (e.keyCode == 13) {
			e.preventDefault();
			$("#password").focus();
		}
	});

	$("#password").on("keypress", function(e) {
		if (e.keyCode == 13) {
			e.preventDefault();
			$("#loginBtn1").trigger("click");
		}
	});
	
	$("#loginFrm1").validate({
		rules : {
			studioId : {
				required : true,
				minlength : 5
			// 중복체크 해달라고 해야함
			},
			password : {
				required : true,
				minlength : 5
			}

		},
		messages : {/* validate 메세지 */
			studioId : {
				required : "아이디를 입력하세요",
				rangelength : "아이디는 5글자 이상 15글자 이하로 입력해주세요",
			},
			password : {
				required : "비밀번호를 입력하세요"
			// minlength: 5
			}
		},
		submitHandler : function(submitForm, event) {
            console.log(submitForm);
            console.log(event);
            console.log($('#loginFrm1').serialize());
			 var returl = findGetParameter();

//        $.ajax({
//            type : 'POST',
//            url :'/api/v1/posts',
//            dataType : 'json',
//            contentType : 'application/json; charset=utf-8',
//            data : JSON.stringify(data)
//        }).done(function(){
//            alert("글이 등록되었습니다.");
//            window.location.href = '/';
//        }).fail(function(error){
//            alert(JSON.stringify(error));
//        })
            var data = {
                studioId : $("input[name=studioId]").val(),
                password : $("input[name=password]").val()
            };
			 $.ajax({
                type : 'POST',
			    url :  '/loginProcess',
			    dataType : 'json',
                contentType : 'application/json; charset=utf-8',
                data : JSON.stringify(data)
        	}).done(function(result,success) {
        		if(result.success){
					if(!returl){
						location.href = 'board/notice';
					}else{
						location.href = returl;
					}
				}else{
        			alert(result.msg);
				}
            });

		}
	});
//	
//	$("#siteBtn").on('click',function(){
//		location.href =  "/preview/guide";
//	});

});
$(function() {
	$.extend($.validator.messages, {
		required : "필수 항목입니다.",
		remote : "항목을 수정하세요.",
		email : "유효하지 않은 E-Mail주소입니다.",
		url : "유효하지 않은 URL입니다.",
		date : "올바른 날짜를 입력하세요.",
		dateISO : "올바른 날짜(ISO)를 입력하세요.",
		number : "유효한 숫자가 아닙니다.",
		digits : "숫자만 입력 가능합니다.",
		creditcard : "신용카드 번호가 바르지 않습니다.",
		equalTo : "같은 값을 다시 입력하세요.",
		extension : "올바른 확장자가 아닙니다.",
		maxlength : $.validator.format("{0}자를 넘을 수 없습니다. "),
		minlength : $.validator.format("{0}자 이상 입력하세요."),
		rangelength : $.validator.format("문자 길이가 {0} 에서 {1} 사이의 값을 입력하세요."),
		range : $.validator.format("{0} 에서 {1} 사이의 값을 입력하세요."),
		max : $.validator.format("{0} 이하의 값을 입력하세요."),
		min : $.validator.format("{0} 이상의 값을 입력하세요.")
	});
});

function findGetParameter() {
    var result = null,
        tmp = [];
    tmp = location.search
        .substr(1)
        .split("returl=");
    if(tmp[1]){
    	result = decodeURIComponent(tmp[1]);
    }
    return result;
}
