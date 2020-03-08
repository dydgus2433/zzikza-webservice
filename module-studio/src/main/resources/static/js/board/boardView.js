$(document).ready(function() {
	
	$("#listBtn").off('click').on('click',function(){
		location.href = "/board/"+brdCateCd;
	});
	
	var imgs = $(".tView tr td img");
	if(imgs){
		if(imgs.length){
			for(var i =0; i < imgs.length; i++){
				var img = imgs[i];
				$(img).css('width', '100%');
			}
		}
		
	}
	
	
});

