$(function(){
    /*日历*/
    $('.form_datetime').datetimepicker({
        format: 'yyyy-mm-dd hh:mm',
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
    ////editor
    //$('#wysiwyg').wysiwyg({
    //    controls: {
    //        bold          : { visible : true },
    //        italic        : { visible : true },
    //        underline     : { visible : true },
    //        strikeThrough : { visible : true },
    //
    //        justifyLeft   : { visible : true },
    //        justifyCenter : { visible : true },
    //        justifyRight  : { visible : true },
    //        justifyFull   : { visible : true },
    //
    //        indent  : { visible : true },
    //        outdent : { visible : true },
    //
    //        subscript   : { visible : true },
    //        superscript : { visible : true },
    //
    //        undo : { visible : true },
    //        redo : { visible : true },
    //
    //        insertOrderedList    : { visible : true },
    //        insertUnorderedList  : { visible : true },
    //        insertHorizontalRule : { visible : true },
    //
    //        h4: {
    //            visible: true,
    //            className: 'h4',
    //            command: ($.browser.msie || $.browser.safari) ? 'formatBlock' : 'heading',
    //            arguments: ($.browser.msie || $.browser.safari) ? '<h4>' : 'h4',
    //            tags: ['h4'],
    //            tooltip: 'Header 4'
    //        },
    //        h5: {
    //            visible: true,
    //            className: 'h5',
    //            command: ($.browser.msie || $.browser.safari) ? 'formatBlock' : 'heading',
    //            arguments: ($.browser.msie || $.browser.safari) ? '<h5>' : 'h5',
    //            tags: ['h5'],
    //            tooltip: 'Header 5'
    //        },
    //        h6: {
    //            visible: true,
    //            className: 'h6',
    //            command: ($.browser.msie || $.browser.safari) ? 'formatBlock' : 'heading',
    //            arguments: ($.browser.msie || $.browser.safari) ? '<h6>' : 'h6',
    //            tags: ['h6'],
    //            tooltip: 'Header 6'
    //        },
    //
    //        cut   : { visible : true },
    //        copy  : { visible : true },
    //        paste : { visible : true },
    //        html  : { visible: true },
    //        increaseFontSize : { visible : true },
    //        decreaseFontSize : { visible : true },
    //        exam_html: {
    //            exec: function() {
    //                this.insertHtml('<abbr title="exam">Jam</abbr>');
    //                return true;
    //            },
    //            visible: true
    //        }
    //    }
    //});
    //self
    $(".add").click(function(){
        $("<tr>").html('<td><input type="text" name="attrN"/></td><td><input type="text" name="attrV"/></td><td class="del">删除</td>').appendTo($(".table"));
        //如果有未输入属性项则不能添加
        var fea = "";
        var tabFea = document.getElementById("tabFea");
        var attrN = document.getElementsByName("attrN");
        var attrV = document.getElementsByName("attrV");
        for(i=0; i<attrN.length; i++) {
            if (attrN[i].value=="" || attrV[i].value=="") {
                $("#add").attr({"disabled":true});
            }
        }
    });

    $(".table").delegate(".del","click",function(){
        $(this).parent().remove();
    });

//    $(".imgyl>.close").click(function(){
//      alert("qw");
//        $(this).parent().remove();
//        alert("123");
//    })

    $(document).on('click','.imgyl>.close',function(){
      $(this).parent().remove();
      //每次移除遍历所有input标签,其值累加到隐藏域masterImg,previewImgs,detailImgs中
      var masterImg= "";
      var previewImgs= "";
      var detailImgs= "";

      var div1 = document.getElementById("gallery1");
      var inps1 = div1.getElementsByTagName("input");
      for(i=0; i<inps1.length; i++) {
        masterImg = masterImg + inps1[i].value + ",";
      }
      $('#masterImg').val(masterImg);

      var div2 = document.getElementById("gallery2");
      var inps2 = div2.getElementsByTagName("input");
      for(i=0; i<inps2.length; i++) {
        previewImgs = previewImgs + inps2[i].value + ",";
      }
      $('#previewImgs').val(previewImgs);

      var div3 = document.getElementById("gallery3");
      var inps3 = div3.getElementsByTagName("input");
      for(i=0; i<inps3.length; i++) {
        detailImgs = detailImgs + inps3[i].value + ",";
      }
      $('#detailImgs').val(detailImgs);


      //没有商品主图,上传按钮置为可点击
      if ($('#masterImg').val() == "") {
        $("#fileinput1").removeAttr("disabled");
        document.getElementById('masterImgAdd').style.background="#00B7EE";
      }


    });

});