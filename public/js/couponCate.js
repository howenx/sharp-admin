$(function() {

    $('#datetimepicker1').datetimepicker({
        locale: 'zh-cn',
        format:'YYYY-MM-DD 00:00:00'
    });
    $('#datetimepicker2').datetimepicker({
        locale: 'zh-cn',
        format:'YYYY-MM-DD 23:59:59'
    });

    //默认不指定商品
    $(".h4-custom-hmm").css("display","none");
    $(".goods-change").css("display","none");
    $(".cates-change").css("display","none");
    $(".theme-change").css("display","none");

    /*************指定类型选择*************/
    $("input[name='assignType']").click(function () {
        var index = $("input[name='assignType']").index(this);
        if(index === 1){
            $(".h4-custom-hmm").css("display","block");
            $(".goods-change").css("display","block");
        }else{
            $(".h4-custom-hmm").css("display","none");
            $(".goods-change").css("display","none");
        }
    })

    /*************勾选指定类型*************/
    $(".h4-custom-hmm>input").click(function () {
        $(this).parent().next().toggle()
    })



});

function ShowModalTheme($obj) {
    var sharedObject = {};
    sharedObject = $obj;
    if (window.showModalDialog) {
        var retValue = showModalDialog("/topic/popup", sharedObject, "dialogWidth:1300px; dialogHeight:800px; dialogLeft:300px;");
        if (retValue) {
            UpdateFields(retValue);
        }
    }
    else {
        // for similar functionality in Opera, but it's not modal!
        var modal = window.open ("/topic/popup", null, "width=1300,height=800,left=300,modal=yes,alwaysRaised=yes", null);
        modal.dialogArguments = sharedObject;
    }
}