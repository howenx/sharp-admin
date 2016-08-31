/*
 * function 开始
 */

// 图片预览上传 开始
function previewImage(obj, file) {

    var gallery = obj;
    var imageType = /image.*/;
    if (!file.type.match(imageType)) {
        throw "File Type must be an image";
    }
    var img = document.createElement("img");
    img.file = file;
    $(img).width('100%');
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

function upload(thumb, file) {
    //当前系统时间
    var date = new Date();
    var dateStr = ''+date.getFullYear()+(date.getMonth()+1>=10?date.getMonth()+1:'0'+(date.getMonth()+1))+(date.getDate()>=10?date.getDate():'0'+date.getDate());
    var formdata = new FormData();
    formdata.append("photo", file);
    formdata.append("params", "minify");
    formdata.append("prefix","themes/photo/themeImg/"+ dateStr +"/");
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
// 图片预览上传 结束

//选择商品 开始
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
    var index1 = $("#sort tbody").find("tr").length,
        index2 = $("#sort thead").find("tr").length;
    var index = index1 + index2;
    for (var i = 0; i < $(obj1).length; i++) {
        $(obj1).eq(i).prepend($("<td class='index'>" + (Number(index) + i) + "</td>")).appendTo($("#sort"));
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
    $(".user-state").text("已更改");
}
//选择商品 结束

/*
 * function 结束
 */

$(function () {

    // 上传图片 开始
    $('#upload-label').change(function () {
        if ($(this).val()) {
            var files = this.files;
            var label_img = $('#dragon-container');
            $(label_img).find("img").remove();
        } else {
            var label_img = $('#dragon-container');
        }
        for (var i = 0; i < files.length; i++) {
            previewImage(label_img, this.files[i]);
        }
    });
    // 上传图片 开始

    // 建立标记 开始
    var numW,numH;
    var drag = '<a class="ui-widget-content draggable" style="width: 50%;height: 20%;position: absolute;top: 0;left: 0;background: #ccc">' +
        '<p>qwer</p>' +
        '</a>';
    $('#mark-bt').click(function() {
        $(drag).find("p").eq(0).text($("#input_imgurl").val());
        $('#dragon-container').append(drag);
        $('a.draggable').draggable({
            containment: "parent",
        });
        numW =parseInt($('a.draggable')[0].style.width);
        numH =parseInt($('a.draggable')[0].style.height);
    });
    // 获取 第几个 标签
    var _self;
    $(document).on("click",'a.draggable',function () {
        $("a.draggable").css("background","#ccc");
        $(this).css("background","#fff");
        _self = this ;
        numW =parseInt(this.style.width);
        numH =parseInt(this.style.height);
    })


    $(window).keydown(function (e) {
        e.preventDefault();
        switch(e.keyCode){
            case 37: //左键
                console.log(numW,_self);
                numW = numW - 1;
                $(_self).css({
                    width : numW + "%"
                });
                break;
            case 38: //向上键
                numH = numH - 1;
                $(_self).css({
                    height : numH + "%"
                });
                break;
            case 39: //右键
                numW = numW + 1;
                $(_self).css({
                    width : numW + "%"
                });
                break;
            case 40: //向下键
                numH = numH + 1;
                $(_self).css({
                    height : numH + "%"
                });
                break;
            default:
                break;
        }
    })
    // 建立标记 开始

})