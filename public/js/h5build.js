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
// 上传图片
$(function () {
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
    $('#mark-bt').click(function() {
        var radio_flag = false;
        var rotate = '';
        $(':radio[name=mark-deg]').each(function(index, element) {
            if ($(this).prop('checked')) {
                radio_flag = true;
                rotate = $(this).val();
            }
        });
        //check length
        if ($('#mark-nm').val() != '' && $('#mark-nm').val().length != 0 && $('#mark-nm').val().replace(/[^\x00-\xff]/ig, "aa").length < 24) {
            //check rotate degree just for number
            // if ($('#mark-rotate').val != '' && $('#mark-rotate').val().match(/[\d]/ig)) {
            if($("#input_imgurl").val() != ""){
                if (radio_flag && rotate != '') {
                    var ch_drag = $(drag).find('p').eq(0).text($('#mark-nm').val());
                    //if the rotate for 180 or 135,first rotate the p tag text 180.
                    if (rotate === '180' || rotate === '135') {
                        var ch_drag_p = ch_drag.parent().css({
                            'transform': 'rotate(180deg)',
                            '-ms-transform': 'rotate(180deg)',
                            '-webkit-transform': 'rotate(180deg)',
                            '-o-transform': 'rotate(180deg)',
                            '-moz-transform': 'rotate(180deg)',
                            'left': '0px'
                        });
                        var ch_graph = ch_drag_p.parent().parent().css({
                            'transform': 'rotate(' + rotate + 'deg)',
                            '-ms-transform': 'rotate(' + rotate + 'deg)',
                            '-webkit-transform': 'rotate(' + rotate + 'deg)',
                            '-o-transform': 'rotate(' + rotate + 'deg)',
                            '-moz-transform': 'rotate(' + rotate + 'deg)'
                        });
                        $('#dragon-container').append(ch_graph.parent());
                        $('div.dragon-contained').draggable({
                            containment: "parent",
                        });
                        var itemId =  $("#input_imgurl").val();
                        var itemType = $("#url-type").val();
                        ch_drag.parent().parent().parent().parent().find(".item-id").html(itemId);
                        ch_drag.parent().parent().parent().parent().find(".item-type").html(itemType);
                    } else if (rotate === '0' || rotate === '45') {
                        var ch_graph = ch_drag.parent().parent().parent().css({
                            'transform': 'rotate(' + rotate + 'deg)',
                            '-ms-transform': 'rotate(' + rotate + 'deg)',
                            '-webkit-transform': 'rotate(' + rotate + 'deg)',
                            '-o-transform': 'rotate(' + rotate + 'deg)',
                            '-moz-transform': 'rotate(' + rotate + 'deg)'
                        });
                        $('#dragon-container').append(ch_graph.parent());
                        $('div.dragon-contained').draggable({
                            containment: "parent"
                        });
                        var itemId =  $("#input_imgurl").val();
                        var itemType = $("#url-type").val();
                        ch_drag.parent().parent().parent().parent().find(".item-id").html(itemId);
                        ch_drag.parent().parent().parent().parent().find(".item-type").html(itemType);

                    } else {
                        alert('Please do not modify rotate degree.');
                    }
                } else {
                    alert('Please choose correct rotate degree.');
                }
            }else{
                alert('Please choose the item ID.');
            }
        } else alert('Please input the length less than 24 characters and more than 1 character.');
    });
})