package domain;

import play.data.validation.Constraints;

import java.io.Serializable;

/**
 * 给用户发送短信的实体
 * Created by Sunny Wu on 16/6/7.
 * kakao china.
 */
public class SMSVo implements Serializable {

    @Constraints.Required
    private String phone_num;    //手机号码
    @Constraints.Required
    private String send_content; //发送内容
    @Constraints.Required
    private String template_id;  //模板id

    public SMSVo() {

    }

    @Override
    public String toString() {
        return "SMSVo{" +
                "phone_num='" + phone_num + '\'' +
                ", send_content='" + send_content + '\'' +
                ", template_id='" + template_id + '\'' +
                '}';
    }

    public String getPhone_num() {
        return phone_num;
    }

    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num;
    }

    public String getSend_content() {
        return send_content;
    }

    public void setSend_content(String send_content) {
        this.send_content = send_content;
    }

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    public SMSVo(String phone_num, String send_content, String template_id) {

        this.phone_num = phone_num;
        this.send_content = send_content;
        this.template_id = template_id;
    }
}
