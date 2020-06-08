var weekend = false;
var validator;
$( document ).ready(function() {
	
	$(".datepicker").datepicker({
    	dateFormat: 'yy-mm-dd'
    });  
	$("#reservationStartDate").datepicker( "setDate", new Date());
    $(".timepicker").timepicker({
    }); 
    //전체 동의 체크시 모두 체크
    $("#reservationStartDate").change(function(){
    	valid();
    })
    $("#allCheck").off('click').on('click', function(){
    	$("ul#termsCheck input[type=checkbox]").prop('checked',$("#allCheck").prop('checked'));
    	
    	if($("#allCheck").prop('checked')){
    		$("ul#termsCheck li span.warring_cmm").hide();
    	}
    })
    
    $("ul#termsCheck input[type=checkbox]").off('click').on('click', function(){
    	var checkboxCnt = $("ul#termsCheck input[type=checkbox]").length;
    	var checkCnt = $("ul#termsCheck :checked").length;
   		$("#allCheck").prop('checked',(checkboxCnt == checkCnt));
    })
    
    validator = valid();
    $(".payBtn").on('click', function(){
    	
    	if($("#rsrvFrm").valid()){
    		if(!confirm("결제하시겠습니까?")){
        		return;
        	}
    		
    		$("#rsrvFrm").submit();
    	}else{
    		validator.showErrors();
    		validator.focusInvalid();
    		return false;
    	}
    	
    	
    });
    
    //시작시간 입력하면 종료시간 입력
    //reservationEndDate rsrvEndTime
    $("#rsrvStrtTime").change(function(){
    	// if time 검사
    	var date = new Date($("#reservationStartDate").val() + "T"+$("#rsrvStrtTime").val());
    	date.setHours(date.getHours() + prdHour);
    	date.setMinutes(date.getMinutes() + prdMin);
    	$("#rsrvEndTime").val(((date.getHours()+"").padStart(2,'0')+":"+(date.getMinutes()+"").padStart(2,'0')) );
    	
    	if($("#reservationStartDate").val() != ""){
	    	$("#reservationEndDate").val(date.getFullYear()+"-"+(date.getMonth()+1+"").padStart(2,'0')+"-"+(date.getDate()+"").padStart(2,'0'));
    	}
    })

    
	$(".pay_btn").click(function(a,b,c){
		$(this).siblings().removeClass('methodChk');
		$(this).addClass('methodChk');
	})
});

function changeEndTime(time) {
    // the input field
    var element = $(this), text;
    // get access to this Timepicker instance
    var timepicker = element.timepicker();
    text = 'Selected time is: ' + timepicker.format(time);
    element.siblings('span.help-line').text(text);
}

function valid(){
	var validator = $("#rsrvFrm").validate({
		invalidHandler : function(event,validator){
			var array = Object.keys(validator.invalid);
			if($('input[name='+array[0]+']').prop('type')=='checkbox'){
				 $('input[name='+array[0]+']').focus();
			}else{
				$('input[name='+array[0]+']').focus();
			}
		},
		errorClass : 'warring_cmm',
		errorElement : 'span',
		showErrors : function(){
			this.defaultShowErrors();
			var image = $('<img />')
			.attr('src', "/img/deco_warring.png")         // ADD IMAGE PROPERTIES.
			.attr('alt', '');
			$("span.warring_cmm").append(image);
		},
		errorPlacement: function(error, element) {
		if(element.prop('type')=='checkbox'){
			error.insertAfter($(element).siblings("a"));
		} else if ($(element).next("input[type='button']").length > 0){
         		error.insertAfter($(element).next("input"));
        } else {
        	error.insertAfter(element);
        }
		
     },
		rules: {
	        reservationStartDate : {
	        	required: true,
	        	date : true,
	        	limitDate : true,
	        	remote :{type:"GET",url:"/api/possible-day", dataType : "json" ,
	        		data : {
	        			studioId : $("#studioId").val(),
	        			prdHour : $("#prdHour").val(),
	        		},
	        		dataFilter : function(responseData){
	        			return responseData;
	        		}
	        	}
	        },
	        rsrvStrtTime : {
	        	required: true,
	        	limitTime : true,
	        	remote :{type:"GET",url:"/api/possible-time",
	        		data : {
	        			studioId : $("#studioId").val(),
	        			prdHour : $("#prdHour").val()
	        		},
	        		dataType : "json" , 
	        		dataFilter : function(responseData){
	        			var weekend = new Date($("#reservationStartDate").val()).getDay();
	        			
	        			if(weekend == 0 || weekend == 6){
		        			$.ajax({
		        				type:"GET",
		        				url:"/api/possible-time-weekend",
		        				dataType : "json" , 
		        				async : false,
		    	        		data : {
		    	        			studioId : $("#studioId").val(),
		    	        			reservationStartDate : $("#reservationStartDate").val(),
		    	        			rsrvStrtTime : $("#rsrvStrtTime").val(),
		    	        			prdHour : $("#prdHour").val()
		    	        		}
		        			}).done(function(data) {
		                		responseData = data
		                	})
	        			}
	                    
	        			return responseData;
	        		}
	        	}
	        },
	        reservationEndDate : {
	        	required: true,
	        	date : true
	        },
	        rsrvEndTime : {
	        	required: true
	        },
	        userName : {
	        	required: true
	        },
	        peopleCnt : {
	        	required: true
	        },
// 	        customRequest : {
// 	        	required: true
// 	        },
	        tel: {
	            required: true,
	            minlength: 9
	        },useTermYn :{
	        	requireChk: true
	        },cancelTermYn :{
	        	requireChk: true
	        },privateTermYn :{
	        	requireChk: true
	        },otherTermYn :{
	        	requireChk: true
	        }	        
	    },
	    messages:{/* validate 메세지 */
	    	reservationStartDate:{
  	    		remote: "{0} 는 예약할 수 없는 날짜 및 요일입니다."
	  	    },
	  	    rsrvStrtTime:{
  	    		remote: "{0} 는 영업시간이 아닙니다."
  	    	}
	    },
        submitHandler: function (frm){
        	requestPay();
        }
	});
	return validator;
}


