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
                                setTimeout("location.href='/sales/import'", 3000);
                            }
                            //商品录入, 成功后返回到商品录入页面
                            else {
                                setTimeout("location.href='/sales/import'", 3000);
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
        var count=$("#count").val();
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
        order.count=count;
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
 })
