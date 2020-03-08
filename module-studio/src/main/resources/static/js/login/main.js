/**
 * 
 */
$(document).ready(function (){
		$("#id").focus();

		$("#stdoId").on("keypress",  function(e){
			if(e.keyCode == 13){
				e.preventDefault();
				$("#pw").focus();
			}
		});
		
		$("#pw").on("keypress", function(e){
			if(e.keyCode == 13){
				e.preventDefault();
				$("#loginBtn").trigger("click");
			}
		});
		$("#loginFrm").validate({
			rules: {
				stdoId : {
		        	required: true,
		            minlength: 5
		        	//중복체크 해달라고 해야함
		        },
		        pw: {
		            required: true,
		            minlength: 5
		        }
		        
		    },
		    messages:{/* validate 메세지 */
		    	stdoId:{
		    		required:"아이디를 입력하세요",
		    		rangelength:"아이디는 5글자 이상 15글자 이하로 입력해주세요",
		    	},pw: {
		            required: "비밀번호를 입력하세요"
//		            minlength: 5
		        }
		    },
	        submitHandler: function (frm){
	            frm.submit();   //유효성 검사를 통과시 전송
	        }
		});
		
		
});

$(function() { 
	$("#loginFrm").validate(); 
	$.extend( $.validator.messages, 
			{ required: "필수 항목입니다.",
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
