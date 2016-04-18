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

    //整数或小数
    public static final String DECIMAL = "^[0-9]+\\.{0,1}[0-9]{0,2}$";

    //正整数
    public static final String NUMBER = "^[0-9]+$";

    //字母
    public static final String LETTER = "^[A-Za-z]+$";

    //字母数字
    public static final String LETTER_NUM = "^[A-Za-z0-9]+$";
}
