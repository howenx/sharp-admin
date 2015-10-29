$(function(){
    $(".content li").hover(function(){
        $(this).find(".imgbg").css("display","block");
    },function(){
        $(this).find(".imgbg").css("display","none");
    });
    $(".font1").click(function(){
        var index=$(".content li").index($(this).parents("li"));
        if(index==0){
            return;
        }
        $(this).parents("li").insertBefore($(".content li").eq(index-1));
        $(this).parent().css("display","none");
        $(this).parents("li").next().find(".imgbg").css("display","block");
    });
    $(".font2").click(function(){
        $(this).parents("li").remove();
    })
    $(".top").click(function(){
        var index=$(".content li").index($(this).parents("li"));
        $(this).parents("li").insertBefore($(".content li").first());
        $(this).parent().css("display","none");
        $(".content li").eq(index).find(".imgbg").css("display","block");
    })
});