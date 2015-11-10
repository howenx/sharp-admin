$(function(){
    /*日历 分钟*/
    $('.form_datetime').datetimepicker({
        format: 'yyyy-mm-dd hh:ii',
        language:  'zh-CN',
        minuteStep:10,
        weekStart: 7,
        todayBtn:  1 ,
        autoclose: 1,
        todayHighlight: 1,
        keyboardNavigation:1,
        startView: 2,
        forceParse: 1,
        // showMeridian: 1,
        minView:0,
        maxView:4,
		pickerPosition: "bottom-left"
    });
})