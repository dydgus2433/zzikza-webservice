const Size = Quill.import('attributors/style/size');
const Font = Quill.import('formats/font');
const FontAttributor = Quill.import('attributors/class/font');


Quill.register(Size, true);
Quill.register(Font, true);
FontAttributor.whitelist = [
    'sofia', 'Nanum-Brush-Script', 'munche_gung_jungja',
    'Black_Han_Sans', 'Gaegu', 'Single_Day', 'Black_And_White_Picture', 'Hi_Melody', 'Gamja_Flower',
    'Cute_Font', 'East_Sea_Dokdo', 'Sunflower', 'Jua', 'Dokdo'
];
Size.whitelist = ["10px", "11px", "12px", "15px", "18px", "20px", "32px", "40px"];
//Color.whitelist.push('color-picker')
Quill.register(FontAttributor, true);

const Color = ["#000000", "#e60000", "#ff9900", "#ffff00", "#008a00", "#0066cc", "#9933ff", "#ffffff", "#facccc", "#ffebcc", "#ffffcc", "#cce8cc", "#cce0f5", "#ebd6ff", "#bbbbbb", "#f06666", "#ffc266", "#ffff66", "#66b966", "#66a3e0", "#c285ff", "#888888", "#a10000", "#b26b00", "#b2b200", "#006100", "#0047b2", "#6b24b2", "#444444", "#5c0000", "#663d00", "#666600", "#003700", "#002966", "#3d1466", 'color-picker'];
const toolbarOptions = [
    [{'font': FontAttributor.whitelist}],
    [{'size': Size.whitelist}],
    ['bold', 'italic', 'underline', 'strike'],        // toggled buttons
    [{'align': []}],
    [{'color': Color}, {'background': []}],
//		  [{ 'header': [1, 2, 3, 4, 5, 6, false] }],

//		  [{ 'size': [{'small': '14px'}, false, 'large', 'huge'] }],
//		  [{'size':[ {'Small': '14px'}, {'Normal': false}, {'Large': '16px'}, {'Huge': '18px'}]}],
//		  [{ 'font-size': [{"10px" : "small"}, "16px","32px"] }],
//		  [{ 'size' : [{'10' : "10px"},2,3,4,5,6,7,8]}],
    ['link', 'image']
];

const quill = new Quill('#quill', {
    theme: 'snow',
    modules: {
        imageResize: {displaySize: true},
        toolbar: toolbarOptions,
    },
    placeholder: '상품 설명은 고객에게 상품 구성 및 소개를 하는 영역입니다.\n홈페이지와 다른 연락처는 입력하지 말아주세요:) \n입력하실 경우 수정될 수 있습니다.'
});

quill.getModule('toolbar').addHandler('image', function () {
    selectLocalImage();
});
// customize the color tool handler
quill.getModule('toolbar').addHandler('color', function (value) {
    console.log(value)
    // if the user clicked the custom-color option, show a prompt window to get the color
    showColorPicker(value);
});

