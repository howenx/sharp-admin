//修改标记
var change_flag = false;

function UpdateFields(obj) {
	var targetType = obj.targetType;
	var $obj = obj;
	var itemTarget = "";
	if(targetType=="T" || targetType=="U") {
		var id = obj.id;
		if (targetType=="T")  itemTarget = "/topic/list/"+id;
		if (targetType=="U")  itemTarget = obj.h5Link;
//		if (targetType=="U")  itemTarget = "/topic/list/"+id;
	}
	if (targetType=="D" || targetType=="P") {
		var itemId = obj.itemId;
		var skuTypeId = obj.skuTypeId;
		if (targetType=="D") {
			itemTarget = "/comm/detail/item/" + itemId + "/" + skuTypeId;
		}
		if (targetType=="P") {
			itemTarget = "/comm/detail/pin/" + itemId + "/" + skuTypeId;
		}
	}

	if(targetType == "M"){

	}

	$obj.attr('data-target', itemTarget);
	$obj.attr('data-type', targetType);
}

function ShowModal($obj) {
	var sharedObject = {};
	sharedObject = $obj;
	//var a = $obj.hasOwnProperty('data-type');
	var targetType =  $obj.attr('data-type');
	var itemTarget =  $obj.attr('data-target');
	sharedObject.targetType = targetType;
	sharedObject.itemTarget = itemTarget;
	if (window.showModalDialog) {
		var retValue = showModalDialog("/topic/category/popup", sharedObject, "dialogWidth:1300px; dialogHeight:800px; dialogLeft:300px;");
		if (retValue) {
			UpdateFields(retValue);
		}
	}
	else {
		// for similar functionality in Opera, but it's not modal!
		var modal = window.open ("/topic/category/popup", null, "width=1300,height=800,left=300,modal=yes,alwaysRaised=yes", null);
		modal.dialogArguments = sharedObject;
	}
}
function changeText(event,element){
	var e = window.event||event;
	var obj = e.target;
	if(obj.tagName=="INPUT"){
		return;
	}
	var oldHtml = $(element).html();
	console.log(oldHtml);

	if(element.childNodes.length==0
		||(element.childNodes.length==1&&element.childNodes[0].nodeType==3)){
		var addText = $("<input type='text'>").css({
			"width":"100%"
		}).val(oldHtml);
		$(addText).blur(function(){
			$(element).html(this.value?this.value:oldHtml);
			if(this.value!==oldHtml){
				$('.usercenter-option > .user-state').css('background-position', '20px -73px');
				$(".user-state").text("已更改");
			}
			if($(element).parent().find(":input")){
				$(element).parent().find(":input").val($(element).html());
			}
		})
	}
	element.innerHTML = "";
	$(addText).appendTo($(element));
	addText.focus();
	change_flag = true;
}
$(function() {
	var del_array = []
	/**	上移 **/
	$(document).on('click', '.slider-label-image-up', function() {
		var temp_sort = $(this).parent().next().attr('data-sort');

		if (temp_sort != '1') {
			$(this).parent().next().attr('data-sort', $(this).parent().parent().parent().prev().find('.slider-content-img').attr('data-sort'));
			$(this).parent().parent().parent().prev().find('.slider-content-img').attr('data-sort', temp_sort);


			$(this).parents("li").prev().before($(this).parents("li"));

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
		ShowModal($(this));
        $('.usercenter-option > .user-state').css('background-position', '20px -73px');
        if (window.lang === 'cn') {
            $('.usercenter-option > .user-state').text('已更改');
        } else {
            $('.usercenter-option > .user-state').text('Changed');
        }
        change_flag = true;
    })

	/**	保存 **/
	$(document).on('click', '#js-usercenter-submit', function() {
		if (change_flag) {
		    //分类入口个数验证
	        var categoryCount = $(".category").size();
            if(categoryCount < window.categoryCount){
                $('#js-userinfo-error').text("必须上传" + window.categoryCount + "个").css('color', '#c00');
                setTimeout("$('#js-userinfo-error').text('').css('color', '#2fa900')",3000);
                return false;
            }

            //文字长度验证
            var loopBreak = false;
            $(".category").find(".category-name").each(function(){
                var index =  $(this).index() + 1;
                if($(this).text().length > 4){
                    $('#js-userinfo-error').text("文本不能超过4个字,第" + index + "个分类入口超过4个字").css('color', '#c00');
                    setTimeout("$('#js-userinfo-error').text('').css('color', '#2fa900')",3000);
                    loopBreak = true;
                    return false;
                }
            })

            if(loopBreak){
            return false;
            }

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
                if (imgSrc!=null) img["url"] = imgSrc.split(window.url)[1];
                img["width"] = $(this).attr('width');
                img["height"] = $(this).attr('height');
                var imgUrl = $(this).attr('src');
				slider.img = JSON.stringify(img);
				var itemTarget = $(this).attr('data-target');
				var targetType = $(this).attr('data-type');
				slider.itemTarget = itemTarget==""?null:itemTarget;
				slider.targetType = targetType==""?null:targetType;
				slider.orNav = true;
				slider.navText = $(this).parent().parent().find('span').text();
				slider_array.push(slider);
			})

			sliderdto.update = slider_array;
			sliderdto.del = del_array;
			 console.log(JSON.stringify(sliderdto));
			$.ajax({
			            type : 'POST',
			            url : "/"+window.lang+"/topic/category/update",
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

							setTimeout("location.href='/"+window.lang+"/topic/category'", 3000);

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
	    var categoryCount = $(".category").size();
	    if(categoryCount < window.categoryCount){
	        $('#fileinput').click();
	    }else{
	     alert("最多只能上传" + window.categoryCount + "个");
	    }
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
           // alert(["图片大小是: width:"+image.width+", height:"+image.height]);
            var width = image.width;
            var height = image.height;
			$('.slider-li-upload').before('<li class="slider-single-li category">' +
				'<span class="category-name" onclick="changeText(event,this)">单击编辑</span>'+
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

     var date = new Date();
     var dateStr = ''+date.getFullYear()+(date.getMonth()+1>=10?date.getMonth()+1:'0'+(date.getMonth()+1))+(date.getDate()>=10?date.getDate():'0'+date.getDate());

	function upload($thumb, file) {
		if (file != null) {
			var formdata = new FormData();
			var prefix = "slider/pic/"+dateStr+"/"
			formdata.append("photo", file);
			formdata.append("params", "minify");
			formdata.append("prefix", prefix);

			var http = new XMLHttpRequest();

			var url = window.uploadUrl + "/upload";

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
					$thumb.find('.slider-content-img').attr('data-target', '');
					$thumb.find('.slider-content-img').attr('data-type', '');

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
	/*******拖拽*******/
	new Sortable(document.getElementById("categorySort"));
});
