package com.bjpowernode.dataservice.service;

import com.bjpowernode.api.model.BidInfo;
import com.bjpowernode.api.model.FinanceAccount;
import com.bjpowernode.api.model.ProductInfo;
import com.bjpowernode.api.pojo.BidInfoProduct;
import com.bjpowernode.api.service.InvestService;
import com.bjpowernode.constants.YLBContants;
import com.bjpowernode.dataservice.mapper.BidInfoMapper;
import com.bjpowernode.dataservice.mapper.FinanceAccountMapper;
import com.bjpowernode.dataservice.mapper.ProductInfoMapper;
import com.bjpowernode.api.pojo.UserCenterBidPage;
import com.bjpowernode.util.CommonUtil;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 动力节点乌兹
 * 2022-4-27
 */
@DubboService(interfaceClass = InvestService.class, version = "1.0.0")
public class InvestServiceImpl implements InvestService {

    @Resource
    private BidInfoMapper bidInfoMapper;

    @Resource
    private FinanceAccountMapper accountMapper;

    @Resource
    private ProductInfoMapper productInfoMapper;

    /*根据产品ID查询某个产品的投资记录*/
    @Override
    public List<BidInfoProduct> queryBidListByProductId(Integer productId, Integer pageNo, Integer pageSize) {
        List<BidInfoProduct> bidInfoProductList = null;
        if (productId != null && productId > 0) {
            pageNo = CommonUtil.defaultPageNo(pageNo);
            pageSize = CommonUtil.defaultPageSize(pageSize);
            int offset = (pageNo - 1) * pageSize;
            bidInfoProductList = bidInfoMapper.selectByProductId(productId, offset, pageSize);
        }

        return bidInfoProductList;
    }

    /*投资理财产品  返回值int是投资的结果  1:投资成功*/
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int investProduct(Integer uid, Integer productId, BigDecimal money) {
        int result = 0;
        int rows = 0;
        //1.检查参数
        if ((uid != null && uid > 0)
                && (productId != null && productId > 0)
                && (money != null && money.intValue() >= 100)) {
            //2.查询用户账户余额
            FinanceAccount account = accountMapper.selectByUidForUpdate(uid);
            if (account != null) {
                if (CommonUtil.ge(account.getAvailableMoney(), money)) {
                    //资金满足购买需求
                    //3.检查产品是否可以购买
                    ProductInfo productInfo = productInfoMapper.selectByPrimaryKey(productId);
                    //状态值 0 为可投资状态
                    if (productInfo != null && productInfo.getProductStatus() == YLBContants.PRODUCT_STATUS_SELLING) {
                        if ((CommonUtil.ge(productInfo.getBidMaxLimit(), money)) &&
                                (CommonUtil.ge(money, productInfo.getBidMinLimit()) &&
                                        CommonUtil.ge(productInfo.getLeftProductMoney(), money))) {
                            //满足产品购买要求  4.扣除账户资金
                            rows = accountMapper.updateAvailableMoneyByInvest(uid, money);
                            if (rows < 1) {
                                throw new RuntimeException("投资更新账号资金失败");
                            }

                            //5.扣除产品剩余可投资金额
                            rows = productInfoMapper.updateLeftProductMoney(productId, money);
                            if (rows < 1) {
                                throw new RuntimeException("投资更新产品剩余投资金额失败");
                            }

                            //6.创建投资记录
                            BidInfo bidInfo = new BidInfo();
                            bidInfo.setBidStatus(YLBContants.INVEST_STATUS_SUCC);
                            bidInfo.setUid(uid);
                            bidInfo.setBidMoney(money);
                            bidInfo.setBidTime(new Date());
                            bidInfo.setproductId(productId);
                            bidInfoMapper.insertSelective(bidInfo);

                            //7.判断产品是否卖完，更新产品满标状态
                            ProductInfo dbProductInfo = productInfoMapper.selectByPrimaryKey(productId);
                            if (dbProductInfo.getLeftProductMoney().compareTo(new BigDecimal("0")) == 0) {
                                rows = productInfoMapper.updateSelled(productId);

                                if (rows < 1) {
                                    throw new RuntimeException("投资更新产品满标状态失败");
                                }
                            }
                            //8.最后投资成功
                            result = 1;
                        }
                    } else {
                        result = 4;//理财产品不存在
                    }
                } else {
                    result = 3;//用户账户余额不足本次购买理理财产品
                }

            } else {
                result = 2;//账号资金不存在
            }


        }
        return result;
    }

    /*根据用户id分页查询投资记录*/
    @Override
        public List<UserCenterBidPage> queryPageByUid(Integer uid, Integer offset, Integer newPSize) {

        List<UserCenterBidPage> bidInfoList = new ArrayList<>();
        bidInfoList = bidInfoMapper.selectPageByUid(uid,offset,newPSize);
        return bidInfoList;
    }
}
