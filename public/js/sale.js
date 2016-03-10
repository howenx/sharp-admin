 $(function() {

 /** 数据提交 **/
    $("#submitSaleProduct").click(function(){


    var isPost = true;
    var id=$("#id").val();
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
    var customSkuId=$("#customSkuId").val();
    var damageOther=$("#damageOther").val();
    var remark=$("#remark").val();
    //必填项不能有空值
    if ( productName=="" || skuCode=="" || productCode=="" || productCost=="" ||storageAt=="") {
        isPost=false;
        alert("必填项不能为空");
    }

    var product=new Object();
    product.id=id;
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
    product.customSkuId=customSkuId;
    product.damageOther=damageOther;
    product.remark=remark;

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
        var saleAt=$("#saleAt").val();
        var orderId=$("#orderId").val();
        var saleProductId=$("#saleProductId").val();
        var price=$("#price").val();
        var saleCount=$("#saleCount").val();
        var discountAmount=$("#discountAmount").val();
        var jdRate=$("#jdRate").val();
        var shipFee=$("#shipFee").val();
        var inteLogistics=$("#inteLogistics").val();
        var packFee=$("#packFee").val();
        var storageFee=$("#storageFee").val();
        var postalTaxRate=$("#postalTaxRate").val();
        var remarkStatus=$('input:radio[name=remarkStatus]:checked').val();
        var remark=$("#remark").val();

        //必填项不能有空值
        if ( saleAt=="" || orderId=="" || price=="" || saleCount=="") {
            isPost=false;
            alert("必填项不能为空");
        }

        var order=new Object();
        order.id=id;
        order.saleAt=saleAt;
        order.orderId=orderId;
        order.saleProductId=saleProductId;
        order.price=price;
        order.saleCount=saleCount;
        order.discountAmount=discountAmount;
        order.jdRate=jdRate;
        order.shipFee=shipFee;
        order.inteLogistics=inteLogistics;
        order.packFee=packFee;
        order.storageFee=storageFee;
        order.postalTaxRate=postalTaxRate;
        order.remarkStatus=remarkStatus;
        order.remark=remark;

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

                 $('#product-topic').find('tbody tr').html('');
                 $('#tb-topic').find('thead tr').html('');
                 $('#tb-topic').find('tbody tr').html('');
                         if(null!=data){
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
                           setTimeout("location.href='/sales/order/search'", 3000);
                       } else alert("删除失败!");

                  }
              });
          }
      }