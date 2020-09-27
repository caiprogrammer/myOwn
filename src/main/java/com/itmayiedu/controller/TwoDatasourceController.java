//package com.itmayiedu.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.itmayiedu.test01.service.UserServiceTest01;
//import com.itmayiedu.test02.service.UserServiceTest02;
//
///**
// * 多数据源配置测试类
// * @author Administrator
// *
// */
//@RestController
//@RequestMapping("/data")
//public class TwoDatasourceController {
//
//	@Autowired
//	private UserServiceTest01 userServiceTest01;
//	
//	@Autowired
//	private UserServiceTest02 userServiceTest02;
//	
//	@RequestMapping("/insert")
//	public String insert001(String name,int age){
//		return userServiceTest01.insertTest001(name, age);
//	}
//	
//}
