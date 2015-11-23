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

    /** 添加颜色 **/
    $(document).on('click','.color .add',function() {
        var color = $(this).parent().prev().children().first().children().first().val();
        //如果最后一项内容为空则不能添加
        if (color == "") {
            $('.color .add').click(function(){});
        } else
            $("<li>").html('<span><input type="text" class="colorIn" name="productColor"/></span><button type="button" class="close" aria-label="Close"><span aria-hidden="true">&times;</span></button>').insertBefore($(this).parent());
    });

    /** 上传图片操作,主图,点击移除的操作 **/
    $(document).on('click','.list-img .close',function(){
        //没有商品主图时,上传按钮置为可点击且颜色恢复
        if (document.getElementById("galleryM").getElementsByTagName("div").length==1) {
            $("#M").removeAttr("disabled");
            document.getElementById('masterImgAdd').style.background="#00B7EE";
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



    /** 添加属性的操作 **/
    $(document).on('click','.feature .add',function(){
        var fea = "";
        var tabFea = document.getElementById("tabFea");
        var attrN = document.getElementsByName("attrN");
        var attrV = document.getElementsByName("attrV");
        var len = attrN.length;
        if (document.getElementById("tabFea").getElementsByTagName("tr").length>1) {
            //如果有未输入属性项则不能添加
            if (attrN[len-1].value=="" || attrV[len-1].value=="") {
                $(".feature .add").click(function(){});
            } else {
              $("<tr>").html('<td><input type="text" name="attrN"/></td><td><input type="text" name="attrV"/></td><td class="del">'+$('#del').val()+'</td>').appendTo($(".feature"));
            }
        } else { //如果全部删除完,点击直接添加
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
        var prods = "{";    //prods表存入数据
        var stocks = "[";    //stock表存入数据
        //必填项不能有空值
        if ($("#language").val=="" || $("#categorySubSelect").val()=="" || $("#bandSelect").val()=="" || $("#productName").val==""
            || $("#sourceArea").val=="" || $("#sellOnDate").val=="" || $("#sellOffDate").val=="") {
            isPost=false;
            alert("必填项不能为空");
        }


        var productColor = document.getElementsByName("productColor");
        var productSize = document.getElementsByName("productSize");
        var productAmount = document.getElementsByName("productAmount");
        var productPrice = document.getElementsByName("productPrice");
        var recommendPrice = document.getElementsByName("recommendPrice");
        var language = $("#language").val();
        var cateId = $("#categorySubSelect").val();
        var brandId = $("#bandSelect").val();
        var productName = $("#productName").val();
        var merchName = $("#merchName").val();
        var sourceArea = $("#sourceArea").val();
        var sellOnDate = $("#sellOnDate").val();
        var sellOffDate = $("#sellOffDate").val();
        var productDesc = $("#productDesc").val();
        if (productColor[productColor.length-1].value=="") {
            isPost = false;
            if (window.lang=="cn") $("#warn-color").text("颜色不能有空值");
            else $("#warn-color").text("color can't be null");
        }
        for(i=0;i<productSize.length;i++) {
            if (productSize[i].value=="") {
                isPost = false;
                if (window.lang=="cn") $("#warn-size").text("尺寸不能有空值");
                else $("#warn-size").text("size can't be null");
                break;
            }
        }
        //验证输入的库存量和价格符合规则
        for(i=0;i<productAmount.length;i++){
            if (!numberReg1.test(productAmount[i].value)) {
                isPost = false;
                if (window.lang=="cn") $("#warn-amount").text("库存量为正整数");
                else $("#warn-amount").text("product amount must be integer");
            }
            if (!numberReg2.test(productPrice[i].value) && !numberReg2.test(recommendPrice[i].value)) {
                isPost = false;
                if (window.lang=="cn") $("#warn-price").text("价格为整数或小数");
                else $("#warn-price").text("price is integer or decimal");
            }
        }
        if (sellOffDate < sellOnDate) {
           isPost = false;
           if (window.lang=="cn") $("#warn-date").text("结束日期必须大于开始日期");
           else $("#warn-date").text("End date mush late to start date");
        }

        var masterImg = "";
        var galleryM = document.getElementById("galleryM");
        var masterImgLen = galleryM.getElementsByTagName("input").length;
        if (masterImgLen<1) {
            isPost = false;
            if (window.lang=="cn") $("#warn-mastImg").text("请上传商品主图");
            else $("#warn-mastImg").text("please upload master image");
        } else {
            masterImg = galleryM.getElementsByTagName("input")[0].value;
        }
        var galleryD = document.getElementById("galleryD");
        var detailImgs = "[";
        var detInps = galleryD.getElementsByTagName("input");
        for(i=0;i<detInps.length;i++) {
            detailImgs = detailImgs + '\"' + detInps[i].value + '\"' + ",";
        }
        detailImgs = detailImgs.substring(0,detailImgs.length - 1) + "]";
        //遍历所有属性及属性值累加到隐藏域features中且属性名或值不能为空
        var features = "{";
        var tabFea = document.getElementById("tabFea");
        var attrN = document.getElementsByName("attrN");
        var attrV = document.getElementsByName("attrV");
        for(i=0; i<attrN.length; i++) {
            if (attrN[i].value=="" || attrV[i].value=="") {
                isPost = false;
                if (window.lang=="cn") $("#warn-attr").text("属性名或值不能有空");
                else $("#warn-attr").text("attribute or attribute value can't be value");
            }
            features = features + '\"' + attrN[i].value + '\"' + ":" + '\"' + attrV[i].value + '\"' +",";
        }
        features = features.substring(0,features.length - 1) + "}";
         //拼装prod数据
         var productColor="[";
         var productSize="[";
         var productAmount="";
         var productPrice="";
         var recommendPrice="";
         var previewImgs="[";
         var colors = document.getElementById("addColor").getElementsByTagName("input");
         for (i=0;i<colors.length;i++) {
            productColor += '\"' + colors[i].value + '\"' + ",";
         }
         productColor = productColor.substring(0,productColor.length - 1) + "]";
         var sizes = document.getElementById("addSize").getElementsByTagName("input");
         for (i=0;i<sizes.length;i++) {
            productSize += '\"' + sizes[i].value + '\"' + ",";
         }
         productSize = productSize.substring(0,productSize.length - 1) + "]";
         var preImgs = document.getElementById("preImgs");
         var prepics = preImgs.getElementsByClassName("imgk")[0].getElementsByTagName("div");;
         for(i=0;i<prepics.length;i++) {
            previewImgs += '\"' + prepics[i].getElementsByTagName("input")[0].value + '\"' + ",";
         }
         previewImgs = previewImgs.substring(0,previewImgs.length - 1) + "]";
        //获取数量价格表格中的数据 拼装stock数据
        var numpri = document.getElementById("numpri");
        var tr1 = numpri.getElementsByTagName("tr")[0].getElementsByTagName("td");
        var tr2 = numpri.getElementsByTagName("tr")[1].getElementsByTagName("td");
        var tr3 = numpri.getElementsByTagName("tr")[2].getElementsByTagName("td");
        var tr4 = numpri.getElementsByTagName("tr")[3].getElementsByTagName("td");
        var tr5 = numpri.getElementsByTagName("tr")[4].getElementsByTagName("td");
        var index = 0;
        var spancols = preImgs.getElementsByTagName("span");
        for(i=1;i<tr1.length;i++) {
            var stockPreImgs = "[";
            var stockColor = tr1[i].innerText;
            for(k=0;k<spancols.length;k++) {
                if (spancols[k].className=="ysfont" && spancols[k].innerText==tr1[i].innerText) {
                    var id = spancols[k].parentNode.parentNode.lastChild.id;
                    var pics;
                    if (id!=null) {
                        pics = document.getElementById(id).getElementsByTagName("div");
                        for(y=0;y<pics.length;y++) {
                             stockPreImgs += '\"' + pics[y].getElementsByTagName("input")[0].value + '\"' + ",";
                        }
                        break;
                    }
                }
            }
            stockPreImgs = stockPreImgs.substring(0,stockPreImgs.length - 1) + "]";
            var sizenum = tr1[i].colSpan;
            for(j=0;j<sizenum;j++) {
                productAmount =tr3[1].getElementsByTagName("input")[0].value;
                productPrice = tr4[1].getElementsByTagName("input")[0].value;
                recommendPrice = tr5[1].getElementsByTagName("input")[0].value;
                var stockSize = tr2[index].innerText;
                var stockAmount = tr3[index+1].getElementsByTagName("input")[0].value;
                var stockPrice = tr4[index+1].getElementsByTagName("input")[0].value;
                var stockRecoPrice = tr5[index+1].getElementsByTagName("input")[0].value;
                eval("var stockData"+index);
                //拼装成一条数据
                stockDataindex = "{";
                stockDataindex += '\"' + "productColor" + '\"' + ":" + '\"' + stockColor + '\"' + ",";
                stockDataindex += '\"' + "productSize" + '\"' + ":" + '\"' + stockSize + '\"' + ",";
                stockDataindex += '\"' + "productAmount" + '\"' + ":" + '\"' + stockAmount + '\"' + ",";
                stockDataindex += '\"' + "productPrice" + '\"' + ":" + '\"' + stockPrice + '\"' + ",";
                stockDataindex += '\"' + "recommendPrice" + '\"' + ":" + '\"' + stockRecoPrice + '\"' + ",";
                stockDataindex += '\"' + "previewImgs" + '\"' + ":" + stockPreImgs + ",";
                stockDataindex = stockDataindex.substring(0,stockDataindex.length - 1) + "}";
                stocks += stockDataindex + ",";
                index++;
            }
        }

        prods += '\"' + "language" + '\"' + ":" + '\"' + language + '\"' + ",";
        prods += '\"' + "cateId" + '\"' + ":" + '\"' + cateId + '\"' + ",";
        prods += '\"' + "brandId" + '\"' + ":" + '\"' + brandId + '\"' + ",";
        prods += '\"' + "productName" + '\"' + ":" + '\"' + productName + '\"' + ",";
        prods +=  '\"' + "productColor" + '\"' + ":" +  productColor + ",";
        prods +=  '\"' + "productSize" + '\"' + ":" +  productSize + ",";
        prods += '\"' + "merchName" + '\"' + ":" + '\"' + merchName + '\"' + ",";
        prods += '\"' + "sourceArea" + '\"' + ":" + '\"' + sourceArea + '\"' + ",";
        prods += '\"' + "sellOnDate" + '\"' + ":" + '\"' + sellOnDate + '\"' + ",";
        prods += '\"' + "sellOffDate" + '\"' + ":" + '\"' + sellOffDate + '\"' + ",";
        prods +=  '\"' + "productAmount" + '\"' + ":" + '\"' + productAmount + '\"' + ",";
        prods +=  '\"' + "productPrice" + '\"' + ":" + '\"' + productPrice + '\"' + ",";
        prods +=  '\"' + "recommendPrice" + '\"' + ":" + '\"' + recommendPrice + '\"' + ",";
        prods += '\"' + "masterImg" + '\"' + ":" + '\"' + masterImg + '\"' + ",";
        prods += '\"' + "previewImgs" + '\"' + ":" +  previewImgs + ",";
        prods += '\"' + "detailImgs" + '\"' + ":" +  detailImgs + ",";
        prods += '\"' + "productDesc" + '\"' + ":" + '\"' + productDesc + '\"' + ",";
        prods += '\"' + "features" + '\"' + ":" + features + ",";
        prods = prods.substring(0,prods.length - 1) + "}";
        stocks = stocks.substring(0,stocks.length - 1) + "]";
        console.log(prods);
        console.log(stocks);
        console.log(isPost);
        if (isPost) {
            $.ajax({
                type :  "POST",
                url : "/insertProducts",
                data : "prods=" + prods +"&stocks="+stocks,
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
                    setTimeout("location.href='/"+window.lang+"/prodCreate'", 3000);
//                    location.href="/prodCreate";
                }
            });
        }
    });

    $(document).on('click','.add1',function(){
        var publicity = $("#publicity").val();
        if (publicity != "") {
            $("#publicityTab").append("<tr><td>"+publicity+"</td></tr>");
            $("#publicity").val("");
        }
    });

    $(".add-goods").click(function(){
        var inventory = document.getElementById("inventory");
        var trs = inventory.getElementsByTagName("tr");
        var index = trs.length;
        var tds = trs[index-1].getElementsByTagName("td");
        var i = 1;
        //有空值不能添加
        for(i=1;i<tds.length;i++) {
            if (i <=8 && tds[i].getElementsByTagName("input")[0].value=="") break;
            if (i ==10 && trs[index-1].getElementsByClassName("list-img")[0].getElementsByTagName("div").length==1) break;
            if (i ==11 && trs[index-1].getElementsByClassName("preview-img")[0].getElementsByTagName("div").length==1) break;
        }
        if (i==14){
            var tr =  document.createElement("tr");

            var td1 = document.createElement("td");
            var input1 = document.createElement("input");
            input1.type = "radio";
            input1.name = "masterInvId";
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
            td10.appendChild(input);

            var td8 = document.createElement("td");
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
            var divZ = document.createElement("div");
            divZ.classList.add('area');
            divZ.innerText="韩国直邮";
            td8.appendChild(inputR1);
            td8.appendChild(divB);
            td8.appendChild(inputR2);
            td8.appendChild(divZ);

            var td11 = document.createElement("td");
            td11.classList.add('preview-img');
            var divM = document.createElement("div");
            divM.id = "galleryM";
            var spanAdd = document.createElement("span");
            spanAdd.classList.add("add");
            spanAdd.id = "preImgAddP" + index;
            spanAdd.innerText = "+";
            var inputF = document.createElement("input");
            inputF.type = "file";
            inputF.id = "M";
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

    /** 删除一条商品的操作 **/
    $(".inv").delegate(".del","click",function(){
        if (document.getElementById("inventory").getElementsByTagName("tr").length>2) {
            $(this).parent().remove();
        }
    });

});
