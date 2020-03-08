$(document).ready(function(){
	console.log("예약관리목록");
	$(".btn_book_new").off('click').on('click',function(){
		$.ajax({
			url : contextPath + '/schedule/popup',
			data : {date : $("#dateVal").val()},
		}).done(function(result){
			console.log(result);
			$("#popup").html('');
			$("#popup").html(result);
			$(".popup_book").show();
		}).fail(function(jqXHR, textStatus, errorThrown) {
	 	console.error('FAIL REQUEST: ', textStatus);
		alert('처리중 오류가 발생하였습니다.');
		 }).always(function() {
		 	console.log('DONE');
		 });		
		$("#startDt").val($("#dateVal").val());
	});
	
	$(".reservation").off('click').on('click',function(){
		var rsrvId = $(this).attr("seq");
		console.log(rsrvId);
		
		$.ajax({
			url : contextPath + '/schedule/popup',
			data : {rsrvId : rsrvId , date : $("#dateVal").val()},
		}).done(function(result){
			console.log(result);
			$("#popup").html('');
			$("#popup").html(result);
			$(".popup_book").show();
		}).fail(function(jqXHR, textStatus, errorThrown) {
	 	console.error('FAIL REQUEST: ', textStatus);
		alert('처리중 오류가 발생하였습니다.');
		 }).always(function() {
		 	console.log('DONE');
		 });		
	});
	
	
});


function prevDay(year,month,day){
	var date = new Date(year, month, day);
	date = date.setDate(date.getDate()-1);
	date = new Date(date);
	 var year	= date.getFullYear() +'';
	 var month	= date.getMonth()+1 +'';
     var day	= date.getDate() +'';
	console.log("prevDay",date.getFullYear(),date.getMonth(),date.getDate(), date)
	console.log("prevDay",date.getFullYear(),date.getMonth(),date.getDate(), date)
	location.href = contextPath + '/schedule/list' + '?year='+year+"&month="+month.padStart(2, '0')+"&day="+day.padStart(2, '0');
}

function nextDay(year,month,day){
	var date = new Date(year, month, day);
	date = date.setDate(date.getDate()+1);
	date = new Date(date)
	var year	= date.getFullYear() +'';
	 var month	= date.getMonth()+1 +'';
    var day	= date.getDate() +'';
	console.log("nextDay",date.getFullYear(),date.getMonth(),date.getDate(), date)
	console.log("nextDay",date.getFullYear(),date.getMonth(),date.getDate(), date)
	location.href = contextPath + '/schedule/list' + '?year='+year+"&month="+month.padStart(2, '0')+"&day="+day.padStart(2, '0');
}

function monthSchedule(year,month){
	console.log(year,month);
	location.href = contextPath + "/schedule/view"+ '?year='+year+"&month="+month.padStart(2, '0');
}

function regSchedule(){
		$(".popup_book").show();
}