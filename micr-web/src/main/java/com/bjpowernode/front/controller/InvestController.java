package com.bjpowernode.front.controller;

import com.bjpowernode.api.model.User;
import com.bjpowernode.constants.RedisKey;
import com.bjpowernode.front.view.RankView;
import com.bjpowernode.front.view.RespResult;
import com.bjpowernode.util.CommonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 动力节点乌兹
 * 2022-4-21
 */
@Api(tags = "投资理财产品")
@RestController
public class InvestController extends BaseController {
    Logger logger = LoggerFactory.getLogger(UserController.class);

    /*投资排行榜*/
    @ApiOperation(value = "投资排行榜", notes = "显示投资金额最高的三位用户")
    @GetMapping("/v1/invest/rank")
    public RespResult showInvestRank() {
        RespResult result = RespResult.ok();
        List<RankView> rankList = new ArrayList<>();
        //从redis查询数据
        Set<ZSetOperations.TypedTuple<String>> sets = stringRedisTemplate.boundZSetOps(RedisKey.KEY_INVEST_RANK).reverseRangeWithScores(0, 2);

        //遍历set
        sets.forEach(tuple -> {
            //tuple.getValue();//手机号
            //tuple.getScore();//投资金额
            rankList.add(new RankView(CommonUtil.tuoMinPhone(tuple.getValue()), tuple.getScore()));
        });

        //把redis中投资金额排在前三的用户写入返回值
        result.setList(rankList);
        return result;
    }

    /*购买理财产品,更新投资排行榜*/
    @ApiOperation(value = "投资理财产品")
    @PostMapping("/v1/invest/product")
    public RespResult investProduct(@RequestHeader("uid") Integer uid,
                                    @RequestParam Integer productId,
                                    @RequestParam BigDecimal money) {
        RespResult result = RespResult.fail();
        if ((uid != null && uid > 0)
                && (productId != null && productId > 0)
                && (money != null && money.intValue() >= 100)) {
            int investResult = investService.investProduct(uid, productId, money);
            switch (investResult) {
                case 0:
                    result.setMsg("投资数据不准确");
                    break;
                case 1:
                    logger.info(String.valueOf(uid) + "完成一笔投资" + new Date() + "投资金额:" + money + "产品id:" + productId);
                    result = RespResult.ok();
                    modifyInvestRank(uid, money);
                    break;
                case 2:
                    result.setMsg("账号资金不存在");
                    break;
                case 3:
                    result.setMsg("用户账户余额不足本次购买理理财产品");
                    break;
                case 4:
                    result.setMsg("理财产品不存在");
                    break;
            }
        }
        return result;
    }

   /* *//*分页查询用户充值记录*//*
    @ApiOperation(value = "分页查询用户充值记录")
    @GetMapping("/v1/invest/invests")
    public RespResult queryInvestPage(@RequestHeader("uid") Integer uid,
                                      @RequestParam(required = false,defaultValue = "1") Integer page,
                                      @RequestParam(required = false,defaultValue = "6")  Integer pageSize){
        RespResult result = RespResult.fail();
        int newPNo = 0;
        int newPSize = 0;
        int offset = 0;
        if (uid != null && uid >0){
            result = RespResult.ok();
            newPNo = CommonUtil.defaultPageNo(page);
            newPSize = CommonUtil.defaultPageSize(pageSize);
            //根据页数计算数据偏移量
            offset = (page - 1) * newPSize;
            List<UserCenterBidPage> bidInfoList = investService.queryPageByUid(uid,offset,newPSize);
            result.setData(bidInfoList);
        }

        return result;
    }*/

    private void modifyInvestRank(Integer uid, BigDecimal money) {
        User user = userService.queryById(uid);
        if (user != null) {
            //更新redis中的投资排行榜
            String key = RedisKey.KEY_INVEST_RANK;
            stringRedisTemplate.boundZSetOps(key).incrementScore(user.getPhone(), money.doubleValue());
        }
    }
}
