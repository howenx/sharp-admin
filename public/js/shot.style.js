$(function() {
	/*** template params array.****/
	var data_array = [];
	var themeImg = "";
	/***** replace file click event. *****/
	$('#upbn').on("click", function() {
		if($(':radio[name=select-minify]').is(':checked')){
			$('#fileinput').click();
		}
		else {
  			alert('Please select minify radio.');
  			return false;
		}
	});

	/**** Template selected radio button click event. *****/
	$(':radio[name=setMain]').on("click", function() {
		if ($(this).prop('checked')) {
			$('.li-dv').hide();
			$('#' + $(this).attr('data-xr')).parent().css('display', 'table');
			if ($(this).attr('data-xr') === 'shop_unpack') {
				$('#' + $(this).attr('data-xr')).parent().css('display', 'block');
			}
		}
	});

	/***Loading..***/
	$(document).ajaxStart(function() {
		$('#mask').show();
	});
	$(document).ajaxStop(function() {
		$('#mask').hide();
	});

	/*** 默认宽度触发事件 ***/
	$(document).on("change", "input[name='origin_wh']", function() {
		if ($(this).is(':checked')) {
			$(this).parent().parent().next().children().last().children().first().val('');
			$(this).parent().parent().next().children().last().children().first().prop('disabled', true);
			$(this).parent().parent().next().next().children().last().children().first().first().val('');
			$(this).parent().parent().next().next().children().last().children().first().first().prop('disabled', true);
		} else {
			$(this).parent().parent().next().children().last().children().first().val('');
			$(this).parent().parent().next().children().last().children().first().prop('disabled', false);
			$(this).parent().parent().next().next().children().last().children().first().first().val('');
			$(this).parent().parent().next().next().children().last().children().first().first().prop('disabled', false);
		}
	})
	
	/**** preview the template event.. ****/
	$(document).on("click", "a[name='pre_unpack_bt']", function() {
		data_array.length = 0;
		/** u_youjipin ***/
		$temp_div = $(this).parents(".pre_temp").prev();

		if ($temp_div.attr('id') == 'u_youjipin') {

			$(this).parent().parent().parent().find(".input-area").each(function(index, element) {
				
				var imgwidth = $(this).parent().parent().parent().find('input[name="custom_w"]').val();
				var imgheight = $(this).parent().parent().parent().find('input[name="custom_h"]').val();
				
				if (index === 0 && $(this).val()!=null && $(this).val()!='') {
					
					$temp_div.find("[data-index='" + index + "']").text($(this).val());
					data_array.push($(this).val());
					
				} else if (index === 1 && $(this).val()!=null && $(this).val()!='') {
					
					$temp_div.find("[data-index='" + index + "']").text($(this).val());
					data_array.push($(this).val());
					
				} else if (index === 2 && $(this).val()!=null && $(this).val()!='') {
					
					$temp_div.find("[data-index='" + index + "']").text($(this).val());
					data_array.push($(this).val());
					
				} else if (index === 3 && $(this).val()!=null && $(this).val()!='') {
					
					$temp_div.find("[data-index='" + index + "']").text($(this).val());
					data_array.push($(this).val());
					
				} else if (index === 4) {

					if ($(this).parent().parent().parent().find("input[name='origin_wh']").is(':checked')) {
						var img_val = $("#u_youjipin").find(".main-img").attr('src');
						data_array.push(img_val);
					} else if(imgwidth!=null && imgwidth!='' &&  imgheight!=null && imgheight!=''){
						var img_val = $("#u_youjipin").find(".main-img").attr('src');
						var imgname = img_val.match(/[^http:\/\/]+\.(jpg|jpeg|JPG|JPEG|png|PNG|gif|GIF|webp|WEBP)/gi).toString();
						$imginput = $(this);
						$temp_div.find(".main-img").width(imgwidth+'px');
						$temp_div.find(".main-img").height(imgheight+'px');
						if (imgname.match(/^(.*)(\.)(.{1,8})$/) != null) {

							thumb = imgname + '_' + imgwidth + '×' + imgheight + '.' + imgname.match(/^(.*)(\.)(.{1,8})$/)[3].toLowerCase();
							
							$.ajax({
								url: window.url+'/thumb/' + thumb, //Server script to process data
								type: 'get',
								success: function(data) {
									if (typeof data.thumb_url != 'undefined' && data.thumb_url != null) {
										$temp_div.find(".main-img").attr('src', data.thumb_url);
										data_array.push(data.thumb_url);
									}
								},
								error: function(data, error, errorThrown) {
									if (data.status && data.status >= 400) {
										alert(data.responseText);
									} else {
										alert("Something went wrong");
									}
								}
							});
							
						} else {
							alert('Image type is not matched.');
							return false;
						}
					} else {
						alert('Please check exists null value.')
						return false;
					}
				} else {
					alert('Please check exists null value.')
					return false;
				}
			})
		}else if ($temp_div.attr('id') == 'q_youjipin') {

			$(this).parent().parent().parent().find(".input-area").each(function(index, element) {
				if (index === 0 && $(this).val()!=null && $(this).val()!='') {
					$temp_div.find("[data-index='" + index + "']").text($(this).val());
					data_array.push($(this).val());

				} else if (index === 1 && $(this).val()!=null && $(this).val()!='') {

					$temp_div.find("[data-index='" + index + "']").text($(this).val());
					data_array.push($(this).val());

				} else if (index === 2 && $(this).val()!=null && $(this).val()!='') {

					$temp_div.find("[data-index='" + index + "']").text($(this).val());
					data_array.push($(this).val());

				} else if (index === 3 && $(this).val()!=null && $(this).val()!='') {

					$temp_div.find("[data-index='" + index + "']").text($(this).val());
					data_array.push($(this).val());

				} else if (index === 4 && $(this).val()!=null && $(this).val()!='') {

					$temp_div.find("[data-index='" + index + "']").text($(this).val());
					data_array.push($(this).val());

				} else if (index === 5 && $(this).val()!=null && $(this).val()!='') {

					$temp_div.find("[data-index='" + index + "']").text($(this).val());
					console.log($(this));
					data_array.push($(this).val());

				} else if (index === 6) {
					var img_val = $("#q_youjipin").find("[data-index='" + index + "']").attr('src');
					data_array.push(img_val);
				} else {
					alert('Please check exists null value.');
					return false;
				}
			})
		}else if ($temp_div.attr('id') == 'q_youjipin2') {

			$(this).parent().parent().parent().find(".input-area").each(function(index, element) {
				if (index === 0 && $(this).val()!=null && $(this).val()!='') {
					$temp_div.find("[data-index='" + index + "']").text($(this).val());
					data_array.push($(this).val());

				} else if (index === 1 && $(this).val()!=null && $(this).val()!='') {

					$temp_div.find("[data-index='" + index + "']").text($(this).val());
					data_array.push($(this).val());

				} else if (index === 2 && $(this).val()!=null && $(this).val()!='') {

					$temp_div.find("[data-index='" + index + "']").text($(this).val());
					data_array.push($(this).val());

				} else if (index === 3 && $(this).val()!=null && $(this).val()!='') {

					$temp_div.find("[data-index='" + index + "']").text($(this).val());
					data_array.push($(this).val());

				} else if (index === 4 && $(this).val()!=null && $(this).val()!='') {

					$temp_div.find("[data-index='" + index + "']").text($(this).val());
					data_array.push($(this).val());

				} else if (index === 5 && $(this).val()!=null && $(this).val()!='') {

					$temp_div.find("[data-index='" + index + "']").text($(this).val());
					console.log($(this));
					data_array.push($(this).val());

				} else if (index === 6) {
					var img_val = $("#q_youjipin2").find("[data-index='" + index + "']").attr('src');
					data_array.push(img_val);
				} else {
					alert('Please check exists null value.');
					return false;
				}
			})
		}
		console.log(data_array);
	})

	/** submit to nwjs shootscreen. **/
	$('#submit').on("click", function() {
		if($("input[data-xr=upload-img]").is(':checked')){
			data_array.length = 0;
			data_array.push($("#upload-img").find("img").attr("src"));
			console.log(data_array);
		}else if($("input[data-xr=shop_unpack]").is(':checked')){
			data_array.length = 0;
			data_array.push($("#shop_unpack").find("img").attr("src"));
			console.log(data_array);
		}
		//console.log(JSON.parse(JSON.stringify(data_array)));
		$check = $('input[name=setMain]:checked');
		//console.log($('#' + $check.attr('data-xr')).width());
		//console.log($('#' + $check.attr('data-xr')).height());
		console.log(JSON.stringify(data_array));
		if ($check.length === 1) {
			$.ajax({
				//url: 'http://172.28.3.47:3008/nw',
				url: 'http://172.28.3.18:3008/nw',
				//url:window.url+"/nw",
				type: 'POST',
				data: {
					tempid: '' + $check.attr('data-xr'),
					img_width: $('#' + $check.attr('data-xr')).find(".main-img").width(),
					img_height: $('#' + $check.attr('data-xr')).find(".main-img").height(),
					xr_width: $('#' + $check.attr('data-xr')).width(),
					xr_height: $('#' + $check.attr('data-xr')).height(),
					array:JSON.stringify(data_array)
				},
				success: function(data) {
					$('#mask').hide();
					alert(data.error + ' ' + data.message + ' ' + data.shot_url);
					themeImg = data.shot_url;
					$check.prop('checked', false);
					window.open(data.shot_url);
					return false;
				}
			})
		} else {
			alert('Please check the templates.');
		}

	});

  	

	/***for mitilupload images foreach files for preview.****/
	$(document).on("change", "#fileinput", function() {
		
  		var files = $(this)[0].files;
  		for (var i = 0; i < files.length; i++) {
  			previewImage(this.files[i]);
  		}
	})
	
	/**** preview single image file.*****/
  	function previewImage(file) {
  		var galleryId = "gallery";

  		var gallery = document.getElementById(galleryId);
  		var imageType = /image.*/;

  		if (!file.type.match(imageType)) {
  			throw "File Type must be an image";
  		}

  		var thumb = document.createElement("div");
  		thumb.classList.add('thumbnail');

  		var img = document.createElement("img");
  		img.file = file;
  		thumb.appendChild(img);
  		gallery.appendChild(thumb);

  		// Using FileReader to display the image content
  		var reader = new FileReader();
  		reader.onload = (function(aImg) {
  			return function(e) {
  				aImg.src = e.target.result;
  			};
  		})(img);
  		reader.readAsDataURL(file);
  		
		upload(thumb, file);
  	}
	
  	function upload(thumb, file) {
		
  		var params = '';
		
  		$(':radio[name=select-minify]').each(function(index, element) {
			
  			if ($(this).prop('checked')) {
  				params = $(this).attr('data-xf');
  			}
  		})
  		if (params != '' && file != null) {
  			var formdata = new FormData();
  			formdata.append("photo", file);
  			formdata.append("params", params);
			
  			var http = new XMLHttpRequest();
  			//var url = "http://172.28.3.47:3008/upload";
  			var url = "http://172.28.3.18:3008/upload";
			//var url = window.url+"/upload";
  			http.open("POST", url, true);
			
  			http.onreadystatechange = function() {
				
  				if (http.readyState == 4 && http.status == 200) {
					
  					var data = JSON.parse(http.responseText);
					
  					alert(data.message);
					
  					if (typeof data.compress != 'undefined' && data.compress != null) {
  						$('#gpicnm').append('<span style="display:block;margin:10px;width:100%;"><h4>第' + ($('#gallery').children().length) 
						+ '张</h4>图片名称: <b>' + data.imgid + '</b></br>图片路径:<b>' + data.path + '</b><br>图片URL: <b>'
						+data.minify_url+'</b><br>压缩数据: <b>压缩前大小 ' 
						+ data.compress.before + ', 压缩后大小 ' + data.compress.after + ', 用时 ' + data.compress.time + ', 压缩率 ' + data.compress.rate + '</b></span>');
  					}
					
  					$(':radio[name=select-minify]').each(function(index, element) {
  						$(this).prop('checked', false);
  					})
  				}
  			}
			
  			http.send(formdata);
			
  		} else {
  			alert('Please select minify radio.');
  			return false;
  		}
  	}

  	$("#push").click(function(){
        var isPost = true;
        if($("#themeTitle").val() == "" || $("#onShelvesAt").val() == "" || $("#offShelvesAt").val() == "")
        {
            isPost = false;
            alert("基本信息不能为空!");
            return false;
        }
        if(themeImg == "")
        {
            isPost = false;
            alert("请选择主题并上传图片!");
            return false;
        }
        if(document.getElementById("dragon-container").getElementsByClassName("dragon-contained ui-draggable ui-draggable-handle").length == 0)
        {
            isPost = false;
            alert("请添加标签!");
            return false;
        }
        var theme = new Object();
        var masterItemId = document.getElementById("sort").rows[1].cells[2].innerHTML;
        var title = $("#themeTitle").val();
        var onShelvesAt = $("#onShelvesAt").val();
        var offShelvesAt = $("#offShelvesAt").val();
        var themeImgFinal = themeImg.substring(themeImg.indexOf('/',themeImg.indexOf('/')+2));
        var sortNu = 1;
        var obj = document.getElementById("u_youjipin").getElementsByTagName("img");
        var url = obj[0].src;
        var themeSrcImg = url.substring(url.indexOf('/',url.indexOf('/')+2));

        var themeConfig = [];
        var config1 = document.getElementsByClassName("pre_temp")[0];
        if(config1.getElementsByTagName("input")[0].value != "")
        {
           themeConfig.push("仅剩:" + config1.getElementsByTagName("input")[0].value);
           themeConfig.push("标题:" + config1.getElementsByTagName("input")[1].value);
           themeConfig.push("价格/折扣:" + config1.getElementsByTagName("input")[2].value);
           themeConfig.push("元/折起:" + config1.getElementsByTagName("input")[3].value);

        }
        var config2 = document.getElementsByClassName("pre_temp")[1];
        if(config2.getElementsByTagName("input")[0].value != "")
        {
           themeConfig.push("折扣率:" + config2.getElementsByTagName("input")[0].value);
           themeConfig.push("市场价:" + config2.getElementsByTagName("input")[1].value);
           themeConfig.push("折扣价:" + config2.getElementsByTagName("input")[2].value);
           themeConfig.push("拼购人数:" + config2.getElementsByTagName("input")[3].value);
           themeConfig.push("拼购价格:" + config2.getElementsByTagName("input")[4].value);
           themeConfig.push("已拼购人数:" + config2.getElementsByTagName("input")[5].value);

        }

        var themeItems = [];
        themeItems.push(document.getElementById("sort").innerHTML);
        var masterItemTag = [];
        var tagsContainer = document.getElementById("dragon-container").getElementsByClassName("dragon-contained ui-draggable ui-draggable-handle");
        for(i=0;i<tagsContainer.length;i++){
            var tag = [];
            var style = tagsContainer[i].style.cssText;
            var left = "left:" + style.substring(style.indexOf("left: ")+6,style.indexOf(";"));
            var top = "top:" + style.substring(style.indexOf("top: ")+5,style.length-1);
            var name = "name:" + tagsContainer[i].getElementsByTagName("p")[0].innerText;
            var angle;
            if(tagsContainer[i].getElementsByClassName("dragon-graph")[0].style.cssText.indexOf(180))
            {
                angle = "angle:" + 180;
            }else{
                angle = "angle:" + 0;
            }
            tag.push(top);
            tag.push(left);
            tag.push(name);
            tag.push(angle);
            masterItemTag.push(tag);
        }
        theme.master_item_id = masterItemId;
        theme.title = title;
        theme.start_at = onShelvesAt;
        theme.end_at = offShelvesAt;
        theme.theme_img = themeImgFinal;
        theme.sort_nu = sortNu;
        theme.or_destroy = false;
        theme.theme_src_img = themeSrcImg;
        theme.theme_config_info = themeConfig;
        theme.theme_item = themeItems;
        theme.master_item_tag = masterItemTag;

        alert(theme);
        /*
        if (isPost) {
                    $.ajax({
                        type :  "POST",
                        url : "/topic/themeSave",
                        contentType: "application/json; charset=utf-8",
                        data : JSON.stringify(itemData),
                        error : function(request) {
                            if (window.lang = 'cn') {
                                $('#js-userinfo-error').text('保存失败');
                            } else {
                                $('#js-userinfo-error').text('Save error');
                            }
                            setTimeout("$('#js-userinfo-error').text('')", 2000);
                        },
                        success: function(data) {
                            if (window.lang = 'cn') {
                                $('#js-userinfo-error').text('保存成功').css('color', '#2fa900');
                            } else {
                                $('#js-userinfo-error').text('Save success');
                            }
                            setTimeout("$('#js-userinfo-error').text('').css('color','#c00')", 2000);
                            //主题录入, 成功后返回到主题录入页面
                            setTimeout("location.href='/"+window.lang+"/topic/add'", 3000);
                        }
                    });
                }
                */
  	})


});
