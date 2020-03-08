var calendarEl;
var week = [];
$(document).ready(function(){
	$.ajax({
		url : contextPath + '/api/selectHolidayReapeatWeek',
		method : 'POST',
		data : '',
		async : false,
		contentType : "application/json;charset=utf-8",
	}).done(function(data) {
		for(var i = 0; i < data.length; i++){
			var day = data[i].dtVal;
			week.push(day);
		}
	}).fail(function(jqXHR, textStatus, errorThrown) {
 	console.error('FAIL REQUEST: ', textStatus);
	alert('처리중 오류가 발생하였습니다.');
	 }).always(function() {
	 });	
	
	
	calendarEl = $('#calendar').fullCalendar({
		header: {
			left: 'prev',
			center: 'title',
			right: 'next',
		},
		titleFormat: {
			month:'YYYY-MM'
		},
		buttonText: {
			today:'오늘'
		},
		dayNamesShort: ['일', '월', '화', '수', '목', '금', '토'],
//		editable: true,
		editable : false ,
		eventLimit: true, // allow "more" link when too many events
		views: {
		    month: {
		      eventLimit: 3
		    }
		  },
//		  disableDragging: false,
		events: [
			{ 
		     title:"휴일", 
		     dow: week, // Repeat monday and thursday
//		     color: 'blue',
		     textColor: 'red'
		    }
		],
		eventClick: function(info) {
			if(info.start._i){
				 var date	= info.start._i;
				 var year	= date.substring(0,4);
				 var month	= date.substring(5,7);
	             var day	= date.substring(8,10);
	             location.href = contextPath + '/schedule/list' + '?year='+year+"&month="+month.padStart(2, '0')+"&day="+day.padStart(2, '0');
			}
		  }, 
		  dayClick: function(info) {
			  var date	= info._d;
			  var year	= date.getFullYear() +'';
			  var month	= date.getMonth() + 1 +'';
              var day	= date.getDate() +'';
              location.href = contextPath + '/schedule/list' + '?year='+year+"&month="+month.padStart(2, '0')+"&day="+day.padStart(2, '0');
              
		  }, 
		  eventLimitClick: 'week',
		  timeFormat: 'HH(:mm)',
		  eventLimitClick : function () {
		      $('[data-toggle="tooltip"]').tooltip({ container: 'body', html: true }); // re-init tooltips
		      return "popover";
		  },
		  eventSources : [
			  {
                url: contextPath + '/api/selectReservationYYYYMM',
                type: 'POST',
                data: function() {
                    var date = $('#calendar').fullCalendar('getDate')._d;
                    return {
                        month: date.getMonth() + 1,
                        year: date.getFullYear()
                    }
                },
//                color: 'yellow',
                textColor: 'black'
            }
			,
            {
                url: contextPath + '/api/selectHolidayCustom',
                type: 'POST',
                data: function() {
                    var date = $('#calendar').fullCalendar('getDate')._d;
                    return {
                        month: date.getMonth() + 1,
                        year: date.getFullYear()
                    }
                },
//                color: 'yellow',
                textColor: 'red'
            }
          ]

	});
	if(customYear&&customMonth){
		calendarEl.fullCalendar( 'gotoDate', new Date(customYear+'-'+customMonth)); 
	}
	
		
});
