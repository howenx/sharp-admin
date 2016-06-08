 $(function() {

 /** 数据提交 **/
    $("#submitSaleProduct").click(function(){


    var isPost = true;
    var id=$("#id").val();
    var categoryId=$("#categoryId").val();
    var jdSkuId=$("#jdSkuId").val();
    var customSkuId=$("#customSkuId").val();
    var productName=$("#productName").val();
    var skuCode=$("#skuCode").val();
    var productCode=$("#productCode").val();
    var spec=$("#spec").val();
    var productCost=$("#productCost").val();
    var purchaseCount=$("#purchaseCount").val();
    var noCard=$("#noCard").val();
    var damage=$("#damage").val();
    var lessDelivery=$("#lessDelivery").val();
    var lessProduct=$("#lessProduct").val();
    var emptyBox=$("#emptyBox").val();
    var invArea=$("#invArea").val();
    var storageAt=$("#storageAt").val();
    var customSkuId=$("#customSkuId").val();
    var damageOther=$("#damageOther").val();
    var remark=$("#remark").val();
    var saleFinishStatus=$('input:radio[name=saleFinishStatus]:checked').val();
    //必填项不能有空值
    if (jdSkuId==""|| productName=="" || skuCode=="" || productCode=="" || productCost=="" ||storageAt=="") {
        isPost=false;
        alert("必填项不能为空");
    }

    var product=new Object();
    product.id=id;
    product.categoryId=categoryId;
    product.jdSkuId=jdSkuId;
    product.customSkuId=customSkuId;
    product.name=productName;
    product.skuCode=skuCode;
    product.productCode=productCode;
    product.spec=spec;
    product.productCost=productCost;
    product.purchaseCount=purchaseCount;
    product.noCard=noCard;
    product.damage=damage;
    product.lessDelivery=lessDelivery;
    product.lessProduct=lessProduct;
    product.emptyBox=emptyBox;
    product.invArea=invArea;
    product.storageAt=storageAt;
    product.customSkuId=customSkuId;
    product.damageOther=damageOther;
    product.remark=remark;
    product.saleFinishStatus=saleFinishStatus;

    if (isPost) {
                $.ajax({
                    type :  "POST",
                    url : "/sales/product/save",
                    contentType: "application/json; charset=utf-8",
                    data : JSON.stringify(product),
                    error : function(request) {
                        if (window.lang = 'cn') {
                            $('#js-userinfo-error').text('保存失败');
                        } else {
                            $('#js-userinfo-error').text('Save error');
                        }
                        setTimeout("$('#js-userinfo-error').text('')", 2000);
                    },
                    success: function(data) {
                        if (data!=""&&data!=null) {
                            $('.usercenter-option > .user-state').css('background-position', '20px -174px');
                            $('.usercenter-option > .user-state').text('未更改');
                            $('#js-userinfo-error').text('保存成功').css('color', '#2fa900');
                            //商品更新, 成功后返回到列表查询页面
                            if($("#itemId").val() != "") {
                                setTimeout("location.href='/sales/search'", 3000);
                            }
                            //商品录入, 成功后返回到商品录入页面
                            else {
                                setTimeout("location.href='/sales/search'", 3000);
                            }
                        }
                        else {
                            $('#js-userinfo-error').text('保存失败');
                        }
                        setTimeout("$('#js-userinfo-error').text('').css('color','#c00')", 2000);
                    }
                });
            }
    });


    /** 数据提交 **/
        $("#submitSaleOrder").click(function(){


        var isPost = true;
        var id=$("#id").val();
        var shop=$("#shop").val();
        var saleAt=$("#saleAt").val();
        var orderId=$("#orderId").val();
        var discountAmount=$("#discountAmount").val();
        var shipFee=$("#shipFee").val();
        var inteLogistics=$("#inteLogistics").val();
        var packFee=$("#packFee").val();
        var storageFee=$("#storageFee").val();
      //  var postalTaxRate=$("#postalTaxRate").val();
        var remarkStatus=$('input:radio[name=remarkStatus]:checked').val();
        var remark=$("#remark").val();
        var inputType=$("#inputType").val();
        var orderStatus=$('input:radio[name=orderStatus]:checked').val();


        var lineIdArray=$("input[name=lineId]");
        var saleProductIdArray=$("input[name=saleProductId]");
        var jdPriceArray=$("input[name=jdPrice]");
        var saleCountArray=$("input[name=saleCount]");
        var jdRateArray=$("input[name=jdRate]");
        var discountAmountLineArray=$("input[name=discountAmountLine]");

        //必填项不能有空值
        if ( saleAt=="" || orderId==""||saleProductIdArray.length<=0) {
            isPost=false;
            alert("必填项不能为空");
        }

        var saleOrderLineArray=new Array();
        var lineId,saleProductId,jdPrice,saleCount,jdRate,discountAmountLine;
        for(var i=0;i<saleProductIdArray.length;i++){
             lineId=lineIdArray[i].value;
             saleProductId=saleProductIdArray[i].value;
             jdPrice=jdPriceArray[i].value;
             saleCount=saleCountArray[i].value;
             jdRate=jdRateArray[i].value;
             discountAmountLine=discountAmountLineArray[i].value;
             if(null==saleProductId||""==saleProductId||null==jdPrice||""==jdPrice
             ||null==saleCount||""==saleCount||null==jdRate||""==jdRate||null==discountAmountLine||""==discountAmountLine){
                isPost=false;
                alert("商品必填项不能为空");
                return false;
             }
             var lineInfo=new Object();
             lineInfo.lineId=lineId;
             lineInfo.saleProductId=saleProductId;
             lineInfo.jdPrice=jdPrice;
             lineInfo.saleCount=saleCount;
             lineInfo.jdRate=jdRate;
             lineInfo.discountAmountLine=discountAmountLine;
             saleOrderLineArray.push(lineInfo);
        }


        var order=new Object();
        order.id=id;
        order.shop=shop;
        order.saleAt=saleAt;
        order.orderId=orderId;
        order.discountAmount=discountAmount;
        order.shipFee=shipFee;
        order.inteLogistics=inteLogistics;
        order.packFee=packFee;
        order.storageFee=storageFee;
       // order.postalTaxRate=postalTaxRate;
        order.remarkStatus=remarkStatus;
        order.remark=remark;
        order.inputType=inputType;
        order.orderStatus=orderStatus;
        order.saleOrderLineArray=saleOrderLineArray;


        if (isPost) {
                    $.ajax({
                        type :  "POST",
                        url : "/sales/order/save",
                        contentType: "application/json; charset=utf-8",
                        data : JSON.stringify(order),
                        error : function(request) {
                            if (window.lang = 'cn') {
                                $('#js-userinfo-error').text('保存失败');
                            } else {
                                $('#js-userinfo-error').text('Save error');
                            }
                            setTimeout("$('#js-userinfo-error').text('')", 2000);
                        },
                        success: function(data) {
                            if (data!=""&&data!=null) {
                                $('.usercenter-option > .user-state').css('background-position', '20px -174px');
                                $('.usercenter-option > .user-state').text('未更改');
                                $('#js-userinfo-error').text('保存成功').css('color', '#2fa900');
                                //商品更新, 成功后返回到列表查询页面
                                setTimeout("location.href='/sales/order/search'", 3000);

                            }
                            else {
                                $('#js-userinfo-error').text('保存失败');
                            }
                            setTimeout("$('#js-userinfo-error').text('').css('color','#c00')", 2000);
                        }
                    });
                }
        });

        /** 数据提交 **/
        $("#sale-statistics-bt").click(function(){


        var isPost = true;
        var categoryId=$("#categoryId").val();
        var starttime=$("#starttime").val();
        var endtime=$("#endtime").val();
        var productName=$("#productName").val();
        var shop=$("#shop").val();

        var statistics=new Object();
        statistics.categoryId=categoryId;
        statistics.productName=productName;
        statistics.starttime=starttime;
        statistics.endtime=endtime;
        statistics.shop=shop;
        if (isPost) {
            $.ajax({
                type :  "POST",
                url : "/sales/statistics",
                contentType: "application/json; charset=utf-8",
                data : JSON.stringify(statistics),
                dataType: 'json',
                error : function(request) {
                    if (window.lang = 'cn') {
                        $('#js-userinfo-error').text('查询失败');
                    } else {
                        $('#js-userinfo-error').text('Search error');
                    }
                    setTimeout("$('#js-userinfo-error').text('')", 2000);
                },
                success: function(data) {

                        if(null!=data&&data.length>0&&null!=data[0]){
                            $('#tb-topic').find('tbody').html('' +
                             '<tr class = "tb-list-data">'+
                             '<td>'+data[0].saleTotal+'</td>'+
                             '<td>'+data[0].jdFeeTotal+'</td>'+
                             '<td>'+data[0].shipFeeTotal+'</td>'+
                             '<td>'+data[0].inteLogisticsTotal+'</td>'+
                             '<td>'+data[0].packFeeTotal+'</td>'+
                             '<td>'+data[0].storageFeeTotal+'</td>'+
                             '<td>'+data[0].profitTotal+'</td>'+
                             '<td>'+data[0].saleCountTotal+'</td>'+
                             '</tr>');
                        }else{
                            if (window.lang = 'cn') {
                             $('#tb-topic').find('tbody').html('<tr class = "tb-list-data"><td>无数据</td></tr>');
                            } else {
                                $('#tb-topic').find('tbody').html('<tr class = "tb-list-data"><td>no data</td></tr>');
                            }
                        }
                    setTimeout("$('#js-userinfo-error').text('').css('color','#c00')", 2000);
                }
            });
         }
     });

      /** 数据提交 **/
         $("#sale-inventory-bt").click(function(){
         var isPost = true;
         var saleProductId=$("#saleProductId").val();
         var saleMonth=$("#starttime").val();

         var inventory=new Object();
         inventory.saleProductId=saleProductId;
         inventory.saleMonth=saleMonth;
         if (isPost) {
             $.ajax({
                 type :  "POST",
                 url : "/sales/inventory",
                 contentType: "application/json; charset=utf-8",
                 data : JSON.stringify(inventory),
                 dataType: 'json',
                 error : function(request) {
                     if (window.lang = 'cn') {
                         $('#js-userinfo-error').text('查询失败');
                     } else {
                         $('#js-userinfo-error').text('Search error');
                     }
                     setTimeout("$('#js-userinfo-error').text('')", 2000);
                 },
                 success: function(data) {
                 console.log("data="+data);
                 console.log("data.saleInventoryList="+data.saleInventoryList);

                 $('#product-topic').find('tbody tr').html('');
                 $('#tb-topic').find('thead tr').html('');
                 $('#tb-topic').find('tbody tr').html('');
                         if(null!=data&&null!=data.saleProduct){
                              $('#product-topic').find('tbody tr').append('<td>'+data.saleProduct.id+'</td>');
                              $('#product-topic').find('tbody tr').append('<td>'+data.saleProduct.name+'</td>');
                              $('#product-topic').find('tbody tr').append('<td>'+data.saleProduct.skuCode+'</td>');
                              $('#product-topic').find('tbody tr').append('<td>'+data.saleProduct.productCode+'</td>');
                              $('#product-topic').find('tbody tr').append('<td>'+data.saleProduct.spec+'</td>');
                              $('#product-topic').find('tbody tr').append('<td>'+data.saleProduct.invArea+'</td>');
                              $('#product-topic').find('tbody tr').append('<td>'+data.saleProduct.saleCount+'</td>');
                              $('#product-topic').find('tbody tr').append('<td>'+data.saleProduct.inventory+'</td>');
                              $('#product-topic').find('tbody tr').append('<td>'+data.saleProduct.productCost+'</td>');
                              $('#product-topic').find('tbody tr').append('<td>'+data.saleProduct.stockValue+'</td>');
                              $('#product-topic').find('tbody tr').append('<td>'+data.saleProduct.purchaseCount+'</td>');
                              $('#product-topic').find('tbody tr').append('<td>'+data.saleProduct.noCard+'</td>');
                              $('#product-topic').find('tbody tr').append('<td>'+data.saleProduct.damage+'</td>');
                              $('#product-topic').find('tbody tr').append('<td>'+data.saleProduct.lessDelivery+'</td>');
                              $('#product-topic').find('tbody tr').append('<td>'+data.saleProduct.lessProduct+'</td>');
                              $('#product-topic').find('tbody tr').append('<td>'+data.saleProduct.emptyBox+'</td>');
                              $('#product-topic').find('tbody tr').append('<td>'+data.saleProduct.damageOther+'</td>');
                              $('#product-topic').find('tbody tr').append('<td>'+data.saleProduct.backCount+'</td>');
                               //日销量
                               $(data.saleInventoryList).each(function(index, element) {
                                    console.log("index="+index+",element="+element);
                                     $('#tb-topic').find('thead tr').append('<th>'+element.saleDate+'</th>');
                                     $('#tb-topic').find('tbody tr').append('<td>'+element.saleCount+'</td>');
                              });
                              $('#tb-topic').find('thead tr').append('<th>月销量</th>');
                              $('#tb-topic').find('tbody tr').append('<td>'+data.saleMonthTotal+'</td>');
                         }else{
                             if (window.lang = 'cn') {
                              $('#tb-topic').find('tbody').html('<tr class = "tb-list-data"><td>无数据</td></tr>');
                             } else {
                                 $('#tb-topic').find('tbody').html('<tr class = "tb-list-data"><td>no data</td></tr>');
                             }
                         }
                     setTimeout("$('#js-userinfo-error').text('').css('color','#c00')", 2000);
                 }
             });
          }
      });


 })

