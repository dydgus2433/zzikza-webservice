$(document).ready(function() {
	console.log("게시판");
	console.log("brdCateCd",brdCateCd);
	 
	$("#btnQna").off('click').on('click',function(){
		location.href = contextPath +"/board/"+brdCateCd+"/write";
	});
	
	$(".list tr").off('click').on('click',clickRow);
});



function clickRow(obj){
//	seq='${board.brdId}' cate='${board.brdCateCd}'
	var brdCateCd, brdId;
	var seq = $(this).attr("seq")
	var cate = $(this).attr("cate")
	console.log(obj, this);
	location.href = contextPath +"/board/"+cate+"/view/"+seq;
}
