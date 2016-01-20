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
var option1,option2,tdindex;
function Init () {
    var sharedObject = window.dialogArguments;
    option1 = sharedObject.obj;
    option2 = sharedObject.obj1;
    tdindex = sharedObject.tdindex;

    var valindex = 0;
    var tds = option2[tdindex];
    if(option1.tagName=="TD"){
        for(var i=1;i<tds.length-1;i++){
            if(i==3){
                var html = tds[i].innerHTML;
                var arr1 = html.split(" ~ ");
                for(var j=0;j<arr1.length;j++){
                    $("#datetimepicker"+(j+1)).find(":input").val(arr1[j]);
                }
            }else if(i==17){
                var html = tds[i].innerHTML;
                var arr1 = html.split("<br>");
                for(var j=0;j<arr1.length;j++){
                    $(".hgba").find(":input").eq(j).val(arr1[j].split(":")[1])
                }
            }else if(i==1){
                var spans = $("input[name=color]").parent().find("span");
                for(var j=0;j<spans.length;j++){
                    if(spans[j].innerHTML==tds[i].innerHTML){
                        $(spans[j]).parent().find(":input").prop("checked","true");
                    }
                }
            }else if(i==2){
                var spans = $("input[name=size]").parent().find("span");
                for(var j=0;j<spans.length;j++){
                    if(spans[j].innerHTML==tds[i].innerHTML){
                        $(spans[j]).parent().find(":input").prop("checked","true");
                    }
                }
            }else{
                var vals = $(".vals:gt(1)");
                $(vals[valindex]).val(tds[i].innerHTML);
                valindex++;
            }
        }
    }
}
/*****保存******/
function holdShow() {
    /**存储信息**/
    if($(option1).hasClass("add-guige")){
        var trh = $("<tr>");
        var trd = $("<tr>");
        if (window.showModalDialog) {
            var sharedObject = {};
            console.log($(".form-group label"))
            window.returnValue = sharedObject;
        }
        else {
            $("<th>").html("设为主商品").appendTo(trh);
            $("<td>").html('<input type="radio" name="orMasterInv" checked="checked" class="master-radio"/>').appendTo(trd);
            $(".form-group>label").each(function(index){
                $("<th>").html(this.innerHTML).appendTo(trh);
                if(index==2){
                    var html1 = $("#datetimepicker1").find(":input").val();
                    var html2 = $("#datetimepicker2").find(":input").val();
                    var htmlr = html1 + " ~ " +html2;
                    $("<td>").html(htmlr).appendTo(trd);
                }else if(index==16){
                    var hz = $(".hz").val();
                    var gz = $(".gz").val();
                    var sh = $(".sh").val();
                    var htmld = "杭州:"+hz+"<br>广州:"+gz+"<br>上海:"+sh;
                    $("<td>").html(htmld).appendTo(trd);
                }else{
                    if($(this).parent().find(".vals").val()==""){
                        $(this).parent().find(".vals").val(0);
                    }
                    $("<td>").html($(this).parent().find(".vals").val()).appendTo(trd);
                }
            })
            $("<th>").html("修改").appendTo(trh);
            $("<td onclick='ShowModal(this,arrobj)'>").html("编辑").appendTo(trd);
            window.opener.updateTable (trh,trd);
        }
        console.log(trh);
    }else if(option1.tagName=="TD"){
        $(".color").find(":input").removeClass("vals");
        $(".color").find("input:checked").addClass("vals");
        $(".size").find(":input").removeClass("vals");
        $(".size").find("input:checked").addClass("vals");
        var valindex = 0;
        var tds = option2[tdindex];
        for(var i=1;i<tds.length-1;i<i++){
            if(i==3){
                var html1 = $("#datetimepicker1").find(":input").val();
                var html2 = $("#datetimepicker2").find(":input").val();
                var htmlr = html1 + " ~ " +html2;
                $(tds[i]).html(htmlr);
            }else if(i==17){
                var hz = $(".hz").val();
                var gz = $(".gz").val();
                var sh = $(".sh").val();
                var htmld = "杭州:"+hz+"<br>广州:"+gz+"<br>上海:"+sh;
                $(tds[i]).html(htmld);
            }else{
                var vals = $(".vals");
                $(tds[i]).html($(vals[valindex]).val());
                valindex++;
            }
        }
    }
    window.close();
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

