package entity;

/**
 * 版本
 * Created by howen on 16/1/29.
 */
public class VersionVo {

    private Long    id;
    private String  releaseNumber;
    private String  productType;
    private String  downloadLink;
    private Long    adminUserId;
    private String  releaseDesc;
    private String  releaseAt;
    private String  fileName;
    private String  updateReqXml;
    private Boolean currentVersion;
    private String  appStoreDownloadLink;
    private String  adminUserNm;

    public VersionVo() {
    }

    public VersionVo(Long id, String releaseNumber, String productType, String downloadLink, Long adminUserId, String releaseDesc, String releaseAt, String fileName, String updateReqXml, Boolean currentVersion, String appStoreDownloadLink, String adminUserNm) {
        this.id = id;
        this.releaseNumber = releaseNumber;
        this.productType = productType;
        this.downloadLink = downloadLink;
        this.adminUserId = adminUserId;
        this.releaseDesc = releaseDesc;
        this.releaseAt = releaseAt;
        this.fileName = fileName;
        this.updateReqXml = updateReqXml;
        this.currentVersion = currentVersion;
        this.appStoreDownloadLink = appStoreDownloadLink;
        this.adminUserNm = adminUserNm;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReleaseNumber() {
        return releaseNumber;
    }

    public void setReleaseNumber(String releaseNumber) {
        this.releaseNumber = releaseNumber;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getDownloadLink() {
        return downloadLink;
    }

    public void setDownloadLink(String downloadLink) {
        this.downloadLink = downloadLink;
    }

    public Long getAdminUserId() {
        return adminUserId;
    }

    public void setAdminUserId(Long adminUserId) {
        this.adminUserId = adminUserId;
    }

    public String getReleaseDesc() {
        return releaseDesc;
    }

    public void setReleaseDesc(String releaseDesc) {
        this.releaseDesc = releaseDesc;
    }

    public String getReleaseAt() {
        return releaseAt;
    }

    public void setReleaseAt(String releaseAt) {
        this.releaseAt = releaseAt;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUpdateReqXml() {
        return updateReqXml;
    }

    public void setUpdateReqXml(String updateReqXml) {
        this.updateReqXml = updateReqXml;
    }

    public Boolean getCurrentVersion() {
        return currentVersion;
    }

    public void setCurrentVersion(Boolean currentVersion) {
        this.currentVersion = currentVersion;
    }

    public String getAppStoreDownloadLink() {
        return appStoreDownloadLink;
    }

    public void setAppStoreDownloadLink(String appStoreDownloadLink) {
        this.appStoreDownloadLink = appStoreDownloadLink;
    }

    public String getAdminUserNm() {
        return adminUserNm;
    }

    public void setAdminUserNm(String adminUserNm) {
        this.adminUserNm = adminUserNm;
    }

    @Override
    public String toString() {
        return "VersionVo{" +
                "id=" + id +
                ", releaseNumber='" + releaseNumber + '\'' +
                ", productType='" + productType + '\'' +
                ", downloadLink='" + downloadLink + '\'' +
                ", adminUserId=" + adminUserId +
                ", releaseDesc='" + releaseDesc + '\'' +
                ", releaseAt='" + releaseAt + '\'' +
                ", fileName='" + fileName + '\'' +
                ", updateReqXml='" + updateReqXml + '\'' +
                ", currentVersion=" + currentVersion +
                ", appStoreDownloadLink='" + appStoreDownloadLink + '\'' +
                ", adminUserNm='" + adminUserNm + '\'' +
                '}';
    }
}
