package com.bjpowernode.task;

import com.bjpowernode.api.service.IncomeService;
import com.bjpowernode.util.HttpClientUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 动力节点乌兹
 * 2022-6-21
 */
@Component("taskManager")
public class TaskManager {

    @DubboReference(interfaceClass = IncomeService.class,version = "1.0.0")
    private IncomeService incomeService;


    /**
     * 定义方法，表示要执行的定时任务功能
     * 方法定义的要求：
     * 1.public 公共方法
     * 2.方法没有参数
     * 3.方法没有返回值
     * 4.方法的上面加入@Scheduled，设置cron属性，指定时间
     */
   /* @Scheduled(cron = "0/5 34 09 * * ?")
    public void testCorn(){
        System.out.println("定时任务:" + new Date());
    }*/

   /*生成收益计划*/
    @Scheduled(cron = "0 0 1 * * ? ")
    public void invokeGenerateIncomePlan(){
        incomeService.generateIncomePlan();
    }

    /*返还收益*/
    @Scheduled(cron = "0 0 2 * * ? ")
    public void invokeGenerateIncomeBack(){
        incomeService.generateIncomeBack();
    }

    /*补单接口*/
    @Scheduled(cron = "0 0/20 * * * ?")
    public void invokeKuaiQianQuery(){
        String url = "http://localhost:9000/pay/kq/rece/query";
        try {
            HttpClientUtils.doGet(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
