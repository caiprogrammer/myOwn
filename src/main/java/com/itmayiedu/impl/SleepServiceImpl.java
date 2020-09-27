package com.itmayiedu.impl;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.itmayiedu.service.SleepService;
@Service
public class SleepServiceImpl implements SleepService{

	@Override
	public void syncSleep() throws InterruptedException {
		System.out.println("线程名: " +Thread.currentThread().getName());
	    System.out.println("开始同步休眠3秒");
	    Thread.sleep(3000);
	    System.out.println("同步休眠结束");
	}

	@Override
	@Async
	public void asyncSleep() throws InterruptedException {
		System.out.println("次线程 "+Thread.currentThread().getName());
	    System.out.println("开始异步休眠3秒");
	    Thread.sleep(3000);
	    System.out.println("异步休眠休眠结束");
	}

	
}
