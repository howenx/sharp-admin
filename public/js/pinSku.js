//弹出商品列表
function ShowModal1() {
        var sharedObject = {};
        sharedObject.flag = true;

        if (window.showModalDialog) {
            var retValue = showModalDialog("/topic/add/popup", sharedObject, "dialogWidth:1200px; dialogHeight:600px; dialogLeft:300px;");
            if (retValue) {
                UpdateFields(retValue);
            }
        }
        else {
            // for similar functionality in Opera, but it's not modal!
            var modal = window.open("/topic/add/popup", null, "width=1200,height=600,left=300,modal=yes,alwaysRaised=yes", null);
            modal.dialogArguments = sharedObject;
        }
    }

//返回选中的商品ID
function UpdateFields(obj) {
    $('#input_imgurl').val(obj.lable_id);
    $('#itemTitle').val(obj.itemTitle);
}

//返回模板中选中的图片 Added by Tiffany Zhu
function updateThemeImg(obj){
    $("#themeImg").find("img").attr("src",window.url + obj.url);
    //$("#themeImg").css({"background-image":"url("+ obj.url +")","background-size":"cover"});
    var input = $("#themeImg").find("input");
    $(input).attr("id",obj.url);
    $(input).width(obj.width);
    $(input).height(obj.height);
}


