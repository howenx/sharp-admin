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
	/***************************************by howen******************************************************/
	var funcList = {};

	var longDateFormat = 'yyyy-MM-dd HH:mm:ss';
	//将当前时间设置为结束时间
	// $('#topic-form-endtime').val($.format.date(new Date(), longDateFormat));

	//每个查询页面对应一个相应的组装函数  主题查询页面 ,只更改前缀,不要更改下划线后面的名称
	funcList.thmlist_search = function thmlist_search(pageIndex) {
		var topicDto = new Object();
		topicDto.id = $("#topic-form-id").val();
		topicDto.masterItemId = $("#topic-form-master-id").val();

		topicDto.startAt = $("#topic-form-starttime").val();
		topicDto.endAt = $("#topic-form-endtime").val();
		//起止时间如果为空
		if ($("#topic-form-starttime").val() == '' || $("#topic-form-starttime").val() == null) {
//			topicDto.startAt = $.format.date("0000-01-01 00:00:00", longDateFormat);
			topicDto.startAt = "0000-01-01 00:00:00";
		}
		if ($("#topic-form-endtime").val() == '' || $("#topic-form-endtime").val() == null) {
//			topicDto.endAt = $.format.date("99999-12-31 23:59:59", longDateFormat);
			topicDto.endAt = "99999-12-31 23:59:59";
		}

		//调用共用ajax,url从根目录开始不需要加上语言
		search("/topic/search/" + pageIndex, topicDto);
	}

	//每个查询页面对应一个相应的返回时填充函数 主题查询页面
	funcList.thmlist_data = function thmlist_data(data) {
		//填充列表数据
		$(data).each(function(index, element) {
			$('#tb-topic').find('tbody').append('' +
				'<tr class="tb-list-data">' +
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
	}

	//每个查询页面对应一个相应的组装函数 商品查询页面
	funcList.commlist_search = function commlist_search(pageIndex) {
		var commDto = new Object();
		commDto.id = $("#comm-id").val();
		commDto.itemTitle = $("#comm-title").val();
		commDto.themeId = $("#comm-topic-id").val();
		//调用共用ajax
		search("/comm/search/" + pageIndex, commDto);
	}

	//每个查询页面对应一个相应的返回时填充函数 商品查询页面
	funcList.commlist_data = function commlist_data(data) {
//		var hadjoin = '';
//		var willjoin = '';
//		//国际化
//		if (window.lang == "cn") {
//			join = '已加入';
//			willjoin = '未加入';
//		} else {
//			join = 'Had Join';
//			willjoin = 'Will Join';
//		}

		//填充列表数据
		$(data).each(function(index, element) {
			var state = "";
            if($(this)[0].state=="Y"){state="正常"}
            if($(this)[0].state=="D"){state="下架"}
            if($(this)[0].state=="K"){state="售空"}
            $('#tb-topic').find('tbody').append('' +
                '<tr class="tb-list-data">' +
                '<td><a href="/'+window.lang+'/comm/findById/'+$(this)[0].id + ' ">' + $(this)[0].id + '</a></td>' +
                '<td style="width: 20%;">' + $(this)[0].itemTitle + '</td>' +
                '<td>' +
                '<img class="main-img" src="' + window.url + $(this)[0].itemMasterImg + '" alt="" width="50">' +
                '</td>' +
                '<td>' + ($(this)[0].onShelvesAt != null && $(this)[0].onShelvesAt != '' ? $(this)[0].onShelvesAt.substr(0, 16) : '') + '</td>}' +
                '<td>' + ($(this)[0].offShelvesAt != null && $(this)[0].offShelvesAt != '' ? $(this)[0].offShelvesAt.substr(0, 16) : '') + '</td>}' +
                '<td><a href="javascript:void(0)">' + $(this)[0].themeId + '</a></td>' +
                '<td>' + state + '</td>' +
                '</tr>'
            );
		})
	}
       //每个查询页面对应一个相应的组装函数  订单查询页面 ,只更改前缀,不要更改下划线后面的名称     Added By Tiffany Zhu
        funcList.orderlist_search = function orderlist_search(pageIndex) {
            var orderDto = new Object();
            orderDto.orderId = $("#order-form-id").val();
            orderDto.userId = $("#order-form-userid").val();

            orderDto.orderCreateAt = $("#onShelvesAt").val();
            var selectedOption = $("#order-form-status option:selected");
            orderDto.orderStatus = selectedOption.value;
            //orderDto.express = $("#order-form-express").val();
            //创建时间如果为空
            if ($("#onShelvesAt").val() == '' || $("#onShelvesAt").val() == null) {
                orderDto.orderCreateAt = "0000-01-01 00:00:00";
            }
            //调用共用ajax,url从根目录开始不需要加上语言
            search("/comm/order/search/" + pageIndex, orderDto);
        }

        //每个查询页面对应一个相应的返回时填充函数 订单查询页面       Added by Tiffany Zhu
        funcList.orderlist_data = function orderlist_data(data) {
            //填充列表数据
            $(data).each(function(index, element) {
                var orderStatus;
                if($(this)[0].orderStatus == "I"){
                    orderStatus = "未支付";
                 }
                 if($(this)[0].orderStatus == "S"){
                    orderStatus = "支付成功";
                 }
                 if($(this)[0].orderStatus == "C"){
                    orderStatus = "订单取消";
                 }
                 if($(this)[0].orderStatus == "F"){
                    orderStatus = "支付失败";
                 }
                 if($(this)[0].orderStatus == "R"){
                     orderStatus = "已签收";
                 }
                 if($(this)[0].orderStatus == "D"){
                     orderStatus = "已发货";
                 }
                 if($(this)[0].orderStatus == ""){
                     orderStatus = "拒收";
                 }
                $('#tb-topic').find('tbody').append('' +
                    '<tr class="tb-list-data">' +
                    '<td><a href="/' + window.lang +'/comm/order/detail/' + $(this)[0].orderId + '">' + $(this)[0].orderId + '</a></td>' +
                    '<td>' + $(this)[0].userId + '</td>' +
                    '<td>' + ($(this)[0].orderCreateAt != null && $(this)[0].orderCreateAt != '' ? $(this)[0].orderCreateAt.substr(0, 16) : '') + '</td>}' +
                    '<td>' + "" + '</td>' +
                    '<td>' + $(this)[0].payTotal + '</td>' +
                    '<td>' + $(this)[0].payMethod + '</td>' +
                    '<td>' + orderStatus + '</td>' +
                    '</tr>'
                );
            })
        }

	/*********************************公用模块，不需要变更改动，如需变更改动请找howen ****************************************/
	//点击页数
	$(document).on('click', '.pagination>.page-num', function() {
		if ($(this).first().text() != $(".pagination").find(".active").first().text()) {
			$(".pagination>.page-num").removeClass("active");
			$(this).addClass("active");
			if (window.search_args + "_search" in funcList) {
				funcList[window.search_args + "_search"]($(this).first().text());
			}
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
			if (window.search_args + "_search" in funcList) {
				funcList[window.search_args + "_search"]($prev.first().text());
			}
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
			if (window.search_args + "_search" in funcList) {
				funcList[window.search_args + "_search"]($next.first().text());
			}
		} else {
			console.log("不存在")
		}
	})

	//主题查询页面,点击查询事件
	$(document).on('click', "#topic-search-bt", function() {
		if (window.search_args + "_search" in funcList) {
			funcList[window.search_args + "_search"](1);
		}
	})

	//公用的,传入当前页,查询条件所组成的Object,以及需要填充的列表tr,同时必须保证使用同一个table id

	function search(url, dto) {
		$.ajax({
			type: 'POST',
			url: "/" + window.lang + url,
			data: JSON.stringify(dto),
			contentType: "application/json; charset=utf-8",
			dataType: 'json',
			success: function(data) {
				// console.log(data);
				//回传结果数据不为空
				if (data.topic.length != 0) {

					// console.log(data);

					//清空列表数据
					$('#tb-topic').find('tbody').find(".tb-list-data").remove();
					//不显示没有数据
					$('#nodata-td').css('display', 'none');
					//填充列表数据
					if (window.search_args + "_data" in funcList) {
						funcList[window.search_args + "_data"](data.topic);
					}

					//page toolbar
					if (window.lang == "cn") {
						$('.page-toolbar-title').text("总计:" + data.countNum + "条/每页:" + data.pageSize + "条/共:" + data.pageCount + "页");
					} else {
						$('.page-toolbar-title').text("Count:" + data.countNum + " results/Every Page:" + data.pageSize + " results/Sum Page:" + data.pageCount + " pages");
					}
					//清空页码
					$('.pagination').find(".page-num").remove();
					//重新生成页码
					for (var i = 1; i <= data.pageCount; i++) {
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
					//清空数据
					$('#tb-topic').find('tbody').find(".tb-list-data").remove();
					//回传结果数据为空，显示没有数据
					$('#nodata-td').css('display', 'table-row');

					//page toolbar
					if (window.lang == "cn") {
						$('.page-toolbar-title').text("总计:" + data.countNum + "条/每页:" + data.pageSize + "条/共:" + data.pageCount + "页");
					} else {
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

	$(".grid").on("click","input[type=radio]",function(){
		//if($(".grid thead").find("tr").length==2){
		$(".grid thead").find("tr").eq(1).prependTo(".grid tbody");
		//}
		$(this).parent().prev().html(1);
		var arr= $(this).parents("tr").prevAll("tr");
		for(var i=0;i<arr.length;i++){
			$(arr[i]).find("td").first().html(Number($(arr[i]).find("td").first().html())+1);
		}
		$(".grid input[type=radio]:checked").parents("tr").index=0;
		$(".grid input[type=radio]:checked").parents("tr").appendTo(".grid thead");
	})
})
