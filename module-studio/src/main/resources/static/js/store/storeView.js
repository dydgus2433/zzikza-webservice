var weekDay = ['일','월','화','수','목','금','토'];
var indexes = [];
var uploadCall;
$(document).ready(function(){
	initDatePicker();
	initDate();
	$("#dateType").off('change').on('change',function(e){
		var value = e.target.value;
		if(value == "W"){
			$("#dateVal").hide();
			$("#weekVal").show();
		}else if(value == "D"){
			$("#dateVal").datepicker().datepicker('setDate', new Date());
			$("#weekVal").hide();
			$("#dateVal").show();
		}
	});
	
	
	$("#btnAdd").off('click').on('click',function (e){
		var type = $("#dateType").val();
		var name ;
		var value;
		if(type == "W"){
			value = $.trim($("#weekVal").val());
			name = $("#weekVal").find("option[value='" + value + "']").text();
		}else{
			value = $.trim($("#dateVal").val());
			name = value;
		}
		
		$.ajax({
				url: contextPath + "/api/insertStudioHoliday",
				data : {dtCd : type, dtVal : value},
				type: "POST"
			})	.done(function(data) {
				console.info(data);
				if(data.rows > 0){
					if(value.length > 0){
						var li=document.createElement('li');
						li.setAttribute("type", type);
						li.setAttribute("value", value);
						li.addEventListener("click", function(){
							this.classList.toggle("active");
						});
						li.innerHTML = name;
						$("#holiday").append(li)
					}
				}else{
					alert('추가하려는 값을 다시 확인해주세요.')
				}
			}).fail(function(jqXHR, textStatus, errorThrown) {
		    	console.error('FAIL REQUEST: ', textStatus);
				alert('처리중 오류가 발생하였습니다.');
		    }).always(function() {
		    });
	})
	
	
	// 기존에 내가 만든 구현
	$("#detailFiles").off('change').on('change',handleImgFileSelect);

	// 바로 파일 올려받을 구현
	fileUploadSetting();
	
	$("#fileBtn").off('click').on('click',fileUploadAction)
	$("#submitBtn").off('click').on('click',submitAction)
	
	$("#sortable").sortable({
		update :  function(event, ui){
			indexing()
		} 
	});
	
	$("#rightBtn").off('click').on('click',function(){
		var options = [];
		$("#keyword li.active").each(function(index, $this){
			options.push(($this));
		});
		
		for(var i = 0; i < options.length; i++){
			var option = options[i];
			$("#keywordCate").append(option);
		}
	});
	
	$("#leftBtn").off('click').on('click',function(){
		var options = [];
		$("#keywordCate li.active").each(function(index, $this){
			options.push(($this));
		});

		for(var i = 0; i < options.length; i++){
			var option = options[i]
			$("#keyword").append(option);
		}
	});
	
	$("#btnDel").off('click').on('click',function(){
		var options = $("#holiday li.active");
		var type = $("#dateType").val();
		
		for(var i= 0; i < options.length; i++){
			var option = options[i];
			var type = option.type.toString();
			var value = '';
			if(type=="D"){
				value = option.textContent.trim();
			}else{
				value = option.value;
			}
			
			$.ajax({
				url: contextPath + "/api/deleteStudioHoliday",
				data : {dtCd : type, dtVal : value},
				type: "POST",
				async : false
			}).done(function(data) {
				console.info(data);
				if(data.rows > 0){
					$("#holiday li.active[value='"+value+"']").remove();
				}
			}).fail(function(jqXHR, textStatus, errorThrown) {
		    	console.error('FAIL REQUEST: ', textStatus);
// alert('처리중 오류가 발생하였습니다.');
		    }).always(function() {
		    });
		}
	})
	
	// 추가적으로 휴무일 데이터 불러오기
	$.ajax({
		url : contextPath + "/api/selectStudioHoliday",
		type : "POST",
	})	.done(function(data,b,c){
		var rows = data.rows;// $("#holiday").append();
		for(var i=0; i < rows.length; ++i){
			var row = rows[i]
			var li=document.createElement('li');
			li.setAttribute("type", row.dtCd);
			li.setAttribute("value", row.dtVal);
			li.addEventListener("click", function(){
				this.classList.toggle("active");
			});
			if(row.dtCd == "D"){
				li.innerHTML = row.dtVal;
			}else{
				li.innerHTML = weekDay[row.dtVal]+"요일";
			}
			
			$("#holiday").append(li)
		}
	}).fail(function(jqXHR, textStatus, errorThrown) {
    	console.error('FAIL REQUEST: ', textStatus);
		alert('처리중 오류가 발생하였습니다.');
    }).always(function() {
    	//console.info('DONE');
    });	
		
	
	// 추가적으로 키워드 관리 데이터 불러오기
	$.ajax({
		url : contextPath + "/api/selectStudioKeyword",
		type : "POST",
	})	.done(function(data,b,c){
		var rows = data.rows;// $("#holiday").append();
		for(var i=0; i < rows.length; ++i){
			var row = rows[i]
			var li=document.createElement('li');
			li.setAttribute("value", row.keywordId);
			li.addEventListener("click", function(){
				this.classList.toggle("active");
			});
			li.innerHTML = row.keywordNm;
			$(".keyword_left ul").append(li)
		}
	}).fail(function(jqXHR, textStatus, errorThrown) {
    	console.error('FAIL REQUEST: ', textStatus);
		alert('처리중 오류가 발생하였습니다.');
    }).always(function() {
    	//console.info('DONE');
    });		
		
	// 추가적으로 키워드 관리 데이터 불러오기
	$.ajax({
		url : contextPath + "/api/selectNotStudioKeyword",
		type : "POST",
	})	.done(function(data,b,c){
		var rows = data.rows;// $("#holiday").append();
		for(var i=0; i < rows.length; ++i){
			var row = rows[i]
			var li=document.createElement('li');
			li.setAttribute("value", row.keywordId);
			li.addEventListener("click", function(){
				this.classList.toggle("active");
			});
			li.innerHTML = row.keywordNm;
			$(".keyword_right ul").append(li)			}
	}).fail(function(jqXHR, textStatus, errorThrown) {
    	console.error('FAIL REQUEST: ', textStatus);
		alert('처리중 오류가 발생하였습니다.');
    }).always(function() {
    	//console.info('DONE');
    });		
	
	$("#stdoDsc").on('keyup', function() {
		var tempText = $("textarea[name='stdoDsc']");
        var tempChar = "";                                        // TextArea의
																	// 문자를 한글자씩
																	// 담는다
        var tempChar2 = "";                                        // 절삭된 문자들을
																	// 담기 위한 변수
        var countChar = 0;                                        // 한글자씩 담긴
																	// 문자를 카운트
																	// 한다
        var tempHangul = 0;                                        // 한글을 카운트
																	// 한다
        var maxSize = 1000;                                        // 최대값
        
        // 글자수 바이트 체크를 위한 반복
        for(var i = 0 ; i < tempText.val().length; i++) {
            tempChar = tempText.val().charAt(i);
 
            // 한글일 경우 2 추가, 영문일 경우 1 추가
            if(escape(tempChar).length > 4) {
                countChar += 2;
                tempHangul++;
            } else {
                countChar++;
            }
        }
        
        // 카운트된 문자수가 MAX 값을 초과하게 되면 절삭 수치까지만 출력을 한다.(한글 입력 체크)
        // 내용에 한글이 입력되어 있는 경우 한글에 해당하는 카운트 만큼을 전체 카운트에서 뺀 숫자가 maxSize보다 크면 수행
        if((countChar-tempHangul) > maxSize) {
            alert("최대 글자수를 초과하였습니다.");
            
            tempChar2 = tempText.val().substr(0, maxSize-1);
            tempText.val(tempChar2);
        }
	});
	
	// 이미지 삭제 이벤트
	$(".btn_del_img").off('click').on('click',deleteImageAction);
	
	
    $("#mobileBtn").off('click').on('click',function(){
    	window.open("", "formInfo", "height=600, width=474, menubar=no, scrollbars=yes, resizable=no, toolbar=no, status=no, left=50, top=50");
    	var srcs = [];
    	var options = [];
    	
    	var img_src = $("#sortable li div.img_area img");
    	for(var i =0; i < img_src.length; i++){
    		var src = img_src[i];
    		srcs.push(src.src);
    	}
    	
    	
    	$("#keyword li").each(function(index, $this){
    		options.push($this.value);
    	});
    	
    	var input =
		$("<input>")
		.attr("class", "temp_element")
        .attr("type", "hidden")
        .attr("name", "files").val(srcs.join(","));
    	$('#stdoFrm').append($(input));
    	
    	var input1 =
    		$("<input>")
    		.attr("class", "temp_element")
    		.attr("type", "hidden")
    		.attr("name", "keywords").val(options.join(","));
    	$('#stdoFrm').append($(input1));
    	
    	var input2 =
    		$("<input>")
    		.attr("class", "temp_element")
    		.attr("type", "hidden")
    		.attr("name", "stdoNm").val($("span.login_info a").text());
    	$('#stdoFrm').append($(input2));
    	
    	$("#stdoFrm").attr("action", "/store/preview/mobile");
    	$("#stdoFrm").attr("method", "post");
    	$("#stdoFrm").attr("target", "formInfo");
    	
    	
        $('#stdoFrm').submit();    
        $(".temp_element").remove();
    });
    
    $("#pcBtn").off('click').on('click',function(){
    	window.open("", "formInfo", "_blank");
    	var srcs = [];
    	var options = [];
    	
    	var img_src = $("#sortable li div.img_area img");
    	for(var i =0; i < img_src.length; i++){
    		var src = img_src[i];
    		srcs.push(src.src);
    	}
    	
    	
    	$("#keyword li").each(function(index, $this){
    		options.push($this.value);
    	});
    	
    	var input =
		$("<input>")
		.attr("class", "temp_element")
        .attr("type", "hidden")
        .attr("name", "files").val(srcs.join(","));
    	$('#stdoFrm').append($(input));
    	
    	var input1 =
    		$("<input>")
    		.attr("class", "temp_element")
    		.attr("type", "hidden")
    		.attr("name", "keywords").val(options.join(","));
    	$('#stdoFrm').append($(input1));
    	
    	var input2 =
    		$("<input>")
    		.attr("class", "temp_element")
    		.attr("type", "hidden")
    		.attr("name", "stdoNm").val($("span.login_info a").text());
    	$('#stdoFrm').append($(input2));
    	
    	$("#stdoFrm").attr("action", "/store/preview/pc");
    	$("#stdoFrm").attr("method", "post");
    	$("#stdoFrm").attr("target", "formInfo");
    	
    	
        $('#stdoFrm').submit();    
        $(".temp_element").remove();
    });
});

