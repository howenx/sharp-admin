package domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import play.data.validation.Constraints;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Random;

/**
 * Created by Sunny Wu on 16/1/27.
 * kakao china.
 * dmin user
 */
public class AdminUser implements Serializable {

    private Long userId;            //用户id
    @Constraints.Required
    private String enNm;            //英文名
    private String chNm;            //中文名
    @Constraints.Required
    private String email;           //邮箱
    @Constraints.Required
    private String userType;        //用户角色(OPERATE 运营人员   FINANCE 财务人员	SERVICE 客服人员 	ADMIN 管理人员  SYSTEM 系统管理员)
    private String passwd;          //密码
    private String regIp;           //注册ip
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp regDt;        //注册时间
    private String activeYN;        //是否激活
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp alterDt;      //修改时间
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp lastLoginDt;  //最后登录时间
    private String lastLoginIp;     //最后登录ip
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp lastPwdChgDt; //最后密码修改时间
    private String status;          //状态(Y 正常  N 阻止)

    public AdminUser() {
    }

    private static final String latterCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final String numCase = "1234567890";
    private static final String specCase = "!@#$%&*";

    //注册时生成的8位的随机密码
    public static String CreateCode(int length){
        Random random = new Random();
        String code = "";
        while (code.length()!=length) {
            if(code.length()==0){
                int spot = random.nextInt (51);
                code += latterCase.charAt(spot);
            }
            int rPick = random.nextInt(6);
            if (rPick == 1 || rPick == 0) {
                int spot = random.nextInt (51);
                code += latterCase.charAt(spot);
            } else if (rPick == 2 || rPick ==3) {
                int spot = random.nextInt (9);
                code += numCase.charAt (spot);
            } else if (rPick == 4 || rPick ==5) {
                int spot = random.nextInt (6);
                code += specCase.charAt (spot);
            }
        }
        return code;
    }

    public AdminUser(Long userId, String enNm, String chNm, String email, String userType, String passwd, String regIp, Timestamp regDt, String activeYN, Timestamp alterDt, Timestamp lastLoginDt, String lastLoginIp, Timestamp lastPwdChgDt, String status) {
        this.userId = userId;
        this.enNm = enNm;
        this.chNm = chNm;
        this.email = email;
        this.userType = userType;
        this.passwd = passwd;
        this.regIp = regIp;
        this.regDt = regDt;
        this.activeYN = activeYN;
        this.alterDt = alterDt;
        this.lastLoginDt = lastLoginDt;
        this.lastLoginIp = lastLoginIp;
        this.lastPwdChgDt = lastPwdChgDt;
        this.status = status;
    }

    @Override
    public String toString() {
        return "AdminUser{" +
                "userId=" + userId +
                ", enNm='" + enNm + '\'' +
                ", chNm='" + chNm + '\'' +
                ", email='" + email + '\'' +
                ", userType='" + userType + '\'' +
                ", passwd='" + passwd + '\'' +
                ", regIp='" + regIp + '\'' +
                ", regDt=" + regDt +
                ", activeYN='" + activeYN + '\'' +
                ", alterDt=" + alterDt +
                ", lastLoginDt=" + lastLoginDt +
                ", lastLoginIp='" + lastLoginIp + '\'' +
                ", lastPwdChgDt=" + lastPwdChgDt +
                ", status='" + status + '\'' +
                '}';
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEnNm() {
        return enNm;
    }

    public void setEnNm(String enNm) {
        this.enNm = enNm;
    }

    public String getChNm() {
        return chNm;
    }

    public void setChNm(String chNm) {
        this.chNm = chNm;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
