

function upload(file, id) {
        var formdata = new FormData();
        formdata.append("photo", file);
        formdata.append("params", "minify");
        var http = new XMLHttpRequest();
        var url = "http://172.28.3.18:3008/upload";
        http.open("POST", url, true);
        var data = "";

        http.onreadystatechange = function() {
            if (http.readyState == 4 && http.status == 200) {
                data = JSON.parse(http.responseText);
                alert(data.message);
                alert("data.oss_prefix:"+data.oss_prefix);
                alert("data.oss_url:"+data.oss_url);
                alert("data.path:"+data.path);
                alert("data.imgid:"+data.imgid);
                alert("data.minify_url:"+data.minify_url);
//                thumb.getElementsByTagName("img")[0].src = data.oss_prefix+data.oss_url;

                //上传商品详细图分割
                if (id.indexOf("D")>=0) {
                    var http2 = new XMLHttpRequest;
                    var url2 = "http://172.28.3.18:3008/split/file/"+data.oss_url;
                    http2.open("GET", url2, true);
                    http2.onreadystatechange = function() {
                        if (http2.readyState == 4 && http2.status == 200) {
                            var data2 = JSON.parse(http2.responseText);
                            var input = document.createElement("input");
                            input.type="hidden";
                            var splitArr = data2.split_url;
                            splitArr =  splitArr.substring(1,splitArr.length-1);
                            var splitArr = splitArr.split(",");
                            var imgArr = [];
                            for(m=0;m<splitArr.length;m++) {
                                var imgPath = splitArr[m].substring(splitArr[m].lastIndexOf('/')+1,splitArr[m].length-1);
                                imgArr.push(imgPath);
                            }
                            input.value = imgArr;
//                            thumb.appendChild(input);

                        }
                    }
                    http2.send();
                }
            }
        }
        return data.oss_prefix+data.oss_url;
        }