//에디터
var Size = Quill.import('attributors/style/size');
Quill.register(Size, true);
var Font = Quill.import('formats/font');
Font.whitelist.push('mirza', 'roboto');
Quill.register(Font, true);
var FontAttributor = Quill.import('attributors/class/font');
FontAttributor.whitelist = [
    'sofia', 'slabo', 'roboto', 'inconsolata', 'ubuntu', 'Nanum-Brush-Script'
];
Quill.register(FontAttributor, true);

var toolbarOptions = [
    [{'font': FontAttributor.whitelist}],
    [{'size': Size.whitelist}],
    ['bold', 'italic', 'underline', 'strike'],        // toggled buttons
    [{'align': []}],

//		  [{ 'header': [1, 2, 3, 4, 5, 6, false] }],

//		  [{ 'size': [{'small': '14px'}, false, 'large', 'huge'] }], 
//		  [{'size':[ {'Small': '14px'}, {'Normal': false}, {'Large': '16px'}, {'Huge': '18px'}]}],
//		  [{ 'font-size': [{"10px" : "small"}, "16px","32px"] }],
//		  [{ 'size' : [{'10' : "10px"},2,3,4,5,6,7,8]}],
    ['link', 'image']
];

var quill = new Quill('#quill', {
    modules: {toolbar: toolbarOptions},
    theme: 'snow'
});

quill.getModule('toolbar').addHandler('image', function () {
    selectLocalImage();
});

//quill editor 사용
function selectLocalImage() {
    var input = document.createElement('input');
    input.setAttribute('type', 'file');
    input.click();

    //Listen upload local image and save to server
    input.onchange = function () {
        var fd = new FormData();
        var file = $(this)[0].files[0];
        fd.append('image', file);


        $.ajax({
            type: 'post',
            enctype: 'multipart/form-data',
            url: '/api/editor-image',
            data: fd,
            processData: false,
            contentType: false,
//					 beforeSend : function(xhr){
//						 xhr.setRequestHeader($("#_csrf_header").val(), $("#_csrf").val());
//					 },
        }).done(function (data) {
            quill.insertEmbed(quill.getLength(), 'image', data.data.filePath);
        }).fail(function (jqXHR) {
            alert(jqXHR.responseJSON.message);

        }).always(function () {
        });
    }

}

$(document).ready(function () {
    //기존에 내가 만든 구현
    $("#detailFiles").off('change').on('change', handleImgFileSelect);
    //바로 파일 올려받을 구현
    fileUploadSetting();
    $("#fileBtn").off('click').on('click', fileUploadAction)
    $("#sortable").sortable({
        update: function (event, ui) {
            console.log("update")
            console.log(event, ui)
            indexing()
        }
    });

    //숫자만 입력
    $("input[type='number']").off('keypress').on('keypress', function (e) {
        if (e.which && (e.which > 47 && e.which < 58 || e.which == 8)) {

        } else {
            event.preventDefault();
        }
    });

    $(".btn_save").off('click').on('click', submitAction);

    //이미지 삭제 이벤트
    $(".btn_del_img").off('click').on('click', deleteImageAction);

    $("#mobileBtn").off('click').on('click', function () {
        window.open("", "formInfo", "height=600, width=474, menubar=no, scrollbars=yes, resizable=no, toolbar=no, status=no, left=50, top=50");
        var srcs = [];

        var img_src = $("#sortable li div.img_area img");
        for (var i = 0; i < img_src.length; i++) {
            var src = img_src[i];
            srcs.push(src.src);
        }

        var input =
            $("<input>")
                .attr("class", "temp_element")
                .attr("type", "hidden")
                .attr("name", "files").val(srcs.join(","));
        $('#reqFrm').append($(input));

        var input2 =
            $("<input>")
                .attr("class", "temp_element")
                .attr("type", "hidden")
                .attr("name", "studioName").val($("span.login_info a").text());
        $('#reqFrm').append($(input2));

        $("#reqFrm").attr("action", "/request/preview/mobile");
        $("#reqFrm").attr("method", "post");
        $("#reqFrm").attr("target", "formInfo");


        $('#reqFrm').submit();
        $(".temp_element").remove();
    });

    $("#pcBtn").off('click').on('click', function () {
        console.log("PC 미리보기");
        window.open("", "formInfo", "_blank");
        var srcs = [];

        var img_src = $("#sortable li div.img_area img");
        for (var i = 0; i < img_src.length; i++) {
            var src = img_src[i];
            srcs.push(src.src);
        }

        var input =
            $("<input>")
                .attr("class", "temp_element")
                .attr("type", "hidden")
                .attr("name", "files").val(srcs.join(","));
        $('#reqFrm').append($(input));

        var input2 =
            $("<input>")
                .attr("class", "temp_element")
                .attr("type", "hidden")
                .attr("name", "studioName").val($("span.login_info a").text());
        $('#reqFrm').append($(input2));

        $("#reqFrm").attr("action", "/request/preview/pc");
        $("#reqFrm").attr("method", "post");
        $("#reqFrm").attr("target", "formInfo");


        $('#reqFrm').submit();
        $(".temp_element").remove();
    });
});


