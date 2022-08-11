package com.bjpowernode.dataservice.mapper;

import com.bjpowernode.api.model.ProductInfo;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface ProductInfoMapper {
    /*查询利率平均值*/
    BigDecimal selectAvgRate();

    /*按产品类型分页查询*/
    List<ProductInfo> selectByTypeLimit(@Param("ptype") Integer pType,
                                         @Param("offset") Integer offset,
                                         @Param("rows") Integer rows);

    /*按产品类型查询该类产品记录总数*/
    Integer selectCountByType(@Param("ptype") Integer pType);

    /*产品详情页面   按产品ID查询该产品详情*/
    ProductInfo selectByPrimaryKey(Integer id);

    /*扣除产品剩余可投资金额*/
    int updateLeftProductMoney(@Param("pid") Integer productId, @Param("money") BigDecimal money);

    /*以下由逆向工程生成*/
    int deleteByPrimaryKey(Integer id);

    /*更新产品为满标*/
    int updateSelled(@Param("id") Integer productId);

    /*查询满标的产品列表*/
    List<ProductInfo> selectFullTimeProducts(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

    /*更新产品状态*/
    int updateStatus(@Param("id") Integer id, @Param("newStatus") int newStatus);

    int insert(ProductInfo record);

    int insertSelective(ProductInfo record);


    int updateByPrimaryKeySelective(ProductInfo record);

    int updateByPrimaryKey(ProductInfo record);
}