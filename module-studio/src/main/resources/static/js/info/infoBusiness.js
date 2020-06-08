$(document).ready(function () {
    $("#infoFrm").validate({
        rules: {
            password: {
                required: true,
                passwordChk: true
            },
            passwordChk: {
                equalTo: "#password",
                required: true
            },
            businessNumber: {
                required: true,
                businessNumberChk: true
            },
            businessLicFile: {
//	        	required: true,
                extension: "png|jpg|jpeg"
            }
        },
        submitHandler: function (frm) {
            console.log("submitHandler");
            var formData = new FormData($("#infoFrm")[0])
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
                if (data.success) {
                    alert('회원정보가 수정 되었습니다.');
                } else{
                    alert(data.msg);
                }
            }).always(function () {
                console.log('DONE');
            });
        },
        errorPlacement: function (error, element) {
            error.insertAfter(element);
        }
    });


});


