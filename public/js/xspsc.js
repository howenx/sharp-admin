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

    //添加颜色
    $(document).on('click','.yanse .add',function() {
        var color = $(this).parent().prev().children().first().children().first().val();
        //如果最后一项内容为空则不能添加
        if (color == "") {
            $('.yanse .add').click(function(){});
        } else
            $("<li>").html('<span><input type="text"/></span><button type="button" class="close" aria-label="Close"><span aria-hidden="true">&times;</span></button>').insertBefore($(this).parent());
    });

    //颜色全部添加完成的操作,点击后添加到尺寸域,和预览图域内
    $(document).on('click','.yanse .complete',function() {
        //最后一项颜色为空点击操作无效
        var color = $(this).parent().prev().prev().children().first().children().first().val();
        if (color == "") {
            alert("颜色不能有空值!");
            $('.yanse .complete').click(function(){});
        } else {
            /** 添加尺寸**/
            var addColor = document.getElementById("addColor");
            var colors = addColor.getElementsByTagName("input");
            var len = colors.length;
            var addSize = document.getElementById("addSize");
            addSize.innerHTML = "";
            var preImgs = document.getElementById("preImgs");
            preImgs.innerHTML="";
            for(i=0; i<len; i++) {
                //每一项颜色值,添加一项对应的尺寸列表
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
                var button = document.createElement("button");
                button.classList.add('close');
                $(button).append('<span>&times;</span>');
                var li2 = document.createElement("li");
                li2.style.border="0px";
                var spanadd = document.createElement("span");
                spanadd.classList.add('add');
                spanadd.innerText = "+";
                addSize.appendChild(div);
                div.appendChild(spancn);
                div.appendChild(br1);
                div.appendChild(br2);
                div.appendChild(ul);
                ul.appendChild(li1);
                li1.appendChild(spansn);
                spansn.appendChild(input);
                li1.appendChild(button);
                ul.appendChild(li2);
                li2.appendChild(spanadd);
                //在最后一项增加完成按钮
                if (i== len -1) {
                    var p = document.createElement("p");
                    p.style.marginTop="60px";
                    p.style.marginLeft="10px";
                    var spancomp = document.createElement("span");
                    spancomp.classList.add('complete');
                    spancomp.innerText = $('#complete').val();
                    ul.appendChild(p);
                    p.appendChild(spancomp);
                }

                /** 添加预览图 **/
                var divpre= document.createElement("div");
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
                divcol.classList.add("col-sm-9");
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
                divsctp1.appendChild(spancol);
                divsctp1.appendChild(a);
                divsctp1.appendChild(inputpres);
                divsctp1.appendChild(inputfile);
                divsctp.appendChild(buttonpre);
                divsctp.appendChild(divsctp1);
                divsctp.appendChild(divimgk);
                divcol.appendChild(divsctp);
                divpre.appendChild(label);
                divpre.appendChild(divcol);
                preImgs.appendChild(divpre);
            }
         }
    });

    //删除颜色,尺寸
    $(document).on('click','.yanse .close,.size .close',function() {
        $(this).parent().remove();
    });

    //添加尺寸
    $(document).on('click','.size .add',function() {
        var size = $(this).parent().prev().children().first().children().first().val();
        //如果最后一项内容为空则不能添加
        if (size == "") {
            $('.size .add').click(function(){});
        } else
            $("<li>").html('<span><input type="text"/></span><button type="button" class="close" aria-label="Close"><span aria-hidden="true">&times;</span></button>').insertBefore($(this).parent());
    });

    //尺寸全部添加完成的操作,点击后添加到数量价格域内
    $(document).on('click','.size .complete',function() {
        //遍历每一项颜色的最后一项尺寸,为空点击完成操作无效
        var divs = document.getElementById("addSize").getElementsByTagName("div");
        for(i=0;i<divs.length;i++) {
            inps = divs[i].getElementsByTagName("input");
            if (inps.length == 0) { //尺寸全部删除完的情况
               break;
            } else if (inps[inps.length-1].value == "") { //最后一个尺寸项有空值
                break;
            } else if (i == divs.length-1) {
                //alert("可以添加了~_~");
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
            }
        }
    });



    $(document).on('click','.fdel .close',function() {
        $(this).parents(".fdel").remove();
    });


    //上传图片操作,对动态添加的标签元素,点击移除的操作
    $(document).on('click','.imgyl>.close',function(){
        $(this).parent().parent().remove();
        //每次移除遍历所有input标签,其值累加到隐藏域masterImg,previewImgs,detailImgs中
        var masterImg= "";
        var previewImgs= "[";
        var detailImgs= "[";

        var divM = document.getElementById("galleryM");
        var inpsM = divM.getElementsByTagName("input");
        for(i=0; i<inpsM.length; i++) {
        masterImg = masterImg + inps[i].value;
        }
        $('#masterImg').val(masterImg);


        var prevImgs = document.getElementById("preImgs");
        var preinpus = prevImgs.getElementsByTagName("input");
        for(i=0;i<preinpus.length;i++) {
            if (preinpus[i].type=="file") {
                var id = "P"+ Math.floor(i/2);
                var div = document.getElementById("gallery"+id);
                var inps = div.getElementsByTagName("input");
                for(i=0; i<inps.length; i++) {
                    previewImgs = previewImgs + '\"' + inpsP[i].value + '\"' + ",";
                }
                previewImgs = previewImgs.substring(0,previewImgs.length - 1) + "]";
                $('#previewImgs').val(previewImgs);
            }
        }

        var divD = document.getElementById("galleryD");
        var inpsD = divD.getElementsByTagName("input");
        for(i=0; i<inpsD.length; i++) {
        detailImgs = detailImgs + '\"' + inpsD[i].value + '\"' + ",";
        }
        detailImgs = detailImgs.substring(0,detailImgs.length - 1) + "]";
        $('#detailImgs').val(detailImgs);

        //没有商品主图时,上传按钮置为可点击且颜色恢复
        if (document.getElementById("galleryM").getElementsByTagName("div").length==0) {
        $("#fileinputM").removeAttr("disabled");
        document.getElementById('masterImgAdd').style.background="#00B7EE";
        }

        //商品预览图小于6张时恢复上传功能
        if (document.getElementById("gallery"+id).getElementsByTagName("div").length<18) {
        $("#fileinput"+id).removeAttr("disabled");
        document.getElementById("preImgAdd"+id).style.background="#00B7EE";
        }
    });

    //添加属性的操作
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

    //删除属性的操作
    $(".table").delegate(".del","click",function(){
        //当只有一行属性时不能删除
//        if (document.getElementById("tabFea").getElementsByTagName("tr").length==2) {
//             $(".del").click(function(){});
//        } else {
             $(this).parent().remove();
//        }
    });

});