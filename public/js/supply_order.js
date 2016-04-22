$(function(){

        //未处理       Added by Tiffany Zhu 2016.04.21
        $("input[name='selAll1']").click(function(){
            $("input[name='supplyOrder1']").prop('checked',$(this).prop("checked"));
        });
        //已打包        Added by Tiffany Zhu 2016.04.21
        $("input[name='selAll2']").click(function(){
            $("input[name='supplyOrder2']").prop('checked',$(this).prop("checked"));
        });
        //已出库        Added by Tiffany Zhu 2016.04.21
        $("input[name='selAll3']").click(function(){
            $("input[name='supplyOrder3']").prop('checked',$(this).prop("checked"));
        });
        //已登机        Added by Tiffany Zhu 2016.04.21
        $("input[name='selAll4']").click(function(){
            $("input[name='supplyOrder4']").prop('checked',$(this).prop("checked"));
        });
        //已抵达中国      Added by Tiffany Zhu 2016.04.21
//        $("input[name='selAll5']").click(function(){
//            $("input[name='supplyOrder5']").prop('checked',$(this).prop("checked"));
//        });


        //未处理--打包商品      Added by Tiffany Zhu 2016.04.21
        $(document).on("click","#btn-submit-packaged",function(){
            var isPost = true;
            if($("input[name='supplyOrder1']:checked").length <= 0){
                isPost = false;
                if(window.lang == "cn"){
                    $('#js-userinfo-error1').text('请选择未处理的订单!').css('color', '#c00');
                    setTimeout("$('#js-userinfo-error1').text('').css('color', '#2fa900')",3000);
                }
                if(window.lang == "en" || window.lang == "kr"){
                    $('#js-userinfo-error1').text('Please select unprocessed order(s)!').css('color', '#c00');
                    setTimeout("$('#js-userinfo-error1').text('').css('color', '#2fa900')",3000);
                }
                return false;
            }
            if(window.lang == "cn"){
                if(confirm("确定已打包商品?")){
                }else{
                    isPost = false;
                    return false;
                }
            }
            if(window.lang == "en" || window.lang == "kr"){
                if(confirm("Have packaged goods for selected order(s)?")){
                }else{
                    isPost = false;
                    return false;
                }
            }

            //已打包商品的订单
            var packagedOrders = [];
            $("input[name='supplyOrder1']:checked").each(function(){
                var OrderNum = $(this).parents("tr").find("td:eq(1)").text();
                packagedOrders.push(OrderNum);
            })
            var status = "P";

            if(isPost){
               $.ajax({
                   type :  "POST",
                   url : "/supply/order/status/save/" + status,
                   contentType: "application/json; charset=utf-8",
                   data : JSON.stringify(packagedOrders),
                   error : function(request) {
                       alert("error");
                   },
                   success: function(data) {
                       setTimeout(location.reload(), 2000);
                   }
               })
            }
        })

        //已打包--商品出库      Added by Tiffany Zhu 2016.04.21
        $(document).on("click","#btn-submit-stock-out",function(){
            var isPost = true;
            if($("input[name='supplyOrder2']:checked").length <= 0){
                isPost = false;
                if(window.lang == "cn"){
                    $('#js-userinfo-error2').text('请选择已打包的订单!').css('color', '#c00');
                    setTimeout("$('#js-userinfo-error2').text('').css('color', '#2fa900')",3000);
                }
                if(window.lang == "en" || window.lang == "kr"){
                    $('#js-userinfo-error2').text('Please select packaged order(s)!').css('color', '#c00');
                    setTimeout("$('#js-userinfo-error2').text('').css('color', '#2fa900')",3000);
                }
                return false;
            }
            if(window.lang == "cn"){
                if(confirm("确定商品已出库?")){
                }else{
                    isPost = false;
                    return false;
                }
            }
            if(window.lang == "en" || window.lang == "kr"){
                if(confirm("the goods of selected order(s) have been stocked out?")){
                }else{
                    isPost = false;
                    return false;
                }
            }

            //已出库商品的订单
            var stockedOutOrders = [];
            $("input[name='supplyOrder2']:checked").each(function(){
                var OrderNum = $(this).parents("tr").find("td:eq(1)").text();
                stockedOutOrders.push(OrderNum);
            })
            var status = "S"

            if(isPost){
               $.ajax({
                   type :  "POST",
                   url : "/supply/order/status/save/" + status,
                   contentType: "application/json; charset=utf-8",
                   data : JSON.stringify(stockedOutOrders),
                   error : function(request) {
                       alert("error");
                   },
                   success: function(data) {
                       setTimeout(location.reload(), 2000);
                   }
               })
            }
        })

        //已出库--商品登机      Added by Tiffany Zhu 2016.04.21
        $(document).on("click","#btn-submit-on-board",function(){
            var isPost = true;
            if($("input[name='supplyOrder3']:checked").length <= 0){
                isPost = false;
                if(window.lang == "cn"){
                    $('#js-userinfo-error3').text('请选择已出库的订单!').css('color', '#c00');
                    setTimeout("$('#js-userinfo-error3').text('').css('color', '#2fa900')",3000);
                }
                if(window.lang == "en" || window.lang == "kr"){
                    $('#js-userinfo-error3').text('Please select stocked out order(s)!').css('color', '#c00');
                    setTimeout("$('#js-userinfo-error3').text('').css('color', '#2fa900')",3000);
                }
                return false;
            }
            if(window.lang == "cn"){
                if(confirm("确定商品已登机?")){
                }else{
                    isPost = false;
                    return false;
                }
            }
            if(window.lang == "en" || window.lang == "kr"){
                if(confirm("the goods of selected order(s) have been on board?")){
                }else{
                    isPost = false;
                    return false;
                }
            }

            //已出库商品的订单
            var onBoardOrders = [];
            $("input[name='supplyOrder3']:checked").each(function(){
                var OrderNum = $(this).parents("tr").find("td:eq(1)").text();
                onBoardOrders.push(OrderNum);
            })
            var status = "O"

            if(isPost){
               $.ajax({
                   type :  "POST",
                   url : "/supply/order/status/save/" + status,
                   contentType: "application/json; charset=utf-8",
                   data : JSON.stringify(onBoardOrders),
                   error : function(request) {
                       alert("error");
                   },
                   success: function(data) {
                       setTimeout(location.reload(), 2000);
                   }
               })
            }
        })

        //已登机--抵达中国      Added by Tiffany Zhu 2016.04.21
         $(document).on("click","#btn-submit-arrived",function(){
             var isPost = true;
             if($("input[name='supplyOrder4']:checked").length <= 0){
                 isPost = false;
                 if(window.lang == "cn"){
                     $('#js-userinfo-error4').text('请选择已登机的订单!').css('color', '#c00');
                     setTimeout("$('#js-userinfo-error4').text('').css('color', '#2fa900')",3000);
                 }
                 if(window.lang == "en" || window.lang == "kr"){
                     $('#js-userinfo-error4').text('Please select on board order(s)!').css('color', '#c00');
                     setTimeout("$('#js-userinfo-error4').text('').css('color', '#2fa900')",3000);
                 }
                 return false;
             }
             if(window.lang == "cn"){
                 if(confirm("确定商品已抵达中国?")){
                 }else{
                     isPost = false;
                     return false;
                 }
             }
             if(window.lang == "en" || window.lang == "kr"){
                 if(confirm("the goods of selected order(s) have arrived in China?")){
                 }else{
                     isPost = false;
                     return false;
                 }
             }

             //已登机商品的订单
             var arrivedOrders = [];
             $("input[name='supplyOrder4']:checked").each(function(){
                 var OrderNum = $(this).parents("tr").find("td:eq(1)").text();
                 arrivedOrders.push(OrderNum);
             })
             var status = "A"

             if(isPost){
                $.ajax({
                    type :  "POST",
                    url : "/supply/order/status/save/" + status,
                    contentType: "application/json; charset=utf-8",
                    data : JSON.stringify(arrivedOrders),
                    error : function(request) {
                        alert("error");
                    },
                    success: function(data) {
                        setTimeout(location.reload(), 2000);
                    }
                })
             }
         })
    //订单排序
    function sortNumber_u(a,b)
    {
        return a-b;
    };
    function sortNumber_d(a,b)
    {
        return b-a;
    };
    $(".order-time").click(function () {
        var index = $(this).parent().find("th").index(this);
        var arrOrderTime = [];
        $(this).parents("table").find("tr").find("td:nth-of-type("+(index+1)+")").each(function () {
            arrOrderTime.push(new Date($(this).html().replace(/-/g,"/")).getTime());
        });
        if($(this).find(":input").val()=="desc"){
            $(this).find("span").removeClass("glyphicon-arrow-up");
            $(this).find("span").addClass("glyphicon-arrow-down");
            arrOrderTime.sort(sortNumber_u);
            $(this).find(":input").val("asc")
        }else{
            $(this).find("span").removeClass("glyphicon-arrow-down");
            $(this).find("span").addClass("glyphicon-arrow-up");
            arrOrderTime.sort(sortNumber_d);
            $(this).find(":input").val("desc")
        }
        for(var i=0;i<arrOrderTime.length;i++){
            $(this).parents("table").find("tr").find("td:nth-of-type("+(index+1)+")").each(function () {
                if (new Date($(this).html().replace(/-/g,"/")).getTime() == arrOrderTime[i]) {
                    $(this).parents("table").find("th").parent().after($(this).parent());
                }
            })
        }
    })
})