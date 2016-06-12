package domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by tiffany on 16/6/8.
 */
public class Image implements Serializable {
    private Long id;                //主键
    private String ossUrl;          //kakao China 图片服务器  图片Url
    private String ctripUrl;        //携程图片服务器  图片Url
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp createAt;     //创建时间

    public Image() {
    }

    public Image(Long id, String ossUrl, String ctripUrl, Timestamp createAt) {
        this.id = id;
        this.ossUrl = ossUrl;
        this.ctripUrl = ctripUrl;
        this.createAt = createAt;
    }

    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", ossUrl='" + ossUrl + '\'' +
                ", ctripUrl='" + ctripUrl + '\'' +
                ", createAt=" + createAt +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOssUrl() {
        return ossUrl;
    }

    public void setOssUrl(String ossUrl) {
        this.ossUrl = ossUrl;
    }

    public String getCtripUrl() {
        return ctripUrl;
    }

    public void setCtripUrl(String ctripUrl) {
        this.ctripUrl = ctripUrl;
    }

    public Timestamp getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }
}
