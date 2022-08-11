package com.bjpowernode.api.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class BidInfo implements Serializable {
    private Integer id;

    private Integer productId;

    private Integer uid;

    private BigDecimal bidMoney;

    private Date bidTime;

    private Integer bidStatus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getproductId() {
        return productId;
    }

    public void setproductId(Integer loanId) {
        this.productId = loanId;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
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

    public Integer getBidStatus() {
        return bidStatus;
    }

    public void setBidStatus(Integer bidStatus) {
        this.bidStatus = bidStatus;
    }
}