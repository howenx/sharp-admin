//上传图片到kakao China OSS
function uploadToKakao(thumb, file, id) {
    var imgUrlOSS = "";
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
    http.open("POST", url, false);
    http.onreadystatechange = function () {
        if (http.readyState == 4 && http.status == 200) {
            var data = JSON.parse(http.responseText);
            console.log(data.minify_url);
            var input = document.createElement("input");
            var img = new Image;
            img.src = data.oss_prefix+data.oss_url;
            console.log("上传到Coupon服务器的url~~~:" + data.oss_prefix+data.oss_url);
            imgUrlOSS = data.oss_prefix+data.oss_url;
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
    return imgUrlOSS;
}
//获取携程参数
function getParams(){
    var result;
    var data = "";
    $.ajax({
        type :  "POST",
        url : "/coupon/params",
        async: false,
        contentType: "application/json; charset=utf-8",
        data : JSON.stringify(data),
        error : function(request) {
           console.log("get ctrip params fail");
        },
        success: function(data) {
            console.log("返回数据携程参数是:" + data.token);
            result = data;
        }
    });
    return result;
}
//上传图片到携程
function uploadToCtrip(params,file){
    var imgUrlCtrip = "";
    var  AID    =  params.AID;        //联盟账号
    var  SID    =  params.SID;        //联盟账号
    var  ICODE  =  params.ICODE;      //接口名称
    var  token  =  params.token;      //验证参数
    var  UUID   =  params.UUID;       //每次请求唯一值
    var  mode   =  params.mode;       //接口请求模式
    var  format =  params.format;     //请求体返回体编码格式
    var queryStrings = "?AID="+ AID +"&SID="+ SID +"&ICODE="+ ICODE +"&UUID="+ UUID +"&Token="+ token +"&mode="+ mode +"&format"+ format;
    var url = window.ctripUrl + queryStrings;
    console.log("ctrip的url:" + url);
    //post数据
    var postdata = {};
    postdata.ImgID = "";
    postdata.VendorBID = "";
    postdata.Url = "";
    postdata.OriginalFormat = "";
    postdata.TargetFormat = "";

    $.ajax({
        type :  "POST",
        url : url ,
        async: false,
        contentType: "application/json; charset=utf-8",
        data : JSON.stringify(postdata),
        error : function(request) {
           console.log("get ctrip params fail");
        },
        success: function(data) {
            console.log("返回数据携程参数是:" + data.token);
            result = data.ImgPath;
        }
    });

    return imgUrlCtrip;

}

$(function () {
    $("#upload-label").change(function () {
        //获取访问携程参数
        var params = getParams();
        if(params == null || params == ""){
            console.log("get token fail");
            return false;
        }

        //上传图片到kakao China OSS
        var file = this.files[0];
        var oss_url = uploadToKakao($("#galleryD"), file);
        //上传到携程服务器
        var ctrip_url = uploadToCtrip(params,file);

    })
})