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
			topicDto.startAt = "0001-01-01 00:00:00";
		}
		if ($("#topic-form-endtime").val() == '' || $("#topic-form-endtime").val() == null) {
//			topicDto.endAt = $.format.date("99999-12-31 23:59:59", longDateFormat);
			topicDto.endAt = "99999-12-31 23:59:59";
		}
		topicDto.title = $("#topic-form-thName").val();
		topicDto.orDestroy = $("#topic-form-status").val();

		//调用共用ajax,url从根目录开始不需要加上语言
		search("/topic/search/" + pageIndex, topicDto);
	}

	//每个查询页面对应一个相应的返回时填充函数 主题查询页面
	funcList.thmlist_data = function thmlist_data(data) {
		//填充列表数据
		$(data).each(function(index, element) {
		    var status;
        	if($(this)[0].orDestroy == false){
        	    status = "正常";
        	}
        	if($(this)[0].orDestroy == true){
                status = "下架";
            }


			$('#tb-topic').find('tbody').append('' +
				'<tr class="tb-list-data">' +
				'<td><a href="/' + window.lang +'/topic/updateById/' + $(this)[0].id + '">' + $(this)[0].id + '</a></td>' +
				'<td>' +
				'<img class="main-img" src="' + window.url + $(this)[0].themeImg + '" alt="" width="50">' +
				'</td>' +
				'<td>' + $(this)[0].type + '</td>' +
				'<td>' + $(this)[0].title + '</td>' +
				'<td>' + ($(this)[0].startAt != null && $(this)[0].startAt != '' ? $(this)[0].startAt.substr(0, 16) : '') + '</td>' +
				'<td>' + ($(this)[0].endAt != null && $(this)[0].endAt != '' ? $(this)[0].endAt.substr(0, 16) : '') + '</td>' +
				'<td>' + status + '</td>' +
				'</tr>'
			);
		})
	}

	//每个查询页面对应一个相应的组装函数 商品查询页面
	funcList.commlist_search = function commlist_search(pageIndex) {
		var commDto = new Object();
		commDto.id = $("#sku-id").val();
		commDto.invTitle = $("#sku-title").val();
		commDto.invCode = $("#inv-code").val();
		commDto.invArea = $("#inv-area").val();
		commDto.state = $("#state").val();
		commDto.startAt = $("#item-form-starttime").val();
        commDto.endAt = $("#item-form-endtime").val();
        //起止时间如果为空
        if ($("#item-form-starttime").val() == '' || $("#item-form-starttime").val() == null) {
            commDto.startAt = "2000-01-01 00:00:00";
        }
        if ($("#item-form-endtime").val() == '' || $("#item-form-endtime").val() == null) {
            commDto.endAt = "2999-12-31 23:59:59";
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
			var invArea = "";
			var orMasterInv = "";
            if($(this)[0].state=="Y"){state="正常"}
            if($(this)[0].state=="D"){state="下架"}
            if($(this)[0].state=="N"){state="删除"}
            if($(this)[0].state=="K"){state="售空"}
            if($(this)[0].state=="P"){state="预售"}
            if($(this)[0].orMasterInv==true){orMasterInv="是"}
            if($(this)[0].orMasterInv==false){orMasterInv="否"}
            if($(this)[0].invArea=="H"){invArea="杭州保税仓备案"}
            if($(this)[0].invArea=="HZ"){invArea="杭州保税仓直邮"}
            if($(this)[0].invArea=="G"){invArea="广州保税仓备案"}
            if($(this)[0].invArea=="GZ"){invArea="广州保税仓直邮"}
            if($(this)[0].invArea=="S"){invArea="上海保税仓备案"}
            if($(this)[0].invArea=="SZ"){invArea="上海保税仓直邮"}
            if($(this)[0].invArea=="K"){invArea="海外直邮"}
            $('#tb-topic').find('tbody').append('' +
                '<tr class="tb-list-data">' +
                '<td><input type="checkbox" name="selectItem"></td>' +
                '<td><a href="/'+window.lang+'/comm/findById/'+$(this)[0].itemId+'" class="item-info">' + $(this)[0].id + '</a></td>' +
                '<td style="width: 20%;">' + $(this)[0].invTitle + '</td>' +
                '<td>' +
                '<img class="main-img" src="' + window.url + $(this)[0].invImg + '" alt="" width="50">' +
                '</td>' +
                '<td>' + $(this)[0].itemColor + '</td>' +
                '<td>' + $(this)[0].itemSize + '</td>' +
                '<td>' + $(this)[0].invCode + '</td>' +
                '<td>' + invArea + '</td>' +
                '<td>' + ($(this)[0].startAt != null && $(this)[0].startAt != '' ? $(this)[0].startAt.substr(0, 19) : '') + '</td>}' +
                '<td>' + ($(this)[0].endAt != null && $(this)[0].endAt != '' ? $(this)[0].endAt.substr(0, 19) : '') + '</td>}' +
                '<td>' + $(this)[0].restAmount + '</td>' +
                '<td>' + orMasterInv + '</td>' +
                '<td><input type="hidden" value="'+$(this)[0].state+'">' + state + '</td>' +
                '</tr>'
            );
		})
	}
       //每个查询页面对应一个相应的组装函数  订单查询页面 ,只更改前缀,不要更改下划线后面的名称     Added By Tiffany Zhu
        funcList.orderlist_search = function orderlist_search(pageIndex) {
            var orderDto = new Object();
            var order = new Object();
            order.orderId = $("#order-form-id").val();
            order.userId = $("#order-form-userid").val();

            order.orderCreateAt = $("#onShelvesAt").val();
            order.orderStatus = $("#order-form-status option:selected").val();
            orderDto.order = order;
            orderDto.userPhone = $("#user_phone_num").val();
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
                if($(this)[4] == "JD"){
                    payMethod = "京东";
                }
                if($(this)[4] == "APAY"){
                    payMethod = "支付宝";
                }
                if($(this)[4] == "WEIXIN"){
                     payMethod = "微信";
                }
                var orderStatus = "";
                //当前时间减去24小时
                var time = new Date($.now() - 1*24*3600*1000);
                var createdTime = new Date($(this)[2]);
                if(createdTime < time && $(this)[5] == "I"){
                    orderStatus = "订单已超时";
                }else{
                    if($(this)[5] == "I"){
                        orderStatus = "未支付";
                    }
                    if($(this)[5] == "S"){
                        orderStatus = "成功";
                    }
                    if($(this)[5] == "C"){
                        orderStatus = "取消";
                    }
                    if($(this)[5] == "F"){
                        orderStatus = "失败";
                    }
                    if($(this)[5] == "R"){
                        orderStatus = "已收货";
                    }
                    if($(this)[5] == "D"){
                        orderStatus = "已发货";
                    }
                    if($(this)[5] == "J"){
                        orderStatus = "拒收";
                    }
                    if($(this)[5] == "N"){
                        orderStatus = "已删除";
                    }
                    if ($(this)[5] == "T") {
                        orderStatus =  "已退款";
                    }
                    if ($(this)[5] == "PI") {
                        orderStatus =  "拼购未支付";
                    }
                    if ($(this)[5] == "PS") {
                        orderStatus =  "拼购成功";
                    }
                    if ($(this)[5] == "PF") {
                        orderStatus =  "拼团失败未退款";
                    }
                }
                $('#tb-topic').find('tbody').append('' +
                    '<tr class="tb-list-data">' +
                    '<td><input type="checkbox" name="selectOrder"></td>'+
                    '<td><a href="/' + window.lang +'/comm/order/detail/' + $(this)[0] + '">' + $(this)[0] + '</a></td>' +
                    '<td>' + $(this)[7] + '</td>' +
                    '<td><a href="/' + window.lang +'/pin/activity/geActivityById/' + $(this)[8] + '">' + $(this)[8] + '</a></td>' +
                    '<td>' + $(this)[1] + '</td>' +
                    '<td>' + $(this)[6] + '</td>' +
                    '<td>' + ($(this)[2] != null && $(this)[2] != '' ? $(this)[2].substr(0, 16) : '') + '</td>}' +
                    '<td>' + $(this)[3] + '</td>' +
                    '<td>' + payMethod + '</td>' +
                    '<td><input type="hidden" value="'+$(this)[5]+'">' + orderStatus + '</td>' +
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
                    pinDto.startAt = "0001-01-01 00:00:00";
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
                        '<td>' +
                        '<img class="main-img" src="' + window.url + $(this)[0].pinImg + '" alt="" width="50">' +
                        '</td>' +
                        '<td>' + $(this)[0].floorPrice + '</td>' +
                        '<td>' + ($(this)[0].startAt != null && $(this)[0].startAt != '' ? $(this)[0].startAt.substr(0, 16) : '') + '</td>}' +
                        '<td>' + ($(this)[0].endAt != null && $(this)[0].endAt != '' ? $(this)[0].endAt.substr(0, 16) : '') + '</td>}' +
                        '<td>' + status + '</td>' +
                        '<td><a href="/'+window.lang+'/pin/activity/list/'+$(this)[0].pinId + ' ">' + $(this)[0].activityCount + '</a></td>' +
                        '<td><a href="/'+window.lang+'/pin/activityAdd/'+$(this)[0].pinId + ' ">' + '手动开团' + '</a></td>' +
                        '</tr>'
                    );
        		})
        	}

        //每个查询页面对应一个相应的组装函数 拼购活动查询页面      Added by Tiffany Zhu 2016.02.16
        funcList.activitylist_search = function activitylist_search(pageIndex) {
        	var activityDto = new Object();
        	activityDto.pinActiveId = $("#activity_id").val();
        	activityDto.pinId = $("#pin_id").val();

        	activityDto.createAt = $("#topic-form-starttime").val();
        	activityDto.endAt = $("#topic-form-endtime").val();
        	//开始时间如果为空
            if ($("#topic-form-starttime").val() == '' || $("#topic-form-starttime").val() == null) {
                activityDto.createAt = "0000-01-01 00:00:00";
            }
            if ($("#topic-form-endtime").val() == '' || $("#topic-form-endtime").val() == null) {
            	activityDto.endAt = "99999-12-31 23:59:59";
            }
        	//调用共用ajax,url从根目录开始不需要加上语言
        	search("/pin/activity/search/" + pageIndex, activityDto);
        }

        //每个查询页面对应一个相应的返回时填充函数 拼购活动查询页面       Added by Tiffany Zhu 2016.02.16
        funcList.activitylist_data = function activitylist_data(data) {
        	//填充列表数据
        	$(data).each(function(index, element) {
                $('#tb-topic').find('tbody').append('' +
                    '<tr class="tb-list-data">' +
                    '<td><a href="/'+window.lang+'/pin/activity/geActivityById/'+$(this)[0].pinActiveId + ' ">' + $(this)[0].pinActiveId + '</a></td>' +
                    '<td><a href="/'+window.lang+'/pin/getPinById/'+ $(this)[0].pinId + ' ">' + $(this)[0].pinId + '</a></td>' +
                    '<td>' + $(this)[0].pinTitle + '</td>' +
                    '<td>' + ($(this)[0].createAt != null && $(this)[0].createAt != '' ? $(this)[0].createAt.substr(0, 16) : '') + '</td>}' +
                    '<td>' + ($(this)[0].endAt != null && $(this)[0].endAt != '' ? $(this)[0].endAt.substr(0, 16) : '') + '</td>}' +
                    '<td>' + $(this)[0].personNum + '</td>' +
                    '<td>' + $(this)[0].pinPrice + '</td>' +
                    '<td>' + $(this)[0].joinPersons + '</td>' +
                    '<td>' + $(this)[0].status + '</td>' +
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
                    '<td>' + $(this)[0].operateTime + '</td>' +
                    '</tr>'
                );
            })
        }

        //每个查询页面对应一个相应的组装函数  日志查询页面 ,只更改前缀,不要更改下划线后面的名称     Added By Sunny Wu
        funcList.userloglist_search = function userloglist_search(pageIndex) {
            var userLogDto = new Object();
            userLogDto.id = $("#user-log-id").val();
            userLogDto.operateUser = $("#operate-user").val();
            userLogDto.operateType = $("#operate-type").val();
            userLogDto.startAt = $("#log-form-starAt").val();
            userLogDto.endAt = $("#log-form-endAt").val();
            //起止时间如果为空
            if ($("#log-form-starAt").val() == '' || $("#log-form-starAt").val() == null) {
                userLogDto.startAt = "0000-01-01 00:00:00";
            }
            if ($("#log-form-endAt").val() == '' || $("#log-form-endAt").val() == null) {
                userLogDto.endAt = "99999-12-31 23:59:59";
            }
            //调用共用ajax,url从根目录开始不需要加上语言
            search("/userlog/search/" + pageIndex, userLogDto);
        }

        //每个查询页面对应一个相应的返回时填充函数 日志查询页面   Added By Sunny Wu
        funcList.userloglist_data = function userloglist_data(data) {
            //填充列表数据
            $(data).each(function(index, element) {
                $('#tb-topic').find('tbody').append('' +
                    '<tr class="tb-list-data">' +
                    '<td><a href="/'+window.lang+'/userlog/findUserLog/'+$(this)[0].id+'">' + $(this)[0].id + '</a></td>' +
                    '<td>' + $(this)[0].operateUser + '</td>' +
                    '<td>' + $(this)[0].operateIp + '</td>' +
                    '<td>' + $(this)[0].operateType + '</td>' +
                    '<td>' + $(this)[0].operateTime + '</td>' +
                    '</tr>'
                );
            })
        }

        //每个查询页面对应一个相应的组装函数  APP用户查询页面 ,只更改前缀,不要更改下划线后面的名称     Added By Sunny Wu 2016.03.04
        funcList.appuserlist_search = function appuserlist_search(pageIndex) {
            var IDDto = new Object();
            IDDto.userId = $("#id-userId").val();
            IDDto.nickname = $("#id-nickname").val();
            IDDto.startAt = $("#id-form-starAt").val();
            IDDto.endAt = $("#id-form-endAt").val();
            //起止时间如果为空
            if ($("#id-form-starAt").val() == '' || $("#id-form-starAt").val() == null) {
                IDDto.startAt = "0000-01-01 00:00:00";
            }
            if ($("#id-form-endAt").val() == '' || $("#id-form-endAt").val() == null) {
                IDDto.endAt = "99999-12-31 23:59:59";
            }
            //调用共用ajax,url从根目录开始不需要加上语言
            search("/appUser/search/" + pageIndex, IDDto);
        }

        //每个查询页面对应一个相应的返回时填充函数 APP用户查询页面   Added By Sunny Wu  2016.03.04
        funcList.appuserlist_data = function appuserlist_data(data) {
            //填充列表数据
            $(data).each(function(index, element) {
                var activeYN = "";
                var realYN = "";
                var status = "";
                var gender = "";
                var idType = "";
                if($(this)[0].gender=="F"){gender="男"}
                if($(this)[0].gender=="M"){gender="女"}
//                if($(this)[0].orActive=="Y"){orActive="已激活"}
//                if($(this)[0].orActive=="N"){orActive="未激活"}
                if($(this)[0].orReal=="Y"){orReal="已认证"}
                if($(this)[0].orReal=="N"){orReal="未认证"}
                if($(this)[0].status=="Y"){status="正常"}
                if($(this)[0].status=="N"){status="阻止"}
                if($(this)[0].idType=="N"){idType="普通手机用户"}
                if($(this)[0].idType=="W"){idType="微信注册用户"}
                if($(this)[0].idType=="Q"){idType="腾讯QQ用户"}
                $('#tb-topic').find('tbody').append('' +
                    '<tr class="tb-list-data">' +
                    '<td><a href="javascript:void(0)">' + $(this)[0].userId + '</a></td>' +
                    '<td>' + $(this)[0].nickname+ '</td>' +
                    '<td><img class="main-img" src="' + window.url + $(this)[0].photoUrl + '" alt="" width="50"></td>' +
                    '<td>' + gender+ '</td>' +
                    '<td>' + $(this)[0].phoneNum + '</td>' +
//                    '<td>' + ($(this)[0].email!=null && $(this)[0].email!=''? $(this)[0].email : '') + '</td>' +
                    '<td>' + $(this)[0].birthday.substr(0, 10) + '</td>' +
//                    '<td>' + ($(this)[0].cardType!=null && $(this)[0].cardType!=''? $(this)[0].cardType : '') + '</td>' +
//                    '<td>' + ($(this)[0].cardNum!=null && $(this)[0].cardNum!=''? $(this)[0].cardNum : '') + '</td>' +
//                    '<td>' + ($(this)[0].realName!=null && $(this)[0].realName!=''? $(this)[0].realName : '') + '</td>' +
                    '<td>' + $(this)[0].regDt.substr(0, 16) + '</td>' +
                    '<td>' + $(this)[0].regIp + '</td>' +
                    '<td>' + idType + '</td>' +
//                    '<td>' + orActive + '</td>' +
                    '<td>' + orReal + '</td>' +
                    '<td>' + status + '</td>' +
                    '<td>' + $(this)[0].lastloginDt.substr(0, 16) + '</td>' +
                    '<td>' + $(this)[0].lastloginIp + '</td>' +
                    '</tr>'
                );
            })
        }

        //每个查询页面对应一个相应的组装函数  已使用优惠券查询页面 ,只更改前缀,不要更改下划线后面的名称     Added By Sunny Wu 2016.03.08
        funcList.couponlist_search = function couponlist_search(pageIndex) {
            var CoupDto = new Object();
            CoupDto.coupId = $("#coupon-id").val();
            CoupDto.cateNm = $("#coupon-catenm").val();
            CoupDto.useStartAt = $("#coup-form-useStarAt").val();
            CoupDto.useEndAt = $("#coup-form-useEndAt").val();
            CoupDto.orderId = $("#order-id").val();
            //起止时间如果为空
            if ($("#coup-form-useStarAt").val() == '' || $("#coup-form-useStarAt").val() == null) {
                CoupDto.useStartAt = "0000-01-01 00:00:00";
            }
            if ($("#coup-form-useEndAt").val() == '' || $("#coup-form-useEndAt").val() == null) {
                CoupDto.useEndAt = "99999-12-31 23:59:59";
            }
            //调用共用ajax,url从根目录开始不需要加上语言
            search("/coup/search/" + pageIndex, CoupDto);
        }

        //每个查询页面对应一个相应的返回时填充函数 已使用优惠券查询页面   Added By Sunny Wu  2016.03.08
        funcList.couponlist_data = function couponlist_data(data) {
        console.log(data);
            //填充列表数据
            $(data).each(function(index, element) {
                var useAt = new Date($(this)[0].useAt);
                useAt = useAt.getFullYear() + '-' + (useAt.getMonth() + 1) + '-' + useAt.getDate() + ' ' + useAt.getHours() + ':' + useAt.getMinutes() + ':' + useAt.getSeconds();
                $('#tb-topic').find('tbody').append('' +
                    '<tr class="tb-list-data">' +
                    '<td><a href="javascript:void(0)">' + $(this)[0].coupId + '</a></td>' +
                    '<td>' + ($(this)[0].cateNm!=null && $(this)[0].cateNm!=''? $(this)[0].cateNm : '') + '</td>' +
                    '<td>' + $(this)[0].userId+ '</td>' +
                    '<td>' + $(this)[0].orderId+ '</td>' +
                    '<td>' + useAt + '</td>' +
                    '<td>' + $(this)[0].limitQuota+ '</td>' +
                    '<td>' + $(this)[0].denomination+ '</td>' +
                    '</tr>'
                );
            })
        }


        //每个查询页面对应一个相应的组装函数  销售产品查询页面 ,只更改前缀,不要更改下划线后面的名称     Added By Sibyl 2016.03.09
        funcList.saleProductlist_search = function saleProductlist_search(pageIndex) {
            var saleDto = new Object();
            saleDto.name=$("#productName").val();
            saleDto.jdSkuId=$("#jdSkuId").val();
            //起止时间如果为空
            if ($("#starttime").val() != '' && $("#starttime").val() != null) {
                saleDto.startTime=$("#starttime").val();
            }
            if ($("#endtime").val()!= '' && $("#endtime").val() != null) {
                saleDto.endTime=$("#endtime").val();
            }
            //调用共用ajax,url从根目录开始不需要加上语言
            search("/sales/search/" + pageIndex, saleDto);
        }

        //每个查询页面对应一个相应的返回时填充函数 销售产品查询页面   Added By Sibyl  2016.03.09
        funcList.saleProductlist_data = function saleProductlist_data(data) {
            //填充列表数据
            $(data).each(function(index, element) {
                $('#tb-topic').find('tbody').append('' +
                    '<tr class="tb-list-data">' +
                     '<td><a href="/sales/product/find/'+$(this)[0].id+'">'+$(this)[0].id+'</a></td>'+
                     '<td>'+ $(this)[0].name+ '</td>' +
                     '<td>'+ $(this)[0].skuCode+ '</td>' +
                     '<td>'+ $(this)[0].productCode+ '</td>' +
                     '<td>'+ $(this)[0].spec+ '</td>' +
                     '<td>'+ $(this)[0].invArea+ '</td>' +
                     '<td>'+ $(this)[0].saleCount+ '</td>' +
                     '<td>'+ $(this)[0].inventory+ '</td>' +
                     '<td>'+ $(this)[0].productCost+ '</td>' +
                     '<td>'+ $(this)[0].stockValue+ '</td>' +
                     '<td>'+ $(this)[0].purchaseCount+ '</td>' +
                     '<td>'+ $(this)[0].updateAt+ '</td>' +
                     '<td><a href="/sales/order/import/'+$(this)[0].id+'" target="_blank" >订单</a></td>'+
                     '<td><a href="/sales/inventory/view/'+$(this)[0].id+'" target="_blank">库存</a></td>'+
                    '</tr>'
                );
            })
        }

        //每个查询页面对应一个相应的组装函数  销售订单查询页面 ,只更改前缀,不要更改下划线后面的名称     Added By Sibyl 2016.03.09
        funcList.saleOrderlist_search = function saleOrderlist_search(pageIndex) {
            var saleDto = new Object();
            saleDto.orderId=$("#orderId").val();
            saleDto.name=$("#productName").val();
            saleDto.shop=$("#shop").val();
            saleDto.saleProductId=$("#saleProductId").val();
            //起止时间如果为空
            if ($("#starttime").val() != '' && $("#starttime").val() != null) {
                saleDto.startTime=$("#starttime").val();
            }
            if ($("#endtime").val()!= '' && $("#endtime").val() != null) {
                saleDto.endTime=$("#endtime").val();
            }
            //调用共用ajax,url从根目录开始不需要加上语言
            search("/sales/order/search/" + pageIndex, saleDto);
        }

        //每个查询页面对应一个相应的返回时填充函数 销售订单查询页面   Added By Sibyl  2016.03.09
        funcList.saleOrderlist_data = function saleOrderlist_data(data) {
            //填充列表数据
            $(data).each(function(index, element) {
            var remarkImg;
            if($(this)[0].remarkStatus == 1){
                remarkImg = "/assets/images/biaoji1.png";
            }
            if($(this)[0].remarkStatus == 2){
                remarkImg = "/assets/images/biaoji2.png";
            }
            if($(this)[0].remarkStatus == 3){
                remarkImg = "/assets/images/biaoji3.png";
            }
            if($(this)[0].remarkStatus == 4){
                remarkImg = "/assets/images/biaoji4.png";
            }
            if($(this)[0].remarkStatus == 5){
                remarkImg = "/assets/images/biaoji5.png";
            }

            var saleAt=new Date($(this)[0].saleAt);
            saleAt = saleAt.getFullYear() + '-' + (saleAt.getMonth() + 1) + '-' + saleAt.getDate()
                $('#tb-topic').find('tbody').append('' +
                    '<tr class="tb-list-data" id="orderTr'+ $(this)[0].id+ '">' +
                     '<td><a href="/sales/order/find/'+$(this)[0].id+'">'+$(this)[0].id+'</a></td>'+
                     '<td>'+ saleAt + '</td>' +
                     '<td>'+ $(this)[0].orderId+ '</td>' +
                     '<td>'+ $(this)[0].productName+ '</td>' +
                     '<td>'+ $(this)[0].price+ '</td>' +
                     '<td>'+ $(this)[0].saleCount+ '</td>' +
                     '<td>'+ $(this)[0].discountAmount+ '</td>' +
                     '<td>'+ $(this)[0].saleTotal+ '</td>' +
                     '<td>'+ $(this)[0].jdRate+ '%</td>' +
                     '<td>'+ $(this)[0].jdFee+ '</td>' +
                     '<td>'+ $(this)[0].cost+ '</td>' +
                     '<td>'+ $(this)[0].shipFee+ '</td>' +
                     '<td>'+ $(this)[0].inteLogistics+ '</td>' +
                     '<td>'+ $(this)[0].packFee+ '</td>' +
                     '<td>'+ $(this)[0].storageFee+ '</td>' +
                     '<td>'+ $(this)[0].postalFee+ '</td>' +
                     '<td>'+ $(this)[0].postalTaxRate+ '%</td>' +
                     '<td>'+ $(this)[0].profit+ '</td>' +
                     '<td>'+ $(this)[0].invArea+'</td>' +
//                     '<td>'+ $(this)[0].remarkStatus+'</td>' +
                     '<td><img src="' + remarkImg + '" alt="" width="20"></td>' +
                     '<td>'+ $(this)[0].shop+'</td>' +
//                     '<td>'+ $(this)[0].updateAt+ '</td>' +
                     '<td><a onclick="delOrder('+$(this)[0].id+')">删除</a></td>'+
                    '</tr>'
                );
            })
        }

         //每个查询页面对应一个相应的组装函数  退款查询页面 ,只更改前缀,不要更改下划线后面的名称     Added By Tiffany Zhu 2016.04.14
         funcList.refundlist_search = function refundlist_search(pageIndex) {
             var refundDto = new Object();
             var refund = new Object();
             refund.id = $("#order-form-id").val();
             refund.userId = $("#order-form-userid").val();
             refund.state = $("#order-form-status option:selected").val();
             refund.orderId = $("#order-form-orderId").val();
             refundDto.refund = refund;
             refundDto.userPhone = $("#user_phone_num").val();
             //调用共用ajax,url从根目录开始不需要加上语言
             search("/comm/order/refundTemp/search/" + pageIndex, refundDto);
         }

         //每个查询页面对应一个相应的返回时填充函数 退款查询页面       Added by Tiffany Zhu 2016.04.14
         funcList.refundlist_data = function refundlist_data(data) {
             //填充列表数据
             $(data).each(function(index, element) {

                 $('#tb-topic').find('tbody').append('' +
                     '<tr class="tb-list-data">' +
                     '<td><a href="/comm/order/refundTemp/detail/'+ $(this)[0] +'">' + $(this)[0] + '</a></td>' +
                     '<td>' + $(this)[1] + '</td>' +
                     '<td>' + $(this)[2] + '</td>' +
                     '<td>' + $(this)[3] + '</td>' +
                     '<td>' + $(this)[4] + '</td>' +
                     '<td>' + $(this)[5] + '</td>' +
                     '<td>' + $(this)[6] + '</td>' +
                     '</tr>'
                 );
             })
         }


         //每个查询页面对应一个相应的组装函数  优惠券系统优惠券查询页面 ,只更改前缀,不要更改下划线后面的名称     Added By Sunny Wu 2016.04.26
         funcList.couList_search = function couList_search(pageIndex) {
             var CouponDto = new Object();
             CouponDto.couponNumber = $("#coupon_number").val();
             CouponDto.couponName = $("#coupon_name").val();
             CouponDto.couponType = $("#coupon_type").val();
             CouponDto.brandName = $("#brand_name").val();
             CouponDto.code = $("#code").val();
             CouponDto.usedPlaceName = $("#place_name").val();
             CouponDto.issuedAt = $("#issued_at").val();
             CouponDto.expiredAt = $("#expired_at").val();
             CouponDto.status = $("#status").val();
             //调用共用ajax,url从根目录开始不需要加上语言
             search("/coupon/search/" + pageIndex, CouponDto);
         }

         //每个查询页面对应一个相应的返回时填充函数 已使用优惠券查询页面   Added By Sunny Wu  2016.03.08
         funcList.couList_data = function couList_data(data) {
         console.log(data);
             //填充列表数据
             $(data).each(function(index, element) {
                 var status = "";
                 var operation = "";
                 if($(this)[0].status=="NOT_USED"){status="可使用";operation="作废";}
                 if($(this)[0].status=="USED"){status="已使用"}
                 if($(this)[0].status=="DROPPED"){status="作废"}
                 $('#tb-topic').find('tbody').append('' +
                     '<tr class="tb-list-data">' +
                     '<td>' + $(this)[0].couponNumber + '</td>' +
                     '<td>' + $(this)[0].couponName+ '</td>' +
                     '<td>' + $(this)[0].couponType+ '</td>' +
                     '<td>' + $(this)[0].usedPlaceName+ '</td>' +
                     '<td>' + $(this)[0].brandName+ '</td>' +
                     '<td>' + $(this)[0].code+ '</td>' +
                     '<td>' + $(this)[0].standardPrice+ '</td>' +
                     '<td>' + $(this)[0].price+ '</td>' +
                     '<td>' + $(this)[0].issuedAt+ '</td>' +
                     '<td>' + $(this)[0].expiredAt+ '</td>' +
                     '<td>' + $(this)[0].maximumExpiredAt+ '</td>' +
                     '<td>' + status+ '</td>' +
                     '<td><a href="javascript:;" class="coupon-drop">' + operation+ '</a></td>' +
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
    /**********订单管理********/
    $(".order-option>div").hover(function () {
        $(this).css("color","#fff");
    },function () {
        $(this).css("color","#333");
        $(".currect").css("color","#fff");
    }).click(function () {
        var index = $(this).index(".order-option>div");
        $(".currect").css("color","#333");
        $(".order-option>div").removeClass("currect");
        $(this).addClass("currect");
        $(".order-admin table").hide();
        $(".btn-submit").hide();
        $(".order-admin table").eq(index).show();
        $(".btn-submit").eq(index).show();
    })
})