// 날짜초기화
function initDate(){
	makeDay();
}
function makeDay(){
	var html = "";
	for(var i = 0 ; i < weekDay.length; i++){
		html+= "<option value="+i+">"+weekDay[i]+"요일</option>";
	}
	$("#weekVal").html(html);
}
function pad(n, width) {
  n = n + '';
  return n.length >= width ? n : new Array(width - n.length + 1).join('0') + n;
}

function fileUploadSetting(){
	// Change this to the location of your server-side upload handler:
	var url = contextPath + '/api/uploadFile';  // 사용
	$('#detailFiles').fileupload({
	    url: url,
	    sequentialUploads: true,
	    dataType: 'json',
	    maxNumberOfFiles: 9,
	    add: function(e, data){
	        var uploadFile = data.files[0];
	        var isValid = true;

	        if (!(/png|jpe?g|gif/i).test(uploadFile.name)) {

	        	alert('png, jpg, gif 만 가능합니다');

	            isValid = false;

	        }
	        else if (uploadFile.size > 1024000 * 15) { // 1000 * 20kb

	        	alert('파일 용량은 15MB를 초과할 수 없습니다.');

	            isValid = false;

	        } 
	        else if ((data.originalFiles.length + $(".img_area").length ) > 9) { // 100 * 5kb
	        	alert('파일은 9개를 넘길 수 없습니다.');
	            return false;
	        } 

	        if (isValid) {
	            uploadCall = data.submit();              
	        }
	    },

	    done: function (e, data) {
	    	var id = "img_id_"+data.result.stdoDtlFlId;
	    	$("#sortable li:last").remove();
			 var html = "<li index=\""+data.result.stdoDtlFlId+"\" id=\""+id+"\">"	
					+"<div class=\"img_area\" >"
					+"<button type=\"button\" class=\"btn_del_img\">삭제</button>"
					+"<img src=\"" + contextPath +  "/img/thumb/" +data.result.flNm  + "\" data-file='"+data.result.flSrcNm+"' onerror=\"this.src='"+contextPath+"/resources/images/common/no_img.gif'\" >"
					+"</div>"
					+"<div class=\"text\">0</div>"
					+"</li>";
			
			$(".shop_img_list").append(html);
			$(".btn_del_img").off('click').on('click',deleteImageAction);
	    },
	    progress : function(e, data){
	    		// 검사하자
		    	var index = data.originalFiles.indexOf(data.files[0]);
	    		
		    	var id = "img_id_"+index;
		    	if(indexes.indexOf(index) > -1){
		    	}else{
		    		indexes.push(index);
		    		// index 가 없을때
			        var html = "<li index=\"\" id=\""+id+"\">"	
							+"<div class=\"img_area\" >"
							+"<button type=\"button\" class=\"btn_del_img\">삭제</button>"
							+"<img src=\"" + contextPath +  "/resources/images/common/no_img.gif"  + "\" alt=\"\">"
							+"</div>"
							+"<div class=\"text\">∞</div>"
							+"</li>";
			        
			        $(".shop_img_list").append(html);
			        $(".btn_del_img").off('click').on('click',deleteImageAction);
		    	}
				
	    }, always : function ( e, data){
	    	indexing();
	    	$(".upload-name").val($("#sortable div.text").length+"개의 파일");
	    }
	}).prop('disabled', !$.support.fileInput)
	    .parent().addClass($.support.fileInput ? undefined : 'disabled');
	
}

