$.fn.serializeObject=function(){"use strict";var a={},b=function(b,c){var d=a[c.name];"undefined"!=typeof d&&d!==null?$.isArray(d)?d.push(c.value):a[c.name]=[d,c.value]:a[c.name]=c.value};return $.each(this.serializeArray(),b),a};
//https://github.com/uxitten/polyfill/blob/master/string.polyfill.js


function checkMenu(){
    var windowWidth = $( window ).width();
    if(windowWidth < 481) {
        $('.header .wrap .menu').hide();
        $('.footer').css('margin-bottom','80px');
    } else {
        $('.header .wrap .menu').show();
        $('.footer').css('margin-bottom','80px');
    }
}

$(document).ready(function(){
	//popup
    $('.popBox .btns a').click(function(){
        $('.dimm').fadeOut();
        $('.popBox').fadeOut();
    });
    
    $("textarea").prop("maxLength", 1000);
//    checkMenu();

//    $(window).resize(function() { 
//        checkMenu();   
//    });    
    
    $(".wish").click(wish);
});




//타이머 관련 메소드 
var timerId = 123;
var limit = 180;
var count = 0;
function startTimer(){
	stopTimer();
	$(".overTime").hide();
	limit = 180;
	count = 0;
	timerId = setInterval(timeIt,1000);
}


function stopTimer(){
	clearInterval(timerId);
}

function timeIt(){
	count++;
	$(".counting_number").html(numberToTime(limit - count));
	if(limit == count){
		stopTimer();
		$("#secureBtn").html("인증번호전송");
// 		$("#secureBtn").append('<span class="warring_cmm overTime" style="">입력시간을 초과하였습니다. 인증번호 재전송을 다시 시도하여 주십시오.</span>')
		$(".overTime").show();
	}
}


function numberToTime(number){
	var minute = (Math.floor(number/60)+"").padStart(2,'0');
	var second = (number%60+"").padStart(2,'0');
	return minute+":"+second;
}

//클립보드 복사
function copyToClipboard(val) {
	  var t = document.createElement("textarea");
	  document.body.appendChild(t);
	  t.value = val;
	  t.select();
	  document.execCommand('copy');
	  document.body.removeChild(t);
	  alert("링크를 클립보드에 복사했습니다.")
}

function copyToURL() {
	  var t = document.createElement("textarea");
	  document.body.appendChild(t);
	  t.value = document.location.href;
	  t.select();
	  document.execCommand('copy');
	  document.body.removeChild(t);
	  alert("링크를 클립보드에 복사했습니다.")
}


//IE 폴리필
//https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/String/padStart
if (!String.prototype.padStart) {
 String.prototype.padStart = function padStart(targetLength,padString) {
     targetLength = targetLength>>0; //truncate if number or convert non-number to 0;
     padString = String((typeof padString !== 'undefined' ? padString : ' '));
     if (this.length > targetLength) {
         return String(this);
     }
     else {
         targetLength = targetLength-this.length;
         if (targetLength > padString.length) {
             padString += padString.repeat(targetLength/padString.length); //append to original to ensure we are longer than needed
         }
         return padString.slice(0,targetLength) + String(this);
     }
 };
}

//https://github.com/uxitten/polyfill/blob/master/string.polyfill.js
//https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/String/padEnd
if (!String.prototype.padEnd) {
 String.prototype.padEnd = function padEnd(targetLength,padString) {
     targetLength = targetLength>>0; //floor if number or convert non-number to 0;
     padString = String((typeof padString !== 'undefined' ? padString : ' '));
     if (this.length > targetLength) {
         return String(this);
     }
     else {
         targetLength = targetLength-this.length;
         if (targetLength > padString.length) {
             padString += padString.repeat(targetLength/padString.length); //append to original to ensure we are longer than needed
         }
         return String(this) + padString.slice(0,targetLength);
     }
 };
}


if (typeof Object.assign != 'function') {
	  // Must be writable: true, enumerable: false, configurable: true
	  Object.defineProperty(Object, "assign", {
	    value: function assign(target, varArgs) { // .length of function is 2
	      'use strict';
	      if (target == null) { // TypeError if undefined or null
	        throw new TypeError('Cannot convert undefined or null to object');
	      }

	      var to = Object(target);

	      for (var index = 1; index < arguments.length; index++) {
	        var nextSource = arguments[index];

	        if (nextSource != null) { // Skip over if undefined or null
	          for (var nextKey in nextSource) {
	            // Avoid bugs when hasOwnProperty is shadowed
	            if (Object.prototype.hasOwnProperty.call(nextSource, nextKey)) {
	              to[nextKey] = nextSource[nextKey];
	            }
	          }
	        }
	      }
	      return to;
	    },
	    writable: true,
	    configurable: true
	  });
	}


//찜하기
function wish(a){
	$.ajax({
	      url: "/api/product-wish",
	      type: "POST",
	      data: {
	    	  "id" : $(a.currentTarget).data("prd-id")
	      },
	      dataType: "json",
	      success : function(data,b,c){
	    	  	if(data.success){
					if(data.data === 1){
						//♡
						$(a.currentTarget).text("♥ 찜하기");
					} else if(data.data === 2){
						//싫어요 
						$(a.currentTarget).text("♡ 찜하기");
					} 
				}else{
					alert(data.msg);
				}
			},
			error : function(a,b,c){
			}
	    });
}
