$(document).ready(function(){
	$(".prdRow").off('click').on('click',function(){
		const seq = $(this).data('seq')
		location.href= '/product/view?id='+seq;
	})
});

