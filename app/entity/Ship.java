package entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;

/**
 * 物流
 * Created by tiffany on 15/12/14.
 */
public class Ship {
    private Long shipId;
    private Long orderId;
    private String deliveryName;
    private String deliveryTel;
    private String deliveryCity;
    private String deliveryAddress;
    private String state;
    private String expressNum;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Timestamp confirmAt;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Timestamp updateAt;
    private Long splitId;
    private String expressCode;

    public Ship() {
    }

    public Ship(Long shipId, Long orderId, String deliveryName, String deliveryTel, String deliveryCity, String deliveryAddress, String state, String expressNum, Timestamp confirmAt, Timestamp updateAt, Long splitId, String expressCode) {
        this.shipId = shipId;
        this.orderId = orderId;
        this.deliveryName = deliveryName;
        this.deliveryTel = deliveryTel;
        this.deliveryCity = deliveryCity;
        this.deliveryAddress = deliveryAddress;
        this.state = state;
        this.expressNum = expressNum;
        this.confirmAt = confirmAt;
        this.updateAt = updateAt;
        this.splitId = splitId;
        this.expressCode = expressCode;
    }

    @Override
    public String toString() {
        return "Ship{" +
                "shipId=" + shipId +
                ", orderId=" + orderId +
                ", deliveryName='" + deliveryName + '\'' +
                ", deliveryTel='" + deliveryTel + '\'' +
                ", deliveryCity='" + deliveryCity + '\'' +
                ", deliveryAddress='" + deliveryAddress + '\'' +
                ", state='" + state + '\'' +
                ", expressNum='" + expressNum + '\'' +
                ", confirmAt=" + confirmAt +
                ", updateAt=" + updateAt +
                ", splitId=" + splitId +
                ", expressCode='" + expressCode + '\'' +
                '}';
    }

    public Long getShipId() {
        return shipId;
    }

    public void setShipId(Long shipId) {
        this.shipId = shipId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getDeliveryName() {
        return deliveryName;
    }

    public void setDeliveryName(String deliveryName) {
        this.deliveryName = deliveryName;
    }

    public String getDeliveryTel() {
        return deliveryTel;
    }

    public void setDeliveryTel(String deliveryTel) {
        this.deliveryTel = deliveryTel;
    }

    public String getDeliveryCity() {
        return deliveryCity;
    }

    public void setDeliveryCity(String deliveryCity) {
        this.deliveryCity = deliveryCity;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getExpressNum() {
        return expressNum;
    }

    public void setExpressNum(String expressNum) {
        this.expressNum = expressNum;
    }

    public Timestamp getConfirmAt() {
        return confirmAt;
    }

    public void setConfirmAt(Timestamp confirmAt) {
        this.confirmAt = confirmAt;
    }

    public Timestamp getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Timestamp updateAt) {
        this.updateAt = updateAt;
    }

    public Long getSplitId() {
        return splitId;
    }

    public void setSplitId(Long splitId) {
        this.splitId = splitId;
    }

    public String getExpressCode() {
        return expressCode;
    }

    public void setExpressCode(String expressCode) {
        this.expressCode = expressCode;
    }
}
