package com.bjpowernode.dataservice.service;

import com.bjpowernode.dataservice.mapper.BidInfoMapper;
import com.bjpowernode.api.pojo.BaseInfo;
import com.bjpowernode.api.service.PlatBaseService;
import com.bjpowernode.dataservice.mapper.ProductInfoMapper;
import com.bjpowernode.dataservice.mapper.UserMapper;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * 动力节点乌兹
 * 2022-4-18
 */
@DubboService(interfaceClass = PlatBaseService.class, version = "1.0.0")
public class PlatBaseServiceImpl implements PlatBaseService {

    @Resource
    BidInfoMapper bidInfoMapper;

    @Resource
    UserMapper userMapper;

    @Resource
    ProductInfoMapper productInfoMapper;

    /*平台基本信息*/
    @Override
    public BaseInfo queryPlatBaseInfo() {

        /*累计投资金额*/
        BigDecimal sumBidMoney = bidInfoMapper.selectSumBidMoney();

        /*注册人数*/
        int registerUser = userMapper.selectCountUser();

        /*平均利率*/
        BigDecimal avgRate = productInfoMapper.selectAvgRate();

        BaseInfo baseInfo = new BaseInfo(avgRate, registerUser, sumBidMoney);

        return baseInfo;
    }
}
