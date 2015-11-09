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
	$(".main-img").click(function() {
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
	$(".goods-img-bg .close").click(function() {
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
		$(".pagination>.page-num").removeClass("active");
		$(this).addClass("active");
		$.ajax({
			type: 'GET',
			url: "/" + window.lang + "/topic/search/" + $(this).children().first().text(),
			dataType: 'json',
			success: function(data) {
				console.log(data.topic[0].id);
				$('#tb-topic').find('tbody').empty();
				$(data.topic).each(function(index,element){
					console.log($(this)[0].id);
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
	})

})
