$(document).ready(function () {
    console.log("게시판");
    console.log("brdCateCd", brdCateCd);

    $("#btnQna").off('click').on('click', function () {
        location.href = "/board/" + brdCateCd + "/write";
    });

    $(".list tr").off('click').on('click', clickRow);
});


function clickRow(obj) {
//	seq='${board.brdId}' cate='${board.brdCateCd}'
	let brdCateCd, brdId;
	const seq = $(this).attr("seq");
	const cate = $(this).attr("cate");
	console.log(obj, this);
    location.href =  "/board/" + cate + "/view/" + seq;
}
