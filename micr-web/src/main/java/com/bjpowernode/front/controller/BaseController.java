package com.bjpowernode.front.controller;

import com.bjpowernode.api.service.*;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;

/**
 * 动力节点乌兹
 * 2022-4-19
 */
public class BaseController {
    //声明公共的方法，属性等
    //Redis
    @Resource
    protected StringRedisTemplate stringRedisTemplate;

    //基本数据服务
    @DubboReference(interfaceClass = PlatBaseService.class,version = "1.0.0")
    protected PlatBaseService platBaseService;

    //产品服务
    @DubboReference(interfaceClass = ProductService.class,version = "1.0.0")
    protected ProductService productService;

    //投资服务
    @DubboReference(interfaceClass = InvestService.class,version = "1.0.0")
    protected InvestService investService;

    //用户服务
    @DubboReference(interfaceClass = UserService.class,version = "1.0.0")
    protected UserService userService;

    //充值服务
    @DubboReference(interfaceClass = RechargeService.class,version = "1.0.0")
    protected RechargeService rechargeService;

}
