$(function(){

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

    $(document).on('click','.fdel .big',function() {
        $(this).parents(".fdel").remove();
    });

    /** 上传图片操作,主图,点击移除的操作 **/
    $(document).on('click','.list-img .close',function(){
        var id = $(this).parent().parent().attr("id");
        //没有商品主图时,上传按钮置为可点击且颜色恢复
        if (document.getElementById(id).getElementsByTagName("div").length==1) {
            id = id.substring(7,9);
            $("#"+id).removeAttr("disabled");
            document.getElementById('masterImgAdd'+id).style.background="#00B7EE";
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
        if (document.getElementById(id).getElementsByTagName("div").length==2) {
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
            inputR1.checked = "checked";
            var divB = document.createElement("div");
            divB.classList.add('area');
            divB.innerText="保税区";
            var inputR2 = document.createElement("input");
            inputR2.type = "radio";
            inputR2.name = "invArea"+index;
            inputR2.value = "Z";
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
        var items = "{";    //items表存入数据
        var inventories = "[";    //inventories表存入数据
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
        if (onShelvesAt > offShelvesAt) {
           isPost = false;
           $("#warn-date").html("结束日期不正确!");
        } else $("#warn-date").html("");
        var inventory = document.getElementById("inventory");
        var trs = inventory.getElementsByTagName("tr");
        var tds = trs[trs.length-1].getElementsByTagName("td");
        for(i=1;i<=8;i++) {
            if (tds[i].getElementsByTagName("input")[0].value=="") {
                $("#warn-inv").val("库存信息不能有空值!");
                break;
            } else $("#warn-inv").val("");
        }
        if (tds[10].getElementsByTagName("div").length<2) $("#warn-inv").val("请上传商品主图"); else $("#warn-inv").val("");
        if (tds[11].getElementsByTagName("div").length<2) $("#warn-inv").val("请上传商品预览图"); else $("#warn-inv").val("");

        //验证输入的数量和价格是否符合规则
        for(i=0;i<amount.length;i++){
            if (!numberReg2.test(amount[i].value) || !numberReg2.test(itemPrice[i].value) || !numberReg2.test(itemSrcPrice[i].value)
                || !numberReg2.test(itemCostPrice[i].value) || !numberReg2.test(itemDiscount[i].value) || !numberReg2.test(restrictAmount[i].value)) {
                isPost = false;
                $("#warn-inv").text("输入数据不合法!");
                break;
            } else $("#warn-inv").text("");
        }
        //优惠信息
        var publicity = "[";
        var publicityTab = document.getElementById("publicityTab");
        var pubtr = publicityTab.getElementsByTagName("tr");
        for(i=1;i<pubtr.length;i++) {
            publicity += '\"' + pubtr[i].getElementsByTagName("td")[0].innerText + '\"' + ",";
        }
        publicity = publicity.substring(0,publicity.length - 1) + "]";

        var itemMasterImg = "";
        var galleryT = document.getElementById("galleryT");
        var masterThImgLen = galleryT.getElementsByTagName("input").length;
        if (masterThImgLen<1) {
            isPost = false;
            $("#warn-img").text("请上传主题宣传图!");
        } else {
            $("#warn-img").text("");
            itemMasterImg = galleryT.getElementsByTagName("input")[0].value;
        }
        var galleryD = document.getElementById("galleryD");
        var itemDetailImgs = "[";
        var detInps = galleryD.getElementsByTagName("input");
        for(i=0;i<detInps.length;i++) {
            itemDetailImgs = itemDetailImgs + '\"' + detInps[i].value + '\"' + ",";
        }
        itemDetailImgs = itemDetailImgs.substring(0,itemDetailImgs.length - 1) + "]";
        //遍历所有属性及属性值累加到隐藏域features中且属性名或值不能为空
        var itemFeatures = "{";
        var tabFea = document.getElementById("tabFea");
        var attrN = document.getElementsByName("attrN");
        var attrV = document.getElementsByName("attrV");
        for(i=0; i<attrN.length; i++) {
            if (attrN[i].value=="" || attrV[i].value=="") {
                isPost = false;
                if (window.lang=="cn") $("#warn-attr").text("属性名或值不能有空");
                else $("#warn-attr").text("attribute or attribute value can't be value");
            }
            itemFeatures = itemFeatures + '\"' + attrN[i].value + '\"' + ":" + '\"' + attrV[i].value + '\"' +",";
        }
        itemFeatures = itemFeatures.substring(0,itemFeatures.length - 1) + "}";
         //拼装inventories数据
        var index = 0;
            for(i=1;i<trs.length;i++) {
            var tds = trs[i].getElementsByTagName("td");
            var itemPreviewImgs = "[";
            var orMasterInv = false;
            eval("var inventoryData"+index);
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
            var invImg = document.getElementById("galleryM"+i).getElementsByTagName("input")[0].value;
            imgs = document.getElementById("galleryP"+i).getElementsByTagName("input");
            for(j=0;j<imgs.length;j++) {
                itemPreviewImgs += '\"' + imgs[j].value + '\"' + ",";
            }
            itemPreviewImgs = itemPreviewImgs.substring(0,itemPreviewImgs.length - 1) + "]";
            //拼装成一条数据
            inventoryDataindex = "{";
            inventoryDataindex += '\"' + "orMasterInv" + '\"' + ":" + '\"' + orMasterInv + '\"' + ",";
            inventoryDataindex += '\"' + "itemColor" + '\"' + ":" + '\"' + itemColor + '\"' + ",";
            inventoryDataindex += '\"' + "itemSize" + '\"' + ":" + '\"' + itemSize + '\"' + ",";
            inventoryDataindex += '\"' + "amount" + '\"' + ":" + '\"' + amount + '\"' + ",";
            inventoryDataindex += '\"' + "itemPrice" + '\"' + ":" + '\"' + itemPrice + '\"' + ",";
            inventoryDataindex += '\"' + "itemSrcPrice" + '\"' + ":" + '\"' + itemSrcPrice + '\"' + ",";
            inventoryDataindex += '\"' + "itemCostPrice" + '\"' + ":" + '\"' + itemCostPrice + '\"' + ",";
            inventoryDataindex += '\"' + "itemDiscount" + '\"' + ":" + '\"' + itemDiscount + '\"' + ",";
            inventoryDataindex += '\"' + "restrictAmount" + '\"' + ":" + '\"' + restrictAmount + '\"' + ",";
            inventoryDataindex += '\"' + "invArea" + '\"' + ":" + '\"' + invArea + '\"' + ",";
            inventoryDataindex += '\"' + "invImg" + '\"' + ":" + '\"' + invImg + '\"' + ",";
            inventoryDataindex += '\"' + "itemPreviewImgs" + '\"' + ":" +  itemPreviewImgs + ",";
            inventoryDataindex = inventoryDataindex.substring(0,inventoryDataindex.length - 1) + "}";
            inventories += inventoryDataindex + ",";
            index++;
        }

        items += '\"' + "cateId" + '\"' + ":" + '\"' + cateId + '\"' + ",";
        items += '\"' + "brandId" + '\"' + ":" + '\"' + brandId + '\"' + ",";
        items += '\"' + "supplyMerch" + '\"' + ":" + '\"' + supplyMerch + '\"' + ",";
        items += '\"' + "itemTitle" + '\"' + ":" + '\"' + itemTitle + '\"' + ",";
        items += '\"' + "onShelvesAt" + '\"' + ":" + '\"' + onShelvesAt + '\"' + ",";
        items += '\"' + "offShelvesAt" + '\"' + ":" + '\"' + offShelvesAt + '\"' + ",";
        items += '\"' + "itemNotice" + '\"' + ":" + '\"' + itemNotice + '\"' + ",";
        items += '\"' + "publicity" + '\"' + ":" +  publicity + ",";
        items += '\"' + "itemMasterImg" + '\"' + ":" + '\"' + itemMasterImg + '\"' + ",";
        items += '\"' + "itemDetailImgs" + '\"' + ":" +  itemDetailImgs + ",";
        items += '\"' + "itemFeatures" + '\"' + ":" + itemFeatures + ",";
        items = items.substring(0,items.length - 1) + "}";
        inventories = inventories.substring(0,inventories.length - 1) + "]";
        console.log(items);
        console.log(inventories);
        console.log(isPost);
        if (isPost) {
            $.ajax({
                type :  "POST",
                url : "/comm/insertItem",
                data : "items=" + items +"&inventories="+inventories,
                error : function(request) {
                    if (window.lang = 'cn') {
                        $('#js-userinfo-error').text('保存失败');
                    } else {
                        $('#js-userinfo-error').text('Save error');
                    }
                    setTimeout("$('#js-userinfo-error').text('')", 2000);
                },
                success: function(data) {
                    if (window.lang = 'cn') {
                        $('#js-userinfo-error').text('保存成功').css('color', '#2fa900');
                        $('.usercenter-option > .user-state').text('未更改');
                    } else {
                        $('#js-userinfo-error').text('Save success');
                        $('.usercenter-option > .user-state').text('Unchanged');
                    }
                    setTimeout("$('#js-userinfo-error').text('').css('color','#c00')", 2000);
                    setTimeout("location.href='/"+window.lang+"/comm/add'", 3000);
                }
            });
        }
    });

});

