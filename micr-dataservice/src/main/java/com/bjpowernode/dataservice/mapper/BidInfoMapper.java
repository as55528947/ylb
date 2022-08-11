package com.bjpowernode.dataservice.mapper;
import com.bjpowernode.api.model.BidInfo;
import com.bjpowernode.api.pojo.BidInfoProduct;
import com.bjpowernode.api.pojo.UserCenterBidPage;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface BidInfoMapper {
    /*某个产品的投资记录  分页*/
    List<BidInfoProduct> selectByProductId(@Param("productId") Integer productId,
                                           @Param("offset") Integer pageNo,
                                           @Param("rows") Integer pageSize);


    /*查询某个产品的投资记录*/
    List<BidInfo> selectByProdId(@Param("productId") Integer productId);

    /*根据用户id分页查询投资记录*/
    List<UserCenterBidPage> selectPageByUid(@Param("uid") Integer uid, @Param("offset") Integer offset, @Param("rows") Integer newPSize);

    BigDecimal selectSumBidMoney();

    int deleteByPrimaryKey(Integer id);

    int insert(BidInfo record);

    int insertSelective(BidInfo record);

    BidInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BidInfo record);

    int updateByPrimaryKey(BidInfo record);



}