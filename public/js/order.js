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

})







