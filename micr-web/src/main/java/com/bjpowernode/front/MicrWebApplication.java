package com.bjpowernode.front;

import com.bjpowernode.front.controller.BaseController;
import com.bjpowernode.util.JwtUtil;
import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


//启动swagger 和 swagger-ui
@EnableSwagger2
@EnableSwaggerBootstrapUI
//启动dubbo服务
@EnableDubbo
@SpringBootApplication()
public class MicrWebApplication {

	@Value("${jwt.secret}")
	private String secretKey;

	//创建jwt
	@Bean
	public JwtUtil jwtUtil(){
		JwtUtil jwtUtil = new JwtUtil(secretKey);
		return jwtUtil;
	}

	public static void main(String[] args) {
		SpringApplication.run(MicrWebApplication.class, args);
	}

}