function fileUploadAction() {
    console.log("fileUploadAction");
    $("#detailFiles").trigger('click');
}

function handleImgFileSelect(e) {
    $(".imgs_wrap").empty();
    index = 0;
    indexes = [];
}

function fileUploadSetting() {
    // Change this to the location of your server-side upload handler:
    const url = '/api/request/product/file/temp';  // 사용
    const upload = $('#detailFiles').fileupload({
        url: url,
        sequentialUploads: true,
		dataType: 'json',
		maxNumberOfFiles: 10,
		formData: {
            tempKey: tempKey
        },
		type: 'post',
		add: function (e, data) {
            var uploadFile = data.files[0];
            var isValid = true;

            console.log(uploadFile.size);

            if (!(/png|jpe?g|gif/i).test(uploadFile.name)) {

                alert('png, jpg, gif 만 가능합니다');

                isValid = false;

            } else if (uploadFile.size > 1024000 * 15) { // 1000 * 20kb

                alert('파일 용량은 15MB를 초과할 수 없습니다.');

                isValid = false;

            } else if ((data.originalFiles.length + $(".img_area").length) > 10) { // 100 * 5kb
                alert('파일은 10개를 넘길 수 없습니다.');
                return false;
            }

            if (isValid) {

                data.submit();

            }
        },

        done: function (e, data) {
            console.log(e, data);
            const result = data.result.data;
            const id = "img_id_" + result.id;
            $("#sortable li:last").remove();
            const html = "<li index=\"" + result.id + "\" id=\"" + id + "\">"
                + "<div class=\"img_area\" >"
                + "<button type=\"button\" class=\"btn_del_img\">삭제</button>"
                + "<img src=\"" + result.filePath + "\" data-file='" + result.fileName + "'  onerror=\"this.src='" + "/images/common/no_img.gif'\" >"
                + "</div>"
                + "<div class=\"text\">0</div>"
                + "</li>";

            $(".shop_img_list").append(html);
            $(".btn_del_img").off('click').on('click', deleteImageAction);

        },
        //선행동작 1
        progress: function (e, data) {
            //검사하자
            console.log(e, data)
            var index = data.originalFiles.indexOf(data.files[0]);
            var id = "img_id_" + index;
            if (indexes.indexOf(index) > -1) {
                console.log("progressall 인덱스있음", index);
            } else {
                console.log("progressall 인덱스 없음", index);
                indexes.push(index);
                //index 가 없을때
                var html = "<li index=\"\" id=\"" + id + "\">"
                    + "<div class=\"img_area\" >"
                    + "<button type=\"button\" class=\"btn_del_img\">삭제</button>"
                    + "<img src=\"" + "/images/common/no_img.gif" + "\" alt=\"\">"
                    + "</div>"
                    + "<div class=\"text\">∞</div>"
                    + "</li>";

                $(".shop_img_list").append(html);
                $(".btn_del_img").off('click').on('click', deleteImageAction);
            }

        }, always: function (e, data) {
            indexing();
            $(".upload-name").val($("#sortable div.text").length + "개의 파일");
        }
    }).prop('disabled', !$.support.fileInput)
        .parent().addClass($.support.fileInput ? undefined : 'disabled');

}

