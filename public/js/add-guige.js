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
            if($(element).parent().find(":input")){
                $(element).parent().find(":input").val($(element).html());
            }
        })
    }
    element.innerHTML = "";
    $(addText).appendTo($(element));
    addText.focus();
}
/******初始数据******/
var option,sku;
function Init () {
    var sharedObject = window.dialogArguments;
    option = sharedObject.obj;
    if(option.tagName=="TD"){
        sku = sharedObject.sku;
        console.log(sku.itemPrice);
        $("input[name=itemPrice]").val(sku.itemPrice);
    }
}
/*****保存******/
function holdShow() {
    /**存储信息**/
    if($(option).hasClass("add-guige")){
        var trh = $("<tr>");
        var trd = $("<tr>");
        if (window.showModalDialog) {
            var sharedObject = {};
            window.returnValue = sharedObject;
        }
        else {
            var trdobj = {};
            trdobj.itemColor = $("input[name=itemColor]:checked").val();
            trdobj.itemSize = $("input[name=itemSize]:checked").val();
            /***销售日期开始***/
            trdobj.itemDate = {};
            trdobj.itemDate.startAt = $("input[name=startAt]").val();
            trdobj.itemDate.endAt = $("input[name=endAt]").val();
            /***销售日期结束***/
            trdobj.invWeight = $("input[name=invWeight]").val();
            trdobj.restrictAmount = $("input[name=restrictAmount]").val();
            trdobj.itemPrice = $("input[name=itemPrice]").val();
            trdobj.itemSrcPrice = $("input[name=itemSrcPrice]").val();
            trdobj.itemCostPrice = $("input[name=itemCostPrice]").val();
            trdobj.itemDiscount = $("input[name=itemDiscount]").val();
            trdobj.amount = $("input[name=amount]").val();
            trdobj.amountremain = $("input[name=amountremain]").val();
            trdobj.carriageModelCode = $("select[name=carriageModelCode]").val();
            trdobj.invCustoms = $("select[name=invCustoms]").val();
            trdobj.invArea = $("select[name=invArea]").val();
            trdobj.rateSet = $("select[name=rateSet]").val();
            trdobj.rateRoundNum = $("input[name=rateRoundNum]").val();
            trdobj.rateNum = $("input[name=rateNum]").val();
            /***海关备案号开始***/
            trdobj.record = {};
            trdobj.record.hz = $("input[name=hz]").val();
            trdobj.record.gz = $("input[name=gz]").val();
            trdobj.record.sh = $("input[name=sh]").val();
            /***海关备案号结束***/
            trdobj.img1 = 1;
            trdobj.img2 = 2;
            $("<th>").html("设为主商品").appendTo(trh);
            $("<td>").html('<input type="radio" name="orMasterInv" checked="checked" class="master-radio"/>').appendTo(trd);
            for(var item in trdobj){
                $("<td>").html(trdobj[item]).appendTo(trd);
            };
            $(".thval").each(function(){
                $("<th>").html($(this).html()).appendTo(trh)
            });
            $("<th>").html("修改").appendTo(trh);
            $("<td onclick='ShowModal(this,sku1)'>").html("编辑").appendTo(trd);
            var sharedObject = {};
            sharedObject.trh = trh;
            sharedObject.trd = trd;
            sharedObject.sku = trdobj;
            window.opener.updateTable (sharedObject);
        }
    }else if(option.tagName=="TD"){

    }
    //window.close();
}
$(function(){
    $(document).on("click","input[name=color]",function(){
        $(".color").find(":input").removeClass("vals");
        $(this).addClass("vals");
    });
    $(document).on("click","input[name=size]",function(){
        $(".size").find(":input").removeClass("vals");
        $(this).addClass("vals");
    });
    /*********切换显示/隐藏table*********/
    $(".YN").click(function(){
        $(".guige").toggleClass("block");
    });
    /*********更改文本*********/
    $(document).on("click",".guige td:not(:last-of-type)",function(e){
        changeText(e,this);
    });

    $(document).on("dblclick",".color span",function(e){
        changeText(e,this);
    });
    $(document).on("dblclick",".size span",function(e){
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
        var html = '<label class="radio-inline">' +
            '<input type="radio" name="itemSize" value="双击编辑">' +
            '<span ondblclick="changeText(e,this)">双击编辑</span>' +
            '</label>';
        $(html).appendTo(".size");
    })
    /*********添加颜色*********/
    $(".add-color").click(function(){
        var html = '<label class="radio-inline">' +
            '<input type="radio" name="itemColor" value="双击编辑">' +
            '<span ondblclick="changeText(e,this)">双击编辑</span>' +
            '</label>';
        $(html).appendTo(".color");
    })
});

