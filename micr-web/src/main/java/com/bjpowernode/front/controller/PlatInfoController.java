package com.bjpowernode.front.controller;
import com.bjpowernode.api.pojo.BaseInfo;
import com.bjpowernode.enums.RCode;
import com.bjpowernode.front.view.RespResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 动力节点乌兹
 * 2022-4-19
 */
@Api(tags = "平台信息功能")
@RestController
@RequestMapping("/v1")
public class PlatInfoController extends BaseController{
    /*平台基本信息*/
    @ApiOperation(value = "平台三项基本信息",notes = "注册人数，平均的利率，累计成交金额")
    @GetMapping("/plat/info")
    public RespResult queryBaseInfo(){
        /*调用远程服务*/
        BaseInfo baseInfo = platBaseService.queryPlatBaseInfo();

        RespResult respResult = new RespResult();
        respResult.setCode(RCode.SUCC);
        respResult.setMsg("查询平台信息成功");
        respResult.setData(baseInfo);

        return respResult;
    }
}
