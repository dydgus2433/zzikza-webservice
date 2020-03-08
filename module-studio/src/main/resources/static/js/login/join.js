$(document).ready(function() {
	$("#stdoFrm").validate({
		rules: {
	        pw: {
	            required: true,
	            passwordChk : true
	        },
	        pwChk: {
	        	equalTo: "#pw",
	            required: true
	        },
	        stdoId : {
	        	required: true,
	        	remote :{type:"POST",url:contextPath+"/api/selectStudioId", dataType : "json" , 
	        		dataFilter : function(responseData){
	        			return responseData;
	        		}
	        	},
	            idChk : true
	        	// 중복체크 해달라고 해야함
	        },
	        bsnsNo : {
	        	required: true,
	        	bsnsNoChk : true
	        },
	        mngEmail : {
	        	required: true,
	        	emailChk : true
	        },
	        rqrdTermYn :  {
	        	required: true,
	        	requireChk : true
	        },
//	        bsnsLicFile : {
//	        	required: true,
//	        	extension : "png|jpg|jpeg"
//	        }, 
	        tel :{
	        	telRexChk : true
	        }, 
	        mngCntt :{
	        	telRexChk : true
	        }
	    },
	    messages:{/* validate 메세지 */
	    	stdoId:{
	    		required:"아이디를 입력하세요",
	    		rangelength:"아이디는 5글자 이상 15글자 이하로 입력해주세요",
	    		remote: "{0} 는 이미 존재하는 아이디입니다."
	    	}
	    },
        submitHandler: function (frm){
        	console.log("submitHandler");
        	var formData = new FormData($("#stdoFrm")[0])
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
        				alert('회원가입이 완료 되었습니다.');
        				location.href = '/';
        			} 
        		}else{
        			alert('회원가입이 실패했습니다. 다시 시도해주세요.');
        		}
        		
        	}).fail(function(jqXHR, textStatus, errorThrown) {
            	console.error('FAIL REQUEST: ', textStatus);
        		alert('처리중 오류가 발생하였습니다.');
            }).always(function() {
            	console.log('DONE');
            });
        } ,
        errorPlacement: function(error, element) {
            if (element.attr("name") == "rqrdTermYn"){
             	if($("#rqrdTermYn").siblings(".error").size() == 0){
             		error.insertAfter("#policy");
             	}
            } else {
            	error.insertAfter(element);
            }
          }
	});
	
	/* 사업자 번호 체크 */
    $.validator.addMethod('bsnsNoChk', checkCorporateRegistrationNumber, "올바른 사업자번호 형식이 아닙니다.");
    $.validator.addMethod('emailChk', emailChk, "올바른 이메일 형식이 아닙니다.");
    $.validator.addMethod('requireChk', requireChk, "필수항목에 동의해주세요.");
    $.validator.addMethod('passwordChk', passwordChk, "문자/숫자 포함 형태의 6~15자리 이내로 입력해주세요.");
    $.validator.addMethod('idChk', idChk, "영문/숫자 포함 5~15자리 이내로 입력해주세요");
    $.validator.addMethod('telRexChk', telRexChk, "올바른 전화번호를 입력해주세요");
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

function requireChk(a,b,c){
	var required = $("#rqrdTermYn").prop('checked');
	if(required){
		$("#rqrdTermYn").val("Y");
	}else{
		$("#rqrdTermYn").val("N");
	}
	return required;
}

function emailChk(str, obj){
	var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	var valid = re.test(String(str).toLowerCase());
	if(valid){
		document.getElementById('mngEmail').classList.remove("error");
		document.getElementById('mngEmail').classList.add('valid')
		$("#mngEmail").siblings(".error").remove();
	}
	return valid;
}


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

function idChk(str, obj){
	var re = /^[a-z]+[a-z0-9]{4,14}$/g;
	var valid = re.test(String(str).toLowerCase());
	if(valid){
		document.getElementById(obj.id).classList.remove("error");
		document.getElementById(obj.id).classList.add('valid')
		$(obj).siblings(".error").remove();
	}
	return valid;
}

