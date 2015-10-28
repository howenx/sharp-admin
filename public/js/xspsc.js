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

    //颜色全部添加完成的操作
    $(document).on('click','.yanse .complete',function() {
        //最后一项颜色为空点击操作无效
        var color = $(this).parent().prev().prev().children().first().children().first().val();
        if (color == "") {
            alert("颜色不能有空值!");
            $('.yanse .complete').click(function(){});
        } else {
            var addColor = document.getElementById("addColor");
            var colors = addColor.getElementsByTagName("input");
            var len = colors.length;
            var addSize = document.getElementById("addSize");
            addSize.innerHTML = "";
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
                var li3 = document.createElement("li");
                li3.style.border="0px";
                var spancomp = document.createElement("span");
                spancomp.classList.add('complete');
                spancomp.innerText = "";

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
                ul.appendChild(li3);
                li3.appendChild(spancomp);
            }

         }
    });

    //删除颜色,尺寸
    $(document).on('click','.yanse .close,.size .close',function() {
        $(this).parent().remove();
    });

    //颜色输入完成的操作,点击后添加到尺寸域内
    $(document).on('click','.yanse .complete',function() {

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


    $(document).on('click','.fdel .close',function() {
        $(this).parents(".fdel").remove();
    });


    //上传图片操作,对动态添加的标签元素,点击移除的操作
    $(document).on('click','.imgyl>.close',function(){
        $(this).parent().remove();
        //每次移除遍历所有input标签,其值累加到隐藏域masterImg,previewImgs,detailImgs中
        var masterImg= "";
        var previewImgs= "[";
        var detailImgs= "[";

        var div1 = document.getElementById("gallery1");
        var inps1 = div1.getElementsByTagName("input");
        for(i=0; i<inps1.length; i++) {
        masterImg = masterImg + inps1[i].value;
        }
        $('#masterImg').val(masterImg);

        var div2 = document.getElementById("gallery2");
        var inps2 = div2.getElementsByTagName("input");
        for(i=0; i<inps2.length; i++) {
        previewImgs = previewImgs + '\"' + inps2[i].value + '\"' + ",";
        }
        previewImgs = previewImgs.substring(0,previewImgs.length - 1) + "]";
        $('#previewImgs').val(previewImgs);

        var div3 = document.getElementById("gallery3");
        var inps3 = div3.getElementsByTagName("input");
        for(i=0; i<inps3.length; i++) {
        detailImgs = detailImgs + '\"' + inps3[i].value + '\"' + ",";
        }
        detailImgs = detailImgs.substring(0,detailImgs.length - 1) + "]";
        $('#detailImgs').val(detailImgs);

        //没有商品主图时,上传按钮置为可点击且颜色恢复
        if (document.getElementById("gallery1").getElementsByTagName("div").length==0) {
        $("#fileinput1").removeAttr("disabled");
        document.getElementById('masterImgAdd').style.background="#00B7EE";
        }

        //商品预览图小于6张时恢复上传功能
        if (document.getElementById("gallery2").getElementsByTagName("div").length<6) {
        $("#fileinput2").removeAttr("disabled");
        document.getElementById('preImgAdd').style.background="#00B7EE";
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
              $("<tr>").html('<td><input type="text" name="attrN"/></td><td><input type="text" name="attrV"/></td><td class="del">'+document.getElementById("del").value+'</td>').appendTo($(".table"));
            }
        } else { //如果全部删除完,点击直接添加
             $("<tr>").html('<td><input type="text" name="attrN"/></td><td><input type="text" name="attrV"/></td><td class="del">'+document.getElementById("del").value+'</td>').appendTo($(".table"));
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