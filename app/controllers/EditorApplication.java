package controllers;


import com.fasterxml.jackson.databind.JsonNode;
import controllers.edit.EditorService;
import org.springframework.util.StringUtils;
import play.Logger;
import play.Play;
import play.cache.Cache;
import play.data.DynamicForm;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class EditorApplication extends Controller{


//	@Security.Authenticated(UserAuth.class)
	public  Result editor(){
		return ok(views.html.index.render());
	}

//	@Security.Authenticated(UserAuth.class)
	public  Result editorController() throws Exception{
		DynamicForm form   = Form.form().bindFromRequest();
		String action = form.get("action");
		//根据上传人得不同，上传目录不同，列出图片目录不同
//		String userId = "1000038";
//		if(StringUtils.isEmpty(userId)){
//			return redirect("/login");
//		}
		Logger.info("图片文件处理editor action："+action);
        String config = getConfig();
        JsonNode json = Json.parse(config);
		if(StringUtils.isEmpty(action)){
			return ok("参数错误");
		}
		else if(action.equals("config")){
			assert config != null;
			return ok(config);
		}
//		else if(action.equals(json.get("imageActionName").textValue())){
//			Http.MultipartFormData body = request().body().asMultipartFormData();
//			Logger.error("body:"+body.getFile(json.get("imageFieldName").textValue()));
//			return ok(Json.toJson(EditorService.getInstance().editorUploadFile(body.getFile(json.get("imageFieldName").textValue()), json.get("imagePathFormat").textValue())));
//		}
//		else if(action.equals(json.get("videoActionName").textValue())){
//			MultipartFormData body = request().body().asMultipartFormData();
//			return ok(Json.toJson(EditorService.getInstance().editorUploadFile(body.getFile(json.get("videoFieldName").textValue()), json.get("videoPathFormat").textValue()+"/"+userId)));
//		}
//		else if(action.equals(json.get("fileActionName").textValue())){
//			MultipartFormData body = request().body().asMultipartFormData();
//			return ok(Json.toJson(EditorService.getInstance().editorUploadFile(body.getFile(json.get("fileFieldName").textValue()), json.get("filePathFormat").textValue()+"/"+userId)));
//		}
//		else if(action.equals(json.get("scrawlActionName").textValue())){//涂鸦
//			return ok(Json.toJson(EditorService.getInstance().editorUploadBase64File(form.get(json.get("scrawlFieldName").textValue()), json.get("scrawlPathFormat").textValue()+"/"+userId)));
//		}
//		else if(action.equals(json.get("imageManagerActionName").textValue())){//列出图片
//			int listSize = json.get("imageManagerListSize").intValue();
//			int start = form.get("start")==null?0:Integer.parseInt(form.get("start"));
//			int end = start+listSize;
//			return ok(Json.toJson(EditorService.getInstance().listImage(json.get("imageManagerListPath").textValue()+"/"+userId,start,end)));
//		}
//		else if(action.equals(json.get("fileManagerActionName").textValue())){//列出文件
//			int listSize = json.get("fileManagerListSize").intValue();
//			int start = form.get("start")==null?0:Integer.parseInt(form.get("start"));
//			int end = start+listSize;
//			return ok(Json.toJson(EditorService.getInstance().listImage(json.get("fileManagerListPath").textValue()+"/"+userId,start,end)));
//
//		}
//		else if(action.equals(json.get("catcherActionName").textValue())){//远程抓取图片
//			RequestBody obj = request().body();
//			String[]  str = obj.asFormUrlEncoded().get("source[]");
//			HashMap<String,Object> map = new HashMap<String,Object>();
//			ArrayList<JsonNode> retrunList = EditorService.getInstance().catchImage(str, json.get("catcherPathFormat").textValue()+"/"+userId);
//			map.put("list", retrunList);
//			map.put("state", retrunList.size()>0?"SUCCESS":"ERROR");
//			return ok(Json.toJson(map));
//		}
		else{
			return ok("请求地址错误");
		}
	}
	
	//获取配置信息
	public static String getConfig(){
		File file = Play.application().getFile("/conf/config.json");
        InputStream in = null;
		String config = "";
		if(Cache.get("editorConfig")!=null){
        	config = String.valueOf(Cache.get("editorConfig"));
        }else{
        	 try {
             	Logger.info("=============读取配置文件：config.json=======");
                in = new FileInputStream(file);
                config = EditorService.getInstance().inputStreamString(in).replaceAll("/\\*[\\s\\S]+?\\*/", "").replaceAll("\n", "").replaceAll(" ", "");
                in.close();
                Cache.set("editorConfig",config,60*60);
     			Logger.info("获取config.json数据："+config);
             } catch (IOException e) {
                 e.printStackTrace();
                 return null;
             }
        }
		return config;
	}
	
}




