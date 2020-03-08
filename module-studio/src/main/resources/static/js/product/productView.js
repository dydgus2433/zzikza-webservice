var Size = Quill.import('attributors/style/size');
var Font = Quill.import('formats/font');
var FontAttributor = Quill.import('attributors/class/font');


Quill.register(Size, true);
Quill.register(Font, true);
FontAttributor.whitelist = [
  'sofia', 'Nanum-Brush-Script', 'munche_gung_jungja',
  'Black_Han_Sans','Gaegu','Single_Day','Black_And_White_Picture','Hi_Melody','Gamja_Flower',
  'Cute_Font','East_Sea_Dokdo','Sunflower','Jua','Dokdo'
];
Size.whitelist = ["10px","11px","12px","15px", "18px", "20px", "32px", "40px"];
//Color.whitelist.push('color-picker')
Quill.register(FontAttributor, true);

var Color = ["#000000", "#e60000", "#ff9900", "#ffff00", "#008a00", "#0066cc", "#9933ff", "#ffffff", "#facccc", "#ffebcc", "#ffffcc", "#cce8cc", "#cce0f5", "#ebd6ff", "#bbbbbb", "#f06666", "#ffc266", "#ffff66", "#66b966", "#66a3e0", "#c285ff", "#888888", "#a10000", "#b26b00", "#b2b200", "#006100", "#0047b2", "#6b24b2", "#444444", "#5c0000", "#663d00", "#666600", "#003700", "#002966", "#3d1466", 'color-picker'];
var toolbarOptions = [
		[{ 'font': FontAttributor.whitelist}],
		[{ 'size': Size.whitelist }],
		  ['bold', 'italic', 'underline', 'strike'],        // toggled buttons
		  [{ 'align': [] }],
		  [{ 'color': Color }, { 'background': [] }],
//		  [{ 'header': [1, 2, 3, 4, 5, 6, false] }],
		  
//		  [{ 'size': [{'small': '14px'}, false, 'large', 'huge'] }], 
//		  [{'size':[ {'Small': '14px'}, {'Normal': false}, {'Large': '16px'}, {'Huge': '18px'}]}],
//		  [{ 'font-size': [{"10px" : "small"}, "16px","32px"] }],
//		  [{ 'size' : [{'10' : "10px"},2,3,4,5,6,7,8]}],
		  ['link', 'image']
		];
	
	  var quill = new Quill('#quill', {
		  theme: 'snow',
	    modules: {
        imageResize: {   displaySize: true          },
         toolbar: toolbarOptions,
	    },
	  placeholder : '상품 설명은 고객에게 상품 구성 및 소개를 하는 영역입니다.\n홈페이지와 다른 연락처는 입력하지 말아주세요:) \n입력하실 경우 수정될 수 있습니다.'
	  });
	
	 quill.getModule('toolbar').addHandler('image', function(){
		selectLocalImage(); 
	 });
	// customize the color tool handler
	 quill.getModule('toolbar').addHandler('color', function(value){
		 console.log(value)
	     // if the user clicked the custom-color option, show a prompt window to get the color
		 showColorPicker(value);
	 });
	 
	//quill editor 사용 
	 function selectLocalImage(){
		 var input = document.createElement('input');
		 input.setAttribute('type', 'file');
		 input.click();
		 
		 //Listen upload local image and save to server
		 input.onchange = function(){
			 var fd = new FormData();
			 var file = $(this)[0].files[0];
			 fd.append('image', file);
			 
			 
			 $.ajax({
				 type : 'post',
				 enctype : 'multipart/form-data',
				 url : '/api/insertEditorImage',
				 data : fd,
				 processData : false,
				 contentType : false,
//					 beforeSend : function(xhr){
//						 xhr.setRequestHeader($("#_csrf_header").val(), $("#_csrf").val());
//					 },
			 }).done(function(data){
				 quill.insertEmbed(quill.getLength(), 'image', contextPath+'/img/editor/'+data.flNm);
			 }).fail(function(jqXHR, textStatus, errorThrown) {
            	console.error('FAIL REQUEST: ', textStatus);
        		alert('처리중 오류가 발생하였습니다.');
            }).always(function() {
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

		      picker.addEventListener('change', function(e) {
			    Color.push(picker.value);
			    quill.format('color', picker.value);
		      }, false);
		    }
		    picker.click();
		  } else {
		    quill.format('color', value);
		  }
		}

	 var toolbar = quill.getModule('toolbar');
	 toolbar.addHandler('color', showColorPicker);
var weekDay = ['일','월','화','수','목','금','토'];
var indexes = [];
$(document).ready(function(){
	initDatePicker();
	initDate();
	
	//기존에 내가 만든 구현	
	$("#detailFiles").off('change').on('change',handleImgFileSelect);

	//바로 파일 올려받을 구현
	fileUploadSetting();
	$("#fileBtn").off('click').on('click',fileUploadAction)
	$("#submitBtn").off('click').on('click',submitAction)
	$("#updateBtn").off('click').on('click',submitAction)
	
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
			var option = options[i]
			$("#keywordCate").append(option);
		}
	});
	
	$("#leftBtn").off('click').on('click',function(){
		var options = [];
		$("#keywordCate li.active").each(function(index, $this){
			options.push(($this));
		});

		for(var i = 0; i < options.length; i++){
			var option = options[i];
			$("#keyword").append(option);
		}
	});
	//추가적으로 키워드 관리 데이터 불러오기
	$.ajax({
		url : contextPath + "/api/selectProductKeyword",
		data : {prdId : prdId},
		type : "POST",
	}).done(function(data,b,c){
		var rows = data.rows;//			$("#holiday").append();
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
    });
	//추가적으로 키워드 관리 데이터 불러오기
	$.ajax({
		url : contextPath + "/api/selectNotProductKeyword",
		type : "POST",
		data : {prdId : prdId},
	}).done(function(data,b,c){
			var rows = data.rows;//			$("#holiday").append();
			for(var i=0; i < rows.length; ++i){
				var row = rows[i]
				var li=document.createElement('li');
				li.setAttribute("value", row.keywordId);
				li.addEventListener("click", function(){
					this.classList.toggle("active");
				});
				li.innerHTML = row.keywordNm;
				$(".keyword_right ul").append(li)			
			}
	}).fail(function(jqXHR, textStatus, errorThrown) {
    	console.error('FAIL REQUEST: ', textStatus);
		alert('처리중 오류가 발생하였습니다.');
    }).always(function() {
    });	
	
	
	//숫자만 입력
	$("input[type='number']").off('keypress').on('keypress',function(e){
    	if (e.which && (e.which  > 47 && e.which  < 58 || e.which == 8)) {
    		
    	  } else {
    	    event.preventDefault();
    	  }
    });
    
    $(".btn_del_img").off('click').on('click',deleteImageAction);
    
    
    $("#mobileBtn").off('click').on('click',function(){
    	window.open("", "formInfo",  "height=600, width=474, menubar=no, scrollbars=yes, resizable=no, toolbar=no, status=no, left=50, top=50");
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
    	$('#prdFrm').append($(input));
    	
    	var input1 =
    		$("<input>")
    		.attr("class", "temp_element")
    		.attr("type", "hidden")
    		.attr("name", "keywords").val(options.join(","));
    	$('#prdFrm').append($(input1));
    	
    	var input2 =
    		$("<input>")
    		.attr("class", "temp_element")
    		.attr("type", "hidden")
    		.attr("name", "stdoNm").val($("span.login_info a").text());
    	$('#prdFrm').append($(input2));

    	var prdDsc =
    		$("<input>")
    		.attr("class", "temp_element")
    		.attr("type", "hidden")
    		.attr("name", "prdDsc").val(quill.container.firstChild.innerHTML);
    	$('#prdFrm').append($(prdDsc));
    	
    	$("#prdFrm").attr("action", "/prod/preview/pc");
    	$("#prdFrm").attr("method", "post");
    	$("#prdFrm").attr("target", "formInfo");
    	
    	
        $('#prdFrm').submit();    
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
    	$('#prdFrm').append($(input));
    	
    	var keywords =
    		$("<input>")
    		.attr("class", "temp_element")
    		.attr("type", "hidden")
    		.attr("name", "keywords").val(options.join(","));
    	$('#prdFrm').append($(keywords));
    	
    	var stdoNm =
    		$("<input>")
    		.attr("class", "temp_element")
    		.attr("type", "hidden")
    		.attr("name", "stdoNm").val($("span.login_info a").text());
    	$('#prdFrm').append($(stdoNm));
    	
    	var prdDsc =
    		$("<input>")
    		.attr("class", "temp_element")
    		.attr("type", "hidden")
    		.attr("name", "prdDsc").val(quill.container.firstChild.innerHTML);
    	$('#prdFrm').append($(prdDsc));
    	$("#prdFrm").attr("action", "/prod/preview/pc");
    	$("#prdFrm").attr("method", "post");
    	$("#prdFrm").attr("target", "formInfo");
    	
    	
        $('#prdFrm').submit();    
        $(".temp_element").remove();
    });
    
    
 // input 텍스트에 숫자만 콤마 찍어가면서 받기

    $(document).on("keypress", "input[type=text].number", function () {
        if  ((event.keyCode < 48 || event.keyCode > 57) && (event.keyCode < 96 || event.keyCode > 105) && (event.keyCode != 8) && (event.keyCode != 46))
            event.returnValue = false;

    });

     

    $(document).on("keyup", "input[type=text].number", function () {

        var $this = $(this);

        var num = $this.val().replace(/[,]/g, "");

     

        var parts = num.toString().split(".");

        parts[0] = parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ",");

        $this.val(parts.join("."));

    });
   


    $("#bigOption").sortable({
    	update :  function(event, ui){
     		bigOptionIndexing()
    	} 
    });
    

    $("#optionSort").sortable({
    	update :  function(event, ui){
     		smallOptionIndexing()
    	} 
    });
    
    //삭제버튼 클릭
    $("#deleteBtn").on("click", function(){
    	if(confirm("삭제하면 복구할 수 없습니다. 삭제하시겠습니까?")){
    		
    		
    		$.ajax({
    			url : contextPath + "/api/deleteProduct",
				type : "POST",
				data : {prdId : prdId},
			}).done(function(data){
					location.href=contextPath + '/prod/list';
			}).fail(function(jqXHR, textStatus, errorThrown) {
		    	console.error('FAIL REQUEST: ', textStatus);
				alert('처리중 오류가 발생하였습니다.');
		    }).always(function() {
		    });	
    	}
    });
    
    
    if($("#prdSalePrc").val() > 0){
    	//select Y 
    	$("#prdSalePrcSel").val("Y");
    	$("#prdSalePrc").show();
    }
    $("#prdSalePrcSel").on("change", function(e){
    	var selValue = $("#prdSalePrcSel").val();
    	console.log("change ",e, selValue);
    	if(selValue == 'Y'){
    		$("#prdSalePrc").show();
    	}else{
    		$("#prdSalePrc").hide();
    		$("#prdSalePrc").val(0)
    	}
    	
    })
});

