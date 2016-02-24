var jsFileShareContent = {};
 var pageEditStatus = false;
function ShowModal() {
    var sharedObject = {};

    var selected_items = [];
    $("#sort").find("tr").each(function(){
        var id = $(this).find("td:eq(1)").text();
        selected_items.push(id);
    })
    sharedObject.selected_items = selected_items;

    if (window.showModalDialog) {
        var retValue = showModalDialog("/topic/add/popup", sharedObject, "dialogWidth:1200px; dialogHeight:600px; dialogLeft:300px;");
        if (retValue) {
            UpdateFields(retValue);
        }
    }
    else {
        // for similar functionality in Opera, but it's not modal!
        var modal = window.open("/topic/add/popup", null, "width=1200,height=600,left=300,modal=yes,alwaysRaised=yes", null);
        modal.dialogArguments = sharedObject;
    }
}
function ShowModal1() {
    var sharedObject = {};
    sharedObject.flag = true;
    if (window.showModalDialog) {
        var retValue = showModalDialog("/topic/add/popup", sharedObject, "dialogWidth:1200px; dialogHeight:600px; dialogLeft:300px;");
        if (retValue) {
            UpdateFields(retValue);
        }
    }
    else {
        // for similar functionality in Opera, but it's not modal!
        var modal = window.open("/topic/add/popup", null, "width=1200,height=600,left=300,modal=yes,alwaysRaised=yes", null);
        modal.dialogArguments = sharedObject;
    }
}
function UpdateFields(obj) {
    var obj1 = obj.id;
    var index1 = $(".grid tbody").find("tr").length,
        index2 = $(".grid thead").find("tr").length;
    var index = index1 + index2;
    for (var i = 0; i < $(obj1).length; i++) {
        $(obj1).eq(i).prepend($("<td class='index'>" + (Number(index) + i) + "</td>")).appendTo($(".grid"));
    }

    $('#input_imgurl').val(obj.lable_id);
    if(obj.itemType == "普通"){
        $('#url-type').val("item");
    }
    if(obj.itemType == "拼购"){
        $('#url-type').val("pin");
    }
    if(obj.itemType == "多样化"){
        $('#url-type').val("vary");
    }
}
function previewImage1(obj, file) {

    var width;
    var height;
    var gallery = obj;
    var imageType = /image.*/;
    if (!file.type.match(imageType)) {
        throw "File Type must be an image";
    }
    var img = document.createElement("img");
    img.file = file;
    $(img).width('100%');
    $(img).height('100%');
    $(img).css({"float":"left"});
    $(gallery).append($(img));
    upload(gallery, file);
    // Using FileReader to display the image content
    var reader = new FileReader();
            reader.onload = (function (aImg) {
                return function (e) {
                    aImg.src = e.target.result;
                }
            })(img);
            reader.readAsDataURL(file);
   }

