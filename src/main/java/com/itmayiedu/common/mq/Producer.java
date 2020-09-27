package com.itmayiedu.common.mq;

import javax.jms.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class Producer {

	@Autowired
	private JmsMessagingTemplate jmsMessagingTemplate;
	
	@Autowired
	private Queue queue;
	
	/*@Scheduled(fixedDelay = 5000)
	public void send(){
		jmsMessagingTemplate.convertAndSend(queue,"测试消息队列"+System.currentTimeMillis());
	}*/
}
