$(function() {
	/*** template params array.****/
	var data_array = [];
	/***Loading..***/
	$(document).ajaxStart(function() {
		$('#mask').show();
	});
	$(document).ajaxStop(function() {
		$('#mask').hide();
	});
  	//修改前主题包含的商品信息
    var beforeUpdItems = [];
    $("#sort").find("tr").each(function(){

        var itemId = $(this).find("td:eq(3)").text();
        var type = $(this).find("td:eq(2)").text();
        if( itemId!= null && itemId != ""){
            var object = new Object();
            if(type == "普通"){
                object.type = "item";
            }
            if(type == "拼购"){
                object.type = "pin";
            }
            if(type == "多样化"){
                object.type = "vary";
            }
            if(type == "自定义"){
                object.type = "customize";
            }
            object.id =  itemId.toString();
            beforeUpdItems.push(object);
        }
    })
  	//保存--普通主题
  	$("#js-usercenter-submit").click(function(){

        var isPost = true;

        if($("#themeTitle").val() == "" || $("#onShelvesAt").val() == "" || $("#offShelvesAt").val() == "")
        {
            isPost = false;
             $('#js-userinfo-error').text('基本信息不能为空!').css('color', '#c00');
             setTimeout("$('#js-userinfo-error').text('').css('color', '#2fa900')",3000);
             return false;
        }
        if($("#onShelvesAt").val()>$("#offShelvesAt").val()){
            isPost = false;
            $('#js-userinfo-error').text('日期不正确!').css('color', '#c00');
            setTimeout("$('#js-userinfo-error').text('').css('color', '#2fa900')",3000);
            return false;
        }
        if($("#themeImg").find("img").attr("src") == ""){
            isPost = false;
            $('#js-userinfo-error').text('请选择主题图片!').css('color', '#c00');
            setTimeout("$('#js-userinfo-error').text('').css('color', '#2fa900')",3000);
            return false;
        }
        if(document.getElementById("sort").rows.length == 1){
            isPost = false;
            $('#js-userinfo-error').text('请添加商品!').css('color', '#c00');
            setTimeout("$('#js-userinfo-error').text('').css('color', '#2fa900')",3000);
            return false;
        }
        if(document.getElementById("dragon-container").innerHTML.indexOf("img")<0)
        {
            isPost = false;
            $('#js-userinfo-error').text('请上传主题的首页主图!').css('color', '#c00');
            setTimeout("$('#js-userinfo-error').text('').css('color', '#2fa900')",3000);
            return false;
        }
       //当前系统时间
       var dateTime = new Date();
       dateTime = moment(dateTime).format("YYYY-MM-DD HH:mm:ss");
       if($("#offShelvesAt").val() <= dateTime){
           isPost = false;
           $('#js-userinfo-error').text('结束时间须大于当前时间!').css('color', '#c00');
           setTimeout("$('#js-userinfo-error').text('').css('color', '#2fa900')",3000);
           return false;
       }

        //填充数据
        var theme = new Object();
        //主题ID
        var themeId = $("#themeId").val();
        //主题标题
        var title = $("#themeTitle").val();
        //开始日期
        var onShelvesAt = $("#onShelvesAt").val();
        //结束日期
        var offShelvesAt = $("#offShelvesAt").val();
        var sortNu = 1;
        //主题的配置信息
        var themeConfig = [];
        //自定义价格
        var customizeItems = [];
        //主题包含的商品信息
        var themeItems = [];
        $("#sort").find("tr").each(function(){

            var itemId = $(this).find("td:eq(3)").text();
            var type = $(this).find("td:eq(2)").text();
            if( itemId!= null && itemId != ""){
                var object = new Object();
                if(type == "普通"){
                    object.type = "item";
                }
                if(type == "拼购"){
                    object.type = "pin";
                }
                if(type == "多样化"){
                    object.type = "vary";
                }
                if(type == "自定义"){
                    object.type = "customize";
                    var customizeObject = new Object();
                    if($(this).find("td:eq(1)").text() != $(this).find("td:eq(3)").text()){
                        customizeObject.id =  $(this).find("td:eq(3)").text();
                    }
                    customizeObject.invId = $(this).find("td:eq(1)").text();
                    customizeObject.price = $(this).find("td:eq(8)").text();
                    customizeObject.discount = $(this).find("td:eq(10)").text();
                    customizeItems.push(customizeObject);
                }
                object.id =  itemId.toString();
                themeItems.push(object);
            }
        })
        //主题主宣传图上的标签
        var masterItemTag = [];
        var tagsContainer = $("#dragon-container");
        $("#dragon-container").find(".dragon-contained").each(function(){
            var tag = {};
            if($(this).find(".dragon-graph").css('transform').indexOf("-1")>=0){
                tag.angle = 180;
                var container_width = parseInt($(this).parent().width());
                var container_height = parseInt($(this).parent().height());
                var left = parseInt($(this).css("left").replace("px","")) + $(this).width();
                var top = parseInt($(this).css("top").replace("px",""));
                tag.left = parseFloat((left/container_width).toFixed(2));
                tag.top = parseFloat((top/container_height).toFixed(2));
                tag.name = $(this).find("p").text();
                var url = {};
                url.type = $(this).find(".item-type").text();
                url.id = $(this).find(".item-id").text();
                tag.url = url;
            }else{
                tag.angle = 0;
                var container_width = parseInt($(this).parent().width());
                var container_height = parseInt($(this).parent().height());
                var left = parseInt($(this).css("left").replace("px",""));
                var top = parseInt($(this).css("top").replace("px",""));
                tag.left = parseFloat((left/container_width).toFixed(2));
                tag.top = parseFloat((top/container_height).toFixed(2));
                tag.name = $(this).find("p").text();
                var url = {};
                url.type = $(this).find(".item-type").text();
                url.id = $(this).find(".item-id").text();
                tag.url = url;
            }
            masterItemTag.push(tag);
        })

        //主题列表主宣传图
        var imgUrl = document.getElementById("dragon-container").getElementsByTagName("img")[0].src;
        var themeMasterImg = imgUrl.substring(imgUrl.indexOf('/',imgUrl.indexOf('/')+2) + 1);

        //主题主图片
        var themeImgContent = {};
        var url = $("#themeImg").find("img").attr("src");
        themeImgContent.url = url.substring(url.indexOf('/',url.indexOf('/')+2) + 1);
        themeImgContent.width = $("#themeImg").find("input").width().toString();
        themeImgContent.height = $("#themeImg").find("input").height().toString();
        //主题tag背景图
        var themeMasterImgContent = {};
        themeMasterImgContent.url = themeMasterImg;
        if(jsFileShareContent.labelImgWidth != null && jsFileShareContent.labelImgHeight != null){
            themeMasterImgContent.width = jsFileShareContent.labelImgWidth.toString();
            themeMasterImgContent.height = jsFileShareContent.labelImgHeight.toString();
        }else{
            themeMasterImgContent.width = $("#dragon-container").find("input").width().toString();
            themeMasterImgContent.height = $("#dragon-container").find("input").height().toString();
        }
        theme.id = themeId;
        theme.title = title;
        theme.startAt = onShelvesAt;
        theme.endAt = offShelvesAt;
        theme.themeImg = JSON.stringify(themeImgContent);
        theme.sortNu = sortNu;
        theme.themeSrcImg = "";
        theme.themeDesc = themeConfig;
        theme.themeItem = themeItems;
        if(JSON.stringify(masterItemTag) != "[]"){
        theme.themeTags = masterItemTag;
        }
        theme.themeMasterImg = JSON.stringify(themeMasterImgContent);
        var data = {};
        data.theme = theme;
        data.beforeUpdItems = beforeUpdItems;
        data.customizeItems = customizeItems;
        if (isPost) {
                    $.ajax({
                        type :  "POST",
                        url : "/topic/add/themeSave",
                        contentType: "application/json; charset=utf-8",
                        data : JSON.stringify(data),
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
                            if(themeId != null){
                                //主题修改, 成功后返回到主题修改页面
                                setTimeout("location.href='/"+window.lang+"/topic/updateById/"+ themeId +"'", 3000);
                            }else{
                                 //主题录入, 成功后返回到主题录入页面
                                 setTimeout("location.href='/"+window.lang+"/topic/add'", 3000);
                            }
                        }
                    });
        }

  	})


    //保存--H5主题
    $(document).on("click","#js-usercenter-submit-h5",function(){
        var http = "^(http|https)://([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?$";
        var httpRe = new RegExp(http);
        var isPost = true;
        if($("#themeTitle").val()=="" || $("#onShelvesAt").val()=="" || $("#offShelvesAt").val()=="" || $("#h5-link").val()==""){
            isPost = false;
            $('#js-userinfo-error').text('基本信息不能为空!').css('color', '#c00');
            setTimeout("$('#js-userinfo-error').text('').css('color', '#2fa900')",3000);
            return false;
        }
        if($("#themeImg").find("img").attr("src") == ""){
            isPost = false;
            $('#js-userinfo-error').text('请选择主题图片!').css('color', '#c00');
            setTimeout("$('#js-userinfo-error').text('').css('color', '#2fa900')",3000);
            return false;
        }
         if($("#onShelvesAt").val()>$("#offShelvesAt").val()){
            isPost = false;
            $('#js-userinfo-error').text('日期不正确!').css('color', '#c00');
            setTimeout("$('#js-userinfo-error').text('').css('color', '#2fa900')",3000);
            return false;
         }
         if(!httpRe.test($("#h5-link").val())){
            isPost = false;
            $('#js-userinfo-error').text('H5链接格式错误!').css('color', '#c00');
            setTimeout("$('#js-userinfo-error').text('').css('color', '#2fa900')",3000);
            return false;
         }
         //当前系统时间
         var dateTime = new Date();
         dateTime = moment(dateTime).format("YYYY-MM-DD HH:mm:ss");
         if($("#offShelvesAt").val() <= dateTime){
             isPost = false;
             $('#js-userinfo-error').text('结束时间须大于当前时间!').css('color', '#c00');
             setTimeout("$('#js-userinfo-error').text('').css('color', '#2fa900')",3000);
             return false;
         }

         var theme = {};
         var themeId = $("#themeId").val();
         theme.id = $("#themeId").val();
         theme.title = $("#themeTitle").val();
         theme.startAt = $("#onShelvesAt").val();
         theme.endAt = $("#offShelvesAt").val();
         theme.h5Link = $("#h5-link").val();
         //主题主图片
         var themeImgContent = {};
         var url = $("#themeImg").find("input").attr("id");
         themeImgContent.url = url.substring(url.indexOf('/',url.indexOf('/')+2) + 1);
         themeImgContent.width = $("#themeImg").find("input").width().toString();
         themeImgContent.height = $("#themeImg").find("input").height().toString();
         theme.themeImg = JSON.stringify(themeImgContent);
         theme.sortNu = 1;

         if(isPost){
            $.ajax({
                type :  "POST",
                url : "/topic/add/h5ThemeSave",
                contentType: "application/json; charset=utf-8",
                data : JSON.stringify(theme),
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
                    if(themeId != null){
                        //主题修改, 成功后返回到主题修改页面
                        setTimeout("location.href='/"+window.lang+"/topic/updateById/"+ themeId +"'", 2000);
                    }else{

                         //主题录入, 成功后返回到主题录入页面
                         setTimeout("location.href='/"+window.lang+"/topic/h5Add'", 2000);
                    }
                }
            })
         }
    })

});
