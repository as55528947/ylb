package com.bjpowernode.pay.controller;

import com.bjpowernode.api.model.User;
import com.bjpowernode.pay.service.KqService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Map;

/**
 * 动力节点乌兹
 * 2022-6-26
 */
@Controller
@RequestMapping("/kq")
public class KuiQianController {
    @Resource
    private KqService kqService;

    /*接收来自前端vue项目的请求*/
    @GetMapping("/rece/recharge")
    public String receFrontRechargeKQ(Integer uid,
                                      BigDecimal rechargeMoney,
                                      Model model) {
        //默认是错误视图
        String view = "err";

        if (uid != null && uid > 0 && rechargeMoney != null && rechargeMoney.doubleValue() > 0) {
            try {
                User user = kqService.queryUser(uid);
                if (user != null) {
                    //创建快钱接口需要的请求数据
                    Map<String, String> map = kqService.generateFormDate(uid, user.getPhone(), rechargeMoney);
                    model.addAllAttributes(map);

                    //生成充值记录
                    kqService.addRecharge(uid, map.get("orderId"), rechargeMoney);
                    //把订单号放在redis中
                    kqService.addOrderIdtoRedis(map.get("orderId"));
                    //提交支付请求给快钱的form页面(thymeleaf)
                    view = "kqForm";

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return view;
    }

    /*接收快钱返回结果的地址  bgUrl*/
    @GetMapping("/rece/notify")
    @ResponseBody
    public String payResultNotify(HttpServletRequest request) {
        //System.out.println("=================================收到异步通知=================================");
        kqService.keNotify(request);
        return "<result>1</result><redirecturl>http://localhost:8080/</redirecturl>";
    }

    //定时任务  补单问题  快钱方查询订单处理结果
    @GetMapping("/rece/query")
    @ResponseBody
    public String queryKQOrderId() {
        kqService.handleQueryOrderId();
        return "快钱接收了查询订单的请求";
    }

}
