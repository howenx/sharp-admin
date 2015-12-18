package entity;

import java.math.BigDecimal;

/**
 * Created by Sunny Wu 15/12/9.
 */
public class Carriage {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 模板编号
     */
    private String modelCode;

    /**
     * 模板名称
     */
    private String modelName;

    /**
     * 首件
     */
    private Integer firstNum;

    /**
     * 首费
     */
    private BigDecimal firstFee;

    /**
     * 续件
     */
    private Integer addNum;

    /**
     * 续费
     */
    private BigDecimal addFee;

    /**
     * 指定城市编号
     */
    private String cityCode;

    public Carriage() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModelCode() {
        return modelCode;
    }

    public void setModelCode(String modelCode) {
        this.modelCode = modelCode;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public Integer getFirstNum() {
        return firstNum;
    }

    public void setFirstNum(Integer firstNum) {
        this.firstNum = firstNum;
    }

    public BigDecimal getFirstFee() {
        return firstFee;
    }

    public void setFirstFee(BigDecimal firstFee) {
        this.firstFee = firstFee;
    }

    public Integer getAddNum() {
        return addNum;
    }

    public void setAddNum(Integer addNum) {
        this.addNum = addNum;
    }

    public BigDecimal getAddFee() {
        return addFee;
    }

    public void setAddFee(BigDecimal addFee) {
        this.addFee = addFee;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    @Override
    public String toString() {
        return "Carriage{" +
                "id=" + id +
                ", modelCode='" + modelCode + '\'' +
                ", modelName='" + modelName + '\'' +
                ", firstNum=" + firstNum +
                ", fistFee=" + firstFee +
                ", addNum=" + addNum +
                ", addFee=" + addFee +
                ", cityCode='" + cityCode + '\'' +
                '}';
    }

    public Carriage(Long id, String modelCode, String modelName, Integer firstNum, BigDecimal firstFee, Integer addNum, BigDecimal addFee, String cityCode) {
        this.id = id;
        this.modelCode = modelCode;
        this.modelName = modelName;
        this.firstNum = firstNum;
        this.firstFee = firstFee;
        this.addNum = addNum;
        this.addFee = addFee;
        this.cityCode = cityCode;
    }
}

