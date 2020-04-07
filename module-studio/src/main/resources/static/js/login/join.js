$(document).ready(function () {
    $("#stdoFrm").validate({
        rules: {
            password: {
                required: true,
                passwordChk: true
            },
            passwordChk: {
                equalTo: "#password",
                required: true
            },
            studioId: {
                required: true,
                remote: {
                    type: "GET", url: "/api/studio/id-check", dataType: "json",
                    dataFilter: function (responseData) {
                        return responseData;
                    }
                },
                idChk: true
                // 중복체크 해달라고 해야함
            },
            businessNumber: {
                required: true,
                businessNumberChk: true
            },
            managerEmail: {
                required: true,
                emailChk: true
            },
            requiredTermStatus: {
                required: true,
                requireChk: true
            },
	        businessLicFile : {
	        	required: true,
	        	extension : "png|jpg|jpeg"
	        },
            tel: {
                telRexChk: true
            },
            managerTel: {
                telRexChk: true
            }
        },
        messages: {/* validate 메세지 */
            studioId: {
                required: "아이디를 입력하세요",
                rangelength: "아이디는 5글자 이상 15글자 이하로 입력해주세요",
                remote: "{0} 는 이미 존재하는 아이디입니다."
            }
        },
        submitHandler: function (frm) {
            console.log("submitHandler");
            var formData = new FormData($("#stdoFrm")[0])
            var $targetForm = $(frm);

            console.log("submitHandler", formData, $targetForm);
            $.ajax({
                url: $targetForm.attr('action'),
                enctype: 'multipart/form-data',
                data: formData,
                type: $targetForm.attr('method'),
                processData: false,
                contentType: false,
                cache: false,

            }).done(function (data) {
                console.log(data);
                if (data.success) {
                    alert('회원가입이 완료 되었습니다.');
                    location.href = '/';
                } else {
                    alert('회원가입이 실패했습니다. 다시 시도해주세요.');
                }

            }).fail(function (jqXHR, textStatus, errorThrown) {
                alert(jqXHR.responseJSON.message);
            }).always(function () {
                console.log('DONE');
            });
        },
        errorPlacement: function (error, element) {
            if (element.attr("name") == "requiredTermStatus") {
                if ($("#requiredTermStatus").siblings(".error").size() == 0) {
                    error.insertAfter("#policy");
                }
            } else {
                error.insertAfter(element);
            }
        }
    });

});
function test(){

    let tt ="studioId=tester2&password=dkssud123&passwordChk=dkssud123&studioName=찍자컴퍼니&tel=01063626542&postCode=02713&addr=서울 성북구 서경로 94&addrDtl=아느칸빌딩 203호&managerName=리롱시엔&managerTel=01022772433&managerEmail=lee@4themoment.co.kr&businessNumber=8935300335&requiredTermStatus=Y&sido=서울특별시&gugun=성북구&dong=정릉1동&lttd=37.6113417&lgtd=127.0139707".split("&");
    for(let i =0; i < tt.length; i++){

        const ttt = tt[i].split("=");
        console.log(ttt);
        const first = ttt[0];
        const second = ttt[1];
        console.log("first", first);
        if(second){
            console.log("second", second);
            document.getElementById(first).value = second;
        }
    }
}

function telRexChk(str, obj) {
    var re = /(^02.{0}|^01.{1}|[0-9]{3})([0-9]{3,4})([0-9]{4})$/g;
    var valid = re.test(String(str).toLowerCase());
    if (valid) {
        document.getElementById(obj.id).classList.remove("error");
        document.getElementById(obj.id).classList.add('valid')
        $(obj).siblings(".error").remove();
    }
    return valid;
}

function searchPostcode() {
    new daum.Postcode({
        oncomplete: function (data) {
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
            document.getElementById('postCode').value = data.zonecode;
            document.getElementById("addr").value = addr;
            var addrArr;
            if (data.autoJibunAddress) {
                addrArr = data.autoJibunAddress.split(" ");
            } else {
                addrArr = data.jibunAddress.split(" ");
            }


            document.getElementById('sido').value = addrArr[0];
            document.getElementById('gugun').value = addrArr[1];
            document.getElementById('dong').value = addrArr[2];

            document.getElementById('postCode').classList.remove("error");
            document.getElementById('postCode').classList.add('valid');

            $("#postCode-error").remove();
            document.getElementById('addr').classList.remove("error");
            document.getElementById('addr').classList.add('valid')
            $("#addr-error").remove();
            // 커서를 상세주소 필드로 이동한다.
            document.getElementById("addrDtl").focus();
            //위경도 구하기
            $(function () {
                $.ajax({
                    url: '/api/location',
                    data: {
                        query: addr
                    },
                    dataType: 'json',
                }).done(function (data) {
                    if (data.addresses) {
                        //위도
                        document.getElementById('lttd').value = data.addresses[0].y;
                        //경도
                        document.getElementById('lgtd').value = data.addresses[0].x;
                    } else {
                        //error
                    }
                    //data.address[0].x
                    //data.address[0].y
                }).fail(function (jqXHR, textStatus, errorThrown) {
                    console.log('FAIL REQUEST: ', textStatus);
                    alert('처리중 오류가 발생하였습니다.');
                }).always(function () {
                    //console.info('DONE');
                });
            });
            //주소 disable
            $("#postCode").prop("readonly", true);
            $("#addr").prop("readonly", true);
        }
    }).open();
}


