let calendarEl;
const week = [];
$(document).ready(function(){
    $.ajax({
        url :  '/api/studio-holiday/week',
        method : 'GET',
        data : '',
        async : false,
        contentType : "application/json;charset=utf-8",
    }).done(function(data) {
        const list = data.list;
        for(let i = 0; i < list.length; i++){
            const day = list[i].dateValue;
            week.push(day);
        }
    }).fail(function(jqXHR) {
        alert(jqXHR.responseJSON.message);

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
                const date = info.start._i;
                const year = date.substring(0, 4);
                const month = date.substring(5, 7);
                const day = date.substring(8, 10);
                location.href =  '/reservation/day' + '?year='+year+"&month="+month.padStart(2, '0')+"&day="+day.padStart(2, '0');
            }
        },
        dayClick: function(info) {
            const date = info._d;
            const year = date.getFullYear() + '';
            const month = date.getMonth() + 1 + '';
            const day = date.getDate() + '';
            location.href =  '/reservation/day' + '?year='+year+"&month="+month.padStart(2, '0')+"&day="+day.padStart(2, '0');

        },
        eventLimitClick: 'week',
        timeFormat: 'HH(:mm)',
        eventLimitClick : function () {
            $('[data-toggle="tooltip"]').tooltip({ container: 'body', html: true }); // re-init tooltips
            return "popover";
        },
        eventSources : [
            {
                url:  '/api/reservation/yyyymm',
                type: 'GET',
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
                url:  '/api/studio-holiday/day',
                type: 'GET',
                data: function() {
                    const date = $('#calendar').fullCalendar('getDate')._d;
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
