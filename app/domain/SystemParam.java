package domain;

import java.io.Serializable;

/**
 * Created by tiffany on 15/12/19.
 */
public class SystemParam implements Serializable{
    private Long sysId;
    private String parameterNm;     //参数名
    private String parameterVal;    //参数值
    private String parameterCode;   //参数代码

    public SystemParam() {
    }

    public SystemParam(Long sysId, String parameterNm, String parameterVal, String parameterCode) {
        this.sysId = sysId;
        this.parameterNm = parameterNm;
        this.parameterVal = parameterVal;
        this.parameterCode = parameterCode;
    }

    @Override
    public String toString() {
        return "SystemParam{" +
                "sysId=" + sysId +
                ", parameterNm='" + parameterNm + '\'' +
                ", parameterVal='" + parameterVal + '\'' +
                ", parameterCode='" + parameterCode + '\'' +
                '}';
    }

    public Long getSysId() {
        return sysId;
    }

    public void setSysId(Long sysId) {
        this.sysId = sysId;
    }

    public String getParameterNm() {
        return parameterNm;
    }

    public void setParameterNm(String parameterNm) {
        this.parameterNm = parameterNm;
    }

    public String getParameterVal() {
        return parameterVal;
    }

    public void setParameterVal(String parameterVal) {
        this.parameterVal = parameterVal;
    }

    public String getParameterCode() {
        return parameterCode;
    }

    public void setParameterCode(String parameterCode) {
        this.parameterCode = parameterCode;
    }
}
