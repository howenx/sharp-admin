package middle;

import com.aliyun.oss.model.ObjectMetadata;
import entity.VersionVo;
import modules.OSSClientProvider;
import play.Configuration;
import play.Logger;
import service.ItemService;

import javax.inject.Inject;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 版本管理
 * Created by howen on 16/1/29.
 */
public class VersionMiddle {

    public static ItemService itemService;

    @Inject
    private Configuration configuration;

    @Inject
    private OSSClientProvider oss_client;

    public VersionMiddle(ItemService itemService) {
        this.itemService = itemService;
    }

    public void publicRelease(VersionVo versionVo,File file){

        FileInputStream is = null;
        try {
            is = new FileInputStream(file);

            Pattern p = Pattern.compile("^(.*)(\\.)(.{1,8})$");
            Matcher m = p.matcher(file.getName());

            ObjectMetadata objMetadata = new ObjectMetadata();
            objMetadata.setContentLength(file.length());
            objMetadata.setContentType(m.group());


            String fileName = "HMM-"+versionVo.getReleaseNumber().toUpperCase()+"."+m.group();

            versionVo.setFileName(fileName);

            itemService.updateVersioning();

            itemService.insertVersioning(versionVo);

            Logger.error("文件后缀"+m.group());

            oss_client.get().putObject(configuration.getString("oss.bucket"), versionVo.getProductType()+"/"+fileName, is, objMetadata);

            ByteArrayOutputStream baos=new ByteArrayOutputStream();

            XMLEncoder encoder = new XMLEncoder(baos);

            encoder.writeObject(versionVo);
            encoder.flush();
            encoder.close();

            String xmlFileName = "hmm.xml";
            objMetadata.setContentLength(baos.toByteArray().length);
            objMetadata.setContentType("application/xml");

            Logger.error("版本信息:\n"+versionVo);

            oss_client.get().putObject(configuration.getString("oss.bucket"), versionVo.getProductType()+"/"+xmlFileName, new ByteArrayInputStream(baos.toByteArray()), objMetadata);

            Logger.error("xml地址:\n"+configuration.getString("oss.prefix")+versionVo.getProductType()+"/"+xmlFileName);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
