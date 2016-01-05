$(function(){
        $("orderCancel").click(function(){
             $.ajax({
                        type :  "POST",
                        url : "/topic/add/themeSave",
                        contentType: "application/json; charset=utf-8",
                        data : JSON.stringify(theme),
                        error : function(request) {
                            if (window.lang = 'cn') {
                                alert("订单取消失败!");
                            } else {
                                alert("Order cancel failed!");
                            }
                            setTimeout("$('#js-userinfo-error').text('')", 2000);
                        },
                        success: function(data) {
                            if (window.lang = 'cn') {
                               alert("订单取消成功!");
                            } else {
                                alert("Order cancel failed!");
                            }
                            setTimeout("$('#js-userinfo-error').text('').css('color','#c00')", 3000);
                            //返回订单详细页面
                            setTimeout("location.href='/"+window.lang+"/comm/order/detail/"+@orderDetail(0)+"'", 1000);
                        }
                    });
          })

    })