//날짜초기화
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
	var url = contextPath + '/api/uploadProductFile';  // 사용
	var upload = $('#detailFiles').fileupload({
	    url: url,
	    sequentialUploads: true,
	    dataType: 'json',
	    maxNumberOfFiles: 10,
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
	        else if ((data.originalFiles.length + $(".img_area").length ) > 10) { // 100 * 5kb
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
	    	var id = "img_id_"+data.result.productFileId;
	    	$("#sortable li:last").remove();
			
			 var html = "<li index=\""+data.result.productFileId+"\" id=\""+id+"\">"	
					+"<div class=\"img_area\" >"
					+"<button type=\"button\" class=\"btn_del_img\">삭제</button>"
					+"<img src=\"" + contextPath +  "/img/thumb/" +data.result.flNm  + "\" data-file='"+data.result.flSrcNm+"'  onerror=\"this.src='"+contextPath+"/resources/images/common/no_img.gif'\" >"
					+"</div>"
					+"<div class=\"text\">0</div>"
					+"</li>";
			
			$(".shop_img_list").append(html);
			$(".btn_del_img").off('click').on('click',deleteImageAction);
	    },
	    //선행동작 1
	    progress : function(e, data){
	    		//검사하자 
	    	
		    	var index = data.originalFiles.indexOf(data.files[0]);
		    	var id = "img_id_"+index;
		    	if(indexes.indexOf(index) > -1){
		    	}else{
		    		indexes.push(index);
		    		//index 가 없을때  
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
		alert('상품사진을 입력해주세요.')
		return false;
	}
	
	var inputs = $("input[type='text']");
	for(var i = 0; i < inputs.length; i++){
		var input = inputs[i];
		if($(input).val() == ""){
			if(input.name == "optionName" || input.name == "optionPrice" ){
				continue;
			}
			
			if(input.placeholder){
				if(input.placeholder == "https://quilljs.com"){
					continue;
				}
				alert(input.placeholder);
				$(input).focus();
				return false;
			}
		}
	}
	
	
	inputs = $("input[type='number']");
	for(var i = 0; i < inputs.length; i++){
		var input = inputs[i];
		if($(input).val() == ""){
			var name = input.name;
			if(name == 'prdHour' || name == 'prdMin'){
				alert("촬영소요시간을 입력해주세요");
			}else if(name == 'prdSalePrc'){
				continue;
			}else{
				alert(input.placeholder);
			}
			$(input).focus();
			return false;
		}
	}
	

	if($("input[name='prdHour']").val() < 0 ){
		alert("알맞은 시간을 입력해주세요.");
		$("input[name='prdHour']").focus();
		return false;
	}
	
	if($("input[name='prdMin']").val() >= 60 || $("input[name='prdMin']").val() < 0 ){
		alert("알맞은 시간을 입력하세요.");
		$("input[name='prdMin']").focus();
		return false;
	}
	
	if($("textarea[name='prdDsc']").val() == ""){
		alert("상품설명을 입력하세요.");
		$("textarea[name='prdDsc']").focus();
		return false;
	}
	
	return true;
}

function submitAction(){
	
	if(!validate()){
		return;
	}
	
	var formData = new FormData($("#prdFrm")[0])
	
	var $targetForm = $("#prdFrm");
	var options = [];
	$("#keyword li").each(function(index, $this){
		options.push($this.value);
	});
	formData.append("keyword",options.join(","))
	
	var indexes = [];
	$("#sortable li").each(function(index, $this){
		indexes.push($($this).attr("index"));
	});
	formData.append("index",indexes.join(","));
	formData.append('prdDsc', quill.container.firstChild.innerHTML);
	
	var url = contextPath + '/api/insertProduct';
	if($("#prdId").val()){
		url = contextPath + '/api/updateProduct';
	}
	
	formData.append("tempKey",tempKey);
	
    $.ajax({
		url: url,
		data : formData,
		type: 'post',
		processData: false,
        contentType: false,
	})	.done(function(data) {
		if(data.rows && data.rows > 0){
			alert('상품이 등록되었습니다.');
			location.href = contextPath + '/prod/list';
		}else{
			alert('상품등록이 실패했습니다. 입력값을 확인해주세요.');
		}
	}).fail(function(jqXHR, textStatus, errorThrown) {
    	console.error('FAIL REQUEST: ', textStatus);
		alert('처리중 오류가 발생하였습니다.');
    }).always(function() {
    	//console.info('DONE');
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
			url: contextPath + "/api/deleteProductFile",
			data : {index : indexStr, flNm : src},
			type: "post"
		 }).done(function(a,b,c){
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
        });
	}else{
	}
}


function smallOptionIndexing(){
	var options = $("#optionSort li");
	if(options.length < 1){
		return;
	}
	var indexes = [];
	for(var i = 0; i < options.length; i++){
		var option = $(options[i]);
		
		indexes.push($(option).data("optn_id"));
	}
	
	$.ajax({
		url: contextPath + "/api/updateOptionOrder",
		data : {optnIds : indexes.join(","), tempKey : tempKey},
		type: "post"
	}).done(function(a,b,c){
	}).fail(function(jqXHR, textStatus, errorThrown) {
    	console.error('FAIL REQUEST: ', textStatus);
		alert('처리중 오류가 발생하였습니다.');
    }).always(function() {
    });
}

function bigOptionIndexing(){
	var options = $("#bigOption li").not(":last");
	if(options.length < 1){
		return;
	}
	var indexes = [];
	for(var i = 0; i < options.length; i++){
		var option = $(options[i]);
		
		indexes.push($(option).data("optn_id"));
	}
	
	$.ajax({
		url: contextPath + "/api/updateOptionOrder",
		data : {optnIds : indexes.join(","), tempKey : tempKey},
		type: "post"
	}).done(function(a,b,c){
	}).fail(function(jqXHR, textStatus, errorThrown) {
    	console.error('FAIL REQUEST: ', textStatus);
		alert('처리중 오류가 발생하였습니다.');
    }).always(function() {
    });
}

//인덱싱
function indexing(){
	var cards = $(".shop_img_list .text");
	if(cards.length < 1){
		return;
	}
	
	for(var i = 0; i < cards.length; i++){
		var card = $(".shop_img_list .text")[i];
		$(card).html(i+1);
	}
	//인덱싱 후 order update 해줘야함
	
	var indexes = [];
	var cards = $("#sortable li");
	for(var i =0; i < cards.length; i++){
		var card = cards[i];
		indexes.push($(cards[i]).attr("index"));
	}
	
	$.ajax({
		url: contextPath + "/api/updateProductFileOrder",
		data : {index : indexes.join(",")},
		type: "post"
	}).done(function(a,b,c){
	}).fail(function(jqXHR, textStatus, errorThrown) {
    	console.error('FAIL REQUEST: ', textStatus);
		alert('처리중 오류가 발생하였습니다.');
    }).always(function() {
    });
}

function initDatePicker(){
	$.datepicker.regional['ko'] = {
		dateFormat: 'yy-mm-dd',
		firstDay: 0,
		};
	$.datepicker.setDefaults($.datepicker.regional['ko']);
}

