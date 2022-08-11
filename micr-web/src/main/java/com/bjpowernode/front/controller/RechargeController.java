package com.bjpowernode.front.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RestController;

/**
 * 动力节点乌兹
 * 2022-6-16
 */
@Api(tags = "充值业务")
@RestController
public class RechargeController extends BaseController {
    /*查询充值流水*//*
    @ApiOperation(value = "查询某个用户的充值记录")
    @GetMapping("/v1/recharge/records")
    public RespResult queryRechargePage(@RequestHeader("uid") Integer uid,
                                        @RequestParam(required = false,defaultValue = "1") Integer pageNo,
                                        @RequestParam(required = false,defaultValue = "6") Integer pageSize){
        RespResult result = RespResult.fail();
        if (uid != null && uid >0){
            List<RechargeRecord> records = rechargeService.queryByUid(uid, pageNo, pageSize);
            result = RespResult.ok();
            result.setList(toView(records));
            //没有做分页
        }

        return result;
    }
    private List<ResultView> toView(List<RechargeRecord> src){
        List<ResultView> target = new ArrayList<>();
        src.forEach(record ->{
            target.add(new ResultView(record));
        });
        return target;
    }*/
}
