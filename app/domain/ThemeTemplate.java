package domain;

import java.io.Serializable;

/**
 * Created by tiffany on 16/1/8.
 */
public class ThemeTemplate implements Serializable {
    private Long id;
    private String url;
    private String navigatorHtml;
    private String contentHtml;

    public ThemeTemplate() {
    }

    public ThemeTemplate(Long id, String url, String navigatorHtml, String contentHtml) {
        this.id = id;
        this.url = url;
        this.navigatorHtml = navigatorHtml;
        this.contentHtml = contentHtml;
    }

    @Override
    public String toString() {
        return "ThemeTemplate{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", navigatorHtml='" + navigatorHtml + '\'' +
                ", contentHtml='" + contentHtml + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNavigatorHtml() {
        return navigatorHtml;
    }

    public void setNavigatorHtml(String navigatorHtml) {
        this.navigatorHtml = navigatorHtml;
    }

    public String getContentHtml() {
        return contentHtml;
    }

    public void setContentHtml(String contentHtml) {
        this.contentHtml = contentHtml;
    }
}
