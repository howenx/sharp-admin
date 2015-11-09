$(function(){
    $(".glyphicon").click(function(){
        if($(this).hasClass("glyphicon-chevron-up")){
            $(this).removeClass("glyphicon-chevron-up");
            $(this).addClass("glyphicon-chevron-down");
        }else{
            $(this).removeClass("glyphicon-chevron-down");
            $(this).addClass("glyphicon-chevron-up");
        }
    });
    $(".main-img").click(function(){
        $(".goods-img-bg").css({"height":$(window).height(),"display":"block"});
        $(".goods-img").css("left",($(window).width()-1200)/2);
        $(this).clone().appendTo($(".goods-img")).css({"width":"100%","z-index":1000});
    })
    $(".goods-img-bg .close").click(function(){
        $(".goods-img-bg img").remove();
        $(".goods-img-bg").css({"display":"none"});
    })
    $(document).on("click",".goods-bg",function(e){
        $(".goods-img-bg img").remove();
        $(".goods-img-bg").css({"display":"none"});
    })
})