package com.bjpowernode.dataservice.service;

import com.bjpowernode.api.model.FinanceAccount;
import com.bjpowernode.api.model.User;
import com.bjpowernode.api.pojo.UserAccountInfo;
import com.bjpowernode.api.service.UserService;
import com.bjpowernode.dataservice.mapper.FinanceAccountMapper;
import com.bjpowernode.dataservice.mapper.UserMapper;
import com.bjpowernode.util.CommonUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.zip.Inflater;

/**
 * 动力节点乌兹
 * 2022-4-28
 */
@DubboService(interfaceClass = UserService.class,version = "1.0.0")
public class UserServiceImpl implements UserService{
    @Resource
    private UserMapper userMapper;

    @Resource
    private FinanceAccountMapper financeAccountMapper;

    @Value("${ylb.config.password-salt}")
    private String passwordSalt;

    /*查询当前手机号注册人信息*/
    @Override
    public User queryByPhone(String phone) {
        User user = null;
        if (CommonUtil.checkPhone(phone)){
            user = userMapper.selectByPhone(phone);
        }
        return user;
    }

    //用户注册
    @Transactional(rollbackFor = Exception.class)
    @Override
    public synchronized int userRegister(String phone, String pword) {
        int result = 0;
        if (CommonUtil.checkPhone(phone) && (pword != null && pword.length() == 32)){
            User queryUser = userMapper.selectByPhone(phone);
            if (queryUser == null){
                String newPassword = DigestUtils.md5Hex(pword + passwordSalt);
                //注册用户
                User user = new User();
                user.setPhone(phone);
                user.setLoginPassword(newPassword);
                user.setAddTime(new Date());
                userMapper.insertReturnPrimaryKey(user);

                //获取主键
                FinanceAccount account = new FinanceAccount();
                account.setUid(user.getId());
                account.setAvailableMoney(new BigDecimal("0"));
                financeAccountMapper.insertSelective(account);

                //成功result=1
                result = 1;
            }else {
                //手机号存在
                result = 2;
            }


        }
        return result;
    }

    /*登录*/
    @Transactional(rollbackFor = Exception.class)
    @Override
    public User userLogin(String phone, String pword) {
        User user = null;
        if (CommonUtil.checkPhone(phone) && (pword != null && pword.length() == 32)){
            String newPassword = DigestUtils.md5Hex(pword + passwordSalt);
            user = userMapper.selectLogin(phone,newPassword);
            if (user != null){
                user.setLastLoginTime(new Date());
                userMapper.updateByPrimaryKeySelective(user);
            }
        }
        return user;
    }

    /*更新实名认证信息*/
    @Override
    public boolean modifyRealname(String phone, String name, String idCard) {
        int rows = 0;
        if (!StringUtils.isAnyBlank(phone,name,idCard)){
            rows = userMapper.updateRealname(phone,name,idCard);
        }
        return rows > 0;
    }

    /*获取用户信息和账户余额*/
    @Override
    public UserAccountInfo queryUserAllInfo(Integer uid) {
        UserAccountInfo userAccountInfo = null;
        if (uid != null && uid >0){
            userAccountInfo = userMapper.selectUserAccountById(uid);
        }
        return userAccountInfo;
    }

    /*查询用户*/
    @Override
    public User queryById(Integer uid) {
        User user = null;
        if (uid != null && uid >0){
            user = userMapper.selectByPrimaryKey(uid);
        }
        return user;
    }
}
