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
        var addText = $("<input type='text'>").css({
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

/****** 初始化数据 ******/
var option; //标识触发的按钮是添加还是编辑
function Init () {
    var sharedObject = window.dialogArguments;
    option = sharedObject.obj;
    //编辑操作,初始化,即把此行SKU数据显示在页面上
    if(option.tagName=="SPAN"){
        var skuObj = sharedObject.skuObj;
        $("#index").val(sharedObject.index);
        var colorCount = 1;
        //itemColor值在现有值中,选中该值
        $(".color").find("input").each(function() {
            if ($(this).val()==skuObj.itemColor) {
                $("input[name=itemColor][value='"+skuObj.itemColor+"']").attr("checked",true);
                return false;
            }
            colorCount++;
        });
        //itemColor值不在现有值中,创建新选项
        if (colorCount>$(".color").find("input").size()) {
            $("<label class='radio-inline'>").html('<input type="radio" name="itemColor" checked="checked" value="'+skuObj.itemColor+'" /> <span>'+skuObj.itemColor+'</span>').appendTo($(".color"));
        }
        var sizeCount = 1;
        //itemSize值在现有值中,选中该值
        $(".size").find("input").each(function() {
            if ($(this).val()==skuObj.itemSize) {
                $("input[name=itemSize][value='"+skuObj.itemSize+"']").attr("checked",true);
                return false;
            }
            sizeCount++;
        });
        //itemSize值不在现有值中,创建新选项
        if (sizeCount>$(".size").find("input").size()) {
            $("<label class='radio-inline'>").html('<input type="radio" name="itemSize" checked="checked" value="'+skuObj.itemSize+'" /> <span>'+skuObj.itemSize+'</span>').appendTo($(".size"));
        }
        $("#startAt").val(skuObj.startAt);
        $("#endAt").val(skuObj.endAt);
        $("#itemPrice").val(skuObj.itemPrice);
        $("#itemSrcPrice").val(skuObj.itemSrcPrice);
        $("#itemCostPrice").val(skuObj.itemCostPrice);
        $("#itemDiscount").val(skuObj.itemDiscount);
        $("#invWeight").val(skuObj.invWeight);
        $("#restrictAmount").val(skuObj.restrictAmount);
        $("#amount").val(skuObj.amount);
        $("#restAmount").val(skuObj.restAmount);
        $("#carriageModelCode").val(skuObj.carriageModelCode);
        $("#invArea").val(skuObj.invArea);
        $("#invCustoms").val(skuObj.invCustoms);
        $("#rateSet").val(skuObj.rateSet);
        if (skuObj.rateSet == "F") {
            $("#postalTaxRate").val(0);
            $("#postalTaxCode").val("");
            $("#postalTaxRate").attr("readonly", true);
            $("#postalTaxCode").attr("readonly", true);
        }
        if (skuObj.rateSet == "S") {
            $("#postalTaxRate").val("");
            $("#postalTaxCode").val("");
            $("#postalTaxRate").attr("readonly", true);
            $("#postalTaxCode").attr("readonly", false);
        }
        if (skuObj.rateSet == "D") {
            $("#postalTaxRate").val("");
            $("#postalTaxCode").val("");
            $("#postalTaxRate").attr("readonly", false);
            $("#postalTaxCode").attr("readonly", true);
        }
        $("#postalTaxRate").val(skuObj.postalTaxRate);
        $("#postalTaxCode").val(skuObj.postalTaxCode);
        $("#recordHZ").val(skuObj.recordHZ);
        $("#recordGZ").val(skuObj.recordGZ);
        $("#recordSH").val(skuObj.recordSH);
        //填充sku主图
        var invImg = JSON.parse(skuObj.invImg);
        $("<div>").html('<img class="main-img" width="'+invImg.width+'" height="'+invImg.height+'" src="'+window.imageUrl+invImg.url+'" ><button class="close"><span>&times;</span></button>').appendTo($("#galleryM"));
        $("#M").parent().css("display","none");
        //填充sku预览图
        var itemPreviewImgs = JSON.parse(skuObj.itemPreviewImgs);
        for(i=0;i<itemPreviewImgs.length;i++) {
            $("<div>").html('<img class="main-img" width="'+itemPreviewImgs[i].width+'" height="'+itemPreviewImgs[i].height+'" src="'+window.imageUrl+itemPreviewImgs[i].url+'" ><button class="close"><span>&times;</span></button>').appendTo($("#galleryP"));
        }
        if (itemPreviewImgs.length==6) $("#P").parent().css("display","none");
        //填充多样化价格
        $("#openVaryPrice").attr("checked",skuObj.orVaryPrice);
        if (skuObj.orVaryPrice==false) {
            $(".guige").removeClass("block");
        }
        var varyPriceArr = skuObj.varyPrice.split(",");
        var priceAmountData = document.getElementById("varyPriceTab").getElementsByTagName("input");
        for(v=0;v<varyPriceArr.length;v++) {
            if (v<=1)
                priceAmountData[v].value = varyPriceArr[v];
            if (v>1 && v%2==0)
                $("<tr>").html('<td><input type="text" name="price" value="'+varyPriceArr[v]+'"></td><td><input type="text" name="limitAmount" value="'+varyPriceArr[v+1]+'"></td><td class="del delTr">删除</td>').appendTo($(".guige"));
        }
    }
}

/***** 保存当前按钮功能 ******/
function saveCurr() {
    var orSave = true;
    var numberReg1 =    /^-?\d+$/;   //正整数
    var numberReg2 =    /^-?\d+\.?\d{0,2}$/; //整数或两位小数
    var numberReg3 =    /[^/d]/g;   //数字
    var numberReg4 =    /^[0-9a-zA-Z]*$/g;   //字母和数字
    /**存储信息**/
    var sharedObject = {};
    var trd = $("<tr>");
    var trdobj = {};
    var itemColor = $("input[name=itemColor]:checked").val();//颜色
    var itemSize = $("input[name=itemSize]:checked").val();//尺寸
    var startAt = $("#startAt").val();//开始时间
    var endAt =  $("#endAt").val();//结束时间;
    var itemPrice = $("#itemPrice").val();//现价;
    var itemSrcPrice = $("#itemSrcPrice").val();//原价
    var itemCostPrice = $("#itemCostPrice").val();//成本价
    var itemDiscount = $("#itemDiscount").val();//折扣
    var invWeight = $("#invWeight").val();//重量
    var restrictAmount = $("#restrictAmount").val();//限购数量
    var amount = $("#amount").val();//库存总量
    var restAmount = $("#restAmount").val();//剩余库存
    var carriageModelCode = $("#carriageModelCode").val();//运费设置
    var invArea = $("#invArea").val();//库存区域
    var invCustoms = $("#invCustoms").val();//报关单位
    var rateSet = $("#rateSet").val();//税率设置
    var postalTaxRate = $("#postalTaxRate").val();//税率
    var postalTaxCode = $("#postalTaxCode").val();//行邮税号
    var recordHZ = $("#recordHZ").val();//备案号:杭州
    var recordGZ = $("#recordGZ").val();//备案号:广州
    var recordSH = $("#recordSH").val();//备案号:上海
    //验证输入数据合法性
    if (!numberReg2.test(itemPrice) || !numberReg2.test(itemSrcPrice) || !numberReg2.test(itemCostPrice)|| !numberReg2.test(itemDiscount) || !numberReg1.test(invWeight)
        || !numberReg1.test(restrictAmount) || !numberReg1.test(amount) || !numberReg1.test(restAmount) || carriageModelCode=="" || (recordHZ=="" && recordGZ=="" && recordSH=="")) {
        orSave = false;
        alert("输入数据不合法!");
    }
    //上下架时间验证
    if (startAt >= endAt) {
           orSave = false;
           $("#warn-date").html("日期不正确");
    } else $("#warn-date").html("");
    //行邮税率设置 F免税:税率为0,行邮税号不设置; S标准税率:税率不设置,输入行邮税号(数字); D自定义税率:设置税率,行邮税号不设置
    if (rateSet == "") {
        orSave = false;
        $("#warn-rate").text("请设置税率");
    }
    else if (rateSet == "S") {
        if (!numberReg1.test(postalTaxCode)) {
            orSave = false;
            $("#warn-rate").text("请输入正确的行邮税号");
        } else $("#warn-rate").text("");
    }
    else if (rateSet == "D") {
        if (!numberReg1.test(postalTaxRate)) {
            orSave = false;
            $("#warn-rate").text("税率为整数");
        } else $("#warn-rate").text("");
    }
    else if (rateSet = "F") {$("#warn-rate").html("");}
    else  $("#warn-rate").html("");
    //sku主图
    var invImg = {};
    var mDiv = document.getElementById("galleryM").getElementsByTagName("div");
    if (mDiv.length<1) {
        orSave = false;
        $("#warn-m").text("请上传主图");
    } else {
        $("#warn-m").text("");
        var imgM = $("#galleryM").find(".main-img");
        var imgSrc = imgM.attr("src");
        if (imgSrc != null) invImg["url"] = imgSrc.substring(imgSrc.lastIndexOf('/')+1,imgSrc.length);
        invImg["width"] = imgM.attr("width");
        invImg["height"] = imgM.attr("height");
    }
    //sku预览图
    var itemPreviewImgs = [];
    var pDiv = document.getElementById("galleryP").getElementsByTagName("div");
    if (pDiv.length<1) {
        orSave = false;
        $("#warn-p").text("请上传预览图");
    } else {
        $("#warn-p").text("");
        //上传的预览图尺寸不一致时提示
        var imgs =  document.getElementById("galleryP").getElementsByTagName("div");
        for(j=0;j<imgs.length;j++) {
            if (j>0) {
                var preImg = imgs[j-1].getElementsByTagName("img")[0];
                var img = imgs[j].getElementsByTagName("img")[0];
                var preWidth = preImg.getAttribute("width");
                var preHeight = preImg.getAttribute("height");
                var width = img.getAttribute("width");
                var height = img.getAttribute("height");
                if (width!=preWidth || height!=preHeight) {
                    if (window.confirm("上传的预览图尺寸不一致,是否继续?")) {}
                    else isPost = false;
                    break;
                }
            }
        }
        $("#galleryP").find(".main-img").each(function() {
            var imgsV = {};
            var preSrc = $(this).attr("src");
            if (preSrc != null) imgsV["url"] = preSrc.substring(preSrc.lastIndexOf('/')+1,preSrc.length);
            imgsV["width"] = $(this).attr("width");
            imgsV["height"] = $(this).attr("height");
            itemPreviewImgs.push(imgsV);
        });
    }

    trdobj.itemColor = itemColor;
    trdobj.itemSize = itemSize;
    trdobj.startAt = startAt;
    trdobj.endAt = endAt;
    trdobj.itemPrice = itemPrice;
    trdobj.itemSrcPrice = itemSrcPrice;
    trdobj.itemCostPrice = itemCostPrice;
    trdobj.itemDiscount = itemDiscount;
    trdobj.invWeight = invWeight;
    trdobj.restrictAmount = restrictAmount;
    trdobj.amount = amount;
    trdobj.restAmount = restAmount;
    trdobj.carriageModelCode = carriageModelCode;
    trdobj.invArea = invArea;
    trdobj.invCustoms = invCustoms;
    trdobj.rateSet = rateSet;
    trdobj.postalTaxRate = postalTaxRate;
    trdobj.postalTaxCode = postalTaxCode;
    trdobj.recordHZ = recordHZ;
    trdobj.recordGZ = recordGZ;
    trdobj.recordSH = recordSH;
    trdobj.invImg = JSON.stringify(invImg);
    trdobj.itemPreviewImgs = JSON.stringify(itemPreviewImgs);
    //多样化价格
    if ($("#openVaryPrice").is(":checked")) {
        trdobj.openVaryPrice = true;
        var varyPrice = [];
        var price = document.getElementsByName("price");
        var limitAmount = document.getElementsByName("limitAmount");
        for(i=0;i<price.length;i++) {
            if (!numberReg2.test(price[i].value) || !numberReg1.test(limitAmount[i].value)) {
                orSave = false;
                $("#warn-vary-price").text("多样化价格数据不正确");
            } else $("#warn-vary-price").text("");
            varyPrice.push(price[i].value);
            varyPrice.push(limitAmount[i].value);
        }
        trdobj.varyPrice = varyPrice.toString();
    }
    else {
        trdobj.openVaryPrice = false;
        trdobj.varyPrice = "";
    }
    $("<td>").html('<input type="radio" name="orMasterInv" checked="checked" class="master-radio"/>').appendTo(trd);
    var count = 0;
    //行数据,其余的隐藏
    for(var item in trdobj){
//        if (count==0||count==1||count==4||count==13)
        $("<td>").html(trdobj[item]).appendTo(trd);
//        else $("<td style='display:none;'>").html(trdobj[item]).appendTo(trd);
        count++;
    }
    $("<td class='trEdit'>").html("<span onclick='ShowModal(this)'> 编辑 </span>  <span class='delTr'> 删除 </span>").appendTo(trd);
    console.log(trd.html());
    //点击的是添加规格按钮
    if($(option).hasClass("add-guige")){
        var trh = $("<tr>");
        $("<th>").html("设为主商品").appendTo(trh);
        //表头,其余的表头项隐藏
        $(".thval").each(function(){
            var thName = $(this).html();
//            if (thName=="颜色"||thName=="尺寸"||thName=="现价"||thName=="库存区域")
            $("<th>").html(thName).appendTo(trh);
//            else $("<th style='display:none;'>").html(thName).appendTo(trh);
        });
        $("<th>").html("修改").appendTo(trh);
        sharedObject.trh = trh;
    }
    sharedObject.trd = trd;
    sharedObject.index = $("#index").val();
    console.log(orSave);
    if (true) {
        if (window.showModalDialog) {
            window.returnValue = sharedObject;
        }
        else {
            window.opener.UpdateFields (sharedObject);
        }
    }
}

/**** 保存并关闭按钮功能 ****/
function saveClose() {
    saveCurr();
    window.close();
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
//                alert(["图片大小是: width:"+image.width+", height:"+image.height]);
                aImg.width = image.width;
                aImg.height = image.height;
            }
        })(img);
        reader.readAsDataURL(file);
    }

    function upload(thumb, file, id) {
        document.getElementById("mask").style.display = 'block';
        var formdata = new FormData();
        formdata.append("photo", file);
        formdata.append("params", "minify");
        var http = new XMLHttpRequest();
        var url = window.uploadURL+"/upload";
        http.open("POST", url, true);

        http.onreadystatechange = function() {
            if (http.readyState == 4 && http.status == 200) {
                var data = JSON.parse(http.responseText);
                document.getElementById("mask").style.display = 'none';
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

    /** 点击关闭窗口按钮,窗口关闭 **/
    $("#return").on("click", function() {
        window.close();
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

    /*********切换显示/隐藏table 多样化价格*********/
    $(".YN").click(function(){
        $(".guige").toggleClass("block");
    });
    /*********更改文本*********/
//    $(document).on("click",".guige td:not(:last-of-type)",function(e){
//        changeText(e,this);
//    });

    /**** 颜色尺寸设置 ****/
    $(document).on("click","input[name=color]",function(){
        $(".color").find(":input").removeClass("vals");
        $(this).addClass("vals");
    });
    $(document).on("click","input[name=size]",function(){
        $(".size").find(":input").removeClass("vals");
        $(this).addClass("vals");
    });
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

