$(function() {
	$(".glyphicon").click(function() {
		if ($(this).hasClass("glyphicon-chevron-up")) {
			$(this).removeClass("glyphicon-chevron-up");
			$(this).addClass("glyphicon-chevron-down");
		} else {
			$(this).removeClass("glyphicon-chevron-down");
			$(this).addClass("glyphicon-chevron-up");
		}
	});
	$(document).on("click", ".main-img", function(e) {
		$(".goods-img-bg").css({
			"height": $(window).height(),
			"display": "block"
		});
		$(".goods-img").css("left", ($(window).width() - 1200) / 2);
		$(this).clone().appendTo($(".goods-img")).css({
			"width": "100%",
			"z-index": 1000
		});
	})
	$(document).on("click", ".goods-img-bg .close", function(e) {
		$(".goods-img-bg img").remove();
		$(".goods-img-bg").css({
			"display": "none"
		});
	})
	$(document).on("click", ".goods-bg", function(e) {
		$(".goods-img-bg img").remove();
		$(".goods-img-bg").css({
			"display": "none"
		});
	})

	//点击页数
	$(document).on('click', '.pagination>.page-num', function() {
		if($(this).first().text()!=$(".pagination").find(".active").first().text()){
			$(".pagination>.page-num").removeClass("active");
			$(this).addClass("active");
			search($(this).first().text());
		}
		else{
			console.log("已经是当前页")
		}
	})
	
	//点击上一页
	$(document).on('click', '.pagination>.prev', function() {
		var $prev =$(".pagination").find(".active").prev(".page-num");
		console.log($prev.length)
		if($prev.length!=0){
			console.log("存在");
			$(".pagination>.page-num").removeClass("active");
			$prev.addClass("active");
			search($prev.first().text());
		}
		else {
			console.log("不存在")
		}
		
	})
	
	
	
	//点击下一页
	$(document).on('click', '.pagination>.next', function() {
		var $next =$(".pagination").find(".active").next(".page-num");
		console.log($next.length)
		if($next.length!=0){
			console.log("存在");
			$(".pagination>.page-num").removeClass("active");
			$next.addClass("active");
			search($next.first().text());
		}
		else {
			console.log("不存在")
		}
	})
	
	//ajax后台取列表
	function search(pageIndex){
		var topicDto = new Object();
		topicDto.id = $("#topic-form-id").val();
		topicDto.masterItemId = $("#topic-form-master-id").val();
		topicDto.startAt =$("#topic-form-starttime").val() ;
		topicDto.endAt = $("#topic-form-endtime").val();
		
		$.ajax({
			type: 'POST',
			url: "/" + window.lang + "/topic/search/" + pageIndex,
			data : JSON.stringify(topicDto),
			contentType: "application/json; charset=utf-8",
			dataType: 'json',
			success: function(data) {
				// console.log(data.topic[0].id);
				$('#tb-topic').find('tbody').empty();
				$(data.topic).each(function(index,element){
					// console.log($(this)[0].id);
					$('#tb-topic').find('tbody').append(''+
						'<tr>'+
                	    	'<td><a href="javascript:void(0)">'+$(this)[0].id+'</a></td>'+
                	    	'<td>'+
                	    	    '<img class="main-img" src="'+window.url+$(this)[0].themeImg+'" alt="" width="50">'+
                	    	'</td>'+
                	    	'<td>'+$(this)[0].masterItemId+'</td>'+
                	    	'<td>'+$(this)[0].title+'</td>'+
                	    	'<td>'+$(this)[0].startAt+'</td>'+
                	    	'<td>'+$(this)[0].endAt+'</td>'+
                		'</tr>'
					);
				
				})
			},
			error: function(jqXHR) {
				console.log(jqXHR);
			}
		});
	}

})
