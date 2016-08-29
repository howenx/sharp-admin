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

//选择主题弹窗
function ShowModalTheme($obj) {
    var sharedObject = {};
    var theme_arr = [];
    var themes = $(".table tbody").find("tr");
    for(i=0;i<themes.length;i++) {
        var theme_id = themes[i].getElementsByTagName("td")[1].innerText;
        theme_arr.push(theme_id);
    }
    sharedObject.theme_arr = theme_arr;
//    console.log(theme_arr);
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

//选中的主题数据
function UpdateFieldsTheme(obj) {
    var obj1 = obj.id;
    var index1 = $("#theme tbody").find("tr").length,
        index2 = $("#theme thead").find("tr").length;
    var index = index1 + index2;
    for (var i = 0; i < $(obj1).length; i++) {
        $(obj1).eq(i).prepend($("<td class='index'>" + (Number(index) + i) + "</td>")).appendTo($("#theme"));
    }
}

/** 删除一条主题 **/
 $(document).on("click",".theme-del",function(){
     if (window.confirm("确定删除吗?")) {
        //用户总数
        var themeCount = $("#theme").find("tr").length - 1;
        //被删除行的编号
        var delColNum = $(this).parents("tr").find("td:eq(0)").text();
        //console.log("被删除行编号delColNum:" + delColNum);
        if(themeCount > delColNum){
            for(i=parseInt(delColNum);i<themeCount;i++){
                var j = i + 1;
                $("#theme").find("tr:eq("+j+")").find("td:eq(0)").text(i);
            }
        }
        $(this).parents("tr").remove();
     }
 });

//选择类别弹窗
function ShowModalCates($obj) {
    var sharedObject = {};
    var cates_arr = [];
    var cates = $(".table tbody").find("tr");
    for(i=0;i<cates.length;i++) {
        var cates_id = cates[i].getElementsByTagName("td")[1].innerText;
        cates_arr.push(cates_id);
    }
    sharedObject.cates_arr = cates_arr;
//    console.log(cates_arr);
    if (window.showModalDialog) {
        var retValue = showModalDialog("/comm/cate/popup", sharedObject, "dialogWidth:1300px; dialogHeight:800px; dialogLeft:300px;");
        if (retValue) {
            UpdateFields(retValue);
        }
    }
    else {
        // for similar functionality in Opera, but it's not modal!
        var modal = window.open ("/comm/cate/popup", null, "width=1300,height=800,left=300,modal=yes,alwaysRaised=yes", null);
        modal.dialogArguments = sharedObject;
    }
}

//选中的类别数据
function UpdateFieldsCates(obj) {
    var obj1 = obj.id;
    var index1 = $("#cates tbody").find("tr").length,
        index2 = $("#cates thead").find("tr").length;
    var index = index1 + index2;
    for (var i = 0; i < $(obj1).length; i++) {
        $(obj1).eq(i).prepend($("<td class='index'>" + (Number(index) + i) + "</td>")).appendTo($("#cates"));
    }
}

/** 删除一条类别 **/
 $(document).on("click",".cates-del",function(){
     if (window.confirm("确定删除吗?")) {
        //用户总数
        var catesCount = $("#cates").find("tr").length - 1;
        //被删除行的编号
        var delColNum = $(this).parents("tr").find("td:eq(0)").text();
        //console.log("被删除行编号delColNum:" + delColNum);
        if(catesCount > delColNum){
            for(i=parseInt(delColNum);i<catesCount;i++){
                var j = i + 1;
                $("#cates").find("tr:eq("+j+")").find("td:eq(0)").text(i);
            }
        }
        $(this).parents("tr").remove();
     }
 });


 //选择商品弹窗
function ShowModalGoods($obj) {
    var sharedObject = {};
    var goods_arr = [];
    var goods = $(".table tbody").find("tr");
    for(i=0;i<goods.length;i++) {
        var goods_id = goods[i].getElementsByTagName("td")[1].innerText;
        goods_arr.push(goods_id);
    }
    sharedObject.goods_arr = goods_arr;
    console.log(goods_arr);
    if (window.showModalDialog) {
        var retValue = showModalDialog("/comm/poup", sharedObject, "dialogWidth:1300px; dialogHeight:800px; dialogLeft:300px;");
        if (retValue) {
            UpdateFields(retValue);
        }
    }
    else {
        // for similar functionality in Opera, but it's not modal!
        var modal = window.open ("/comm/poup", null, "width=1300,height=800,left=300,modal=yes,alwaysRaised=yes", null);
        modal.dialogArguments = sharedObject;
    }
}

//选中的商品数据
function UpdateFieldsGoods(obj) {
    var obj1 = obj.id;
    var index1 = $("#goods tbody").find("tr").length,
        index2 = $("#goods thead").find("tr").length;
    var index = index1 + index2;
    for (var i = 0; i < $(obj1).length; i++) {
        $(obj1).eq(i).prepend($("<td class='index'>" + (Number(index) + i) + "</td>")).appendTo($("#goods"));
    }
}

/** 删除一条商品 **/
 $(document).on("click",".goods-del",function(){
     if (window.confirm("确定删除吗?")) {
        //用户总数
        var goodsCount = $("#goods").find("tr").length - 1;
        //被删除行的编号
        var delColNum = $(this).parents("tr").find("td:eq(0)").text();
        //console.log("被删除行编号delColNum:" + delColNum);
        if(goodsCount > delColNum){
            for(i=parseInt(delColNum);i<goodsCount;i++){
                var j = i + 1;
                $("#goods").find("tr:eq("+j+")").find("td:eq(0)").text(i);
            }
        }
        $(this).parents("tr").remove();
     }
 });