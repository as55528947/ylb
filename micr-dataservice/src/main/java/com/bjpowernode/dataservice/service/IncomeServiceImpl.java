package com.bjpowernode.dataservice.service;

import com.bjpowernode.api.model.BidInfo;
import com.bjpowernode.api.model.IncomeRecord;
import com.bjpowernode.api.model.ProductInfo;
import com.bjpowernode.api.service.IncomeService;
import com.bjpowernode.constants.YLBContants;
import com.bjpowernode.dataservice.mapper.BidInfoMapper;
import com.bjpowernode.dataservice.mapper.FinanceAccountMapper;
import com.bjpowernode.dataservice.mapper.IncomeRecordMapper;
import com.bjpowernode.dataservice.mapper.ProductInfoMapper;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 动力节点乌兹
 * 2022-6-21
 */
@Async
@DubboService(interfaceClass = IncomeService.class, version = "1.0.0")
public class IncomeServiceImpl implements IncomeService {
    @Resource
    private ProductInfoMapper productInfoMapper;

    @Resource
    private BidInfoMapper bidInfoMapper;

    @Resource
    private IncomeRecordMapper incomeMapper;

    @Resource
    private FinanceAccountMapper accountMapper;

    /*收益计划  定时任务控制*/
    @Transactional(rollbackFor = Exception.class)
    @Override
    public synchronized void generateIncomePlan() {
        //1.获取要处理的理财产品记录
        Date currentDate = new Date();
        Date beginTime = DateUtils.truncate(DateUtils.addDays(currentDate, -1), Calendar.DATE);
        Date endTime = DateUtils.truncate(currentDate, Calendar.DATE);

        List<ProductInfo> productList = productInfoMapper.selectFullTimeProducts(beginTime, endTime);

        //2.查询满标产品的投资记录
        int rows = 0;
        BigDecimal income = null;     //利息金额(不算本金)
        BigDecimal dayRate = null;    //日利率
        BigDecimal cycle = null;      //周期
        Date incomeDate = null;       //到期时间(结算利息的时间)

        for (ProductInfo productInfo : productList) {
            //计算日利率              /360/100
            dayRate = productInfo.getRate().divide(new BigDecimal("360"), 10, RoundingMode.HALF_DOWN).

                    divide(new BigDecimal("100"), 10, RoundingMode.HALF_UP);
            //查询产品类型   新手包周期  7天  其他产品  cycle单位为月需要 *30   统一周期单位为天
            if (productInfo.getProductType() == 0) {
                cycle = new BigDecimal(productInfo.getCycle());

                incomeDate = DateUtils.addDays(productInfo.getProductFullTime(), (productInfo.getCycle() + 1));
            } else {
                cycle = new BigDecimal(productInfo.getCycle() * 30);
                incomeDate = DateUtils.addDays(productInfo.getProductFullTime(), (productInfo.getCycle() * 30 + 1));
            }
            List<BidInfo> bidList = bidInfoMapper.selectByProdId(productInfo.getId());
            //3.计算每笔投资的利息
            for (BidInfo bidInfo : bidList) {
                //利息 = 本金 * 周期  * 利率
                income = bidInfo.getBidMoney().multiply(cycle).multiply(dayRate);
                //创建收益记录
                IncomeRecord incomeRecord = new IncomeRecord();
                incomeRecord.setBidId(bidInfo.getId());
                incomeRecord.setUid(bidInfo.getUid());
                incomeRecord.setProdId(bidInfo.getproductId());
                incomeRecord.setBidMoney(bidInfo.getBidMoney());
                incomeRecord.setIncomeDate(incomeDate);
                //充值状态0  表示未返还
                incomeRecord.setIncomeStatus(YLBContants.INCOME_STATUS_PLAN);
                incomeRecord.setIncomeMoney(income);
                incomeMapper.insert(incomeRecord);
            }

            //4.更新产品状态
            rows = productInfoMapper.updateStatus(productInfo.getId(),YLBContants.PRODUCT_STATUS_PLAN);
            if (rows <1){
                throw new RuntimeException("生成受益计划,更新产品状态为2失败");
            }
        }
    }

    /*收益返还*/
    @Transactional(rollbackFor = Exception.class)
    @Override
    public synchronized void generateIncomeBack() {
        //1.获取到期的收益记录
        int rows = 0;
        Date curDate = new Date();
        Date expiredDate = DateUtils.truncate(DateUtils.addDays(curDate, -1), Calendar.DATE);
        System.out.println("expiredDate = " + expiredDate);
        List<IncomeRecord> incomeRecordList = incomeMapper.selectExpiredIncome(expiredDate);
        //2.遍历收益记录集合  返还本金和利息  更新用户资金表
        for (IncomeRecord incomeRecord : incomeRecordList) {
            rows = accountMapper.updateAvailableMoneyByIncomeBack(incomeRecord.getUid(),incomeRecord.getIncomeMoney(),incomeRecord.getBidMoney());
            if (rows < 1){
                throw new RuntimeException("收益返还，更新账号资金失败");
            }
            //3.更新收益记录的状态为1
            incomeRecord.setIncomeStatus(YLBContants.INCOME_STATUS_BACK);
            rows = incomeMapper.updateByPrimaryKey(incomeRecord);
            if (rows < 1){
                throw new RuntimeException("收益返还，更新收益状态失败");
            }

        }
    }
}
