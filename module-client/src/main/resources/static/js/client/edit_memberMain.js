$(document).ready(function(){
	$("#defaultChangeTr").on("click", function(){
		location.href = '/edit_memberInfo';
	})
	
	$("#passwordChangeTr").on("click", function(){
		location.href = '/edit_password';
	})

	$("#reservationTr").on("click", function(){
		location.href = '/reservation/list';
	})
	
	$("#withdrawalTr").on("click", function(){
		location.href = '/withdrawal';
	});

	$("#requestTr").on("click", function(){
		location.href = '/request/my_list';
	});
	
	$("#requestReplyTr").on("click", function(){
		location.href = '/requestReply/my_list';
	});
	
	$("#eventTestTr").on("click", function(){
		location.href = '/evt/goods/list?productCategory=&exhId=EXH0000000002&companyCd=dc';
	});
	
	$("#wishListTr").on("click", function(){
		location.href = '/wish_list';
	});
});

