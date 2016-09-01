$(function() {

    $('#datetimepicker1').datetimepicker({
        locale: 'zh-cn',
        format:'YYYY-MM-DD 00:00:00'
    });
    $('#datetimepicker2').datetimepicker({
        locale: 'zh-cn',
        format:'YYYY-MM-DD 23:59:59'
    });

    if ($("#assignType").val()=="assign")  $("input[name='assignType']:eq(1)").attr("checked",true);
    if ($("#assignType").val()=="none")  $("input[name='assignType']:eq(0)").attr("checked",true);

    if ($("#couponType").val()==1)  $("input[name='couponType']:eq(0)").attr("checked",true);
    if ($("#couponType").val()==2)  $("input[name='couponType']:eq(1)").attr("checked",true);
    if ($("#couponType").val()==3)  $("input[name='couponType']:eq(2)").attr("checked",true);

    //根据指定类型显示商品选择块
    var orAssign = $("input[name='assignType']:checked").val();
    if (orAssign=="assign")  $(".assign").css("display","block");
    else if (orAssign=="none") $(".assign").css("display","none");


    //修改页面根据是否有数据判断显示/隐藏
    var itemSize = $("#itemSize").val();
    var skusSize = $("#skusSize").val();
    var catesSize = $("#catesSize").val();
    var themeSize = $("#themeSize").val();
    if (itemSize + skusSize > 0)  {
        $("#goodsSel").attr("checked",'true');
        $(".goods-change").css("display","block");
    }
    if (catesSize > 0) {
         $("#catesSel").attr("checked",'true');
         $(".cates-change").css("display","block");
    }
    if (themeSize > 0) {
        $("#themeSel").attr("checked",'true');
        $(".theme-change").css("display","block");
    }

    /*************指定类型选择*************/
    $("input[name='assignType']").click(function () {
        var index = $("input[name='assignType']").index(this);
        if(index === 1){//指定
            $(".assign").css("display","block");
        } else {//不指定
            $(".assign").css("display","none");
        }
    })

    /*************勾选指定类型*************/
    $(".h4-custom-hmm>input").click(function () {
        $(this).parent().next().toggle()
    })

    /** 数据提交 **/
    $("#submit").click(function(){
        var isPost = true;
        var numberReg1 = /^-?\d+\.?\d{0,2}$/;   //整数或小数
        var coupCateNm = $("#coupCateNm").val();
        var limitQuota = $("#limitQuota").val();
        var denomination = $("#denomination").val();
        var startAt = $("#startAt").val();
        var endAt = $("#endAt").val();

        //验证使用期限
        var now = new Date();
        var nowTime = now.getFullYear()+"-"+(now.getMonth()+1>=10?now.getMonth()+1:'0'+(now.getMonth()+1))+"-"+(now.getDate()>=10?now.getDate():'0'+now.getDate())+" "+(now.getHours()>=10?now.getHours():'0'+now.getHours())+":"+(now.getMinutes()>=10?now.getMinutes():'0'+now.getMinutes())+":"+(now.getSeconds()>=10?now.getSeconds():'0'+now.getSeconds());
        var maxDate = now.setMonth(now.getMonth()+6);
        var d1 = new Date(Date.parse(startAt.replace(/-/g,"/")));//开始时间
        var d2 = new Date(Date.parse(endAt.replace(/-/g,"/")));//结束时间

        //优惠券类型
        var couponType = $("input[name='couponType']:checked").val();

        //商品选择
        var assignType = $("input[name='assignType']:checked").val();
        var orGoodsSel = $("input[id='goodsSel']").is(':checked');
        var orCatesSel = $("input[id='catesSel']").is(':checked');
        var orThemeSel = $("input[id='themeSel']").is(':checked');
        var goodsLen = $("#goods tbody").find("tr").length;
        var catesLen = $("#cates tbody").find("tr").length;
        var themeLen = $("#theme tbody").find("tr").length;

        //验证必填项及数据正确性
        if (coupCateNm=="" || !numberReg1.test(limitQuota) || !numberReg1.test(denomination) || couponType=="") {
            isPost=false;
            $('#js-userinfo-error').text('必填项有误');
        } else if (startAt=="" || endAt=="" || startAt >= endAt ) {
           isPost = false;
           $('#js-userinfo-error').text('请检查时间设置');
        } else if (endAt<nowTime) {
           isPost = false;
           $('#js-userinfo-error').text('截止时间不能小于当前时间');
        } else if (d1>=maxDate || d2>=maxDate) {
            isPost = false;
            $('#js-userinfo-error').text('开始时间和结束时间不能超过当前时间6个月');
        }
        else if (assignType=="assign" && !orGoodsSel && !orCatesSel && !orThemeSel) {
             isPost = false;
             $('#js-userinfo-error').text('请指定商品');
        }
        else if (assignType=="assign" && orGoodsSel && goodsLen==0) {
             isPost = false;
             $('#js-userinfo-error').text('请选择商品');
        }
        else if (assignType=="assign" && orCatesSel && catesLen==0) {
             isPost = false;
             $('#js-userinfo-error').text('请选择商品类别');
        }
        else if (assignType=="assign" && orThemeSel && themeLen==0) {
             isPost = false;
             $('#js-userinfo-error').text('请选择主题');
        }
        else $('#js-userinfo-error').text('');

        var CouponsCate = new Object();
        CouponsCate.coupCateNm = coupCateNm;
        CouponsCate.limitQuota = limitQuota;
        CouponsCate.denomination = denomination;
        CouponsCate.startAt = startAt;
        CouponsCate.endAt = endAt;
        CouponsCate.couponType = couponType;

        var couponsMapList = [];
        if (assignType=="assign" && orGoodsSel && goodsLen > 0) {
            var goods = $("#goods tbody").find("tr");
            for(var i=0;i<goods.length;i++) {
                var CouponsMap = new Object();
                var cateType = goods[i].getElementsByTagName("td")[4].innerText;
                var cateTypeId = "";
                if (cateType=="商品") {
                    cateType = 2;
                    cateTypeId = goods[i].getElementsByTagName("td")[3].innerText;
                }
                if (cateType=="SKU") {
                    cateType = 3;
                    cateTypeId = goods[i].getElementsByTagName("td")[1].innerText;
                }
                if (cateType=="拼购商品") {
                    cateType = 4;
                    cateTypeId = goods[i].getElementsByTagName("td")[1].innerText;
                }
                CouponsMap.cateType = cateType;
                CouponsMap.cateTypeId = cateTypeId;
                couponsMapList.push(CouponsMap);
            }
        }
        if (assignType=="assign" && orCatesSel && catesLen > 0) {
            var cates = $("#cates tbody").find("tr");
            for(var j=0;j<cates.length;j++) {
                var CouponsMap = new Object();
                var cateType = cates[j].getElementsByTagName("td")[3].getElementsByTagName("input")[0].value;
                var cateTypeId = "";
                if (cateType=="first") {
                    cateType = 7;
                    cateTypeId = cates[j].getElementsByTagName("td")[1].innerText;
                }
                if (cateType=="second") {
                    cateType = 5;
                    cateTypeId = cates[j].getElementsByTagName("td")[1].innerText;
                }
                CouponsMap.cateType = cateType;
                CouponsMap.cateTypeId = cateTypeId;
                couponsMapList.push(CouponsMap);
            }
        }
        if (assignType=="assign" && orThemeSel && themeLen > 0) {
            var theme = $("#theme tbody").find("tr");
            for(var k=0;k<theme.length;k++) {
                var CouponsMap = new Object();
                var cateType = 6;
                var cateTypeId = theme[k].getElementsByTagName("td")[1].innerText;
                CouponsMap.cateType = cateType;
                CouponsMap.cateTypeId = cateTypeId;
                couponsMapList.push(CouponsMap);
            }
        }
        if (assignType=="none") {
            var CouponsMap = new Object();
            CouponsMap.cateType = 1;
            CouponsMap.cateTypeId = 0;
            couponsMapList.push(CouponsMap);
        }

        var couponsCateData = new Object();
        couponsCateData.couponsCate = CouponsCate;
        couponsCateData.couponsMapList = couponsMapList;

        console.log(isPost);
        console.log(JSON.stringify(couponsCateData));
        if (false) {
            $.ajax({
                type :  "POST",
                url : "/coup/coupCateSave",
                contentType: "application/json; charset=utf-8",
                data : JSON.stringify(couponsCateData),
                error : function(request) {
                    if (window.lang = 'cn') {
                        $('#js-userinfo-error').text('保存失败');
                    } else {
                        $('#js-userinfo-error').text('Save error');
                    }
                    setTimeout("$('#js-userinfo-error').text('')", 2000);
                },
                success: function(data) {
                    if (data=="保存成功") {
                        $('#js-userinfo-error').text('保存成功').css('color', '#2fa900');
                        $('.usercenter-option > .user-state').css('background-position', '20px -174px');
                        $('.usercenter-option > .user-state').text('未更改');
//                        setTimeout("location.href='/"+window.lang+"/coupCate/search'", 3000);
                    }
                    else {
                        $('#js-userinfo-error').text('保存失败');
                    }
                    setTimeout("$('#js-userinfo-error').text('').css('color','#c00')", 2000);
                }
            });
        }
    });
});

/** 数据改变的提示 **/
$(document).on('change', '.form-data-area', function() {
    $('.usercenter-option > .user-state').css('background-position', '20px -73px');
    $('.usercenter-option > .user-state').text('已更改');
});

//选择主题弹窗
function ShowModalTheme($obj) {
    var sharedObject = {};
    var theme_arr = [];
    var themes = $("#theme tbody").find("tr");
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
    var index1 = $("#theme tbody").find("tr").length;
    var index2 = $("#theme thead").find("tr").length;
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
    var cates = $("#cates tbody").find("tr");
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
    var index1 = $("#cates tbody").find("tr").length;
    var index2 = $("#cates thead").find("tr").length;
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
    var goods = $("#goods tbody").find("tr");
    for(i=0;i<goods.length;i++) {
        var goods_id = goods[i].getElementsByTagName("td")[1].innerText;
        goods_arr.push(goods_id);
    }
    sharedObject.goods_arr = goods_arr;
//    console.log(goods_arr);
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
    var index1 = $("#goods tbody").find("tr").length;
    var index2 = $("#goods thead").find("tr").length;
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