function upload(thumb, file, id) {
    var formdata = new FormData();
    formdata.append("photo", file);
    formdata.append("params", "minify");
    var http = new XMLHttpRequest();
    //var url = "http://172.28.3.18:3008/upload";
    var url  = window.uploadUrl;
    http.open("POST", url, true);
    http.onreadystatechange = function () {
        if (http.readyState == 4 && http.status == 200) {
            var data = JSON.parse(http.responseText);
            console.log(data.minify_url);
            var input = document.createElement("input");
            var img = new Image;
            img.onload = function(){
                 jsFileShareContent.labelImgWidth = img.width;
                 jsFileShareContent.labelImgHeight = img.height;
                 $(thumb).width(img.width);
                 $(thumb).height(img.height);
            }
            img.src = data.oss_prefix+data.oss_url;
            imgName = data.imgid;
            input.id = imgName.substr(0, imgName.lastIndexOf("."));
            input.type = "hidden";
            input.name = data.imgid;
            input.value = data.path;
            $(thumb).find("img").attr('src', data.oss_prefix+data.oss_url);
            $(thumb).append(input);
            alert(data.message);
        }
    }
    http.send(formdata);
}
$(function () {
    var fixHelperModified = function (e, tr) {
            var $originals = tr.children();
            var $helper = tr.clone();
            $helper.children().each(function (index) {
                $(this).width($originals.eq(index).width())
            });
            return $helper;
        },
        updateIndex = function (e, ui) {
            if ($("thead").find('tr').length == 2) {
                $('td.index', ui.item.parent()).each(function (i) {
                    $(this).html(i + 2);
                });
            } else {
                $('td.index', ui.item.parent()).each(function (i) {
                    $(this).html(i + 1);
                });
            }
        };
    $("#sort tbody").sortable({
        helper: fixHelperModified,
        stop: updateIndex
    }).disableSelection();

    $(document).on('change', '.upload1', function () {
        //var id = window.event.srcElement.id;
        var id = window.event.srcElement.id || window.event.target.id;
        var files = this.files;
        var li_dv = $(this).parent().parent();
        $(this).parent().css("display", "none");
        $(this).parents(".li-dv").find(".text").css("display", "block");
        console.log(li_dv);
        for (var i = 0; i < files.length; i++) {
            previewImage(li_dv, this.files[i], id);
        }
    });

//  upload-lable
    $(document).on('change', '#upload-label', function () {
        if ($(this).val()) {
            var files = this.files;
            var label_img = $('#dragon-container');
            $(label_img).find("img").remove();
        } else {
            var label_img = $('#dragon-container');
        }
        for (var i = 0; i < files.length; i++) {
            previewImage1(label_img, this.files[i]);
        }
    });

    $(document).on("click",".th-del",function(){
        //商品总数
        var itemCount = $("#sort").find("tr").length - 1;
        //被删除行的编号
        var delColNum = $(this).parents("tr").find("td:eq(0)").text();
        if(itemCount > delColNum){
            for(var i = parseInt(delColNum);i < itemCount;i++){
                var j = i + 1;
                $("#sort").find("tr:eq("+j+")").find("td:eq(0)").text(i);
            }
        }
        $(this).parents("tr").remove();

    })
    $("#getTemplate").click(function(){
        var sharedObject = {};
        var modal = window.open("/"+window.lang+"/topic/add/templates", null, "modal=yes,alwaysRaised=yes", null);
        modal.dialogArguments = sharedObject;

    });

    var trindex;
    $("table.grid tbody").on("dblclick","tr",function(e){
        if(pageEditStatus){
            var obj = e.target;
            if(obj.className=="th-del"){
                return;
            }
            if($(this).find("td").eq(2).text() == "普通" || $(this).find("td").eq(2).text() == "自定义"){
                trindex = $(".grid tbody tr").index($(this));
                $(".tableBlock").show();
                $(this).find("td").eq(11).remove();
                $(this).appendTo(".tableBlock tbody");
            }
        }
    })
    $(".tableBlock tbody").on("click","td:nth-of-type(9),td:nth-of-type(11)",function(e){
        changeText(e,this);
    })
    $(".tableok").click(function(){
        if(trindex==0){
            $("<td>").css({"background":"#ccc","cursor":"pointer"}).html("删除").addClass("th-del").appendTo($(".tableBlock tbody tr"));
            if($(".tableBlock tbody tr").find("td").eq(8).text() != $(".tableBlock tbody tr").find("td").eq(9).text()){
                $(".tableBlock tbody tr").find("td").eq(2).text("自定义");
            }
            $(".tableBlock tbody tr").prependTo($(".grid tbody"));

        }else{
            $("<td>").css({"background":"#ccc","cursor":"pointer"}).html("删除").addClass("th-del").appendTo($(".tableBlock tbody tr"));
            if($(".tableBlock tbody tr").find("td").eq(8).text() != $(".tableBlock tbody tr").find("td").eq(9).text()){
                $(".tableBlock tbody tr").find("td").eq(2).text("自定义");
            }
            $(".tableBlock tbody tr").insertAfter($(".grid>tbody>tr").eq(trindex-1));
        }
        $(".tableBlock").hide();
    })

    $(".tableok-cancel").click(function(){
        if(trindex==0){
            $("<td>").css({"background":"#ccc","cursor":"pointer"}).html("删除").addClass("th-del").appendTo($(".tableBlock tbody tr"));
            $(".tableBlock tbody tr").prependTo($(".grid tbody"));
        }else{
            $("<td>").css({"background":"#ccc","cursor":"pointer"}).html("删除").addClass("th-del").appendTo($(".tableBlock tbody tr"));
            $(".tableBlock tbody tr").insertAfter($(".grid>tbody>tr").eq(trindex-1));
        }
        $(".tableBlock").hide();
    })
})

//返回模板中选中的图片 Added by Tiffany Zhu
function updateThemeImg(obj){
    $("#themeImg").find("img").attr("src",window.url + obj.url);
    //$("#themeImg").css({"background-image":"url("+ obj.url +")","background-size":"cover"});
    var input = $("#themeImg").find("input");
    $(input).attr("id",obj.url);
    $(input).width(obj.width);
    $(input).height(obj.height);
}