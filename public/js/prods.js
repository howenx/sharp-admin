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

    /** 颜色变化 添加到数量价格表格和预览图区域中**/
    $(document).on('keyup','.colorIn',function() {
        var addColor = document.getElementById("addColor");
        var colors = addColor.getElementsByTagName("input");
        var len = colors.length;
        var numpri = document.getElementById("numpri");
        numpri.innerHTML = "";
        var preImgs = document.getElementById("preImgs");
        preImgs.innerHTML = "";
        var tr1 = document.createElement("tr");
        var tr2 = document.createElement("tr");
        var tr3 = document.createElement("tr");
        var tr4 = document.createElement("tr");
        var tr5 = document.createElement("tr");
        tr1.innerHTML = '<td rowspan="2">\\</td>';
        tr2.innerHTML = '';
        tr3.innerHTML = '<td >'+$('#amount').val()+'</td>';
        tr4.innerHTML = '<td >'+$('#pri').val()+'</td>';
        tr5.innerHTML = '<td >'+$('#spri').val()+'</td>';
        for(i=0; i<len; i++) {
            //颜色添加到数量价格表格中
            var td1 = document.createElement("td");
            var td2 = document.createElement("td");
            var td3 = document.createElement("td");
            var td4 = document.createElement("td");
            var td5 = document.createElement("td");
            td1.innerText = colors[i].value;
            td2.innerText = " ";
            tr1.appendChild(td1);
            tr2.appendChild(td2);
            tr3.appendChild(td3);
            tr4.appendChild(td4);
            tr5.appendChild(td5);
            //颜色添加到预览图中
            var divpre = document.createElement("div");
            divpre.classList.add('form-group');
            divpre.classList.add('fdel');
            var label = document.createElement("label");
            label.classList.add('col-sm-3');
            label.classList.add('control-label');
            var divx = document.createElement("div");
            divx.classList.add('r');
            divx.innerText=$('#preIms').val();
            var divn = document.createElement("div");
            divn.style.paddingTop="20px";
            divn.style.paddingRight="5px";
            divn.innerText= "("+$('#mostS').val()+")";
            label.appendChild(divx);
            label.appendChild(divn);
            var divcol = document.createElement("div");
            divcol.classList.add("col-sm-7");
            var divsctp = document.createElement("div");
            divsctp.classList.add("sctp");
            var buttonpre = document.createElement("button");
            buttonpre.classList.add('close');
            buttonpre.classList.add('big');
            $(buttonpre).append('<span>&times;</span>');
            var divsctp1 = document.createElement("div");
            divsctp1.classList.add("sctp1");
            divsctp1.classList.add("l");
            var spancol = document.createElement("span");
            spancol.classList.add("ysfont");
            spancol.innerText=colors[i].value;
            var a = document.createElement("a");
            a.setAttribute('id',"preImgAddP" + i);
            a.innerText=$('#addPic').val();
            var inputfile = document.createElement("input");
            inputfile.type="file";
            inputfile.id="P"+i;
            inputfile.classList.add("hidden1");
            inputfile.setAttribute("accept", "image/gif, image/jpeg, image/webp, image/png");
            var divimgk = document.createElement("div");
            divimgk.classList.add("imgk");
            divimgk.classList.add("l");
            divimgk.id = "galleryP"+i;
            var inputpres = document.createElement("input");
            inputpres.type="hidden";
            inputpres.id="previewImgs"+i;
            inputpres.name="previewImgs";
            var p = document.createElement("p");
            p.style.marginTop="70px";
            p.innerText = "(如不需上传图片可移除)";
            divsctp1.appendChild(spancol);
            divsctp1.appendChild(a);
            divsctp1.appendChild(inputfile);
            divsctp.appendChild(buttonpre);
            divsctp.appendChild(divsctp1);
            divsctp.appendChild(divimgk);
            divcol.appendChild(divsctp);
            divimgk.appendChild(inputpres);
            divpre.appendChild(label);
            divpre.appendChild(divcol);
            divpre.appendChild(p);
            preImgs.appendChild(divpre);
        }
        numpri.appendChild(tr1);
        numpri.appendChild(tr2);
        numpri.appendChild(tr3);
        numpri.appendChild(tr4);
        numpri.appendChild(tr5);

    });

    /** 删除颜色 **/
    $(document).on('click','.color .close',function() {
        $(this).parent().remove();
        //删除数量价格表格中对应的颜色
        var thiscol = $(this).prev().children().first().val();
        var addSize = document.getElementById("addSize");
        var sizes = addSize.getElementsByTagName("input");
        var numpri = document.getElementById("numpri");
        var tr1 = numpri.getElementsByTagName("tr")[0].getElementsByTagName("td");
        var tr2 = numpri.getElementsByTagName("tr")[1].getElementsByTagName("td");
        var index = 0;
        for(i=1;i<tr1.length;i++) {
            if(tr1[i].innerText == thiscol) {
                $("table tr").eq(0).find("td").eq(i).remove();
                for(j=0;j<sizes.length;j++) {
                     $("table tr").eq(1).find("td").eq(index).remove();
                     $("table tr").eq(2).find("td").eq(index+1).remove();
                     $("table tr").eq(3).find("td").eq(index+1).remove();
                     $("table tr").eq(4).find("td").eq(index+1).remove();
                }
                break;
            }
            for(j=0;j<sizes.length;j++) {
                index++;
            }
        }
        //删除预览图区域中对应的颜色
        var preImgs = document.getElementById("preImgs");
        var spancols = preImgs.getElementsByTagName("span");
        for(i=0;i<spancols.length;i++) {
            if (spancols[i].className=="ysfont" && spancols[i].innerText==thiscol) {
                spancols[i].parentNode.parentNode.parentNode.parentNode.remove();
                break;
            }
        }
    });

    /** 添加尺寸 **/
    $(document).on('click','.size .add',function() {
        var size = $(this).parent().prev().children().first().children().first().val();
        //如果最后一项内容为空则不能添加
        if (size == "") {
            $('.size .add').click(function(){});
        } else
            $("<li>").html('<span><input type="text" class="sizeIn" name="productSize"/></span><button type="button" class="close" aria-label="Close"><span aria-hidden="true">&times;</span></button>').insertBefore($(this).parent());
    });

    /** 尺寸变化,添加到数量价格表格中 **/
    $(document).on('keyup','.sizeIn',function() {
         var addSize = document.getElementById("addSize");
         var sizes = addSize.getElementsByTagName("input");
         var addColor = document.getElementById("addColor");
         var colors = addColor.getElementsByTagName("input");
        var numpri = document.getElementById("numpri");
        var tr1 = document.createElement("tr");
        var tr2 = document.createElement("tr");
        var tr3 = document.createElement("tr");
        var tr4 = document.createElement("tr");
        var tr5 = document.createElement("tr");
        var td = document.createElement("td");
        var tda = document.createElement("td");
        var tdp = document.createElement("td");
        var tdsp = document.createElement("td");
        numpri.innerHTML = "";
        td.setAttribute('rowspan',2);
        td.innerText="\\"
        tda.innerText=$('#amount').val();
        tdp.innerText=$('#pri').val();
        tdsp.innerText=$('#spri').val();
        tr1.appendChild(td);
        tr3.appendChild(tda);
        tr4.appendChild(tdp);
        tr5.appendChild(tdsp);
        for(j=0;j<colors.length;j++) {
             var colname = colors[j].value; //取得颜色值
             var td = document.createElement("td");
             td.setAttribute('colspan',sizes.length);
             td.innerText= colname;
             tr1.appendChild(td);
             for (k=0;k<sizes.length;k++) { //取得每项尺寸
                sizename = sizes[k].value;
                var tdsi = document.createElement("td");
                var tdam = document.createElement("td");
                var tdpr = document.createElement("td");
                var tdspr = document.createElement("td");
                var inp1 = document.createElement("input");
                var inp2 = document.createElement("input");
                var inp3 = document.createElement("input");
                inp1.name="productAmount";
                inp2.name="productPrice";
                inp3.name="recommendPrice";
                tdsi.innerText = sizename;
                tdam.appendChild(inp1);
                tdpr.appendChild(inp2);
                tdspr.appendChild(inp3);
                tr2.appendChild(tdsi);
                tr3.appendChild(tdam);
                tr4.appendChild(tdpr);
                tr5.appendChild(tdspr);
             }
        }
        numpri.appendChild(tr1);
        numpri.appendChild(tr2);
        numpri.appendChild(tr3);
        numpri.appendChild(tr4);
        numpri.appendChild(tr5);
    });

    /** 删除尺寸 **/
    $(document).on('click','.size .close',function() {
        size = $(this).prev().children().first().val();
        //删除数量价格表格中对应的尺寸
        var numpri = document.getElementById("numpri");
        var tr1 = numpri.getElementsByTagName("tr")[0].getElementsByTagName("td");
        var tr2 = numpri.getElementsByTagName("tr")[1].getElementsByTagName("td");
        len = tr2.length;
        for(i=1;i<tr1.length;i++) {
            var colSpan = tr1[i].colSpan;
            for(j=0;j<len;j++) {
                if (colSpan==1) $("table tr").eq(0).find("td").eq(1).remove();
                if(size==tr2[j].innerText) {
                    $("table tr").eq(1).find("td").eq(j).remove();
                    $("table tr").eq(2).find("td").eq(j+1).remove();
                    $("table tr").eq(3).find("td").eq(j+1).remove();
                    $("table tr").eq(4).find("td").eq(j+1).remove();
                    len -= 1;
                    break;
                }
            }
            if (colSpan != 1) tr1[i].setAttribute('colspan',colSpan-1);
            if (colSpan==1) {
                for(k=0;k<len;k++) {
                     $("table tr").eq(0).find("td").eq(1).remove();
                     $("table tr").eq(1).find("td").eq(0).remove();
                     $("table tr").eq(2).find("td").eq(1).remove();
                     $("table tr").eq(3).find("td").eq(1).remove();
                     $("table tr").eq(4).find("td").eq(1).remove();
                }
            }
        }
         $(this).parent().remove();
    });

    /** 上传图片操作,对动态添加的标签元素,点击移除的操作 **/
    $(document).on('click','.imgyl .close',function(){
        var id = $(this).parent().parent().attr("id");
        //没有商品主图时,上传按钮置为可点击且颜色恢复
        if (id=="galleryM" && document.getElementById(id).getElementsByTagName("div").length==1) {
            $("#M").removeAttr("disabled");
            document.getElementById('masterImgAdd').style.background="#00B7EE";
        }
        //商品预览图小于6张时恢复上传功能
        if (document.getElementById(id).getElementsByTagName("div").length==6) {
            id = id.substring(7,9);
            $("#"+id).removeAttr("disabled");
            document.getElementById('preImgAdd'+id).style.background="#00B7EE";
        }
        $(this).parent().remove();
    });

    /** 添加属性的操作 **/
    $(".qubian .add").click(function(){
        var fea = "";
        var tabFea = document.getElementById("tabFea");
        var attrN = document.getElementsByName("attrN");
        var attrV = document.getElementsByName("attrV");
        var len = attrN.length;
        if (document.getElementById("tabFea").getElementsByTagName("tr").length>1) {
            //如果有未输入属性项则不能添加
            if (attrN[len-1].value=="" || attrV[len-1].value=="") {
                $(".qubian .add").click(function(){});
            } else {
              $("<tr>").html('<td><input type="text" name="attrN"/></td><td><input type="text" name="attrV"/></td><td class="del">'+$('#del').val()+'</td>').appendTo($(".qubian"));
            }
        } else { //如果全部删除完,点击直接添加
             $("<tr>").html('<td><input type="text" name="attrN"/></td><td><input type="text" name="attrV"/></td><td class="del">'+$('#del').val()+'</td>').appendTo($(".qubian"));
        }
    });

    /** 删除属性的操作 **/
    $(".table").delegate(".del","click",function(){
        $(this).parent().remove();
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
        var tr=$("<tr>").html('<td><input type="radio"></td>' +
            '<td><input type="text"></td>' +
            '<td><input type="text"></td>' +
            '<td><input type="text"></td>' +
            '<td><input type="text"></td>' +
            '<td><input type="text"></td>' +
            '<td><input type="text"></td>' +
            '<td><input type="text"></td>' +
            '<td class="list-img"><span class="add">＋<input type="file"></span></td>' +
            '<td width="146" class="preview-img"><div><img class="main-img" src="images/1.jpg" alt="" width="40"><button type="button" class="close" aria-label="Close"><span aria-hidden="true">&times;</span></button></div>' + '  ' +
            '<div><img class="main-img" src="images/1.jpg" alt="" width="40"><button type="button" class="close" aria-label="Close"><span aria-hidden="true">&times;</span></button></div>' + '  ' +
            '<div><img class="main-img" src="images/1.jpg" alt="" width="40"><button type="button" class="close" aria-label="Close"><span aria-hidden="true">&times;</span></button></div>' + '  ' +
            '<div><img class="main-img" src="images/1.jpg" alt="" width="40"><button type="button" class="close" aria-label="Close"><span aria-hidden="true">&times;</span></button></div>' + '  ' +
            '<div><img class="main-img" src="images/1.jpg" alt="" width="40"><button type="button" class="close" aria-label="Close"><span aria-hidden="true">&times;</span></button></div>' + '  ' +
            '<div><img class="main-img" src="images/1.jpg" alt="" width="40"><button type="button" class="close" aria-label="Close"><span aria-hidden="true">&times;</span></button></div>' + '  ' +
            '<div class="add">＋<input type="file"></div></td>');
        $(this).siblings(".table").append(tr);
    });
});

