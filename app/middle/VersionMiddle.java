package middle;

import com.aliyun.oss.model.ObjectMetadata;
import entity.VersionVo;
import modules.OSSClientProvider;
import play.Configuration;
import play.Logger;
import service.ItemService;
import java.beans.XMLEncoder;
import java.io.*;

/**
 * 版本管理
 * Created by howen on 16/1/29.
 */
public class VersionMiddle {

    private  ItemService itemService;

    private Configuration configuration;

    private OSSClientProvider oss_client;

    public VersionMiddle(ItemService itemService,Configuration configuration,OSSClientProvider oss_client) {
        this.itemService = itemService;
        this.configuration = configuration;
        this.oss_client = oss_client;
    }

    public void publicRelease(VersionVo versionVo,File file) throws FileNotFoundException {

        FileInputStream is =  new FileInputStream(file);

            ObjectMetadata objMetadata = new ObjectMetadata();
            objMetadata.setContentLength(file.length());

            String fileName = "HMM-"+versionVo.getReleaseNumber().toUpperCase()+".";

            String productType="android";

            if (versionVo.getProductType().equals("I")){
                objMetadata.setContentType("application/ipa");
                fileName = fileName+"ipa";
                productType="ios";
            }else{
                objMetadata.setContentType("application/apk");
                fileName = fileName+"apk";
            }

            versionVo.setFileName(fileName);

            versionVo.setDownloadLink(configuration.getString("image.server.url")+productType+"/"+fileName);


            oss_client.get().putObject(configuration.getString("oss.bucket"), productType+"/"+fileName, is, objMetadata);

            ByteArrayOutputStream baos=new ByteArrayOutputStream();

            XMLEncoder encoder = new XMLEncoder(baos);

            encoder.writeObject(versionVo);
            encoder.flush();
            encoder.close();

            String xmlFileName = "hmm.xml";
            objMetadata.setContentLength(baos.toByteArray().length);
            objMetadata.setContentType("application/xml");

            Logger.error("版本信息:\n"+versionVo);

            oss_client.get().putObject(configuration.getString("oss.bucket"), productType+"/"+xmlFileName, new ByteArrayInputStream(baos.toByteArray()), objMetadata);


            versionVo.setUpdateReqXml(configuration.getString("image.server.url")+productType+"/"+xmlFileName);

            itemService.updateVersioning(versionVo);

            itemService.insertVersioning(versionVo);

            Logger.error("xml地址:\n"+configuration.getString("image.server.url")+productType+"/"+xmlFileName);

            Logger.error("上传的APP地址:\n"+configuration.getString("image.server.url")+productType+"/"+fileName);

    }

    public void apiPublicRelease(VersionVo versionVo,File file) throws FileNotFoundException {

        String fileName = "style-" +versionVo.getProductType() + "-"+ versionVo.getReleaseNumber().toUpperCase()+".zip";

        String productType="style-id";
        if (versionVo.getProductType().equals("imgProcess")){
            productType="style-imgProcess";
        } else if(versionVo.getProductType().equals("promotion")){
            productType="style-promotion";
        } else if(versionVo.getProductType().equals("service")){
            productType="style-service";
        } else if(versionVo.getProductType().equals("shopping")){
            productType="style-shopping";
        } else if(versionVo.getProductType().equals("web")){
            productType="style-web";
        }

        versionVo.setFileName(fileName);
        versionVo.setDownloadLink(configuration.getString("deploy.upload.path") + productType+"/"+fileName);
        File targetFolder = new File(configuration.getString("deploy.upload.path"),productType);

        if(!targetFolder.exists()){
            targetFolder.mkdirs();
        }
        file.renameTo(new File(targetFolder.getAbsoluteFile() +"/"+ fileName));
        try {
            file.createNewFile();
        }catch (IOException e){
            e.printStackTrace();
        }

        versionVo.setUpdateReqXml("101010");
        itemService.updateVersioning(versionVo);
        itemService.insertVersioning(versionVo);
    }

}
