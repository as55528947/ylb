package com.bjpowernode.dataservice.service;

import com.bjpowernode.api.model.RechargeRecord;
import com.bjpowernode.api.service.RechargeService;
import com.bjpowernode.constants.YLBContants;
import com.bjpowernode.dataservice.mapper.FinanceAccountMapper;
import com.bjpowernode.dataservice.mapper.RechargeRecordMapper;
import com.bjpowernode.util.CommonUtil;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 动力节点乌兹
 * 2022-6-16
 */
@DubboService(interfaceClass = RechargeService.class,version = "1.0.0")
public class RechargeServiceImpl implements RechargeService {
    @Resource
    private RechargeRecordMapper rechargeRecordMapper;

    @Resource
    private FinanceAccountMapper accountMapper;
    /*根据userId查询他的充值记录*/
    @Override
    public List<RechargeRecord> queryByUid(Integer uid, Integer pageNo, Integer pageSize) {
        List<RechargeRecord> records = new ArrayList<>();
        if (uid != null && uid>0){
            pageNo = CommonUtil.defaultPageNo(pageNo);
            pageSize = CommonUtil.defaultPageSize(pageSize);
            int offset = (pageNo - 1) * pageSize;
            records = rechargeRecordMapper.selectById(uid,offset,pageSize);
        }

        return records;
    }

    /*生成充值记录*/
    @Override
    public int addRechargeRecord(RechargeRecord record) {

        return rechargeRecordMapper.insertSelective(record);
    }

    /*处理后续充值*/
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.NESTED)
    @Override
    public synchronized int handleKQNotify(String orderId, String payAmount, String payResult) {
        int result = 0;
        int rows = 0;
        RechargeRecord record = rechargeRecordMapper.selectByRechargeNo(orderId);
        //判断该订单号是否存在充值记录
        if ( record!= null){
            //判断充值状态
            if (record.getRechargeStatus()==YLBContants.RECHARGE_STATUS_PROCESSING){
                //判断金额是否一致
                String fen = record.getRechargeMoney().multiply(new BigDecimal("100")).stripTrailingZeros().toPlainString();
                if (fen.equals(payAmount)){
                    if ("10".equals(payResult)){
                        //充值成功
                        rows = accountMapper.updateAvailableMoneyByRecharge(record.getUid(),record.getRechargeMoney());
                        if (rows < 1){
                            throw new RuntimeException("充值更新资金账号失败");
                        }
                        //更新充值记录
                        rows = rechargeRecordMapper.updateStatus(record.getId(),YLBContants.RECHARGE_STATUS_SUCCESS);
                        if (rows < 1){
                            throw new RuntimeException("更新充值状态失败");
                        }
                        result = 1;
                    }else {
                        //充值失败
                        //更新充值记录
                        rows = rechargeRecordMapper.updateStatus(record.getId(),YLBContants.RECHARGE_STATUS_FAIL);
                        if (rows < 1){
                            throw new RuntimeException("更新充值状态失败");
                        }
                        result = 2;//充值结果失败
                    }
                }else {
                    result = 4;//充值金额不一致
                }
            }else {
                result = 3;//订单已经处理过了
            }
        }
        return result;
    }
}
