package com.bjpowernode.dataservice;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//启动dubbo服务
@EnableDubbo
//Mapper包扫描
@MapperScan(basePackages = "com.bjpowernode.dataservice.mapper")
@SpringBootApplication
public class MicrDataserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicrDataserviceApplication.class, args);
	}

}
