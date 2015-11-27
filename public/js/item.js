$(function(){

    /** 图片放大和关闭的功能 **/
    $(document).on("click", ".main-img", function(e) {
		$(".goods-img-bg").css({
			"height": $(window).height(),
			"display": "block"
		});
		$(".goods-img").css("left", ($(window).width() - 1200) / 2);
		$(this).clone().appendTo($(".goods-img")).css({
			"width": "100%",
			"height":"800px",
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

//    $(document).on('click','.fdel .big',function() {
//        $(this).parents(".fdel").remove();
//    });

    /* 一级类别改变重新加载二级类别*/
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
        img.file = file;
        thumb.appendChild(img);
        thumb.appendChild(button);
        gallery.appendChild(thumb);

         //添加商品主图主图后,添加图片按钮置为不可点击且颜色变为灰色
         if (id.indexOf("M")>=0&&document.getElementById(galleryId).getElementsByTagName("div").length>0) {
             //$("#"+id).attr({"disabled":true});
             //document.getElementById('masterImgAdd'+id).style.background="#ccc";
             $("#"+id).parent().css("display","none");
         }

         //添加主题宣传图后,添加图片按钮置为不可点击且颜色变为灰色
         if (document.getElementById("galleryT").getElementsByTagName("div").length>0) {
             $("#T").attr({"disabled":true});
             document.getElementById('masterThImgAdd').style.background="#ccc";
         }

         //商品预览图最多为6张
         if (id.indexOf("P")>=0 && document.getElementById("gallery"+id).getElementsByTagName("div").length==6) {
             $("#"+id).attr({"disabled":true});
             document.getElementById("preImgAdd"+id).style.background="#ccc";
         }

         upload(thumb, file, id);

        // Using FileReader to display the image content
        var reader = new FileReader();
        reader.onload = (function(aImg) {
            return function(e) {
                aImg.src = e.target.result;
            }
        })(img);
        reader.readAsDataURL(file);
    }

    function upload(thumb, file, id) {
        var formdata = new FormData();
        formdata.append("photo", file);
        formdata.append("params", "minify");
        var http = new XMLHttpRequest();
        var url = "http://172.28.3.18:3008/upload";
        http.open("POST", url, true);
        http.onreadystatechange = function() {
            if (http.readyState == 4 && http.status == 200) {
                var data = JSON.parse(http.responseText);
                var input= document.createElement("input");
                imgName = data.imgid;
                input.id = imgName.substr(0,imgName.lastIndexOf("."));
                input.type="hidden";
                input.name=data.imgid;
                input.value=data.path;
                thumb.appendChild(input);
                alert(data.message);
            }
        }
        http.send(formdata);
    }

    /** 上传图片操作,主图,点击移除的操作 **/
    $(document).on('click','.list-img .close',function(){
        var id = $(this).parent().parent().attr("id");
        //没有商品主图时,上传按钮置为可点击且颜色恢复
        if (document.getElementById(id).getElementsByTagName("div").length==1) {
            id = id.substring(7,9);
            //$("#"+id).removeAttr("disabled");
            //document.getElementById('masterImgAdd'+id).style.background="#00B7EE";
            $("#"+id).parent().css("display","inline-block");
        }
        $(this).parent().remove();
    });

    /** 主题宣传图,点击移除的操作 **/
    $(document).on('click','.master-img .close',function(){
        //没有主题宣传图时,上传按钮置为可点击且颜色恢复
        if (document.getElementById("galleryT").getElementsByTagName("div").length==1) {
            $("#T").removeAttr("disabled");
            document.getElementById('masterThImgAdd').style.background="#00B7EE";
        }
        $(this).parent().remove();
    });

    /** 预览图 点击移除的操作 **/
    $(document).on('click','.preview-img .close',function(){
        var id = $(this).parent().parent().attr("id");
        //商品预览图小于6张时恢复上传功能
        if (document.getElementById(id).getElementsByTagName("div").length==6) {
            id = id.substring(7,9);
            $("#"+id).removeAttr("disabled");
            document.getElementById('preImgAdd'+id).style.background="#00B7EE";
        }
        $(this).parent().remove();
    });

    /** 详细图 点击移除的操作 **/
    $(document).on('click','.detail-img .close',function(){
        $(this).parent().remove();
    });

    /** 添加一条库存信息 **/
    $(".add-goods").click(function(){
        var inventory = document.getElementById("inventory");
        var trs = inventory.getElementsByTagName("tr");
        var index = trs.length;
        var tds = trs[index-1].getElementsByTagName("td");
        var i = 1;
        //有空值不能添加
        for(i=1;i<tds.length;i++) {
            if (i <=8 && tds[i].getElementsByTagName("input")[0].value=="") break;
            if (i ==11 && trs[index-1].getElementsByClassName("list-img")[0].getElementsByTagName("div").length==1) break;
            if (i ==12 && trs[index-1].getElementsByClassName("preview-img")[0].getElementsByTagName("div").length==1) break;
        }
        if (i==13){
            var tr =  document.createElement("tr");

            var td1 = document.createElement("td");
            var input1 = document.createElement("input");
            input1.type = "radio";
            input1.name = "orMasterInv";
            td1.appendChild(input1);

            var td2 = document.createElement("td");
            var input2 = document.createElement("input");
            input2.type = "text";
            input2.name = "itemColor";
            td2.appendChild(input2);

            var td3 = document.createElement("td");
            var input3 = document.createElement("input");
            input3.type = "text";
            input3.name = "itemSize";
            td3.appendChild(input3);

            var td4 = document.createElement("td");
            var input4 = document.createElement("input");
            input4.type = "text";
            input4.name = "amount";
            td4.appendChild(input4);

            var td5 = document.createElement("td");
            var input5 = document.createElement("input");
            input5.type = "text";
            input5.name = "itemPrice";
            td5.appendChild(input5);

            var td6 = document.createElement("td");
            var input6 = document.createElement("input");
            input6.type = "text";
            input6.name = "itemSrcPrice";
            td6.appendChild(input6);

            var td7 = document.createElement("td");
            var input7 = document.createElement("input");
            input7.type = "text";
            input7.name = "itemCostPrice";
            td7.appendChild(input7);

            var td8 = document.createElement("td");
            var input8 = document.createElement("input");
            input8.type = "text";
            input8.name = "itemDiscount";
            td8.appendChild(input8);

            var td9 = document.createElement("td");
            var input9 = document.createElement("input");
            input9.type = "text";
            input9.name = "restrictAmount";
            td9.appendChild(input9);

            var td10 = document.createElement("td");
            var inputR1 = document.createElement("input");
            inputR1.type = "radio";
            inputR1.name = "invArea"+index;
            inputR1.value = "B";
            var divB = document.createElement("div");
            divB.classList.add('area');
            divB.innerText="保税区";
            var inputR2 = document.createElement("input");
            inputR2.type = "radio";
            inputR2.name = "invArea"+index;
            inputR2.value = "Z";
            inputR2.checked = "checked";
            var divZ = document.createElement("div");
            divZ.classList.add('area');
            divZ.innerText="韩国直邮";
            td10.appendChild(inputR1);
            td10.appendChild(divB);
            td10.appendChild(inputR2);
            td10.appendChild(divZ);

            var td11 = document.createElement("td");
            td11.classList.add('list-img');
            var divM = document.createElement("div");
            divM.id = "galleryM"+index;
            var spanAdd = document.createElement("span");
            spanAdd.classList.add("add");
            spanAdd.id = "masterImgAddM" + index;
            spanAdd.innerText = "+";
            var inputF = document.createElement("input");
            inputF.type = "file";
            inputF.id = "M"+index;
            inputF.classList.add("hidden1");
            inputF.setAttribute("accept", "image/gif, image/jpeg, image/webp, image/png");
            spanAdd.appendChild(inputF);
            td11.appendChild(divM);
            td11.appendChild(spanAdd);

            var td12 = document.createElement("td");
            td12.width="146px";
            td12.classList.add('preview-img');
            var divPre = document.createElement("div");
            divPre.id = "galleryP"+index;
            var spanAdd = document.createElement("div");
            spanAdd.classList.add("add");
            spanAdd.id = "preImgAddP" + index;
            spanAdd.innerText = "+";
            var inputF = document.createElement("input");
            inputF.type = "file";
            inputF.id = "P" + index;
            inputF.classList.add("hidden1");
            inputF.setAttribute("accept", "image/gif, image/jpeg, image/webp, image/png");
            spanAdd.appendChild(inputF);
            td12.appendChild(divPre);
            td12.appendChild(spanAdd);

            var td13 = document.createElement("td");
            td13.classList.add("del");
            td13.innerText = "删除";

            tr.appendChild(td1);
            tr.appendChild(td2);
            tr.appendChild(td3);
            tr.appendChild(td4);
            tr.appendChild(td5);
            tr.appendChild(td6);
            tr.appendChild(td7);
            tr.appendChild(td8);
            tr.appendChild(td9);
            tr.appendChild(td10);
            tr.appendChild(td11);
            tr.appendChild(td12);
            tr.appendChild(td13);
            inventory.appendChild(tr);
        }
    });

    /** 删除一条库存信息 **/
    $(".inv").delegate(".del","click",function(){
        if (document.getElementById("inventory").getElementsByTagName("tr").length>2) {
            $(this).parent().remove();
        }
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

    /** 添加属性的操作 **/
    $(document).on('click','.feature .add',function(){
        var fea = "";
        var tabFea = document.getElementById("tabFea");
        var attrN = document.getElementsByName("attrN");
        var attrV = document.getElementsByName("attrV");
        var len = attrN.length;
        if (document.getElementById("tabFea").getElementsByTagName("tr").length>1 && attrN[len-1].value!="" && attrV[len-1].value!="") {
            $("<tr>").html('<td><input type="text" name="attrN"/></td><td><input type="text" name="attrV"/></td><td class="del">'+$('#del').val()+'</td>').appendTo($(".feature"));
        }
    });

    /** 删除属性的操作 **/
    $(".feature").delegate(".del","click",function(){
        if (document.getElementById("tabFea").getElementsByTagName("tr").length>2) {
            $(this).parent().remove();
        }
    });

    /** 数据提交 **/
    $("#submitProducts").click(function(){
        var isPost = true;
        var numberReg1 =    /^-?\d+$/;   //正整数
        var numberReg2 =    /^-?\d+\.?\d{0,2}$/;   //整数或小数
        var item = new Object();
        var inventories = [];
        var itemData = new Object();
        //必填项不能有空值
        if ( $("#categorySubSelect").val()=="" || $("#bandSelect").val()=="" || $("#productName").val=="" || $("#supplyMerch").val=="" || $("#itemTitle").val==""
            || $("#onShelvesAt").val=="" || $("#offShelvesAt").val=="" ) {
            isPost=false;
            alert("必填项不能为空");
        }
        var cateId = $("#categorySubSelect").val();
        var brandId = $("#bandSelect").val();
        var supplyMerch = $("#supplyMerch").val();
        var itemTitle = $("#itemTitle").val();
        var onShelvesAt = $("#onShelvesAt").val();
        var offShelvesAt = $("#offShelvesAt").val();
        var itemNotice = $("#itemNotice").val();
        var amount = document.getElementsByName("amount");
        var itemPrice = document.getElementsByName("itemPrice");
        var itemSrcPrice = document.getElementsByName("itemSrcPrice");
        var itemCostPrice = document.getElementsByName("itemCostPrice");
        var itemDiscount = document.getElementsByName("itemDiscount");
        var restrictAmount = document.getElementsByName("restrictAmount");
        if (onShelvesAt >= offShelvesAt) {
           isPost = false;
           $("#warn-date").html("日期不正确!");
        } else $("#warn-date").html("");
        var inventory = document.getElementById("inventory");
        var trs = inventory.getElementsByTagName("tr");
        var tds = trs[trs.length-1].getElementsByTagName("td");
        //验证输入的数量和价格是否符合规则
        for(i=1;i<=8;i++) {
            if (tds[i].getElementsByTagName("input")[0].value=="") {
                isPost = false;
                $("#warn-inv").html("库存信息不能有空值!");
                break;
            } else $("#warn-inv").html("");
        }
        for(i=0;i<amount.length;i++){
            if (!numberReg2.test(amount[i].value) || !numberReg2.test(itemPrice[i].value) || !numberReg2.test(itemSrcPrice[i].value)
                || !numberReg2.test(itemCostPrice[i].value) || !numberReg2.test(itemDiscount[i].value) || !numberReg2.test(restrictAmount[i].value)) {
                isPost = false;
                $("#warn-num").text("输入数据不合法!");
                break;
            } else $("#warn-num").text("");
        }
        if (tds[10].getElementsByTagName("div").length<2) {
            isPost = false;
            $("#warn-mas").html("请上传商品主图");
        } else $("#warn-mas").html("");
        if (tds[11].getElementsByTagName("div").length<2) {
            isPost = false;
            $("#warn-pre").html("请上传商品预览图");
        } else $("#warn-pre").html("");

        var orMasterInv = document.getElementsByName("orMasterInv");
        for(m=0;m<orMasterInv.length;m++) {
            if (orMasterInv[m].checked==true) break;
            if (m==orMasterInv.length-1) {
                isPost = false;
                $("#warn-cho").text("请选择主商品!");
            } else $("#warn-cho").text("");
        }

        //优惠信息
        var publicity = [];
        var publicityTab = document.getElementById("publicityTab");
        var pubtr = publicityTab.getElementsByTagName("tr");
        if (pubtr.length==1) {
            isPost = false;
            $("#warn-pub").text("请录入优惠信息!");
        } else $("#warn-pub").text("");
        for(i=1;i<pubtr.length;i++) {
            publicity.push(pubtr[i].getElementsByTagName("td")[0].innerText);
        }
        var itemMasterImg = "";
        var galleryT = document.getElementById("galleryT");
        var masterThImgLen = galleryT.getElementsByTagName("img").length;
        if (masterThImgLen<1) {
            isPost = false;
            $("#warn-masthimg").text("请上传主题宣传图!");
        } else {
            $("#warn-masthimg").text("");
        }
        //修改页面图片来源为读取商品的信息
        if (galleryT.getElementsByTagName("input").length==0) {
            itemMasterImg = galleryT.getElementsByTagName("img")[0].src;
            //截取图片的url
            itemMasterImg  = itemMasterImg.substring(itemMasterImg.indexOf('/',itemMasterImg.indexOf('/')+2));
        }
        else itemMasterImg = galleryT.getElementsByTagName("input")[0].value;
        var galleryD = document.getElementById("galleryD");
        var detailImgLen = galleryD.getElementsByTagName("img").length;
        if (detailImgLen<1) {
            isPost = false;
            $("#warn-detailimg").text("请上传商品详细图!");
        } else {
            $("#warn-detailimg").text("");
        }
        var itemDetailImgs = [];
        var  det = "";
        if (galleryD.getElementsByTagName("input").length==0) {
            det = galleryD.getElementsByTagName("img");
            var detV = "";
            for(i=0;i<det.length;i++) {
                detV = det[i].src;
                detV = detV.substring(detV.indexOf('/',detV.indexOf('/')+2));
            }
            itemDetailImgs.push(detV);
        }
        else {
            det = galleryD.getElementsByTagName("input");
            for(i=0;i<detInps.length;i++) {
                itemDetailImgs.push(detInps[i].value);
            }
        }
        console.log(itemDetailImgs);
        //遍历所有属性及属性值累加到隐藏域features中且属性名或值不能为空
        var itemFeatures = {};
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
            var itemPreviewImgs = [];
            var orMasterInv = false;
            if (tds[0].getElementsByTagName("input")[0].checked==true)    orMasterInv = true;
            var itemColor = tds[1].getElementsByTagName("input")[0].value;
            var itemSize = tds[2].getElementsByTagName("input")[0].value;
            var amount = tds[3].getElementsByTagName("input")[0].value;
            var itemPrice = tds[4].getElementsByTagName("input")[0].value;
            var itemSrcPrice = tds[5].getElementsByTagName("input")[0].value;
            var itemCostPrice = tds[6].getElementsByTagName("input")[0].value;
            var itemDiscount = tds[7].getElementsByTagName("input")[0].value;
            var restrictAmount = tds[8].getElementsByTagName("input")[0].value;
            var invArea = "B";
            if (tds[9].getElementsByTagName("input")[1].checked==true)   invArea = "Z";
            var invImg = "";
            if (document.getElementById("galleryM"+i).getElementsByTagName("input").length==0) {
                invImg = document.getElementById("galleryM"+i).getElementsByTagName("img")[0].src;
                invImg  = invImg.substring(invImg.indexOf('/',invImg.indexOf('/')+2));
            }
            else invImg = document.getElementById("galleryM"+i).getElementsByTagName("input")[0].value;
            imgs = document.getElementById("galleryP"+i).getElementsByTagName("input");
            if (imgs.length==0) {
                imgs = document.getElementById("galleryP"+i).getElementsByTagName("img");
                var imgsV = "";
                 for(j=0;j<imgs.length;j++) {
                    imgsV = imgs[i].src;
                    imgsV = imgsV.substring(imgsV.indexOf('/',imgsV.indexOf('/')+2));
                    itemPreviewImgs.push(imgsV);
                 }
            }
            else {
                for(j=0;j<imgs.length;j++) {
                    itemPreviewImgs.push(imgs[j].value);
                }
            }
            console.log(itemPreviewImgs);
            //拼装成一条数据
            var inventory = new Object();
            inventory.orMasterInv = orMasterInv;
            inventory.itemColor = itemColor;
            inventory.itemSize = itemSize;
            inventory.amount = amount;
            inventory.itemPrice = itemPrice;
            inventory.itemPrice = itemPrice;
            inventory.itemSrcPrice = itemSrcPrice;
            inventory.itemCostPrice = itemCostPrice;
            inventory.itemDiscount = itemDiscount;
            inventory.restrictAmount = restrictAmount;
            inventory.invArea = invArea;
            inventory.invImg = invImg;
            inventory.itemPreviewImgs = itemPreviewImgs;
            inventories.push(inventory);
        }
        item.cateId = cateId;
        item.brandId = brandId;
        item.supplyMerch = supplyMerch;
        item.itemTitle = itemTitle;
        item.onShelvesAt = onShelvesAt;
        item.offShelvesAt = offShelvesAt;
        item.itemNotice = itemNotice;
        item.publicity = publicity;
        item.itemMasterImg = itemMasterImg;
        item.itemDetailImgs = itemDetailImgs;
        item.itemFeatures = itemFeatures;

        itemData.item = item;
        itemData.inventories = inventories;

        console.log(JSON.stringify(item));
        console.log(JSON.stringify(inventories));
        console.log(JSON.stringify(itemData));
        console.log(isPost);
        if (isPost) {
            $.ajax({
                type :  "POST",
                url : "/comm/itemInsert",
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
                    $('.usercenter-option > .user-state').css('background-position', '20px -174px');
                    if (window.lang = 'cn') {
                        $('#js-userinfo-error').text('保存成功').css('color', '#2fa900');
                        $('.usercenter-option > .user-state').text('未更改');
                    } else {
                        $('#js-userinfo-error').text('Save success');
                        $('.usercenter-option > .user-state').text('Unchanged');
                    }
                    setTimeout("$('#js-userinfo-error').text('').css('color','#c00')", 2000);
                    setTimeout("location.href='/"+window.lang+"/comm/search'", 3000);
                }
            });
        }
    });

});

