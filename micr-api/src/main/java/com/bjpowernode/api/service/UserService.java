package com.bjpowernode.api.service;

import com.bjpowernode.api.model.User;
import com.bjpowernode.api.pojo.UserAccountInfo;

/**
 * 动力节点乌兹
 * 2022-4-28
 */
public interface UserService {
    /*查询当前手机号注册人信息*/
    User queryByPhone(String phone);

    /*用户注册*/
    int userRegister(String phone, String pword);

    /*登录*/
    User userLogin(String phone, String pword);

    /*更新实名认证信息*/
    boolean modifyRealname(String phone, String name, String idCard);

    /*获取用户信息和账户余额*/
    UserAccountInfo queryUserAllInfo(Integer uid);

    /*查询用户*/
    User queryById(Integer uid);
}
