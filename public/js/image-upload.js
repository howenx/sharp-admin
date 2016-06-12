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
$(function () {
    $("#upload-label").change(function () {
        var file = this.files[0];
        previewImage1($("#galleryD"),file);
    })
})