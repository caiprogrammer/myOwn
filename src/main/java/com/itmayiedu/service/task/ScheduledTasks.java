package com.itmayiedu.service.task;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
@Component
public class ScheduledTasks {

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	
	//@Scheduled(fixedRate = 5000)
	public void reportCurrentTime(){
		//System.out.println("现在时间："+dateFormat.format(new Date()));
	}
}
