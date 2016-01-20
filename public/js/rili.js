$(function(){
	var datetimepicker_lang = '';
	if(window.lang == 'cn' ) datetimepicker_lang = 'zh-CN';
	else if(window.lang == 'kr' ) datetimepicker_lang = 'ko';
	else datetimepicker_lang = 'en';

    $('#datetimepicker1').datetimepicker({
        locale: 'zh-cn',
        format:'YYYY-MM-DD HH:mm:ss'
    });
    $('#datetimepicker2').datetimepicker({
        locale: 'zh-cn',
        format:'YYYY-MM-DD HH:mm:ss'
    });
    $('#datetimepicker3').datetimepicker({
        locale: 'zh-cn',
        format:'YYYY-MM-DD HH:mm:ss'
    });
    $('#datetimepicker4').datetimepicker({
        locale: 'zh-cn',
        format:'YYYY-MM-DD HH:mm:ss'
    });
	
    /*日历 分钟*/
    //$('.form_datetime').datetimepicker({
    //    format: 'yyyy-mm-dd hh:ii',
    //    language:  datetimepicker_lang,
    //    minuteStep:10,
    //    weekStart: 7,
    //    todayBtn:  1 ,
    //    autoclose: 1,
    //    todayHighlight: 1,
    //    keyboardNavigation:1,
    //    startView: 2,
    //    forceParse: 1,
    //    // showMeridian: 1,
    //    minView:0,
    //    maxView:4,
		//pickerPosition: "bottom-left"
    //});
    //
    //$('.form_date').datetimepicker({
    //    format: 'yyyy-mm-dd',
    //    language:  datetimepicker_lang,
    //    minuteStep:10,
    //    weekStart: 7,
    //    todayBtn:  1 ,
    //    autoclose: 1,
    //    todayHighlight: 1,
    //    keyboardNavigation:1,
    //    startView: 2,
    //    forceParse: 1,
    //    // showMeridian: 1,
    //    minView:2,
    //    maxView:4,
    //    pickerPosition: "bottom-left"
    //});
})