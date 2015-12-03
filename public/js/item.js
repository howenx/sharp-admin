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

    /** 修改主图和预览图所在div的id值,修改页面使用 **/
    if ($("#itemId").val() != "") {
        var upInv = document.getElementById("inventory");
        var uptrs = inventory.getElementsByTagName("tr");
        for(i=1;i<uptrs.length;i++) {
            var uptds = uptrs[i].getElementsByTagName("td");
            uptds[12].getElementsByTagName("div")[0].id = "galleryM"+i;
            uptds[12].getElementsByTagName("span")[0].id = "masterImgAddM"+i;
            uptds[12].getElementsByTagName("input")[0].id = "M"+i;
            uptds[13].getElementsByTagName("div")[0].id = "galleryP"+i;
            uptds[13].getElementsByTagName("span")[0].id = "preImgAddP"+i;
            uptds[13].getElementsByTagName("input")[0].id = "P"+i;
        }
    }

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

         //添加商品主图后,添加图片按钮隐藏
         if (id.indexOf("M")>=0&&document.getElementById(galleryId).getElementsByTagName("div").length>0) {
             $("#"+id).parent().css("display","none");
         }

         //添加主题宣传图后,添加图片按钮隐藏
         if (document.getElementById("galleryT").getElementsByTagName("div").length>0) {
            $("#T").parent().css("display","none");
         }

         //商品预览图最多为6张
         if (id.indexOf("P")>=0 && document.getElementById("gallery"+id).getElementsByTagName("div").length==6) {
//             $("#"+id).attr({"disabled":true});
//             document.getElementById("preImgAdd"+id).style.background="#ccc";
            $("#"+id).parent().css("display","none");
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
        //没有商品主图时,上传按钮置为显示
        if (document.getElementById(id).getElementsByTagName("div").length==1) {
            id = id.substring(7,9);
            $("#"+id).parent().css("display","inline-block");
        }
        $(this).parent().remove();
    });

    /** 主题宣传图,点击移除的操作 **/
    $(document).on('click','.master-img .close',function(){
        //没有主题宣传图时,上传按钮置为显示
        if (document.getElementById("galleryT").getElementsByTagName("div").length==1) {
            $("#T").parent().css("display","inline-block");
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
        //最后一行有空值不能添加
        for(i=1;i<tds.length;i++) {
            if (i <=9 && tds[i].getElementsByTagName("input")[0].value=="") break;
            if (i ==13 && trs[index-1].getElementsByClassName("list-img")[0].getElementsByTagName("div").length==1) break;
            if (i ==14 && trs[index-1].getElementsByClassName("preview-img")[0].getElementsByTagName("div").length==1) break;
        }
        if (i==15){
            var tr =  document.createElement("tr");

            var tdR = document.createElement("td");
            var inputR = document.createElement("input");
            inputR.type = "radio";
            inputR.name = "orMasterInv";
            inputR.classList.add("master-radio");
            tdR.appendChild(inputR);

            var tdC = document.createElement("td");
            var inputC = document.createElement("input");
            inputC.type = "text";
            inputC.name = "itemColor";
            tdC.appendChild(inputC);

            var tdS = document.createElement("td");
            var inputS = document.createElement("input");
            inputS.type = "text";
            inputS.name = "itemSize";
            tdS.appendChild(inputS);

            var tdW = document.createElement("td");
            var inputW = document.createElement("input");
            inputW.type = "text";
            inputW.name = "invWeight";
            tdW.appendChild(inputW);

            var tdA = document.createElement("td");
            var inputA = document.createElement("input");
            inputA.type = "text";
            inputA.name = "amount";
            tdA.appendChild(inputA);

            var tdP = document.createElement("td");
            var inputP = document.createElement("input");
            inputP.type = "text";
            inputP.name = "itemPrice";
            tdP.appendChild(inputP);

            var tdSP = document.createElement("td");
            var inputSP = document.createElement("input");
            inputSP.type = "text";
            inputSP.name = "itemSrcPrice";
            tdSP.appendChild(inputSP);

            var tdCP = document.createElement("td");
            var inputCP = document.createElement("input");
            inputCP.type = "text";
            inputCP.name = "itemCostPrice";
            tdCP.appendChild(inputCP);

            var tdD = document.createElement("td");
            var inputD = document.createElement("input");
            inputD.type = "text";
            inputD.name = "itemDiscount";
            tdD.appendChild(inputD);

            var tdRA = document.createElement("td");
            var inputRA = document.createElement("input");
            inputRA.type = "text";
            inputRA.name = "restrictAmount";
            tdRA.appendChild(inputRA);

            var tdIA = document.createElement("td");
            var inputR1 = document.createElement("input");
            inputR1.type = "radio";
            inputR1.name = "invArea"+index;
            inputR1.value = "B";
            inputR1.classList.add("area-radio");
            var divB = document.createElement("div");
            divB.classList.add('area');
            divB.innerText="保税区";
            var inputR2 = document.createElement("input");
            inputR2.type = "radio";
            inputR2.name = "invArea"+index;
            inputR2.value = "Z";
            inputR2.classList.add("area-radio");
            inputR2.checked = "checked";
            var divZ = document.createElement("div");
            divZ.classList.add('area');
            divZ.innerText="韩国直邮";
            tdIA.appendChild(inputR1);
            tdIA.appendChild(divB);
            tdIA.appendChild(inputR2);
            tdIA.appendChild(divZ);

            var tdPost = document.createElement("td");
            tdPost.innerHTML = '<select><option value="shanghai">上海海关</option><option value="guangzhou">广州海关</option><option value="hangzhou">杭州海关</option>'+
                               '<option value="ningbo">宁波海关</option><option value="zhengzhou">郑州海关</option><option value="chongqing">重庆海关</option>'+
                               '<option value="gzjichang">广州机场海关</option><option value="gzluogang">广州萝岗海关</option></select>';
            var tdMI = document.createElement("td");
            tdMI.classList.add('list-img');
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
            tdMI.appendChild(divM);
            tdMI.appendChild(spanAdd);

            var tdPI = document.createElement("td");
            tdPI.width="146px";
            tdPI.classList.add('preview-img');
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
            tdPI.appendChild(divPre);
            tdPI.appendChild(spanAdd);

            var tdDe = document.createElement("td");
            tdDe.classList.add("del");
            tdDe.innerText = "删除";
            var inputInvId = document.createElement("input");
            inputInvId.type = "hidden";
            inputInvId.value = "";
            tdDe.appendChild(inputInvId);

            tr.appendChild(tdR);
            tr.appendChild(tdC);
            tr.appendChild(tdS);
            tr.appendChild(tdW);
            tr.appendChild(tdA);
            tr.appendChild(tdP);
            tr.appendChild(tdSP);
            tr.appendChild(tdCP);
            tr.appendChild(tdD);
            tr.appendChild(tdRA);
            tr.appendChild(tdIA);
            tr.appendChild(tdPost);
            tr.appendChild(tdMI);
            tr.appendChild(tdPI);
            tr.appendChild(tdDe);
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
    $("#submitItem").click(function(){
        var isPost = true;
        var numberReg1 =    /^-?\d+$/;   //正整数
        var numberReg2 =    /^-?\d+\.?\d{0,2}$/;   //整数或小数
        var item = new Object();
        var inventories = [];
        var itemData = new Object();
        //必填项不能有空值
        if ( $("#categorySubSelect").val()=="" || $("#bandSelect").val()=="" || $("#productName").val()=="" || $("#supplyMerch").val()=="" || $("#itemTitle").val()==""
            || $("#onShelvesAt").val()=="" || $("#offShelvesAt").val()=="" ) {
            isPost=false;
            alert("必填项不能为空");
        }
        var cateId = "";
        if($("#categorySubSelect").text()=="") cateId=$("#categorySelect").val();
        else cateId=$("#categorySubSelect").val();
        var brandId = $("#bandSelect").val();
        var supplyMerch = $("#supplyMerch").val();
        var itemTitle = $("#itemTitle").val();
        var onShelvesAt = $("#onShelvesAt").val();
        var offShelvesAt = $("#offShelvesAt").val();
        var itemNotice = $("#itemNotice").val();
        var invWeight = document.getElementsByName("invWeight");
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
        //验证输入的数量和价格等是否符合规则
        for(i=1;i<=9;i++) {
            if (tds[i].getElementsByTagName("input")[0].value=="") {
                isPost = false;
                $("#warn-inv").html("库存信息不能有空值!");
                break;
            } else $("#warn-inv").html("");
        }
        for(i=0;i<amount.length;i++){
            if (!numberReg2.test(invWeight[i].value) ||  !numberReg2.test(amount[i].value) || !numberReg2.test(itemPrice[i].value) || !numberReg2.test(itemSrcPrice[i].value)
                || !numberReg2.test(itemCostPrice[i].value) || !numberReg2.test(itemDiscount[i].value) || !numberReg2.test(restrictAmount[i].value)) {
                isPost = false;
                $("#warn-num").text("输入数据不合法!");
                break;
            } else $("#warn-num").text("");
        }
        if (tds[12].getElementsByTagName("div").length<2) {
            isPost = false;
            $("#warn-mas").html("请上传商品主图");
        } else $("#warn-mas").html("");
        if (tds[13].getElementsByTagName("div").length<2) {
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
        var masterThImg = galleryT.getElementsByTagName("div");
        if (masterThImg.length<1) {
            isPost = false;
            $("#warn-masthimg").text("请上传主题宣传图!");
        } else {
            $("#warn-masthimg").text("");
        }
        //修改页面图片来源为读取商品的信息不是上传  商品主题图
        var inpT = galleryT.getElementsByTagName("input");
        var imgT = galleryT.getElementsByTagName("img");
        if (inpT.length==0 && imgT.length!=0) {
            itemMasterImg = imgT[0].src;
            //截取图片的url
            itemMasterImg  = itemMasterImg.substring(itemMasterImg.indexOf('/',itemMasterImg.indexOf('/')+2));
        }
        if (inpT.length!=0 && imgT.length!=0) itemMasterImg = inpT[0].value;
        //商品详细图
        var galleryD = document.getElementById("galleryD");
        var detailImg = galleryD.getElementsByTagName("div");
        if (detailImg.length<1) {
            isPost = false;
            $("#warn-detailimg").text("请上传商品详细图!");
        } else {
            $("#warn-detailimg").text("");
        }
        var itemDetailImgs = [];
        for(i=0;i<detailImg.length;i++) {
            var inpD = detailImg[i].getElementsByTagName("input");
            var imgD = detailImg[i].getElementsByTagName("img");
            if(inpD.length==0 && imgD.length!=0) {
                var detV  = imgD[0].src;
                detV = detV.substring(detV.indexOf('/',detV.indexOf('/')+2));
                itemDetailImgs.push(detV);
            }
            if(inpD.length!=0 && imgD.length!=0) itemDetailImgs.push(inpD[0].value);
        }
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
            var invWeight = tds[3].getElementsByTagName("input")[0].value;
            var amount = tds[4].getElementsByTagName("input")[0].value;
            var itemPrice = tds[5].getElementsByTagName("input")[0].value;
            var itemSrcPrice = tds[6].getElementsByTagName("input")[0].value;
            var itemCostPrice = tds[7].getElementsByTagName("input")[0].value;
            var itemDiscount = tds[8].getElementsByTagName("input")[0].value;
            var restrictAmount = tds[9].getElementsByTagName("input")[0].value;
            var invArea = "B";
            if (tds[10].getElementsByTagName("input")[1].checked==true)   invArea = "Z";
            var invCustoms = tds[11].getElementsByTagName("select")[0].value;
            //sku主图
            var invImg = "";
            var inpM = document.getElementById("galleryM"+i).getElementsByTagName("input");
            var imgM = document.getElementById("galleryM"+i).getElementsByTagName("img");
            if (inpM.length==0 && imgM.length!=0) {
                invImg = imgM[0].src;
                invImg  = invImg.substring(invImg.indexOf('/',invImg.indexOf('/')+2));
            }
           if (inpM.length!=0 && imgM.length!=0) invImg = inpM[0].value;
            //sku预览图
            imgs = document.getElementById("galleryP"+i).getElementsByTagName("div");
            for(j=0;j<imgs.length;j++) {
                var inpP = imgs[j].getElementsByTagName("input");
                var imgP = imgs[j].getElementsByTagName("img");
                if (inpP.length==0 && imgP.length!=0) {
                    imgsV = imgP[0].src;
                    imgsV = imgsV.substring(imgsV.indexOf('/',imgsV.indexOf('/')+2));
                    itemPreviewImgs.push(imgsV);
                }
                if (inpP.length!=0 && imgP.length!=0) itemPreviewImgs.push(inpP[0].value);
            }
            //拼装成一条数据
            var inventory = new Object();
            inventory.orMasterInv = orMasterInv;
            inventory.itemColor = itemColor;
            inventory.itemSize = itemSize;
            inventory.invWeight = invWeight;
            inventory.amount = amount;
            inventory.itemPrice = itemPrice;
            inventory.itemPrice = itemPrice;
            inventory.itemSrcPrice = itemSrcPrice;
            inventory.itemCostPrice = itemCostPrice;
            inventory.itemDiscount = itemDiscount;
            inventory.restrictAmount = restrictAmount;
            inventory.invArea = invArea;
            inventory.invCustoms = invCustoms;
            inventory.invImg = invImg;
            inventory.itemPreviewImgs = itemPreviewImgs;
            if (tds[14].getElementsByTagName("input")[0].value != "") {
                inventory.id = tds[14].getElementsByTagName("input")[0].value;
                if (tds[0].getElementsByTagName("input")[0].checked==true)
                item.masterInvId = tds[14].getElementsByTagName("input")[0].value;
            }
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
        if ($("#itemId").val() != "") {
            item.id = $("#itemId").val();
        }

        itemData.item = item;
        itemData.inventories = inventories;

        console.log(JSON.stringify(item));
        console.log(JSON.stringify(inventories));
//        console.log(JSON.stringify(itemData));
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
                    $('.usercenter-option > .user-state').css('background-position', '20px -174px');
                    if (window.lang = 'cn') {
                        $('#js-userinfo-error').text('保存成功').css('color', '#2fa900');
                        $('.usercenter-option > .user-state').text('未更改');
                    } else {
                        $('#js-userinfo-error').text('Save success');
                        $('.usercenter-option > .user-state').text('Unchanged');
                    }
                    setTimeout("$('#js-userinfo-error').text('').css('color','#c00')", 2000);
                    //商品更新, 成功后返回到列表查询页面
                    if($("#itemId").val() != "") {
                        setTimeout("location.href='/"+window.lang+"/comm/search'", 3000);
                    }
                    //商品录入, 成功后返回到商品录入页面
                    else setTimeout("location.href='/"+window.lang+"/comm/add'", 3000);
                }
            });
        }
    });

});

