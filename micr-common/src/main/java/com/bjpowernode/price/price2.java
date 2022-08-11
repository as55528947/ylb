package com.bjpowernode.price;

/**
 * 动力节点乌兹
 * 2022-7-2
 */
public interface price2 {
    static void zz() {
        System.out.println("xx");
    }

    default String queryName() {

        return "";
    }

    int queryAddress(Integer phone);
}
