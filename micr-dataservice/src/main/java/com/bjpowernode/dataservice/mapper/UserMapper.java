package com.bjpowernode.dataservice.mapper;

import com.bjpowernode.api.model.User;
import com.bjpowernode.api.pojo.UserAccountInfo;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    /*查询注册人数*/
    int selectCountUser();

    /*查询当前手机号注册人信息*/
    User selectByPhone(@Param("phone") String phone);

    /*新增用户  并获取其自增主键id值*/
    int insertReturnPrimaryKey(User user);

    /*以下由逆向工程生成*/
    int deleteByPrimaryKey(Integer id);

    /*登录*/
    User selectLogin(@Param("phone") String phone, @Param("loginPassword") String newPassword);

    /*更新实名认证信息*/
    int updateRealname(@Param("phone") String phone, @Param("name") String name, @Param("idCard") String idCard);

    /*获取用户信息和账户余额*/
    UserAccountInfo selectUserAccountById(@Param("uid") Integer uid);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

}