package com.bjpowernode.front.service;

import com.alibaba.fastjson.JSONObject;
import com.bjpowernode.api.service.UserService;
import com.bjpowernode.front.config.JdwxRealnameConfig;
import com.bjpowernode.util.HttpClientUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 动力节点乌兹
 * 2022-6-13
 */
@Service
public class RealnameServiceImpl {
    @Resource
    private JdwxRealnameConfig realnameConfig;

    @DubboReference(interfaceClass = UserService.class,version = "1.0.0")
    private UserService userService;

    public boolean handleRealname(String phone,String name,String idCard){
        boolean realname = false;
        Map<String,String> params = new HashMap<>();
        params.put("cardNo", idCard);
        params.put("realName", name);
        params.put("appkey", realnameConfig.getAppkey());

        try {
            //使用HttPClient  访问第三方实名认证接口
            //String resp = HttpClientUtils.doGet(realnameConfig.getUrl(), params);
            String resp = "{\n" +
                    "    \"code\": \"10000\",\n" +
                    "    \"charge\": false,\n" +
                    "    \"remain\": 1305,\n" +
                    "    \"msg\": \"查询成功\",\n" +
                    "    \"result\": {\n" +
                    "        \"error_code\": 0,\n" +
                    "        \"reason\": \"成功\",\n" +
                    "        \"result\": {\n" +
                    "            \"realname\": \"" + name + "\",\n" +
                    "            \"idcard\": \"350721197702134399\",\n" +
                    "            \"isok\": true\n" +
                    "        }\n" +
                    "    }\n" +
                    "}";
            //解析返回的Json结果
            JSONObject respObject = JSONObject.parseObject(resp);
            //code=10000  表示查询成功(接口访问成功)
            if ("10000".equalsIgnoreCase(respObject.getString("code"))){
                //解析json  得到姓名与身份证号码是否匹配的结果
                realname = respObject.getJSONObject("result").getJSONObject("result").getBoolean("isok");

                if (realname == true){
                    //处理更新数据库
                    realname = userService.modifyRealname(phone,name,idCard);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return realname;
    }
}
