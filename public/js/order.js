$(function(){

    //全选或取消    Added by Tiffany Zhu 2016.01.07
    $("input[name='selAll']").click(function(){

        $("input[name='outTimeOrder']").prop('checked',$(this).prop("checked"));

    });
    //超时订单列表--取消订单
    $("#cancel").click(function(){
        var isPost = true;
        if($("input[name='outTimeOrder']:checked").length <= 0){
            isPost = false;
            alert("请选择订单!");
            return false;
        }
        if(confirm("确定取消订单?")){

        }else{
            isPost = false;
            return false;
        }
        //被取消的订单ID
        var cancelOrders = [];
        $("input[name='outTimeOrder']:checked").each(function(){
            var clOrderNum = $(this).parents("tr").find("td:eq(1)").text();
            cancelOrders.push(clOrderNum);
            console.log(clOrderNum);
        })

        if(isPost){
            $.ajax({
                type :  "POST",
                url : "/"+window.lang+"/comm/order/cancel",
                contentType: "application/json; charset=utf-8",
                data : JSON.stringify(cancelOrders),
                error : function(request) {
                    if (window.lang = 'cn') {
                        alert("订单取消失败!");
                    } else {
                        alert("Order cancel failed!");
                    }
                },
                success: function(data) {
                    if (window.lang = 'cn') {
                        alert("订单取消成功!");
                    } else {
                        alert("Order cancel success!");
                    }
                setTimeout("location.reload()", 1000);
                }
            });
        }

    })

    //返回订单列表
    $("#orderBack").click(function(){
         setTimeout("location.href='/"+window.lang+"/comm/order/search'", 300);
    })


     //取消超时订单
     $("#orderCancel").click(function(){
         var isPost = false;
         if(confirm("确定取消订单?")){
             isPost = true;
         }else{
             return false;
         }
         var ids = [];
         var id = $("#orderId").val();
         ids.push(id);

         if(isPost){
              $.ajax({
                    type :  "POST",
                    url : "/"+window.lang+"/comm/order/cancel",
                    contentType: "application/json; charset=utf-8",
                    data : JSON.stringify(ids),
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
                        setTimeout("location.href='/"+window.lang+"/comm/order/detail/"+id+"'", 1000);
                    }
              });
         }
     })

     //确认收货     Added by Tiffany Zhu 2016.04.15
     $("#confirm-receive").click(function(){
        var isPost = true;
                if($("input[name='outTimeOrder']:checked").length <= 0){
                    isPost = false;
                    alert("请选择订单!");
                    return false;
                }
                if(confirm("确定确认收货?")){

                }else{
                    isPost = false;
                    return false;
                }
                //被确认收货的订单ID
                var cancelOrders = [];
                $("input[name='outTimeOrder']:checked").each(function(){
                    var clOrderNum = $(this).parents("tr").find("td:eq(1)").text();
                    cancelOrders.push(clOrderNum);
                    console.log(clOrderNum);
                })

                if(isPost){
                    $.ajax({
                        type :  "POST",
                        url : "/"+window.lang+"/comm/order/confirmReceive",
                        contentType: "application/json; charset=utf-8",
                        data : JSON.stringify(cancelOrders),
                        error : function(request) {
                            if (window.lang = 'cn') {
                                alert("确认收货失败!");
                            } else {
                                alert("Order confirmed failed!");
                            }
                        },
                        success: function(data) {
                            if (window.lang = 'cn') {
                                alert("确认收货成功!");
                            } else {
                                alert("Order confirmed success!");
                            }
                        setTimeout("location.reload()", 1000);
                        }
                    });
                }
     })




//     $(document).on("click",".check-logistics",function(){
//        var expId = $("#exp-id").val();
//        if(expId != 0){
//            $.ajax({
//                  type :  "POST",
//                  url : "/"+window.lang+"/comm/order/getLogistics",
//                  contentType: "application/json; charset=utf-8",
//                  data : JSON.stringify(expId),
//                  error : function(request) {
//
//                  },
//                  success: function(data) {
//                     console.log(data);
//
//                  }
//            });
//        }
//     })


})







