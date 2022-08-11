package com.bjpowernode.constants;

/**
 * 动力节点乌兹
 * 2022-4-21
 */
public class RedisKey {
    /*投资排行榜*/
    public static final String KEY_INVEST_RANK = "INVEST:RANK";

    /*注册时  短信验证码  SMS:REG: 手机号*/
    public static final String KEY_SMS_CODE_REG = "SMS:REG:";

    /*注册时  短信验证码  SMS:REG: 手机号*/
    public static final String KEY_SMS_CODE_LOGIN = "SMS:LOGIN:";

    /*订单号  自动增长id*/
    public static final String KEY_RECHARGE_ORDERID = "RECHARGE:ORDERID:SEQ";

    /*orderId*/
    public static final String KEY_ORDERID_SET = "RECHARGE:ORDERID:SET";
}
