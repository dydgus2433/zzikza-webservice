$(document).ready(function () {
    $("#listBtn").off('click').on('click', function () {
        location.href = "/board/" + brdCateCd;
    });

	const images = $(".tView tr td img");
	if (images) {
        if (images.length) {
            for (let i = 0; i < images.length; i++) {
				const img = images[i];
				$(img).css('width', '100%');
            }
        }
    }
});

