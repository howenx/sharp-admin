/**
 * Created by cedric on 15/10/28.
 */
$(function(){
    $(".contentA .nav>li").click(function(){
        $(".contentA .nav>li").removeClass("cur");
        $(this).addClass("cur");
    })
})