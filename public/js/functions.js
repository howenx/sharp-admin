/*上传背景图*/
var templates_img = $(".temp-img").parents("ul").find("li:nth-child(1)").children();
var temp_img = $(".temp-img").parents("ul").find("li:nth-child(1)").children().find("img");
var temp_index = 0;
var drag_img_index,
    div_index,
    p_index;
var div,nw, w,sw, s,se, e,ne,n;
$(".upload").change(function(){
    var obj = $(templates_img).find(".bg-img");
    console.log(obj);
    if($(this).val()){
        $(obj).find("img").remove();
    }
    /******存储图片*****/
    temp_img = $(obj).find("img");
    for(var i=0;i<obj.length;i++){
        var file = this.files[0];
        var img = document.createElement("img");
        img.file = file;
        obj[i].appendChild(img);
    }
    var formdata = new FormData();
    formdata.append("photo", file);
    formdata.append("params", "minify");
    var http = new XMLHttpRequest();
    var url = "/test/upload";
//    var url = window.uploadUrl;
    http.open("POST", url, true);
    http.onreadystatechange = function () {
        if (http.readyState == 4 && http.status == 200) {
            var data = JSON.parse(http.responseText);
            console.log(data.minify_url);
            var input = document.createElement("input");
            imgName = data.imgid;
            input.id = imgName.substr(0, imgName.lastIndexOf("."));
            input.type = "hidden";
            input.name = data.imgid;
            input.value = data.path;
            $(obj).find("img").attr("src",data.oss_prefix + data.oss_url);
            for(var i=0;i<obj.length;i++){
                obj[i].appendChild(input);
            }
            //alert(data.message);
        }
    }
    http.send(formdata);
    $(obj).parent().height("100%");
    $(this).val("");
});
$(".add-upload").change(function(){
    var obj = $("<div class='drag-img' onmousedown='righthit(this)'>").css({"width":"50%","top":0,"left":0}).appendTo($(templates_img));
    $(obj).eq(1).width(($(templates_img).eq(1).width()/2)).on("click",function(){
        dire(this);
    }).on("dblclick",function(){
        delDire(this);
    });
    /******存储图片*****/
    for(var i=0;i<obj.length;i++){
        var file = this.files[0];
        var img = document.createElement("img");
        img.file = file;
        obj[i].appendChild(img);
    }
    temp_img = $(obj).find("img");
    console.log(file.name);

    var formdata = new FormData();
    formdata.append("photo", file);
    formdata.append("params", "minify");
    var http = new XMLHttpRequest();
    //var url = "http://172.28.3.18:3008/upload";
    var url = window.uploadUrl;
    http.open("POST", url, true);
    http.onreadystatechange = function () {
        if (http.readyState == 4 && http.status == 200) {
            var data = JSON.parse(http.responseText);
            console.log(data.minify_url);
            var input = document.createElement("input");
            imgName = data.imgid;
            input.id = imgName.substr(0, imgName.lastIndexOf("."));
            input.type = "hidden";
            input.name = data.imgid;
            input.value = data.path;
            $(obj).find("img").attr("src",data.oss_prefix + data.oss_url);
            for(var i=0;i<obj.length;i++){
                obj[i].appendChild(input);
            }
            //alert(data.message);
        }
    }
    http.send(formdata);
    $(this).val("");
})
/*拖拽*/
$("ul.templates").on("mousedown",function(e){
    var obj = e.target;
    if($(templates_img).find(".bg-img").children().length==0){
        var bgnheight = $(templates_img).eq(1).height();
    }
    if(obj.tagName=="IMG"){
        drag_img_index = $(obj).parents(".temp-img").find(".drag-img").index($(obj).parent());
    }else if(obj.className=="drag"&&obj.tagName=="DIV"){
        div_index = $(obj).parents(".temp-img").find("div.drag").index($(obj));
    }else if(obj.className=="drag"&&obj.tagName=="P"){
        p_index = $(obj).parents(".temp-img").find("p.drag").index($(obj));
        console.log(p_index);
    }
    var x = e.pageX;
    var y = e.pageY;
    var xx = x - $(obj).offset().left;
    var yy = y - $(obj).offset().top;
    /*div old属性*/
    var div_width = $(templates_img).eq(1).find("div.drag").eq(div_index).width();
    var div_height = $(templates_img).eq(1).find("div.drag").eq(div_index).height();
    var div_left = parseInt($(templates_img).eq(1).find("div.drag").eq(div_index).css("left"));
    var div_top = parseInt($(templates_img).eq(1).find("div.drag").eq(div_index).css("top"));
    /*drag-img属性*/
    var img_height = $(obj).parent().height();
    var img_width = $(obj).parent().width();
    var img_top = parseInt($(obj).parent().css("top"));
    var img_left = parseInt($(obj).parent().css("left"));
    ///*****img 大 小*******/
    var temp_width = $(templates_img).eq(1).width();
    var temp_height = $(templates_img).eq(1).height();
    var xtemp_width = $(templates_img).eq(0).width();

    var ximg_height = $(templates_img).eq(0).find(".drag-img").eq(drag_img_index).height();
    var ximg_width = $(templates_img).eq(0).find(".drag-img").eq(drag_img_index).width();
    $(document).on("mousemove",function(e){
        var xxx = e.pageX;
        var yyy = e.pageY;
        var fnix = xxx - xx;
        var fniy = yyy - yy;
        if(obj.className=="drag"&&obj.tagName=="P"){
            var aa = (xxx-xx-$(templates_img).eq(1).offset().left)/$(templates_img).eq(1).width()*100+"%";
            var bb = (yyy-yy-$(templates_img).eq(1).offset().top)/$(templates_img).eq(1).height()*100+"%";
            $(obj).offset({"left":fnix,"top":fniy});
            $(templates_img).eq(0).find("p.drag").eq(p_index).css({"left":aa,"top":bb});
        }else if(obj.className=="drag"&&obj.tagName=="DIV"){
            var aa = (xxx-xx-$(templates_img).eq(1).offset().left)/$(templates_img).eq(1).width()*100+"%";
            var bb = (yyy-yy-$(templates_img).eq(1).offset().top)/$(templates_img).eq(1).height()*100+"%";
            $(obj).offset({"left":fnix,"top":fniy});
            $(templates_img).eq(0).find("div.drag").eq(div_index).css({"left":aa,"top":bb});
        }else if(obj.tagName=="IMG"){
            e.preventDefault();
            if($(obj).parent()[0].className=="drag-img"){
                if((img_top+(yyy-y)<0||img_top+(yyy-y)>temp_height-img_height||img_left+xxx-x<0||img_left+xxx-x>temp_width-img_width)){
                    return;
                }
                $(templates_img).eq(0).find(".drag-img").eq(drag_img_index).css({
                    "top":(img_top+(yyy-y))*(xtemp_width/temp_width),
                    "left":(img_left+(xxx-x))*(xtemp_width/temp_width)
                })
                $(obj).parent().css({
                    "top":img_top+yyy-y,
                    "left":img_left+xxx-x
                })
            }
        }else if(obj.className=="n"){

            if($(obj).parent()[0].className=="drag"){
                /******* div大小变化 ******/
                $(obj).parent().css({"height":div_height+(y-yyy),"top":div_top-(y-yyy)});
                $(templates_img).eq(0).find("div.drag").eq(div_index).css({
                    "height":(div_height+(y-yyy))*(xtemp_width/temp_width),
                    "top":(div_top-(y-yyy))*(xtemp_width/temp_width)
                });
            }else if($(obj).parent()[0].className=="drag-img"){
                /***图片大小变化***/
                $(templates_img).eq(0).find(".drag-img").eq(drag_img_index).css({
                    "width":ximg_width,
                    "height":ximg_height-(yyy-y)*(ximg_width/img_width),
                    "top":(img_top+yyy-y)*(ximg_width/img_width)
                });
                $(obj).parent().css({
                    "width":img_width,
                    "height":img_height-(yyy-y),
                    "top":img_top+yyy-y
                })
            }

        }else if(obj.className=="w"){
            if($(obj).parent()[0].className=="drag"){
                /******* div大小变化 ******/
                $(obj).parent().css({
                    "width":div_width-(xxx-x),
                    "left":div_left+(xxx-x)
                });
                $(templates_img).eq(0).find("div.drag").eq(div_index).css({
                    "width":(div_width-(xxx-x))*(xtemp_width/temp_width),
                    "left":(div_left+(xxx-x))*(xtemp_width/temp_width)
                });
            }else if($(obj).parent()[0].className=="drag-img"){
                /***图片大小变化***/
                $(templates_img).eq(0).find(".drag-img").eq(drag_img_index).css({
                    "width":ximg_width-(xxx-x)*(ximg_width/img_width),
                    "height":img_height*(ximg_width/img_width),
                    "left":(img_left+xxx-x)*(ximg_width/img_width)
                });
                $(obj).parent().css({
                    "width":img_width-(xxx-x),
                    "height":img_height,
                    "left":img_left+xxx-x
                });
            }
        }else if(obj.className=="s"){
            if($(obj).parent()[0].className=="drag"){
                /******* div大小变化 ******/
                $(obj).parent().css({"height":div_height+(yyy-y)});
                $(templates_img).eq(0).find("div.drag").eq(div_index).css({
                    "height":(div_height+(yyy-y))*(xtemp_width/temp_width)
                })
            }else if($(obj).parent()[0].className=="drag-img"){
                /***图片大小变化***/
                $(templates_img).eq(0).find(".drag-img").eq(drag_img_index).css({
                    "width":ximg_width,
                    "height":ximg_height+(yyy-y)*(ximg_width/img_width)
                });
                $(obj).parent().css({
                    "width":img_width,
                    "height":img_height+(yyy-y)
                })
            }else if($(obj).parent().find(".bg-img").children().length==0){
                /******单独写的高度*****/
                $(obj).parent().css({
                    "height":bgnheight+(yyy-y),
                })
                $(templates_img).eq(0).css({
                    "height":(bgnheight+(yyy-y))*(xtemp_width/temp_width),
                })
            }
        }else if(obj.className=="e"){
            if($(obj).parent()[0].className=="drag"){
                /******* div大小变化 ******/
                $(obj).parent().css({"width":div_width+(xxx-x)});
                $(templates_img).eq(0).find("div.drag").eq(div_index).css({
                    "width":(div_width+(xxx-x))*(xtemp_width/temp_width)
                })
            }else if($(obj).parent()[0].className=="drag-img"){
                /***图片大小变化***/
                $(templates_img).eq(0).find(".drag-img").eq(drag_img_index).css({
                    "width":ximg_width-(x-xxx)*(ximg_width/img_width),
                    "height":img_height*(ximg_width/img_width),
                });
                $(obj).parent().css({
                    "width":img_width-(x-xxx),
                    "height":img_height,
                });
            }
        }else if(obj.className=="nw"){
            if($(obj).parent()[0].className=="drag"){
                /******* div大小变化 ******/
                $(obj).parent().css({
                    "width":div_width-(xxx-x),
                    "height":(div_width-(xxx-x))*(div_height/div_width),
                    "top":div_top+(div_height-(div_width-(xxx-x))*(div_height/div_width)),
                    "left":div_left+(xxx-x)
                });
                $(templates_img).eq(0).find("div.drag").eq(div_index).css({
                    "width":(div_width-(xxx-x))*(xtemp_width/temp_width),
                    "height":(div_width-(xxx-x))*(div_height/div_width)*(xtemp_width/temp_width),
                    "top":(div_top+(div_height-(div_width-(xxx-x))*(div_height/div_width)))*(xtemp_width/temp_width),
                    "left":(div_left+(xxx-x))*(xtemp_width/temp_width),
                });
            }else if($(obj).parent()[0].className=="drag-img"){
                /***图片大小变化***/
                $(templates_img).eq(0).find(".drag-img").eq(drag_img_index).css({
                    "width":ximg_width-(xxx-x)*(ximg_width/img_width),
                    "height":(ximg_width-(xxx-x)*(ximg_width/img_width))*(ximg_height/ximg_width),
                    "top":(img_top+(xxx-x)*(img_height/img_width))*(ximg_width/img_width),
                    "left":(img_left+xxx-x)*(ximg_width/img_width),
                });
                $(obj).parent().css({
                    "width":img_width-(xxx-x),
                    "height":(img_width-(xxx-x))*(img_height/img_width),
                    "top":img_top+(xxx-x)*(img_height/img_width),
                    "left":img_left+xxx-x,
                })
            }
        }else if(obj.className=="ne"){
            if($(obj).parent()[0].className=="drag"){
                /******* div大小变化 ******/
                $(obj).parent().css({
                    "width":div_width+(xxx-x),
                    "height":(div_width+(xxx-x))*(div_height/div_width),
                    "top":div_top-(div_height-(div_width-(xxx-x))*(div_height/div_width))
                });
                $(templates_img).eq(0).find("div.drag").eq(div_index).css({
                    "width":(div_width+(xxx-x))*(xtemp_width/temp_width),
                    "height":(div_width+(xxx-x))*(div_height/div_width) *(xtemp_width/temp_width),
                    "top":(div_top-(div_height-(div_width-(xxx-x))*(div_height/div_width)))*(xtemp_width/temp_width),
                })
            }else if($(obj).parent()[0].className=="drag-img"){
                /***图片大小变化***/
                $(templates_img).eq(0).find(".drag-img").eq(drag_img_index).css({
                    "width":ximg_width+(xxx-x)*(ximg_width/img_width),
                    "height":(ximg_width+(xxx-x)*(ximg_width/img_width))*(img_height/img_width),
                    "top":(img_top+(x-xxx)*(img_height/img_width))*(ximg_width/img_width),
                });
                $(obj).parent().css({
                    "width":img_width+(xxx-x),
                    "height":(img_width+(xxx-x))*(img_height/img_width),
                    "top":img_top+(x-xxx)*(img_height/img_width),
                })
            }
        }else if(obj.className=="sw"){
            if($(obj).parent()[0].className=="drag"){
                /******* div大小变化 ******/
                $(obj).parent().css({
                    "width":div_width-(xxx-x),
                    "height":(div_width-(xxx-x))*(div_height/div_width),
                    "left":div_left+(xxx-x)
                });
                $(templates_img).eq(0).find("div.drag").eq(div_index).css({
                    "width":(div_width-(xxx-x))*(xtemp_width/temp_width),
                    "height":(div_width-(xxx-x))*(div_height/div_width)*(xtemp_width/temp_width),
                    "left":(div_left+(xxx-x))*(xtemp_width/temp_width),
                })
            }else if($(obj).parent()[0].className=="drag-img"){
                /***图片大小变化***/
                $(templates_img).eq(0).find(".drag-img").eq(drag_img_index).css({
                    "width":ximg_width-(xxx-x)*(ximg_width/img_width),
                    "height":(ximg_width-(xxx-x)*(ximg_width/img_width))*(img_height/img_width),
                    "left":(img_left+xxx-x)*(ximg_width/img_width),
                });
                $(obj).parent().css({
                    "width":img_width-(xxx-x),
                    "height":(img_width-(xxx-x))*(img_height/img_width),
                    "left":img_left+xxx-x,
                })
            }
        }else if(obj.className=="se"){
            if($(obj).parent()[0].className=="drag"){
                /******* div大小变化 ******/
                $(obj).parent().css({
                    "width":div_width+(xxx-x),
                    "height":(div_width+(xxx-x))*(div_height/div_width)
                });
                $(templates_img).eq(0).find("div.drag").eq(div_index).css({
                    "width":(div_width+(xxx-x))*(xtemp_width/temp_width),
                    "height":(div_width+(xxx-x))*(div_height/div_width)*(xtemp_width/temp_width),
                });
            }else if($(obj).parent()[0].className=="drag-img"){
                /***图片大小变化***/
                $(templates_img).eq(0).find(".drag-img").eq(drag_img_index).css({
                    "width":ximg_width-(x-xxx)*(ximg_width/img_width),
                    "height":(ximg_width-(x-xxx)*(ximg_width/img_width))*(img_height/img_width),
                });
                $(obj).parent().css({
                    "width":img_width-(x-xxx),
                    "height":(img_width-(x-xxx))*(img_height/img_width),
                })
            }
        }
    }).on("mouseup",function(){
        $(document).off("mousemove").off("mouseup");
    })
})
/*右键删除*/
function righthit(element){
    var e = window.event||event.target;
    if (e.button===2){
        var obj = e.target;
        console.log(obj);
        var x = e.pageX;
        var y = e.pageY;
        $(".rightkey").remove();
        $("<ul class='rightkey'>").html("<li class='right-del'>删除</li>" +
            "<li>5678</li>" +
            "<li>9012</li>").css({"left":x,"top":y,"z-index":100000}).appendTo($("body"));
        $("*").not(".rightkey").click(function(){
            $(".rightkey").remove();
        })
        $(document).off("click",".right-del");
        $(document).on("click",".right-del",function(){
            if(obj.tagName=="P"&&obj.className=="drag"){
                $(".templates-choose li").eq(temp_index).find("p.drag").eq(p_index).remove();
            }else if($(obj).parent()[0].className=="drag-img"){
                $(".templates-choose li").eq(temp_index).find(".drag-img").eq(drag_img_index).remove();
            }else if(obj.className=="drag"&&obj.tagName=="DIV"){
                $(".templates-choose li").eq(temp_index).find("div.drag").eq(div_index).remove();
            }else if($(obj).parent().parent().parent().parent()[0].className=="templates"){
                var index = $(obj).parents("ul.templates").find(".bg-img").index($(obj).parent());
                $(".templates-choose").find(".bg-img").eq(index).children().remove();
            }else if($(obj).parent().parent().parent().parent()[0].className=="templates-choose"){
                var index = $(obj).parents("ul.templates-choose").find(".bg-img").index($(obj).parent());
                $(".templates").find("li").eq(index).remove();

                $(".templates-choose").find("li").css("border","none");
                $(".templates-choose").find("li").eq(index+1).css("border","1px solid #ccc");
                //if(index!==0){
                //    $(".templates").find("li").eq(index-1).css("display","block");
                //}
            }
            $(element).remove();
        })
    }
}
/*双击编辑文本*/
function ShowElement(element) {
    var oldhtml = $(element).html();
    console.log(oldhtml);
    var newobj = document.createElement('input');
//创建新的input元素
    newobj.type = 'text';
    newobj.style.background = "none";
    newobj.value = oldhtml;
//为新增元素添加类型
    newobj.onblur = function(){
        element.innerHTML = this.value ? this.value : oldhtml;
        $(templates_img).eq(0).find("p.drag").eq(p_index).html(this.value ? this.value : oldhtml);
//当触发时判断新增元素值是否为空，为空则不修改，并返回原有值
    }
    element.innerHTML = '';
    element.appendChild(newobj);
    newobj.focus();
}
/****颜色****/
function changeColor(element) {
    var newobj = document.createElement('input');
//创建新的input元素
    newobj.type = 'color';
    newobj.value = '#fff';
    $(newobj).css({"position":"absolute","z-index":10000});
//为新增元素添加类型
    newobj.onblur = function(){
        element.style.backgroundColor = this.value;
//当触发时判断新增元素值是否为空，为空则不修改，并返回原有值
    }
    element.innerHTML = '';
    element.appendChild(newobj);
    newobj.focus();
}
/*添加文本*/
function addElement(){
    if($(".addText").prev().val()){
        var p = $("<p onmousedown='righthit(this)'>").addClass("drag").html($(".addText").prev().val()).appendTo($(templates_img));
        $(p).css({"color":$("input[type=color]").val(),"font-family":$(".font-fam").find("option:selected").html(),"font-size":$(".font-siz").find("option:selected").html(),"z-index":1});
    }
}
function dire(obj){
    $("div[dire=true]").remove();
    nw = $("<div class='nw' dire='true'>").css({"position":"absolute","width":10,"height":10,"border-radius":"50%","left":-5,"top":-5,"background":"#555","cursor":"nw-resize"});
    w = $("<div class='w' dire='true'>").css({"position":"absolute","width":10,"height":10,"left":-5,"top":"50%","background":"#555","cursor":"w-resize"});
    sw = $("<div class='sw' dire='true'>").css({"position":"absolute","width":10,"height":10,"border-radius":"50%","left":-5,"bottom":-5,"background":"#555","cursor":"sw-resize"});
    s = $("<div class='s' dire='true'>").css({"position":"absolute","width":10,"height":10,"left":"50%","bottom":-5,"background":"#555","cursor":"s-resize"});
    se = $("<div class='se' dire='true'>").css({"position":"absolute","width":10,"height":10,"border-radius":"50%","right":-5,"bottom":-5,"background":"#555","cursor":"se-resize"});
    e = $("<div class='e' dire='true'>").css({"position":"absolute","width":10,"height":10,"right":-5,"top":"50%","background":"#555","cursor":"e-resize"});
    ne = $("<div class='ne' dire='true'>").css({"position":"absolute","width":10,"height":10,"border-radius":"50%","right":-5,"top":-5,"background":"#555","cursor":"ne-resize"});
    n = $("<div class='n' dire='true'>").css({"position":"absolute","width":10,"height":10,"left":"50%","top":-5,"background":"#555","cursor":"n-resize"});
    $(nw).appendTo(obj);
    $(w).appendTo(obj);
    $(sw).appendTo(obj);
    $(s).appendTo(obj);
    $(se).appendTo(obj);
    $(e).appendTo(obj);
    $(ne).appendTo(obj);
    $(n).appendTo(obj);
}
function delDire(obj){
    $(obj).find("div[dire=true]").remove();
}
/*$(function(){})*/
$(function(){
    $(".addText").click(function(){
        addElement();
    });
    $(document).on("dblclick","p.drag",function(){
        ShowElement(this);
        $("input[type=color]").off("change");
        $("input[type=color]").change(function(){
            $(templates_img).eq(1).find("p.drag").eq(p_index).css("color",$(this).val());
            $(templates_img).eq(0).find("p.drag").eq(p_index).css("color",$(this).val());
        });
        $("select").change(function(){
            $(templates_img).eq(1).find("p.drag").eq(p_index).css("font-family",$(this).find("option:selected").html());
            $(templates_img).eq(1).find("p.drag").eq(p_index).css("font-size",$(this).find("option:selected").html());
            $(templates_img).eq(0).find("p.drag").eq(p_index).css("font-family",$(this).find("option:selected").html());
            $(templates_img).eq(0).find("p.drag").eq(p_index).css("font-size",$(this).find("option:selected").html());
        });
        $(".through").off("click");
        $(".through").on("click",function(){
            if($(templates_img).eq(1).find("p.drag").eq(p_index).css("text-decoration")=="none"){
                $(templates_img).eq(1).find("p.drag").eq(p_index).css("text-decoration","line-through");
                $(templates_img).eq(0).find("p.drag").eq(p_index).css("text-decoration","line-through");
            }else{
                $(templates_img).eq(1).find("p.drag").eq(p_index).css("text-decoration","none");
                $(templates_img).eq(0).find("p.drag").eq(p_index).css("text-decoration","none");
            }
        })
    });

    //$.get("pingou.html",function(data){ //初始將a.html include div#iframe
    //    $(".templates-choose li").eq(0).html(data);
    //});

    /*新建div*/
    $(".new-div").click(function(){
        div = $("<div class='drag' onmousedown='righthit(this)' onclick='dire(this)' ondblclick='delDire(this)'>");
        $(div).css({"position":"absolute","left":0,"top":"80%","background":"#ccc","height":"20%","width":"100%"}).appendTo($(templates_img)).on("click",function(){
            $("input[type=color]").off("change").change(function(){
                $(templates_img).eq(1).find("div.drag").eq(div_index).css("background",$(this).val());
                $(templates_img).eq(0).find("div.drag").eq(div_index).css("background",$(this).val());
            });
            $(".opa").change(function(){
                $(templates_img).eq(1).find("div.drag").eq(div_index).css("opacity",$(this).val()/100);
                $(templates_img).eq(0).find("div.drag").eq(div_index).css("opacity",$(this).val()/100);
            });
        })
        $(div).off("mousedown");
    })
    $(document).on("click",".templates-choose li",function(e){
        temp_index = $(this).index(".templates-choose li");
        $(".templates li").css("display","none");
        $(".templates li").eq(temp_index).css("display","block");
        $(".templates-choose li").css("border","none");
        $(this).css("border","1px solid #ccc");
        /****保存对象****/
        templates_img = $(".temp-img").parents("ul").find("li:nth-child("+(temp_index+1)+")").children();
        temp_img = $(templates_img).find("img");
    });
    /*********新增模版**********/
    $(".add-temp").click(function(){
        var height1 = $(templates_img).eq(0).height();
        var width1 = $("ul.templates-choose").width();
        var height2 = $(templates_img).eq(1).height();
        var width2 = $("ul.templates").width();
        if(height1==null||height2==null){
            height1 = width1/1.5;
            height2 = width2/1.5;
        }

        var html_li ='<div class="temp-img" style="height:'+height1+'px;">' +
            '<div class="bg-img" onmousedown="righthit(this.childNodes)">' +
            '</div>' +
            '</div>';
        var html_li_1 ='<div class="temp-img" style="height:'+height2+'px;">' +
            '<div class="bg-img" onmousedown="righthit(this.childNodes)">' +
            '</div>' +
            '</div>';
        $("<li onmousedown='righthit(this);'>").html(html_li).appendTo($(this).prev());
        $("<li>").html(html_li_1).appendTo(".templates");
    });

    $(document).on("click","ul.templates .bg-img",function(){
        //$("input[type=color]").click();
        $("input[type=color]").change(function(){
            $(templates_img).eq(1).find(".bg-img").css("background",$(this).val());
            $(templates_img).eq(0).find(".bg-img").css("background",$(this).val());
        })
    })
    $(".changewh").click(function(){
        if(!(!$(".setwidth").val()&&!$(".setheight").val())){
            $(templates_img).eq(1).css({
                "width":parseInt($(".setwidth").val()) + 2,
                "height":parseInt($(".setheight").val()) + 2
            })
        }
    })
    $("ul.templates").on("click",$(templates_img).find(".bg-img"),function(){
        if($(templates_img).find(".bg-img").children().length==0){
            dire($(templates_img).eq(1));
        }
    }).on("dblclick",$(templates_img).find(".bg-img"),function(){
        if($(templates_img).find(".bg-img").children().length==0){
            delDire($(templates_img).eq(1));
        }
    })

    //生成图片    Added by Tiffany Zhu
            $("#createImg").click(function(){
                    var isPost = true;
                    var width = $(".templates").find("li:visible").find(".temp-img").width();
                    var height = $(".templates").find("li:visible").find(".temp-img").height();
                    var html = $(".templates").find("li:visible").find(".temp-img").prop("outerHTML");
                    alert("html:" + html);
                    alert("图片宽度:" + width + "图片高度:" + height );

                    if(html == null || html == ""){
                        isPost = false;
                        alert("请添加模板!");
                        return false;
                    }
                     if(isPost){
                         $.ajax({
                            url: window.cutUrl, //Server script to process data
                            //url: "http://172.28.3.18:3008/cut", //Server script to process data
                            type: 'post',
                            data: {
                                html: '' +html,
                                width:width,
                                height:height
                            },
                            success: function(data) {
                                console.log(JSON.stringify(data));
                                var input = document.createElement("input");
                                input.id = data.oss_url;
                                input.type = "hidden";
                                $(".templates-choose").find("li").each(function(){
                                    if($(this).css("border-top-style") == "solid"){
                                        $(this).append(input);
                                    }
                                })
                                //alert(data.oss_url);
                                window.open(data.shot_url,'_blank');
                                //window.open(data.oss_prefix + data.oss_url,'_blank');
                            },
                            error: function(data, error, errorThrown) {
                                if (data.status && data.status >= 400) {
                                    alert(data.responseText);
                                } else {
                                    alert("Something went wrong");
                                }
                            }
                         });
                      }
                })
            //保存 Added by Tiffany Zhu
            $("#save").click(function(){
                var template = {};
                var url = "";
                var navigatorHtml = "";
                var id = 0;
                $(".templates-choose").find("li").each(function(){
                    if($(this).css("border-top-style") == "solid"){
                        if($(this).find("input").length != 0){
                            url = $(this).find("input").attr("id");
                        }
                        navigatorHtml = $(this).html();
                    }
                })

                if($(".templates").find("li:visible").attr("id") != null){
                    id = Number($(".templates").find("li:visible").attr("id"));
                }

                template.id = id;
                template.url = url.substring(url.indexOf('/',url.indexOf('/')+2)+1);
                template.navigatorHtml = navigatorHtml;
                template.contentHtml = $(".templates").find("li:visible").find(".temp-img").prop("outerHTML");

                $.ajax({
                   type :  "POST",
                   url : "/topic/templates/save",
                   contentType: "application/json; charset=utf-8",
                   data : JSON.stringify(template),
                   error : function(request) {
                     if (window.lang = 'cn') {
                         $('#js-userinfo-error').text('保存失败');
                     } else {
                         $('#js-userinfo-error').text('Save error');
                     }
                         setTimeout("$('#js-userinfo-error').text('')", 2000);
                     },
                   success: function(data) {
                     alert("Save Success");
                     if (window.lang = 'cn') {
                         $('#js-userinfo-error').text('保存成功').css('color', '#2fa900');
                     } else {
                         $('#js-userinfo-error').text('Save success');
                     }
                     //setTimeout("$('#js-userinfo-error').text('').css('color','#c00')", 3000);

                   }
                });
            })

            //下一步   Added by Tiffany Zhu
            $("#nextStep").click(function(){
               var sharedObject = window.dialogArguments;
               var isNext = true;
               var url = "";
               $(".templates-choose").find("li").each(function(){
                   if($(this).css("border-top-style") == "solid"){
                        url = $(this).find("input").attr("id");
                        if(url == "" || url == null){
                        alert("请先生成图片!");
                          isNext = false;
                        }
                   }
               })

               if(isNext){
                    var width = $(".templates").find("li:visible").find(".temp-img").width();
                    var height = $(".templates").find("li:visible").find(".temp-img").height();
                    sharedObject = {};
                    sharedObject.url = url;
                    sharedObject.width = width;
                    sharedObject.height = height;
                    window.opener.updateThemeImg (sharedObject);
                    window.close();
               }
            })
});
