package util;
import play.libs.Json;

/**
 * Created by tiffany on 16/2/22.
 */
public class Regex {
    //http url
    public static final String HTTP = "^(http|https)://([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?$";

    //是否为Json类型
    public static boolean isJason(String value){
        try{
            Json.parse(value);
        }catch(Exception e){
            return false;
        }
        return true;
    }
}