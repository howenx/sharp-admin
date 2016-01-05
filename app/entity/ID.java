package entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;
import java.sql.Date;

/**
 * Created by Sunny Wu on 15/12/30.
 */
public class ID {

    private Integer userId;     //用户ID
    private String cardType;    //证件类型(默认身份证)
    private String cardNum;     //证件号码
    private String cardImg;     //证件图片(A:正面 B:反面)
    private String regIp;       //注册IP
    private Timestamp regDt;    //注册时间
    private String activeYN;    //是否激活(默认:N)
    private String realYN;      //是否实名认证(默认:N)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Timestamp alterDt;  //修改时间
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Timestamp lastloginDt;//最后登录时间
    private String lastloginIp;   //最后登录IP
    private String nickname;      //用户名
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Timestamp lastpwdchgDt;//最后修改密码时间
    private String desc;           //备注
    private String status;         //状态(Y:正常,N:阻止)
    private String passwd;         //密码
    private String email;          //邮箱
    private String phoneNum;       //电话号码
    private String gender;         //性别
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone = "GMT+8")
    private Date birthday;         //生日
    private String photoUrl;       //头像
    private String realName;       //真实姓名

    public ID() {
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public String getCardImg() {
        return cardImg;
    }

    public void setCardImg(String cardImg) {
        this.cardImg = cardImg;
    }

    public String getRegIp() {
        return regIp;
    }

    public void setRegIp(String regIp) {
        this.regIp = regIp;
    }

    public Timestamp getRegDt() {
        return regDt;
    }

    public void setRegDt(Timestamp regDt) {
        this.regDt = regDt;
    }

    public String getActiveYN() {
        return activeYN;
    }

    public void setActiveYN(String activeYN) {
        this.activeYN = activeYN;
    }

    public String getRealYN() {
        return realYN;
    }

    public void setRealYN(String realYN) {
        this.realYN = realYN;
    }

    public Timestamp getAlterDt() {
        return alterDt;
    }

    public void setAlterDt(Timestamp alterDt) {
        this.alterDt = alterDt;
    }

    public Timestamp getLastloginDt() {
        return lastloginDt;
    }

    public void setLastloginDt(Timestamp lastloginDt) {
        this.lastloginDt = lastloginDt;
    }

    public String getLastloginIp() {
        return lastloginIp;
    }

    public void setLastloginIp(String lastloginIp) {
        this.lastloginIp = lastloginIp;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Timestamp getLastpwdchgDt() {
        return lastpwdchgDt;
    }

    public void setLastpwdchgDt(Timestamp lastpwdchgDt) {
        this.lastpwdchgDt = lastpwdchgDt;
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

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    @Override
    public String toString() {
        return "ID{" +
                "userId=" + userId +
                ", cardType='" + cardType + '\'' +
                ", cardNum='" + cardNum + '\'' +
                ", cardImg='" + cardImg + '\'' +
                ", regIp='" + regIp + '\'' +
                ", regDt=" + regDt +
                ", activeYN='" + activeYN + '\'' +
                ", realYN='" + realYN + '\'' +
                ", alterDt=" + alterDt +
                ", lastloginDt=" + lastloginDt +
                ", lastloginIp='" + lastloginIp + '\'' +
                ", nickname='" + nickname + '\'' +
                ", lastpwdchgDt=" + lastpwdchgDt +
                ", desc='" + desc + '\'' +
                ", status='" + status + '\'' +
                ", passwd='" + passwd + '\'' +
                ", email='" + email + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", gender='" + gender + '\'' +
                ", birthday=" + birthday +
                ", photoUrl='" + photoUrl + '\'' +
                ", realName='" + realName + '\'' +
                '}';
    }

    public ID(Integer userId, String cardType, String cardNum, String cardImg, String regIp, Timestamp regDt, String activeYN, String realYN, Timestamp alterDt, Timestamp lastloginDt, String lastloginIp, String nickname, Timestamp lastpwdchgDt, String desc, String status, String passwd, String email, String phoneNum, String gender, Date birthday, String photoUrl, String realName) {
        this.userId = userId;
        this.cardType = cardType;
        this.cardNum = cardNum;
        this.cardImg = cardImg;
        this.regIp = regIp;
        this.regDt = regDt;
        this.activeYN = activeYN;
        this.realYN = realYN;
        this.alterDt = alterDt;
        this.lastloginDt = lastloginDt;
        this.lastloginIp = lastloginIp;
        this.nickname = nickname;
        this.lastpwdchgDt = lastpwdchgDt;
        this.desc = desc;
        this.status = status;
        this.passwd = passwd;
        this.email = email;
        this.phoneNum = phoneNum;
        this.gender = gender;
        this.birthday = birthday;
        this.photoUrl = photoUrl;
        this.realName = realName;
    }
}