$(function(){
    //删除一行价格阶梯
    $(document).on("click",".delPingou",function(){
        $(this).parent().remove();
        $(".pingou").find("tr:gt(0)").find("td:nth-of-type(1)").each(function(index){
            $(this).html(index+1);
        });
    });

    //添加一行价格阶梯
    $(document).on("click",".addPriceLevel",function(){
        //验证用
        var positive_int = /^[0-9]*[1-9][0-9]*$/;
        var positive_float = /^(([0-9]+.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*.[0-9]+)|([0-9]*[1-9][0-9]*))$/;


        var peopleNum = $("#peopleNum").val();
        var price = $("#price").val();
        var masterMin = $("#masterMin").val();
        var memberMin = $("#memberMin").val();

        //进行验证
        if(!positive_int.test(peopleNum)){
            alert('"拼购人数"为整数数字!');
            return false;
        }
        if(!positive_float.test(price)){
            alert('"拼购价格"为数字!');
             return false;
        }
        if(!positive_float.test(masterMin)){
            alert('"团长减价"为数字!');
             return false;
        }
        if(!positive_float.test(memberMin)){
            alert('"团员减价"为数字!');
             return false;
        }

        //团长优惠券
        if($(":radio[value='tz-yes']").prop("checked")){
            if($(".tz").find("select").val() == ""){
                alert('请选择团长优惠券类别!');
                return false;
            }
            $(".tz").find("input").each(function(){
                if($(this).val() == ""){
                   alert('团长优惠券信息均不能为空!');
                    return false;
                }
            })
            if(!positive_float.test($(".tz").find("input").eq(0).val())){
                    alert('团长优惠券-限额为数字!');
                     return false;
            }
            if(!positive_float.test($(".tz").find("input").eq(1).val())){
                     alert('团长优惠券-面值为数字!');
                     return false;
            }
            if($(".tz").find("input").eq(2).val() > $(".tz").find("input").eq(3).val()){
                   alert('团长优惠券-实用期限日期不正确!');
                    return false;
            }
        }
        //没有团长优惠券
        if($(":radio[value='tz-no']").prop("checked")){

        }
        //团员优惠券
        if($(":radio[value='ty-yes']").prop("checked")){
            if($(".ty").find("select").val() == ""){
                alert('请选择团员优惠券类别!');
                return false;
            }
            $(".ty").find("input").each(function(){
                if($(this).val() == ""){
                    alert('团员优惠券信息均不能为空!');
                    return false;
                }
            })
            if(!positive_float.test($(".ty").find("input").eq(0).val())){
                     alert('团员优惠券-限额为数字!');
                     return false;
            }
            if(!positive_float.test($(".ty").find("input").eq(1).val())){
                     alert('团员优惠券-面值为数字!');
                     return false;
            }
            if($(".ty").find("input").eq(2).val() > $(".ty").find("input").eq(3).val()){
                    alert('团员优惠券-实用期限日期不正确!');
                    return false;
            }
        }

        var tableLength = $(".pingou").find("th").length + 3 ;
        var order = $(".pingou").find("tr").length;

            var tr=$("<tr>").appendTo(".pingou");
            for(var i=0;i<tableLength;i++){
                if(i==0){
                    var td = $("<td>").html("").appendTo(tr);   //阶梯价格ID
                    td.css({"display":"none"});
                }else if(i==1){
                    $("<td>").html(order).appendTo(tr);         //编号
                }else if(i==2){
                    $("<td>").html(peopleNum).appendTo(tr);     //人数
                }else if(i==3){
                    $("<td>").html(price).appendTo(tr);         //价格
                }else if(i==4){
                    $("<td>").html(masterMin).appendTo(tr);     //团长减价
                }else if(i==5){
                    $("<td>").html(memberMin).appendTo(tr);     //团员减价
                }else if(i==6){
                    if($(":radio[value='tz-no']").prop("checked")){
                        var td = $("<td>").html("").appendTo(tr);
                        td.css({"display":"none"});
                    }else{
                        var td = $("<td>").html($(".tz").find("select").val()).appendTo(tr);
                        td.css({"display":"none"});
                    }
                }else if(i==7){
                    if($(":radio[value='tz-no']").prop("checked")){
                        $("<td>").html("").appendTo(tr);                              //团长-类别
                    }else{
                        $("<td>").html($(".tz").find("option:selected").text()).appendTo(tr);
                    }
                }else if(i==8){
                    if($(":radio[value='tz-no']").prop("checked")){
                        $("<td>").html("").appendTo(tr);
                    }else{
                        $("<td>").html($(".tz").find("input").eq(0).val()).appendTo(tr);         //团长-限额
                    }
                }else if(i==9){
                     if($(":radio[value='tz-no']").prop("checked")){
                         $("<td>").html("").appendTo(tr);
                     }else{
                         $("<td>").html($(".tz").find("input").eq(1).val()).appendTo(tr);         //团长-面值
                     }
                }else if(i==10){
                     if($(":radio[value='tz-no']").prop("checked")){
                         $("<td>").html("").appendTo(tr);
                     }else{
                         $("<td>").html($(".tz").find("input").eq(2).val()).appendTo(tr);         //团长-开始时间
                     }
                }else if(i==11){
                     if($(":radio[value='tz-no']").prop("checked")){
                         $("<td>").html("").appendTo(tr);
                     }else{
                         $("<td>").html($(".tz").find("input").eq(3).val()).appendTo(tr);         //团长-结束时间
                     }
                }else if(i==12){
                    if($(":radio[value='ty-no']").prop("checked")){
                       var td = $("<td>").html("").appendTo(tr);
                       td.css({"display":"none"});
                    }else{
                        var td = $("<td>").html($(".ty").find("select").val()).appendTo(tr);
                        td.css({"display":"none"});
                    }
                }else if(i==13){
                    if($(":radio[value='ty-no']").prop("checked")){
                        $("<td>").html("").appendTo(tr);
                    }else{
                        $("<td>").html($(".ty").find("option:selected").text()).appendTo(tr); //团员-类别
                    }
                }else if(i==14){
                    if($(":radio[value='ty-no']").prop("checked")){
                        $("<td>").html("").appendTo(tr);
                    }else{
                        $("<td>").html($(".ty").find("input").eq(0).val()).appendTo(tr);         //团员-限额
                    }
                }else if(i==15){
                    if($(":radio[value='ty-no']").prop("checked")){
                        $("<td>").html("").appendTo(tr);
                    }else{
                        $("<td>").html($(".ty").find("input").eq(1).val()).appendTo(tr);         //团员-面值
                    }
                }else if(i==16){
                    if($(":radio[value='ty-no']").prop("checked")){
                        $("<td>").html("").appendTo(tr);
                    }else{
                        $("<td>").html($(".ty").find("input").eq(2).val()).appendTo(tr);         //团员-开始时间
                    }
                }else if(i==17){
                    if($(":radio[value='ty-no']").prop("checked")){
                        $("<td>").html("").appendTo(tr);
                    }else{
                        $("<td>").html($(".ty").find("input").eq(3).val()).appendTo(tr);         //团员-结束时间
                    }
                }else if(i==18){
                    $('<td class="delPingou" style="background: #ccc;cursor: pointer">').html("删除").appendTo(tr);
                }
            }
            var peopleNum = $("#peopleNum").val("");
            var price = $("#price").val("");
            var masterMin = $("#masterMin").val("");
            var memberMin = $("#memberMin").val("");
            $(".tz").find("select").val("");
            $(".tz").find("input").each(function(){
                $(this).val("");
            })
            $(".ty").find("select").val("");
            $(".ty").find("input").each(function(){
                $(this).val("");
            })


    });

    //不给团长发放优惠券
    $(document).on("click",":radio[value='tz-no']",function(){
        $(".tz").css({"display":"none"})
    })
    //给团长发放优惠券
    $(document).on("click",":radio[value='tz-yes']",function(){
        $(".tz").css({"display":""})
    })
    //不给团员发放优惠券
    $(document).on("click",":radio[value='ty-no']",function(){
        $(".ty").css({"display":"none"})
    })
    //给团员发放优惠券
    $(document).on("click",":radio[value='ty-yes']",function(){
        $(".ty").css({"display":""})
    })



    //打开图片模板
    $("#getTemplate").click(function(){
        var sharedObject = {};
        var modal = window.open("/"+window.lang+"/topic/add/templates", null, null, null);
        modal.dialogArguments = sharedObject;
    });

    //点击编辑按钮,可编辑内容
     $(document).on("click","#pinSkuEdit",function(){
         $("#pinSkuEdit").css("display","none");   //编辑
         $("#pinSkuSubmit").css("display","");   //保存
         $("#cancel").css("display","");         //取消

         $("#onShelvesAt").attr("disabled",false);    //开始时间
         $("#offShelvesAt").attr("disabled",false);   //结束时间
         $("#status").attr("disabled",false);         //状态
         $("#restrict").attr("disabled",false);       //每用户限购
         $(".btn-xm").eq(0).css("display","");        //选择商品'按钮'
         $("#getTemplate").eq(0).css("display","");   //图片模板'按钮'

         $("#pin_discount").attr("disabled",false);      //最低折扣率

         $(".pingou").find("tr").each(function(){     //价格阶梯表中的'删除'
             if($(this).find("td").length == 0){
                 $(this).find("th").eq(15).css("display","");
             }else{
                 $(this).find("td").eq(18).css("display","");
             }
         })
         $(".tiered-price").css("display","");      //编辑阶梯价格

     })

    //点击取消,不可编辑内容
     $(document).on("click","#cancel",function(){
         $("#pinSkuEdit").css("display","");             //编辑
            $("#pinSkuSubmit").css("display","none");    //保存
         $("#cancel").css("display","none");             //取消

         $("#onShelvesAt").attr("disabled",true);        //开始时间
         $("#offShelvesAt").attr("disabled",true);       //结束时间
         $("#status").attr("disabled",true);             //状态
         $("#restrict").attr("disabled",true);           //每用户限购
         $(".btn-xm").eq(0).css("display","none");       //选择商品'按钮'
         $("#getTemplate").eq(0).css("display","none");  //图片模板'按钮'

         $("#pin_discount").attr("disabled",true);      //最低折扣率

         $(".pingou").find("tr").each(function(){        //价格阶梯表中的'删除'
             if($(this).find("td").length == 0){
                 $(this).find("th").eq(15).css("display","none");
             }else{
                 $(this).find("td").eq(18).css("display","none");
             }
         })

         $(".tiered-price").css("display","none");  //编辑阶梯价格

     })

    //保存
    $(document).on("click","#pinSkuSubmit",function(){
        var isPost = true;
        var positive_int = /^[0-9]*[1-9][0-9]*$/;
        var positive_float = /^(([0-9]+.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*.[0-9]+)|([0-9]*[1-9][0-9]*))$/;
        //必填项验证
        if($("#onShelvesAt").val() == "" || $("#offShelvesAt").val() == "" || $("#input_imgurl").val() == "" || $("#restrict").val() == "" || $("#status").val() == ""){
            isPost = false;
            $('#js-userinfo-error').text('基本信息不能为空!').css('color', '#c00');
            setTimeout("$('#js-userinfo-error').text('').css('color', '#2fa900')",3000);
            return false;
        }

        if(!positive_int.test($("#restrict").val())){
            isPost = false;
            $('#js-userinfo-error').text('每用户限购为整数数字!').css('color', '#c00');
            setTimeout("$('#js-userinfo-error').text('').css('color', '#2fa900')",3000);
            return false;
        }

        if($("#onShelvesAt").val() > $("#offShelvesAt").val()){
            isPost = false;
            $('#js-userinfo-error').text('日期不正确!').css('color', '#c00');
            setTimeout("$('#js-userinfo-error').text('').css('color', '#2fa900')",3000);
            return false;
        }

        if($("#themeImg").find("img").attr("src") == ""){
            isPost = false;
            $('#js-userinfo-error').text('请添加活动图片!').css('color', '#c00');
            setTimeout("$('#js-userinfo-error').text('').css('color', '#2fa900')",3000);
            return false;
        }

        if($("table").find("tr").length == 1){
           isPost = false;
           $('#js-userinfo-error').text('请添加价格阶梯!').css('color', '#c00');
           setTimeout("$('#js-userinfo-error').text('').css('color', '#2fa900')",3000);
           return false;
        }

        if($("#pin_discount").val() == ""){
           isPost = false;
           $('#js-userinfo-error').text('最低折扣率不能为空!').css('color', '#c00');
           setTimeout("$('#js-userinfo-error').text('').css('color', '#2fa900')",3000);
           return false;
        }

        if(!positive_float.test($("#pin_discount").val())){
           isPost = false;
           $('#js-userinfo-error').text('最低折扣率为数字!').css('color', '#c00');
           setTimeout("$('#js-userinfo-error').text('').css('color', '#2fa900')",3000);
           return false;
        }

        var pinData = new Object();

        //组装价格阶梯
        var tieredPrice = []
        //最低价格
        var minPrice = parseFloat($(".pingou").find("tr").eq(1).find("td:eq(3)").text());
        var floorPrice = {};
        $(".pingou").find("tr").each(function(){
            if($(this).index() != 0){
                if(parseFloat($(this).find("td:eq(3)").text()) < minPrice){
                    minPrice = parseFloat($(this).find("td:eq(3)").text());
                    floorPrice.person_num = parseInt($(this).find("td:eq(2)").text());
                    floorPrice.price = minPrice;
                }
                var object = new Object();
                object.peopleNum = parseInt($(this).find("td:eq(2)").text());           //拼购人数
                object.price = parseFloat($(this).find("td:eq(3)").text());             //价格
                object.masterMinPrice = parseFloat($(this).find("td:eq(4)").text());    //团长减价
                object.memberMinPrice = parseFloat($(this).find("td:eq(5)").text());    //团员减价
                object.masterCouponClass = $(this).find("td:eq(6)").text();             //团长-类别
                object.masterCouponQuota = parseInt($(this).find("td:eq(8)").text());   //团长-限额
                object.masterCoupon = parseInt($(this).find("td:eq(9)").text());        //团长-面值
                if($(this).find("td:eq(10)").text() == ""){
                     object.masterCouponStartAt = "0000-01-01 00:00:00";
                }else{
                     object.masterCouponStartAt = $(this).find("td:eq(10)").text();      //团长-开始时间
                }
                if($(this).find("td:eq(11)").text() == ""){
                     object.masterCouponEndAt = "0000-01-01 00:00:00";
                }else{
                     object.masterCouponEndAt = $(this).find("td:eq(11)").text();       //团长-结束时间
                }
                object.memberCouponClass = $(this).find("td:eq(12)").text();            //团员-类别
                object.memberCouponQuota = parseInt($(this).find("td:eq(14)").text());  //团员-限额
                object.memberCoupon = parseInt($(this).find("td:eq(15)").text());       //团员-面值
                if($(this).find("td:eq(16)").text() == ""){
                    object.memberCouponStartAt = "0000-01-01 00:00:00";
                }else{
                    object.memberCouponStartAt = $(this).find("td:eq(16)").text();      //团员-开始时间
                }
                if($(this).find("td:eq(17)").text() == ""){
                    object.memberCouponEndAt = "0000-01-01 00:00:00";
                }else{
                    object.memberCouponEndAt = $(this).find("td:eq(17)").text();        //团员-结束时间
                }
                tieredPrice.push(object);
            }
        })
        pinData.tieredPrice = tieredPrice;

        //pinSku组装数据
        var pinSku = new Object();
        //列表图片
        var imgUrl = {};
        var url = $("#themeImg").find("img").attr("src");
        imgUrl.url = url.substring(url.indexOf('/',url.indexOf('/')+2) + 1);
        imgUrl.width = $("#themeImg").find("input").width();
        imgUrl.height =  $("#themeImg").find("input").height();

        if($("#pinId").val() != null){
            pinSku.pinId = $("#pinId").val();
        }
        pinSku.pinImg = JSON.stringify(imgUrl);
        pinSku.shareUrl = "";
        pinSku.status = $("#status").val();
        pinSku.startAt = $("#onShelvesAt").val();
        pinSku.endAt = $("#offShelvesAt").val();
        pinSku.restrictAmount = $("#restrict").val();
        pinSku.floorPrice = Json.stringify(floorPrice);
        pinSku.invId = $("#input_imgurl").val();
        pinSku.pinTitle = $("#itemTitle").val();
        pinSku.pinDiscount = $("#pin_discount").val();

        pinData.pinSku = pinSku;


        if(isPost){

         $.ajax({
             type :  "POST",
             url : "/pin/save",
             contentType: "application/json; charset=utf-8",
             data : JSON.stringify(pinData),
             error : function(request) {
                 if (window.lang = 'cn') {
                     $('#js-userinfo-error').text('保存失败');
                 } else {
                     $('#js-userinfo-error').text('Save error');
                 }
                 setTimeout("$('#js-userinfo-error').text('')", 2000);
             },
             success: function(data) {
                 alert("Save Success");
                 if (window.lang = 'cn') {
                     $('#js-userinfo-error').text('保存成功').css('color', '#2fa900');
                 } else {
                     $('#js-userinfo-error').text('Save success');
                 }
                 setTimeout("$('#js-userinfo-error').text('').css('color','#c00')", 3000);

                 if($("#pinId").val() != null){
                     //拼购修改, 成功后返回到拼购修改页面
                     setTimeout("location.href='/"+window.lang+"/pin/getPinById/"+ $("#pinId").val() +"'", 3000);
                 }else{
                      //拼购录入, 成功后返回到拼购录入页面
                      setTimeout("location.href='/"+window.lang+"/pin/add'", 3000);
                 }

             }
         });
        }
    })

})

