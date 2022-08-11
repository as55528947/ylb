package com.bjpowernode.api.service;

import com.bjpowernode.api.model.ProductInfo;
import com.bjpowernode.api.pojo.MultiProduct;

import java.util.List;

/**
 * 动力节点乌兹
 * 2022-4-19
 */
public interface ProductService {
    /*根据产品类型查询产品  分页查询*/
    List<ProductInfo> queryByTypeLimit(Integer pType,Integer pageNo,Integer pageSize);

    /*按产品类型查询该类产品记录总数*/
    Integer queryRecordNumsByType(Integer pType);

    /*首页的多个产品数据*/
    MultiProduct queryIndexPageProduct();

    /*产品详情页面   按产品ID查询该产品详情*/
    ProductInfo queryProductByPid(Integer pid);
}
