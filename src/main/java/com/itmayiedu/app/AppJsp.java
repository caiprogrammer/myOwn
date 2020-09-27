
package com.itmayiedu.app;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

//import com.itmayiedu.datasource.DBConfig1;
//import com.itmayiedu.datasource.DBConfig2;

@ComponentScan(basePackages={"com.itmayiedu.controller","com.itmayiedu.service","com.itmayiedu.common","com.itmayiedu.impl","com.itmayiedu.test01","com.itmayiedu.test02"})
//@EnableConfigurationProperties(value = { DBConfig1.class, DBConfig2.class })//配置多个数据源
@MapperScan(basePackages={"com.itmayiedu.mapper"})
@EnableAutoConfiguration
//@EnableScheduling//定时任务配置
@EnableAsync//异步方法,使用这个注解，springboot会帮我们开多个线程去处理异步方法
public class AppJsp {

	public static void main(String[] args) {
		SpringApplication.run(AppJsp.class, args);
	}

}