//删除订单
function delOrder(id){
    if (window.confirm("确定删除?")) {
        $.ajax({
              type :"GET",
              url : "/sales/order/del/"+id,
              contentType: "application/json; charset=utf-8",
              error : function(request) {
                  if (window.lang = 'cn') {
                      $('#js-userinfo-error').text('删除失败');
                  } else {
                      $('#js-userinfo-error').text('delete error');
                  }
                  setTimeout("$('#js-userinfo-error').text('')", 2000);
              },
              success: function(data) {
                   if(data=="success"){
                       $(".orderTr"+id).remove();
                   } else alert("删除失败!");

              }
          });
      }
  }

  //删除商品
  function delSaleProduct(id){
      if (window.confirm("确定删除该商品吗?")) {
          $.ajax({
                type :"GET",
                url : "/sales/product/del/"+id,
                contentType: "application/json; charset=utf-8",
                error : function(request) {
                    if (window.lang = 'cn') {
                        $('#js-userinfo-error').text('删除失败');
                    } else {
                        $('#js-userinfo-error').text('delete error');
                    }
                    setTimeout("$('#js-userinfo-error').text('')", 2000);
                },
                success: function(data) {
                     if(data=="success"){
                         $("#productTr"+id).remove();
                     }else if(data=="exist"){
                       alert("存在关联的订单,不允许删除!");
                     }
                     else alert("删除失败!");

                }
            });
        }
  }

  //导入订单
  $(document).on("click",".orderExcelBtn",function(){

       var orderPath=$("#orderPath").val();

       if(null==orderPath||""==orderPath){
          alert("请导入订单excel");
          return;
       }
      //提交表单
      $("#orderForm").submit();

  });


  //导入订单费用
    $(document).on("click",".feeExcelBtn",function(){

         var orderPath=$("#feePath").val();

         if(null==orderPath||""==orderPath){
            alert("请导入订单费用excel");
            return;
         }
        //提交表单
        $("#feeForm").submit();

    });
