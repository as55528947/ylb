package com.bjpowernode.api.service;

/**
 * 动力节点乌兹
 * 2022-6-21
 */
public interface IncomeService {
    /*收益计划  定时任务控制*/
    void generateIncomePlan();

    /*收益返还   定时任务控制*/
    void generateIncomeBack();
}
