 $(function() {

 /** 数据提交 **/
    $("#submitSaleProduct").click(function(){


    var isPost = true;
    var categoryId=$("#categoryId").val();
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


    var product=new Object();
    product.categoryId=categoryId;
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
        var saleAt=$("#saleAt").val();
        var orderId=$("#orderId").val();
        var saleProductId=$("#saleProductId").val();
        var productName=$("#productName").val();
        var price=$("#price").val();
        var saleCount=$("#saleCount").val();
        var discountAmount=$("#discountAmount").val();
        var jdRate=$("#jdRate").val();
        var cost=$("#cost").val();
        var shipFee=$("#shipFee").val();
        var inteLogistics=$("#inteLogistics").val();
        var packFee=$("#packFee").val();
        var storageFee=$("#storageFee").val();
        var postalTaxRate=$("#postalTaxRate").val();

        var order=new Object();
        order.saleAt=saleAt;
        order.orderId=orderId;
        order.saleProductId=saleProductId;
        order.productName=productName;
        order.price=price;
        order.saleCount=saleCount;
        order.discountAmount=discountAmount;
        order.jdRate=jdRate;
        order.cost=cost;
        order.shipFee=shipFee;
        order.inteLogistics=inteLogistics;
        order.packFee=packFee;
        order.storageFee=storageFee;
        order.postalTaxRate=postalTaxRate;

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
                                if($("#itemId").val() != "") {
                                    setTimeout("location.href='/sales/order/import'", 3000);
                                }
                                //商品录入, 成功后返回到商品录入页面
                                else {
                                    setTimeout("location.href='/sales/order/import'", 3000);
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
        $("#sale-statistics-bt").click(function(){


        var isPost = true;
        var categoryId=$("#categoryId").val();
        var starttime=$("#starttime").val();
        var endtime=$("#endtime").val();

        var statistics=new Object();
        statistics.categoryId=categoryId;
        statistics.starttime=starttime;
        statistics.endtime=endtime;
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
                 $('#tb-topic').find('thead tr').html('');
                 $('#tb-topic').find('tbody tr').html('');
                         if(null!=data){
                              $('#tb-topic').find('thead tr').append('<th>序号</th>');
                              $('#tb-topic').find('tbody tr').append('<td>'+data.saleProduct.id+'</td>');
                              $('#tb-topic').find('thead tr').append('<th>商品名称</th>');
                              $('#tb-topic').find('tbody tr').append('<td>'+data.saleProduct.name+'</td>');
                              $('#tb-topic').find('thead tr').append('<th>SKU编码</th>');
                              $('#tb-topic').find('tbody tr').append('<td>'+data.saleProduct.skuCode+'</td>');
                              $('#tb-topic').find('thead tr').append('<th>货品编码</th>');
                              $('#tb-topic').find('tbody tr').append('<td>'+data.saleProduct.productCode+'</td>');
                              $('#tb-topic').find('thead tr').append('<th>规格</th>');
                              $('#tb-topic').find('tbody tr').append('<td>'+data.saleProduct.spec+'</td>');
                              $('#tb-topic').find('thead tr').append('<th>保税区</th>');
                              $('#tb-topic').find('tbody tr').append('<td>'+data.saleProduct.invArea+'</td>');
                              $('#tb-topic').find('thead tr').append('<th>总销量</th>');
                              $('#tb-topic').find('tbody tr').append('<td>'+data.saleProduct.saleCount+'</td>');
                              $('#tb-topic').find('thead tr').append('<th>库存量</th>');
                              $('#tb-topic').find('tbody tr').append('<td>'+data.saleProduct.inventory+'</td>');
                              $('#tb-topic').find('thead tr').append('<th>库存商品成本</th>');
                              $('#tb-topic').find('tbody tr').append('<td>'+data.saleProduct.productCost+'</td>');
                              $('#tb-topic').find('thead tr').append('<th>库存商品价值</th>');
                              $('#tb-topic').find('tbody tr').append('<td>'+data.saleProduct.stockValue+'</td>');
                              $('#tb-topic').find('thead tr').append('<th>进货量</th>');
                              $('#tb-topic').find('tbody tr').append('<td>'+data.saleProduct.purchaseCount+'</td>');
                              $('#tb-topic').find('thead tr').append('<th>无卡</th>');
                              $('#tb-topic').find('tbody tr').append('<td>'+data.saleProduct.noCard+'</td>');
                              $('#tb-topic').find('thead tr').append('<th>破损</th>');
                              $('#tb-topic').find('tbody tr').append('<td>'+data.saleProduct.damage+'</td>');
                              $('#tb-topic').find('thead tr').append('<th>少配件</th>');
                              $('#tb-topic').find('tbody tr').append('<td>'+data.saleProduct.lessDelivery+'</td>');
                              $('#tb-topic').find('thead tr').append('<th>少货</th>');
                              $('#tb-topic').find('tbody tr').append('<td>'+data.saleProduct.lessProduct+'</td>');
                              $('#tb-topic').find('thead tr').append('<th>空盒</th>');
                              $('#tb-topic').find('tbody tr').append('<td>'+data.saleProduct.emptyBox+'</td>');


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
