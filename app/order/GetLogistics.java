package order;


import play.libs.ws.WS;
import play.libs.ws.WSResponse;


/**
 * Created by tiffany on 15/12/17.
 */
public class GetLogistics {

    public static String sendGet(String expCode,String param){
        String url = "http://www.kiees.cn/" + expCode + ".php?wen=" + param + "&action=ajax&rnd=0.0";
        String result = "";
        try{
            WSResponse response = WS.url(url).get().get(1000L);         //http://www.kiees.cn/sf.php?wen=603359393732&action=ajax&rnd=0.0
            result = new String(response.getBody().getBytes("ISO8859-1"),"utf-8");
            int endIndex = result.indexOf("|");
            result = result.substring(1,endIndex);
            System.out.print(result);
        }catch(Exception e){
            e.printStackTrace();
        }
r       return result;

    }
}
