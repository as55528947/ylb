package com.bjpowernode.api.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 动力节点乌兹
 * 2022-4-18
 */
public class BaseInfo implements Serializable {
    /*收益率平均值*/
    private BigDecimal historyAvgRate;

    /*注册人数*/
    private Integer registerUsers;

    /*累计成交金额*/
    private BigDecimal sumBidMoney;

    public BaseInfo() {
    }

    public BaseInfo(BigDecimal historyAvgRate, Integer registerUsers, BigDecimal sumBidMoney) {
        this.historyAvgRate = historyAvgRate;
        this.registerUsers = registerUsers;
        this.sumBidMoney = sumBidMoney;
    }

    public BigDecimal getHistoryAvgRate() {
        return historyAvgRate;
    }

    public void setHistoryAvgRate(BigDecimal historyAvgRate) {
        this.historyAvgRate = historyAvgRate;
    }

    public Integer getRegisterUsers() {
        return registerUsers;
    }

    public void setRegisterUsers(Integer registerUsers) {
        this.registerUsers = registerUsers;
    }

    public BigDecimal getSumBidMoney() {
        return sumBidMoney;
    }

    public void setSumBidMoney(BigDecimal sumBidMoney) {
        this.sumBidMoney = sumBidMoney;
    }
}
