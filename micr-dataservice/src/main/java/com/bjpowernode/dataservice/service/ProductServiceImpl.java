package com.bjpowernode.dataservice.service;

import com.bjpowernode.api.model.ProductInfo;
import com.bjpowernode.api.pojo.MultiProduct;
import com.bjpowernode.api.service.ProductService;
import com.bjpowernode.constants.YLBContants;
import com.bjpowernode.dataservice.mapper.ProductInfoMapper;
import com.bjpowernode.util.CommonUtil;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 动力节点乌兹
 * 2022-4-19
 */
@DubboService(interfaceClass = ProductService.class,version = "1.0.0")
public class ProductServiceImpl implements ProductService {
    @Resource
    private ProductInfoMapper productInfoMapper;

    /*按类型分页查询产品*/
    @Override
    public List<ProductInfo> queryByTypeLimit(Integer pType, Integer pageNo, Integer pageSize) {
        List<ProductInfo> productInfos = new ArrayList<>();

        if (pType == 0||pType == 1||pType == 2){
            //验证参数是否有误
            pageNo = CommonUtil.defaultPageNo(pageNo);
            pageSize = CommonUtil.defaultPageSize(pageSize);
            int offset = (pageNo - 1) * pageSize;
            productInfos = productInfoMapper.selectByTypeLimit(pType, offset, pageSize);
        }

        return productInfos;
    }

    /*按产品类型查询该类产品记录总数*/
    @Override
    public Integer queryRecordNumsByType(Integer pType) {
        Integer counts = 0;
        if (pType == 0||pType == 1||pType ==2){
            counts = productInfoMapper.selectCountByType(pType);
        }

        return counts;
    }

    /*首页的多个产品数据*/
    @Override
    public MultiProduct queryIndexPageProduct() {
        MultiProduct result = new MultiProduct();
        List<ProductInfo> XinShou = productInfoMapper.selectByTypeLimit(YLBContants.PRODUCT_TYPE_XINSHOUBAO, 0, 1);
        List<ProductInfo> YouXuan = productInfoMapper.selectByTypeLimit(YLBContants.PRODUCT_TYPE_YOUXUAN, 0, 3);
        List<ProductInfo>  SanBiao= productInfoMapper.selectByTypeLimit(YLBContants.PRODUCT_TYPE_SANBIAO, 0, 3);

        result.setXinShouBao(XinShou);
        result.setYouXuan(YouXuan);
        result.setSanBiao(SanBiao);
        return result;
    }

    /*产品详情页面   按产品ID查询该产品详情*/
    @Override
    public ProductInfo queryProductByPid(Integer pid) {
        ProductInfo productInfo = null;
        if (pid != null && pid >0) {
            productInfo = productInfoMapper.selectByPrimaryKey(pid);
        }

        return productInfo;
    }
}
