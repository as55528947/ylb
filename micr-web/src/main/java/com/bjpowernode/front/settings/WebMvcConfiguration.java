package com.bjpowernode.front.settings;

import com.bjpowernode.front.interceptor.TokenInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 动力节点乌兹
 * 2022-4-27
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        TokenInterceptor tokenInterceptor = new TokenInterceptor(jwtSecret);
        String[] addPath = {"/v1/user/realname"};
        registry.addInterceptor(tokenInterceptor).addPathPatterns(addPath);
    }

    /*处理跨域*/
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        System.out.println("===========addCorsMappings===========");
        registry.addMapping("/**")
                .allowedOriginPatterns("http://localhost:8080")
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true)
                .maxAge(3600)
                .allowedHeaders("*");
    }
}
