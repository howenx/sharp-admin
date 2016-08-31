/**
 * Created by cedric on 16/8/31.
 */
$(function () {
    // 回车==点击 确认添加
    $(window).keypress(function (e) {
        if(e.keyCode===13){
            $(".return").trigger("click");
        };
    })
})