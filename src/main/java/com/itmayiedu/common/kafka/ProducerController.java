package com.itmayiedu.common.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProducerController {
	
	@Autowired
	private KafkaTemplate<String,Object> KafkaTemplate;
	
	@RequestMapping("message/send")
	public String send(String msg){
		KafkaTemplate.send("demo9211",msg);//使用kafka模板发送信息
		return "success";
	}
	
}
