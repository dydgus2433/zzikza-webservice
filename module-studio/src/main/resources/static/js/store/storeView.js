const weekDay = ['일', '월', '화', '수', '목', '금', '토'];
let indexes = [];
let uploadCall;
$(document).ready(function () {
    initDatePicker();
    initDate();
    $("#dateType").off('change').on('change', function (e) {
        const value = e.target.value;
        if (value === "W") {
            $("#dateVal").hide();
            $("#weekVal").show();
        } else if (value === "D") {
            $("#dateVal").datepicker().datepicker('setDate', new Date());
            $("#weekVal").hide();
            $("#dateVal").show();
        }
    });


    $("#btnAdd").off('click').on('click', function (e) {
        const type = $("#dateType").val();
        let name;
        let value;
        if (type === "W") {
            value = $.trim($("#weekVal").val());
            name = $("#weekVal").find("option[value='" + value + "']").text();
        } else {
            value = $.trim($("#dateVal").val());
            name = value;
        }

        $.ajax({
            url: "/api/studio-holiday",
            data: {dateCode: type, dateValue: value},
            type: "POST"
        }).done(function (result) {
            console.info(result);

            if (result.data) {
                if (value.length > 0) {
                    const li = document.createElement('li');
                    li.setAttribute("type", type);
                    li.setAttribute("value", value);
                    li.addEventListener("click", function () {
                        this.classList.toggle("active");
                    });
                    li.innerHTML = name;
                    $("#holiday").append(li)
                }
            } else {
                alert('추가하려는 값을 다시 확인해주세요.')
            }
        }).fail(function (jqXHR, textStatus, errorThrown) {
            alert(jqXHR.responseJSON.message);
        }).always(function () {
        });
    })


    // 기존에 내가 만든 구현
    $("#detailFiles").off('change').on('change', handleImgFileSelect);

    // 바로 파일 올려받을 구현
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

    $("#btnDel").off('click').on('click', function () {
        const options = $("#holiday li.active");
        let type = $("#dateType").val();

        for (let i = 0; i < options.length; i++) {
            const option = options[i];
            type = option.type.toString();
            let value = '';
            if (type === "D") {
                value = option.textContent.trim();
            } else {
                value = option.value;
            }

            $.ajax({
                url: "/api/studio-holiday",
                data: {dateCode: type, dateValue: value ,id : option.id},
                type: "DELETE",
                async: false
            }).done(function (data) {
                console.info(data);

                if (data.success) {
                    $("#holiday li.active[value='" + value + "']").remove();
                }
            }).fail(function (jqXHR, textStatus, errorThrown) {
                if (jqXHR.responseJSON.message) {
                    alert(jqXHR.responseJSON.message);
                } else {
                    alert('처리중 오류가 발생하였습니다.');
                }
            }).always(function () {
            });
        }
    });

    $("#studioDescription").on('keyup', function () {
        const tempText = $("textarea[name='studioDescription']");
        let tempChar = "";
        // TextArea의
        // 문자를 한글자씩
        // 담는다
        let tempChar2 = "";
        // 절삭된 문자들을
        // 담기 위한 변수
        let countChar = 0;
        // 한글자씩 담긴
        // 문자를 카운트
        // 한다
        let tempHangul = 0;
        // 한글을 카운트
        // 한다
        const maxSize = 1000;
        // 최대값

        // 글자수 바이트 체크를 위한 반복
        for (let i = 0; i < tempText.val().length; i++) {
            tempChar = tempText.val().charAt(i);

            // 한글일 경우 2 추가, 영문일 경우 1 추가
            if (escape(tempChar).length > 4) {
                countChar += 2;
                tempHangul++;
            } else {
                countChar++;
            }
        }

        // 카운트된 문자수가 MAX 값을 초과하게 되면 절삭 수치까지만 출력을 한다.(한글 입력 체크)
        // 내용에 한글이 입력되어 있는 경우 한글에 해당하는 카운트 만큼을 전체 카운트에서 뺀 숫자가 maxSize보다 크면 수행
        if ((countChar - tempHangul) > maxSize) {
            alert("최대 글자수를 초과하였습니다.");

            tempChar2 = tempText.val().substr(0, maxSize - 1);
            tempText.val(tempChar2);
        }
    });

    // 이미지 삭제 이벤트
    $(".btn_del_img").off('click').on('click', deleteImageAction);


    $("#mobileBtn").off('click').on('click', function () {
        window.open("", "formInfo", "height=600, width=474, menubar=no, scrollbars=yes, resizable=no, toolbar=no, status=no, left=50, top=50");
        var srcs = [];
        var options = [];

        var img_src = $("#sortable li div.img_area img");
        for (var i = 0; i < img_src.length; i++) {
            var src = img_src[i];
            srcs.push(src.src);
        }


        $("#keyword li").each(function (index, $this) {
            options.push($this.value);
        });
        const $stdoFrm = $('#stdoFrm');
        const input =
            $("<input>")
                .attr("class", "temp_element")
                .attr("type", "hidden")
                .attr("name", "files").val(srcs.join(","));
        $stdoFrm.append($(input));

        const input1 =
            $("<input>")
                .attr("class", "temp_element")
                .attr("type", "hidden")
                .attr("name", "keywords").val(options.join(","));
        $stdoFrm.append($(input1));

        const input2 =
            $("<input>")
                .attr("class", "temp_element")
                .attr("type", "hidden")
                .attr("name", "studioName").val($("span.login_info a").text());
        $stdoFrm.append($(input2));

        $stdoFrm.attr("action", "/store/preview/mobile");
        $stdoFrm.attr("method", "post");
        $stdoFrm.attr("target", "formInfo");


        $stdoFrm.submit();
        $(".temp_element").remove();
    });

    $("#pcBtn").off('click').on('click', function () {
        window.open("", "formInfo", "_blank");
        const srcs = [];
        const options = [];

        const img_src = $("#sortable li div.img_area img");
        for (let i = 0; i < img_src.length; i++) {
            const src = img_src[i];
            srcs.push(src.src);
        }


        $("#keyword li").each(function (index, $this) {
            options.push($this.value);
        });

        const input =
            $("<input>")
                .attr("class", "temp_element")
                .attr("type", "hidden")
                .attr("name", "files").val(srcs.join(","));
        $stdoFrm.append($(input));

        const input1 =
            $("<input>")
                .attr("class", "temp_element")
                .attr("type", "hidden")
                .attr("name", "keywords").val(options.join(","));
        $stdoFrm.append($(input1));

        const input2 =
            $("<input>")
                .attr("class", "temp_element")
                .attr("type", "hidden")
                .attr("name", "studioName").val($("span.login_info a").text());
        $stdoFrm.append($(input2));

        $stdoFrm.attr("action", "/store/preview/pc");
        $stdoFrm.attr("method", "post");
        $stdoFrm.attr("target", "formInfo");


        $stdoFrm.submit();
        $(".temp_element").remove();
    });
});

