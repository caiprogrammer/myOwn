package com.itmayiedu.service.syslog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.itmayiedu.entity.EventMessage;

/**
 * 使用kafka发送消息
 * @author pengdan
 *
 */
@Service
public class KafkaSenderService implements Sender{

	@Autowired
	private KafkaTemplate<String,Object> KafkaTemplate;
	
	public void sendEvent(String param, EventMessage event){
		if ("AUDIT_II".equalsIgnoreCase(param) || "AUDIT_IV".equalsIgnoreCase(param)) {
			int i = (int) (Math.random() * 10) + 1;
			param = param + "_" + i;
		}
		long prefix = System.nanoTime();
		param = prefix + "-" + param;
		KafkaTemplate.send(param,event);//使用kafka模板发送信息
	} 
}
