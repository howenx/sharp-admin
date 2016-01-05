$(function() {
	var del_array = []
	var change_flag = false;

	/**	上移 **/
	$(document).on('click', '.slider-label-image-up', function() {
		var temp_sort = $(this).parent().next().attr('data-sort');

		if (temp_sort != '1') {
			$(this).parent().next().attr('data-sort', $(this).parent().parent().parent().prev().find('.slider-content-img').attr('data-sort'));
			$(this).parent().parent().parent().prev().find('.slider-content-img').attr('data-sort', temp_sort);


			$(this).parent().parent().parent().prev().before($(this).parent().parent().parent())

			$('.usercenter-option > .user-state').css('background-position', '20px -73px');

			if (window.lang === 'cn') {
				$('.usercenter-option > .user-state').text('已更改');
			} else {
				$('.usercenter-option > .user-state').text('Changed');
			}

			change_flag = true;
		} else {
			if (window.lang === 'cn') {
				$('#js-userinfo-error').text("已经是第一个").css('color', '#2fa900');
			} else {
				$('#js-userinfo-error').text("It is already the first").css('color', '#2fa900');
			}
			setTimeout("$('#js-userinfo-error').text('').css('color','#c00')", 1000);
		}

	});

	/**	删除 **/
	$(document).on('click', '.slider-label-image-del', function() {

		$('.usercenter-option > .user-state').css('background-position', '20px -73px');

		if (window.lang === 'cn') {
			$('.usercenter-option > .user-state').text('已更改');
		} else {
			$('.usercenter-option > .user-state').text('Changed');
		}

		if($(this).parent().next().attr('data-index')!="-1"){
			del_array.push($(this).parent().next().attr('data-index'));
		}
		
		$(this).parent().parent().parent().remove();

		//改变标志位
		change_flag = true;

		//重新排序当前data-sort
		$('.slider-content-img').each(function(index, element) {
			$(this).attr('data-sort', index + 1);
		})

		if (window.lang === 'cn') {
			$('#js-userinfo-error').text("已删除").css('color', '#2fa900');
		} else {
			$('#js-userinfo-error').text("It has been deleted").css('color', '#2fa900');
		}
		setTimeout("$('#js-userinfo-error').text('').css('color','#c00')", 1000);

	})

	/**	点击图片绑定主题或商品 **/
    $(document).on('click', '.slider-content-img', function() {
        change_flag = true;
    })

	/**	保存 **/
	$(document).on('click', '#js-usercenter-submit', function() {
		if (change_flag) {
			var sliderdto = new Object();
			var slider_array = [];
			//取现有图片ID和排序
			$('.slider-hover-div>.slider-content-img').each(function(index, element) {
				var slider = new Object();
				slider.id = $(this).attr('data-index');
				slider.sortNu = $(this).attr('data-sort');
				var regex = new RegExp(window.url,"gi");
//				slider.img = $(this).attr('src').replace(regex,'');
                var img = {};
                var imgSrc = $(this).attr('src');
                img["url"] = imgSrc.substring(imgSrc.lastIndexOf('/')+1,imgSrc.length);
                img["width"] = $(this).attr('width');
                img["height"] = $(this).attr('height');
                var imgUrl = $(this).attr('src');
				slider.img = JSON.stringify(img);
				slider.itemTarget = $(this).attr('data-target');
                slider.targetType = $(this).attr('data-type');
				slider_array.push(slider);
			})

			sliderdto.update = slider_array;
			sliderdto.del = del_array;
			 console.log(JSON.stringify(sliderdto));
			$.ajax({
			            type : 'POST',
			            url : "/"+window.lang+"/topic/slider/update",
			            data : JSON.stringify(sliderdto),
			            dataType: 'json',
						contentType: "application/json; charset=utf-8",
			            success : function(data) {
			                console.log(data);
							
							$('.usercenter-option > .user-state').css('background-position', '20px -174px');
							
							if (window.lang = 'cn') {
								$('#js-userinfo-error').text('保存成功').css('color', '#2fa900');
								$('.usercenter-option > .user-state').text('未更改');
							} else {
								$('#js-userinfo-error').text('Save success');
								$('.usercenter-option > .user-state').text('Unchanged');
							}
							setTimeout("$('#js-userinfo-error').text('').css('color','#c00')", 2000);
							change_flag=false;
							
							setTimeout("location.href='/"+window.lang+"/topic/slider'", 3000);
							
			            },
			            error : function(jqXHR) {
			                console.log(jqXHR);
 							if (window.lang = 'cn') {
 								$('#js-userinfo-error').text('保存失败');
 							} else {
 								$('#js-userinfo-error').text('Save error');
 							}
							setTimeout("$('#js-userinfo-error').text('')", 2000);
			            }
			        });
		} else {
			if (window.lang = 'cn') {
				$('#js-userinfo-error').text('未更改')
			} else {
				$('#js-userinfo-error').text('Not changed')
			}
			setTimeout("$('#js-userinfo-error').text('')", 1000);
		}
	})

	/**	上传 **/
	$(document).on('click', '#upbn', function() {
		$('#fileinput').click();
	});

	/**	预览 **/
	$(document).on('change', '#fileinput', function() {
	    var file = $(this);
        file.after(file.clone().val(""));
        file.remove();
		var files = $(this)[0].files;
		for (var i = 0; i < files.length; i++) {
			previewImage($(this)[0].files[i]);
		}
	})
	function previewImage(file) {

		if (!/image\/\w+/.test(file.type)) {
			alert("File Type must be an image");
			return false;
		}

		var reader = new FileReader();
		reader.readAsDataURL(file);
		reader.onload = function(e) {
            var image = new Image();
            image.src = e.target.result;
            alert(["图片大小是: width:"+image.width+", height:"+image.height]);
            var width = image.width;
            var height = image.height;
			$('.slider-li-upload').before('<li class="slider-single-li">' +
				'<div class="slider-hover-div">' +
				'<div class="slider-label">' +
				'<div class="slider-label-image-up"></div>' +
				'<div class="slider-label-image-del"></div>' +
				'</div>' +
				'<img data-index="-1" data-sort="' + $("#usercenter-info > ul").children().length + '" class="slider-content-img" width="'+width+'"  height="'+height+'" src="' + this.result + '">' +
				'</div>' +
				'</li>');
			upload($('.slider-li-upload').prev(), file);
		}
	}

	function upload($thumb, file) {
		if (file != null) {
			var formdata = new FormData();
			formdata.append("photo", file);
			formdata.append("params", "minify");

			var http = new XMLHttpRequest();

			var url = window.url + "/upload";

			http.open("POST", url, true);

			http.onreadystatechange = function() {
				if (http.readyState == 4 && http.status == 200) {
					var data = JSON.parse(http.responseText);
//					console.log("data.oss_prefix:"+data.oss_prefix);
//                    console.log("data.oss_url:"+data.oss_url);
//                    console.log("data.path:"+data.path);
//                    console.log("data.imgid:"+data.imgid);
//                    console.log("data.minify_url:"+data.minify_url);

					change_flag=true;
					
					$('.usercenter-option > .user-state').css('background-position', '20px -73px');

					if (window.lang === 'cn') {
						$('.usercenter-option > .user-state').text('已更改');
					} else {
						$('.usercenter-option > .user-state').text('Changed');
					}
					
					//替换dataURL为返回的服务器保存的url
					$thumb.find('.slider-content-img').attr('src', data.oss_prefix+data.oss_url);

					if (window.lang = 'cn') {
						$('#js-userinfo-error').text('图片上传成功').css('color', '#2fa900')
					} else {
						$('#js-userinfo-error').text('Upload success')
					}
					setTimeout("$('#js-userinfo-error').text('').css('color','#c00')", 2000);
				} else {
					if (window.lang = 'cn') {
						$('#js-userinfo-error').text('图片上传失败');
					} else {
						$('#js-userinfo-error').text('Upload error');
					}
					setTimeout("$('#js-userinfo-error').text('')", 2000);
				}
			}
			http.send(formdata);
		} else {
			alert("File must be an image");
			return false;
		}
	}

});
