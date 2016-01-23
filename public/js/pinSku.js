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
        var vals = $(this).parents(".form-group").find("input[type=text]");
        var tableLength = $(".pingou").find("th").length;
        var order = $(".pingou").find("tr").length;
        var positive_int = /^[0-9]*[1-9][0-9]*$/;
        var positive_float = /^(([0-9]+.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*.[0-9]+)|([0-9]*[1-9][0-9]*))$/;

        if(!positive_int.test(vals[0].value)){
            alert('"拼购人数"为整数数字!');
            return false;
        }
        if(!positive_float.test(vals[1].value)){
            alert('"拼购价格"为数字!');
             return false;
        }

        if(vals[0].value!==""&&vals[1].value!==""){
            var tr=$("<tr>").appendTo(".pingou");
            for(var i=0;i<tableLength;i++){
                if(i==0){
                    $("<td>").html(order).appendTo(tr);
                }else if(i==1){
                    $("<td>").html(vals[0].value).appendTo(tr);
                }else if(i==2){
                    $("<td>").html(vals[1].value).appendTo(tr);
                }else if(i==3){
                    $('<td class="delPingou" style="background: #ccc;cursor: pointer">').html("删除").appendTo(tr);
                }
            }
            vals[0].value="";
            vals[1].value="";
        }
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

         $(".pingou").find("tr").each(function(){     //价格阶梯表中的'删除'
             if($(this).find("td").length == 0){
                 $(this).find("th").eq(3).css("display","");
             }else{
                 $(this).find("td").eq(3).css("display","");
             }
         })
         $(".add-level").css("display","");               //添加价格
         $(":radio").attr("disabled",false);              //优惠券的'radio'
         $(".tz").find("select").attr("disabled",false);  //团长优惠券类别
         $(".tz").find("input").attr("disabled",false);   //团长优惠券填写框
         $(".ty").find("select").attr("disabled",false);  //团员优惠券类别
         $(".ty").find("input").attr("disabled",false);   //团员优惠券填写框
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

         $(".pingou").find("tr").each(function(){        //价格阶梯表中的'删除'
             if($(this).find("td").length == 0){
                 $(this).find("th").eq(3).css("display","none");
             }else{
                 $(this).find("td").eq(3).css("display","none");
             }
         })
         $(".add-level").css("display","none");          //添加价格
         $(":radio").attr("disabled",true);              //优惠券的'radio'
         $(".tz").find("select").attr("disabled",true);  //团长优惠券类别
         $(".tz").find("input").attr("disabled",true);   //团长优惠券填写框
         $(".ty").find("select").attr("disabled",true);  //团员优惠券类别
         $(".ty").find("input").attr("disabled",true);   //团员优惠券填写框
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

        //团长优惠券
        if($(":radio[value='tz-yes']").prop("checked")){
            if($(".tz").find("select").val() == ""){
                isPost = false;
                $('#js-userinfo-error').text('请选择团长优惠券类别!').css('color', '#c00');
                setTimeout("$('#js-userinfo-error').text('').css('color', '#2fa900')",3000);
                return false;

            }
            $(".tz").find("input").each(function(){
                if($(this).val() == ""){
                    isPost = false;
                    $('#js-userinfo-error').text('团长优惠券信息均不能为空!').css('color', '#c00');
                    setTimeout("$('#js-userinfo-error').text('').css('color', '#2fa900')",3000);
                    return false;
                }
            })

            if(!positive_float.test($(".tz").find("input").eq(0).val())){
                     isPost = false;
                     $('#js-userinfo-error').text('团长优惠券-限额为数字!').css('color', '#c00');
                     setTimeout("$('#js-userinfo-error').text('').css('color', '#2fa900')",3000);
                     return false;
            }

            if(!positive_float.test($(".tz").find("input").eq(1).val())){
                     isPost = false;
                     $('#js-userinfo-error').text('团长优惠券-面值为数字!').css('color', '#c00');
                     setTimeout("$('#js-userinfo-error').text('').css('color', '#2fa900')",3000);
                     return false;
            }


            if($(".tz").find("input").eq(2).val() > $(".tz").find("input").eq(3).val()){
                    isPost = false;
                    $('#js-userinfo-error').text('团长优惠券-实用期限日期不正确!').css('color', '#c00');
                    setTimeout("$('#js-userinfo-error').text('').css('color', '#2fa900')",3000);
                    return false;

            }
        }

        //团员优惠券
        if($(":radio[value='ty-yes']").prop("checked")){
            if($(".ty").find("select").val() == ""){
                isPost = false;
                $('#js-userinfo-error').text('请选择团员优惠券类别!').css('color', '#c00');
                setTimeout("$('#js-userinfo-error').text('').css('color', '#2fa900')",3000);
                return false;

            }
            $(".ty").find("input").each(function(){
                if($(this).val() == ""){
                    isPost = false;
                    $('#js-userinfo-error').text('团员优惠券信息均不能为空!').css('color', '#c00');
                    setTimeout("$('#js-userinfo-error').text('').css('color', '#2fa900')",3000);
                    return false;
                }
            })

            if(!positive_float.test($(".ty").find("input").eq(0).val())){
                     isPost = false;
                     $('#js-userinfo-error').text('团员优惠券-限额为数字!').css('color', '#c00');
                     setTimeout("$('#js-userinfo-error').text('').css('color', '#2fa900')",3000);
                     return false;
            }

            if(!positive_float.test($(".ty").find("input").eq(1).val())){
                     isPost = false;
                     $('#js-userinfo-error').text('团员优惠券-面值为数字!').css('color', '#c00');
                     setTimeout("$('#js-userinfo-error').text('').css('color', '#2fa900')",3000);
                     return false;
            }

            if($(".ty").find("input").eq(2).val() > $(".ty").find("input").eq(3).val()){
                    isPost = false;
                    $('#js-userinfo-error').text('团员优惠券-实用期限日期不正确!').css('color', '#c00');
                    setTimeout("$('#js-userinfo-error').text('').css('color', '#2fa900')",3000);
                    return false;

            }
        }

        //pinSku组装数据
        var pinData = new Object();
        var pinSku = new Object();
        //列表图片
        var imgUrl = {};
        var url = $("#themeImg").find("img").attr("src");
        imgUrl.url = url.substring(url.indexOf('/',url.indexOf('/')+2) + 1);
        imgUrl.width = $("#themeImg").find("input").width();
        imgUrl.height =  $("#themeImg").find("input").height();
        //价格阶梯
        var priceRule = [];
        //最低价格
        var minPrice = parseFloat($(".pingou").find("tr").eq(1).find("td:eq(2)").text());;
        $(".pingou").find("tr").each(function(){
            if($(this).index() != 0){
                if(parseFloat($(this).find("td:eq(2)").text()) < minPrice){
                    minPrice = parseFloat($(this).find("td:eq(2)").text());
                }
                var level = {};
                level.person_num = parseInt($(this).find("td:eq(1)").text());
                level.price = parseFloat($(this).find("td:eq(2)").text());
                priceRule.push(level);
            }
        })
        if($("#pinId").val() != null){
            pinSku.pinId = $("#pinId").val();
        }
        pinSku.pinImg = JSON.stringify(imgUrl);
        pinSku.shareUrl = "";
        pinSku.status = $("#status").val();
        pinSku.startAt = $("#onShelvesAt").val();
        pinSku.endAt = $("#offShelvesAt").val();
        pinSku.pinPriceRule = JSON.stringify(priceRule);
        pinSku.restrictAmount = $("#restrict").val();
        pinSku.floorPrice = minPrice;
        pinSku.itemId = $("#input_imgurl").val();
        pinSku.pinTitle = $("#itemTitle").val();

        pinData.pinSku = pinSku;


        //pinCoupon组装数据
        var pinCoupon = new Object();
        if($("#pinId").val() != null){
          pinCoupon.pinId = $("#pinId").val();
          pinCoupon.id = $("#coupon_id").val();
        }

        if($(":radio[value='tz-yes']").prop("checked")){
          pinCoupon.masterCouponClass = $(".tz").find("select").val();
          pinCoupon.masterCouponQuota = $(".tz").find("input").eq(0).val();
          pinCoupon.masterCoupon = $(".tz").find("input").eq(1).val();
          pinCoupon.masterCouponStartAt = $(".tz").find("input").eq(2).val();
          pinCoupon.masterCouponEndAt = $(".tz").find("input").eq(3).val();
        }
        if($(":radio[value='ty-yes']").prop("checked")){
          pinCoupon.memberCouponClass = $(".ty").find("select").val();
          pinCoupon.memberCouponQuota = $(".ty").find("input").eq(0).val();
          pinCoupon.memberCoupon = $(".ty").find("input").eq(1).val();
          pinCoupon.memberCouponStartAt = $(".ty").find("input").eq(2).val();
          pinCoupon.memberCouponEndAt = $(".ty").find("input").eq(3).val();
        }
        pinData.pinCoupon = pinCoupon;

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

