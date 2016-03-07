  var drag = '<div class="dragon-contained">' +
  	"<div class='dragon-graph'>" +
  	"<div class='dragon-circle'>" +
  	"<div class='dragon-circle-inner'></div>" +
  	'</div>' +
  	"<div class='dragon-rectangle'>" +
  	"<div class='dragon-trangle'></div>" +
  	"<div class='dragon-text'>" +
  	"<p class='dragon-p'>CDKIODU</p>" +
  	'</div>' +
  	'</div>' +
  	"<div class='dragon-close'>" +
  	'</div>' +
  	'</div>' +
  	"<div class='item-id'>" +
    '</div>' +
    "<div class='item-type'>" +
    '</div>' +
  	'</div>';
  var mark_image_nm;
  $(function() {

    $(document).on("mouseenter",".dragon-contained",function(){
           $(this).find(".item-id").css({"display":"block"});
         })

    $(document).on("mouseleave",".dragon-contained",function(){
      $(this).find(".item-id").css({"display":"none"});
    })

  	$(document).on("click", "p.dragon-p", function() {
  		//if the icon-circle-close exists,then remove it
  		if(pageEditStatus){
  		    if ($(this).parent().parent().next().has('div.icon-circle-close').length === 0) {
            	$(this).parent().parent().next().empty();
            	var left_close = $(this).parent().parent().parent().width();
            	$(this).parent().parent().next().css({
            		'left': left_close + 'px'
            	});
            	$(this).parent().parent().next().append('<div class="icon-circle-close"></div>');
            } else {
            	$(this).parent().parent().next().empty();
            }
  		}
  	});
  	/*If the element is dynamic append.You should the following method.*/
  	$(document).on("click", "div.icon-circle-close", function() {
  		$(this).parent().parent().parent().remove();
		$(".user-state").text("已更改");
  	});
  	$('#mark-bt').click(function() {
  		var radio_flag = false;
  		var rotate = '';
  		$(':radio[name=mark-deg]').each(function(index, element) {
  			if ($(this).prop('checked')) {
  				radio_flag = true;
  				rotate = $(this).val();
  			}
  		});
  		//check length
  		if ($('#mark-nm').val() != '' && $('#mark-nm').val().length != 0 && $('#mark-nm').val().replace(/[^\x00-\xff]/ig, "aa").length < 24) {
  			//check rotate degree just for number			
  			// if ($('#mark-rotate').val != '' && $('#mark-rotate').val().match(/[\d]/ig)) {
  			if($("#input_imgurl").val() != ""){
                if (radio_flag && rotate != '') {
                    var ch_drag = $(drag).find('p').eq(0).text($('#mark-nm').val());
                    //if the rotate for 180 or 135,first rotate the p tag text 180.
                    if (rotate === '180' || rotate === '135') {
                        var ch_drag_p = ch_drag.parent().css({
                            'transform': 'rotate(180deg)',
                            '-ms-transform': 'rotate(180deg)',
                            '-webkit-transform': 'rotate(180deg)',
                            '-o-transform': 'rotate(180deg)',
                            '-moz-transform': 'rotate(180deg)',
                            'left': '0px'
                        });
                        var ch_graph = ch_drag_p.parent().parent().css({
                            'transform': 'rotate(' + rotate + 'deg)',
                            '-ms-transform': 'rotate(' + rotate + 'deg)',
                            '-webkit-transform': 'rotate(' + rotate + 'deg)',
                            '-o-transform': 'rotate(' + rotate + 'deg)',
                            '-moz-transform': 'rotate(' + rotate + 'deg)'
                        });
                        $('#dragon-container').append(ch_graph.parent());
                        $('div.dragon-contained').draggable({
                            containment: "parent",
                        });
                        var itemId =  $("#input_imgurl").val();
                        var itemType = $("#url-type").val();
                        ch_drag.parent().parent().parent().parent().find(".item-id").html(itemId);
                        ch_drag.parent().parent().parent().parent().find(".item-type").html(itemType);
                    } else if (rotate === '0' || rotate === '45') {
                        var ch_graph = ch_drag.parent().parent().parent().css({
                            'transform': 'rotate(' + rotate + 'deg)',
                            '-ms-transform': 'rotate(' + rotate + 'deg)',
                            '-webkit-transform': 'rotate(' + rotate + 'deg)',
                            '-o-transform': 'rotate(' + rotate + 'deg)',
                            '-moz-transform': 'rotate(' + rotate + 'deg)'
                        });
                        $('#dragon-container').append(ch_graph.parent());
                        $('div.dragon-contained').draggable({
                            containment: "parent"
                        });
                        var itemId =  $("#input_imgurl").val();
                        var itemType = $("#url-type").val();
                        ch_drag.parent().parent().parent().parent().find(".item-id").html(itemId);
                        ch_drag.parent().parent().parent().parent().find(".item-type").html(itemType);

                    } else {
                        alert('Please do not modify rotate degree.');
                    }
                } else {
                    alert('Please choose correct rotate degree.');
                }
  			}else{
                alert('Please choose the item ID.');
            }
  		} else alert('Please input the length less than 24 characters and more than 1 character.');
  	});
  	/*Push the mark info by json.*/
  	$('#push').click(function() {
  		var container_off = $(this).prev().offset();
  		// var obj = $.parseJSON ( '{ "image_nm": "'+mark_image_nm+'",{}}' );
  		var json = '{ "image_nm": "' + mark_image_nm + '","mark":[';
  		$('.dragon-contained').each(function(index, element) {
  			var contained_off = $(this).offset();
  			var dot_off = $(this).find('div.dragon-circle').first().offset();
  			var dot_position_top = dot_off.top - container_off.top;
  			var dot_postion_left = dot_off.left - container_off.left;
  			var drag_position_top = contained_off.top - container_off.top;
  			var drag_position_left = contained_off.left - container_off.left;
  			if (index === 0) {
  				json += '{"dot_postion_top":"' + dot_position_top + '","dot_postion_left":"' + dot_postion_left + '","drag_position_top":"' + drag_position_top + '","drag_position_left":"' + drag_position_left + '","rotate":"' + matrix2deg($(this).children().eq(0).css('transform')) + '","text":"' + $(this).find('p').eq(0).text() + '"}';
  			} else {
  				json += ',{"dot_postion_top":"' + dot_position_top + '","dot_postion_left":"' + dot_postion_left + '","drag_position_top":"' + drag_position_top + '","drag_position_left":"' + drag_position_left + '","rotate":"' + matrix2deg($(this).children().eq(0).css('transform')) + '","text":"' + $(this).find('p').eq(0).text() + '"}';
  			}
  		});
  		json += ']}';
  		var obj = $.parseJSON(json);
  		console.log(obj);
  	});

  	/** rotation matrix - http://en.wikipedia.org/wiki/Rotation_matrix **/
  	var matrix2deg = function(matrix) {
  		var values = matrix.split('(')[1].split(')')[0].split(',');
  		var a = values[0];
  		var b = values[1];
  		var c = values[2];
  		var d = values[3];

  		var scale = Math.sqrt(a * a + b * b);

  		// arc sin, convert from radians to degrees, round
  		var sin = b / scale;
  		// next line works for 30deg but not 130deg (returns 50);
  		// var angle = Math.round(Math.asin(sin) * (180/Math.PI));
  		var angle = Math.round(Math.atan2(b, a) * (180 / Math.PI));

  		// console.log('Rotate: ' + angle + 'deg');
  		return angle;
  	}
  });