// 날짜초기화
function initDate() {
    makeDay();
}

function makeDay() {
    var html = "";
    for (var i = 0; i < weekDay.length; i++) {
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
    const url = '/api/studio-file';  // 사용
    $('#detailFiles').fileupload({
        url: url,
        type: 'POST',
        sequentialUploads: true,
        dataType: 'json',
        maxNumberOfFiles: 9,
        add: function (e, data) {
            const uploadFile = data.files[0];
            let isValid = true;

            if (!(/png|jpe?g|gif/i).test(uploadFile.name)) {

                alert('png, jpg, gif 만 가능합니다');

                isValid = false;

            } else if (uploadFile.size > 1024000 * 15) { // 1000 * 20kb

                alert('파일 용량은 15MB를 초과할 수 없습니다.');

                isValid = false;

            } else if ((data.originalFiles.length + $(".img_area").length) > 9) { // 100 * 5kb
                alert('파일은 9개를 넘길 수 없습니다.');
                return false;
            }

            if (isValid) {
                uploadCall = data.submit();
            }
        },

        done: function (e, data) {
            // data.result.data.filePath;
            // data.result.data.fileName

            const id = "img_id_" + data.result.data.id;
            $("#sortable li:last").remove();
            const html = "<li index=\"" + data.result.data.id + "\" id=\"" + id + "\">"
                + "<div class=\"img_area\" >"
                + "<button type=\"button\" class=\"btn_del_img\">삭제</button>"
                + "<img src=\"" + data.result.data.filePath + "\" data-file='" + data.result.data.fileName + "' onerror=\"this.src='" + "/images/common/no_img.gif'\" >"
                + "</div>"
                + "<div class=\"text\">0</div>"
                + "</li>";

            $(".shop_img_list").append(html);
            $(".btn_del_img").off('click').on('click', deleteImageAction);
        },
        progress: function (e, data) {
            // 검사하자
            const index = data.originalFiles.indexOf(data.files[0]);

            const id = "img_id_" + index;
            if (indexes.indexOf(index) > -1) {
            } else {
                indexes.push(index);
                // index 가 없을때
                const html = "<li index=\"\" id=\"" + id + "\">"
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
    let i;
    let input;
    const file_length = $(".img_area").length;
    if (file_length < 1) {
        alert('스튜디오 사진을 입력해주세요.')
        return false;
    }


    let inputs = $("input[type='number']");
    for (i = 0; i < inputs.length; i++) {
        input = inputs[i];
        if ($(input).val() === "") {
            const name = input.name;
            alert(input.placeholder);
            $(input).focus();
            return false;
        }
    }

    inputs = $("textarea[name='studioDescription']");
    for (i = 0; i < inputs.length; i++) {
        input = inputs[i];
        if ($(input).val() === "") {
            alert("스튜디오 설명을 입력하세요.");
            $(input).focus();
            return false;
        }
    }

    return true;
}

function submitAction() {
    if (!validate()) {
        return;
    }

    const $targetForm = $("#stdoFrm");
    let formData = new FormData($targetForm[0]);

    const options = [];
    $("#keyword li").each(function (index, $this) {
        options.push($(this).data("keyword-id"));
    });
    formData.append("keyword", options.join(","))
    $.ajax({
        url: '/api/studio-detail',
        data: formData,
        type: 'PUT',
        processData: false,
        contentType: false,
    }).done(function (data) {
        console.info(data);
        if (data.success) {
            alert('매장정보가 저장되었습니다.');
        } else {
            alert('매장정보 저장 중 오류가 발생했습니다.')
        }
// location.reload();
    }).fail(function (jqXHR, textStatus, errorThrown) {
        alert(jqXHR.responseJSON.message);
    }).always(function () {
        // console.info('DONE');
    });
}

function deleteImageAction(e) {
    if (!confirm("삭제하시겠습니까?")) {
        return false;
    }
    var target = $(e.currentTarget).parent().parent();
    var indexStr = $(target).attr("index");
    var src = $(target).children("div.img_area").children("img").attr("src");
    if (src) {
        // 이거로 파일 삭제해야함

        $.ajax({
            url: "/api/studio-file",
            data: {index: indexStr, flNm: src},
            type: "DELETE"
        }).done(function (a, b, c) {
            $(target).remove();
            indexing();
            if ($("#sortable div.text").length > 0) {
                $(".upload-name").val($("#sortable div.text").length + "개의 파일");
            } else {
                $(".upload-name").val("파일선택");
            }
        }).fail(function (jqXHR, textStatus, errorThrown) {
            alert(jqXHR.responseJSON.message);
        }).always(function () {
            //console.info('DONE');
        });
    } else {
// uploadCall.abort();
    }
}

// 인덱싱
function indexing() {
    let i;
    let card;
    let cards = $(".shop_img_list .text");
    if (cards.length < 1) {
        return;
    }

    for (i = 0; i < cards.length; i++) {
        card = cards[i];
        $(card).html(i + 1);
    }
    // 인덱싱 후 order update 해줘야함

    const indexes = [];
    let card_li = $("#sortable li");
    for (i = 0; i < card_li.length; i++) {
        card = card_li[i];
        indexes.push($(card_li[i]).attr("index"));
    }

    $.ajax({
        url: "/api/studio-file/order",
        data: {index: indexes.join(",")},
        type: "PUT"
    }).done(function (a, b, c) {
        if(b){
            alert(a.msg)
        }
    }).fail(function (jqXHR, textStatus, errorThrown) {
        alert(jqXHR.responseJSON.message);
    }).always(function () {
        //console.info('DONE');
    });
}

function initDatePicker() {
    $.datepicker.regional['ko'] = {
        dateFormat: 'yy-mm-dd',
        firstDay: 0,
    };
    $.datepicker.setDefaults($.datepicker.regional['ko']);
}
