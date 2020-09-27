package com.itmayiedu.controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.itmayiedu.service.RedisService;


@RestController
public class RedisController {

	private static Logger logger = Logger.getLogger(RedisController.class);
	
	@Autowired
	private RedisService redisService;
	
	@RequestMapping("/setRedis")
	public String setRedis(){
		redisService.setObject("test", "redis");
		return "success";
	}

	@RequestMapping("/getRedis")
	public String getRedis(){
		logger.info("redis测试：获取redis中的值......");
		String value = redisService.getString("test");
		return value;
	}
}
