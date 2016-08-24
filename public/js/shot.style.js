$(function() {
	/*** template params array.****/
	var themeOffShelf = false;
	var themeOffShelfTime =  $("#offShelvesAt").val();
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

    //返回列表
    $(document).on("click","#js-usercenter-back",function(){
      setTimeout("location.href='/"+window.lang+"/topic/search'", 300);
    })

  	//保存--普通主题
  	$("#js-usercenter-submit").click(function(){

        var isPost = true;

        if($("#themeTitle").val() == "" || $("#onShelvesAt").val() == "" || $("#offShelvesAt").val() == "" || $("#themeDescribe").val() == "")
        {
            isPost = false;
             $('#js-userinfo-error').text('基本信息不能为空!').css('color', '#c00');
             setTimeout("$('#js-userinfo-error').text('').css('color', '#2fa900')",3000);
             return false;
        }
        //主题描述长度检查
        if($("#themeDescribe").val().length > 40){
            isPost = false;
            $('#js-userinfo-error').text('主题描述的长度不能超过40个字!').css('color', '#c00');
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
        if($("#themeImg").find("img").attr("src") == ""){
            isPost = false;
            $('#js-userinfo-error').text('请选择主题图片!').css('color', '#c00');
            setTimeout("$('#js-userinfo-error').text('').css('color', '#2fa900')",3000);
            return false;
        }

        var themeTypeCheck = $('input[name="theme"]:checked').attr("id");
        //是否选择商品
         var goodsSel = $("#goodsSel").prop("checked");
         //主题列表主宣传图
         var imgSel = $("#imgSel").prop("checked");

        //选择普通商品---商品不能为空     Modified by Tiffany Zhu 016.07.29
        if(goodsSel && themeTypeCheck == 'ordinary' && document.getElementById("sort").rows.length == 1){
            isPost = false;
            $('#js-userinfo-error').text('请添加商品!').css('color', '#c00');
            setTimeout("$('#js-userinfo-error').text('').css('color', '#2fa900')",3000);
            return false;
        }
        //选择单一商品---商品只能是一件      Modified by Tiffany Zhu 2016.07.29
        //if(goodsSel && themeTypeCheck == 'detail' && document.getElementById("sort").rows.length != 2){
        if(themeTypeCheck == 'detail' && document.getElementById("sort").rows.length != 2){
            isPost = false;
            $('#js-userinfo-error').text('必须添加一个商品!').css('color', '#c00');
            setTimeout("$('#js-userinfo-error').text('').css('color', '#2fa900')",3000);
            return false;
        }

        if(themeTypeCheck == 'ordinary' && $("#imgSel").prop("checked") == false && $("#goodsSel").prop("checked") == false)
        {
            isPost = false;
            $('#js-userinfo-error').text('"商品选择"与"主题主图"至少选一个!').css('color', '#c00');
            setTimeout("$('#js-userinfo-error').text('').css('color', '#2fa900')",3000);
            return false;
        }

        //不选择商品 只上传图片 验证图片是否上传   Modified by Tiffany Zhu 2016.07.29
        if(( themeTypeCheck == 'ordinary' && goodsSel == false && document.getElementById("dragon-container").innerHTML.indexOf("img")<0) || (themeTypeCheck == 'ordinary' && $("#imgSel").prop("checked") == true && document.getElementById("dragon-container").innerHTML.indexOf("img")<0))
        {
            isPost = false;
            $('#js-userinfo-error').text('请上传主题的首页主图!').css('color', '#c00');
            setTimeout("$('#js-userinfo-error').text('').css('color', '#2fa900')",3000);
            return false;
        }

        //不选择商品 只上传图片 验证必须添加标签    Modified by Tiffany Zhu 2016.07.29
        if(themeTypeCheck == 'ordinary' && goodsSel == false && $(".dragon-contained").size() == 0)
        {
            isPost = false;
            $('#js-userinfo-error').text('请添加标签!').css('color', '#c00');
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
        //var sortNu = 1;
        //主题的配置信息
        var themeConfig = [];
        //自定义价格
        var customizeItems = [];
        //主题包含的商品信息
        var themeItems;
        if(goodsSel || themeTypeCheck == 'detail'){
          themeItems = [];
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
                      if($(this).find("td:eq(7)").text() == "正常"){
                             customizeObject.state = "";
                      }else{
                             customizeObject.state = "D";
                      }
                      customizeObject.price = $(this).find("td:eq(8)").text();
                      customizeObject.discount = $(this).find("td:eq(10)").text();
                      customizeItems.push(customizeObject);
                  }
                  object.id =  itemId.toString();
                  themeItems.push(object);
              }
          })
        }

        //主题类型和h5链接
        var themeType = "ordinary";
        var h5Link;
        if(themeTypeCheck == "detail"){
            var itemId =$("#sort").find("tr").eq(1).find("td:eq(11)").text();
            var skuTypeId =  $("#sort").find("tr").eq(1).find("td:eq(3)").text();
            if( $("#sort").find("tr").eq(1).find("td:eq(2)").text() == "拼购"){
                    h5Link ="/comm/detail/pin/" + itemId + "/" + skuTypeId;
                    themeType = "pin";
            }else{
                    h5Link ="/comm/detail/item/" + itemId + "/" + skuTypeId;
                    themeType = "detail";
            }
        }
        //主题主宣传图上的标签
         var masterItemTag;
        if(themeTypeCheck != 'detail' && imgSel){
            masterItemTag = [];
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
        }


        //主题主图片
        var themeImgContent = {};
        var url = $("#themeImg").find("img").attr("src");
        themeImgContent.url = url.substring(url.indexOf('/',url.indexOf('/')+2) + 1);
        themeImgContent.width = $("#themeImg").find("input").width().toString();
        themeImgContent.height = $("#themeImg").find("input").height().toString();
        //主题tag背景图
         if(themeTypeCheck != 'detail' && imgSel){
             //var imgUrl = document.getElementById("dragon-container").getElementsByTagName("img")[0].src;
             var imgUrl = $("#dragon-container").find("img").attr("src");
             console.log("图片地址:" + imgUrl);
             var themeMasterImg = imgUrl.substring(imgUrl.indexOf('/',imgUrl.indexOf('/')+2) + 1);
             var themeMasterImgContent = {};
             themeMasterImgContent.url = themeMasterImg;
             if(jsFileShareContent.labelImgWidth != null && jsFileShareContent.labelImgHeight != null){
                 themeMasterImgContent.width = jsFileShareContent.labelImgWidth.toString();
                 themeMasterImgContent.height = jsFileShareContent.labelImgHeight.toString();
             }else{
                 themeMasterImgContent.width = $("#dragon-container").find("input").width().toString();
                 themeMasterImgContent.height = $("#dragon-container").find("input").height().toString();
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

        theme.id = themeId;
        theme.title = title;
        theme.startAt = onShelvesAt;
        theme.endAt = offShelvesAt;
        theme.themeImg = JSON.stringify(themeImgContent);
        //theme.sortNu = sortNu;
        theme.themeSrcImg = "";
        theme.themeDesc = themeConfig;
        theme.themeConfigInfo = $("#themeDescribe").val();
        theme.type = themeType;
        theme.h5Link = h5Link;
        theme.themeItem = themeItems;
        //if(JSON.stringify(masterItemTag) != "[]"){
        theme.themeTags = masterItemTag;
        //}
        theme.themeState = themeState;

        theme.themeMasterImg = JSON.stringify(themeMasterImgContent);
        var data = {};
        data.theme = theme;
        data.beforeUpdItems = beforeUpdItems;
        data.customizeItems = customizeItems;
        data.users = users;
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
//         if(!httpRe.test($("#h5-link").val())){
//            isPost = false;
//            $('#js-userinfo-error').text('H5链接格式错误!').css('color', '#c00');
//            setTimeout("$('#js-userinfo-error').text('').css('color', '#2fa900')",3000);
//            return false;
//         }
         if($("#themeImg").find("img").attr("src") == ""){
             isPost = false;
             $('#js-userinfo-error').text('请选择主题图片!').css('color', '#c00');
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
         theme.sortNu = 1;

         data.theme = theme;
         data.users = users;

         if(isPost){
            $.ajax({
                type :  "POST",
                url : "/topic/add/h5ThemeSave",
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
                        setTimeout("location.href='/"+window.lang+"/topic/updateById/"+ themeId +"'", 2000);
                    }else{

                         //主题录入, 成功后返回到主题录入页面
                         setTimeout("location.href='/"+window.lang+"/topic/h5Add'", 2000);
                    }
                }
            })
         }
    })

    //主题下架-----普通
    $(document).on("click","#js-usercenter-delete",function(){
        themeOffShelf = true;
        $("#js-usercenter-submit").click();
    })

    //主题下架-----HTML5
    $(document).on("click","#js-usercenter-delete-h5",function(){
        //点击保存
        themeOffShelf = true;
        $("#js-usercenter-submit-h5").click();

    })

});
