$(function() {
	/*** template params array.****/
	var data_array = [];
	var themeImg = "";
	var uploadImgWidth;
    var uploadImgHeight;
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
			$(".pre_temp").css("display","none");
			$('.' + $(this).attr('data-xr')).css("display","block");
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
	/*****品购模版******/
	$("#unpack_img").click(function(){
		$(".unpack_img").click();
	})
	$(".unpack_img").change(function(){
	    var img = document.createElement("img");
        var file = this.files[0];
        //获取图片文件信息
        var reader = new FileReader();
        reader.onload = (function(aImg) {
            return function(e) {
                aImg.src = e.target.result;
                var image = new Image();
                image.src = e.target.result;
                alert(["图片大小是: width:"+image.width+", height:"+image.height]);
                if(image.width >= 730){
                    uploadImgWidth = image.width;
                }else{
                     uploadImgWidth = 730;
                }
                if(image.height >= 254){
                    uploadImgHeight = image.height;
                }else{
                    uploadImgHeight = 254;
                }

                //aImg.width = image.width;
                //aImg.height = image.height;
                 }
         })(img);
        reader.readAsDataURL(file);
		var formdata = new FormData();
		formdata.append("photo", file);
		formdata.append("params", "minify");
		var http = new XMLHttpRequest();
		var url = "http://172.28.3.18:3008/upload";
		http.open("POST", url, true);
		http.onreadystatechange = function () {
			if (http.readyState == 4 && http.status == 200) {
				var data = JSON.parse(http.responseText);
				console.log(data);
				var str = data.oss_prefix+data.oss_url;
				$("#unpack_img").css({"background-image":"url("+ str +")","width":uploadImgWidth,"height":uploadImgHeight,"background-size":"cover"})

			}
		}
		http.send(formdata);
	})
	/**** preview the template event.. ****/
	$(document).on("click", "a[name='pre_unpack_bt']", function() {
		data_array.length = 0;
		/** u_youjipin ***/
		$temp_div = $(this).parents(".pre_temp").prev().children();
		console.log($temp_div)

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
		}else if($temp_div.attr('id') == 'shop_unpack'){
			$(this).parents(".shop_unpack").find(".input-area").each(function(index, element) {
				if (index === 0 && $(this).val()!=null && $(this).val()!='') {
					$temp_div.find("#unpack_discount").text($(this).val());
					data_array.push($(this).val());

				} else if (index === 1 && $(this).val()!=null && $(this).val()!='') {

					$temp_div.find("#unpack_price_origin").text($(this).val());
					data_array.push($(this).val());

				} else if (index === 2 && $(this).val()!=null && $(this).val()!='') {

					$temp_div.find("#unpack_price_current").text($(this).val());
					data_array.push($(this).val());

				}  else if (index === 3 && $(this).val()!=null && $(this).val()!='') {

					$temp_div.find("#unpack_price_unpack").text($(this).val());
					data_array.push($(this).val());
				}else if (index === 4 && $(this).val()!=null && $(this).val()!='') {
					//$temp_div.find("#unpack_img").css("background-size",$(this).val()+"px");
					//alert(1);
				}else {
					alert('Please check exists null value.');
					return false;
				}
			})
		}
		console.log(data_array);
	})

	/** submit to nwjs shootscreen. **/
	$('#submit').on("click", function() {
		  var background = $("#unpack_img").css("background");
          if(background.indexOf("/assets/images/upload_iezdinbygzqwmoldgmzdambqmmyde_750x316.jpg")>0)
          {
            isPost = false;
            alert("请点击默认图片,上传主题图片!");
            return false;
          }
          var html = $("#shop_unpack").prop("outerHTML");
          var width = $("#shop_unpack").width();
          var height = $("#shop_unpack").height();
          $check = $('input[name=setMain]:checked');
          console.log(html);
          console.log(width);
          console.log(height);

          if ($check.length === 1) {
             $.ajax({
                         url: "http://172.28.3.51:3008/cut", //Server script to process data
                         type: 'post',
                         data: {
                            html: '' +html,
                            width:width,
                            height:height
                         },
                         success: function(data) {
                            console.log(JSON.stringify(data));
                            themeImg = data.oss_url;
                            themeImgShot = data.shot_url;
                            window.open(data.shot_url,'_blank');
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
        if($("#onShelvesAt").val()>$("#offShelvesAt").val()){
            isPost = false;
            alert("日期不正确!");
            return false;
        }
        var background = $("#unpack_img").css("background");
        if(themeImg == "")
        {
            isPost = false;
            alert("请提交主题图片!");
            return false;
        }
        if(document.getElementById("sort").rows.length == 1){
            isPost = false;
            alert("请添加商品!");
            return false;
        }
        if(document.getElementById("sort").rows[1].cells[1].getElementsByTagName("input")[0].checked == false){
            isPost = false;
            alert("请选择主商品!");
            return false;
        }
        if(document.getElementById("dragon-container").innerHTML.indexOf("img")<0)
        {
            isPost = false;
            alert("请上传标签的背景图片!");
            return false;
        }
        if($("#input_imgurl").val() == "")
        {
            isPost = false;
            alert("请添加标签中的商品ID!");
            return false;
        }
        if(document.getElementById("dragon-container").getElementsByClassName("dragon-contained ui-draggable ui-draggable-handle").length == 0)
        {
            isPost = false;
            alert("请添加标签!");
            return false;
        }
        //检查主商品是否重复
        var itemFlag = false;
        var items = document.getElementById("sort");
        var itemCount = items.getElementsByTagName("tr").length;
        for(i=1;i<itemCount;i++){
            for(j=1;j<itemCount;j++){
                if(i != j && items.rows[i].cells[2].innerText == items.rows[j].cells[2].innerText){
                    items.rows[i].style.backgroundColor = "skyblue";
                    items.rows[j].style.backgroundColor = "skyblue";
                    itemFlag = true;
                }
            }
        }
        if(itemFlag){
            isPost = false;
            alert("有重复的商品ID!");
            return false;
        }
        var theme = new Object();
        //主商品ID
        var masterItemId = parseInt(document.getElementById("sort").rows[1].cells[2].innerHTML);
        //主题标题
        var title = $("#themeTitle").val();
        //开始日期
        var onShelvesAt = $("#onShelvesAt").val();
        //结束日期
        var offShelvesAt = $("#offShelvesAt").val();
        //主题图片,主页显示图片
        var themeImgFinal = themeImg.substring(themeImg.indexOf('/',themeImg.indexOf('/')+2));
        //排序
        var sortNu = 1;
        //主题源背景图片
        var themeSrcImgTemp = background.substring(background.indexOf('url("')+5,background.indexOf('")'));
        var themeSrcImg = themeSrcImgTemp.substring(themeSrcImgTemp.indexOf('/',themeSrcImgTemp.indexOf('/')+2));
        //主题的配置信息
        var themeConfig = [];
        //主题包含的商品信息
        var themeItems = [];
        var items = document.getElementById("sort");
        for(i=1;i<items.getElementsByTagName("tr").length;i++){
            var itemId = parseInt(items.rows[i].cells[2].innerText);
            themeItems.push(itemId);
            }
        //主题主宣传图上的标签
        var masterItemTag = [];
        var tagsContainer = document.getElementById("dragon-container");
        var containerWidth = tagsContainer.scrollWidth;
        var containerHeight = tagsContainer.scrollHeight;
        var tags = tagsContainer.getElementsByClassName("dragon-contained ui-draggable ui-draggable-handle");
        for(i=0;i<tags.length;i++)
        {
            var tag = {};
            var style = tags[i].style.cssText;
            var tagLeft = parseInt(style.substring(style.indexOf("left: ")+6,style.indexOf("px;")));
            var tagTop =  parseInt(style.substring(style.indexOf("top: ")+5,style.length-3)) - 50;

            tag.left = parseFloat((tagLeft/containerWidth).toFixed(2));
            tag.url = "/comm/detail/" + masterItemId;
            tag.top = parseFloat((tagTop/containerHeight).toFixed(2));
            tag.name = tags[i].getElementsByTagName("p")[0].innerText;
            if(tags[i].getElementsByClassName("dragon-graph")[0].style.cssText.indexOf(180)>0)
            {
                tag.angle = 180;
            }else{
                tag.angle = 0;
            }
            masterItemTag.push(tag);
        }
        //主题列表主宣传图
        var imgUrl = document.getElementById("dragon-container").getElementsByTagName("img")[0].src;
        var themeMasterImg = imgUrl.substring(imgUrl.indexOf('/',imgUrl.indexOf('/')+2));
        //主题主图片
        var themeImgContent = {};
        themeImgContent.url = themeImgFinal;
        themeImgContent.width = uploadImgWidth;
        themeImgContent.height = uploadImgHeight;
        //主题tag背景图
        var themeMasterImgContent = {};
        themeMasterImgContent.url = themeMasterImg;
        themeMasterImgContent.width = labelImgWidth;
        themeMasterImgContent.height = labelImgHeight;

        theme.masterItemId = masterItemId;
        theme.title = title;
        theme.startAt = onShelvesAt;
        theme.endAt = offShelvesAt;
        theme.themeImg = JSON.stringify(themeImgContent);
        //theme.themeImg = themeImgFinal;
        theme.sortNu = sortNu;
        //theme.orDestory = false;
        theme.themeSrcImg = themeSrcImg;
        theme.themeDesc = themeConfig;
        theme.themeItem = themeItems;
        theme.themeTags = masterItemTag;
        //theme.themeMasterImg = themeMasterImg;
        theme.themeMasterImg = JSON.stringify(themeMasterImgContent);


        if (isPost) {
                    $.ajax({
                        type :  "POST",
                        url : "/topic/add/themeSave",
                        contentType: "application/json; charset=utf-8",
                        data : JSON.stringify(theme),
                        error : function(request) {
                            if (window.lang = 'cn') {
                                $('#js-userinfo-error').text('保存失败');
                            } else {
                                $('#js-userinfo-error').text('Save error');
                            }
                            setTimeout("$('#js-userinfo-error').text('')", 2000);
                        },
                        success: function(data) {
                            alert("Save Success");
                            if (window.lang = 'cn') {
                                $('#js-userinfo-error').text('保存成功').css('color', '#2fa900');
                            } else {
                                $('#js-userinfo-error').text('Save success');
                            }
                            setTimeout("$('#js-userinfo-error').text('').css('color','#c00')", 3000);
                            //主题录入, 成功后返回到主题录入页面
                            setTimeout("location.href='/"+window.lang+"/topic/add'", 1000);
                        }
                    });
                }


  	})


});
