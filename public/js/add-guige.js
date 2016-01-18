/*********改变文本方法*********/
function changeText(event,element){
    var e = window.event||event;
    var obj = e.target;
    if(obj.tagName=="INPUT"){
        return;
    }
    var oldHtml = $(element).html();
    console.log(oldHtml);

    if(element.childNodes.length==0
        ||(element.childNodes.length==1&&element.childNodes[0].nodeType==3)){
        var addText = $("<input type='text' autofocus='autofocus'>").css({
            "width":"100%"
        }).val(oldHtml);
        $(addText).blur(function(){
            $(element).html(this.value?this.value:oldHtml);
            if(element.tagName=="SPAN"){
                $(element).css({
                    "width":'100%',
                    "display":"inline"
                })
            }
        })
    }
    element.innerHTML = "";
    $(addText).appendTo($(element));
    addText.focus();
}

$(function(){
    /*********切换显示/隐藏table*********/
    $(".YN").click(function(){
        $(".guige").toggleClass("block");
    });
    /*********更改文本*********/
    $(document).on("click",".guige td:not(:last-of-type)",function(e){
        changeText(e,this);
    });

    $(document).on("click","ul span",function(e){
        changeText(e,this);
    });
    /********关闭********/
    $(document).on("click",".size .close",function(){
        $(this).parent().remove();
    });
    $(document).on("click",".color .close",function(){
        $(this).parent().remove();
    });
    /*********添加一行*********/
    $(document).on("click",".addTr",function(){
        var trHtml = '<td></td><td></td><td class="delTr">删除</td>';
        $("<tr>").html(trHtml).appendTo($(".guige"));
    });
    /*********删除一行*********/
    $(document).on("click",".delTr",function(){
        $(this).parent().remove();
    });
    /*********添加尺寸*********/
    $(".add-size").click(function(){
        //var oldheight = $(".size").find("li").find("span").height();
        //var oldwidth = $(".size").find("li").find("span").width();
        var html = '<span style="display:inline-block;width: '+80+'px;height: '+17+'px"></span>' +
            '<button type="button" class="close" aria-label="Close"><span aria-hidden="true">&times;</span></button>';
        $("<li onclick='changeText(e,this.childNodes[0])'>").html(html).appendTo($(".size"));
    });
    /*********添加颜色*********/
    $(".add-color").click(function(){
        //var oldheight = $(".color").find("li").find("span").height();
        //var oldwidth = $(".color").find("li").find("span").width();
        var html = '<span style="display:inline-block;width: '+80+'px;height: '+17+'px"></span>' +
            '<button type="button" class="close" aria-label="Close"><span aria-hidden="true">&times;</span></button>';
        $("<li onclick='changeText(e,this.childNodes[0])'>").html(html).appendTo($(".color"));
    })
});