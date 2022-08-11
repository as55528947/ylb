package com.bjpowernode.front.service;

/**
 * 动力节点乌兹
 * 2022-5-2
 */
public interface SmsService {
    /**
     * @param phone   手机号
     * @return  true:发送成功,false:发送失败
     */
    boolean sendSms(String phone);


    boolean checkSmsCode(String phone,String code);
}
