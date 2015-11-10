$(function() {

	$(document).on("click", ".sort-topic", function(e) {
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


	var longDateFormat = 'yyyy-MM-dd HH:mm';
	//将当前时间设置为结束时间
	// $('#topic-form-endtime').val($.format.date(new Date(), longDateFormat));

	//点击页数
	$(document).on('click', '.pagination>.page-num', function() {
		if ($(this).first().text() != $(".pagination").find(".active").first().text()) {
			$(".pagination>.page-num").removeClass("active");
			$(this).addClass("active");
			search($(this).first().text());
		} else {
			console.log("已经是当前页")
		}
	})

	//点击上一页
	$(document).on('click', '.pagination>.prev', function() {
		var $prev = $(".pagination").find(".active").prev(".page-num");
		console.log($prev.length)
		if ($prev.length != 0) {
			console.log("存在");
			$(".pagination>.page-num").removeClass("active");
			$prev.addClass("active");
			search($prev.first().text());
		} else {
			console.log("不存在")
		}

	})

	//点击下一页
	$(document).on('click', '.pagination>.next', function() {
		var $next = $(".pagination").find(".active").next(".page-num");
		console.log($next.length)
		if ($next.length != 0) {
			console.log("存在");
			$(".pagination>.page-num").removeClass("active");
			$next.addClass("active");
			search($next.first().text());
		} else {
			console.log("不存在")
		}
	})

	//点击查询事件
	$(document).on('click',"#topic-search-bt",function(){
		search(1);
	})
	
	//点击重置事件
	$(document).on('click',"#topic-reset-bt",function(){
		$("#topic-form-id").val("");
		$("#topic-form-master-id").val("");
		$("#topic-form-starttime").val("");
		$("#topic-form-endtime").val("");
	})

	//ajax后台取列表
	function search(pageIndex) {
		var topicDto = new Object();
		topicDto.id = $("#topic-form-id").val();
		topicDto.masterItemId = $("#topic-form-master-id").val();

		topicDto.startAt = $("#topic-form-starttime").val();
		topicDto.endAt = $("#topic-form-endtime").val();

		//起止时间如果为空
		if ($("#topic-form-starttime").val() == '' || $("#topic-form-starttime").val() == null) {
			topicDto.startAt = $.format.date("0000-01-01 00:00", longDateFormat)
		}
		if ($("#topic-form-endtime").val() == '' || $("#topic-form-endtime").val() == null) {
			topicDto.endAt = $.format.date("99999-12-31 23:59", longDateFormat)
		}

		$.ajax({
			type: 'POST',
			url: "/" + window.lang + "/topic/search/" + pageIndex,
			data: JSON.stringify(topicDto),
			contentType: "application/json; charset=utf-8",
			dataType: 'json',
			success: function(data) {
				// console.log(data);
				//回传结果数据不为空
				if (data.topic.length != 0) {
					//清空列表数据
					$('#tb-topic').find('tbody').empty();
					//填充列表数据
					$(data.topic).each(function(index, element) {
						$('#tb-topic').find('tbody').append('' +
							'<tr>' +
							'<td><a href="javascript:void(0)">' + $(this)[0].id + '</a></td>' +
							'<td>' +
							'<img class="main-img" src="' + window.url + $(this)[0].themeImg + '" alt="" width="50">' +
							'</td>' +
							'<td>' + $(this)[0].masterItemId + '</td>' +
							'<td>' + $(this)[0].title + '</td>' +
							'<td>' + $(this)[0].startAt + '</td>' +
							'<td>' + $(this)[0].endAt + '</td>' +
							'</tr>'

						);
					})
					if (window.lang == "cn") {
						//page toolbar
						$('.page-toolbar-title').text("总计:" + data.countNum + "条/每页:" + data.pageSize + "条/共:" + data.pageCount + "页");
						
					}else{
						//page toolbar
						$('.page-toolbar-title').text("Count:" + data.countNum + " results/Every Page:" + data.pageSize + " results/Sum Page:" + data.pageCount + " pages");
					}
					//清空页码
					$('.pagination').find(".page-num").remove();
					//重新生成页码
					for (var i = 1;i <= data.pageCount;i++) {
						//如果是当前页,高亮
						if (i == data.pageNum) {
							$('.pagination').find('.next').before("" +
								'<li class="page-num active"><a href="javascript:void(0)">' + i + '</a></li>'
							);
						} else {
							$('.pagination').find('.next').before("" +
								'<li class="page-num"><a href="javascript:void(0)">' + i + '</a></li>'
							);
						}
					}

				} else {
					//回传结果数据为空，显示没有数据
					$('#tb-topic').find('tbody').empty();
					if (window.lang == "cn") {
						$('#tb-topic').find('tbody').append('' +
							'<tr><td colspan="6">没有数据</td></tr>'
						);
						//page toolbar
						$('.page-toolbar-title').text("总计:" + data.countNum + "条/每页:" + data.pageSize + "条/共:" + data.pageCount + "页");
						
						
					} else {
						$('#tb-topic').find('tbody').append('' +
							'<tr><td colspan="6">Not Found Data</td></tr>'
						);
						//page toolbar
						$('.page-toolbar-title').text("Count:" + data.countNum + " results/Every Page:" + data.pageSize + " results/Sum Page:" + data.pageCount + " pages");
					}
					//清空页码
					$('.pagination').find(".page-num").remove();
					//重新生成页码
					$('.pagination').find('.next').before("" +
						'<li class="page-num active"><a href="javascript:void(0)">1</a></li>'
					);
				}

			},
			error: function(jqXHR) {
				console.log(jqXHR);
			}
		});
	}

})
