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
//            console.log(data.minify_url);
//            var input = document.createElement("input");
            var img = new Image;
            img.onload = function(){
                jsFileShareContent.labelImgWidth = img.width;
                jsFileShareContent.labelImgHeight = img.height;
                $(thumb).width(img.width);
                $(thumb).height(img.height);
            }
            img.src = data.oss_prefix+data.oss_url;
            imgName = data.imgid;
//            input.id = imgName.substr(0, imgName.lastIndexOf("."));
//            input.type = "hidden";
//            input.name = data.imgid;
//            input.value = data.path;
            $(thumb).find("img").attr('src', data.oss_prefix+data.oss_url);
//            $(thumb).append(input);
//            alert(data.message);
            $.ajax({
                url: window.uploadUrl2 + "/split/file",
                data: {
                    filename: '' + data.oss_url,
                    prefix:'themes/photo/themeImg/'+ dateStr + '/'
                },
                type: 'post',
                success: function(data2) {
                    var array_oss_url = JSON.parse(data2.oss_url);
                    var path = data2.oss_prefix.split(window.url)[1];
                    var inpV = path + array_oss_url[0] + "," + path + array_oss_url[1] + "," + path + array_oss_url[2];
                    var input = document.createElement("input");
                    input.type="hidden";
                    input.value = inpV;
                    $(thumb).append(input);
                    document.getElementById("mask").style.display = 'none';
                }
            });
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
            var label_img = $('#draga');
            $(label_img).find("img").remove();
        } else {
            var label_img = $('#draga');
        }
        for (var i = 0; i < files.length; i++) {
            previewImage(label_img, this.files[i]);
        }
    });
    // 上传图片 开始

    // 建立标记 开始
    var numW,
        numH,
        numT,
        numL,
        widthimg,
        heightimg,
        indexA = -1,
        itemID,
        _self;
    $('#mark-bt').click(function() {
        if($(".a-container").find("img").length!=0){
            // img 高度
            heightimg = $(".a-container").find("img").height();
            widthimg = $(".a-container").find("img").width();
            console.log(heightimg);
            if($("#input_imgurl").val() != ''){
                if($("#input_imgurl").val() != itemID){
                    itemID = $("#input_imgurl").val();
                    console.log(itemID);
                    var drag = '<a class="ui-widget-content draggable" style="width: 50%;height: 6%;position: absolute;top: 0;left: 0;background: #000;opacity: 0.4">' +
                        '<P style="font-size: 20px;color: #fff">'+ itemID +'</P>'+
                        '</a>';
                    $('#draga').append(drag);
                    _self = $("a.draggable").eq(indexA+=1);
                    console.log(_self);
                    $("a.draggable").eq(indexA).draggable({
                        cursor: "move",
                        containment: "parent",
                        stop:function (event, ui) {
                            $(ui.helper).css({
                                top : parseInt(parseFloat(this.style.top) / heightimg * 100)+'%',
                                left : parseInt(parseFloat(this.style.left) / widthimg * 100)+'%'
                            })
                            _self = ui.helper;
                            numW =parseInt($(ui.helper)[0].style.width);
                            numH =parseInt($(ui.helper)[0].style.height);
                            // numT = parseInt(parseFloat(this.style.top) / heightimg * 100);
                            // numL = parseInt(parseFloat(this.style.left) / widthimg * 100);
                            numT = parseInt($(ui.helper)[0].style.top);
                            numL = parseInt($(ui.helper)[0].style.left);
                        }
                    });
                    numW =parseInt($('a.draggable').eq(indexA)[0].style.width);
                    numH =parseInt($('a.draggable').eq(indexA)[0].style.height);
                    // numT = parseInt(parseFloat(this.style.top) / heightimg * 100);
                    // numL = parseInt(parseFloat(this.style.left) / widthimg * 100);
                    numT = parseInt($('a.draggable').eq(indexA)[0].style.top);
                    numL = parseInt($('a.draggable').eq(indexA)[0].style.left);
                }else{
                    alert("请选择不同商品ID");
                }
            }else{
                alert("请选择商品ID");
            }
        }else{
            alert("请先上传图片");
        }

    });
    // 获取 第几个 标签
    $(document).on("click",'a.draggable',function () {
        _self = this ;
        numW =parseInt(this.style.width);
        numH =parseInt(this.style.height);
        numT = parseInt(this.style.top);
        numL = parseInt(this.style.left);
    })


    $(window).keydown(function (e) {
        e.preventDefault();
        switch(e.keyCode){
            case 37: //左键
                numW = numW - 1;
                $(_self).css({
                    width : numW + "%"
                });
                break;
            case 38: //向上键
                numH = numH - 0.5;
                $(_self).css({
                    height : numH + "%"
                });
                break;
            case 39: //右键
                numW = numW + 1;
                $(_self).css({
                    width: numW + "%",
                });
                break;
            case 40: //向下键
                numH = numH + 0.5;
                $(_self).css({
                    height : numH + "%"
                });
                break;
            default:
                break;
        }
    })
    // 建立标记 开始


    //保存        Added by Tiffany Zhu 2016.09.01
    var themeOffShelf = false;
    $(document).on("click","#js-usercenter-submit-h5",function(){
        var http = "^(http|https)://([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?$";
                var httpRe = new RegExp(http);
                var isPost = true;
                if($("#themeTitle").val()=="" || $("#onShelvesAt").val()=="" || $("#offShelvesAt").val()=="" || $("#h5-link").val()=="" || $("#themeDescribe").val() == ""){
                    isPost = false;
                    $('#js-userinfo-error').text('基本信息不能为空!').css('color', '#c00');
                    setTimeout("$('#js-userinfo-error').text('').css('color', '#2fa900')",3000);
                    return false;
                }
                //当前系统时间
                var dateTime = new Date();
                var currentTime = moment(dateTime).format("YYYY-MM-DD HH:mm:ss");
                //限制时间 当前时间 + 6个月
                dateTime.setMonth(dateTime.getMonth() + 6);
                var validDate = moment(dateTime).format("YYYY-MM-DD HH:mm:ss");
                if($("#offShelvesAt").val() > validDate || $("#onShelvesAt").val() > 0){
                    isPost = false;
                    $('#js-userinfo-error').text('开始时间和结束时间均不能大于当前时间 + 6个月!').css('color', '#c00');
                    setTimeout("$('#js-userinfo-error').text('').css('color', '#2fa900')",3000);
                    return false;
                }
                 if(!themeOffShelf && $("#onShelvesAt").val()>$("#offShelvesAt").val()){
                    isPost = false;
                    $('#js-userinfo-error').text('日期不正确!').css('color', '#c00');
                    setTimeout("$('#js-userinfo-error').text('').css('color', '#2fa900')",3000);
                    return false;
                 }
                 if($("#offShelvesAt").val() <= currentTime){
                     isPost = false;
                     $('#js-userinfo-error').text('结束时间须大于当前时间!').css('color', '#c00');
                     setTimeout("$('#js-userinfo-error').text('').css('color', '#2fa900')",3000);
                     return false;
                 }
                 if($("input[name='themearea1']").prop("checked") == false  && $("input[name='themearea2']").prop("checked") == false && $("input[name='themearea3']").prop("checked") == false){
                     isPost = false;
                     $('#js-userinfo-error').text('请选择显示位置!').css('color', '#c00');
                     setTimeout("$('#js-userinfo-error').text('').css('color', '#2fa900')",3000);
                     return false;
                 }
                 if($("#themeImg").find("img").attr("src") == ""){
                     isPost = false;
                     $('#js-userinfo-error').text('请选择主题图片!').css('color', '#c00');
                     setTimeout("$('#js-userinfo-error').text('').css('color', '#2fa900')",3000);
                     return false;
                 }
                 //主题列表主宣传图
                 var imgSel = $("#imgSel").prop("checked");
                 if(imgSel && $(".a-container").find("img").length == 0){
                    isPost = false;
                    $('#js-userinfo-error').text('请上传主题的主图!').css('color', '#c00');
                    setTimeout("$('#js-userinfo-error').text('').css('color', '#2fa900')",3000);
                    return false;
                 }






                 var confirm_text = "确定保存吗?";
                 if(themeOffShelf == true){
                    confirm_text = "确定下架吗?";
                 }
                 var a = confirm(confirm_text);
                 if(!a){
                     $("#offShelvesAt").val(themeOffShelfTime);
                     isPost = false;
                     return false;
                 }else{
                     if(confirm_text == "确定下架吗?"){
                            //当前系统时间
                            var dateTime = new Date();
                            var currentTime = moment(dateTime).format("YYYY-MM-DD HH:mm:ss");
                            dateTime.setSeconds(dateTime.getSeconds() + 15);
                            var deleteDate = moment(dateTime).format("YYYY-MM-DD HH:mm:ss");
                            //设置主题结束时间
                            $("#offShelvesAt").val(deleteDate);
                     }
                 }

                 //主题状态
                 var themeState = parseInt($("input[name='themestate']:checked").attr("id"));
                  //专用主题用户
                  var users = [];
                  if(themeState == 2){
                       $("#user-add").find("tr").each(function(){
                           var userId = $(this).find("td").eq(1).text();
                           var phoneNum = $(this).find("td").eq(5).text()
                           if(userId != "" && phoneNum != ""){
                               var object = new Object();
                               object.userId = userId;
                               object.phoneNum = phoneNum;
                               users.push(object);
                           }
                       })
                  }

                  //主题显示位置
                  var themeCates = [];
                  //首页
                  if($("input[name='themearea1']").prop("checked") == true){
                      themeCates.push(1);
                  }
                  //拼购
                  if($("input[name='themearea2']").prop("checked") == true){
                      themeCates.push(2);
                  }
                  //礼物
                  if($("input[name='themearea3']").prop("checked") == true){
                      themeCates.push(3);
                  }

                 var data = {};
                 var theme = new Object();
                 var themeId = $("#themeId").val();
                 theme.id = $("#themeId").val();
                 theme.title = $("#themeTitle").val();
                 theme.startAt = $("#onShelvesAt").val();
                 theme.endAt = $("#offShelvesAt").val();
                 theme.h5Link = $("#h5-link").val();
                 theme.themeConfigInfo = $("#themeDescribe").val();
                 theme.themeState = themeState;


                 //主题主图片
                 var themeImgContent = {};
                 var url = $("#themeImg").find("img").attr("src");
                 themeImgContent.url = url.substring(url.indexOf('/',url.indexOf('/')+2) + 1);
                 themeImgContent.width = $("#themeImg").find("input").width().toString();
                 themeImgContent.height = $("#themeImg").find("input").height().toString();
                 theme.themeImg = JSON.stringify(themeImgContent);
                 theme.type = "h5";
                 theme.sortNu = 1;

                 data.theme = theme;
                 data.users = users;
                 data.themeCates = themeCates;














//                 if(isPost){
//                    $.ajax({
//                        type :  "POST",
//                        url : "/topic/add/h5ThemeSave",
//                        contentType: "application/json; charset=utf-8",
//                        data : JSON.stringify(data),
//                        error : function(request) {
//                            if (window.lang = 'cn') {
//                                 alert("保存失败!");
//                            } else {
//                                alert("Save error!");
//                            }
//                        },
//                        success: function(data) {
//                            if (window.lang = 'cn') {
//                                alert("保存成功!");
//                            } else {
//                                alert("Save Success!");
//                            }
//                            if(themeId != null){
//                                //主题修改, 成功后返回到主题修改页面
//                                setTimeout("location.href='/"+window.lang+"/topic/updateById/"+ themeId +"'", 2000);
//                            }else{
//
//                                 //主题录入, 成功后返回到主题录入页面
//                                 setTimeout("location.href='/"+window.lang+"/topic/h5Add'", 2000);
//                            }
//                        }
//                    })
//                 }

    })





})