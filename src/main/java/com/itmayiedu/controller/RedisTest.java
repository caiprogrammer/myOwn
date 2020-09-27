package com.itmayiedu.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;
import com.itmayiedu.app.AppJsp;
import com.itmayiedu.entity.User;

/**
 * redis的测试类
 * @author Administrator
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppJsp.class)
public class RedisTest {
	
	@Autowired
	private RedisTemplate redisTemplate;
	
	@Test
	public void testObj() throws Exception{
		User user = new User(1,"java",20);
		ValueOperations<String, User> operations = redisTemplate.opsForValue();
		operations.set("fdd2", user);
		boolean exists = redisTemplate.hasKey("fdd2");
		System.out.println("redis是否存在相应的key"+exists);
		User getUser = (User) redisTemplate.opsForValue().get("fdd2");
		System.out.println("从redis数据库获取的user："+getUser.toString());
	}
}
