 $(function(){
    var ids = [];
    $(".grid td.index-none").each(function(){
        ids.push($(this).html());
    });
    var fixHelperModified = function (e, tr) {
        var $originals = tr.children();
        var $helper = tr.clone();
        $helper.children().each(function (index) {
            $(this).width($originals.eq(index).width());
        });
        return $helper;
    },
    updateIndex = function (e, ui) {
        $('td.index', ui.item.parent()).each(function (i) {
            $(this).html(i+1);
        });
        $('td.index-none', ui.item.parent()).each(function (i) {
            $(this).html(ids[i]);
        });
    };

    $("#sort tbody").sortable({
        helper: fixHelperModified,
        stop: updateIndex
    }).disableSelection();

    var idOld,idNew,idNoneOld,idNoneNew;
    $(".grid").on("dblclick","tr",function(){
        if($(".oldH").find(".index").html()==""){
            var themeinfo = {};
            //themeinfo.themeAll = $(".grid td.index").length;
            themeinfo.index = $(this).find(".index").html();
            themeinfo.index_none = $(this).find(".index-none").html();
            themeinfo.themeId = $(this).find(".themeId").html();
            themeinfo.themeType = $(this).find(".themeType").html();
            themeinfo.themeImg = $(this).find(".themeImg").html();

            $(".oldH").find(".index").html(themeinfo.index);
            $(".oldH").find(".themeId").html(themeinfo.themeId);
            $(".oldH").find(".themeType").html(themeinfo.themeType);
            $(".oldH").find(".themeImg").html(themeinfo.themeImg);

            idOld = themeinfo.index;
            idNoneOld = themeinfo.index_none;
        }else{
            var themeinfo = {};
            //themeinfo.themeAll = $(".grid td.index").length;
            themeinfo.index = $(this).find(".index").html();
            themeinfo.index_none = $(this).find(".index-none").html();
            themeinfo.themeId = $(this).find(".themeId").html();
            themeinfo.themeType = $(this).find(".themeType").html();
            themeinfo.themeImg = $(this).find(".themeImg").html();

            $(".newH").find(".index").html(themeinfo.index);
            $(".newH").find(".themeId").html(themeinfo.themeId);
            $(".newH").find(".themeType").html(themeinfo.themeType);
            $(".newH").find(".themeImg").html(themeinfo.themeImg);

            idNew = themeinfo.index;
            idNoneNew = themeinfo.index_none;
        }

    });
    $(".exchange").click(function(){
        if($(".oldH").find(".index").html()==""||$(".newH").find(".index").html()==""){
            return false;
        }
        var htmlOld = $(".oldH").html();
        var htmlNew = $(".newH").html();
        $(".oldH").html(htmlNew);
        $(".newH").html(htmlOld);
        if($(".oldH").find(".inde").length===1){
            $(".oldH").find(".index").html(idOld);
            $(".newH").find(".index").html(idNew);
        }else{
            $(".oldH").find(".index").html(idOld);
            $(".newH").find(".index").html(idNew);
        }

        var newhtml,oldhtml,newindex,oldindex;
        $(".grid tbody tr").each(function(){
            if($(this).children("td.index").html()===idOld){
                $(this).find("td.index").html(idNew);
                $(this).find("td.index-none").html(idNoneNew);
                oldhtml = $(this).html();
                oldindex = $(".grid tbody tr").index($(this));
                console.log(oldindex);
            }else if($(this).children("td.index").html()===idNew){
                $(this).find("td.index").html(idOld);
                $(this).find("td.index-none").html(idNoneOld);
                newhtml = $(this).html();
                newindex = $(".grid tbody tr").index($(this));
                console.log(newindex);
            }else if(ids.indexOf(idNew)==-1){
                return false;
            }
        });
        $(".grid tbody tr").each(function(i){
            if(i===oldindex){
                $(this).html(newhtml);
            }else if(i===newindex){
                $(this).html(oldhtml);
            }
        })
    })

    $("#themeAll").html($("#sort").find("tr").length -1);

    //修改之前的数据
    var beforeHtml = $("#sort").html().toString();
    var beforeSortThemes = [];
    $("#sort").find("tr").each(function(){
        if($(this).find("td").eq(1).text() != "" && $(this).find("td").eq(2).text() != null){
            var themeObject = new Object();
            themeObject.sortNu = $(this).find("td").eq(1).text();
            themeObject.id = $(this).find("td").eq(2).text();
            beforeSortThemes.push(themeObject);
        }
    })

    //主题排序  保存
    $(document).on("click","#js-sortNu-submit",function(){
        var isPost = true;
        var sortedHtml = $("#sort").html().toString();
        if(beforeHtml == sortedHtml){
            isPost = false;
            $('#js-userinfo-error').text('主题顺序未改变!').css('color', '#c00');
            setTimeout("$('#js-userinfo-error').text('').css('color', '#2fa900')",3000);
            return false;
        }
        var editThemes = [];
        $("#sort").find("tr").each(function(){
            var editObject = new Object();
            if($(this).find("td").eq(1).text() != "" && $(this).find("td").eq(2).text() != null){
                var index = $(this).index();
                var object = beforeSortThemes[index];
                if(object.id != $(this).find("td").eq(2).text()){
                    editObject.sortNu = $(this).find("td").eq(1).text();
                    editObject.newId = $(this).find("td").eq(2).text();
                    editObject.oldId = object.id;
                    editObject.num = $(this).find("td").eq(0).text();
                    editThemes.push(editObject);
                }
            }
        })

        var confirmContent = "";
        for(i=0;i<editThemes.length;i++){
            var object = editThemes[i];
            confirmContent = confirmContent + object.newId + "-->" + object.num + "\n";
        }
        confirmContent + "\n" + "确定修改吗?";
        var a = confirm(confirmContent);
        if(!a){
            isPost = false;
            return false;
        }

        if(isPost){
            $.ajax({
                type :  "POST",
                url : "/topic/sort/save",
                contentType: "application/json; charset=utf-8",
                data : JSON.stringify(editThemes),
                error : function(request) {
                    if (window.lang = 'cn') {
                        alert("保存失败!");
                    } else {
                        alert("Save error!");
                    }
                },
                success: function(data) {
                    if (window.lang = 'cn') {
                        alert("保存成功!");
                    } else {
                        alert("Save Success!");
                    }
                    setTimeout("location.href='/"+window.lang+"/topic/sort'", 3000);
                }
            });
        }
    })
    //主题排序  取消
    $(document).on("click","#js-sortNu-cancel",function(){
        setTimeout("location.href='/"+window.lang+"/topic/sort'", 300);
    })

 })