package middle;

import akka.actor.ActorRef;
import com.aliyun.oss.model.ObjectMetadata;
import domain.VersionVo;
import modules.OSSClientProvider;
import play.Configuration;
import play.Logger;
import service.ItemService;

import javax.inject.Inject;
import javax.inject.Named;
import java.beans.XMLEncoder;
import java.io.*;

/**
 * 版本管理
 * Created by howen on 16/1/29.
 */
public class VersionMiddle {

    @Inject
    private ItemService itemService;

    @Inject
    private Configuration configuration;

    @Inject
    private OSSClientProvider oss_client;

    @Inject
    @Named("autoDeployActor")
    private ActorRef autoDeployActor;

    /**
     * 发布版本
     *
     * @param versionVo versionVo
     * @param file      file
     * @throws FileNotFoundException
     */
    public void publicRelease(VersionVo versionVo, File file) throws FileNotFoundException {


        FileInputStream is = new FileInputStream(file);

        ObjectMetadata objMetadata = new ObjectMetadata();
        objMetadata.setContentLength(file.length());

        String fileName = "HMM-" + versionVo.getReleaseName().toUpperCase() + ".";

        String productType = "android";

        if (versionVo.getProductType().equals("I")) {
            objMetadata.setContentType("application/ipa");
            fileName = fileName + "ipa";
            productType = "ios";
        } else {
            objMetadata.setContentType("application/apk");
            fileName = fileName + "apk";
        }

        versionVo.setFileName(fileName);

        versionVo.setDownloadLink(configuration.getString("image.server.url") + productType + "/" + fileName);


        oss_client.get().putObject(configuration.getString("oss.bucket"), productType + "/" + fileName, is, objMetadata);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        XMLEncoder encoder = new XMLEncoder(baos);

        encoder.writeObject(versionVo);
        encoder.flush();
        encoder.close();

        String xmlFileName = "hmm.xml";
        objMetadata.setContentLength(baos.toByteArray().length);
        objMetadata.setContentType("application/xml");

        Logger.error("版本信息:\n" + versionVo);


        oss_client.get().putObject(configuration.getString("oss.bucket"), productType + "/" + xmlFileName, new ByteArrayInputStream(baos.toByteArray()), objMetadata);


        versionVo.setUpdateReqXml(configuration.getString("image.server.url") + productType + "/" + xmlFileName);

        itemService.insertVersioning(versionVo);

        Logger.error("xml地址:\n" + configuration.getString("image.server.url") + productType + "/" + xmlFileName);

        Logger.error("上传的APP地址:\n" + configuration.getString("image.server.url") + productType + "/" + fileName);

    }

    public void apiPublicRelease(VersionVo versionVo, File file) throws FileNotFoundException {

        String fileName = "style-" + versionVo.getProductType() + "-" + versionVo.getReleaseName() + ".zip".toLowerCase();

        String productType = "style-" + versionVo.getProductType().toLowerCase();


        versionVo.setFileName(fileName);
        String uploadPath = configuration.getString("deploy.upload.path");
        versionVo.setDownloadLink(uploadPath + productType + "/" + fileName);
        File targetFolder = new File(uploadPath, productType);

        if (!targetFolder.exists()) {
            if (!targetFolder.mkdirs()) Logger.error("创建目录出错");
        }
        if (!file.renameTo(new File(targetFolder.getPath() + "/" + fileName))) Logger.error("文件重命名出错");

        try {
            if (file.exists()){
               if (!file.delete()) Logger.error("文件删除出错");
            }
            if (!file.createNewFile()) Logger.error("文件创建出错");
        } catch (IOException e) {
            e.printStackTrace();
        }
        versionVo.setUpdateReqXml("101010");
//        if (itemService.insertVersioning(versionVo)) {
//            autoDeployActor.tell(versionVo, ActorRef.noSender());
//        }
    }

}
