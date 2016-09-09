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
    /********************物流查询按钮********************/
    $(".check-logistics").click(function () {
        if($(this).hasClass("glyphicon-triangle-bottom")){
            $(this).removeClass("glyphicon-triangle-bottom").addClass("glyphicon-triangle-top");
        }else{
            $(this).removeClass("glyphicon-triangle-top").addClass("glyphicon-triangle-bottom");
        }
        $(".logistics-info").toggle();
    })


    //订单修改信息保存      Added by Tiffany Zhu 2016.09.09
    $(document).on("click","#order-save",function(){
        var isPost = true;
        if($("#exp-id-editable").val() == ""){
            isPost = false;
             $('#js-userinfo-error').text('请添加快递单号').css('color', '#c00');
             setTimeout("$('#js-userinfo-error').text('').css('color', '#2fa900')",3000);
             return false;
        }

        var orderId = parseInt($("#orderId").val());       //订单编号
        var subOrderId = parseInt($("#sub-order-num").val());     //子订单号
        var expName = $("#order-form-expName").val();   //快递公司名称
        var expCompanyCode = $("#order-form-expName option:selected").text();   //快递公司编号
        var expNum = $("#exp-id-editable").val();       //快递编号

        var data = [];
        var orderSplit = new Object();
        orderSplit.orderId = orderId;
        orderSplit.expressNm = expName;
        orderSplit.expressNum = expNum;
        data.push(orderSplit);

        if(isPost){
           $.ajax({
               type :  "POST",
               url : "/comm/order/updSplitOrder",
               contentType: "application/json; charset=utf-8",
               data : JSON.stringify(data),
               error : function(request) {
                   if (window.lang = 'cn') {
                        alert("保存失败!");
                   } else {
                       alert("Save error!");
                   }
               },
               success: function(data) {
                   if (window.lang = 'cn') {
                       alert("保存成功!");
                   } else {
                       alert("Save Success!");
                   }
                   //返回详情页面
                   setTimeout("location.href='/"+window.lang+"/comm/order/detail/"+ $("#orderId").val() +"'", 2000);

               }
           })
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







