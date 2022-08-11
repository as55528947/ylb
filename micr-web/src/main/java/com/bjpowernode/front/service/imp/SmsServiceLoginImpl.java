package com.bjpowernode.front.service.imp;

import com.alibaba.fastjson.JSONObject;
import com.bjpowernode.constants.RedisKey;
import com.bjpowernode.front.config.JdwxSmsConfig;
import com.bjpowernode.front.service.SmsService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 动力节点乌兹
 * 2022-5-2
 */
@Service(value = "SmsCodeLoginImpl")
public class SmsServiceLoginImpl implements SmsService {
    @Resource
    private JdwxSmsConfig jdwxSmsConfig;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean sendSms(String phone) {
        boolean smsSendResult = false;
        //验证码随机数
        String random = RandomStringUtils.randomNumeric(4);
        System.out.println("手机号---->" + phone +  "的验证码为:" + random);
        //更新配置文件中短信内容 %s 【利盈宝】登录验证码是：%s，3分钟内有效！请勿泄露他人
        String content = String.format(jdwxSmsConfig.getLoginText(), random);
        //使用HttpClient发送Get请求给第三方
        CloseableHttpClient client = HttpClients.createDefault();
        String url = jdwxSmsConfig.getUrl() + "?mobile=" + phone +"&content=" + content + "&appkey=" + jdwxSmsConfig.getAppkey();

        HttpGet get = new HttpGet(url);

        try {
            //CloseableHttpResponse response = client.execute(get);
            //从响应信息中获取状态码  如果是200
            //if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                //得到返回的数据  json
                //String text = EntityUtils.toString(response.getEntity());
                //使用测试数据
                String text = "{\n" +
                        "    \"code\": \"10000\",\n" +
                        "    \"charge\": false,\n" +
                        "    \"remain\": 1305,\n" +
                        "    \"msg\": \"查询成功\",\n" +
                        "    \"result\": {\n" +
                        "        \"ReturnStatus\": \"Success\",\n" +
                        "        \"Message\": \"ok\",\n" +
                        "        \"RemainPoint\": 420842,\n" +
                        "        \"TaskID\": 18424321,\n" +
                        "        \"SuccessCounts\": 1\n" +
                        "    }\n" +
                        "}";

                //解析json
                if (StringUtils.isNotBlank(text)){
                    //fastjson
                    JSONObject jsonObject = JSONObject.parseObject(text);
                    //读取result中的key:ReturnStatus
                    if ("Success".equalsIgnoreCase(jsonObject.getJSONObject("result").getString("ReturnStatus"))){
                        smsSendResult = true;
                        //把短信验证码存到redis中
                        stringRedisTemplate.boundValueOps(RedisKey.KEY_SMS_CODE_LOGIN + phone).set(random, 3, TimeUnit.MINUTES);
                    }
                }
            //}
        }catch (Exception e){
            e.printStackTrace();
        }
        return smsSendResult;
    }

    @Override
    public boolean checkSmsCode(String phone, String code) {
        String key = RedisKey.KEY_SMS_CODE_LOGIN + phone;
        if (stringRedisTemplate.hasKey(key)){
            String querySmsCode = stringRedisTemplate.boundValueOps(key).get();
            if (code.equals(querySmsCode)){
                return true;
            }
        }

        return false;
    }
}
