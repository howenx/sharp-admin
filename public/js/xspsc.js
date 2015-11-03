$(function(){
    /*日历*/
    $('.form_datetime').datetimepicker({
        format: 'yyyy-mm-dd hh:ii',
        language:  'zh-CN',
        minuteStep:1,
        weekStart: 1,
        todayBtn:  1 ,
        autoclose: 1,
        todayHighlight: 1,
        keyboardNavigation:1,
        startView: 2,
        forceParse: 1,
        showMeridian: 1,
        minView:0,
        maxView:4
    });

    $('#datetimepicker').datetimepicker();

    /** 添加颜色 **/
    $(document).on('click','.yanse .add',function() {
        var color = $(this).parent().prev().children().first().children().first().val();
        //如果最后一项内容为空则不能添加
        if (color == "") {
            $('.yanse .add').click(function(){});
        } else
            $("<li>").html('<span><input type="text" class="colorIn"/></span><button type="button" class="close" aria-label="Close"><span aria-hidden="true">&times;</span></button>').insertBefore($(this).parent());
    });

    /** 颜色变化 添加到尺寸列表,数量价格表格和预览图区域中**/
    $(document).on('keyup','.colorIn',function() {
        var addColor = document.getElementById("addColor");
        var colors = addColor.getElementsByTagName("input");
        var len = colors.length;
        var addSize = document.getElementById("addSize");
        addSize.innerHTML = "";
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
            //每项颜色添加到尺寸列表中
            var div = document.createElement("div");
            var spancn = document.createElement("span");
            spancn.classList.add('l');
            var br1 = document.createElement("br");
            var br2 = document.createElement("br");
            spancn.innerText = colors[i].value;
            var ul = document.createElement("ul");
            ul.classList.add('l');
            var li1 = document.createElement("li");
            var spansn = document.createElement("span");
            var input = document.createElement("input");
            input.classList.add('sizeIn');
            var button = document.createElement("button");
            button.classList.add('close');
            $(button).append('<span>&times;</span>');
            var li2 = document.createElement("li");
            li2.style.border="0px";
            var spanadd = document.createElement("span");
            spanadd.classList.add('add');
            spanadd.innerText = "+";
            addSize.appendChild(div);
            spansn.appendChild(input);
            li1.appendChild(spansn);
            li1.appendChild(button);
            li2.appendChild(spanadd);
            ul.appendChild(li1);
            ul.appendChild(li2);
            div.appendChild(spancn);
            div.appendChild(br1);
            div.appendChild(br2);
            div.appendChild(ul);
            //颜色添加到数量价格表格中
            var td1 = document.createElement("td");
            var td2 = document.createElement("td");
            var td3 = document.createElement("td");
            var td4 = document.createElement("td");
            var td5 = document.createElement("td");
            td1.innerText = colors[i].value;
            td2.innerText = "";
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
            divn.classList.add('r');
            divn.style.paddingTop="20px";
            divn.style.paddingLeft="100px";
            divn.innerText= "("+$('#mostS').val()+")";
            label.appendChild(divx);
            label.appendChild(divn);
            var divcol = document.createElement("div");
            divcol.classList.add("col-sm-7");
            var divsctp = document.createElement("div");
            divsctp.classList.add("sctp");
            var buttonpre = document.createElement("button");
            buttonpre.classList.add('close');
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
            var inputpres = document.createElement("input");
            inputpres.type="hidden";
            inputpres.id="previewImgs"+i;
            inputpres.name="previewImgs";
            var inputfile = document.createElement("input");
            inputfile.type="file";
            inputfile.id="fileinputP"+i;
            inputfile.classList.add("hidden1");
            inputfile.setAttribute("accept", "image/gif, image/jpeg, image/webp, image/png");
            var divimgk = document.createElement("div");
            divimgk.classList.add("imgk");
            divimgk.classList.add("l");
            divimgk.id = "galleryP"+i;
            var divrad = document.createElement("div");
            divrad.classList.add("col-sm-1");
            divrad.classList.add("magrad");
            var inputrad = document.createElement("input");
            inputrad.type="radio";
            inputrad.name="mainPics";
            inputrad.style.marginLeft="40px";
            inputrad.style.marginTop="70px";
            var p = document.createElement("p");
            p.innerText = "设为首要预览图";
            divsctp1.appendChild(spancol);
            divsctp1.appendChild(a);
            divsctp1.appendChild(inputpres);
            divsctp1.appendChild(inputfile);
            divsctp.appendChild(buttonpre);
            divsctp.appendChild(divsctp1);
            divsctp.appendChild(divimgk);
            divrad.appendChild(inputrad);
            divrad.appendChild(p);
            divcol.appendChild(divsctp);
            divpre.appendChild(label);
            divpre.appendChild(divcol);
            divpre.appendChild(divrad);
            preImgs.appendChild(divpre);
        }
        numpri.appendChild(tr1);
        numpri.appendChild(tr2);
        numpri.appendChild(tr3);
        numpri.appendChild(tr4);
        numpri.appendChild(tr5);

    });


    /** 删除颜色 **/
    $(document).on('click','.yanse .close',function() {
        $(this).parent().remove();
        //删除尺寸列表中对应的颜色
        var thiscol = $(this).prev().children().first().val();
        var addSize = document.getElementById("addSize");
        var spans = addSize.getElementsByTagName("span");
        for(i=0;i<spans.length;i++) {
            if (spans[i].className=="l" && spans[i].innerText==thiscol) {
                spans[i].parentNode.remove();
                break;
            }
        }
        //删除数量价格表格中对应的颜色
        var numpri = document.getElementById("numpri");
        var tr1 = numpri.getElementsByTagName("tr")[0].getElementsByTagName("td");
        var tr2 = numpri.getElementsByTagName("tr")[1].getElementsByTagName("td");
        var index = 0;
        for(i=1;i<tr1.length;i++) {
            var sizenum = tr1[i].colSpan;
            if(tr1[i].innerText == thiscol) {
                $("table tr").eq(0).find("td").eq(i).remove();
                for(j=0;j<sizenum;j++) {
                     $("table tr").eq(1).find("td").eq(index).remove();
                     $("table tr").eq(2).find("td").eq(index+1).remove();
                     $("table tr").eq(3).find("td").eq(index+1).remove();
                     $("table tr").eq(4).find("td").eq(index+1).remove();
                }
                break;
            }
            for(j=0;j<sizenum;j++) {
                index++;
            }
        }
        //删除预览图区域中对应的颜色
        var preImgs = document.getElementById("preImgs");
        spancols = preImgs.getElementsByTagName("span");
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
            $("<li>").html('<span><input type="text" class="sizeIn"/></span><button type="button" class="close" aria-label="Close"><span aria-hidden="true">&times;</span></button>').insertBefore($(this).parent());
    });

    /** 尺寸变化,添加到数量价格表格中 **/
    $(document).on('keyup','.sizeIn',function() {
        var divs = document.getElementById("addSize").getElementsByTagName("div");
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
        var ul = document.getElementById("addSize").getElementsByTagName("ul");
        for(j=0;j<divs.length;j++) {
             var scn = divs[j].getElementsByTagName("span")[0].innerText; //取得颜色值
             var li = divs[j].getElementsByTagName("li");
             var snum = li.length-1; //每种颜色的尺寸数
             var td = document.createElement("td");
             td.setAttribute('colspan',snum);
             td.innerText= scn;
             tr1.appendChild(td);
             for (k=0;k<snum;k++) { //取得每项尺寸
                si = li[k].getElementsByTagName("span")[0].getElementsByTagName("input")[0].value;
                var tdsi = document.createElement("td");
                var tdam = document.createElement("td");
                var tdpr = document.createElement("td");
                var tdspr = document.createElement("td");
                var inp1 = document.createElement("input");
                var inp2 = document.createElement("input");
                var inp3 = document.createElement("input");
                tdsi.innerText = si;
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
        var thiscol = $(this).parent().parent().parent().children().first().text();
        $(this).parent().remove();
        //删除数量价格表格中对应的尺寸
        var numpri = document.getElementById("numpri");
        var tr1 = numpri.getElementsByTagName("tr")[0].getElementsByTagName("td");
        var tr2 = numpri.getElementsByTagName("tr")[1].getElementsByTagName("td");
        for(i=0;i<tr2.length;i++) {
            if($(this).prev().children().first().val()==tr2[i].innerText) {
                $("table tr").eq(1).find("td").eq(i).remove();
                $("table tr").eq(2).find("td").eq(i+1).remove();
                $("table tr").eq(3).find("td").eq(i+1).remove();
                $("table tr").eq(4).find("td").eq(i+1).remove();
            }
        }
        for(i=1;i<tr1.length;i++) {
            if (thiscol==tr1[i].innerText) {
                var colSpan = tr1[i].colSpan;
                if (colSpan==1) {
                   $("table tr").eq(0).find("td").eq(i).remove();
                } else {
                    tr1[i].setAttribute('colspan',colSpan-1);
                    break;
                }
            }
        }
    });

    $(document).on('click','.fdel .close',function() {
        $(this).parents(".fdel").remove();
    });

    /** 上传图片操作,对动态添加的标签元素,点击移除的操作 **/
    $(document).on('click','.imgyl>.close',function(){
        $(this).parent().remove();

        //没有商品主图时,上传按钮置为可点击且颜色恢复
        if (document.getElementById("galleryM").getElementsByTagName("div").length==0) {
        $("#fileinputM").removeAttr("disabled");
        document.getElementById('masterImgAdd').style.background="#00B7EE";
        }

//        //商品预览图小于6张时恢复上传功能
//        if (document.getElementById("gallery"+id).getElementsByTagName("div").length<18) {
//        $("#fileinput"+id).removeAttr("disabled");
//        document.getElementById("preImgAdd"+id).style.background="#00B7EE";
//        }
    });

    /** 添加属性的操作 **/
    $(".table .add").click(function(){
        var fea = "";
        var tabFea = document.getElementById("tabFea");
        var attrN = document.getElementsByName("attrN");
        var attrV = document.getElementsByName("attrV");
        var len = attrN.length;
        if (document.getElementById("tabFea").getElementsByTagName("tr").length>1) {
            //如果有未输入属性项则不能添加
            if (attrN[len-1].value=="" || attrV[len-1].value=="") {
                $(".table .add").click(function(){});
            } else {
              $("<tr>").html('<td><input type="text" name="attrN"/></td><td><input type="text" name="attrV"/></td><td class="del">'+$('#del').val()+'</td>').appendTo($(".table"));
            }
        } else { //如果全部删除完,点击直接添加
             $("<tr>").html('<td><input type="text" name="attrN"/></td><td><input type="text" name="attrV"/></td><td class="del">'+$('#del').val()+'</td>').appendTo($(".table"));
        }
    });

    /** 删除属性的操作 **/
    $(".table").delegate(".del","click",function(){
        $(this).parent().remove();
    });

});