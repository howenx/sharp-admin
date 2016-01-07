var jsFileShareContent = {};
function ShowModal() {
    var sharedObject = {};

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
    var index1 = $(".table tbody").find("tr").length,
        index2 = $(".table thead").find("tr").length;
    var index = index1 + index2;
    for (var i = 0; i < $(obj1).length; i++) {
        $(obj1).eq(i).prepend($("<td class='index'>" + (Number(index) + i) + "</td><td><input type='radio'  name='goods-main'></td>")).appendTo($(".table"));
    }

    $('#input_imgurl').val(obj.lable_id);
}

function previewImage(obj, file, id) {
    var gallery = obj;
    var imageType = /image.*/;

    if (!file.type.match(imageType)) {
        throw "File Type must be an image";
    }

    var thumb = document.createElement("a");
    thumb.classList.add('track');
    var img = document.createElement("img");
    img.classList.add('main-img');
    img.file = file;
    $(img).attr('data-index', '6');
    $(thumb).append($(img));
    $(gallery).append($(thumb));
    $("<button>").addClass("close").css({
        "position": "absolute",
        "top": "10px",
        "right": "10px"
    }).attr("type", "button").attr("aria-lable", "Close").html('<span aria-hidden="true">' +
        '&times;' +
        '</span>').appendTo($(obj));

    upload(thumb, file, id);
    // Using FileReader to display the image content
    var reader = new FileReader();
    reader.onload = (function (aImg) {
        return function (e) {
            aImg.src = e.target.result;
        }
    })(img);
    reader.readAsDataURL(file);


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
    console.log(id);
    var formdata = new FormData();
    formdata.append("photo", file);
    formdata.append("params", "minify");
    var http = new XMLHttpRequest();
    var url = "http://172.28.3.18:3008/upload";
    http.open("POST", url, true);
    http.onreadystatechange = function () {
        if (http.readyState == 4 && http.status == 200) {
            var data = JSON.parse(http.responseText);
            console.log(data.minify_url);
            var input = document.createElement("input");
            var img = new Image;
            img.onload = function(){
                 alert(["图片大小是: width:"+img.width+", height:"+img.height]);
                 if(id == "uploadDirect"){
                    jsFileShareContent.ulpDirectImgWidth = img.width;
                    jsFileShareContent.ulpDirectImgHeight = img.height;

                }else{
                     jsFileShareContent.labelImgWidth = img.width;
                     jsFileShareContent.labelImgHeight = img.height;
                }
            }
            img.src = data.oss_prefix+data.oss_url;
            imgName = data.imgid;
            input.id = imgName.substr(0, imgName.lastIndexOf("."));
            input.type = "hidden";
            input.name = data.imgid;
            input.value = data.path;
            $(thumb).find("img").attr('src', data.oss_prefix+data.oss_url);
            $(thumb).append(input);
            alert(data.oss_prefix+data.oss_url);
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
    $(document).on('change', '.upload2', function () {
        var id = window.event.srcElement.id;
        var files = this.files;
        var li_dv = $(this).parent().parent();
        $(this).parent().css("display", "none");
        $(this).parent().next().css("display", "block");
        $(this).parents(".li-dv").find(".text").css("display", "block");
        console.log(li_dv);
        for (var i = 0; i < files.length; i++) {
            previewImage(li_dv, this.files[i], id);
        }
    });

//        upload-lable
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


    $(document).on("click", ".img .close", function () {
        if ($(this).parent().find(".onlyday")) {
            $(this).parents(".img").find(".upload2").val("");
            $(this).parent().find(".onlyday").css("display", "none");
        }
        $(this).parents(".img").find(".upload1").val("");
        $(this).parents(".img").find(".btn-primary").css("display", "block");
        $(this).parents(".img").find(".track").remove();
        $(this).parents(".li-dv").find(".text").css("display", "none");
        $(this).remove();
    });
    $(document).on("click",".th-del",function(){
        //商品总数
        var itemCount = $("#sort").find("tr").length - 1;
        console.log("商品总数itemCount:" + itemCount);
        //被删除行的编号
        var delColNum = $(this).parents("tr").find("td:eq(0)").text();
        console.log("被删除行编号delColNum:" + delColNum);

        if(itemCount > delColNum){
            for(var i = parseInt(delColNum);i < itemCount;i++){
                var j = i + 1;
                $("#sort").find("tr:eq("+j+")").find("td:eq(0)").text(i);
            }
        }
        $(this).parents("tr").remove();

    })
})