//修改商品Id
 $(document).on("click","#editProIdBtn",function(){
    if (window.confirm("您确定要修改订单关联的商品ID吗?")) {
        $("#saleProductId").attr("readonly",false)//去除input元素的readonly属性
    }
 });


//导入订单费用
 $(document).on("click",".saleDetailExcelBtn",function(){

      var saleDetailPath=$("#saleDetailPath").val();

      if(null==saleDetailPath||""==saleDetailPath){
         alert("请导入妥投销货清单明细.csv");
         return;
      }
     //提交表单
     $("#saleDetailForm").submit();

 });

//导入订单费用
 $(document).on("click",".saleCouponExcelBtn",function(){

      var saleDetailPath=$("#saleCouponPath").val();

      if(null==saleDetailPath||""==saleDetailPath){
         alert("请导入优惠信息");
         return;
      }
     //提交表单
     $("#saleCouponForm").submit();

 });

//添加商品
 function addSaleOrderLine(){
    var appendHtml=' <tr><td><input type="hidden" name="lineId" value=""/><input type="text" name="saleProductId" value=""/></td>'+
                        '<td></td>'+
                        '<td><input type="text" name="jdPrice" value=""/></td>'+
                        '<td><input type="text" name="saleCount" value=""/></td>'+
                        '<td><input type="text" name="jdRate" value=""/></td>'+
                        '<td></td>'+
                        '<td></td>'+
                        '<td><input type="text" name="discountAmountLine" value=""/></td>'+
                        '<td class="delSaleOrderLineCss">删除</td>'+
                    '</tr>';
     $("#orderLinetbody").append(appendHtml);
  };

  $(document).on("click",".delSaleOrderLineCss",function(){
    $(this).parent().remove();
  });
