package com.bjpowernode.enums;

/**
 * 动力节点乌兹
 * 2022-4-20
 */
public enum RCode {
    UNKOWN(0,"请稍后重试"),
    SUCC(1000,"请求成功"),
    REQUEST_PARAM_ERR(1001,"请求参数有误"),
    REQUEST_PRODUCT_TYPE_ERR(1002,"产品类型有误"),
    PRODUCT_OFFLINE(1003,"产品已下线"),
    PHONE_FORMAT_ERR(1004,"手机号码格式错误"),
    PHONE_EXISTS(1005,"该手机号已注册"),
    PHONE_CAN_USE(1006,"验证码在三分钟内可继续使用"),
    SMS_CODE_INVALID(1007,"验证码无效"),
    PHONE_LOGIN_PASSWORD_INVALID(1008,"手机或密码错误"),
    REALNAME_FAIL(1009,"实名认证失败"),
    REALNAME_RETRY(1010,"该用户已完成实名认证操作"),




    TOKEN_INVALID(3000,"token无效");

    /*
    * 应答码
    * 0:默认
    * 1000-2000:请求参数有误，逻辑的问题
    * 2000-3000:服务请求错误
    * 3000-4000:访问dubbo的应答结果
    */
    private int code;
    private String text;

    RCode(int c,String t){
        this.code = c;
        this.text = t;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


}
