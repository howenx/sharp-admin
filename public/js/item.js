$(function(){

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

    if ($("#itemId").val() != "") {
        //修改主图和预览图所在div的id值,修改页面使用
//        var upInv = document.getElementById("inventory");
//        var uptrs = inventory.getElementsByTagName("tr");
//        for(i=1;i<uptrs.length;i++) {
//            var uptds = uptrs[i].getElementsByTagName("td");
//            uptds[11].getElementsByTagName("select")[0].id = i;
//            uptds[16].id = "rec"+i;
//            uptds[17].getElementsByTagName("div")[0].id = "galleryM"+i;
//            uptds[17].getElementsByTagName("span")[0].id = "masterImgAddM"+i;
//            uptds[17].getElementsByTagName("input")[0].id = "M"+i;
//            uptds[18].getElementsByTagName("div")[0].id = "galleryP"+i;
//            uptds[18].getElementsByTagName("span")[0].id = "preImgAddP"+i;
//            uptds[18].getElementsByTagName("input")[0].id = "P"+i;
//        }

        //修改页面二级类别列表加载数据
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

    $(document).on('click','.delTr',function(){
        if (window.confirm("确定删除吗?")) {
            $(this).parent().parent().remove();
        }
    });

//    /** 主题宣传图,点击移除的操作 **/
//    $(document).on('click','.master-img .close',function(){
//        //没有主题宣传图时,上传按钮置为显示
//        if (document.getElementById("galleryT").getElementsByTagName("div").length==1) {
//            $("#T").parent().css("display","inline-block");
//        }
//        $(this).parent().remove();
//    });
//
//    /** 详细图 点击移除的操作 **/
//    $(document).on('click','.detail-img .close',function(){
//        $(this).parent().remove();
//    });

    /** 添加一条库存信息 **/
//    $(".add-goods").click(function(){
//        var inventory = document.getElementById("inventory");
//        var trs = inventory.getElementsByTagName("tr");
//        var index = trs.length;
//        var tds = trs[index-1].getElementsByTagName("td");
//        var i = 1;
//        //最后一行有空值不能添加
//        for(i=1;i<=18;i++) {
//            if (i <=9 && tds[i].getElementsByTagName("input")[0].value=="") break;
//            if ((i==10 || i==11) && $("#itemId").val()=="" && tds[i].getElementsByTagName("select")[0].value=="" )break;
//            if (i==10 && $("#itemId").val()!="" && tds[i].getElementsByTagName("select")[0].value=="" )break;
//            if (tds[16].getElementsByTagName("input")[0].value==""&&tds[16].getElementsByTagName("input")[1].value==""&&tds[16].getElementsByTagName("input")[2].value=="") break;
//            if (i ==17 && trs[index-1].getElementsByClassName("list-img")[0].getElementsByTagName("div").length==1) break;
//            if (i ==18 && trs[index-1].getElementsByClassName("preview-img")[0].getElementsByTagName("div").length==1) break;
//        }
//        if (i==19){
////        if (true){
//            var tr =  document.createElement("tr");
//
//            var tdR = document.createElement("td");
//            tdR.innerHTML = '<input type="radio" name="orMasterInv" class="master-radio"/>';
//
//            var tdC = document.createElement("td");
//            tdC.innerHTML = '<input type="text" name="itemColor">';
//
//            var tdS = document.createElement("td");
//            tdS.innerHTML = '<input type="text" name="itemSize">';
//
//            var tdW = document.createElement("td");
//            tdW.innerHTML = '<input type="text" name="invWeight">';
//
//            var tdA = document.createElement("td");
//            tdA.innerHTML = '<input type="text" name="amount">';
//
//            var tdP = document.createElement("td");
//            tdP.innerHTML = '<input type="text" name="itemPrice">';
//
//            var tdSP = document.createElement("td");
//            tdSP.innerHTML = '<input type="text" name="itemSrcPrice">';
//
//            var tdCP = document.createElement("td");
//            tdCP.innerHTML = '<input type="text" name="itemCostPrice">';
//
//            var tdD = document.createElement("td");
//            tdD.innerHTML = '<input type="text" name="itemDiscount">';
//
//            var tdRA = document.createElement("td");
//            tdRA.innerHTML = '<input type="text" name="restrictAmount">';
//
//            var tdCarr = document.createElement("td");
//            var sel = document.createElement("select");
//            var opt = document.createElement("option");
//            opt.value = "";
//            opt.innerText="请选择";
//            sel.appendChild(opt);
//            var mList = $("#mList").val();
//            mList = mList.substring(2,mList.length-1).split(", C");
//            for(c=0;c<mList.length;c++) {
//                var carrArr = mList[c].substring(7,mList[c].length).split(", ");
//                var modelCode = carrArr[1]; modelCode = modelCode.substring(11,modelCode.length-1);
//                var modelName = carrArr[2]; modelName = modelName.substring(11,modelName.length-1);
//                var opt = document.createElement("option");
//                opt.value = modelCode;
//                opt.innerText = modelName;
//                sel.appendChild(opt);
//            }
//            tdCarr.appendChild(sel);
//
//            var tdTRSet = document.createElement("td");
//            tdTRSet.innerHTML = '<select class="trset" id="'+index+'"><option value="">请选择</option><option value="S">标准税率</option><option value="F">免税</option><option value="D">自定义</option></select>';
//
//            var tdTRate = document.createElement("td");
//            tdTRate.innerHTML = '<input type="text" name="postalTaxRate" readOnly>';
//
//            var tdTCode = document.createElement("td");
//            tdTCode.innerHTML = '<input type="text" name="postalTaxCode" readOnly>';
//
//            var tdIA = document.createElement("td");
//            tdIA.innerHTML = '<select><option value="H">杭州保税仓备案</option><option value="HZ">杭州保税仓直邮</option><option value="G">广州保税仓备案</option><option value="GZ">广州保税仓直邮</option>'+
//                                      '<option value="S">上海保税仓备案</option><option value="SZ">上海保税仓直邮</option><option value="K">海外直邮</option></select>';
//
//            var tdCus = document.createElement("td");
//            tdCus.innerHTML = '<select><option value="shanghai">上海海关</option><option value="hangzhou">杭州海关</option><option value="guangzhou">广州海关</option></select>';
//
//            var tdRecord = document.createElement("td");
//            tdRecord.id = "rec"+index;
//            tdRecord.innerHTML = '<div class="record"><span>杭州 </span><input type="text" name="recordHZ" style="width:70%;"></div>'+
//                                 ' <div class="record"><span>广州 </span><input type="text" name="recordGZ" style="width:70%;"></div><div class="record"><span>上海 </span><input type="text" name="recordSH" style="width:70%;"></div>';
//
//            var tdMI = document.createElement("td");
//            tdMI.classList.add('list-img');
//            var divM = document.createElement("div");
//            divM.id = "galleryM"+index;
//            var spanAdd = document.createElement("span");
//            spanAdd.classList.add("add");
//            spanAdd.id = "masterImgAddM" + index;
//            spanAdd.innerText = "+";
//            var inputF = document.createElement("input");
//            inputF.type = "file";
//            inputF.id = "M"+index;
//            inputF.classList.add("hidden1");
//            inputF.setAttribute("accept", "image/gif, image/jpeg, image/webp, image/png");
//            spanAdd.appendChild(inputF);
//            tdMI.appendChild(divM);
//            tdMI.appendChild(spanAdd);
//
//            var tdPI = document.createElement("td");
//            tdPI.style.whiteSpace = "nowrap";
//            tdPI.classList.add('preview-img');
//            var divPre = document.createElement("div");
//            divPre.id = "galleryP"+index;
//            var spanAdd = document.createElement("span");
//            spanAdd.classList.add("add");
//            spanAdd.id = "preImgAddP" + index;
//            spanAdd.innerText = "+";
//            var inputF = document.createElement("input");
//            inputF.type = "file";
//            inputF.id = "P" + index;
//            inputF.classList.add("hidden1");
//            inputF.setAttribute("accept", "image/gif, image/jpeg, image/webp, image/png");
//            spanAdd.appendChild(inputF);
//            tdPI.appendChild(divPre);
//            tdPI.appendChild(spanAdd);
//
//            var tdState = document.createElement("td");
//            if($("#itemId").val() != "") tdState.innerHTML = "<input type='text' name='state' value='正常' style='font-size:10px;'>";
//            else tdState.style.display="none";
//
//            var tdDe = document.createElement("td");
//            tdDe.classList.add("del");
//            tdDe.innerText = "删除";
//            var inputInvId = document.createElement("input");
//            inputInvId.type = "hidden";
//            inputInvId.value = "";
//            tdDe.appendChild(inputInvId);
//
//            tr.appendChild(tdR);
//            tr.appendChild(tdC);
//            tr.appendChild(tdS);
//            tr.appendChild(tdW);
//            tr.appendChild(tdA);
//            tr.appendChild(tdP);
//            tr.appendChild(tdSP);
//            tr.appendChild(tdCP);
//            tr.appendChild(tdD);
//            tr.appendChild(tdRA);
//            tr.appendChild(tdCarr);
//            tr.appendChild(tdTRSet);
//            tr.appendChild(tdTRate);
//            tr.appendChild(tdTCode);
//            tr.appendChild(tdIA);
//            tr.appendChild(tdCus);
//            tr.appendChild(tdRecord);
//            tr.appendChild(tdMI);
//            tr.appendChild(tdPI);
//            tr.appendChild(tdState);
//            tr.appendChild(tdDe);
//            inventory.appendChild(tr);
//        }
//    });


    /** 删除一条库存信息 **/
//    $(".inv").delegate(".del","click",function(){
//        if (document.getElementById("inventory").getElementsByTagName("tr").length>2) {
//            $(this).parent().remove();
//            //移除之后重新给控件的id赋值
//            var upInv = document.getElementById("inventory");
//            var uptrs = inventory.getElementsByTagName("tr");
//            for(i=1;i<uptrs.length;i++) {
//                var uptds = uptrs[i].getElementsByTagName("td");
//                uptds[16].id = "rec"+i;
//                uptds[11].getElementsByTagName("select")[0].id = i;
//                uptds[17].getElementsByTagName("div")[0].id = "galleryM"+i;
//                uptds[17].getElementsByTagName("span")[0].id = "masterImgAddM"+i;
//                uptds[17].getElementsByTagName("input")[0].id = "M"+i;
//                uptds[18].getElementsByTagName("div")[0].id = "galleryP"+i;
//                uptds[18].getElementsByTagName("span")[0].id = "preImgAddP"+i;
//                uptds[18].getElementsByTagName("input")[0].id = "P"+i;
//            }
//        }
//    });

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
            $("<tr>").html('<td><input type="text" name="attrN"/></td><td><input type="text" name="attrV"/></td><td class="del">删除</td>').appendTo($(".feature"));
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
        var numberReg1 =    /^-?\d+$/;   //正整数
        var numberReg2 =    /^-?\d+\.?\d{0,2}$/;   //整数或小数
        var numberReg3 =    /[^/d]/g;   //数字
        var numberReg4 =    /^[0-9a-zA-Z]*$/g;   //字母和数字
        var item = new Object();
        var inventories = [];
        var itemData = new Object();
        var cateId = "";
        if($("#categorySubSelect").text()=="") cateId=$("#categorySelect").val();
        else cateId=$("#categorySubSelect").val();
        var brandId = $("#bandSelect").val();
        var itemTitle = $("#itemTitle").val();
        var supplyMerch = $("#supplyMerch").val();
//        var onShelvesAt = $("#onShelvesAt").val();
//        var offShelvesAt = $("#offShelvesAt").val();
        var itemNotice = $("#itemNotice").val();
        var itemDetail = UE.getEditor('editor').getContent();
//        var invWeight = document.getElementsByName("invWeight");
//        var amount = document.getElementsByName("amount");
//        var itemPrice = document.getElementsByName("itemPrice");
//        var itemSrcPrice = document.getElementsByName("itemSrcPrice");
//        var itemCostPrice = document.getElementsByName("itemCostPrice");
//        var itemDiscount = document.getElementsByName("itemDiscount");
//        var restrictAmount = document.getElementsByName("restrictAmount");
//        var recordHZ = document.getElementsByName("recordHZ");
//        var recordGZ = document.getElementsByName("recordGZ");
//        var recordSH = document.getElementsByName("recordSH");
        //必填项不能有空值
        if ( cateId=="" || brandId=="" || itemTitle=="" || supplyMerch=="" || itemDetail=="" ) {
            isPost=false;
            alert("必填项不能为空");
        }
//        if (onShelvesAt >= offShelvesAt) {
//           isPost = false;
//           $("#warn-date").html("日期不正确!");
//        } else $("#warn-date").html("");
        var inventoryTab = document.getElementById("inventory");
        var trs = inventoryTab.getElementsByTagName("tr");
        //最后一行的数据验证
//        var tds = trs[trs.length-1].getElementsByTagName("td");
        //验证输入的数量和价格等是否符合规则
//        for(i=1;i<=9;i++) {
//            if (tds[i].getElementsByTagName("input")[0].value=="") {
//                isPost = false;
//                $("#warn-inv").html("库存信息不能有空值!");
//                break;
//            } else $("#warn-inv").html("");
//        }
//        for(i=0;i<amount.length;i++){
//
//            if (!numberReg2.test(invWeight[i].value) || !numberReg1.test(amount[i].value) || !numberReg2.test(itemPrice[i].value) || !numberReg2.test(itemSrcPrice[i].value)
//                || !numberReg2.test(itemCostPrice[i].value) || !numberReg2.test(itemDiscount[i].value) || !numberReg1.test(restrictAmount[i].value)
//                || (!numberReg4.test(recordHZ[i].value) && !numberReg4.test(recordGZ[i].value) && !numberReg4.test(recordSH[i].value))) {
//                isPost = false;
//                $("#warn-num").text("输入数据不合法!");
//                break;
//            } else $("#warn-num").text("");
//        }
//        if (tds[17].getElementsByTagName("div").length<2) {
//            isPost = false;
//            $("#warn-mas").html("请上传商品主图");
//        } else $("#warn-mas").html("");
//        if (tds[18].getElementsByTagName("div").length<2) {
//            isPost = false;
//            $("#warn-pre").html("请上传商品预览图");
//        } else $("#warn-pre").html("");
        //选择主商品
        var orMasterInv = document.getElementsByName("orMasterInv");
        for(m=0;m<orMasterInv.length;m++) {
            if (orMasterInv[m].checked==true) break;
            if (m==orMasterInv.length-1) {
                isPost = false;
//                $("#warn-cho").text("请选择主商品!");
            }
//            else $("#warn-cho").text("");
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
//        var itemMasterImg = "";
//        var galleryT = document.getElementById("galleryT");
//        var masterThImg = galleryT.getElementsByTagName("div");
//        if (masterThImg.length<1) {
//            isPost = false;
//            $("#warn-masthimg").text("请上传主题宣传图!");
//        } else {
//            $("#warn-masthimg").text("");
//        }

//        var imgT = galleryT.getElementsByTagName("img");
        //主题宣传图
//        itemMasterImg = imgT[0].src;
//        itemMasterImg  = itemMasterImg.substring(itemMasterImg.lastIndexOf('/')+1,itemMasterImg.length);
        //商品详细图
//        var galleryD = document.getElementById("galleryD");
//        var detailImg = galleryD.getElementsByTagName("div");
//        if (detailImg.length<1) {
//            isPost = false;
//            $("#warn-detailimg").text("请上传商品详细图!");
//        } else {
//            $("#warn-detailimg").text("");
//        }
//        var itemDetailImgs = [];
//        for(i=0;i<detailImg.length;i++) {
//            var spanArr = [];
//            var spanD = detailImg[i].getElementsByTagName("span");
//            var inpD = detailImg[i].getElementsByTagName("input");
//            var imgD = detailImg[i].getElementsByTagName("img");
//            //图片来源为读取商品图片/上传新图片
//            if(inpD.length==0 && imgD.length!=0) {
//                var imgArr = [];
//                for(d=0;d<imgD.length;d++) {
//                    var detV  = imgD[d].src;
//                    detV = detV.substring(detV.lastIndexOf('/')+1,detV.length);
//                    imgArr.push(detV);
//                }
//                itemDetailImgs.push(imgArr);
//            }
//            if(inpD.length!=0 && imgD.length!=0) {
//                var inpDArr = inpD[0].value.split(",");
//                for(s=0;s<inpDArr.length;s++){
//                    spanArr.push(inpDArr[s]);
//                }
//                itemDetailImgs.push(spanArr);
//            }
//        }
//            console.log(itemDetailImgs.toString());
        //遍历所有参数及参数值,且不能有空值
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
            var orMasterInv = false;
            if (tds[0].getElementsByTagName("input")[0].checked==true)    orMasterInv = true;
            var itemColor = tds[1].innerHTML;
            var itemSize = tds[2].innerHTML;
            var startAt = tds[3].innerHTML;
            var endAt = tds[4].innerHTML;
            var itemPrice = tds[5].innerHTML;
            var itemSrcPrice = tds[6].innerHTML;
            var itemCostPrice = tds[7].innerHTML;
            var itemDiscount = tds[8].innerHTML;
            var invWeight = tds[9].innerHTML;
            var restrictAmount = tds[10].innerHTML;
            var amount = tds[11].innerHTML;
            var restAmount = tds[12].innerHTML;
            var carriageModelCode = tds[13].innerHTML;
            var invArea = tds[14].innerHTML;
            var invCustoms = tds[15].innerHTML;
            var postalTaxRate = tds[17].innerHTML;
            var postalTaxCode = tds[18].innerHTML;
            var recordHZ = tds[19].innerHTML;
            var recordGZ = tds[20].innerHTML;
            var recordSH = tds[21].innerHTML;
            var recordCode = {};
            if(recordHZ!="") recordCode["hangzhou"] = recordHZ;
            if(recordGZ!="") recordCode["guangzhou"] = recordGZ;
            if(recordSH!="") recordCode["shanghai"] = recordSH;
            var invImg = tds[22].innerHTML;
            var itemPreviewImgs = tds[23].innerHTML;
            var orVaryPrice = tds[24].innerHTML;
//            if($("#itemId").val() == "" && tRSet=="") {
//                isPost = false;
//                $("#warn-num").text("请选择税率设置!");
//            } else $("#warn-num").text("");
//            if(carriageModelCode=="") {
//                isPost = false;
//                $("#warn-num").text("请选择运费模板!");
//            } else $("#warn-num").text("");
            //行邮税率设置 F免税:税率为0,行邮税号不设置; S标准税率:税率不设置,输入行邮税号(数字); D自定义税率:设置税率,行邮税号不设置
//            if (tRSet == "S") {
//                if (!numberReg1.test(tds[13].getElementsByTagName("input")[0].value)) {
//                    isPost = false;
//                    $("#warn-num").text("请输入正确的行邮税号!");
//                } else $("#warn-num").text("");
//            }
//            if (tRSet == "D") {
//                if (!numberReg1.test(tds[12].getElementsByTagName("input")[0].value)) {
//                    isPost = false;
//                    $("#warn-num").text("税率输入不合法!");
//                } else $("#warn-num").text("");
//            }
            //sku主图
//            var invImg = {};
//            var imgM = document.getElementById("galleryM"+i).getElementsByTagName("img");
//            var imgSrc = imgM[0].src;
//            var imgWidth = imgM[0].getAttribute("width");
//            var imgHeight = imgM[0].getAttribute("height");
//            invImg["url"] = imgSrc.substring(imgSrc.lastIndexOf('/')+1,imgSrc.length);
//            invImg["width"] = imgWidth;
//            invImg["height"] = imgHeight;
//            invImg = imgM[0].src;
//            invImg  = invImg.substring(invImg.lastIndexOf('/')+1,invImg.length);
            //sku预览图
//            imgs = document.getElementById("galleryP"+i).getElementsByTagName("div");
//            for(j=0;j<imgs.length;j++) {
//                var imgP = imgs[j].getElementsByTagName("img");
////                imgsV = imgP[0].src;
////                imgsV = imgsV.substring(imgsV.lastIndexOf('/')+1,imgsV.length);
////                itemPreviewImgs.push(imgsV);
//                //sku预览图保存图片的url,宽和高
//                var imgsV = {};
//                var preSrc = imgP[0].src;
//                var width = imgP[0].getAttribute("width");
//                var height = imgP[0].getAttribute("height");
//                imgsV["url"] = preSrc.substring(preSrc.lastIndexOf('/')+1,preSrc.length);
//                imgsV["width"] = width;
//                imgsV["height"] = height;
//                itemPreviewImgs.push(imgsV);
//            }

    //上传的预览图尺寸不一致时提示

            /*for(jj=0;jj<imgs.length;jj++) {
                if (jj>0) {
                    var preImg = imgs[jj-1].getElementsByTagName("img")[0];
                    var img = imgs[jj].getElementsByTagName("img")[0];
                    var preWidth = preImg.getAttribute("width");
                    var preHeight = preImg.getAttribute("height");
                    var width = img.getAttribute("width");
                    var height = img.getAttribute("height");
                    if (width!=preWidth && height!=preHeight) {
                        if (window.confirm("第"+i+"条库存上传的预览图尺寸不一致,是否继续?")) {}
                        else isPost = false;
                        break;
                    }
                }
            }*/

            //拼装成一条数据
            var invData = new Object();
            var inventory = new Object();
            var varyPrices = [];
            inventory.orMasterInv = orMasterInv;
            inventory.itemColor = itemColor;
            inventory.itemSize = itemSize;
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
            invData.inventory = inventory;
            if (orVaryPrice) {
                var vp_arr = tds[25].innerHTML.split(",");
                for(v=0;v<vp_arr.length;v++) {
                    if (v%2==0) {
                        var varyPrice = new Object();
                        varyPrice.price = vp_arr[v];
                        varyPrice.limitAmount = vp_arr[v+1];
                        varyPrices.push(varyPrice);
                    }
                }
                invData.varyPrices = varyPrices;
            }


//            if (tds[20].getElementsByTagName("input")[0].value != "" && divId =="submitItem") {
//                inventory.id = tds[20].getElementsByTagName("input")[0].value;
//                inventory.state = tds[19].getElementsByTagName("select")[0].value;
//                inventory.soldAmount = tds[20].getElementsByTagName("input")[1].value;
//                inventory.restAmount = tds[20].getElementsByTagName("input")[2].value;
//                inventory.orDestroy = tds[20].getElementsByTagName("input")[3].value;
//                if (tds[0].getElementsByTagName("input")[0].checked==true)
//                item.masterInvId = tds[20].getElementsByTagName("input")[0].value;
//            }
            inventories.push(invData);
        }
        item.cateId = cateId;
        item.brandId = brandId;
        item.supplyMerch = supplyMerch;
        item.itemTitle = itemTitle;
        item.itemNotice = itemNotice;
        item.publicity = publicity;
//        item.itemMasterImg = itemMasterImg;
//        item.itemDetailImgs = itemDetailImgs;
        item.itemFeatures = itemFeatures;
        item.itemDetail = itemDetail;
//        if ($("#itemId").val() != "" && divId=="submitItem") {
//            item.id = $("#itemId").val();
//            item.shareCount = $("#shareCount").val();
//            item.collectCount = $("#collectCount").val();
//            item.browseCount = $("#browseCount").val();
//            item.orDestroy = $("#orDestroy").val();
//            item.themeId = $("#themeId").val();
//            item.state = $("#state").val();
//        }

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

