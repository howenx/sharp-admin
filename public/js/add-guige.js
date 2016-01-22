/*********改变文本方法*********/
function changeText(event,element){
    var e = window.event||event;
    var obj = e.target;
    if(obj.tagName=="INPUT"){
        return;
    }
    var oldHtml = $(element).html();
    console.log(oldHtml);

    if(element.childNodes.length==0
        ||(element.childNodes.length==1&&element.childNodes[0].nodeType==3)){
        var addText = $("<input type='text' autofocus='autofocus'>").css({
            "width":"100%"
        }).val(oldHtml);
        $(addText).blur(function(){
            $(element).html(this.value?this.value:oldHtml);
            if($(element).parent().find(":input")){
                $(element).parent().find(":input").val($(element).html());
            }
        })
    }
    element.innerHTML = "";
    $(addText).appendTo($(element));
    addText.focus();
}
/******初始数据******/
var option,sku;
function Init () {
    var sharedObject = window.dialogArguments;
    option = sharedObject.obj;
    if(option.tagName=="TD"){
        sku = sharedObject.sku;
        console.log(sku.itemPrice);
        $("input[name=itemPrice]").val(sku.itemPrice);
    }
}
/*****保存******/
function holdShow() {
    /**存储信息**/
    if($(option).hasClass("add-guige")){
        var trh = $("<tr>");
        var trd = $("<tr>");
        if (window.showModalDialog) {
            var sharedObject = {};
            window.returnValue = sharedObject;
        }
        else {
            var trdobj = {};
            trdobj.itemColor = $("input[name=itemColor]:checked").val();//颜色
            trdobj.itemSize = $("input[name=itemSize]:checked").val();//尺寸
            trdobj.startAt = $("input[name=startAt]").val();//开始时间
            trdobj.endAt = $("input[name=endAt]").val();//结束时间
            trdobj.itemPrice = $("input[name=itemPrice]").val();//现价
            trdobj.itemSrcPrice = $("input[name=itemSrcPrice]").val();//原价
            trdobj.itemCostPrice = $("input[name=itemCostPrice]").val();//成本价
            trdobj.itemDiscount = $("input[name=itemDiscount]").val();//折扣
            trdobj.invWeight = $("input[name=invWeight]").val();//重量
            trdobj.restrictAmount = $("input[name=restrictAmount]").val();//限购数量
            trdobj.amount = $("input[name=amount]").val();//库存总量
            trdobj.restAmount = $("input[name=restAmount]").val();//剩余库存
            trdobj.carriageModelCode = $("select[name=carriageModelCode]").val();//运费设置
            trdobj.invArea = $("select[name=invArea]").val();//库存区域
            trdobj.invCustoms = $("select[name=invCustoms]").val();//报关单位
            trdobj.postalTaxRate = $("input[name=postalTaxRate]").val();//税率
            trdobj.postalTaxCode = $("input[name=postalTaxCode]").val();//行邮税号
            trdobj.recordHZ = $("input[name=recordHZ]").val();//备案号:杭州
            trdobj.recordGZ = $("input[name=recordGZ]").val();//备案号:广州
            trdobj.recordSH = $("input[name=recordSH]").val();//备案号:上海
            trdobj.invImg = $("#galleryM").find(".main-img").attr("src");//sku主图
            var itemPreviewImgs = [];
            $("#galleryP").find(".main-img").each(function() {
                itemPreviewImgs.push($(this).attr("src"));
            });
            trdobj.itemPreviewImgs = itemPreviewImgs;//sku预览图
            //多样化价格
            if ($("#openVaryPrice").is(":checked")) {
                trdobj.openVaryPrice = true;
                var varyPrice = [];
                var price = document.getElementsByName("price");
                var limitAmount = document.getElementsByName("limitAmount");
                for(i=0;i<price.length;i++) {
//                    if (price[i].value=="" || limitAmount[i].value=="") {
//                        isPost = false;
//                        $("#warn-vary-price").text("多样化价格不能有空值");
//                    } else $("#arn-vary-price").text("");
                    varyPrice.push(price[i].value);
                    varyPrice.push(limitAmount[i].value);
                }
                trdobj.varyPrice = varyPrice;
                console.log(varyPrice);
            }
            else {
                trdobj.openVaryPrice = false;
                trdobj.varyPrice = "";
            }
alert(trdobj.itemPreviewImgs);
alert(trdobj.varyPrice);
            $("<th>").html("设为主商品").appendTo(trh);
            $("<td>").html('<input type="radio" name="orMasterInv" checked="checked" class="master-radio"/>').appendTo(trd);
            for(var item in trdobj){
                $("<td>").html(trdobj[item]).appendTo(trd);
            };
            $(".thval").each(function(){
                $("<th>").html($(this).html()).appendTo(trh)
            });
            $("<th>").html("修改").appendTo(trh);
            $("<td onclick='ShowModal(this,sku1)'>").html("编辑").appendTo(trd);
            var sharedObject = {};
            sharedObject.trh = trh;
            sharedObject.trd = trd;
            sharedObject.sku = trdobj;
            window.opener.updateTable (sharedObject);
        }
    }else if(option.tagName=="TD"){

    }
//    window.close();
}


