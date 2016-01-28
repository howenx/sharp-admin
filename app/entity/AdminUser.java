package entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by Sunny Wu on 16/1/27.
 * kakao china.
 * dmin user
 */
public class AdminUser implements Serializable {

    private Long userId;            //用户id
    private String nickname;        //用户名
    private String email;           //邮箱
    private String passwd;          //密码
    private String gender;          //性别
    private String phoneNum;        //电话号码
    private String realName;        //真实姓名
    private String regIp;           //注册ip
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Timestamp regDT;        //注册时间
    private String activeYN;        //是否激活
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Timestamp alterDt;      //修改时间
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Timestamp lastLoginDt;  //最后登录时间
    private String lastLoginIp;     //最后登录ip
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Timestamp lastPwdChgDt; //最后密码修改时间
    private String desc;            //备注
    private String status;          //状态(Y 正常  N 阻止)

    public AdminUser() {
    }

    @Override
    public String toString() {
        return "AdminUser{" +
                "userId=" + userId +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", passwd='" + passwd + '\'' +
                ", gender='" + gender + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", realName='" + realName + '\'' +
                ", regIp='" + regIp + '\'' +
                ", regDT=" + regDT +
                ", activeYN='" + activeYN + '\'' +
                ", alterDt=" + alterDt +
                ", lastLoginDt=" + lastLoginDt +
                ", lastLoginIp='" + lastLoginIp + '\'' +
                ", lastPwdChgDt=" + lastPwdChgDt +
                ", desc='" + desc + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getRegIp() {
        return regIp;
    }

    public void setRegIp(String regIp) {
        this.regIp = regIp;
    }

    public Timestamp getRegDT() {
        return regDT;
    }

    public void setRegDT(Timestamp regDT) {
        this.regDT = regDT;
    }

    public String getActiveYN() {
        return activeYN;
    }

    public void setActiveYN(String activeYN) {
        this.activeYN = activeYN;
    }

    public Timestamp getAlterDt() {
        return alterDt;
    }

    public void setAlterDt(Timestamp alterDt) {
        this.alterDt = alterDt;
    }

    public Timestamp getLastLoginDt() {
        return lastLoginDt;
    }

    public void setLastLoginDt(Timestamp lastLoginDt) {
        this.lastLoginDt = lastLoginDt;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public Timestamp getLastPwdChgDt() {
        return lastPwdChgDt;
    }

    public void setLastPwdChgDt(Timestamp lastPwdChgDt) {
        this.lastPwdChgDt = lastPwdChgDt;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public AdminUser(Long userId, String nickname, String email, String passwd, String gender, String phoneNum, String realName, String regIp, Timestamp regDT, String activeYN, Timestamp alterDt, Timestamp lastLoginDt, String lastLoginIp, Timestamp lastPwdChgDt, String desc, String status) {

        this.userId = userId;
        this.nickname = nickname;
        this.email = email;
        this.passwd = passwd;
        this.gender = gender;
        this.phoneNum = phoneNum;
        this.realName = realName;
        this.regIp = regIp;
        this.regDT = regDT;
        this.activeYN = activeYN;
        this.alterDt = alterDt;
        this.lastLoginDt = lastLoginDt;
        this.lastLoginIp = lastLoginIp;
        this.lastPwdChgDt = lastPwdChgDt;
        this.desc = desc;
        this.status = status;
    }
}
