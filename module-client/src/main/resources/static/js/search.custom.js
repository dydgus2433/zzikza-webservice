$(document).ready(function(){
	
	$.ajax({
		url : "/api/search-categories",
		method : "post",
	}).done(function(data){
		if(data.success){
			for(let i = 0; i < data.list.length; i ++){
				const category = data.list[i];
				//TODO : 컨텐츠 추가되면 지워
				//data.list[0] {commCd: "BP", commCdNm: "베이비"}
				const fileterCategory = ["BP", "GP", "ST", "WP"];
				if(fileterCategory.indexOf(category.commCd) < 0){
					const html = "<li><a href='javascript:moveToSearchCategories(\"" + category.commCd + "\");'>" + category.commCdNm + "</a></li>";
					$("ul#searchCategories").append(html);
						
				}
				
			}
		}
	}).fail(function(jqXHR) {
    	alert(jqXHR.responseJSON.message);

    }).always(function() {
    });

/*	$.ajax({
		url : "/api/search-keywords",
		method : "post",
	}).done(function(data){
		if(data.rows){
			for(var i = 0; i < data.rows.length; i ++){
				var keyword = data.rows[i];
				var html = "<li><a href='javascript:moveToSearchKeywords(\""+keyword.keywordId+"\");'>"+keyword.keywordNm+"</a></li>";
				$("ul#searchKeywords").append(html)
			}
		}
	}).fail(function(jqXHR) {
    	alert(jqXHR.responseJSON.message);

    }).always(function() {
    });*/
});

function moveToSearchKeywords(keywordId){
	
}


function moveToSearchCategories(categoryCode){
	location.href="/goods/list?productCategory="+categoryCode
}