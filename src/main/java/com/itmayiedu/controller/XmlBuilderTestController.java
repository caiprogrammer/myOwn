package com.itmayiedu.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itmayiedu.service.ConfigDataServiceImpl;
import com.itmayiedu.service.syslog.config.SystemName;


/**
 * springboot整合xml配置文件测试类
 * @author pengdan
 *
 */
@Controller
public class XmlBuilderTestController {

	@Autowired
	ConfigDataServiceImpl configDataServiceImpl;
	
	
	@RequestMapping("/index/getxml")
	public String getXml(Map<String,Object> result){
		
		List<SystemName> systemList = new ArrayList<SystemName>();
		try {
			systemList = configDataServiceImpl.listSystem();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		result.put("list", systemList);
		return "index";
		
	}
	
}
