package middle;

import akka.actor.ActorRef;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.cdn.model.v20141111.RefreshObjectCachesRequest;
import com.aliyuncs.cdn.model.v20141111.RefreshObjectCachesResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.FormatType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.google.common.base.Throwables;
import domain.VersionVo;
import modules.OSSClientProvider;
import play.Configuration;
import play.Logger;
import service.ItemService;

import javax.inject.Inject;
import javax.inject.Named;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.Timer;
import java.util.TimerTask;

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
     * android与ios 发布版本
     *
     * @param versionVo versionVo
     * @param file      file
     * @throws FileNotFoundException
     */
    public void publicRelease(VersionVo versionVo, File file) throws FileNotFoundException {

        String productType = versionVo.getProductType();

        FileInputStream is = new FileInputStream(file);

        ObjectMetadata objMetadata = new ObjectMetadata();
        objMetadata.setContentLength(file.length());

        String fileName = "HMM-" + versionVo.getReleaseName().toUpperCase() + ".";


        if (versionVo.getProductType().equals("ios")) {
            objMetadata.setContentType("application/ipa");
            fileName = fileName + "ipa";
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

        itemService.updateVersioning(versionVo);

        itemService.insertVersioning(versionVo);

        Logger.error("xml地址:\n" + configuration.getString("image.server.url") + productType + "/" + xmlFileName);

        Logger.error("上传的APP地址:\n" + configuration.getString("image.server.url") + productType + "/" + fileName);

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                refreshCdn();
            }
        };

        Timer timer = new Timer();
        timer.schedule(timerTask, 60000);
    }

    /**
     * 普通的Java zip包发布
     *
     * @param versionVo versionVo
     * @param file      file
     * @throws FileNotFoundException
     */
    public void apiPublicRelease(VersionVo versionVo, File file) throws FileNotFoundException {

        if (versionVo.getProductType().equals("android") || versionVo.getProductType().equals("ios")) {
            publicRelease(versionVo, file);
        } else {
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
                if (file.exists()) {
                    if (!file.delete()) Logger.error("文件删除出错");
                }
                if (!file.createNewFile()) Logger.error("文件创建出错");
            } catch (IOException e) {
                Logger.error(Throwables.getStackTraceAsString(e));
            }
            versionVo.setUpdateReqXml("101010");
            if (itemService.insertVersioning(versionVo)) {
                autoDeployActor.tell(versionVo, ActorRef.noSender());
            }
        }
    }

    /**
     * 刷新CDN
     */
    private void refreshCdn() {
        try {
            String endpoint = configuration.getString("cdn.endpoint");
            String key = configuration.getString("oss.access_key");
            String secret = configuration.getString("oss.access_secret");

            DefaultProfile.addEndpoint(endpoint, endpoint, "Cdn", "cdn.aliyuncs.com");
            IClientProfile profile = DefaultProfile.getProfile(endpoint, key, secret);
            IAcsClient client = new DefaultAcsClient(profile);

            RefreshObjectCachesRequest describeCdnServiceRequest = new RefreshObjectCachesRequest();

            describeCdnServiceRequest.setAcceptFormat(FormatType.JSON); //指定api返回格式
            describeCdnServiceRequest.setRegionId(endpoint);
            describeCdnServiceRequest.setObjectPath(configuration.getString("cdn.directory"));
            describeCdnServiceRequest.setObjectType("Directory");

            RefreshObjectCachesResponse describeCdnServiceResponse = client.getAcsResponse(describeCdnServiceRequest);

            Logger.info("刷新cdn:" + describeCdnServiceResponse.getRefreshTaskId());
        } catch (ClientException ex) {
            Logger.error("刷新CDN出错:" + Throwables.getStackTraceAsString(ex));
        }
    }

}