function fileUploadAction(){
	$("#detailFiles").trigger('click');
}

function handleImgFileSelect(e){
	$(".imgs_wrap").empty();
	index = 0;
	indexes = [];
}


function validate(){
	var file_length = $(".img_area").length;
	if(file_length < 1){
		alert('스튜디오 사진을 입력해주세요.')
		return false;
	}
	
	
	var inputs = $("input[type='number']");
	for(var i = 0; i < inputs.length; i++){
		var input = inputs[i];
		if($(input).val() == ""){
			var name = input.name;
			alert(input.placeholder);
			$(input).focus();
			return false;
		}
	}
	
	inputs = $("textarea[name='stdoDsc']");
	for(var i = 0; i < inputs.length; i++){
		var input = inputs[i];
		if($(input).val() == ""){
			alert("스튜디오 설명을 입력하세요.");
			$(input).focus();
			return false;
		}
	}
	
	return true;
}

function submitAction(){
	if(!validate()){
		return;
	}
	
	var formData = new FormData($("#stdoFrm")[0])
	
	var $targetForm = $("#stdoFrm");
	var options = [];
	$("#keyword li").each(function(index, $this){
		options.push($this.value);
	});
	formData.append("keyword",options.join(","))
    $.ajax({
		url: contextPath + '/api/updateStudioDetail',
		data : formData,
		type: $targetForm.attr('method'),
		processData: false,
        contentType: false,
	})	.done(function(data) {
		console.info(data);
		if(data.rows && data.rows > 0){
			alert('매장정보가 저장되었습니다.');
		}else{
			alert('매장정보 저장 중 오류가 발생했습니다.')
		}
// location.reload();
	}).fail(function(jqXHR, textStatus, errorThrown) {
    	console.error('FAIL REQUEST: ', textStatus);
		alert('처리중 오류가 발생하였습니다.');
    }).always(function() {
    	// console.info('DONE');
    });
}

