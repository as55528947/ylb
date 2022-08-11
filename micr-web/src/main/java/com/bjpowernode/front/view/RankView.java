package com.bjpowernode.front.view;

/**
 * 动力节点乌兹
 * 2022-4-21
 * 存储投资排行榜的数据
 */
public class RankView {
    private String phone;
    private Double money;

    public RankView(String phone, Double money) {
        this.phone = phone;
        this.money = money;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }
}
