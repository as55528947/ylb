package com.bjpowernode.front.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 动力节点乌兹
 * 2022-5-1
 */
@Component
@ConfigurationProperties(prefix = "jdwx.realname")
public class JdwxRealnameConfig {
    private String url;
    private String appkey;

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
}
