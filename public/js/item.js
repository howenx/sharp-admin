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

    /** 数据改变的提示 **/
    $(document).on('change', '.form-data-area', function() {
        $('.usercenter-option > .user-state').css('background-position', '20px -73px');
        $('.usercenter-option > .user-state').text('已更改');
    });

	/** 点击返回按钮,返回到列表查询页面 **/
	$("#return").on("click", function() {
	    location.href="/"+window.lang+"/comm/search";
	});

    /** 一级类别改变重新加载二级类别 **/
    $('#categorySelect').change(function() {
        var pcid = $("#categorySelect").val();
        $.ajax({
            type: "get",
            data: "pcid="+ pcid,
            url: "/getSubcates",
            success: function(data) {
                if (typeof data != undefined && data.length > 0 && data != null) {
                    $('#categorySubSelect').empty();
                    $('#categorySubSelect').append('<option value=""></option>');
                    $.each(data, function (i, item) {
                             $('#categorySubSelect').append("<option value=" + item.cateId + ">" + item.cateNm + "</option>");
                    });
                }
                else {
                    $("#categorySubSelect").empty();
                }
            }
        });
    });

    //修改页面二级类别列表加载数据
    if ($("#itemId").val() != "") {
        var pcid = $("#categorySelect").val();
        $.ajax({
            type: "get",
            data: "pcid="+ pcid,
            url: "/getSubcates",
            success: function(data) {
                if (typeof data != undefined && data.length > 0 && data != null) {
                    $('#categorySubSelect').empty();
                    $.each(data, function (i, item) {
                            if (item.cateId==$("#catesId").val()) {
                                $('#categorySubSelect').append("<option selected value=" + item.cateId + ">" + item.cateNm + "</option>");
                            }
                            if (item.cateId!=$("#catesId").val()) {
                                $('#categorySubSelect').append("<option value=" + item.cateId + ">" + item.cateNm + "</option>");
                            }
                    });
                }
                else {
                    $("#categorySubSelect").empty();
                }
            }
        });
    }

    var date = new Date();
    var dateStr = ''+date.getFullYear()+(date.getMonth()+1>=10?date.getMonth()+1:'0'+(date.getMonth()+1))+(date.getDate()>=10?date.getDate():'0'+date.getDate());

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

         upload(thumb, file, id);

        // Using FileReader to display the image content
        var reader = new FileReader();
        reader.onload = (function(aImg) {
            return function(e) {
                aImg.src = e.target.result;
                var image = new Image();
                image.src = e.target.result;
                aImg.width = image.width;
                aImg.height = image.height;
            }
        })(img);
        reader.readAsDataURL(file);
    }


    function upload(thumb, file, id) {
        document.getElementById("mask").style.display = 'block';
        var formdata = new FormData();
        var prefix = "item/photo/" + dateStr + "/";
        formdata.append("photo", file);
        formdata.append("params", "minify");
        formdata.append("prefix", prefix);
        $.ajax({
            url: window.uploadURL + "/upload",
            data: formdata,
            cache: false,
            contentType: false,
            processData: false,
            type: 'POST',
            success: function(data) {
                thumb.getElementsByTagName("img")[0].src = data.oss_prefix+data.oss_url;
//                console.log("data.oss_prefix:"+data.oss_prefix);
//                console.log("data.oss_url:"+data.oss_url);
//                console.log("data.path:"+data.path);
//                console.log("data.imgid:"+data.imgid);
//                console.log("data.minify_url:"+data.minify_url);
                if (id.indexOf("D")>=0) {
                    $.ajax({
                        url: window.uploadURL + "/split/file",
                        data: {
                            filename: '' + data.oss_url,
                            prefix:'' + prefix
                        },
                        type: 'post',
                        success: function(data2) {
//                            console.log("data2.oss_prefix:"+data2.oss_prefix);
//                            console.log("data2.oss_url:"+data2.oss_url);
//                            console.log("data2.minify_url:"+data.minify_url);
                            var array_oss_url = JSON.parse(data2.oss_url);
                            var path = data2.oss_prefix.split(window.imageUrl)[1];
                            var inpV = path + array_oss_url[0] + "," + path + array_oss_url[1] + "," + path + array_oss_url[2];
                            var input = document.createElement("input");
                            input.type="hidden";
                            input.value = inpV;
                            thumb.appendChild(input);
                            document.getElementById("mask").style.display = 'none';
                        }
                    });
                }

            }
        });
    }

    $(document).on('click','.delTr',function(){
        if (window.confirm("确定删除吗?")) {
            $(this).parent().parent().remove();
        }
    });
    /** 详细图 点击移除的操作 **/
    $(document).on('click','.detail-img .close',function(){
        $(this).parent().remove();
    });

    $(".pic").click(function(){
        $(".pic-info").css('display','block');
        $(".edit-info").css('display','none');
    });

    $(".edit").click(function(){
        $(".pic-info").css('display','none');
        $(".edit-info").css('display','block');
    });

    /** 添加优惠信息 **/
    $(document).on('click','.add1',function(){
        var publicity = $("#publicity").val();
        if (publicity != "") {
            $("#publicityTab").append("<tr><td>"+publicity+"</td><td class='del'>删除</td></tr>");
            $("#publicity").val("");
        }
    });

    /** 删除一条优惠信息 **/
    $(".pub").delegate(".del","click",function(){
        if (document.getElementById("publicityTab").getElementsByTagName("tr").length>1) {
            $(this).parent().remove();
        }
    });

    /** 添加参数的操作 **/
    $(document).on('click','.feature .add',function(){
        var fea = "";
        var tabFea = document.getElementById("tabFea");
        var attrN = document.getElementsByName("attrN");
        var attrV = document.getElementsByName("attrV");
        var len = attrN.length;
        if (document.getElementById("tabFea").getElementsByTagName("tr").length>1 && attrN[len-1].value!="" && attrV[len-1].value!="") {
            $("<tr>").html('<td><input type="text" name="attrN" class="form-data-area"/></td><td><input type="text" name="attrV" class="form-data-area"/></td><td class="del">删除</td>').appendTo($(".feature"));
        }
    });

    /** 删除参数的操作 **/
    $(".feature").delegate(".del","click",function(){
        if (document.getElementById("tabFea").getElementsByTagName("tr").length>2) {
            $(this).parent().remove();
        }
    });

    /** 数据提交 **/
    $(".saveItem").click(function(){
        var divId = window.event.srcElement.id;
        var isPost = true;
        var numberReg1 =    /^-?\d+$/;          //正整数
        var numberReg2 =    /^-?\d+\.?\d{0,2}$/;//整数或小数
        var numberReg3 =    /[^/d]/g;           //数字
        var numberReg4 =    /^[0-9a-zA-Z]*$/g;  //字母和数字
        var item = new Object();
        var inventories = [];
        var itemData = new Object();
        var cateId = "";
        if($("#categorySubSelect").text()=="") cateId=$("#categorySelect").val();
        else cateId=$("#categorySubSelect").val();
        var brandId = $("#bandSelect").val();
        var itemTitle = $("#itemTitle").val();
        var supplyMerch = $("#supplyMerch").val();
        var itemFeatures = {};
        var publicity = [];
        var itemDetailImgs = [];
        var itemNotice = $("#itemNotice").val();
        var itemDetail = UE.getEditor('editor').getContent();
        //必填项不能有空值
        if ( cateId=="" || brandId=="" || itemTitle=="" || supplyMerch=="") {
            isPost=false;
            alert("必填项不能为空");
        }
        var inventoryTab = document.getElementById("inventory");
        var trs = inventoryTab.getElementsByTagName("tr");
        if (trs.length==0) {
            isPost = false;
            alert("请添加商品规格!");
        }
        //选择主商品
        var orMasterInv = document.getElementsByName("orMasterInv");
        for(m=0;m<orMasterInv.length;m++) {
            if (orMasterInv[m].checked==true) break;
            if (m==orMasterInv.length-1) {
                isPost = false;
                $("#warn-choice").text("请选择主商品!");
            }
            else $("#warn-choice").text("");
        }
        //优惠信息
        var publicityTab = document.getElementById("publicityTab");
        var pubtr = publicityTab.getElementsByTagName("tr");
        if (pubtr.length==1) {
            isPost = false;
            $("#warn-pub").text("请录入优惠信息!");
        } else $("#warn-pub").text("");
        for(i=1;i<pubtr.length;i++) {
            publicity.push(pubtr[i].getElementsByTagName("td")[0].innerText);
        }
        //商品详细图
        var galleryD = document.getElementById("galleryD");
        var detailImg = galleryD.getElementsByTagName("div");
        if ($(".pic").is(":checked")) {
           if (detailImg.length<1) {
               isPost = false;
               $("#warn-detailImg").text("请上传商品详细图!");
           } else {
               $("#warn-detailImg").text("");
           }
           for(i=0;i<detailImg.length;i++) {
               var spanArr = [];
               var spanD = detailImg[i].getElementsByTagName("span");
               var inpD = detailImg[i].getElementsByTagName("input");
               var imgD = detailImg[i].getElementsByTagName("img");
               //图片来源为读取商品图片/上传新图片
               if(inpD.length==0 && imgD.length!=0) {
                   var imgArr = [];
                   for(d=0;d<imgD.length;d++) {
                       var detV  = imgD[d].src;
                       detV = detV.split(imageUrl)[1];
                       imgArr.push(detV);
                   }
                   itemDetailImgs.push(imgArr);
               }
               if(inpD.length!=0 && imgD.length!=0) {
                   var inpDArr = inpD[0].value.split(",");
                   for(s=0;s<inpDArr.length;s++){
                       spanArr.push(inpDArr[s]);
                   }
                   itemDetailImgs.push(spanArr);
               }
           }
//           console.log(itemDetailImgs.toString());
        }
        if ($(".edit").is(":checked")) {
            if (itemDetail=="") {
                isPost = false;
                $("#warn-edit").text("请输入商品详情!");
            } else $("#warn-edit").text("");
        }

        //遍历所有参数及参数值,且不能有空值
        var tabFea = document.getElementById("tabFea");
        var attrN = document.getElementsByName("attrN");
        var attrV = document.getElementsByName("attrV");
        for(i=0; i<attrN.length; i++) {
            if (attrN[i].value=="" || attrV[i].value=="") {
                isPost = false;
                if (window.lang=="cn") $("#warn-attr").text("属性名或值不能有空");
                else $("#warn-attr").text("attribute or attribute value can't be value");
            } else $("#warn-attr").text("");
            itemFeatures[attrN[i].value] = attrV[i].value;
        }
        //拼装inventories数据
        for(i=1;i<trs.length;i++) {
            var tds = trs[i].getElementsByTagName("td");
            var orMasterInv = false;
            if (tds[0].getElementsByTagName("input")[0].checked==true)    orMasterInv = true;
            var itemColor = tds[1].innerHTML;
            var itemSize = tds[2].innerHTML;
            var invCode = tds[3].innerHTML;
            var state = tds[4].innerHTML;
            var startAt = tds[5].innerHTML;
            var endAt = tds[6].innerHTML;
            var itemPrice = tds[7].innerHTML;
            var itemSrcPrice = tds[8].innerHTML;
            var itemCostPrice = tds[9].innerHTML;
            var itemDiscount = tds[10].innerHTML;
            var invWeight = tds[11].innerHTML;
            var restrictAmount = tds[12].innerHTML;
            var amount = tds[13].innerHTML;
            var restAmount = tds[14].innerHTML;
            var carriageModelCode = tds[15].innerHTML;
            var invArea = tds[16].innerHTML;
            var invCustoms = tds[17].innerHTML;
            var postalTaxRate = tds[19].innerHTML;
            var postalTaxCode = tds[20].innerHTML;
            var recordCode = tds[21].innerHTML;
            var invImg = tds[22].innerHTML;
            var itemPreviewImgs = tds[23].innerHTML;
            var orVaryPrice = tds[24].innerHTML;
            var invId = tds[26].innerHTML;
            //拼装成一条数据
            var invData = new Object();
            var inventory = new Object();
            var varyPrices = [];
            inventory.orMasterInv = orMasterInv;
            inventory.itemColor = itemColor;
            inventory.itemSize = itemSize;
            inventory.invCode = invCode;
            inventory.state = state;
            inventory.startAt = startAt;
            inventory.endAt = endAt;
            inventory.itemPrice = itemPrice;
            inventory.itemSrcPrice = itemSrcPrice;
            inventory.itemCostPrice = itemCostPrice;
            inventory.itemDiscount = itemDiscount;
            inventory.invWeight = invWeight;
            inventory.restrictAmount = restrictAmount;
            inventory.amount = amount;
            inventory.restAmount = restAmount;
            inventory.carriageModelCode = carriageModelCode;
            inventory.invArea = invArea;
            inventory.invCustoms = invCustoms;
            inventory.postalTaxRate = postalTaxRate;
            inventory.postalTaxCode = postalTaxCode;
            if(postalTaxRate=="") {inventory.postalTaxRate = null;}
            if(postalTaxCode=="") {inventory.postalTaxCode = null;}
            inventory.recordCode = recordCode;
            inventory.invImg = invImg;
            inventory.itemPreviewImgs = itemPreviewImgs;
            inventory.orVaryPrice = orVaryPrice;
            if (invId!=null && invId !=""  && divId =="submitItem") {
                inventory.id = invId;
            }
            invData.inventory = inventory;
            if (orVaryPrice=="true") {
                var vp_arr = tds[25].innerHTML.split(",");
                for(v=0;v<vp_arr.length;v++) {
                    if (v%4==0) {
                        var varyPrice = new Object();
                        if (vp_arr[v]!="" && vp_arr[v]!=null && divId =="submitItem") {
                            varyPrice.id = vp_arr[v];
                        }
                        varyPrice.status = vp_arr[v+1];
                        varyPrice.price = vp_arr[v+2];
                        varyPrice.limitAmount = vp_arr[v+3];
                        varyPrices.push(varyPrice);
                    }
                }
                invData.varyPrices = varyPrices;
            }
            inventories.push(invData);
        }
        item.cateId = cateId;
        item.brandId = brandId;
        item.supplyMerch = supplyMerch;
        item.itemTitle = itemTitle;
        item.publicity = publicity;
        item.itemFeatures = itemFeatures;
        if ($(".pic").is(":checked")) {
            item.itemDetailImgs = itemDetailImgs;
            item.itemNotice = itemNotice;
//            item.itemDetail = null;
        }
        if ($(".edit").is(":checked")) {
//            item.itemDetailImgs = null;
//            item.itemNotice = null;
            item.itemDetail = itemDetail;
        }

        if ($("#itemId").val()!="" && $("#itemId").val()!=null && divId=="submitItem") {
            item.id = $("#itemId").val();
        }

        itemData.item = item;
        itemData.inventories = inventories;

        console.log(JSON.stringify(itemData));
//        console.log(JSON.stringify(item));
//        console.log(JSON.stringify(inventories));
        console.log(isPost);
        if (isPost) {
            $.ajax({
                type :  "POST",
                url : "/comm/itemSave",
                contentType: "application/json; charset=utf-8",
                data : JSON.stringify(itemData),
                error : function(request) {
                    if (window.lang = 'cn') {
                        $('#js-userinfo-error').text('保存失败');
                    } else {
                        $('#js-userinfo-error').text('Save error');
                    }
                    setTimeout("$('#js-userinfo-error').text('')", 2000);
                },
                success: function(data) {
                    if (data!=""&&data!=null) {
                        $('.usercenter-option > .user-state').css('background-position', '20px -174px');
                        $('.usercenter-option > .user-state').text('未更改');
                        $('#js-userinfo-error').text('保存成功').css('color', '#2fa900');
                        //商品更新, 成功后返回到列表查询页面
                        if($("#itemId").val() != "") {
                            setTimeout("location.href='/"+window.lang+"/comm/search'", 3000);
                        }
                        //商品录入, 成功后返回到商品录入页面
                        else {
                            setTimeout("location.href='/"+window.lang+"/comm/add'", 3000);
                        }
                    }
                    else {
                        $('#js-userinfo-error').text('保存失败');
                    }
                    setTimeout("$('#js-userinfo-error').text('').css('color','#c00')", 2000);
                }
            });
        }
    });

});

