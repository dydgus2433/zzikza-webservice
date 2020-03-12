let toolbarOptions = [
    ['bold', 'italic', 'underline', 'strike'],        // toggled buttons
    [{'align': []}],
    ['link', 'image']
];
let quill = new Quill('#quill', {
    modules: {toolbar: toolbarOptions},
    theme: 'snow'
});

quill.getModule('toolbar').addHandler('image', function () {
    selectLocalImage();
});

//quill editor 사용
function selectLocalImage() {
    var input = document.createElement('input');
    document.createElement('input')
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
        }).done(function (data) {
            if(data.success){
                quill.insertEmbed(quill.getLength(), 'image', data.data.filePath);
            }

        }).fail(function (jqXHR, textStatus, errorThrown) {
            console.error('FAIL REQUEST: ', textStatus);
            alert('처리중 오류가 발생하였습니다.');
        }).always(function () {
            console.log('DONE');
        });
    }

}

let filesTempArr = [];
//quill editor 사용
$(document).ready(function () {
    console.log("글쓰기");
    /* === fileup === */
    var fileTarget = $('.filebox .upload-hidden');
    fileTarget.on('change', function () {
        if (window.FileReader) {  // modern browser
            var filename = $(this)[0].files[0].name;
        } else {  // old IE
            console.log("$(this).val()", $(this).val());
            var filename = $(this).val().split('/').pop().split('\\').pop();
        }
        $(this).siblings('.upload-name').val(filename);
    });


    $("#btnInsert").off('click').on('click', function (e) {
        if ($("input[name='title']").val() === "") {
            alert('제목을 입력해주세요.');
            return;
        }

        if (quill.container.firstChild.innerHTML === "<p><br></p>") {
            alert('내용을 입력해주세요.');
            return;
        }


        var formData = new FormData($("#boardFrm")[0]);
        // 파일 데이터
        var fileSize = 0;
        for (var i = 0; i < filesTempArr.length; i++) {
            if (filesTempArr[i].size > 1024 * 1024 * 2) {
                alert('개별 파일 용량이 2MB를 넘을 수 없습니다.')
                return false;
            }
            fileSize += filesTempArr[i].size;
            formData.append('files[' + i + ']', filesTempArr[i]);
        }
        formData.append('fileLength', filesTempArr.length);

        if (fileSize > 1024 * 1024 * 6) {
            alert('총 용량이 6MB를 넘을 수 없습니다.')
            return false;
        }

        console.log("quill.getContents()", quill.container.firstChild.innerHTML);

//		formData.append('content', JSON.stringify(quill.getContents()));
        formData.append('content', quill.container.firstChild.innerHTML);

        $.ajax({
            type: "POST",
            url: '/api/studio-board',
            data: formData,
            processData: false,
            contentType: false,
        }).done(function (data) {
            if (data.success) {
                location.href = '/board/' + brdCateCd;
            }
        }).fail(function (jqXHR, textStatus, errorThrown) {
            console.error('FAIL REQUEST: ', jqXHR);
            alert('처리중 오류가 발생하였습니다.');
        }).always(function () {
            console.log('DONE');
        });

    });

    $("#fileupload").on("change", addFiles);

});


//파일 추가
function addFiles(e) {

    var totalSize = 0;

    var files = e.target.files;
    var filesArr = Array.prototype.slice.call(files);
    var filesArrLen = filesArr.length;
    var filesTempArrLen = filesTempArr.length;
    for (var i = 0; i < filesArrLen; i++) {
//	 if(filesArr[i].size > 1024*1024 * 2){
//		alert('파일 용량이 2MB를 넘을 수 없습니다.');
//		$(this).siblings('.upload-name').val('');	
//		$(this).val('');
//		return;
//	 }
        totalSize += filesArr[i].size;
        filesTempArr.push(filesArr[i]);
        $("#fileList").append("<div><p class=\"file_name\"><a href=\"#\">" + filesArr[i].name + "</a><img src=\"/images/common/btn_del.png\" onclick=\"deleteFile(event, " + i + ");\"></p>" + "</div>");
    }
// 
// if(totalSize > 1024*1024 * 6){
//	alert('파일 용량이 6MB를 넘을 수 없습니다.');
//	return;
// }
    console.log(this);
    var filename = $("#fileList div").length + "개의 파일";
    $(this).siblings('.upload-name').val(filename);
    $(this).val('');
}

//파일 삭제
function deleteFile(eventParam, orderParam) {
    filesTempArr.splice(orderParam, 1);
    var innerHtmlTemp = "";
    var filesTempArrLen = filesTempArr.length;
    for (var i = 0; i < filesTempArrLen; i++) {
        innerHtmlTemp += "<div><p class=\"file_name\"><a href=\"#\">" + filesTempArr[i].name + "</a><img src=\"/images/common/btn_del.png\" onclick=\"deleteFile(event, " + i + ");\"></p>" + "</div>"
    }
    $("#fileList").html(innerHtmlTemp);
    var filename = "파일선택";
    if (filesTempArrLen > 0) {
        filename = filesTempArrLen + "개의 파일";
    }
    $('.upload-name').val(filename);
}



