package com.bjpowernode.api.pojo;

import com.bjpowernode.api.model.User;

import java.math.BigDecimal;

/**
 * 动力节点乌兹
 * 2022-6-16
 */
public class UserAccountInfo extends User {
    private BigDecimal availableMoney;


    public BigDecimal getAvailableMoney() {
        return availableMoney;
    }

    public void setAvailableMoney(BigDecimal availableMoney) {
        this.availableMoney = availableMoney;
    }
}
