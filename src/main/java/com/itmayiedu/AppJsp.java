package com.itmayiedu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

//import com.itmayiedu.datasource.DBConfig1;
//import com.itmayiedu.datasource.DBConfig2;

@SpringBootApplication
//@EnableConfigurationProperties(value = { DBConfig1.class, DBConfig2.class })
//@ComponentScan(basePackages = "com.itmayiedu.service.syslog")
@MapperScan(basePackages = { "com.itmayiedu.mapper" })
//@EnableAsync
//@EnableScheduling
public class AppJsp {

	public static void main(String[] args) {
		SpringApplication.run(AppJsp.class, args);
	}

}