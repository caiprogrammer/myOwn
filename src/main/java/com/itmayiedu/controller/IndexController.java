
package com.itmayiedu.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itmayiedu.entity.User;
import com.itmayiedu.mapper.UserMapper;
import com.itmayiedu.service.ConfigDataServiceImpl;
import com.itmayiedu.service.syslog.config.SystemName;

@Controller
public class IndexController {
	
	@Autowired
	ConfigDataServiceImpl configDataServiceImpl;
//	@Autowired
//	private UserMapper userMapper;

	@RequestMapping("/index")
	public String index() {
		return "index";
	}

	/*@RequestMapping("/index/getxml")
	public String getXml(){
		
		List<SystemName> systemList = new ArrayList<SystemName>();
		try {
			systemList = configDataServiceImpl.listSystem();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map<String,Object> result = new HashMap<String, Object>();
		result.put("list", systemList);
		return "index";
		
	}*/
//	@ResponseBody
//	@RequestMapping("/findByName")
//	public User findByName(String name) {
//		return userMapper.findByName(name);
//	}
//
//	@ResponseBody
//	@RequestMapping("/insert")
//	public String insert(String name, Integer age) {
//		userMapper.insert(name, age);
//		return "success";
//	}

}
