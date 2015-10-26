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

    //添加属性的操作
    $(".add").click(function(){
        var fea = "";
        var tabFea = document.getElementById("tabFea");
        var attrN = document.getElementsByName("attrN");
        var attrV = document.getElementsByName("attrV");
        var len = attrN.length;
        if (document.getElementById("tabFea").getElementsByTagName("tr").length>1) {
            //如果有未输入属性项则不能添加
            if (attrN[len-1].value=="" || attrV[len-1].value=="") {
                $(".add").click(function(){
                });
            } else {
              $("<tr>").html('<td><input type="text" name="attrN"/></td><td><input type="text" name="attrV"/></td><td class="del">删除</td>').appendTo($(".table"));
            }
        } else {
             $("<tr>").html('<td><input type="text" name="attrN"/></td><td><input type="text" name="attrV"/></td><td class="del">删除</td>').appendTo($(".table"));
        }
    });

    $(".table").delegate(".del","click",function(){
        //当只有一行属性时不能删除
//        if (document.getElementById("tabFea").getElementsByTagName("tr").length==2) {
//             $(".del").click(function(){
//             });
//        } else {
             $(this).parent().remove();
//        }
    });

//    $(".imgyl>.close").click(function(){
//      alert("qw");
//        $(this).parent().remove();
//        alert("123");
//    })

    //对动态添加的标签元素,点击移除的操作
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

    $(document).on('click','.yanse .close,.size .close',function() {
        $(this).parent().remove();
    })
    $(document).on('click','.yanse .add,.size .add',function() {
        $("<li>").html('<span><input type="text"/></span><button type="button" class="close" aria-label="Close"><span aria-hidden="true">&times;</span></button>').insertBefore($(this).parent());
    })
    $(document).on('click','.fdel .close',function() {
        $(this).parents(".fdel").remove();
    })
});