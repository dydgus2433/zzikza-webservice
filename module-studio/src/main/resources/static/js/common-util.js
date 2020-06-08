
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

var Util = {
		//ajax  통신
		 httpRequest : function(url, data, successCallBack, reqFailCallBack, isAsync) {
			var type = "POST";
			var async = isAsync == 'undefined' ?  true : ( isAsync != 'false' ? true : false);
			ajaxAbort = $.ajax({
				url :  url,
				method : type,
				type : type,
				async : async,
				data : JSON.stringify(data),
				contentType : "application/json;charset=utf-8",
			}).done(function(response) {
				successCallBack(url, response, data);
			}).fail(function(xhr, option, error) {
				var msg = '';
				if(xhr.status === 0){
					msg = "Not connect. verify Network.";
				}else if(xhr.status === 404){
					msg = "Requested page not found. [404]";
				}else if(xhr.status === 500){
					msg = "Internal Server Error. [500]";
				}else if(error === "parsererror"){
					msg = "Requested JSON parse failed.";
				}else if(error === "timeout"){
					msg = "Time out error.";
				}else if(error === "abort"){
					msg = "Ajax request aborted.";
				}else {
					msg = "Uncaught Error. : "+xhr.responseText;
				}
			}).always(function() {
		    	console.log('DONE');
		    });
		}
}


/**
 * 폼 Ajax 서브밋
 * 
 * @param jQuerySelector formSelector
 * @param Function validator
 * @returns
 */
function saveForm(formSelector, validator) {

	if (formSelector === undefined) {
		formSelector = '#mainForm';
	}
	var $targetForm = $(formSelector);

	if (validator !== undefined) {
		if (validator() === false) {
			return false;
		}
	}

	// 서브밋
	//$('#cont').html($('#cont_ifr').contents().find('#tinymce').html());
	$.ajax({
		url: $targetForm.attr('action'),
		data : $targetForm.serialize(),
		type: $targetForm.attr('method'),
	}).done(function(data) {
		console.info(data);
		alert('요청이 정상처리 되었습니다.');
		//console.log($targetForm.data('succeed-url'));
		if ($targetForm.data('succeed-url')) {
			location.href = $targetForm.data('succeed-url');
		} else {
			location.reload();
		}
	}).always(function() {
    	//console.info('DONE');
    });
}