//function companyNameChk(str, obj){
//	var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
//	var valid = re.test(String(str).toLowerCase());
//	if(valid){
//		document.getElementById(obj.id).classList.remove("error");
//		document.getElementById(obj.id).classList.add('valid')
//		$(obj).siblings(".error").remove();
//	}
//	return valid;
//}



function telRexChk(str, obj){
	var re = /(^02.{0}|^01.{1}|[0-9]{3})([0-9]{3,4})([0-9]{4})$/g;
	var valid = re.test(String(str).toLowerCase());
	if(valid){
		document.getElementById(obj.id).classList.remove("error");
		document.getElementById(obj.id).classList.add('valid')
		$(obj).siblings(".error").remove();
	}
	return valid;
}
function searchPostcode() {
	new daum.Postcode({
		oncomplete : function(data) {
			// 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

			// 각 주소의 노출 규칙에 따라 주소를 조합한다.
			// 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
			var addr = ''; // 주소 변수
			var extraAddr = ''; // 참고항목 변수

			// 사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
			if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
				addr = data.roadAddress;
			} else { // 사용자가 지번 주소를 선택했을 경우(J)
				addr = data.jibunAddress;
			}

			// 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
			if (data.userSelectedType === 'R') {
				// 법정동명이 있을 경우 추가한다. (법정리는 제외)
				// 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
				if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
					extraAddr += data.bname;
				}
				// 건물명이 있고, 공동주택일 경우 추가한다.
				if (data.buildingName !== '' && data.apartment === 'Y') {
					extraAddr += (extraAddr !== '' ? ', ' + data.buildingName
							: data.buildingName);
				}
				// 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
				if (extraAddr !== '') {
					extraAddr = ' (' + extraAddr + ')';
				}
				// 조합된 참고항목을 해당 필드에 넣는다.
				// document.getElementById("sample6_extraAddress").value =
				// extraAddr;

			} else {
				// document.getElementById("sample6_extraAddress").value = '';
			}
//			<input type="text" id="sido" name="sido"  class=""/>
//				<input type="text" id="gugun" name="gugun"  class=""/>
//				<input type="text" id="dong" name="dong"  class=""/>
			// 우편번호와 주소 정보를 해당 필드에 넣는다.
			document.getElementById('postCd').value = data.zonecode;
			document.getElementById("addr").value = addr;
			var addrArr ;
			if(data.autoJibunAddress){
				addrArr = data.autoJibunAddress.split(" ");
			}else{
				addrArr = data.jibunAddress.split(" ");
			}
			
			
			document.getElementById('sido').value = addrArr[0];
			document.getElementById('gugun').value = addrArr[1];
			document.getElementById('dong').value = addrArr[2];
			
			document.getElementById('postCd').classList.remove("error");
			document.getElementById('postCd').classList.add('valid');
			
			$("#postCd-error").remove();
			document.getElementById('addr').classList.remove("error");
			document.getElementById('addr').classList.add('valid')
			$("#addr-error").remove();
			// 커서를 상세주소 필드로 이동한다.
			document.getElementById("addrDtl").focus();
			//위경도 구하기
			$(function() {
				$.ajax({
					url: contextPath+'/api/selectLatLng',
					data: {
						query: addr
					},
					dataType: 'json',
				}).done(function(data) {
					if(data.addresses){
						//위도
						document.getElementById('lttd').value = data.addresses[0].y;
						//경도
						document.getElementById('lgtd').value= data.addresses[0].x;
					}else{
						//error
					}
					//data.address[0].x
					//data.address[0].y
				}).fail(function(jqXHR, textStatus, errorThrown) {
	            	console.log('FAIL REQUEST: ', textStatus);
	        		alert('처리중 오류가 발생하였습니다.');
	            }).always(function() {
	            	//console.info('DONE');
	            });
			});
			//주소 disable
			$("#postCd").prop("readonly", true);
			$("#addr").prop("readonly", true);
		}
	}).open();
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
	$("#stdoFrm").validate(); 
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

