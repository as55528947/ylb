package com.bjpowernode.front.controller;

import com.bjpowernode.constants.RedisKey;
import com.bjpowernode.enums.RCode;
import com.bjpowernode.front.service.SmsService;
import com.bjpowernode.front.view.RespResult;
import com.bjpowernode.util.CommonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 动力节点乌兹
 * 2022-5-1
 */
@Api(tags = "短信业务")
@RestController
@RequestMapping("/v1/sms")
public class SmsController extends BaseController {

    @Resource(name = "SmsCodeRegisterImpl")
    SmsService smsService;

    @Resource(name = "SmsCodeLoginImpl")
    SmsService loginSmsService;

    @ApiOperation(value = "发送短信验证码")
    @GetMapping("/code/register")
    public RespResult sendCodeRegister(@RequestParam String phone) {
        RespResult result = RespResult.fail();
        if (CommonUtil.checkPhone(phone)) {
            //判断redis中是否有这个手机号的验证码
            String key = RedisKey.KEY_SMS_CODE_REG + phone;
            if (stringRedisTemplate.hasKey(key)) {
                result = RespResult.ok();
                result.setCode(RCode.PHONE_CAN_USE);
            }else {
                boolean isSuccess = smsService.sendSms(phone);
                if (isSuccess) {
                    result = RespResult.ok();
                }
            }
        }else {
            result.setCode(RCode.PHONE_FORMAT_ERR);
        }
        return result;
    }

    //登录时验证码
    @GetMapping("/code/login")
    public RespResult sendCodeLogin(@RequestParam String phone) {
        RespResult result = RespResult.fail();
        if (CommonUtil.checkPhone(phone)) {
            //判断redis中是否存有这个手机号的验证码
            String key = RedisKey.KEY_SMS_CODE_REG + phone;
            if (stringRedisTemplate.hasKey(key)) {
                result = RespResult.ok();
                result.setCode(RCode.PHONE_CAN_USE);
            }else {
                boolean isSuccess = loginSmsService.sendSms(phone);
                if (isSuccess) {
                    result = RespResult.ok();
                }
            }
        }else {
            result.setCode(RCode.PHONE_FORMAT_ERR);
        }
        return result;
    }
}