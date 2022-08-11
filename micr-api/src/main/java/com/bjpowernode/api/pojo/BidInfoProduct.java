package com.bjpowernode.api.pojo;

import com.bjpowernode.api.model.BidInfo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 动力节点乌兹
 * 2022-4-27
 */
public class BidInfoProduct implements Serializable {
    private Integer id;
    private String phone;
    private BigDecimal bidMoney;
    private String bidTime;

    public BidInfoProduct() {
    }

    public BidInfoProduct(Integer id, String phone, BigDecimal bidMoney, String bidTime) {
        this.id = id;
        this.phone = phone;
        this.bidMoney = bidMoney;
        this.bidTime = bidTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public BigDecimal getBidMoney() {
        return bidMoney;
    }

    public void setBidMoney(BigDecimal bidMoney) {
        this.bidMoney = bidMoney;
    }

    public String getBidTime() {
        return bidTime;
    }

    public void setBidTime(String bidTime) {
        this.bidTime = bidTime;
    }
}
