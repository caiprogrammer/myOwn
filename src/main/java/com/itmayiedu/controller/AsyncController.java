package com.itmayiedu.controller;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itmayiedu.service.AsyncService;

@RestController
@RequestMapping("/hello")
public class AsyncController {

	@Autowired
	private AsyncService asyncService;
	
	@RequestMapping("/async")
	public String getAsync(){
		long n = Instant.now().toEpochMilli();
		//异步
		String s = asyncService.asynchSayHello();
		
		long f = Instant.now().toEpochMilli();
		return s + " 时间: " + (f-n);
	}
	
	  /**
	   * 同步方法
	   * * @return
	   */
	  @RequestMapping("/sync")
	  public String getSyncHello(){

	    long n = Instant.now().toEpochMilli();
	    //异步
	    String s = asyncService.synchSayHello();

	    long f = Instant.now().toEpochMilli();
	    return s + " 时间: " + (f-n);
	  }

}
