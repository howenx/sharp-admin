package entity;

import java.io.Serializable;

/**
 * Created by tiffany on 16/1/8.
 */
public class ThemeTemplate implements Serializable {
    private Long id;
    private String html;
    private String url;

    public ThemeTemplate() {
    }

    public ThemeTemplate(Long id, String html, String url) {
        this.id = id;
        this.html = html;
        this.url = url;
    }

    @Override
    public String toString() {
        return "ThemeTemplate{" +
                "id=" + id +
                ", html='" + html + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