function deleteImageAction(e){
	if(!confirm("삭제하시겠습니까?")){
		return false;
	}
	var target = $(e.currentTarget).parent().parent();
	var indexStr = $(target).attr("index");
	var src = $(target).children("div.img_area").children("img").attr("src");
	if(src){
		// 이거로 파일 삭제해야함
		
		 $.ajax({
			url: contextPath + "/api/deleteStudioDetailFile",
			data : {index : indexStr, flNm : src},
			type: "post"
		 })	.done(function(a,b,c){
				$(target).remove();
				indexing();
				if($("#sortable div.text").length > 0){
					$(".upload-name").val($("#sortable div.text").length+"개의 파일");
				}else{
					$(".upload-name").val("파일선택");
				}
			}).fail(function(jqXHR, textStatus, errorThrown) {
		    	console.error('FAIL REQUEST: ', textStatus);
				alert('처리중 오류가 발생하였습니다.');
		    }).always(function() {
		    	//console.info('DONE');
		    });	
	}else{
// uploadCall.abort();
	}
}

// 인덱싱
function indexing(){
	var cards = $(".shop_img_list .text");
	if(cards.length < 1){
		return;
	}
	
	for(var i = 0; i < cards.length; i++){
		var card = $(".shop_img_list .text")[i];
		$(card).html(i+1);
	}
	// 인덱싱 후 order update 해줘야함
	
	var indexes = [];
	var cards = $("#sortable li");
	for(var i =0; i < cards.length; i++){
		var card = cards[i];
		indexes.push($(cards[i]).attr("index"));
	}
	
	$.ajax({
		url: contextPath + "/api/updateStudioDetailFileOrder",
		data : {index : indexes.join(",")},
		type: "post"
	 })	.done(function(a,b,c){
		}).fail(function(jqXHR, textStatus, errorThrown) {
	    	console.error('FAIL REQUEST: ', textStatus);
			alert('처리중 오류가 발생하였습니다.');
	    }).always(function() {
	    	//console.info('DONE');
	    });	
}

function initDatePicker(){
	$.datepicker.regional['ko'] = {
		dateFormat: 'yy-mm-dd',
		firstDay: 0,
	};
	$.datepicker.setDefaults($.datepicker.regional['ko']);
}
