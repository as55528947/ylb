package com.bjpowernode.api.service;

import com.bjpowernode.api.model.RechargeRecord;

import java.util.List;

/**
 * 动力节点乌兹
 * 2022-6-16
 */
public interface RechargeService {
    /*根据userId查询他的充值记录*/
    List<RechargeRecord> queryByUid(Integer uid,Integer pageNo,Integer pageSize);

    int addRechargeRecord(RechargeRecord record);

    /*处理后续充值*/
    int handleKQNotify(String orderId, String payAmount, String payResult);
}
