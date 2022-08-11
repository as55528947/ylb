package com.bjpowernode.api.service;

import com.bjpowernode.api.pojo.BidInfoProduct;
import com.bjpowernode.api.pojo.UserCenterBidPage;

import java.math.BigDecimal;
import java.util.List;

/**
 * 动力节点乌兹
 * 2022-4-27
 */
public interface InvestService {
    /*根据产品ID查询某个产品的投资记录*/
    List<BidInfoProduct> queryBidListByProductId(Integer productId,Integer pageNo,Integer pageSize);

    /*投资理财产品  返回值int是投资的结果  1:投资成功*/
    int investProduct(Integer uid, Integer productId, BigDecimal money);

    /*根据用户id分页查询投资记录*/
    List<UserCenterBidPage> queryPageByUid(Integer uid, Integer newPNo, Integer newPSize);
}
