package com.itmayiedu.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itmayiedu.service.AsyncService;
import com.itmayiedu.service.SleepService;
@Service
public class AsyncServiceImpl implements AsyncService{

	@Autowired
	private SleepService sleepService;

	@Override
	public String synchSayHello() {
		try {
			System.out.println("主线程 "+Thread.currentThread().getName());
			sleepService.syncSleep();
			return "这是同步方法!";
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "error";
		}
	}

	@Override
	public String asynchSayHello() {
		try {
			System.out.println("主线程 "+Thread.currentThread().getName());
			sleepService.asyncSleep();
			return "这是异步方法!";
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "error";
		}
	}

	
}
