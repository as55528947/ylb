package com.bjpowernode.dataservice.mapper;

import com.bjpowernode.api.model.RechargeRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RechargeRecordMapper {
    /*按userId查询充值记录*/
    List<RechargeRecord> selectById(@Param("uid") Integer uid, @Param("offset") int offset, @Param("rows") Integer rows);

    /*根据订单号查询充值记录*/
    RechargeRecord selectByRechargeNo(@Param("rechargeNo") String orderId);

    /*更新充值状态*/
    int updateStatus(@Param("id") Integer id, @Param("newStatus") int rechargeStatusSuccess);

    int deleteByPrimaryKey(Integer id);

    int insert(RechargeRecord record);

    int insertSelective(RechargeRecord record);

    RechargeRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RechargeRecord record);

    int updateByPrimaryKey(RechargeRecord record);


}