//quill editor 사용
function selectLocalImage() {
    const input = document.createElement('input');
    input.setAttribute('type', 'file');
    input.click();

    //Listen upload local image and save to server
    input.onchange = function () {
        const fd = new FormData();
        const file = $(this)[0].files[0];
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

function showColorPicker(value) {
    if (value === 'color-picker') {
        var picker = document.getElementById('color-picker');
        if (!picker) {
            picker = document.createElement('input');
            picker.id = 'color-picker';
            picker.type = 'color';
            picker.style.display = 'none';
            picker.value = '#FF0000';
            document.body.appendChild(picker);

            picker.addEventListener('change', function (e) {
                Color.push(picker.value);
                quill.format('color', picker.value);
            }, false);
        }
        picker.click();
    } else {
        quill.format('color', value);
    }
}

const toolbar = quill.getModule('toolbar');
toolbar.addHandler('color', showColorPicker);
const weekDay = ['일', '월', '화', '수', '목', '금', '토'];
let indexes = [];
$(document).ready(function () {
    initDatePicker();
    initDate();

    //기존에 내가 만든 구현
    $("#detailFiles").off('change').on('change', handleImgFileSelect);

    //바로 파일 올려받을 구현
    fileUploadSetting();
    $("#fileBtn").off('click').on('click', fileUploadAction)
    $("#submitBtn").off('click').on('click', submitAction)

    $("#sortable").sortable({
        update: function (event, ui) {
            indexing()
        }
    });

    $("#rightBtn").off('click').on('click', function () {
        const options = [];
        $("#keyword li.active").each(function (index, $this) {
            options.push(($this));
        });

        for (let i = 0; i < options.length; i++) {
            const option = options[i];
            $("#keywordCate").append(option);
        }
    });

    $("#leftBtn").off('click').on('click', function () {
        const options = [];
        $("#keywordCate li.active").each(function (index, $this) {
            options.push(($this));
        });

        for (let i = 0; i < options.length; i++) {
            const option = options[i];
            $("#keyword").append(option);
        }
    });


    //숫자만 입력
    $("input[type='number']").off('keypress').on('keypress', function (e) {
        if (e.which && (e.which > 47 && e.which < 58 || e.which === 8)) {

        } else {
            e.preventDefault();
        }
    });

    $(".btn_del_img").off('click').on('click', deleteImageAction);


    // input 텍스트에 숫자만 콤마 찍어가면서 받기

    $(document).on("keypress", "input[type=text].number", function (event) {
        if ((event.keyCode < 48 || event.keyCode > 57) && (event.keyCode < 96 || event.keyCode > 105) && (event.keyCode !== 8) && (event.keyCode !== 46))
            event.returnValue = false;

    });


    $(document).on("keyup", "input[type=text].number", function () {

        const $this = $(this);

        const num = $this.val().replace(/[,]/g, "");


        const parts = num.toString().split(".");

        parts[0] = parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ",");

        $this.val(parts.join("."));

    });


    $("#bigOption").sortable({
        update: function (event, ui) {
            bigOptionIndexing()
        }
    });


    $("#optionSort").sortable({
        update: function (event, ui) {
            smallOptionIndexing()
        }
    });


    if ($("#productSalePrice").val() > 0) {
        //select Y
        $("#productSalePriceSel").val("Y");
        $("#productSalePrice").show();
    }
    $("#productSalePriceSel").on("change", function (e) {
        const selValue = $("#productSalePriceSel").val();
        console.log("change ", e, selValue);
        if (selValue == 'Y') {
            $("#productSalePrice").show();
        } else {
            $("#productSalePrice").hide();
            $("#productSalePrice").val(0)
        }

    })
});

//날짜초기화
function initDate() {
    makeDay();
}

function makeDay() {
    let html = "";
    for (let i = 0; i < weekDay.length; i++) {
        html += "<option value=" + i + ">" + weekDay[i] + "요일</option>";
    }
    $("#weekVal").html(html);
}

function pad(n, width) {
    n = n + '';
    return n.length >= width ? n : new Array(width - n.length + 1).join('0') + n;
}

function fileUploadSetting() {
    // Change this to the location of your server-side upload handler:
    const url = '/api/product-file/temp';  // 사용
    const upload = $('#detailFiles').fileupload({
        url: url,
        sequentialUploads: true,
        dataType: 'json',
        maxNumberOfFiles: 10,
        type: 'POST',
        formData: {
            tempKey: tempKey
        },
        add: function (e, data) {
            const uploadFile = data.files[0];
            let isValid = true;
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
//	    	indexes.splice(index,1);
            //삭제하면서 progressBar 없애줘
            const result =  data.result.data;
            const id = "img_id_" + result.id;
            $("#sortable li:last").remove();
            // result
            /*
            {id: "PFT0000000003", fileName: "75b20055-b924-4cc4-b236-6028f6530e31.jpg", filePath: "https://zzikza-upload.s3.ap-northeast-2.amazonaws.…ad/thumb/75b20055-b924-4cc4-b236-6028f6530e31.jpg", fileOrder: 1}
id: "PFT0000000003"
fileName: "75b20055-b924-4cc4-b236-6028f6530e31.jpg"
filePath: "https://zzikza-upload.s3.ap-northeast-2.amazonaws.com/upload/thumb/75b20055-b924-4cc4-b236-6028f6530e31.jpg"
fileOrder: 1
             */
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

            const index = data.originalFiles.indexOf(data.files[0]);
            const id = "img_id_" + index;
            if (indexes.indexOf(index) > -1) {
            } else {
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

function fileUploadAction() {
    $("#detailFiles").trigger('click');
}

function handleImgFileSelect(e) {
    $(".imgs_wrap").empty();
    index = 0;
    indexes = [];
}

function validate() {
    const file_length = $(".img_area").length;
    if (file_length < 1) {
        alert('상품사진을 입력해주세요.')
        return false;
    }

    let $inputs = $("input[type='text']");
    for (let i = 0; i < $inputs.length; i++) {
        const input = $inputs[i];
        if ($(input).val() === "") {
            if (input.name === "optionName" || input.name === "optionPrice") {
                continue;
            }

            if (input.placeholder) {
                if (input.placeholder === "https://quilljs.com") {
                    continue;
                }
                alert(input.placeholder);
                $(input).focus();
                return false;
            }
        }
    }


    let $number = $("input[type='number']");
    for (let i = 0; i < $number.length; i++) {
        const input = $number[i];
        if ($(input).val() === "") {
            const name = input.name;
            if (name === 'productHour' || name === 'productMinute') {
                alert("촬영소요시간을 입력해주세요");
            } else if (name === 'productSalePrice') {
                continue;
            } else {
                alert(input.placeholder);
            }
            $(input).focus();
            return false;
        }
    }

    const $productHour = $("input[name='productHour']");
    if ($productHour.val() < 0) {
        alert("알맞은 시간을 입력해주세요.");
        $productHour.focus();
        return false;
    }
    const $productMinute = $("input[name='productMinute']");
    if ($productMinute.val() >= 60 || $productMinute.val() < 0) {
        alert("알맞은 시간을 입력하세요.");
        $productMinute.focus();
        return false;
    }
    const $productDescription = $("textarea[name='productDescription']");
    if ($productDescription.val() === "") {
        alert("상품설명을 입력하세요.");
        $productDescription.focus();
        return false;
    }

    return true;
}

function submitAction() {

    if (!validate()) {
        return;
    }

    const $targetForm = $("#prdFrm");
    const formData = new FormData($targetForm[0]);
    const options = [];
    $("#keyword li").each(function (index, $this) {
        options.push($(this).data("keyword-id"));
    });
    formData.append("keyword", options.join(","))

    let indexes = [];
    $("#sortable li").each(function (index, $this) {
        indexes.push($($this).attr("index"));
    });
    formData.append("index", indexes.join(","));
    formData.append('productDescription', quill.container.firstChild.innerHTML);

    let url = '/api/product';
    let ajaxType = 'POST';

    formData.append("tempKey", tempKey);

    $.ajax({
        url: url,
        data: formData,
        type: ajaxType,
        processData: false,
        contentType: false,
    }).done(function (data) {
        console.log(url, data);
        if (data.success) {
            alert('상품이 등록되었습니다.');
            location.href = '/product/list';
        } else {
            alert('상품등록이 실패했습니다. 입력값을 확인해주세요.');
        }
    }).fail(function (jqXHR) {
        alert(jqXHR.responseJSON.message);

    }).always(function () {
        //console.info('DONE');
    });
}

function deleteImageAction(e) {
    if (!confirm("삭제하시겠습니까?")) {
        return false;
    }
    const target = $(e.currentTarget).parent().parent();
    const indexStr = $(target).attr("index");
    const src = $(target).children("div.img_area").children("img").attr("src");
    if (src) {
        // 이거로 파일 삭제해야함

        $.ajax({
            url: "/api/product-file/temp",
            data: {index: indexStr, fileName: src, tempKey: tempKey},
            type: "DELETE"
        }).done(function (a, b, c) {
            $(target).remove();
            indexing();
            const textLength = $("#sortable div.text").length;
            const $uploadName = $(".upload-name");
            if (textLength > 0) {
                $uploadName.val(textLength + "개의 파일");
            } else {
                $uploadName.val("파일선택");
            }
        }).fail(function (jqXHR) {
            alert(jqXHR.responseJSON.message);

        }).always(function () {
        });
    } else {
    }
}

function updateAndOrderOption(options) {
    if (options.length < 1) {
        return;
    }
    const indexes = [];
    for (let i = 0; i < options.length; i++) {
        const option = $(options[i]);

        indexes.push($(option).data("optn_id"));
    }

    $.ajax({
        url: "/api/product-file/temp/order",
        data: {optnIds: indexes.join(","), tempKey: tempKey},
        type: "PUT"
    }).done(function (a, b, c) {
    }).fail(function (jqXHR) {
        alert(jqXHR.responseJSON.message);

    }).always(function () {
    });
}

function smallOptionIndexing() {
    const options = $("#optionSort li");
    updateAndOrderOption(options);

}

function bigOptionIndexing() {
    const options = $("#bigOption li").not(":last");
    updateAndOrderOption(options);
    // if(options.length < 1){
    // 	return;
    // }
    // var indexes = [];
    // for(var i = 0; i < options.length; i++){
    // 	var option = $(options[i]);
    //
    // 	indexes.push($(option).data("optn_id"));
    // }
    //
    // $.ajax({
    // 	url:  "/api/updateOptionOrder",
    // 	data : {optnIds : indexes.join(","), tempKey : tempKey},
    // 	type: "post"
    // }).done(function(a,b,c){
    // }).fail(function(jqXHR) {
    // 	alert(jqXHR.responseJSON.message);
    //
    // }).always(function() {
    // });
}

//인덱싱
function indexing() {
    let card;
    let i;
    let cards = $(".shop_img_list .text");
    if (cards.length < 1) {
        return;
    }

    for (i = 0; i < cards.length; i++) {
        card = cards[i];
        $(card).html(i + 1);
    }
    //인덱싱 후 order update 해줘야함

    const indexes = [];
    cards = $("#sortable li");
    for (i = 0; i < cards.length; i++) {
        card = cards[i];
        indexes.push($(cards[i]).attr("index"));
    }

    $.ajax({
        url:  "/api/product-file/temp/order",
        data: {index: indexes.join(","), tempKey: tempKey},
        type: "PUT"
    }).done(function (a, b, c) {
    }).fail(function (jqXHR) {
        alert(jqXHR.responseJSON.message);

    }).always(function () {
    });
}

function initDatePicker() {
    $.datepicker.regional['ko'] = {
        dateFormat: 'yy-mm-dd',
        firstDay: 0,
    };
    $.datepicker.setDefaults($.datepicker.regional['ko']);
}

