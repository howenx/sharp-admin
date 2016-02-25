package tool;
import play.libs.Json;

/**
 * Created by tiffany on 16/2/22.
 */
public class Regex {
    //正整数
    public static final String NON_ZERO_POSTIVE_INT = "^[1-9]\\d*$";
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