$(function(){
    /** 图片放大和关闭的功能 **/
    $(document).on("click", ".main-img", function(e) {
        $(".goods-img-bg").css({
            "height": $(window).height(),
            "display": "block"
        });
        $(".goods-img").css("left", ($(window).width() - 1200) / 2);
        $(this).clone().appendTo($(".goods-img")).css({
            "width": "80%",
            "height":"600px",
            "z-index": 1000
        });
    });
    $(document).on("click", ".goods-img-bg .close", function(e) {
        $(".goods-img-bg img").remove();
        $(".goods-img-bg").css({
            "display": "none"
        });
    });
    $(document).on("click", ".goods-bg", function(e) {
        $(".goods-img-bg img").remove();
        $(".goods-img-bg").css({
            "display": "none"
        });
    });

    /** sku主图,点击移除的操作 **/
    $(document).on('click','.list-img .close',function(){
        var id = $(this).parent().parent().attr("id");
        //没有商品主图时,上传按钮置为显示
        if (document.getElementById(id).getElementsByTagName("div").length==1) {
            id = id.substring(7,9);
            $("#"+id).parent().css("display","inline-block");
        }
        $(this).parent().remove();
    });

    /** 预览图 点击移除的操作 **/
    $(document).on('click','.preview-img .close',function(){
        var id = $(this).parent().parent().attr("id");
        //商品预览图小于6张时恢复上传功能
        if (document.getElementById(id).getElementsByTagName("div").length==6) {
            id = id.substring(7,9);
            $("#"+id).parent().css("display","inline-block");
        }
        $(this).parent().remove();
    });

    /** 上传图片 **/
    $(document).on('change','.hidden1',function() {
        var file = $(this);
        file.after(file.clone().val(""));
        file.remove();
        var id=window.event.srcElement.id;
        var files = this.files;
        for (var i = 0; i < files.length; i++) {
            previewImage(this.files[i], id);
        }
    });

    function previewImage(file, id) {
        var galleryId = "gallery" + id;
        var gallery = document.getElementById(galleryId);
        var imageType = /image.*/;

        if (!file.type.match(imageType)) {
            throw "File Type must be an image";
        }

        var thumb = document.createElement("div");
        var img = document.createElement("img");

        img.classList.add('main-img');
        var button = document.createElement("button");
        button.classList.add('close');
        $(button).append('<span>&times;</span>');
        img.width = "";
        img.height = "";
        thumb.appendChild(img);
        thumb.appendChild(button);
        gallery.appendChild(thumb);

         //添加商品主图后,添加图片按钮隐藏
         if (id.indexOf("M")>=0&&document.getElementById(galleryId).getElementsByTagName("div").length>0) {
             $("#"+id).parent().css("display","none");
         }

         //商品预览图最多为6张
         if (id.indexOf("P")>=0 && document.getElementById("gallery"+id).getElementsByTagName("div").length==6) {
            $("#"+id).parent().css("display","none");
         }

         upload(thumb, file, id);

        // Using FileReader to display the image content
        var reader = new FileReader();
        reader.onload = (function(aImg) {
            return function(e) {
                aImg.src = e.target.result;
                var image = new Image();
                image.src = e.target.result;
                alert(["图片大小是: width:"+image.width+", height:"+image.height]);
                aImg.width = image.width;
                aImg.height = image.height;
            }
        })(img);
        reader.readAsDataURL(file);
    }

    function upload(thumb, file, id) {
        var formdata = new FormData();
        formdata.append("photo", file);
        formdata.append("params", "minify");
        var http = new XMLHttpRequest();
        var url = window.uploadURL+"/upload";
        alert(url);

        http.onreadystatechange = function() {
            if (http.readyState == 4 && http.status == 200) {
                var data = JSON.parse(http.responseText);
//                alert(data.message);
//                console.log("data.oss_prefix:"+data.oss_prefix);
//                console.log("data.oss_url:"+data.oss_url);
//                console.log("data.path:"+data.path);
//                console.log("data.imgid:"+data.imgid);
//                console.log("data.minify_url:"+data.minify_url);
                thumb.getElementsByTagName("img")[0].src = data.oss_prefix+data.oss_url;
            }
        }
        http.send(formdata);
    }

    /** 点击返回按钮,返回到商品录入页面 **/
    $("#return").on("click", function() {
        location.href="/comm/add";
    });

     /** 税率设置 **/
    $(document).on('change','.rateSet',function() {
        var rateSet = $("#rateSet").val();
        //行邮税率设置 F免税:税率为0,行邮税号不设置; S标准税率:税率不设置,选择行邮税号; D自定义税率:设置税率,行邮税号不设置
        if (rateSet == "F") {
            $("#postalTaxRate").val(0);
            $("#postalTaxCode").val("");
            $("#postalTaxRate").attr("readonly", true);
            $("#postalTaxCode").attr("readonly", true);
        }
        if (rateSet == "S") {
            $("#postalTaxRate").val("");
            $("#postalTaxCode").val("");
            $("#postalTaxRate").attr("readonly", true);
            $("#postalTaxCode").attr("readonly", false);
        }
        if (rateSet == "D") {
            $("#postalTaxRate").val("");
            $("#postalTaxCode").val("");
            $("#postalTaxRate").attr("readonly", false);
            $("#postalTaxCode").attr("readonly", true);
        }
    });

    $(document).on("click","input[name=color]",function(){
        $(".color").find(":input").removeClass("vals");
        $(this).addClass("vals");
    });
    $(document).on("click","input[name=size]",function(){
        $(".size").find(":input").removeClass("vals");
        $(this).addClass("vals");
    });
    /*********切换显示/隐藏table*********/
    $(".YN").click(function(){
        $(".guige").toggleClass("block");
    });
    /*********更改文本*********/
//    $(document).on("click",".guige td:not(:last-of-type)",function(e){
//        changeText(e,this);
//    });

    $(document).on("dblclick",".color span",function(e){
        changeText(e,this);
    });
    $(document).on("dblclick",".size span",function(e){
        changeText(e,this);
    });
    /********关闭********/
    $(document).on("click",".size .close",function(){
        $(this).parent().remove();
    });
    $(document).on("click",".color .close",function(){
        $(this).parent().remove();
    });

    /*********添加一行*********/
    $(document).on("click",".addTr",function(){
        var varyPriceTab = document.getElementById("varyPriceTab");
        var limitAmount = document.getElementsByName("limitAmount");
        var price = document.getElementsByName("price");
        var len = price.length;
        if (varyPriceTab.getElementsByTagName("tr").length>1 && limitAmount[len-1].value!="" && price[len-1].value!="" ) {
            var trHtml = '<td><input type="text" name="price"></td><td><input type="text" name="limitAmount"></td><td class="del delTr">删除</td>';
            $("<tr>").html(trHtml).appendTo($(".guige"));
        }
    });
    /*********删除一行*********/
    $(document).on("click",".delTr",function(){
        if (document.getElementById("varyPriceTab").getElementsByTagName("tr").length>2) {
            $(this).parent().remove();
        }
    });
    /*********添加尺寸*********/
    $(".add-size").click(function(){
        var html = '<label class="radio-inline">' +
            '<input type="radio" name="itemSize" value="双击编辑">' +
            '<span ondblclick="changeText(e,this)">双击编辑</span>' +
            '</label>';
        $(html).appendTo(".size");
    })
    /*********添加颜色*********/
    $(".add-color").click(function(){
        var html = '<label class="radio-inline">' +
            '<input type="radio" name="itemColor" value="双击编辑">' +
            '<span ondblclick="changeText(e,this)">双击编辑</span>' +
            '</label>';
        $(html).appendTo(".color");
    })
});

