package order;

import play.libs.ws.WS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by tiffany on 15/12/17.
 */
public class GetLogisticsInfo {
    private String weburl = "http://www.kiees.cn/";

    public static String sendGet(String expType,String param){

        WS.url("http://www.kiees.cn/sf.php?wen=603359393732&action=ajax&rnd=0.0").get().get(1000L);
        String result = "";
        BufferedReader read = null;
        try{
            //URL url = new URL(this.weburl + expType +"?wen=" +param);
            URL url = new URL("http://www.kiees.cn/sf.php?wen=603359393732&action=ajax&rnd=0.0");//http://www.kiees.cn/sf.php?wen=603359393732&action=ajax&rnd=0.9881563398727631
            HttpURLConnection httpConn = (HttpURLConnection)url.openConnection();
            InputStreamReader input = new InputStreamReader(httpConn.getInputStream(),"utf-8");
            BufferedReader bufferedReader = new BufferedReader(input);
            String line = "";
            StringBuffer contentBuff = new StringBuffer();
            while((line = bufferedReader.readLine()) != null){
                contentBuff.append(line);
            }
            result = contentBuff.toString();

        }catch(Exception e){
            e.printStackTrace();
        }
        int Index = result.indexOf("|");
        result = result.substring(1,Index);
        System.out.print(result);
        return result;

    }
}
