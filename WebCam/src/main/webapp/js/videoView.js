

$(document).ready(function() {
	var data = {
			url: "/api/image",
			method: "GET"
	}
	var fps = 0;
	var size = 0;
	var currentCount = 0;
	
	$.ajax(data).done(function(data) {
		var first = data[0];
		fps = first.split("_")[1];
		size = data.length;
		
		for(var i=0; i<size; i++) {
			var name = data[i].split("/")[data[i].split("/").length-1];
			$(".image-preload").append("<img src='https://devdogs-cdn.azureedge.net/devdogs/odin/acro/"+name+"'>");
		}
		
		setInterval(function() {
			var src = $(".image-preload img").eq(currentCount).attr("src");
			var fileName = src.split("/")[src.split("/").length-1].split(".")[0];
			var nowCnt = fileName.split("_")[fileName.split("_").length-1];
			$("#videoImg").attr("src", src);
			$("#cnt-now").html(nowCnt);
			currentCount++;
			if(currentCount == size) {
				currentCount = 0;
			}
		}, 1000/fps);
	});
});