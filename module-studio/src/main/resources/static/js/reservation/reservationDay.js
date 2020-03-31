$(document).ready(function () {
    console.log("예약관리목록");
    $(".btn_book_new").off('click').on('click', function () {
        $.ajax({
            url: '/reservation/popup',
            data: {date: $("#dateVal").val()},
        }).done(function (result) {
            console.log(result);
            $("#popup").html('');
            $("#popup").html(result);
            $(".popup_book").show();
        }).fail(function (jqXHR, textStatus, errorThrown) {
            console.error('FAIL REQUEST: ', textStatus);
            alert('처리중 오류가 발생하였습니다.');
        }).always(function () {
            console.log('DONE');
        });
        $("#startDt").val($("#dateVal").val());
    });

    $(".reservation").off('click').on('click', function () {
        const id = $(this).attr("seq");
        console.log(id);

        $.ajax({
            url: '/reservation/popup',
            data: {id: id, date: $("#dateVal").val()},
        }).done(function (result) {
            console.log(result);
            $("#popup").html('');
            $("#popup").html(result);
            $(".popup_book").show();
        }).fail(function (jqXHR, textStatus, errorThrown) {
            console.error('FAIL REQUEST: ', textStatus);
            alert('처리중 오류가 발생하였습니다.');
        }).always(function () {
            console.log('DONE');
        });
    });


});


function prevDay(year, month, day) {
	let date = new Date(year, month, day);
	date = date.setDate(date.getDate() - 1);
    date = new Date(date);
    const dateYear = date.getFullYear() + '';
    const dateMonth = date.getMonth() + 1 + '';
    const dateDay = date.getDate() + '';
    console.log("prevDay", date.getFullYear(), date.getMonth(), date.getDate(), date);
    console.log("prevDay", date.getFullYear(), date.getMonth(), date.getDate(), date);
    location.href = '/reservation/day' + '?year=' + dateYear + "&month=" + dateMonth.padStart(2, '0') + "&day=" + dateDay.padStart(2, '0');
}

function nextDay(year, month, day) {
    let date = new Date(year, month, day);
    date = date.setDate(date.getDate() + 1);
    date = new Date(date);
    const dateYear = date.getFullYear() + '';
    const dateMonth = date.getMonth() + 1 + '';
    const dateDay = date.getDate() + '';
    console.log("nextDay", date.getFullYear(), date.getMonth(), date.getDate(), date);
    console.log("nextDay", date.getFullYear(), date.getMonth(), date.getDate(), date);
    location.href = '/reservation/day' + '?year=' + dateYear + "&month=" + dateMonth.padStart(2, '0') + "&day=" + dateDay.padStart(2, '0');
}

function monthSchedule(year, month) {
    console.log(year, month);
    location.href = "/reservation/month" + '?year=' + year + "&month=" + month.padStart(2, '0');
}

function regSchedule() {
    $(".popup_book").show();
}