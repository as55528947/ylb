package com.bjpowernode.front.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 动力节点乌兹
 * 2022-5-1
 */
@Component
@ConfigurationProperties(prefix = "jdwx.sms")
public class JdwxSmsConfig {
    private String url;
    private String appkey;
    private String content;
    private String loginText;

    public String getLoginText() {
        return loginText;
    }

    public void setLoginText(String loginTest) {
        this.loginText = loginTest;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAppkey() {
        return appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
