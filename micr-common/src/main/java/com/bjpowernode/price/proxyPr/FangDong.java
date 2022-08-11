package com.bjpowernode.price.proxyPr;

import java.util.ArrayList;

/**
 * 动力节点乌兹
 * 2022-7-4
 */
public class FangDong<T> implements Rent{
    private T data;
    @Override
    public void rent() {
        System.out.println("房东要租房");
        ArrayList arrayList = new ArrayList();
        System.out.println();
    }
}
