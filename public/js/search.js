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
			"height":"800px",
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
		topicDto.type = $("#topic-form-type").val();

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
				'<td><a href="/' + window.lang +'/topic/updateById/' + $(this)[0].id + '">' + $(this)[0].id + '</a></td>' +
				'<td>' +
				'<img class="main-img" src="' + window.url + $(this)[0].themeImg + '" alt="" width="50">' +
				'</td>' +
				'<td>' + $(this)[0].type + '</td>' +
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
		commDto.id = $("#sku-id").val();
		commDto.invTitle = $("#sku-title").val();
		commDto.startAt = $("#item-form-starttime").val();
        commDto.endAt = $("#item-form-endtime").val();
        //起止时间如果为空
        if ($("#item-form-starttime").val() == '' || $("#item-form-starttime").val() == null) {
            commDto.startAt = "0000-01-01 00:00:00";
        }
        if ($("#item-form-endtime").val() == '' || $("#item-form-endtime").val() == null) {
            commDto.endAt = "99999-12-31 23:59:59";
        }
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
			var orMasterInv = "";
            if($(this)[0].state=="Y"){state="正常"}
            if($(this)[0].state=="D"){state="下架"}
            if($(this)[0].state=="N"){state="删除"}
            if($(this)[0].state=="K"){state="售空"}
            if($(this)[0].state=="P"){state="预售"}
            if($(this)[0].orMasterInv==true){orMasterInv="是"}
            if($(this)[0].orMasterInv==false){orMasterInv="否"}
            $('#tb-topic').find('tbody').append('' +
                '<tr class="tb-list-data">' +
                '<td><a href="javascript:void(0)" class="item-info">' + $(this)[0].id + '</a><input type="hidden" value="'+$(this)[0].id+'"><input type="hidden" value="'+$(this)[0].itemId+'"></td>' +
                '<td style="width: 20%;">' + $(this)[0].invTitle + '</td>' +
                '<td>' +
                '<img class="main-img" src="' + window.url + $(this)[0].invImg.substring(1,$(this)[0].invImg.length-1).split(",")[0].substring(7,$(this)[0].invImg.substring(1,$(this)[0].invImg.length-1).split(",")[0].length-1) + '" alt="" width="50">' +
                '</td>' +
                '<td>' + ($(this)[0].startAt != null && $(this)[0].startAt != '' ? $(this)[0].startAt.substr(0, 16) : '') + '</td>}' +
                '<td>' + ($(this)[0].endAt != null && $(this)[0].endAt != '' ? $(this)[0].endAt.substr(0, 16) : '') + '</td>}' +
                '<td>' + $(this)[0].restAmount + '</td>' +
                '<td>' + state + '</td>' +
                '<td>' + orMasterInv + '</td>' +
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
            orderDto.orderStatus = $("#order-form-status option:selected").val();
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
                var payMethod = "";
                if($(this)[0].payMethod == "JD"){
                    payMethod = "京东";
                }
                if($(this)[0].payMethod == "APAY"){
                    payMethod = "支付宝";
                }
                if($(this)[0].payMethod == "WEIXIN"){
                     payMethod = "微信";
                }
                var orderStatus = "";
                //当前时间减去24小时
                var time = new Date($.now() - 1*24*3600*1000);
                var createdTime = new Date($(this)[0].orderCreateAt);
                console.log(time);
                console.log(createdTime);

                if(createdTime < time && $(this)[0].orderStatus == "I"){
                    orderStatus = "订单已超时";
                }else{
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
                    if($(this)[0].orderStatus == "J"){
                        orderStatus = "拒收";
                    }
                    if($(this)[0].orderStatus == "N"){
                        orderStatus = "已删除";
                    }
                }
                $('#tb-topic').find('tbody').append('' +
                    '<tr class="tb-list-data">' +
                    '<td><a href="/' + window.lang +'/comm/order/detail/' + $(this)[0].orderId + '">' + $(this)[0].orderId + '</a></td>' +
                    '<td>' + $(this)[0].userId + '</td>' +
                    '<td>' + ($(this)[0].orderCreateAt != null && $(this)[0].orderCreateAt != '' ? $(this)[0].orderCreateAt.substr(0, 16) : '') + '</td>}' +
                    '<td>' + $(this)[0].payTotal + '</td>' +
                    '<td>' + payMethod + '</td>' +
                    '<td>' + orderStatus + '</td>' +
                    //'<td>' + (orderStatus == "订单已超时" ? '<a href="/' + window.lang +'/comm/order/detail/' + $(this)[0].orderId + '">取消订单</a>' : '') + '</td>}' +
                    '</tr>'
                );
            })
        }

        //每个查询页面对应一个相应的组装函数 拼购查询页面      Added by Tiffany Zhu 2016.01.22
        	funcList.pinlist_search = function pinlist_search(pageIndex) {
        		var pinDto = new Object();
        		pinDto.pinId = $("#pin_id").val();
        		pinDto.status = $("#status").val();

        		pinDto.startAt = $("#topic-form-starttime").val();
        		pinDto.endAt = $("#topic-form-endtime").val();
        		//开始时间如果为空
                if ($("#topic-form-starttime").val() == '' || $("#topic-form-starttime").val() == null) {
                    pinDto.startAt = "0000-01-01 00:00:00";
                }
                if ($("#topic-form-endtime").val() == '' || $("#topic-form-endtime").val() == null) {
                	pinDto.endAt = "99999-12-31 23:59:59";
                }
        		//调用共用ajax,url从根目录开始不需要加上语言
        		search("/pin/search/" + pageIndex, pinDto);
        	}

        	//每个查询页面对应一个相应的返回时填充函数 拼购查询页面       Added by Tiffany Zhu 2016.01.22
        	funcList.pinlist_data = function pinlist_data(data) {
        		//填充列表数据
        		$(data).each(function(index, element) {
        			var status = "";
                    if($(this)[0].status=="Y"){status="正常"}
                    if($(this)[0].status=="D"){status="下架"}
                    if($(this)[0].status=="N"){status="删除"}
                    if($(this)[0].status=="K"){status="售空"}
                    if($(this)[0].status=="P"){status="预售"}
                    $('#tb-topic').find('tbody').append('' +
                        '<tr class="tb-list-data">' +
                        '<td><a href="/'+window.lang+'/pin/getPinById/'+$(this)[0].pinId + ' ">' + $(this)[0].pinId + '</a></td>' +
                        '<td>' + $(this)[0].pinTitle + '</td>' +
                        '<td>' + ($(this)[0].startAt != null && $(this)[0].startAt != '' ? $(this)[0].startAt.substr(0, 16) : '') + '</td>}' +
                        '<td>' + ($(this)[0].endAt != null && $(this)[0].endAt != '' ? $(this)[0].endAt.substr(0, 16) : '') + '</td>}' +
                        '<td>' + status + '</td>' +
                        '<td><a href="javascript:void(0)' + ' ">' + $(this)[0].activityCount + '</a></td>' +
                        '<td><a href="/'+window.lang+'/pin/activityAdd/'+$(this)[0].pinId + ' ">' + '手动开团' + '</a></td>' +
                        '</tr>'
                    );
        		})
        	}





        //每个查询页面对应一个相应的组装函数  erp商品资料查询页面 ,只更改前缀,不要更改下划线后面的名称     Added By Sunny Wu
        funcList.itemInfoList_search = function orderlist_search(pageIndex) {
            var itemInfoDto = new Object();
            itemInfoDto.startTime = $("#itemInfo-form-starttime").val();
            itemInfoDto.endTime = $("#itemInfo-form-endtime").val();
            //起止时间如果为空
            if ($("#itemInfo-form-starttime").val() == '' || $("#itemInfo-form-starttime").val() == null) {
                itemInfoDto.startTime = "2015-01-01 00:00:00";
            }
            if ($("#itemInfo-form-endtime").val() == '' || $("#itemInfo-form-endtime").val() == null) {
                itemInfoDto.endTime = "2020-12-31 23:59:59";
            }
            //调用共用ajax,url从根目录开始不需要加上语言
            search("/itemInfo/search/" + pageIndex, itemInfoDto);
        }

        //每个查询页面对应一个相应的返回时填充函数 erp商品资料查询页面   Added By Sunny Wu
        funcList.itemInfoList_data = function itemInfoList_data(data) {
            //填充列表数据
            $(data).each(function(index, element) {
                $('#tb-topic').find('tbody').append('' +
                    '<tr class="tb-list-data">' +
                    '<td><a href="javascript:void(0)">' + $(this)[0].ItemId + '</a></td>' +
                    '<td>' + $(this)[0].ItemCode + '</td>' +
                    '<td>' + $(this)[0].ItemName + '</td>' +
                    '<td>' + '<img class="main-img" src="' + $(this)[0].PictureUrl + '" alt="" width="50">' + '</td>' +
                    '<td>' + $(this)[0].BarCode + '</td>' +
                    '<td>' + $(this)[0].CatCode + '</td>' +
                    '<td>' + $(this)[0].Supplier + '</td>' +
                    '<td>' + $(this)[0].SalesPrice + '</td>' +
                    '<td>' + $(this)[0].Size + '</td>' +
                    '<td>' + $(this)[0].Weight + '</td>' +
                    '<td>' + $(this)[0].CreatedTime + '</td>' +
                    '<td>' + $(this)[0].Status + '</td>' +
                    '<td>' + $(this)[0].Memo + '</td>' +
                    '</tr>'
                );
            })
        }

        //每个查询页面对应一个相应的组装函数  日志查询页面 ,只更改前缀,不要更改下划线后面的名称     Added By Sunny Wu
        funcList.loglist_search = function loglist_search(pageIndex) {
            var dataLogDto = new Object();
            dataLogDto.id = $("#log-id").val();
            dataLogDto.operateUser = $("#operate-user").val();
            dataLogDto.operateType = $("#operate-type").val();
            dataLogDto.startAt = $("#log-form-starAt").val();
            dataLogDto.endAt = $("#log-form-endAt").val();
            //起止时间如果为空
            if ($("#log-form-starAt").val() == '' || $("#log-form-starAt").val() == null) {
                dataLogDto.startAt = "0000-01-01 00:00:00";
            }
            if ($("#log-form-endAt").val() == '' || $("#log-form-endAt").val() == null) {
                dataLogDto.endAt = "99999-12-31 23:59:59";
            }
            //调用共用ajax,url从根目录开始不需要加上语言
            search("/log/search/" + pageIndex, dataLogDto);
        }

        //每个查询页面对应一个相应的返回时填充函数 日志查询页面   Added By Sunny Wu
        funcList.loglist_data = function loglist_data(data) {
            //填充列表数据
            $(data).each(function(index, element) {
                $('#tb-topic').find('tbody').append('' +
                    '<tr class="tb-list-data">' +
                    '<td><a href="/'+window.lang+'/log/findLog/'+$(this)[0].id+'">' + $(this)[0].id + '</a></td>' +
                    '<td>' + $(this)[0].operateUser + '</td>' +
                    '<td>' + $(this)[0].operateIp + '</td>' +
                    '<td>' + $(this)[0].operateType + '</td>' +
                    '<td>' + $(this)[0].logContent + '</td>' +
                    '<td>' + $(this)[0].operateTime.substr(0, 16) + '</td>' +
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
		if($(".grid thead").find("tr").length==2){
			$(".grid thead").find("tr").eq(1).prependTo(".grid tbody");
		}
		$(this).parent().prev().html(1);
		var arr= $(this).parents("tr").prevAll("tr");
		for(var i=0;i<arr.length;i++){
			$(arr[i]).find("td").first().html(Number($(arr[i]).find("td").first().html())+1);
		}
		$(".grid input[type=radio]:checked").parents("tr").index=0;
		$(".grid input[type=radio]:checked").parents("tr").appendTo(".grid thead");
	});
	/******选择商品*******/
	$(".chooseGood li").click(function(){
		$(".chooseGood li").css("background","none");
		$(this).css("background","#337ab7");
		var index = $(this).index(".chooseGood li");
		$("table.table").css("display","none");
		$("table.table").eq(index).css("display","table");
	})
})
