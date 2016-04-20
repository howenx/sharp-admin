$(function(){

        $("input[name='selAll1']").click(function(){
            $("input[name='supplyOrder1']").prop('checked',$(this).prop("checked"));
        });

        $("input[name='selAll2']").click(function(){
            $("input[name='supplyOrder2']").prop('checked',$(this).prop("checked"));
        });

        $("input[name='selAll3']").click(function(){
            $("input[name='supplyOrder3']").prop('checked',$(this).prop("checked"));
        });

        $("input[name='selAll4']").click(function(){
            $("input[name='supplyOrder4']").prop('checked',$(this).prop("checked"));
        });

        $("input[name='selAll5']").click(function(){
            $("input[name='supplyOrder5']").prop('checked',$(this).prop("checked"));
        });


    })