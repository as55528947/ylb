package com.bjpowernode.api.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 动力节点乌兹
 * 2022-8-10
 */
public class UserCenterBidPage implements Serializable {
    final static Integer id = 0;
    private String productName;
    private BigDecimal bidMoney;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date bidTime;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getBidMoney() {
        return bidMoney;
    }

    public void setBidMoney(BigDecimal bidMoney) {
        this.bidMoney = bidMoney;
    }

    public Date getBidTime() {
        return bidTime;
    }

    public void setBidTime(Date bidTime) {
        this.bidTime = bidTime;
    }
}