function deleteImageAction(e) {
    console.log("this", $(this));
    if (!confirm("삭제하시겠습니까?")) {
        return false;
    }
    console.log("e", e);
    console.log("target", $(e.currentTarget).parent().parent())
    var target = $(e.currentTarget).parent().parent();
    var indexStr = $(target).attr("index");
    var src = $(target).children("div.img_area").children("img").attr("src");
    if (src) {
        //이거로 파일 삭제해야함

        $.ajax({
            url: "/api/request/product/file/temp",
            data: {index: indexStr, flNm: src},
            type: "DELETE",
        }).done(function (a, b, c) {
            $(target).remove();
            indexing();
            if ($("#sortable div.text").length > 0) {
                $(".upload-name").val($("#sortable div.text").length + "개의 파일");
            } else {
                $(".upload-name").val("파일선택");
            }
        }).fail(function (jqXHR) {
            alert(jqXHR.responseJSON.message);

        }).always(function () {
            console.log('DONE');
        });
    } else {
        console.log("uploadCall.abort()");
//		uploadCall.abort();
    }

}

//인덱싱
function indexing() {
    var cards = $(".shop_img_list .text");
    if (cards.length < 1) {
        return;
    }

    for (var i = 0; i < cards.length; i++) {
        var card = $(".shop_img_list .text")[i];
        $(card).html(i + 1);
    }
    //인덱싱 후 order update 해줘야함

    var indexes = [];
    var cards = $("#sortable li");
    for (var i = 0; i < cards.length; i++) {
        var card = cards[i];
        indexes.push($(cards[i]).attr("index"));
    }

    $.ajax({
        url: "/api/request/product/file/temp/order",
        data: {index: indexes.join(",")},
        type: "PUT"
    }).done(function (a, b, c) {
        console.log("success", a, b, c)
    }).fail(function (jqXHR) {
        alert(jqXHR.responseJSON.message);

    }).always(function () {
        console.log('DONE');
    });

}


function validate() {
    var file_length = $(".img_area").length;
    if (file_length < 1) {
        alert('상품 사진을 입력해주세요.')
        return false;
    }


    inputs = $("input[type='number']");
    for (var i = 0; i < inputs.length; i++) {
        var input = inputs[i];
        if ($(input).val() == "") {
            var name = input.name;
            if (name == 'productHour' || name == 'productMinute') {
                alert("촬영소요시간을 입력해주세요");
            } else {
                alert(input.placeholder);
            }
            $(input).focus();
            return false;
        }
    }

    inputs = $("textarea[name='productDescription']");
    for (var i = 0; i < inputs.length; i++) {
        var input = inputs[i];
        if ($(input).val() == "") {
            alert("상품설명을 입력하세요.");
            $(input).focus();
            return false;
        }
    }

    if ($("input[name='productMinute']").val() >= 60 || $("input[name='productMinute']").val() < 0) {
        alert("알맞은 시간을 입력하세요.");
        $("input[name='productMinute']").focus();
        return false;
    }

    if ($("textarea[name='productDescription']").val() == "") {
        alert("상품설명을 입력하세요.");
        $("textarea[name='productDescription']").focus();
        return false;
    }

    return true;
}

function submitAction() {
    console.log("submitAction");

    if (!validate()) {
        return;
    }

    var formData = new FormData($("#reqFrm")[0])

    var $targetForm = $("#reqFrm");

    var indexes = [];
    $("#sortable li").each(function (index, $this) {
        console.log("sortable li", index, $this);
        indexes.push($($this).attr("index"));
    });
    formData.append("index", indexes.join(","))
    formData.append("tempKey", tempKey);
    formData.append("productDescription", quill.container.firstChild.innerHTML);


    $.ajax({
        url: '/api/request/product',
        data: formData,
        type: $targetForm.attr('method'),
        processData: false,
        contentType: false,
    }).done(function (data) {
        if (data.success) {
            alert('요청상품이 등록되었습니다.');
            location.href = '/request/product/list';
        } else {
            alert('요청상품등록이 실패했습니다. 입력값을 확인해주세요.');
        }

    }).fail(function (jqXHR) {
        alert(jqXHR.responseJSON.message);

    }).always(function () {
        //console.info('DONE');
    });
}