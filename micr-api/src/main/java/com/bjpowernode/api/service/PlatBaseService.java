package com.bjpowernode.api.service;

import com.bjpowernode.api.pojo.BaseInfo;

/**
 * 动力节点乌兹
 * 2022-4-18
 */

public interface PlatBaseService {
    /*首页  计算利率  注册人数  累计成交金额*/
    BaseInfo queryPlatBaseInfo();
}
